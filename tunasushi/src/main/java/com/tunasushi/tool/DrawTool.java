package com.tunasushi.tool;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import static com.tunasushi.tuna.TView.BOTTOM;
import static com.tunasushi.tuna.TView.CENTER;
import static com.tunasushi.tuna.TView.CENTER_HORIZONTAL;
import static com.tunasushi.tuna.TView.CENTER_VERTICAL;
import static com.tunasushi.tuna.TView.GRAVITY_MASK;
import static com.tunasushi.tuna.TView.LEFT;
import static com.tunasushi.tuna.TView.RIGHT;

/**
 * @author TunaSashimi
 * @date 2020-02-03 00:10
 * @Copyright 2020 TunaSashimi. All rights reserved.
 * @Descriptiona
 */
public class DrawTool {
    public static void drawAnchor(Canvas canvas, Paint paint,
                                  Bitmap srcAnchor,
                                  Matrix anchorMatrix,
                                  int width, int height,
                                  int srcAnchorGravityMode,
                                  float srcAnchorWidth, float srcAnchorHeight,
                                  float srcAnchorDx, float srcAnchorDy) {

        float anchorDxNormal = 0, anchorDyNormal = 0;
        switch (srcAnchorGravityMode & GRAVITY_MASK) {
            case LEFT:
                break;
            case CENTER_HORIZONTAL:
                anchorDxNormal = (width >> 1) - srcAnchorWidth * 0.5f;
                break;
            case RIGHT:
                anchorDxNormal = width - srcAnchorWidth;
                break;
            case CENTER_VERTICAL:
                anchorDyNormal = (height >> 1) - srcAnchorHeight * 0.5f;
                break;
            case CENTER:
                anchorDxNormal = (width >> 1) - srcAnchorWidth * 0.5f;
                anchorDyNormal = (height >> 1) - srcAnchorHeight * 0.5f;
                break;
            case RIGHT | CENTER_VERTICAL:
                anchorDxNormal = width - srcAnchorWidth;
                anchorDyNormal = (height >> 1) - srcAnchorHeight * 0.5f;
                break;
            case BOTTOM:
                anchorDyNormal = height - srcAnchorHeight;
                break;
            case BOTTOM | CENTER_HORIZONTAL:
                anchorDxNormal = (width >> 1) - srcAnchorWidth * 0.5f;
                anchorDyNormal = height - srcAnchorHeight;
                break;
            case BOTTOM | RIGHT:
                anchorDxNormal = width - srcAnchorWidth;
                anchorDyNormal = height - srcAnchorHeight;
                break;
            default:
                break;
        }
        canvas.translate(anchorDxNormal + srcAnchorDx, anchorDyNormal + srcAnchorDy);
        canvas.drawBitmap(srcAnchor, anchorMatrix, paint);
        canvas.translate(-anchorDxNormal + -srcAnchorDx, -anchorDyNormal - srcAnchorDy);
    }
}
