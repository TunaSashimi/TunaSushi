package com.tunasushi.tuna;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;

import com.tuna.R;


/**
 * @author Tunasashimi
 * @date 10/30/15 16:58
 * @Copyright 2015 TunaSashimi. All rights reserved.
 * @Description
 */
public class TScale extends TView {
    private float
            tunaScaleTouchRectangleFractionLeft,
            tunaScaleTouchRectangleFractionTop,
            tunaScaleTouchRectangleFractionRight,
            tunaScaleTouchRectangleFractionBottom;
    //original pitcure area,four parameters is top left corner x width, height occupied the upper left corner y, x width occupied the upper right corner, lower right corner of the height

    private boolean tunaScaleTouchRectangleable;

    private TunaScaleType tunaScaleType;

    public enum TunaScaleType {
        WIDTH_TOP(0),
        WIDTH_CENTER(1),
        WIDTH_BOTTOM(2),;
        final int nativeInt;

        TunaScaleType(int ni) {
            nativeInt = ni;
        }
    }

    private static final TunaScaleType[] tunaScaleTypeArray = {
            TunaScaleType.WIDTH_TOP,
            TunaScaleType.WIDTH_CENTER,
            TunaScaleType.WIDTH_BOTTOM,
    };

    public TScale(Context context) {
        this(context, null);
    }

    public TScale(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TScale(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        tunaTag = TScale.class.getSimpleName();

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TScale);

        int tunaBitmapId = typedArray.getResourceId(R.styleable.TScale_scaleBitmap, -1);
        if (tunaBitmapId != -1) {
            tunaSrcBitmap = BitmapFactory.decodeResource(getResources(), tunaBitmapId);
        }

        int tunaScaleTypeIndex = typedArray.getInt(R.styleable.TScale_scaleType, -1);
        if (tunaScaleTypeIndex >= 0) {
            tunaScaleType = tunaScaleTypeArray[tunaScaleTypeIndex];
        } else {
            throw new IllegalArgumentException("The content attribute require a property named tunaScaleType");
        }

        tunaScaleTouchRectangleable = typedArray.getBoolean(R.styleable.TScale_scaleTouchRectangleable, false);
        if (tunaScaleTouchRectangleable) {
            tunaScaleTouchRectangleFractionLeft = typedArray.getFraction(R.styleable.TScale_scaleTouchRectangleFractionLeft, 1, 1, 0);
            tunaScaleTouchRectangleFractionTop = typedArray.getFraction(R.styleable.TScale_scaleTouchRectangleFractionTop, 1, 1, 0);
            tunaScaleTouchRectangleFractionRight = typedArray.getFraction(R.styleable.TScale_scaleTouchRectangleFractionRight, 1, 1, 1);
            tunaScaleTouchRectangleFractionBottom = typedArray.getFraction(R.styleable.TScale_scaleTouchRectangleFractionBottom, 1, 1, 1);
        }

        typedArray.recycle();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if (tunaSrcBitmap == null) {
            return;
        }

        tunaScale = tunaWidth * 1f / tunaSrcBitmap.getWidth();

        initTunaMatrix(tunaScale, tunaScale);

        tunaSrcWidthScale = tunaSrcBitmap.getWidth() * tunaScale;
        tunaSrcHeightScale = tunaSrcBitmap.getHeight() * tunaScale;

        tunaDy = tunaSrcHeightScale - tunaHeight;

        if (TunaScaleType.WIDTH_TOP == tunaScaleType) {

        } else if (TunaScaleType.WIDTH_CENTER == tunaScaleType) {
            tunaMatrix.postTranslate(0, tunaDy * -0.5f);
        } else if (TunaScaleType.WIDTH_BOTTOM == tunaScaleType) {
            tunaMatrix.postTranslate(0, -tunaDy);
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (tunaSrcBitmap == null) {
            return;
        }

        canvas.drawBitmap(tunaSrcBitmap, tunaMatrix, null);

        //generateTunaScaleTouchRectagle
        if (tunaDebugable && tunaScaleTouchRectangleable) {
            generateTunaTouchRectagle();
        } else if (tunaScaleTouchRectangleable && tunaRect == null) {
            generateTunaTouchRectagle();
        }
    }

    private void generateTunaTouchRectagle() {

        if (TunaScaleType.WIDTH_TOP == tunaScaleType) {
            initTunaRect((int) (tunaSrcWidthScale * tunaScaleTouchRectangleFractionLeft), (int) (tunaSrcHeightScale * tunaScaleTouchRectangleFractionTop),
                    (int) (tunaSrcWidthScale * tunaScaleTouchRectangleFractionRight), (int) (tunaSrcHeightScale * tunaScaleTouchRectangleFractionBottom));
        } else if (TunaScaleType.WIDTH_CENTER == tunaScaleType) {
            initTunaRect((int) (tunaSrcWidthScale * tunaScaleTouchRectangleFractionLeft), (int) (tunaSrcHeightScale * tunaScaleTouchRectangleFractionTop - tunaDy * 0.5f),
                    (int) (tunaSrcWidthScale * tunaScaleTouchRectangleFractionRight), (int) (tunaSrcHeightScale * tunaScaleTouchRectangleFractionBottom - tunaDy * 0.5f));
        } else if (TunaScaleType.WIDTH_BOTTOM == tunaScaleType) {
            initTunaRect((int) (tunaSrcWidthScale * tunaScaleTouchRectangleFractionLeft), (int) (tunaSrcHeightScale * tunaScaleTouchRectangleFractionTop - tunaDy),
                    (int) (tunaSrcWidthScale * tunaScaleTouchRectangleFractionRight), (int) (tunaSrcHeightScale * tunaScaleTouchRectangleFractionBottom - tunaDy));
        }
    }

    public Rect getTunaTouchRectagle() {
        return tunaRect;
    }

    public void setTunaTouchRectagle(Rect touchRectagle) {
        this.tunaRect = touchRectagle;
    }

    public Bitmap getTunaScaleBitmap() {
        return tunaSrcBitmap;
    }

    public void setTunaScaleBitmap(Bitmap tunaSrcBitmap) {
        this.tunaSrcBitmap = tunaSrcBitmap;
    }

    public void setTunaScaleBitmapSrc(int tunaSrcBitmapId) {
        tunaSrcBitmap = BitmapFactory.decodeResource(getResources(), tunaSrcBitmapId);
    }
}
