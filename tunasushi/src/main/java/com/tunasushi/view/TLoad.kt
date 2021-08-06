package com.tunasushi.view

import android.content.Context
import kotlin.jvm.JvmOverloads
import android.os.CountDownTimer
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import com.tunasushi.R

/**
 * @author TunaSashimi
 * @date 2020-03-05 13:59
 * @Copyright 2020 TunaSashimi. All rights reserved.
 * @Description
 */
class TLoad @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : TView(context, attrs, defStyle) {
    private val loadTime: Int
    private val loadColorOut: Int
    private val loadColorIn: Int
    private val loadThickOut: Float
    private val loadThickIn: Float
    private val loadAngleOut = 300f
    private val loadAngleIn = 60f
    private val loadAngleStart: Float
    private var loadAngleCurrent = 0f

    //Execute every 10 ms
    private var loadCountDownTimer: CountDownTimer? = null
    override fun onDraw(canvas: Canvas) {
        initPaint(Paint.Style.STROKE, loadColorOut, loadThickOut)
        paint.strokeJoin = Paint.Join.ROUND
        paint.strokeCap = Paint.Cap.ROUND
        canvas.drawPath(
            initPathArc(
                10f,
                10f,
                (width - 10).toFloat(),
                (height - 10).toFloat(),
                loadAngleCurrent,
                loadAngleOut
            ), paint
        )
        initPaint(Paint.Style.STROKE, loadColorIn, loadThickIn)
        paint.strokeJoin = Paint.Join.ROUND
        paint.strokeCap = Paint.Cap.ROUND
        canvas.drawArc(
            30 + loadThickOut,
            30 + loadThickOut,
            width - (30 + loadThickOut),
            height - (30 + loadThickOut),
            360 - loadAngleCurrent,
            -loadAngleIn,
            false,
            paint
        )
    }

    fun finish() {
        if (loadCountDownTimer != null) {
            loadCountDownTimer!!.onFinish()
            loadCountDownTimer!!.cancel()
            loadCountDownTimer = null
        }
    }

    init {
        tag = TLoad::class.java.simpleName
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.TLoad)
        loadTime = typedArray.getInt(R.styleable.TLoad_loadTime, 1500)
        loadColorOut = typedArray.getColor(R.styleable.TLoad_loadColorOut, Color.BLACK)
        loadColorIn = typedArray.getColor(R.styleable.TLoad_loadColorIn, Color.BLACK)
        loadThickOut = typedArray.getDimension(R.styleable.TLoad_loadThickOut, 15f)
        loadThickIn = typedArray.getDimension(R.styleable.TLoad_loadThickIn, 15f)
        loadAngleStart = typedArray.getDimension(R.styleable.TLoad_loadThickIn, 105f)
        if (loadCountDownTimer != null) {
            loadCountDownTimer!!.cancel()
            loadCountDownTimer!!.onFinish()
            loadCountDownTimer = null
        }
        typedArray.recycle()

        //
        loadCountDownTimer = object : CountDownTimer(loadTime.toLong(), 10) {
            override fun onTick(millisUntilFinished: Long) {
                val radio = millisUntilFinished.toFloat() / loadTime.toFloat()
                val addAngle = 360 - 360 * radio
                loadAngleCurrent = loadAngleStart
                loadAngleCurrent = loadAngleStart + addAngle
                invalidate()
            }

            override fun onFinish() {
                if (loadCountDownTimer != null) {
                    loadCountDownTimer!!.start()
                }
            }
        }
        loadCountDownTimer!!.start()
    }
}