package com.sx.permissionsdemo;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.joker.annotation.PermissionsCustomRationale;
import com.joker.annotation.PermissionsDenied;
import com.joker.annotation.PermissionsGranted;
import com.joker.annotation.PermissionsNonRationale;
import com.joker.api.Permissions4M;

import java.io.File;
import java.util.List;

public class Permission4MActivity extends AppCompatActivity {
    private File photoTempFile = new File(Environment.getExternalStorageDirectory() + File.separator + "aaa"
            + File.separator + "health", CROP_HEAD_PHOTO_TEMP);

    private static final int PHOTO_REQUEST_CAMERA = 1;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 3;// 结果

    private static final String CROP_HEAD_PHOTO_TEMP = "head_photo_temp.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission4_m);
    }

    public void camera(View view) {
        Permissions4M
                .get(this)
                .requestForce(true)
                .requestPermissions(Manifest.permission.CAMERA)
                .requestCodes(PHOTO_REQUEST_CAMERA)
                .requestPageType(Permissions4M.PageType.MANAGER_PAGE)
                .request();
    }


    //申请成功回调
    @PermissionsGranted(PHOTO_REQUEST_CAMERA)
    public void cameraGranted() {
        Toast.makeText(this, "相机权限申请成功", Toast.LENGTH_SHORT).show();
        openCamera();
    }

    //权限拒绝回调
    @PermissionsDenied(PHOTO_REQUEST_CAMERA)
    public void cameraDenied() {
        Toast.makeText(this, "权限申请失败", Toast.LENGTH_SHORT).show();
    }

    //二次授权时回调
    @PermissionsCustomRationale(PHOTO_REQUEST_CAMERA)
    public void cameraCustomRational() {
        new AlertDialog.Builder(this)
                .setMessage("读取地理位置权限申请：\n我们需要您开启读取地理位置权限(in activity with annotation)")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Permissions4M.get(Permission4MActivity.this)
                                // 注意添加 .requestOnRationale()
                                .requestOnRationale()
                                .requestPermissions(Manifest.permission.CAMERA)
                                .requestCodes(PHOTO_REQUEST_CAMERA)
                                .request();
                    }
                })
                .show();
    }


    //用户拒绝权限并且不再提示时回调
    @PermissionsNonRationale(PHOTO_REQUEST_CAMERA)
    public void cameraNonRational(Intent intent) {

        System.out.println("INTENT-----> " + intent.toString());
        final Intent finalIntent = intent;

        new AlertDialog.Builder(this)
                .setMessage("读取相机拍照权限申请：\n我们需要您开启读取相机拍照权限(in activity with annotation)")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

//                        startActivity(finalIntent);
                        settingPermissionActivity(Permission4MActivity.this);
                    }
                })
                .show();

    }

    public static void settingPermissionActivity(Activity activity) {
        //判断是否为小米系统
        if (TextUtils.equals(BrandUtils.getSystemInfo().getOs(), BrandUtils.SYS_MIUI)) {
            Intent miuiIntent = new Intent("miui.intent.action.APP_PERM_EDITOR");
            miuiIntent.putExtra("extra_pkgname", activity.getPackageName());
            //检测是否有能接受该Intent的Activity存在
            List<ResolveInfo> resolveInfos = activity.getPackageManager().queryIntentActivities(miuiIntent, PackageManager.MATCH_DEFAULT_ONLY);
            if (resolveInfos.size() > 0) {
                activity.startActivityForResult(miuiIntent, PHOTO_REQUEST_CAMERA);
                return;
            }
        }
        //如果不是小米系统 则打开Android系统的应用设置页
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
        intent.setData(uri);
        activity.startActivityForResult(intent, PHOTO_REQUEST_CAMERA);
    }


    private void openCamera() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoTempFile));
        // intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);// 三星的相机是否会出现问题
        startActivityForResult(intent, PHOTO_REQUEST_CAMERA);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[]
            grantResults) {
        Permissions4M.onRequestPermissionsResult(Permission4MActivity.this, requestCode, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
