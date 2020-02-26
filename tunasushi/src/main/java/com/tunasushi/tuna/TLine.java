package com.tunasushi.tuna;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.tuna.R;

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
    private int lineSurround;

    private float lineArrowWidth;
    private float lineArrowHeight;
    private int lineArrowColor;

    private float lineStrokeWidth;
    private int lineStrokeColor;

    private float lineX;

    public float getLineX() {
        return lineX;
    }

    public void setLineX(float lineX) {
        this.lineX = lineX;
    }


    @IntDef({TOP, BOTTOM})
    @Retention(RetentionPolicy.SOURCE)
    public @interface lineArrowMode {
    }

    public static final int TOP = 0;
    public static final int BOTTOM = 1;
    private static final int[] lineArrowModeArray = {TOP, BOTTOM,};
    private @lineArrowMode
    int lineArrowMode;


    @IntDef({HORIZONTAL, SLASH, SLASHBACK})
    @Retention(RetentionPolicy.SOURCE)
    public @interface lineMode {
    }

    public static final int HORIZONTAL = 0;
    public static final int SLASH = 1;
    public static final int SLASHBACK = 2;
    private static final int[] lineModeArray = {HORIZONTAL, SLASH, SLASHBACK};
    private @lineMode
    int lineMode;


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

        lineStrokeWidth = typedArray.getDimension(R.styleable.TLine_lineStrokeWidth, 0);
        lineStrokeColor = typedArray.getColor(R.styleable.TLine_lineStrokeColor, Color.TRANSPARENT);

        //If 0 is hidden
        lineX = typedArray.getDimension(R.styleable.TLine_lineX, 0);
        if (lineX == 0) {
            lineX = -lineArrowWidth;
        }
        lineSurround = typedArray.getColor(R.styleable.TLine_lineSurround, Color.TRANSPARENT);

        int lineModeIndex = typedArray.getInt(R.styleable.TLine_lineMode, 0);
        if (lineModeIndex >= 0) {
            lineMode = lineModeArray[lineModeIndex];
        }

        int lineArrowModeIndex = typedArray.getInt(R.styleable.TLine_lineArrowMode, 0);
        if (lineArrowModeIndex >= 0) {
            lineArrowMode = lineArrowModeArray[lineArrowModeIndex];
        } else {
//            throw new IllegalArgumentException("The content attribute lineArrowMode type must be given");
        }

        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        typedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(lineSurround);

        //
        if (lineMode == SLASH) {
            canvas.drawLine(0, 0, width, height, initPaint(Paint.Style.FILL, lineStrokeColor, lineStrokeWidth));
            return;
        } else if (lineMode == SLASHBACK) {
            canvas.drawLine(width, 0, 0, height, initPaint(Paint.Style.FILL, lineStrokeColor, lineStrokeWidth));
            return;
        }

        //
        if (lineStrokeWidth > 0) {
            switch (lineArrowMode) {
                case TOP:
                    drawArrow(
                            canvas,
                            width, height,
                            lineX,
                            lineArrowWidth, lineArrowHeight,
                            lineStrokeWidth,
                            lineStrokeColor,
                            true);
                    break;
                case BOTTOM:
                    drawArrow(
                            canvas,
                            width, height,
                            lineX,
                            lineArrowWidth, lineArrowHeight,
                            lineStrokeWidth,
                            lineStrokeColor,
                            false);
                    break;
                default:
                    break;
            }

            //The filling part of the color triangle
            if (lineArrowColor != Color.TRANSPARENT) {
                switch (lineArrowMode) {
                    case TOP:
                        initPathMoveTo(lineX - lineArrowWidth * 0.5f + lineStrokeWidth * 0.5f, height);
                        path.lineTo(lineX, height - lineArrowHeight - lineStrokeWidth * 0.5f);
                        path.lineTo(lineX + lineArrowWidth * 0.5f - lineStrokeWidth * 0.5f, height);
                        break;
                    case BOTTOM:
                        initPathMoveTo(lineX - lineArrowWidth * 0.5f + lineStrokeWidth * 0.5f, 0);
                        path.lineTo(lineX, lineArrowHeight - lineStrokeWidth * 0.5f);
                        path.lineTo(lineX + lineArrowWidth * 0.5f - lineStrokeWidth * 0.5f, 0);
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
