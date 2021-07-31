package com.tunasushi.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

/**
 * @author TunaSashimi
 * @date 2015-10-30 16:48
 * @Copyright 2015 TunaSashimi. All rights reserved.
 * @Description
 */
public class TAnalysis extends TView {
    private int analysisControlX, analysisControlY;

    public int getAnalysisControlX() {

        System.out.println("1.0.04");

        return analysisControlX;
    }

    public void setAnalysisControlX(int analysisControlX) {
        this.analysisControlX = analysisControlX;
    }

    public int getAnalysisControlY() {
        return analysisControlY;
    }

    public void setAnalysisControlY(int analysisControlY) {
        this.analysisControlY = analysisControlY;
    }

    public TAnalysis(Context context) {
        this(context, null);
    }

    public TAnalysis(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TAnalysis(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        tag = TAnalysis.class.getSimpleName();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        initPaint(Paint.Style.FILL, Color.RED);

        int bezierCircleX = 150;
        int bezierCircley = 150;

        int deviationX = 80;
        int deviationtY = 80;

        initPathMoveTo(bezierCircleX - deviationX, bezierCircley);
        path.quadTo((bezierCircleX - deviationX + bezierCircleX) / 2 - analysisControlX, (bezierCircley + bezierCircley + deviationtY) / 2 + analysisControlY, bezierCircleX, bezierCircley
                + deviationtY);
        path.quadTo((bezierCircleX + bezierCircleX + deviationX) / 2 + analysisControlX, (bezierCircley + deviationtY + bezierCircley) / 2 + analysisControlY, bezierCircleX + deviationX,
                bezierCircley);
        path.quadTo((bezierCircleX + deviationX + bezierCircleX) / 2 + analysisControlX, (bezierCircley + bezierCircley - deviationtY) / 2 - analysisControlY, bezierCircleX, bezierCircley
                - deviationtY);
        path.quadTo((bezierCircleX + bezierCircleX - deviationX) / 2 - analysisControlX, (bezierCircley - deviationtY + bezierCircley) / 2 - analysisControlY, bezierCircleX - deviationX,
                bezierCircley);

        canvas.drawPath(path, paint);
    }

    public void setAnalyaiaControlXY(int analysisControlX, int abalysisControlY) {
        this.analysisControlX = analysisControlX;
        this.analysisControlY = abalysisControlY;
        invalidate();
    }
}
