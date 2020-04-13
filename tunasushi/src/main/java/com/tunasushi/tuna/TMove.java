package com.tunasushi.tuna;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;


import com.tuna.R;

import java.util.Timer;
import java.util.TimerTask;


/**
 * @author TunaSashimi
 * @date 2015-11-05 15:49
 * @Copyright 2015 TunaSashimi. All rights reserved.
 * @Description
 */
public class TMove extends TView {
    private float moveDistance;
    private Bitmap moveSrc;

    private int moveSrcHeight;
    private int moveSrcStartY;
    private static final int moveMsgWhat = 0x123;

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == moveMsgWhat) {
                if (moveDistance <= 0) {
                    moveSrcStartY = (int) (moveSrcHeight - moveDistance);
                } else {
                    moveSrcStartY -= moveDistance;
                }
            }
            invalidate();
        }
    };

    public TMove(Context context) {
        this(context, null);
    }

    public TMove(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TMove(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        tag = TMove.class.getSimpleName();

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TMove);

        int moveBitmapId = typedArray.getResourceId(R.styleable.TMove_moveBitmap, -1);
        if (moveBitmapId != -1) {
            srcBitmap = BitmapFactory.decodeResource(getResources(), moveBitmapId);
        }

        moveDistance = typedArray.getDimension(R.styleable.TMove_moveDistance, 2);

        typedArray.recycle();

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(moveMsgWhat);
            }
        }, 0, 50);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (srcBitmap == null) {
            return;
        }
        int srcBitmapWidth = srcBitmap.getWidth();
        scale = width * 1f / srcBitmapWidth;


        moveSrc = Bitmap.createBitmap(srcBitmap, 0, 0, srcBitmap.getWidth(), srcBitmap.getHeight(), initMatrix(matrixNormal, scale, scale), true);
        moveSrcHeight = moveSrc.getHeight();

        moveSrcStartY = moveSrcHeight - height;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //
        canvas.drawBitmap(Bitmap.createBitmap(moveSrc, 0, moveSrcStartY, width, height), 0, 0, null);
    }
}