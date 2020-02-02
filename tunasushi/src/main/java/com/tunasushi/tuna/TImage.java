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
 * @Copyright 2015 Sashimi. All rights reserved.
 * @Description
 */
public class TImage extends TView {


    private float imageRadius;

    public float getImageRadius() {
        return imageRadius;
    }

    public void setImageRadius(float imageRadius) {
        this.imageRadius = imageRadius;
    }

    private float imageAlpha;

    public float getImageAlpha() {
        return imageAlpha;
    }

    public void setImageAlpha(float imageAlpha) {
        this.imageAlpha = imageAlpha;
    }

    private boolean imageSepia;

    public boolean isImageSepia() {
        return imageSepia;
    }

    public void setImageSepia(boolean imageSepia) {
        this.imageSepia = imageSepia;
    }

    private boolean imageEmboss;

    public boolean isImageEmboss() {
        return imageEmboss;
    }

    public void setImageEmboss(boolean imageEmboss) {
        this.imageEmboss = imageEmboss;
    }

    private boolean imageBacksheet;

    public boolean isImageBacksheet() {
        return imageBacksheet;
    }

    public void setImageBacksheet(boolean imageBacksheet) {
        this.imageBacksheet = imageBacksheet;
    }

    private boolean imageSketch;

    public boolean isImageSketch() {
        return imageSketch;
    }

    public void setImageSketch(boolean imageSketch) {
        this.imageSketch = imageSketch;
    }

    private float imageSunshineFractionX;

    public float getImageSunshineFractionX() {
        return imageSunshineFractionX;
    }

    public void setImageSunshineFractionX(float imageSunshineFractionX) {
        this.imageSunshineFractionX = imageSunshineFractionX;
    }

    private float imageSunshineFractionY;

    public float getImageSunshineFractionY() {
        return imageSunshineFractionY;
    }

    public void setImageSunshineFractionY(float imageSunshineFractionY) {
        this.imageSunshineFractionY = imageSunshineFractionY;
    }

    private float imageBright;

    public float getImageBright() {
        return imageBright;
    }

    public void setImageBright(float imageBright) {
        this.imageBright = imageBright;
        invalidate();
    }

    private float imageHue;

    public float getImageHue() {
        return imageHue;
    }

    public void setImageHue(float imageHue) {
        this.imageHue = imageHue;
        invalidate();
    }

    private float imageSaturation;

    public float getImageSaturation() {
        return imageSaturation;
    }

    public void setImageSaturation(float imageSaturation) {
        this.imageSaturation = imageSaturation;
        invalidate();
    }

    private ImageReverse imageReverse;

    public enum ImageReverse {
        HORIZONTAL(0),
        VERTICAL(1),
        ;
        final int nativeInt;

        ImageReverse(int ni) {
            nativeInt = ni;
        }
    }

    public ImageReverse getImageReverse() {
        return imageReverse;
    }

    public void setImageReverse(ImageReverse imageReverse) {
        this.imageReverse = imageReverse;
    }

    private static final ImageReverse[] imageReverseArray = {ImageReverse.HORIZONTAL, ImageReverse.VERTICAL,};

    private Bitmap imageSrcBitmap;

    public Bitmap getImageSrcBitmap() {
        return imageSrcBitmap;
    }

    public void setImageSrcBitmap(Bitmap imageSrcBitmap) {
        this.imageSrcBitmap = imageSrcBitmap;
    }

    public TImage(Context context) {
        this(context, null);
    }

    public TImage(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TImage(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        Tag = TImage.class.getSimpleName();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TImage);

        int imageSrcIndex = typedArray.getResourceId(R.styleable.TImage_imageSrc, -1);
        if (imageSrcIndex >= 0) {
            srcBitmap = BitmapFactory.decodeResource(getResources(), imageSrcIndex);
        } else {
            throw new IllegalArgumentException("The content attribute require a property named imageSrc");
        }

        imageRadius = typedArray.getDimension(R.styleable.TImage_imageRadius, 0);
        imageAlpha = typedArray.getFraction(R.styleable.TImage_imageAlpha, 1, 1, 1f);

        imageSepia = typedArray.getBoolean(R.styleable.TImage_imageSepia, false);
        imageEmboss = typedArray.getBoolean(R.styleable.TImage_imageEmboss, false);
        imageBacksheet = typedArray.getBoolean(R.styleable.TImage_imageBacksheet, false);
        imageSketch = typedArray.getBoolean(R.styleable.TImage_imageSketch, false);

        imageSunshineFractionX = typedArray.getFraction(R.styleable.TImage_imageSunshineFractionX, 1, 1, 0);
        imageSunshineFractionY = typedArray.getFraction(R.styleable.TImage_imageSunshineFractionY, 1, 1, 0);

        imageBright = typedArray.getDimension(R.styleable.TImage_imageBright, 1f);
        imageHue = typedArray.getDimension(R.styleable.TImage_imageHue, 0f);
        imageSaturation = typedArray.getDimension(R.styleable.TImage_imageSaturation, 1f);

        int imageReverseIndex = typedArray.getInt(R.styleable.TImage_imageReverse, -1);
        if (imageReverseIndex >= 0) {
            imageReverse = imageReverseArray[imageReverseIndex];
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        scaleSx = width * 1f / srcBitmap.getWidth();
        scaleSy = height * 1f / srcBitmap.getHeight();

        initMatrix(scaleSx, scaleSy);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //
        imageSrcBitmap = srcBitmap;

        if (imageRadius > 0) {
            imageSrcBitmap = getClassicRoundBitmap(srcBitmap, width);
        }

        if (imageAlpha != 1f) {
            if (imageSrcBitmap == null) {
                imageSrcBitmap = getAlphaBitmap(srcBitmap, imageAlpha);
            } else {
                imageSrcBitmap = getAlphaBitmap(imageSrcBitmap, imageAlpha);
            }
        }

        if (imageSepia) {
            if (imageSrcBitmap == null) {
                imageSrcBitmap = getSepiaBitmap(srcBitmap);
            } else {
                imageSrcBitmap = getSepiaBitmap(imageSrcBitmap);
            }
        }

        if (imageEmboss) {
            if (imageSrcBitmap == null) {
                imageSrcBitmap = getEmbossBitmap(srcBitmap);
            } else {
                imageSrcBitmap = getEmbossBitmap(imageSrcBitmap);
            }
        }

        if (imageBacksheet) {
            if (imageSrcBitmap == null) {
                imageSrcBitmap = getBacksheetBitmap(srcBitmap);
            } else {
                imageSrcBitmap = getBacksheetBitmap(imageSrcBitmap);
            }
        }

        if (imageSketch) {
            if (imageSrcBitmap == null) {
                imageSrcBitmap = getSketchBitmap(srcBitmap);
            } else {
                imageSrcBitmap = getSketchBitmap(imageSrcBitmap);
            }
        }

        if (imageSunshineFractionX != 0 || imageSunshineFractionY != 0) {
            if (imageSrcBitmap == null) {
                imageSrcBitmap = getSunshineBitmap(
                        srcBitmap,
                        width * imageSunshineFractionX,
                        height * imageSunshineFractionY);
                ;
            } else {
                imageSrcBitmap = getSunshineBitmap(
                        imageSrcBitmap,
                        width * imageSunshineFractionX,
                        height * imageSunshineFractionY);
                ;
            }
        }

        if (imageReverse != null) {
            if (imageSrcBitmap == null) {
                imageSrcBitmap = getReverseBitmap(srcBitmap, imageReverse == ImageReverse.HORIZONTAL ? 0 : 1);
            } else {
                imageSrcBitmap = getReverseBitmap(imageSrcBitmap, imageReverse == ImageReverse.HORIZONTAL ? 0 : 1);
            }
        }

        if (imageBright != 1f || imageHue != 0f || imageSaturation != 1f) {
            if (imageSrcBitmap == null) {
                imageSrcBitmap = processBitmap(srcBitmap, imageBright, imageHue, imageSaturation);
            } else {
                imageSrcBitmap = processBitmap(imageSrcBitmap, imageBright, imageHue, imageSaturation);
            }
        }
        canvas.drawBitmap(imageSrcBitmap, matrix, null);
    }
}
