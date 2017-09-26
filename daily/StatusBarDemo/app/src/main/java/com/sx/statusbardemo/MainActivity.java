package com.sx.statusbardemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        StatusBarUtil.setStatusBarColor(this, Color.WHITE);
//        StatusBarUtil.FlymeSetStatusBarLightMode(getWindow(),true);
        StatusBarUtil.StatusBarLightMode(this);
    }

    public void jump(View view) {
        startActivity(new Intent(this,Main2Activity.class));
    }
}
