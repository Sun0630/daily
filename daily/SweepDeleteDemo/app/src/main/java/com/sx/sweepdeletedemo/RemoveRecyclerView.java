package com.sx.sweepdeletedemo;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * @Author Sunxin
 * @Date 2017/9/15 0015 下午 3:53
 * @Description 自定义可侧滑删除的RecyclerView
 */
public class RemoveRecyclerView extends RecyclerView {


    //删除按钮的四种状态  0:关闭   1：将要关闭  2：将要打开  3：打开
    public int mDeleteBtnStatus;

    public RemoveRecyclerView(Context context) {
        this(context, null);
    }

    public RemoveRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RemoveRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    public boolean onTouchEvent(MotionEvent e) {

        //拿到当前触摸点的位置
        int x = (int) e.getX();
        int y = (int) e.getY();

        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //打开状态
                if (mDeleteBtnStatus == 0) {
                    //当前点所在的顶层View
                    View view = findChildViewUnder(x, y);
                    if (view == null) {
                        return false;
                    }


                }
                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:

                break;
        }


        return super.onTouchEvent(e);
    }
}
