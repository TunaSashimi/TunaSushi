package com.tunasushi.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import com.tunasushi.R;


/**
 * @author TunaSashimi
 * @date 2020-02-13 13:50
 * @Copyright 2020 TunaSashimi. All rights reserved.
 * @Description
 */
public class TRange extends TView {
    private String[] rangeTextArray;

    private float rangeThick;

    private int rangeColorNormal;
    private int rangeColorSelect;

    private int rangeDragColor;
    private float rangeDragStrokeWidth;
    private int rangeDragStrokeColor;

    private int rangeDragIndexLeft;

    public int getRangeDragIndexLeft() {
        return rangeDragIndexLeft;
    }

    public void setRangeDragIndexLeft(int rangeDragIndexLeft) {
        this.rangeDragIndexLeft = rangeDragIndexLeft;
    }

    private int rangeDragIndexRight;

    public int getRangeDragIndexRight() {
        return rangeDragIndexRight;
    }

    public void setRangeDragIndexRight(int rangeDragIndexRight) {
        this.rangeDragIndexRight = rangeDragIndexRight;
    }

    private Bitmap rangeDragSrcLeft;
    private Bitmap rangeDragSrcRight;
    //
    private float rangeCircleCentreX;

    public float getRangeCircleCentreX() {
        return rangeCircleCentreX;
    }

    public void setRangeCircleCentreX(float rangeCircleCentreX) {
        this.rangeCircleCentreX = rangeCircleCentreX;
    }

    //
    private String rangeText;

    public String getRangeText() {
        if (dx > 0) {
            return rangeTextArray[rangeDragIndexRight];
        } else {
            return rangeTextArray[rangeDragIndexLeft];
        }
    }

    public void setRangeText(String rangeText) {
        this.rangeText = rangeText;
    }

    public String getRangeTextLeft() {
        return rangeTextArray[rangeDragIndexLeft];
    }

    public String getRangeTextRight() {
        return rangeTextArray[rangeDragIndexRight];
    }

    public void reset() {
        rangeDragIndexLeft = 0;
        rangeDragIndexRight = total - 1;
        invalidate();
    }

    public TRange(Context context) {
        this(context, null);
    }

    public TRange(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TRange(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        tag = TRange.class.getSimpleName();

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TRange);

        int rangeValueArrayId = typedArray.getResourceId(R.styleable.TRange_rangeValueArray, -1);
        if (rangeValueArrayId != -1) {
            rangeTextArray = typedArray.getResources().getStringArray(rangeValueArrayId);
            total = rangeTextArray.length;
            if (total < 2) {
                throw new IndexOutOfBoundsException("The content attribute rangeTextArray length must be at least 2");
            } else {
                floatArray = new float[total];
            }
        } else {
            throw new IllegalArgumentException("The content attribute require a property named rangeTextArray");
        }

        rangeThick = typedArray.getDimension(R.styleable.TRange_rangeThick, 0);
        if (rangeThick <= 0) {
            throw new IllegalArgumentException("The content attribute rangeThick must be greater than 0 ");
        }

        rangeColorNormal = typedArray.getColor(R.styleable.TRange_rangeColorNormal, Color.TRANSPARENT);
        rangeColorSelect = typedArray.getColor(R.styleable.TRange_rangeColorSelect, rangeColorNormal);

        rangeDragColor = typedArray.getColor(R.styleable.TRange_rangeDragColor, Color.TRANSPARENT);
        rangeDragStrokeWidth = typedArray.getDimension(R.styleable.TRange_rangeDragStrokeWidth, 0);
        rangeDragStrokeColor = typedArray.getColor(R.styleable.TRange_rangeDragStrokeColor, Color.TRANSPARENT);

        //0
        rangeDragIndexLeft = typedArray.getInt(R.styleable.TRange_rangeDragIndexLeft, 0);
        if (rangeDragIndexLeft < 0 || rangeDragIndexLeft > total - 1) {
            throw new IndexOutOfBoundsException("The content attribute rangeDragIndexLeft must be no less than 0 and no greater than rangeTextArray length");
        }

        //rangeTextArray.length - 1
        rangeDragIndexRight = typedArray.getInt(R.styleable.TRange_rangeDragIndexRight, total - 1);
        if (rangeDragIndexRight < 0 || rangeDragIndexRight > total - 1) {
            throw new IndexOutOfBoundsException("The content attribute rangeDragIndexRight must be no less than 0 and no greater than rangeTextArray length");
        }

        //
        int rangeDragSrcLeftId = typedArray.getResourceId(R.styleable.TRange_rangeDragSrcLeft, -1);
        if (rangeDragSrcLeftId != -1) {
            rangeDragSrcLeft = BitmapFactory.decodeResource(getResources(), rangeDragSrcLeftId);
        }

        int rangeDragSrcRightId = typedArray.getResourceId(R.styleable.TRange_rangeDragSrcRight, rangeDragSrcLeftId);
        if (rangeDragSrcRightId != -1) {
            rangeDragSrcRight = BitmapFactory.decodeResource(getResources(), rangeDragSrcRightId);
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
            floatArray[i] = share * i + (height >> 1);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //draw rangeColorNormal
        initPaint(Paint.Style.FILL, rangeColorNormal, rangeThick);
        canvas.drawLine(0, height >> 1, width, height >> 1, paint);

        // draw range dragLeft
        initPaint(Paint.Style.FILL, rangeDragColor);

        // Paint.Style.FILL
        canvas.drawCircle(floatArray[rangeDragIndexLeft], height >> 1, (height >> 1) - rangeDragStrokeWidth, paint);

        if (rangeDragStrokeWidth > 0) {
            initPaint(Paint.Style.STROKE, rangeDragStrokeColor, rangeDragStrokeWidth);
            //Paint.Style.STROKE is Diverge to both sides
            canvas.drawCircle(floatArray[rangeDragIndexLeft], height >> 1, (height >> 1) - rangeDragStrokeWidth / 2, paint);
        }

        // draw range dragRight
        initPaint(Paint.Style.FILL, rangeDragColor);
        canvas.drawCircle(floatArray[rangeDragIndexRight], height >> 1, (height >> 1) - rangeDragStrokeWidth, paint);

        if (rangeDragStrokeWidth > 0) {
            initPaint(Paint.Style.STROKE, rangeDragStrokeColor, rangeDragStrokeWidth);
            canvas.drawCircle(floatArray[rangeDragIndexRight], height >> 1, (height >> 1) - rangeDragStrokeWidth / 2, paint);
        }

        //draw rangeColorSelect
        initPaint(Paint.Style.FILL, rangeColorSelect, rangeThick);
        canvas.drawLine(floatArray[rangeDragIndexLeft] + (height >> 1), height >> 1,
                floatArray[rangeDragIndexRight] - (height >> 1), height >> 1, paint);
    }

    @Override
    public void setTouchXYRaw(float touchX, float touchY) {
        x = touchX;
        float distenceMin = width;
        if (Math.abs(x - floatArray[rangeDragIndexLeft]) <= Math.abs(x - floatArray[rangeDragIndexRight])) {
            dx = -1;
        } else {
            dx = 1;
        }
        for (int i = 0; i < total; i++) {
            float circlecentreDx = Math.abs(x - floatArray[i]);
            if (circlecentreDx < distenceMin) {
                if (dx > 0) {
                    rangeDragIndexRight = i;
                    rangeCircleCentreX = floatArray[rangeDragIndexRight];
                } else {
                    rangeDragIndexLeft = i;
                    rangeCircleCentreX = floatArray[rangeDragIndexLeft];
                }
                distenceMin = circlecentreDx;
            } else {
                break;
            }
        }
        invalidate();
    }
}
