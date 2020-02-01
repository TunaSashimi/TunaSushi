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
 * @author Tunasashimi
 * @date 11/5/15 15:49
 * @Copyright 2015 TunaSashimi. All rights reserved.
 * @Description
 */
public class TMove extends TView {
    private float tunaMoveDistance;
    private Bitmap tunaMoveSrcBitmap;

    private int tunaMoveSrcBitmapHeight;
    private int tunaMoveSrcBitmapStartY;
    private static final int tunaMoveMsgWhat = 0x123;

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == tunaMoveMsgWhat) {
                if (tunaMoveDistance <= 0) {
                    tunaMoveSrcBitmapStartY = (int) (tunaMoveSrcBitmapHeight - tunaMoveDistance);
                } else {
                    tunaMoveSrcBitmapStartY -= tunaMoveDistance;
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

        tunaTag = TMove.class.getSimpleName();

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TMove);

        int tunaBitmapId = typedArray.getResourceId(R.styleable.TMove_moveBitmap, -1);
        if (tunaBitmapId != -1) {
            tunaSrcBitmap = BitmapFactory.decodeResource(getResources(), tunaBitmapId);
        }

        tunaMoveDistance = typedArray.getDimension(R.styleable.TMove_moveDistance, 2);

        typedArray.recycle();


        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(tunaMoveMsgWhat);
            }
        }, 0, 50);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (tunaSrcBitmap == null) {
            return;
        }
        int tunaSrcBitmapWidth = tunaSrcBitmap.getWidth();
        tunaScale = tunaWidth * 1f / tunaSrcBitmapWidth;


        tunaMoveSrcBitmap = Bitmap.createBitmap(tunaSrcBitmap, 0, 0, tunaSrcBitmap.getWidth(), tunaSrcBitmap.getHeight(), initTunaMatrix(tunaScale, tunaScale), true);
        tunaMoveSrcBitmapHeight = tunaMoveSrcBitmap.getHeight();

        tunaMoveSrcBitmapStartY = tunaMoveSrcBitmapHeight - tunaHeight;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //
        canvas.drawBitmap(Bitmap.createBitmap(tunaMoveSrcBitmap, 0, tunaMoveSrcBitmapStartY, tunaWidth, tunaHeight), 0, 0, null);
    }
}