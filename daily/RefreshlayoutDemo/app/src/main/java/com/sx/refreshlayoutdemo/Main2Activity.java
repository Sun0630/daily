package com.sx.refreshlayoutdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

public class Main2Activity extends AppCompatActivity {

    private SmartRefreshLayout smartRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        smartRefreshLayout = (SmartRefreshLayout) findViewById(R.id.smart_layout);
        smartRefreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                Toast.makeText(Main2Activity.this, "onLoadmore", Toast.LENGTH_SHORT).show();
                refreshlayout.finishLoadmore(2000);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                Toast.makeText(Main2Activity.this, "onRefresh", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void stop(View view) {
        smartRefreshLayout.finishRefresh();
    }
}
