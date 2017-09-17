package com.sx.sweepdeletedemo.custom;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.sx.sweepdeletedemo.R;

/**
 * @Author Sunxin
 * @Date 2017/9/15 0015 下午 3:53
 * @Description 自定义可侧滑删除的RecyclerView
 */
public class RemoveRecyclerView extends RecyclerView {


    //删除按钮的四种状态  0:关闭   1：将要关闭  2：将要打开  3：打开
    public int mDeleteBtnStatus;
    private Scroller mScroller;
    private VelocityTracker mTracker;
    private LinearLayout mItemLayout;
    private int mPosition;
    private View mDelete;
    private int mMaxWidth;

    private int mLastX, mLastY;//上次的触摸点的坐标
    private boolean isItemMoving;//是否在跟随手指移动
    private boolean isStartScroll;//是否开始自动滑动
    private boolean isDragging;//是否在垂直滑动列表

    public RemoveRecyclerView(Context context) {
        this(context, null);
    }

    public RemoveRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RemoveRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        //弹性滑动
        mScroller = new Scroller(context, new LinearInterpolator());
        //速度追踪
        mTracker = VelocityTracker.obtain();
    }


    @Override
    public boolean onTouchEvent(MotionEvent e) {
        //将事件传递给速度追踪
        mTracker.addMovement(e);
        //拿到当前触摸点的位置
        int x = (int) e.getX();
        int y = (int) e.getY();
        System.out.println("x --> " + x + " y --> " + y);
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //打开状态
                if (mDeleteBtnStatus == 0) {
                    //当前点所在的顶层View
                    View view = findChildViewUnder(x, y);
                    if (view == null) {
                        return false;
                    }
                    MyViewHolder viewHolder = (MyViewHolder) getChildViewHolder(view);
                    //自布局
                    mItemLayout = viewHolder.mLayout;
                    //点击的位置
                    mPosition = viewHolder.getAdapterPosition();
                    //拿到DeleteBtn
                    mDelete = viewHolder.itemView.findViewById(R.id.item_delete);
                    //获取删除按钮的宽度
                    mMaxWidth = mDelete.getWidth();
                    //设置删除按钮的点击事件
                    mDelete.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //设置点击事件
                            if (mOnItemClickListener != null) {
                                mOnItemClickListener.onDeleteClick(mPosition);
                            }
                            //设置弹性关闭
                            mItemLayout.scrollTo(0, 0);
                            //状态改为关闭
                            mDeleteBtnStatus = 0;
                        }
                    });
                } else if (mDeleteBtnStatus == 3) {//打开状态
                    mScroller.startScroll(mItemLayout.getScrollX(), 0, -mMaxWidth,0, 200);
                    invalidate();
                    mDeleteBtnStatus = 0;
                    return false;
                } else {
                    return false;
                }

                break;
            case MotionEvent.ACTION_MOVE:
                int dx = mLastX - x;
                int dy = mLastY - y;
                System.out.println("x --> " + dx + " y --> " + dy);
                int scrollx = mItemLayout.getScrollX();
                //边界检测
                if (Math.abs(dx) > Math.abs(dy)) {//判断横向移动
                    isItemMoving = true;
                    if (scrollx + dx <= 0) {//左边界
                        mItemLayout.scrollTo(0, 0);
                        return true;
                    } else if (scrollx + dx > mMaxWidth) {//右边界
                        mItemLayout.scrollTo(mMaxWidth, 0);
                        return true;
                    }
                    mItemLayout.scrollBy(dx, 0);//item跟随手指移动
                }

                break;
            case MotionEvent.ACTION_UP:
                if (!isItemMoving && !isDragging && mOnItemClickListener != null) {
                    //如果没有滑动，设置点击事件
                    mOnItemClickListener.onItemClick(mItemLayout, mPosition);
                }
                isItemMoving = false;
                //计算手指滑动的速度
                mTracker.computeCurrentVelocity(1000);
                //水平方向上的速度,向左为负
                float xVelocity = mTracker.getXVelocity();
                //竖直方向的速度
                float yVelocity = mTracker.getYVelocity();

                int deltaX = 0;
                int upScrollX = mItemLayout.getScrollX();

                if (Math.abs(xVelocity) > 100 && Math.abs(xVelocity) > Math.abs(yVelocity)) {
                    if (xVelocity <= -100) {
                        // 左滑速度大于100，删除按钮显示
                        deltaX = mMaxWidth - upScrollX;
                        mDeleteBtnStatus = 2;
                    } else if (xVelocity > 100) {
                        //右滑速度大于100，则删除按钮隐藏
                        deltaX = -upScrollX;
                        mDeleteBtnStatus = 1;
                    }
                } else {
                    if (upScrollX > mMaxWidth / 2) {
                        //左滑的距离大于删除按钮的一半宽度，则显示
                        deltaX = mMaxWidth - upScrollX;
                        mDeleteBtnStatus = 2;
                    } else if (upScrollX < mMaxWidth / 2) {//否则隐藏
                        deltaX = -upScrollX;
                        mDeleteBtnStatus = 1;
                    }
                }

                //item 自动滑动到指定位置
                mScroller.startScroll(upScrollX, 0, deltaX, 0, 200);
                isStartScroll = true;
                invalidate();

                mTracker.clear();
                break;
        }

        mLastX = x;
        mLastY = y;

        return super.onTouchEvent(e);
    }

    @Override
    protected void onDetachedFromWindow() {
        mTracker.recycle();
        super.onDetachedFromWindow();
    }

    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        isDragging = state == SCROLL_STATE_DRAGGING;
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            mItemLayout.scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();//重绘
        } else if (isStartScroll) {
            //如果在自动滑动
            isStartScroll = false;
            if (mDeleteBtnStatus == 1) {
                //将要关闭，设置为关闭
                mDeleteBtnStatus = 0;
            }
            if (mDeleteBtnStatus == 2) {
                //将要打开设置为打开
                mDeleteBtnStatus = 3;
            }
        }
    }

    private onItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(onItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface onItemClickListener {
        /**
         * 条目点击事件
         *
         * @param view
         * @param position
         */
        void onItemClick(View view, int position);


        /**
         * 删除按钮点击
         *
         * @param position
         */
        void onDeleteClick(int position);
    }


}
