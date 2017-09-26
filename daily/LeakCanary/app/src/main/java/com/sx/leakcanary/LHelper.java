package com.sx.leakcanary;

import android.content.Context;
import android.widget.TextView;

/**
 * @Author Administrator
 * @Date 2017/9/19 0019 下午 2:15
 * @Description
 */

public class LHelper {
    private static LHelper instance = null;
    private Context context;
    private TextView textView;


    private LHelper() {

    }

    public LHelper(Context context) {
        this.context = context;
    }

    public static LHelper getInstance(Context context) {
        if (instance == null) {
            return new LHelper(context);
        }
        return instance;
    }

    public void setRetainedTextView(TextView tv) {
        this.textView = tv;
        textView.setText(context.getString(android.R.string.ok));
    }



}
