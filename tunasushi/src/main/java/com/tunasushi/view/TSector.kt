package com.tunasushi.view

import android.animation.Animator
import kotlin.jvm.JvmOverloads
import com.tunasushi.view.TView
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.animation.Animator.AnimatorListener
import android.animation.ValueAnimator
import android.content.Context
import com.tunasushi.view.TSector
import android.content.res.TypedArray
import android.graphics.*
import android.os.Handler
import android.os.Message
import android.util.AttributeSet
import com.tunasushi.R

/**
 * @author TunaSashimi
 * @date 2020-02-12 18:03
 * @Copyright 2020 TunaSashimi. All rights reserved.
 * @Description
 */
class TSector @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : TView(context, attrs, defStyleAttr) {
    private val sectorPaint //画笔
            : Paint
    private val sectorPaintShader //彩色画笔
            : Paint
    private val sectorPaintBitmap //图片画笔
            : Paint

    //半径
    private val radius = 0

    //外圆弧的宽度
    private val sectorArcWidthOut: Float

    //内部大圆弧的宽度
    private val sectorArcWidthIn: Float

    //两圆弧中间间隔距离
    private val sectorSpaceWidth: Float
    private val sectorDragRadius: Float
    private val yellowColor: Int
    private val pinkRedColor: Int
    private val redColor: Int
    private val deepRedColor: Int
    private val grayColor: Int

    //间隔的角度
    private val spaceAngle = 22.5

    //两条圆弧的起始角度，起始角度控制半圆开口的大小，数值越小开口越大，数值越大开口越小
    private val floatAngel = 50.0

    //时刻变化的Angel
    private var mAngel = 0.0

    //内弧半径
    private var insideArcRadius = 0f
    private var aimPercent = 0.0
    private val pos // 当前点的实际位置
            : FloatArray
    private val tan // 当前点的tangent值,用于计算图片所需旋转的角度
            : FloatArray
    private var insideArea //内圆的矩形
            : RectF? = null

    // 动效过程监听器
    private var mUpdateListener: AnimatorUpdateListener? = null
    private var mAnimatorListener: AnimatorListener? = null

    // 过程动画
    private var mValueAnimator: ValueAnimator? = null

    // 用于控制动画状态转换
    private var mAnimatorHandler: Handler? = null

    // 默认的动效周期 2s
    private val defaultDuration = 0
    override fun onDraw(canvas: Canvas) {
        radius = (height shr 1).toFloat()
        insideArcRadius = radius - sectorDragRadius - sectorSpaceWidth //内弧半径

        //画线的底色
        sectorPaint.color = grayColor
        sectorPaint.strokeWidth = sectorArcWidthOut //sectorArcWidthOut
        sectorPaint.style = Paint.Style.STROKE
        sectorPaint.strokeCap = Paint.Cap.ROUND //设置为圆角
        sectorPaint.isAntiAlias = true

        //绘制里层大宽度弧形底色
        sectorPaint.strokeWidth = sectorArcWidthIn
        sectorPaint.style = Paint.Style.STROKE
        canvas.drawArc(
            RectF(
                (width shr 1) - insideArcRadius,
                radius - insideArcRadius,
                (width shr 1) + insideArcRadius,
                radius + insideArcRadius
            ),
            (180 - floatAngel).toFloat(),
            (180 + 2 * floatAngel).toFloat(), false, sectorPaint
        )
        /***
         * 4个色值由浅到深分别是 ffd200 ff5656 fa4040 f60157
         * 等级划分：0-20%    21-60%    61-90%    90以上
         * 绘制颜色线条
         * 主要用到Xfermode的SRC_ATOP显示上层绘制
         * setStrokeCap   Paint.Cap.ROUND设置为圆角矩形
         */
        val roateAngel = mAngel * 0.01 * 225
        sectorPaintShader.color = yellowColor
        sectorPaintShader.strokeCap = Paint.Cap.ROUND
        sectorPaintShader.isAntiAlias = true
        sectorPaintShader.xfermode =
            PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP) //sectorPaintShader.setColor(yellowColor);
        if (aimPercent >= 0 && aimPercent <= 20) {
        } else if (aimPercent > 20 && aimPercent <= 60) {
            val colorSweep = intArrayOf(yellowColor, pinkRedColor)
            val position = floatArrayOf(0.5f, 0.7f)
            val sweepGradient = SweepGradient(width / 2*1f, radius, colorSweep, position)
            sectorPaintShader.shader = sweepGradient
        } else if (aimPercent > 60 && aimPercent <= 90) {
            val colorSweep = intArrayOf(redColor, yellowColor, yellowColor, pinkRedColor, redColor)
            val position = floatArrayOf(0.25f, 0.35f, 0.5f, 0.7f, 0.8f)
            val sweepGradient = SweepGradient(width / 2*1f, radius, colorSweep, position)
            sectorPaintShader.shader = sweepGradient
        } else if (aimPercent > 90) {
            val colorSweep = intArrayOf(
                deepRedColor,
                yellowColor,
                yellowColor,
                pinkRedColor,
                redColor,
                deepRedColor
            )
            val position = floatArrayOf(0.2f, 0.4f, 0.5f, 0.7f, 0.9f, 1.0f)
            val sweepGradient = SweepGradient(width / 2*1f, radius, colorSweep, position)
            sectorPaintShader.shader = sweepGradient
        }
        if (aimPercent <= 10) { //目的是为了
            drawInsideArc(
                (180 - floatAngel).toFloat(),
                roateAngel.toFloat(),
                canvas,
                yellowColor
            ) //在这里改变指示圆点的颜色
        } else if (aimPercent > 10 && aimPercent <= 20) {
            drawInsideArc(
                (180 - floatAngel).toFloat(),
                roateAngel.toFloat(),
                canvas,
                yellowColor
            ) //在这里改变指示圆点的颜色
        } else if (aimPercent > 20 && aimPercent <= 60) {
            drawInsideArc(
                (180 - floatAngel).toFloat(),
                (roateAngel - (spaceAngle - floatAngel)).toFloat(),
                canvas,
                pinkRedColor
            )
        } else if (aimPercent > 60 && aimPercent <= 90) {
            drawInsideArc(
                (180 - floatAngel).toFloat(),
                (roateAngel - (spaceAngle - floatAngel)).toFloat(),
                canvas,
                redColor
            )
        } else {
            drawInsideArc(
                (180 - floatAngel).toFloat(),
                (roateAngel - 2 * (spaceAngle - floatAngel)).toFloat(),
                canvas,
                deepRedColor
            )
        }
    }

    /***
     * 画内部圆环渐变
     * @param formDegree 起始角度
     * @param toDegree 旋转角度
     * @param canvas 画布
     */
    private fun drawInsideArc(formDegree: Float, toDegree: Float, canvas: Canvas, color: Int) {
        sectorPaintShader.strokeWidth = sectorArcWidthIn
        sectorPaintShader.style = Paint.Style.STROKE
        //内弧半径
        insideArea = RectF(
            (width shr 1) - insideArcRadius,
            radius - insideArcRadius,
            (width shr 1) + insideArcRadius,
            radius + insideArcRadius
        )
        canvas.drawArc(insideArea!!, formDegree, toDegree, false, sectorPaintShader)
        if (toDegree != 0f) {
            val orbit = Path()
            //通过Path类画一个90度（180—270）的内切圆弧路径
            orbit.addArc(insideArea!!, formDegree, toDegree)
            // 创建 PathMeasure
            val measure = PathMeasure(orbit, false)
            measure.getPosTan(measure.length * 1, pos, tan)
            canvas.drawPath(orbit, sectorPaintShader) //绘制外层的线条
            sectorPaintBitmap.color = color
            //绘制实心小圆圈
            canvas.drawCircle(pos[0], pos[1], 30f, sectorPaintBitmap)
        }
    }

    //设置角度变化，刷新界面
    fun setProgress(aimPercent: Double) {
        //
        var aimPercent = aimPercent
        if (aimPercent < 1) {
            aimPercent = 1.0
        } else if (aimPercent > 99) {
            aimPercent = 100.0
        }
        //
        this.aimPercent = aimPercent

        //
        val finalAimPercent = aimPercent
        mUpdateListener = AnimatorUpdateListener { animation ->
            mAngel = animation.animatedValue as Float * finalAimPercent
            if (mAngel >= 0 && mAngel <= 20) {
                sectorPaintBitmap.color = yellowColor
            } else if (mAngel > 20 && mAngel <= 60) {
                sectorPaintBitmap.color = pinkRedColor
            } else if (mAngel > 60 && mAngel <= 90) {
                sectorPaintBitmap.color = redColor
            } else {
                sectorPaintBitmap.color = deepRedColor
            }
            invalidate()
        }
        mAnimatorListener = object : AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}
            override fun onAnimationEnd(animation: Animator) {
                mAnimatorHandler!!.sendEmptyMessage(0)
            }

            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        }

        //
        mAnimatorHandler = object : Handler() {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                when (msg.what) {
                    0 -> {
                        mValueAnimator!!.removeAllUpdateListeners()
                        mValueAnimator!!.removeAllListeners()
                    }
                    1 -> invalidate()
                }
            }
        }

        //
        mValueAnimator = ValueAnimator.ofFloat(0f, 1f)
            .setDuration(defaultDuration.toLong()) //设置动画时间，这里设置为0，则看不出动画执行的过程
        mValueAnimator!!.addUpdateListener(mUpdateListener)
        mValueAnimator!!.addListener(mAnimatorListener)
        mValueAnimator!!.start()
    }

    init {
        tag = TSector::class.java.simpleName
        pos = FloatArray(2)
        tan = FloatArray(2)
        sectorPaint = Paint()
        sectorPaint.style = Paint.Style.STROKE
        sectorPaint.isAntiAlias = true
        sectorPaintShader = Paint()
        sectorPaintBitmap = Paint()
        sectorPaintBitmap.style = Paint.Style.FILL
        sectorPaintBitmap.isAntiAlias = true
        yellowColor = -0xb5338b
        pinkRedColor = -0xca6830
        redColor = -0xca6830
        deepRedColor = -0xca6830
        grayColor = -0xf0f10
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.TSector)
        sectorArcWidthOut = typedArray.getDimension(R.styleable.TSector_sectorArcWidthOut, 0f)
        sectorArcWidthIn = typedArray.getDimension(R.styleable.TSector_sectorArcWidthIn, 0f)
        sectorSpaceWidth = typedArray.getDimension(R.styleable.TSector_sectorSpaceWidth, 0f)
        sectorDragRadius = typedArray.getDimension(R.styleable.TSector_sectorDragRadius, 0f)
        typedArray.recycle()
    }
}