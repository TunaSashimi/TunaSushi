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
import com.tunasushi.tool.PaintTool;

import static com.tunasushi.tool.DrawTool.drawText;
import static com.tunasushi.tool.PaintTool.paint;


/**
 * @author Tunasashimi
 * @date 11/5/15 15:49
 * @Copyright 2015 Sashimi. All rights reserved.
 * @Description
 */
public class THollow extends TView {
    private String hollowTextValue;
    private float hollowTextSize;
    private float hollowTextFractionVertical;
    private Bitmap hollowSrcBitmap;

    public THollow(Context context) {
        this(context, null);
    }

    public THollow(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public THollow(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        Tag = THollow.class.getSimpleName();

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.THollow);

        int hollowBitmapId = typedArray.getResourceId(R.styleable.THollow_hollowBitmap, -1);
        if (hollowBitmapId != -1) {
            srcBitmap = BitmapFactory.decodeResource(getResources(), hollowBitmapId);
        }

        hollowTextValue = typedArray.getString(R.styleable.THollow_hollowTextValue);
        hollowTextSize = typedArray.getDimension(R.styleable.THollow_hollowTextSize, 0);
        hollowTextFractionVertical = typedArray.getFraction(R.styleable.THollow_hollowTextFractionVertical, 1, 1, 0.5f);

        typedArray.recycle();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if (srcBitmap == null) {
            return;
        }

        dy = (int) (height * hollowTextFractionVertical);

        srcBitmap = Bitmap.createScaledBitmap(srcBitmap, width, height, false);
        hollowSrcBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        canvas = new Canvas(hollowSrcBitmap);
        drawText(canvas, hollowTextValue, width, width >> 1, dy, PaintTool.initTextPaint(Color.WHITE, hollowTextSize, Paint.Align.CENTER));

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
        canvas.drawBitmap(srcBitmap, 0, 0, paint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(hollowSrcBitmap, 0, 0, null);
    }
}
