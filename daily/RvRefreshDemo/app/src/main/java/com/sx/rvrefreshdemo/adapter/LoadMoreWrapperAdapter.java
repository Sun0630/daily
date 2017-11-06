package com.sx.rvrefreshdemo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sx.rvrefreshdemo.R;

import java.util.ArrayList;

/**
 * @author Administrator
 * @Date 2017/11/6 0006 上午 9:59
 * @Description
 */

public class LoadMoreWrapperAdapter extends RecyclerView.Adapter {

    private ArrayList<String> dataList;

    public LoadMoreWrapperAdapter(ArrayList<String> dataList) {

        this.dataList = dataList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_recycler_view, parent, false);
        return new RecyclerViewHoler(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        RecyclerViewHoler viewHoler = (RecyclerViewHoler) holder;
        viewHoler.setData(position);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    private class RecyclerViewHoler extends RecyclerView.ViewHolder{

        TextView tv_item;

        public RecyclerViewHoler(View itemView) {
            super(itemView);
            tv_item = itemView.findViewById(R.id.tv_item);
        }

        public void setData(int position) {
            tv_item.setText(dataList.get(position));
        }
    }



}
