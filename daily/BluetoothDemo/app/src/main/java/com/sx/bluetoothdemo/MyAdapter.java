package com.sx.bluetoothdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import cn.com.heaton.blelibrary.BleVO.BleDevice;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements View.OnClickListener {


    private Context mContext;
    private ArrayList<BleDevice> mDevices;

    public MyAdapter(Context context, ArrayList<BleDevice> devices) {
        mContext = context;

        mDevices = devices;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.layout_ble, null);
        ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BleDevice device = mDevices.get(position);
        holder.setData(device);
        //将点击事件的position存setTag
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mDevices.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }

    /**
     * 获取一个蓝牙信息
     *
     * @param position
     * @return
     */
    public BleDevice getDevice(int position) {
        return mDevices.get(position);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_name;
        private TextView tv_macAddress;
        private TextView tv_link;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_link = (TextView) itemView.findViewById(R.id.tv_ble_link);
            tv_name = (TextView) itemView.findViewById(R.id.tv_ble_name);
            tv_macAddress = (TextView) itemView.findViewById(R.id.tv_ble_mac);
        }

        public void setData(BleDevice data) {
            if (data.getmBleName() == null) {
                tv_name.setText("佚名");
            } else {
                tv_name.setText(data.getmBleName());
            }

            tv_macAddress.setText(data.getBleAddress());

            if (data.isConnected()) {
                tv_link.setText("已连接");
            } else if (data.isConnectting()) {
                tv_link.setText("正在连接中...");
            } else {
                tv_link.setText("未连接");
            }
        }
    }

    public onItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(onItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface onItemClickListener {
        void onItemClick(View view, int position);
    }

}