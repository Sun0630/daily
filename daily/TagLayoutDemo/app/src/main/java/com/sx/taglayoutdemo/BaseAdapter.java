package com.sx.taglayoutdemo;

import android.view.View;
import android.view.ViewGroup;

/**
 * @Author Administrator
 * @Date 2018/1/17 0017 下午 2:43
 * @Description Adapter设计模式
 */

public abstract class BaseAdapter {


    /**
     * 获取个数
     * @return
     */
    public abstract int getCount();

    /**
     * 获取View
     * @param position
     * @param parent
     * @return
     */
    public abstract View getView(int position, ViewGroup parent);

    /**
     * 观察者模式通知及时更新
     */
}
