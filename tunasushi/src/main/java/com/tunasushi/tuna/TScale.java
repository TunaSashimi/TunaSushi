package com.tunasushi.tuna;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;

import com.tuna.R;

/**
 * @author Tunasashimi
 * @date 10/30/15 16:58
 * @Copyright 2015 Sashimi. All rights reserved.
 * @Description
 */
public class TScale extends TView {
    private float
            scaleTouchRectangleFractionLeft,
            scaleTouchRectangleFractionTop,
            scaleTouchRectangleFractionRight,
            scaleTouchRectangleFractionBottom;
    //original pitcure area,four parameters is top left corner x width, height occupied the upper left corner y, x width occupied the upper right corner, lower right corner of the height

    private boolean scaleTouchRectangleable;

    private ScaleType scaleType;

    public enum ScaleType {
        WIDTH_TOP(0),
        WIDTH_CENTER(1),
        WIDTH_BOTTOM(2),
        ;
        final int nativeInt;

        ScaleType(int ni) {
            nativeInt = ni;
        }
    }

    private static final ScaleType[] scaleTypeArray = {
            ScaleType.WIDTH_TOP,
            ScaleType.WIDTH_CENTER,
            ScaleType.WIDTH_BOTTOM,
    };

    public TScale(Context context) {
        this(context, null);
    }

    public TScale(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TScale(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        tag = TScale.class.getSimpleName();

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TScale);

        int scaleBitmapId = typedArray.getResourceId(R.styleable.TScale_scaleBitmap, -1);
        if (scaleBitmapId != -1) {
            srcBitmap = BitmapFactory.decodeResource(getResources(), scaleBitmapId);
        }

        int scaleTypeIndex = typedArray.getInt(R.styleable.TScale_scaleType, -1);
        if (scaleTypeIndex >= 0) {
            scaleType = scaleTypeArray[scaleTypeIndex];
        } else {
            throw new IllegalArgumentException("The content attribute require a property named scaleType");
        }

        scaleTouchRectangleable = typedArray.getBoolean(R.styleable.TScale_scaleTouchRectangleable, false);
        if (scaleTouchRectangleable) {
            scaleTouchRectangleFractionLeft = typedArray.getFraction(R.styleable.TScale_scaleTouchRectangleFractionLeft, 1, 1, 0);
            scaleTouchRectangleFractionTop = typedArray.getFraction(R.styleable.TScale_scaleTouchRectangleFractionTop, 1, 1, 0);
            scaleTouchRectangleFractionRight = typedArray.getFraction(R.styleable.TScale_scaleTouchRectangleFractionRight, 1, 1, 1);
            scaleTouchRectangleFractionBottom = typedArray.getFraction(R.styleable.TScale_scaleTouchRectangleFractionBottom, 1, 1, 1);
        }

        typedArray.recycle();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if (srcBitmap == null) {
            return;
        }

        scale = width * 1f / srcBitmap.getWidth();

        initMatrix(scale, scale);

        srcWidthScale = srcBitmap.getWidth() * scale;
        srcHeightScale = srcBitmap.getHeight() * scale;

        dy = srcHeightScale - height;

        if (ScaleType.WIDTH_TOP == scaleType) {

        } else if (ScaleType.WIDTH_CENTER == scaleType) {
            matrix.postTranslate(0, dy * -0.5f);
        } else if (ScaleType.WIDTH_BOTTOM == scaleType) {
            matrix.postTranslate(0, -dy);
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (srcBitmap == null) {
            return;
        }

        canvas.drawBitmap(srcBitmap, matrix, null);

        //generateScaleTouchRectagle
        if (debugable && scaleTouchRectangleable) {
            generateTouchRectagle();
        } else if (scaleTouchRectangleable && rect == null) {
            generateTouchRectagle();
        }
    }

    private void generateTouchRectagle() {

        if (ScaleType.WIDTH_TOP == scaleType) {
            initRect((int) (srcWidthScale * scaleTouchRectangleFractionLeft), (int) (srcHeightScale * scaleTouchRectangleFractionTop),
                    (int) (srcWidthScale * scaleTouchRectangleFractionRight), (int) (srcHeightScale * scaleTouchRectangleFractionBottom));
        } else if (ScaleType.WIDTH_CENTER == scaleType) {
            initRect((int) (srcWidthScale * scaleTouchRectangleFractionLeft), (int) (srcHeightScale * scaleTouchRectangleFractionTop - dy * 0.5f),
                    (int) (srcWidthScale * scaleTouchRectangleFractionRight), (int) (srcHeightScale * scaleTouchRectangleFractionBottom - dy * 0.5f));
        } else if (ScaleType.WIDTH_BOTTOM == scaleType) {
            initRect((int) (srcWidthScale * scaleTouchRectangleFractionLeft), (int) (srcHeightScale * scaleTouchRectangleFractionTop - dy),
                    (int) (srcWidthScale * scaleTouchRectangleFractionRight), (int) (srcHeightScale * scaleTouchRectangleFractionBottom - dy));
        }
    }

    public Bitmap getScaleBitmap() {
        return srcBitmap;
    }

    public void setScaleBitmap(Bitmap srcBitmap) {
        this.srcBitmap = srcBitmap;
    }

    public void setScaleSrc(int srcBitmapId) {
        srcBitmap = BitmapFactory.decodeResource(getResources(), srcBitmapId);
    }
}
