package com.tunasushi.tuna;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import com.tuna.R;
import com.tunasushi.tool.PaintTool;


import static com.tunasushi.tool.PaintTool.paint;

/**
 * @author Tunasashimi
 * @date 10/30/15 16:53
 * @Copyright 2015 Sashimi. All rights reserved.
 * @Description
 */
public class TRange extends TView {

    private String[] rangeValueArray;

    private float rangeThink;
    private int rangeColorNormal;
    private int rangeColorSelect;

    private Bitmap rangeDragLeftBitmapSrc;
    private Bitmap rangeDragRightBitmapSrc;

    private float[] rangeCircleCentreXArray;

    public TRange(Context context) {
        this(context, null);
    }

    public TRange(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TRange(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        Tag = TRange.class.getSimpleName();

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TRange);

        int rangeValueArrayId = typedArray.getResourceId(R.styleable.TRange_rangeValueArray, -1);
        if (rangeValueArrayId != -1) {
            rangeValueArray = typedArray.getResources().getStringArray(rangeValueArrayId);
            total = rangeValueArray.length;
            if (total < 2) {
                throw new IndexOutOfBoundsException("The content attribute rangeValueArray length must be at least 2");
            } else {
                rangeCircleCentreXArray = new float[total];
            }

        } else {
            throw new IllegalArgumentException("The content attribute require a property named rangeValueArray");
        }

        rangeThink = typedArray.getDimension(R.styleable.TRange_rangeThink, 0);
        if (rangeThink <= 0) {
            throw new IllegalArgumentException("The content attribute rangeThink must be greater than 0 ");
        }

        rangeColorNormal = typedArray.getColor(R.styleable.TRange_rangeColorNormal, Color.TRANSPARENT);
        rangeColorSelect = typedArray.getColor(R.styleable.TRange_rangeColorSelect, rangeColorNormal);

        //
        int rangeDragLeftBitmapSrcId = typedArray.getResourceId(R.styleable.TRange_rangeDragLeftBitmapSrc, -1);
        if (rangeDragLeftBitmapSrcId != -1) {
            rangeDragLeftBitmapSrc = BitmapFactory.decodeResource(getResources(), rangeDragLeftBitmapSrcId);
        }

        int rangeDragRightBitmapSrcId = typedArray.getResourceId(R.styleable.TRange_rangeDragRightBitmapSrc, rangeDragLeftBitmapSrcId);
        if (rangeDragRightBitmapSrcId != -1) {
            rangeDragRightBitmapSrc = BitmapFactory.decodeResource(getResources(), rangeDragRightBitmapSrcId);
        }
        typedArray.recycle();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        share = width * 1f / (total - 1);
        for (int i = 0; i < total; i++) {
            rangeCircleCentreXArray[i] = share * i;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //draw rangeColorNormal
        PaintTool.initPaint(Paint.Style.FILL, rangeColorNormal, rangeThink);
        canvas.drawLine(0, height >> 1, width, height >> 1, paint);

        // draw range dragLeft
        PaintTool.initPaint(Color.WHITE);
        canvas.drawCircle(rangeCircleCentreXArray[0] + (height >> 1), height >> 1, height >> 1, paint);

        // draw range dragRight
        canvas.drawCircle(rangeCircleCentreXArray[total - 1] - (height >> 1), height >> 1, height >> 1, paint);

        //draw rangeColorSelect
        PaintTool.initPaint(Paint.Style.FILL, rangeColorSelect, rangeThink);
        canvas.drawLine(rangeCircleCentreXArray[0] + height, height >> 1, width - height, height >> 1, paint);
    }
}
