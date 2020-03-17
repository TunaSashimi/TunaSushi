package com.tunasushi.tuna;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.IntDef;
import android.util.AttributeSet;

import com.tuna.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * @author TunaSashimi
 * @date 2015-10-30 16:57
 * @Copyright 2015 TunaSashimi. All rights reserved.
 * @Description
 */
public class TRepeat extends TView {
    private float repeatItemFractionTop, repeatItemFractionBottom;
    private float repeatItemTextFractionTop, repeatItemTextFractionBottom;

    private Bitmap repeatSrcNormal, repeatSrcSelect;

    private int repeatIndex;

    private float repeatItemTextSize;
    private int repeatItemTextColorNormal, repeatItemTextColorSelect;

    private int repeatItemBackgroundNormal, repeatItemBackgroundSelect;


    @IntDef({CONNECT, CURRENT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface repeatSelectMode {
    }

    public static final int CONNECT = 0;
    public static final int CURRENT = 1;
    private static final int[] repeatSelectModeArray = {CONNECT, CURRENT,};
    private @repeatSelectMode
    int repeatSelectMode;


    @IntDef({CUSTOM, CIRCLE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface repeatShapeMode {
    }

    public static final int CUSTOM = 0;
    public static final int CIRCLE = 1;

    private static final int[] repeatShapeModeArray = {CUSTOM, CIRCLE,};
    private @repeatShapeMode
    int repeatShapeMode;

    public TRepeat(Context context) {
        this(context, null);
    }

    public TRepeat(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TRepeat(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        tag = TRepeat.class.getSimpleName();

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TRepeat);

        total = typedArray.getInt(R.styleable.TRepeat_repeatTotal, 0);

        int repeatItemTextArrayId = typedArray.getResourceId(R.styleable.TRepeat_repeatItemTextArray, -1);
        if (repeatItemTextArrayId != -1) {
            stringArray = typedArray.getResources().getStringArray(repeatItemTextArrayId);
            if (total != stringArray.length) {
                throw new IndexOutOfBoundsException("The content attribute repeatTotal and repeatItemTextArray must be the same length");
            }
        }

        repeatItemTextSize = typedArray.getDimension(R.styleable.TRepeat_repeatItemTextSize, textSizeDefault);
        repeatItemTextColorNormal = typedArray.getColor(R.styleable.TRepeat_repeatItemTextColorNormal, textColorDefault);
        repeatItemTextColorSelect = typedArray.getColor(R.styleable.TRepeat_repeatItemTextColorSelect, repeatItemTextColorNormal);

        repeatItemTextFractionTop = typedArray.getFraction(R.styleable.TRepeat_repeatItemTextFractionTop, 1, 1, 0);
        repeatItemTextFractionBottom = typedArray.getFraction(R.styleable.TRepeat_repeatItemTextFractionBottom, 1, 1, 1);

        if (repeatItemTextFractionBottom <= repeatItemTextFractionTop) {
            throw new IndexOutOfBoundsException("The content attribute repeatItemTextFractionBottom must be greater than repeatItemTextFractionTop");
        }

        repeatItemFractionTop = typedArray.getFraction(R.styleable.TRepeat_repeatItemFractionTop, 1, 1, 0);
        repeatItemFractionBottom = typedArray.getFraction(R.styleable.TRepeat_repeatItemFractionBottom, 1, 1, 1);

        if (repeatItemFractionBottom <= repeatItemFractionTop) {
            throw new IndexOutOfBoundsException("The content attribute repeatItemFractionBottom must be greater than repeatItemFractionTop");
        }

        int repeatShapeModeIndex = typedArray.getInt(R.styleable.TRepeat_repeatShapeMode, -1);
        if (repeatShapeModeIndex >= 0) {
            repeatShapeMode = repeatShapeModeArray[repeatShapeModeIndex];
        } else {
            throw new IllegalArgumentException("The content attribute require a property named repeatShapeMode");
        }

        if (repeatShapeMode == CUSTOM) {
            int repeatSrcNormalId = typedArray.getResourceId(R.styleable.TRepeat_repeatSrcNormal, -1);
            if (repeatSrcNormalId != -1) {
                repeatSrcNormal = BitmapFactory.decodeResource(getResources(), repeatSrcNormalId);
            }
            int repeatSrcSelectId = typedArray.getResourceId(R.styleable.TRepeat_repeatSrcSelect, -1);
            if (repeatSrcSelectId != -1) {
                repeatSrcSelect = BitmapFactory.decodeResource(getResources(), repeatSrcSelectId);
            }
        } else if (repeatShapeMode == CIRCLE) {
            repeatItemBackgroundNormal = typedArray.getColor(R.styleable.TRepeat_repeatItemBackgroundNormal, Color.TRANSPARENT);
            repeatItemBackgroundSelect = typedArray.getColor(R.styleable.TRepeat_repeatItemBackgroundSelect, repeatItemBackgroundNormal);
        }

        int repeatSelectModeIndex = typedArray.getInt(R.styleable.TRepeat_repeatSelectMode, -1);
        if (repeatSelectModeIndex >= 0) {
            repeatSelectMode = repeatSelectModeArray[repeatSelectModeIndex];
        } else {
            throw new IllegalArgumentException("The content attribute require a property named repeatSelectMode");
        }

        repeatIndex = typedArray.getInt(R.styleable.TRepeat_repeatIndex, -1);
        typedArray.recycle();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (total < 1) {
            throw new IndexOutOfBoundsException("The content attribute repeatTotal must be greater than or equal 1");
        } else {
            floatArray = new float[total];
        }

        if (repeatIndex < -1 || repeatIndex > total - 1) {
            throw new IndexOutOfBoundsException("The content attribute repeatIndex length must be no less than -1 and smaller than the total length minus 1");
        }

        if (repeatShapeMode == CUSTOM) {
            int repeatCustomSrcNormalWidth = repeatSrcNormal.getWidth();
            int repeatCustomSrcNormalHeight = repeatSrcNormal.getHeight();
            int repeatCustomSrcWidthSelect = repeatSrcSelect.getWidth();
            int repeatCustomSrcHeightSelect = repeatSrcSelect.getHeight();
            if (repeatCustomSrcNormalWidth != repeatCustomSrcWidthSelect || repeatCustomSrcNormalHeight != repeatCustomSrcHeightSelect) {
                throw new IndexOutOfBoundsException("Both the width and height of the attribute repeatCustomSrcNormal and repeatCustomSrcSelect needed equal");
            }

            srcHeightScale = height * (repeatItemFractionBottom - repeatItemFractionTop);
            srcWidthScale = srcHeightScale * repeatCustomSrcNormalWidth / repeatCustomSrcNormalHeight;
            scale = srcHeightScale / repeatCustomSrcNormalWidth;

            matrix = initMatrix(matrix, scale, scale);

        } else if (repeatShapeMode == CIRCLE) {
            srcHeightScale = height * (repeatItemFractionBottom - repeatItemFractionTop);
            srcWidthScale = srcHeightScale;
        }

        dy = height * repeatItemFractionTop;
        surplus = width - srcWidthScale * total;
        share = surplus / (total + 1);
        // repeatCentreXArray avoid generating with new
        for (int i = 0; i < total; i++) {
            floatArray[i] = share + srcWidthScale * 0.5f + (share + srcWidthScale) * i;
        }
        if (subLayoutListener != null) {
            subLayoutListener.subLayout(this);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //
        switch (repeatShapeMode) {
            case CUSTOM:

                // in order to draw a map of the effect of the half, the first painting baseBitmap
                for (int i = 0; i < total; i++) {
                    if (i == 0) {
                        canvas.translate(share, dy);
                    } else {
                        canvas.translate(share + srcWidthScale, 0);
                    }
                    canvas.drawBitmap(repeatSrcNormal, matrix, null);
                }
                canvas.translate((share + srcWidthScale) * (1 - total) - share, -dy);

                //
                if (stringArray != null) {
                    for (int i = 0; i < stringArray.length; i++) {
                        drawText(canvas, stringArray[i], width, floatArray[i], (height * repeatItemTextFractionTop + height
                                        * repeatItemTextFractionBottom) * 0.5f, 0, 0,
                                initTextPaint(Paint.Style.FILL, repeatItemTextColorNormal, repeatItemTextSize, Paint.Align.CENTER));
                    }
                }

                for (int i = 0; i < total; i++) {
                    if (i == 0) {
                        canvas.translate(share, dy);
                    } else {
                        canvas.translate(share + srcWidthScale, 0);
                    }
                    canvas.drawBitmap(repeatSelectMode == CONNECT ? i <= repeatIndex ? repeatSrcSelect
                            : repeatSrcNormal : i == repeatIndex ? repeatSrcSelect : repeatSrcNormal, matrix, null);
                }
                canvas.translate((share + srcWidthScale) * (1 - total) - share, -dy);
                if (stringArray != null) {
                    for (int i = 0; i < stringArray.length; i++) {
                        drawText(
                                canvas,
                                stringArray[i],
                                width,
                                floatArray[i],
                                (height * repeatItemTextFractionTop + height * repeatItemTextFractionBottom) * 0.5f,
                                0,
                                0,
                                initTextPaint(Paint.Style.FILL,
                                        repeatSelectMode == CONNECT ? i <= repeatIndex ? repeatItemTextColorSelect
                                                : repeatItemTextColorNormal : i == repeatIndex ? repeatItemTextColorSelect : repeatItemTextColorNormal,
                                        repeatItemTextSize, Paint.Align.CENTER));
                    }
                }
                break;

            case CIRCLE:

                // in order to draw a map of the effect of the half, the first painting baseBitmap
                for (int i = 0; i < total; i++) {
                    if (i == 0) {
                        canvas.translate(share, dy);
                    } else {
                        canvas.translate(share + srcWidthScale, 0);
                    }
                    canvas.drawCircle(srcWidthScale / 2, srcWidthScale / 2, srcWidthScale / 2, initPaint(repeatItemBackgroundNormal));
                }
                canvas.translate((share + srcWidthScale) * (1 - total) - share, -dy);

                //
                if (stringArray != null) {
                    for (int i = 0; i < stringArray.length; i++) {
                        drawText(canvas, stringArray[i], width, floatArray[i], (height * repeatItemTextFractionTop + height
                                        * repeatItemTextFractionBottom) * 0.5f, 0, 0,
                                initTextPaint(Paint.Style.FILL, repeatItemTextColorNormal, repeatItemTextSize, Paint.Align.CENTER));
                    }
                }
                for (int i = 0; i < total; i++) {
                    if (i == 0) {
                        canvas.translate(share, dy);
                    } else {
                        canvas.translate(share + srcWidthScale, 0);
                    }
                    canvas.drawCircle(srcWidthScale / 2, srcWidthScale / 2, srcWidthScale / 2, initPaint(repeatSelectMode == CONNECT ? i <= repeatIndex ? repeatItemBackgroundSelect
                            : repeatItemBackgroundNormal : i == repeatIndex ? repeatItemBackgroundSelect : repeatItemBackgroundNormal));
                }
                canvas.translate((share + srcWidthScale) * (1 - total) - share, -dy);
                if (stringArray != null) {
                    for (int i = 0; i < stringArray.length; i++) {
                        drawText(
                                canvas,
                                stringArray[i],
                                width,
                                floatArray[i],
                                (height * repeatItemTextFractionTop + height * repeatItemTextFractionBottom) * 0.5f,
                                0,
                                0,
                                initTextPaint(Paint.Style.FILL,
                                        repeatSelectMode == CONNECT ? i <= repeatIndex ? repeatItemTextColorSelect
                                                : repeatItemTextColorNormal : i == repeatIndex ? repeatItemTextColorSelect : repeatItemTextColorNormal,
                                        repeatItemTextSize, Paint.Align.CENTER));
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void setTouchXYRaw(float touchX, float touchY) {
        x = touchX;
        float distenceMin = width;
        for (int i = 0; i < total; i++) {
            float centreDistance = Math.abs(x - floatArray[i]);
            if (centreDistance < distenceMin) {
                repeatIndex = i;
                distenceMin = centreDistance;
            } else {
                break;
            }
        }
        invalidate();
    }

    public int getRepeatIndex() {
        return repeatIndex;
    }

    public void setRepeatIndex(int repeatIndex) {
        if (repeatIndex < -1 || repeatIndex > total - 1) {
            throw new IndexOutOfBoundsException("The content attribute repeatIndex length must be no less than -1 and smaller than the total length -1");
        }
        this.repeatIndex = repeatIndex;
        invalidate();
    }

    public float[] getRepeatCentreXArray() {
        return floatArray;
    }

    public void setRepeatCentreXArray(float[] repeatCentreXArray) {
        this.floatArray = repeatCentreXArray;
    }

    public int getRepeatTotal() {
        return total;
    }

    public void setRepeatTotal(int total) {
        this.total = total;
        invalidate();
    }

    public String[] getRepeatItemTextArray() {
        return stringArray;
    }

    public void setRepeatItemTextArray(String[] repeatItemTextArray) {
        this.stringArray = repeatItemTextArray;
    }
}
