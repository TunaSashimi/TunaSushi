package com.tunasushi.tuna;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;


import com.tuna.R;

import java.util.ArrayList;
import java.util.List;

import static com.tunasushi.tool.PaintTool.initTunaTextPaint;
import static com.tunasushi.tool.PaintTool.tunaPaint;

/**
 * @author Tunasashimi
 * @date 10/30/15 16:53
 * @Copyright 2015 TunaSashimi. All rights reserved.
 * @Description
 */

public class TWrap extends TView {
    private float tunaWrapLineSpacing;
    private float tunaWrapRowSpacing;

    private float tunaWrapRadius;

    private int tunaWrapBackgroundNormal, tunaWrapBackgroundSelect;

    private float tunaWrapStrokeWidth;
    private int tunaWrapStrokeColorNormal, tunaWrapStrokeColorSelect;

    //

    private float tunaWrapTextSize;
    private int tunaWrapTextColorNormal, tunaWrapTextColorSelect;

    public String[] getTunaWrapItemTextValueArray() {
        return tunaWrapItemTextValueArray;
    }

    public void setTunaWrapItemTextValueArray(String[] tunaWrapItemTextValueArray) {
        this.tunaWrapItemTextValueArray = tunaWrapItemTextValueArray;
    }

    private String[] tunaWrapItemTextValueArray;

    //
    private List<Wrap> wrapList;
    private boolean[] wrapCurrentSelect;

    public TWrap(Context context) {
        this(context, null);
    }

    public TWrap(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TWrap(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        tunaTag = TWrap.class.getSimpleName();

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TWrap);


        tunaWrapLineSpacing = typedArray.getDimension(R.styleable.TWrap_wrapLineSpacing, 0);
        tunaWrapRowSpacing = typedArray.getDimension(R.styleable.TWrap_wrapRowSpacing, 0);

        tunaWrapRadius = typedArray.getDimension(R.styleable.TWrap_wrapRadius, 0);

        tunaWrapBackgroundNormal = typedArray.getColor(R.styleable.TWrap_wrapBackgroundNormal, Color.TRANSPARENT);
        tunaWrapBackgroundSelect = typedArray.getColor(R.styleable.TWrap_wrapBackgroundSelect, tunaWrapBackgroundNormal);

        tunaWrapStrokeWidth = typedArray.getDimension(R.styleable.TWrap_wrapStrokeWidth, 0);
        tunaWrapStrokeColorNormal = typedArray.getColor(R.styleable.TWrap_wrapStrokeColorNormal, Color.TRANSPARENT);
        tunaWrapStrokeColorSelect = typedArray.getColor(R.styleable.TWrap_wrapStrokeColorSelect, tunaWrapStrokeColorNormal);

        tunaWrapTextSize = typedArray.getDimension(R.styleable.TWrap_wrapTextSize, 0);
        tunaWrapTextColorNormal = typedArray.getColor(R.styleable.TWrap_wrapTextColorNormal, Color.TRANSPARENT);
        tunaWrapTextColorSelect = typedArray.getColor(R.styleable.TWrap_wrapTextColorSelect, tunaWrapTextColorNormal);

        int tunaDragTextValueArrayId = typedArray.getResourceId(R.styleable.TWrap_wrapItemTextValueArray, -1);
        if (tunaDragTextValueArrayId != -1) {
            if (isInEditMode()) {
                tunaWrapItemTextValueArray = new String[]{"ONE", "TWO", "THREE"};
            } else {
                tunaWrapItemTextValueArray = typedArray.getResources().getStringArray(tunaDragTextValueArrayId);
            }
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);

            typedArray.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //
        tunaTotal = tunaWrapItemTextValueArray.length;

        if (tunaTotal <= 0) {
            throw new IllegalArgumentException("The content attribute tunaWrapItemTextValueArray length must be greater than 0 ");
        } else {
            wrapList = new ArrayList(tunaTotal);
            wrapCurrentSelect = new boolean[tunaTotal];
        }

        //
        if (tunaWrapTextSize <= 0) {
            throw new IllegalArgumentException("The content attribute tunaWrapTextSize length must be greater than 0 ");
        } else {
            initTunaTextPaint(tunaWrapTextColorNormal, tunaWrapTextSize);
        }


        //
        int specModeWidth = View.MeasureSpec.getMode(widthMeasureSpec);
        int specSizeWidth = View.MeasureSpec.getSize(widthMeasureSpec);

        //
        int specModeHeight = View.MeasureSpec.getMode(heightMeasureSpec);
        int specSizeHeight = View.MeasureSpec.getSize(heightMeasureSpec);


        Paint.FontMetricsInt fontMetrics = tunaPaint.getFontMetricsInt();
        int rowHeight = fontMetrics.descent - fontMetrics.ascent;

        //
        int measuredWidth = specSizeWidth;
        int measuredHeight = rowHeight;//At least one line
        float characterWidth = tunaPaint.measureText(tunaWrapItemTextValueArray[0]);//At least one field

        //measuredHeight
        if (specModeHeight == View.MeasureSpec.AT_MOST) {// wrap_content
            for (int i = 0; i <= tunaTotal - 1; i++) {
                //
                if (i != 0) {
                    float itemWidth = tunaPaint.measureText(tunaWrapItemTextValueArray[i]);
                    characterWidth += tunaWrapLineSpacing + itemWidth;
                }
//                System.out.println("tunaWrapItemTextValueArray==>" + tunaWrapItemTextValueArray[i]);
//                System.out.println("characterWidth==>" + characterWidth);
//                System.out.println("specSizeWidth==>" + specSizeWidth);
                if (characterWidth > specSizeWidth) {
                    characterWidth = 0;
                    measuredHeight += tunaWrapRowSpacing + rowHeight;
                } else {
                }
            }
        } else if (specModeHeight == View.MeasureSpec.EXACTLY) {// match_parent
            measuredHeight = specSizeHeight;
        } else if (specModeHeight == View.MeasureSpec.UNSPECIFIED) {// unspecified
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

        Paint.FontMetricsInt fontMetrics = tunaPaint.getFontMetricsInt();
        float rowHeight = fontMetrics.descent - fontMetrics.ascent;
        float characterWidth = tunaPaint.measureText(tunaWrapItemTextValueArray[0]);//At least one field

        int dx = 0;
        int dy = 0;

        for (int i = 0; i <= tunaTotal - 1; i++) {
            float itemWidth = tunaPaint.measureText(tunaWrapItemTextValueArray[i]);
            if (i != 0) {
                characterWidth += tunaWrapLineSpacing + itemWidth;
            }
            if (characterWidth > tunaWidth) {
                //
                dx = 0;
                dy += tunaWrapRowSpacing + rowHeight;
                //
                drawDetail(canvas, dx, dy, itemWidth, rowHeight, i);
                //
                dx += tunaWrapLineSpacing + itemWidth;
                characterWidth = itemWidth;
            } else {
                //
                drawDetail(canvas, dx, dy, itemWidth, rowHeight, i);
                //
                dx += tunaWrapLineSpacing + itemWidth;
            }
        }
    }


    private void drawDetail(Canvas canvas, int dx, int dy, float itemWidth, float rowHeight, int i) {

        //
        if (wrapList.size() <= i) {
            wrapList.add(new Wrap(new Rect(dx, dy, dx + (int) itemWidth, dy + (int) rowHeight), false, tunaWrapItemTextValueArray[i]));
        }

        Wrap wrap = wrapList.get(i);

        //
        canvas.translate(dx, dy);
        drawTunaRectClassic(
                canvas,
                itemWidth,
                rowHeight,
                wrap.wrapSelect ? tunaWrapBackgroundSelect : tunaWrapBackgroundNormal,
                tunaWrapStrokeWidth,
                wrap.wrapSelect ? tunaWrapStrokeColorSelect : tunaWrapStrokeColorNormal,
                tunaWrapRadius
        );

        //
        drawTunaText(
                canvas,
                wrap.wrapString,
                itemWidth,
                0,
                rowHeight * 0.5f,
                initTunaTextPaint(wrap.wrapSelect ? tunaWrapTextColorSelect : tunaWrapTextColorNormal, tunaWrapTextSize)
        );
        canvas.translate(-dx, -dy);

    }

    public void setTunaWrapCurrentXY(float tunaWrapCurrentX, float tunaWrapCurrentY) {
        for (int i = 0; i <= tunaTotal - 1; i++) {
            Wrap wrap = wrapList.get(i);
            if (wrap.wrapRect.contains((int) tunaWrapCurrentX, (int) tunaWrapCurrentY)) {
                wrap.wrapSelect = !wrap.wrapSelect;
                wrapCurrentSelect[i] = wrap.wrapSelect;
            }
        }
    }

    public boolean[] getTunaWrapCurrentSelect() {
        return wrapCurrentSelect;
    }

    public class Wrap {
        public Rect wrapRect;
        public boolean wrapSelect;
        public String wrapString;

        public Wrap(Rect wrapRect, boolean wrapSelect, String wrapString) {
            this.wrapRect = wrapRect;
            this.wrapSelect = wrapSelect;
            this.wrapString = wrapString;
        }
    }
}