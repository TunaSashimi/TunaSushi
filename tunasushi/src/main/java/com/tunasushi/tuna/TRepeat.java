package com.tunasushi.tuna;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import com.tuna.R;


/**
 * @author Tunasashimi
 * @date 10/30/15 16:57
 * @Copyright 2015 Sashimi. All rights reserved.
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

    private RepeatSelectType repeatSelectType;

    public enum RepeatSelectType {
        CONNECT(0), CURRENT(1),
        ;
        final int nativeInt;

        RepeatSelectType(int ni) {
            nativeInt = ni;
        }
    }

    private static final RepeatSelectType[] repeatSelectTypeArray = {RepeatSelectType.CONNECT, RepeatSelectType.CURRENT,};

    //
    private RepeatShapeType repeatShapeType;

    public enum RepeatShapeType {
        CUSTOM(0), CIRCLE(1);
        final int nativeInt;

        RepeatShapeType(int ni) {
            nativeInt = ni;
        }
    }

    private static final RepeatShapeType[] repeatShapeTypeArray = {RepeatShapeType.CUSTOM, RepeatShapeType.CIRCLE};

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

        int repeatItemTextValueArrayId = typedArray.getResourceId(R.styleable.TRepeat_repeatItemTextValueArray, -1);
        if (repeatItemTextValueArrayId != -1) {
            stringArray = typedArray.getResources().getStringArray(repeatItemTextValueArrayId);
            if (total != stringArray.length) {
                throw new IndexOutOfBoundsException("The content attribute repeatTotal and repeatItemTextValueArray must be the same length");
            }
        }

        repeatItemTextSize = typedArray.getDimension(R.styleable.TRepeat_repeatItemTextSize, 0);
        repeatItemTextColorNormal = typedArray.getColor(R.styleable.TRepeat_repeatItemTextColorNormal, Color.TRANSPARENT);
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


        int repeatShapeTypeIndex = typedArray.getInt(R.styleable.TRepeat_repeatShapeType, -1);
        if (repeatShapeTypeIndex >= 0) {
            repeatShapeType = repeatShapeTypeArray[repeatShapeTypeIndex];
        } else {
            throw new IllegalArgumentException("The content attribute require a property named repeatShapeType");
        }

        if (repeatShapeType == RepeatShapeType.CUSTOM) {
            int repeatSrcNormalId = typedArray.getResourceId(R.styleable.TRepeat_repeatSrcNormal, -1);
            if (repeatSrcNormalId != -1) {
                repeatSrcNormal = BitmapFactory.decodeResource(getResources(), repeatSrcNormalId);
            }
            int repeatSrcSelectId = typedArray.getResourceId(R.styleable.TRepeat_repeatSrcSelect, -1);
            if (repeatSrcSelectId != -1) {
                repeatSrcSelect = BitmapFactory.decodeResource(getResources(), repeatSrcSelectId);
            }
        } else if (repeatShapeType == RepeatShapeType.CIRCLE) {
            repeatItemBackgroundNormal = typedArray.getColor(R.styleable.TRepeat_repeatItemBackgroundNormal, Color.TRANSPARENT);
            repeatItemBackgroundSelect = typedArray.getColor(R.styleable.TRepeat_repeatItemBackgroundSelect, repeatItemBackgroundNormal);
        }


        int repeatSelectTypeIndex = typedArray.getInt(R.styleable.TRepeat_repeatSelectType, -1);
        if (repeatSelectTypeIndex >= 0) {
            repeatSelectType = repeatSelectTypeArray[repeatSelectTypeIndex];
        } else {
            throw new IllegalArgumentException("The content attribute require a property named repeatSelectType");
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


        if (repeatShapeType == RepeatShapeType.CUSTOM) {
            int repeatCustomSrcNormalWidth = repeatSrcNormal.getWidth();
            int repeatCustomSrcNormalHeight = repeatSrcNormal.getHeight();
            int repeatCustomSrcSelectWidth = repeatSrcSelect.getWidth();
            int repeatCustomSrcSelectHeight = repeatSrcSelect.getHeight();
            if (repeatCustomSrcNormalWidth != repeatCustomSrcSelectWidth || repeatCustomSrcNormalHeight != repeatCustomSrcSelectHeight) {
                throw new IndexOutOfBoundsException("Both the width and height of the attribute repeatCustomSrcNormal and repeatCustomSrcSelect needed equal");
            }

            srcHeightScale = height * (repeatItemFractionBottom - repeatItemFractionTop);
            srcWidthScale = srcHeightScale * repeatCustomSrcNormalWidth / repeatCustomSrcNormalHeight;
            scale = srcHeightScale / repeatCustomSrcNormalWidth;

            initMatrix(scale, scale);

        } else if (repeatShapeType == RepeatShapeType.CIRCLE) {
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
        super.onDraw(canvas);

        //
        switch (repeatShapeType) {
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

                // in order to draw a map of the effect of the half, drawing overlay
                if (press) {
                    canvas.save();
                    switch (repeatSelectType) {
                        case CONNECT:
                            canvas.clipRect(0, 0, x, height);
                            break;
                        case CURRENT:
                            canvas.clipRect(x - srcWidthScale, 0, x, height);
                            break;
                        default:
                            break;
                    }
                    for (int i = 0; i < total; i++) {
                        if (i == 0) {
                            canvas.translate(share, dy);
                        } else {
                            canvas.translate(share + srcWidthScale, 0);
                        }
                        canvas.drawBitmap(repeatSrcSelect, matrix, null);
                    }
                    canvas.translate((share + srcWidthScale) * (1 - total) - share, -dy);
                    if (stringArray != null) {
                        for (int i = 0; i < stringArray.length; i++) {
                            drawText(canvas, stringArray[i], width, floatArray[i], (height * repeatItemTextFractionTop + height
                                            * repeatItemTextFractionBottom) * 0.5f, 0, 0,
                                    initTextPaint(Paint.Style.FILL, repeatItemTextColorSelect, repeatItemTextSize, Paint.Align.CENTER));
                        }
                    }
                    canvas.restore();
                } else {
                    for (int i = 0; i < total; i++) {
                        if (i == 0) {
                            canvas.translate(share, dy);
                        } else {
                            canvas.translate(share + srcWidthScale, 0);
                        }
                        canvas.drawBitmap(repeatSelectType == RepeatSelectType.CONNECT ? i <= repeatIndex ? repeatSrcSelect
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
                                            repeatSelectType == RepeatSelectType.CONNECT ? i <= repeatIndex ? repeatItemTextColorSelect
                                                    : repeatItemTextColorNormal : i == repeatIndex ? repeatItemTextColorSelect : repeatItemTextColorNormal,
                                            repeatItemTextSize, Paint.Align.CENTER));
                        }
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

                // in order to draw a map of the effect of the half, drawing overlay
                if (press) {
                    canvas.save();
                    switch (repeatSelectType) {
                        case CONNECT:
                            canvas.clipRect(0, 0, x, height);
                            break;
                        case CURRENT:
                            canvas.clipRect(x - srcWidthScale, 0, x, height);
                            break;
                        default:
                            break;
                    }
                    for (int i = 0; i < total; i++) {
                        if (i == 0) {
                            canvas.translate(share, dy);
                        } else {
                            canvas.translate(share + srcWidthScale, 0);
                        }
//                        canvas.drawBitmap(repeatSrcSelect, matrix, null);
                        canvas.drawCircle(srcWidthScale / 2, srcWidthScale / 2, srcWidthScale / 2, initPaint(repeatItemBackgroundSelect));
                    }

                    canvas.translate((share + srcWidthScale) * (1 - total) - share, -dy);
                    if (stringArray != null) {
                        for (int i = 0; i < stringArray.length; i++) {
                            drawText(canvas, stringArray[i], width, floatArray[i], (height * repeatItemTextFractionTop + height
                                            * repeatItemTextFractionBottom) * 0.5f, 0, 0,
                                    initTextPaint(Paint.Style.FILL, repeatItemTextColorSelect, repeatItemTextSize, Paint.Align.CENTER));
                        }
                    }
                    canvas.restore();
                } else {
                    for (int i = 0; i < total; i++) {
                        if (i == 0) {
                            canvas.translate(share, dy);
                        } else {
                            canvas.translate(share + srcWidthScale, 0);
                        }
//                        canvas.drawBitmap(repeatSelectType == repeatSelectType.CONNECT ? i <= repeatIndex ? repeatSrcSelect
//                                : repeatSrcNormal : i == repeatIndex ? repeatSrcSelect : repeatSrcNormal, matrix, null);
                        canvas.drawCircle(srcWidthScale / 2, srcWidthScale / 2, srcWidthScale / 2, initPaint(repeatSelectType == RepeatSelectType.CONNECT ? i <= repeatIndex ? repeatItemBackgroundSelect
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
                                            repeatSelectType == RepeatSelectType.CONNECT ? i <= repeatIndex ? repeatItemTextColorSelect
                                                    : repeatItemTextColorNormal : i == repeatIndex ? repeatItemTextColorSelect : repeatItemTextColorNormal,
                                            repeatItemTextSize, Paint.Align.CENTER));
                        }
                    }
                }

                break;

            default:
                break;
        }
    }

    //
    public float getRepeatX() {
        return floatArray[repeatIndex];
    }

    @Override
    public void setXRaw(float x) {
        this.x = x;
        // calculate index
        float minDistence = width;
        // From 0 to judge one by one, if the distance farther on the end of the
        // cycle
        for (int i = 0; i < total; i++) {
            float centreDistance = Math.abs(x - floatArray[i]);
            if (centreDistance < minDistence) {
                repeatIndex = i;
                minDistence = centreDistance;
            } else {
                break;
            }
        }
//        if (needInvalidate) {
        invalidate();
//        }
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

    public String[] getRepeatItemTextValueArray() {
        return stringArray;
    }

    public void setRepeatItemTextValueArray(String[] repeatItemTextValueArray) {
        this.stringArray = repeatItemTextValueArray;
    }

    //
    public void setRepeatListener(final TouchListener touchListener, final TouchDownListener touchDownListener, final TouchUpListener touchUpListener) {
        this.setTouchListener(touchListener);
        this.setTouchDownListener(touchDownListener);
        this.setTouchUpListener(touchUpListener);
        this.setTouchCancelListener(new TouchCancelListener() {
            @Override
            public void touchCancel(TView t) {
                if (touchUpListener != null) {
                    touchUpListener.touchUp(TRepeat.this);
                }
            }
        });

        this.setTouchOutListener(new TouchOutListener() {
            @Override
            public void touchOut(TView t) {
                if (touchUpListener != null) {
                    touchUpListener.touchUp(TRepeat.this);
                }
            }
        });

        this.setTouchInListener(new TouchInListener() {
            @Override
            public void touchIn(TView t) {
                if (touchDownListener != null) {
                    touchDownListener.touchDown(TRepeat.this);
                }
            }
        });

    }
}
