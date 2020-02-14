package com.tunasushi.tuna;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Region.Op;
import android.util.AttributeSet;

import com.tuna.R;


/**
 * @author TunaSashimi
 * @date 2015-10-30 16:50
 * @Copyright 2015 TunaSashimi. All rights reserved.
 * @Description
 */
public class TBubble extends TView {
    private int bubbleBackground;

    public int getTBubbleBackground() {
        return bubbleBackground;
    }

    public void setTBubbleBackground(int bubbleBackground) {
        this.bubbleBackground = bubbleBackground;
    }

    private float bubbleEdgeWidth;

    public float getTBubbleEdgeWidth() {
        return bubbleEdgeWidth;
    }

    public void setTBubbleEdgeWidth(float bubbleEdgeWidth) {
        this.bubbleEdgeWidth = bubbleEdgeWidth;
    }

    private float bubbleEdgeHeight;

    public float getTBubbleEdgeHeight() {
        return bubbleEdgeHeight;
    }

    public void setTBubbleEdgeHeight(float bubbleEdgeHeight) {
        this.bubbleEdgeHeight = bubbleEdgeHeight;
    }

    private String bubbleTextValue;

    public String getTBubbleTextValue() {
        return bubbleTextValue;
    }

    public void setTBubbleTextValue(String bubbleTextValue) {
        this.bubbleTextValue = bubbleTextValue;
        requestLayout();
    }

    private float bubbleTextSize;

    public float getTBubbleTextSize() {
        return bubbleTextSize;
    }

    public void setTBubbleTextSize(float bubbleTextSize) {
        this.bubbleTextSize = bubbleTextSize;
    }

    private int bubbleTextColorNormal;

    public int getTBubbleTextColorNormal() {
        return bubbleTextColorNormal;
    }

    public void setTBubbleTextColorNormal(int bubbleTextColorNormal) {
        this.bubbleTextColorNormal = bubbleTextColorNormal;
    }


    private float bubbleTextPadding;

    public float getTBubbleTextPadding() {
        return bubbleTextPadding;
    }

    public void setTBubbleTextPadding(float bubbleTextPadding) {
        this.bubbleTextPadding = bubbleTextPadding;
    }

    private TBubbleTowardType bubbleTowardType;

    public TBubbleTowardType getTBubbleTowardType() {
        return bubbleTowardType;
    }

    public void setTBubbleTowardType(TBubbleTowardType bubbleTowardType) {
        this.bubbleTowardType = bubbleTowardType;
    }

    public enum TBubbleTowardType {
        LEFT(0), TOP(1), RIGHT(2), BOTTOM(3),
        ;
        final int nativeInt;

        TBubbleTowardType(int ni) {
            nativeInt = ni;
        }
    }

    private static final TBubbleTowardType[] bubbleTowardTypeArray = {TBubbleTowardType.LEFT, TBubbleTowardType.TOP, TBubbleTowardType.RIGHT,
            TBubbleTowardType.BOTTOM,};

    public static TBubbleTowardType[] getTunabubbletowardtypeArray() {
        return bubbleTowardTypeArray;
    }

    private TBubbleLocationType bubbleLocationType;

    public TBubbleLocationType getTBubbleLocationType() {
        return bubbleLocationType;
    }

    public void setTBubbleTowardType(TBubbleLocationType bubbleLocationType) {
        this.bubbleLocationType = bubbleLocationType;
    }

    public enum TBubbleLocationType {
        LOW(0), MIDDLE(1), HIGH(2),
        ;
        final int nativeInt;

        TBubbleLocationType(int ni) {
            nativeInt = ni;
        }
    }

    private static final TBubbleLocationType[] bubbleLocationTypeArray = {TBubbleLocationType.LOW, TBubbleLocationType.MIDDLE, TBubbleLocationType.HIGH,};

    public static TBubbleLocationType[] getTTBubbleLocationTypeArray() {
        return bubbleLocationTypeArray;
    }

    private float bubbleOffset;

    public float getTBubbleOffset() {
        return bubbleOffset;
    }

    public void setTBubbleOffset(float bubbleOffset) {
        this.bubbleOffset = bubbleOffset;
    }

    private int bubbleStrokeColor;

    public int getTBubbleStrokeColor() {
        return bubbleStrokeColor;
    }

    public void setTBubbleStrokeColor(int bubbleStrokeColor) {
        this.bubbleStrokeColor = bubbleStrokeColor;
    }

    private float bubbleStrokeWidth;

    public float getTBubbleStrokeWidth() {
        return bubbleStrokeWidth;
    }

    public void setTBubbleStrokeWidth(float bubbleStrokeWidth) {
        this.bubbleStrokeWidth = bubbleStrokeWidth;
    }

    public TBubble(Context context) {
        this(context, null);
    }

    public TBubble(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TBubble(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        tag = TBubble.class.getSimpleName();

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TBubble);

        bubbleBackground = typedArray.getColor(R.styleable.TBubble_bubbleBackground, Color.TRANSPARENT);

        bubbleEdgeWidth = typedArray.getDimension(R.styleable.TBubble_bubbleEdgeWidth, 0);
        bubbleEdgeHeight = typedArray.getDimension(R.styleable.TBubble_bubbleEdgeHeight, 0);

        bubbleTextValue = typedArray.getString(R.styleable.TBubble_bubbleTextValue);
        bubbleTextSize = typedArray.getDimension(R.styleable.TBubble_bubbleTextSize, 0);
        bubbleTextColorNormal = typedArray.getColor(R.styleable.TBubble_bubbleTextColorNormal, Color.TRANSPARENT);
        bubbleTextPadding = typedArray.getDimension(R.styleable.TBubble_bubbleTextPadding, 0);

        int bubbleTowardTypeIndex = typedArray.getInt(R.styleable.TBubble_bubbleTowardType, 0);
        if (bubbleTowardTypeIndex >= 0) {
            bubbleTowardType = bubbleTowardTypeArray[bubbleTowardTypeIndex];
        } else {
            throw new IndexOutOfBoundsException("The content attribute bubbleTowardType type it does not conform to the rules");
        }

        int bubbleLocationTypeIndex = typedArray.getInt(R.styleable.TBubble_bubbleLocationType, 0);
        if (bubbleLocationTypeIndex >= 0) {
            bubbleLocationType = bubbleLocationTypeArray[bubbleLocationTypeIndex];
        } else {
            throw new IndexOutOfBoundsException("The content attribute bubbleLocationType type it does not conform to the rules");
        }

        bubbleOffset = typedArray.getDimension(R.styleable.TBubble_bubbleOffset, 0);
        bubbleStrokeColor = typedArray.getColor(R.styleable.TBubble_bubbleStrokeColor, bubbleBackground);
        bubbleStrokeWidth = typedArray.getDimension(R.styleable.TBubble_bubbleStrokeWidth, 0);

        typedArray.recycle();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (bubbleEdgeWidth <= 0 || bubbleEdgeWidth + bubbleStrokeWidth * 0.5f > width) {
            throw new IndexOutOfBoundsException("The content attribute bubbleEdgeWidth it does not conform to the rules");
        }
        if (bubbleEdgeHeight <= 0 || bubbleEdgeHeight + bubbleStrokeWidth * 0.5f > height) {
            throw new IndexOutOfBoundsException("The content attribute bubbleEdgeHeight it does not conform to the rules");
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //By default center offset to adjust the position!
        float left = 0;
        float top = 0;
        float right = width;
        float bottom = height;

        float rectLeft = 0;
        float rectTop = 0;
        float rectRight = 0;
        float rectBottom = 0;

        float inPolygonVertexX0 = 0;
        float inPolygonVertexY0 = 0;
        float inPolygonVertexX1 = 0;
        float inPolygonVertexY1 = 0;
        float inPolygonVertexX2 = 0;
        float inPolygonVertexY2 = 0;
        float inPolygonVertexX3 = 0;
        float inPolygonVertexY3 = 0;
        float inPolygonVertexX4 = 0;
        float inPolygonVertexY4 = 0;

        float outPolygonVertexX0 = 0;
        float outPolygonVertexY0 = 0;
        float outPolygonVertexX1 = 0;
        float outPolygonVertexY1 = 0;
        float outPolygonVertexX2 = 0;
        float outPolygonVertexY2 = 0;
        float outPolygonVertexX3 = 0;
        float outPolygonVertexY3 = 0;
        float outPolygonVertexX4 = 0;
        float outPolygonVertexY4 = 0;
        float outPolygonVertexX5 = 0;
        float outPolygonVertexY5 = 0;

        float offsetX = 0;
        float offsetY = 0;

        float textWidth = width - bubbleStrokeWidth * 2;
        float textCenterX = width >> 1;
        float textCenterY = height >> 1;

        switch (bubbleTowardType) {
            case LEFT:

                switch (bubbleLocationType) {
                    case LOW:
                        offsetY = (height >> 1) - bubbleEdgeWidth * 0.5f - bubbleStrokeWidth * 1;
                        offsetY += bubbleOffset;
                        break;
                    case MIDDLE:
                        offsetY += bubbleOffset;
                        break;
                    case HIGH:
                        offsetY = ((height >> 1) - bubbleEdgeWidth * 0.5f - bubbleStrokeWidth * 1) * -1;
                        offsetY += bubbleOffset;
                        break;
                    default:
                        break;
                }
                left = bubbleEdgeHeight;

                rectTop = (height >> 1) - bubbleEdgeWidth * 0.5f + offsetY;
                rectBottom = rectTop + bubbleEdgeWidth;
                rectLeft = bubbleEdgeHeight + offsetX;
                rectRight = rectLeft + bubbleStrokeWidth;

                inPolygonVertexX0 = bubbleStrokeWidth + offsetX;
                inPolygonVertexY0 = (height >> 1) + offsetY;
                inPolygonVertexX1 = inPolygonVertexX0 + bubbleEdgeHeight;
                inPolygonVertexY1 = inPolygonVertexY0 + bubbleEdgeWidth * 0.5f;
                inPolygonVertexX2 = inPolygonVertexX1 + radius;
                inPolygonVertexY2 = inPolygonVertexY1;
                inPolygonVertexX3 = inPolygonVertexX2;
                inPolygonVertexY3 = inPolygonVertexY2 - bubbleEdgeWidth;
                inPolygonVertexX4 = inPolygonVertexX1;
                inPolygonVertexY4 = inPolygonVertexY3;

                outPolygonVertexX0 = offsetX;
                outPolygonVertexY0 = (height >> 1) + offsetY;
                outPolygonVertexX1 = outPolygonVertexX0 + bubbleEdgeHeight;
                outPolygonVertexY1 = outPolygonVertexY0 + bubbleEdgeWidth * 0.5f;
                outPolygonVertexX2 = outPolygonVertexX1 + bubbleStrokeWidth;
                outPolygonVertexY2 = outPolygonVertexY1;
                outPolygonVertexX3 = inPolygonVertexX0;
                outPolygonVertexY3 = inPolygonVertexY0;
                outPolygonVertexX4 = outPolygonVertexX2;
                outPolygonVertexY4 = outPolygonVertexY1 - bubbleEdgeWidth;
                outPolygonVertexX5 = outPolygonVertexX1;
                outPolygonVertexY5 = outPolygonVertexY4;

                //
                textCenterX += bubbleEdgeHeight * 0.5f;

                break;

            case TOP:
                switch (bubbleLocationType) {
                    case LOW:
                        offsetX = ((width >> 1) - bubbleEdgeWidth * 0.5f - bubbleStrokeWidth * 1) * -1;
                        offsetX += bubbleOffset;
                        break;
                    case MIDDLE:
                        offsetX += bubbleOffset;
                        break;
                    case HIGH:
                        offsetX = (width >> 1) - bubbleEdgeWidth * 0.5f - bubbleStrokeWidth * 1;
                        offsetX += bubbleOffset;
                        break;
                    default:
                        break;
                }
                top = bubbleEdgeHeight;

                rectTop = bubbleEdgeHeight + offsetY;
                rectBottom = rectTop + bubbleStrokeWidth;
                rectLeft = (width >> 1) - bubbleEdgeWidth * 0.5f + offsetX;
                rectRight = rectLeft + bubbleEdgeWidth;

                inPolygonVertexX0 = (width >> 1) + offsetX;
                inPolygonVertexY0 = bubbleStrokeWidth + offsetY;
                inPolygonVertexX1 = inPolygonVertexX0 - bubbleEdgeWidth * 0.5f;
                inPolygonVertexY1 = inPolygonVertexY0 + bubbleEdgeHeight;
                inPolygonVertexX2 = inPolygonVertexX1;
                inPolygonVertexY2 = inPolygonVertexY1 + radius;
                inPolygonVertexX3 = inPolygonVertexX2 + bubbleEdgeWidth;
                inPolygonVertexY3 = inPolygonVertexY2;
                inPolygonVertexX4 = inPolygonVertexX3;
                inPolygonVertexY4 = inPolygonVertexY1;

                outPolygonVertexX0 = (width >> 1) + offsetX;
                outPolygonVertexY0 = offsetY;
                outPolygonVertexX1 = outPolygonVertexX0 - bubbleEdgeWidth * 0.5f;
                outPolygonVertexY1 = outPolygonVertexY0 + bubbleEdgeHeight;
                outPolygonVertexX2 = outPolygonVertexX1;
                outPolygonVertexY2 = outPolygonVertexY1 + bubbleStrokeWidth;
                outPolygonVertexX3 = inPolygonVertexX0;
                outPolygonVertexY3 = inPolygonVertexY0;
                outPolygonVertexX4 = outPolygonVertexX2 + bubbleEdgeWidth;
                outPolygonVertexY4 = outPolygonVertexY2;
                outPolygonVertexX5 = outPolygonVertexX4;
                outPolygonVertexY5 = outPolygonVertexY1;

                //
                textCenterY += bubbleEdgeHeight * 0.5f;

                break;

            case RIGHT:
                switch (bubbleLocationType) {
                    case LOW:
                        offsetY = ((height >> 1) - bubbleEdgeWidth * 0.5f - bubbleStrokeWidth * 1) * -1;
                        offsetY += bubbleOffset;
                        break;
                    case MIDDLE:
                        offsetY += bubbleOffset;
                        break;
                    case HIGH:
                        offsetY = (height >> 1) - bubbleEdgeWidth * 0.5f - bubbleStrokeWidth * 1;
                        offsetY += bubbleOffset;
                        break;
                    default:
                        break;
                }
                right -= bubbleEdgeHeight;

                rectTop = (height >> 1) - bubbleEdgeWidth * 0.5f + offsetY;
                rectBottom = rectTop + bubbleEdgeWidth;
                rectRight = width - bubbleStrokeWidth + offsetX;
                rectLeft = rectRight - bubbleEdgeHeight;

                inPolygonVertexX0 = width - bubbleStrokeWidth + offsetX;
                inPolygonVertexY0 = (height >> 1) + offsetY;
                inPolygonVertexX1 = inPolygonVertexX0 - bubbleEdgeHeight;
                inPolygonVertexY1 = inPolygonVertexY0 - bubbleEdgeWidth * 0.5f;
                inPolygonVertexX2 = inPolygonVertexX1 - radius;
                inPolygonVertexY2 = inPolygonVertexY1;
                inPolygonVertexX3 = inPolygonVertexX2;
                inPolygonVertexY3 = inPolygonVertexY2 + bubbleEdgeWidth;
                inPolygonVertexX4 = inPolygonVertexX1;
                inPolygonVertexY4 = inPolygonVertexY3;

                outPolygonVertexX0 = width + offsetX;
                outPolygonVertexY0 = (height >> 1) + offsetY;
                outPolygonVertexX1 = outPolygonVertexX0 - bubbleEdgeHeight;
                outPolygonVertexY1 = outPolygonVertexY0 - bubbleEdgeWidth * 0.5f;
                outPolygonVertexX2 = outPolygonVertexX1 - bubbleStrokeWidth;
                outPolygonVertexY2 = outPolygonVertexY1;
                outPolygonVertexX3 = inPolygonVertexX0;
                outPolygonVertexY3 = inPolygonVertexY0;
                outPolygonVertexX4 = outPolygonVertexX2;
                outPolygonVertexY4 = outPolygonVertexY1 + bubbleEdgeWidth;
                outPolygonVertexX5 = outPolygonVertexX1;
                outPolygonVertexY5 = outPolygonVertexY4;

                //
                textCenterX -= bubbleEdgeHeight * 0.5f;

                break;

            case BOTTOM:
                switch (bubbleLocationType) {
                    case LOW:
                        offsetX = ((width >> 1) - bubbleEdgeWidth * 0.5f - bubbleStrokeWidth * 1) * -1;
                        offsetX += bubbleOffset;
                        break;
                    case MIDDLE:
                        offsetX += bubbleOffset;
                        break;
                    case HIGH:
                        offsetX = (width >> 1) - bubbleEdgeWidth * 0.5f - bubbleStrokeWidth * 1;
                        offsetX += bubbleOffset;
                        break;
                    default:
                        break;
                }
                bottom -= bubbleEdgeHeight;

                rectBottom = height - bubbleStrokeWidth + offsetY;
                rectTop = rectBottom - bubbleEdgeHeight;
                rectLeft = (width >> 1) - bubbleEdgeWidth * 0.5f + offsetX;
                rectRight = rectLeft + bubbleEdgeWidth;

                inPolygonVertexX0 = (width >> 1) + offsetX;
                inPolygonVertexY0 = height - bubbleStrokeWidth + offsetY;
                inPolygonVertexX1 = inPolygonVertexX0 - bubbleEdgeWidth * 0.5f;
                inPolygonVertexY1 = inPolygonVertexY0 - bubbleEdgeHeight;
                inPolygonVertexX2 = inPolygonVertexX1;
                inPolygonVertexY2 = inPolygonVertexY1 - radius;
                inPolygonVertexX3 = inPolygonVertexX2 + bubbleEdgeWidth;
                inPolygonVertexY3 = inPolygonVertexY2;
                inPolygonVertexX4 = inPolygonVertexX3;
                inPolygonVertexY4 = inPolygonVertexY1;

                outPolygonVertexX0 = (width >> 1) + offsetX;
                outPolygonVertexY0 = height + offsetY;
                outPolygonVertexX1 = outPolygonVertexX0 - bubbleEdgeWidth * 0.5f;
                outPolygonVertexY1 = outPolygonVertexY0 - bubbleEdgeHeight;
                outPolygonVertexX2 = outPolygonVertexX1;
                outPolygonVertexY2 = outPolygonVertexY1 - bubbleStrokeWidth;
                outPolygonVertexX3 = inPolygonVertexX0;
                outPolygonVertexY3 = inPolygonVertexY0;
                outPolygonVertexX4 = outPolygonVertexX2 + bubbleEdgeWidth;
                outPolygonVertexY4 = outPolygonVertexY2;
                outPolygonVertexX5 = outPolygonVertexX4;
                outPolygonVertexY5 = outPolygonVertexY1;

                //
                textCenterY -= bubbleEdgeHeight * 0.5f;

                break;
            default:
                break;
        }

        //
        canvas.save();
        canvas.clipRect(initRect((int) rectLeft, (int) rectTop, (int) rectRight, (int) rectBottom), Op.DIFFERENCE);
        drawRectClassic(canvas, left, top, right, bottom, bubbleBackground, bubbleStrokeWidth, bubbleStrokeColor, radius);
        canvas.restore();

        //
        initPathMoveTo(inPolygonVertexX0, inPolygonVertexY0);
        path.lineTo(inPolygonVertexX1, inPolygonVertexY1);
        path.lineTo(inPolygonVertexX2, inPolygonVertexY2);
        path.lineTo(inPolygonVertexX3, inPolygonVertexY3);
        path.lineTo(inPolygonVertexX4, inPolygonVertexY4);
        path.close();
        canvas.drawPath(path, initPaint(Paint.Style.FILL, bubbleBackground));

        //
        initPathMoveTo(outPolygonVertexX0, outPolygonVertexY0);
        path.lineTo(outPolygonVertexX1, outPolygonVertexY1);
        path.lineTo(outPolygonVertexX2, outPolygonVertexY2);
        path.lineTo(outPolygonVertexX3, outPolygonVertexY3);
        path.lineTo(outPolygonVertexX4, outPolygonVertexY4);
        path.lineTo(outPolygonVertexX5, outPolygonVertexY5);
        path.close();
        canvas.drawPath(path, initPaint(Paint.Style.FILL, bubbleStrokeColor));

        //
        if (bubbleTextValue != null) {
            drawText(canvas, bubbleTextValue, textWidth, textCenterX, textCenterY, 0, 0,
                    initTextPaint(Paint.Style.FILL, bubbleTextColorNormal, bubbleTextSize, Paint.Align.CENTER));
        }
    }
}
