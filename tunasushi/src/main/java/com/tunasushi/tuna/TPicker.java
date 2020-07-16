package com.tunasushi.tuna;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;

/**
 * @author TunaSashimi
 * @date 2020-07-16 10:17
 * @Copyright 2020 TunaSashimi. All rights reserved.
 * @Description
 */
public class TPicker extends TView {
    private Paint mPaint;
    private Paint mCenterPaint;
    private final int[] mColors;

    //
    public interface PickerSelectListener {
        void pickerSelect(int color);
    }

    private PickerSelectListener pickerSelectListener;

    public PickerSelectListener getPickerSelectListener() {
        return pickerSelectListener;
    }

    public void setPickerSelectListener(PickerSelectListener pickerSelectListener) {
        this.pickerSelectListener = pickerSelectListener;
    }

    private boolean mTrackingCenter;

    private static final float PI = 3.1415926f;

    private float CENTER_X = 100;
    private float CENTER_Y = 100;
    private float CENTER_RADIUS = 32;
    private float paintStroke = 32;
    private float centerPaintStroke = 5;

    float displayDensity;

    TPicker(Context c, int color) {
        super(c);

        tag = TPicker.class.getSimpleName();

        mColors = new int[]{0xffff0000, 0xffff00ff, 0xff0000ff, 0xff00ffff, 0xff00ff00, 0xffffff00, 0xffff0000};

        displayDensity = getResources().getDisplayMetrics().density;

        CENTER_X = CENTER_X * displayDensity + 0.5f;
        CENTER_Y = CENTER_Y * displayDensity + 0.5f;
        CENTER_RADIUS = CENTER_RADIUS * displayDensity + 0.5f;

        paintStroke = paintStroke * displayDensity + 0.5f;
        centerPaintStroke = centerPaintStroke * displayDensity + 0.5f;

        Shader shader = new SweepGradient(0, 0, mColors, null);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setShader(shader);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(paintStroke);

        mCenterPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCenterPaint.setColor(color);
        mCenterPaint.setStrokeWidth(centerPaintStroke);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension((int) CENTER_X * 2, (int) CENTER_Y * 2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float r = CENTER_X - mPaint.getStrokeWidth() * 0.5f;

        canvas.translate(CENTER_X, CENTER_X);

        canvas.drawOval(new RectF(-r, -r, r, r), mPaint);
        canvas.drawCircle(0, 0, CENTER_RADIUS, mCenterPaint);

        if (mTrackingCenter) {
            int c = mCenterPaint.getColor();
            mCenterPaint.setStyle(Paint.Style.STROKE);

            canvas.drawCircle(0, 0, CENTER_RADIUS + mCenterPaint.getStrokeWidth(), mCenterPaint);

            mCenterPaint.setStyle(Paint.Style.FILL);
            mCenterPaint.setColor(c);
        }
    }

    @Override
    public void setTouchXYRaw(float touchX, float touchY) {
        float x = touchX - CENTER_X;
        float y = touchY - CENTER_Y;
        mTrackingCenter = Math.sqrt(x * x + y * y) <= CENTER_RADIUS;
        if (press) {
            if (mTrackingCenter) {
                invalidate();
            } else {
                float angle = (float) Math.atan2(y, x);
                float unit = angle / (2 * PI);
                if (unit < 0) {
                    unit += 1;
                }
                mCenterPaint.setColor(interpColor(mColors, unit));
                invalidate();
            }
        } else if (touchUp) {
            if (mTrackingCenter) {
                pickerSelectListener.pickerSelect(mCenterPaint.getColor());
                invalidate();
            }
        }
    }

    private int interpColor(int colors[], float unit) {
        if (unit <= 0) {
            return colors[0];
        }
        if (unit >= 1) {
            return colors[colors.length - 1];
        }

        float p = unit * (colors.length - 1);
        int i = (int) p;
        p -= i;

        // now p is just the fractional part [0...1) and i is the index
        int c0 = colors[i];
        int c1 = colors[i + 1];
        int a = ave(Color.alpha(c0), Color.alpha(c1), p);
        int r = ave(Color.red(c0), Color.red(c1), p);
        int g = ave(Color.green(c0), Color.green(c1), p);
        int b = ave(Color.blue(c0), Color.blue(c1), p);

        return Color.argb(a, r, g, b);
    }

    private int ave(int s, int d, float p) {
        return s + Math.round(p * (d - s));
    }
}