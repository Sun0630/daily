package com.sx.refreshlayoutdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import in.srain.cube.views.ptr.PtrFrameLayout;

public class MainActivity extends AppCompatActivity {

    private PtrFrameLayout ptrFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        ptrFrame = (PtrFrameLayout) findViewById(R.id.ptrFrame);
        KYunFrameLayoutHeader header = new KYunFrameLayoutHeader(this);
        ptrFrame.setHeaderView(header);
        ptrFrame.addPtrUIHandler(header);
        ptrFrame.setLoadingMinTime(2000);
        ptrFrame.setDurationToCloseHeader(2000);
    }


    public void stop(View view) {
        ptrFrame.refreshComplete();
        startActivity(new Intent(this, Main2Activity.class));
    }

}
