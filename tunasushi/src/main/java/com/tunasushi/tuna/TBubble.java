package com.tunasushi.tuna;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Region.Op;
import android.util.AttributeSet;


import com.tunasushi.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;


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

    private float bubbleCornerWidth;

    public float getTBubbleCornerWidth() {
        return bubbleCornerWidth;
    }

    public void setTBubbleCornerWidth(float bubbleCornerWidth) {
        this.bubbleCornerWidth = bubbleCornerWidth;
    }

    private float bubbleCornerHeight;

    public float getTBubbleCornerHeight() {
        return bubbleCornerHeight;
    }

    public void setTBubbleCornerHeight(float bubbleCornerHeight) {
        this.bubbleCornerHeight = bubbleCornerHeight;
    }

    private String bubbleText;

    public String getTBubbleText() {
        return bubbleText;
    }

    public void setTBubbleText(String bubbleText) {
        this.bubbleText = bubbleText;
        requestLayout();
    }

    private float bubbleTextSize;

    public float getTBubbleTextSize() {
        return bubbleTextSize;
    }

    public void setTBubbleTextSize(float bubbleTextSize) {
        this.bubbleTextSize = bubbleTextSize;
    }

    private int bubbleTextColor;

    private float bubbleTextPadding;

    public float getTBubbleTextPadding() {
        return bubbleTextPadding;
    }

    public void setTBubbleTextPadding(float bubbleTextPadding) {
        this.bubbleTextPadding = bubbleTextPadding;
    }

    @IntDef({TOP, BOTTOM, LEFT, RIGHT,})
    @Retention(RetentionPolicy.SOURCE)
    public @interface bubbleToward {
    }

    public static final int TOP = 0;
    public static final int BOTTOM = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;
    private static final int[] bubbleTowardArray = {TOP, BOTTOM, LEFT, RIGHT,};
    private @bubbleToward
    int bubbleToward;


    @IntDef({LOW, MIDDLE, HIGH,})
    @Retention(RetentionPolicy.SOURCE)
    public @interface bubblePosition {
    }

    public static final int LOW = 0;
    public static final int MIDDLE = 1;
    public static final int HIGH = 2;
    private static final int[] bubblePositionArray = {LOW, MIDDLE, HIGH,};
    private @bubblePosition
    int bubblePosition;


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

        bubbleCornerWidth = typedArray.getDimension(R.styleable.TBubble_bubbleCornerWidth, 0);
        bubbleCornerHeight = typedArray.getDimension(R.styleable.TBubble_bubbleCornerHeight, 0);

        bubbleText = typedArray.getString(R.styleable.TBubble_bubbleText);
        bubbleTextSize = typedArray.getDimension(R.styleable.TBubble_bubbleTextSize, textSizeDefault);
        bubbleTextColor = typedArray.getColor(R.styleable.TBubble_bubbleTextColor, textColorDefault);
        bubbleTextPadding = typedArray.getDimension(R.styleable.TBubble_bubbleTextPadding, 0);

        int bubbleTowardIndex = typedArray.getInt(R.styleable.TBubble_bubbleToward, 0);
        if (bubbleTowardIndex >= 0) {
            bubbleToward = bubbleTowardArray[bubbleTowardIndex];
        } else {
            throw new IndexOutOfBoundsException("The content attribute bubbleToward type it does not conform to the rules");
        }

        int bubblePositionIndex = typedArray.getInt(R.styleable.TBubble_bubblePosition, 0);
        if (bubblePositionIndex >= 0) {
            bubblePosition = bubblePositionArray[bubblePositionIndex];
        } else {
            throw new IndexOutOfBoundsException("The content attribute bubblePosition type it does not conform to the rules");
        }

        bubbleOffset = typedArray.getDimension(R.styleable.TBubble_bubbleOffset, 0);
        bubbleStrokeColor = typedArray.getColor(R.styleable.TBubble_bubbleStrokeColor, bubbleBackground);
        bubbleStrokeWidth = typedArray.getDimension(R.styleable.TBubble_bubbleStrokeWidth, 0);

        typedArray.recycle();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (bubbleCornerWidth <= 0 || bubbleCornerWidth + bubbleStrokeWidth * 0.5f > width) {
            throw new IndexOutOfBoundsException("The content attribute bubbleCornerWidth it does not conform to the rules");
        }
        if (bubbleCornerHeight <= 0 || bubbleCornerHeight + bubbleStrokeWidth * 0.5f > height) {
            throw new IndexOutOfBoundsException("The content attribute bubbleCornerHeight it does not conform to the rules");
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
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

        switch (bubbleToward) {
            case TOP:
                switch (bubblePosition) {
                    case LOW:
                        offsetX = ((width >> 1) - bubbleCornerWidth * 0.5f - bubbleStrokeWidth * 1) * -1;
                        offsetX += bubbleOffset;
                        break;
                    case MIDDLE:
                        offsetX += bubbleOffset;
                        break;
                    case HIGH:
                        offsetX = (width >> 1) - bubbleCornerWidth * 0.5f - bubbleStrokeWidth * 1;
                        offsetX += bubbleOffset;
                        break;
                    default:
                        break;
                }
                top = bubbleCornerHeight;

                rectTop = bubbleCornerHeight + offsetY;
                rectBottom = rectTop + bubbleStrokeWidth;
                rectLeft = (width >> 1) - bubbleCornerWidth * 0.5f + offsetX;
                rectRight = rectLeft + bubbleCornerWidth;

                inPolygonVertexX0 = (width >> 1) + offsetX;
                inPolygonVertexY0 = bubbleStrokeWidth + offsetY;
                inPolygonVertexX1 = inPolygonVertexX0 - bubbleCornerWidth * 0.5f;
                inPolygonVertexY1 = inPolygonVertexY0 + bubbleCornerHeight;
                inPolygonVertexX2 = inPolygonVertexX1;
                inPolygonVertexY2 = inPolygonVertexY1 + radius;
                inPolygonVertexX3 = inPolygonVertexX2 + bubbleCornerWidth;
                inPolygonVertexY3 = inPolygonVertexY2;
                inPolygonVertexX4 = inPolygonVertexX3;
                inPolygonVertexY4 = inPolygonVertexY1;

                outPolygonVertexX0 = (width >> 1) + offsetX;
                outPolygonVertexY0 = offsetY;
                outPolygonVertexX1 = outPolygonVertexX0 - bubbleCornerWidth * 0.5f;
                outPolygonVertexY1 = outPolygonVertexY0 + bubbleCornerHeight;
                outPolygonVertexX2 = outPolygonVertexX1;
                outPolygonVertexY2 = outPolygonVertexY1 + bubbleStrokeWidth;
                outPolygonVertexX3 = inPolygonVertexX0;
                outPolygonVertexY3 = inPolygonVertexY0;
                outPolygonVertexX4 = outPolygonVertexX2 + bubbleCornerWidth;
                outPolygonVertexY4 = outPolygonVertexY2;
                outPolygonVertexX5 = outPolygonVertexX4;
                outPolygonVertexY5 = outPolygonVertexY1;

                //
                textCenterY += bubbleCornerHeight * 0.5f;
                break;
            case BOTTOM:
                switch (bubblePosition) {
                    case LOW:
                        offsetX = ((width >> 1) - bubbleCornerWidth * 0.5f - bubbleStrokeWidth * 1) * -1;
                        offsetX += bubbleOffset;
                        break;
                    case MIDDLE:
                        offsetX += bubbleOffset;
                        break;
                    case HIGH:
                        offsetX = (width >> 1) - bubbleCornerWidth * 0.5f - bubbleStrokeWidth * 1;
                        offsetX += bubbleOffset;
                        break;
                    default:
                        break;
                }
                bottom -= bubbleCornerHeight;

                rectBottom = height - bubbleStrokeWidth + offsetY;
                rectTop = rectBottom - bubbleCornerHeight;
                rectLeft = (width >> 1) - bubbleCornerWidth * 0.5f + offsetX;
                rectRight = rectLeft + bubbleCornerWidth;

                inPolygonVertexX0 = (width >> 1) + offsetX;
                inPolygonVertexY0 = height - bubbleStrokeWidth + offsetY;
                inPolygonVertexX1 = inPolygonVertexX0 - bubbleCornerWidth * 0.5f;
                inPolygonVertexY1 = inPolygonVertexY0 - bubbleCornerHeight;
                inPolygonVertexX2 = inPolygonVertexX1;
                inPolygonVertexY2 = inPolygonVertexY1 - radius;
                inPolygonVertexX3 = inPolygonVertexX2 + bubbleCornerWidth;
                inPolygonVertexY3 = inPolygonVertexY2;
                inPolygonVertexX4 = inPolygonVertexX3;
                inPolygonVertexY4 = inPolygonVertexY1;

                outPolygonVertexX0 = (width >> 1) + offsetX;
                outPolygonVertexY0 = height + offsetY;
                outPolygonVertexX1 = outPolygonVertexX0 - bubbleCornerWidth * 0.5f;
                outPolygonVertexY1 = outPolygonVertexY0 - bubbleCornerHeight;
                outPolygonVertexX2 = outPolygonVertexX1;
                outPolygonVertexY2 = outPolygonVertexY1 - bubbleStrokeWidth;
                outPolygonVertexX3 = inPolygonVertexX0;
                outPolygonVertexY3 = inPolygonVertexY0;
                outPolygonVertexX4 = outPolygonVertexX2 + bubbleCornerWidth;
                outPolygonVertexY4 = outPolygonVertexY2;
                outPolygonVertexX5 = outPolygonVertexX4;
                outPolygonVertexY5 = outPolygonVertexY1;

                //
                textCenterY -= bubbleCornerHeight * 0.5f;
                break;
            case LEFT:
                switch (bubblePosition) {
                    case LOW:
                        offsetY = (height >> 1) - bubbleCornerWidth * 0.5f - bubbleStrokeWidth * 1;
                        offsetY += bubbleOffset;
                        break;
                    case MIDDLE:
                        offsetY += bubbleOffset;
                        break;
                    case HIGH:
                        offsetY = ((height >> 1) - bubbleCornerWidth * 0.5f - bubbleStrokeWidth * 1) * -1;
                        offsetY += bubbleOffset;
                        break;
                    default:
                        break;
                }
                left = bubbleCornerHeight;

                rectTop = (height >> 1) - bubbleCornerWidth * 0.5f + offsetY;
                rectBottom = rectTop + bubbleCornerWidth;
                rectLeft = bubbleCornerHeight + offsetX;
                rectRight = rectLeft + bubbleStrokeWidth;

                inPolygonVertexX0 = bubbleStrokeWidth + offsetX;
                inPolygonVertexY0 = (height >> 1) + offsetY;
                inPolygonVertexX1 = inPolygonVertexX0 + bubbleCornerHeight;
                inPolygonVertexY1 = inPolygonVertexY0 + bubbleCornerWidth * 0.5f;
                inPolygonVertexX2 = inPolygonVertexX1 + radius;
                inPolygonVertexY2 = inPolygonVertexY1;
                inPolygonVertexX3 = inPolygonVertexX2;
                inPolygonVertexY3 = inPolygonVertexY2 - bubbleCornerWidth;
                inPolygonVertexX4 = inPolygonVertexX1;
                inPolygonVertexY4 = inPolygonVertexY3;

                outPolygonVertexX0 = offsetX;
                outPolygonVertexY0 = (height >> 1) + offsetY;
                outPolygonVertexX1 = outPolygonVertexX0 + bubbleCornerHeight;
                outPolygonVertexY1 = outPolygonVertexY0 + bubbleCornerWidth * 0.5f;
                outPolygonVertexX2 = outPolygonVertexX1 + bubbleStrokeWidth;
                outPolygonVertexY2 = outPolygonVertexY1;
                outPolygonVertexX3 = inPolygonVertexX0;
                outPolygonVertexY3 = inPolygonVertexY0;
                outPolygonVertexX4 = outPolygonVertexX2;
                outPolygonVertexY4 = outPolygonVertexY1 - bubbleCornerWidth;
                outPolygonVertexX5 = outPolygonVertexX1;
                outPolygonVertexY5 = outPolygonVertexY4;

                //
                textCenterX += bubbleCornerHeight * 0.5f;
                break;
            case RIGHT:
                switch (bubblePosition) {
                    case LOW:
                        offsetY = ((height >> 1) - bubbleCornerWidth * 0.5f - bubbleStrokeWidth * 1) * -1;
                        offsetY += bubbleOffset;
                        break;
                    case MIDDLE:
                        offsetY += bubbleOffset;
                        break;
                    case HIGH:
                        offsetY = (height >> 1) - bubbleCornerWidth * 0.5f - bubbleStrokeWidth * 1;
                        offsetY += bubbleOffset;
                        break;
                    default:
                        break;
                }
                right -= bubbleCornerHeight;

                rectTop = (height >> 1) - bubbleCornerWidth * 0.5f + offsetY;
                rectBottom = rectTop + bubbleCornerWidth;
                rectRight = width - bubbleStrokeWidth + offsetX;
                rectLeft = rectRight - bubbleCornerHeight;

                inPolygonVertexX0 = width - bubbleStrokeWidth + offsetX;
                inPolygonVertexY0 = (height >> 1) + offsetY;
                inPolygonVertexX1 = inPolygonVertexX0 - bubbleCornerHeight;
                inPolygonVertexY1 = inPolygonVertexY0 - bubbleCornerWidth * 0.5f;
                inPolygonVertexX2 = inPolygonVertexX1 - radius;
                inPolygonVertexY2 = inPolygonVertexY1;
                inPolygonVertexX3 = inPolygonVertexX2;
                inPolygonVertexY3 = inPolygonVertexY2 + bubbleCornerWidth;
                inPolygonVertexX4 = inPolygonVertexX1;
                inPolygonVertexY4 = inPolygonVertexY3;

                outPolygonVertexX0 = width + offsetX;
                outPolygonVertexY0 = (height >> 1) + offsetY;
                outPolygonVertexX1 = outPolygonVertexX0 - bubbleCornerHeight;
                outPolygonVertexY1 = outPolygonVertexY0 - bubbleCornerWidth * 0.5f;
                outPolygonVertexX2 = outPolygonVertexX1 - bubbleStrokeWidth;
                outPolygonVertexY2 = outPolygonVertexY1;
                outPolygonVertexX3 = inPolygonVertexX0;
                outPolygonVertexY3 = inPolygonVertexY0;
                outPolygonVertexX4 = outPolygonVertexX2;
                outPolygonVertexY4 = outPolygonVertexY1 + bubbleCornerWidth;
                outPolygonVertexX5 = outPolygonVertexX1;
                outPolygonVertexY5 = outPolygonVertexY4;

                //
                textCenterX -= bubbleCornerHeight * 0.5f;
                break;
            default:
                break;
        }

        //
        canvas.save();
        canvas.clipRect(initRect((int) rectLeft, (int) rectTop, (int) rectRight, (int) rectBottom), Op.DIFFERENCE);

        //
        drawRectClassic(
                canvas,
                left,
                top,
                right,
                bottom,
                bubbleBackground,
                bubbleStrokeWidth,
                radius);
        //
        drawRectClassicStroke(
                canvas,
                left,
                top,
                right,
                bottom,
                bubbleStrokeWidth,
                bubbleStrokeColor,
                radius);
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
        if (bubbleText != null) {
            drawText(canvas, bubbleText, textWidth, textCenterX, textCenterY, 0, 0,
                    initTextPaint(Paint.Style.FILL, bubbleTextColor, bubbleTextSize, Paint.Align.CENTER));
        }
    }
}
