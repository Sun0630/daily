package com.sx.sweepdeletedemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * @Author Administrator
 * @Date 2017/9/15 0015 下午 4:38
 * @Description
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.RViewHolder> {

    private Context mContext;
    private ArrayList<String> datas;

    public MyAdapter(Context context, ArrayList<String> datas) {
        mContext = context;
        this.datas = datas;
    }

    @Override
    public RViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_layout,parent,false);
        RViewHolder holder = new RViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RViewHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class RViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTextView;
        private Button mDelBtn;

        public RViewHolder(View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.tv_text);
            mDelBtn = itemView.findViewById(R.id.btnDelete);
        }

        public void setData(int position) {
            mTextView.setText(datas.get(position));
            mDelBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mDeleteListener != null) {
                Log.e("TAG", "getAdapterPosition: " + getAdapterPosition());
                mDeleteListener.onDelete(this.getAdapterPosition());
            }
        }
    }


    public OnDeleteListener mDeleteListener;

    public void setDeleteListener(OnDeleteListener deleteListener) {
        mDeleteListener = deleteListener;
    }

    public interface OnDeleteListener {
        void onDelete(int position);
    }


}
