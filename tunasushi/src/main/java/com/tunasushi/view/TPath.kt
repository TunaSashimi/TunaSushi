package com.tunasushi.view

import android.content.Context
import android.graphics.*
import kotlin.jvm.JvmOverloads
import android.util.AttributeSet

/**
 * @author TunaSashimi
 * @date 2020-02-12 20:02
 * @Copyright 2020 TunaSashimi. All rights reserved.
 * @Description
 */
class TPath @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : TView(context, attrs, defStyle) {
    private val mPaint: Paint
    private var mX = 0f
    private val mPos: FloatArray
    private val mPath: Path
    private val mPathPaint: Paint
    private fun buildTextPositions(text: String, y: Float, paint: Paint): FloatArray {
        val widths = FloatArray(text.length)
        // initially get the widths for each char
        val n = paint.getTextWidths(text, widths)
        // now popuplate the array, interleaving spaces for the Y values
        val pos = FloatArray(n * 2)
        var accumulatedX = 0f
        for (i in 0 until n) {
            pos[i * 2 + 0] = accumulatedX
            pos[i * 2 + 1] = y
            accumulatedX += widths[i]
        }
        return pos
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawColor(Color.WHITE)
        val p = mPaint
        val x = mX
        val y = 0f
        val pos = mPos

        // draw the normal strings
        p.color = -0x7f010000
        canvas.drawLine(x, y, x, y + DY * 3, p)
        p.color = Color.BLACK
        canvas.translate(0f, DY.toFloat())
        p.textAlign = Paint.Align.LEFT
        canvas.drawText(TEXT_L, x, y, p)
        canvas.translate(0f, DY.toFloat())
        p.textAlign = Paint.Align.CENTER
        canvas.drawText(TEXT_C, x, y, p)
        canvas.translate(0f, DY.toFloat())
        p.textAlign = Paint.Align.RIGHT
        canvas.drawText(TEXT_R, x, y, p)
        canvas.translate(100f, (DY * 2).toFloat())

        // now draw the positioned strings
        p.color = -0x44ff0100
        for (i in 0 until pos.size / 2) {
            canvas.drawLine(
                pos[i * 2 + 0],
                pos[i * 2 + 1] - DY,
                pos[i * 2 + 0],
                pos[i * 2 + 1] + DY * 2,
                p
            )
        }
        p.color = Color.BLACK
        p.textAlign = Paint.Align.LEFT
        canvas.drawPosText(POSTEXT, pos, p)
        canvas.translate(0f, DY.toFloat())
        p.textAlign = Paint.Align.CENTER
        canvas.drawPosText(POSTEXT, pos, p)
        canvas.translate(0f, DY.toFloat())
        p.textAlign = Paint.Align.RIGHT
        canvas.drawPosText(POSTEXT, pos, p)

        // now draw the text on path
        canvas.translate(-100f, (DY * 2).toFloat())
        canvas.drawPath(mPath, mPathPaint)
        p.textAlign = Paint.Align.LEFT
        canvas.drawTextOnPath(TEXTONPATH, mPath, 0f, 0f, p)
        canvas.translate(0f, DY * 1.5f)
        canvas.drawPath(mPath, mPathPaint)
        p.textAlign = Paint.Align.CENTER
        canvas.drawTextOnPath(TEXTONPATH, mPath, 0f, 0f, p)
        canvas.translate(0f, DY * 1.5f)
        canvas.drawPath(mPath, mPathPaint)
        p.textAlign = Paint.Align.RIGHT
        canvas.drawTextOnPath(TEXTONPATH, mPath, 0f, 0f, p)
    }

    override fun onSizeChanged(w: Int, h: Int, ow: Int, oh: Int) {
        super.onSizeChanged(w, h, ow, oh)
        mX = w * 0.5f // remember the center of the screen
    }

    companion object {
        private const val DY = 30
        private const val TEXT_L = "Left"
        private const val TEXT_C = "Center"
        private const val TEXT_R = "Right"
        private const val POSTEXT = "Positioned"
        private const val TEXTONPATH = "Along a path"
        private fun makePath(p: Path) {
            p.moveTo(10f, 0f)
            p.cubicTo(100f, -50f, 200f, 50f, 300f, 0f)
        }
    }

    init {
        tag = TPath::class.java.simpleName
        isFocusable = true
        mPaint = Paint()
        mPaint.isAntiAlias = true
        mPaint.textSize = 30f
        mPaint.typeface = Typeface.SERIF
        mPos = buildTextPositions(POSTEXT, 0f, mPaint)
        mPath = Path()
        makePath(mPath)
        mPathPaint = Paint()
        mPathPaint.isAntiAlias = true
        mPathPaint.color = -0x7fffff01
        mPathPaint.style = Paint.Style.STROKE
    }
}