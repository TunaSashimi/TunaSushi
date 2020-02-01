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

import static com.tunasushi.tool.PaintTool.initTunaPaint;

/**
 * @author Tunasashimi
 * @date 10/30/15 16:56
 * @Copyright 2015 TunaSashimi. All rights reserved.
 * @Description
 */
public class TProgress extends TView {
    private int tunaProgressArcBackgroundNormal, tunaProgressBoundBackgroundNormal;
    private Bitmap tunaProgressBitmapSrcBack, tunaProgressBitmapSrcFront;

    private TunaProgressShapeType tunaProgressShapeType;

    public enum TunaProgressShapeType {
        CUSTOM(0),
        CIRCLE(1),;
        final int nativeInt;

        TunaProgressShapeType(int ni) {
            nativeInt = ni;
        }
    }

    private static final TunaProgressShapeType[] tunaProgressShapeTypeArray = {
            TunaProgressShapeType.CUSTOM,
            TunaProgressShapeType.CIRCLE,
    };

    private TunaProgressPromoteType tunaProgressPromoteType;

    public enum TunaProgressPromoteType {
        CLOCKWISE(0),
        UPWARD(1),
        UPDOWN(2),;
        final int nativeInt;

        TunaProgressPromoteType(int ni) {
            nativeInt = ni;
        }
    }

    private static final TunaProgressPromoteType[] tunaProgressPromoteTypeArray = {
            TunaProgressPromoteType.CLOCKWISE,
            TunaProgressPromoteType.UPWARD,
            TunaProgressPromoteType.UPDOWN,
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

        tunaTag = TProgress.class.getSimpleName();

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TProgress);

        int tunaProgressShapeTypeIndex = typedArray.getInt(R.styleable.TProgress_progressShapeType, -1);
        if (tunaProgressShapeTypeIndex >= 0) {
            tunaProgressShapeType = tunaProgressShapeTypeArray[tunaProgressShapeTypeIndex];
        } else {
            throw new IllegalArgumentException("The content attribute require a property named tunaProgressShapeType");
        }

        int tunaProgressPromoteTypeIndex = typedArray.getInt(R.styleable.TProgress_progressPromoteType, -1);
        if (tunaProgressPromoteTypeIndex >= 0) {
            tunaProgressPromoteType = tunaProgressPromoteTypeArray[tunaProgressPromoteTypeIndex];
        } else {
            throw new IllegalArgumentException("The content attribute require a property named tunaProgressPromoteType");
        }

        if (tunaProgressShapeType == TunaProgressShapeType.CUSTOM) {

            int tunaProgressBitmapSrcBackId = typedArray.getResourceId(R.styleable.TProgress_progressBitmapSrcBack, -1);
            if (tunaProgressBitmapSrcBackId != -1) {
                tunaProgressBitmapSrcBack = BitmapFactory.decodeResource(getResources(), tunaProgressBitmapSrcBackId);
            } else {
                throw new IllegalArgumentException("The content attribute require a property named tunaProgressBitmapSrcBack");
            }

            int tunaProgressBitmapSrcFrontId = typedArray.getResourceId(R.styleable.TProgress_progressBitmapSrcFront, -1);
            if (tunaProgressBitmapSrcFrontId != -1) {
                tunaProgressBitmapSrcFront = BitmapFactory.decodeResource(getResources(), tunaProgressBitmapSrcFrontId);
            } else {
                throw new IllegalArgumentException("The content attribute require a property named tunaProgressBitmapSrcFront");
            }
        } else {
            tunaProgressArcBackgroundNormal = typedArray.getColor(R.styleable.TProgress_progressArcBackgroundNormal, Color.TRANSPARENT);
            tunaProgressBoundBackgroundNormal = typedArray.getColor(R.styleable.TProgress_progressBoundBackgroundNormal, Color.TRANSPARENT);
        }

        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        typedArray.recycle();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if (tunaProgressShapeType == TunaProgressShapeType.CUSTOM) {
            int tunaProgressBitmapSrcFrontWidth = tunaProgressBitmapSrcFront.getWidth();
            int tunaProgressBitmapSrcFrontHeight = tunaProgressBitmapSrcFront.getHeight();
            int tunaProgressBitmapSrcBackWidth = tunaProgressBitmapSrcBack.getWidth();
            int tunaProgressBitmapSrcBackHeight = tunaProgressBitmapSrcBack.getHeight();
            if (tunaProgressBitmapSrcFrontWidth != tunaProgressBitmapSrcBackWidth || tunaProgressBitmapSrcFrontHeight != tunaProgressBitmapSrcBackHeight) {
                throw new IndexOutOfBoundsException("Both the width and height of the attribute tunaProgressBitmapSrcFront and tunaProgressBitmapSrcBack needed equal");
            }

            if (tunaProgressShapeType == TunaProgressShapeType.CUSTOM) {
                tunaScale = tunaWidth * 1f / tunaProgressBitmapSrcBackWidth;
            }
            initTunaMatrix(tunaScale, tunaScale);
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        switch (tunaProgressShapeType) {
            case CUSTOM:

                canvas.drawBitmap(tunaProgressBitmapSrcBack, tunaMatrix, null);
                canvas.save();

                switch (tunaProgressPromoteType) {
                    case CLOCKWISE:
                        initTunaPathMoveTo(tunaWidth >> 1, tunaHeight >> 1);
                        tunaPath.lineTo(tunaWidth >> 1, 0);
                        //Change two times to deal with circular coverage to the part to be lazy
                        tunaPath.addArc(initTunaRectF(-tunaWidth, -tunaHeight, tunaWidth << 1, tunaWidth << 1), PROMOTE_CIRCLE_STARTANGLE, 360 * tunaPercent);
                        tunaPath.lineTo(tunaWidth >> 1, tunaHeight >> 1);
                        tunaPath.close();
                        canvas.clipPath(tunaPath);
                        break;
                    case UPWARD:
                        canvas.clipRect(initTunaRect(0, (int) (tunaHeight * (1 - tunaPercent)), tunaWidth, tunaHeight));
                        break;
                    case UPDOWN:
                        canvas.clipRect(initTunaRect(0, (int) (tunaHeight * tunaPercent * 0.5f), tunaWidth, (int) (tunaHeight * (1 - tunaPercent * 0.5f))), Op.INTERSECT);
                        break;
                    default:
                        break;
                }

                canvas.drawBitmap(tunaProgressBitmapSrcFront, tunaMatrix, null);
                canvas.restore();

                break;
            case CIRCLE:

                canvas.drawCircle(tunaWidth >> 1, tunaHeight >> 1, tunaWidth >> 1, initTunaPaint(Paint.Style.STROKE, tunaProgressBoundBackgroundNormal));

                switch (tunaProgressPromoteType) {
                    case CLOCKWISE:
                        canvas.drawArc(initTunaRectF(0, 0, tunaWidth, tunaHeight), PROMOTE_CIRCLE_STARTANGLE, 360 * tunaPercent, true, initTunaPaint(Paint.Style.FILL, tunaProgressArcBackgroundNormal));
                        break;
                    case UPWARD:
                        canvas.save();
                        canvas.clipRect(initTunaRect(0, (int) (tunaHeight * (1 - tunaPercent)), tunaWidth, tunaHeight));
                        canvas.drawCircle(tunaWidth >> 1, tunaHeight >> 1, tunaWidth >> 1, initTunaPaint(Paint.Style.FILL, tunaProgressBoundBackgroundNormal));
                        canvas.restore();
                        break;
                    case UPDOWN:
                        canvas.save();
                        canvas.clipRect(initTunaRect(0, (int) (tunaHeight * tunaPercent * 0.5f), tunaWidth, (int) (tunaHeight * (1 - tunaPercent * 0.5f))), Op.INTERSECT);
                        canvas.drawCircle(tunaWidth >> 1, tunaHeight >> 1, tunaWidth >> 1, initTunaPaint(Paint.Style.FILL, tunaProgressBoundBackgroundNormal));
                        canvas.restore();
                        break;
                    default:
                        break;
                }
                break;
        }

    }

    public float getTunaProgressPercent() {
        return tunaPercent;
    }

    public void setTunaProgressPercent(float tunaProgressBallPercent) {
        this.tunaPercent = tunaProgressBallPercent;
        invalidate();
    }
}
