package com.tunasushi.view

import android.content.Context
import com.tunasushi.tool.BitmapTool.Companion.getCircleBitmap
import com.tunasushi.tool.BitmapTool.Companion.getSVGBitmap
import kotlin.jvm.JvmOverloads
import android.graphics.Bitmap
import com.tunasushi.R
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Matrix
import android.util.AttributeSet
import androidx.annotation.IntDef
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 * @author TunaSashimi
 * @date 2015-10-30 16:52
 * @Copyright 2015 TunaSashimi. All rights reserved.
 * @Description
 */
class TSVG @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : TView(context, attrs, defStyle) {
    //
    var sVGSrc: Bitmap? = null

    //
    var sVGMatrix: Matrix? = null
    protected fun initSVGMatrix(sx: Float, sy: Float): Matrix {
        if (sVGMatrix == null) {
            sVGMatrix = Matrix()
        }
        sVGMatrix!!.reset()
        sVGMatrix!!.setScale(sx, sy)
        return sVGMatrix!!
    }

    @IntDef(CIRCLE, STAR, HEART, FLOWER, PENTAGON, SIXTEENEDGE, FORTYEDGE, SNAIL)
    @Retention(
        RetentionPolicy.SOURCE
    )
    annotation class SVGStyle

    @SVGStyle
    private var svgStyle = 0
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        //
        val specSizeWidth = MeasureSpec.getSize(widthMeasureSpec)
        val specModeHeight = MeasureSpec.getMode(heightMeasureSpec)
        val specSizeHeight = MeasureSpec.getSize(heightMeasureSpec)
        var measuredHeight = specSizeWidth
        if (specModeHeight == MeasureSpec.AT_MOST) { //wrap_content
            measuredHeight = specSizeWidth
        } else if (specModeHeight == MeasureSpec.EXACTLY) { // match_parent
            measuredHeight = specSizeHeight
        } else if (specModeHeight == MeasureSpec.UNSPECIFIED) { // unspecified
            measuredHeight = specSizeWidth
        }
        setMeasuredDimension(specSizeWidth, measuredHeight)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        scaleSx = width * 1F / srcBitmap.width
        scaleSy = height * 1F / srcBitmap.height
        matrixNormal = initMatrix(matrixNormal, scaleSx, scaleSy)
        val shortSide = if (width >= height) height else width
        initSVGMatrix(width * 1F / shortSide, height * 1F / shortSide)
        when (svgStyle) {
            CIRCLE -> sVGSrc = getCircleBitmap(shortSide)
            STAR -> sVGSrc = getSVGBitmap(context, shortSide, shortSide, R.raw.svg_star)
            HEART -> sVGSrc = getSVGBitmap(context, shortSide, shortSide, R.raw.svg_heart)
            FLOWER -> sVGSrc = getSVGBitmap(context, shortSide, shortSide, R.raw.svg_flower)
            PENTAGON -> sVGSrc = getSVGBitmap(context, shortSide, shortSide, R.raw.svg_pentagon)
            SIXTEENEDGE -> sVGSrc =
                getSVGBitmap(context, shortSide, shortSide, R.raw.svg_sixteenedge)
            FORTYEDGE -> sVGSrc = getSVGBitmap(context, shortSide, shortSide, R.raw.svg_fortyedge)
            SNAIL -> sVGSrc = getSVGBitmap(context, shortSide, shortSide, R.raw.svg_snail)
        }
    }

    override fun onDraw(canvas: Canvas) {
        canvas.saveLayer(0f, 0f, width.toFloat(), height.toFloat(), null, Canvas.ALL_SAVE_FLAG)
        //
        canvas.drawBitmap(sVGSrc!!, sVGMatrix!!, initPaint())
        //
        paint.xfermode = porterDuffXferStyle
        canvas.drawBitmap(srcBitmap, matrixNormal, paint)
        paint.xfermode = null
    }

    companion object {
        const val CIRCLE = 0
        const val STAR = 1
        const val HEART = 2
        const val FLOWER = 3
        const val PENTAGON = 4
        const val SIXTEENEDGE = 5
        const val FORTYEDGE = 6
        const val SNAIL = 7
        private val SVGStyleArray = intArrayOf(
            CIRCLE,
            STAR,
            HEART,
            FLOWER,
            PENTAGON,
            SIXTEENEDGE,
            FORTYEDGE,
            SNAIL
        )
    }

    init {
        tag = TSVG::class.java.simpleName

        //
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.TSVG)
        val SVGSrcId = typedArray.getResourceId(R.styleable.TSVG_SVGSrc, -1)
        if (SVGSrcId != -1) {
            srcBitmap = BitmapFactory.decodeResource(resources, SVGSrcId)
        }
        val SVGStyleIndex = typedArray.getInt(R.styleable.TSVG_SVGStyle, 0)
        if (SVGStyleIndex >= 0) {
            svgStyle = SVGStyleArray[SVGStyleIndex]
        }
        typedArray.recycle()
    }
}