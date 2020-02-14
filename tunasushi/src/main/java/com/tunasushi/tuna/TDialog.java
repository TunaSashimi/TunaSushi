package com.tunasushi.tuna;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.tuna.R;

/**
 * @author TunaSashimi
 * @date 2015-10-30 16:52
 * @Copyright 2015 TunaSashimi. All rights reserved.
 * @Description
 */
public class TDialog extends TView {
    private float dialogWidth;
    private float dialogHeight;

    private int dialogBackground;
    private int dialogSurround;

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

    public String[] getDialogChoiceTextValueArray() {
        return dialogChoiceTextValueArray;
    }

    public void setDialogChoiceTextValueArray(String[] dialogChoiceTextValueArray) {
        this.dialogChoiceTextValueArray = dialogChoiceTextValueArray;
    }

    private float dialogChoiceTextSize;
    private int dialogChoiceTextColorNormal;
    private int dialogChoiceTextColorPress;

    private int dialogChoiceIndex = -1;

    public int getDialogChoiceIndex() {
        return dialogChoiceIndex;
    }

    public void setDialogChoiceIndex(int dialogChoiceIndex) {
        this.dialogChoiceIndex = dialogChoiceIndex;
    }

    //
    private float dialogRadiusLeftBottom;
    private float dialogRadiusRightBottom;
    private int dialogChoiceWidth;

    private float dialogDx;
    private float dialogDy;

    public TDialog(Context context) {
        this(context, null);
    }

    public TDialog(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TDialog(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        tag = TDialog.class.getSimpleName();

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TDialog);

        dialogBackground = typedArray.getColor(R.styleable.TDialog_dialogBackground, Color.TRANSPARENT);
        dialogSurround = typedArray.getColor(R.styleable.TDialog_dialogSurround, Color.TRANSPARENT);

        dialogWidth = typedArray.getDimension(R.styleable.TDialog_dialogWidth, 0);
        if (dialogWidth <= 0) {
            throw new IllegalArgumentException("The content attribute dialogWidth length must be greater than 0");
        }

        dialogHeight = typedArray.getDimension(R.styleable.TDialog_dialogHeight, 0);
        if (dialogHeight <= 0) {
            throw new IllegalArgumentException("The content attribute dialogHeight length must be greater than 0");
        }

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
            dialogChoiceTextValueArray = typedArray.getResources().getStringArray(dialogChoiceTextValueArrayId);
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

        dialogChoiceIndex = typedArray.getInt(R.styleable.TDialog_dialogChoiceIndex, -1);

        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        typedArray.recycle();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        //
        dx = (width - dialogWidth) / 2;
        dy = (height - dialogHeight) / 2;

        //
        share = dialogWidth * 1f / total;
        dialogDy = dialogHeight - dialogChoiceHeight;

        //floatArray means dialogChoiceArrayCenterX
        for (int i = 0; i < total; i++) {
            floatArray[i] = share * i + share * 0.5f + dx;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(dialogSurround);
        canvas.translate(dx, dy);

        //
        drawRectClassic(canvas,
                dialogWidth, dialogHeight,
                dialogBackground,
                dialogStrokeWidth,
                dialogStrokeColor,
                radius);

//		//drawDialogTitleText
        drawText(
                canvas,
                dialogTitleTextValue,
                dialogWidth,
                dialogWidth / 2,
                dialogTitleTextSize * 0.5f + dialogTitleTextDy,
                0, 0,
                initTextPaint(Paint.Style.FILL, dialogTitleTextColor, dialogTitleTextSize, Paint.Align.CENTER));


        //drawDialogContentText
        drawText(
                canvas,
                dialogContentTextValue,
                dialogWidth,
                dialogWidth / 2,
                dialogHeight / 2 + dialogContentTextDy,
                dialogContentTextPaddingLeft, dialogContentTextPaddingRight,
                initTextPaint(Paint.Style.FILL, dialogContentTextColor, dialogContentTextSize, Paint.Align.CENTER));

        //draw Options
        for (int i = 0; i < total; i++) {
            if (i == 0) {
                dialogRadiusLeftBottom = radius;
                dialogDx = share * i;
                if (i != (total - 1)) {
                    dialogChoiceWidth = (int) (share + dialogChoiceStrokeWidth * 0.5f);
                    dialogRadiusRightBottom = 0;
                } else {
                    dialogChoiceWidth = (int) (share + dialogChoiceStrokeWidth);
                    dialogRadiusRightBottom = radius;
                }
            } else {
                dialogRadiusLeftBottom = 0;
                dialogDx = share * i - dialogChoiceStrokeWidth * 0.5f;
                if (i != (total - 1)) {
                    dialogRadiusRightBottom = 0;
                    dialogChoiceWidth = (int) (share + dialogChoiceStrokeWidth);
                } else {
                    dialogChoiceWidth = (int) (share + dialogChoiceStrokeWidth * 0.5f);
                    dialogRadiusRightBottom = radius;
                }
            }
            //
            canvas.translate(dialogDx, dialogDy);
            //
            drawRectCustom(canvas, dialogChoiceWidth, (int) dialogChoiceHeight, i == dialogChoiceIndex ? isPress() ? dialogChoiceBackgroundPress : dialogChoiceBackgroundNormal : dialogChoiceBackgroundNormal,
                    dialogChoiceStrokeWidth, dialogChoiceStrokeColor, 0, dialogRadiusLeftBottom, 0, dialogRadiusRightBottom);
            //
            drawText(
                    canvas,
                    dialogChoiceTextValueArray[i],
                    dialogWidth,
                    dialogChoiceWidth * 0.5f,
                    dialogChoiceHeight * 0.5f,
                    0,
                    0,
                    initTextPaint(Paint.Style.FILL,
                            i == dialogChoiceIndex ? dialogChoiceTextColorPress : dialogChoiceTextColorNormal, dialogChoiceTextSize,
                            Paint.Align.CENTER));
            //
            canvas.translate(-dialogDx, -dialogDy);
        }

//		//Then draw a border line again
        drawRectClassic(canvas,
                dialogWidth, dialogHeight,
                Color.TRANSPARENT,
                dialogStrokeWidth,
                dialogStrokeColor,
                radius);

        canvas.translate(-dx, -dy);
    }


    public void setDialogXY(float TouchEventX, float TouchEventY) {
        //calculate index
        if (TouchEventY >= dialogDy + dy
                && TouchEventY <= dialogDy + dy + dialogChoiceHeight
                && TouchEventX >= dx
                && TouchEventX <= width - dx
        ) {
            float minDistence = dialogWidth;
            //From 0 to judge one by one, if the distance farther on the end of the cycle
            for (int i = 0; i < total; i++) {
                float centreDistance = Math.abs(TouchEventX - floatArray[i]);
                if (centreDistance < minDistence) {
                    dialogChoiceIndex = i;
                    minDistence = centreDistance;
                } else {
                    break;
                }
            }
        } else {
            dialogChoiceIndex = -1;
        }
    }
}
