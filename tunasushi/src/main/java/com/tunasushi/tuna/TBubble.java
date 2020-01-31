package com.tunasushi.tuna;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Region.Op;
import android.util.AttributeSet;

import com.tuna.R;

import static com.tunasushi.tool.PaintTool.initTunaPaint;
import static com.tunasushi.tool.PaintTool.initTunaTextPaint;

/**
 * @author Tunasashimi
 * @date 10/30/15 16:50
 * @Copyright 2015 TunaSashimi. All rights reserved.
 * @Description
 */
public class TBubble extends TView {

    private int tunaBubbleBackground;

    public int getTunaBubbleBackground() {
        return tunaBubbleBackground;
    }

    public void setTunaBubbleBackground(int tunaBubbleBackground) {
        this.tunaBubbleBackground = tunaBubbleBackground;
    }

    private float tunaBubbleRadius;

    public float getTunaBubbleRadius() {
        return tunaBubbleRadius;
    }

    public void setTunaBubbleRadius(float tunaBubbleRadius) {
        this.tunaBubbleRadius = tunaBubbleRadius;
    }

    private float tunaBubbleEdgeWidth;

    public float getTunaBubbleEdgeWidth() {
        return tunaBubbleEdgeWidth;
    }

    public void setTunaBubbleEdgeWidth(float tunaBubbleEdgeWidth) {
        this.tunaBubbleEdgeWidth = tunaBubbleEdgeWidth;
    }

    private float tunaBubbleEdgeHeight;

    public float getTunaBubbleEdgeHeight() {
        return tunaBubbleEdgeHeight;
    }

    public void setTunaBubbleEdgeHeight(float tunaBubbleEdgeHeight) {
        this.tunaBubbleEdgeHeight = tunaBubbleEdgeHeight;
    }

    private String tunaBubbleTextValue;

    public String getTunaBubbleTextValue() {
        return tunaBubbleTextValue;
    }

    public void setTunaBubbleTextValue(String tunaBubbleTextValue) {
        this.tunaBubbleTextValue = tunaBubbleTextValue;
        requestLayout();
    }

    private float tunaBubbleTextSize;

    public float getTunaBubbleTextSize() {
        return tunaBubbleTextSize;
    }

    public void setTunaBubbleTextSize(float tunaBubbleTextSize) {
        this.tunaBubbleTextSize = tunaBubbleTextSize;
    }

    private int tunaBubbleTextColorNormal;

    public int getTunaBubbleTextColorNormal() {
        return tunaBubbleTextColorNormal;
    }

    public void setTunaBubbleTextColorNormal(int tunaBubbleTextColorNormal) {
        this.tunaBubbleTextColorNormal = tunaBubbleTextColorNormal;
    }


    private float tunaBubbleTextPadding;

    public float getTunaBubbleTextPadding() {
        return tunaBubbleTextPadding;
    }

    public void setTunaBubbleTextPadding(float tunaBubbleTextPadding) {
        this.tunaBubbleTextPadding = tunaBubbleTextPadding;
    }

    private TunaBubbleTowardType tunaBubbleTowardType;

    public TunaBubbleTowardType getTunaBubbleTowardType() {
        return tunaBubbleTowardType;
    }

    public void setTunaBubbleTowardType(TunaBubbleTowardType tunaBubbleTowardType) {
        this.tunaBubbleTowardType = tunaBubbleTowardType;
    }

    public enum TunaBubbleTowardType {
        LEFT(0), TOP(1), RIGHT(2), BOTTOM(3),;
        final int nativeInt;

        TunaBubbleTowardType(int ni) {
            nativeInt = ni;
        }
    }

    private static final TunaBubbleTowardType[] tunaBubbleTowardTypeArray = {TunaBubbleTowardType.LEFT, TunaBubbleTowardType.TOP, TunaBubbleTowardType.RIGHT,
            TunaBubbleTowardType.BOTTOM,};

    public static TunaBubbleTowardType[] getTunabubbletowardtypeArray() {
        return tunaBubbleTowardTypeArray;
    }

    private TunaBubbleLocationType tunaBubbleLocationType;

    public TunaBubbleLocationType getTunaBubbleLocationType() {
        return tunaBubbleLocationType;
    }

    public void setTunaBubbleTowardType(TunaBubbleLocationType tunaBubbleLocationType) {
        this.tunaBubbleLocationType = tunaBubbleLocationType;
    }

    public enum TunaBubbleLocationType {
        LOW(0), MIDDLE(1), HIGH(2),;
        final int nativeInt;

        TunaBubbleLocationType(int ni) {
            nativeInt = ni;
        }
    }

    private static final TunaBubbleLocationType[] tunaBubbleLocationTypeArray = {TunaBubbleLocationType.LOW, TunaBubbleLocationType.MIDDLE, TunaBubbleLocationType.HIGH,};

    public static TunaBubbleLocationType[] getTTunaBubbleLocationTypeArray() {
        return tunaBubbleLocationTypeArray;
    }

    private float tunaBubbleOffset;

    public float getTunaBubbleOffset() {
        return tunaBubbleOffset;
    }

    public void setTunaBubbleOffset(float tunaBubbleOffset) {
        this.tunaBubbleOffset = tunaBubbleOffset;
    }

    private int tunaBubbleStrokeColor;

    public int getTunaBubbleStrokeColor() {
        return tunaBubbleStrokeColor;
    }

    public void setTunaBubbleStrokeColor(int tunaBubbleStrokeColor) {
        this.tunaBubbleStrokeColor = tunaBubbleStrokeColor;
    }

    private float tunaBubbleStrokeWidth;

    public float getTunaBubbleStrokeWidth() {
        return tunaBubbleStrokeWidth;
    }

    public void setTunaBubbleStrokeWidth(float tunaBubbleStrokeWidth) {
        this.tunaBubbleStrokeWidth = tunaBubbleStrokeWidth;
    }

    public TBubble(Context context) {
        this(context, null);
    }

    public TBubble(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TBubble(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        tunaTag = TBubble.class.getSimpleName();

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TBubble);

        tunaBubbleBackground = typedArray.getColor(R.styleable.TBubble_bubbleBackground, Color.TRANSPARENT);
        tunaBubbleRadius = typedArray.getDimension(R.styleable.TBubble_bubbleRadius, 0);

        tunaBubbleEdgeWidth = typedArray.getDimension(R.styleable.TBubble_bubbleEdgeWidth, 0);
        tunaBubbleEdgeHeight = typedArray.getDimension(R.styleable.TBubble_bubbleEdgeHeight, 0);

        tunaBubbleTextValue = typedArray.getString(R.styleable.TBubble_bubbleTextValue);
        tunaBubbleTextSize = typedArray.getDimension(R.styleable.TBubble_bubbleTextSize, 0);
        tunaBubbleTextColorNormal = typedArray.getColor(R.styleable.TBubble_bubbleTextColorNormal, Color.TRANSPARENT);
        tunaBubbleTextPadding = typedArray.getDimension(R.styleable.TBubble_bubbleTextPadding, 0);

        int tunaBubbleTowardTypeIndex = typedArray.getInt(R.styleable.TBubble_bubbleTowardType, 0);
        if (tunaBubbleTowardTypeIndex >= 0) {
            tunaBubbleTowardType = tunaBubbleTowardTypeArray[tunaBubbleTowardTypeIndex];
        } else {
            throw new IndexOutOfBoundsException("The content attribute tunaBubbleTowardType type it does not conform to the rules");
        }

        int tunaBubbleLocationTypeIndex = typedArray.getInt(R.styleable.TBubble_bubbleLocationType, 0);
        if (tunaBubbleLocationTypeIndex >= 0) {
            tunaBubbleLocationType = tunaBubbleLocationTypeArray[tunaBubbleLocationTypeIndex];
        } else {
            throw new IndexOutOfBoundsException("The content attribute tunaBubbleLocationType type it does not conform to the rules");
        }

        tunaBubbleOffset = typedArray.getDimension(R.styleable.TBubble_bubbleOffset, 0);
        tunaBubbleStrokeColor = typedArray.getColor(R.styleable.TBubble_bubbleStrokeColor, tunaBubbleBackground);
        tunaBubbleStrokeWidth = typedArray.getDimension(R.styleable.TBubble_bubbleStrokeWidth, 0);

        typedArray.recycle();
    }


//	@Override
//	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
//		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//		
////		setMeasuredDimension(resolveTunaSize((int)(getMinimumTunaWidth(tunaBubbleTextValue,tunaBubbleTextSize) + tunaBubbleEdgeWidth + tunaBubbleTextPadding), widthMeasureSpec), 
////				resolveTunaSize((int) getMinimumTunaHeight(tunaBubbleTextValue), heightMeasureSpec));
//		
//	}

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (tunaBubbleEdgeWidth <= 0 || tunaBubbleEdgeWidth + tunaBubbleStrokeWidth * 0.5f > tunaWidth) {
            throw new IndexOutOfBoundsException("The content attribute tunaBubbleEdgeWidth it does not conform to the rules");
        }
        if (tunaBubbleEdgeHeight <= 0 || tunaBubbleEdgeHeight + tunaBubbleStrokeWidth * 0.5f > tunaHeight) {
            throw new IndexOutOfBoundsException("The content attribute tunaBubbleEdgeHeight it does not conform to the rules");
        }


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //By default center offset to adjust the position!
        float left = 0;
        float top = 0;
        float right = tunaWidth;
        float bottom = tunaHeight;

        float rectLeft = 0;
        float rectTop = 0;
        float rectRight = 0;
        float rectBottom = 0;

        float inPolygonVertexX0 = 0;
        float inPolygonVertexY0 = 0;
        float inPolygonVertexX1 = 0;
        float intPolygonVertexY1 = 0;
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

        float textWidth = tunaWidth - tunaBubbleStrokeWidth * 2;
        float textCenterX = tunaWidth >> 1;
        float textCenterY = tunaHeight >> 1;

        switch (tunaBubbleTowardType) {
            case LEFT:

                switch (tunaBubbleLocationType) {
                    case LOW:
                        offsetY = (tunaHeight >> 1) - tunaBubbleEdgeWidth * 0.5f - tunaBubbleStrokeWidth * 1;
                        offsetY += tunaBubbleOffset;
                        break;
                    case MIDDLE:
                        offsetY += tunaBubbleOffset;
                        break;
                    case HIGH:
                        offsetY = ((tunaHeight >> 1) - tunaBubbleEdgeWidth * 0.5f - tunaBubbleStrokeWidth * 1) * -1;
                        offsetY += tunaBubbleOffset;
                        break;
                    default:
                        break;
                }
                left = tunaBubbleEdgeHeight;

                rectTop = (tunaHeight >> 1) - tunaBubbleEdgeWidth * 0.5f + offsetY;
                rectBottom = rectTop + tunaBubbleEdgeWidth;
                rectLeft = tunaBubbleEdgeHeight + offsetX;
                rectRight = rectLeft + tunaBubbleStrokeWidth;

                inPolygonVertexX0 = tunaBubbleStrokeWidth + offsetX;
                inPolygonVertexY0 = (tunaHeight >> 1) + offsetY;
                inPolygonVertexX1 = inPolygonVertexX0 + tunaBubbleEdgeHeight;
                intPolygonVertexY1 = inPolygonVertexY0 + tunaBubbleEdgeWidth * 0.5f;
                inPolygonVertexX2 = inPolygonVertexX1 + tunaBubbleRadius;
                inPolygonVertexY2 = intPolygonVertexY1;
                inPolygonVertexX3 = inPolygonVertexX2;
                inPolygonVertexY3 = inPolygonVertexY2 - tunaBubbleEdgeWidth;
                inPolygonVertexX4 = inPolygonVertexX1;
                inPolygonVertexY4 = inPolygonVertexY3;

                outPolygonVertexX0 = offsetX;
                outPolygonVertexY0 = (tunaHeight >> 1) + offsetY;
                outPolygonVertexX1 = outPolygonVertexX0 + tunaBubbleEdgeHeight;
                outPolygonVertexY1 = outPolygonVertexY0 + tunaBubbleEdgeWidth * 0.5f;
                outPolygonVertexX2 = outPolygonVertexX1 + tunaBubbleStrokeWidth;
                outPolygonVertexY2 = outPolygonVertexY1;
                outPolygonVertexX3 = inPolygonVertexX0;
                outPolygonVertexY3 = inPolygonVertexY0;
                outPolygonVertexX4 = outPolygonVertexX2;
                outPolygonVertexY4 = outPolygonVertexY1 - tunaBubbleEdgeWidth;
                outPolygonVertexX5 = outPolygonVertexX1;
                outPolygonVertexY5 = outPolygonVertexY4;

                //
                textCenterX += tunaBubbleEdgeHeight * 0.5f;

                break;

            case TOP:
                switch (tunaBubbleLocationType) {
                    case LOW:
                        offsetX = ((tunaWidth >> 1) - tunaBubbleEdgeWidth * 0.5f - tunaBubbleStrokeWidth * 1) * -1;
                        offsetX += tunaBubbleOffset;
                        break;
                    case MIDDLE:
                        offsetX += tunaBubbleOffset;
                        break;
                    case HIGH:
                        offsetX = (tunaWidth >> 1) - tunaBubbleEdgeWidth * 0.5f - tunaBubbleStrokeWidth * 1;
                        offsetX += tunaBubbleOffset;
                        break;
                    default:
                        break;
                }
                top = tunaBubbleEdgeHeight;

                rectTop = tunaBubbleEdgeHeight + offsetY;
                rectBottom = rectTop + tunaBubbleStrokeWidth;
                rectLeft = (tunaWidth >> 1) - tunaBubbleEdgeWidth * 0.5f + offsetX;
                rectRight = rectLeft + tunaBubbleEdgeWidth;

                inPolygonVertexX0 = (tunaWidth >> 1) + offsetX;
                inPolygonVertexY0 = tunaBubbleStrokeWidth + offsetY;
                inPolygonVertexX1 = inPolygonVertexX0 - tunaBubbleEdgeWidth * 0.5f;
                intPolygonVertexY1 = inPolygonVertexY0 + tunaBubbleEdgeHeight;
                inPolygonVertexX2 = inPolygonVertexX1;
                inPolygonVertexY2 = intPolygonVertexY1 + tunaBubbleRadius;
                inPolygonVertexX3 = inPolygonVertexX2 + tunaBubbleEdgeWidth;
                inPolygonVertexY3 = inPolygonVertexY2;
                inPolygonVertexX4 = inPolygonVertexX3;
                inPolygonVertexY4 = intPolygonVertexY1;

                outPolygonVertexX0 = (tunaWidth >> 1) + offsetX;
                outPolygonVertexY0 = offsetY;
                outPolygonVertexX1 = outPolygonVertexX0 - tunaBubbleEdgeWidth * 0.5f;
                outPolygonVertexY1 = outPolygonVertexY0 + tunaBubbleEdgeHeight;
                outPolygonVertexX2 = outPolygonVertexX1;
                outPolygonVertexY2 = outPolygonVertexY1 + tunaBubbleStrokeWidth;
                outPolygonVertexX3 = inPolygonVertexX0;
                outPolygonVertexY3 = inPolygonVertexY0;
                outPolygonVertexX4 = outPolygonVertexX2 + tunaBubbleEdgeWidth;
                outPolygonVertexY4 = outPolygonVertexY2;
                outPolygonVertexX5 = outPolygonVertexX4;
                outPolygonVertexY5 = outPolygonVertexY1;

                //
                textCenterY += tunaBubbleEdgeHeight * 0.5f;

                break;

            case RIGHT:
                switch (tunaBubbleLocationType) {
                    case LOW:
                        offsetY = ((tunaHeight >> 1) - tunaBubbleEdgeWidth * 0.5f - tunaBubbleStrokeWidth * 1) * -1;
                        offsetY += tunaBubbleOffset;
                        break;
                    case MIDDLE:
                        offsetY += tunaBubbleOffset;
                        break;
                    case HIGH:
                        offsetY = (tunaHeight >> 1) - tunaBubbleEdgeWidth * 0.5f - tunaBubbleStrokeWidth * 1;
                        offsetY += tunaBubbleOffset;
                        break;
                    default:
                        break;
                }
                right -= tunaBubbleEdgeHeight;

                rectTop = (tunaHeight >> 1) - tunaBubbleEdgeWidth * 0.5f + offsetY;
                rectBottom = rectTop + tunaBubbleEdgeWidth;
                rectRight = tunaWidth - tunaBubbleStrokeWidth + offsetX;
                rectLeft = rectRight - tunaBubbleEdgeHeight;

                inPolygonVertexX0 = tunaWidth - tunaBubbleStrokeWidth + offsetX;
                inPolygonVertexY0 = (tunaHeight >> 1) + offsetY;
                inPolygonVertexX1 = inPolygonVertexX0 - tunaBubbleEdgeHeight;
                intPolygonVertexY1 = inPolygonVertexY0 - tunaBubbleEdgeWidth * 0.5f;
                inPolygonVertexX2 = inPolygonVertexX1 - tunaBubbleRadius;
                inPolygonVertexY2 = intPolygonVertexY1;
                inPolygonVertexX3 = inPolygonVertexX2;
                inPolygonVertexY3 = inPolygonVertexY2 + tunaBubbleEdgeWidth;
                inPolygonVertexX4 = inPolygonVertexX1;
                inPolygonVertexY4 = inPolygonVertexY3;

                outPolygonVertexX0 = tunaWidth + offsetX;
                outPolygonVertexY0 = (tunaHeight >> 1) + offsetY;
                outPolygonVertexX1 = outPolygonVertexX0 - tunaBubbleEdgeHeight;
                outPolygonVertexY1 = outPolygonVertexY0 - tunaBubbleEdgeWidth * 0.5f;
                outPolygonVertexX2 = outPolygonVertexX1 - tunaBubbleStrokeWidth;
                outPolygonVertexY2 = outPolygonVertexY1;
                outPolygonVertexX3 = inPolygonVertexX0;
                outPolygonVertexY3 = inPolygonVertexY0;
                outPolygonVertexX4 = outPolygonVertexX2;
                outPolygonVertexY4 = outPolygonVertexY1 + tunaBubbleEdgeWidth;
                outPolygonVertexX5 = outPolygonVertexX1;
                outPolygonVertexY5 = outPolygonVertexY4;

                //
                textCenterX -= tunaBubbleEdgeHeight * 0.5f;

                break;

            case BOTTOM:
                switch (tunaBubbleLocationType) {
                    case LOW:
                        offsetX = ((tunaWidth >> 1) - tunaBubbleEdgeWidth * 0.5f - tunaBubbleStrokeWidth * 1) * -1;
                        offsetX += tunaBubbleOffset;
                        break;
                    case MIDDLE:
                        offsetX += tunaBubbleOffset;
                        break;
                    case HIGH:
                        offsetX = (tunaWidth >> 1) - tunaBubbleEdgeWidth * 0.5f - tunaBubbleStrokeWidth * 1;
                        offsetX += tunaBubbleOffset;
                        break;
                    default:
                        break;
                }
                bottom -= tunaBubbleEdgeHeight;

                rectBottom = tunaHeight - tunaBubbleStrokeWidth + offsetY;
                rectTop = rectBottom - tunaBubbleEdgeHeight;
                rectLeft = (tunaWidth >> 1) - tunaBubbleEdgeWidth * 0.5f + offsetX;
                rectRight = rectLeft + tunaBubbleEdgeWidth;

                inPolygonVertexX0 = (tunaWidth >> 1) + offsetX;
                inPolygonVertexY0 = tunaHeight - tunaBubbleStrokeWidth + offsetY;
                inPolygonVertexX1 = inPolygonVertexX0 - tunaBubbleEdgeWidth * 0.5f;
                intPolygonVertexY1 = inPolygonVertexY0 - tunaBubbleEdgeHeight;
                inPolygonVertexX2 = inPolygonVertexX1;
                inPolygonVertexY2 = intPolygonVertexY1 - tunaBubbleRadius;
                inPolygonVertexX3 = inPolygonVertexX2 + tunaBubbleEdgeWidth;
                inPolygonVertexY3 = inPolygonVertexY2;
                inPolygonVertexX4 = inPolygonVertexX3;
                inPolygonVertexY4 = intPolygonVertexY1;

                outPolygonVertexX0 = (tunaWidth >> 1) + offsetX;
                outPolygonVertexY0 = tunaHeight + offsetY;
                outPolygonVertexX1 = outPolygonVertexX0 - tunaBubbleEdgeWidth * 0.5f;
                outPolygonVertexY1 = outPolygonVertexY0 - tunaBubbleEdgeHeight;
                outPolygonVertexX2 = outPolygonVertexX1;
                outPolygonVertexY2 = outPolygonVertexY1 - tunaBubbleStrokeWidth;
                outPolygonVertexX3 = inPolygonVertexX0;
                outPolygonVertexY3 = inPolygonVertexY0;
                outPolygonVertexX4 = outPolygonVertexX2 + tunaBubbleEdgeWidth;
                outPolygonVertexY4 = outPolygonVertexY2;
                outPolygonVertexX5 = outPolygonVertexX4;
                outPolygonVertexY5 = outPolygonVertexY1;

                //
                textCenterY -= tunaBubbleEdgeHeight * 0.5f;

                break;
            default:
                break;
        }

        //
        canvas.save();
        canvas.clipRect(initTunaRect((int) rectLeft, (int) rectTop, (int) rectRight, (int) rectBottom), Op.DIFFERENCE);
        drawTunaRectClassic(canvas, left, top, right, bottom, tunaBubbleBackground, tunaBubbleStrokeWidth, tunaBubbleStrokeColor, tunaBubbleRadius);
        canvas.restore();

        //
        initTunaPathMoveTo(inPolygonVertexX0, inPolygonVertexY0);
        tunaPath.lineTo(inPolygonVertexX1, intPolygonVertexY1);
        tunaPath.lineTo(inPolygonVertexX2, inPolygonVertexY2);
        tunaPath.lineTo(inPolygonVertexX3, inPolygonVertexY3);
        tunaPath.lineTo(inPolygonVertexX4, inPolygonVertexY4);
        tunaPath.close();
        canvas.drawPath(tunaPath, initTunaPaint(Paint.Style.FILL, tunaBubbleBackground));

        //
        initTunaPathMoveTo(outPolygonVertexX0, outPolygonVertexY0);
        tunaPath.lineTo(outPolygonVertexX1, outPolygonVertexY1);
        tunaPath.lineTo(outPolygonVertexX2, outPolygonVertexY2);
        tunaPath.lineTo(outPolygonVertexX3, outPolygonVertexY3);
        tunaPath.lineTo(outPolygonVertexX4, outPolygonVertexY4);
        tunaPath.lineTo(outPolygonVertexX5, outPolygonVertexY5);
        tunaPath.close();
        canvas.drawPath(tunaPath, initTunaPaint(Paint.Style.FILL, tunaBubbleStrokeColor));

        //
        if (tunaBubbleTextValue != null) {
            drawTunaText(canvas, tunaBubbleTextValue, textWidth, textCenterX, textCenterY, 0, 0,
                    initTunaTextPaint(Paint.Style.FILL, tunaBubbleTextColorNormal, tunaBubbleTextSize, Paint.Align.CENTER));
        }

    }
}
