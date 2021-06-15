package com.tunasushi.tuna;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.CountDownTimer;
import android.util.AttributeSet;

import com.tunasushi.R;


/**
 * @author TunaSashimi
 * @date 2020-03-05 13:59
 * @Copyright 2020 TunaSashimi. All rights reserved.
 * @Description
 */
public class TLoad extends TView {
    private int loadTime;
    private int loadColorOut;
    private int loadColorIn;
    private float loadThickOut;
    private float loadThickIn;
    private float loadAngleOut = 300f;
    private float loadAngleIn = 60f;
    private float loadAngleStart;
    private float loadAngleCurrent;

    //Execute every 10 ms
    private CountDownTimer loadCountDownTimer;

    public TLoad(Context context) {
        this(context, null);
    }

    public TLoad(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TLoad(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        tag = TLoad.class.getSimpleName();

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TLoad);

        loadTime = typedArray.getInt(R.styleable.TLoad_loadTime, 1500);
        loadColorOut = typedArray.getColor(R.styleable.TLoad_loadColorOut, Color.BLACK);
        loadColorIn = typedArray.getColor(R.styleable.TLoad_loadColorIn, Color.BLACK);

        loadThickOut = typedArray.getDimension(R.styleable.TLoad_loadThickOut, 15);
        loadThickIn = typedArray.getDimension(R.styleable.TLoad_loadThickIn, 15);

        loadAngleStart = typedArray.getDimension(R.styleable.TLoad_loadThickIn, 105);

        if (loadCountDownTimer != null) {
            loadCountDownTimer.cancel();
            loadCountDownTimer.onFinish();
            loadCountDownTimer = null;
        }
        typedArray.recycle();

        //
        loadCountDownTimer = new CountDownTimer(loadTime, 10) {
            @Override
            public void onTick(long millisUntilFinished) {
                float radio = (float) millisUntilFinished / (float) loadTime;
                float addAngle = 360 - 360 * radio;
                loadAngleCurrent = loadAngleStart;
                loadAngleCurrent = loadAngleStart + addAngle;
                invalidate();
            }

            @Override
            public void onFinish() {
                if (loadCountDownTimer != null) {
                    loadCountDownTimer.start();
                }
            }
        };
        loadCountDownTimer.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        initPaint(Paint.Style.STROKE, loadColorOut, loadThickOut);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawPath(initPathArc(10, 10, width - 10, height - 10, loadAngleCurrent, loadAngleOut), paint);

        initPaint(Paint.Style.STROKE, loadColorIn, loadThickIn);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawArc(30 + loadThickOut, 30 + loadThickOut, width - (30 + loadThickOut), height - (30 + loadThickOut), (360 - loadAngleCurrent), -loadAngleIn, false, paint);
    }

    public void finish() {
        if (loadCountDownTimer != null) {
            loadCountDownTimer.onFinish();
            loadCountDownTimer.cancel();
            loadCountDownTimer = null;
        }
    }
}
