package com.tunasushi.tuna;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.View;

import com.tuna.R;

import static com.tunasushi.tool.BitmapTool.getCircleBitmap;
import static com.tunasushi.tool.BitmapTool.getSVGBitmap;
import static com.tunasushi.tool.PaintTool.initTunaPaint;
import static com.tunasushi.tool.PaintTool.tunaPaint;


/**
 * @author Tunasashimi
 * @date 10/30/15 16:52
 * @Copyright 2015 TunaSashimi. All rights reserved.
 * @Description
 */
public class TSVG extends TView {

    //
    private Bitmap tunaSVGSrcBitmap;

    public Bitmap getTunaSVGSrcBitmap() {
        return tunaSVGSrcBitmap;
    }

    public void setTunaSVGSrcBitmap(Bitmap tunaSVGSrcBitmap) {
        this.tunaSVGSrcBitmap = tunaSVGSrcBitmap;
    }

    //
    protected Matrix tunaSVGMatrix;
    public Matrix getTunaSVGMatrix() {
        return tunaSVGMatrix;
    }
    public void setTunaSVGMatrix(Matrix tunaSVGMatrix) {
        this.tunaSVGMatrix = tunaSVGMatrix;
    }
    protected Matrix initTunaSVGMatrix(float sx, float sy) {
        if (tunaSVGMatrix == null) {
            tunaSVGMatrix = new Matrix();
        }
        tunaSVGMatrix.reset();
        tunaSVGMatrix.setScale(sx, sy);
        return tunaSVGMatrix;
    }

    private TunaSVGType tunaSVGType;

    public enum TunaSVGType {
        CIRCLE(0),
        STAR(1),
        HEART(2),
        FLOWER(3),
        PENTAGON(4),
        SIXTEENEDGE(5),
        FORTYEDGE(6),
        SNAIL(7),
        FISH(8),
        ;
        final int nativeInt;

        TunaSVGType(int ni) {
            nativeInt = ni;
        }
    }

    private static final TunaSVGType[]tunaScaleTypeArray = {
            TunaSVGType.CIRCLE,
            TunaSVGType.STAR,
            TunaSVGType.HEART,
            TunaSVGType.FLOWER,
            TunaSVGType.PENTAGON,
            TunaSVGType.SIXTEENEDGE,
            TunaSVGType.FORTYEDGE,
            TunaSVGType.SNAIL,
            TunaSVGType.FISH,
    };


    public TSVG(Context context) {
        this(context, null);
    }

    public TSVG(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TSVG(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        tunaTag = TSVG.class.getSimpleName();

        //
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TSVG);
        int tunaBitmapId = typedArray.getResourceId(R.styleable.TSVG_sVGSrc, -1);
        if (tunaBitmapId != -1) {
            tunaSrcBitmap = BitmapFactory.decodeResource(getResources(), tunaBitmapId);
        }


        int tunaSVGTypeIndex = typedArray.getInt(R.styleable.TSVG_sVGType, 0);
        if (tunaSVGTypeIndex >= 0) {
            tunaSVGType = tunaScaleTypeArray[tunaSVGTypeIndex];
        }

        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        //
        int specSizeWidth = View.MeasureSpec.getSize(widthMeasureSpec);
        int specModeHeight = View.MeasureSpec.getMode(heightMeasureSpec);
        int specSizeHeight = View.MeasureSpec.getSize(heightMeasureSpec);

        int measuredWidth = specSizeWidth;
        int measuredHeight = specSizeWidth;

        if (specModeHeight == View.MeasureSpec.AT_MOST) {//wrap_content
            measuredHeight = measuredWidth;
        } else if (specModeHeight == View.MeasureSpec.EXACTLY) {// match_parent
            measuredHeight = specSizeHeight;
        } else if (specModeHeight == View.MeasureSpec.UNSPECIFIED) {// unspecified
            measuredHeight = measuredWidth;
        }
        setMeasuredDimension(measuredWidth, measuredHeight);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        tunaScaleSx = tunaWidth * 1f / tunaSrcBitmap.getWidth();
        tunaScaleSy = tunaHeight * 1f / tunaSrcBitmap.getHeight();
        initTunaMatrix(tunaScaleSx, tunaScaleSy);

        int shortSide = tunaWidth >= tunaHeight ? tunaHeight : tunaWidth;
        initTunaSVGMatrix(tunaWidth * 1f / shortSide, tunaHeight * 1f / shortSide);

        if (isInEditMode()) {
            tunaSVGSrcBitmap = getCircleBitmap(shortSide);
        } else {
            switch (tunaSVGType) {
                case CIRCLE:
                    tunaSVGSrcBitmap = getCircleBitmap(shortSide);
                    break;
                case STAR:
                    tunaSVGSrcBitmap = getSVGBitmap(getContext(), shortSide, shortSide, R.raw.svg_star);
                    break;
                case HEART:
                    tunaSVGSrcBitmap = getSVGBitmap(getContext(), shortSide, shortSide, R.raw.svg_heart);
                    break;
                case FLOWER:
                    tunaSVGSrcBitmap = getSVGBitmap(getContext(), shortSide, shortSide, R.raw.svg_flower);
                    break;
                case PENTAGON:
                    tunaSVGSrcBitmap = getSVGBitmap(getContext(), shortSide, shortSide, R.raw.svg_pentagon);
                    break;
                case SIXTEENEDGE:
                    tunaSVGSrcBitmap = getSVGBitmap(getContext(), shortSide, shortSide, R.raw.svg_sixteenedge);
                    break;
                case FORTYEDGE:
                    tunaSVGSrcBitmap = getSVGBitmap(getContext(), shortSide, shortSide, R.raw.svg_fortyedge);
                    break;
                case SNAIL:
                    tunaSVGSrcBitmap = getSVGBitmap(getContext(), shortSide, shortSide, R.raw.svg_snail);
                    break;
                case FISH:
                    tunaSVGSrcBitmap = getSVGBitmap(getContext(), shortSide, shortSide, R.raw.svg_fish);
                    break;
            }
        }


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.saveLayer(0, 0, tunaWidth, tunaHeight, null, Canvas.ALL_SAVE_FLAG);

        //
        canvas.drawBitmap(tunaSVGSrcBitmap, tunaSVGMatrix, initTunaPaint());

        //
        tunaPaint.setXfermode(tunaPorterDuffXfermode);
        canvas.drawBitmap(tunaSrcBitmap, tunaMatrix, tunaPaint);

        tunaPaint.setXfermode(null);
    }
}
