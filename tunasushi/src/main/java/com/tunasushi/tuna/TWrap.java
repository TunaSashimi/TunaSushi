package com.tunasushi.tuna;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.tuna.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tunasashimi
 * @date 10/30/15 16:53
 * @Copyright 2015 Sashimi. All rights reserved.
 * @Description
 */

public class TWrap extends TView {
    private float wrapLineSpacing;
    private float wrapRowSpacing;

    private float wrapRadius;
    private int wrapBackgroundNormal, wrapBackgroundSelect;

    private float wrapStrokeWidth;
    private int wrapStrokeColorNormal, wrapStrokeColorSelect;

    //
    private float wrapTextSize;
    private int wrapTextColorNormal, wrapTextColorSelect;

    public String[] getWrapItemTextValueArray() {
        return wrapItemTextValueArray;
    }

    private String[] wrapItemTextValueArray;

    public void setWrapItemTextValueArray(String[] wrapItemTextValueArray) {
        this.wrapItemTextValueArray = wrapItemTextValueArray;
    }

    //
    private List<Wrap> wrapList;
    private boolean[] wrapSelect;

    public TWrap(Context context) {
        this(context, null);
    }

    public TWrap(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TWrap(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        tag = TWrap.class.getSimpleName();

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TWrap);

        wrapLineSpacing = typedArray.getDimension(R.styleable.TWrap_wrapLineSpacing, 0);
        wrapRowSpacing = typedArray.getDimension(R.styleable.TWrap_wrapRowSpacing, 0);

        wrapRadius = typedArray.getDimension(R.styleable.TWrap_wrapRadius, 0);

        wrapBackgroundNormal = typedArray.getColor(R.styleable.TWrap_wrapBackgroundNormal, Color.TRANSPARENT);
        wrapBackgroundSelect = typedArray.getColor(R.styleable.TWrap_wrapBackgroundSelect, wrapBackgroundNormal);

        wrapStrokeWidth = typedArray.getDimension(R.styleable.TWrap_wrapStrokeWidth, 0);
        wrapStrokeColorNormal = typedArray.getColor(R.styleable.TWrap_wrapStrokeColorNormal, Color.TRANSPARENT);
        wrapStrokeColorSelect = typedArray.getColor(R.styleable.TWrap_wrapStrokeColorSelect, wrapStrokeColorNormal);

        wrapTextSize = typedArray.getDimension(R.styleable.TWrap_wrapTextSize, 0);
        wrapTextColorNormal = typedArray.getColor(R.styleable.TWrap_wrapTextColorNormal, Color.TRANSPARENT);
        wrapTextColorSelect = typedArray.getColor(R.styleable.TWrap_wrapTextColorSelect, wrapTextColorNormal);

        int wrapItemTextValueArrayId = typedArray.getResourceId(R.styleable.TWrap_wrapItemTextValueArray, -1);
        if (wrapItemTextValueArrayId != -1) {
            wrapItemTextValueArray = typedArray.getResources().getStringArray(wrapItemTextValueArrayId);
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //Not only the onMeasure method of the parent class will affect the alignment!
        //Because the paint that measures the length is static, it will be affected by other classes and must be independent!
        total = wrapItemTextValueArray.length;

        if (total <= 0) {
            throw new IllegalArgumentException("The content attribute tunaWrapItemTextValueArray length must be greater than 0 ");
        } else {
            wrapList = new ArrayList(total);
            wrapSelect = new boolean[total];
        }

        //
        if (wrapTextSize <= 0) {
            throw new IllegalArgumentException("The content attribute tunaWrapTextSize length must be greater than 0 ");
        }
        //
        int specModeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int specSizeWidth = MeasureSpec.getSize(widthMeasureSpec);

        //
        int specModeHeight = MeasureSpec.getMode(heightMeasureSpec);
        int specSizeHeight = MeasureSpec.getSize(heightMeasureSpec);

        initTextPaint(wrapTextColorNormal, wrapTextSize);
        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        int rowHeight = fontMetrics.descent - fontMetrics.ascent;

        //
        int measuredWidth = specSizeWidth;
        int measuredHeight = rowHeight;//At least one line

        float characterWidth = paint.measureText(wrapItemTextValueArray[0]);//At least one field

        //measuredHeight
        if (specModeHeight == MeasureSpec.AT_MOST) {// wrap_content
            for (int i = 0; i <= total - 1; i++) {
                //
                if (i != 0) {
                    float itemWidth = paint.measureText(wrapItemTextValueArray[i]);
                    characterWidth += wrapLineSpacing + itemWidth;
                }
                if (characterWidth > specSizeWidth) {
                    characterWidth = 0;
                    measuredHeight += wrapRowSpacing + rowHeight;
                } else {
                }
            }
        } else if (specModeHeight == MeasureSpec.EXACTLY) {// match_parent
            measuredHeight = specSizeHeight;
        } else if (specModeHeight == MeasureSpec.UNSPECIFIED) {// unspecified
            measuredHeight = specSizeHeight;
        }
        setMeasuredDimension(measuredWidth, measuredHeight);
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        float rowHeight = fontMetrics.descent - fontMetrics.ascent;
        float characterWidth = paint.measureText(wrapItemTextValueArray[0]);//At least one field

        dx = 0;
        dy = 0;

        for (int i = 0; i <= total - 1; i++) {
            float itemWidth = paint.measureText(wrapItemTextValueArray[i]);
            if (i != 0) {
                characterWidth += wrapLineSpacing + itemWidth;
            }
            if (characterWidth > width) {
                //
                dx = 0;
                dy += wrapRowSpacing + rowHeight;
                //
                drawDetail(canvas, dx, dy, itemWidth, rowHeight, i);
                //
                dx += wrapLineSpacing + itemWidth;
                characterWidth = itemWidth;
            } else {
                //
                drawDetail(canvas, dx, dy, itemWidth, rowHeight, i);
                //
                dx += wrapLineSpacing + itemWidth;
            }
        }
    }


    private void drawDetail(Canvas canvas, float dx, float dy, float itemWidth, float rowHeight, int i) {

        //
        if (wrapList.size() <= i) {
            wrapList.add(new Wrap(new RectF(dx, dy, dx + (int) itemWidth, dy + (int) rowHeight), false, wrapItemTextValueArray[i]));
        }

        Wrap wrap = wrapList.get(i);

        //
        canvas.translate(dx, dy);
        drawRectClassic(
                canvas,
                itemWidth,
                rowHeight,
                wrap.wrapSelect ? wrapBackgroundSelect : wrapBackgroundNormal,
                wrapStrokeWidth,
                wrap.wrapSelect ? wrapStrokeColorSelect : wrapStrokeColorNormal,
                wrapRadius
        );

        //
        drawText(
                canvas,
                wrap.wrapString,
                itemWidth,
                0,
                rowHeight * 0.5f,
                initTextPaint(wrap.wrapSelect ? wrapTextColorSelect : wrapTextColorNormal, wrapTextSize)
        );
        canvas.translate(-dx, -dy);

    }

    public void setWrapXY(float wrapX, float wrapY) {
        for (int i = 0; i <= total - 1; i++) {
            Wrap wrap = wrapList.get(i);
            if (wrap.wrapRect.contains((int) wrapX, (int) wrapY)) {
                wrap.wrapSelect = !wrap.wrapSelect;
                wrapSelect[i] = wrap.wrapSelect;
            }
        }
    }

    public boolean[] getWrapSelect() {
        return wrapSelect;
    }

    public class Wrap {
        public RectF wrapRect;
        public boolean wrapSelect;
        public String wrapString;

        public Wrap(RectF wrapRect, boolean wrapSelect, String wrapString) {
            this.wrapRect = wrapRect;
            this.wrapSelect = wrapSelect;
            this.wrapString = wrapString;
        }
    }
}