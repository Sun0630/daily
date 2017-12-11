package com.sx.searchlayout;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @author Administrator
 * @Date 2017/12/11 0011 上午 9:58
 * @Description 自定义EditText控件，多了左侧图片和右侧图片的设置。
 */

public class EditTextClear extends android.support.v7.widget.AppCompatEditText {


    /**
     * 定义左侧搜索图片和右侧清除图片
     */
    private Drawable clearDrawable, searchDrawable;

    public EditTextClear(Context context) {
        super(context);
        init();
        // 初始化该组件时，对EditText_Clear进行初始化 ->>步骤2
    }

    public EditTextClear(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EditTextClear(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化图标资源
     */
    private void init() {
        clearDrawable = getResources().getDrawable(R.drawable.clear);
        searchDrawable = getResources().getDrawable(R.drawable.search);
        //搜索图标设置到左边，不想在哪个位置显示就置为null。
        setCompoundDrawablesWithIntrinsicBounds(searchDrawable, null, null, null);
    }

    /**
     * 当输入框内容发生变化的时候调用，用来判断是否显示右侧删除图标
     *
     * @param text
     * @param start
     * @param lengthBefore
     * @param lengthAfter
     */
    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        //当获得焦点并且有内容的时候清除按钮显示出来
        setClearIconVisiable(hasFocus() && text.length() > 0);
    }

    /**
     * 当输入框焦点发生变化的时候调用
     *
     * @param focused
     * @param direction
     * @param previouslyFocusedRect
     */
    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        setClearIconVisiable(focused && length() > 0);
    }


    /**
     * 设置右侧清除图标是否显示
     *
     * @param visiable
     */
    private void setClearIconVisiable(boolean visiable) {
        setCompoundDrawablesWithIntrinsicBounds(searchDrawable, null, visiable ? clearDrawable : null, null);
    }


    /**
     * 点击删除图标清空内容
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            //当手指抬起的时候的位置位于删除图标的区域的时候，清空搜索框内容
            case MotionEvent.ACTION_UP:
                Drawable drawable = clearDrawable;
                if (drawable != null && event.getX() <= (getWidth() - getPaddingRight())
                        && event.getX() >= (getWidth() - getPaddingRight() - drawable.getBounds().width())) {
                    setText("");
                }
                break;
        }
        return super.onTouchEvent(event);
    }
}
