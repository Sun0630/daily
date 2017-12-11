package com.sx.searchlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * @author Administrator
 * @Date 2017/12/11 0011 上午 11:05
 * @Description 搜索框
 */

public class SearchView extends LinearLayout {


    private float searchTextSize;
    private String textHint;
    private int textColor;
    private int searchHeight;
    private int searchBolokColor;

    public SearchView(Context context) {
        this(context, null);
    }

    public SearchView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SearchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
        init();
    }


    /**
     * 初始化自定义属性
     *
     * @param context
     * @param attrs
     */
    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Search_View);
        searchTextSize = typedArray.getDimension(R.styleable.Search_View_textSizeSearch, 20);
        textHint = typedArray.getString(R.styleable.Search_View_textHintSearch);
        textColor = typedArray.getColor(R.styleable.Search_View_textColorSearch, getResources().getColor(R.color.colorText));
        //搜索框高度
        searchHeight = typedArray.getInteger(R.styleable.Search_View_searchBlockHeight, 150);
        //搜索框颜色
        searchBolokColor = typedArray.getColor(R.styleable.Search_View_searchBlockColor, getResources().getColor(R.color.colorDefault));

        typedArray.recycle();
    }

    /**
     * 初始化搜索框
     */
    private void init() {

    }

}
