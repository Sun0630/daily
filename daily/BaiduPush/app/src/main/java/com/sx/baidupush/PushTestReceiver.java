package com.sx.baidupush;

import android.content.Context;
import android.util.Log;

import com.baidu.android.pushservice.PushMessageReceiver;

import java.util.List;

/**
 * @Author Administrator
 * @Date 2017/9/29 0029 下午 2:53
 * @Description
 */

public class PushTestReceiver extends PushMessageReceiver {
    public static final String TAG = "PushTestReceiver";

    @Override
    public void onBind(Context context, int i, String s, String s1, String s2, String s3) {
        Log.e(TAG, "onBind: " + i + "--" + s + "--" + s1 + "--" + s2 + "--" + s3);
    }

    @Override
    public void onUnbind(Context context, int i, String s) {
        Log.e(TAG, "onUnbind: ");
    }

    @Override
    public void onSetTags(Context context, int i, List<String> list, List<String> list1, String s) {
        Log.e(TAG, "onSetTags: ");
    }

    @Override
    public void onDelTags(Context context, int i, List<String> list, List<String> list1, String s) {
        Log.e(TAG, "onDelTags: ");
    }

    @Override
    public void onListTags(Context context, int i, List<String> list, String s) {
        Log.e(TAG, "onListTags: ");
    }

    @Override
    public void onMessage(Context context, String s, String s1) {
        Log.e(TAG, "onMessage: " + s + "--" + s1);
    }

    @Override
    public void onNotificationClicked(Context context, String s, String s1, String s2) {
        Log.e(TAG, "onNotificationClicked: ");
    }

    @Override
    public void onNotificationArrived(Context context, String s, String s1, String s2) {
        Log.e(TAG, "onNotificationArrived: " + s + "--" + s1 + "--" + s2);
    }
}
