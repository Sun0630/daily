package com.sx.searchlayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * @author Administrator
 * @Date 2017/12/11 0011 上午 10:27
 * @Description
 */

public class SearchActivity extends AppCompatActivity {

    private static final String TAG = "SearchActivity";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        SearchView searchView = findViewById(R.id.search_view);

       searchView.setOnClickSearch(new ICallBack() {
           @Override
           public void ActionSearch(String str) {
               Log.e(TAG, "ActionSearch: "+str );
           }
       });

       searchView.setOnClickBack(new BackCallBack() {
           @Override
           public void backAction() {
               finish();
           }
       });

    }
}
