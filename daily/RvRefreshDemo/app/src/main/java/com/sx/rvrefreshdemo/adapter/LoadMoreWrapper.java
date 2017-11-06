package com.sx.rvrefreshdemo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sx.rvrefreshdemo.R;

/**
 * @author Administrator
 * @Date 2017/11/6 0006 上午 9:56
 * @Description
 */

public class LoadMoreWrapper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private RecyclerView.Adapter adapter;

    /*两种条目布局*/
    /**
     * 普通条目
     */
    private final int TYPE_ITEM = 1;

    /**
     * footer layout
     */
    private final int TYPE_FOOTER = 2;

    /*三种加载状态*/

    /**
     * 正在加载
     */
    public final int LOADING = 1;

    /**
     * 加载完成
     */
    public final int LOADING_COMPLETE = 2;

    /**
     * 加载到底部
     */
    public final int LOADINT_END = 3;

    /**
     * 默认是加载完成
     */
    public int loadState = LOADING_COMPLETE;

    public LoadMoreWrapper(RecyclerView.Adapter adapter) {

        this.adapter = adapter;
    }

    @Override
    public int getItemViewType(int position) {
        //最后一个Item设置成FooterView
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_load_more, parent, false);
            return new FooterViewHolder(view);
        } else {

            return adapter.onCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FooterViewHolder) {
            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
            switch (loadState) {
                //正在加载
                case LOADING:
                    ((FooterViewHolder) holder).tvLoading.setVisibility(View.VISIBLE);
                    ((FooterViewHolder) holder).pbLoading.setVisibility(View.VISIBLE);
                    ((FooterViewHolder) holder).llEnd.setVisibility(View.GONE);
                    break;
                //加载完毕
                case LOADING_COMPLETE:
                    ((FooterViewHolder) holder).tvLoading.setVisibility(View.INVISIBLE);
                    ((FooterViewHolder) holder).pbLoading.setVisibility(View.INVISIBLE);
                    ((FooterViewHolder) holder).llEnd.setVisibility(View.GONE);
                    break;
                //加载到底
                case LOADINT_END:
                    ((FooterViewHolder) holder).tvLoading.setVisibility(View.GONE);
                    ((FooterViewHolder) holder).pbLoading.setVisibility(View.GONE);
                    ((FooterViewHolder) holder).llEnd.setVisibility(View.VISIBLE);
                    break;
                default:
                    break;
            }
        } else {
            adapter.onBindViewHolder(holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return adapter.getItemCount() + 1;
    }

    /**
     * 脚布局
     */
    private class FooterViewHolder extends RecyclerView.ViewHolder {

        ProgressBar pbLoading;
        TextView tvLoading;
        LinearLayout llEnd;

        public FooterViewHolder(View itemView) {
            super(itemView);
            pbLoading = itemView.findViewById(R.id.progress_loading);
            tvLoading = itemView.findViewById(R.id.tv_loading);
            llEnd = itemView.findViewById(R.id.ll_end);
        }
    }


    /**
     * 设置加载状态
     *
     * @param loadState
     */
    public void setLoadState(int loadState) {
        this.loadState = loadState;
        notifyDataSetChanged();
    }
}
