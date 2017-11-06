package com.sx.rvrefreshdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.sx.rvrefreshdemo.adapter.LoadMoreWrapper;
import com.sx.rvrefreshdemo.adapter.LoadMoreWrapperAdapter;
import com.sx.rvrefreshdemo.listener.EndlessRecyclerOnScrollListener;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Administrator
 */
public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recycler_view;
    private SwipeRefreshLayout swipe_refresh_layout;

    private ArrayList<String> dataList = new ArrayList<>();
    private LoadMoreWrapper moreWrapper;
    private LoadMoreWrapperAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        swipe_refresh_layout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);

        //使用Toolbar替代ActionBar
        setSupportActionBar(toolbar);

        swipe_refresh_layout.setColorSchemeColors(Color.parseColor("#4DB6AC"));

        //获取数据
        getData();

        recycler_view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new LoadMoreWrapperAdapter(dataList);
        //封装一层
        moreWrapper = new LoadMoreWrapper(adapter);
        recycler_view.setAdapter(moreWrapper);

        //下拉刷新
        swipe_refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //刷新数据
                dataList.clear();
                getData();

                //yanshi 1s 关闭下拉刷新
                swipe_refresh_layout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (swipe_refresh_layout != null && swipe_refresh_layout.isRefreshing()) {
                            swipe_refresh_layout.setRefreshing(false);
                        }
                    }
                }, 1000);
            }
        });


        //上拉加载更多
        recycler_view.setOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                moreWrapper.setLoadState(moreWrapper.LOADING);
                if (dataList.size() <= 52){
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                           runOnUiThread(new Runnable() {
                               @Override
                               public void run() {
                                   getData();
                                   moreWrapper.setLoadState(moreWrapper.LOADING_COMPLETE);
                               }
                           });
                        }
                    },1000);
                }else {
                    //数据加载完毕到底
                    moreWrapper.setLoadState(moreWrapper.LOADINT_END);
                }
            }
        });


    }

    private void getData() {
        char letter = 'A';
        for (int i = 0; i < 26; i++) {
            dataList.add(String.valueOf(letter));
            letter++;
        }
    }
}
