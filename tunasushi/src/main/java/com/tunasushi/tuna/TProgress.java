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

/**
 * @author TunaSashimi
 * @date 2015-10-30 16:56
 * @Copyright 2015 TunaSashimi. All rights reserved.
 * @Description
 */
public class TProgress extends TView {
    private int progressArcBackground;
    private int progressBoundBackground;
    private Bitmap progressSrcBack, progressSrcFront;

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

        tag = TProgress.class.getSimpleName();

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

            int progressSrcBackId = typedArray.getResourceId(R.styleable.TProgress_progressSrcBack, -1);
            if (progressSrcBackId != -1) {
                progressSrcBack = BitmapFactory.decodeResource(getResources(), progressSrcBackId);
            } else {
                throw new IllegalArgumentException("The content attribute require a property named progressSrcBack");
            }

            int progressSrcFrontId = typedArray.getResourceId(R.styleable.TProgress_progressSrcFront, -1);
            if (progressSrcFrontId != -1) {
                progressSrcFront = BitmapFactory.decodeResource(getResources(), progressSrcFrontId);
            } else {
                throw new IllegalArgumentException("The content attribute require a property named progressSrcFront");
            }
        } else {
            progressArcBackground = typedArray.getColor(R.styleable.TProgress_progressArcBackground, Color.TRANSPARENT);
            progressBoundBackground = typedArray.getColor(R.styleable.TProgress_progressBoundBackground, Color.TRANSPARENT);
        }

        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        typedArray.recycle();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if (progressShapeType == ProgressShapeType.CUSTOM) {
            int progressSrcFrontWidth = progressSrcFront.getWidth();
            int progressSrcFrontHeight = progressSrcFront.getHeight();
            int progressSrcBackWidth = progressSrcBack.getWidth();
            int progressSrcBackHeight = progressSrcBack.getHeight();
            if (progressSrcFrontWidth != progressSrcBackWidth || progressSrcFrontHeight != progressSrcBackHeight) {
                throw new IndexOutOfBoundsException("Both the width and height of the attribute progressSrcFront and progressSrcBack needed equal");
            }

            if (progressShapeType == ProgressShapeType.CUSTOM) {
                scale = width * 1f / progressSrcBackWidth;
            }
            matrix = initMatrix(matrix, scale, scale);
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        switch (progressShapeType) {
            case CUSTOM:

                canvas.drawBitmap(progressSrcBack, matrix, null);
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

                canvas.drawBitmap(progressSrcFront, matrix, null);
                canvas.restore();

                break;
            case CIRCLE:

                canvas.drawCircle(width >> 1, height >> 1, width >> 1, initPaint(Paint.Style.STROKE, progressBoundBackground));

                switch (progressPromoteType) {
                    case CLOCKWISE:
                        canvas.drawArc(initRectF(0, 0, width, height), PROMOTE_CIRCLE_STARTANGLE, 360 * percent, true, initPaint(Paint.Style.FILL, progressArcBackground));
                        break;
                    case UPWARD:
                        canvas.save();
                        canvas.clipRect(initRect(0, (int) (height * (1 - percent)), width, height));
                        canvas.drawCircle(width >> 1, height >> 1, width >> 1, initPaint(Paint.Style.FILL, progressBoundBackground));
                        canvas.restore();
                        break;
                    case UPDOWN:
                        canvas.save();
                        canvas.clipRect(initRect(0, (int) (height * percent * 0.5f), width, (int) (height * (1 - percent * 0.5f))), Op.DIFFERENCE);
                        canvas.drawCircle(width >> 1, height >> 1, width >> 1, initPaint(Paint.Style.FILL, progressBoundBackground));
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
