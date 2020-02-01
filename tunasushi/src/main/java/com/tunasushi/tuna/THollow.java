package com.tunasushi.tuna;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;

import com.tuna.R;

import static com.tunasushi.tool.PaintTool.initTunaTextPaint;
import static com.tunasushi.tool.PaintTool.tunaPaint;


/**
 * @author Tunasashimi
 * @date 11/5/15 15:49
 * @Copyright 2015 TunaSashimi. All rights reserved.
 * @Description
 */
public class THollow extends TView {
    private String tunaHollowTextValue;
    private float tunaHollowTextSize;
    private float tunaHollowTextFractionVertical;
    private Bitmap tunaHollowSrcBitmap;

    public THollow(Context context) {
        this(context, null);
    }

    public THollow(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public THollow(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        tunaTag = THollow.class.getSimpleName();

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.THollow);

        int tunaBitmapId = typedArray.getResourceId(R.styleable.THollow_hollowBitmap, -1);
        if (tunaBitmapId != -1) {
            tunaSrcBitmap = BitmapFactory.decodeResource(getResources(), tunaBitmapId);
        }

        tunaHollowTextValue = typedArray.getString(R.styleable.THollow_hollowTextValue);
        tunaHollowTextSize = typedArray.getDimension(R.styleable.THollow_hollowTextSize, 0);
        tunaHollowTextFractionVertical = typedArray.getFraction(R.styleable.THollow_hollowTextFractionVertical, 1, 1, 0.5f);

        typedArray.recycle();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if (tunaSrcBitmap == null) {
            return;
        }

        int dy = (int) (tunaHeight * tunaHollowTextFractionVertical);

        tunaSrcBitmap = Bitmap.createScaledBitmap(tunaSrcBitmap, tunaWidth, tunaHeight, false);
        tunaHollowSrcBitmap = Bitmap.createBitmap(tunaWidth, tunaHeight, Bitmap.Config.ARGB_8888);

        tunaCanvas = new Canvas(tunaHollowSrcBitmap);
        drawTunaText(tunaCanvas, tunaHollowTextValue, tunaWidth, tunaWidth >> 1, dy, initTunaTextPaint(Color.WHITE, tunaHollowTextSize, Paint.Align.CENTER));

        tunaPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
        tunaCanvas.drawBitmap(tunaSrcBitmap, 0, 0, tunaPaint);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(tunaHollowSrcBitmap, 0, 0, null);
    }
}
