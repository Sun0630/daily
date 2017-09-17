package com.sx.sweepdeletedemo.custom;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sx.sweepdeletedemo.R;

/**
 * @Author sunxin
 * @Date 2017/9/16 16:37
 * @Description
 */

public class MyViewHolder extends RecyclerView.ViewHolder {

    public LinearLayout mLayout;
    public TextView mContent;
    public TextView mDelete;

    public MyViewHolder(View itemView) {
        super(itemView);
        mContent = (TextView) itemView.findViewById(R.id.item_content);
        mDelete = (TextView) itemView.findViewById(R.id.item_delete);
        mLayout = (LinearLayout) itemView.findViewById(R.id.item_layout);
    }
}
