package com.tunasushi.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetricsInt;
import android.util.AttributeSet;
import com.tunasushi.R;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import androidx.annotation.IntDef;

/**
 * @author TunaSashimi
 * @date 2015-10-30 16:53
 * @Copyright 2015 TunaSashimi. All rights reserved.
 * @Description
 */
public class TSign extends TView {

    private int signRectColor;
    private float signRectWidth, signRectHeight;
    private float signRectMargin;
    private float signRectStrokeWidth;

    private float signCircleRadius;
    private float signCircleMargin;
    private float signCircleStrokeWidth;
    private int signCircleColorBefore, signCircleColorAfter;

    private float signTextSize;
    private float signTextStrokeWidth;
    private String signTextBefore, signTextAfter;


    @IntDef({HORIZONTAL, VERTICAL,})
    @Retention(RetentionPolicy.SOURCE)
    public @interface signStyle {
    }

    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
    private static final int[] signStyleArray = {HORIZONTAL, VERTICAL,};
    private @signStyle
    int signStyle;

    public TSign(Context context) {
        this(context, null);
    }

    public TSign(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TSign(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        tag = TSign.class.getSimpleName();

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TSign);

        signRectColor = typedArray.getColor(R.styleable.TSign_signRectColor, Color.TRANSPARENT);
        signRectWidth = typedArray.getDimension(R.styleable.TSign_signRectWidth, 2);
        signRectHeight = typedArray.getDimension(R.styleable.TSign_signRectHeight, 2);
        signRectMargin = typedArray.getDimension(R.styleable.TSign_signRectMargin, 2);
        signRectStrokeWidth = typedArray.getDimension(R.styleable.TSign_signRectStrokeWidth, 2);

        signCircleRadius = typedArray.getDimension(R.styleable.TSign_signCircleRadius, 2);
        signCircleMargin = typedArray.getDimension(R.styleable.TSign_signCircleMargin, 2);
        signCircleStrokeWidth = typedArray.getDimension(R.styleable.TSign_signCircleStrokeWidth, 2);
        signCircleColorBefore = typedArray.getColor(R.styleable.TSign_signCircleColorBefore, textColorDefault);
        signCircleColorAfter = typedArray.getColor(R.styleable.TSign_signCircleColorAfter, textColorDefault);

        signTextSize = typedArray.getDimension(R.styleable.TSign_signTextSize, textSizeDefault);
        signTextStrokeWidth = typedArray.getDimension(R.styleable.TSign_signTextStrokeWidth, 2);
        signTextBefore = typedArray.getString(R.styleable.TSign_signTextBefore);
        signTextAfter = typedArray.getString(R.styleable.TSign_signTextAfter);

        int signStyleIndex = typedArray.getInt(R.styleable.TSign_signStyle, -1);
        if (signStyleIndex >= 0) {
            signStyle = signStyleArray[signStyleIndex];
        } else {
            throw new IllegalArgumentException("The content attribute signStyle type must be given");
        }

        typedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (signCircleStrokeWidth != 0) {
            initPaint(Paint.Style.STROKE, signCircleColorBefore, signCircleStrokeWidth);
        } else {
            initPaint(Paint.Style.FILL, signCircleColorBefore);
        }

        float circleCenterYBefore = signCircleRadius + signCircleMargin;
        canvas.drawCircle(width >> 1, circleCenterYBefore, signCircleRadius, paint);

        // 后期拆分
        if (signTextStrokeWidth != 0) {
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(signTextStrokeWidth);
        } else {
            paint.setStyle(Paint.Style.FILL);
        }

        paint.setTextSize(signTextSize);
        paint.setTextAlign(Paint.Align.CENTER);
        FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        float baseline = (circleCenterYBefore * 2 - fontMetrics.bottom - fontMetrics.top) * 0.5f;

        if (signTextBefore != null) {
            canvas.drawText(signTextBefore, width >> 1, baseline, paint);
        }

        if (signRectStrokeWidth != 0) {
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(signRectStrokeWidth);
        } else {
            paint.setStyle(Paint.Style.FILL);
        }
        paint.setColor(signRectColor);

        if (signStyle == VERTICAL) {

            float rectCenterX = width >> 1;
            float rectCenterY = signCircleMargin + signCircleRadius * 2 + signCircleStrokeWidth * 2 + signRectMargin + signRectHeight / 2
                    + signRectWidth;

            for (; ; ) {
                canvas.drawRect(rectCenterX - signRectWidth / 2, rectCenterY - signRectHeight / 2, rectCenterX + signRectWidth / 2, rectCenterY
                        + signRectHeight / 2, paint);
                if (height
                        - (rectCenterY + signRectHeight / 2 + signRectMargin + signRectStrokeWidth + signCircleMargin + signCircleRadius
                        * 2 + signCircleStrokeWidth * 2) <= signRectHeight + signRectStrokeWidth * 2 + signRectMargin) {
                    break;
                }
                rectCenterY += signRectHeight + signRectMargin + signRectMargin * 2;
            }

            if (signCircleStrokeWidth != 0) {
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(signCircleStrokeWidth);
            } else {
                paint.setStyle(Paint.Style.FILL);
            }
            paint.setColor(signCircleColorAfter);

            float circleCenterAfter = rectCenterY + signRectHeight / 2 + signRectMargin + signRectStrokeWidth + signCircleRadius
                    + signCircleStrokeWidth;
            canvas.drawCircle(width >> 1, circleCenterAfter, signCircleRadius, paint);

            if (signTextStrokeWidth != 0) {
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(signTextStrokeWidth);
            } else {
                paint.setStyle(Paint.Style.FILL);
            }
            baseline = (circleCenterAfter * 2 - fontMetrics.bottom - fontMetrics.top) * 0.5f;

            if (signTextAfter != null) {
                canvas.drawText(signTextAfter, width >> 1, baseline, paint);
            }

        } else {

            float rectCenterX = signCircleMargin + signCircleRadius * 2 + signCircleStrokeWidth * 2 + signRectMargin + signRectHeight / 2
                    + signRectWidth;
            float rectCenterY = height >> 1;

            for (; ; ) {
                canvas.drawRect(rectCenterX - signRectWidth / 2, rectCenterY - signRectHeight / 2, rectCenterX + signRectWidth / 2, rectCenterY
                        + signRectHeight / 2, paint);

                if (width
                        - (rectCenterX + signRectWidth / 2 + signRectMargin + signRectStrokeWidth + signCircleMargin + signCircleRadius * 2 + signCircleStrokeWidth * 2) <= signRectWidth
                        + signRectStrokeWidth * 2 + signRectMargin) {
                    break;
                }
                rectCenterX += signRectWidth + signRectMargin + signRectMargin * 2;
            }
        }
    }
}