package com.sx.popupwndowdemo;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private View lineLocation;
    private OnePopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lineLocation = findViewById(R.id.view_search_doctor_region_divider);
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i + "s");
        }
        popupWindow = new OnePopupWindow(this, list);
        popupWindow.setPopupListener(new OnePopupWindow.PopupListener() {
            @Override
            public void onDismissListener() {

            }

            @Override
            public void onSelectedListener(String id, String name) {
                Toast.makeText(MainActivity.this, "name:" + name, Toast.LENGTH_SHORT).show();
            }
        });

        calc();

    }

    private void calc() {
        Map<String, String> map = new HashMap<>();
        map.put("timestamp", "1505382691413");
        map.put("type", "2");
        map.put("userId", "123");
        try {
            Log.e("MD5Utils", SignUtil.sign("a8965388440f95be0e9c79dc610909b9", map));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void showPop(View view) {
        if (popupWindow != null) {
            if (popupWindow.isShowing()) {
                popupWindow.dismiss();
            } else {
                showWindow(popupWindow);
            }

        }

    }


    public void showWindow(PopupWindow departmentPopupWindow) {
        if (Build.VERSION.SDK_INT >= 24) { // Android 7.x中,PopupWindow高度为match_parent时,会出现兼容性问题,需要处理兼容性
            int[] location = new int[2]; // 记录anchor在屏幕中的位置
            lineLocation.getLocationOnScreen(location);
            int offsetY = location[1] + lineLocation.getHeight();
            // 故而需要在 Android 7.1上再做特殊处理
            if (Build.VERSION.SDK_INT == 25) { // Android 7.1中，PopupWindow高度为 match_parent 时，会占据整个屏幕
                // 故而需要在 Android 7.1上再做特殊处理
                int screenHeight = Util.getScreenHeight(this); // 获取屏幕高度
                departmentPopupWindow.setHeight(screenHeight - offsetY); // 重新设置 PopupWindow 的高度
            }
            departmentPopupWindow.showAtLocation(lineLocation, Gravity.NO_GRAVITY, 0, offsetY);
        } else {
            departmentPopupWindow.showAsDropDown(lineLocation);
        }
    }
}
