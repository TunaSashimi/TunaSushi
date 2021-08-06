package com.tunasushi.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import kotlin.jvm.JvmOverloads
import android.view.MotionEvent

/**
 * @author TunaSashimi
 * @date 2015-10-30 16:50
 * @Copyright 2015 TunaSashimi. All rights reserved.
 * @Description
 */
class TTrack @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : TView(context, attrs, defStyle) {
    private var trackX = 0f
    private var trackY = 0f
    private var trackDistanceX = 0f
    private var trackDistanceY = 0f
    private var trackIntervalX = 0f
    private var trackIntervalY = 0f
    private var trackIntervalTime = 0
    private var trackisIntervalTime = false
    private val trackPaint: Paint
    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                trackDistanceX = event.x - trackX
                trackDistanceY = event.y - trackY
                trackIntervalX = trackDistanceX / 5
                trackIntervalY = trackDistanceY / 5
                trackIntervalTime = 5
                trackisIntervalTime = true
                invalidate()
            }
            MotionEvent.ACTION_MOVE, MotionEvent.ACTION_UP -> {
                if (trackisIntervalTime) {
                    return true
                }
                trackX = event.x
                trackY = event.y
                invalidate()
            }
            else -> {
            }
        }
        return true
    }

    public override fun onDraw(canvas: Canvas) {
        canvas.drawCircle(trackX, trackY, 80f, trackPaint)
        if (trackIntervalTime > 0) {
            trackIntervalTime--
            trackX = trackX + trackIntervalX
            trackY = trackY + trackIntervalY
            postInvalidateDelayed(20)
        } else {
            trackisIntervalTime = false
        }
    }

    init {
        tag = TTrack::class.java.simpleName
        trackPaint = Paint()
        trackPaint.color = Color.CYAN
    }
}