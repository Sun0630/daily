package com.sx.taglayoutdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * @author sunxin
 */
public class MainActivity extends AppCompatActivity {

    private TagLayout mTagLayout;
    private ArrayList<String> mItems;
    private TouchView mTouchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTagLayout = findViewById(R.id.tag_layout);

        mTouchView = findViewById(R.id.touch_view);

//        mTouchView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                Log.e("TAG", "onTouch: " + event.getAction());
//                return false;
//            }
//        });

        mTouchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("TAG", "onClick: ");
            }
        });


        mItems = new ArrayList<>();

        mItems.add("Android艺术开发探索");
        mItems.add("第一行代码");
        mItems.add("Http详解");
        mItems.add("Android群英传");
        mItems.add("Game of Thrones");
        mItems.add("The Walking Dead");
        mItems.add("Shameless");
        mItems.add("iOS");
        mItems.add("Cools");
        mItems.add("Hank");

        mTagLayout.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return mItems.size();
            }

            @Override
            public View getView(int position, ViewGroup parent) {
                TextView tagView = (TextView) LayoutInflater.from(MainActivity.this).inflate(R.layout.layout, parent, false);
                tagView.setText(mItems.get(position));
                return tagView;
            }
        });
    }
}