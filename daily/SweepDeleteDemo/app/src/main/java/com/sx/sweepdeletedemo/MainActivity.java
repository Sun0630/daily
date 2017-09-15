package com.sx.sweepdeletedemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ArrayList<String> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
    }

    private void initData() {
        datas = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            datas.add("数据----------------------------------->" + i);
        }
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        final MyAdapter adapter = new MyAdapter(this, datas);
        mRecyclerView.setAdapter(adapter);

        adapter.setDeleteListener(new MyAdapter.OnDeleteListener() {
            @Override
            public void onDelete(int position) {
                if (position >= 0 && position < datas.size()) {
                    Toast.makeText(MainActivity.this, "删除条目：" + position, Toast.LENGTH_SHORT).show();
                    datas.remove(position);
                    adapter.notifyItemChanged(position);//局部刷新
                }
            }
        });
    }

}



