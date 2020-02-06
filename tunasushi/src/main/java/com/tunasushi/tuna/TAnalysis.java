package com.tunasushi.tuna;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import com.tunasushi.tool.PaintTool;

import static com.tunasushi.tool.PaintTool.paint;
import static com.tunasushi.tool.PathTool.initPathMoveTo;
import static com.tunasushi.tool.PathTool.path;

/**
 * @author Tunasashimi
 * @date 10/30/15 16:48
 * @Copyright 2015 Sashimi. All rights reserved.
 * @Description
 */
public class TAnalysis extends TView {
    private int analysisControlX, analysisControlY;

    public int getAnalysisControlX() {
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

        Tag = TAnalysis.class.getSimpleName();

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        //父类中的onMeasure会initPaint导致构造方法中的paint失效
        PaintTool.initPaint(Paint.Style.FILL, Color.RED);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.YELLOW);

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
