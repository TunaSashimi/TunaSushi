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


import com.tunasushi.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;

import static com.tunasushi.tool.BitmapTool.getCircleBitmap;
import static com.tunasushi.tool.BitmapTool.getSVGBitmap;
import static com.tunasushi.tool.ConvertTool.dpToPx;

/**
 * @author TunaSashimi
 * @date 2015-11-15 16:17
 * @Copyright 2015 TunaSashimi. All rights reserved.
 * @Description
 */
public class TDraw extends TView {
    //
    private float drawThick;
    private int drawColor;

    //
    private Bitmap drawSrc;


    @IntDef({NORMAL, CIRCLE, STAR, HEART, FLOWER, PENTAGON, SIXTEENEDGE, FORTYEDGE, SNAIL,})
    @Retention(RetentionPolicy.SOURCE)
    public @interface drawStyle {
    }

    public static final int NORMAL = 0;
    public static final int CIRCLE = 1;
    public static final int STAR = 2;
    public static final int HEART = 3;
    public static final int FLOWER = 4;
    public static final int PENTAGON = 5;
    public static final int SIXTEENEDGE = 6;
    public static final int FORTYEDGE = 7;
    public static final int SNAIL = 8;
    private static final int[] drawStyleArray = {NORMAL, CIRCLE, STAR, HEART, FLOWER, PENTAGON, SIXTEENEDGE, FORTYEDGE, SNAIL,};
    private @drawStyle
    int drawStyle;

    private Bitmap drawDstBitmap;
    protected Matrix drawDstMatrix;

    public Matrix getDrawDstMatrix() {
        return drawDstMatrix;
    }

    public void setDrawDstMatrix(Matrix drawDstMatrix) {
        this.drawDstMatrix = drawDstMatrix;
    }

    protected Matrix initPaintingDstMatrix(float sx, float sy) {
        if (drawDstMatrix == null) {
            drawDstMatrix = new Matrix();
        }
        drawDstMatrix.reset();
        drawDstMatrix.setScale(sx, sy);
        return drawDstMatrix;
    }


    //
    private Paint drawPaint;
    private float drawX, drawY;
    private static final float TOUCH_TOLERANCE = 4;

    public Paint getPaintingPaint() {
        return paint;
    }

    public TDraw(Context context) {
        this(context, null);
    }

    public TDraw(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TDraw(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        tag = TDraw.class.getSimpleName();

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TDraw);

        drawThick = typedArray.getDimension(R.styleable.TDraw_drawThick, dpToPx(12));
        drawColor = typedArray.getColor(R.styleable.TDraw_drawColor, Color.RED);

        int drawSrcId = typedArray.getResourceId(R.styleable.TDraw_drawSrc, -1);
        if (drawSrcId != -1) {
            drawSrc = BitmapFactory.decodeResource(getResources(), drawSrcId);
        }

        //
        int drawStyleIndex = typedArray.getInt(R.styleable.TDraw_drawStyle, -1);
        if (drawStyleIndex >= 0) {
            drawStyle = drawStyleArray[drawStyleIndex];
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

        //When the parent class in onMeasure initialized paint in TView
        initPaint(Paint.Style.STROKE, drawColor, drawThick);
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);

        //
        srcBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(srcBitmap);

        drawPaint = new Paint(Paint.DITHER_FLAG);
        //
        if (drawSrc != null) {
            scaleSx = width * 1f / drawSrc.getWidth();
            scaleSy = height * 1f / drawSrc.getHeight();
            matrixNormal = initMatrix(matrixNormal, scaleSx, scaleSy);
        }

        //
        if (drawStyle != NORMAL) {
            int shortSide = width >= height ? height : width;
            initPaintingDstMatrix(width * 1f / shortSide, height * 1f / shortSide);

            switch (drawStyle) {
                case CIRCLE:
                    drawDstBitmap = getCircleBitmap(shortSide);
                    break;
                case STAR:
                    drawDstBitmap = getSVGBitmap(getContext(), shortSide, shortSide, R.raw.svg_star);
                    break;
                case HEART:
                    drawDstBitmap = getSVGBitmap(getContext(), shortSide, shortSide, R.raw.svg_heart);
                    break;
                case FLOWER:
                    drawDstBitmap = getSVGBitmap(getContext(), shortSide, shortSide, R.raw.svg_flower);
                    break;
                case PENTAGON:
                    drawDstBitmap = getSVGBitmap(getContext(), shortSide, shortSide, R.raw.svg_pentagon);
                    break;
                case SIXTEENEDGE:
                    drawDstBitmap = getSVGBitmap(getContext(), shortSide, shortSide, R.raw.svg_sixteenedge);
                    break;
                case FORTYEDGE:
                    drawDstBitmap = getSVGBitmap(getContext(), shortSide, shortSide, R.raw.svg_fortyedge);
                    break;
                case SNAIL:
                    drawDstBitmap = getSVGBitmap(getContext(), shortSide, shortSide, R.raw.svg_snail);
                    break;
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (drawStyle == NORMAL) {
            if (drawSrc != null) {
                canvas.drawBitmap(drawSrc, matrixNormal, null);
            }
            canvas.drawBitmap(srcBitmap, 0, 0, drawPaint);
            if (path != null) {
                canvas.drawPath(path, paint);
            }
        } else {
            //
            if (drawSrc != null) {
                canvas.saveLayer(0, 0, width, height, null, Canvas.ALL_SAVE_FLAG);
                canvas.drawBitmap(drawDstBitmap, drawDstMatrix, paint);
                paint.setXfermode(porterDuffXferStyle);
                canvas.drawBitmap(drawSrc, matrixNormal, paint);
                paint.setXfermode(null);
                canvas.restore();
            }

            //
            canvas.saveLayer(0, 0, width, height, null, Canvas.ALL_SAVE_FLAG);
            canvas.drawBitmap(drawDstBitmap, drawDstMatrix, drawPaint);
            drawPaint.setXfermode(porterDuffXferStyle);
            canvas.drawBitmap(srcBitmap, 0, 0, drawPaint);
            drawPaint.setXfermode(null);
            canvas.restore();

            //
            if (path != null) {
                canvas.drawPath(path, paint);
            }
        }
    }

    public void drawTouchDown(float x, float y) {
        initPathMoveTo(x, y);
        drawX = x;
        drawY = y;
        invalidate();
    }

    public void drawTouchMove(float x, float y) {
        dx = Math.abs(x - drawX);
        dy = Math.abs(y - drawY);
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            path.quadTo(drawX, drawY, (x + drawX) / 2, (y + drawY) / 2);
            drawX = x;
            drawY = y;
        }
        invalidate();
    }

    public void drawTouchUp() {
        path.lineTo(drawX, drawY);
        canvas.drawPath(path, paint);
        invalidate();
    }

    //
    public void setPaintingListener() {
        setTouchDownListener(new TouchDownListener() {
            @Override
            public void touchDown(TView t) {
                drawTouchDown(getTouchX(), getTouchY());
            }
        });

        setTouchMoveListener(new TouchMoveListener() {
            @Override
            public void touchMove(TView t) {
                drawTouchMove(getTouchX(), getTouchY());
            }
        });

        setTouchUpListener(new TouchUpListener() {
            @Override
            public void touchUp(TView t) {
                drawTouchUp();
            }
        });
    }
}