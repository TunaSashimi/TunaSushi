package com.tunasushi.tuna;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import static com.tunasushi.tool.PaintTool.initTunaPaint;
import static com.tunasushi.tool.PaintTool.tunaPaint;

/**
 * @author Tunasashimi
 * @date 10/30/15 16:48
 * @Copyright 2015 TunaSashimi. All rights reserved.
 * @Description
 */
public class TAnalysis extends TView {
    private int controlX, controlY;

    public TAnalysis(Context context) {
        this(context, null);
    }

    public TAnalysis(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TAnalysis(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        tunaTag = TAnalysis.class.getSimpleName();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //父类中的onMeasure会initTunaPaint导致构造方法中的paint失效
        initTunaPaint(Paint.Style.FILL, Color.RED);

        canvas.drawColor(Color.YELLOW);

        int bezierCircleX = 150;
        int bezierCircley = 150;

        int deviationX = 80;
        int deviationtY = 80;

        initTunaPathMoveTo(bezierCircleX - deviationX, bezierCircley);
        tunaPath.quadTo((bezierCircleX - deviationX + bezierCircleX) / 2 - controlX, (bezierCircley + bezierCircley + deviationtY) / 2 + controlY, bezierCircleX, bezierCircley
                + deviationtY);
        tunaPath.quadTo((bezierCircleX + bezierCircleX + deviationX) / 2 + controlX, (bezierCircley + deviationtY + bezierCircley) / 2 + controlY, bezierCircleX + deviationX,
                bezierCircley);
        tunaPath.quadTo((bezierCircleX + deviationX + bezierCircleX) / 2 + controlX, (bezierCircley + bezierCircley - deviationtY) / 2 - controlY, bezierCircleX, bezierCircley
                - deviationtY);
        tunaPath.quadTo((bezierCircleX + bezierCircleX - deviationX) / 2 - controlX, (bezierCircley - deviationtY + bezierCircley) / 2 - controlY, bezierCircleX - deviationX,
                bezierCircley);

        canvas.drawPath(tunaPath, tunaPaint);
    }

    public int getControlX() {
        return controlX;
    }

    public void setControlX(int controlX) {
        this.controlX = controlX;
    }

    public int getControlY() {
        return controlY;
    }

    public void setControlY(int controlY) {
        this.controlY = controlY;
    }

    public void SetControlXandY(int controlX, int controlY) {
        this.controlX = controlX;
        this.controlY = controlY;
        invalidate();
    }
}
