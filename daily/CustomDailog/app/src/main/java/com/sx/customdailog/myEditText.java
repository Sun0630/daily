package com.sx.customdailog;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class myEditText extends android.support.v7.widget.AppCompatEditText {
    private int lineColor;//横线颜色
    private float lineWidth;//横线宽度

    public myEditText(Context context) {
        super(context);
        //设置默认颜色和横线宽度
        lineColor = Color.BLUE;//默认蓝色线
        lineWidth = 0.5f;//默认宽度为0.5
    }

    public myEditText(Context context, int color, float width) {
        super(context);
        //设置颜色和横线宽度
        this.lineColor = color;
        this.lineWidth = width;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        //创建画笔
        Paint mPaint = new Paint();
        mPaint.setStrokeWidth(lineWidth);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(lineColor);
        //获取参数
        int padL = this.getPaddingLeft();//获取框内左边留白
        int padR = this.getPaddingRight();//获取框内右边留白
        int padT = this.getPaddingTop();//获取框内顶部留白
        int lines = this.getLineCount();//获取行数
        float size = this.getTextSize();//获取字体大小
        float baseTop = padT + size / 6;//从上向下第一条线的位置
        /*这里需要说明的是size/6这个值，是偶然测试得到的，近似于行距的一半
         *为什么不用EditText.getLineSpacingExtra();来获取行距？
         *因为测试发现若调用EditText的getLineSpacingExtra方法会报NoSuchMethod错误，具体原因不明
         *测试发现行距的值近似于TextSize的1/3，在需要用到行距的时候可以用这个值来代替getLineSpacingExtra方法
         * */
        float gap = this.getLineHeight();//获取行宽
        //从上向下划线
        for (int i = 1; i <= lines; i++) {
            canvas.drawLine(padL//startX
                    , baseTop + gap * i//startY
                    , this.getWidth() - padR//endX
                    , baseTop + gap * i//endY
                    , mPaint);
        }
    }

    public int getLineColor() {
        return lineColor;
    }

    public void setLineColor(int color) {
        this.lineColor = color;
    }

    public float getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth(float width) {
        this.lineWidth = width;
    }
}