package com.sx.expandabletextview;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.SparseBooleanArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author sunxin
 * @Date 2017/11/7 0007 上午 10:12
 * @Description 自定义可展开的TextView
 */

@SuppressLint("AppCompatCustomView")
public class ExpandableTextView extends LinearLayout implements View.OnClickListener {

    private static final float DEFAULT_CONTENT_TEXT_SIZE = 16;
    private static final int MAX_COLLAPSED_LINES = 8;
    private static final float DEFAULT_CONTENT_TEXT_LINE_SPACING_MULTIPLIER = 1.0f;


    private static final int STATE_TV_GRAVITY_RIGHT = 2;
    private static final int STATE_TV_GRAVITY_CENTER = 1;
    private static final int STATE_TV_GRAVITY_LEFT = 0;
    private static final int DEFAULT_ANIMATION_DURATION = 400;

    private float contentTextSize;
    private int contentTextColor;
    private int maxCollapsedLines;
    private float contentTextLineSpacingMult;
    private Drawable expandDrawable;
    private String expandString;
    private Drawable collapseDrawable;
    private String collapseText;
    private int collapseTextColor;
    private int stateTvGravity;
    private int animationDuration;
    private TextView tv;
    private TextView stateTv;

    private boolean reLayout;
    private int marginBetWeenTextAndBottom;
    /**
     * 是否折叠
     */
    private boolean isCollapsed = true;
    private int textHeightWithMaxLines;
    private int collapseHeight;

    /**
     * 存储状态
     */
    private SparseBooleanArray collapseStatus;

    private int position;

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            marginBetWeenTextAndBottom = getHeight() - tv.getHeight();
        }
    };
    private boolean animating;


    public ExpandableTextView(Context context) {
        this(context, null);
    }

    public ExpandableTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExpandableTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        LayoutInflater.from(context).inflate(R.layout.expandable_text_view, this, true);

        //方向是垂直的
        setOrientation(VERTICAL);

        //默认隐藏
        setVisibility(GONE);


        //设置自定义属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ExpandableTextView);

        contentTextSize = typedArray.getDimension(R.styleable.ExpandableTextView_contentTextSize, DEFAULT_CONTENT_TEXT_SIZE);
        contentTextColor = typedArray.getColor(R.styleable.ExpandableTextView_contentTextColor, Color.BLACK);
        collapseTextColor = typedArray.getColor(R.styleable.ExpandableTextView_ExpandCollapseTextColor, Color.BLACK);
        maxCollapsedLines = typedArray.getInt(R.styleable.ExpandableTextView_maxCollapseLines, MAX_COLLAPSED_LINES);
        contentTextLineSpacingMult = typedArray.getFloat(R.styleable.ExpandableTextView_contentLineSpacingMultiplier, DEFAULT_CONTENT_TEXT_LINE_SPACING_MULTIPLIER);
        expandDrawable = typedArray.getDrawable(R.styleable.ExpandableTextView_expandDrawable);
        collapseText = typedArray.getString(R.styleable.ExpandableTextView_collapseText);
        collapseDrawable = typedArray.getDrawable(R.styleable.ExpandableTextView_collapseDrawable);
        expandString = typedArray.getString(R.styleable.ExpandableTextView_expandText);
        stateTvGravity = typedArray.getInt(R.styleable.ExpandableTextView_DrawableAndTextGravity, STATE_TV_GRAVITY_RIGHT);
        animationDuration = typedArray.getInt(R.styleable.ExpandableTextView_animationDuration, DEFAULT_ANIMATION_DURATION);


        if (expandDrawable == null) {
            expandDrawable = getDrawable(getContext(), R.drawable.ic_expand_more_black_12dp);
        }


        if (collapseDrawable == null) {
            collapseDrawable = getDrawable(context, R.drawable.ic_expand_less_black_12dp);
        }

        if (expandString == null) {
            expandString = "more";
        }

        if (collapseText == null) {
            collapseText = "less";
        }

        typedArray.recycle();


    }

    /**
     * 所有的子View都添加完毕之后调用
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        findViews();
    }

    private void findViews() {
        tv = findViewById(R.id.expandable_text);
        tv.setTextColor(contentTextColor);
        tv.setTextSize(contentTextSize);
        tv.setLineSpacing(0, contentTextLineSpacingMult);
        tv.setOnClickListener(this);

        stateTv = findViewById(R.id.expand_collapse);
        LinearLayout.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (stateTvGravity == STATE_TV_GRAVITY_LEFT) {
            params.gravity = Gravity.LEFT;
        } else if (stateTvGravity == STATE_TV_GRAVITY_CENTER) {
            params.gravity = Gravity.CENTER_HORIZONTAL;
        } else if (stateTvGravity == STATE_TV_GRAVITY_RIGHT) {
            params.gravity = Gravity.RIGHT;
        }


        stateTv.setLayoutParams(params);
        stateTv.setText(isCollapsed ? expandString : collapseText);
        stateTv.setTextColor(collapseTextColor);
        stateTv.setCompoundDrawablesWithIntrinsicBounds(isCollapsed ? expandDrawable : collapseDrawable, null, null, null);
        stateTv.setCompoundDrawablePadding(10);
        stateTv.setOnClickListener(this);

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        if (!reLayout || getVisibility() == GONE) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }
        reLayout = false;

        stateTv.setVisibility(GONE);

        tv.setMaxLines(Integer.MAX_VALUE);

        //测量
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


        if (tv.getLineCount() <= maxCollapsedLines) {
            return;
        }

        textHeightWithMaxLines = getRealTextViewHeight(tv);

        if (isCollapsed) {
            tv.setMaxLines(maxCollapsedLines);
        }

        stateTv.setVisibility(VISIBLE);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (isCollapsed) {
            tv.post(runnable);
            collapseHeight = getMeasuredHeight();
        }
    }


    /**
     * 获取TextView真实的高度
     *
     * @param textView
     */
    private static int getRealTextViewHeight(@NonNull TextView textView) {
        int textHeight = textView.getLayout().getLineTop(textView.getLineCount());
        int textPadding = textView.getCompoundPaddingTop() + textView.getCompoundPaddingBottom();
        return textHeight + textPadding;
    }


    /**
     * 版本适配
     *
     * @param context
     * @param resId
     * @return
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private Drawable getDrawable(Context context, int resId) {
        Resources resources = context.getResources();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return resources.getDrawable(resId, context.getTheme());
        } else {
            return resources.getDrawable(resId);
        }
    }


    @Override
    public void setOrientation(int orientation) {
        if (orientation == LinearLayout.HORIZONTAL) {
            throw new IllegalArgumentException("just support Vertical layout");
        }
        super.setOrientation(orientation);
    }

    private OnExpandStateChangeListener listener;


    @Override
    public void onClick(View v) {

        if (stateTv.getVisibility() != VISIBLE) {
            return;
        }

        isCollapsed = !isCollapsed;

        stateTv.setText(isCollapsed ? expandString : collapseText);
        stateTv.setCompoundDrawablesWithIntrinsicBounds(isCollapsed ? expandDrawable : collapseDrawable, null, null, null);

        if (collapseStatus != null) {
            collapseStatus.put(position, isCollapsed);
        }

        //动画正在进行
        animating = true;

        Animation animation;
        if (isCollapsed) {
            animation = new ExpandCollapseAnimation(this, getHeight(), collapseHeight);
        } else {
            animation = new ExpandCollapseAnimation(this, getHeight(), getHeight()
                    + textHeightWithMaxLines - tv.getHeight());
        }

        animation.setFillAfter(true);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                clearAnimation();
                animating = false;
                if (listener != null) {
                    listener.onExpandStateChanged(tv,!isCollapsed);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        clearAnimation();
        startAnimation(animation);

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // while an animation is in progress, intercept all the touch events to children to
        // prevent extra clicks during the animation
        return animating;
    }

    public void setText(@NonNull CharSequence text) {
        reLayout = true;
        tv.setText(text);
        setVisibility(TextUtils.isEmpty(text) ? GONE : VISIBLE);
    }


    public class ExpandCollapseAnimation extends Animation {

        private View targetView;
        private int startheight;
        private int endHeight;

        public ExpandCollapseAnimation(View view, int startheight, int endHeight) {
            targetView = view;
            this.startheight = startheight;
            this.endHeight = endHeight;
            setDuration(animationDuration);
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            final int newHeight = (int) ((endHeight - startheight) * interpolatedTime + startheight);
            tv.setMaxHeight(newHeight - marginBetWeenTextAndBottom);
            targetView.getLayoutParams().height = newHeight;
            targetView.requestLayout();
        }


        @Override
        public void initialize(int width, int height, int parentWidth, int parentHeight) {
            super.initialize(width, height, parentWidth, parentHeight);
        }


        @Override
        public boolean willChangeBounds() {
            return true;
        }
    }


    public interface OnExpandStateChangeListener {
        /**
         * Called when the expand/collapse animating has been finished
         *
         * @param textView   - TextView being expanded/collapsed
         * @param isExpanded - true if the TextView has been expanded
         */
        void onExpandStateChanged(TextView textView, boolean isExpanded);
    }
}
