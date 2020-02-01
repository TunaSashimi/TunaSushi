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

import static com.tunasushi.tool.DeviceTool.applyDimension;
import static com.tunasushi.tool.PaintTool.initTunaPaint;
import static com.tunasushi.tool.PaintTool.initTunaTextPaint;
import static com.tunasushi.tool.PaintTool.tunaPaint;


/**
 * @author Tunasashimi
 * @date 10/30/15 16:53
 * @Copyright 2015 TunaSashimi. All rights reserved.
 * @Description
 */
public class TDrag extends TView {

    private String[] tunaDragTextValueArray;
    private int tunaDragAngle;
    private int tunaDragFillColor;
    private float tunaDragStrokeWidth;
    private int tunaDragStrokeColor;
    private float tunaDragTextSize;
    private int tunaDragTextColorNormal;

    private int tunaDragBallBackgroundNormal, tunaDragBallBackgroundPress;

    private float tunaDragBallStrokeWidth;
    private int tunaDragBallStrokeColorNormal, tunaDragBallStrokeColorPress;

    private float tunaDragBallRadiusNormal, tunaDragBallRadiusPress;

    private int tunaDragBallTextColor;
    private Bitmap tunaDragBallBitmapSrcPress;

    private RectF[] tunaDragCircleRectFArray;
    private float[] tunaDragCircleCentreXArray;

    private float tunaDragCurrentX;
    private int tunaDragCurrentIndex;

    // some draw variables
    private float circleNormalDiameter;

    public TDrag(Context context) {
        this(context, null);
    }

    public TDrag(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TDrag(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        tunaTag = TDrag.class.getSimpleName();

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TDrag);

        int tunaDragTextValueArrayId = typedArray.getResourceId(R.styleable.TDrag_dragTextValueArray, -1);
        if (tunaDragTextValueArrayId != -1) {
            if (isInEditMode()) {
                tunaDragTextValueArray = new String[]{"0", "1", "2", "5", "10"};
            } else {
                tunaDragTextValueArray = typedArray.getResources().getStringArray(tunaDragTextValueArrayId);
            }
            tunaTotal = tunaDragTextValueArray.length;
            if (tunaTotal < 2) {
                throw new IndexOutOfBoundsException("The content attribute tunaDragArray length must be at least 2");
            } else {
                tunaDragCircleCentreXArray = new float[tunaTotal];
                tunaDragCircleRectFArray = new RectF[tunaTotal];
                for (int i = 0; i < tunaTotal; i++) {
                    tunaDragCircleRectFArray[i] = new RectF();
                }
            }

        } else {
            throw new IllegalArgumentException("The content attribute require a property named tunaDragArray");
        }

        tunaDragCurrentIndex = typedArray.getInt(R.styleable.TDrag_dragCurrentIndex, -1);
        if (tunaDragCurrentIndex >= tunaTotal) {
            throw new IndexOutOfBoundsException("The content attribute tunaDragCurrentIndex length must be smaller than the tunaDragArray length");
        }

        tunaDragAngle = typedArray.getInt(R.styleable.TDrag_dragAngle, 30);
        tunaDragFillColor = typedArray.getColor(R.styleable.TDrag_dragFillColor, Color.TRANSPARENT);
        tunaDragStrokeWidth = typedArray.getDimension(R.styleable.TDrag_dragStrokeWidth, 0);
        tunaDragStrokeColor = typedArray.getColor(R.styleable.TDrag_dragStrokeColor, Color.TRANSPARENT);
        tunaDragTextSize = typedArray.getDimension(R.styleable.TDrag_dragTextSize, 18);
        tunaDragTextColorNormal = typedArray.getColor(R.styleable.TDrag_dragTextColorNormal, Color.TRANSPARENT);

        tunaDragBallBackgroundNormal = typedArray.getColor(R.styleable.TDrag_dragBallBackgroundNormal, Color.TRANSPARENT);
        tunaDragBallBackgroundPress = typedArray.getColor(R.styleable.TDrag_dragBallBackgroundPress, tunaDragBallBackgroundNormal);

        tunaDragBallStrokeWidth = typedArray.getDimension(R.styleable.TDrag_dragBallStrokeWidth, 0);
        tunaDragBallStrokeColorNormal = typedArray.getColor(R.styleable.TDrag_dragBallStrokeColorNormal, Color.TRANSPARENT);
        tunaDragBallStrokeColorPress = typedArray.getColor(R.styleable.TDrag_dragBallStrokeColorPress, tunaDragBallStrokeColorNormal);

        tunaDragBallRadiusNormal = typedArray.getDimension(R.styleable.TDrag_dragBallRadiusNormal, 0);
        tunaDragBallRadiusPress = typedArray.getDimension(R.styleable.TDrag_dragBallRadiusPress, tunaDragBallRadiusNormal);

        tunaDragBallTextColor = typedArray.getColor(R.styleable.TDrag_dragBallTextColor, Color.TRANSPARENT);

        int tunaDragBallBitmapSrcPressId = typedArray.getResourceId(R.styleable.TDrag_dragBallBitmapSrcPress, -1);
        if (tunaDragBallBitmapSrcPressId != -1) {
            tunaDragBallBitmapSrcPress = BitmapFactory.decodeResource(getResources(), tunaDragBallBitmapSrcPressId);
        }

        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        typedArray.recycle();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        /**
         * attantion that the bottom of the circle radius equals the small
         * tunaDragBallRadiusNormal!
         */
        circleNormalDiameter = tunaDragBallRadiusNormal * 2;
        tunaSurplus = tunaWidth - circleNormalDiameter * tunaTotal - tunaDragStrokeWidth;
        tunaShare = tunaSurplus / (tunaTotal - 1);

        // The view must be greater than the height multiplied by the
        // tunaDragArray length
        if (tunaShare <= 0) {
            throw new IndexOutOfBoundsException("The view must be greater than the height multiplied by the tunaDragArray length");
        }

        // first start draw bottom of the picture:

        // tunaDy=hypotenuse*sin(tunaDragAngle*0.5f)
        tunaDy = (float) (circleNormalDiameter * 0.5f * Math.sin(Math.toRadians(tunaDragAngle * 0.5f)));
        // tunaDx=hypotenuse-hypotenuse*cos(tunaDragAngle*0.5f)
        tunaDx = (float) (circleNormalDiameter * 0.5f - circleNormalDiameter * 0.5f * Math.cos(Math.toRadians(tunaDragAngle * 0.5f)));

        // tunaDragCircleCentreXArray avoid generating with new
        for (int i = 0; i < tunaTotal; i++) {
            tunaDragCircleCentreXArray[i] = (circleNormalDiameter + tunaShare) * i + circleNormalDiameter * 0.5f + tunaDragStrokeWidth * 0.5f;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 移上去按下去边框没了
        // tunaRectArray avoid generating with new
        for (int i = 0; i < tunaTotal; i++) {
            tunaDragCircleRectFArray[i].set((circleNormalDiameter + tunaShare) * i + tunaDragStrokeWidth * 0.5f, (tunaHeight - circleNormalDiameter) * 0.5f,
                    (circleNormalDiameter + tunaShare) * i + circleNormalDiameter + tunaDragStrokeWidth * 0.5f, (tunaHeight - circleNormalDiameter) * 0.5f + circleNormalDiameter);
        }

        initTunaPath();

        for (int i = 0; i < tunaTotal; i++) {
            if (i == 0) {
                tunaPath.addArc(tunaDragCircleRectFArray[i], tunaDragAngle * 0.5f, 360 - tunaDragAngle);
            } else {
                tunaPath.lineTo(tunaDragCircleCentreXArray[i] - circleNormalDiameter * 0.5f + tunaDx, (tunaHeight >> 1) - tunaDy);
                tunaPath.addArc(tunaDragCircleRectFArray[i], 180 + tunaDragAngle * 0.5f, i != tunaTotal - 1 ? 180 - tunaDragAngle : 360 - tunaDragAngle);
            }
        }

        for (int i = tunaTotal - 2; i >= 0; i--) {
            tunaPath.lineTo(tunaDragCircleCentreXArray[i] + circleNormalDiameter * 0.5f - tunaDx, (tunaHeight >> 1) + tunaDy);
            if (i != 0) {
                tunaPath.addArc(tunaDragCircleRectFArray[i], tunaDragAngle * 0.5f, 180 - tunaDragAngle);
            }
        }

        // draw bottom pitcure
        canvas.drawPath(tunaPath, initTunaPaint(Paint.Style.STROKE, tunaDragStrokeColor, tunaDragStrokeWidth));

        // draw bottom filling
        initTunaPaint(Paint.Style.FILL, tunaDragFillColor);

        for (int i = 0; i < tunaTotal; i++) {
            canvas.drawCircle(tunaDragCircleCentreXArray[i], tunaHeight >> 1, circleNormalDiameter * 0.5f - tunaDragBallStrokeWidth, tunaPaint);

            if (i != tunaTotal - 1) {
                // Normally rectangle from the top left corner should be
                // tunaDragCircleCentreXArray [i] + circleDiameter / 2 ,
                // Considering the circles and rectangles are not well matched
                // and another reason from float to int when deleting the
                // decimal, so the need to subtract about fivefold tunaDx
                // adjustment (),
                // To the left :tunaDragCircleCentreXArray [i] +
                // circleDiameter*0.5f-tunaDx * 5 ,
                // To the right:tunaDragCircleCentreXArray [i] + circleDiameter
                // / 2 + tunaShare + tunaDx * 5

                tunaDragCircleRectFArray[0].set(tunaDragCircleCentreXArray[i] + circleNormalDiameter * 0.5f - tunaDx * 5, (tunaHeight >> 1) - tunaDy, tunaDragCircleCentreXArray[i]
                        + circleNormalDiameter * 0.5f + tunaShare + tunaDx * 5, (tunaHeight >> 1) + tunaDy);

                canvas.drawRect(tunaDragCircleRectFArray[0], tunaPaint);
            }
        }

        // draw bottom text

        initTunaTextPaint(Paint.Style.FILL, tunaDragTextColorNormal, tunaDragTextSize, Align.CENTER);
        for (int i = 0; i < tunaTotal; i++) {
            drawTunaText(canvas, tunaDragTextValueArray[i], tunaWidth, tunaDragCircleCentreXArray[i], tunaHeight >> 1, 0, 0, tunaPaint);
        }

        // draw response
        if (tunaPress) {
            // If the incoming the background painted directly
            if (tunaDragBallBitmapSrcPress != null) {

                tunaDragCircleRectFArray[0].set(tunaDragCurrentX - tunaDragBallRadiusPress, (tunaHeight >> 1) - tunaDragBallRadiusPress,
                        tunaDragCurrentX + tunaDragBallRadiusPress, (tunaHeight >> 1) + tunaDragBallRadiusPress);

                canvas.drawBitmap(tunaDragBallBitmapSrcPress, null, tunaDragCircleRectFArray[0], tunaPaint);

                // No not incoming the background draw the default texture
            } else {
                tunaPaint.setColor(tunaDragBallStrokeColorPress);
                canvas.drawCircle(tunaDragCurrentX, tunaHeight >> 1, tunaDragBallRadiusPress, tunaPaint);
                tunaPaint.setColor(tunaDragBallBackgroundPress);
                canvas.drawCircle(tunaDragCurrentX, tunaHeight >> 1, tunaDragBallRadiusPress - tunaDragBallStrokeWidth, tunaPaint);

                // draw veins overlapping round

                float bezierOvalX = tunaDragCurrentX;
                float bezierOvalY = tunaHeight >> 1;

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

                tunaPath.reset();

                initTunaPaint(Paint.Style.STROKE, tunaDragStrokeColor);

                for (int i = 0; (deviationOvalXOffset + controlOvalYOffset) * i < tunaDragBallRadiusPress; i++) {
                    if (i == 0) {
                        tunaPath.moveTo(bezierOvalX, bezierOvalY);
                    }
                    tunaPath.quadTo(bezierOvalX, bezierOvalY - deviationOvalY - controlOvalY, bezierOvalX + deviationOvalX, bezierOvalY - deviationOvalY);// topRight
                    tunaPath.quadTo(bezierOvalX + deviationOvalX + controlOvalX, bezierOvalY, bezierOvalX + deviationOvalX, bezierOvalY + deviationOvalY);// bottomRight
                    tunaPath.quadTo(bezierOvalX, bezierOvalY + deviationOvalY + controlOvalY, bezierOvalX - deviationOvalX, bezierOvalY + deviationOvalY);// bottomLeft
                    tunaPath.quadTo(bezierOvalX - deviationOvalX - controlOvalX, bezierOvalY, bezierOvalX - deviationOvalX, bezierOvalY - deviationOvalY);// topLeft

                    deviationOvalX += deviationOvalXOffset;
                    deviationOvalY += deviationOvalYOffset;

                    controlOvalX += controlOvalXOffset;
                    controlOvalY += controlOvalYOffset;
                }
                canvas.drawPath(tunaPath, tunaPaint);
            }

            drawTunaText(canvas, tunaDragTextValueArray[tunaDragCurrentIndex], tunaWidth, tunaDragCurrentX, tunaHeight >> 1, 0, 0,
                    initTunaTextPaint(Paint.Style.FILL, tunaDragBallTextColor, tunaDragTextSize, Align.CENTER));

        } else {
            float adjuestX = tunaDragCircleCentreXArray[tunaDragCurrentIndex];

            // draw response circle
            canvas.drawCircle(adjuestX, tunaHeight >> 1, tunaDragBallRadiusNormal, initTunaPaint(Paint.Style.FILL, tunaDragBallStrokeColorNormal));
            canvas.drawCircle(adjuestX, tunaHeight >> 1, tunaDragBallRadiusNormal - tunaDragBallStrokeWidth, initTunaPaint(Paint.Style.FILL, tunaDragBallBackgroundNormal));

            // draw response text
            drawTunaText(canvas, tunaDragTextValueArray[tunaDragCurrentIndex], tunaWidth, adjuestX, tunaHeight >> 1, 0, 0,
                    initTunaTextPaint(Paint.Style.FILL, tunaDragBallTextColor, tunaDragTextSize, Align.CENTER));
        }
    }

    public float getTunaDragCurrentX() {
        return tunaDragCurrentX;
    }

    public void setTunaDragCurrentX(float tunaDragCurrentX) {
        setTunaDragCurrentX(TypedValue.COMPLEX_UNIT_DIP, tunaDragCurrentX);
    }

    public void setTunaDragCurrentX(int unit, float tunaDragCurrentX) {
        Context c = getContext();
        Resources r;
        if (c == null)
            r = Resources.getSystem();
        else
            r = c.getResources();
        setTunaDragCurrentXRaw(applyDimension(unit, tunaDragCurrentX, r.getDisplayMetrics()));
    }

    private void setTunaDragCurrentXRaw(float tunaDragCurrentX) {
        if (this.tunaDragCurrentX != tunaDragCurrentX) {
            this.tunaDragCurrentX = tunaDragCurrentX;
            // calculate index
            float minDistence = tunaWidth;
            // From 0 to judge one by one, if the distance farther on the end of
            // the cycle
            for (int i = 0; i < tunaTotal; i++) {
                float circleCentreDistance = Math.abs(tunaDragCurrentX - tunaDragCircleCentreXArray[i]);
                if (circleCentreDistance < minDistence) {
                    tunaDragCurrentIndex = i;
                    minDistence = circleCentreDistance;
                } else {
                    break;
                }
            }
        }
    }

    public int getTunaCurrentIndex() {
        return tunaDragCurrentIndex;
    }

    public void setTunaCurrentIndex(int tunaDragCurrentIndex) {
        if (tunaDragCurrentIndex < 0 || tunaDragCurrentIndex >= tunaTotal) {
            throw new IndexOutOfBoundsException("The content attribute tunaDragCurrentIndex length must be not less than zero and smaller than the tunaDragArray length");
        }
        this.tunaDragCurrentIndex = tunaDragCurrentIndex;
        invalidate();
    }
}
