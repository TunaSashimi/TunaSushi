package com.tunasushi.tuna;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;


import com.tuna.R;
import com.tunasushi.tool.PaintTool;

import static com.tunasushi.tool.DeviceTool.applyDimension;
import static com.tunasushi.tool.PathTool.initPathMoveTo;
import static com.tunasushi.tool.PathTool.path;

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

    private float lineCurrentX;

    public float getLineCurrentX() {
        return lineCurrentX;
    }

    //
    public void setLineCurrentX(float lineCurrentX) {
        setLineCurrentX(TypedValue.COMPLEX_UNIT_DIP, lineCurrentX);
    }

    public void setLineCurrentX(int unit, float lineCurrentX) {
        Context c = getContext();
        Resources r;
        if (c == null)
            r = Resources.getSystem();
        else
            r = c.getResources();
        setLineCurrentXRaw(applyDimension(unit, lineCurrentX, r.getDisplayMetrics()));
    }

    private void setLineCurrentXRaw(float lineCurrentX) {
        if (this.lineCurrentX != lineCurrentX) {
            this.lineCurrentX = lineCurrentX;
            invalidate();
        }
    }

    private LineTowardType lineTowardType;

    public enum LineTowardType {
        TOP(0),
        BOTTOM(1),;
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

        Tag = TLine.class.getSimpleName();

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TLine);

        lineBackgroundNormal = typedArray.getColor(R.styleable.TLine_lineBackgroundNormal, Color.TRANSPARENT);

        lineArrowWidth = typedArray.getDimension(R.styleable.TLine_lineArrowWidth, 0);
        lineArrowHeight = typedArray.getDimension(R.styleable.TLine_lineArrowHeight, 0);

        lineArrowStrokeWidth = typedArray.getDimension(R.styleable.TLine_lineArrowStrokeWidth, 0);
        lineArrowStrokeColor = typedArray.getColor(R.styleable.TLine_lineArrowStrokeColor, Color.TRANSPARENT);

        lineArrowColor = typedArray.getColor(R.styleable.TLine_lineArrowColor, Color.TRANSPARENT);

        //If 0 is hidden
        lineCurrentX = typedArray.getDimension(R.styleable.TLine_lineCurrentX, 0);
        if (lineCurrentX == 0) {
            lineCurrentX = -lineArrowWidth;
        }

        int tunaLineTowardTypeIndex = typedArray.getInt(R.styleable.TLine_lineTowardType, -1);
        if (tunaLineTowardTypeIndex >= 0) {
            lineTowardType = lineTowardTypeArray[tunaLineTowardTypeIndex];
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
                    drawarrow(
                            canvas,
                            lineCurrentX,
                            lineArrowWidth, lineArrowHeight,
                            lineArrowStrokeWidth,
                            lineArrowStrokeColor,
                            true);
                    break;
                case BOTTOM:
                    drawarrow(
                            canvas,
                            lineCurrentX,
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
                        initPathMoveTo(lineCurrentX - lineArrowWidth * 0.5f + lineArrowStrokeWidth * 0.5f, height);
                        path.lineTo(lineCurrentX, height - lineArrowHeight - lineArrowStrokeWidth * 0.5f);
                        path.lineTo(lineCurrentX + lineArrowWidth * 0.5f - lineArrowStrokeWidth * 0.5f, height);
                        break;
                    case BOTTOM:
                        initPathMoveTo(lineCurrentX - lineArrowWidth * 0.5f + lineArrowStrokeWidth * 0.5f, 0);
                        path.lineTo(lineCurrentX, lineArrowHeight - lineArrowStrokeWidth * 0.5f);
                        path.lineTo(lineCurrentX + lineArrowWidth * 0.5f - lineArrowStrokeWidth * 0.5f, 0);
                        break;
                    default:
                        break;
                }
                path.close();
                canvas.drawPath(path, PaintTool.initPaint(Paint.Style.FILL, lineArrowColor));
            }

        }
    }

    //
    protected void drawarrow(Canvas canvas,
                                 float floatX,
                                 float arrowWidth, float arrowHeight,
                                 float arrowStrokeWidth, int arrowStrokeColor,
                                 boolean upward) {

        if (upward) {
            initPathMoveTo(0, height - arrowStrokeWidth * 0.5f);
            path.lineTo(floatX - arrowWidth * 0.5f, height - arrowStrokeWidth * 0.5f);
            path.lineTo(floatX, height - arrowStrokeWidth * 0.5f - arrowHeight);
            path.lineTo(floatX + arrowWidth * 0.5f, height - arrowStrokeWidth * 0.5f);
            path.lineTo(width, height - arrowStrokeWidth * 0.5f);
        } else {
            initPathMoveTo(0, arrowStrokeWidth * 0.5f);
            path.lineTo(floatX - arrowWidth * 0.5f, arrowStrokeWidth * 0.5f);
            path.lineTo(floatX, arrowHeight + arrowStrokeWidth * 0.5f);
            path.lineTo(floatX + arrowWidth * 0.5f, arrowStrokeWidth * 0.5f);
            path.lineTo(width, arrowStrokeWidth * 0.5f);
        }

        canvas.drawPath(path, PaintTool.initPaint(Paint.Style.STROKE, arrowStrokeColor, arrowStrokeWidth));
    }

    public void centerArrow() {
        lineCurrentX = width >> 1;
        invalidate();
    }

    public void hideArrow() {
        lineCurrentX = -lineArrowWidth;
        invalidate();
    }

}
