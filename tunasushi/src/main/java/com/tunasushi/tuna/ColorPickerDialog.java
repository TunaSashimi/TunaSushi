package com.tunasushi.tuna;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author Tunasashimi
 * @date 10/30/15 16:51
 * @Copyright 2015 Sashimi. All rights reserved.
 * @Description
 */

public class ColorPickerDialog extends Dialog {
    private int initialColor;
    private colorSelectListener colorSelectListener;

    public interface colorSelectListener {
        void colorSelect(int color);
    }

    public ColorPickerDialog(Context context, int initialColor, colorSelectListener colorSelectListener) {
        super(context);

        this.initialColor = initialColor;
        this.colorSelectListener = colorSelectListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Add a layer inside the listener and Add to contentview
        colorSelectListener colorSelectListener = new colorSelectListener() {
            public void colorSelect(int color) {
                ColorPickerDialog.this.colorSelectListener.colorSelect(color);
                dismiss();
            }
        };

        setContentView(new colorPicker(getContext(), initialColor, colorSelectListener));
        setTitle("Select Color");
    }

    private static class colorPicker extends View {

        private Paint mPaint;
        private Paint mCenterPaint;
        private final int[] mColors;
        private colorSelectListener mColorSelectListener;

        private boolean mTrackingCenter;
        private boolean mHighlightCenter;

        private static final float PI = 3.1415926f;

        private float CENTER_X = 100;
        private float CENTER_Y = 100;
        private float CENTER_RADIUS = 32;
        private float paintStroke = 32;
        private float centerPaintStroke = 5;

        float displayDensity;

        colorPicker(Context c, int color, colorSelectListener l) {
            super(c);

            mColorSelectListener = l;
            mColors = new int[]{
                    0xFFFF0000, 0xFFFF00FF, 0xFF0000FF, 0xFF00FFFF, 0xFF00FF00,
                    0xFFFFFF00, 0xFFFF0000
            };

            displayDensity = getResources().getDisplayMetrics().density;

            CENTER_X = CENTER_X * displayDensity + 0.5f;
            CENTER_Y = CENTER_Y * displayDensity + 0.5f;
            CENTER_RADIUS = CENTER_RADIUS * displayDensity + 0.5f;
            paintStroke = paintStroke * displayDensity + 0.5f;
            centerPaintStroke = centerPaintStroke * displayDensity + 0.5f;

            Shader s = new SweepGradient(0, 0, mColors, null);

            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mPaint.setShader(s);
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

                if (mHighlightCenter) {
                    mCenterPaint.setAlpha(0xFF);
                } else {
                    mCenterPaint.setAlpha(0x80);
                }
                canvas.drawCircle(0, 0,
                        CENTER_RADIUS + mCenterPaint.getStrokeWidth(),
                        mCenterPaint);

                mCenterPaint.setStyle(Paint.Style.FILL);
                mCenterPaint.setColor(c);
            }
        }


        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float x = event.getX() - CENTER_X;
            float y = event.getY() - CENTER_Y;
            boolean inCenter = Math.sqrt(x * x + y * y) <= CENTER_RADIUS;

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mTrackingCenter = inCenter;
                    if (inCenter) {
                        mHighlightCenter = true;
                        invalidate();
                        break;
                    }
                case MotionEvent.ACTION_MOVE:
                    if (mTrackingCenter) {
                        if (mHighlightCenter != inCenter) {
                            mHighlightCenter = inCenter;
                            invalidate();
                        }
                    } else {
                        float angle = (float) Math.atan2(y, x);
                        // need to turn angle [-PI ... PI] into unit [0....1]
                        float unit = angle / (2 * PI);
                        if (unit < 0) {
                            unit += 1;
                        }
                        mCenterPaint.setColor(interpColor(mColors, unit));
                        invalidate();
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    if (mTrackingCenter) {
                        if (inCenter) {
                            mColorSelectListener.colorSelect(mCenterPaint.getColor());
                        }
                        mTrackingCenter = false;    // so we draw w/o halo
                        invalidate();
                    }
                    break;
            }
            return true;
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
}
