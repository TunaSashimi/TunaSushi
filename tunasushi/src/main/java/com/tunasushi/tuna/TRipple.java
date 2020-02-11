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
 * @author Tunasashimi
 * @date 10/30/15 16:57
 * @Copyright 2015 Sashimi. All rights reserved.
 * @Description
 */
public class TRipple extends TView {

    private float rippleInnerCircleRadius;

    public float getRippleInnerCircleRadius() {
        return rippleInnerCircleRadius;
    }

    public void setRippleInnerCircleRadius(float rippleInnerCircleRadius) {
        this.rippleInnerCircleRadius = rippleInnerCircleRadius;
    }

    private int rippleInnerCircleColor;

    public int getRippleInnerCircleColor() {
        return rippleInnerCircleColor;
    }

    public void setRippleInnerCircleColor(int rippleInnerCircleColor) {
        if (this.rippleInnerCircleColor != rippleInnerCircleColor) {
            rippleInnerCircleColorShader = null;
            this.rippleInnerCircleColor = rippleInnerCircleColor;
        }
    }

    //
    public void setRippleInnerCircleColor(int rippleInnerCircleColorGradientStart, int rippleInnerCircleColorGradientEnd) {
        this.rippleInnerCircleColorGradientStart = rippleInnerCircleColorGradientStart;
        this.rippleInnerCircleColorGradientEnd = rippleInnerCircleColorGradientEnd;

        setRippleInnerCircleColor(
                rippleInnerCircleAngle == Integer.MAX_VALUE ? 0 : rippleInnerCircleAngle,
                rippleInnerCircleColorGradientStart,
                rippleInnerCircleColorGradientEnd);
    }

    public void setRippleInnerCircleColor(int rippleInnerCircleAngle, int rippleInnerCircleColorGradientStart, int rippleInnerCircleColorGradientEnd) {
        this.rippleInnerCircleColorGradientStart = rippleInnerCircleColorGradientStart;
        this.rippleInnerCircleColorGradientEnd = rippleInnerCircleColorGradientEnd;

        rippleInnerCircleColorShader = getLinearGradient(width, height, rippleInnerCircleAngle, rippleInnerCircleColorGradientStart, rippleInnerCircleColorGradientEnd);
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

    private int rippleOuterCircleColor;

    public int getRippleOuterCircleColor() {
        return rippleOuterCircleColor;
    }

    public void setRippleOuterCircleColor(int rippleOuterCircleColor) {

        if (this.rippleOuterCircleColor != rippleOuterCircleColor) {

            rippleOuterCircleColorShader = null;
            this.rippleOuterCircleColor = rippleOuterCircleColor;
        }

    }

    public void setRippleOuterCircleColor(int rippleOuterCircleColorGradientStart, int rippleOuterCircleColorGradientEnd) {
        setRippleOuterCircleColor(
                rippleOuterCircleAngle == Integer.MAX_VALUE ? 0 : rippleOuterCircleAngle,
                rippleOuterCircleColorGradientStart,
                rippleOuterCircleColorGradientEnd);
    }

    public void setRippleOuterCircleColor(int rippleOuterCircleAngle, int rippleOuterCircleColorGradientStart, int rippleOuterCircleColorGradientEnd) {
        this.rippleOuterCircleColorGradientStart = rippleOuterCircleColorGradientStart;
        this.rippleOuterCircleColorGradientEnd = rippleOuterCircleColorGradientEnd;

        rippleOuterCircleColorShader = getLinearGradient(width, height, rippleOuterCircleAngle, rippleOuterCircleColorGradientStart, rippleOuterCircleColorGradientEnd);
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
        setRippleTextDx(TypedValue.COMPLEX_UNIT_DIP, rippleTextDx);
    }

    public void setRippleTextDx(int unit, float rippleTextDx) {
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
        setRippleTextDy(TypedValue.COMPLEX_UNIT_DIP, rippleTextDy);
    }

    public void setRippleTextDy(int unit, float rippleTextDy) {
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

    private int rippleInnerCircleAngle;

    public int getRippleInnerCircleAngle() {
        return rippleInnerCircleAngle;
    }

    public void setRippleInnerCircleAngle(int rippleInnerCircleAngle) {
        this.rippleInnerCircleAngle = rippleInnerCircleAngle;
        rippleInnerCircleColorShader = getLinearGradient(width, height, rippleInnerCircleAngle, rippleInnerCircleColorGradientStart, rippleInnerCircleColorGradientEnd);
    }

    private int rippleOuterCircleAngle;

    public int getRippleOuterCircleAngle() {
        return rippleOuterCircleAngle;
    }

    public void setRippleOuterCircleAngle(int rippleOuterCircleAngle) {
        this.rippleOuterCircleAngle = rippleOuterCircleAngle;
        rippleOuterCircleColorShader = getLinearGradient(width, height, rippleOuterCircleAngle, rippleOuterCircleColorGradientStart, rippleOuterCircleColorGradientEnd);
    }

    // rippleInnerCircleColorGradientStart default rippleInnerCircleColor
    private int rippleInnerCircleColorGradientStart;

    public int getRippleInnerCircleColorGradientStart() {
        return rippleInnerCircleColorGradientStart;
    }

    public void setRippleInnerCircleColorGradientStart(int rippleInnerCircleColorGradientStart) {
        this.rippleInnerCircleColorGradientStart = rippleInnerCircleColorGradientStart;
    }

    // rippleInnerCircleColorGradientEnd default rippleInnerCircleColor
    private int rippleInnerCircleColorGradientEnd;

    public int getRippleInnerCircleColorGradientEnd() {
        return rippleInnerCircleColorGradientEnd;
    }

    public void setRippleInnerCircleColorGradientEnd(int rippleInnerCircleColorGradientEnd) {
        this.rippleInnerCircleColorGradientEnd = rippleInnerCircleColorGradientEnd;
    }

    // rippleOuterCircleColorGradientStart default rippleOuterCircleColor
    private int rippleOuterCircleColorGradientStart;

    public int getRippleOuterCircleColorGradientStart() {
        return rippleOuterCircleColorGradientStart;
    }

    public void setRippleOuterCircleColorGradientStart(int rippleOuterCircleColorGradientStart) {
        this.rippleOuterCircleColorGradientStart = rippleOuterCircleColorGradientStart;
    }

    // rippleOuterCircleColorGradientEnd default rippleOuterCircleColor
    private int rippleOuterCircleColorGradientEnd;

    public int getRippleOuterCircleColorGradientEnd() {
        return rippleOuterCircleColorGradientEnd;
    }

    public void setRippleOuterCircleColorGradientEnd(int rippleOuterCircleColorGradientEnd) {
        this.rippleOuterCircleColorGradientEnd = rippleOuterCircleColorGradientEnd;
    }

    // rippleInnerCircleColorShader default null
    private Shader rippleInnerCircleColorShader;

    public Shader getRippleInnerCircleColorShader() {
        return rippleInnerCircleColorShader;
    }

    public void setRippleInnerCircleColorShader(Shader rippleInnerCircleColorShader) {
        this.rippleInnerCircleColorShader = rippleInnerCircleColorShader;
    }

    // rippleOuterCircleColorShader default null
    private Shader rippleOuterCircleColorShader;

    public Shader getRippleOuterCircleColorShader() {
        return rippleOuterCircleColorShader;
    }

    public void setRippleOuterCircleColorShader(Shader rippleOuterCircleColorShader) {
        this.rippleOuterCircleColorShader = rippleOuterCircleColorShader;
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

        rippleInnerCircleRadius = typedArray.getDimension(R.styleable.TRipple_rippleInnerCircleRadius, 0);
        rippleInnerCircleColor = typedArray.getColor(R.styleable.TRipple_rippleInnerCircleColor, Color.TRANSPARENT);

        //
        rippleAnimationCircleRadius = rippleInnerCircleRadius;

        rippleOuterCircleColor = typedArray.getColor(R.styleable.TRipple_rippleOuterCircleColor, rippleInnerCircleColor);

        rippleTextSize = typedArray.getDimension(R.styleable.TRipple_rippleTextSize, 0);
        rippleTextColor = typedArray.getColor(R.styleable.TRipple_rippleTextColor, Color.TRANSPARENT);

        rippleTextValue = typedArray.getString(R.styleable.TRipple_rippleTextValue);

        //
        rippleTextDx = typedArray.getDimension(R.styleable.TRipple_rippleTextDx, 0);
        rippleTextDy = typedArray.getDimension(R.styleable.TRipple_rippleTextDy, 0);
        rippleTextFractionDx = typedArray.getFraction(R.styleable.TRipple_rippleTextFractionDx, 1, 1, 0);
        rippleTextFractionDy = typedArray.getFraction(R.styleable.TRipple_rippleTextFractionDy, 1, 1, 0);

        //
        rippleInnerCircleAngle = typedArray.getInt(R.styleable.TRipple_rippleInnerCircleAngle, Integer.MAX_VALUE);
        if (rippleInnerCircleAngle != Integer.MAX_VALUE) {
            rippleInnerCircleColorGradientStart = typedArray.getColor(R.styleable.TRipple_rippleInnerCircleColorGradientStart, rippleInnerCircleColor);
            rippleInnerCircleColorGradientEnd = typedArray.getColor(R.styleable.TRipple_rippleInnerCircleColorGradientEnd, rippleInnerCircleColor);
        }

        rippleOuterCircleAngle = typedArray.getInt(R.styleable.TRipple_rippleOuterCircleAngle, rippleInnerCircleAngle);
        if (rippleOuterCircleAngle != Integer.MAX_VALUE) {
            rippleOuterCircleColorGradientStart = typedArray.getColor(R.styleable.TRipple_rippleOuterCircleColorGradientStart, rippleInnerCircleColorGradientStart);
            rippleOuterCircleColorGradientEnd = typedArray.getColor(R.styleable.TRipple_rippleOuterCircleColorGradientEnd, rippleInnerCircleColorGradientEnd);
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
        rippleDeltaRadius = rippleMaxRadius - rippleInnerCircleRadius;

        if (rippleInnerCircleRadius >= rippleMaxRadius) {
            throw new IndexOutOfBoundsException("The content attribute rippleInnerCircleRadius length must be less than half of the width or height");
        }

        rippleTextDx += width * rippleTextFractionDx;
        rippleTextDy += height * rippleTextFractionDy;

        if (rippleInnerCircleAngle != Integer.MAX_VALUE) {
            rippleInnerCircleColorShader = getLinearGradient(width, height, rippleInnerCircleAngle, rippleInnerCircleColorGradientStart, rippleInnerCircleColorGradientEnd);
        }

        if (rippleOuterCircleAngle != Integer.MAX_VALUE) {
            rippleOuterCircleColorShader = getLinearGradient(width, height, rippleOuterCircleAngle, rippleOuterCircleColorGradientStart, rippleOuterCircleColorGradientEnd);
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (rippleInnerCircleRadius > 0) {
            // The latter part of the judge to modify the contents added to initPaint in
            canvas.drawCircle(width >> 1, height >> 1, rippleInnerCircleRadius,
                    initPaint(Paint.Style.FILL, rippleInnerCircleColor, rippleInnerCircleColorShader));
        }

        // 255
        if (rippleOuterCircleRadius > rippleInnerCircleRadius && rippleOuterCircleRadius < rippleMaxRadius) {
            canvas.drawCircle(
                    width >> 1,
                    height >> 1,
                    rippleOuterCircleRadius,
                    initPaint(Paint.Style.FILL, rippleOuterCircleColor, rippleInnerCircleColorShader, (int) ((rippleMaxRadius - rippleOuterCircleRadius)
                            / rippleDeltaRadius * 255)));
        }
        if (rippleOuterDelayCircleRadius > rippleInnerCircleRadius && rippleOuterDelayCircleRadius < rippleMaxRadius) {
            canvas.drawCircle(
                    width >> 1,
                    height >> 1,
                    rippleOuterDelayCircleRadius,
                    initPaint(Paint.Style.FILL, rippleOuterCircleColor, rippleOuterCircleColorShader, (int) ((rippleMaxRadius - rippleOuterDelayCircleRadius)
                            / rippleDeltaRadius * 255)));
        }
        if (rippleOuterDeferCircleRadius > rippleInnerCircleRadius && rippleOuterDeferCircleRadius < rippleMaxRadius) {
            canvas.drawCircle(
                    width >> 1,
                    height >> 1,
                    rippleOuterDeferCircleRadius,
                    initPaint(Paint.Style.FILL, rippleOuterCircleColor, rippleOuterCircleColorShader, (int) ((rippleMaxRadius - rippleOuterDeferCircleRadius)
                            / rippleDeltaRadius * 255)));
        }

        if (rippleTextValue != null) {
            drawText(canvas, rippleTextValue, width, (width >> 1) + rippleTextDx, (height >> 1) + rippleTextDy, 0, 0,
                    initTextPaint(Paint.Style.FILL, rippleTextColor, rippleTextSize, Align.CENTER));
        }
    }

    public void play() {
        rippleAnimatorSet = new AnimatorSet();

        ObjectAnimator rippleOuterCircleObjectAnimator = ObjectAnimator.ofFloat(this, rippleAnimationCircleRadiusProperty, rippleInnerCircleRadius,
                rippleInnerCircleRadius + rippleDeltaRadius * 3);

        rippleOuterCircleObjectAnimator.setDuration(rippleDuraction);
        rippleAnimatorSet.playTogether(rippleOuterCircleObjectAnimator);

        if (rippleTimeInterpolator != null) {
            rippleAnimatorSet.setInterpolator(rippleTimeInterpolator);
        }
        rippleAnimatorSet.start();
    }

}
