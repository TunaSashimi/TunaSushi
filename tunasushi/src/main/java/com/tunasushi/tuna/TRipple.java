package com.tunasushi.tuna;

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
import android.util.TypedValue;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

import com.tuna.R;

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
    public void setRippleCircleColorInner(int rippleCircleColorGradientStartInner, int rippleCircleColorGradientEndInner) {
        this.rippleCircleColorGradientStartInner = rippleCircleColorGradientStartInner;
        this.rippleCircleColorGradientEndInner = rippleCircleColorGradientEndInner;

        setRippleCircleColorInner(
                rippleCircleAngleInner == Integer.MAX_VALUE ? 0 : rippleCircleAngleInner,
                rippleCircleColorGradientStartInner,
                rippleCircleColorGradientEndInner);
    }

    public void setRippleCircleColorInner(int rippleCircleAngleInner, int rippleCircleColorGradientStartInner, int rippleCircleColorGradientEndInner) {
        this.rippleCircleColorGradientStartInner = rippleCircleColorGradientStartInner;
        this.rippleCircleColorGradientEndInner = rippleCircleColorGradientEndInner;

        rippleCircleColorInnerShader = getLinearGradient(width, height, rippleCircleAngleInner, rippleCircleColorGradientStartInner, rippleCircleColorGradientEndInner);
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

    public void setRippleCircleColorOuter(int rippleCircleColorGradientStartOuter, int rippleCircleColorGradientEndOuter) {
        setRippleCircleColorOuter(
                rippleCircleAngleOuter == Integer.MAX_VALUE ? 0 : rippleCircleAngleOuter,
                rippleCircleColorGradientStartOuter,
                rippleCircleColorGradientEndOuter);
    }

    public void setRippleCircleColorOuter(int rippleCircleAngleOuter, int rippleCircleColorGradientStartOuter, int rippleCircleColorGradientEndOuter) {
        this.rippleCircleColorGradientStartOuter = rippleCircleColorGradientStartOuter;
        this.rippleCircleColorGradientEndOuter = rippleCircleColorGradientEndOuter;

        rippleCircleColorOuterShader = getLinearGradient(width, height, rippleCircleAngleOuter, rippleCircleColorGradientStartOuter, rippleCircleColorGradientEndOuter);
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

    private String rippleTextValue;

    public String getRippleTextValue() {
        return rippleTextValue;
    }

    public void setRippleTextValue(String rippleTextValue) {
        this.rippleTextValue = rippleTextValue;
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

    private int rippleDuraction;

    public int getRippleDuraction() {
        return rippleDuraction;
    }

    public void setRippleDuraction(int rippleDuraction) {
        this.rippleDuraction = rippleDuraction;
    }

    private int rippleCircleAngleInner;

    public int getRippleCircleAngleInner() {
        return rippleCircleAngleInner;
    }

    public void setRippleCircleAngleInner(int rippleCircleAngleInner) {
        this.rippleCircleAngleInner = rippleCircleAngleInner;
        rippleCircleColorInnerShader = getLinearGradient(width, height, rippleCircleAngleInner, rippleCircleColorGradientStartInner, rippleCircleColorGradientEndInner);
    }

    private int rippleCircleAngleOuter;

    public int getRippleCircleAngleOuter() {
        return rippleCircleAngleOuter;
    }

    public void setRippleCircleAngleOuter(int rippleCircleAngleOuter) {
        this.rippleCircleAngleOuter = rippleCircleAngleOuter;
        rippleCircleColorOuterShader = getLinearGradient(width, height, rippleCircleAngleOuter, rippleCircleColorGradientStartOuter, rippleCircleColorGradientEndOuter);
    }

    // rippleCircleColorGradientStartInner default rippleCircleColorInner
    private int rippleCircleColorGradientStartInner;

    public int getRippleCircleColorGradientStartInner() {
        return rippleCircleColorGradientStartInner;
    }

    public void setRippleCircleColorGradientStartInner(int rippleCircleColorGradientStartInner) {
        this.rippleCircleColorGradientStartInner = rippleCircleColorGradientStartInner;
    }

    // rippleCircleColorGradientEndInner default rippleCircleColorInner
    private int rippleCircleColorGradientEndInner;

    public int getRippleCircleColorGradientEndInner() {
        return rippleCircleColorGradientEndInner;
    }

    public void setRippleCircleColorGradientEndInner(int rippleCircleColorGradientEndInner) {
        this.rippleCircleColorGradientEndInner = rippleCircleColorGradientEndInner;
    }

    // rippleCircleColorGradientStartOuter default rippleCircleColorOuter
    private int rippleCircleColorGradientStartOuter;

    public int getRippleCircleColorGradientStartOuter() {
        return rippleCircleColorGradientStartOuter;
    }

    public void setRippleCircleColorGradientStartOuter(int rippleCircleColorGradientStartOuter) {
        this.rippleCircleColorGradientStartOuter = rippleCircleColorGradientStartOuter;
    }

    // rippleCircleColorGradientEndOuter default rippleCircleColorOuter
    private int rippleCircleColorGradientEndOuter;

    public int getRippleCircleColorGradientEndOuter() {
        return rippleCircleColorGradientEndOuter;
    }

    public void setRippleCircleColorGradientEndOuter(int rippleCircleColorGradientEndOuter) {
        this.rippleCircleColorGradientEndOuter = rippleCircleColorGradientEndOuter;
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

    public enum RippleTimeInterpolator {
        ACCELERATEDECELERATEINTERPOLATOR(0), ACCELERATEINTERPOLATOR(1), ANTICIPATEINTERPOLATOR(2), ANTICIPATEOVERSHOOTINTERPOLATOR(3), BOUNCEINTERPOLATOR(4), CYCLEINTERPOLATOR(5), DECELERATEINTERPOLATOR(
                6), LINEARINTERPOLATOR(7), OVERSHOOTINTERPOLATOR(8),
        ;
        final int nativeInt;

        RippleTimeInterpolator(int ni) {
            nativeInt = ni;
        }
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

        rippleTextSize = typedArray.getDimension(R.styleable.TRipple_rippleTextSize, textSizeDefault);
        rippleTextColor = typedArray.getColor(R.styleable.TRipple_rippleTextColor, textColorDefault);

        rippleTextValue = typedArray.getString(R.styleable.TRipple_rippleTextValue);

        //
        rippleTextDx = typedArray.getDimension(R.styleable.TRipple_rippleTextDx, 0);
        rippleTextDy = typedArray.getDimension(R.styleable.TRipple_rippleTextDy, 0);
        rippleTextFractionDx = typedArray.getFraction(R.styleable.TRipple_rippleTextFractionDx, 1, 1, 0);
        rippleTextFractionDy = typedArray.getFraction(R.styleable.TRipple_rippleTextFractionDy, 1, 1, 0);

        //
        rippleCircleAngleInner = typedArray.getInt(R.styleable.TRipple_rippleCircleAngleInner, Integer.MAX_VALUE);
        if (rippleCircleAngleInner != Integer.MAX_VALUE) {
            rippleCircleColorGradientStartInner = typedArray.getColor(R.styleable.TRipple_rippleCircleColorGradientStartInner, rippleCircleColorInner);
            rippleCircleColorGradientEndInner = typedArray.getColor(R.styleable.TRipple_rippleCircleColorGradientEndInner, rippleCircleColorInner);
        }

        rippleCircleAngleOuter = typedArray.getInt(R.styleable.TRipple_rippleCircleAngleOuter, rippleCircleAngleInner);
        if (rippleCircleAngleOuter != Integer.MAX_VALUE) {
            rippleCircleColorGradientStartOuter = typedArray.getColor(R.styleable.TRipple_rippleCircleColorGradientStartOuter, rippleCircleColorGradientStartInner);
            rippleCircleColorGradientEndOuter = typedArray.getColor(R.styleable.TRipple_rippleCircleColorGradientEndOuter, rippleCircleColorGradientEndInner);
        }

        rippleDuraction = typedArray.getInt(R.styleable.TRipple_rippleDuraction, 0);

        int rippleTimeInterpolatorIndex = typedArray.getInt(R.styleable.TRipple_rippleTimeInterpolator, -1);
        if (rippleTimeInterpolatorIndex > -1) {
            rippleTimeInterpolator = rippleTimeInterpolatorArray[rippleTimeInterpolatorIndex];
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
            rippleCircleColorInnerShader = getLinearGradient(width, height, rippleCircleAngleInner, rippleCircleColorGradientStartInner, rippleCircleColorGradientEndInner);
        }

        if (rippleCircleAngleOuter != Integer.MAX_VALUE) {
            rippleCircleColorOuterShader = getLinearGradient(width, height, rippleCircleAngleOuter, rippleCircleColorGradientStartOuter, rippleCircleColorGradientEndOuter);
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

        if (rippleTextValue != null) {
            drawText(canvas, rippleTextValue, width, (width >> 1) + rippleTextDx, (height >> 1) + rippleTextDy, 0, 0,
                    initTextPaint(Paint.Style.FILL, rippleTextColor, rippleTextSize, Align.CENTER));
        }
    }

    public void play() {
        rippleAnimatorSet = new AnimatorSet();

        ObjectAnimator rippleOuterCircleObjectAnimator = ObjectAnimator.ofFloat(this, rippleAnimationCircleRadiusProperty, rippleCircleRadiusInner,
                rippleCircleRadiusInner + rippleDeltaRadius * 3);

        rippleOuterCircleObjectAnimator.setDuration(rippleDuraction);
        rippleAnimatorSet.playTogether(rippleOuterCircleObjectAnimator);

        if (rippleTimeInterpolator != null) {
            rippleAnimatorSet.setInterpolator(rippleTimeInterpolator);
        }
        rippleAnimatorSet.start();
    }

}
