package com.sx.myratingbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author Administrator
 * @Date 2017/12/15 0015 下午 2:22
 * @Description
 */

public class RatingBar extends View {

    public static final String TAG = "RatingBar";
    private Bitmap starNormalBitmap, starFocusBitmap;
    private int starCount = 5;
    private int currentStars = 0;

    public RatingBar(Context context) {
        this(context, null);
    }

    public RatingBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RatingBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        /*获取自定义属性*/
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RatingBar);

        int starFocusId = typedArray.getResourceId(R.styleable.RatingBar_starFocus, 0);
        starFocusBitmap = BitmapFactory.decodeResource(getResources(), starFocusId);

        int starNormalId = typedArray.getResourceId(R.styleable.RatingBar_starNormal, 0);
        starNormalBitmap = BitmapFactory.decodeResource(getResources(), starNormalId);

        starCount = typedArray.getInt(R.styleable.RatingBar_starCount, starCount);

        typedArray.recycle();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //绘制高度，一张图片的高度    padding  间隔
        int height = starFocusBitmap.getHeight();
        int width = starFocusBitmap.getWidth() * starCount + getPaddingRight();
        setMeasuredDimension(width, height);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        //绘制几个星星
        for (int i = 0; i < starCount; i++) {
            int left = i * starNormalBitmap.getWidth();
            if (currentStars > i) {
                canvas.drawBitmap(starFocusBitmap, left, getPaddingTop(), null);
            } else {
                canvas.drawBitmap(starNormalBitmap, left, getPaddingTop(), null);
            }
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
//            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_DOWN:
                //计算分数
                //1,拿到手指按下的位置
                float moveX = event.getX();
                Log.e(TAG, "onTouchEvent: " + moveX);
                int currentStarCount = (int) (moveX / starNormalBitmap.getWidth() + 1);

                //2.判断范围
                if (currentStarCount < 0) {
                    currentStarCount = 0;
                }
                if (currentStarCount > starCount) {
                    currentStarCount = starCount;
                }

                if (currentStarCount == currentStars){
                    return true;
                }

                currentStars = currentStarCount;

                //刷新
                invalidate();//减少它的调用
                break;
        }

        return true;
    }
}
