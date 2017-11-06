package com.sx.rvrefreshdemo.listener;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * @author sunxin
 * @Date 2017/11/6 0006 下午 12:57
 * @Description recyclerView滑动监听
 */

public abstract class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {

    /**
     * 用来标记是否是向上滑动
     */
    private boolean isSlidingUp = false;

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        //当状态是静止的时候
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            //获取最后一条可见的完整条目
            int completelyVisibleItemPosition = layoutManager.findLastCompletelyVisibleItemPosition();
            int itemCount = layoutManager.getItemCount();
            if (completelyVisibleItemPosition == (itemCount - 1) && isSlidingUp) {
                onLoadMore();
            }
        }
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        isSlidingUp = dy > 0;
    }

    /**
     * 加载更多
     */
    public abstract void onLoadMore();
}
