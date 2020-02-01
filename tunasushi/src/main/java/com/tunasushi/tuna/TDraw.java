package com.tunasushi.tuna;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.tuna.R;

import static com.tunasushi.tool.BitmapTool.decodeBitmapResource;
import static com.tunasushi.tool.BitmapTool.getCircleBitmap;
import static com.tunasushi.tool.BitmapTool.getSVGBitmap;
import static com.tunasushi.tool.PaintTool.initTunaPaint;
import static com.tunasushi.tool.PaintTool.tunaPaint;

/**
 * @author Tunasashimi
 * @date 11/15/15 16:17
 * @Copyright 2015 TunaSashimi. All rights reserved.
 * @Description
 */
public class TDraw extends TView {

    //
    private int tunaDrawColor;

    public int getTunaDrawColor() {
        return tunaDrawColor;
    }

    public void setTunaDrawColor(int tunaDrawColor) {
        this.tunaDrawColor = tunaDrawColor;
    }

    private float tunaDrawWidth;

    public float getTunaDrawWidth() {
        return tunaDrawWidth;
    }

    public void setTunaDrawWidth(float tunaDrawWidth) {
        this.tunaDrawWidth = tunaDrawWidth;
    }

    //
    private Bitmap tunaDrawSrc;

    public Bitmap getTunaDrawSrc() {
        return tunaDrawSrc;
    }

    public void setTunaDrawSrc(int id) {
        setTunaPaintingSrc(decodeBitmapResource(getContext(),id));
    }

    public void setTunaPaintingSrc(Bitmap tunaPaintingSrc) {
        this.tunaDrawSrc = tunaPaintingSrc;
    }


    private TunaPaintingType tunaDrawType;
    public enum TunaPaintingType {
        CIRCLE(0),
        STAR(1),
        HEART(2),
        FLOWER(3),
        PENTAGON(4),
        SIXTEENEDGE(5),
        FORTYEDGE(6),
        SNAIL(7),;
        final int nativeInt;

        TunaPaintingType(int ni) {
            nativeInt = ni;
        }
    }

    private static final TunaPaintingType[] tunaScaleTypeArray = {
            TunaPaintingType.CIRCLE,
            TunaPaintingType.STAR,
            TunaPaintingType.HEART,
            TunaPaintingType.FLOWER,
            TunaPaintingType.PENTAGON,
            TunaPaintingType.SIXTEENEDGE,
            TunaPaintingType.FORTYEDGE,
            TunaPaintingType.SNAIL,
    };

    private Bitmap tunaDrawDstBitmap;
    protected Matrix tunaDrawDstMatrix;

    public Matrix getTunaDrawDstMatrix() {
        return tunaDrawDstMatrix;
    }

    public void setTunaDrawDstMatrix(Matrix tunaDrawDstMatrix) {
        this.tunaDrawDstMatrix = tunaDrawDstMatrix;
    }

    protected Matrix initTunaPaintingDstMatrix(float sx, float sy) {
        if (tunaDrawDstMatrix == null) {
            tunaDrawDstMatrix = new Matrix();
        }
        tunaDrawDstMatrix.reset();
        tunaDrawDstMatrix.setScale(sx, sy);
        return tunaDrawDstMatrix;
    }


    //
    private Paint mBitmapPaint;
    private float mX, mY;
    private static final float TOUCH_TOLERANCE = 4;

    public Paint getTunaPaintingPaint() {
        return tunaPaint;
    }

    public TDraw(Context context) {
        this(context, null);
    }

    public TDraw(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TDraw(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        tunaTag = TDraw.class.getSimpleName();

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TDraw);

        tunaDrawColor = typedArray.getColor(R.styleable.TDraw_drawColor, Color.RED);
        tunaDrawWidth = typedArray.getDimension(R.styleable.TDraw_drawWidth, 12);


        int tunaDrawSrcId = typedArray.getResourceId(R.styleable.TDraw_drawSrc, -1);
        if (tunaDrawSrcId != -1) {
            tunaDrawSrc = BitmapFactory.decodeResource(getResources(), tunaDrawSrcId);
        }

        //
        int tunaDrawTypeIndex = typedArray.getInt(R.styleable.TDraw_drawType, -1);
        if (tunaDrawTypeIndex >= 0) {
            tunaDrawType = tunaScaleTypeArray[tunaDrawTypeIndex];
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

        //When the parent class in onMeasure initialized  tunaPaint in TunaView
        initTunaPaint(Paint.Style.STROKE, tunaDrawColor, tunaDrawWidth);
        tunaPaint.setAntiAlias(true);
        tunaPaint.setDither(true);
        tunaPaint.setStrokeJoin(Paint.Join.ROUND);
        tunaPaint.setStrokeCap(Paint.Cap.ROUND);


        //
        tunaSrcBitmap = Bitmap.createBitmap(tunaWidth, tunaHeight, Bitmap.Config.ARGB_8888);
        tunaCanvas = new Canvas(tunaSrcBitmap);

        mBitmapPaint = new Paint(Paint.DITHER_FLAG);
        //
        if (tunaDrawSrc != null) {
            tunaScaleSx = tunaWidth * 1f / tunaDrawSrc.getWidth();
            tunaScaleSy = tunaHeight * 1f / tunaDrawSrc.getHeight();
            initTunaMatrix(tunaScaleSx, tunaScaleSy);
        }

        //
        if (tunaDrawType != null) {
            int shortSide = tunaWidth >= tunaHeight ? tunaHeight : tunaWidth;
            initTunaPaintingDstMatrix(tunaWidth * 1f / shortSide, tunaHeight * 1f / shortSide);

            if (isInEditMode()) {
                tunaDrawDstBitmap = getCircleBitmap(shortSide);
            } else {
                switch (tunaDrawType) {
                    case CIRCLE:
                        tunaDrawDstBitmap = getCircleBitmap(shortSide);
                        break;
                    case STAR:
                        tunaDrawDstBitmap = getSVGBitmap(getContext(), shortSide, shortSide, R.raw.svg_star);
                        break;
                    case HEART:
                        tunaDrawDstBitmap = getSVGBitmap(getContext(), shortSide, shortSide, R.raw.svg_heart);
                        break;
                    case FLOWER:
                        tunaDrawDstBitmap = getSVGBitmap(getContext(), shortSide, shortSide, R.raw.svg_flower);
                        break;
                    case PENTAGON:
                        tunaDrawDstBitmap = getSVGBitmap(getContext(), shortSide, shortSide, R.raw.svg_pentagon);
                        break;
                    case SIXTEENEDGE:
                        tunaDrawDstBitmap = getSVGBitmap(getContext(), shortSide, shortSide, R.raw.svg_sixteenedge);
                        break;
                    case FORTYEDGE:
                        tunaDrawDstBitmap = getSVGBitmap(getContext(), shortSide, shortSide, R.raw.svg_fortyedge);
                        break;
                    case SNAIL:
                        tunaDrawDstBitmap = getSVGBitmap(getContext(), shortSide, shortSide, R.raw.svg_snail);
                        break;
                }
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (tunaDrawType == null) {
            if (tunaDrawSrc != null) {
                canvas.drawBitmap(tunaDrawSrc, tunaMatrix, null);
            }
            canvas.drawBitmap(tunaSrcBitmap, 0, 0, mBitmapPaint);
            if (tunaPath != null) {
                canvas.drawPath(tunaPath, tunaPaint);
            }
        } else {

            //
            if (tunaDrawSrc != null) {
                canvas.saveLayer(0, 0, tunaWidth, tunaHeight, null, Canvas.ALL_SAVE_FLAG);
                canvas.drawBitmap(tunaDrawDstBitmap, tunaDrawDstMatrix, tunaPaint);
                tunaPaint.setXfermode(tunaPorterDuffXfermode);
                canvas.drawBitmap(tunaDrawSrc, tunaMatrix, tunaPaint);
                tunaPaint.setXfermode(null);
                canvas.restore();
            }

            //
            canvas.saveLayer(0, 0, tunaWidth, tunaHeight, null, Canvas.ALL_SAVE_FLAG);
            canvas.drawBitmap(tunaDrawDstBitmap, tunaDrawDstMatrix, mBitmapPaint);
            mBitmapPaint.setXfermode(tunaPorterDuffXfermode);
            canvas.drawBitmap(tunaSrcBitmap, 0, 0, mBitmapPaint);
            mBitmapPaint.setXfermode(null);
            canvas.restore();

            //
            if (tunaPath != null) {
                canvas.drawPath(tunaPath, tunaPaint);
            }
        }
    }

    public void touchDown(float x, float y) {
        initTunaPathMoveTo(x, y);
        mX = x;
        mY = y;
        invalidate();
    }

    public void touchMove(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            tunaPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            mX = x;
            mY = y;
        }
        invalidate();
    }

    public void touchUp() {
        tunaPath.lineTo(mX, mY);
        tunaCanvas.drawPath(tunaPath, tunaPaint);
        invalidate();
    }

    //
    public void setTunaPaintingListener() {
        setTunaTouchDownListener(new TView.TunaTouchDownListener() {
            @Override
            public void tunaTouchDown(View v) {
                touchDown(getTunaTouchEventX(), getTunaTouchEventY());
            }
        });
        setTunaTouchMoveListener(new TView.TunaTouchMoveListener() {
            @Override
            public void tunaTouchMove(View v) {
                touchMove(getTunaTouchEventX(), getTunaTouchEventY());

            }
        });
        setTunaTouchUpListener(new TView.TunaTouchUpListener() {
            @Override
            public void tunaTouchUp(View v) {
                touchUp();
            }
        });
    }
}