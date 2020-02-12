package com.tunasushi.tuna;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DrawFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Shader;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author Tunasashimi
 * @date 2020-02-12 20:06
 * @Copyright 2020 TunaSashimi. All rights reserved.
 * @Description
 */
public class TPattern extends View {
    private final Shader mShader1;
    private final Shader mShader2;
    private final Paint mPaint;
    private final DrawFilter mFastDF;

    private float mTouchStartX;
    private float mTouchStartY;
    private float mTouchCurrX;
    private float mTouchCurrY;
    private DrawFilter mDF;

    public TPattern(Context context) {
        super(context);
        setFocusable(true);
        setFocusableInTouchMode(true);

        mFastDF = new PaintFlagsDrawFilter(Paint.FILTER_BITMAP_FLAG | Paint.DITHER_FLAG, 0);

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

        canvas.translate(mTouchCurrX - mTouchStartX,
                mTouchCurrY - mTouchStartY);

        mPaint.setShader(mShader2);
        canvas.drawPaint(mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mTouchStartX = mTouchCurrX = x;
                mTouchStartY = mTouchCurrY = y;
                mDF = mFastDF;
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                mTouchCurrX = x;
                mTouchCurrY = y;
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                mDF = null;
                invalidate();
                break;
        }
        return true;
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

}
