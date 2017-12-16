package com.sx.cityindex;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author sunxin
 * @Date 2017/12/16 0016 上午 10:03
 * @Description 侧边栏
 */

public class LetterSideBar extends View {

    private Paint paint;
    private int barTextSize;
    private int barTextColor;

    /**
     * 定义26个字母
     */
    public static String[] mLetters = {"A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z", "#"};
    private String currentLetter;


    public LetterSideBar(Context context) {
        this(context, null);
    }

    public LetterSideBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LetterSideBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LetterSideBar);
        barTextSize = (int) typedArray.getDimension(R.styleable.LetterSideBar_barTextSize, 12);
        int defaultColor = getResources().getColor(R.color.colorAccent);
        barTextColor = typedArray.getColor(R.styleable.LetterSideBar_barTextColor, defaultColor);
        typedArray.recycle();
        initPaint();
    }

    private void initPaint() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(barTextSize);
        paint.setColor(barTextColor);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //计算宽度，  左右padding值+画笔的宽度
        int width = (int) (getPaddingLeft() + getPaddingRight() + paint.measureText("A"));
        //获取高度
        int height = MeasureSpec.getSize(heightMeasureSpec);
        //设置宽高
        setMeasuredDimension(width, height);
    }


    @Override
    protected void onDraw(Canvas canvas) {

        //y 是基线,基线基于中心位置，1，中心位置为字母高度的一半，字母高度的一半+上面字符的高度
        int itemHeight = (getHeight() - getPaddingBottom() - getPaddingTop()) / mLetters.length;
        for (int i = 0; i < mLetters.length; i++) {
            //计算中心点的位置
            int letterCenterY = itemHeight / 2 + itemHeight * i + getPaddingTop();
            //计算基线
            Paint.FontMetrics fontMetrics = paint.getFontMetrics();
            int dy = (int) ((fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom);
            int baseLine = letterCenterY + dy;
            //绘制26个字母和一个#号.字母不居中  x需要=宽度/2  - 字母宽度/2
            int textWidth = (int) paint.measureText(mLetters[i]);
            int x = getWidth() / 2 - textWidth / 2;

            //当前字母高亮
            if (mLetters[i].equals(currentLetter)) {
                paint.setColor(Color.BLACK);
                canvas.drawText(mLetters[i], x, baseLine, paint);
            } else {
                paint.setColor(Color.RED);
                canvas.drawText(mLetters[i], x, baseLine, paint);
            }


        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_DOWN:
                //计算出手指触摸位置的字母，重绘
                //拿到字母的高度
                int itemHeight = (getHeight() - getPaddingBottom() - getPaddingTop()) / mLetters.length;
                //获取当前触摸的位置
                float currentMoveY = event.getY();
                //计算字母的position

                int currentTouchPosition = (int) (currentMoveY / itemHeight);
                if (currentTouchPosition < 0) {
                    currentTouchPosition = 0;
                }

                if (currentTouchPosition > mLetters.length - 1) {
                    currentTouchPosition = mLetters.length - 1;
                }
                //根据position找出字母
                currentLetter = mLetters[currentTouchPosition];
                if (onTouchLetterListener != null) {
                    onTouchLetterListener.onTouchLetter(currentLetter, true);
                }

                if (currentTouchPosition == mLetters.length - 1) {
                    return true;
                }


                invalidate();//--> onDraw()  不要重复多次掉

                break;
            case MotionEvent.ACTION_UP:
                //延迟消失
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (onTouchLetterListener != null) {
                            onTouchLetterListener.onTouchLetter(currentLetter, false);
                        }
                    }
                }, 300);
                break;
        }
        return true;
    }

    private OnTouchLetterListener onTouchLetterListener;

    public void setOnTouchLetterListener(OnTouchLetterListener onTouchLetterListener) {
        this.onTouchLetterListener = onTouchLetterListener;
    }

    public interface OnTouchLetterListener {
        /**
         * 触摸字母的回调
         *
         * @param isTouch 是否触摸
         * @param letter
         */
        void onTouchLetter(CharSequence letter, boolean isTouch);

    }


}
