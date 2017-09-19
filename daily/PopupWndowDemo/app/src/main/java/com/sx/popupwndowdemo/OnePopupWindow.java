package com.sx.popupwndowdemo;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.util.List;

/**
 * @Author Administrator
 * @Date 2017/9/18 0018 下午 2:57
 * @Description
 */

public class OnePopupWindow extends PopupWindow {

    private Context context;
    private final ListView listView;

    public OnePopupWindow(final Context context, List<String> list) {
        super(context);
        this.context = context;
        //拿到一个Inflater
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View menuView = inflater.inflate(R.layout.pop_layout, null);
        listView = menuView.findViewById(R.id.pop_listview_left);
//        menuView.removeView(listView);
        //设置内容
        this.setContentView(menuView);

        //设置弹出窗体的宽高
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);

        //设置焦点
        this.setFocusable(true);

        //设置弹出动画效果
        this.setAnimationStyle(R.style.PopAnim);

        //为窗体设置一个背景
        ColorDrawable colorDrawable = new ColorDrawable(0xb000000);

        this.setBackgroundDrawable(colorDrawable);

        //设置消失监听
        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                if (popupListener != null) {
                    popupListener.onDismissListener();
                }
            }
        });


        //处理Listview,当触摸位置在窗口之外时关闭popupwindow
        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                //拿到高
                int height = menuView.findViewById(R.id.ll_pop_layout).getBottom();
                //拿到触摸点的位置
                int y = (int) event.getY();

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y > height) {
                        dismiss();
                    }
                }

                return true;
            }
        });


        //为Listview设置数据
        MyAdapter adapter = new MyAdapter(context, list);
        listView.setAdapter(adapter);


        //设置Listview的点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> view, View view1, int position, long l) {
                Toast.makeText(context, "点击了" + position, Toast.LENGTH_SHORT).show();
            }
        });

    }


    private PopupListener popupListener;

    public void setPopupListener(PopupListener popupListener) {
        this.popupListener = popupListener;
    }

    public interface PopupListener {
        void onDismissListener();

        void onSelectedListener(String id, String name);
    }

}
