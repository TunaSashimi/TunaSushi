package com.tunasushi.tuna;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.tuna.R;

import static com.tunasushi.tool.PaintTool.initTunaTextPaint;


/**
 * @author Tunasashimi
 * @date 10/30/15 16:52
 * @Copyright 2015 TunaSashimi. All rights reserved.
 * @Description
 */
public class TDialog extends TView {
    private int tunaDialogBackgroundNormal;
    private float tunaDialogRadius;

    private float tunaDialogStrokeWidth;
    private int tunaDialogStrokeColor;

    private String tunaDialogTitleTextValue;
    private float tunaDialogTitleTextSize;
    private int tunaDialogTitleTextColor;
    private float tunaDialogTitleTextDy;

    private String tunaDialogContentTextValue;
    private float tunaDialogContentTextSize;
    private int tunaDialogContentTextColor;
    private float tunaDialogContentTextPaddingLeft;
    private float tunaDialogContentTextPaddingRight;
    private float tunaDialogContentTextDy;

    private int tunaDialogChoiceBackgroundNormal;
    private int tunaDialogChoiceBackgroundPress;

    private float tunaDialogChoiceHeight;
    private float tunaDialogChoiceStrokeWidth;
    private int tunaDialogChoiceStrokeColor;


    private String[] tunaDialogChoiceTextValueArray;
    private float tunaDialogChoiceTextSize;
    private int tunaDialogChoiceTextColorNormal;
    private int tunaDialogChoiceTextColorPress;

    private int tunaDialogChoiceCurrentIndex;

    public int getTunaDialogChoiceCurrentIndex() {
        return tunaDialogChoiceCurrentIndex;
    }

    public void setTunaDialogChoiceCurrentIndex(int tunaDialogChoiceCurrentIndex) {
        this.tunaDialogChoiceCurrentIndex = tunaDialogChoiceCurrentIndex;
    }

    //
    private float radiusLeftBottom;
    private float radiusRightBottom;
    private int width;

    public TDialog(Context context) {
        this(context, null);
    }

    public TDialog(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TDialog(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        tunaTag = TDialog.class.getSimpleName();

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TDialog);

        tunaDialogBackgroundNormal = typedArray.getColor(R.styleable.TDialog_dialogBackgroundNormal, Color.TRANSPARENT);
        tunaDialogRadius = typedArray.getDimension(R.styleable.TDialog_dialogRadius, 0);

        tunaDialogStrokeWidth = typedArray.getDimension(R.styleable.TDialog_dialogStrokeWidth, 0);
        tunaDialogStrokeColor = typedArray.getColor(R.styleable.TDialog_dialogStrokeColor, Color.TRANSPARENT);

        tunaDialogTitleTextValue = typedArray.getString(R.styleable.TDialog_dialogTitleTextValue);
        tunaDialogTitleTextSize = typedArray.getDimension(R.styleable.TDialog_dialogTitleTextSize, 0);
        tunaDialogTitleTextColor = typedArray.getColor(R.styleable.TDialog_dialogTitleTextColor, Color.TRANSPARENT);
        tunaDialogTitleTextDy = typedArray.getDimension(R.styleable.TDialog_dialogTitleTextDy, 0);

        tunaDialogContentTextValue = typedArray.getString(R.styleable.TDialog_dialogContentTextValue);
        tunaDialogContentTextSize = typedArray.getDimension(R.styleable.TDialog_dialogContentTextSize, 0);
        tunaDialogContentTextColor = typedArray.getColor(R.styleable.TDialog_dialogContentTextColor, Color.TRANSPARENT);

        tunaDialogContentTextPaddingLeft = typedArray.getDimension(R.styleable.TDialog_dialogContentTextPaddingLeft, 0);
        tunaDialogContentTextPaddingRight = typedArray.getDimension(R.styleable.TDialog_dialogContentTextPaddingRight, 0);

        tunaDialogContentTextDy = typedArray.getDimension(R.styleable.TDialog_dialogContentTextDy, 0);

        tunaDialogChoiceBackgroundNormal = typedArray.getColor(R.styleable.TDialog_dialogChoiceBackgroundNormal, Color.TRANSPARENT);
        tunaDialogChoiceBackgroundPress = typedArray.getColor(R.styleable.TDialog_dialogChoiceBackgroundPress, tunaDialogChoiceBackgroundNormal);

        tunaDialogChoiceStrokeWidth = typedArray.getDimension(R.styleable.TDialog_dialogChoiceStrokeWidth, 0);
        tunaDialogChoiceStrokeColor = typedArray.getColor(R.styleable.TDialog_dialogChoiceStrokeColor, Color.TRANSPARENT);

        int tunaDialogChoiceTextValueArrayId = typedArray.getResourceId(R.styleable.TDialog_dialogChoiceTextValueArray, -1);

        if (tunaDialogChoiceTextValueArrayId != -1) {
            if (isInEditMode()) {
                tunaDialogChoiceTextValueArray = new String[]{"Confirm", "Cancel"};
            } else {
                tunaDialogChoiceTextValueArray = typedArray.getResources().getStringArray(tunaDialogChoiceTextValueArrayId);
            }
            tunaTotal = tunaDialogChoiceTextValueArray.length;
            tunaFloatArray = new float[tunaTotal];
        } else {
            throw new IllegalArgumentException("The content attribute require a property named tunaDragArray");
        }

        tunaDialogChoiceHeight = typedArray.getDimension(R.styleable.TDialog_dialogChoiceHeight, 0);
        if (tunaDialogChoiceHeight <= 0) {
            throw new IndexOutOfBoundsException("The content attribute tunaDialogChoiceHeight length must be greater than 0");
        }

        tunaDialogChoiceTextSize = typedArray.getDimension(R.styleable.TDialog_dialogChoiceTextSize, 0);
        tunaDialogChoiceTextColorNormal = typedArray.getColor(R.styleable.TDialog_dialogChoiceTextColorNormal, Color.TRANSPARENT);
        tunaDialogChoiceTextColorPress = typedArray.getColor(R.styleable.TDialog_dialogChoiceTextColorPress, tunaDialogChoiceTextColorNormal);

        tunaDialogChoiceCurrentIndex = typedArray.getInt(R.styleable.TDialog_dialogChoiceCurrentIndex, -1);

        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        typedArray.recycle();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        tunaShare = tunaWidth * 1f / tunaTotal;
        tunaDy = tunaHeight - tunaDialogChoiceHeight;

        //tunaRepeatCentreXArray avoid generating with new
        for (int i = 0; i < tunaTotal; i++) {
            tunaFloatArray[i] = tunaShare * 0.5f + tunaShare * i;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //
        drawTunaRectClassic(canvas,
                tunaWidth, tunaHeight,
                tunaDialogBackgroundNormal,
                tunaDialogStrokeWidth,
                tunaDialogStrokeColor,
                tunaDialogRadius);

//		//drawTunaDialogTitleText
        drawTunaText(
                canvas,
                tunaDialogTitleTextValue,
                tunaWidth,
                tunaWidth >> 1,
                tunaDialogTitleTextSize * 0.5f + tunaDialogTitleTextDy,
                0, 0,
                initTunaTextPaint(Paint.Style.FILL, tunaDialogTitleTextColor, tunaDialogTitleTextSize, Paint.Align.CENTER));

        //drawTunaDialogContentText
        drawTunaText(
                canvas,
                tunaDialogContentTextValue,
                tunaWidth,
                tunaWidth >> 1,
                (tunaHeight >> 1) + tunaDialogContentTextDy,
                tunaDialogContentTextPaddingLeft, tunaDialogContentTextPaddingRight,
                initTunaTextPaint(Paint.Style.FILL, tunaDialogContentTextColor, tunaDialogContentTextSize, Paint.Align.CENTER));

        //draw Options
        for (int i = 0; i < tunaTotal; i++) {
            if (i == 0) {
                radiusLeftBottom = tunaDialogRadius;
                tunaDx = tunaShare * i;
                if (i != (tunaTotal - 1)) {
                    width = (int) (tunaShare + tunaDialogChoiceStrokeWidth * 0.5f);
                    radiusRightBottom = 0;
                } else {
                    width = (int) (tunaShare + tunaDialogChoiceStrokeWidth);
                    radiusRightBottom = tunaDialogRadius;
                }
            } else {
                radiusLeftBottom = 0;
                tunaDx = tunaShare * i - tunaDialogChoiceStrokeWidth * 0.5f;
                if (i != (tunaTotal - 1)) {
                    radiusRightBottom = 0;
                    width = (int) (tunaShare + tunaDialogChoiceStrokeWidth);
                } else {
                    width = (int) (tunaShare + tunaDialogChoiceStrokeWidth * 0.5f);
                    radiusRightBottom = tunaDialogRadius;
                }
            }
            //
            canvas.translate(tunaDx, tunaDy);
            //
            drawTunaRectCustom(canvas, width, (int) tunaDialogChoiceHeight, i == tunaDialogChoiceCurrentIndex ? tunaDialogChoiceBackgroundPress : tunaDialogChoiceBackgroundNormal,
                    tunaDialogChoiceStrokeWidth, tunaDialogChoiceStrokeColor, 0, radiusLeftBottom, 0, radiusRightBottom);
            //
            drawTunaText(
                    canvas,
                    tunaDialogChoiceTextValueArray[i],
                    tunaWidth,
                    width * 0.5f,
                    tunaDialogChoiceHeight * 0.5f,
                    0,
                    0,
                    initTunaTextPaint(Paint.Style.FILL,
                            i == tunaDialogChoiceCurrentIndex ? tunaDialogChoiceTextColorPress : tunaDialogChoiceTextColorNormal, tunaDialogChoiceTextSize,
                            Paint.Align.CENTER));
            //
            canvas.translate(-tunaDx, -tunaDy);
        }

//		//Then draw a border line again
        drawTunaRectClassic(canvas,
                tunaWidth, tunaHeight,
                Color.TRANSPARENT,
                tunaDialogStrokeWidth,
                tunaDialogStrokeColor,
                tunaDialogRadius);
    }


    public void setTunaDialogCurrentXY(float tunaDialogCurrentX, float tunaDialogCurrentY) {
        //calculate index
        if (tunaDialogCurrentY >= tunaDy) {

            float minDistence = tunaWidth;

            //From 0 to judge one by one, if the distance farther on the end of the cycle
            for (int i = 0; i < tunaTotal; i++) {
                float centreDistance = Math.abs(tunaDialogCurrentX - tunaFloatArray[i]);

                if (centreDistance < minDistence) {
                    tunaDialogChoiceCurrentIndex = i;
                    minDistence = centreDistance;

                } else {
                    break;
                }
            }
        } else {
            tunaDialogChoiceCurrentIndex = -1;
        }
    }

    public String getTunaDialogCurrentChoiceTextValue() {
        if (tunaDialogChoiceCurrentIndex >= 0) {
            return tunaDialogChoiceTextValueArray[tunaDialogChoiceCurrentIndex];
        } else {
            return null;
        }
    }
}
