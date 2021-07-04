package com.tunasushi.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Property;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;


import com.tunasushi.R;

import static com.tunasushi.tool.ConvertTool.convertToPX;
import static com.tunasushi.tool.ViewTool.getLinearGradient;

/**
 * @author TunaSashimi
 * @date 2015-10-30 16:57
 * @Copyright 2015 TunaSashimi. All rights reserved.
 * @Description
 */
public class TRipple extends TView {
    private float rippleCircleRadiusInner;

    public float getRippleCircleRadiusInner() {
        return rippleCircleRadiusInner;
    }

    public void setRippleCircleRadiusInner(float rippleCircleRadiusInner) {
        this.rippleCircleRadiusInner = rippleCircleRadiusInner;
    }

    private int rippleCircleColorInner;

    public int getRippleCircleColorInner() {
        return rippleCircleColorInner;
    }

    public void setRippleCircleColorInner(int rippleCircleColorInner) {
        if (this.rippleCircleColorInner != rippleCircleColorInner) {
            rippleCircleColorInnerShader = null;
            this.rippleCircleColorInner = rippleCircleColorInner;
        }
    }

    //
    public void setRippleCircleColorInner(int rippleCircleColorStartInner, int rippleCircleColorEndInner) {
        this.rippleCircleColorStartInner = rippleCircleColorStartInner;
        this.rippleCircleColorEndInner = rippleCircleColorEndInner;

        setRippleCircleColorInner(
                rippleCircleAngleInner == Integer.MAX_VALUE ? 0 : rippleCircleAngleInner,
                rippleCircleColorStartInner,
                rippleCircleColorEndInner);
    }

    public void setRippleCircleColorInner(int rippleCircleAngleInner, int rippleCircleColorStartInner, int rippleCircleColorEndInner) {
        this.rippleCircleColorStartInner = rippleCircleColorStartInner;
        this.rippleCircleColorEndInner = rippleCircleColorEndInner;

        rippleCircleColorInnerShader = getLinearGradient(width, height, rippleCircleAngleInner, rippleCircleColorStartInner, rippleCircleColorEndInner);
    }

    //
    private float rippleOuterCircleRadius;

    public float getRippleOuterCircleRadius() {
        return rippleOuterCircleRadius;
    }

    public void setRippleOuterCircleRadius(float rippleOuterCircleRadius) {
        this.rippleOuterCircleRadius = rippleOuterCircleRadius;
    }

    // delay
    private float rippleOuterDelayCircleRadius;

    public float getRippleOuterDelayCircleRadius() {
        return rippleOuterDelayCircleRadius;
    }

    public void setRippleOuterDelayCircleRadius(float rippleOuterDelayCircleRadius) {
        this.rippleOuterDelayCircleRadius = rippleOuterDelayCircleRadius;
    }

    // defer
    private float rippleOuterDeferCircleRadius;

    public float getRippleOuterDeferCircleRadius() {
        return rippleOuterDeferCircleRadius;
    }

    public void setRippleOuterDeferCircleRadius(float rippleOuterDeferCircleRadius) {
        this.rippleOuterDeferCircleRadius = rippleOuterDeferCircleRadius;
    }

    private int rippleCircleColorOuter;

    public int getRippleCircleColorOuter() {
        return rippleCircleColorOuter;
    }

    public void setRippleCircleColorOuter(int rippleCircleColorOuter) {

        if (this.rippleCircleColorOuter != rippleCircleColorOuter) {

            rippleCircleColorOuterShader = null;
            this.rippleCircleColorOuter = rippleCircleColorOuter;
        }

    }

    public void setRippleCircleColorOuter(int rippleCircleColorStartOuter, int rippleCircleColorEndOuter) {
        setRippleCircleColorOuter(
                rippleCircleAngleOuter == Integer.MAX_VALUE ? 0 : rippleCircleAngleOuter,
                rippleCircleColorStartOuter,
                rippleCircleColorEndOuter);
    }

    public void setRippleCircleColorOuter(int rippleCircleAngleOuter, int rippleCircleColorStartOuter, int rippleCircleColorEndOuter) {
        this.rippleCircleColorStartOuter = rippleCircleColorStartOuter;
        this.rippleCircleColorEndOuter = rippleCircleColorEndOuter;

        rippleCircleColorOuterShader = getLinearGradient(width, height, rippleCircleAngleOuter, rippleCircleColorStartOuter, rippleCircleColorEndOuter);
    }

    private String rippleText;

    public String getRippleText() {
        return rippleText;
    }

    public void setRippleText(String rippleText) {
        this.rippleText = rippleText;
    }

    private float rippleTextSize;

    public float getRippleTextSize() {
        return rippleTextSize;
    }

    public void setRippleTextSize(float rippleTextSize) {
        this.rippleTextSize = rippleTextSize;
    }

    private int rippleTextColor;

    public int getRippleTextColor() {
        return rippleTextColor;
    }

    public void setRippleTextColor(int rippleTextColor) {
        this.rippleTextColor = rippleTextColor;
    }

    //
    private float rippleTextDx;

    public float getRippleTextDx() {
        return rippleTextDx;
    }

    public void setRippleTextDx(float rippleTextDx) {
        setRippleTextDxRaw(rippleTextDx);
    }

    public void setRippleTextDx(float rippleTextDx, int unit) {
        setRippleTextDxRaw(convertToPX(rippleTextDx, unit));
    }

    private void setRippleTextDxRaw(float rippleTextDx) {
        if (this.rippleTextDx != rippleTextDx) {
            this.rippleTextDx = rippleTextDx;
        }
    }

    //
    private float rippleTextDy;

    public float getRippleTextDy() {
        return rippleTextDy;
    }

    public void setRippleTextDy(float rippleTextDy) {
        setRippleTextDyRaw(rippleTextDy);
    }

    public void setRippleTextDy(float rippleTextDy, int unit) {
        setRippleTextDyRaw(convertToPX(rippleTextDy, unit));
    }

    private void setRippleTextDyRaw(float rippleTextDy) {
        if (this.rippleTextDy != rippleTextDy) {
            this.rippleTextDy = rippleTextDy;
        }
    }

    //
    private float rippleTextFractionDx;

    public float getRippleTextFractionDx() {
        return rippleTextFractionDx;
    }

    public void setRippleTextFractionDx(float rippleTextFractionDx) {
        this.rippleTextFractionDx = rippleTextFractionDx;
    }

    //
    private float rippleTextFractionDy;

    public float getRippleTextFractionDy() {
        return rippleTextFractionDy;
    }

    public void setRippleTextFractionDy(float rippleTextFractionDy) {
        this.rippleTextFractionDy = rippleTextFractionDy;
    }

    private int rippleAngle;

    public int getRippleDuraction() {
        return rippleAngle;
    }

    public void setRippleDuraction(int rippleAngle) {
        this.rippleAngle = rippleAngle;
    }

    private int rippleCircleAngleInner;

    public int getRippleCircleAngleInner() {
        return rippleCircleAngleInner;
    }

    public void setRippleCircleAngleInner(int rippleCircleAngleInner) {
        this.rippleCircleAngleInner = rippleCircleAngleInner;
        rippleCircleColorInnerShader = getLinearGradient(width, height, rippleCircleAngleInner, rippleCircleColorStartInner, rippleCircleColorEndInner);
    }

    private int rippleCircleAngleOuter;

    public int getRippleCircleAngleOuter() {
        return rippleCircleAngleOuter;
    }

    public void setRippleCircleAngleOuter(int rippleCircleAngleOuter) {
        this.rippleCircleAngleOuter = rippleCircleAngleOuter;
        rippleCircleColorOuterShader = getLinearGradient(width, height, rippleCircleAngleOuter, rippleCircleColorStartOuter, rippleCircleColorEndOuter);
    }

    // rippleCircleColorStartInner default rippleCircleColorInner
    private int rippleCircleColorStartInner;

    public int getRippleCircleColorStartInner() {
        return rippleCircleColorStartInner;
    }

    public void setRippleCircleColorStartInner(int rippleCircleColorStartInner) {
        this.rippleCircleColorStartInner = rippleCircleColorStartInner;
    }

    // rippleCircleColorEndInner default rippleCircleColorInner
    private int rippleCircleColorEndInner;

    public int getRippleCircleColorEndInner() {
        return rippleCircleColorEndInner;
    }

    public void setRippleCircleColorEndInner(int rippleCircleColorEndInner) {
        this.rippleCircleColorEndInner = rippleCircleColorEndInner;
    }

    // rippleCircleColorStartOuter default rippleCircleColorOuter
    private int rippleCircleColorStartOuter;

    public int getRippleCircleColorStartOuter() {
        return rippleCircleColorStartOuter;
    }

    public void setRippleCircleColorStartOuter(int rippleCircleColorStartOuter) {
        this.rippleCircleColorStartOuter = rippleCircleColorStartOuter;
    }

    // rippleCircleColorEndOuter default rippleCircleColorOuter
    private int rippleCircleColorEndOuter;

    public int getRippleCircleColorEndOuter() {
        return rippleCircleColorEndOuter;
    }

    public void setRippleCircleColorEndOuter(int rippleCircleColorEndOuter) {
        this.rippleCircleColorEndOuter = rippleCircleColorEndOuter;
    }

    // rippleCircleColorInnerShader default null
    private Shader rippleCircleColorInnerShader;

    public Shader getRippleCircleColorInnerShader() {
        return rippleCircleColorInnerShader;
    }

    public void setRippleCircleColorInnerShader(Shader rippleCircleColorInnerShader) {
        this.rippleCircleColorInnerShader = rippleCircleColorInnerShader;
    }

    // rippleCircleColorOuterShader default null
    private Shader rippleCircleColorOuterShader;

    public Shader getRippleCircleColorOuterShader() {
        return rippleCircleColorOuterShader;
    }

    public void setRippleCircleColorOuterShader(Shader rippleCircleColorOuterShader) {
        this.rippleCircleColorOuterShader = rippleCircleColorOuterShader;
    }

    //
    private TimeInterpolator rippleTimeInterpolator;

    public TimeInterpolator getRippleTimeInterpolator() {
        return rippleTimeInterpolator;
    }

    public void setRippleTimeInterpolator(TimeInterpolator rippleTimeInterpolator) {
        this.rippleTimeInterpolator = rippleTimeInterpolator;
    }

    //
    private static final TimeInterpolator[] rippleTimeInterpolatorArray = {new AccelerateDecelerateInterpolator(), new AccelerateInterpolator(), new AnticipateInterpolator(),
            new AnticipateOvershootInterpolator(), new BounceInterpolator(), new CycleInterpolator(0), new DecelerateInterpolator(), new LinearInterpolator(),
            new OvershootInterpolator(),};

    public static TimeInterpolator[] getRippletimeinterpolatorarray() {
        return rippleTimeInterpolatorArray;
    }

    //
    private float rippleMaxRadius;
    private float rippleDeltaRadius;
    private float rippleAnimationCircleRadius;

    //
    private AnimatorSet rippleAnimatorSet;

    public AnimatorSet getRippleAnimatorSet() {
        return rippleAnimatorSet;
    }

    public void setRippleAnimatorSet(AnimatorSet rippleAnimatorSet) {
        this.rippleAnimatorSet = rippleAnimatorSet;
    }

    private Property<TRipple, Float> rippleAnimationCircleRadiusProperty = new Property<TRipple, Float>(Float.class, "rippleAnimationCircleRadiusProperty") {
        @Override
        public Float get(TRipple object) {
            return object.rippleAnimationCircleRadius;
        }

        @Override
        public void set(TRipple object, Float value) {
            object.rippleAnimationCircleRadius = value;
            // We need to draw three radius
            object.rippleOuterCircleRadius = value;
            object.rippleOuterDelayCircleRadius = value - rippleDeltaRadius * 0.5f;
            object.rippleOuterDeferCircleRadius = value - rippleDeltaRadius * 1.0f;
            invalidate();
        }
    };

    public TRipple(Context context) {
        this(context, null);
    }

    public TRipple(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TRipple(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        tag = TRipple.class.getSimpleName();

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TRipple);

        rippleCircleRadiusInner = typedArray.getDimension(R.styleable.TRipple_rippleCircleRadiusInner, 0);
        rippleCircleColorInner = typedArray.getColor(R.styleable.TRipple_rippleCircleColorInner, Color.TRANSPARENT);

        //
        rippleAnimationCircleRadius = rippleCircleRadiusInner;

        rippleCircleColorOuter = typedArray.getColor(R.styleable.TRipple_rippleCircleColorOuter, rippleCircleColorInner);

        rippleText = typedArray.getString(R.styleable.TRipple_rippleText);
        rippleTextSize = typedArray.getDimension(R.styleable.TRipple_rippleTextSize, textSizeDefault);
        rippleTextColor = typedArray.getColor(R.styleable.TRipple_rippleTextColor, textColorDefault);

        //
        rippleTextDx = typedArray.getDimension(R.styleable.TRipple_rippleTextDx, 0);
        rippleTextDy = typedArray.getDimension(R.styleable.TRipple_rippleTextDy, 0);
        rippleTextFractionDx = typedArray.getFraction(R.styleable.TRipple_rippleTextFractionDx, 1, 1, 0);
        rippleTextFractionDy = typedArray.getFraction(R.styleable.TRipple_rippleTextFractionDy, 1, 1, 0);

        //
        rippleCircleAngleInner = typedArray.getInt(R.styleable.TRipple_rippleCircleAngleInner, Integer.MAX_VALUE);
        if (rippleCircleAngleInner != Integer.MAX_VALUE) {
            rippleCircleColorStartInner = typedArray.getColor(R.styleable.TRipple_rippleCircleColorStartInner, rippleCircleColorInner);
            rippleCircleColorEndInner = typedArray.getColor(R.styleable.TRipple_rippleCircleColorEndInner, rippleCircleColorInner);
        }

        rippleCircleAngleOuter = typedArray.getInt(R.styleable.TRipple_rippleCircleAngleOuter, rippleCircleAngleInner);
        if (rippleCircleAngleOuter != Integer.MAX_VALUE) {
            rippleCircleColorStartOuter = typedArray.getColor(R.styleable.TRipple_rippleCircleColorStartOuter, rippleCircleColorStartInner);
            rippleCircleColorEndOuter = typedArray.getColor(R.styleable.TRipple_rippleCircleColorEndOuter, rippleCircleColorEndInner);
        }

        rippleAngle = typedArray.getInt(R.styleable.TRipple_rippleAngle, 0);

        int rippleStyleIndex = typedArray.getInt(R.styleable.TRipple_rippleStyle, -1);
        if (rippleStyleIndex > -1) {
            rippleTimeInterpolator = rippleTimeInterpolatorArray[rippleStyleIndex];
        }
        typedArray.recycle();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        rippleMaxRadius = width > height ? height >> 1 : width >> 1;
        rippleDeltaRadius = rippleMaxRadius - rippleCircleRadiusInner;

        if (rippleCircleRadiusInner >= rippleMaxRadius) {
            throw new IndexOutOfBoundsException("The content attribute rippleCircleRadiusInner length must be less than half of the width or height");
        }

        rippleTextDx += width * rippleTextFractionDx;
        rippleTextDy += height * rippleTextFractionDy;

        if (rippleCircleAngleInner != Integer.MAX_VALUE) {
            rippleCircleColorInnerShader = getLinearGradient(width, height, rippleCircleAngleInner, rippleCircleColorStartInner, rippleCircleColorEndInner);
        }

        if (rippleCircleAngleOuter != Integer.MAX_VALUE) {
            rippleCircleColorOuterShader = getLinearGradient(width, height, rippleCircleAngleOuter, rippleCircleColorStartOuter, rippleCircleColorEndOuter);
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (rippleCircleRadiusInner > 0) {
            // The latter part of the judge to modify the contents added to initPaint in
            canvas.drawCircle(width >> 1, height >> 1, rippleCircleRadiusInner,
                    initPaint(Paint.Style.FILL, rippleCircleColorInner, rippleCircleColorInnerShader));
        }

        // 255
        if (rippleOuterCircleRadius > rippleCircleRadiusInner && rippleOuterCircleRadius < rippleMaxRadius) {
            canvas.drawCircle(
                    width >> 1,
                    height >> 1,
                    rippleOuterCircleRadius,
                    initPaint(Paint.Style.FILL, rippleCircleColorOuter, rippleCircleColorInnerShader, (int) ((rippleMaxRadius - rippleOuterCircleRadius)
                            / rippleDeltaRadius * 255)));
        }
        if (rippleOuterDelayCircleRadius > rippleCircleRadiusInner && rippleOuterDelayCircleRadius < rippleMaxRadius) {
            canvas.drawCircle(
                    width >> 1,
                    height >> 1,
                    rippleOuterDelayCircleRadius,
                    initPaint(Paint.Style.FILL, rippleCircleColorOuter, rippleCircleColorOuterShader, (int) ((rippleMaxRadius - rippleOuterDelayCircleRadius)
                            / rippleDeltaRadius * 255)));
        }
        if (rippleOuterDeferCircleRadius > rippleCircleRadiusInner && rippleOuterDeferCircleRadius < rippleMaxRadius) {
            canvas.drawCircle(
                    width >> 1,
                    height >> 1,
                    rippleOuterDeferCircleRadius,
                    initPaint(Paint.Style.FILL, rippleCircleColorOuter, rippleCircleColorOuterShader, (int) ((rippleMaxRadius - rippleOuterDeferCircleRadius)
                            / rippleDeltaRadius * 255)));
        }

        if (rippleText != null) {
            drawText(canvas, rippleText, width, (width >> 1) + rippleTextDx, (height >> 1) + rippleTextDy, 0, 0,
                    initTextPaint(Paint.Style.FILL, rippleTextColor, rippleTextSize, Align.CENTER));
        }
    }

    public void play() {
        rippleAnimatorSet = new AnimatorSet();

        ObjectAnimator rippleOuterCircleObjectAnimator = ObjectAnimator.ofFloat(this, rippleAnimationCircleRadiusProperty, rippleCircleRadiusInner,
                rippleCircleRadiusInner + rippleDeltaRadius * 3);

        rippleOuterCircleObjectAnimator.setDuration(rippleAngle);
        rippleAnimatorSet.playTogether(rippleOuterCircleObjectAnimator);

        if (rippleTimeInterpolator != null) {
            rippleAnimatorSet.setInterpolator(rippleTimeInterpolator);
        }
        rippleAnimatorSet.start();
    }
}
