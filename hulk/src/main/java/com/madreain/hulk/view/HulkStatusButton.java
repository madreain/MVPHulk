package com.madreain.hulk.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.StateListDrawable;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;


import com.madreain.hulk.R;
import com.madreain.hulk.view.avloading.AVLoadingIndicatorView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author madreain
 * @date 2019-07-04.
 * module：
 * description：主按钮：绿色渐变，蓝色渐变，红色 默认圆角4dp
 *              1：不可以点，2：正常可点状态，3：点击状态，4：正在加载不可点状态
 */
public class HulkStatusButton extends FrameLayout {
    public static final int COLOR_STYLE_ORANGE = 0;
    public static final int COLOR_STYLE_GRAY_GREEN = 1;
    public static final int COLOR_STYLE_RED = 2;
    public static final int COLOR_STYLE_GRAY_GREEN_LIGHT = 3;//浅绿色  再浅一点
    private int colorStyle = COLOR_STYLE_ORANGE;

    @IntDef({COLOR_STYLE_ORANGE, COLOR_STYLE_GRAY_GREEN, COLOR_STYLE_RED,COLOR_STYLE_GRAY_GREEN_LIGHT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface COLOR_STYLE {
    }

    /**
     * [COLOR_STYLE_ORANGE,COLOR_STYLE_GRAY_GREEN,COLOR_STYLE_RED,COLOR_STYLE_GRAY_GREEN_LIGHT]
     * [0,1,2,3]-->[正常态开始颜色，正常态结束颜色，按压态开始颜色，按压态结束颜色]
     */
    public static final String[][] statusColors = {{"#C8EBCA", "#C8EBCA", "#C8E6CA", "#C8E6CA"}, {"#B0997B", "#B0997B", "#968369", "#968369"}, {"#f63d1e", "#f63d1e", "#C72F16", "#C72F16"},{"#D9B3A3", "#D9B3A3", "#B0997B", "#B0997B"}};

    public void setColorStyle(@COLOR_STYLE int colorStyle) {
        this.colorStyle = colorStyle;
        initColor();
    }

    public HulkStatusButton(Context context) {
        this(context, null);
    }

    public HulkStatusButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HulkStatusButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setMinimumHeight(dp2px(48));
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.HulkStatusButton);
        int n = ta.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = ta.getIndex(i);
            if (attr == R.styleable.HulkStatusButton_sbColorStyle) {
                colorStyle = ta.getInteger(attr, COLOR_STYLE_ORANGE);
            } else if (attr == R.styleable.HulkStatusButton_android_text) {
                btnText = ta.getString(attr);
            } else if (attr == R.styleable.HulkStatusButton_android_enabled) {
                enabled = ta.getBoolean(attr, true);
            } else if (attr == R.styleable.HulkStatusButton_sbTextSize) {
                textSizePX = ta.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, textSizePX, getResources().getDisplayMetrics()));
            } else if (attr == R.styleable.HulkStatusButton_sbRadius) {
                sbRadius = ta.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, sbRadius, getResources().getDisplayMetrics()));
            }
        }
        ta.recycle();
        init();
    }

    private AVLoadingIndicatorView iv_loading;
    private TextView tv;
    private GradientDrawable gradientDrawableUnable;
    private Drawable rippleDrawable;
    private StateListDrawable stateListDrawable = new StateListDrawable();
    private boolean enabled = true;
    /**
     * 单位px
     */
    private float sbRadius = dp2px(8);

    /**
     * 设置按钮文本大小 单位dp
     *
     * @param sbRadiusDp dp
     */
    public void setSbRadius(float sbRadiusDp) {
        this.sbRadius = dp2px(sbRadiusDp);
        initColor();
    }

    private CharSequence btnText;
    /**
     * 单位px  默认18sp
     */
    private float textSizePX = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 18, getResources().getDisplayMetrics());

    public void init() {
        inflate(getContext(), R.layout.layout_status_button, this);
        iv_loading = findViewById(R.id.iv_loading);
        iv_loading.setIndicatorColor(getContext().getResources().getColor(R.color.mC8EBCA));
        tv = findViewById(R.id.tv_text);
        tv.setTextColor(enabled ? Color.WHITE : Color.parseColor("#cccccc"));
        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSizePX);
        tv.setText(btnText);
        initColor();
    }

    public void setText(CharSequence text) {
        tv.setText(text);
    }

    /**
     * 设置按钮文本大小 单位sp
     *
     * @param textSizeSP sp
     */
    public void setTextSize(float textSizeSP) {
        textSizePX = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, textSizeSP, getResources().getDisplayMetrics());
        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSizePX);
    }

    public void initColor() {
        gradientDrawableUnable = new GradientDrawable();
        gradientDrawableUnable.setColor(Color.parseColor("#eeeeee"));
        gradientDrawableUnable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawableUnable.setCornerRadius(sbRadius);

        GradientDrawable gradientDrawableNormal = new GradientDrawable();
        gradientDrawableNormal.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
        gradientDrawableNormal.setColors(new int[]{Color.parseColor(statusColors[colorStyle][0]), Color.parseColor(statusColors[colorStyle][1])});
        gradientDrawableNormal.setShape(GradientDrawable.RECTANGLE);
        gradientDrawableNormal.setCornerRadius(sbRadius);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            rippleDrawable = new RippleDrawable(ColorStateList.valueOf(Color.parseColor("#33000000")), gradientDrawableNormal, gradientDrawableNormal);
            stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, rippleDrawable);
            setElevation(2);
            setBackground(enabled ? rippleDrawable : gradientDrawableUnable);
        } else {
            GradientDrawable gradientDrawablePress = new GradientDrawable();
            gradientDrawablePress.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
            gradientDrawablePress.setColors(new int[]{Color.parseColor(statusColors[colorStyle][2]), Color.parseColor(statusColors[colorStyle][3])});
            gradientDrawablePress.setShape(GradientDrawable.RECTANGLE);
            gradientDrawablePress.setCornerRadius(sbRadius);

            stateListDrawable.addState(new int[]{-android.R.attr.state_enabled}, gradientDrawableUnable);
            stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, gradientDrawablePress);
            stateListDrawable.addState(new int[]{-android.R.attr.state_pressed}, gradientDrawableNormal);
            setBackground(enabled ? stateListDrawable : gradientDrawableUnable);
        }
    }

    public int dp2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public void startLoading() {
        tv.startAnimation(AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_out));
        tv.setVisibility(GONE);
        iv_loading.smoothToShow();
        setClickable(false);
    }

    public void stopLoading() {
        iv_loading.smoothToHide();
        tv.startAnimation(AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_in));
        tv.setVisibility(VISIBLE);
        setClickable(true);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        this.enabled = enabled;
        tv.setTextColor(enabled ? Color.WHITE : Color.parseColor("#cccccc"));
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            setBackground(enabled ? rippleDrawable : gradientDrawableUnable);
        } else {
            setBackground(enabled ? stateListDrawable : gradientDrawableUnable);
        }
    }
}
