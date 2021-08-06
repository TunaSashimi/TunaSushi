package com.tunasushi.view

import com.tunasushi.tool.ViewTool.getLinearGradient
import com.tunasushi.tool.ConvertTool.convertToPX
import kotlin.jvm.JvmOverloads
import android.graphics.Shader
import android.animation.TimeInterpolator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import android.view.animation.AnticipateInterpolator
import android.view.animation.AnticipateOvershootInterpolator
import android.view.animation.BounceInterpolator
import android.view.animation.CycleInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import android.view.animation.OvershootInterpolator
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Property
import com.tunasushi.R
import java.lang.IndexOutOfBoundsException

/**
 * @author TunaSashimi
 * @date 2015-10-30 16:57
 * @Copyright 2015 TunaSashimi. All rights reserved.
 * @Description
 */
class TRipple @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : TView(context, attrs, defStyle) {
    var rippleCircleRadiusInner: Float
    private var rippleCircleColorInner: Int
    fun getRippleCircleColorInner(): Int {
        return rippleCircleColorInner
    }

    fun setRippleCircleColorInner(rippleCircleColorInner: Int) {
        if (this.rippleCircleColorInner != rippleCircleColorInner) {
            rippleCircleColorInnerShader = null
            this.rippleCircleColorInner = rippleCircleColorInner
        }
    }

    //
    fun setRippleCircleColorInner(
        rippleCircleColorStartInner: Int,
        rippleCircleColorEndInner: Int
    ) {
        this.rippleCircleColorStartInner = rippleCircleColorStartInner
        this.rippleCircleColorEndInner = rippleCircleColorEndInner
        setRippleCircleColorInner(
            if (rippleCircleAngleInner == Int.MAX_VALUE) 0 else rippleCircleAngleInner,
            rippleCircleColorStartInner,
            rippleCircleColorEndInner
        )
    }

    fun setRippleCircleColorInner(
        rippleCircleAngleInner: Int,
        rippleCircleColorStartInner: Int,
        rippleCircleColorEndInner: Int
    ) {
        this.rippleCircleColorStartInner = rippleCircleColorStartInner
        this.rippleCircleColorEndInner = rippleCircleColorEndInner
        rippleCircleColorInnerShader = getLinearGradient(
            width,
            height,
            rippleCircleAngleInner,
            rippleCircleColorStartInner,
            rippleCircleColorEndInner
        )
    }

    //
    private var rippleOuterCircleRadius = 0f
    fun getRippleOuterCircleRadius(): Float {
        return rippleOuterCircleRadius
    }

    fun setRippleOuterCircleRadius(rippleOuterCircleRadius: Float) {
        this.rippleOuterCircleRadius = rippleOuterCircleRadius
    }

    // delay
    private var rippleOuterDelayCircleRadius = 0f
    fun getRippleOuterDelayCircleRadius(): Float {
        return rippleOuterDelayCircleRadius
    }

    fun setRippleOuterDelayCircleRadius(rippleOuterDelayCircleRadius: Float) {
        this.rippleOuterDelayCircleRadius = rippleOuterDelayCircleRadius
    }

    // defer
    private var rippleOuterDeferCircleRadius = 0f
    fun getRippleOuterDeferCircleRadius(): Float {
        return rippleOuterDeferCircleRadius
    }

    fun setRippleOuterDeferCircleRadius(rippleOuterDeferCircleRadius: Float) {
        this.rippleOuterDeferCircleRadius = rippleOuterDeferCircleRadius
    }

    private var rippleCircleColorOuter: Int
    fun getRippleCircleColorOuter(): Int {
        return rippleCircleColorOuter
    }

    fun setRippleCircleColorOuter(rippleCircleColorOuter: Int) {
        if (this.rippleCircleColorOuter != rippleCircleColorOuter) {
            rippleCircleColorOuterShader = null
            this.rippleCircleColorOuter = rippleCircleColorOuter
        }
    }

    fun setRippleCircleColorOuter(
        rippleCircleColorStartOuter: Int,
        rippleCircleColorEndOuter: Int
    ) {
        setRippleCircleColorOuter(
            if (rippleCircleAngleOuter == Int.MAX_VALUE) 0 else rippleCircleAngleOuter,
            rippleCircleColorStartOuter,
            rippleCircleColorEndOuter
        )
    }

    fun setRippleCircleColorOuter(
        rippleCircleAngleOuter: Int,
        rippleCircleColorStartOuter: Int,
        rippleCircleColorEndOuter: Int
    ) {
        this.rippleCircleColorStartOuter = rippleCircleColorStartOuter
        this.rippleCircleColorEndOuter = rippleCircleColorEndOuter
        rippleCircleColorOuterShader = getLinearGradient(
            width,
            height,
            rippleCircleAngleOuter,
            rippleCircleColorStartOuter,
            rippleCircleColorEndOuter
        )
    }

    var rippleText: String?
    var rippleTextSize: Float
    var rippleTextColor: Int

    //
    private var rippleTextDx: Float
    fun getRippleTextDx(): Float {
        return rippleTextDx
    }

    fun setRippleTextDx(rippleTextDx: Float) {
        setRippleTextDxRaw(rippleTextDx)
    }

    fun setRippleTextDx(rippleTextDx: Float, unit: Int) {
        setRippleTextDxRaw(convertToPX(rippleTextDx, unit))
    }

    private fun setRippleTextDxRaw(rippleTextDx: Float) {
        if (this.rippleTextDx != rippleTextDx) {
            this.rippleTextDx = rippleTextDx
        }
    }

    //
    private var rippleTextDy: Float
    fun getRippleTextDy(): Float {
        return rippleTextDy
    }

    fun setRippleTextDy(rippleTextDy: Float) {
        setRippleTextDyRaw(rippleTextDy)
    }

    fun setRippleTextDy(rippleTextDy: Float, unit: Int) {
        setRippleTextDyRaw(convertToPX(rippleTextDy, unit))
    }

    private fun setRippleTextDyRaw(rippleTextDy: Float) {
        if (this.rippleTextDy != rippleTextDy) {
            this.rippleTextDy = rippleTextDy
        }
    }

    //
    var rippleTextFractionDx: Float

    //
    var rippleTextFractionDy: Float
    var rippleDuraction: Int
    private var rippleCircleAngleInner: Int
    fun getRippleCircleAngleInner(): Int {
        return rippleCircleAngleInner
    }

    fun setRippleCircleAngleInner(rippleCircleAngleInner: Int) {
        this.rippleCircleAngleInner = rippleCircleAngleInner
        rippleCircleColorInnerShader = getLinearGradient(
            width,
            height,
            rippleCircleAngleInner,
            rippleCircleColorStartInner,
            rippleCircleColorEndInner
        )
    }

    private var rippleCircleAngleOuter: Int
    fun getRippleCircleAngleOuter(): Int {
        return rippleCircleAngleOuter
    }

    fun setRippleCircleAngleOuter(rippleCircleAngleOuter: Int) {
        this.rippleCircleAngleOuter = rippleCircleAngleOuter
        rippleCircleColorOuterShader = getLinearGradient(
            width,
            height,
            rippleCircleAngleOuter,
            rippleCircleColorStartOuter,
            rippleCircleColorEndOuter
        )
    }

    // rippleCircleColorStartInner default rippleCircleColorInner
    var rippleCircleColorStartInner = 0

    // rippleCircleColorEndInner default rippleCircleColorInner
    var rippleCircleColorEndInner = 0

    // rippleCircleColorStartOuter default rippleCircleColorOuter
    var rippleCircleColorStartOuter = 0

    // rippleCircleColorEndOuter default rippleCircleColorOuter
    var rippleCircleColorEndOuter = 0

    // rippleCircleColorInnerShader default null
    var rippleCircleColorInnerShader: Shader? = null

    // rippleCircleColorOuterShader default null
    var rippleCircleColorOuterShader: Shader? = null

    //
    var rippleTimeInterpolator: TimeInterpolator? = null

    //
    private var rippleMaxRadius = 0f
    private var rippleDeltaRadius = 0f
    private var rippleAnimationCircleRadius: Float

    //
    var rippleAnimatorSet: AnimatorSet? = null
    private val rippleAnimationCircleRadiusProperty: Property<TRipple, Float> =
        object : Property<TRipple, Float>(
            Float::class.java, "rippleAnimationCircleRadiusProperty"
        ) {
            override fun get(`object`: TRipple): Float {
                return `object`.rippleAnimationCircleRadius
            }

            override fun set(`object`: TRipple, value: Float) {
                `object`.rippleAnimationCircleRadius = value
                // We need to draw three radius
                `object`.rippleOuterCircleRadius = value
                `object`.rippleOuterDelayCircleRadius = value - rippleDeltaRadius * 0.5f
                `object`.rippleOuterDeferCircleRadius = value - rippleDeltaRadius * 1.0f
                invalidate()
            }
        }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        rippleMaxRadius = if (width > height) (height shr 1).toFloat() else (width shr 1.toFloat()
            .toInt()).toFloat()
        rippleDeltaRadius = rippleMaxRadius - rippleCircleRadiusInner
        if (rippleCircleRadiusInner >= rippleMaxRadius) {
            throw IndexOutOfBoundsException("The content attribute rippleCircleRadiusInner length must be less than half of the width or height")
        }
        rippleTextDx += width * rippleTextFractionDx
        rippleTextDy += height * rippleTextFractionDy
        if (rippleCircleAngleInner != Int.MAX_VALUE) {
            rippleCircleColorInnerShader = getLinearGradient(
                width,
                height,
                rippleCircleAngleInner,
                rippleCircleColorStartInner,
                rippleCircleColorEndInner
            )
        }
        if (rippleCircleAngleOuter != Int.MAX_VALUE) {
            rippleCircleColorOuterShader = getLinearGradient(
                width,
                height,
                rippleCircleAngleOuter,
                rippleCircleColorStartOuter,
                rippleCircleColorEndOuter
            )
        }
    }

    override fun onDraw(canvas: Canvas) {
        if (rippleCircleRadiusInner > 0) {
            // The latter part of the judge to modify the contents added to initPaint in
            canvas.drawCircle(
                (width shr 1).toFloat(), (height shr 1).toFloat(), rippleCircleRadiusInner,
                initPaint(Paint.Style.FILL, rippleCircleColorInner, rippleCircleColorInnerShader)
            )
        }

        // 255
        if (rippleOuterCircleRadius > rippleCircleRadiusInner && rippleOuterCircleRadius < rippleMaxRadius) {
            canvas.drawCircle(
                (
                        width shr 1).toFloat(), (
                        height shr 1).toFloat(),
                rippleOuterCircleRadius,
                initPaint(
                    Paint.Style.FILL,
                    rippleCircleColorOuter,
                    rippleCircleColorInnerShader,
                    ((rippleMaxRadius - rippleOuterCircleRadius)
                            / rippleDeltaRadius * 255).toInt()
                )
            )
        }
        if (rippleOuterDelayCircleRadius > rippleCircleRadiusInner && rippleOuterDelayCircleRadius < rippleMaxRadius) {
            canvas.drawCircle(
                (
                        width shr 1).toFloat(), (
                        height shr 1).toFloat(),
                rippleOuterDelayCircleRadius,
                initPaint(
                    Paint.Style.FILL,
                    rippleCircleColorOuter,
                    rippleCircleColorOuterShader,
                    ((rippleMaxRadius - rippleOuterDelayCircleRadius)
                            / rippleDeltaRadius * 255).toInt()
                )
            )
        }
        if (rippleOuterDeferCircleRadius > rippleCircleRadiusInner && rippleOuterDeferCircleRadius < rippleMaxRadius) {
            canvas.drawCircle(
                (
                        width shr 1).toFloat(), (
                        height shr 1).toFloat(),
                rippleOuterDeferCircleRadius,
                initPaint(
                    Paint.Style.FILL,
                    rippleCircleColorOuter,
                    rippleCircleColorOuterShader,
                    ((rippleMaxRadius - rippleOuterDeferCircleRadius)
                            / rippleDeltaRadius * 255).toInt()
                )
            )
        }
        if (rippleText != null) {
            drawText(
                canvas,
                rippleText,
                width.toFloat(),
                (width shr 1) + rippleTextDx,
                (height shr 1) + rippleTextDy,
                0f,
                0f,
                initTextPaint(Paint.Style.FILL, rippleTextColor, rippleTextSize, Paint.Align.CENTER)
            )
        }
    }

    fun play() {
        rippleAnimatorSet = AnimatorSet()
        val rippleOuterCircleObjectAnimator = ObjectAnimator.ofFloat(
            this, rippleAnimationCircleRadiusProperty, rippleCircleRadiusInner,
            rippleCircleRadiusInner + rippleDeltaRadius * 3
        )
        rippleOuterCircleObjectAnimator.duration = rippleDuraction.toLong()
        rippleAnimatorSet!!.playTogether(rippleOuterCircleObjectAnimator)
        if (rippleTimeInterpolator != null) {
            rippleAnimatorSet!!.interpolator = rippleTimeInterpolator
        }
        rippleAnimatorSet!!.start()
    }

    companion object {
        //
        val rippletimeinterpolatorarray = arrayOf<TimeInterpolator>(
            AccelerateDecelerateInterpolator(),
            AccelerateInterpolator(),
            AnticipateInterpolator(),
            AnticipateOvershootInterpolator(),
            BounceInterpolator(),
            CycleInterpolator(0F),
            DecelerateInterpolator(),
            LinearInterpolator(),
            OvershootInterpolator()
        )
    }

    init {
        tag = TRipple::class.java.simpleName
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.TRipple)
        rippleCircleRadiusInner =
            typedArray.getDimension(R.styleable.TRipple_rippleCircleRadiusInner, 0f)
        rippleCircleColorInner =
            typedArray.getColor(R.styleable.TRipple_rippleCircleColorInner, Color.TRANSPARENT)

        //
        rippleAnimationCircleRadius = rippleCircleRadiusInner
        rippleCircleColorOuter =
            typedArray.getColor(R.styleable.TRipple_rippleCircleColorOuter, rippleCircleColorInner)
        rippleText = typedArray.getString(R.styleable.TRipple_rippleText)
        rippleTextSize =
            typedArray.getDimension(R.styleable.TRipple_rippleTextSize, textSizeDefault)
        rippleTextColor = typedArray.getColor(R.styleable.TRipple_rippleTextColor, textColorDefault)

        //
        rippleTextDx = typedArray.getDimension(R.styleable.TRipple_rippleTextDx, 0f)
        rippleTextDy = typedArray.getDimension(R.styleable.TRipple_rippleTextDy, 0f)
        rippleTextFractionDx =
            typedArray.getFraction(R.styleable.TRipple_rippleTextFractionDx, 1, 1, 0f)
        rippleTextFractionDy =
            typedArray.getFraction(R.styleable.TRipple_rippleTextFractionDy, 1, 1, 0f)

        //
        rippleCircleAngleInner =
            typedArray.getInt(R.styleable.TRipple_rippleCircleAngleInner, Int.MAX_VALUE)
        if (rippleCircleAngleInner != Int.MAX_VALUE) {
            rippleCircleColorStartInner = typedArray.getColor(
                R.styleable.TRipple_rippleCircleColorStartInner,
                rippleCircleColorInner
            )
            rippleCircleColorEndInner = typedArray.getColor(
                R.styleable.TRipple_rippleCircleColorEndInner,
                rippleCircleColorInner
            )
        }
        rippleCircleAngleOuter =
            typedArray.getInt(R.styleable.TRipple_rippleCircleAngleOuter, rippleCircleAngleInner)
        if (rippleCircleAngleOuter != Int.MAX_VALUE) {
            rippleCircleColorStartOuter = typedArray.getColor(
                R.styleable.TRipple_rippleCircleColorStartOuter,
                rippleCircleColorStartInner
            )
            rippleCircleColorEndOuter = typedArray.getColor(
                R.styleable.TRipple_rippleCircleColorEndOuter,
                rippleCircleColorEndInner
            )
        }
        rippleDuraction = typedArray.getInt(R.styleable.TRipple_rippleAngle, 0)
        val rippleStyleIndex = typedArray.getInt(R.styleable.TRipple_rippleStyle, -1)
        if (rippleStyleIndex > -1) {
            rippleTimeInterpolator = rippletimeinterpolatorarray[rippleStyleIndex]
        }
        typedArray.recycle()
    }
}