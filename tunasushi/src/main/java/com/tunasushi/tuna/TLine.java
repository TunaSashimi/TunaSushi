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

import static com.tunasushi.tool.DeviceTool.applyDimension;
import static com.tunasushi.tool.PaintTool.initTunaPaint;

/**
 * @author Tunasashimi
 * @date 10/30/15 16:55
 * @Copyright 2015 TunaSashimi. All rights reserved.
 * @Description
 */
public class TLine extends TView {

    private int tunaLineBackgroundNormal;

    private float tunaLineArrowWidth;
    private float tunaLineArrowHeight;

    private float tunaLineArrowStrokeWidth;
    private int tunaLineArrowStrokeColor;

    private int tunaLineArrowColor;

    private float tunaLineCurrentX;

    public float getTunaLineCurrentX() {
        return tunaLineCurrentX;
    }

    //
    public void setLineCurrentX(float tunaLineCurrentX) {
        setLineCurrentX(TypedValue.COMPLEX_UNIT_DIP, tunaLineCurrentX);
    }

    public void setLineCurrentX(int unit, float tunaLineCurrentX) {
        Context c = getContext();
        Resources r;
        if (c == null)
            r = Resources.getSystem();
        else
            r = c.getResources();
        setTunaLineCurrentXRaw(applyDimension(unit, tunaLineCurrentX, r.getDisplayMetrics()));
    }

    private void setTunaLineCurrentXRaw(float tunaLineCurrentX) {
        if (this.tunaLineCurrentX != tunaLineCurrentX) {
            this.tunaLineCurrentX = tunaLineCurrentX;
            invalidate();
        }
    }

    private TunaLineTowardType tunaLineTowardType;

    public enum TunaLineTowardType {
        TOP(0),
        BOTTOM(1),;
        final int nativeInt;

        TunaLineTowardType(int ni) {
            nativeInt = ni;
        }
    }

    private static final TunaLineTowardType[] tunaLineTowardTypeArray = {
            TunaLineTowardType.TOP,
            TunaLineTowardType.BOTTOM,
    };

    public TLine(Context context) {
        this(context, null);
    }

    public TLine(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TLine(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        tunaTag = TLine.class.getSimpleName();

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TLine);

        tunaLineBackgroundNormal = typedArray.getColor(R.styleable.TLine_lineBackgroundNormal, Color.TRANSPARENT);

        tunaLineArrowWidth = typedArray.getDimension(R.styleable.TLine_lineArrowWidth, 0);
        tunaLineArrowHeight = typedArray.getDimension(R.styleable.TLine_lineArrowHeight, 0);

        tunaLineArrowStrokeWidth = typedArray.getDimension(R.styleable.TLine_lineArrowStrokeWidth, 0);
        tunaLineArrowStrokeColor = typedArray.getColor(R.styleable.TLine_lineArrowStrokeColor, Color.TRANSPARENT);

        tunaLineArrowColor = typedArray.getColor(R.styleable.TLine_lineArrowColor, Color.TRANSPARENT);

        //If 0 is hidden
//        tunaLineCurrentX = typedArray.getDimension(R.styleable.TunaLine_tunaLineCurrentX, 0);
        tunaLineCurrentX = 0;
        if (tunaLineCurrentX == 0) {
            tunaLineCurrentX = -tunaLineArrowWidth;
        }

//        int tunaTrangleTowardTypeIndex = typedArray.getInt(R.styleable.TunaTrangle_tunaTrangleTowardType, -1);
        int tunaTrangleTowardTypeIndex = 0;
        if (tunaTrangleTowardTypeIndex >= 0) {
            tunaLineTowardType = tunaLineTowardTypeArray[tunaTrangleTowardTypeIndex];
        } else {
            throw new IllegalArgumentException("The content attribute tunaLineTowardType type must be given");
        }

        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        typedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(tunaLineBackgroundNormal);

        if (tunaLineArrowStrokeWidth > 0) {
            switch (tunaLineTowardType) {
                case TOP:
                    drawTunaArrow(
                            canvas,
                            tunaLineCurrentX,
                            tunaLineArrowWidth, tunaLineArrowHeight,
                            tunaLineArrowStrokeWidth,
                            tunaLineArrowStrokeColor,
                            true);
                    break;
                case BOTTOM:
                    drawTunaArrow(
                            canvas,
                            tunaLineCurrentX,
                            tunaLineArrowWidth, tunaLineArrowHeight,
                            tunaLineArrowStrokeWidth,
                            tunaLineArrowStrokeColor,
                            false);
                    break;
                default:
                    break;
            }

            //The filling part of the color triangle
            if (tunaLineArrowColor != Color.TRANSPARENT) {
                switch (tunaLineTowardType) {
                    case TOP:
                        initTunaPathMoveTo(tunaLineCurrentX - tunaLineArrowWidth * 0.5f + tunaLineArrowStrokeWidth * 0.5f, tunaHeight);
                        tunaPath.lineTo(tunaLineCurrentX, tunaHeight - tunaLineArrowHeight - tunaLineArrowStrokeWidth * 0.5f);
                        tunaPath.lineTo(tunaLineCurrentX + tunaLineArrowWidth * 0.5f - tunaLineArrowStrokeWidth * 0.5f, tunaHeight);
                        break;
                    case BOTTOM:
                        initTunaPathMoveTo(tunaLineCurrentX - tunaLineArrowWidth * 0.5f + tunaLineArrowStrokeWidth * 0.5f, 0);
                        tunaPath.lineTo(tunaLineCurrentX, tunaLineArrowHeight - tunaLineArrowStrokeWidth * 0.5f);
                        tunaPath.lineTo(tunaLineCurrentX + tunaLineArrowWidth * 0.5f - tunaLineArrowStrokeWidth * 0.5f, 0);
                        break;
                    default:
                        break;
                }
                tunaPath.close();
                canvas.drawPath(tunaPath, initTunaPaint(Paint.Style.FILL, tunaLineArrowColor));
            }

        }
    }

    //
    protected void drawTunaArrow(Canvas canvas,
                                 float floatX,
                                 float arrowWidth, float arrowHeight,
                                 float arrowStrokeWidth, int arrowStrokeColor,
                                 boolean upward) {

        if (upward) {
            initTunaPathMoveTo(0, tunaHeight - arrowStrokeWidth * 0.5f);
            tunaPath.lineTo(floatX - arrowWidth * 0.5f, tunaHeight - arrowStrokeWidth * 0.5f);
            tunaPath.lineTo(floatX, tunaHeight - arrowStrokeWidth * 0.5f - arrowHeight);
            tunaPath.lineTo(floatX + arrowWidth * 0.5f, tunaHeight - arrowStrokeWidth * 0.5f);
            tunaPath.lineTo(tunaWidth, tunaHeight - arrowStrokeWidth * 0.5f);
        } else {
            initTunaPathMoveTo(0, arrowStrokeWidth * 0.5f);
            tunaPath.lineTo(floatX - arrowWidth * 0.5f, arrowStrokeWidth * 0.5f);
            tunaPath.lineTo(floatX, arrowHeight + arrowStrokeWidth * 0.5f);
            tunaPath.lineTo(floatX + arrowWidth * 0.5f, arrowStrokeWidth * 0.5f);
            tunaPath.lineTo(tunaWidth, arrowStrokeWidth * 0.5f);
        }

        canvas.drawPath(tunaPath, initTunaPaint(Paint.Style.STROKE, arrowStrokeColor, arrowStrokeWidth));
    }

    public void centerArrow() {
        tunaLineCurrentX = tunaWidth >> 1;
        invalidate();
    }

    public void hideArrow() {
        tunaLineCurrentX = -tunaLineArrowWidth;
        invalidate();
    }

}
