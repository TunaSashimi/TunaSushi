package com.tunasushi.view

import android.content.Context
import android.graphics.*
import kotlin.jvm.JvmOverloads
import com.tunasushi.R
import android.util.AttributeSet
import java.lang.IllegalArgumentException
import java.lang.IndexOutOfBoundsException

/**
 * @author TunaSashimi
 * @date 2020-02-13 13:50
 * @Copyright 2020 TunaSashimi. All rights reserved.
 * @Description
 */
class TRange @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : TView(context, attrs, defStyle) {
    private val rangeTextArray: Array<String>
    private val rangeThick: Float
    private val rangeColorNormal: Int
    private val rangeColorSelect: Int
    private val rangeDragColor: Int
    private val rangeDragStrokeWidth: Float
    private val rangeDragStrokeColor: Int
    var rangeDragIndexLeft: Int
    var rangeDragIndexRight: Int
    private var rangeDragSrcLeft: Bitmap? = null
    private var rangeDragSrcRight: Bitmap? = null

    //
    var rangeCircleCentreX = 0f

    //
    var rangeText: String? = null
        get() = if (dx > 0) {
            rangeTextArray[rangeDragIndexRight]
        } else {
            rangeTextArray[rangeDragIndexLeft]
        }
    val rangeTextLeft: String
        get() = rangeTextArray[rangeDragIndexLeft]
    val rangeTextRight: String
        get() = rangeTextArray[rangeDragIndexRight]

    fun reset() {
        rangeDragIndexLeft = 0
        rangeDragIndexRight = total - 1
        invalidate()
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        surplus = (width - height).toFloat()
        share = if (surplus <= 0) {
            throw IndexOutOfBoundsException("The content attribute width must be greater than height")
        } else {
            surplus * 1f / (total - 1)
        }
        for (i in 0 until total) {
            floatArray[i] = share * i + (height shr 1)
        }
    }

    override fun onDraw(canvas: Canvas) {
        //draw rangeColorNormal
        initPaint(Paint.Style.FILL, rangeColorNormal, rangeThick)
        canvas.drawLine(
            0f,
            (height shr 1).toFloat(),
            width.toFloat(),
            (height shr 1).toFloat(),
            paint
        )

        // draw range dragLeft
        initPaint(Paint.Style.FILL, rangeDragColor)

        // Paint.Style.FILL
        canvas.drawCircle(
            floatArray[rangeDragIndexLeft],
            (height shr 1).toFloat(),
            (height shr 1) - rangeDragStrokeWidth,
            paint
        )
        if (rangeDragStrokeWidth > 0) {
            initPaint(Paint.Style.STROKE, rangeDragStrokeColor, rangeDragStrokeWidth)
            //Paint.Style.STROKE is Diverge to both sides
            canvas.drawCircle(
                floatArray[rangeDragIndexLeft],
                (height shr 1).toFloat(),
                (height shr 1) - rangeDragStrokeWidth / 2,
                paint
            )
        }

        // draw range dragRight
        initPaint(Paint.Style.FILL, rangeDragColor)
        canvas.drawCircle(
            floatArray[rangeDragIndexRight],
            (height shr 1).toFloat(),
            (height shr 1) - rangeDragStrokeWidth,
            paint
        )
        if (rangeDragStrokeWidth > 0) {
            initPaint(Paint.Style.STROKE, rangeDragStrokeColor, rangeDragStrokeWidth)
            canvas.drawCircle(
                floatArray[rangeDragIndexRight],
                (height shr 1).toFloat(),
                (height shr 1) - rangeDragStrokeWidth / 2,
                paint
            )
        }

        //draw rangeColorSelect
        initPaint(Paint.Style.FILL, rangeColorSelect, rangeThick)
        canvas.drawLine(
            floatArray[rangeDragIndexLeft] + (height shr 1), (height shr 1).toFloat(),
            floatArray[rangeDragIndexRight] - (height shr 1), (height shr 1).toFloat(), paint
        )
    }

    override fun setTouchXYRaw(touchX: Float, touchY: Float) {
        x = touchX
        var distenceMin = width.toFloat()
        dx =
            if (Math.abs(x - floatArray[rangeDragIndexLeft]) <= Math.abs(x - floatArray[rangeDragIndexRight])) {
                -1f
            } else {
                1f
            }
        for (i in 0 until total) {
            val circlecentreDx = Math.abs(x - floatArray[i])
            if (circlecentreDx < distenceMin) {
                if (dx > 0) {
                    rangeDragIndexRight = i
                    rangeCircleCentreX = floatArray[rangeDragIndexRight]
                } else {
                    rangeDragIndexLeft = i
                    rangeCircleCentreX = floatArray[rangeDragIndexLeft]
                }
                distenceMin = circlecentreDx
            } else {
                break
            }
        }
        invalidate()
    }

    init {
        tag = TRange::class.java.simpleName
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.TRange)
        val rangeValueArrayId = typedArray.getResourceId(R.styleable.TRange_rangeValueArray, -1)
        if (rangeValueArrayId != -1) {
            rangeTextArray = typedArray.resources.getStringArray(rangeValueArrayId)
            total = rangeTextArray.size
            floatArray = if (total < 2) {
                throw IndexOutOfBoundsException("The content attribute rangeTextArray length must be at least 2")
            } else {
                FloatArray(total)
            }
        } else {
            throw IllegalArgumentException("The content attribute require a property named rangeTextArray")
        }
        rangeThick = typedArray.getDimension(R.styleable.TRange_rangeThick, 0f)
        require(rangeThick > 0) { "The content attribute rangeThick must be greater than 0 " }
        rangeColorNormal =
            typedArray.getColor(R.styleable.TRange_rangeColorNormal, Color.TRANSPARENT)
        rangeColorSelect =
            typedArray.getColor(R.styleable.TRange_rangeColorSelect, rangeColorNormal)
        rangeDragColor = typedArray.getColor(R.styleable.TRange_rangeDragColor, Color.TRANSPARENT)
        rangeDragStrokeWidth = typedArray.getDimension(R.styleable.TRange_rangeDragStrokeWidth, 0f)
        rangeDragStrokeColor =
            typedArray.getColor(R.styleable.TRange_rangeDragStrokeColor, Color.TRANSPARENT)

        //0
        rangeDragIndexLeft = typedArray.getInt(R.styleable.TRange_rangeDragIndexLeft, 0)
        if (rangeDragIndexLeft < 0 || rangeDragIndexLeft > total - 1) {
            throw IndexOutOfBoundsException("The content attribute rangeDragIndexLeft must be no less than 0 and no greater than rangeTextArray length")
        }

        //rangeTextArray.length - 1
        rangeDragIndexRight = typedArray.getInt(R.styleable.TRange_rangeDragIndexRight, total - 1)
        if (rangeDragIndexRight < 0 || rangeDragIndexRight > total - 1) {
            throw IndexOutOfBoundsException("The content attribute rangeDragIndexRight must be no less than 0 and no greater than rangeTextArray length")
        }

        //
        val rangeDragSrcLeftId = typedArray.getResourceId(R.styleable.TRange_rangeDragSrcLeft, -1)
        if (rangeDragSrcLeftId != -1) {
            rangeDragSrcLeft = BitmapFactory.decodeResource(resources, rangeDragSrcLeftId)
        }
        val rangeDragSrcRightId =
            typedArray.getResourceId(R.styleable.TRange_rangeDragSrcRight, rangeDragSrcLeftId)
        if (rangeDragSrcRightId != -1) {
            rangeDragSrcRight = BitmapFactory.decodeResource(resources, rangeDragSrcRightId)
        }
        typedArray.recycle()
    }
}