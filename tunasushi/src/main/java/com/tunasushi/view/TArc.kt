package com.tunasushi.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import kotlin.jvm.JvmOverloads
import com.tunasushi.view.TView
import android.graphics.RectF
import android.util.AttributeSet
import com.tunasushi.view.TArc

/**
 * @author TunaSashimi
 * @date 2020-02-12 19:58
 * @Copyright 2020 TunaSashimi. All rights reserved.
 * @Description
 */
class TArc @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : TView(context, attrs, defStyleAttr) {
    private val mPaints: Array<Paint?>
    private val mFramePaint: Paint
    private val mUseCenters: BooleanArray
    private val mOvals: Array<RectF?>
    private val mBigOval: RectF
    private var mStart = 0f
    private var mSweep = 0f
    private var mBigIndex = 0
    private fun drawArcs(canvas: Canvas, oval: RectF?, useCenter: Boolean, paint: Paint?) {
        canvas.drawRect(oval!!, mFramePaint)
        canvas.drawArc(oval, mStart, mSweep, useCenter, paint!!)
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawColor(Color.WHITE)
        drawArcs(canvas, mBigOval, mUseCenters[mBigIndex], mPaints[mBigIndex])
        for (i in 0..3) {
            drawArcs(canvas, mOvals[i], mUseCenters[i], mPaints[i])
        }
        mSweep += SWEEP_INC
        if (mSweep > 360) {
            mSweep -= 360f
            mStart += START_INC
            if (mStart >= 360) {
                mStart -= 360f
            }
            mBigIndex = (mBigIndex + 1) % mOvals.size
        }
        invalidate()
    }

    companion object {
        private const val SWEEP_INC = 2f
        private const val START_INC = 15f
    }

    init {
        tag = TArc::class.java.simpleName
        mPaints = arrayOfNulls(4)
        mUseCenters = BooleanArray(4)
        mOvals = arrayOfNulls(4)
        mPaints[0] = Paint()
        mPaints[0]!!.isAntiAlias = true
        mPaints[0]!!.style = Paint.Style.FILL
        mPaints[0]!!.color = -0x77010000
        mUseCenters[0] = false
        mPaints[1] = Paint(mPaints[0])
        mPaints[1]!!.color = -0x77ff0100
        mUseCenters[1] = true
        mPaints[2] = Paint(mPaints[0])
        mPaints[2]!!.style = Paint.Style.STROKE
        mPaints[2]!!.strokeWidth = 4f
        mPaints[2]!!.color = -0x77ffff01
        mUseCenters[2] = false
        mPaints[3] = Paint(mPaints[2])
        mPaints[3]!!.color = -0x77777778
        mUseCenters[3] = true
        mBigOval = RectF(40f, 10f, 280f, 250f)
        mOvals[0] = RectF(10f, 270f, 70f, 330f)
        mOvals[1] = RectF(90f, 270f, 150f, 330f)
        mOvals[2] = RectF(170f, 270f, 230f, 330f)
        mOvals[3] = RectF(250f, 270f, 310f, 330f)
        mFramePaint = Paint()
        mFramePaint.isAntiAlias = true
        mFramePaint.style = Paint.Style.STROKE
        mFramePaint.strokeWidth = 0f
    }
}