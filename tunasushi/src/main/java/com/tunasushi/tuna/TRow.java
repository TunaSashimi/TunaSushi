package com.tunasushi.tuna;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;

import com.tuna.R;

/**
 * @author TunaSashimi
 * @date 2015-10-30 16:57
 * @Copyright 2015 TunaSashimi. All rights reserved.
 * @Description
 */
public class TRow extends TView {

    private int rowBackgroundNormal;

    public int getRowBackgroundNormal() {
        return rowBackgroundNormal;
    }

    public void setRowBackgroundNormal(int rowBackgroundNormal) {
        this.rowBackgroundNormal = rowBackgroundNormal;
    }

    private int rowDuraction;

    public int getRowDuraction() {
        return rowDuraction;
    }

    public void setRowDuraction(int rowDuraction) {
        this.rowDuraction = rowDuraction;
    }

    private RowDirection rowDirection;

    public enum RowDirection {
        TOP(0),
        BOTTOM(1),
        ;
        final int nativeInt;

        RowDirection(int ni) {
            nativeInt = ni;
        }
    }

    private static final RowDirection[] rowDirectionArray = {
            RowDirection.TOP,
            RowDirection.BOTTOM,
    };

    private int rowStopY;
    //
    private AnimatorSet rowAnimatorSet;

    public AnimatorSet getRowAnimatorSet() {
        return rowAnimatorSet;
    }

    public void setRowAnimatorSet(AnimatorSet rowAnimatorSet) {
        this.rowAnimatorSet = rowAnimatorSet;
    }

    private Property<TRow, Integer> rowStopYProperty = new Property<TRow, Integer>(Integer.class, "rowStopYProperty") {
        @Override
        public Integer get(TRow object) {
            return object.rowStopY;
        }

        @Override
        public void set(TRow object, Integer value) {
            object.rowStopY = value;
            invalidate();
        }
    };


    public TRow(Context context) {
        this(context, null);
    }

    public TRow(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TRow(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        tag = TRow.class.getSimpleName();

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TRow);

        rowBackgroundNormal = typedArray.getColor(R.styleable.TRow_rowBackgroundNormal, Color.TRANSPARENT);
        rowDuraction = typedArray.getInt(R.styleable.TRow_rowDuraction, 1000);

        int rowDirectionIndex = typedArray.getInt(R.styleable.TRow_rowDirection, 0);
        if (rowDirectionIndex >= 0) {
            rowDirection = rowDirectionArray[rowDirectionIndex];
        }
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        typedArray.recycle();
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        switch (rowDirection) {
            case TOP:
                rowStopY = 0;
                break;
            case BOTTOM:
                rowStopY = height;
                break;
        }

        initPaint(Paint.Style.FILL, rowBackgroundNormal, width);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //
        switch (rowDirection) {
            case TOP:
                canvas.drawLine(width >> 1, 0, width >> 1, rowStopY, paint);
                break;
            case BOTTOM:
                canvas.drawLine(width >> 1, height, width >> 1, rowStopY, paint);
                break;
        }
    }

    public void play() {
        rowAnimatorSet = new AnimatorSet();
        ObjectAnimator rowStopYObjectAnimator = null;
        //
        switch (rowDirection) {
            case TOP:
                rowStopYObjectAnimator = ObjectAnimator.ofInt(this, rowStopYProperty, 0, height);
                break;
            case BOTTOM:
                rowStopYObjectAnimator = ObjectAnimator.ofInt(this, rowStopYProperty, height, 0);
                break;
        }
        rowStopYObjectAnimator.setDuration(rowDuraction);
        rowAnimatorSet.playTogether(rowStopYObjectAnimator);
        rowAnimatorSet.start();
    }
}