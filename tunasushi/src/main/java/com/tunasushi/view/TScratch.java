package com.tunasushi.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;


import com.tunasushi.R;

import static com.tunasushi.tool.ConvertTool.convertToPX;

/**
 * @author TunaSashimi
 * @date 2015-10-30 16:58
 * @Copyright 2015 TunaSashimi. All rights reserved.
 * @Description
 */
public class TScratch extends TView {

    private Paint scratchCoverPaint;

    private Bitmap scratchCoverBitmap, scratchSrc;

    //
    private int scratchTouchX, scratchTouchY;

    //
    private String scratchText;
    //
    private int scratchTextSize;
    private int scratchTextColor;

    private int scratchCoverColor;
    private float scratchCoverStrokeWidth;

    private float scratchRadius;

    private volatile boolean scratchComplete;

    public interface onScratchCompleteListener {
        void onScratchComplete();
    }

    private onScratchCompleteListener onScratchCompleteListener;

    public onScratchCompleteListener getOnScratchCompleteListener() {
        return onScratchCompleteListener;
    }

    public void setOnScratchCompleteListener(onScratchCompleteListener onScratchCompleteListener) {
        this.onScratchCompleteListener = onScratchCompleteListener;
    }

    public TScratch(Context context) {
        this(context, null);
    }

    public TScratch(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TScratch(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        tag = TScratch.class.getSimpleName();

        //
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TScratch);
        int scratchSrcId = typedArray.getResourceId(R.styleable.TScratch_scratchSrc, -1);
        if (scratchSrcId > -1) {
            scratchSrc = BitmapFactory.decodeResource(getResources(), scratchSrcId);
        }

        scratchRadius = (int) typedArray.getDimension(R.styleable.TScratch_scratchRadius, convertToPX(10, TypedValue.COMPLEX_UNIT_DIP));
        scratchCoverColor = typedArray.getColor(R.styleable.TScratch_scratchCoverColor, 0xffc0c0c0);

        scratchCoverStrokeWidth = (int) typedArray.getDimension(R.styleable.TScratch_scratchCoverStrokeWidth, convertToPX(20, TypedValue.COMPLEX_UNIT_DIP));

        scratchText = typedArray.getString(R.styleable.TScratch_scratchText);
        scratchTextSize = (int) typedArray.getDimension(R.styleable.TScratch_scratchTextSize, textSizeDefault);
        scratchTextColor = typedArray.getColor(R.styleable.TScratch_scratchTextColor, textColorDefault);

        typedArray.recycle();

        //
        initPath();
        initRect();

        scratchCoverPaint = new Paint();
    }

    public void setScratchText(String scratchText) {
        this.scratchText = scratchText;
        paint.getTextBounds(scratchText, 0, scratchText.length(), rect);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        //
        scratchCoverBitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        canvas = new Canvas(scratchCoverBitmap);

        //
        scratchCoverPaint.setColor(scratchCoverColor);
        scratchCoverPaint.setAntiAlias(true);
        scratchCoverPaint.setDither(true);
        // 设置结合处的样子, Miter为锐角, Round为圆弧, BEVEL为直线。
        scratchCoverPaint.setStrokeJoin(Paint.Join.ROUND);
        // 设置笔刷类型
        scratchCoverPaint.setStrokeCap(Paint.Cap.ROUND);
        scratchCoverPaint.setStyle(Style.FILL);
        scratchCoverPaint.setStrokeWidth(scratchCoverStrokeWidth);

        //
        initTextPaint(Style.FILL, scratchTextColor, scratchTextSize, Align.LEFT);
        paint.getTextBounds(scratchText, 0, scratchText.length(), rect);

        canvas.drawRoundRect(new RectF(0, 0, width, height), scratchRadius, scratchRadius, scratchCoverPaint);
        canvas.drawBitmap(scratchSrc, null, new Rect(0, 0, width, height), null);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        int action = event.getAction();
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                scratchTouchX = x;
                scratchTouchY = y;
                path.moveTo(scratchTouchX, scratchTouchY);
                break;
            case MotionEvent.ACTION_MOVE:
                dx = Math.abs(x - scratchTouchX);
                dy = Math.abs(y - scratchTouchY);
                if (dx > 3 || dy > 3) {
                    path.lineTo(x, y);
                }
                scratchTouchX = x;
                scratchTouchY = y;
                break;
            case MotionEvent.ACTION_UP:
                if (!scratchComplete) {
                    measurePixels();
                }
                break;
            default:
                break;
        }
        if (!scratchComplete) {
            invalidate();
        }
        return true;
    }

    private void measurePixels() {
        new Thread(mRunnable).start();
    }

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            int w = getWidth();
            int h = getHeight();
            float wipeArea = 0;
            float totalArea = w * h;
            Bitmap bitmap = scratchCoverBitmap;
            int[] mPixels = new int[w * h];
            // 获得bitmap上所有信息
            bitmap.getPixels(mPixels, 0, w, 0, 0, w, h);
            for (int i = 0; i < w; i++) {
                for (int j = 0; j < h; j++) {
                    int index = i + j * w;
                    if (mPixels[index] == 0) {
                        wipeArea++;
                    }
                }
            }

            if (wipeArea > 0 && totalArea > 0) {
                int percent = (int) (wipeArea * 100 / totalArea);
                if (percent > 60) {
                    scratchComplete = true;
                    postInvalidate();
                }
            }
        }
    };

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawText(scratchText, (width >> 1) - (rect.width() >> 1), (height >> 1) + (rect.height() >> 1), paint);

        if (!scratchComplete) {
            scratchCoverPaint.setStyle(Style.STROKE);
            scratchCoverPaint.setXfermode(new PorterDuffXfermode(Mode.DST_OUT));
            this.canvas.drawPath(path, scratchCoverPaint);
            canvas.drawBitmap(scratchCoverBitmap, 0, 0, null);
        }

        if (scratchComplete) {
            if (onScratchCompleteListener != null) {
                onScratchCompleteListener.onScratchComplete();
            }
        }
    }
}
