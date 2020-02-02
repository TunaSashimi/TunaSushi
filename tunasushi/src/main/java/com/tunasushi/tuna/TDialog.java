package com.tunasushi.tuna;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.tuna.R;
import com.tunasushi.tool.PaintTool;


/**
 * @author Tunasashimi
 * @date 10/30/15 16:52
 * @Copyright 2015 Sashimi. All rights reserved.
 * @Description
 */
public class TDialog extends TView {
    private int dialogBackgroundNormal;
    private float dialogRadius;

    private float dialogStrokeWidth;
    private int dialogStrokeColor;

    private String dialogTitleTextValue;
    private float dialogTitleTextSize;
    private int dialogTitleTextColor;
    private float dialogTitleTextDy;

    private String dialogContentTextValue;
    private float dialogContentTextSize;
    private int dialogContentTextColor;
    private float dialogContentTextPaddingLeft;
    private float dialogContentTextPaddingRight;
    private float dialogContentTextDy;

    private int dialogChoiceBackgroundNormal;
    private int dialogChoiceBackgroundPress;

    private float dialogChoiceHeight;
    private float dialogChoiceStrokeWidth;
    private int dialogChoiceStrokeColor;

    private String[] dialogChoiceTextValueArray;
    private float dialogChoiceTextSize;
    private int dialogChoiceTextColorNormal;
    private int dialogChoiceTextColorPress;

    private int dialogChoiceCurrentIndex;

    public int getDialogChoiceCurrentIndex() {
        return dialogChoiceCurrentIndex;
    }

    public void setDialogChoiceCurrentIndex(int dialogChoiceCurrentIndex) {
        this.dialogChoiceCurrentIndex = dialogChoiceCurrentIndex;
    }

    //
    private float dialogRadiusLeftBottom;
    private float dialogRadiusRightBottom;
    private int dialogWidth;

    public TDialog(Context context) {
        this(context, null);
    }

    public TDialog(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TDialog(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        Tag = TDialog.class.getSimpleName();

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TDialog);

        dialogBackgroundNormal = typedArray.getColor(R.styleable.TDialog_dialogBackgroundNormal, Color.TRANSPARENT);
        dialogRadius = typedArray.getDimension(R.styleable.TDialog_dialogRadius, 0);

        dialogStrokeWidth = typedArray.getDimension(R.styleable.TDialog_dialogStrokeWidth, 0);
        dialogStrokeColor = typedArray.getColor(R.styleable.TDialog_dialogStrokeColor, Color.TRANSPARENT);

        dialogTitleTextValue = typedArray.getString(R.styleable.TDialog_dialogTitleTextValue);
        dialogTitleTextSize = typedArray.getDimension(R.styleable.TDialog_dialogTitleTextSize, 0);
        dialogTitleTextColor = typedArray.getColor(R.styleable.TDialog_dialogTitleTextColor, Color.TRANSPARENT);
        dialogTitleTextDy = typedArray.getDimension(R.styleable.TDialog_dialogTitleTextDy, 0);

        dialogContentTextValue = typedArray.getString(R.styleable.TDialog_dialogContentTextValue);
        dialogContentTextSize = typedArray.getDimension(R.styleable.TDialog_dialogContentTextSize, 0);
        dialogContentTextColor = typedArray.getColor(R.styleable.TDialog_dialogContentTextColor, Color.TRANSPARENT);

        dialogContentTextPaddingLeft = typedArray.getDimension(R.styleable.TDialog_dialogContentTextPaddingLeft, 0);
        dialogContentTextPaddingRight = typedArray.getDimension(R.styleable.TDialog_dialogContentTextPaddingRight, 0);

        dialogContentTextDy = typedArray.getDimension(R.styleable.TDialog_dialogContentTextDy, 0);

        dialogChoiceBackgroundNormal = typedArray.getColor(R.styleable.TDialog_dialogChoiceBackgroundNormal, Color.TRANSPARENT);
        dialogChoiceBackgroundPress = typedArray.getColor(R.styleable.TDialog_dialogChoiceBackgroundPress, dialogChoiceBackgroundNormal);

        dialogChoiceStrokeWidth = typedArray.getDimension(R.styleable.TDialog_dialogChoiceStrokeWidth, 0);
        dialogChoiceStrokeColor = typedArray.getColor(R.styleable.TDialog_dialogChoiceStrokeColor, Color.TRANSPARENT);

        int dialogChoiceTextValueArrayId = typedArray.getResourceId(R.styleable.TDialog_dialogChoiceTextValueArray, -1);

        if (dialogChoiceTextValueArrayId != -1) {
            if (isInEditMode()) {
                dialogChoiceTextValueArray = new String[]{"Confirm", "Cancel"};
            } else {
                dialogChoiceTextValueArray = typedArray.getResources().getStringArray(dialogChoiceTextValueArrayId);
            }
            total = dialogChoiceTextValueArray.length;
            floatArray = new float[total];
        } else {
            throw new IllegalArgumentException("The content attribute require a property named dialogChoiceTextValueArray");
        }

        dialogChoiceHeight = typedArray.getDimension(R.styleable.TDialog_dialogChoiceHeight, 0);
        if (dialogChoiceHeight <= 0) {
            throw new IndexOutOfBoundsException("The content attribute dialogChoiceHeight length must be greater than 0");
        }

        dialogChoiceTextSize = typedArray.getDimension(R.styleable.TDialog_dialogChoiceTextSize, 0);
        dialogChoiceTextColorNormal = typedArray.getColor(R.styleable.TDialog_dialogChoiceTextColorNormal, Color.TRANSPARENT);
        dialogChoiceTextColorPress = typedArray.getColor(R.styleable.TDialog_dialogChoiceTextColorPress, dialogChoiceTextColorNormal);

        dialogChoiceCurrentIndex = typedArray.getInt(R.styleable.TDialog_dialogChoiceCurrentIndex, -1);

        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        typedArray.recycle();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        share = width * 1f / total;
        dy = height - dialogChoiceHeight;

        //tunaRepeatCentreXArray avoid generating with new
        for (int i = 0; i < total; i++) {
            floatArray[i] = share * 0.5f + share * i;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //
        drawRectClassic(canvas,
                width, height,
                dialogBackgroundNormal,
                dialogStrokeWidth,
                dialogStrokeColor,
                dialogRadius);

//		//drawDialogTitleText
        drawText(
                canvas,
                dialogTitleTextValue,
                width,
                width >> 1,
                dialogTitleTextSize * 0.5f + dialogTitleTextDy,
                0, 0,
                PaintTool.initTextPaint(Paint.Style.FILL, dialogTitleTextColor, dialogTitleTextSize, Paint.Align.CENTER));

        //drawDialogContentText
        drawText(
                canvas,
                dialogContentTextValue,
                width,
                width >> 1,
                (height >> 1) + dialogContentTextDy,
                dialogContentTextPaddingLeft, dialogContentTextPaddingRight,
                PaintTool.initTextPaint(Paint.Style.FILL, dialogContentTextColor, dialogContentTextSize, Paint.Align.CENTER));

        //draw Options
        for (int i = 0; i < total; i++) {
            if (i == 0) {
                dialogRadiusLeftBottom = dialogRadius;
                dx = share * i;
                if (i != (total - 1)) {
                    dialogWidth = (int) (share + dialogChoiceStrokeWidth * 0.5f);
                    dialogRadiusRightBottom = 0;
                } else {
                    dialogWidth = (int) (share + dialogChoiceStrokeWidth);
                    dialogRadiusRightBottom = dialogRadius;
                }
            } else {
                dialogRadiusLeftBottom = 0;
                dx = share * i - dialogChoiceStrokeWidth * 0.5f;
                if (i != (total - 1)) {
                    dialogRadiusRightBottom = 0;
                    dialogWidth = (int) (share + dialogChoiceStrokeWidth);
                } else {
                    dialogWidth = (int) (share + dialogChoiceStrokeWidth * 0.5f);
                    dialogRadiusRightBottom = dialogRadius;
                }
            }
            //
            canvas.translate(dx, dy);
            //
            drawRectCustom(canvas, dialogWidth, (int) dialogChoiceHeight, i == dialogChoiceCurrentIndex ? dialogChoiceBackgroundPress : dialogChoiceBackgroundNormal,
                    dialogChoiceStrokeWidth, dialogChoiceStrokeColor, 0, dialogRadiusLeftBottom, 0, dialogRadiusRightBottom);
            //
            drawText(
                    canvas,
                    dialogChoiceTextValueArray[i],
                    width,
                    dialogWidth * 0.5f,
                    dialogChoiceHeight * 0.5f,
                    0,
                    0,
                    PaintTool.initTextPaint(Paint.Style.FILL,
                            i == dialogChoiceCurrentIndex ? dialogChoiceTextColorPress : dialogChoiceTextColorNormal, dialogChoiceTextSize,
                            Paint.Align.CENTER));
            //
            canvas.translate(-dx, -dy);
        }

//		//Then draw a border line again
        drawRectClassic(canvas,
                width, height,
                Color.TRANSPARENT,
                dialogStrokeWidth,
                dialogStrokeColor,
                dialogRadius);
    }


    public void setDialogCurrentXY(float dialogCurrentX, float dialogCurrentY) {
        //calculate index
        if (dialogCurrentY >= dy) {

            float minDistence = width;

            //From 0 to judge one by one, if the distance farther on the end of the cycle
            for (int i = 0; i < total; i++) {
                float centreDistance = Math.abs(dialogCurrentX - floatArray[i]);

                if (centreDistance < minDistence) {
                    dialogChoiceCurrentIndex = i;
                    minDistence = centreDistance;

                } else {
                    break;
                }
            }
        } else {
            dialogChoiceCurrentIndex = -1;
        }
    }

    public String getDialogCurrentChoiceTextValue() {
        if (dialogChoiceCurrentIndex >= 0) {
            return dialogChoiceTextValueArray[dialogChoiceCurrentIndex];
        } else {
            return null;
        }
    }
}
