package com.tunasushi.tuna;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @author TunaSashimi
 * @date 2015-10-30 16:50
 * @Copyright 2015 TunaSashimi. All rights reserved.
 * @Description
 */
public class TTrack extends TView {
    private float trackX, trackY;
    private float trackDistanceX, trackDistanceY;
    private float trackIntervalX, trackIntervalY;

    private int trackIntervalTime;
    private boolean trackisIntervalTime;

    private Paint trackPaint;

    public TTrack(Context context) {
        this(context, null);
    }

    public TTrack(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TTrack(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        tag = TTrack.class.getSimpleName();

        trackPaint = new Paint();
        trackPaint.setColor(Color.CYAN);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                trackDistanceX = event.getX() - trackX;
                trackDistanceY = event.getY() - trackY;

                trackIntervalX = trackDistanceX / 5;
                trackIntervalY = trackDistanceY / 5;

                trackIntervalTime = 5;
                trackisIntervalTime = true;

                invalidate();

                break;
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
                if (trackisIntervalTime) {
                    return true;
                }
                trackX = event.getX();
                trackY = event.getY();
                invalidate();
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(trackX, trackY, 80, trackPaint);

        if (trackIntervalTime > 0) {
            trackIntervalTime--;
            trackX = trackX + trackIntervalX;
            trackY = trackY + trackIntervalY;
            postInvalidateDelayed(20);
        } else {
            trackisIntervalTime = false;
        }
    }
}
