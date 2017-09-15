package com.sx.permissionsdemo;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.yanzhenjie.alertdialog.AlertDialog;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private static final int PHOTO_REQUEST_CAMERA = 1;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 3;// 结果

    private static final String CROP_HEAD_PHOTO_TEMP = "head_photo_temp.jpg";

    private File photoTempFile = new File(Environment.getExternalStorageDirectory() + File.separator + "aaa"
            + File.separator + "health", CROP_HEAD_PHOTO_TEMP);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void camera(View view) {
        AndPermission
                .with(this)
                .permission(Manifest.permission.CAMERA)
                .callback(listener)
                .rationale(rationaleListern)
                .start();

    }

    private PermissionListener listener = new PermissionListener() {
        //授权成功回调
        @Override
        public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
            Toast.makeText(MainActivity.this, "授权成功", Toast.LENGTH_SHORT).show();
            //授权成功
            openCamera();
            System.out.println("授权成功");
        }

        //授权失败回调
        @Override
        public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
//            Toast.makeText(MainActivity.this, "授权失败", Toast.LENGTH_SHORT).show();

            // 是否有不再提示并拒绝的权限。
            if (AndPermission.hasAlwaysDeniedPermission(MainActivity.this, deniedPermissions)) {
                // 第一种：用AndPermission默认的提示语。
//                AndPermission.defaultSettingDialog(MainActivity.this, 400).show();
                String name = deniedPermissions.get(0);
                System.out.println("name-->" + name);
                // 第二种：用自定义的提示语。
                AndPermission.defaultSettingDialog(MainActivity.this, 400)
                        .setTitle("权限申请失败")
                        .setMessage("您拒绝了我们必要的一些权限，已经没法愉快的玩耍了，请在设置中授权！")
                        .setPositiveButton("好，去设置")
                        .show();

//                 第三种：自定义dialog样式。
//                SettingService settingService = AndPermission.defineSettingDialog(MainActivity.this, 400);
//                // 你的dialog点击了确定调用：
//                settingService.execute();
//                // 你的dialog点击了取消调用：
//                settingService.cancel();
            }
        }
    };

    //打开相机
    private void openCamera() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoTempFile));
        // intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);// 三星的相机是否会出现问题
        startActivityForResult(intent, PHOTO_REQUEST_CAMERA);
    }


    //二次申请回调
    private RationaleListener rationaleListern = new RationaleListener() {
        @Override
        public void showRequestPermissionRationale(int requestCode, final Rationale rationale) {
//            AndPermission.rationaleDialog(MainActivity.this,rationale).show();
            AlertDialog.newBuilder(MainActivity.this)
                    .setTitle("友好提醒")
                    .setMessage("你已拒权限，你看着办！")
                    .setPositiveButton("好，给你", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface anInterface, int i) {
                            rationale.resume();
                        }
                    })
                    .setNegativeButton("不，再见！", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface anInterface, int i) {
                            rationale.cancel();
                        }
                    }).show();

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("回调了-----");
//        if (AndPermission.hasPermission(MainActivity.this,Manifest.permission.CAMERA)){
//            openCamera();
//        }else {
//            AndPermission.defaultSettingDialog(this, 400).show();
//        }

    }
}
