package com.sx.sweepdeletedemo;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @Author Sunxin
 * @Date 2017/9/15 0015 下午 3:53
 * @Description 自定义可侧滑删除的RecyclerView
 */
public class RemoveRecyclerView extends RecyclerView {


    public RemoveRecyclerView(Context context) {
        super(context);
    }

    public RemoveRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RemoveRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    public boolean onTouchEvent(MotionEvent e) {

        return super.onTouchEvent(e);
    }
}
