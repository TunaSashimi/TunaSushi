package com.tunasushi.view

import android.content.Context
import android.graphics.*
import kotlin.jvm.JvmOverloads
import android.util.AttributeSet

/**
 * @author TunaSashimi
 * @date 2020-02-12 20:06
 * @Copyright 2020 TunaSashimi. All rights reserved.
 * @Description
 */
class TPattern @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : TView(context, attrs, defStyle) {
    private val mShader1: Shader
    private val mShader2: Shader
    private val mPaint: Paint
    private var mTouchCurrentX = 0F
    private var mTouchCurrentY = 0F
    private var mDF: DrawFilter? = null
    override fun onDraw(canvas: Canvas) {
        canvas.drawFilter = mDF
        mPaint.shader = mShader1
        canvas.drawPaint(mPaint)
        canvas.translate(mTouchCurrentX, mTouchCurrentY)
        mPaint.shader = mShader2
        canvas.drawPaint(mPaint)
    }

    override fun setTouchXY(touchX: Float, touchY: Float) {
        if (press) {
            mTouchCurrentX = touchX
            mTouchCurrentY = touchY
        } else if (touchUp) {
            mDF = null
        }
        invalidate()
    }

    companion object {
        private fun makeBitmap1(): Bitmap {
            val bm = Bitmap.createBitmap(40, 40, Bitmap.Config.RGB_565)
            val c = Canvas(bm)
            c.drawColor(Color.RED)
            val p = Paint()
            p.color = Color.BLUE
            c.drawRect(5F, 5F, 35F, 35F, p)
            return bm
        }

        private fun makeBitmap2(): Bitmap {
            val bm = Bitmap.createBitmap(64, 64, Bitmap.Config.ARGB_8888)
            val c = Canvas(bm)
            val p = Paint(Paint.ANTI_ALIAS_FLAG)
            p.color = Color.GREEN
            p.alpha = 0xCC
            c.drawCircle(32F, 32F, 27F, p)
            return bm
        }
    }

    init {
        tag = TPattern::class.java.simpleName
        isFocusable = true
        isFocusableInTouchMode = true
        mShader1 = BitmapShader(makeBitmap1(), Shader.TileMode.REPEAT, Shader.TileMode.REPEAT)
        mShader2 = BitmapShader(makeBitmap2(), Shader.TileMode.REPEAT, Shader.TileMode.REPEAT)
        val m = Matrix()
        m.setRotate(30F)
        mShader2.setLocalMatrix(m)
        mPaint = Paint(Paint.FILTER_BITMAP_FLAG)
    }
}