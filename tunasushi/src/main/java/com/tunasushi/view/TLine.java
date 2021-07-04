package com.tunasushi.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;


import com.tunasushi.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;


/**
 * @author TunaSashimi
 * @date 2015-10-30 16:55
 * @Copyright 2015 TunaSashimi. All rights reserved.
 * @Description
 */
public class TLine extends TView {
    private float lineThick;
    private int lineColor;

    @IntDef({TOP, BOTTOM, LEFT, RIGHT, SLASH, SLASHBACK,})
    @Retention(RetentionPolicy.SOURCE)
    public @interface lineStyle {
    }

    public static final int TOP = 0;
    public static final int BOTTOM = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;
    public static final int SLASH = 4;
    public static final int SLASHBACK = 5;
    public static final int CROSS = 6;
    private static final int[] lineStyleArray = {TOP, BOTTOM, LEFT, RIGHT, SLASH, SLASHBACK, CROSS,};
    private @lineStyle
    int lineStyle;

    public int getLineStyle() {
        return lineStyle;
    }

    public void setLineStyle(int lineStyle) {
        this.lineStyle = lineStyle;
        invalidate();
    }

    private int lineBackground;

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

        lineThick = typedArray.getDimension(R.styleable.TLine_lineThick, 0);
        lineColor = typedArray.getColor(R.styleable.TLine_lineColor, Color.TRANSPARENT);

        int lineStyleIndex = typedArray.getInt(R.styleable.TLine_lineStyle, 0);
        if (lineStyleIndex >= 0) {
            lineStyle = lineStyleArray[lineStyleIndex];
        }

        lineBackground = typedArray.getColor(R.styleable.TLine_lineBackground, Color.TRANSPARENT);

        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        typedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        switch (lineStyle) {
            case TOP:
                if (lineBackground != Color.TRANSPARENT) {
                    initPathMoveTo(0, height);
                    path.lineTo(width >> 1, 0);
                    path.lineTo(width, height);
                    path.close();
                    canvas.drawPath(path, initPaint(lineBackground));
                }
                canvas.drawLine(0, height, width >> 1, 0, initPaint(Paint.Style.FILL, lineColor, lineThick));
                canvas.drawLine(width >> 1, 0, width, height, initPaint(Paint.Style.FILL, lineColor, lineThick));
                break;
            case BOTTOM:
                if (lineBackground != Color.TRANSPARENT) {
                    initPathMoveTo(0, 0);
                    path.lineTo(width >> 1, height);
                    path.lineTo(width, 0);
                    path.close();
                    canvas.drawPath(path, initPaint(lineBackground));
                }
                canvas.drawLine(0, 0, width >> 1, height, initPaint(Paint.Style.FILL, lineColor, lineThick));
                canvas.drawLine(width >> 1, height, width, 0, initPaint(Paint.Style.FILL, lineColor, lineThick));
                break;
            case LEFT:
                if (lineBackground != Color.TRANSPARENT) {
                    initPathMoveTo(width, 0);
                    path.lineTo(0, height >> 1);
                    path.lineTo(width, height);
                    path.close();
                    canvas.drawPath(path, initPaint(lineBackground));
                }
                canvas.drawLine(width, 0, 0, height >> 1, initPaint(Paint.Style.FILL, lineColor, lineThick));
                canvas.drawLine(0, height >> 1, width, height, initPaint(Paint.Style.FILL, lineColor, lineThick));
                break;
            case RIGHT:
                if (lineBackground != Color.TRANSPARENT) {
                    initPathMoveTo(0, 0);
                    path.lineTo(width, height >> 1);
                    path.lineTo(0, height);
                    path.close();
                    canvas.drawPath(path, initPaint(lineBackground));
                }
                canvas.drawLine(0, 0, width, height >> 1, initPaint(Paint.Style.FILL, lineColor, lineThick));
                canvas.drawLine(width, height >> 1, 0, height, initPaint(Paint.Style.FILL, lineColor, lineThick));
                break;
            case SLASH:
                if (lineBackground != Color.TRANSPARENT) {
                    canvas.drawColor(lineBackground);
                }
                canvas.drawLine(0, 0, width, height, initPaint(Paint.Style.FILL, lineColor, lineThick));
                break;
            case SLASHBACK:
                if (lineBackground != Color.TRANSPARENT) {
                    canvas.drawColor(lineBackground);
                }
                canvas.drawLine(width, 0, 0, height, initPaint(Paint.Style.FILL, lineColor, lineThick));
                break;
            case CROSS:
                if (lineBackground != Color.TRANSPARENT) {
                    canvas.drawColor(lineBackground);
                }
                canvas.drawLine(0, 0, width, height, initPaint(Paint.Style.FILL, lineColor, lineThick));
                canvas.drawLine(width, 0, 0, height, initPaint(Paint.Style.FILL, lineColor, lineThick));
                break;
            default:
                break;
        }
    }
}
