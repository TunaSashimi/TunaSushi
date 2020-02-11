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
 * @author Tunasashimi
 * @date 10/30/15 16:55
 * @Copyright 2015 Sashimi. All rights reserved.
 * @Description
 */
public class TLine extends TView {

    private int lineBackgroundNormal;

    private float lineArrowWidth;
    private float lineArrowHeight;

    private float lineArrowStrokeWidth;
    private int lineArrowStrokeColor;

    private int lineArrowColor;


    @Override
    public void setXRaw(float x) {
            this.x = x;
            invalidate();
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

        lineBackgroundNormal = typedArray.getColor(R.styleable.TLine_lineBackgroundNormal, Color.TRANSPARENT);

        lineArrowWidth = typedArray.getDimension(R.styleable.TLine_lineArrowWidth, 0);
        lineArrowHeight = typedArray.getDimension(R.styleable.TLine_lineArrowHeight, 0);

        lineArrowStrokeWidth = typedArray.getDimension(R.styleable.TLine_lineArrowStrokeWidth, 0);
        lineArrowStrokeColor = typedArray.getColor(R.styleable.TLine_lineArrowStrokeColor, Color.TRANSPARENT);

        lineArrowColor = typedArray.getColor(R.styleable.TLine_lineArrowColor, Color.TRANSPARENT);

        //If 0 is hidden
        x = typedArray.getDimension(R.styleable.TLine_lineX, 0);
        if (x == 0) {
            x = -lineArrowWidth;
        }

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
        super.onDraw(canvas);

        canvas.drawColor(lineBackgroundNormal);

        if (lineArrowStrokeWidth > 0) {
            switch (lineTowardType) {
                case TOP:
                    drawArrow(
                            canvas,
                            width, height,
                            x,
                            lineArrowWidth, lineArrowHeight,
                            lineArrowStrokeWidth,
                            lineArrowStrokeColor,
                            true);
                    break;
                case BOTTOM:
                    drawArrow(
                            canvas,
                            width, height,
                            x,
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
                        initPathMoveTo(x - lineArrowWidth * 0.5f + lineArrowStrokeWidth * 0.5f, height);
                        path.lineTo(x, height - lineArrowHeight - lineArrowStrokeWidth * 0.5f);
                        path.lineTo(x + lineArrowWidth * 0.5f - lineArrowStrokeWidth * 0.5f, height);
                        break;
                    case BOTTOM:
                        initPathMoveTo(x - lineArrowWidth * 0.5f + lineArrowStrokeWidth * 0.5f, 0);
                        path.lineTo(x, lineArrowHeight - lineArrowStrokeWidth * 0.5f);
                        path.lineTo(x + lineArrowWidth * 0.5f - lineArrowStrokeWidth * 0.5f, 0);
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
        x = width >> 1;
        invalidate();
    }

    public void hideArrow() {
        x = -lineArrowWidth;
        invalidate();
    }
}
