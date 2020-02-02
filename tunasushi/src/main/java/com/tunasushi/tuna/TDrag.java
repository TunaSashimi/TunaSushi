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
import static com.tunasushi.tool.PaintTool.paint;
import static com.tunasushi.tool.PathTool.initPath;
import static com.tunasushi.tool.PathTool.path;


/**
 * @author Tunasashimi
 * @date 10/30/15 16:53
 * @Copyright 2015 Sashimi. All rights reserved.
 * @Description
 */
public class TDrag extends TView {

    private String[] dragTextValueArray;
    private int dragAngle;
    private int dragFillColor;
    private float dragStrokeWidth;
    private int dragStrokeColor;
    private float dragTextSize;
    private int dragTextColorNormal;

    private int dragBallBackgroundNormal, dragBallBackgroundPress;

    private float dragBallStrokeWidth;
    private int dragBallStrokeColorNormal, dragBallStrokeColorPress;

    private float dragBallRadiusNormal, dragBallRadiusPress;

    private int dragBallTextColor;
    private Bitmap dragBallBitmapSrcPress;

    private RectF[] dragCircleRectFArray;
    private float[] dragCircleCentreXArray;

    private float dragCurrentX;
    private int dragCurrentIndex;

    // some draw variables
    private float dragCircleNormalDiameter;

    public TDrag(Context context) {
        this(context, null);
    }

    public TDrag(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TDrag(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        Tag = TDrag.class.getSimpleName();

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TDrag);

        int dragTextValueArrayId = typedArray.getResourceId(R.styleable.TDrag_dragTextValueArray, -1);
        if (dragTextValueArrayId != -1) {
            if (isInEditMode()) {
                dragTextValueArray = new String[]{"0", "1", "2", "5", "10"};
            } else {
                dragTextValueArray = typedArray.getResources().getStringArray(dragTextValueArrayId);
            }
            total = dragTextValueArray.length;
            if (total < 2) {
                throw new IndexOutOfBoundsException("The content attribute dragTextValueArray length must be at least 2");
            } else {
                dragCircleCentreXArray = new float[total];
                dragCircleRectFArray = new RectF[total];
                for (int i = 0; i < total; i++) {
                    dragCircleRectFArray[i] = new RectF();
                }
            }

        } else {
            throw new IllegalArgumentException("The content attribute require a property named dragTextValueArray");
        }

        dragCurrentIndex = typedArray.getInt(R.styleable.TDrag_dragCurrentIndex, -1);
        if (dragCurrentIndex >= total) {
            throw new IndexOutOfBoundsException("The content attribute dragCurrentIndex length must be smaller than the tunaDragArray length");
        }

        dragAngle = typedArray.getInt(R.styleable.TDrag_dragAngle, 30);
        dragFillColor = typedArray.getColor(R.styleable.TDrag_dragFillColor, Color.TRANSPARENT);
        dragStrokeWidth = typedArray.getDimension(R.styleable.TDrag_dragStrokeWidth, 0);
        dragStrokeColor = typedArray.getColor(R.styleable.TDrag_dragStrokeColor, Color.TRANSPARENT);
        dragTextSize = typedArray.getDimension(R.styleable.TDrag_dragTextSize, 18);
        dragTextColorNormal = typedArray.getColor(R.styleable.TDrag_dragTextColorNormal, Color.TRANSPARENT);

        dragBallBackgroundNormal = typedArray.getColor(R.styleable.TDrag_dragBallBackgroundNormal, Color.TRANSPARENT);
        dragBallBackgroundPress = typedArray.getColor(R.styleable.TDrag_dragBallBackgroundPress, dragBallBackgroundNormal);

        dragBallStrokeWidth = typedArray.getDimension(R.styleable.TDrag_dragBallStrokeWidth, 0);
        dragBallStrokeColorNormal = typedArray.getColor(R.styleable.TDrag_dragBallStrokeColorNormal, Color.TRANSPARENT);
        dragBallStrokeColorPress = typedArray.getColor(R.styleable.TDrag_dragBallStrokeColorPress, dragBallStrokeColorNormal);

        dragBallRadiusNormal = typedArray.getDimension(R.styleable.TDrag_dragBallRadiusNormal, 0);
        dragBallRadiusPress = typedArray.getDimension(R.styleable.TDrag_dragBallRadiusPress, dragBallRadiusNormal);

        dragBallTextColor = typedArray.getColor(R.styleable.TDrag_dragBallTextColor, Color.TRANSPARENT);

        int dragBallBitmapSrcPressId = typedArray.getResourceId(R.styleable.TDrag_dragBallBitmapSrcPress, -1);
        if (dragBallBitmapSrcPressId != -1) {
            dragBallBitmapSrcPress = BitmapFactory.decodeResource(getResources(), dragBallBitmapSrcPressId);
        }

        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        typedArray.recycle();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        /**
         * attantion that the bottom of the circle radius equals the small
         * dragBallRadiusNormal!
         */
        dragCircleNormalDiameter = dragBallRadiusNormal * 2;
        surplus = width - dragCircleNormalDiameter * total - dragStrokeWidth;
        share = surplus / (total - 1);

        // The view must be greater than the height multiplied by the
        // tunaDragArray length
        if (share <= 0) {
            throw new IndexOutOfBoundsException("The view must be greater than the height multiplied by the dragArray length");
        }

        // first start draw bottom of the picture:

        // dy=hypotenuse*sin(dragAngle*0.5f)
        dy = (float) (dragCircleNormalDiameter * 0.5f * Math.sin(Math.toRadians(dragAngle * 0.5f)));
        // dx=hypotenuse-hypotenuse*cos(dragAngle*0.5f)
        dx = (float) (dragCircleNormalDiameter * 0.5f - dragCircleNormalDiameter * 0.5f * Math.cos(Math.toRadians(dragAngle * 0.5f)));

        // dragCircleCentreXArray avoid generating with new
        for (int i = 0; i < total; i++) {
            dragCircleCentreXArray[i] = (dragCircleNormalDiameter + share) * i + dragCircleNormalDiameter * 0.5f + dragStrokeWidth * 0.5f;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 移上去按下去边框没了
        // rectArray avoid generating with new
        for (int i = 0; i < total; i++) {
            dragCircleRectFArray[i].set((dragCircleNormalDiameter + share) * i + dragStrokeWidth * 0.5f, (height - dragCircleNormalDiameter) * 0.5f,
                    (dragCircleNormalDiameter + share) * i + dragCircleNormalDiameter + dragStrokeWidth * 0.5f, (height - dragCircleNormalDiameter) * 0.5f + dragCircleNormalDiameter);
        }

        initPath();

        for (int i = 0; i < total; i++) {
            if (i == 0) {
                path.addArc(dragCircleRectFArray[i], dragAngle * 0.5f, 360 - dragAngle);
            } else {
                path.lineTo(dragCircleCentreXArray[i] - dragCircleNormalDiameter * 0.5f + dx, (height >> 1) - dy);
                path.addArc(dragCircleRectFArray[i], 180 + dragAngle * 0.5f, i != total - 1 ? 180 - dragAngle : 360 - dragAngle);
            }
        }

        for (int i = total - 2; i >= 0; i--) {
            path.lineTo(dragCircleCentreXArray[i] + dragCircleNormalDiameter * 0.5f - dx, (height >> 1) + dy);
            if (i != 0) {
                path.addArc(dragCircleRectFArray[i], dragAngle * 0.5f, 180 - dragAngle);
            }
        }

        // draw bottom pitcure
        canvas.drawPath(path, PaintTool.initPaint(Paint.Style.STROKE, dragStrokeColor, dragStrokeWidth));

        // draw bottom filling
        PaintTool.initPaint(Paint.Style.FILL, dragFillColor);

        for (int i = 0; i < total; i++) {
            canvas.drawCircle(dragCircleCentreXArray[i], height >> 1, dragCircleNormalDiameter * 0.5f - dragBallStrokeWidth, paint);

            if (i != total - 1) {
                // Normally rectangle from the top left corner should be
                // dragCircleCentreXArray [i] + circleDiameter / 2 ,
                // Considering the circles and rectangles are not well matched
                // and another reason from float to int when deleting the
                // decimal, so the need to subtract about fivefold dx
                // adjustment (),
                // To the left :dragCircleCentreXArray [i] +
                // circleDiameter*0.5f-dx * 5 ,
                // To the right:dragCircleCentreXArray [i] + circleDiameter
                // / 2 + share + dx * 5

                dragCircleRectFArray[0].set(dragCircleCentreXArray[i] + dragCircleNormalDiameter * 0.5f - dx * 5, (height >> 1) - dy, dragCircleCentreXArray[i]
                        + dragCircleNormalDiameter * 0.5f + share + dx * 5, (height >> 1) + dy);

                canvas.drawRect(dragCircleRectFArray[0], paint);
            }
        }

        // draw bottom text

        PaintTool.initTextPaint(Paint.Style.FILL, dragTextColorNormal, dragTextSize, Align.CENTER);
        for (int i = 0; i < total; i++) {
            drawText(canvas, dragTextValueArray[i], width, dragCircleCentreXArray[i], height >> 1, 0, 0, paint);
        }

        // draw response
        if (press) {
            // If the incoming the background painted directly
            if (dragBallBitmapSrcPress != null) {

                dragCircleRectFArray[0].set(dragCurrentX - dragBallRadiusPress, (height >> 1) - dragBallRadiusPress,
                        dragCurrentX + dragBallRadiusPress, (height >> 1) + dragBallRadiusPress);

                canvas.drawBitmap(dragBallBitmapSrcPress, null, dragCircleRectFArray[0], paint);

                // No not incoming the background draw the default texture
            } else {
                paint.setColor(dragBallStrokeColorPress);
                canvas.drawCircle(dragCurrentX, height >> 1, dragBallRadiusPress, paint);
                paint.setColor(dragBallBackgroundPress);
                canvas.drawCircle(dragCurrentX, height >> 1, dragBallRadiusPress - dragBallStrokeWidth, paint);

                // draw veins overlapping round

                float bezierOvalX = dragCurrentX;
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

                PaintTool.initPaint(Paint.Style.STROKE, dragStrokeColor);

                for (int i = 0; (deviationOvalXOffset + controlOvalYOffset) * i < dragBallRadiusPress; i++) {
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

            drawText(canvas, dragTextValueArray[dragCurrentIndex], width, dragCurrentX, height >> 1, 0, 0,
                    PaintTool.initTextPaint(Paint.Style.FILL, dragBallTextColor, dragTextSize, Align.CENTER));

        } else {
            float adjuestX = dragCircleCentreXArray[dragCurrentIndex];

            // draw response circle
            canvas.drawCircle(adjuestX, height >> 1, dragBallRadiusNormal, PaintTool.initPaint(Paint.Style.FILL, dragBallStrokeColorNormal));
            canvas.drawCircle(adjuestX, height >> 1, dragBallRadiusNormal - dragBallStrokeWidth, PaintTool.initPaint(Paint.Style.FILL, dragBallBackgroundNormal));

            // draw response text
            drawText(canvas, dragTextValueArray[dragCurrentIndex], width, adjuestX, height >> 1, 0, 0,
                    PaintTool.initTextPaint(Paint.Style.FILL, dragBallTextColor, dragTextSize, Align.CENTER));
        }
    }

    public float getDragCurrentX() {
        return dragCurrentX;
    }

    public void setDragCurrentX(float dragCurrentX) {
        setDragCurrentX(TypedValue.COMPLEX_UNIT_DIP, dragCurrentX);
    }

    public void setDragCurrentX(int unit, float dragCurrentX) {
        Context c = getContext();
        Resources r;
        if (c == null)
            r = Resources.getSystem();
        else
            r = c.getResources();
        setDragCurrentXRaw(applyDimension(unit, dragCurrentX, r.getDisplayMetrics()));
    }

    private void setDragCurrentXRaw(float dragCurrentX) {
        if (this.dragCurrentX != dragCurrentX) {
            this.dragCurrentX = dragCurrentX;
            // calculate index
            float minDistence = width;
            // From 0 to judge one by one, if the distance farther on the end of
            // the cycle
            for (int i = 0; i < total; i++) {
                float circleCentreDistance = Math.abs(dragCurrentX - dragCircleCentreXArray[i]);
                if (circleCentreDistance < minDistence) {
                    dragCurrentIndex = i;
                    minDistence = circleCentreDistance;
                } else {
                    break;
                }
            }
        }
    }

    public int getDragCurrentIndex() {
        return dragCurrentIndex;
    }

    public void setDragCurrentIndex(int dragCurrentIndex) {
        if (dragCurrentIndex < 0 || dragCurrentIndex >= total) {
            throw new IndexOutOfBoundsException("The content attribute dragCurrentIndex length must be not less than zero and smaller than the dragArray length");
        }
        this.dragCurrentIndex = dragCurrentIndex;
        invalidate();
    }
}
