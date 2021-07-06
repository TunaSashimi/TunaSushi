package com.tunasushi.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import com.tunasushi.R;

/**
 * @author TunaSashimi
 * @date 2020-02-12 18:03
 * @Copyright 2020 TunaSashimi. All rights reserved.
 * @Description
 */
public class TSector extends TView {
    private Paint sectorPaint;//画笔
    private Paint sectorPaintShader;//彩色画笔
    private Paint sectorPaintBitmap;//图片画笔
    //半径
    private int radius;
    //外圆弧的宽度
    private float sectorArcWidthOut;
    //内部大圆弧的宽度
    private float sectorArcWidthIn;
    //两圆弧中间间隔距离
    private float sectorSpaceWidth;
    private float sectorDragRadius;

    private int yellowColor;
    private int pinkRedColor;
    private int redColor;
    private int deepRedColor;
    private int grayColor;
    //间隔的角度
    private double spaceAngle = 22.5;
    //两条圆弧的起始角度，起始角度控制半圆开口的大小，数值越小开口越大，数值越大开口越小
    private double floatAngel = 50;
    //时刻变化的Angel
    private double mAngel;
    //内弧半径
    private float insideArcRadius;
    private double aimPercent = 0;
    private float[] pos;                // 当前点的实际位置
    private float[] tan;                // 当前点的tangent值,用于计算图片所需旋转的角度
    private RectF insideArea;           //内圆的矩形
    // 动效过程监听器
    private ValueAnimator.AnimatorUpdateListener mUpdateListener;
    private Animator.AnimatorListener mAnimatorListener;
    // 过程动画
    private ValueAnimator mValueAnimator;
    // 用于控制动画状态转换
    private Handler mAnimatorHandler;
    // 默认的动效周期 2s
    private int defaultDuration = 0;

    public TSector(Context context) {
        this(context, null);
    }

    public TSector(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TSector(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        tag = TSector.class.getSimpleName();

        pos = new float[2];
        tan = new float[2];

        sectorPaint = new Paint();
        sectorPaint.setStyle(Paint.Style.STROKE);
        sectorPaint.setAntiAlias(true);

        sectorPaintShader = new Paint();

        sectorPaintBitmap = new Paint();
        sectorPaintBitmap.setStyle(Paint.Style.FILL);
        sectorPaintBitmap.setAntiAlias(true);

        yellowColor = 0xff4acc75;
        pinkRedColor = 0xff3597d0;
        redColor = 0xff3597d0;
        deepRedColor = 0xff3597d0;
        grayColor = 0xfff0f0f0;

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TSector);

        sectorArcWidthOut = typedArray.getDimension(R.styleable.TSector_sectorArcWidthOut, 0);
        sectorArcWidthIn = typedArray.getDimension(R.styleable.TSector_sectorArcWidthIn, 0);
        sectorSpaceWidth = typedArray.getDimension(R.styleable.TSector_sectorSpaceWidth, 0);
        sectorDragRadius = typedArray.getDimension(R.styleable.TSector_sectorDragRadius, 0);

        typedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        radius = height >> 1;
        insideArcRadius = radius - sectorDragRadius - sectorSpaceWidth;//内弧半径

        //画线的底色
        sectorPaint.setColor(grayColor);
        sectorPaint.setStrokeWidth(sectorArcWidthOut);//sectorArcWidthOut
        sectorPaint.setStyle(Paint.Style.STROKE);
        sectorPaint.setStrokeCap(Paint.Cap.ROUND);//设置为圆角
        sectorPaint.setAntiAlias(true);

        //绘制里层大宽度弧形底色
        sectorPaint.setStrokeWidth(sectorArcWidthIn);
        sectorPaint.setStyle(Paint.Style.STROKE);
        canvas.drawArc(new RectF((width >> 1) - insideArcRadius, radius - insideArcRadius, (width >> 1) + insideArcRadius, radius + insideArcRadius),
                (float) (180 - floatAngel),
                (float) (180 + 2 * floatAngel), false, sectorPaint);

        /***
         * 4个色值由浅到深分别是 ffd200 ff5656 fa4040 f60157
         * 等级划分：0-20%    21-60%    61-90%    90以上
         * 绘制颜色线条
         * 主要用到Xfermode的SRC_ATOP显示上层绘制
         * setStrokeCap   Paint.Cap.ROUND设置为圆角矩形
         */
        double roateAngel = mAngel * 0.01 * 225;
        sectorPaintShader.setColor(yellowColor);
        sectorPaintShader.setStrokeCap(Paint.Cap.ROUND);
        sectorPaintShader.setAntiAlias(true);
        sectorPaintShader.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));//sectorPaintShader.setColor(yellowColor);
        if (aimPercent >= 0 && aimPercent <= 20) {
        } else if (aimPercent > 20 && aimPercent <= 60) {
            int colorSweep[] = {yellowColor, pinkRedColor};
            float position[] = {0.5f, 0.7f};
            SweepGradient sweepGradient = new SweepGradient(width / 2, radius, colorSweep, position);
            sectorPaintShader.setShader(sweepGradient);
        } else if (aimPercent > 60 && aimPercent <= 90) {
            int colorSweep[] = {redColor, yellowColor, yellowColor, pinkRedColor, redColor};
            float position[] = {0.25f, 0.35f, 0.5f, 0.7f, 0.8f};
            SweepGradient sweepGradient = new SweepGradient(width / 2, radius, colorSweep, position);
            sectorPaintShader.setShader(sweepGradient);
        } else if (aimPercent > 90) {
            int colorSweep[] = {deepRedColor, yellowColor, yellowColor, pinkRedColor, redColor, deepRedColor};
            float position[] = {0.2f, 0.4f, 0.5f, 0.7f, 0.9f, 1.0f};
            SweepGradient sweepGradient = new SweepGradient(width / 2, radius, colorSweep, position);
            sectorPaintShader.setShader(sweepGradient);
        }
        if (aimPercent <= 10) {//目的是为了
            drawInsideArc((float) (180 - floatAngel), (float) roateAngel, canvas, yellowColor);//在这里改变指示圆点的颜色

        } else if (aimPercent > 10 && aimPercent <= 20) {
            drawInsideArc((float) (180 - floatAngel), (float) roateAngel, canvas, yellowColor);//在这里改变指示圆点的颜色

        } else if (aimPercent > 20 && aimPercent <= 60) {
            drawInsideArc((float) (180 - floatAngel), (float) (roateAngel - (spaceAngle - floatAngel)), canvas, pinkRedColor);

        } else if (aimPercent > 60 && aimPercent <= 90) {
            drawInsideArc((float) (180 - floatAngel), (float) (roateAngel - (spaceAngle - floatAngel)), canvas, redColor);

        } else {
            drawInsideArc((float) (180 - floatAngel), (float) (roateAngel - 2 * (spaceAngle - floatAngel)), canvas, deepRedColor);
        }
    }

    /***
     * 画内部圆环渐变
     * @param formDegree 起始角度
     * @param toDegree 旋转角度
     * @param canvas 画布
     */
    private void drawInsideArc(float formDegree, float toDegree, Canvas canvas, int color) {
        sectorPaintShader.setStrokeWidth(sectorArcWidthIn);
        sectorPaintShader.setStyle(Paint.Style.STROKE);
        //内弧半径
        insideArea = new RectF((width >> 1) - insideArcRadius, radius - insideArcRadius, (width >> 1) + insideArcRadius, radius + insideArcRadius);

        canvas.drawArc(insideArea, formDegree, toDegree, false, sectorPaintShader);
        if (toDegree != 0) {
            Path orbit = new Path();
            //通过Path类画一个90度（180—270）的内切圆弧路径
            orbit.addArc(insideArea, formDegree, toDegree);
            // 创建 PathMeasure
            PathMeasure measure = new PathMeasure(orbit, false);
            measure.getPosTan(measure.getLength() * 1, pos, tan);
            canvas.drawPath(orbit, sectorPaintShader);//绘制外层的线条
            sectorPaintBitmap.setColor(color);
            //绘制实心小圆圈
            canvas.drawCircle(pos[0], pos[1], 30, sectorPaintBitmap);
        }
    }

    //设置角度变化，刷新界面
    public void setProgress(double aimPercent) {
        //
        if (aimPercent < 1) {
            aimPercent = 1;
        } else if (aimPercent > 99) {
            aimPercent = 100;
        }
        //
        this.aimPercent = aimPercent;

        //
        final double finalAimPercent = aimPercent;
        mUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mAngel = (float) animation.getAnimatedValue() * finalAimPercent;
                if (mAngel >= 0 && mAngel <= 20) {
                    sectorPaintBitmap.setColor(yellowColor);
                } else if (mAngel > 20 && mAngel <= 60) {
                    sectorPaintBitmap.setColor(pinkRedColor);
                } else if (mAngel > 60 && mAngel <= 90) {
                    sectorPaintBitmap.setColor(redColor);
                } else {
                    sectorPaintBitmap.setColor(deepRedColor);
                }
                invalidate();
            }
        };

        mAnimatorListener = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mAnimatorHandler.sendEmptyMessage(0);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        };

        //
        mAnimatorHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0:
                        mValueAnimator.removeAllUpdateListeners();
                        mValueAnimator.removeAllListeners();
                        break;
                    case 1:
                        invalidate();
                        break;
                }
            }
        };

        //
        mValueAnimator = ValueAnimator.ofFloat(0, 1).setDuration(defaultDuration);//设置动画时间，这里设置为0，则看不出动画执行的过程
        mValueAnimator.addUpdateListener(mUpdateListener);
        mValueAnimator.addListener(mAnimatorListener);

        mValueAnimator.start();
    }
}
