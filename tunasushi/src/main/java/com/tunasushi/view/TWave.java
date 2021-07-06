package com.tunasushi.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DrawFilter;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PaintFlagsDrawFilter;
import android.util.AttributeSet;

import static com.tunasushi.tool.ConvertTool.dpToPx;

/**
 * @author TunaSashimi
 * @date 2015-10-30 16:59
 * @Copyright 2015 TunaSashimi. All rights reserved.
 * @Description
 */
public class TWave extends TView {
    // Wave Color
    private static final int WAVE_PAINT_COLOR = 0x880000aa;
    // y = Asin(wx+b)+h
    private static final float WAVE_STRETCH_FACTOR_A = 20;
    private static final int WAVE_OFFSET_Y = 0;
    // The first wave moving speed
    private static final int WAVE_TRANSLATE_X_SPEED_ONE = 7;
    // The second water wave moving speed
    private static final int WAVE_TRANSLATE_X_SPEED_TWO = 5;
    private float waveCycleFactorW;

    private int waveTotalWidth, waveTotalHeight;
    private float[] waveYPositions;
    private float[] waveResetOneYPositions;
    private float[] waveResetTwoYPositions;
    private int waveXOffsetSpeedOne;
    private int waveXOffsetSpeedTwo;
    private int waveXOneOffset;
    private int waveXTwoOffset;

    private Paint wavePaint;
    private DrawFilter mwaveDrawFilter;

    public TWave(Context context) {
        this(context, null);
    }

    public TWave(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TWave(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        tag = TWave.class.getSimpleName();

        waveXOffsetSpeedOne = dpToPx(WAVE_TRANSLATE_X_SPEED_ONE);
        waveXOffsetSpeedTwo = dpToPx(WAVE_TRANSLATE_X_SPEED_TWO);

        wavePaint = new Paint();
        wavePaint.setAntiAlias(true);
        wavePaint.setStyle(Style.FILL);
        wavePaint.setColor(WAVE_PAINT_COLOR);
        mwaveDrawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.setDrawFilter(mwaveDrawFilter);
        resetPositonY();
        for (int i = 0; i < waveTotalWidth; i++) {
            canvas.drawLine(i, waveTotalHeight - waveResetOneYPositions[i] - 400, i, waveTotalHeight, wavePaint);
            canvas.drawLine(i, waveTotalHeight - waveResetTwoYPositions[i] - 400, i, waveTotalHeight, wavePaint);
        }
        waveXOneOffset += waveXOffsetSpeedOne;
        waveXTwoOffset += waveXOffsetSpeedTwo;
        if (waveXOneOffset >= waveTotalWidth) {
            waveXOneOffset = 0;
        }
        if (waveXTwoOffset > waveTotalWidth) {
            waveXTwoOffset = 0;
        }
        postInvalidate();
    }

    private void resetPositonY() {
        int yOneInterval = waveYPositions.length - waveXOneOffset;
        System.arraycopy(waveYPositions, waveXOneOffset, waveResetOneYPositions, 0, yOneInterval);
        System.arraycopy(waveYPositions, 0, waveResetOneYPositions, yOneInterval, waveXOneOffset);
        int yTwoInterval = waveYPositions.length - waveXTwoOffset;
        System.arraycopy(waveYPositions, waveXTwoOffset, waveResetTwoYPositions, 0, yTwoInterval);
        System.arraycopy(waveYPositions, 0, waveResetTwoYPositions, yTwoInterval, waveXTwoOffset);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        waveTotalWidth = w;
        waveTotalHeight = h;
        waveYPositions = new float[waveTotalWidth];
        waveResetOneYPositions = new float[waveTotalWidth];
        waveResetTwoYPositions = new float[waveTotalWidth];
        waveCycleFactorW = (float) (2 * Math.PI / waveTotalWidth);
        for (int i = 0; i < waveTotalWidth; i++) {
            waveYPositions[i] = (float) (WAVE_STRETCH_FACTOR_A * Math.sin(waveCycleFactorW * i) + WAVE_OFFSET_Y);
        }
    }
}
