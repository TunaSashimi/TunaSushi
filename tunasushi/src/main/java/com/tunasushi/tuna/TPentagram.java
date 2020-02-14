package com.tunasushi.tuna;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;

import com.tuna.R;

/**
 * @author TunaSashimi
 * @date 2020-02-12 20:15
 * @Copyright 2020 TunaSashimi. All rights reserved.
 * @Description
 */
public class TPentagram extends TView {
    private final static float DEGREE = 36; // 五角星角度
    private float pentagramRadius = 20;
    private int pentagramColor = 0xff0000;

    public TPentagram(Context context) {
        this(context, null);
    }

    public TPentagram(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TPentagram(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        tag = TPentagram.class.getSimpleName();

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TPentagram);

        pentagramColor = typedArray.getColor(R.styleable.TPentagram_pentagramColor, pentagramColor);
        pentagramRadius = typedArray.getFloat(R.styleable.TPentagram_pentagramRadius, pentagramRadius);// 中间五边形的边长 200.0

        typedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        Path path = new Path();

        paint.setAntiAlias(true);

        float radian = (float) Math.toRadians(DEGREE);//36

        float radius_in = (float) (pentagramRadius * Math.sin(radian / 2) / Math.cos(radian)); //内五边形的半径

        path.moveTo((float) (pentagramRadius * Math.cos(radian / 2)), 0);//五角星的上顶点

        path.lineTo((float) (pentagramRadius * Math.cos(radian / 2) + radius_in * Math.sin(radian)), (float) (pentagramRadius - pentagramRadius * Math.sin(radian / 2)));//五角星的右一

        path.lineTo((float) (pentagramRadius * Math.cos(radian / 2) * 2), (float) (pentagramRadius - pentagramRadius * Math.sin(radian / 2)));//五角星的最右

        path.lineTo((float) (pentagramRadius * Math.cos(radian / 2) + radius_in * Math.cos(radian / 2)), (float) (pentagramRadius + radius_in * Math.sin(radian / 2)));//五角星的最右下面一个

        path.lineTo((float) (pentagramRadius * Math.cos(radian / 2) + pentagramRadius * Math.sin(radian)), (float) (pentagramRadius + pentagramRadius * Math.cos(radian)));//五角星的最右下

        path.lineTo((float) (pentagramRadius * Math.cos(radian / 2)), (pentagramRadius + radius_in));

        path.lineTo((float) (pentagramRadius * Math.cos(radian / 2) - pentagramRadius * Math.sin(radian)), (float) (pentagramRadius + pentagramRadius * Math.cos(radian)));

        path.lineTo((float) (pentagramRadius * Math.cos(radian / 2) - radius_in * Math.cos(radian / 2)), (float) (pentagramRadius + radius_in * Math.sin(radian / 2)));

        path.lineTo(0, (float) (pentagramRadius - pentagramRadius * Math.sin(radian / 2)));

        path.lineTo((float) (pentagramRadius * Math.cos(radian / 2) - radius_in * Math.sin(radian)), (float) (pentagramRadius - pentagramRadius * Math.sin(radian / 2)));

        path.close();

        paint.setColor(this.pentagramColor);
        canvas.drawPath(path, paint);
    }
}
