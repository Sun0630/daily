package com.sx.leakcanary;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * @Author Administrator
 * @Date 2017/9/19 0019 下午 2:08
 * @Description
 */

public class LApplication extends Application {

    private static RefWatcher refWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {

            return;
        }
        refWatcher = LeakCanary.install(this);
    }

    public static RefWatcher getRefWatcher() {
        return refWatcher;
    }
}
