package com.tunasushi.tuna;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import com.tunasushi.R;


/**
 * @author TunaSashimi
 * @date 2015-10-30 16:51
 * @Copyright 2015 TunaSashimi. All rights reserved.
 * @Description
 */
public class TButton extends TView {
    private float buttonBitmapFractionTop, buttonBitmapFractionBottom;
    private float buttonTextFractionTop, buttonTextFractionBottom;

    private int buttonBackgroundNormal, buttonBackgroundPress;
    private int buttonForegroundNormal, buttonForegroundPress;

    private String buttonText;

    public String getButtonText() {
        return buttonText;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }

    private float buttonTextSize;
    private int buttonTextColor;

    private Bitmap buttonSrcNormal, buttonSrcPress;

    public TButton(Context context) {
        this(context, null);
    }

    public TButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        tag = TButton.class.getSimpleName();

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TButton);

        buttonBackgroundNormal = typedArray.getColor(R.styleable.TButton_buttonBackgroundNormal, Color.TRANSPARENT);
        buttonBackgroundPress = typedArray.getColor(R.styleable.TButton_buttonBackgroundPress, buttonBackgroundNormal);

        buttonForegroundNormal = typedArray.getColor(R.styleable.TButton_buttonForegroundNormal, Color.TRANSPARENT);
        buttonForegroundPress = typedArray.getColor(R.styleable.TButton_buttonForegroundPress, buttonForegroundNormal);

        int buttonSrcNormalId = typedArray.getResourceId(R.styleable.TButton_buttonSrcNormal, -1);
        if (buttonSrcNormalId != -1) {
            buttonSrcNormal = BitmapFactory.decodeResource(getResources(), buttonSrcNormalId);
        }

        int buttonSrcPressId = typedArray.getResourceId(R.styleable.TButton_buttonSrcPress, buttonSrcNormalId);
        if (buttonSrcPressId != -1) {
            buttonSrcPress = BitmapFactory.decodeResource(getResources(), buttonSrcPressId);
        }

        // depending on the type of fraction, the getFraction method multiples
        // the values accordingly.
        // If you specify a parent fraction (%p), it uses the second argument
        // (pbase), ignoring the first.
        // On the other hand, specifying a normal fraction, only the base
        // argument is used, multiplying the fraction by this.
        buttonBitmapFractionTop = typedArray.getFraction(R.styleable.TButton_buttonBitmapFractionTop, 1, 1, 0);
        buttonBitmapFractionBottom = typedArray.getFraction(R.styleable.TButton_buttonBitmapFractionBottom, 1, 1, 1);

        buttonText = typedArray.getString(R.styleable.TButton_buttonText);
        buttonTextSize = typedArray.getDimension(R.styleable.TButton_buttonTextSize, textSizeDefault);
        buttonTextColor = typedArray.getColor(R.styleable.TButton_buttonTextColor, textColorDefault);

        buttonTextFractionTop = typedArray.getFraction(R.styleable.TButton_buttonTextFractionTop, 1, 1, 0);
        buttonTextFractionBottom = typedArray.getFraction(R.styleable.TButton_buttonTextFractionBottom, 1, 1, 1);

        typedArray.recycle();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        int buttonSrcNormalWidth = buttonSrcNormal.getWidth();
        int buttonSrcNormalHeight = buttonSrcNormal.getHeight();
        int buttonSrcPressWidth = buttonSrcPress.getWidth();
        int buttonSrcPressHeight = buttonSrcPress.getHeight();

        if (buttonSrcNormalWidth != buttonSrcPressWidth || buttonSrcNormalHeight != buttonSrcPressHeight) {
            throw new IllegalArgumentException("Both the width and height of the attribute buttonSrcNormal and buttonSrcPress needed equal");
        }

        scale = height * (buttonBitmapFractionBottom - buttonBitmapFractionTop) / buttonSrcNormalWidth;
        dy = height * buttonBitmapFractionTop;
        dx = (width - buttonSrcNormalWidth * scale) * 0.5f;

        matrixNormal = initMatrix(matrixNormal, scale, scale);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(press ? buttonBackgroundPress : buttonBackgroundNormal);

        canvas.save();
        canvas.translate(dx, dy);
        canvas.drawBitmap(press ? buttonSrcPress : buttonSrcNormal, matrixNormal, null);
        canvas.translate(-dx, -dy);

        drawText(canvas, buttonText, width, width >> 1, (height * buttonTextFractionTop + height * buttonTextFractionBottom) * 0.5f, 0, 0,
                initTextPaint(Paint.Style.FILL, buttonTextColor, buttonTextSize, Paint.Align.CENTER));

        canvas.drawColor(press ? buttonForegroundPress : buttonForegroundNormal);

    }

    @Override
    public void setTouchXY(float touchX, float touchY) {
        invalidate();
    }
}
