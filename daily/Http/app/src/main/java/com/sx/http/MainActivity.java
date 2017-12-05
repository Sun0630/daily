package com.sx.http;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.sx.http_lib.base.HttpUtils;
import com.sx.http_lib.callback.HttpCallBack;

/**
 * @author Sunxin
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    String url = "https://api.douban.com/v2/book/1220562";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HttpUtils
                .with(this)
                .get()
                .url(url)
                .excute(new HttpCallBack<GankEntity>() {
                    @Override
                    public void onError(Exception e) {
                        Log.e(TAG, "onError: ");
                    }

                    @Override
                    public void success(GankEntity reslut) {
                        Log.e(TAG, "success: "+reslut.getResults().get(0).getUrl() );
                    }
                });

    }
}
