package tech.sx.slidingmenu;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
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

    private int mMenuWidth;

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
    }


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
        View menuView = container.getChildAt(0);
        //通过LayoutParams来指定宽度
        ViewGroup.LayoutParams params = menuView.getLayoutParams();
        params.width = mMenuWidth;
        //在7.0以下的手机必须指定
        menuView.setLayoutParams(params);


        //内容
        View contentView = container.getChildAt(1);
        ViewGroup.LayoutParams contentParmas = contentView.getLayoutParams();
        contentParmas.width = getScreenWidth(getContext());
        contentView.setLayoutParams(contentParmas);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        //初始化进来之后菜单是关闭的
        scrollTo(mMenuWidth, 0);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
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

    private void openMenu() {
        //滚动到0的位置
        smoothScrollTo(0,0);
    }

    private void closeMenu() {
        //滚动到mMenuWidth的位置
        smoothScrollTo(mMenuWidth,0);
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
