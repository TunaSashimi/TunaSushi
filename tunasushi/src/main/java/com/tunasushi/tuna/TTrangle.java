package com.tunasushi.tuna;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import com.tuna.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;

/**
 * @author TunaSashimi
 * @date 2015-10-30 16:59
 * @Copyright 2015 TunaSashimi. All rights reserved.
 * @Description
 */
public class TTrangle extends TView {
    //trangleStrokeWidth default 0
    private float trangleStrokeWidth;

    //trangleStrokeColor default transparent
    private int trangleStrokeColor;

    // trangleBackgroundNormal default Color.WHITE,trangleBackgroundPress default trangleBackgroundNormal,trangleBackgroundSelect default trangleBackgroundNormal
    private int trangleBackgroundNormal, trangleBackgroundPress, trangleBackgroundSelect;

    @IntDef({TOP, BOTTOM, LEFT, RIGHT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface trangleMode {
    }

    public static final int TOP = 0;
    public static final int BOTTOM = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;
    private static final int[] trangleModeArray = {TOP, BOTTOM, LEFT, RIGHT,};
    private @trangleMode
    int trangleMode;

    //some draw variables
    private float trangleTopCornerDistance;
    private float trangleBottomCornerInternalDirectionDistance;
    private float trangleBoundaryLineInterceptionDistance;

    public TTrangle(Context context) {
        this(context, null);
    }

    public TTrangle(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TTrangle(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        tag = TTrangle.class.getSimpleName();

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TTrangle);

        int trangleModeIndex = typedArray.getInt(R.styleable.TTrangle_trangleMode, -1);
        if (trangleModeIndex >= 0) {
            trangleMode = trangleModeArray[trangleModeIndex];
        } else {
            throw new IllegalArgumentException("The content attribute trangleMode type must be given");
        }

        trangleBackgroundNormal = typedArray.getColor(R.styleable.TTrangle_trangleBackgroundNormal, Color.TRANSPARENT);
        trangleBackgroundPress = typedArray.getColor(R.styleable.TTrangle_trangleBackgroundPress, trangleBackgroundNormal);
        trangleBackgroundSelect = typedArray.getColor(R.styleable.TTrangle_trangleBackgroundSelect, trangleBackgroundNormal);

        trangleStrokeWidth = typedArray.getDimension(R.styleable.TTrangle_trangleStrokeWidth, 0);
        trangleStrokeColor = typedArray.getColor(R.styleable.TTrangle_trangleStrokeColor, Color.TRANSPARENT);

        typedArray.recycle();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        double trangleTopCornerHalf = 0;
        if (trangleMode == TOP || trangleMode == BOTTOM) {
            //tan(trangleTopCornerHalf)=(1/2 width)/height
            trangleTopCornerHalf = Math.atan((width * 0.5f) / height);
        } else if (trangleMode == LEFT || trangleMode == RIGHT) {
            //tan(trangleTopCornerHalf)=(1/2 height)/width
            trangleTopCornerHalf = Math.atan((height * 0.5f) / width);
        }

        //sin(trangleTopCornerHalf)=trangleStrokeWidth/trangleTopCornerDistance
        trangleTopCornerDistance = (float) (trangleStrokeWidth / Math.sin(trangleTopCornerHalf));
        //cos(trangleTopCornerHalf)=trangleStrokeWidth/bottomCornerOneCalDirectionDistance
        trangleBottomCornerInternalDirectionDistance = (float) (trangleStrokeWidth / Math.cos(trangleTopCornerHalf));
        //tan(trangleTopCornerHalf)=trangleBoundaryLineInterceptionDistance/trangleStrokeWidth
        trangleBoundaryLineInterceptionDistance = (float) (Math.tan(trangleTopCornerHalf) * trangleStrokeWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //start drawing from the outside triangle topCorner and draw clockwise
        //attention! direct coverage on some models will be the sideline, the way to avoid the use of painted area
        switch (trangleMode) {
            case TOP:
                initPathMoveTo(width >> 1, 0);
                path.lineTo(width, height);
                path.lineTo(0, height);
                break;
            case BOTTOM:
                initPathMoveTo(width >> 1, height);
                path.lineTo(0, 0);
                path.lineTo(width, 0);
                break;
            case LEFT:
                initPathMoveTo(0, height >> 1);
                path.lineTo(width, 0);
                path.lineTo(width, height);
                break;
            case RIGHT:
                initPathMoveTo(width, height >> 1);
                path.lineTo(0, height);
                path.lineTo(0, 0);
                break;
        }

        path.close();
        canvas.drawPath(path,
                initPaint(Paint.Style.FILL, trangleStrokeWidth != 0 ?
                        trangleStrokeColor : select ? trangleBackgroundSelect : press ? trangleBackgroundPress : trangleBackgroundNormal));

        //drawing  the inside triangle topCorner and draw clockwise
        path.reset();
        paint.setColor(select ? trangleBackgroundSelect : press ? trangleBackgroundPress : trangleBackgroundNormal);
        switch (trangleMode) {
            case TOP:
                path.moveTo(width >> 1, trangleTopCornerDistance);
                path.lineTo(width - (trangleBottomCornerInternalDirectionDistance + trangleBoundaryLineInterceptionDistance), height - trangleStrokeWidth);
                path.lineTo(trangleBottomCornerInternalDirectionDistance + trangleBoundaryLineInterceptionDistance, height - trangleStrokeWidth);
                break;
            case BOTTOM:
                path.moveTo(width >> 1, height - trangleTopCornerDistance);
                path.lineTo(trangleBottomCornerInternalDirectionDistance + trangleBoundaryLineInterceptionDistance, trangleStrokeWidth);
                path.lineTo(width - (trangleBottomCornerInternalDirectionDistance + trangleBoundaryLineInterceptionDistance), trangleStrokeWidth);
                break;
            case LEFT:
                path.moveTo(trangleTopCornerDistance, height >> 1);
                path.lineTo(width - trangleStrokeWidth, trangleBottomCornerInternalDirectionDistance + trangleBoundaryLineInterceptionDistance);
                path.lineTo(width - trangleStrokeWidth, height - (trangleBottomCornerInternalDirectionDistance + trangleBoundaryLineInterceptionDistance));
                break;
            case RIGHT:
                path.moveTo(width - trangleTopCornerDistance, height >> 1);
                path.lineTo(trangleStrokeWidth, height - (trangleBottomCornerInternalDirectionDistance + trangleBoundaryLineInterceptionDistance));
                path.lineTo(trangleStrokeWidth, trangleBottomCornerInternalDirectionDistance + trangleBoundaryLineInterceptionDistance);
                break;
        }
        path.close();
        canvas.drawPath(path, paint);
    }

    @Override
    public void setTouchXYRaw(float touchX, float touchY) {
        invalidate();
    }
}
