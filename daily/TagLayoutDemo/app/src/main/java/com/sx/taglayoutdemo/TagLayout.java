package com.sx.taglayoutdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author Administrator
 * @Date 2017/12/18 0018 下午 4:37
 * @Description
 */

public class TagLayout extends ViewGroup {

    public TagLayout(Context context) {
        super(context);
    }

    public TagLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TagLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();

        //获取到宽高
        int width = MeasureSpec.getSize(widthMeasureSpec);

        //计算高度
        int height = getPaddingBottom() + getPaddingTop();

        //一行的宽度
        int lineWidth = getPaddingLeft();

        //for循环测量子View
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            //这句代码执行之后就可以获取子View的宽高
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);

            //根据子View执行和指定自己的布局  margine
            if (lineWidth + childView.getMeasuredWidth() > width) {
                //换行,高度需要累加
                height += childView.getMeasuredHeight();
                //行宽置为0
                lineWidth = 0;
            } else {
                lineWidth += childView.getMeasuredWidth();
            }
        }

        //指定自己的宽高
        setMeasuredDimension(width,height);

    }

    /**
     * 摆放子View
     *
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}
