package com.sx.sweepdeletedemo.custom;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.sx.sweepdeletedemo.R;

import java.util.ArrayList;

/**
 * @Author sunxin
 * @Date 2017/9/16 21:10
 * @Description
 */

public class MyRemoveAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private Context mContext;
    private ArrayList<String> mList;
    private LayoutInflater mInflater;

    public MyRemoveAdapter(Context context, ArrayList<String> list) {
        mContext = context;
        mList = list;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(mInflater.inflate(R.layout.recycler_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.mContent.setText(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    public void removeItem(int position){
        mList.remove(position);
        notifyItemChanged(position);
    }
}
