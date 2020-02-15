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

    private String dialogTextValue;
    private float dialogTextSize;
    private int dialogTextColor;
    private float dialogTextDy;

    //
    private Typeface dialogTextTypeFace;

    public enum DialogTextTypeFace {
        NORMAL(0), BOLD(1), ITALIC(2), BOLD_ITALIC(3);
        final int nativeInt;

        DialogTextTypeFace(int ni) {
            nativeInt = ni;
        }
    }

    private static final int[] dialogTextTypeFaceArray = {Typeface.NORMAL, Typeface.BOLD, Typeface.ITALIC, Typeface.BOLD_ITALIC};

    public Typeface getDialogTextTypeFace() {
        return dialogTextTypeFace;
    }

    public void setDialogTextTypeFace(Typeface dialogTextTypeFace) {
        this.dialogTextTypeFace = dialogTextTypeFace;
    }

    //
    private String dialogContentValue;
    private float dialogContentSize;
    private int dialogContentColor;
    private float dialogContentDy;

    //
    private Typeface dialogContentTypeFace;

    public enum DialogContentTypeFace {
        NORMAL(0), BOLD(1), ITALIC(2), BOLD_ITALIC(3);
        final int nativeInt;

        DialogContentTypeFace(int ni) {
            nativeInt = ni;
        }
    }

    private static final int[] dialogContentTypeFaceArray = {Typeface.NORMAL, Typeface.BOLD, Typeface.ITALIC, Typeface.BOLD_ITALIC};

    public Typeface getDialogContentTypeFace() {
        return dialogContentTypeFace;
    }

    public void setDialogContentTypeFace(Typeface dialogContentTypeFace) {
        this.dialogContentTypeFace = dialogContentTypeFace;
    }

    private float dialogContentPaddingLeft;
    private float dialogContentPaddingRight;

    private int dialogChoiceBackgroundNormal;
    private int dialogChoiceBackgroundPress;
    private float dialogChoiceHeight;

    private String[] dialogChoiceTextValueArray;

    public String[] getDialogChoiceTextValueArray() {
        return dialogChoiceTextValueArray;
    }

    public void setDialogChoiceTextValueArray(String[] dialogChoiceTextValueArray) {
        this.dialogChoiceTextValueArray = dialogChoiceTextValueArray;
    }

    private float dialogChoiceSize;
    //Because there are only string-array and integer-array, it cannot be defined in xml
    private float[] dialogChoiceSizeArray;

    private int dialogChoiceColor;
    //Because there are only string-array and integer-array, it cannot be defined in xml
    private int[] dialogChoiceColorArray;

    public int[] getDialogChoiceColorArray() {
        return dialogChoiceColorArray;
    }

    public void setDialogChoiceColorArray(int[] dialogChoiceColorArray) {
        this.dialogChoiceColorArray = dialogChoiceColorArray;
    }

    public float[] getDialogChoiceSizeArray() {
        return dialogChoiceSizeArray;
    }

    public void setDialogChoiceSizeArray(float[] dialogChoiceSizeArray) {
        this.dialogChoiceSizeArray = dialogChoiceSizeArray;
    }

    private int dialogChoiceIndex;

    public int getDialogChoiceIndex() {
        return dialogChoiceIndex;
    }

    public void setDialogChoiceIndex(int dialogChoiceIndex) {
        this.dialogChoiceIndex = dialogChoiceIndex;
    }

    //
    private float dialogRadiusLeftTop;
    private float dialogRadiusLeftBottom;
    private float dialogRadiusRightTop;
    private float dialogRadiusRightBottom;
    private float dialogChoiceWidth;
    private float dialogChoiceDx;
    private float dialogChoiceDy;

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

        dialogTextValue = typedArray.getString(R.styleable.TDialog_dialogTextValue);
        dialogTextSize = typedArray.getDimension(R.styleable.TDialog_dialogTextSize, 0);
        dialogTextColor = typedArray.getColor(R.styleable.TDialog_dialogTextColor, Color.TRANSPARENT);
        dialogTextDy = typedArray.getDimension(R.styleable.TDialog_dialogTextDy, 0);

        int dialogTextTypeFaceIndex = typedArray.getInt(R.styleable.TDialog_dialogTextTypeFace, 0);
        if (dialogTextTypeFaceIndex >= 0) {
            dialogTextTypeFace = Typeface.create(Typeface.DEFAULT, dialogTextTypeFaceArray[dialogTextTypeFaceIndex]);
        }

        dialogContentValue = typedArray.getString(R.styleable.TDialog_dialogContentValue);
        dialogContentSize = typedArray.getDimension(R.styleable.TDialog_dialogContentSize, 0);
        dialogContentColor = typedArray.getColor(R.styleable.TDialog_dialogContentColor, Color.TRANSPARENT);
        dialogContentDy = typedArray.getDimension(R.styleable.TDialog_dialogContentDy, 0);

        int dialogContentTypeFaceIndex = typedArray.getInt(R.styleable.TDialog_dialogContentTypeFace, 0);
        if (dialogContentTypeFaceIndex >= 0) {
            dialogContentTypeFace = Typeface.create(Typeface.DEFAULT, dialogContentTypeFaceArray[dialogContentTypeFaceIndex]);
        }

        dialogContentPaddingLeft = typedArray.getDimension(R.styleable.TDialog_dialogContentPaddingLeft, 0);
        dialogContentPaddingRight = typedArray.getDimension(R.styleable.TDialog_dialogContentPaddingRight, 0);

        dialogChoiceBackgroundNormal = typedArray.getColor(R.styleable.TDialog_dialogChoiceBackgroundNormal, Color.TRANSPARENT);
        dialogChoiceBackgroundPress = typedArray.getColor(R.styleable.TDialog_dialogChoiceBackgroundPress, dialogChoiceBackgroundNormal);

        int dialogChoiceTextValueArrayId = typedArray.getResourceId(R.styleable.TDialog_dialogChoiceValueArray, -1);

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

        //
        dialogChoiceSize = typedArray.getDimension(R.styleable.TDialog_dialogChoiceSize, 0);
        dialogChoiceSizeArray = new float[total];
        for (int i = 0; i < total; i++) {
            dialogChoiceSizeArray[i] = dialogChoiceSize;
        }

        //
        dialogChoiceColor = typedArray.getColor(R.styleable.TDialog_dialogChoiceColor, Color.TRANSPARENT);
        dialogChoiceColorArray = new int[total];
        for (int i = 0; i < total; i++) {
            dialogChoiceColorArray[i] = dialogChoiceColor;
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
        dialogChoiceDy = dialogHeight - dialogChoiceHeight;

        //floatArray means dialogChoiceArrayCenterX
        for (int i = 0; i < total; i++) {
            floatArray[i] = share * i + share / 2;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(dialogSurround);
        canvas.translate(dx, dy);

        //
        drawRectClassic(canvas,
                dialogWidth + dialogStrokeWidth * 2,
                dialogHeight,
                dialogBackground,
                dialogStrokeWidth,
                dialogStrokeColor,
                radius);

        //drawDialogText
        drawText(
                canvas,
                dialogTextValue,
                dialogWidth,
                dialogWidth / 2,
                dialogTextSize * 0.5f + dialogTextDy,
                0, 0,
                initTextPaint(Paint.Style.FILL, dialogTextColor, dialogTextSize, dialogTextTypeFace, Paint.Align.CENTER));

        //drawDialogContent
        drawText(
                canvas,
                dialogContentValue,
                dialogWidth,
                dialogWidth / 2,
                dialogHeight / 2 + dialogContentDy,
                dialogContentPaddingLeft, dialogContentPaddingRight,
                initTextPaint(Paint.Style.FILL, dialogContentColor, dialogContentSize, dialogContentTypeFace, Paint.Align.CENTER));

        //draw Choice
        //Longer in the middle and shorter choices on the left and right!
        for (int i = 0; i < total; i++) {
            if (i == 0 && i == total - 1) {
                dialogRadiusLeftBottom = radius;
                dialogRadiusRightBottom = radius;
                dialogChoiceWidth = dialogWidth + dialogStrokeWidth * 2;
                dialogChoiceDx = 0;
                //Left
            } else if (i == 0) {
                dialogRadiusLeftBottom = radius;
                dialogRadiusRightBottom = 0;
                dialogChoiceWidth = share + dialogStrokeWidth * 2;
                dialogChoiceDx = 0;
                //Right
            } else if (i == total - 1) {
                dialogRadiusLeftBottom = 0;
                dialogRadiusRightBottom = radius;
                dialogChoiceWidth = share + dialogStrokeWidth * 2;
                dialogChoiceDx = share * i;
            } else {
                dialogRadiusLeftBottom = 0;
                dialogRadiusRightBottom = 0;
                dialogChoiceWidth = share + dialogStrokeWidth * 2;
                dialogChoiceDx = share * i - dialogStrokeWidth;
            }

            //
            canvas.translate(dialogChoiceDx, dialogChoiceDy);

            //
            drawRectCustom(canvas,
                    (int) dialogChoiceWidth, (int) dialogChoiceHeight,
                    i == dialogChoiceIndex ? isPress() ? dialogChoiceBackgroundPress : dialogChoiceBackgroundNormal : dialogChoiceBackgroundNormal,
                    dialogStrokeWidth, dialogStrokeColor,
                    dialogRadiusLeftTop, dialogRadiusLeftBottom, dialogRadiusRightTop, dialogRadiusRightBottom);
            //
            drawText(
                    canvas,
                    dialogChoiceTextValueArray[i],
                    dialogWidth,
                    share / 2,
                    dialogChoiceHeight / 2,
                    0,
                    0,
                    initTextPaint(Paint.Style.FILL,
                            dialogChoiceColorArray[i],
                            dialogChoiceSizeArray[i],
                            Paint.Align.CENTER));
            //
            canvas.translate(-dialogChoiceDx, -dialogChoiceDy);
        }
        canvas.translate(-dx, -dy);
    }

    @Override
    public void setTouchXYRaw(float touchX, float touchY) {
        x = touchX;
        y = touchY;
        if (y >= dialogChoiceDy + dy
                && y <= dialogChoiceDy + dy + dialogChoiceHeight
                && x >= dx
                && x <= width - dx
        ) {
            float distenceMin = dialogWidth;
            //From 0 to judge one by one, if the distance farther on the end of the cycle
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
