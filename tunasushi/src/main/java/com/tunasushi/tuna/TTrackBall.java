package com.tunasushi.tuna;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @author Tunasashimi
 * @date 10/30/15 16:50
 * @Copyright 2015 TunaSashimi. All rights reserved.
 * @Description
 */
public class TTrackBall extends TView {
    private float currentX, currentY;
    private float distanceX, distanceY;
    private float intervalX, intervalY;

    private int intervalTime;
    private boolean isIntervalTime;

    private Paint p;

    public TTrackBall(Context context) {
        this(context, null);
    }

    public TTrackBall(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TTrackBall(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        tunaTag = TTrackBall.class.getSimpleName();

        p = new Paint();
        p.setColor(Color.CYAN);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                distanceX = event.getX() - currentX;
                distanceY = event.getY() - currentY;

                intervalX = distanceX / 5;
                intervalY = distanceY / 5;

                intervalTime = 5;
                isIntervalTime = true;

                invalidate();

                break;
            case MotionEvent.ACTION_MOVE:
                if (isIntervalTime) {
                    return true;
                }
                currentX = event.getX();
                currentY = event.getY();
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                if (isIntervalTime) {
                    return true;
                }
                currentX = event.getX();
                currentY = event.getY();
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

        canvas.drawCircle(currentX, currentY, 80, p);

        if (intervalTime > 0) {
            intervalTime--;
            currentX = currentX + intervalX;
            currentY = currentY + intervalY;
            postInvalidateDelayed(20);
        } else {
            isIntervalTime = false;
        }

    }

}
