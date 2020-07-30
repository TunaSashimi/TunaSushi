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

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;

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


    @IntDef({CUSTOM, CIRCLE,})
    @Retention(RetentionPolicy.SOURCE)
    public @interface progressShape {
    }

    public static final int CUSTOM = 0;
    public static final int CIRCLE = 1;
    private static final int[] progressShapeArray = {CUSTOM, CIRCLE,};
    private @progressShape
    int progressShape;


    @IntDef({CLOCKWISE, UPWARD, UPDOWN,})
    @Retention(RetentionPolicy.SOURCE)
    public @interface progressEffect {
    }

    public static final int CLOCKWISE = 0;
    public static final int UPWARD = 1;
    public static final int UPDOWN = 2;
    private static final int[] progressEffectArray = {CLOCKWISE, UPWARD, UPDOWN,};
    private @progressEffect
    int progressEffect;

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

        int progressShapeIndex = typedArray.getInt(R.styleable.TProgress_progressShape, -1);
        if (progressShapeIndex >= 0) {
            progressShape = progressShapeArray[progressShapeIndex];
        } else {
            throw new IllegalArgumentException("The content attribute require a property named progressShape");
        }

        int progressEffectIndex = typedArray.getInt(R.styleable.TProgress_progressEffect, -1);
        if (progressEffectIndex >= 0) {
            progressEffect = progressEffectArray[progressEffectIndex];
        } else {
            throw new IllegalArgumentException("The content attribute require a property named progressEffect");
        }

        if (progressShape == CUSTOM) {

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

        if (progressShape == CUSTOM) {
            int progressSrcFrontWidth = progressSrcFront.getWidth();
            int progressSrcFrontHeight = progressSrcFront.getHeight();
            int progressSrcBackWidth = progressSrcBack.getWidth();
            int progressSrcBackHeight = progressSrcBack.getHeight();
            if (progressSrcFrontWidth != progressSrcBackWidth || progressSrcFrontHeight != progressSrcBackHeight) {
                throw new IndexOutOfBoundsException("Both the width and height of the attribute progressSrcFront and progressSrcBack needed equal");
            }

            if (progressShape == CUSTOM) {
                scale = width * 1f / progressSrcBackWidth;
            }
            matrixNormal = initMatrix(matrixNormal, scale, scale);
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        switch (progressShape) {
            case CUSTOM:

                canvas.drawBitmap(progressSrcBack, matrixNormal, null);
                canvas.save();

                switch (progressEffect) {
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

                canvas.drawBitmap(progressSrcFront, matrixNormal, null);
                canvas.restore();

                break;
            case CIRCLE:

                canvas.drawCircle(width >> 1, height >> 1, width >> 1, initPaint(Paint.Style.STROKE, progressBoundBackground));

                switch (progressEffect) {
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
