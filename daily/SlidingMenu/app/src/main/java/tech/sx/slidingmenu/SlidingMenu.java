package tech.sx.slidingmenu;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;

/**
 * @Author Administrator
 * @Date 2018/1/24 0024 下午 4:04
 * @Description
 */

public class SlidingMenu extends HorizontalScrollView {
    public static final String TAG = "SlidingMenu";
    private int mMenuWidth;
    private View mContentView;
    private View mMenuView;
    private GestureDetector mGestureDetector;
    private boolean mMenuIsOpen = false;
    /**
     * 是否拦截了事件
     */
    private boolean mIsIntercepted = false;

    public SlidingMenu(Context context) {
        this(context, null);
    }

    public SlidingMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SlidingMenu);
        float rightMargin = typedArray.getDimension(R.styleable.SlidingMenu_menuRightMargin, dip2px(context, 50));
        //菜单的宽度 = 屏幕的宽度-右边一小部分距离
        mMenuWidth = (int) (getScreenWidth(context) - rightMargin);
        typedArray.recycle();

        mGestureDetector = new GestureDetector(context, mSimpleOnGestureListener);
    }

    private GestureDetector.SimpleOnGestureListener mSimpleOnGestureListener = new GestureDetector.SimpleOnGestureListener() {

        /**
         * 快速滑动时回调
         * @param e1
         * @param e2
         * @param velocityX
         * @param velocityY
         * @return
         */
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            mIsIntercepted = false;
            //快速往左滑时一个负数，往右滑是一个正数
            Log.e(TAG, "onFling: " + velocityX);

            if (mMenuIsOpen) {
                if (velocityX < 0) {
                    closeMenu();
                    return true;
                }
            } else {
                if (velocityX > 0) {
                    openMenu();
                    return true;
                }
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    };


    /**
     * 布局解析完毕之后回调
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //指定内容页面和菜单页面的宽度
        ViewGroup container = (ViewGroup) getChildAt(0);
        int childCount = container.getChildCount();
        if (childCount != 2) {
            throw new RuntimeException("只允许两个子View！！");
        }

        //菜单
        mMenuView = container.getChildAt(0);
        //通过LayoutParams来指定宽度
        ViewGroup.LayoutParams params = mMenuView.getLayoutParams();
        params.width = mMenuWidth;
        //在7.0以下的手机必须指定
        mMenuView.setLayoutParams(params);


        //内容
        mContentView = container.getChildAt(1);
        ViewGroup.LayoutParams contentParmas = mContentView.getLayoutParams();
        contentParmas.width = getScreenWidth(getContext());
        mContentView.setLayoutParams(contentParmas);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        mIsIntercepted = false;
        //当菜单打开的时候，手指点击右侧部分关闭菜单，并且拦截右侧部分的点击事件
        if (mMenuIsOpen) {
            if (ev.getX() > mMenuWidth) {
                closeMenu();
                mIsIntercepted = true;
                return true;
            }
        }

        return super.onInterceptTouchEvent(ev);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        //初始化进来之后菜单是关闭的
        scrollTo(mMenuWidth, 0);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        if (mIsIntercepted){
            //如果事件被拦截了，下面的代码也不用执行了
            return true;
        }

        if (mGestureDetector.onTouchEvent(ev)) {
            //如果快速滑动执行了，下面的代码就不执行
            return true;
        }

        if (ev.getAction() == MotionEvent.ACTION_UP) {
            //手指抬起的时候判断是应该向左还是向右
            int currentScrollX = getScrollX();
            if (currentScrollX > mMenuWidth / 2) {
                //关闭菜单
                closeMenu();
            } else {
                //打开菜单
                openMenu();
            }

            return true;
        }
        return super.onTouchEvent(ev);
    }


    /**
     * 处理缩放和透明度
     *
     * @param l
     * @param t
     * @param oldl
     * @param oldt
     */
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        //计算一个梯度值
        float scale = 1f * l / mMenuWidth;
        //设置右边的缩放,默认是以中心点缩放
        float rigthScale = 0.8f + 0.2f * scale;
        //设置中心点
        ViewCompat.setPivotX(mContentView, 0);
        ViewCompat.setPivotY(mContentView, mContentView.getMeasuredHeight() / 2);
        ViewCompat.setScaleX(mContentView, rigthScale);
        ViewCompat.setScaleY(mContentView, rigthScale);

        //菜单需要设置透明度变化和缩放动画
        float leftAlpha = 0.5f + (1 - scale) * 0.5f;
        ViewCompat.setAlpha(mMenuView, leftAlpha);

        float leftScale = 0.7f + (1 - scale) * 0.3f;
        ViewCompat.setScaleX(mMenuView, leftScale);
        ViewCompat.setScaleY(mMenuView, leftScale);

        //设置平移效果
        ViewCompat.setTranslationX(mMenuView, 0.25f * l);
    }

    private void openMenu() {
        //滚动到0的位置
        smoothScrollTo(0, 0);
        mMenuIsOpen = true;
    }

    private void closeMenu() {
        //滚动到mMenuWidth的位置
        smoothScrollTo(mMenuWidth, 0);
        mMenuIsOpen = false;
    }


    private int dip2px(Context context, int dp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    /**
     * 获得屏幕高度
     *
     * @param context
     * @return
     */
    private int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }
}
