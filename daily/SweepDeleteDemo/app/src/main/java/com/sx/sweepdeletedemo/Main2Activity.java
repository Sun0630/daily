package com.sx.sweepdeletedemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.sx.sweepdeletedemo.custom.MyRemoveAdapter;
import com.sx.sweepdeletedemo.custom.RemoveRecyclerView;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {

    private RemoveRecyclerView mRemoveRecyclerView;
    private ArrayList<String> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mRemoveRecyclerView = (RemoveRecyclerView) findViewById(R.id.remove_recycle);

        mList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            mList.add(i + "s");
        }

        mRemoveRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        final MyRemoveAdapter adapter = new MyRemoveAdapter(this, mList);
        mRemoveRecyclerView.setAdapter(adapter);
        mRemoveRecyclerView.setOnItemClickListener(new RemoveRecyclerView.onItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(Main2Activity.this, "点击了条目" + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDeleteClick(int position) {
                adapter.removeItem(position);
            }
        });
    }
}
