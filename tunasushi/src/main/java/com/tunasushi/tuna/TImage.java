package com.tunasushi.tuna;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;

import com.tuna.R;

import static com.tunasushi.tool.BitmapTool.getAlphaBitmap;
import static com.tunasushi.tool.BitmapTool.getBacksheetBitmap;
import static com.tunasushi.tool.BitmapTool.getClassicRoundBitmap;
import static com.tunasushi.tool.BitmapTool.getEmbossBitmap;
import static com.tunasushi.tool.BitmapTool.getReverseBitmap;
import static com.tunasushi.tool.BitmapTool.getSepiaBitmap;
import static com.tunasushi.tool.BitmapTool.getSketchBitmap;
import static com.tunasushi.tool.BitmapTool.getSunshineBitmap;
import static com.tunasushi.tool.BitmapTool.processBitmap;


/**
 * @author Tunasashimi
 * @date 11/20/15 10:46
 * @Copyright 2015 TunaSashimi. All rights reserved.
 * @Description
 */
public class TImage extends TView {


    private float tunaImageRadius;

    public float getTunaImageRadius() {
        return tunaImageRadius;
    }

    public void setTunaImageRadius(float tunaImageRadius) {
        this.tunaImageRadius = tunaImageRadius;
    }

    private float tunaImageAlpha;

    public float getTunaImageAlpha() {
        return tunaImageAlpha;
    }

    public void setTunaImageAlpha(float tunaImageAlpha) {
        this.tunaImageAlpha = tunaImageAlpha;
    }

    private boolean tunaImageSepia;

    public boolean isTunaImageSepia() {
        return tunaImageSepia;
    }

    public void setTunaImageSepia(boolean tunaImageSepia) {
        this.tunaImageSepia = tunaImageSepia;
    }

    private boolean tunaImageEmboss;

    public boolean isTunaImageEmboss() {
        return tunaImageEmboss;
    }

    public void setTunaImageEmboss(boolean tunaImageEmboss) {
        this.tunaImageEmboss = tunaImageEmboss;
    }

    private boolean tunaImageBacksheet;

    public boolean isTunaImageBacksheet() {
        return tunaImageBacksheet;
    }

    public void setTunaImageBacksheet(boolean tunaImageBacksheet) {
        this.tunaImageBacksheet = tunaImageBacksheet;
    }

    private boolean tunaImageSketch;

    public boolean isTunaImageSketch() {
        return tunaImageSketch;
    }

    public void setTunaImageSketch(boolean tunaImageSketch) {
        this.tunaImageSketch = tunaImageSketch;
    }

    private float tunaImageSunshineFractionX;

    public float getTunaImageSunshineFractionX() {
        return tunaImageSunshineFractionX;
    }

    public void setTunaImageSunshineFractionX(float tunaImageSunshineFractionX) {
        this.tunaImageSunshineFractionX = tunaImageSunshineFractionX;
    }

    private float tunaImageSunshineFractionY;

    public float getTunaImageSunshineFractionY() {
        return tunaImageSunshineFractionY;
    }

    public void setTunaImageSunshineFractionY(float tunaImageSunshineFractionY) {
        this.tunaImageSunshineFractionY = tunaImageSunshineFractionY;
    }

    private float tunaImageBright;

    public float getTunaImageBright() {
        return tunaImageBright;
    }

    public void setTunaImageBright(float tunaImageBright) {
        this.tunaImageBright = tunaImageBright;
        invalidate();
    }

    private float tunaImageHue;

    public float getTunaImageHue() {
        return tunaImageHue;
    }

    public void setTunaImageHue(float tunaImageHue) {
        this.tunaImageHue = tunaImageHue;
        invalidate();
    }

    private float tunaImageSaturation;

    public float getTunaImageSaturation() {
        return tunaImageSaturation;
    }

    public void setTunaImageSaturation(float tunaImageSaturation) {
        this.tunaImageSaturation = tunaImageSaturation;
        invalidate();
    }

    private TunaImageReverse tunaImageReverse;

    public enum TunaImageReverse {
        HORIZONTAL(0),
        VERTICAL(1),
        ;
        final int nativeInt;

        TunaImageReverse(int ni) {
            nativeInt = ni;
        }
    }

    public TunaImageReverse getTunaImageReverse() {
        return tunaImageReverse;
    }

    public void setTunaImageReverse(TunaImageReverse tunaImageReverse) {
        this.tunaImageReverse = tunaImageReverse;
    }

    private static final TunaImageReverse[] tunaImageReverseArray = {TunaImageReverse.HORIZONTAL, TunaImageReverse.VERTICAL,};

    private Bitmap tunaImageSrcBitmap;

    public Bitmap getTunaImageSrcBitmap() {
        return tunaImageSrcBitmap;
    }

    public void setTunaImageSrcBitmap(Bitmap tunaImageSrcBitmap) {
        this.tunaImageSrcBitmap = tunaImageSrcBitmap;
    }

    public TImage(Context context) {
        this(context, null);
    }

    public TImage(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TImage(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        tunaTag = TImage.class.getSimpleName();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TImage);

        int tunaImageSrcIndex = typedArray.getResourceId(R.styleable.TImage_imageSrc, -1);
        if (tunaImageSrcIndex >= 0) {
            tunaSrcBitmap = BitmapFactory.decodeResource(getResources(), tunaImageSrcIndex);
        } else {
            throw new IllegalArgumentException("The content attribute require a property named tunaImageSrc");
        }

        tunaImageRadius = typedArray.getDimension(R.styleable.TImage_imageRadius, 0);
        tunaImageAlpha = typedArray.getFraction(R.styleable.TImage_imageAlpha, 1, 1, 1f);

        tunaImageSepia = typedArray.getBoolean(R.styleable.TImage_imageSepia, false);
        tunaImageEmboss = typedArray.getBoolean(R.styleable.TImage_imageEmboss, false);
        tunaImageBacksheet = typedArray.getBoolean(R.styleable.TImage_imageBacksheet, false);
        tunaImageSketch = typedArray.getBoolean(R.styleable.TImage_imageSketch, false);

        tunaImageSunshineFractionX = typedArray.getFraction(R.styleable.TImage_imageSunshineFractionX, 1, 1, 0);
        tunaImageSunshineFractionY = typedArray.getFraction(R.styleable.TImage_imageSunshineFractionY, 1, 1, 0);

        tunaImageBright = typedArray.getDimension(R.styleable.TImage_imageBright, 1f);
        tunaImageHue = typedArray.getDimension(R.styleable.TImage_imageHue, 0f);
        tunaImageSaturation = typedArray.getDimension(R.styleable.TImage_imageSaturation, 1f);

        int tunaImageReverseIndex = typedArray.getInt(R.styleable.TImage_imageReverse, -1);
        if (tunaImageReverseIndex >= 0) {
            tunaImageReverse = tunaImageReverseArray[tunaImageReverseIndex];
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        tunaScaleSx = tunaWidth * 1f / tunaSrcBitmap.getWidth();
        tunaScaleSy = tunaHeight * 1f / tunaSrcBitmap.getHeight();

        initTunaMatrix(tunaScaleSx, tunaScaleSy);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //
        tunaImageSrcBitmap = tunaSrcBitmap;

        if (tunaImageRadius > 0) {
            tunaImageSrcBitmap = getClassicRoundBitmap(tunaSrcBitmap, tunaWidth);
        }

        if (tunaImageAlpha != 1f) {
            if (tunaImageSrcBitmap == null) {
                tunaImageSrcBitmap = getAlphaBitmap(tunaSrcBitmap, tunaImageAlpha);
            } else {
                tunaImageSrcBitmap = getAlphaBitmap(tunaImageSrcBitmap, tunaImageAlpha);
            }
        }

        if (tunaImageSepia) {
            if (tunaImageSrcBitmap == null) {
                tunaImageSrcBitmap = getSepiaBitmap(tunaSrcBitmap);
            } else {
                tunaImageSrcBitmap = getSepiaBitmap(tunaImageSrcBitmap);
            }
        }

        if (tunaImageEmboss) {
            if (tunaImageSrcBitmap == null) {
                tunaImageSrcBitmap = getEmbossBitmap(tunaSrcBitmap);
            } else {
                tunaImageSrcBitmap = getEmbossBitmap(tunaImageSrcBitmap);
            }
        }

        if (tunaImageBacksheet) {
            if (tunaImageSrcBitmap == null) {
                tunaImageSrcBitmap = getBacksheetBitmap(tunaSrcBitmap);
            } else {
                tunaImageSrcBitmap = getBacksheetBitmap(tunaImageSrcBitmap);
            }
        }

        if (tunaImageSketch) {
            if (tunaImageSrcBitmap == null) {
                tunaImageSrcBitmap = getSketchBitmap(tunaSrcBitmap);
            } else {
                tunaImageSrcBitmap = getSketchBitmap(tunaImageSrcBitmap);
            }
        }

        if (tunaImageSunshineFractionX != 0 || tunaImageSunshineFractionY != 0) {
            if (tunaImageSrcBitmap == null) {
                tunaImageSrcBitmap = getSunshineBitmap(
                        tunaSrcBitmap,
                        tunaWidth * tunaImageSunshineFractionX,
                        tunaHeight * tunaImageSunshineFractionY);
                ;
            } else {
                tunaImageSrcBitmap = getSunshineBitmap(
                        tunaImageSrcBitmap,
                        tunaWidth * tunaImageSunshineFractionX,
                        tunaHeight * tunaImageSunshineFractionY);
                ;
            }
        }

        if (tunaImageReverse != null) {
            if (tunaImageSrcBitmap == null) {
                tunaImageSrcBitmap = getReverseBitmap(tunaSrcBitmap, tunaImageReverse == TunaImageReverse.HORIZONTAL ? 0 : 1);
            } else {
                tunaImageSrcBitmap = getReverseBitmap(tunaImageSrcBitmap, tunaImageReverse == TunaImageReverse.HORIZONTAL ? 0 : 1);
            }
        }

        if (tunaImageBright != 1f || tunaImageHue != 0f || tunaImageSaturation != 1f) {
            if (tunaImageSrcBitmap == null) {
                tunaImageSrcBitmap = processBitmap(tunaSrcBitmap, tunaImageBright, tunaImageHue, tunaImageSaturation);
            } else {
                tunaImageSrcBitmap = processBitmap(tunaImageSrcBitmap, tunaImageBright, tunaImageHue, tunaImageSaturation);
            }
        }
        canvas.drawBitmap(tunaImageSrcBitmap, tunaMatrix, null);
    }
}
