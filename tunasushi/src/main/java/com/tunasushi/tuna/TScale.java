package com.tunasushi.tuna;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;

import com.tuna.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;


/**
 * @author TunaSashimi
 * @date 2015-10-30 16:58
 * @Copyright 2015 TunaSashimi. All rights reserved.
 * @Description
 */
public class TScale extends TView {
    private float
            scaleCropFractionLeft,
            scaleCropFractionTop,
            scaleCropFractionRight,
            scaleCropFractionBottom;
    //original pitcure area,four parameters is top left corner x width, height occupied the upper left corner y, x width occupied the upper right corner, lower right corner of the height

    private boolean scaleCrop;

    @IntDef({WIDTH_TOP, WIDTH_CENTER, WIDTH_BOTTOM})
    @Retention(RetentionPolicy.SOURCE)
    public @interface scaleMode {
    }

    public static final int WIDTH_TOP = 0;
    public static final int WIDTH_CENTER = 1;
    public static final int WIDTH_BOTTOM = 2;
    private static final int[] scaleModeArray = {WIDTH_TOP, WIDTH_CENTER, WIDTH_BOTTOM,
    };
    private @scaleMode
    int scaleMode;

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

        //srcBitmap can be set by code
        int scaleBitmapId = typedArray.getResourceId(R.styleable.TScale_scaleBitmap, -1);
        if (scaleBitmapId != -1) {
            srcBitmap = BitmapFactory.decodeResource(getResources(), scaleBitmapId);
        }

        int scaleModeIndex = typedArray.getInt(R.styleable.TScale_scaleMode, -1);
        if (scaleModeIndex >= 0) {
            scaleMode = scaleModeArray[scaleModeIndex];
        } else {
            throw new IllegalArgumentException("The content attribute require a property named scaleMode");
        }

        scaleCrop = typedArray.getBoolean(R.styleable.TScale_scaleCrop, false);
        if (scaleCrop) {
            scaleCropFractionLeft = typedArray.getFraction(R.styleable.TScale_scaleCropFractionLeft, 1, 1, 0);
            scaleCropFractionTop = typedArray.getFraction(R.styleable.TScale_scaleCropFractionTop, 1, 1, 0);
            scaleCropFractionRight = typedArray.getFraction(R.styleable.TScale_scaleCropFractionRight, 1, 1, 1);
            scaleCropFractionBottom = typedArray.getFraction(R.styleable.TScale_scaleCropFractionBottom, 1, 1, 1);
        }
        typedArray.recycle();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        //
        if (scaleCrop) {
            generateCrop();
        } else {
            //
            scale = width * 1f / srcBitmap.getWidth();
            matrixNormal = initMatrix(matrixNormal, scale, scale);

            //
            srcWidthScale = srcBitmap.getWidth() * scale;
            srcHeightScale = srcBitmap.getHeight() * scale;

            dy = srcHeightScale - height;

            //
            if (WIDTH_TOP == scaleMode) {
            } else if (WIDTH_CENTER == scaleMode) {
                matrixNormal.postTranslate(0, dy * -0.5f);
            } else if (WIDTH_BOTTOM == scaleMode) {
                matrixNormal.postTranslate(0, -dy);
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (scaleCrop) {
            canvas.drawBitmap(srcBitmap, rect, new Rect(0, 0, width, height), null);
        } else {
            canvas.drawBitmap(srcBitmap, matrixNormal, null);
        }
    }

    private void generateCrop() {
        if (WIDTH_TOP == scaleMode) {
            initRect((int) (srcWidthScale * scaleCropFractionLeft), (int) (srcHeightScale * scaleCropFractionTop),
                    (int) (srcWidthScale * scaleCropFractionRight), (int) (srcHeightScale * scaleCropFractionBottom));
        } else if (WIDTH_CENTER == scaleMode) {
            initRect((int) (srcWidthScale * scaleCropFractionLeft), (int) (srcHeightScale * scaleCropFractionTop - dy * 0.5f),
                    (int) (srcWidthScale * scaleCropFractionRight), (int) (srcHeightScale * scaleCropFractionBottom - dy * 0.5f));
        } else if (WIDTH_BOTTOM == scaleMode) {
            initRect((int) (srcWidthScale * scaleCropFractionLeft), (int) (srcHeightScale * scaleCropFractionTop - dy),
                    (int) (srcWidthScale * scaleCropFractionRight), (int) (srcHeightScale * scaleCropFractionBottom - dy));
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
