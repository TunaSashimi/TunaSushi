package com.tunasushi.tuna;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.tuna.R;

import static com.tunasushi.tool.DeviceTool.applyDimension;
import static com.tunasushi.tool.PaintTool.initTunaPaint;
import static com.tunasushi.tool.PaintTool.initTunaTextPaint;


/**
 * @author Tunasashimi
 * @date 10/30/15 16:57
 * @Copyright 2015 TunaSashimi. All rights reserved.
 * @Description
 */
public class TRepeat extends TView {

    private float tunaRepeatItemFractionTop, tunaRepeatItemFractionBottom;
    private float tunaRepeatItemTextFractionTop, tunaRepeatItemTextFractionBottom;

    private Bitmap tunaRepeatBitmapSrcNormal, tunaRepeatBitmapSrcSelect;

    private float tunaRepeatCurrentX;
    private int tunaRepeatCurrentIndex;

    private float tunaRepeatItemTextSize;
    private int tunaRepeatItemTextColorNormal, tunaRepeatItemTextColorSelect;

    private int tunaRepeatItemBackgroundNormal, tunaRepeatItemBackgroundSelect;


    private TunaRepeatSelectType tunaRepeatSelectType;

    public enum TunaRepeatSelectType {
        CONNECT(0), CURRENT(1),;
        final int nativeInt;

        TunaRepeatSelectType(int ni) {
            nativeInt = ni;
        }
    }

    private static final TunaRepeatSelectType[] tunaRepeatSelectTypeArray = {TunaRepeatSelectType.CONNECT, TunaRepeatSelectType.CURRENT,};

    //
    private TunaRepeatShapeType tunaRepeatShapeType;

    public enum TunaRepeatShapeType {
        CUSTOM(0), CIRCLE(1);
        final int nativeInt;

        TunaRepeatShapeType(int ni) {
            nativeInt = ni;
        }
    }

    private static final TunaRepeatShapeType[] tunaRepeatShapeTypeArray = {TunaRepeatShapeType.CUSTOM, TunaRepeatShapeType.CIRCLE};

    public TRepeat(Context context) {
        this(context, null);
    }

    public TRepeat(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TRepeat(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        tunaTag = TRepeat.class.getSimpleName();

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TRepeat);

        tunaTotal = typedArray.getInt(R.styleable.TRepeat_repeatTotal, 0);

        int tunaRepeatItemTextValueArrayId = typedArray.getResourceId(R.styleable.TRepeat_repeatItemTextValueArray, -1);
        if (tunaRepeatItemTextValueArrayId != -1) {
            tunaStringArray = typedArray.getResources().getStringArray(tunaRepeatItemTextValueArrayId);
            if (tunaTotal != tunaStringArray.length) {
                throw new IndexOutOfBoundsException("These two properties of tunaTotal and tunaRepeatItemTextValueArray must be the same length");
            }
        }

        tunaRepeatItemTextSize = typedArray.getDimension(R.styleable.TRepeat_repeatItemTextSize, 0);
        tunaRepeatItemTextColorNormal = typedArray.getColor(R.styleable.TRepeat_repeatItemTextColorNormal, Color.TRANSPARENT);
        tunaRepeatItemTextColorSelect = typedArray.getColor(R.styleable.TRepeat_repeatItemTextColorSelect, tunaRepeatItemTextColorNormal);

        tunaRepeatItemTextFractionTop = typedArray.getFraction(R.styleable.TRepeat_repeatItemTextFractionTop, 1, 1, 0);
        tunaRepeatItemTextFractionBottom = typedArray.getFraction(R.styleable.TRepeat_repeatItemTextFractionBottom, 1, 1, 1);

        if (tunaRepeatItemTextFractionBottom <= tunaRepeatItemTextFractionTop) {
            throw new IndexOutOfBoundsException("The content attribute tunaRepeatItemTextFractionBottom must be Equal to or greater than tunaRepeatItemTextFractionTop");
        }

        tunaRepeatItemFractionTop = typedArray.getFraction(R.styleable.TRepeat_repeatItemFractionTop, 1, 1, 0);
        tunaRepeatItemFractionBottom = typedArray.getFraction(R.styleable.TRepeat_repeatItemFractionBottom, 1, 1, 1);

        if (tunaRepeatItemFractionBottom <= tunaRepeatItemFractionTop) {
            throw new IndexOutOfBoundsException("The content attribute tunaRepeatItemFractionBottom must be greater than tunaRepeatItemFractionTop");
        }


        int tunaRepeatShapeTypeIndex = typedArray.getInt(R.styleable.TRepeat_repeatShapeType, -1);
        if (tunaRepeatShapeTypeIndex >= 0) {
            tunaRepeatShapeType = tunaRepeatShapeTypeArray[tunaRepeatShapeTypeIndex];
        } else {
            throw new IllegalArgumentException("The content attribute require a property named tunaRepeatShapeType");
        }

        if (tunaRepeatShapeType == TunaRepeatShapeType.CUSTOM) {
            int tunaRepeatBitmapSrcNormalId = typedArray.getResourceId(R.styleable.TRepeat_repeatBitmapSrcNormal, -1);
            if (tunaRepeatBitmapSrcNormalId != -1) {
                tunaRepeatBitmapSrcNormal = BitmapFactory.decodeResource(getResources(), tunaRepeatBitmapSrcNormalId);
            }
            int tunaRepeatBitmapSrcSelectId = typedArray.getResourceId(R.styleable.TRepeat_repeatBitmapSrcSelect, -1);
            if (tunaRepeatBitmapSrcSelectId != -1) {
                tunaRepeatBitmapSrcSelect = BitmapFactory.decodeResource(getResources(), tunaRepeatBitmapSrcSelectId);
            }
        } else if (tunaRepeatShapeType == TunaRepeatShapeType.CIRCLE) {
            tunaRepeatItemBackgroundNormal = typedArray.getColor(R.styleable.TRepeat_repeatItemBackgroundNormal, Color.TRANSPARENT);
            tunaRepeatItemBackgroundSelect = typedArray.getColor(R.styleable.TRepeat_repeatItemBackgroundSelect, tunaRepeatItemBackgroundNormal);
        }


        int tunaRepeatSelectTypeIndex = typedArray.getInt(R.styleable.TRepeat_repeatSelectType, -1);
        if (tunaRepeatSelectTypeIndex >= 0) {
            tunaRepeatSelectType = tunaRepeatSelectTypeArray[tunaRepeatSelectTypeIndex];
        } else {
            throw new IllegalArgumentException("The content attribute require a property named tunaRepeatSelectType");
        }

        tunaRepeatCurrentIndex = typedArray.getInt(R.styleable.TRepeat_repeatCurrentIndex, -1);
        typedArray.recycle();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (tunaTotal < 1) {
            // Preview defaults
            if (isInEditMode()) {
                tunaTotal = 5;
                tunaFloatArray = new float[tunaTotal];
            } else {
                throw new IndexOutOfBoundsException("The content attribute tunaRepeatTotal must be greater than or equal 1");
            }
        } else {
            tunaFloatArray = new float[tunaTotal];
        }

        if (tunaRepeatCurrentIndex < -1 || tunaRepeatCurrentIndex > tunaTotal - 1) {
            throw new IndexOutOfBoundsException("The content attribute tunaRepeatCurrentIndex length must be not less than -1 and smaller than the tunaTotal length minus 1");
        }


        if (tunaRepeatShapeType == TunaRepeatShapeType.CUSTOM) {
            int tunaRepeatCustomBitmapSrcNormalWidth = tunaRepeatBitmapSrcNormal.getWidth();
            int tunaRepeatCustomBitmapSrcNormalHeight = tunaRepeatBitmapSrcNormal.getHeight();
            int tunaRepeatCustomBitmapSrcSelectWidth = tunaRepeatBitmapSrcSelect.getWidth();
            int tunaRepeatCustomBitmapSrcSelectHeight = tunaRepeatBitmapSrcSelect.getHeight();
            if (tunaRepeatCustomBitmapSrcNormalWidth != tunaRepeatCustomBitmapSrcSelectWidth || tunaRepeatCustomBitmapSrcNormalHeight != tunaRepeatCustomBitmapSrcSelectHeight) {
                throw new IndexOutOfBoundsException("Both the width and height of the attribute tunaRepeatCustomBitmapSrcNormal and tunaRepeatCustomBitmapSrcSelect needed equal");
            }

            tunaSrcHeightScale = tunaHeight * (tunaRepeatItemFractionBottom - tunaRepeatItemFractionTop);
            tunaSrcWidthScale = tunaSrcHeightScale * tunaRepeatCustomBitmapSrcNormalWidth / tunaRepeatCustomBitmapSrcNormalHeight;
            tunaScale = tunaSrcHeightScale / tunaRepeatCustomBitmapSrcNormalWidth;

            initTunaMatrix(tunaScale, tunaScale);

        } else if (tunaRepeatShapeType == TunaRepeatShapeType.CIRCLE) {
            tunaSrcHeightScale = tunaHeight * (tunaRepeatItemFractionBottom - tunaRepeatItemFractionTop);
            tunaSrcWidthScale = tunaSrcHeightScale;

        }

        tunaDy = tunaHeight * tunaRepeatItemFractionTop;
        tunaSurplus = tunaWidth - tunaSrcWidthScale * tunaTotal;
        tunaShare = tunaSurplus / (tunaTotal + 1);
        // tunaRepeatCentreXArray avoid generating with new
        for (int i = 0; i < tunaTotal; i++) {
            tunaFloatArray[i] = tunaShare + tunaSrcWidthScale * 0.5f + (tunaShare + tunaSrcWidthScale) * i;
        }
        if (tunaSubLayoutListener != null) {
            tunaSubLayoutListener.tunaSubLayout(this);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //
        switch (tunaRepeatShapeType) {
            case CUSTOM:

                // in order to draw a map of the effect of the half, the first painting baseBitmap
                for (int i = 0; i < tunaTotal; i++) {
                    if (i == 0) {
                        canvas.translate(tunaShare, tunaDy);
                    } else {
                        canvas.translate(tunaShare + tunaSrcWidthScale, 0);
                    }
                    canvas.drawBitmap(tunaRepeatBitmapSrcNormal, tunaMatrix, null);
                }
                canvas.translate((tunaShare + tunaSrcWidthScale) * (1 - tunaTotal) - tunaShare, -tunaDy);

                //
                if (tunaStringArray != null) {
                    for (int i = 0; i < tunaStringArray.length; i++) {
                        drawTunaText(canvas, tunaStringArray[i], tunaWidth, tunaFloatArray[i], (tunaHeight * tunaRepeatItemTextFractionTop + tunaHeight
                                        * tunaRepeatItemTextFractionBottom) * 0.5f, 0, 0,
                                initTunaTextPaint(Paint.Style.FILL, tunaRepeatItemTextColorNormal, tunaRepeatItemTextSize, Paint.Align.CENTER));
                    }
                }

                // in order to draw a map of the effect of the half, drawing overlay
                if (tunaPress) {
                    canvas.save();
                    switch (tunaRepeatSelectType) {
                        case CONNECT:
                            canvas.clipRect(0, 0, tunaRepeatCurrentX, tunaHeight);
                            break;
                        case CURRENT:
                            canvas.clipRect(tunaRepeatCurrentX - tunaSrcWidthScale, 0, tunaRepeatCurrentX, tunaHeight);
                            break;
                        default:
                            break;
                    }
                    for (int i = 0; i < tunaTotal; i++) {
                        if (i == 0) {
                            canvas.translate(tunaShare, tunaDy);
                        } else {
                            canvas.translate(tunaShare + tunaSrcWidthScale, 0);
                        }
                        canvas.drawBitmap(tunaRepeatBitmapSrcSelect, tunaMatrix, null);
                    }
                    canvas.translate((tunaShare + tunaSrcWidthScale) * (1 - tunaTotal) - tunaShare, -tunaDy);
                    if (tunaStringArray != null) {
                        for (int i = 0; i < tunaStringArray.length; i++) {
                            drawTunaText(canvas, tunaStringArray[i], tunaWidth, tunaFloatArray[i], (tunaHeight * tunaRepeatItemTextFractionTop + tunaHeight
                                            * tunaRepeatItemTextFractionBottom) * 0.5f, 0, 0,
                                    initTunaTextPaint(Paint.Style.FILL, tunaRepeatItemTextColorSelect, tunaRepeatItemTextSize, Paint.Align.CENTER));
                        }
                    }
                    canvas.restore();
                } else {
                    for (int i = 0; i < tunaTotal; i++) {
                        if (i == 0) {
                            canvas.translate(tunaShare, tunaDy);
                        } else {
                            canvas.translate(tunaShare + tunaSrcWidthScale, 0);
                        }
                        canvas.drawBitmap(tunaRepeatSelectType == TunaRepeatSelectType.CONNECT ? i <= tunaRepeatCurrentIndex ? tunaRepeatBitmapSrcSelect
                                : tunaRepeatBitmapSrcNormal : i == tunaRepeatCurrentIndex ? tunaRepeatBitmapSrcSelect : tunaRepeatBitmapSrcNormal, tunaMatrix, null);
                    }
                    canvas.translate((tunaShare + tunaSrcWidthScale) * (1 - tunaTotal) - tunaShare, -tunaDy);
                    if (tunaStringArray != null) {
                        for (int i = 0; i < tunaStringArray.length; i++) {
                            drawTunaText(
                                    canvas,
                                    tunaStringArray[i],
                                    tunaWidth,
                                    tunaFloatArray[i],
                                    (tunaHeight * tunaRepeatItemTextFractionTop + tunaHeight * tunaRepeatItemTextFractionBottom) * 0.5f,
                                    0,
                                    0,
                                    initTunaTextPaint(Paint.Style.FILL,
                                            tunaRepeatSelectType == TunaRepeatSelectType.CONNECT ? i <= tunaRepeatCurrentIndex ? tunaRepeatItemTextColorSelect
                                                    : tunaRepeatItemTextColorNormal : i == tunaRepeatCurrentIndex ? tunaRepeatItemTextColorSelect : tunaRepeatItemTextColorNormal,
                                            tunaRepeatItemTextSize, Paint.Align.CENTER));
                        }
                    }
                }
                break;

            case CIRCLE:

                // in order to draw a map of the effect of the half, the first painting baseBitmap
                for (int i = 0; i < tunaTotal; i++) {
                    if (i == 0) {
                        canvas.translate(tunaShare, tunaDy);
                    } else {
                        canvas.translate(tunaShare + tunaSrcWidthScale, 0);
                    }
                    canvas.drawCircle(tunaSrcWidthScale / 2, tunaSrcWidthScale / 2, tunaSrcWidthScale / 2, initTunaPaint(tunaRepeatItemBackgroundNormal));
                }
                canvas.translate((tunaShare + tunaSrcWidthScale) * (1 - tunaTotal) - tunaShare, -tunaDy);

                //
                if (tunaStringArray != null) {
                    for (int i = 0; i < tunaStringArray.length; i++) {
                        drawTunaText(canvas, tunaStringArray[i], tunaWidth, tunaFloatArray[i], (tunaHeight * tunaRepeatItemTextFractionTop + tunaHeight
                                        * tunaRepeatItemTextFractionBottom) * 0.5f, 0, 0,
                                initTunaTextPaint(Paint.Style.FILL, tunaRepeatItemTextColorNormal, tunaRepeatItemTextSize, Paint.Align.CENTER));
                    }
                }

                // in order to draw a map of the effect of the half, drawing overlay
                if (tunaPress) {
                    canvas.save();
                    switch (tunaRepeatSelectType) {
                        case CONNECT:
                            canvas.clipRect(0, 0, tunaRepeatCurrentX, tunaHeight);
                            break;
                        case CURRENT:
                            canvas.clipRect(tunaRepeatCurrentX - tunaSrcWidthScale, 0, tunaRepeatCurrentX, tunaHeight);
                            break;
                        default:
                            break;
                    }
                    for (int i = 0; i < tunaTotal; i++) {
                        if (i == 0) {
                            canvas.translate(tunaShare, tunaDy);
                        } else {
                            canvas.translate(tunaShare + tunaSrcWidthScale, 0);
                        }
//                        canvas.drawBitmap(tunaRepeatBitmapSrcSelect, tunaMatrix, null);
                        canvas.drawCircle(tunaSrcWidthScale / 2, tunaSrcWidthScale / 2, tunaSrcWidthScale / 2, initTunaPaint(tunaRepeatItemBackgroundSelect));
                    }

                    canvas.translate((tunaShare + tunaSrcWidthScale) * (1 - tunaTotal) - tunaShare, -tunaDy);
                    if (tunaStringArray != null) {
                        for (int i = 0; i < tunaStringArray.length; i++) {
                            drawTunaText(canvas, tunaStringArray[i], tunaWidth, tunaFloatArray[i], (tunaHeight * tunaRepeatItemTextFractionTop + tunaHeight
                                            * tunaRepeatItemTextFractionBottom) * 0.5f, 0, 0,
                                    initTunaTextPaint(Paint.Style.FILL, tunaRepeatItemTextColorSelect, tunaRepeatItemTextSize, Paint.Align.CENTER));
                        }
                    }
                    canvas.restore();
                } else {
                    for (int i = 0; i < tunaTotal; i++) {
                        if (i == 0) {
                            canvas.translate(tunaShare, tunaDy);
                        } else {
                            canvas.translate(tunaShare + tunaSrcWidthScale, 0);
                        }
//                        canvas.drawBitmap(tunaRepeatSelectType == TunaRepeatSelectType.CONNECT ? i <= tunaRepeatCurrentIndex ? tunaRepeatBitmapSrcSelect
//                                : tunaRepeatBitmapSrcNormal : i == tunaRepeatCurrentIndex ? tunaRepeatBitmapSrcSelect : tunaRepeatBitmapSrcNormal, tunaMatrix, null);
                        canvas.drawCircle(tunaSrcWidthScale / 2, tunaSrcWidthScale / 2, tunaSrcWidthScale / 2, initTunaPaint(tunaRepeatSelectType == TunaRepeatSelectType.CONNECT ? i <= tunaRepeatCurrentIndex ? tunaRepeatItemBackgroundSelect
                                : tunaRepeatItemBackgroundNormal : i == tunaRepeatCurrentIndex ? tunaRepeatItemBackgroundSelect : tunaRepeatItemBackgroundNormal));
                    }
                    canvas.translate((tunaShare + tunaSrcWidthScale) * (1 - tunaTotal) - tunaShare, -tunaDy);
                    if (tunaStringArray != null) {
                        for (int i = 0; i < tunaStringArray.length; i++) {
                            drawTunaText(
                                    canvas,
                                    tunaStringArray[i],
                                    tunaWidth,
                                    tunaFloatArray[i],
                                    (tunaHeight * tunaRepeatItemTextFractionTop + tunaHeight * tunaRepeatItemTextFractionBottom) * 0.5f,
                                    0,
                                    0,
                                    initTunaTextPaint(Paint.Style.FILL,
                                            tunaRepeatSelectType == TunaRepeatSelectType.CONNECT ? i <= tunaRepeatCurrentIndex ? tunaRepeatItemTextColorSelect
                                                    : tunaRepeatItemTextColorNormal : i == tunaRepeatCurrentIndex ? tunaRepeatItemTextColorSelect : tunaRepeatItemTextColorNormal,
                                            tunaRepeatItemTextSize, Paint.Align.CENTER));
                        }
                    }
                }

                break;

            default:
                break;
        }
    }

    //
    public float getTunaRepeatCurrentX() {
        return tunaFloatArray[tunaRepeatCurrentIndex];
    }

    public void setTunaRepeatCurrentX(float tunaRepeatCurrentX) {
        setTunaRepeatCurrentX(tunaRepeatCurrentX, false);
    }

    public void setTunaRepeatCurrentX(int unit, float tunaRepeatCurrentX) {
        setTunaRepeatCurrentX(unit, tunaRepeatCurrentX, false);
    }

    public void setTunaRepeatCurrentX(float tunaRepeatCurrentX, boolean needInvalidate) {
        setTunaRepeatCurrentX(TypedValue.COMPLEX_UNIT_DIP, tunaRepeatCurrentX, needInvalidate);
    }

    public void setTunaRepeatCurrentX(int unit, float tunaRepeatCurrentX, boolean needInvalidate) {
        Context c = getContext();
        Resources r;
        if (c == null)
            r = Resources.getSystem();
        else
            r = c.getResources();
        setTunaRepeatCurrentXRaw(applyDimension(unit, tunaRepeatCurrentX, r.getDisplayMetrics()), needInvalidate);
    }

    private void setTunaRepeatCurrentXRaw(float tunaRepeatCurrentX, boolean needInvalidate) {
        this.tunaRepeatCurrentX = tunaRepeatCurrentX;
        // calculate index
        float minDistence = tunaWidth;
        // From 0 to judge one by one, if the distance farther on the end of the
        // cycle
        for (int i = 0; i < tunaTotal; i++) {
            float centreDistance = Math.abs(tunaRepeatCurrentX - tunaFloatArray[i]);
            if (centreDistance < minDistence) {
                tunaRepeatCurrentIndex = i;
                minDistence = centreDistance;
            } else {
                break;
            }
        }
        if (needInvalidate) {
            invalidate();
        }
    }

    public int getTunaRepeatCurrentIndex() {
        return tunaRepeatCurrentIndex;
    }

    public void setTunaRepeatCurrentIndex(int tunaRepeatCurrentIndex) {
        if (tunaRepeatCurrentIndex < -1 || tunaRepeatCurrentIndex > tunaTotal - 1) {
            throw new IndexOutOfBoundsException("The content attribute tunaRepeatCurrentIndex length must be not less than -1 and smaller than the tunaTotal length -1");
        }
        this.tunaRepeatCurrentIndex = tunaRepeatCurrentIndex;
        invalidate();
    }

    public float[] getTunaRepeatCentreXArray() {
        return tunaFloatArray;
    }

    public void setTunaRepeatCentreXArray(float[] tunaRepeatCentreXArray) {
        this.tunaFloatArray = tunaRepeatCentreXArray;
    }

    public int getTunaRepeatTotal() {
        return tunaTotal;
    }

    public void setTunaRepeatTotal(int tunaTotal) {
        this.tunaTotal = tunaTotal;
        invalidate();
    }

    public String[] getTunaRepeatItemTextValueArray() {
        return tunaStringArray;
    }

    public void setTunaRepeatItemTextValueArray(String[] tunaRepeatItemTextValueArray) {
        this.tunaStringArray = tunaRepeatItemTextValueArray;
    }

    //
    public void setTunaRepeatListener(final TunaTouchListener tunaTouchListener, final TunaTouchDownListener tunaTouchDownListener, final TunaTouchUpListener tunaTouchUpListener) {
        this.setTunaTouchListener(tunaTouchListener);
        this.setTunaTouchDownListener(tunaTouchDownListener);
        this.setTunaTouchUpListener(tunaTouchUpListener);
        this.setTunaTouchCancelListener(new TunaTouchCancelListener() {
            @Override
            public void tunaTouchCancel(View v) {
                if (tunaTouchUpListener != null) {
                    tunaTouchUpListener.tunaTouchUp(TRepeat.this);
                }
            }
        });
        this.setTunaTouchOutListener(new TunaTouchOutListener() {
            @Override
            public void tunaTouchOut(View v) {
                if (tunaTouchUpListener != null) {
                    tunaTouchUpListener.tunaTouchUp(TRepeat.this);
                }
            }
        });
        this.setTunaTouchInListener(new TunaTouchInListener() {
            @Override
            public void tunaTouchIn(View v) {
                if (tunaTouchDownListener != null) {
                    tunaTouchDownListener.tunaTouchDown(TRepeat.this);
                }
            }
        });
    }
}
