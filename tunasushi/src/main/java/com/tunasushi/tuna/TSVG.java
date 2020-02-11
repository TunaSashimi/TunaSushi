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

/**
 * @author Tunasashimi
 * @date 10/30/15 16:52
 * @Copyright 2015 Sashimi. All rights reserved.
 * @Description
 */
public class TSVG extends TView {

    //
    private Bitmap SVGSrc;

    public Bitmap getSVGSrc() {
        return SVGSrc;
    }

    public void setSVGSrc(Bitmap SVGSrc) {
        this.SVGSrc = SVGSrc;
    }

    //
    protected Matrix SVGMatrix;

    public Matrix getSVGMatrix() {
        return SVGMatrix;
    }

    public void setSVGMatrix(Matrix SVGMatrix) {
        this.SVGMatrix = SVGMatrix;
    }

    protected Matrix initSVGMatrix(float sx, float sy) {
        if (SVGMatrix == null) {
            SVGMatrix = new Matrix();
        }
        SVGMatrix.reset();
        SVGMatrix.setScale(sx, sy);
        return SVGMatrix;
    }

    private SVGType svgType;

    public enum SVGType {
        CIRCLE(0),
        STAR(1),
        HEART(2),
        FLOWER(3),
        PENTAGON(4),
        SIXTEENEDGE(5),
        FORTYEDGE(6),
        SNAIL(7),
        ;
        final int nativeInt;

        SVGType(int ni) {
            nativeInt = ni;
        }
    }

    private static final SVGType[] SVGTypeArray = {
            SVGType.CIRCLE,
            SVGType.STAR,
            SVGType.HEART,
            SVGType.FLOWER,
            SVGType.PENTAGON,
            SVGType.SIXTEENEDGE,
            SVGType.FORTYEDGE,
            SVGType.SNAIL,
    };


    public TSVG(Context context) {
        this(context, null);
    }

    public TSVG(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TSVG(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        tag = TSVG.class.getSimpleName();

        //
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TSVG);
        int SVGSrcId = typedArray.getResourceId(R.styleable.TSVG_SVGSrc, -1);
        if (SVGSrcId != -1) {
            srcBitmap = BitmapFactory.decodeResource(getResources(), SVGSrcId);
        }

        int tunaSVGTypeIndex = typedArray.getInt(R.styleable.TSVG_SVGType, 0);
        if (tunaSVGTypeIndex >= 0) {
            svgType = SVGTypeArray[tunaSVGTypeIndex];
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

        scaleSx = width * 1f / srcBitmap.getWidth();
        scaleSy = height * 1f / srcBitmap.getHeight();
        initMatrix(scaleSx, scaleSy);

        int shortSide = width >= height ? height : width;
        initSVGMatrix(width * 1f / shortSide, height * 1f / shortSide);

        switch (svgType) {
            case CIRCLE:
                SVGSrc = getCircleBitmap(shortSide);
                break;
            case STAR:
                SVGSrc =getSVGBitmap(getContext(),shortSide, shortSide, R.raw.svg_star);
                break;
            case HEART:
                SVGSrc = getSVGBitmap(getContext(),shortSide, shortSide, R.raw.svg_heart);
                break;
            case FLOWER:
                SVGSrc = getSVGBitmap(getContext(),shortSide, shortSide, R.raw.svg_flower);
                break;
            case PENTAGON:
                SVGSrc = getSVGBitmap(getContext(),shortSide, shortSide, R.raw.svg_pentagon);
                break;
            case SIXTEENEDGE:
                SVGSrc = getSVGBitmap(getContext(),shortSide, shortSide, R.raw.svg_sixteenedge);
                break;
            case FORTYEDGE:
                SVGSrc = getSVGBitmap(getContext(),shortSide, shortSide, R.raw.svg_fortyedge);
                break;
            case SNAIL:
                SVGSrc = getSVGBitmap(getContext(),shortSide, shortSide, R.raw.svg_snail);
                break;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.saveLayer(0, 0, width, height, null, Canvas.ALL_SAVE_FLAG);

        //
        canvas.drawBitmap(SVGSrc, SVGMatrix, initPaint());

        //
        paint.setXfermode(TPorterDuffXfermode);
        canvas.drawBitmap(srcBitmap, matrix, paint);

        paint.setXfermode(null);
    }
}
