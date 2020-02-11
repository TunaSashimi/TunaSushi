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

/**
 * @author Tunasashimi
 * @date 11/15/15 16:17
 * @Copyright 2015 Sashimi. All rights reserved.
 * @Description
 */
public class TDraw extends TView {

    //
    private int drawColor;

    public int getDrawColor() {
        return drawColor;
    }

    public void setDrawColor(int drawColor) {
        this.drawColor = drawColor;
    }

    private float drawWidth;

    public float getDrawWidth() {
        return drawWidth;
    }

    public void setDrawWidth(float drawWidth) {
        this.drawWidth = drawWidth;
    }

    //
    private Bitmap drawSrc;

    public Bitmap getDrawSrc() {
        return drawSrc;
    }

    public void setDrawSrc(int id) {
        setDrawSrc(decodeBitmapResource(id));
    }

    public void setDrawSrc(Bitmap bitmap) {
        this.drawSrc = bitmap;
    }


    private DrawType drawType;

    public enum DrawType {
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

        DrawType(int ni) {
            nativeInt = ni;
        }
    }

    private static final DrawType[] drawTypeArray = {
            DrawType.CIRCLE,
            DrawType.STAR,
            DrawType.HEART,
            DrawType.FLOWER,
            DrawType.PENTAGON,
            DrawType.SIXTEENEDGE,
            DrawType.FORTYEDGE,
            DrawType.SNAIL,
    };

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

        drawColor = typedArray.getColor(R.styleable.TDraw_drawColor, Color.RED);
        drawWidth = typedArray.getDimension(R.styleable.TDraw_drawWidth, 12);


        int drawSrcId = typedArray.getResourceId(R.styleable.TDraw_drawSrc, -1);
        if (drawSrcId != -1) {
            drawSrc = BitmapFactory.decodeResource(getResources(), drawSrcId);
        }

        //
        int drawTypeIndex = typedArray.getInt(R.styleable.TDraw_drawType, -1);
        if (drawTypeIndex >= 0) {
            drawType = drawTypeArray[drawTypeIndex];
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
        initPaint(Paint.Style.STROKE, drawColor, drawWidth);
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
            initMatrix(scaleSx, scaleSy);
        }

        //
        if (drawType != null) {
            int shortSide = width >= height ? height : width;
            initPaintingDstMatrix(width * 1f / shortSide, height * 1f / shortSide);

            switch (drawType) {
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
        if (drawType == null) {
            if (drawSrc != null) {
                canvas.drawBitmap(drawSrc, matrix, null);
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
                paint.setXfermode(TPorterDuffXfermode);
                canvas.drawBitmap(drawSrc, matrix, paint);
                paint.setXfermode(null);
                canvas.restore();
            }

            //
            canvas.saveLayer(0, 0, width, height, null, Canvas.ALL_SAVE_FLAG);
            canvas.drawBitmap(drawDstBitmap, drawDstMatrix, drawPaint);
            drawPaint.setXfermode(TPorterDuffXfermode);
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
                drawTouchDown(getTouchEventX(), getTouchEventY());
            }
        });

        setTouchMoveListener(new TouchMoveListener() {
            @Override
            public void touchMove(TView t) {
                drawTouchMove(getTouchEventX(), getTouchEventY());
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