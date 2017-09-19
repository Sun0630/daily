package com.sx.customdailog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;

/**
 * @Author sunxin
 * @Date 2017/9/19 0019 下午 2:27
 * @Description 自定义Dialog
 */

public class RemarkDialog extends Dialog {


    public RemarkDialog(@NonNull Context context) {
        super(context);
    }

    public RemarkDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    protected RemarkDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remark_dialog);
    }
}
