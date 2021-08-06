package com.tunasushi.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import kotlin.jvm.JvmOverloads
import com.tunasushi.view.TView
import com.tunasushi.view.TAnalysis

/**
 * @author TunaSashimi
 * @date 2015-10-30 16:48
 * @Copyright 2015 TunaSashimi. All rights reserved.
 * @Description
 */
class TAnalysis @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : TView(context, attrs, defStyleAttr) {
    var analysisControlX = 0
    var analysisControlY = 0
    override fun onDraw(canvas: Canvas) {
        initPaint(Paint.Style.FILL, Color.RED)
        val bezierCircleX = 150
        val bezierCircley = 150
        val deviationX = 80
        val deviationtY = 80
        initPathMoveTo((bezierCircleX - deviationX).toFloat(), bezierCircley.toFloat())
        path.quadTo(
            ((bezierCircleX - deviationX + bezierCircleX) / 2 - analysisControlX).toFloat(),
            ((bezierCircley + bezierCircley + deviationtY) / 2 + analysisControlY).toFloat(),
            bezierCircleX.toFloat(),
            (bezierCircley
                    + deviationtY).toFloat()
        )
        path.quadTo(
            ((bezierCircleX + bezierCircleX + deviationX) / 2 + analysisControlX).toFloat(),
            ((bezierCircley + deviationtY + bezierCircley) / 2 + analysisControlY).toFloat(),
            (bezierCircleX + deviationX).toFloat(),
            bezierCircley.toFloat()
        )
        path.quadTo(
            ((bezierCircleX + deviationX + bezierCircleX) / 2 + analysisControlX).toFloat(),
            ((bezierCircley + bezierCircley - deviationtY) / 2 - analysisControlY).toFloat(),
            bezierCircleX.toFloat(),
            (bezierCircley
                    - deviationtY).toFloat()
        )
        path.quadTo(
            ((bezierCircleX + bezierCircleX - deviationX) / 2 - analysisControlX).toFloat(),
            ((bezierCircley - deviationtY + bezierCircley) / 2 - analysisControlY).toFloat(),
            (bezierCircleX - deviationX).toFloat(),
            bezierCircley.toFloat()
        )
        canvas.drawPath(path, paint)
    }

    fun setAnalyaiaControlXY(analysisControlX: Int, abalysisControlY: Int) {
        this.analysisControlX = analysisControlX
        analysisControlY = abalysisControlY
        invalidate()
    }

    init {
        tag = TAnalysis::class.java.simpleName
    }
}