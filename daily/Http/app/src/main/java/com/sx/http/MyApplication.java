package com.sx.http;

import android.app.Application;

import com.sx.http_lib.base.HttpUtils;
import com.sx.http_lib.engine.OkHttpEngine;

/**
 * @author Administrator
 * @Date 2017/11/10 0010 上午 11:20
 * @Description
 */

public class MyApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        HttpUtils.init(new OkHttpEngine());
    }
}
