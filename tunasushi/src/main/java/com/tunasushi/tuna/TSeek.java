package com.tunasushi.tuna;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.tuna.R;
import com.tunasushi.tool.PaintTool;

import static com.tunasushi.tool.DeviceTool.applyDimension;
import static com.tunasushi.tool.DrawTool.drawText;
import static com.tunasushi.tool.PaintTool.paint;
import static com.tunasushi.tool.PathTool.initPath;
import static com.tunasushi.tool.PathTool.path;

/**
 * @author Tunasashimi
 * @date 10/30/15 16:53
 * @Copyright 2015 Sashimi. All rights reserved.
 * @Description
 */
public class TSeek extends TView {

    private String[] seekTextValueArray;
    private int seekAngle;
    private int seekFillColor;
    private float seekStrokeWidth;
    private int seekStrokeColor;
    private float seekTextSize;
    private int seekTextColorNormal;

    private int seekDragBackgroundNormal, seekDragBackgroundPress;

    private float seekDragStrokeWidth;
    private int seekDragStrokeColorNormal, seekDragStrokeColorPress;

    private float seekDragRadiusNormal, seekDragRadiusPress;

    private int seekDragTextColor;
    private Bitmap seekDragBitmapSrcPress;

    private RectF[] seekCircleRectFArray;
    private float[] seekCircleCentreXArray;

    private float seekCurrentX;
    private int seekCurrentIndex;

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

        Tag = TSeek.class.getSimpleName();

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TSeek);

        int seekTextValueArrayId = typedArray.getResourceId(R.styleable.TSeek_seekTextValueArray, -1);
        if (seekTextValueArrayId != -1) {
            if (isInEditMode()) {
                seekTextValueArray = new String[]{"0", "1", "2", "5", "10"};
            } else {
                seekTextValueArray = typedArray.getResources().getStringArray(seekTextValueArrayId);
            }
            total = seekTextValueArray.length;
            if (total < 2) {
                throw new IndexOutOfBoundsException("The content attribute seekTextValueArray length must be at least 2");
            } else {
                seekCircleCentreXArray = new float[total];
                seekCircleRectFArray = new RectF[total];
                for (int i = 0; i < total; i++) {
                    seekCircleRectFArray[i] = new RectF();
                }
            }

        } else {
            throw new IllegalArgumentException("The content attribute require a property named seekTextValueArray");
        }

        seekCurrentIndex = typedArray.getInt(R.styleable.TSeek_seekCurrentIndex, -1);
        if (seekCurrentIndex >= total) {
            throw new IndexOutOfBoundsException("The content attribute seekCurrentIndex length must be smaller than the seekTextValueArray length");
        }

        seekAngle = typedArray.getInt(R.styleable.TSeek_seekAngle, 30);
        seekFillColor = typedArray.getColor(R.styleable.TSeek_seekFillColor, Color.TRANSPARENT);
        seekStrokeWidth = typedArray.getDimension(R.styleable.TSeek_seekStrokeWidth, 0);
        seekStrokeColor = typedArray.getColor(R.styleable.TSeek_seekStrokeColor, Color.TRANSPARENT);
        seekTextSize = typedArray.getDimension(R.styleable.TSeek_seekTextSize, 18);
        seekTextColorNormal = typedArray.getColor(R.styleable.TSeek_seekTextColorNormal, Color.TRANSPARENT);

        seekDragBackgroundNormal = typedArray.getColor(R.styleable.TSeek_seekDragBackgroundNormal, Color.TRANSPARENT);
        seekDragBackgroundPress = typedArray.getColor(R.styleable.TSeek_seekDragBackgroundPress, seekDragBackgroundNormal);

        seekDragStrokeWidth = typedArray.getDimension(R.styleable.TSeek_seekDragStrokeWidth, 0);
        seekDragStrokeColorNormal = typedArray.getColor(R.styleable.TSeek_seekDragStrokeColorNormal, Color.TRANSPARENT);
        seekDragStrokeColorPress = typedArray.getColor(R.styleable.TSeek_seekDragStrokeColorPress, seekDragStrokeColorNormal);

        seekDragRadiusNormal = typedArray.getDimension(R.styleable.TSeek_seekDragRadiusNormal, 0);
        seekDragRadiusPress = typedArray.getDimension(R.styleable.TSeek_seekDragRadiusPress, seekDragRadiusNormal);

        seekDragTextColor = typedArray.getColor(R.styleable.TSeek_seekDragTextColor, Color.TRANSPARENT);

        int seekDragBitmapSrcPressId = typedArray.getResourceId(R.styleable.TSeek_seekDragBitmapSrcPress, -1);
        if (seekDragBitmapSrcPressId != -1) {
            seekDragBitmapSrcPress = BitmapFactory.decodeResource(getResources(), seekDragBitmapSrcPressId);
        }

        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        typedArray.recycle();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        /**
         * attantion that the bottom of the circle radius equals the small
         * seekDragRadiusNormal!
         */
        seekCircleNormalDiameter = seekDragRadiusNormal * 2;
        surplus = width - seekCircleNormalDiameter * total - seekStrokeWidth;
        share = surplus / (total - 1);

        // The view must be greater than the height multiplied by the
        // seekArray length
        if (share <= 0) {
            throw new IndexOutOfBoundsException("The view must be greater than the height multiplied by the seekArray length");
        }

        // first start draw bottom of the picture:

        // dy=hypotenuse*sin(seekAngle*0.5f)
        dy = (float) (seekCircleNormalDiameter * 0.5f * Math.sin(Math.toRadians(seekAngle * 0.5f)));
        // dx=hypotenuse-hypotenuse*cos(seekAngle*0.5f)
        dx = (float) (seekCircleNormalDiameter * 0.5f - seekCircleNormalDiameter * 0.5f * Math.cos(Math.toRadians(seekAngle * 0.5f)));

        // seekCircleCentreXArray avoid generating with new
        for (int i = 0; i < total; i++) {
            seekCircleCentreXArray[i] = (seekCircleNormalDiameter + share) * i + seekCircleNormalDiameter * 0.5f + seekStrokeWidth * 0.5f;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < total; i++) {
            seekCircleRectFArray[i].set((seekCircleNormalDiameter + share) * i + seekStrokeWidth * 0.5f, (height - seekCircleNormalDiameter) * 0.5f,
                    (seekCircleNormalDiameter + share) * i + seekCircleNormalDiameter + seekStrokeWidth * 0.5f, (height - seekCircleNormalDiameter) * 0.5f + seekCircleNormalDiameter);
        }

        initPath();

        for (int i = 0; i < total; i++) {
            if (i == 0) {
                path.addArc(seekCircleRectFArray[i], seekAngle * 0.5f, 360 - seekAngle);
            } else {
                path.lineTo(seekCircleCentreXArray[i] - seekCircleNormalDiameter * 0.5f + dx, (height >> 1) - dy);
                path.addArc(seekCircleRectFArray[i], 180 + seekAngle * 0.5f, i != total - 1 ? 180 - seekAngle : 360 - seekAngle);
            }
        }

        for (int i = total - 2; i >= 0; i--) {
            path.lineTo(seekCircleCentreXArray[i] + seekCircleNormalDiameter * 0.5f - dx, (height >> 1) + dy);
            if (i != 0) {
                path.addArc(seekCircleRectFArray[i], seekAngle * 0.5f, 180 - seekAngle);
            }
        }

        // draw bottom pitcure
        canvas.drawPath(path, PaintTool.initPaint(Paint.Style.STROKE, seekStrokeColor, seekStrokeWidth));

        // draw bottom filling
        PaintTool.initPaint(Paint.Style.FILL, seekFillColor);

        for (int i = 0; i < total; i++) {
            canvas.drawCircle(seekCircleCentreXArray[i], height >> 1, seekCircleNormalDiameter * 0.5f - seekDragStrokeWidth, paint);

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

                seekCircleRectFArray[0].set(seekCircleCentreXArray[i] + seekCircleNormalDiameter * 0.5f - dx * 5, (height >> 1) - dy, seekCircleCentreXArray[i]
                        + seekCircleNormalDiameter * 0.5f + share + dx * 5, (height >> 1) + dy);

                canvas.drawRect(seekCircleRectFArray[0], paint);
            }
        }

        // draw bottom text

        PaintTool.initTextPaint(Paint.Style.FILL, seekTextColorNormal, seekTextSize, Align.CENTER);
        for (int i = 0; i < total; i++) {
            drawText(canvas, seekTextValueArray[i], width, seekCircleCentreXArray[i], height >> 1, 0, 0, paint);
        }

        // draw response
        if (press) {
            // If the incoming the background painted directly
            if (seekDragBitmapSrcPress != null) {

                seekCircleRectFArray[0].set(seekCurrentX - seekDragRadiusPress, (height >> 1) - seekDragRadiusPress,
                        seekCurrentX + seekDragRadiusPress, (height >> 1) + seekDragRadiusPress);

                canvas.drawBitmap(seekDragBitmapSrcPress, null, seekCircleRectFArray[0], paint);

                // No not incoming the background draw the default texture
            } else {
                paint.setColor(seekDragStrokeColorPress);
                canvas.drawCircle(seekCurrentX, height >> 1, seekDragRadiusPress, paint);
                paint.setColor(seekDragBackgroundPress);
                canvas.drawCircle(seekCurrentX, height >> 1, seekDragRadiusPress - seekDragStrokeWidth, paint);

                // draw veins overlapping round

                float bezierOvalX = seekCurrentX;
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

                PaintTool.initPaint(Paint.Style.STROKE, seekStrokeColor);

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

            drawText(canvas, seekTextValueArray[seekCurrentIndex], width, seekCurrentX, height >> 1, 0, 0,
                    PaintTool.initTextPaint(Paint.Style.FILL, seekDragTextColor, seekTextSize, Align.CENTER));

        } else {
            float adjuestX = seekCircleCentreXArray[seekCurrentIndex];

            // draw response circle
            canvas.drawCircle(adjuestX, height >> 1, seekDragRadiusNormal, PaintTool.initPaint(Paint.Style.FILL, seekDragStrokeColorNormal));
            canvas.drawCircle(adjuestX, height >> 1, seekDragRadiusNormal - seekDragStrokeWidth, PaintTool.initPaint(Paint.Style.FILL, seekDragBackgroundNormal));

            // draw response text
            drawText(canvas, seekTextValueArray[seekCurrentIndex], width, adjuestX, height >> 1, 0, 0,
                    PaintTool.initTextPaint(Paint.Style.FILL, seekDragTextColor, seekTextSize, Align.CENTER));
        }
    }

    public float getSeekCurrentX() {
        return seekCurrentX;
    }

    public void setSeekCurrentX(float seekCurrentX) {
        setSeekCurrentX(TypedValue.COMPLEX_UNIT_DIP, seekCurrentX);
    }

    public void setSeekCurrentX(int unit, float seekCurrentX) {
        Context c = getContext();
        Resources r;
        if (c == null)
            r = Resources.getSystem();
        else
            r = c.getResources();
        setSeekCurrentXRaw(applyDimension(unit, seekCurrentX, r.getDisplayMetrics()));
    }

    private void setSeekCurrentXRaw(float seekCurrentX) {
        if (this.seekCurrentX != seekCurrentX) {
            this.seekCurrentX = seekCurrentX;
            // calculate index
            float minDistence = width;
            // From 0 to judge one by one, if the distance farther on the end of
            // the cycle
            for (int i = 0; i < total; i++) {
                float circleCentreDistance = Math.abs(seekCurrentX - seekCircleCentreXArray[i]);
                if (circleCentreDistance < minDistence) {
                    seekCurrentIndex = i;
                    minDistence = circleCentreDistance;
                } else {
                    break;
                }
            }
        }
    }

    public int getSeekCurrentIndex() {
        return seekCurrentIndex;
    }

    public void setSeekCurrentIndex(int seekCurrentIndex) {
        if (seekCurrentIndex < 0 || seekCurrentIndex >= total) {
            throw new IndexOutOfBoundsException("The content attribute seekCurrentIndex length must be not less than zero and smaller than the seekArray length");
        }
        this.seekCurrentIndex = seekCurrentIndex;
        invalidate();
    }
}
