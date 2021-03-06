package com.tunasushi.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import com.tunasushi.R;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 * @author TunaSashimi
 * @date 2015-10-30 16:53
 * @Copyright 2015 TunaSashimi. All rights reserved.
 * @Description
 */
public class TSeek extends TView {

    private String[] seekTextArray;
    private int seekAngle;
    private int seekColorFill;
    private float seekStrokeWidth;
    private int seekStrokeColor;
    private float seekTextSize;
    private int seekTextColor;

    private int seekDragBackgroundNormal, seekDragBackgroundPress;

    private float seekDragStrokeWidth;
    private int seekDragStrokeColorNormal, seekDragStrokeColorPress;

    private float seekDragRadiusNormal, seekDragRadiusPress;

    private float seekDragTextSize;
    private int seekDragTextColor;

    private Bitmap seekDragSrcPress;

    private RectF[] seekCircleRectFArray;

    private int seekIndex;

    public int getSeekIndex() {
        return seekIndex;
    }

    public void setSeekIndex(int seekIndex) {
        if (seekIndex < 0 || seekIndex >= total) {
            throw new IndexOutOfBoundsException("The content attribute seekIndex length must be no less than zero and smaller than the seekArray length");
        }
        this.seekIndex = seekIndex;
        invalidate();
    }

    // some draw variables
    private float seekCircleNormalDiameter;

    public TSeek(Context context) {
        this(context, null);
    }

    public TSeek(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TSeek(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        tag = TSeek.class.getSimpleName();

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TSeek);

        int seekTextArrayId = typedArray.getResourceId(R.styleable.TSeek_seekTextArray, -1);
        if (seekTextArrayId != -1) {
            seekTextArray = typedArray.getResources().getStringArray(seekTextArrayId);
            total = seekTextArray.length;
            if (total < 2) {
                throw new IndexOutOfBoundsException("The content attribute seekTextArray length must be at least 2");
            } else {
                floatArray = new float[total];
                seekCircleRectFArray = new RectF[total];
                for (int i = 0; i < total; i++) {
                    seekCircleRectFArray[i] = new RectF();
                }
            }
        } else {
            throw new IllegalArgumentException("The content attribute require a property named seekTextArray");
        }

        seekIndex = typedArray.getInt(R.styleable.TSeek_seekIndex, -1);
        if (seekIndex >= total) {
            throw new IndexOutOfBoundsException("The content attribute seekIndex length must be smaller than the seekTextArray length");
        }

        seekAngle = typedArray.getInt(R.styleable.TSeek_seekAngle, 30);
        seekColorFill = typedArray.getColor(R.styleable.TSeek_seekColorFill, Color.TRANSPARENT);
        seekStrokeWidth = typedArray.getDimension(R.styleable.TSeek_seekStrokeWidth, 0);
        seekStrokeColor = typedArray.getColor(R.styleable.TSeek_seekStrokeColor, Color.TRANSPARENT);

        seekTextSize = typedArray.getDimension(R.styleable.TSeek_seekTextSize, textSizeDefault);
        seekTextColor = typedArray.getColor(R.styleable.TSeek_seekTextColor,textColorDefault);

        seekDragBackgroundNormal = typedArray.getColor(R.styleable.TSeek_seekDragBackgroundNormal, Color.TRANSPARENT);
        seekDragBackgroundPress = typedArray.getColor(R.styleable.TSeek_seekDragBackgroundPress, seekDragBackgroundNormal);

        seekDragStrokeWidth = typedArray.getDimension(R.styleable.TSeek_seekDragStrokeWidth, 0);
        seekDragStrokeColorNormal = typedArray.getColor(R.styleable.TSeek_seekDragStrokeColorNormal, Color.TRANSPARENT);
        seekDragStrokeColorPress = typedArray.getColor(R.styleable.TSeek_seekDragStrokeColorPress, seekDragStrokeColorNormal);

        seekDragRadiusNormal = typedArray.getDimension(R.styleable.TSeek_seekDragRadiusNormal, 0);
        seekDragRadiusPress = typedArray.getDimension(R.styleable.TSeek_seekDragRadiusPress, seekDragRadiusNormal);

        seekDragTextColor = typedArray.getColor(R.styleable.TSeek_seekDragTextColor, textColorDefault);
        seekDragTextSize = typedArray.getDimension(R.styleable.TSeek_seekDragTextSize, textSizeDefault);

        int seekDragSrcPressId = typedArray.getResourceId(R.styleable.TSeek_seekDragSrcPress, -1);
        if (seekDragSrcPressId != -1) {
            seekDragSrcPress = BitmapFactory.decodeResource(getResources(), seekDragSrcPressId);
        }

        typedArray.recycle();

        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        /**
         * attantion that the bottom of the circle radius equals the small seekDragRadiusNormal!
         */
        seekCircleNormalDiameter = seekDragRadiusNormal * 2;
        surplus = width - seekCircleNormalDiameter * total - seekStrokeWidth;
        share = surplus / (total - 1);

        if (share <= 0) {
            throw new IndexOutOfBoundsException("The content attribute width must be greater than the height multiplied by the seekArray length");
        }

        // first start draw bottom of the picture:

//        dy = hypotenuse * sin(seekAngle * 0.5f)
        dy = (float) (seekCircleNormalDiameter * 0.5f * sin(Math.toRadians(seekAngle * 0.5f)));
//        dx = hypotenuse - hypotenuse * cos(seekAngle * 0.5f)
        dx = (float) (seekCircleNormalDiameter * 0.5f - seekCircleNormalDiameter * 0.5f * cos(Math.toRadians(seekAngle * 0.5f)));

        // seekCircleCentreXArray avoid generating with new
        for (int i = 0; i < total; i++) {
            floatArray[i] = (seekCircleNormalDiameter + share) * i + seekCircleNormalDiameter * 0.5f + seekStrokeWidth * 0.5f;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < total; i++) {
            seekCircleRectFArray[i].set((seekCircleNormalDiameter + share) * i + seekStrokeWidth * 0.5f, (height - seekCircleNormalDiameter) * 0.5f,
                    (seekCircleNormalDiameter + share) * i + seekCircleNormalDiameter + seekStrokeWidth * 0.5f, (height - seekCircleNormalDiameter) * 0.5f + seekCircleNormalDiameter);
        }

        initPath();

        for (int i = 0; i < total; i++) {
            if (i == 0) {
                path.addArc(seekCircleRectFArray[i], seekAngle * 0.5f, 360 - seekAngle);
            } else {
                path.lineTo(floatArray[i] - seekCircleNormalDiameter * 0.5f + dx, (height >> 1) - dy);
                path.addArc(seekCircleRectFArray[i], 180 + seekAngle * 0.5f, i != total - 1 ? 180 - seekAngle : 360 - seekAngle);
            }
        }

        for (int i = total - 2; i >= 0; i--) {
            path.lineTo(floatArray[i] + seekCircleNormalDiameter * 0.5f - dx, (height >> 1) + dy);
            if (i != 0) {
                path.addArc(seekCircleRectFArray[i], seekAngle * 0.5f, 180 - seekAngle);
            }
        }

        // draw bottom stroke
        canvas.drawPath(path, initPaint(Paint.Style.STROKE, seekStrokeColor, seekStrokeWidth));

        initPaint(Paint.Style.FILL, seekColorFill);

        for (int i = 0; i < total; i++) {

            // draw bottom circle
            canvas.drawCircle(floatArray[i], height >> 1, seekCircleNormalDiameter * 0.5f - seekDragStrokeWidth, paint);

            if (i != total - 1) {
                // Normally rectangle from the top left corner should be
                // seekCircleCentreXArray [i] + circleDiameter / 2 ,
                // Considering the circles and rectangles are not well matched
                // and another reason from float to int when deleting the
                // decimal, so the need to subtract about fivefold dx
                // adjustment (),
                // To the left :seekCircleCentreXArray [i] +
                // circleDiameter*0.5f-dx * 5 ,
                // To the right:seekCircleCentreXArray [i] + circleDiameter
                // / 2 + share + dx * 5

                seekCircleRectFArray[0].set(floatArray[i] + seekCircleNormalDiameter * 0.5f - dx * 5, (height >> 1) - dy, floatArray[i]
                        + seekCircleNormalDiameter * 0.5f + share + dx * 5, (height >> 1) + dy);

                // draw bottom rect
                canvas.drawRect(seekCircleRectFArray[0], paint);
            }
        }

        // draw bottom text
        initTextPaint(Paint.Style.FILL, seekTextColor, seekTextSize, Align.CENTER);
        for (int i = 0; i < total; i++) {
            drawText(canvas, seekTextArray[i], width, floatArray[i], height >> 1, 0, 0, paint);
        }

        // draw drag
        if (press) {
            // If the incoming the background painted directly
            if (seekDragSrcPress != null) {

                seekCircleRectFArray[0].set(x - seekDragRadiusPress, (height >> 1) - seekDragRadiusPress,
                        x + seekDragRadiusPress, (height >> 1) + seekDragRadiusPress);

                canvas.drawBitmap(seekDragSrcPress, null, seekCircleRectFArray[0], paint);

                // No not incoming the background draw the default texture
            } else {
                paint.setColor(seekDragStrokeColorPress);
                canvas.drawCircle(x, height >> 1, seekDragRadiusPress, paint);
                paint.setColor(seekDragBackgroundPress);
                canvas.drawCircle(x, height >> 1, seekDragRadiusPress - seekDragStrokeWidth, paint);

                // draw veins overlapping round

                float bezierOvalX = x;
                float bezierOvalY = height >> 1;

                // 左下的图形宽度的一半,高度的一半
                float deviationOvalX = 4;
                float deviationOvalY = 10;
                // 宽度的一半,高度的一般的变动量
                float deviationOvalXOffset = 4;
                float deviationOvalYOffset = 4;

                // 左下的图形拉伸距离的宽度,高度
                float controlOvalX = 4;
                float controlOvalY = 12;
                // 拉伸距离的宽度,高度的变动量
                float controlOvalXOffset = 4;
                float controlOvalYOffset = 4;

                path.reset();

                initPaint(Paint.Style.STROKE, seekStrokeColor);

                for (int i = 0; (deviationOvalXOffset + controlOvalYOffset) * i < seekDragRadiusPress; i++) {
                    if (i == 0) {
                        path.moveTo(bezierOvalX, bezierOvalY);
                    }
                    path.quadTo(bezierOvalX, bezierOvalY - deviationOvalY - controlOvalY, bezierOvalX + deviationOvalX, bezierOvalY - deviationOvalY);// topRight
                    path.quadTo(bezierOvalX + deviationOvalX + controlOvalX, bezierOvalY, bezierOvalX + deviationOvalX, bezierOvalY + deviationOvalY);// bottomRight
                    path.quadTo(bezierOvalX, bezierOvalY + deviationOvalY + controlOvalY, bezierOvalX - deviationOvalX, bezierOvalY + deviationOvalY);// bottomLeft
                    path.quadTo(bezierOvalX - deviationOvalX - controlOvalX, bezierOvalY, bezierOvalX - deviationOvalX, bezierOvalY - deviationOvalY);// topLeft

                    deviationOvalX += deviationOvalXOffset;
                    deviationOvalY += deviationOvalYOffset;

                    controlOvalX += controlOvalXOffset;
                    controlOvalY += controlOvalYOffset;
                }
                canvas.drawPath(path, paint);
            }

            drawText(canvas, seekTextArray[seekIndex], width, x, height >> 1, 0, 0,
                    initTextPaint(Paint.Style.FILL, seekDragTextColor, seekDragTextSize, Align.CENTER));

        } else {
            float adjuestX = floatArray[seekIndex];

            // draw response circle
            canvas.drawCircle(adjuestX, height >> 1, seekDragRadiusNormal, initPaint(Paint.Style.FILL, seekDragStrokeColorNormal));
            canvas.drawCircle(adjuestX, height >> 1, seekDragRadiusNormal - seekDragStrokeWidth, initPaint(Paint.Style.FILL, seekDragBackgroundNormal));

            // draw response text
            drawText(canvas, seekTextArray[seekIndex], width, adjuestX, height >> 1, 0, 0,
                    initTextPaint(Paint.Style.FILL, seekDragTextColor, seekDragTextSize, Align.CENTER));
        }
    }

    @Override
    public void setTouchXYRaw(float touchX, float touchY) {
        x = touchX;
        float distenceMin = width;
        for (int i = 0; i < total; i++) {
            float dx = Math.abs(x - floatArray[i]);
            if (dx < distenceMin) {
                seekIndex = i;
                distenceMin = dx;
            } else {
                break;
            }
        }
        invalidate();
    }
}
