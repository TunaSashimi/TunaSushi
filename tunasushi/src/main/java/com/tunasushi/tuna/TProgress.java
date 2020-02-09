package com.tunasushi.tuna;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Region.Op;
import android.util.AttributeSet;
import android.view.View;

import com.tuna.R;
import com.tunasushi.tool.PaintTool;

import static com.tunasushi.tool.PathTool.initPathMoveTo;
import static com.tunasushi.tool.PathTool.path;

/**
 * @author Tunasashimi
 * @date 10/30/15 16:56
 * @Copyright 2015 Sashimi. All rights reserved.
 * @Description
 */
public class TProgress extends TView {
    private int progressArcBackgroundNormal, progressBoundBackgroundNormal;
    private Bitmap progressBitmapSrcBack, progressBitmapSrcFront;

    private ProgressShapeType progressShapeType;

    public enum ProgressShapeType {
        CUSTOM(0),
        CIRCLE(1),
        ;
        final int nativeInt;

        ProgressShapeType(int ni) {
            nativeInt = ni;
        }
    }

    private static final ProgressShapeType[] progressShapeTypeArray = {
            ProgressShapeType.CUSTOM,
            ProgressShapeType.CIRCLE,
    };

    private ProgressPromoteType progressPromoteType;

    public enum ProgressPromoteType {
        CLOCKWISE(0),
        UPWARD(1),
        UPDOWN(2),
        ;
        final int nativeInt;

        ProgressPromoteType(int ni) {
            nativeInt = ni;
        }
    }

    private static final ProgressPromoteType[] progressPromoteTypeArray = {
            ProgressPromoteType.CLOCKWISE,
            ProgressPromoteType.UPWARD,
            ProgressPromoteType.UPDOWN,
    };

    private static final float PROMOTE_CIRCLE_STARTANGLE = -90;

    public TProgress(Context context) {
        this(context, null);
    }

    public TProgress(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TProgress(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        Tag = TProgress.class.getSimpleName();

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TProgress);

        int progressShapeTypeIndex = typedArray.getInt(R.styleable.TProgress_progressShapeType, -1);
        if (progressShapeTypeIndex >= 0) {
            progressShapeType = progressShapeTypeArray[progressShapeTypeIndex];
        } else {
            throw new IllegalArgumentException("The content attribute require a property named progressShapeType");
        }

        int progressPromoteTypeIndex = typedArray.getInt(R.styleable.TProgress_progressPromoteType, -1);
        if (progressPromoteTypeIndex >= 0) {
            progressPromoteType = progressPromoteTypeArray[progressPromoteTypeIndex];
        } else {
            throw new IllegalArgumentException("The content attribute require a property named progressPromoteType");
        }

        if (progressShapeType == ProgressShapeType.CUSTOM) {

            int progressBitmapSrcBackId = typedArray.getResourceId(R.styleable.TProgress_progressBitmapSrcBack, -1);
            if (progressBitmapSrcBackId != -1) {
                progressBitmapSrcBack = BitmapFactory.decodeResource(getResources(), progressBitmapSrcBackId);
            } else {
                throw new IllegalArgumentException("The content attribute require a property named progressBitmapSrcBack");
            }

            int progressBitmapSrcFrontId = typedArray.getResourceId(R.styleable.TProgress_progressBitmapSrcFront, -1);
            if (progressBitmapSrcFrontId != -1) {
                progressBitmapSrcFront = BitmapFactory.decodeResource(getResources(), progressBitmapSrcFrontId);
            } else {
                throw new IllegalArgumentException("The content attribute require a property named progressBitmapSrcFront");
            }
        } else {
            progressArcBackgroundNormal = typedArray.getColor(R.styleable.TProgress_progressArcBackgroundNormal, Color.TRANSPARENT);
            progressBoundBackgroundNormal = typedArray.getColor(R.styleable.TProgress_progressBoundBackgroundNormal, Color.TRANSPARENT);
        }

        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        typedArray.recycle();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if (progressShapeType == ProgressShapeType.CUSTOM) {
            int progressBitmapSrcFrontWidth = progressBitmapSrcFront.getWidth();
            int progressBitmapSrcFrontHeight = progressBitmapSrcFront.getHeight();
            int progressBitmapSrcBackWidth = progressBitmapSrcBack.getWidth();
            int progressBitmapSrcBackHeight = progressBitmapSrcBack.getHeight();
            if (progressBitmapSrcFrontWidth != progressBitmapSrcBackWidth || progressBitmapSrcFrontHeight != progressBitmapSrcBackHeight) {
                throw new IndexOutOfBoundsException("Both the width and height of the attribute progressBitmapSrcFront and progressBitmapSrcBack needed equal");
            }

            if (progressShapeType == ProgressShapeType.CUSTOM) {
                scale = width * 1f / progressBitmapSrcBackWidth;
            }
            initMatrix(scale, scale);
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        switch (progressShapeType) {
            case CUSTOM:

                canvas.drawBitmap(progressBitmapSrcBack, matrix, null);
                canvas.save();

                switch (progressPromoteType) {
                    case CLOCKWISE:
                        initPathMoveTo(width >> 1, height >> 1);
                        path.lineTo(width >> 1, 0);
                        //Change two times to deal with circular coverage to the part to be lazy
                        path.addArc(initRectF(-width, -height, width << 1, width << 1), PROMOTE_CIRCLE_STARTANGLE, 360 * percent);
                        path.lineTo(width >> 1, height >> 1);
                        path.close();
                        canvas.clipPath(path);
                        break;
                    case UPWARD:
                        canvas.clipRect(initRect(0, (int) (height * (1 - percent)), width, height));
                        break;
                    case UPDOWN:
                        canvas.clipRect(initRect(0, (int) (height * percent * 0.5f), width, (int) (height * (1 - percent * 0.5f))), Op.DIFFERENCE);
                        break;
                    default:
                        break;
                }

                canvas.drawBitmap(progressBitmapSrcFront, matrix, null);
                canvas.restore();

                break;
            case CIRCLE:

                canvas.drawCircle(width >> 1, height >> 1, width >> 1, PaintTool.initPaint(Paint.Style.STROKE, progressBoundBackgroundNormal));

                switch (progressPromoteType) {
                    case CLOCKWISE:
                        canvas.drawArc(initRectF(0, 0, width, height), PROMOTE_CIRCLE_STARTANGLE, 360 * percent, true, PaintTool.initPaint(Paint.Style.FILL, progressArcBackgroundNormal));
                        break;
                    case UPWARD:
                        canvas.save();
                        canvas.clipRect(initRect(0, (int) (height * (1 - percent)), width, height));
                        canvas.drawCircle(width >> 1, height >> 1, width >> 1, PaintTool.initPaint(Paint.Style.FILL, progressBoundBackgroundNormal));
                        canvas.restore();
                        break;
                    case UPDOWN:
                        canvas.save();
                        canvas.clipRect(initRect(0, (int) (height * percent * 0.5f), width, (int) (height * (1 - percent * 0.5f))), Op.DIFFERENCE);
                        canvas.drawCircle(width >> 1, height >> 1, width >> 1, PaintTool.initPaint(Paint.Style.FILL, progressBoundBackgroundNormal));
                        canvas.restore();
                        break;
                    default:
                        break;
                }
                break;
        }

    }

    public float getProgressPercent() {
        return percent;
    }

    public void setProgressPercent(float progressPercent) {
        this.percent = progressPercent;
        invalidate();
    }
}
