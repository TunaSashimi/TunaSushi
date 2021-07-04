package com.tunasushi.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;

import com.tunasushi.R;


/**
 * @author TunaSashimi
 * @date 2020-02-12 20:15
 * @Copyright 2020 TunaSashimi. All rights reserved.
 * @Description
 */
public class TPentagram extends TView {
    private final static float DEGREE = 36; //Pentagram angle
    private float pentagramThick;
    private int pentagramColor;

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

        //Length of the middle pentagon
        pentagramThick = typedArray.getDimension(R.styleable.TPentagram_pentagramThick, 0);
        pentagramColor = typedArray.getColor(R.styleable.TPentagram_pentagramColor, Color.TRANSPARENT);

        typedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        Path path = new Path();

        paint.setAntiAlias(true);

        float radian = (float) Math.toRadians(DEGREE);//36

        float radius_in = (float) (pentagramThick * Math.sin(radian / 2) / Math.cos(radian)); //Radius of inner pentagon

        path.moveTo((float) (pentagramThick * Math.cos(radian / 2)), 0);//Top point of the pentagram

        path.lineTo((float) (pentagramThick * Math.cos(radian / 2) + radius_in * Math.sin(radian)), (float) (pentagramThick - pentagramThick * Math.sin(radian / 2)));//Right of the pentagram

        path.lineTo((float) (pentagramThick * Math.cos(radian / 2) * 2), (float) (pentagramThick - pentagramThick * Math.sin(radian / 2)));//The far right of the pentagram

        path.lineTo((float) (pentagramThick * Math.cos(radian / 2) + radius_in * Math.cos(radian / 2)), (float) (pentagramThick + radius_in * Math.sin(radian / 2)));//The far right of the pentagram

        path.lineTo((float) (pentagramThick * Math.cos(radian / 2) + pentagramThick * Math.sin(radian)), (float) (pentagramThick + pentagramThick * Math.cos(radian)));//Bottom right of pentagram

        path.lineTo((float) (pentagramThick * Math.cos(radian / 2)), (pentagramThick + radius_in));

        path.lineTo((float) (pentagramThick * Math.cos(radian / 2) - pentagramThick * Math.sin(radian)), (float) (pentagramThick + pentagramThick * Math.cos(radian)));

        path.lineTo((float) (pentagramThick * Math.cos(radian / 2) - radius_in * Math.cos(radian / 2)), (float) (pentagramThick + radius_in * Math.sin(radian / 2)));

        path.lineTo(0, (float) (pentagramThick - pentagramThick * Math.sin(radian / 2)));

        path.lineTo((float) (pentagramThick * Math.cos(radian / 2) - radius_in * Math.sin(radian)), (float) (pentagramThick - pentagramThick * Math.sin(radian / 2)));

        path.close();

        paint.setColor(this.pentagramColor);
        canvas.drawPath(path, paint);
    }
}
