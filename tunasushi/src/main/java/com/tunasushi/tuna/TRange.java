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
    private float rangeThink;

    private int rangeColorNormal;
    private int rangeColorSelect;

    private String[] rangeValueArray;

    private int rangeDragColor;
    private int rangeDragStrokeColor;
    private float rangeDragStrokeWidth;

    private int rangeDragLeftIndex;
    public int getRangeDragLeftIndex() {
        return rangeDragLeftIndex;
    }
    public void setRangeDragLeftIndex(int rangeDragLeftIndex) {
        this.rangeDragLeftIndex = rangeDragLeftIndex;
    }

    private int rangeDragRightIndex;
    public int getRangeDragRightIndex() {
        return rangeDragRightIndex;
    }
    public void setRangeDragRightIndex(int rangeDragRightIndex) {
        this.rangeDragRightIndex = rangeDragRightIndex;
    }

    private Bitmap rangeDragLeftBitmapSrc;
    private Bitmap rangeDragRightBitmapSrc;

    //
    private float[] rangeCircleCentreXArray;

    // Which circle is near
    boolean rangeTouchNearLeft = false;

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

        rangeThink = typedArray.getDimension(R.styleable.TRange_rangeThink, 0);
        if (rangeThink <= 0) {
            throw new IllegalArgumentException("The content attribute rangeThink must be greater than 0 ");
        }

        rangeColorNormal = typedArray.getColor(R.styleable.TRange_rangeColorNormal, Color.TRANSPARENT);
        rangeColorSelect = typedArray.getColor(R.styleable.TRange_rangeColorSelect, rangeColorNormal);

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

        rangeDragColor = typedArray.getColor(R.styleable.TRange_rangeDragColor, Color.TRANSPARENT);
        rangeDragStrokeColor = typedArray.getColor(R.styleable.TRange_rangeDragStrokeColor, Color.TRANSPARENT);
        rangeDragStrokeWidth = typedArray.getDimension(R.styleable.TRange_rangeDragStrokeWidth, 0);

        //0
        rangeDragLeftIndex = typedArray.getInt(R.styleable.TRange_rangeDragLeftIndex, 0);
        if (rangeDragLeftIndex < 0 || rangeDragLeftIndex > total - 1) {
            throw new IndexOutOfBoundsException("The content attribute rangeDragLeftIndex must be no less than 0 and no greater than rangeValueArray length");
        }

        //rangeValueArray.length - 1
        rangeDragRightIndex = typedArray.getInt(R.styleable.TRange_rangeDragRightIndex, total - 1);
        if (rangeDragRightIndex < 0 || rangeDragRightIndex > total - 1) {
            throw new IndexOutOfBoundsException("The content attribute rangeDragRightIndex must be no less than 0 and no greater than rangeValueArray length");
        }

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

        surplus = width - height;
        if (surplus <= 0) {
            throw new IndexOutOfBoundsException("The content attribute width must be greater than height");
        } else {
            share = surplus * 1f / (total - 1);
        }
        for (int i = 0; i < total; i++) {
            rangeCircleCentreXArray[i] = share * i + (height >> 1);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //draw rangeColorNormal
        PaintTool.initPaint(Paint.Style.FILL, rangeColorNormal, rangeThink);
        canvas.drawLine(0, height >> 1, width, height >> 1, paint);

        // draw range dragLeft
        PaintTool.initPaint(Paint.Style.FILL, rangeDragColor);
        // Paint.Style.FILL
        canvas.drawCircle(rangeCircleCentreXArray[rangeDragLeftIndex], height >> 1, (height >> 1) - rangeDragStrokeWidth, paint);

        if (rangeDragStrokeWidth > 0) {
            PaintTool.initPaint(Paint.Style.STROKE, rangeDragStrokeColor, rangeDragStrokeWidth);
            //Paint.Style.STROKE is Diverge to both sides
            canvas.drawCircle(rangeCircleCentreXArray[rangeDragLeftIndex], height >> 1, (height >> 1) - rangeDragStrokeWidth / 2, paint);
        }

        // draw range dragRight
        PaintTool.initPaint(Paint.Style.FILL, rangeDragColor);
        canvas.drawCircle(rangeCircleCentreXArray[rangeDragRightIndex], height >> 1, (height >> 1) - rangeDragStrokeWidth, paint);

        if (rangeDragStrokeWidth > 0) {
            PaintTool.initPaint(Paint.Style.STROKE, rangeDragStrokeColor, rangeDragStrokeWidth);
            canvas.drawCircle(rangeCircleCentreXArray[rangeDragRightIndex], height >> 1, (height >> 1) - rangeDragStrokeWidth / 2, paint);
        }

        //draw rangeColorSelect
        PaintTool.initPaint(Paint.Style.FILL, rangeColorSelect, rangeThink);
        canvas.drawLine(rangeCircleCentreXArray[rangeDragLeftIndex] + (height >> 1), height >> 1,
                rangeCircleCentreXArray[rangeDragRightIndex] - (height >> 1), height >> 1, paint);
    }

    @Override
    public void setXRaw(float x) {
        this.x = x;
        float minDistence = width;

        //Which circle is close
        if (Math.abs(x - rangeCircleCentreXArray[rangeDragLeftIndex]) <= Math.abs(x - rangeCircleCentreXArray[rangeDragRightIndex])) {
            rangeTouchNearLeft = true;
        } else {
            rangeTouchNearLeft = false;
        }

        // Which interval is close
        for (int i = 0; i < total; i++) {
            float circleCentreDistance = Math.abs(x - rangeCircleCentreXArray[i]);
            if (circleCentreDistance < minDistence) {
                if (rangeTouchNearLeft) {
                    rangeDragLeftIndex = i;
                } else {
                    rangeDragRightIndex = i;
                }
                minDistence = circleCentreDistance;
            } else {
                break;
            }
        }
    }
}
