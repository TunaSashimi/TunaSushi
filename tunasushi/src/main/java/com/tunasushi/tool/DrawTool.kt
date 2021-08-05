package com.tunasushi.tool

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import com.tunasushi.view.TView

/**
 * @author TunaSashimi
 * @date 2020-02-03 00:10
 * @Copyright 2020 TunaSashimi. All rights reserved.
 * @Descriptiona
 */
object DrawTool {
    @JvmStatic
    fun drawAnchor(
        canvas: Canvas, paint: Paint?,
        srcAnchor: Bitmap?,
        anchorMatrix: Matrix?,
        width: Int, height: Int,
        srcAnchorGravity: Int,
        srcAnchorWidth: Float, srcAnchorHeight: Float,
        srcAnchorDx: Float, srcAnchorDy: Float
    ) {
        var anchorDxNormal = 0f
        var anchorDyNormal = 0f
        when (srcAnchorGravity and TView.GRAVITY_MASK) {
            TView.LEFT -> {
            }
            TView.CENTER_HORIZONTAL -> anchorDxNormal = (width shr 1) - srcAnchorWidth * 0.5f
            TView.RIGHT -> anchorDxNormal = width - srcAnchorWidth
            TView.CENTER_VERTICAL -> anchorDyNormal = (height shr 1) - srcAnchorHeight * 0.5f
            TView.CENTER -> {
                anchorDxNormal = (width shr 1) - srcAnchorWidth * 0.5f
                anchorDyNormal = (height shr 1) - srcAnchorHeight * 0.5f
            }
            TView.RIGHT or TView.CENTER_VERTICAL -> {
                anchorDxNormal = width - srcAnchorWidth
                anchorDyNormal = (height shr 1) - srcAnchorHeight * 0.5f
            }
            TView.BOTTOM -> anchorDyNormal = height - srcAnchorHeight
            TView.BOTTOM or TView.CENTER_HORIZONTAL -> {
                anchorDxNormal = (width shr 1) - srcAnchorWidth * 0.5f
                anchorDyNormal = height - srcAnchorHeight
            }
            TView.BOTTOM or TView.RIGHT -> {
                anchorDxNormal = width - srcAnchorWidth
                anchorDyNormal = height - srcAnchorHeight
            }
            else -> {
            }
        }
        canvas.translate(anchorDxNormal + srcAnchorDx, anchorDyNormal + srcAnchorDy)
        canvas.drawBitmap(srcAnchor!!, anchorMatrix!!, paint)
        canvas.translate(-anchorDxNormal + -srcAnchorDx, -anchorDyNormal - srcAnchorDy)
    }
}