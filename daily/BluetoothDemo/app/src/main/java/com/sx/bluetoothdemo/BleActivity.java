package com.sx.bluetoothdemo;

import android.Manifest;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.joker.annotation.PermissionsDenied;
import com.joker.annotation.PermissionsGranted;
import com.joker.api.Permissions4M;

import net.idik.lib.slimadapter.SlimAdapter;

import java.util.ArrayList;

import cn.com.heaton.blelibrary.BleConfig;
import cn.com.heaton.blelibrary.BleLisenter;
import cn.com.heaton.blelibrary.BleManager;
import cn.com.heaton.blelibrary.BleVO.BleDevice;

/**
 * @Author sunxin
 * @Date 2017/9/15 0015 下午 2:31
 * @Description
 */

public class BleActivity extends AppCompatActivity {

    private static final int BLUETOOTH_ADMIN_CODE = 0X0011;
    private static final int LOCATION_CODE = 0X0012;
    private static final String TAG = "BleActivity";
    private BleManager<BleDevice> mBleManager;
    private ArrayList<BleDevice> mDevices = new ArrayList<>();
    ;

    private BleLisenter bleListener = new BleLisenter() {

        @Override
        public void onStart() {
            super.onStart();
            Log.e(TAG, "onStart: ");
        }


        @Override
        public void onConnectTimeOut() {
            super.onConnectTimeOut();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(BleActivity.this, "连接超时", Toast.LENGTH_SHORT).show();
                    mAdapter.notifyDataSetChanged();
                }
            });
        }

        @Override
        public void onLeScan(final BleDevice device, int rssi, byte[] scanRecord) {
            Log.e(TAG, "onLeScan: " + device.getmBleName());
            synchronized (mBleManager.getLocker()) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //扫描到数据回调
                        mDevices.add(device);
                        mAdapter.notifyDataSetChanged();
                        Log.e(TAG, "initData: Size--> " + mDevices.size());
                    }
                });
            }
        }

        @Override
        public void onChanged(BluetoothGattCharacteristic characteristic) {
            super.onChanged(characteristic);
            //蓝牙的数据已经改变

        }

        @Override
        public void onConnectionChanged(final BleDevice device) {
            //连接状态发生改变回调
            Log.e(TAG, "onConnectionChanged: 状态改变了" + device.getConnectionState() + "---> " + device.isConnected());

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    setConnectedNum();
                    for (int i = 0; i < mAdapter.getItemCount(); i++) {
                        //如果点击的条目和
                        if (device.getBleAddress().equals(mAdapter.getDevice(i).getBleAddress())) {
                            if (device.isConnected()) {
                                mAdapter.getDevice(i).setConnectionState(BleConfig.BleStatus.CONNECTED);
                                Toast.makeText(BleActivity.this, "已连接", Toast.LENGTH_SHORT).show();
                            } else if (device.isConnectting()) {
                                mAdapter.getDevice(i).setConnectionState(BleConfig.BleStatus.CONNECTING);
                            } else {
                                mAdapter.getDevice(i).setConnectionState(BleConfig.BleStatus.DISCONNECT);
                                Toast.makeText(BleActivity.this, "断开连接", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            });

//            mAdapter.getDevice()


        }

        @Override
        public void onReady(BluetoothDevice device) {
            super.onReady(device);
            Log.e(TAG, "onReady: ");
        }
    };

    private RecyclerView mRecyclerView;
    private SlimAdapter mSlimAdapter;
    private MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ble);
        initView();
        initBle();
    }


    @Override
    protected void onResume() {
        super.onResume();
        mAdapter.notifyDataSetChanged();
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapter = new MyAdapter(this, mDevices);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new MyAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                BleDevice bleDevice = mAdapter.getDevice(position);
                if (bleDevice == null) {
                    return;
                }
                if (mBleManager.isScanning()) {
                    //正在扫描，停止扫描
                    mBleManager.scanLeDevice(false);
                } else {
                    if (bleDevice.isConnected()) {
                        mBleManager.disconnect(bleDevice.getBleAddress());
                    } else {
                        mBleManager.connect(bleDevice.getBleAddress());
                    }
                }
                synchronized (mBleManager.getLocker()){
                    mAdapter.notifyDataSetChanged();
                }

            }
        });
    }



    private void setConnectedNum() {
        if (mBleManager != null) {
            Log.e("mConnectedNum", "已连接的数量：" + mBleManager.getConnetedDevices().size() + "");
            for (BleDevice device : mBleManager.getConnetedDevices()) {
                Log.e("device", "设备地址：" + device.getBleAddress());
            }
        }
    }


    private void initBle() {
        try {
            //获取实例
            mBleManager = BleManager.getInstance(this);
            //注册监听
            mBleManager.registerBleListener(bleListener);
            boolean result = false;

            if (mBleManager != null) {
                //绑定蓝牙服务
                result = mBleManager.startService();
                //判断蓝牙是否可用
                if (!mBleManager.isBleEnable()) {
                    //没有打开，就打开
                    mBleManager.turnOnBlueTooth(this);//打开蓝牙
                } else {
                    //已经打开，判断权限，开始扫描
                    Permissions4M
                            .get(this)
                            .requestForce(true)
                            .requestPermissions(Manifest.permission.BLUETOOTH_ADMIN, Manifest.permission.ACCESS_COARSE_LOCATION)
                            .requestCodes(BLUETOOTH_ADMIN_CODE, LOCATION_CODE)
                            .request();


                }
            }

            if (!result) {
                //绑定失败
                Log.e(TAG, "initBle: 服务绑定失败");
                //再次绑定
                if (mBleManager == null) {
                    mBleManager.startService();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @PermissionsGranted({BLUETOOTH_ADMIN_CODE, LOCATION_CODE})
    public void granted(int code) {
        switch (code) {
            case BLUETOOTH_ADMIN_CODE://蓝牙权限
                Toast.makeText(this, "蓝牙权限申请成功", Toast.LENGTH_SHORT).show();
                //开始蓝牙扫描
                mBleManager.scanLeDevice(true);
                break;
            case LOCATION_CODE://定位权限
                Toast.makeText(this, "定位权限申请成功", Toast.LENGTH_SHORT).show();
                break;
        }
    }


    @PermissionsDenied({BLUETOOTH_ADMIN_CODE, LOCATION_CODE})
    public void denied(int code) {
        switch (code) {
            case BLUETOOTH_ADMIN_CODE://蓝牙权限
                Toast.makeText(this, "蓝牙权限拒绝", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case LOCATION_CODE://定位权限
                Toast.makeText(this, "定位权限申请拒绝", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[]
            grantResults) {
        Permissions4M.onRequestPermissionsResult(BleActivity.this, requestCode, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBleManager == null) {
            mBleManager.unService();
            mBleManager.unRegisterBleListener(bleListener);
        }
    }


}
