package com.tunasushi.tuna;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
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

    private String dialogText;
    private float dialogTextSize;
    private int dialogTextColor;
    private float dialogTextDy;

    //
    private Typeface dialogTextMode;
    private static final int[] dialogTextModeArray = {Typeface.NORMAL, Typeface.BOLD, Typeface.ITALIC, Typeface.BOLD_ITALIC};

    public Typeface getDialogTextTypeFace() {
        return dialogTextMode;
    }

    public void setDialogTextTypeFace(Typeface dialogTextMode) {
        this.dialogTextMode = dialogTextMode;
    }

    //
    private String dialogContent;
    private float dialogContentSize;
    private int dialogContentColor;
    private float dialogContentDy;

    //
    private Typeface dialogContentMode;
    private static final int[] dialogContentModeArray = {Typeface.NORMAL, Typeface.BOLD, Typeface.ITALIC, Typeface.BOLD_ITALIC};

    public Typeface getDialogContentTypeFace() {
        return dialogContentMode;
    }

    public void setDialogContentTypeFace(Typeface dialogContentMode) {
        this.dialogContentMode = dialogContentMode;
    }

    private float dialogContentPaddingLeft;
    private float dialogContentPaddingRight;

    private int dialogChoiceBackgroundNormal;
    private int dialogChoiceBackgroundPress;
    private float dialogChoiceHeight;

    private String[] dialogChoiceTextArray;

    public String[] getDialogChoiceTextArray() {
        return dialogChoiceTextArray;
    }

    public void setDialogChoiceTextArray(String[] dialogChoiceTextArray) {
        this.dialogChoiceTextArray = dialogChoiceTextArray;
    }

    private float dialogChoiceSize;

    private int dialogChoiceColor;
    //Because there are only string-array and integer-array, it cannot be defined in xml
    private int[] dialogChoiceColorArray;

    public int[] getDialogChoiceColorArray() {
        return dialogChoiceColorArray;
    }

    public void setDialogChoiceColorArray(int[] dialogChoiceColorArray) {
        this.dialogChoiceColorArray = dialogChoiceColorArray;
    }


    private int dialogChoiceIndex;

    public int getDialogChoiceIndex() {
        return dialogChoiceIndex;
    }

    public void setDialogChoiceIndex(int dialogChoiceIndex) {
        this.dialogChoiceIndex = dialogChoiceIndex;
    }

    //
    private float dialogRadiusBottomLeft;
    private float dialogRadiusBottomRight;
    private int dialogChoiceLeft;
    private int dialogChoiceTop;
    private int dialogChoiceRight;
    private int dialogChoiceBottom;

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

        dialogText = typedArray.getString(R.styleable.TDialog_dialogText);
        dialogTextSize = typedArray.getDimension(R.styleable.TDialog_dialogTextSize, textSizeDefault);
        dialogTextColor = typedArray.getColor(R.styleable.TDialog_dialogTextColor, textColorDefault);
        dialogTextDy = typedArray.getDimension(R.styleable.TDialog_dialogTextDy, 0);

        int dialogTextModeIndex = typedArray.getInt(R.styleable.TDialog_dialogTextMode, 0);
        if (dialogTextModeIndex >= 0) {
            dialogTextMode = Typeface.create(Typeface.DEFAULT, dialogTextModeArray[dialogTextModeIndex]);
        }

        dialogContent = typedArray.getString(R.styleable.TDialog_dialogContent);
        dialogContentSize = typedArray.getDimension(R.styleable.TDialog_dialogContentSize, textSizeDefault);
        dialogContentColor = typedArray.getColor(R.styleable.TDialog_dialogContentColor, textColorDefault);
        dialogContentDy = typedArray.getDimension(R.styleable.TDialog_dialogContentDy, 0);

        int dialogContentModeIndex = typedArray.getInt(R.styleable.TDialog_dialogContentMode, 0);
        if (dialogContentModeIndex >= 0) {
            dialogContentMode = Typeface.create(Typeface.DEFAULT, dialogContentModeArray[dialogContentModeIndex]);
        }

        dialogContentPaddingLeft = typedArray.getDimension(R.styleable.TDialog_dialogContentPaddingLeft, 0);
        dialogContentPaddingRight = typedArray.getDimension(R.styleable.TDialog_dialogContentPaddingRight, 0);

        dialogChoiceBackgroundNormal = typedArray.getColor(R.styleable.TDialog_dialogChoiceBackgroundNormal, Color.TRANSPARENT);
        dialogChoiceBackgroundPress = typedArray.getColor(R.styleable.TDialog_dialogChoiceBackgroundPress, dialogChoiceBackgroundNormal);

        int dialogChoiceTextArrayId = typedArray.getResourceId(R.styleable.TDialog_dialogChoiceValueArray, -1);
        if (dialogChoiceTextArrayId != -1) {
            dialogChoiceTextArray = typedArray.getResources().getStringArray(dialogChoiceTextArrayId);
            total = dialogChoiceTextArray.length;
            floatArray = new float[total];
        } else {
            throw new IllegalArgumentException("The content attribute require a property named dialogChoiceTextArray");
        }

        dialogChoiceHeight = typedArray.getDimension(R.styleable.TDialog_dialogChoiceHeight, 0);
        if (dialogChoiceHeight <= 0) {
            throw new IndexOutOfBoundsException("The content attribute dialogChoiceHeight length must be greater than 0");
        }

        //
        dialogChoiceSize = typedArray.getDimension(R.styleable.TDialog_dialogChoiceSize, textSizeDefault);

        // If dialogChoiceColorArray is set then dialogChoiceColor will be replaced!
        int dialogChoiceColorArrayId = typedArray.getResourceId(R.styleable.TDialog_dialogChoiceColorArray, -1);
        if (dialogChoiceColorArrayId != -1) {
            dialogChoiceColorArray = typedArray.getResources().getIntArray(dialogChoiceColorArrayId);
        } else {
            dialogChoiceColor = typedArray.getColor(R.styleable.TDialog_dialogChoiceColor, textColorDefault);
            dialogChoiceColorArray = new int[total];
            for (int i = 0; i < total; i++) {
                dialogChoiceColorArray[i] = dialogChoiceColor;
            }
        }

        //
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
        share = dialogWidth / total;
        //floatArray means dialogChoiceArrayCenterX

        dialogChoiceTop = (int) (dialogHeight - dialogChoiceHeight + dialogStrokeWidth * 0.5f);
        dialogChoiceBottom = (int) (dialogHeight - dialogStrokeWidth);

        for (int i = 0; i < total; i++) {
            floatArray[i] = share * (i + 0.5f) + dx;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (dialogSurround != Color.TRANSPARENT) {
            canvas.drawColor(dialogSurround);
        }
        canvas.translate(dx, dy);

        //
        drawRectClassic(canvas,
                dialogWidth,
                dialogHeight,
                dialogBackground,
                dialogStrokeWidth,
                dialogStrokeColor,
                radius);

        //drawDialogText
        drawText(
                canvas,
                dialogText,
                dialogWidth,
                dialogWidth / 2,
                dialogTextSize * 0.5f + dialogTextDy,
                0, 0,
                initTextPaint(Paint.Style.FILL, dialogTextColor, dialogTextSize, dialogTextMode, Paint.Align.CENTER));

        //drawDialogContent
        drawText(
                canvas,
                dialogContent,
                dialogWidth,
                dialogWidth / 2,
                dialogHeight / 2 + dialogContentDy,
                dialogContentPaddingLeft, dialogContentPaddingRight,
                initTextPaint(Paint.Style.FILL, dialogContentColor, dialogContentSize, dialogContentMode, Paint.Align.CENTER));

        //draw Choice
        for (int i = 0; i < total; i++) {
            //Only one
            if (i == 0 && i == total - 1) {
                //draw choice separate line horizontal
                canvas.drawLine(0, dialogHeight - dialogChoiceHeight, dialogWidth, dialogHeight - dialogChoiceHeight,
                        initPaint(Paint.Style.FILL, dialogStrokeColor, dialogStrokeWidth));

                dialogRadiusBottomLeft = radius;
                dialogRadiusBottomRight = radius;

                dialogChoiceLeft = (int) (share * i + dialogStrokeWidth);
                dialogChoiceRight = (int) (share * (i + 1) - dialogStrokeWidth);

                //Left
            } else if (i == 0) {
                //draw choice separate line horizontal
                canvas.drawLine(0, dialogHeight - dialogChoiceHeight, dialogWidth, dialogHeight - dialogChoiceHeight,
                        initPaint(Paint.Style.FILL, dialogStrokeColor, dialogStrokeWidth));

                dialogRadiusBottomLeft = radius;
                dialogRadiusBottomRight = 0;

                dialogChoiceLeft = (int) (share * i + dialogStrokeWidth);
                dialogChoiceRight = (int) (share * (i + 1) - dialogStrokeWidth / 2);

                //Right
            } else if (i == total - 1) {
                //draw choice separate line vertical
                canvas.drawLine(share * i, dialogHeight - dialogChoiceHeight, share * i, dialogHeight,
                        initPaint(Paint.Style.FILL, dialogStrokeColor, dialogStrokeWidth)
                );

                dialogRadiusBottomLeft = 0;
                dialogRadiusBottomRight = radius;

                dialogChoiceLeft = (int) (share * i + dialogStrokeWidth / 2);
                dialogChoiceRight = (int) (share * (i + 1) - dialogStrokeWidth);

                //Center
            } else {
                //draw choice separate line vertical
                canvas.drawLine(share * i, dialogHeight - dialogChoiceHeight, share * i, dialogHeight,
                        initPaint(Paint.Style.FILL, dialogStrokeColor, dialogStrokeWidth)
                );

                dialogRadiusBottomLeft = 0;
                dialogRadiusBottomRight = 0;

                dialogChoiceLeft = (int) (share * i + dialogStrokeWidth / 2);
                dialogChoiceRight = (int) (share * (i + 1) - dialogStrokeWidth / 2);
            }

            //
            drawRectCustom(
                    canvas,
                    dialogChoiceLeft,
                    dialogChoiceTop,
                    dialogChoiceRight,
                    dialogChoiceBottom,
                    i == dialogChoiceIndex ? isPress() ? dialogChoiceBackgroundPress : dialogChoiceBackgroundNormal : dialogChoiceBackgroundNormal,
                    0, dialogRadiusBottomLeft, 0, dialogRadiusBottomRight);

            //
            drawText(
                    canvas,
                    dialogChoiceTextArray[i],
                    dialogWidth,
                    share * (i + 0.5f),
                    dialogHeight - dialogChoiceHeight * 0.5f,
                    0,
                    0,
                    initTextPaint(Paint.Style.FILL,
                            dialogChoiceColorArray[i],
                            dialogChoiceSize,
                            Paint.Align.CENTER));
        }
        canvas.translate(-dx, -dy);
    }

    @Override
    public void setTouchXYRaw(float touchX, float touchY) {
        x = touchX;
        y = touchY;
        if (y >= dialogHeight - dialogChoiceHeight + dy
                && y <= dialogHeight - dialogChoiceHeight + dy + dialogChoiceHeight
                && x >= dx
                && x <= width - dx
        ) {
            float distenceMin = dialogWidth;
            for (int i = 0; i < total; i++) {
                float centreDistance = Math.abs(x - floatArray[i]);
                if (centreDistance < distenceMin) {
                    dialogChoiceIndex = i;
                    distenceMin = centreDistance;
                } else {
                    break;
                }
            }
        } else {
            dialogChoiceIndex = -1;
        }
        invalidate();
    }
}
