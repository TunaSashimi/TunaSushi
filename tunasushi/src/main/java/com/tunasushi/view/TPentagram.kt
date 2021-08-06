package com.tunasushi.view

import android.content.Context
import kotlin.jvm.JvmOverloads
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import com.tunasushi.R

/**
 * @author TunaSashimi
 * @date 2020-02-12 20:15
 * @Copyright 2020 TunaSashimi. All rights reserved.
 * @Description
 */
class TPentagram @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : TView(context, attrs, defStyle) {
    private val pentagramThick: Float
    private val pentagramColor: Int
    override fun onDraw(canvas: Canvas) {
        val paint = Paint()
        val path = Path()
        paint.isAntiAlias = true
        val radian = Math.toRadians(DEGREE.toDouble())
            .toFloat() //36
        val radius_in =
            (pentagramThick * Math.sin((radian / 2).toDouble()) / Math.cos(radian.toDouble())).toFloat() //Radius of inner pentagon
        path.moveTo(
            (pentagramThick * Math.cos((radian / 2).toDouble())).toFloat(),
            0f
        ) //Top point of the pentagram
        path.lineTo(
            (pentagramThick * Math.cos((radian / 2).toDouble()) + radius_in * Math.sin(
                radian.toDouble()
            )).toFloat(),
            (pentagramThick - pentagramThick * Math.sin((radian / 2).toDouble())).toFloat()
        ) //Right of the pentagram
        path.lineTo(
            (pentagramThick * Math.cos((radian / 2).toDouble()) * 2).toFloat(),
            (pentagramThick - pentagramThick * Math.sin((radian / 2).toDouble())).toFloat()
        ) //The far right of the pentagram
        path.lineTo(
            (pentagramThick * Math.cos((radian / 2).toDouble()) + radius_in * Math.cos((radian / 2).toDouble())).toFloat(),
            (pentagramThick + radius_in * Math.sin((radian / 2).toDouble())).toFloat()
        ) //The far right of the pentagram
        path.lineTo(
            (pentagramThick * Math.cos((radian / 2).toDouble()) + pentagramThick * Math.sin(
                radian.toDouble()
            )).toFloat(), (pentagramThick + pentagramThick * Math.cos(radian.toDouble())).toFloat()
        ) //Bottom right of pentagram
        path.lineTo(
            (pentagramThick * Math.cos((radian / 2).toDouble())).toFloat(),
            pentagramThick + radius_in
        )
        path.lineTo(
            (pentagramThick * Math.cos((radian / 2).toDouble()) - pentagramThick * Math.sin(
                radian.toDouble()
            )).toFloat(), (pentagramThick + pentagramThick * Math.cos(radian.toDouble())).toFloat()
        )
        path.lineTo(
            (pentagramThick * Math.cos((radian / 2).toDouble()) - radius_in * Math.cos((radian / 2).toDouble())).toFloat(),
            (pentagramThick + radius_in * Math.sin((radian / 2).toDouble())).toFloat()
        )
        path.lineTo(
            0f,
            (pentagramThick - pentagramThick * Math.sin((radian / 2).toDouble())).toFloat()
        )
        path.lineTo(
            (pentagramThick * Math.cos((radian / 2).toDouble()) - radius_in * Math.sin(
                radian.toDouble()
            )).toFloat(),
            (pentagramThick - pentagramThick * Math.sin((radian / 2).toDouble())).toFloat()
        )
        path.close()
        paint.color = pentagramColor
        canvas.drawPath(path, paint)
    }

    companion object {
        //Pentagram angle
        private const val DEGREE = 36f
    }

    init {
        tag = TPentagram::class.java.simpleName
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.TPentagram)

        //Length of the middle pentagon
        pentagramThick = typedArray.getDimension(R.styleable.TPentagram_pentagramThick, 0f)
        pentagramColor =
            typedArray.getColor(R.styleable.TPentagram_pentagramColor, Color.TRANSPARENT)
        typedArray.recycle()
    }
}