package com.sx.popupwndowdemo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * @Author Administrator
 * @Date 2017/9/18 0018 下午 3:29
 * @Description
 */

public class MyAdapter extends BaseAdapter {

    private Context context;
    private List<String> list;

    public MyAdapter(Context context, List<String> list) {

        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup group) {
        View inflate = View.inflate(context, R.layout.popup_left_listview_item, null);
        TextView textview = inflate.findViewById(R.id.left_item_name);
        String s = list.get(i);
        textview.setText(s);

        return inflate;
    }
}
