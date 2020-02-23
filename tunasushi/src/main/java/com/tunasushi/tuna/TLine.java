package com.tunasushi.tuna;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.tuna.R;

/**
 * @author TunaSashimi
 * @date 2015-10-30 16:55
 * @Copyright 2015 TunaSashimi. All rights reserved.
 * @Description
 */
public class TLine extends TView {
    private int lineSurround;

    private float lineArrowWidth;
    private float lineArrowHeight;
    private int lineArrowColor;

    private float lineArrowStrokeWidth;
    private int lineArrowStrokeColor;

    private float lineX;

    public float getLineX() {
        return lineX;
    }

    public void setLineX(float lineX) {
        this.lineX = lineX;
    }

    private LineTowardType lineTowardType;

    public enum LineTowardType {
        TOP(0),
        BOTTOM(1),
        ;
        final int nativeInt;

        LineTowardType(int ni) {
            nativeInt = ni;
        }
    }

    private static final LineTowardType[] lineTowardTypeArray = {
            LineTowardType.TOP,
            LineTowardType.BOTTOM,
    };

    public TLine(Context context) {
        this(context, null);
    }

    public TLine(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TLine(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        tag = TLine.class.getSimpleName();

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TLine);

        lineArrowWidth = typedArray.getDimension(R.styleable.TLine_lineArrowWidth, 0);
        lineArrowHeight = typedArray.getDimension(R.styleable.TLine_lineArrowHeight, 0);

        lineArrowColor = typedArray.getColor(R.styleable.TLine_lineArrowColor, Color.TRANSPARENT);

        lineArrowStrokeWidth = typedArray.getDimension(R.styleable.TLine_lineArrowStrokeWidth, 0);
        lineArrowStrokeColor = typedArray.getColor(R.styleable.TLine_lineArrowStrokeColor, Color.TRANSPARENT);

        //If 0 is hidden
        lineX = typedArray.getDimension(R.styleable.TLine_lineX, 0);
        if (lineX == 0) {
            lineX = -lineArrowWidth;
        }
        lineSurround = typedArray.getColor(R.styleable.TLine_lineSurround, Color.TRANSPARENT);

        int lineTowardTypeIndex = typedArray.getInt(R.styleable.TLine_lineTowardType, -1);
        if (lineTowardTypeIndex >= 0) {
            lineTowardType = lineTowardTypeArray[lineTowardTypeIndex];
        } else {
            throw new IllegalArgumentException("The content attribute lineTowardType type must be given");
        }

        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        typedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(lineSurround);

        if (lineArrowStrokeWidth > 0) {
            switch (lineTowardType) {
                case TOP:
                    drawArrow(
                            canvas,
                            width, height,
                            lineX,
                            lineArrowWidth, lineArrowHeight,
                            lineArrowStrokeWidth,
                            lineArrowStrokeColor,
                            true);
                    break;
                case BOTTOM:
                    drawArrow(
                            canvas,
                            width, height,
                            lineX,
                            lineArrowWidth, lineArrowHeight,
                            lineArrowStrokeWidth,
                            lineArrowStrokeColor,
                            false);
                    break;
                default:
                    break;
            }

            //The filling part of the color triangle
            if (lineArrowColor != Color.TRANSPARENT) {
                switch (lineTowardType) {
                    case TOP:
                        initPathMoveTo(lineX - lineArrowWidth * 0.5f + lineArrowStrokeWidth * 0.5f, height);
                        path.lineTo(lineX, height - lineArrowHeight - lineArrowStrokeWidth * 0.5f);
                        path.lineTo(lineX + lineArrowWidth * 0.5f - lineArrowStrokeWidth * 0.5f, height);
                        break;
                    case BOTTOM:
                        initPathMoveTo(lineX - lineArrowWidth * 0.5f + lineArrowStrokeWidth * 0.5f, 0);
                        path.lineTo(lineX, lineArrowHeight - lineArrowStrokeWidth * 0.5f);
                        path.lineTo(lineX + lineArrowWidth * 0.5f - lineArrowStrokeWidth * 0.5f, 0);
                        break;
                    default:
                        break;
                }
                path.close();
                canvas.drawPath(path, initPaint(Paint.Style.FILL, lineArrowColor));
            }
        }
    }


    public void centerArrow() {
        lineX = width >> 1;
        invalidate();
    }

    public void hideArrow() {
        lineX = -lineArrowWidth;
        invalidate();
    }

    @Override
    public void setTouchXYRaw(float touchX, float touchY) {
        lineX = touchX;
        invalidate();
    }
}
