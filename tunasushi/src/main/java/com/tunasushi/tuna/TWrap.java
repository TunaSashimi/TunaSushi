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

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.IntDef;

/**
 * @author TunaSashimi
 * @date 2015-10-30 16:53
 * @Copyright 2015 TunaSashimi. All rights reserved.
 * @Description
 */
public class TWrap extends TView {

    @IntDef({MULTIPLE, SINGLE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface wrapMode {
    }

    public static final int MULTIPLE = 0;
    public static final int SINGLE = 1;
    private static final int[] wrapModeArray = {MULTIPLE, SINGLE,};
    private @TWrap.wrapMode
    int wrapMode;

    private float wrapSpaceLine;
    private float wrapSpaceRow;

    private int wrapBackgroundNormal, wrapBackgroundSelect;

    private float wrapStrokeWidth;
    private int wrapStrokeColorNormal, wrapStrokeColorSelect;

    //
    private float wrapTextSize;
    private float wrapTextPadding;

    private int wrapTextColorNormal, wrapTextColorSelect;

    private int index;

    public String[] getWrapItemTextArray() {
        return wrapItemTextArray;
    }

    private String[] wrapItemTextArray;

    public void setWrapItemTextArray(String[] wrapItemTextArray) {
        this.wrapItemTextArray = wrapItemTextArray;
    }

    //
    private List<Wrap> wrapList;
    private boolean[] wrapSelect;

    public boolean[] getWrapSelect() {
        return wrapSelect;
    }

    public int getWrapSelectIndex() {
        return index;
    }

    public String getWrapSelectString() {
        return wrapItemTextArray[index];
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

        int wrapModeIndex = typedArray.getInt(R.styleable.TWrap_wrapMode, 0);
        if (wrapModeIndex >= 0) {
            wrapMode = wrapModeArray[wrapModeIndex];
        } else {
            throw new IllegalArgumentException("The content attribute wrapMode type must be given");
        }

        wrapSpaceLine = typedArray.getDimension(R.styleable.TWrap_wrapSpaceLine, 0);
        wrapSpaceRow = typedArray.getDimension(R.styleable.TWrap_wrapSpaceRow, 0);

        wrapBackgroundNormal = typedArray.getColor(R.styleable.TWrap_wrapBackgroundNormal, Color.TRANSPARENT);
        wrapBackgroundSelect = typedArray.getColor(R.styleable.TWrap_wrapBackgroundSelect, wrapBackgroundNormal);

        wrapStrokeWidth = typedArray.getDimension(R.styleable.TWrap_wrapStrokeWidth, 0);
        wrapStrokeColorNormal = typedArray.getColor(R.styleable.TWrap_wrapStrokeColorNormal, Color.TRANSPARENT);
        wrapStrokeColorSelect = typedArray.getColor(R.styleable.TWrap_wrapStrokeColorSelect, wrapStrokeColorNormal);

        wrapTextSize = typedArray.getDimension(R.styleable.TWrap_wrapTextSize, textSizeDefault);

        wrapTextPadding = typedArray.getDimension(R.styleable.TWrap_wrapTextPadding, 0);

        wrapTextColorNormal = typedArray.getColor(R.styleable.TWrap_wrapTextColorNormal, textColorDefault);
        wrapTextColorSelect = typedArray.getColor(R.styleable.TWrap_wrapTextColorSelect, wrapTextColorNormal);

        int wrapItemTextArrayId = typedArray.getResourceId(R.styleable.TWrap_wrapItemTextArray, -1);
        if (wrapItemTextArrayId != -1) {
            wrapItemTextArray = typedArray.getResources().getStringArray(wrapItemTextArrayId);
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //Not only the onMeasure method of the parent class will affect the alignment!
        //Because the paint that measures the length is static, it will be affected by other classes and must be independent!
        total = wrapItemTextArray.length;
        if (total <= 0) {
            throw new IllegalArgumentException("The content attribute tunaWrapItemTextArray length must be greater than 0 ");
        } else if (wrapList == null && wrapList == null) {
            wrapList = new ArrayList(total);//The actual length here is 0
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

        //
        initTextPaint(wrapTextColorNormal, wrapTextSize);
        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();

        //Need to add padding
        float rowHeight = fontMetrics.descent - fontMetrics.ascent + wrapTextPadding * 2;

        //
        int measuredWidth = specSizeWidth;
        int measuredHeight = (int) rowHeight;//At least one line

        //
        float characterWidth = paint.measureText(wrapItemTextArray[0]) + wrapTextPadding * 2;//At least one field

        //measuredHeight
        if (specModeHeight == MeasureSpec.AT_MOST) {// wrap_content
            for (int i = 0; i <= total - 1; i++) {
                //Start from the second
                if (i != 0) {
                    float itemWidth = paint.measureText(wrapItemTextArray[i]) + wrapTextPadding * 2;
                    characterWidth += itemWidth + wrapSpaceLine;
                }
                if (characterWidth > specSizeWidth) {
                    characterWidth = 0;
                    measuredHeight += rowHeight + wrapSpaceRow;
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
    protected void onDraw(Canvas canvas) {
        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        float rowHeight = fontMetrics.descent - fontMetrics.ascent + wrapTextPadding * 2;
        float characterWidth = paint.measureText(wrapItemTextArray[0]) + wrapTextPadding * 2;//At least one field
        dx = 0;
        dy = 0;
        for (int i = 0; i <= total - 1; i++) {
            float itemWidth = paint.measureText(wrapItemTextArray[i]) + wrapTextPadding * 2;
            if (i != 0) {
                characterWidth += itemWidth + wrapSpaceLine;
            }
            if (characterWidth > width) {
                //
                dx = 0;
                dy += rowHeight + wrapSpaceRow;
                //
                drawDetail(canvas, dx, dy, itemWidth, rowHeight, wrapTextPadding, i);
                //
                dx += itemWidth + wrapSpaceLine;
                characterWidth = itemWidth;
            } else {
                //
                drawDetail(canvas, dx, dy, itemWidth, rowHeight, wrapTextPadding, i);
                //
                dx += itemWidth + wrapSpaceLine;
            }
        }
    }


    private void drawDetail(Canvas canvas, float dx, float dy, float itemWidth, float rowHeight, float wrapTextPadding, int i) {
        //
        if (wrapList.size() <= i) {
            wrapList.add(new Wrap(new RectF(dx, dy, dx + (int) itemWidth, dy + (int) rowHeight), false, wrapItemTextArray[i]));
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
                radius
        );

        //
        drawRectClassicStroke(
                canvas,
                itemWidth,
                rowHeight,
                wrapStrokeWidth,
                wrap.wrapSelect ? wrapStrokeColorSelect : wrapStrokeColorNormal,
                radius
        );

        //
        drawText(
                canvas,
                wrap.wrapString,
                itemWidth,
                wrapTextPadding,
                rowHeight * 0.5f,
                initTextPaint(wrap.wrapSelect ? wrapTextColorSelect : wrapTextColorNormal, wrapTextSize)
        );
        canvas.translate(-dx, -dy);
    }

    @Override
    public void setTouchXY(float touchX, float touchY) {
        //MotionEvent.ACTION_DOWN press = true;
        //MotionEvent.ACTION_MOVE press = true;
        //MotionEvent.ACTION_UP press = false;
        //MotionEvent.ACTION_CANCEL press = false;

        if (!press) {
            for (int i = 0; i <= total - 1; i++) {
                Wrap wrap = wrapList.get(i);
                if (wrap.wrapRect.contains((int) touchX, (int) touchY)) {
                    index = i;
                    if (wrapMode == MULTIPLE) {
                        wrap.wrapSelect = !wrap.wrapSelect;
                    } else {
                        wrap.wrapSelect = true;
                    }
                    wrapSelect[i] = wrap.wrapSelect;
                } else if (wrapMode == SINGLE) {
                    wrap.wrapSelect = false;
                }
            }
            invalidate();
        }
    }
}