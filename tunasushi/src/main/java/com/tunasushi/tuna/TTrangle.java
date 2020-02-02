package com.tunasushi.tuna;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import com.tuna.R;
import com.tunasushi.tool.PaintTool;

import static com.tunasushi.tool.PaintTool.paint;
import static com.tunasushi.tool.PathTool.initPathMoveTo;
import static com.tunasushi.tool.PathTool.path;

/**
 * @author Tunasashimi
 * @date 10/30/15 16:59
 * @Copyright 2015 Sashimi. All rights reserved.
 * @Description
 */
public class TTrangle extends TView {

    //trangleStrokeWidth default 0
    private float trangleStrokeWidth;

    //trangleStrokeColor default transparent
    private int trangleStrokeColor;

    // trangleBackgroundNormal default Color.WHITE,trangleBackgroundPress default trangleBackgroundNormal,trangleBackgroundSelect default trangleBackgroundNormal
    private int trangleBackgroundNormal, trangleBackgroundPress, trangleBackgroundSelect;

    private TrangleTowardType trangleTowardType;

    public enum TrangleTowardType {
        TOP(0),
        BOTTOM(1),
        LEFT(2),
        RIGHT(3),;
        final int nativeInt;

        TrangleTowardType(int ni) {
            nativeInt = ni;
        }
    }

    private static final TrangleTowardType[] towardTypeArray = {
            TrangleTowardType.TOP,
            TrangleTowardType.BOTTOM,
            TrangleTowardType.LEFT,
            TrangleTowardType.RIGHT,
    };

    //when draw a triangle possible need hide Maximum border line default false
    private boolean trangleHideEdge;

    //some draw variables
    private double halfTopCornerDadian;
    private float topCornerDistance;
    private float bottomCornerInternalDirectionDistance;
    private float boundaryLineInterceptionDistance;

    public TTrangle(Context context) {
        this(context, null);
    }

    public TTrangle(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TTrangle(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        Tag = TTrangle.class.getSimpleName();

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TTrangle);

        int tunaTrangleTowardTypeIndex = typedArray.getInt(R.styleable.TTrangle_trangleTowardType, -1);
        if (tunaTrangleTowardTypeIndex >= 0) {
            trangleTowardType = towardTypeArray[tunaTrangleTowardTypeIndex];
        } else {
            throw new IllegalArgumentException("The content attribute trangleTowardType type must be given");
        }

        trangleBackgroundNormal = typedArray.getColor(R.styleable.TTrangle_trangleBackgroundNormal, Color.TRANSPARENT);
        trangleBackgroundPress = typedArray.getColor(R.styleable.TTrangle_trangleBackgroundPress, trangleBackgroundNormal);
        trangleBackgroundSelect = typedArray.getColor(R.styleable.TTrangle_trangleBackgroundSelect, trangleBackgroundNormal);

        trangleStrokeWidth = typedArray.getDimension(R.styleable.TTrangle_trangleStrokeWidth, 0);
        trangleStrokeColor = typedArray.getColor(R.styleable.TTrangle_trangleStrokeColor, Color.TRANSPARENT);

        trangleHideEdge = typedArray.getBoolean(R.styleable.TTrangle_trangleHideEdge, false);

        typedArray.recycle();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (trangleTowardType == TrangleTowardType.TOP || trangleTowardType == TrangleTowardType.BOTTOM) {
            //tan(halfTopCornerDadian)=(1/2tunaWidth)/height
            halfTopCornerDadian = Math.atan((width * 0.5f) / height);
        } else {
            //tan(halfTopCornerDadian)=(1/2tunaHeight)/width
            halfTopCornerDadian = Math.atan((height * 0.5f) / width);
        }

        //sin(halfTopCornerDadian)=trangleStrokeWidth/topCornerDistance
        topCornerDistance = (float) (trangleStrokeWidth / Math.sin(halfTopCornerDadian));
        //cos(halfTopCornerDadian)=trangleStrokeWidth/bottomCornerOneCalDirectionDistance
        bottomCornerInternalDirectionDistance = (float) (trangleStrokeWidth / Math.cos(halfTopCornerDadian));
        //tan(halfTopCornerDadian)=boundaryLineInterceptionDistance/trangleStrokeWidth
        boundaryLineInterceptionDistance = (float) (Math.tan(halfTopCornerDadian) * trangleStrokeWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //start drawing from the outside triangle topCorner and draw clockwise
        //attention! direct coverage on some models will be the sideline, the way to avoid the use of painted area
        switch (trangleTowardType) {
            case TOP:
                initPathMoveTo(width >> 1, 0);
                //both requirements trangleStrokeWidth != 0 and tunaTrangleHideHypotenuse=true will cut edge
                if (trangleStrokeWidth != 0 && trangleHideEdge) {
                    path.lineTo(width - boundaryLineInterceptionDistance, height - trangleStrokeWidth);
                    path.lineTo(width - (boundaryLineInterceptionDistance + bottomCornerInternalDirectionDistance), height - trangleStrokeWidth);
                    path.lineTo(width >> 1, topCornerDistance);
                    path.lineTo(boundaryLineInterceptionDistance + bottomCornerInternalDirectionDistance, height - trangleStrokeWidth);
                    path.lineTo(boundaryLineInterceptionDistance, height - trangleStrokeWidth);
                } else {
                    path.lineTo(width, height);
                    path.lineTo(0, height);
                }
                break;
            case BOTTOM:
                initPathMoveTo(width >> 1, height);
                if (trangleStrokeWidth != 0 && trangleHideEdge) {
                    path.lineTo(boundaryLineInterceptionDistance, trangleStrokeWidth);
                    path.lineTo(boundaryLineInterceptionDistance + bottomCornerInternalDirectionDistance, trangleStrokeWidth);
                    path.lineTo(width >> 1, height - topCornerDistance);
                    path.lineTo(width - (boundaryLineInterceptionDistance + bottomCornerInternalDirectionDistance), trangleStrokeWidth);
                    path.lineTo(width - boundaryLineInterceptionDistance, trangleStrokeWidth);
                } else {
                    path.lineTo(0, 0);
                    path.lineTo(width, 0);
                }
                break;
            case LEFT:
                initPathMoveTo(0, height >> 1);
                if (trangleStrokeWidth != 0 && trangleHideEdge) {
                    path.lineTo(width - trangleStrokeWidth, boundaryLineInterceptionDistance);
                    path.lineTo(width - trangleStrokeWidth, boundaryLineInterceptionDistance + bottomCornerInternalDirectionDistance);
                    path.lineTo(topCornerDistance, height >> 1);
                    path.lineTo(width - trangleStrokeWidth, height - (boundaryLineInterceptionDistance + bottomCornerInternalDirectionDistance));
                    path.lineTo(width - trangleStrokeWidth, height - boundaryLineInterceptionDistance);
                } else {
                    path.lineTo(width, 0);
                    path.lineTo(width, height);
                }
                break;
            case RIGHT:
                initPathMoveTo(width, height >> 1);
                if (trangleStrokeWidth != 0 && trangleHideEdge) {
                    path.lineTo(trangleStrokeWidth, height - boundaryLineInterceptionDistance);
                    path.lineTo(trangleStrokeWidth, height - (boundaryLineInterceptionDistance + bottomCornerInternalDirectionDistance));
                    path.lineTo(width - topCornerDistance, height >> 1);
                    path.lineTo(trangleStrokeWidth, boundaryLineInterceptionDistance + bottomCornerInternalDirectionDistance);
                    path.lineTo(trangleStrokeWidth, boundaryLineInterceptionDistance);
                } else {
                    path.lineTo(0, height);
                    path.lineTo(0, 0);
                }
                break;
        }

        path.close();
        canvas.drawPath(path,
                PaintTool.initPaint(Paint.Style.FILL, trangleStrokeWidth != 0 ?
                        trangleStrokeColor : select ? trangleBackgroundSelect : press ? trangleBackgroundPress : trangleBackgroundNormal));

        //drawing  the inside triangle topCorner and draw clockwise
        switch (trangleTowardType) {
            case TOP:
                if (trangleStrokeWidth != 0) {
                    paint.setColor(select ? trangleBackgroundSelect : press ? trangleBackgroundPress : trangleBackgroundNormal);
                    path.reset();
                    path.moveTo(width >> 1, topCornerDistance);
                    path.lineTo(width - (bottomCornerInternalDirectionDistance + boundaryLineInterceptionDistance), height - trangleStrokeWidth);
                    path.lineTo(bottomCornerInternalDirectionDistance + boundaryLineInterceptionDistance, height - trangleStrokeWidth);
                    path.close();
                    canvas.drawPath(path, paint);
                }
                break;
            case BOTTOM:
                if (trangleStrokeWidth != 0) {
                    paint.setColor(select ? trangleBackgroundSelect : press ? trangleBackgroundPress : trangleBackgroundNormal);
                    path.reset();
                    path.moveTo(width >> 1, height - topCornerDistance);
                    path.lineTo(bottomCornerInternalDirectionDistance + boundaryLineInterceptionDistance, trangleStrokeWidth);
                    path.lineTo(width - (bottomCornerInternalDirectionDistance + boundaryLineInterceptionDistance), trangleStrokeWidth);
                    path.close();
                    canvas.drawPath(path, paint);
                }
                break;
            case LEFT:
                if (trangleStrokeWidth != 0) {
                    paint.setColor(select ? trangleBackgroundSelect : press ? trangleBackgroundPress : trangleBackgroundNormal);
                    path.reset();
                    path.moveTo(topCornerDistance, height >> 1);
                    path.lineTo(width - trangleStrokeWidth, bottomCornerInternalDirectionDistance + boundaryLineInterceptionDistance);
                    path.lineTo(width - trangleStrokeWidth, height - (bottomCornerInternalDirectionDistance + boundaryLineInterceptionDistance));
                    path.close();
                    canvas.drawPath(path, paint);
                }
                break;
            case RIGHT:
                if (trangleStrokeWidth != 0) {
                    paint.setColor(select ? trangleBackgroundSelect : press ? trangleBackgroundPress : trangleBackgroundNormal);
                    path.reset();
                    path.moveTo(width - topCornerDistance, height >> 1);
                    path.lineTo(trangleStrokeWidth, height - (bottomCornerInternalDirectionDistance + boundaryLineInterceptionDistance));
                    path.lineTo(trangleStrokeWidth, bottomCornerInternalDirectionDistance + boundaryLineInterceptionDistance);
                    path.close();
                    canvas.drawPath(path, paint);
                }
                break;
        }
    }
}
