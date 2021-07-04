package com.tunasushi.view;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DrawFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;

/**
 * @author TunaSashimi
 * @date 2020-02-12 20:06
 * @Copyright 2020 TunaSashimi. All rights reserved.
 * @Description
 */
public class TPattern extends TView {
    private final Shader mShader1;
    private final Shader mShader2;
    private final Paint mPaint;

    private float mTouchCurrentX;
    private float mTouchCurrentY;
    private DrawFilter mDF;

    public TPattern(Context context) {
        this(context, null);
    }

    public TPattern(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TPattern(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        tag = TPattern.class.getSimpleName();

        setFocusable(true);
        setFocusableInTouchMode(true);

        mShader1 = new BitmapShader(makeBitmap1(), Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        mShader2 = new BitmapShader(makeBitmap2(), Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);

        Matrix m = new Matrix();
        m.setRotate(30);
        mShader2.setLocalMatrix(m);

        mPaint = new Paint(Paint.FILTER_BITMAP_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.setDrawFilter(mDF);

        mPaint.setShader(mShader1);
        canvas.drawPaint(mPaint);

        canvas.translate(mTouchCurrentX, mTouchCurrentY);

        mPaint.setShader(mShader2);
        canvas.drawPaint(mPaint);
    }

    private static Bitmap makeBitmap1() {
        Bitmap bm = Bitmap.createBitmap(40, 40, Bitmap.Config.RGB_565);
        Canvas c = new Canvas(bm);
        c.drawColor(Color.RED);
        Paint p = new Paint();
        p.setColor(Color.BLUE);
        c.drawRect(5, 5, 35, 35, p);
        return bm;
    }

    private static Bitmap makeBitmap2() {
        Bitmap bm = Bitmap.createBitmap(64, 64, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bm);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(Color.GREEN);
        p.setAlpha(0xCC);
        c.drawCircle(32, 32, 27, p);
        return bm;
    }

    @Override
    public void setTouchXY(float touchX, float touchY) {
        if (press) {
            mTouchCurrentX = touchX;
            mTouchCurrentY = touchY;
        } else if (touchUp) {
            mDF = null;
        }
        invalidate();
    }
}
