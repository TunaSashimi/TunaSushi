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
import com.tunasushi.tool.PaintTool;

import static com.tunasushi.tool.DrawTool.drawText;

/**
 * @author Tunasashimi
 * @date 10/30/15 16:51
 * @Copyright 2015 Sashimi. All rights reserved.
 * @Description
 */
public class TButton extends TView{

	private float buttonBitmapFractionTop, buttonBitmapFractionBottom;
	private float buttonTextFractionTop, buttonTextFractionBottom;

	private int buttonBackgroundNormal, buttonBackgroundPress;
	private int buttonForegroundNormal, buttonForegroundPress;

	private String buttonTextValue;

	public String getButtonTextValue(){
		return buttonTextValue;
	}

	public void setButtonTextValue(String buttonTextValue){
		this.buttonTextValue = buttonTextValue;
	}

	private float buttonTextSize;
	private int buttonTextColorNormal;

	private Bitmap buttonBitmapSrcNormal, buttonBitmapSrcPress;

    public TButton(Context context) {
        this(context, null);
    }

    public TButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

		Tag = TButton.class.getSimpleName();

		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TButton);

		buttonBackgroundNormal = typedArray.getColor(R.styleable.TButton_buttonBackgroundNormal, Color.TRANSPARENT);
		buttonBackgroundPress = typedArray.getColor(R.styleable.TButton_buttonBackgroundPress, buttonBackgroundNormal);

		buttonForegroundNormal = typedArray.getColor(R.styleable.TButton_buttonForegroundNormal, Color.TRANSPARENT);
		buttonForegroundPress = typedArray.getColor(R.styleable.TButton_buttonForegroundPress, buttonForegroundNormal);

		int buttonBitmapSrcNormalId = typedArray.getResourceId(R.styleable.TButton_buttonBitmapSrcNormal, -1);
		if (buttonBitmapSrcNormalId != -1) {
			buttonBitmapSrcNormal = BitmapFactory.decodeResource(getResources(), buttonBitmapSrcNormalId);
		}

		int buttonBitmapSrcPressId = typedArray.getResourceId(R.styleable.TButton_buttonBitmapSrcPress, buttonBitmapSrcNormalId);
		if (buttonBitmapSrcPressId != -1) {
			buttonBitmapSrcPress = BitmapFactory.decodeResource(getResources(), buttonBitmapSrcPressId);
		}

		// depending on the type of fraction, the getFraction method multiples
		// the values accordingly.
		// If you specify a parent fraction (%p), it uses the second argument
		// (pbase), ignoring the first.
		// On the other hand, specifying a normal fraction, only the base
		// argument is used, multiplying the fraction by this.
		buttonBitmapFractionTop = typedArray.getFraction(R.styleable.TButton_buttonBitmapFractionTop, 1, 1, 0);
		buttonBitmapFractionBottom = typedArray.getFraction(R.styleable.TButton_buttonBitmapFractionBottom, 1, 1, 1);

		buttonTextValue = typedArray.getString(R.styleable.TButton_buttonTextValue);
		buttonTextSize = typedArray.getDimension(R.styleable.TButton_buttonTextSize, 0);
		buttonTextColorNormal = typedArray.getColor(R.styleable.TButton_buttonTextColorNormal, Color.TRANSPARENT);

		buttonTextFractionTop = typedArray.getFraction(R.styleable.TButton_buttonTextFractionTop, 1, 1, 0);
		buttonTextFractionBottom = typedArray.getFraction(R.styleable.TButton_buttonTextFractionBottom, 1, 1, 1);

		typedArray.recycle();
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom){
		super.onLayout(changed, left, top, right, bottom);

		int buttonBitmapSrcNormalWidth = buttonBitmapSrcNormal.getWidth();
		int buttonBitmapSrcNormalHeight = buttonBitmapSrcNormal.getHeight();
		int buttonBitmapSrcPressWidth = buttonBitmapSrcPress.getWidth();
		int buttonBitmapSrcPressHeight = buttonBitmapSrcPress.getHeight();

		if (buttonBitmapSrcNormalWidth != buttonBitmapSrcPressWidth || buttonBitmapSrcNormalHeight != buttonBitmapSrcPressHeight) {
			throw new IllegalArgumentException("Both the width and height of the attribute buttonBitmapSrcNormal and buttonBitmapSrcPress needed equal");
		}

		scale = height * (buttonBitmapFractionBottom - buttonBitmapFractionTop) / buttonBitmapSrcNormalWidth;
		dy = height * buttonBitmapFractionTop;
		dx = (width - buttonBitmapSrcNormalWidth * scale) * 0.5f;

		initMatrix(scale, scale);
	}

	@Override
	protected void onDraw(Canvas canvas){
		super.onDraw(canvas);
		canvas.drawColor(press ? buttonBackgroundPress : buttonBackgroundNormal);

		canvas.save();
		canvas.translate(dx, dy);
		canvas.drawBitmap(press ? buttonBitmapSrcPress : buttonBitmapSrcNormal, matrix, null);
		canvas.translate(-dx, -dy);

		drawText(canvas, buttonTextValue, width, width >> 1, (height * buttonTextFractionTop + height * buttonTextFractionBottom) * 0.5f, 0, 0,
				PaintTool.initTextPaint(Paint.Style.FILL, buttonTextColorNormal, buttonTextSize, Paint.Align.CENTER));

		canvas.drawColor(press ? buttonForegroundPress : buttonForegroundNormal);

	}
}
