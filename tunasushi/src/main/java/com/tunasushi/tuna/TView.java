package com.tunasushi.tuna;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ApplicationInfo;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Property;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.tunasushi.tool.DeviceTool;
import com.tuna.R;
import com.tunasushi.tool.PaintTool;

import java.util.ArrayList;
import java.util.List;

import static com.tunasushi.tool.BitmapTool.decodeBitmapResource;
import static com.tunasushi.tool.DeviceTool.applyDimension;
import static com.tunasushi.tool.DeviceTool.convertToPX;
import static com.tunasushi.tool.DeviceTool.getViewDisplayMetrics;
import static com.tunasushi.tool.PaintTool.initTextPaint;
import static com.tunasushi.tool.PaintTool.paint;
import static com.tunasushi.tool.PathTool.initPathRoundRect;
import static com.tunasushi.tool.ViewTool.getLinearGradient;
import static com.tunasushi.tool.ViewTool.setViewMargins;

/**
 * @author Tunasashimi
 * @date 11/4/15 17:25
 * @Copyright 2015 Tunasashimi. All rights reserved.
 * @Description
 */

public class TView extends View {
    /**
     * The following fields and methods of the parent class and subclass can always use
     */
    // as the mark of TView species
    protected String Tag;

    // the width and height of the TView(put together to save the number of rows)
    protected int width, height;

    //
    protected int layer;
    protected int total;

    protected Bitmap srcBitmap;


    protected float srcWidthScale, srcHeightScale;

    protected float centerX, centerY;
    protected float dx, dy;
    protected float scale, scaleSx, scaleSy;

    protected float percent;
    protected float surplus, share;


    protected float[] floatArray;
    protected String[] stringArray;



    protected Rect rect;

    public Rect getRect() {
        return rect;
    }

    public void setRect(Rect rect) {
        this.rect = rect;
    }

    protected Rect initRect() {
        if (rect == null) {
            rect = new Rect();
        }
        return rect;
    }

    protected Rect initRect(int left, int top, int right, int bottom) {
        if (rect == null) {
            rect = new Rect(left, top, right, bottom);
        }
        rect.set(left, top, right, bottom);
        return rect;
    }

    protected RectF rectF;

    public RectF getRectF() {
        return rectF;
    }

    public void setRectF(RectF rectF) {
        this.rectF = rectF;
    }

    //
    protected RectF initRectF(float left, float top, float right, float bottom) {
        if (rectF == null) {
            rectF = new RectF(left, top, right, bottom);
        }
        rectF.set(left, top, right, bottom);
        return rectF;
    }

    protected Canvas canvas;

    public Canvas getCanvas() {
        return canvas;
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    //
    protected Matrix matrix;

    public Matrix getMatrix() {
        return matrix;
    }

    public void setMatrix(Matrix matrix) {
        this.matrix = matrix;
    }

    protected Matrix initMatrix(float sx, float sy) {
        if (matrix == null) {
            matrix = new Matrix();
        }
        matrix.reset();
        matrix.setScale(sx, sy);
        return matrix;
    }

    public static Matrix initMatrix(Matrix matrix, float sx, float sy) {
        if (matrix == null) {
            matrix = new Matrix();
        }
        matrix.reset();
        matrix.setScale(sx, sy);
        return matrix;
    }


    protected LayoutInflater initLayoutInflater() {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(getContext());
        }
        return layoutInflater;
    }


    protected void setLayout(int width, int height) {
        if (layoutParams == null) {
            layoutParams = getLayoutParams();
        }
        layoutParams.width = width;
        layoutParams.height = height;
        setLayoutParams(layoutParams);
        invalidate();
    }


    //
    protected void initcanvas(Canvas canvas) {
        if (canvas.getDrawFilter() == null) {
            canvas.setDrawFilter(paintFlagsDrawFilter);
            canvasHardwareAccelerated = canvas.isHardwareAccelerated();
        }
    }

    // 8
    protected void drawRectCustom(Canvas canvas, int width, int height, int fillColor, float radiusLeftTop, float radiusLeftBottom,
                                  float radiusRightTop, float radiusRightBottom) {

        drawRectCustom(canvas, 0, 0, width, height, fillColor, null, 0, Color.TRANSPARENT, 0, 0, 0, Color.TRANSPARENT, radiusLeftTop, radiusLeftBottom, radiusRightTop,
                radiusRightBottom);
    }

    // 10
    protected void drawRectCustom(Canvas canvas, int width, int height, int fillColor, float strokeWidth, int strokeColor, float radiusLeftTop, float radiusLeftBottom,
                                  float radiusRightTop, float radiusRightBottom) {

        drawRectCustom(canvas, 0, 0, width, height, fillColor, null, 0, Color.TRANSPARENT, 0, 0, strokeWidth, strokeColor, radiusLeftTop, radiusLeftBottom, radiusRightTop,
                radiusRightBottom);
    }

    // 10
    protected void drawRectCustom(Canvas canvas, int width, int height, Shader shader, float strokeWidth, int strokeColor, float radiusLeftTop, float radiusLeftBottom,
                                  float radiusRightTop, float radiusRightBottom) {

        drawRectCustom(canvas, 0, 0, width, height, Color.TRANSPARENT, shader, 0, Color.TRANSPARENT, 0, 0, strokeWidth, strokeColor, radiusLeftTop, radiusLeftBottom,
                radiusRightTop, radiusRightBottom);
    }

    // 14
    protected void drawRectCustom(Canvas canvas, int width, int height, int fillColor, float shadowRadius, int shadowColor, float shadowDx, float shadowDy, float strokeWidth,
                                  int strokeColor, float radiusLeftTop, float radiusLeftBottom, float radiusRightTop, float radiusRightBottom) {

        drawRectCustom(canvas, 0, 0, width, height, fillColor, null, shadowRadius, shadowColor, shadowDx, shadowDy, strokeWidth, strokeColor, radiusLeftTop, radiusLeftBottom,
                radiusRightTop, radiusRightBottom);
    }

    // 14
    protected void drawRectCustom(Canvas canvas, int width, int height, Shader shader, float shadowRadius, int shadowColor, float shadowDx, float shadowDy, float strokeWidth,
                                  int strokeColor, float radiusLeftTop, float radiusLeftBottom, float radiusRightTop, float radiusRightBottom) {

        drawRectCustom(canvas, 0, 0, width, height, Color.TRANSPARENT, shader, shadowRadius, shadowColor, shadowDx, shadowDy, strokeWidth, strokeColor, radiusLeftTop,
                radiusLeftBottom, radiusRightTop, radiusRightBottom);
    }

    // 15
    protected void drawRectCustom(Canvas canvas, float left, float top, float right, float bottom, int fillColor, Shader shader, float shadowRadius, int shadowColor,
                                  float shadowDx, float shadowDy, float strokeWidth, int strokeColor, float radiusLeftTop, float radiusLeftBottom, float radiusRightTop, float radiusRightBottom) {

        float[] radii = {radiusLeftTop, radiusLeftTop, radiusRightTop, radiusRightTop, radiusRightBottom, radiusRightBottom, radiusLeftBottom, radiusLeftBottom};
        if (strokeWidth > 0) {
            canvas.drawPath(
                    initPathRoundRect(initRectF(left + strokeWidth * 0.5f, top + strokeWidth * 0.5f, right - strokeWidth * 0.5f, bottom - strokeWidth * 0.5f), radii,
                            Path.Direction.CW), PaintTool.initPaint(Paint.Style.STROKE, strokeColor, strokeWidth));
        }

        int radiiLength = radii.length;
        for (int i = 0; i < radiiLength; i++) {
            radii[i] -= strokeWidth;
            radii[i] = radii[i] >= 0 ? radii[i] : 0;
        }

        canvas.drawPath(
                initPathRoundRect(initRectF(left + strokeWidth, top + strokeWidth, right - strokeWidth, bottom - strokeWidth), radii, Path.Direction.CW),
                shader == null ? shadowRadius == 0 ? PaintTool.initPaint(fillColor) : PaintTool.initPaint(Paint.Style.FILL, fillColor, shadowRadius, shadowColor, shadowDx,
                        shadowDy) : shadowRadius == 0 ? PaintTool.initPaint(Paint.Style.FILL, shader) : PaintTool.initPaint(Paint.Style.FILL, shader, shadowRadius, shadowColor, shadowDx,
                        shadowDy));
    }

    // 5
    protected void drawRectClassic(Canvas canvas, float width, float height, int fillColor, float radius) {

        drawRectClassic(canvas, 0, 0, width, height, fillColor, null, 0, Color.TRANSPARENT, 0, 0, 0, Color.TRANSPARENT, radius);
    }

    // 7
    protected void drawRectClassic(Canvas canvas, float width, float height, int fillColor, float strokeWidth, int strokeColor, float radius) {

        drawRectClassic(canvas, 0, 0, width, height, fillColor, null, 0, Color.TRANSPARENT, 0, 0, strokeWidth, strokeColor, radius);
    }

    // 7
    protected void drawRectClassic(Canvas canvas, float width, float height, Shader shader, float strokeWidth, int strokeColor, float radius) {

        drawRectClassic(canvas, 0, 0, width, height, Color.TRANSPARENT, shader, 0, Color.TRANSPARENT, 0, 0, strokeWidth, strokeColor, radius);
    }

    // 9
    protected void drawRectClassic(Canvas canvas, float left, float top, float right, float bottom, int fillColor, float strokeWidth, int strokeColor, float radius) {

        drawRectClassic(canvas, left, top, right, bottom, fillColor, null, 0, Color.TRANSPARENT, 0, 0, strokeWidth, strokeColor, radius);
    }

    // 9
    protected void drawRectClassic(Canvas canvas, float left, float top, float right, float bottom, Shader shader, float strokeWidth, int strokeColor, float radius) {

        drawRectClassic(canvas, left, top, right, bottom, Color.TRANSPARENT, shader, 0, Color.TRANSPARENT, 0, 0, strokeWidth, strokeColor, radius);
    }

    // 11
    protected void drawRectClassic(Canvas canvas, float width, float height, int fillColor, float shadowRadius, int shadowColor, float shadowDx, float shadowDy, float strokeWidth,
                                   int strokeColor, float radius) {

        drawRectClassic(canvas, 0, 0, width, height, fillColor, null, shadowRadius, shadowColor, shadowDx, shadowDy, strokeWidth, strokeColor, radius);
    }

    // 11
    protected void drawRectClassic(Canvas canvas, float width, float height, Shader shader, float shadowRadius, int shadowColor, float shadowDx, float shadowDy, float strokeWidth,
                                   int strokeColor, float radius) {

        drawRectClassic(canvas, 0, 0, width, height, Color.TRANSPARENT, shader, shadowRadius, shadowColor, shadowDx, shadowDy, strokeWidth, strokeColor, radius);
    }

    // 12
    protected void drawRectClassic(Canvas canvas, float left, float top, float right, float bottom, int fillColor, Shader shader, float shadowRadius, int shadowColor,
                                   float shadowDx, float shadowDy, float strokeWidth, int strokeColor, float radius) {

        if (strokeWidth > 0) {
            canvas.drawRoundRect(initRectF(left + strokeWidth * 0.5f, top + strokeWidth * 0.5f, right - strokeWidth * 0.5f, bottom - strokeWidth * 0.5f), radius, radius,
                    PaintTool.initPaint(Paint.Style.STROKE, strokeColor, strokeWidth));
        }

        canvas.drawRoundRect(
                initRectF(left + strokeWidth, top + strokeWidth, right - strokeWidth, bottom - strokeWidth),
                radius,
                radius,
                shader == null ? shadowRadius == 0 ? PaintTool.initPaint(fillColor) : PaintTool.initPaint(Paint.Style.FILL, fillColor, shadowRadius, shadowColor, shadowDx,
                        shadowDy) : shadowRadius == 0 ? PaintTool.initPaint(Paint.Style.FILL, shader) : PaintTool.initPaint(Paint.Style.FILL, shader, shadowRadius, shadowColor, shadowDx,
                        shadowDy));
    }

    // 6
    protected float[] drawText(Canvas canvas, String string, float width, float centerX, float centerY, Paint paint) {
        return drawText(canvas, string, width, centerX, centerY, 0, 0, paint, textGravityArray[0], 1.0f, null);
    }

    // 8
    protected float[] drawText(Canvas canvas, String string, float width, float centerX, float centerY, float paddingLeft, float paddingRight, Paint paint) {
        return drawText(canvas, string, width, centerX, centerY, paddingLeft, paddingRight, paint, textGravityArray[0], 1.0f, null);
    }

    // 9
    protected float[] drawText(Canvas canvas, String string, float width, float centerX, float centerY, float paddingLeft, float paddingRight, Paint paint,
                               float textRowSpaceRatio, List<Integer> valueMeasureList) {
        return drawText(canvas, string, width, centerX, centerY, paddingLeft, paddingRight, paint, textGravityArray[0], textRowSpaceRatio, valueMeasureList);
    }

    // 10
    protected float[] drawText(Canvas canvas, String string, float width, float centerX, float centerY, float paddingLeft, float paddingRight, Paint paint,
                               TextGravity textGravity, float textRowSpaceRatio, List<Integer> valueMeasureList) {

        if (valueMeasureList == null) {
            valueMeasureList = generateMeasureList(string, paint, width, paddingLeft, paddingRight);
        }

        float textMiddleRow = (valueMeasureList.size() + 1) * 0.5f;

        FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        // float baseline = (targetRectBottom + targetRectTop - fontMetrics.bottom - fontMetrics.top) * 0.5f;
        float baseline = centerY - fontMetrics.bottom * 0.5f - fontMetrics.top * 0.5f;
        int halfWordHeight = (fontMetrics.descent - fontMetrics.ascent) >> 1;

        //
        String drawString;
        float drawLineY;

        int valueMeasureListSize = valueMeasureList.size();
        for (int i = 0; i < valueMeasureListSize; i++) {
            drawLineY = baseline + (i + 1 - textMiddleRow) * paint.getTextSize() * textRowSpaceRatio;
            if (i == 0) {
                drawString = string.substring(0, valueMeasureList.get(i));
            } else {
                drawString = string.substring(valueMeasureList.get(i - 1), valueMeasureList.get(i));
            }

            float measureLength = paint.measureText(drawString);

            if (i != valueMeasureListSize - 1) {
                switch (textGravity) {
                    case ALL_CENTER:
                        canvas.drawText(drawString, centerX + (paddingLeft - paddingRight) * 0.5f, drawLineY, paint);
                        break;
                    case ALL_LEFT:
                        canvas.drawText(drawString, paddingLeft + measureLength * 0.5f, drawLineY, paint);
                        break;
                    case CENTER_LEFT:
                        canvas.drawText(drawString, centerX + (paddingLeft - paddingRight) * 0.5f, drawLineY, paint);
                        break;
                    case LEFT_CENTER:
                        canvas.drawText(drawString, paddingLeft + measureLength * 0.5f, drawLineY, paint);
                        break;
                    default:
                        break;
                }
            } else {
                float availableWidth = width - paddingLeft - paddingRight;
                switch (textGravity) {
                    case ALL_CENTER:
                        canvas.drawText(drawString, centerX, drawLineY, paint);
                        return new float[]{measureLength, measureLength * 0.5f, drawLineY - centerY - halfWordHeight};
                    case ALL_LEFT:
                        canvas.drawText(drawString, paddingLeft + measureLength * 0.5f, drawLineY, paint);
                        return new float[]{availableWidth, measureLength + paddingLeft - width * 0.5f, drawLineY - centerY - halfWordHeight};
                    case CENTER_LEFT:
                        canvas.drawText(drawString, paddingLeft + measureLength * 0.5f, drawLineY, paint);
                        return new float[]{availableWidth, measureLength + paddingLeft - width * 0.5f, drawLineY - centerY - halfWordHeight};
                    case LEFT_CENTER:
                        canvas.drawText(drawString, centerX, drawLineY, paint);
                        return new float[]{measureLength, measureLength * 0.5f, drawLineY - centerY - halfWordHeight};
                    default:
                        break;
                }
            }
        }
        return new float[]{width, 0, 0};
    }

    //
    protected void drawTextMark(
            Canvas canvas,
            float radius, Paint paint,
            float markRadius,
            float markDx, float markDy,
            float offsetX, float offsetY,
            String markText,
            int markTextColor,
            float markTextSize,
            List<Integer> valueMeasureList) {

        float cx = (width >> 1) + offsetX + markRadius + markDx;
        float cy = (height >> 1) + offsetY + markDy;

        canvas.drawCircle(cx, cy, radius, paint);
        if (markText != null) {
            // Because, drawText use the same method to clear the cache measureRowList
            drawText(canvas, markText, width, cx, cy, 0, 0,
                    PaintTool.initTextPaint(Paint.Style.FILL, markTextColor, markTextSize, Paint.Align.CENTER)
                    , 1, valueMeasureList);
        }
    }

    //
    public void setStatius(boolean press, boolean select, boolean textMark) {
        if (this.press != press || this.select != select || this.textMark != textMark) {
            this.press = press;
            this.select = select;
            this.textMark = textMark;
            invalidate();
        }
    }

    //
    public void setStatius(boolean press, boolean select, boolean textMark, boolean material) {
        if (this.press != press || this.select != select || this.textMark != textMark) {
            this.press = press;
            this.select = select;
            this.textMark = textMark;
            if (!material && materialAnimatorSet != null) {
                this.materialAnimatorSet.cancel();
            }
            invalidate();
        }
    }

    //
    public static void associate(final TView[] TViewArray) {
        if (TViewArray == null) {
            return;
        }
        final int arraySize = TViewArray.length;
        for (int i = 0; i < arraySize; i++) {
            final int finalI = i;
            TViewArray[i].setAssociateListener(new associateListener() {
                @Override
                public void associate(View v) {
                    for (int j = 0; j < arraySize; j++) {
                        if (j != finalI) {
                            TViewArray[j].setStatius(false, false, false);
                        }
                    }
                }
            });
            TViewArray[i].setTouchCancelListener(new TouchCancelListener() {
                @Override
                public void touchCancel(View v) {
                    for (int j = 0; j < arraySize; j++) {
                        switch (finalI) {
                            case 0:
                                if (j == finalI + 1) {
                                    TViewArray[j].setStatius(false, true, false);
                                } else if (j > finalI + 1) {
                                    TViewArray[j].setStatius(false, false, false);
                                }
                                break;
                            default:
                                if (j == finalI - 1) {
                                    TViewArray[j].setStatius(false, true, false);
                                } else if (j < finalI - 1) {
                                    TViewArray[j].setStatius(false, false, false);
                                }
                                break;
                        }
                    }
                }
            });
        }
    }

    //
    public static void associate(final List<TView> TViewList) {
        if (TViewList == null) {
            return;
        }
        final int listSize = TViewList.size();
        for (int i = 0; i < listSize; i++) {
            final int finalI = i;
            TViewList.get(i).setAssociateListener(new associateListener() {
                @Override
                public void associate(View v) {
                    for (int j = 0; j < listSize; j++) {
                        if (j != finalI) {
                            TViewList.get(j).setStatius(false, false, false, false);
                        }
                    }
                }
            });
            TViewList.get(i).setTouchCancelListener(new TouchCancelListener() {
                @Override
                public void touchCancel(View v) {
                    for (int j = 0; j < listSize; j++) {
                        switch (finalI) {
                            case 0:
                                if (j == finalI + 1) {
                                    TViewList.get(j).setStatius(false, true, false, false);
                                } else if (j > finalI + 1) {
                                    TViewList.get(j).setStatius(false, false, false, false);
                                }
                                break;
                            default:
                                if (j == finalI - 1) {
                                    TViewList.get(j).setStatius(false, true, false, false);
                                } else if (j < finalI - 1) {
                                    TViewList.get(j).setStatius(false, false, false, false);
                                }
                                break;
                        }
                    }
                }
            });
        }
    }

    //
    public static void dynamic(String[] titleArray, String string, TouchUpListener touchUpListener, LinearLayout linearLayout, int width, int leftStyle,
                               int rightStyle, int horizontalStyle, int wholeStyle) {

        dynamic(titleArray, string, touchUpListener, linearLayout, TypedValue.COMPLEX_UNIT_DIP, width, leftStyle, rightStyle, horizontalStyle, wholeStyle);
    }

    //
    public static void dynamic(String[] titleArray, String string, TouchUpListener touchUpListener, LinearLayout linearLayout, int unitWidth, int width, int leftStyle,
                               int rightStyle, int horizontalStyle, int wholeStyle) {
        int index = 0;
        for (int i = 0; i < titleArray.length; i++) {
            if (titleArray[i].equals(string)) {
                index = i;
                break;
            }
        }
        dynamic(titleArray, index, touchUpListener, linearLayout, unitWidth, width, leftStyle, rightStyle, horizontalStyle, wholeStyle);
    }

    //
    public static void dynamic(String[] titleArray, TouchUpListener touchUpListener, LinearLayout linearLayout, int width, int leftStyle, int rightStyle,
                               int horizontalStyle, int wholeStyle) {

        dynamic(titleArray, 0, touchUpListener, linearLayout, TypedValue.COMPLEX_UNIT_DIP, width, leftStyle, rightStyle, horizontalStyle, wholeStyle);
    }

    //
    public static void dynamic(String[] titleArray, TouchUpListener touchUpListener, LinearLayout linearLayout, int unitWidth, int width, int leftStyle,
                               int rightStyle, int horizontalStyle, int wholeStyle) {

        dynamic(titleArray, 0, touchUpListener, linearLayout, unitWidth, width, leftStyle, rightStyle, horizontalStyle, wholeStyle);
    }

    //
    public static void dynamic(String[] titleArray, int index, TouchUpListener touchUpListener, LinearLayout linearLayout, int width, int leftStyle, int rightStyle,
                               int horizontalStyle, int wholeStyle) {

        dynamic(titleArray, index, touchUpListener, linearLayout, TypedValue.COMPLEX_UNIT_DIP, width, leftStyle, rightStyle, horizontalStyle, wholeStyle);
    }

    //
    public static void dynamic(String[] titleArray, int index, TouchUpListener touchUpListener, LinearLayout linearLayout, int widthUnit, int width, int leftStyle,
                               int rightStyle, int horizontalStyle, int wholeStyle) {

        dynamicRaw(titleArray, index, touchUpListener, linearLayout, (int) applyDimension(widthUnit, width, getViewDisplayMetrics(linearLayout)), leftStyle, rightStyle,
                horizontalStyle, wholeStyle);
    }

    //
    private static void dynamicRaw(String[] titleArray, int index, TouchUpListener touchUpListener, LinearLayout linearLayout, int width, int leftStyle,
                                   int rightStyle, int horizontalStyle, int wholeStyle) {

        Context context = linearLayout.getContext();

//		int margin = context.getResources().getDimensionPixelOffset(R.dimen.tuna_stroke_mask);
        int margin = -2;    //-2px

        List<TView> TViewList = new ArrayList<TView>();

        if (titleArray.length <= 0) {
            return;
        } else if (titleArray.length == 1) {

            TView TView = new TView(context, null, wholeStyle);

            TView.setTextValue(titleArray[0]);
            TView.setTouchUpListener(touchUpListener);
            linearLayout.addView(TView, width, LinearLayout.LayoutParams.MATCH_PARENT);
        } else {

            for (int i = 0; i < titleArray.length; i++) {
                TView TView = new TView(context, null, i == 0 ? leftStyle : i == titleArray.length - 1 ? rightStyle : horizontalStyle);
                TView.setTextValue(titleArray[i]);
                if (i == index) {
                    TView.setSelect(true);
                }
                TView.setTouchUpListener(touchUpListener);

                TViewList.add(TView);
                linearLayout.addView(TView, width, LinearLayout.LayoutParams.MATCH_PARENT);

                if (i == 0 && titleArray.length == 2) {
                    setViewMargins(TView, TypedValue.COMPLEX_UNIT_PX, 0, 0, margin, 0);
                } else if (i == 1 && titleArray.length == 2) {
                    setViewMargins(TView, TypedValue.COMPLEX_UNIT_PX, margin, 0, 0, 0);
                } else if (i != 0 && i != titleArray.length - 1) {
                    setViewMargins(TView, TypedValue.COMPLEX_UNIT_PX, margin, 0, margin, 0);
                }
            }
            TView.associate(TViewList);
        }
    }

    //
    protected View propertiesView;
    protected LayoutParams layoutParams;
    protected LayoutInflater layoutInflater;

    protected void showProperties() {

        propertiesView = initLayoutInflater().inflate(R.layout.properties, null);

        final TextView text_display = propertiesView.findViewById(R.id.text_display);

        final EditText edit_width = propertiesView.findViewById(R.id.edit_width);
        final EditText edit_height = propertiesView.findViewById(R.id.edit_height);

        final EditText edit_backgroundNormal = propertiesView.findViewById(R.id.edit_backgroundNormal);
        final EditText edit_backgroundPress = propertiesView.findViewById(R.id.edit_backgroundPress);
        final EditText edit_backgroundSelect = propertiesView.findViewById(R.id.edit_backgroundSelect);

        final EditText edit_textSize = propertiesView.findViewById(R.id.edit_textSize);
        final EditText edit_textColorNormal = propertiesView.findViewById(R.id.edit_textColorNormal);

        final EditText edit_strokeWidth = propertiesView.findViewById(R.id.edit_strokeWidth);
        final EditText edit_strokeColor = propertiesView.findViewById(R.id.edit_strokeColor);

        final Button btn_width_pius = propertiesView.findViewById(R.id.btn_width_pius);
        final Button btn_width_minus = propertiesView.findViewById(R.id.btn_width_minus);
        final Button btn_height_pius = propertiesView.findViewById(R.id.btn_height_pius);
        final Button btn_height_minus = propertiesView.findViewById(R.id.btn_height_minus);

        final Button btn_textSize_pius = propertiesView.findViewById(R.id.btn_textSize_pius);
        final Button btn_textSize_minus = propertiesView.findViewById(R.id.btn_textSize_minus);

        final Button btn_strokeWidth_pius = propertiesView.findViewById(R.id.btn_strokeWidth_pius);
        final Button btn_strokeWidth_minus = propertiesView.findViewById(R.id.btn_strokeWidth_minus);

        final Button btn_backgroundNormal = propertiesView.findViewById(R.id.btn_backgroundNormal);
        final Button btn_backgroundPress = propertiesView.findViewById(R.id.btn_backgroundPress);
        final Button btn_backgroundSelect = propertiesView.findViewById(R.id.btn_backgroundSelect);

        final Button btn_textColorNormal = propertiesView.findViewById(R.id.btn_textColorNormal);

        final Button btn_strokeColor = propertiesView.findViewById(R.id.btn_strokeColor);

        final ToggleButton toogle_mark = propertiesView.findViewById(R.id.toogle_mark);
        final TextView text_mark = propertiesView.findViewById(R.id.text_mark);

        final ToggleButton toogle_thisHardwareAccelerated = propertiesView.findViewById(R.id.toogle_thisHardwareAccelerated);
        final TextView text_thisHardwareAccelerated = propertiesView.findViewById(R.id.text_thisHardwareAccelerated);

        final ToggleButton toogle_canvasHardwareAccelerated = propertiesView.findViewById(R.id.toogle_canvasHardwareAccelerated);
        final TextView text_canvasHardwareAccelerated = propertiesView.findViewById(R.id.text_canvasHardwareAccelerated);


        DeviceTool.initDisplayMetrics(getContext());
        text_display.setText(DeviceTool.stringBuffer);

        edit_width.setText(String.valueOf(DeviceTool.pxToDp(getContext(), width)));
        edit_height.setText(String.valueOf(DeviceTool.pxToDp(getContext(), height)));

        edit_backgroundNormal.setText(backgroundNormal != 0 ? Integer.toHexString(backgroundNormal) : "00000000");
        edit_backgroundPress.setText(backgroundPress != 0 ? Integer.toHexString(backgroundPress) : "00000000");
        edit_backgroundSelect.setText(backgroundSelect != 0 ? Integer.toHexString(backgroundSelect) : "00000000");

        edit_textSize.setText(String.valueOf(DeviceTool.pxToDp(getContext(), textSize)));
        edit_textColorNormal.setText(textColorNormal != 0 ? Integer.toHexString(textColorNormal) : "00000000");

        edit_strokeWidth.setText(String.valueOf(DeviceTool.pxToDp(getContext(), strokeWidthNormal)));
        edit_strokeColor.setText(strokeColorNormal != 0 ? Integer.toHexString(strokeColorNormal) : "00000000");

        //
        edit_backgroundNormal.setTextColor(backgroundNormal);
        edit_backgroundPress.setTextColor(backgroundPress);
        edit_backgroundSelect.setTextColor(backgroundSelect);
        edit_strokeColor.setTextColor(strokeColorNormal);
        edit_textColorNormal.setTextColor(textColorNormal);

        btn_backgroundNormal.setBackgroundColor(backgroundNormal);
        btn_backgroundPress.setBackgroundColor(backgroundPress);
        btn_backgroundSelect.setBackgroundColor(backgroundSelect);
        btn_strokeColor.setBackgroundColor(strokeColorNormal);
        btn_textColorNormal.setBackgroundColor(textColorNormal);

        toogle_mark.setChecked(textMark);
        text_mark.setText(String.valueOf(textMark));

        isHardwareAccelerated = this.isHardwareAccelerated();
        toogle_thisHardwareAccelerated.setChecked(isHardwareAccelerated);
        text_thisHardwareAccelerated.setText(String.valueOf(isHardwareAccelerated));

        toogle_canvasHardwareAccelerated.setChecked(canvasHardwareAccelerated);
        text_canvasHardwareAccelerated.setText(String.valueOf(canvasHardwareAccelerated));

        //
        toogle_mark.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                text_mark.setText(String.valueOf(isChecked));
            }
        });

        //
        OnClickListener onClickListener = new OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 * adt14 will lib where R in the file ID to the final tag removed All you want to set library package, which the switch ... case statement can only be changed if ... else for the job
                 */
                int viewId = view.getId();
                if (viewId == R.id.btn_width_pius) {
                    edit_width.setText(String.valueOf(Float.parseFloat(edit_width.getText().toString().trim()) + 1));
                } else if (viewId == R.id.btn_width_minus) {
                    edit_width.setText(String.valueOf(Float.parseFloat(edit_width.getText().toString().trim()) - 1));
                } else if (viewId == R.id.btn_height_pius) {
                    edit_height.setText(String.valueOf(Float.parseFloat(edit_height.getText().toString().trim()) + 1));
                } else if (viewId == R.id.btn_height_minus) {
                    edit_height.setText(String.valueOf(Float.parseFloat(edit_height.getText().toString().trim()) - 1));
                } else if (viewId == R.id.btn_textSize_pius) {
                    edit_textSize.setText(String.valueOf(Float.parseFloat(edit_textSize.getText().toString().trim()) + 1));
                } else if (viewId == R.id.btn_textSize_minus) {
                    edit_textSize.setText(String.valueOf(Float.parseFloat(edit_textSize.getText().toString().trim()) - 1));
                } else if (viewId == R.id.btn_strokeWidth_pius) {
                    edit_strokeWidth.setText(String.valueOf(Float.parseFloat(edit_strokeWidth.getText().toString().trim()) + 1));
                } else if (viewId == R.id.btn_strokeWidth_minus) {
                    edit_strokeWidth.setText(String.valueOf(Float.parseFloat(edit_strokeWidth.getText().toString().trim()) - 1));
                } else if (viewId == R.id.btn_backgroundNormal) {
                    new ColorPickerDialog(getContext(), backgroundNormal, new ColorPickerDialog.colorSelectListener() {
                        @Override
                        public void colorSelect(int color) {
                            btn_backgroundNormal.setBackgroundColor(color);
                            edit_backgroundNormal.setTextColor(color);
                            edit_backgroundNormal.setText(String.valueOf(Integer.toHexString(color)));
                        }
                    }).show();
                } else if (viewId == R.id.btn_backgroundPress) {
                    new ColorPickerDialog(getContext(), backgroundNormal, new ColorPickerDialog.colorSelectListener() {
                        @Override
                        public void colorSelect(int color) {
                            btn_backgroundPress.setBackgroundColor(color);
                            edit_backgroundPress.setTextColor(color);
                            edit_backgroundPress.setText(String.valueOf(Integer.toHexString(color)));
                        }
                    }).show();
                } else if (viewId == R.id.btn_backgroundSelect) {
                    new ColorPickerDialog(getContext(), backgroundNormal, new ColorPickerDialog.colorSelectListener() {
                        @Override
                        public void colorSelect(int color) {
                            btn_backgroundSelect.setBackgroundColor(color);
                            edit_backgroundSelect.setTextColor(color);
                            edit_backgroundSelect.setText(String.valueOf(Integer.toHexString(color)));
                        }
                    }).show();
                } else if (viewId == R.id.btn_textColorNormal) {
                    new ColorPickerDialog(getContext(), strokeColorNormal, new ColorPickerDialog.colorSelectListener() {
                        @Override
                        public void colorSelect(int color) {
                            btn_textColorNormal.setBackgroundColor(color);
                            edit_textColorNormal.setTextColor(color);
                            edit_textColorNormal.setText(String.valueOf(Integer.toHexString(color)));
                        }
                    }).show();
                } else if (viewId == R.id.btn_strokeColor) {
                    new ColorPickerDialog(getContext(), strokeColorNormal, new ColorPickerDialog.colorSelectListener() {
                        @Override
                        public void colorSelect(int color) {
                            btn_strokeColor.setBackgroundColor(color);
                            edit_strokeColor.setTextColor(color);
                            edit_strokeColor.setText(String.valueOf(Integer.toHexString(color)));
                        }
                    }).show();
                }
            }
        };

        btn_width_pius.setOnClickListener(onClickListener);
        btn_width_minus.setOnClickListener(onClickListener);
        btn_height_pius.setOnClickListener(onClickListener);
        btn_height_minus.setOnClickListener(onClickListener);
        btn_textSize_pius.setOnClickListener(onClickListener);
        btn_textSize_minus.setOnClickListener(onClickListener);
        btn_strokeWidth_pius.setOnClickListener(onClickListener);
        btn_strokeWidth_minus.setOnClickListener(onClickListener);

        btn_backgroundNormal.setOnClickListener(onClickListener);
        btn_backgroundPress.setOnClickListener(onClickListener);
        btn_backgroundSelect.setOnClickListener(onClickListener);

        btn_textColorNormal.setOnClickListener(onClickListener);
        btn_strokeColor.setOnClickListener(onClickListener);

        new AlertDialog.Builder(getContext(), android.R.style.Theme_Holo_Light)
                .setIconAttribute(android.R.attr.alertDialogIcon)
                .setView(propertiesView)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //
                        backgroundNormal = Color.parseColor("#" + edit_backgroundNormal.getText().toString().trim());
                        backgroundPress = Color.parseColor("#" + edit_backgroundPress.getText().toString().trim());
                        backgroundSelect = Color.parseColor("#" + edit_backgroundSelect.getText().toString().trim());
                        strokeColorNormal = Color.parseColor("#" + edit_strokeColor.getText().toString().trim());
                        textColorNormal = Color.parseColor("#" + edit_textColorNormal.getText().toString().trim());

                        textSize = DeviceTool.dpToPx(getContext(), Float.parseFloat(edit_textSize.getText().toString().trim()));

                        strokeWidthNormal = DeviceTool.dpToPx(getContext(), Float.parseFloat(edit_strokeWidth.getText().toString().trim()));

                        textMark = text_mark.getText().toString().trim().equals("true") ? true : false;

                        setLayout(DeviceTool.dpToPx(getContext(), Float.parseFloat(edit_width.getText().toString().trim())), DeviceTool.dpToPx(getContext(), Float.parseFloat(edit_height.getText().toString().trim())));
                    }
                }).setNegativeButton("Cancel", null).create().show();
    }

    // Hardware accelerated this
    protected boolean isHardwareAccelerated;
    // Hardware accelerated canvas
    protected boolean canvasHardwareAccelerated;

    // default edge
    private TouchType touchType;

    public enum TouchType {
        EDGE(0), ALWAYS(1), NONE(2),
        ;
        final int nativeInt;

        TouchType(int ni) {
            nativeInt = ni;
        }
    }

    private static final TouchType[] touchTypeArray = {TouchType.EDGE, TouchType.ALWAYS, TouchType.NONE,};

    public TouchType getTouchType() {
        return touchType;
    }

    public void setTouchType(TouchType touchType) {
        this.touchType = touchType;
    }

    private boolean tSuper;

    public boolean istSuper() {
        return tSuper;
    }

    public void settSuper(boolean tSuper) {
        this.tSuper = tSuper;
    }

    protected boolean classic;

    public boolean isclassic() {
        return classic;
    }

    public void setClassic(boolean classic) {
        this.classic = classic;
    }

    // can modify the properties when debug is true
    protected boolean debugable;

    public boolean isDebugable() {
        return debugable;
    }

    public void setDebugable(boolean debugable) {
        this.debugable = debugable;
    }

    // touchIntercept default false
    protected boolean touchIntercept;

    public boolean isTouchIntercept() {
        return touchIntercept;
    }

    public void setTouchIntercept(boolean touchIntercept) {
        this.touchIntercept = touchIntercept;
    }

    // press default false
    protected boolean press;

    public boolean isPress() {
        return press;
    }

    public void setPress(boolean press) {
        this.press = press;
    }

    // select default false
    protected boolean select;

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
        invalidate();
    }

    //
    protected boolean selectRaw;

    // default none
    private SelectType selectType;

    public enum SelectType {
        NONE(0), SAME(1), REVERSE(2),
        ;
        final int nativeInt;

        SelectType(int ni) {
            nativeInt = ni;
        }
    }

    private static final SelectType[] selectTypeArray = {SelectType.NONE, SelectType.SAME, SelectType.REVERSE,};

    public SelectType getSelectType() {
        return selectType;
    }

    public void setSelectType(SelectType selectType) {
        this.selectType = selectType;
    }

    // rotate default 0
    private int rotate;

    public int getRotate() {
        return rotate;
    }

    public void setRotate(int rotate) {
        this.rotate = rotate;
    }

    // default false
    protected boolean animationable;

    public boolean isanimationable() {
        return animationable;
    }

    public void setAnimationable(boolean animationable) {
        this.animationable = animationable;
        if (animationable) {
            invalidate();
        }
    }

    //
    protected float touchEventX;

    public float getTouchEventX() {
        return touchEventX;
    }

    public void setTouchEventX(float touchEventEventX) {
        this.touchEventX = touchEventEventX;
    }

    //
    protected float touchEventY;

    public float getTouchEventY() {
        return touchEventY;
    }

    public void setTouchEventY(float touchEventEventY) {
        this.touchEventY = touchEventEventY;
    }

    // default false
    protected boolean touchOutBounds;

    public boolean isTouchOutBounds() {
        return touchOutBounds;
    }

    public void setTouchOutBounds(boolean touchOutBounds) {
        this.touchOutBounds = touchOutBounds;
    }

    //
    protected LayoutListener layoutListener;

    public interface LayoutListener {
        void layout(View v);
    }

    public LayoutListener getLayoutListener() {
        return layoutListener;
    }

    public void setLayoutListener(LayoutListener layoutListener) {
        this.layoutListener = layoutListener;
    }

    //
    protected DrawListener drawListener;

    public interface DrawListener {
        void draw(View v);
    }

    public DrawListener getDrawListener() {
        return drawListener;
    }

    public void setDrawListener(DrawListener drawListener) {
        this.drawListener = drawListener;
    }

    //
    protected animateEndListener animateEndListener;

    public interface animateEndListener {
        void animateEnd(View v);
    }

    public animateEndListener getAnimateEndListener() {
        return animateEndListener;
    }

    public void setAnimateEndListener(animateEndListener animateEndListener) {
        this.animateEndListener = animateEndListener;
    }

    //
    protected TouchListener touchListener;

    public interface TouchListener {
        void touch(View v);
    }

    public TouchListener getTouchListener() {
        return touchListener;
    }

    public void setTouchListener(TouchListener touchListener) {
        this.touchListener = touchListener;
    }

    //
    protected TouchDownListener touchDownListener;

    public interface TouchDownListener {
        void touchDown(View v);
    }

    public TouchDownListener getTouchDownListener() {
        return touchDownListener;
    }

    public void setTouchDownListener(TouchDownListener touchDownListener) {
        this.touchDownListener = touchDownListener;
    }

    //
    protected TouchMoveListener touchMoveListener;

    public interface TouchMoveListener {
        void touchMove(View v);
    }

    public TouchMoveListener getTouchMoveListener() {
        return touchMoveListener;
    }

    public void setTouchMoveListener(TouchMoveListener touchMoveListener) {
        this.touchMoveListener = touchMoveListener;
    }

    //
    protected TouchUpListener touchUpListener;

    public interface TouchUpListener {
        void touchUp(View v);
    }

    public TouchUpListener getTouchUpListener() {
        return touchUpListener;
    }

    public void setTouchUpListener(TouchUpListener touchUpListener) {
        this.touchUpListener = touchUpListener;
    }

    //
    protected TouchCancelListener touchCancelListener;

    public interface TouchCancelListener {
        void touchCancel(View v);
    }

    public TouchCancelListener getTouchCancelListener() {
        return touchCancelListener;
    }

    public void setTouchCancelListener(TouchCancelListener touchCancelListener) {
        this.touchCancelListener = touchCancelListener;
    }

    //
    protected TouchOutListener touchOutListener;

    public interface TouchOutListener {
        void touchOut(View v);
    }

    public TouchOutListener getTouchOutListener() {
        return touchOutListener;
    }

    public void setTouchOutListener(TouchOutListener touchOutListener) {
        this.touchOutListener = touchOutListener;
    }

    //
    protected TouchInListener touchInListener;

    public interface TouchInListener {
        void touchIn(View v);
    }

    public TouchInListener getTouchInListener() {
        return touchInListener;
    }

    public void setTouchInListener(TouchInListener touchInListener) {
        this.touchInListener = touchInListener;
    }

    // associateListener is written in onTouchUp
    protected associateListener associateListener;

    public interface associateListener {
        void associate(View v);
    }

    public associateListener getAssociateListener() {
        return associateListener;
    }

    public void setAssociateListener(associateListener associateListener) {
        this.associateListener = associateListener;
    }

    //
    protected SubLayoutListener subLayoutListener;

    public interface SubLayoutListener {
        void subLayout(View v);
    }

    public SubLayoutListener getSubLayoutListener() {
        return subLayoutListener;
    }

    public void setSubLayoutListener(SubLayoutListener subLayoutListener) {
        this.subLayoutListener = subLayoutListener;
    }

    //
    protected SubDrawListener subDrawListener;

    public interface SubDrawListener {
        void subDraw(View v);
    }

    public SubDrawListener getSubDrawListener() {
        return subDrawListener;
    }

    public void setSubDrawListener(SubDrawListener subDrawListener) {
        this.subDrawListener = subDrawListener;
    }

    //
    protected SubAnimateEndListener subAnimateEndListener;

    public interface SubAnimateEndListener {
        void subAnimateEnd(View v);
    }

    public SubAnimateEndListener getSubAnimateEndListener() {
        return subAnimateEndListener;
    }

    public void setSubAnimateEndListener(SubAnimateEndListener subAnimateEndListener) {
        this.subAnimateEndListener = subAnimateEndListener;
    }

    /**
     * The following fields and methods can be used only in the superclass
     */

    // backgroundNormal in onLayout if backgroundNormal transparent and have drawn the case of default white shadow
    private int backgroundNormal;

    public int getBackgroundNormal() {
        return backgroundNormal;
    }

    public void setBackgroundNormal(int backgroundNormal) {
        this.backgroundNormal = backgroundNormal;
    }

    // backgroundPress default backgroundNormal
    private int backgroundPress;

    public int getBackgroundPress() {
        return backgroundPress;
    }

    public void setBackgroundPress(int backgroundPress) {
        this.backgroundPress = backgroundPress;
    }

    // backgroundSelect default backgroundNormal
    private int backgroundSelect;

    public int getBackgroundSelect() {
        return backgroundSelect;
    }

    public void setBackgroundSelect(int backgroundSelect) {
        this.backgroundSelect = backgroundSelect;
    }

    // foregroundNormal default transparent
    private int foregroundNormal;

    public int getForegroundNormal() {
        return foregroundNormal;
    }

    public void setForegroundNormal(int foregroundNormal) {
        this.foregroundNormal = foregroundNormal;
    }

    // foregroundPress default foregroundNormal
    private int foregroundPress;

    public int getForegroundPress() {
        return foregroundPress;
    }

    public void setForegroundPress(int foregroundPress) {
        this.foregroundPress = foregroundPress;
    }

    // foregroundSelect default foregroundNormal
    private int foregroundSelect;

    public int getForegroundSelect() {
        return foregroundSelect;
    }

    public void setForegroundSelect(int foregroundSelect) {
        this.foregroundSelect = foregroundSelect;
    }

    public static final int DIRECTION_LEFT = 0x00000001;
    public static final int DIRECTION_RIGHT = DIRECTION_LEFT << 1;
    public static final int DIRECTION_TOP = DIRECTION_RIGHT << 1;
    public static final int DIRECTION_BOTTOM = DIRECTION_TOP << 1;
    public static final int DIRECTION_MASK = DIRECTION_LEFT | DIRECTION_RIGHT | DIRECTION_TOP | DIRECTION_BOTTOM;

    private int backgroundNormalAngle;

    public int getBackgroundNormalAngle() {
        return backgroundNormalAngle;
    }

    public void setBackgroundNormalAngle(int backgroundNormalAngle) {
        this.backgroundNormalAngle = backgroundNormalAngle;
    }

    private int backgroundPressAngle;

    public int getBackgroundPressAngle() {
        return backgroundPressAngle;
    }

    public void setBackgroundPressAngle(int backgroundPressAngle) {
        this.backgroundPressAngle = backgroundPressAngle;
    }

    private int backgroundSelectAngle;

    public int getBackgroundSelectAngle() {
        return backgroundSelectAngle;
    }

    public void setBackgroundSelectAngle(int backgroundSelectAngle) {
        this.backgroundSelectAngle = backgroundSelectAngle;
    }

    // backgroundNormalGradientStart default backgroundNormal
    private int backgroundNormalGradientStart;

    public int getBackgroundNormalGradientStart() {
        return backgroundNormalGradientStart;
    }

    public void setBackgroundNormalGradientStart(int backgroundNormalGradientStart) {
        this.backgroundNormalGradientStart = backgroundNormalGradientStart;
    }

    // backgroundNormalGradientEnd default backgroundNormal
    private int backgroundNormalGradientEnd;

    public int getBackgroundNormalGradientEnd() {
        return backgroundNormalGradientEnd;
    }

    public void setBackgroundNormalGradientEnd(int backgroundNormalGradientEnd) {
        this.backgroundNormalGradientEnd = backgroundNormalGradientEnd;
    }

    // backgroundPressGradientStart default backgroundPress
    private int backgroundPressGradientStart;

    public int getBackgroundPressGradientStart() {
        return backgroundPressGradientStart;
    }

    public void setBackgroundPressGradientStart(int backgroundPressGradientStart) {
        this.backgroundPressGradientStart = backgroundPressGradientStart;
    }

    // backgroundPressGradientEnd default backgroundPress
    private int backgroundPressGradientEnd;

    public int getBackgroundPressGradientEnd() {
        return backgroundPressGradientEnd;
    }

    public void setBackgroundPressGradientEnd(int backgroundPressGradientEnd) {
        this.backgroundPressGradientEnd = backgroundPressGradientEnd;
    }

    // backgroundSelectGradientStart default backgroundSelect
    private int backgroundSelectGradientStart;

    public int getBackgroundSelectGradientStart() {
        return backgroundSelectGradientStart;
    }

    public void setBackgroundSelectGradientStart(int backgroundSelectGradientStart) {
        this.backgroundSelectGradientStart = backgroundSelectGradientStart;
    }

    // backgroundSelectGradientEnd default backgroundSelect
    private int backgroundSelectGradientEnd;

    public int getBackgroundSelectGradientEnd() {
        return backgroundSelectGradientEnd;
    }

    public void setBackgroundSelectGradientEnd(int backgroundSelectGradientEnd) {
        this.backgroundSelectGradientEnd = backgroundSelectGradientEnd;
    }

    // backgroundNormalShader default null
    private Shader backgroundNormalShader;

    public Shader getBackgroundNormalShader() {
        return backgroundNormalShader;
    }

    public void setBackgroundNormalShader(Shader backgroundNormalShader) {
        this.backgroundNormalShader = backgroundNormalShader;
    }

    // backgroundPressShader default null
    private Shader backgroundPressShader;

    public Shader getBackgroundPressShader() {
        return backgroundPressShader;
    }

    public void setBackgroundPressShader(Shader backgroundPressShader) {
        this.backgroundPressShader = backgroundPressShader;
    }

    // backgroundSelectShader default null
    private Shader backgroundSelectShader;

    public Shader getBackgroundSelectShader() {
        return backgroundSelectShader;
    }

    public void setBackgroundSelectShader(Shader backgroundSelectShader) {
        this.backgroundSelectShader = backgroundSelectShader;
    }

    //
    private float backgroundNormalShadowRadius;

    public float getBackgroundNormalShadowRadius() {
        return backgroundNormalShadowRadius;
    }

    public void setBackgroundNormalShadowRadius(float backgroundNormalShadowRadius) {
        setBackgroundNormalShadowRadius(TypedValue.COMPLEX_UNIT_DIP, backgroundNormalShadowRadius);
    }

    public void setBackgroundNormalShadowRadius(int unit, float backgroundNormalShadowRadius) {
        setBackgroundNormalShadowRadiusRaw(applyDimension(unit, backgroundNormalShadowRadius, getViewDisplayMetrics(this)));
    }

    private void setBackgroundNormalShadowRadiusRaw(float backgroundNormalShadowRadius) {
        if (this.backgroundNormalShadowRadius != backgroundNormalShadowRadius) {
            this.backgroundNormalShadowRadius = backgroundNormalShadowRadius;
            invalidate();
        }
    }

    //
    private int backgroundNormalShadowColor;

    public int getBackgroundNormalShadowColor() {
        return backgroundNormalShadowColor;
    }

    public void setBackgroundNormalShadowColor(int backgroundNormalShadowColor) {
        this.backgroundNormalShadowColor = backgroundNormalShadowColor;
    }

    //
    private float backgroundNormalShadowDx;

    public float getBackgroundNormalShadowDx() {
        return backgroundNormalShadowDx;
    }

    public void setBackgroundNormalShadowDx(float backgroundNormalShadowDx) {
        setBackgroundNormalShadowDx(TypedValue.COMPLEX_UNIT_DIP, backgroundNormalShadowDx);
    }

    public void setBackgroundNormalShadowDx(int unit, float backgroundNormalShadowDx) {
        setBackgroundNormalShadowDxRaw(applyDimension(unit, backgroundNormalShadowDx, getViewDisplayMetrics(this)));
    }

    private void setBackgroundNormalShadowDxRaw(float backgroundNormalShadowDx) {
        if (this.backgroundNormalShadowDx != backgroundNormalShadowDx) {
            this.backgroundNormalShadowDx = backgroundNormalShadowDx;
            invalidate();
        }
    }

    //
    private float backgroundNormalShadowDy;

    public float getBackgroundNormalShadowDy() {
        return backgroundNormalShadowDy;
    }

    public void setBackgroundNormalShadowDy(float backgroundNormalShadowDy) {
        setBackgroundNormalShadowDy(TypedValue.COMPLEX_UNIT_DIP, backgroundNormalShadowDy);
    }

    public void setBackgroundNormalShadowDy(int unit, float backgroundNormalShadowDy) {
        setBackgroundNormalShadowDyRaw(applyDimension(unit, backgroundNormalShadowDy, getViewDisplayMetrics(this)));
    }

    private void setBackgroundNormalShadowDyRaw(float backgroundNormalShadowDy) {
        if (this.backgroundNormalShadowDy != backgroundNormalShadowDy) {
            this.backgroundNormalShadowDy = backgroundNormalShadowDy;
            invalidate();
        }
    }

    // default backgroundNormalShadowRadius
    private float backgroundPressShadowRadius;

    public float getBackgroundPressShadowRadius() {
        return backgroundPressShadowRadius;
    }

    public void setBackgroundPressShadowRadius(float backgroundPressShadowRadius) {
        setBackgroundPressShadowRadius(TypedValue.COMPLEX_UNIT_DIP, backgroundPressShadowRadius);
    }

    public void setBackgroundPressShadowRadius(int unit, float backgroundPressShadowRadius) {
        setBackgroundPressShadowRadiusRaw(applyDimension(unit, backgroundPressShadowRadius, getViewDisplayMetrics(this)));
    }

    private void setBackgroundPressShadowRadiusRaw(float backgroundPressShadowRadius) {
        if (this.backgroundPressShadowRadius != backgroundPressShadowRadius) {
            this.backgroundPressShadowRadius = backgroundPressShadowRadius;
            invalidate();
        }
    }

    // default backgroundNormalShadowColor
    private int backgroundPressShadowColor;

    public int getBackgroundPressShadowColor() {
        return backgroundPressShadowColor;
    }

    public void setBackgroundPressShadowColor(int backgroundPressShadowColor) {
        this.backgroundPressShadowColor = backgroundPressShadowColor;
    }

    // default backgroundNormalShadowDx
    private float backgroundPressShadowDx;

    public float getBackgroundPressShadowDx() {
        return backgroundPressShadowDx;
    }

    public void setBackgroundPressShadowDx(float backgroundPressShadowDx) {
        setBackgroundPressShadowDx(TypedValue.COMPLEX_UNIT_DIP, backgroundPressShadowDx);
    }

    public void setBackgroundPressShadowDx(int unit, float backgroundPressShadowDx) {
        setBackgroundPressShadowDxRaw(applyDimension(unit, backgroundPressShadowDx, getViewDisplayMetrics(this)));
    }

    private void setBackgroundPressShadowDxRaw(float backgroundPressShadowDx) {
        if (this.backgroundPressShadowDx != backgroundPressShadowDx) {
            this.backgroundPressShadowDx = backgroundPressShadowDx;
            invalidate();
        }
    }

    // default backgroundNormalShadowDy
    private float backgroundPressShadowDy;

    public float getBackgroundPressShadowDy() {
        return backgroundPressShadowDy;
    }

    public void setBackgroundPressShadowDy(float backgroundPressShadowDy) {
        setBackgroundPressShadowDy(TypedValue.COMPLEX_UNIT_DIP, backgroundPressShadowDy);
    }

    public void setBackgroundPressShadowDy(int unit, float backgroundPressShadowDy) {
        setBackgroundPressShadowDyRaw(applyDimension(unit, backgroundPressShadowDy, getViewDisplayMetrics(this)));
    }

    private void setBackgroundPressShadowDyRaw(float backgroundPressShadowDy) {
        if (this.backgroundPressShadowDy != backgroundPressShadowDy) {
            this.backgroundPressShadowDy = backgroundPressShadowDy;
            invalidate();
        }
    }

    // default backgroundNormalShadowRadius
    private float backgroundSelectShadowRadius;

    public float getBackgroundSelectShadowRadius() {
        return backgroundSelectShadowRadius;
    }

    public void setBackgroundSelectShadowRadius(float backgroundSelectShadowRadius) {
        setBackgroundSelectShadowRadius(TypedValue.COMPLEX_UNIT_DIP, backgroundSelectShadowRadius);
    }

    public void setBackgroundSelectShadowRadius(int unit, float backgroundSelectShadowRadius) {
        setBackgroundSelectShadowRadiusRaw(applyDimension(unit, backgroundSelectShadowRadius, getViewDisplayMetrics(this)));
    }

    private void setBackgroundSelectShadowRadiusRaw(float backgroundSelectShadowRadius) {
        if (this.backgroundSelectShadowRadius != backgroundSelectShadowRadius) {
            this.backgroundSelectShadowRadius = backgroundSelectShadowRadius;
            invalidate();
        }
    }

    // default backgroundNormalShadowColor
    private int backgroundSelectShadowColor;

    public int getBackgroundSelectShadowColor() {
        return backgroundSelectShadowColor;
    }

    public void setBackgroundSelectShadowColor(int backgroundSelectShadowColor) {
        this.backgroundSelectShadowColor = backgroundSelectShadowColor;
    }

    // default backgroundNormalShadowDx
    private float backgroundSelectShadowDx;

    public float getBackgroundSelectShadowDx() {
        return backgroundSelectShadowDx;
    }

    public void setBackgroundSelectShadowDx(float backgroundSelectShadowDx) {
        setBackgroundSelectShadowDx(TypedValue.COMPLEX_UNIT_DIP, backgroundSelectShadowDx);
    }

    public void setBackgroundSelectShadowDx(int unit, float backgroundSelectShadowDx) {
        setBackgroundSelectShadowDxRaw(applyDimension(unit, backgroundSelectShadowDx, getViewDisplayMetrics(this)));
    }

    private void setBackgroundSelectShadowDxRaw(float backgroundSelectShadowDx) {
        if (this.backgroundSelectShadowDx != backgroundSelectShadowDx) {
            this.backgroundSelectShadowDx = backgroundSelectShadowDx;
            invalidate();
        }
    }

    // default backgroundNormalShadowDy
    private float backgroundSelectShadowDy;

    public float getBackgroundSelectShadowDy() {
        return backgroundSelectShadowDy;
    }

    public void setBackgroundSelectShadowDy(float backgroundSelectShadowDy) {
        setBackgroundSelectShadowDy(TypedValue.COMPLEX_UNIT_DIP, backgroundSelectShadowDy);
    }

    public void setBackgroundSelectShadowDy(int unit, float backgroundSelectShadowDy) {
        setBackgroundSelectShadowDyRaw(applyDimension(unit, backgroundSelectShadowDy, getViewDisplayMetrics(this)));
    }

    private void setBackgroundSelectShadowDyRaw(float backgroundSelectShadowDy) {
        if (this.backgroundSelectShadowDy != backgroundSelectShadowDy) {
            this.backgroundSelectShadowDy = backgroundSelectShadowDy;
            invalidate();
        }
    }

    //
    private Bitmap srcNormal;

    public Bitmap getSrcNormal() {
        return srcNormal;
    }

    public void setSrcNormal(int id) {
        setSrcNormal(decodeBitmapResource(getContext(), id));
    }

    public void setSrcNormal(Bitmap srcNormal) {
        this.srcNormal = srcNormal;
        invalidate();
    }

    //
    private Bitmap srcPress;

    public Bitmap getSrcPress() {
        return srcPress;
    }

    public void setSrcPress(int id) {
        setSrcPress(decodeBitmapResource(getContext(), id));
    }

    public void setSrcPress(Bitmap srcPress) {
        this.srcPress = srcPress;
        invalidate();
    }

    //
    private Bitmap srcSelect;

    public Bitmap getSrcSelect() {
        return srcSelect;
    }

    public void setSrcSelect(int id) {
        setSrcSelect(decodeBitmapResource(getContext(), id));
    }

    public void setSrcSelect(Bitmap srcSelect) {
        this.srcSelect = srcSelect;
        invalidate();
    }

    //
    private float srcNormalShadowRadius;

    public float getSrcNormalShadowRadius() {
        return srcNormalShadowRadius;
    }

    public void setSrcNormalShadowRadius(float srcNormalShadowRadius) {
        setSrcNormalShadowRadius(TypedValue.COMPLEX_UNIT_DIP, srcNormalShadowRadius);
    }

    public void setSrcNormalShadowRadius(int unit, float srcNormalShadowRadius) {
        setSrcNormalShadowRadiusRaw(applyDimension(unit, srcNormalShadowRadius, getViewDisplayMetrics(this)));
    }

    private void setSrcNormalShadowRadiusRaw(float srcNormalShadowRadius) {
        if (this.srcNormalShadowRadius != srcNormalShadowRadius) {
            this.srcNormalShadowRadius = srcNormalShadowRadius;
            invalidate();
        }
    }

    //
    private float srcNormalShadowDx;

    public float getSrcNormalShadowDx() {
        return srcNormalShadowDx;
    }

    public void setSrcNormalShadowDx(float srcNormalShadowDx) {
        setSrcNormalShadowDx(TypedValue.COMPLEX_UNIT_DIP, srcNormalShadowDx);
    }

    public void setSrcNormalShadowDx(int unit, float srcNormalShadowDx) {
        setSrcNormalShadowDxRaw(applyDimension(unit, srcNormalShadowDx, getViewDisplayMetrics(this)));
    }

    private void setSrcNormalShadowDxRaw(float srcNormalShadowDx) {
        if (this.srcNormalShadowDx != srcNormalShadowDx) {
            this.srcNormalShadowDx = srcNormalShadowDx;
            invalidate();
        }
    }

    //
    private float srcNormalShadowDy;

    public float getSrcNormalShadowDy() {
        return srcNormalShadowDy;
    }

    public void setSrcNormalShadowDy(float srcNormalShadowDy) {
        setSrcNormalShadowDy(TypedValue.COMPLEX_UNIT_DIP, srcNormalShadowDy);
    }

    public void setSrcNormalShadowDy(int unit, float srcNormalShadowDy) {
        setSrcNormalShadowDyRaw(applyDimension(unit, srcNormalShadowDy, getViewDisplayMetrics(this)));
    }

    private void setSrcNormalShadowDyRaw(float srcNormalShadowDy) {
        if (this.srcNormalShadowDy != srcNormalShadowDy) {
            this.srcNormalShadowDy = srcNormalShadowDy;
            invalidate();
        }
    }

    // default srcNormalShadowRadius
    private float srcPressShadowRadius;

    public float getSrcPressShadowRadius() {
        return srcPressShadowRadius;
    }

    public void setSrcPressShadowRadius(float srcPressShadowRadius) {
        setSrcPressShadowRadius(TypedValue.COMPLEX_UNIT_DIP, srcPressShadowRadius);
    }

    public void setSrcPressShadowRadius(int unit, float srcPressShadowRadius) {
        setSrcPressShadowRadiusRaw(applyDimension(unit, srcPressShadowRadius, getViewDisplayMetrics(this)));
    }

    private void setSrcPressShadowRadiusRaw(float srcPressShadowRadius) {
        if (this.srcPressShadowRadius != srcPressShadowRadius) {
            this.srcPressShadowRadius = srcPressShadowRadius;
            invalidate();
        }
    }

    // default srcNormalShadowDx
    private float srcPressShadowDx;

    public float getSrcPressShadowDx() {
        return srcPressShadowDx;
    }

    public void setSrcPressShadowDx(float srcPressShadowDx) {
        setSrcPressShadowDx(TypedValue.COMPLEX_UNIT_DIP, srcPressShadowDx);
    }

    public void setSrcPressShadowDx(int unit, float srcPressShadowDx) {
        setSrcPressShadowDxRaw(applyDimension(unit, srcPressShadowDx, getViewDisplayMetrics(this)));
    }

    private void setSrcPressShadowDxRaw(float srcPressShadowDx) {
        if (this.srcPressShadowDx != srcPressShadowDx) {
            this.srcPressShadowDx = srcPressShadowDx;
            invalidate();
        }
    }

    // default srcNormalShadowDy
    private float srcPressShadowDy;

    public float getSrcPressShadowDy() {
        return srcPressShadowDy;
    }

    public void setSrcPressShadowDy(float srcPressShadowDy) {
        setSrcPressShadowDy(TypedValue.COMPLEX_UNIT_DIP, srcPressShadowDy);
    }

    public void setSrcPressShadowDy(int unit, float srcPressShadowDy) {
        setSrcPressShadowDyRaw(applyDimension(unit, srcPressShadowDy, getViewDisplayMetrics(this)));
    }

    private void setSrcPressShadowDyRaw(float srcPressShadowDy) {
        if (this.srcPressShadowDy != srcPressShadowDy) {
            this.srcPressShadowDy = srcPressShadowDy;
            invalidate();
        }
    }

    // default srcNormalShadowRadius
    private float srcSelectShadowRadius;

    public float getSrcSelectShadowRadius() {
        return srcSelectShadowRadius;
    }

    public void setSrcSelectShadowRadius(float srcSelectShadowRadius) {
        setSrcSelectShadowRadius(TypedValue.COMPLEX_UNIT_DIP, srcSelectShadowRadius);
    }

    public void setSrcSelectShadowRadius(int unit, float srcSelectShadowRadius) {
        setSrcSelectShadowRadiusRaw(applyDimension(unit, srcSelectShadowRadius, getViewDisplayMetrics(this)));
    }

    private void setSrcSelectShadowRadiusRaw(float srcSelectShadowRadius) {
        if (this.srcSelectShadowRadius != srcSelectShadowRadius) {
            this.srcSelectShadowRadius = srcSelectShadowRadius;
            invalidate();
        }
    }

    // default srcNormalShadowDx
    private float srcSelectShadowDx;

    public float getSrcSelectShadowDx() {
        return srcSelectShadowDx;
    }

    public void setSrcSelectShadowDx(float srcSelectShadowDx) {
        setSrcSelectShadowDx(TypedValue.COMPLEX_UNIT_DIP, srcSelectShadowDx);
    }

    public void setSrcSelectShadowDx(int unit, float srcSelectShadowDx) {
        setSrcSelectShadowDxRaw(applyDimension(unit, srcSelectShadowDx, getViewDisplayMetrics(this)));
    }

    private void setSrcSelectShadowDxRaw(float srcSelectShadowDx) {
        if (this.srcSelectShadowDx != srcSelectShadowDx) {
            this.srcSelectShadowDx = srcSelectShadowDx;
            invalidate();
        }
    }

    // default srcNormalShadowDy
    private float srcSelectShadowDy;

    public float getSrcSelectShadowDy() {
        return srcSelectShadowDy;
    }

    public void setSrcSelectShadowDy(float srcSelectShadowDy) {
        setSrcSelectShadowDy(TypedValue.COMPLEX_UNIT_DIP, srcSelectShadowDy);
    }

    public void setSrcSelectShadowDy(int unit, float srcSelectShadowDy) {
        setSrcSelectShadowDyRaw(applyDimension(unit, srcSelectShadowDy, getViewDisplayMetrics(this)));
    }

    private void setSrcSelectShadowDyRaw(float srcSelectShadowDy) {
        if (this.srcSelectShadowDy != srcSelectShadowDy) {
            this.srcSelectShadowDy = srcSelectShadowDy;
            invalidate();
        }
    }

    public static final int LEFT = 0x00000000;
    public static final int CENTER_HORIZONTAL = 0x00000001;
    public static final int RIGHT = CENTER_HORIZONTAL << 1;
    public static final int TOP = 0x00000000;
    public static final int CENTER_VERTICAL = RIGHT << 1;
    public static final int BOTTOM = CENTER_VERTICAL << 1;
    public static final int CENTER = CENTER_HORIZONTAL | CENTER_VERTICAL;
    public static final int GRAVITY_MASK = CENTER_HORIZONTAL | RIGHT | CENTER_VERTICAL | BOTTOM;

    //
    private int srcAnchorGravity;

    public int getDownloadMarkGravity() {
        return srcAnchorGravity;
    }

    public void setSrcAnchorGravity(int srcAnchorGravity) {
        this.srcAnchorGravity = srcAnchorGravity;
    }

    // anchor Normal,Press,Select use one Matrix
    protected Matrix anchorMatrix;

    public Matrix getAnchorMatrix() {
        return anchorMatrix;
    }

    public void setAnchorMatrix(Matrix anchorMatrix) {
        this.anchorMatrix = anchorMatrix;
    }

    protected Matrix initanchorMatrix(float sx, float sy) {
        if (anchorMatrix == null) {
            anchorMatrix = new Matrix();
        }
        anchorMatrix.reset();
        anchorMatrix.setScale(sx, sy);
        return anchorMatrix;
    }

    //
    private Bitmap srcAnchorNormal;

    public Bitmap getSrcAnchorNormal() {
        return srcAnchorNormal;
    }

    public void setSrcAnchorNormal(Bitmap srcAnchorNormal) {
        this.srcAnchorNormal = srcAnchorNormal;
    }

    //
    private float srcAnchorNormalWidth;

    public float getSrcAnchorNormalWidth() {
        return srcAnchorNormalWidth;
    }

    public void setSrcAnchorNormalWidth(float srcAnchorNormalWidth) {
        setSrcAnchorNormalWidth(TypedValue.COMPLEX_UNIT_DIP, srcAnchorNormalWidth);
    }

    public void setSrcAnchorNormalWidth(int unit, float srcAnchorNormalWidth) {
        setSrcAnchorNormalWidthRaw(applyDimension(unit, srcAnchorNormalWidth, getViewDisplayMetrics(this)));
    }

    private void setSrcAnchorNormalWidthRaw(float srcAnchorNormalWidth) {
        if (this.srcAnchorNormalWidth != srcAnchorNormalWidth) {
            this.srcAnchorNormalWidth = srcAnchorNormalWidth;
            invalidate();
        }
    }

    //
    private float srcAnchorNormalHeight;

    public float getSrcAnchorNormalHeight() {
        return srcAnchorNormalHeight;
    }

    public void setSrcAnchorNormalHeight(float srcAnchorNormalHeight) {
        setSrcAnchorNormalHeight(TypedValue.COMPLEX_UNIT_DIP, srcAnchorNormalHeight);
    }

    public void setSrcAnchorNormalHeight(int unit, float srcAnchorNormalHeight) {
        setSrcAnchorNormalHeightRaw(applyDimension(unit, srcAnchorNormalHeight, getViewDisplayMetrics(this)));
    }

    private void setSrcAnchorNormalHeightRaw(float srcAnchorNormalHeight) {
        if (this.srcAnchorNormalHeight != srcAnchorNormalHeight) {
            this.srcAnchorNormalHeight = srcAnchorNormalHeight;
            invalidate();
        }
    }

    //
    private float srcAnchorNormalDx;

    public float getSrcAnchorNormalDx() {
        return srcAnchorNormalDx;
    }

    public void setSrcAnchorNormalDx(float srcAnchorNormalDx) {
        setSrcAnchorNormalDx(TypedValue.COMPLEX_UNIT_DIP, srcAnchorNormalDx);
    }

    public void setSrcAnchorNormalDx(int unit, float srcAnchorNormalDx) {
        setSrcAnchorNormalDxRaw(applyDimension(unit, srcAnchorNormalDx, getViewDisplayMetrics(this)));
    }

    private void setSrcAnchorNormalDxRaw(float srcAnchorNormalDx) {
        if (this.srcAnchorNormalDx != srcAnchorNormalDx) {
            this.srcAnchorNormalDx = srcAnchorNormalDx;
            invalidate();
        }
    }

    //
    private float srcAnchorNormalDy;

    public float getSrcAnchorNormalDy() {
        return srcAnchorNormalDy;
    }

    public void setSrcAnchorNormalDy(float srcAnchorNormalDy) {
        setSrcAnchorNormalDy(TypedValue.COMPLEX_UNIT_DIP, srcAnchorNormalDy);
    }

    public void setSrcAnchorNormalDy(int unit, float srcAnchorNormalDy) {
        setSrcAnchorNormalDyRaw(applyDimension(unit, srcAnchorNormalDy, getViewDisplayMetrics(this)));
    }

    private void setSrcAnchorNormalDyRaw(float srcAnchorNormalDy) {
        if (this.srcAnchorNormalDy != srcAnchorNormalDy) {
            this.srcAnchorNormalDy = srcAnchorNormalDy;
            invalidate();
        }
    }

    //
    private Bitmap srcAnchorPress;

    public Bitmap getSrcAnchorPress() {
        return srcAnchorPress;
    }

    public void setSrcAnchorPress(Bitmap srcAnchorPress) {
        this.srcAnchorPress = srcAnchorPress;
    }

    //
    private float srcAnchorPressWidth;

    public float getSrcAnchorPressWidth() {
        return srcAnchorPressWidth;
    }

    public void setSrcAnchorPressWidth(float srcAnchorPressWidth) {
        setSrcAnchorPressWidth(TypedValue.COMPLEX_UNIT_DIP, srcAnchorPressWidth);
    }

    public void setSrcAnchorPressWidth(int unit, float srcAnchorPressWidth) {
        setSrcAnchorPressWidthRaw(applyDimension(unit, srcAnchorPressWidth, getViewDisplayMetrics(this)));
    }

    private void setSrcAnchorPressWidthRaw(float srcAnchorPressWidth) {
        if (this.srcAnchorPressWidth != srcAnchorPressWidth) {
            this.srcAnchorPressWidth = srcAnchorPressWidth;
            invalidate();
        }
    }

    //
    private float srcAnchorPressHeight;

    public float getSrcAnchorPressHeight() {
        return srcAnchorPressHeight;
    }

    public void setSrcAnchorPressHeight(float srcAnchorPressHeight) {
        setSrcAnchorPressHeight(TypedValue.COMPLEX_UNIT_DIP, srcAnchorPressHeight);
    }

    public void setSrcAnchorPressHeight(int unit, float srcAnchorPressHeight) {
        setSrcAnchorPressHeightRaw(applyDimension(unit, srcAnchorPressHeight, getViewDisplayMetrics(this)));
    }

    private void setSrcAnchorPressHeightRaw(float srcAnchorPressHeight) {
        if (this.srcAnchorPressHeight != srcAnchorPressHeight) {
            this.srcAnchorPressHeight = srcAnchorPressHeight;
            invalidate();
        }
    }

    //
    private float srcAnchorPressDx;

    public float getSrcAnchorPressDx() {
        return srcAnchorPressDx;
    }

    public void setSrcAnchorPressDx(float srcAnchorPressDx) {
        setSrcAnchorPressDx(TypedValue.COMPLEX_UNIT_DIP, srcAnchorPressDx);
    }

    public void setSrcAnchorPressDx(int unit, float srcAnchorPressDx) {
        setSrcAnchorPressDxRaw(applyDimension(unit, srcAnchorPressDx, getViewDisplayMetrics(this)));
    }

    private void setSrcAnchorPressDxRaw(float srcAnchorPressDx) {
        if (this.srcAnchorPressDx != srcAnchorPressDx) {
            this.srcAnchorPressDx = srcAnchorPressDx;
            invalidate();
        }
    }

    //
    private float srcAnchorPressDy;

    public float getSrcAnchorPressDy() {
        return srcAnchorPressDy;
    }

    public void setSrcAnchorPressDy(float srcAnchorPressDy) {
        setSrcAnchorPressDy(TypedValue.COMPLEX_UNIT_DIP, srcAnchorPressDy);
    }

    public void setSrcAnchorPressDy(int unit, float srcAnchorPressDy) {
        setSrcAnchorPressDyRaw(applyDimension(unit, srcAnchorPressDy, getViewDisplayMetrics(this)));
    }

    private void setSrcAnchorPressDyRaw(float srcAnchorPressDy) {
        if (this.srcAnchorPressDy != srcAnchorPressDy) {
            this.srcAnchorPressDy = srcAnchorPressDy;
            invalidate();
        }
    }

    //
    private Bitmap srcAnchorSelect;

    public Bitmap getSrcAnchorSelect() {
        return srcAnchorSelect;
    }

    public void setSrcAnchorSelect(Bitmap srcAnchorSelect) {
        this.srcAnchorSelect = srcAnchorSelect;
    }

    //
    private float srcAnchorSelectWidth;

    public float getSrcAnchorSelectWidth() {
        return srcAnchorSelectWidth;
    }

    public void setSrcAnchorSelectWidth(float srcAnchorSelectWidth) {
        setSrcAnchorSelectWidth(TypedValue.COMPLEX_UNIT_DIP, srcAnchorSelectWidth);
    }

    public void setSrcAnchorSelectWidth(int unit, float srcAnchorSelectWidth) {
        setSrcAnchorSelectWidthRaw(applyDimension(unit, srcAnchorSelectWidth, getViewDisplayMetrics(this)));
    }

    private void setSrcAnchorSelectWidthRaw(float srcAnchorSelectWidth) {
        if (this.srcAnchorSelectWidth != srcAnchorSelectWidth) {
            this.srcAnchorSelectWidth = srcAnchorSelectWidth;
            invalidate();
        }
    }

    //
    private float srcAnchorSelectHeight;

    public float getSrcAnchorSelectHeight() {
        return srcAnchorSelectHeight;
    }

    public void setSrcAnchorSelectHeight(float srcAnchorSelectHeight) {
        setSrcAnchorSelectHeight(TypedValue.COMPLEX_UNIT_DIP, srcAnchorSelectHeight);
    }

    public void setSrcAnchorSelectHeight(int unit, float srcAnchorSelectHeight) {
        setSrcAnchorSelectHeightRaw(applyDimension(unit, srcAnchorSelectHeight, getViewDisplayMetrics(this)));
    }

    private void setSrcAnchorSelectHeightRaw(float srcAnchorSelectHeight) {
        if (this.srcAnchorSelectHeight != srcAnchorSelectHeight) {
            this.srcAnchorSelectHeight = srcAnchorSelectHeight;
            invalidate();
        }
    }

    //
    private float srcAnchorSelectDx;

    public float getSrcAnchorSelectDx() {
        return srcAnchorSelectDx;
    }

    public void setSrcAnchorSelectDx(float srcAnchorSelectDx) {
        setSrcAnchorSelectDx(TypedValue.COMPLEX_UNIT_DIP, srcAnchorSelectDx);
    }

    public void setSrcAnchorSelectDx(int unit, float srcAnchorSelectDx) {
        setSrcAnchorSelectDxRaw(applyDimension(unit, srcAnchorSelectDx, getViewDisplayMetrics(this)));
    }

    private void setSrcAnchorSelectDxRaw(float srcAnchorSelectDx) {
        if (this.srcAnchorSelectDx != srcAnchorSelectDx) {
            this.srcAnchorSelectDx = srcAnchorSelectDx;
            invalidate();
        }
    }

    //
    private float srcAnchorSelectDy;

    public float getSrcAnchorSelectDy() {
        return srcAnchorSelectDy;
    }

    public void setSrcAnchorSelectDy(float srcAnchorSelectDy) {
        setSrcAnchorSelectDy(TypedValue.COMPLEX_UNIT_DIP, srcAnchorSelectDy);
    }

    public void setSrcAnchorSelectDy(int unit, float srcAnchorSelectDy) {
        setSrcAnchorSelectDyRaw(applyDimension(unit, srcAnchorSelectDy, getViewDisplayMetrics(this)));
    }

    private void setSrcAnchorSelectDyRaw(float srcAnchorSelectDy) {
        if (this.srcAnchorSelectDy != srcAnchorSelectDy) {
            this.srcAnchorSelectDy = srcAnchorSelectDy;
            invalidate();
        }
    }

    // strokeWidthNormal default 0
    private float strokeWidthNormal;

    public float getStrokeWidthNormal() {
        return strokeWidthNormal;
    }

    public void setStrokeWidthNormal(float strokeWidthNormal) {
        setStrokeWidthNormal(TypedValue.COMPLEX_UNIT_DIP, strokeWidthNormal);
    }

    public void setStrokeWidthNormal(int unit, float strokeWidthNormal) {
        setStrokeWidthNormalRaw(applyDimension(unit, strokeWidthNormal, getViewDisplayMetrics(this)));
    }

    private void setStrokeWidthNormalRaw(float strokeWidthNormal) {
        if (this.strokeWidthNormal != strokeWidthNormal) {
            this.strokeWidthNormal = strokeWidthNormal;
            invalidate();
        }
    }

    // strokeColorNormal default transparent
    private int strokeColorNormal;

    public int getStrokeColorNormal() {
        return strokeColorNormal;
    }

    public void setStrokeColorNormal(int strokeColorNormal) {
        this.strokeColorNormal = strokeColorNormal;
    }

    // strokeWidthPress default strokeWidthNormal
    private float strokeWidthPress;

    public float getStrokeWidthPress() {
        return strokeWidthPress;
    }

    public void setStrokeWidthPress(float strokeWidthPress) {
        setStrokeWidthPress(TypedValue.COMPLEX_UNIT_DIP, strokeWidthPress);
    }

    public void setStrokeWidthPress(int unit, float strokeWidthPress) {
        setStrokeWidthPressRaw(applyDimension(unit, strokeWidthPress, getViewDisplayMetrics(this)));
    }

    private void setStrokeWidthPressRaw(float strokeWidthPress) {
        if (this.strokeWidthPress != strokeWidthPress) {
            this.strokeWidthPress = strokeWidthPress;
            invalidate();
        }
    }

    // strokeColorPress default strokeColorNormal
    private int strokeColorPress;

    public int getStrokeColorPress() {
        return strokeColorPress;
    }

    public void setStrokeColorPress(int strokeColorPress) {
        this.strokeColorPress = strokeColorPress;
    }

    // strokeWidthSelect default strokeWidthNormal
    private float strokeWidthSelect;

    public float getStrokeWidthSelect() {
        return strokeWidthSelect;
    }

    public void setStrokeWidthSelect(float strokeWidthSelect) {
        setStrokeWidthSelect(TypedValue.COMPLEX_UNIT_DIP, strokeWidthSelect);
    }

    public void setStrokeWidthSelect(int unit, float strokeWidthSelect) {
        setStrokeWidthSelectRaw(applyDimension(unit, strokeWidthSelect, getViewDisplayMetrics(this)));
    }

    private void setStrokeWidthSelectRaw(float strokeWidthSelect) {
        if (this.strokeWidthSelect != strokeWidthSelect) {
            this.strokeWidthSelect = strokeWidthSelect;
            invalidate();
        }
    }

    // strokeColorSelect default strokeColorNormal
    private int strokeColorSelect;

    public int getStrokeColorSelect() {
        return strokeColorSelect;
    }

    public void setStrokeColorSelect(int strokeColorSelect) {
        this.strokeColorSelect = strokeColorSelect;
    }

    // radius default 0
    private float radius;

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        setRadius(TypedValue.COMPLEX_UNIT_DIP, radius);
    }

    public void setRadius(int unit, float radius) {
        setRadiusRaw(applyDimension(unit, radius, getViewDisplayMetrics(this)));
    }

    private void setRadiusRaw(float radius) {
        if (this.radius != radius) {
            this.radius = radius;
            invalidate();
        }
    }

    // radius of draw custom roundRect
    // radiusLeftTop,radiusLeftBottom,radiusRightTop,radiusRightBottom default 0
    private float radiusLeftTop;

    public float getRadiusLeftTop() {
        return radiusLeftTop;
    }

    public void setRadiusLeftTop(float radiusLeftTop) {
        setRadiusLeftTop(TypedValue.COMPLEX_UNIT_DIP, radiusLeftTop);
    }

    public void setRadiusLeftTop(int unit, float radiusLeftTop) {
        setRadiusLeftTopRaw(applyDimension(unit, radiusLeftTop, getViewDisplayMetrics(this)));
    }

    private void setRadiusLeftTopRaw(float radiusLeftTop) {
        if (this.radiusLeftTop != radiusLeftTop) {
            this.radiusLeftTop = radiusLeftTop;
            invalidate();
        }
    }

    //
    private float radiusLeftBottom;

    public float getRadiusLeftBottom() {
        return radiusLeftBottom;
    }

    public void setRadiusLeftBottom(float radiusLeftBottom) {
        setRadiusLeftBottom(TypedValue.COMPLEX_UNIT_DIP, radiusLeftBottom);
    }

    public void setRadiusLeftBottom(int unit, float radiusLeftBottom) {
        setRadiusLeftBottomRaw(applyDimension(unit, radiusLeftBottom, getViewDisplayMetrics(this)));
    }

    private void setRadiusLeftBottomRaw(float radiusLeftBottom) {
        if (this.radiusLeftBottom != radiusLeftBottom) {
            this.radiusLeftBottom = radiusLeftBottom;
            invalidate();
        }
    }

    //
    private float radiusRightTop;

    public float getRadiusRightTop() {
        return radiusRightTop;
    }

    public void setRadiusRightTop(float radiusRightTop) {
        setRadiusRightTop(TypedValue.COMPLEX_UNIT_DIP, radiusRightTop);
    }

    public void setRadiusRightTop(int unit, float radiusRightTop) {
        setRadiusRightTopRaw(applyDimension(unit, radiusRightTop, getViewDisplayMetrics(this)));
    }

    private void setRadiusRightTopRaw(float radiusRightTop) {
        if (this.radiusRightTop != radiusRightTop) {
            this.radiusRightTop = radiusRightTop;
            invalidate();
        }
    }

    //
    private float radiusRightBottom;

    public float getRadiusRightBottom() {
        return radiusRightBottom;
    }

    public void setRadiusRightBottom(float radiusRightBottom) {
        setRadiusRightBottom(TypedValue.COMPLEX_UNIT_DIP, radiusRightBottom);
    }

    public void setRadiusRightBottom(int unit, float radiusRightBottom) {
        setRadiusRightBottomRaw(applyDimension(unit, radiusRightBottom, getViewDisplayMetrics(this)));
    }

    private void setRadiusRightBottomRaw(float radiusRightBottom) {
        if (this.radiusRightBottom != radiusRightBottom) {
            this.radiusRightBottom = radiusRightBottom;
            invalidate();
        }
    }

    // textMark default false
    protected boolean textMark;

    public boolean istextMark() {
        return textMark;
    }

    public void setTextMark(boolean textMark) {
        this.textMark = textMark;
        invalidate();
    }

    public void setTextMark(String textMarkTextValue) {
        this.textMarkTextValue = textMarkTextValue;
        requestLayout();
    }

    public void setTextMark(float textMarkRadius, int textMarkColor, String textMarkTextValue, float textMarkTextSize, int textMarkTextColor,
                                float textMarkDx, float textMarkDy) {
        setTextMark(TypedValue.COMPLEX_UNIT_DIP, textMarkRadius, textMarkColor, textMarkTextValue, TypedValue.COMPLEX_UNIT_DIP, textMarkTextSize,
                textMarkTextColor, TypedValue.COMPLEX_UNIT_DIP, textMarkDx, TypedValue.COMPLEX_UNIT_DIP, textMarkDy);
    }

    public void setTextMark(int textMarkRadiusUnit, float textMarkRadius, int textMarkColor, String textMarkTextValue, int textMarkTextSizeUnit,
                                float textMarkTextSize, int textMarkTextColor, int textMarkDxUnit, float textMarkDx, int textMarkDyUnit, float textMarkDy) {

        DisplayMetrics displayMetrics = getViewDisplayMetrics(this);

        setTextMarkRaw(applyDimension(textMarkRadiusUnit, textMarkRadius, displayMetrics), textMarkColor, textMarkTextValue,
                applyDimension(textMarkTextSizeUnit, textMarkTextSize, displayMetrics), textMarkTextColor,
                applyDimension(textMarkDxUnit, textMarkDx, displayMetrics), applyDimension(textMarkDyUnit, textMarkDy, displayMetrics));
    }

    private void setTextMarkRaw(float textMarkRadius, int textMarkColor, String textMarkTextValue, float textMarkTextSize, int textMarkTextColor,
                                    float textMarkDx, float textMarkDy) {
        if (this.textMarkRadius != textMarkRadius || this.textMarkColor != textMarkColor || this.textMarkTextValue != textMarkTextValue
                || this.textMarkTextSize != textMarkTextSize || this.textMarkTextColor != textMarkTextColor || this.textMarkDx != textMarkDx
                || this.textMarkDy != textMarkDy) {
            this.textMarkRadius = textMarkRadius;
            this.textMarkColor = textMarkColor;
            this.textMarkTextValue = textMarkTextValue;
            this.textMarkTextSize = textMarkTextSize;
            this.textMarkTextColor = textMarkTextColor;
            this.textMarkDx = textMarkDx;
            this.textMarkDy = textMarkDy;
            this.textMark = true;
            invalidate();
        }
    }

    // textMarkTouchable default false
    private boolean textMarkTouchable;

    public boolean istextMarkTouchable() {
        return textMarkTouchable;
    }

    public void setTextMarkTouchable(boolean textMarkTouchable) {
        this.textMarkTouchable = textMarkTouchable;
    }

    // textMarkRadius default 0
    private float textMarkRadius;

    public float getTextMarkRadius() {
        return textMarkRadius;
    }

    public void setTextMarkRadius(float textMarkRadius) {
        setTextMarkRadius(TypedValue.COMPLEX_UNIT_DIP, textMarkRadius);
    }

    public void setTextMarkRadius(int unit, float textMarkRadius) {
        setTextMarkRadiusRaw(applyDimension(unit, textMarkRadius, getViewDisplayMetrics(this)));
    }

    private void setTextMarkRadiusRaw(float textMarkRadius) {
        if (this.textMarkRadius != textMarkRadius) {
            this.textMarkRadius = textMarkRadius;
            invalidate();
        }
    }

    // textMarkColor default transparent
    private int textMarkColor;

    public int getTextMarkColor() {
        return textMarkColor;
    }

    public void setTextMarkColor(int textMarkColor) {
        this.textMarkColor = textMarkColor;
    }

    //
    private String textMarkTextValue;

    public String getTextMarkTextValue() {
        return textMarkTextValue;
    }

    public void setTextMarkTextValue(String textMarkTextValue) {
        this.textMarkTextValue = textMarkTextValue;
        requestLayout();
    }

    private List<Integer> textMarkTextValueMeasureList;

    public List<Integer> getTextMarkTextValueMeasureList() {
        return textMarkTextValueMeasureList;
    }

    public void setTextMarkTextValueMeasureList(List<Integer> textMarkTextValueMeasureList) {
        this.textMarkTextValueMeasureList = textMarkTextValueMeasureList;
    }

    //
    private float textMarkTextSize;

    public float getTextMarkTextSize() {
        return textMarkTextSize;
    }

    public void setTextMarkTextSize(float textMarkTextSize) {
        setTextMarkTextSize(TypedValue.COMPLEX_UNIT_DIP, textMarkTextSize);
    }

    public void setTextMarkTextSize(int unit, float textMarkTextSize) {
        setTextMarkTextSizeRaw(applyDimension(unit, textMarkTextSize, getViewDisplayMetrics(this)));
    }

    private void setTextMarkTextSizeRaw(float textMarkTextSize) {
        if (this.textMarkTextSize != textMarkTextSize) {
            this.textMarkTextSize = textMarkTextSize;
            invalidate();
        }
    }

    //
    private int textMarkTextColor;

    public int getTextMarkTextColor() {
        return textMarkTextColor;
    }

    public void setTextMarkTextColor(int textMarkTextColor) {
        this.textMarkTextColor = textMarkTextColor;
    }

    //
    private float textMarkDx;

    public float getTextMarkDx() {
        return textMarkDx;
    }

    public void setTextMarkDx(float textMarkDx) {
        setTextMarkDx(TypedValue.COMPLEX_UNIT_DIP, textMarkDx);
    }

    public void setTextMarkDx(int unit, float textMarkDx) {
        setTextMarkDxRaw(applyDimension(unit, textMarkDx, getViewDisplayMetrics(this)));
    }

    private void setTextMarkDxRaw(float textMarkDx) {
        if (this.textMarkDx != textMarkDx) {
            this.textMarkDx = textMarkDx;
            invalidate();
        }
    }

    //
    private float textMarkDy;

    public float getTextMarkDy() {
        return textMarkDy;
    }

    public void setTextMarkDy(float textMarkDy) {
        setTextMarkDy(TypedValue.COMPLEX_UNIT_DIP, textMarkDy);
    }

    public void setTextMarkDy(int unit, float textMarkDy) {
        setTextMarkDyRaw(applyDimension(unit, textMarkDy, getViewDisplayMetrics(this)));
    }

    private void setTextMarkDyRaw(float textMarkDy) {
        if (this.textMarkDy != textMarkDy) {
            this.textMarkDy = textMarkDy;
            invalidate();
        }
    }

    //
    private float textMarkFractionDx;

    public float getTextMarkFractionDx() {
        return textMarkFractionDx;
    }

    public void setTextMarkFractionDx(float textMarkFractionDx) {
        this.textMarkFractionDx = textMarkFractionDx;
    }

    //
    private float textMarkFractionDy;

    public float getTextMarkFractionDy() {
        return textMarkFractionDy;
    }

    public void setTextMarkFractionDy(float textMarkFractionDy) {
        this.textMarkFractionDy = textMarkFractionDy;
    }

    // textValue default null
    private String textValue;

    public String getTextValue() {
        return textValue;
    }

    public void setTextValue(String textValue) {
        this.textValue = textValue;
        requestLayout();
    }

    private List<Integer> textValueMeasureList;

    public List<Integer> getTextValueMeasureList() {
        return textValueMeasureList;
    }

    public void setTextValueMeasureList(List<Integer> textValueMeasureList) {
        this.textValueMeasureList = textValueMeasureList;
    }

    // textSize default 0
    private float textSize;

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSize);
    }

    public void setTextSize(int unit, float textSize) {
        setTextSizeRaw(applyDimension(unit, textSize, getViewDisplayMetrics(this)));
    }

    private void setTextSizeRaw(float textSize) {
        if (this.textSize != textSize) {
            this.textSize = textSize;
            invalidate();
        }
    }

    // textColorNormal default transparent
    private int textColorNormal;

    public int getTextColorNormal() {
        return textColorNormal;
    }

    public void setTextColorNormal(int textColorNormal) {
        this.textColorNormal = textColorNormal;
    }

    // textColorPress default textColorNormal
    private int textColorPress;

    public int getTextColorPress() {
        return textColorPress;
    }

    public void setTextColorPress(int textColorPress) {
        this.textColorPress = textColorPress;
    }

    // textColorSelect default textColorNormal
    private int textColorSelect;

    public int getTextColorSelect() {
        return textColorSelect;
    }

    public void setTextColorSelect(int textColorSelect) {
        this.textColorSelect = textColorSelect;
    }

    // textPaddingLeft means distance between srcLeft and The
    // leftmost,note about the srcLeftPadding
    private float textPaddingLeft;

    public float getTextPaddingLeft() {
        return textPaddingLeft;
    }

    public void setTextPaddingLeft(float textPaddingLeft) {
        setTextPaddingLeft(TypedValue.COMPLEX_UNIT_DIP, textPaddingLeft);
    }

    public void setTextPaddingLeft(int unit, float textPaddingLeft) {
        setTextPaddingLeftRaw(applyDimension(unit, textPaddingLeft, getViewDisplayMetrics(this)));
    }

    private void setTextPaddingLeftRaw(float textPaddingLeft) {
        if (this.textPaddingLeft != textPaddingLeft) {
            this.textPaddingLeft = textPaddingLeft;
            invalidate();
        }
    }

    // textPaddingRight means distance between srcRight and The
    // rightmost,note about the srcRightPadding
    private float textPaddingRight;

    public float getTextPaddingRight() {
        return textPaddingRight;
    }

    public void setTextPaddingRight(float textPaddingRight) {
        setTextPaddingRight(TypedValue.COMPLEX_UNIT_DIP, textPaddingRight);
    }

    public void setTextPaddingRight(int unit, float textPaddingRight) {
        setTextPaddingRightRaw(applyDimension(unit, textPaddingRight, getViewDisplayMetrics(this)));
    }

    private void setTextPaddingRightRaw(float textPaddingRight) {
        if (this.textPaddingRight != textPaddingRight) {
            this.textPaddingRight = textPaddingRight;
            invalidate();
        }
    }

    //
    private float textRowSpaceRatio = 1.0f;

    public float getTextRowSpaceRatio() {
        return textRowSpaceRatio;
    }

    public void setTextRowSpaceRatio(float textRowSpaceRatio) {
        this.textRowSpaceRatio = textRowSpaceRatio;
    }

    //
    private TextGravity textGravity;

    public enum TextGravity {
        ALL_CENTER(0), ALL_LEFT(1), CENTER_LEFT(2), LEFT_CENTER(3);
        final int nativeInt;

        TextGravity(int ni) {
            nativeInt = ni;
        }
    }

    private static final TextGravity[] textGravityArray = {TextGravity.ALL_CENTER, TextGravity.ALL_LEFT, TextGravity.CENTER_LEFT, TextGravity.LEFT_CENTER,};

    public TextGravity getTextGravity() {
        return textGravity;
    }

    public void setTextGravity(TextGravity textGravity) {
        this.textGravity = textGravity;
    }

    //
    private Typeface textTypeFace;

    public enum TextTypeFace {
        NORMAL(0), BOLD(1), ITALIC(2), BOLD_ITALIC(3);
        final int nativeInt;

        TextTypeFace(int ni) {
            nativeInt = ni;
        }
    }

    private static final int[] textTypeFaceArray = {Typeface.NORMAL, Typeface.BOLD, Typeface.ITALIC, Typeface.BOLD_ITALIC};

    public Typeface getTextTypeFace() {
        return textTypeFace;
    }

    public void setTextTypeFace(Typeface textTypeFace) {
        this.textTypeFace = textTypeFace;
    }

    // attention that textDx is the width of the base , textDy is the
    // height of the base
    private float textDx;

    public float getTextDx() {
        return textDx;
    }

    public void setTextDx(float textDx) {
        setTextDx(TypedValue.COMPLEX_UNIT_DIP, textDx);
    }

    public void setTextDx(int unit, float textDx) {
        setTextDxRaw(applyDimension(unit, textDx, getViewDisplayMetrics(this)));
    }

    private void setTextDxRaw(float textDx) {
        if (this.textDx != textDx) {
            this.textDx = textDx;
            invalidate();
        }
    }

    //
    private float textDy;

    public float getTextDy() {
        return textDy;
    }

    public void setTextDy(float textDy) {
        setTextDy(TypedValue.COMPLEX_UNIT_DIP, textDy);
    }

    public void setTextDy(int unit, float textDy) {
        setTextDyRaw(applyDimension(unit, textDy, getViewDisplayMetrics(this)));
    }

    private void setTextDyRaw(float textDy) {
        if (this.textDy != textDy) {
            this.textDy = textDy;
            invalidate();
        }
    }

    //
    private float textFractionDx;

    public float getTextFractionDx() {
        return textFractionDx;
    }

    public void setTextFractionDx(float textFractionDx) {
        this.textFractionDx = textFractionDx;
    }

    //
    private float textFractionDy;

    public float getTextFractionDy() {
        return textFractionDy;
    }

    public void setTextFractionDy(float textFractionDy) {
        this.textFractionDy = textFractionDy;
    }

    //
    private float textDrawWidth;
    private float textEndOffsetCenterX;
    private float textEndOffsetCenterY;

    //
    private float textShadowRadius;

    public float getTextShadowRadius() {
        return textShadowRadius;
    }

    public void setTextShadowRadius(float textShadowRadius) {
        setTextShadowRadius(TypedValue.COMPLEX_UNIT_DIP, textShadowRadius);
    }

    public void setTextShadowRadius(int unit, float textShadowRadius) {
        setTextShadowRadiusRaw(applyDimension(unit, textShadowRadius, getViewDisplayMetrics(this)));
    }

    private void setTextShadowRadiusRaw(float textShadowRadius) {
        if (this.textShadowRadius != textShadowRadius) {
            this.textShadowRadius = textShadowRadius;
            invalidate();
        }
    }

    //
    private int textShadowColor;

    public int getTextShadowColor() {
        return textShadowColor;
    }

    public void setTextShadowColor(int textShadowColor) {
        this.textShadowColor = textShadowColor;
    }

    //
    private float textShadowDx;

    public float getTextShadowDx() {
        return textShadowDx;
    }

    public void setTextShadowDx(float textShadowDx) {
        setTextShadowDx(TypedValue.COMPLEX_UNIT_DIP, textShadowDx);
    }

    public void setTextShadowDx(int unit, float textShadowDx) {
        setTextShadowDxRaw(applyDimension(unit, textShadowDx, getViewDisplayMetrics(this)));
    }

    private void setTextShadowDxRaw(float textShadowDx) {
        if (this.textShadowDx != textShadowDx) {
            this.textShadowDx = textShadowDx;
            invalidate();
        }
    }

    //
    private float textShadowDy;

    public float getTextShadowDy() {
        return textShadowDy;
    }

    public void setTextShadowDy(float textShadowDy) {
        setTextShadowDy(TypedValue.COMPLEX_UNIT_DIP, textShadowDy);
    }

    public void setTextShadowDy(int unit, float textShadowDy) {
        setTextShadowDyRaw(applyDimension(unit, textShadowDy, getViewDisplayMetrics(this)));
    }

    private void setTextShadowDyRaw(float textShadowDy) {
        if (this.textShadowDy != textShadowDy) {
            this.textShadowDy = textShadowDy;
            invalidate();
        }
    }

    // contentValue default null
    private String contentValue;

    public String getContentValue() {
        return contentValue;
    }

    public void setContentValue(String contentValue) {
        this.contentValue = contentValue;
        requestLayout();
    }

    private List<Integer> contentValueMeasureList;

    public List<Integer> getContentValueMeasureList() {
        return contentValueMeasureList;
    }

    public void setContentValueMeasureList(List<Integer> contentValueMeasureList) {
        this.contentValueMeasureList = contentValueMeasureList;
    }

    // contentSize default 0
    private float contentSize;

    public float getContentSize() {
        return contentSize;
    }

    public void setContentSize(float contentSize) {
        setContentSize(TypedValue.COMPLEX_UNIT_DIP, contentSize);
    }

    public void setContentSize(int unit, float contentSize) {
        setContentSizeRaw(applyDimension(unit, contentSize, getViewDisplayMetrics(this)));
    }

    private void setContentSizeRaw(float contentSize) {
        if (this.contentSize != contentSize) {
            this.contentSize = contentSize;
            invalidate();
        }
    }

    //
    private float contentShadowRadius;

    public float getContentShadowRadius() {
        return contentShadowRadius;
    }

    public void setContentShadowRadius(float contentShadowRadius) {
        setContentShadowRadius(TypedValue.COMPLEX_UNIT_DIP, contentShadowRadius);
    }

    public void setContentShadowRadius(int unit, float contentShadowRadius) {
        setContentShadowRadiusRaw(applyDimension(unit, contentShadowRadius, getViewDisplayMetrics(this)));
    }

    private void setContentShadowRadiusRaw(float contentShadowRadius) {
        if (this.contentShadowRadius != contentShadowRadius) {
            this.contentShadowRadius = contentShadowRadius;
            invalidate();
        }
    }

    //
    private int contentShadowColor;

    public int getContentShadowColor() {
        return contentShadowColor;
    }

    public void setContentShadowColor(int contentShadowColor) {
        this.contentShadowColor = contentShadowColor;
    }

    //
    private float contentShadowDx;

    public float getContentShadowDx() {
        return contentShadowDx;
    }

    public void setContentShadowDx(float contentShadowDx) {
        setContentShadowDx(TypedValue.COMPLEX_UNIT_DIP, contentShadowDx);
    }

    public void setContentShadowDx(int unit, float contentShadowDx) {
        setContentShadowDxRaw(applyDimension(unit, contentShadowDx, getViewDisplayMetrics(this)));
    }

    private void setContentShadowDxRaw(float contentShadowDx) {
        if (this.contentShadowDx != contentShadowDx) {
            this.contentShadowDx = contentShadowDx;
            invalidate();
        }
    }

    //
    private float contentShadowDy;

    public float getContentShadowDy() {
        return contentShadowDy;
    }

    public void setContentShadowDy(float contentShadowDy) {
        setContentShadowDy(TypedValue.COMPLEX_UNIT_DIP, contentShadowDy);
    }

    public void setContentShadowDy(int unit, float contentShadowDy) {
        setContentShadowDyRaw(applyDimension(unit, contentShadowDy, getViewDisplayMetrics(this)));
    }

    private void setContentShadowDyRaw(float contentShadowDy) {
        if (this.contentShadowDy != contentShadowDy) {
            this.contentShadowDy = contentShadowDy;
            invalidate();
        }
    }

    // contentColorNormal default transparent
    private int contentColorNormal;

    public int getContentColorNormal() {
        return contentColorNormal;
    }

    public void setContentColorNormal(int contentColorNormal) {
        this.contentColorNormal = contentColorNormal;
    }

    // contentColorPress default contentColorNormal
    private int contentColorPress;

    public int getContentColorPress() {
        return contentColorPress;
    }

    public void setContentColorPress(int contentColorPress) {
        this.contentColorPress = contentColorPress;
    }

    // contentColorSelect default contentColorNormal
    private int contentColorSelect;

    public int getContentColorSelect() {
        return contentColorSelect;
    }

    public void setContentColorSelect(int contentColorSelect) {
        this.contentColorSelect = contentColorSelect;
    }

    // contentPaddingLeft means distance between srcLeft and The
    // leftmost,note about the srcLeftPadding
    private float contentPaddingLeft;

    public float getContentPaddingLeft() {
        return contentPaddingLeft;
    }

    public void setContentPaddingLeft(float contentPaddingLeft) {
        setContentPaddingLeft(TypedValue.COMPLEX_UNIT_DIP, contentPaddingLeft);
    }

    public void setContentPaddingLeft(int unit, float contentPaddingLeft) {
        setContentPaddingLeftRaw(applyDimension(unit, contentPaddingLeft, getViewDisplayMetrics(this)));
    }

    private void setContentPaddingLeftRaw(float contentPaddingLeft) {
        if (this.contentPaddingLeft != contentPaddingLeft) {
            this.contentPaddingLeft = contentPaddingLeft;
            invalidate();
        }
    }

    // contentPaddingRight means distance between srcRight and The
    // rightmost,note about the srcRightPadding
    private float contentPaddingRight;

    public float getContentPaddingRight() {
        return contentPaddingRight;
    }

    public void setContentPaddingRight(float contentPaddingRight) {
        setContentPaddingRight(TypedValue.COMPLEX_UNIT_DIP, contentPaddingRight);
    }

    public void setContentPaddingRight(int unit, float contentPaddingRight) {
        setContentPaddingRightRaw(applyDimension(unit, contentPaddingRight, getViewDisplayMetrics(this)));
    }

    private void setContentPaddingRightRaw(float contentPaddingRight) {
        if (this.contentPaddingRight != contentPaddingRight) {
            this.contentPaddingRight = contentPaddingRight;
            invalidate();
        }
    }

    private float contentRowSpaceRatio = 1.0f;

    public float getContentRowSpaceRatio() {
        return contentRowSpaceRatio;
    }

    public void setContentRowSpaceRatio(float contentRowSpaceRatio) {
        this.contentRowSpaceRatio = contentRowSpaceRatio;
    }

    //
    private ContentGravity contentGravity;

    public enum ContentGravity {
        ALL_CENTER(0), ALL_LEFT(1), CENTER_LEFT(2), LEFT_CENTER(3);
        final int nativeInt;

        ContentGravity(int ni) {
            nativeInt = ni;
        }
    }

    private static final ContentGravity[] contentGravityArray = {ContentGravity.ALL_CENTER, ContentGravity.ALL_LEFT, ContentGravity.CENTER_LEFT, ContentGravity.LEFT_CENTER,};

    public ContentGravity getContentGravity() {
        return contentGravity;
    }

    public void setContentGravity(ContentGravity contentGravity) {
        this.contentGravity = contentGravity;
    }

    //
    private Typeface contentTypeFace;

    public enum ContentTypeFace {
        NORMAL(0), BOLD(1), ITALIC(2), BOLD_ITALIC(3);
        final int nativeInt;

        ContentTypeFace(int ni) {
            nativeInt = ni;
        }
    }

    private static final int[] contentTypeFaceArray = {Typeface.NORMAL, Typeface.BOLD, Typeface.ITALIC, Typeface.BOLD_ITALIC,};

    public Typeface getContentTypeFace() {
        return contentTypeFace;
    }

    public void Typeface(Typeface contentTypeFace) {
        this.contentTypeFace = contentTypeFace;
    }

    // attention that contentDx is the width of the base , contentDy is
    // the height of the base
    private float contentDx;

    public float getContenttDx() {
        return contentDx;
    }

    public void setContentDx(float contentDx) {
        setTextDx(TypedValue.COMPLEX_UNIT_DIP, contentDx);
    }

    public void setContentDx(int unit, float contentDx) {
        setContentDxRaw(applyDimension(unit, contentDx, getViewDisplayMetrics(this)));
    }

    private void setContentDxRaw(float contentDx) {
        if (this.contentDx != contentDx) {
            this.contentDx = contentDx;
            invalidate();
        }
    }

    //
    private float contentDy;

    public float getContentDy() {
        return contentDy;
    }

    public void setContentDy(float contentDy) {
        setContentDy(TypedValue.COMPLEX_UNIT_DIP, contentDy);
    }

    public void setContentDy(int unit, float contentDy) {
        setContentDyRaw(applyDimension(unit, contentDy, getViewDisplayMetrics(this)));
    }

    private void setContentDyRaw(float contentDy) {
        if (this.contentDy != contentDy) {
            this.contentDy = contentDy;
            invalidate();
        }
    }

    //
    private float contentFractionDx;

    public float getContentFractionDx() {
        return contentFractionDx;
    }

    public void setContentFractionDx(float contentFractionDx) {
        this.contentFractionDx = contentFractionDx;
    }

    //
    private float contentFractionDy;

    public float getContentFractionDy() {
        return contentFractionDy;
    }

    public void setContentFractionDy(float contentFractionDy) {
        this.contentFractionDy = contentFractionDy;
    }

    // contentMark default false
    protected boolean contentMark;

    public boolean iscontentMark() {
        return contentMark;
    }

    public void setContentMark(boolean contentMark) {
        this.contentMark = contentMark;
        invalidate();
    }

    public void setContentMark(String contentMarkTextValue) {
        this.contentMarkTextValue = contentMarkTextValue;
        invalidate();
    }

    public void setContentMark(float contentMarkRadius, int contentMarkColor, String contentMarkTextValue, float contentMarkTextSize,
                                   int contentMarkTextColor, float contentMarkDx, float contentMarkDy) {
        setContentMark(TypedValue.COMPLEX_UNIT_DIP, contentMarkRadius, contentMarkColor, contentMarkTextValue, TypedValue.COMPLEX_UNIT_DIP,
                contentMarkTextSize, contentMarkTextColor, TypedValue.COMPLEX_UNIT_DIP, contentMarkDx, TypedValue.COMPLEX_UNIT_DIP, contentMarkDy);
    }

    public void setContentMark(int contentMarkRadiusUnit, float contentMarkRadius, int contentMarkColor, String contentMarkTextValue,
                                   int contentMarkTextSizeUnit, float contentMarkTextSize, int contentMarkTextColor, int contentMarkDxUnit, float contentMarkDx,
                                   int contentMarkDyUnit, float contentMarkDy) {

        DisplayMetrics displayMetrics = getViewDisplayMetrics(this);

        setContentMarkRaw(applyDimension(contentMarkRadiusUnit, contentMarkRadius, displayMetrics), contentMarkColor, contentMarkTextValue,
                applyDimension(contentMarkTextSizeUnit, contentMarkTextSize, displayMetrics), contentMarkTextColor,
                applyDimension(contentMarkDxUnit, contentMarkDx, displayMetrics), applyDimension(contentMarkDyUnit, contentMarkDy, displayMetrics));
    }

    private void setContentMarkRaw(float contentMarkRadius, int contentMarkColor, String contentMarkTextValue, float contentMarkTextSize,
                                       int contentMarkTextColor, float contentMarkDx, float contentMarkDy) {
        if (this.contentMarkRadius != contentMarkRadius || this.contentMarkColor != contentMarkColor || this.contentMarkTextValue != contentMarkTextValue
                || this.contentMarkTextSize != contentMarkTextSize || this.contentMarkTextColor != contentMarkTextColor
                || this.contentMarkDx != contentMarkDx || this.contentMarkDy != contentMarkDy) {
            this.contentMarkRadius = contentMarkRadius;
            this.contentMarkColor = contentMarkColor;
            this.contentMarkTextValue = contentMarkTextValue;
            this.contentMarkTextSize = contentMarkTextSize;
            this.contentMarkTextColor = contentMarkTextColor;
            this.contentMarkDx = contentMarkDx;
            this.contentMarkDy = contentMarkDy;
            this.contentMark = true;
            invalidate();
        }
    }

    // contentMarkTouchable default false
    private boolean contentMarkTouchable;

    public boolean iscontentMarkTouchable() {
        return contentMarkTouchable;
    }

    public void setContentMarkTouchable(boolean contentMarkTouchable) {
        this.contentMarkTouchable = contentMarkTouchable;
    }

    // contentMarkRadius default 0
    private float contentMarkRadius;

    public float getContentMarkRadius() {
        return contentMarkRadius;
    }

    public void setContentMarkRadius(float contentMarkRadius) {
        setContentMarkRadius(TypedValue.COMPLEX_UNIT_DIP, contentMarkRadius);
    }

    public void setContentMarkRadius(int unit, float contentMarkRadius) {
        setContentMarkRadiusRaw(applyDimension(unit, contentMarkRadius, getViewDisplayMetrics(this)));
    }

    private void setContentMarkRadiusRaw(float contentMarkRadius) {
        if (this.contentMarkRadius != contentMarkRadius) {
            this.contentMarkRadius = contentMarkRadius;
            invalidate();
        }
    }

    // contentMarkColor default transparent
    private int contentMarkColor;

    public int getContentMarkColor() {
        return contentMarkColor;
    }

    public void setContentMarkColor(int contentMarkColor) {
        this.contentMarkColor = contentMarkColor;
    }

    //
    private String contentMarkTextValue;

    public String getContentMarkTextValue() {
        return contentMarkTextValue;
    }

    public void setContentMarkTextValue(String contentMarkTextValue) {
        this.contentMarkTextValue = contentMarkTextValue;
        requestLayout();
    }

    private List<Integer> contentMarkTextValueMeasureList;

    public List<Integer> getContentMarkTextValueMeasureList() {
        return contentMarkTextValueMeasureList;
    }

    public void setContentMarkTextValueMeasureList(List<Integer> contentMarkTextValueMeasureList) {
        this.contentMarkTextValueMeasureList = contentMarkTextValueMeasureList;
    }

    //
    private float contentMarkTextSize;

    public float getContentMarkTextSize() {
        return contentMarkTextSize;
    }

    public void setContentMarkTextSize(float contentMarkTextSize) {
        setContentMarkTextSize(TypedValue.COMPLEX_UNIT_DIP, contentMarkTextSize);
    }

    public void setContentMarkTextSize(int unit, float contentMarkTextSize) {
        setContentMarkTextSizeRaw(applyDimension(unit, contentMarkTextSize, getViewDisplayMetrics(this)));
    }

    private void setContentMarkTextSizeRaw(float contentMarkTextSize) {
        if (this.contentMarkTextSize != contentMarkTextSize) {
            this.contentMarkTextSize = contentMarkTextSize;
            invalidate();
        }
    }

    //
    private int contentMarkTextColor;

    public int getContentMarkTextColor() {
        return contentMarkTextColor;
    }

    public void setContentMarkTextColor(int contentMarkTextColor) {
        this.contentMarkTextColor = contentMarkTextColor;
    }

    //
    private float contentMarkDx;

    public float getContentMarkDx() {
        return contentMarkDx;
    }

    public void setContentMarkDx(float contentMarkDx) {
        setContentMarkDx(TypedValue.COMPLEX_UNIT_DIP, contentMarkDx);
    }

    public void setContentMarkDx(int unit, float contentMarkDx) {
        setContentMarkDxRaw(applyDimension(unit, contentMarkDx, getViewDisplayMetrics(this)));
    }

    private void setContentMarkDxRaw(float contentMarkDx) {
        if (this.contentMarkDx != contentMarkDx) {
            this.contentMarkDx = contentMarkDx;
            invalidate();
        }
    }

    //
    private float contentMarkDy;

    public float getContentMarkDy() {
        return contentMarkDy;
    }

    public void setContentMarkDy(float contentMarkDy) {
        setContentMarkDy(TypedValue.COMPLEX_UNIT_DIP, contentMarkDy);
    }

    public void setContentMarkDy(int unit, float contentMarkDy) {
        setContentMarkDyRaw(applyDimension(unit, contentMarkDy, getViewDisplayMetrics(this)));
    }

    private void setContentMarkDyRaw(float contentMarkDy) {
        if (this.contentMarkDy != contentMarkDy) {
            this.contentMarkDy = contentMarkDy;
            invalidate();
        }
    }

    //
    private float contentMarkFractionDx;

    public float getContentMarkFractionDx() {
        return contentMarkFractionDx;
    }

    public void setContentMarkFractionDx(float contentMarkFractionDx) {
        this.contentMarkFractionDx = contentMarkFractionDx;
    }

    //
    private float contentMarkFractionDy;

    public float getContentMarkFractionDy() {
        return contentMarkFractionDy;
    }

    public void setContentMarkFractionDy(float contentMarkFractionDy) {
        this.contentMarkFractionDy = contentMarkFractionDy;
    }

    //
    private float contentDrawWidth;
    private float contentEndOffsetCenterX;
    private float contentEndOffsetCenterY;

    //
    private Bitmap srcLeft;

    public Bitmap getSrcLeft() {
        return srcLeft;
    }

    public void setSrcLeft(Bitmap srcLeft) {
        this.srcLeft = srcLeft;
    }

    //
    private float srcLeftWidth;

    public float getSrcLeftWidth() {
        return srcLeftWidth;
    }

    public void setSrcLeftWidth(float srcLeftWidth) {
        setSrcLeftWidth(TypedValue.COMPLEX_UNIT_DIP, srcLeftWidth);
    }

    public void setSrcLeftWidth(int unit, float srcLeftWidth) {
        setSrcLeftWidthRaw(applyDimension(unit, srcLeftWidth, getViewDisplayMetrics(this)));
    }

    private void setSrcLeftWidthRaw(float srcLeftWidth) {
        if (this.srcLeftWidth != srcLeftWidth) {
            this.srcLeftWidth = srcLeftWidth;
            invalidate();
        }
    }

    //
    private float srcLeftHeight;

    public float getSrcLeftHeight() {
        return srcLeftHeight;
    }

    public void setSrcLeftHeight(float srcLeftHeight) {
        setSrcLeftHeight(TypedValue.COMPLEX_UNIT_DIP, srcLeftHeight);
    }

    public void setSrcLeftHeight(int unit, float srcLeftHeight) {
        setSrcLeftHeightRaw(applyDimension(unit, srcLeftHeight, getViewDisplayMetrics(this)));
    }

    private void setSrcLeftHeightRaw(float srcLeftHeight) {
        if (this.srcLeftHeight != srcLeftHeight) {
            this.srcLeftHeight = srcLeftHeight;
            invalidate();
        }
    }

    //
    protected Matrix leftMatrix;

    public Matrix getLeftMatrix() {
        return leftMatrix;
    }

    public void setLeftMatrix(Matrix leftMatrix) {
        this.leftMatrix = leftMatrix;
    }

    protected Matrix initLeftMatrix(float sx, float sy) {
        if (leftMatrix == null) {
            leftMatrix = new Matrix();
        }
        leftMatrix.reset();
        leftMatrix.setScale(sx, sy);
        return leftMatrix;
    }

    // srcLeftPadding means distance between srcLeft and textview,note
    // about the textPaddingLeft
    private float srcLeftPadding;

    public float getSrcLeftPadding() {
        return srcLeftPadding;
    }

    public void setSrcLeftPadding(float srcLeftPadding) {
        setSrcLeftPadding(TypedValue.COMPLEX_UNIT_DIP, srcLeftPadding);
    }

    public void setSrcLeftPadding(int unit, float srcLeftPadding) {
        setSrcLeftPaddingRaw(applyDimension(unit, srcLeftPadding, getViewDisplayMetrics(this)));
    }

    private void setSrcLeftPaddingRaw(float srcLeftPadding) {
        if (this.srcLeftPadding != srcLeftPadding) {
            this.srcLeftPadding = srcLeftPadding;
            invalidate();
        }
    }

    //
    private float srcLeftDx;

    public float getSrcLeftDx() {
        return srcLeftDx;
    }

    public void setSrcLeftDx(float srcLeftDx) {
        setSrcLeftDx(TypedValue.COMPLEX_UNIT_DIP, srcLeftDx);
    }

    public void setSrcLeftDx(int unit, float srcLeftDx) {
        setSrcLeftDxRaw(applyDimension(unit, srcLeftDx, getViewDisplayMetrics(this)));
    }

    private void setSrcLeftDxRaw(float srcLeftDx) {
        if (this.srcLeftDx != srcLeftDx) {
            this.srcLeftDx = srcLeftDx;
            invalidate();
        }
    }

    //
    private float srcLeftDy;

    public float getSrcLeftDy() {
        return srcLeftDy;
    }

    public void setSrcLeftDy(float srcLeftDy) {
        setSrcLeftDy(TypedValue.COMPLEX_UNIT_DIP, srcLeftDy);
    }

    public void setSrcLeftDy(int unit, float srcLeftDy) {
        setSrcLeftDyRaw(applyDimension(unit, srcLeftDy, getViewDisplayMetrics(this)));
    }

    private void setSrcLeftDyRaw(float srcLeftDy) {
        if (this.srcLeftDy != srcLeftDy) {
            this.srcLeftDy = srcLeftDy;
            invalidate();
        }
    }

    //
    private Bitmap srcRight;

    public Bitmap getSrcRight() {
        return srcRight;
    }

    public void setSrcRight(Bitmap srcRight) {
        this.srcRight = srcRight;
    }

    //
    private float srcRightWidth;

    public float getSrcRightWidth() {
        return srcRightWidth;
    }

    public void setSrcRightWidth(float srcRightWidth) {
        setSrcRightWidth(TypedValue.COMPLEX_UNIT_DIP, srcRightWidth);
    }

    public void setSrcRightWidth(int unit, float srcRightWidth) {
        setSrcRightWidthRaw(applyDimension(unit, srcRightWidth, getViewDisplayMetrics(this)));
    }

    private void setSrcRightWidthRaw(float srcRightWidth) {
        if (this.srcRightWidth != srcRightWidth) {
            this.srcRightWidth = srcRightWidth;
            invalidate();
        }
    }

    //
    private float srcRightHeight;

    public float getSrcRightHeight() {
        return srcRightHeight;
    }

    public void setSrcRightHeight(float srcRightHeight) {
        setSrcRightHeight(TypedValue.COMPLEX_UNIT_DIP, srcRightHeight);
    }

    public void setSrcRightHeight(int unit, float srcRightHeight) {
        setSrcRightHeightRaw(applyDimension(unit, srcRightHeight, getViewDisplayMetrics(this)));
    }

    private void setSrcRightHeightRaw(float srcRightHeight) {
        if (this.srcRightHeight != srcRightHeight) {
            this.srcRightHeight = srcRightHeight;
            invalidate();
        }
    }

    //
    protected Matrix rightMatrix;

    public Matrix getRightMatrix() {
        return rightMatrix;
    }

    public void setRightMatrix(Matrix rightMatrix) {
        this.rightMatrix = rightMatrix;
    }

    protected Matrix initrightMatrix(float sx, float sy) {
        if (rightMatrix == null) {
            rightMatrix = new Matrix();
        }
        rightMatrix.reset();
        rightMatrix.setScale(sx, sy);
        return rightMatrix;
    }

    // srcRightPadding means distance between srcRight and textview,note
    // about the textPaddingRight
    private float srcRightPadding;

    public float getSrcRightPadding() {
        return srcRightPadding;
    }

    public void setSrcRightPadding(float srcRightPadding) {
        setSrcRightPadding(TypedValue.COMPLEX_UNIT_DIP, srcRightPadding);
    }

    public void setSrcRightPadding(int unit, float srcRightPadding) {
        setSrcRightPaddingRaw(applyDimension(unit, srcRightPadding, getViewDisplayMetrics(this)));
    }

    private void setSrcRightPaddingRaw(float srcRightPadding) {
        if (this.srcRightPadding != srcRightPadding) {
            this.srcRightPadding = srcRightPadding;
            invalidate();
        }
    }

    //
    private float srcRightDx;

    public float getSrcRightDx() {
        return srcRightDx;
    }

    public void setSrcRightDx(float srcRightDx) {
        setSrcRightDx(TypedValue.COMPLEX_UNIT_DIP, srcRightDx);
    }

    public void setSrcRightDx(int unit, float srcRightDx) {
        setSrcRightDxRaw(applyDimension(unit, srcRightDx, getViewDisplayMetrics(this)));
    }

    private void setSrcRightDxRaw(float srcRightDx) {
        if (this.srcRightDx != srcRightDx) {
            this.srcRightDx = srcRightDx;
            invalidate();
        }
    }

    //
    private float srcRightDy;

    public float getSrcRightDy() {
        return srcRightDy;
    }

    public void setSrcRightDy(float srcRightDy) {
        setSrcRightDy(TypedValue.COMPLEX_UNIT_DIP, srcRightDy);
    }

    public void setSrcRightDy(int unit, float srcRightDy) {
        setSrcRightDyRaw(applyDimension(unit, srcRightDy, getViewDisplayMetrics(this)));
    }

    private void setSrcRightDyRaw(float srcRightDy) {
        if (this.srcRightDy != srcRightDy) {
            this.srcRightDy = srcRightDy;
            invalidate();
        }
    }

    // attention porterDuffXfermode default 0 instead of -1!
    protected PorterDuffXfermode porterDuffXfermode;

    public enum TPorterDuffXfermode {
        SRC_IN(0), SRC_OUT(1),
        ;
        final int nativeInt;

        TPorterDuffXfermode(int ni) {
            nativeInt = ni;
        }
    }

    public static final Mode[] porterDuffXfermodeArray = {PorterDuff.Mode.SRC_IN, PorterDuff.Mode.SRC_OUT,};

    public PorterDuffXfermode getPorterDuffXfermode() {
        return porterDuffXfermode;
    }

    public void setPorterDuffXfermode(PorterDuffXfermode porterDuffXfermode) {
        this.porterDuffXfermode = porterDuffXfermode;
    }

    //
    private Material material;

    public enum Material {
        SPREAD(0), TRANS(1),
        ;
        final int nativeInt;

        Material(int ni) {
            nativeInt = ni;
        }
    }

    private static final Material[] materialArray = {Material.SPREAD, Material.TRANS,};

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    //
    private boolean materialMove;

    public boolean isMaterialMove() {
        return materialMove;
    }

    public void setMaterialMove(boolean materialMove) {
        this.materialMove = materialMove;
    }

    //
    private float materialRadius;

    public float getMaterialRadius() {
        return materialRadius;
    }

    public void setMaterialRadius(float materialRadius) {
        this.materialRadius = materialRadius;
    }

    //
    private float touchDownEventX;

    public float getTouchDownEventX() {
        return touchDownEventX;
    }

    public void setTouchDownEventX(float touchDownEventX) {
        this.touchDownEventX = touchDownEventX;
    }

    //
    private float touchDownEventY;

    public float getTouchDownEventY() {
        return touchDownEventY;
    }

    public void setTouchDownEventY(float touchDownEventY) {
        this.touchDownEventY = touchDownEventY;
    }

    //
    private int materialDuraction = 500;

    public int getMaterialDuraction() {
        return materialDuraction;
    }

    public void setMaterialDuraction(int materialDuraction) {
        this.materialDuraction = materialDuraction;
    }

    //
    private boolean materialPlay;

    public boolean ismaterialPlay() {
        return materialPlay;
    }

    public void setMaterialPlay(boolean materialPlay) {
        this.materialPlay = materialPlay;
    }

    //
    private TimeInterpolator materialTimeInterpolator;

    public TimeInterpolator getMaterialTimeInterpolator() {
        return materialTimeInterpolator;
    }

    public void setMaterialTimeInterpolator(TimeInterpolator materialTimeInterpolator) {
        this.materialTimeInterpolator = materialTimeInterpolator;
    }

    //
    public enum materialTimeInterpolator {
        ACCELERATEDECELERATEINTERPOLATOR(0),
        ACCELERATEINTERPOLATOR(1),
        ANTICIPATEINTERPOLATOR(2),
        ANTICIPATEOVERSHOOTINTERPOLATOR(3),
        BOUNCEINTERPOLATOR(4),
        CYCLEINTERPOLATOR(5),
        DECELERATEINTERPOLATOR(6),
        LINEARINTERPOLATOR(7),
        OVERSHOOTINTERPOLATOR(8),
        ;
        final int nativeInt;

        materialTimeInterpolator(int ni) {
            nativeInt = ni;
        }
    }

    //
    private static final TimeInterpolator[] materialTimeInterpolatorArray = {new AccelerateDecelerateInterpolator(), new AccelerateInterpolator(),
            new AnticipateInterpolator(), new AnticipateOvershootInterpolator(), new BounceInterpolator(), new CycleInterpolator(0), new DecelerateInterpolator(),
            new LinearInterpolator(), new OvershootInterpolator(),};

    //
    private AnimatorSet materialAnimatorSet;

    public AnimatorSet getMaterialAnimatorSet() {
        return materialAnimatorSet;
    }

    public void setMaterialAnimatorSet(AnimatorSet materialAnimatorSet) {
        this.materialAnimatorSet = materialAnimatorSet;
    }

    //
    private Property<TView, Float> materialRadiusProperty = new Property<TView, Float>(Float.class, "materialRadius") {
        @Override
        public Float get(TView tview) {
            return tview.materialRadius;
        }

        @Override
        public void set(TView tview, Float value) {
            tview.materialRadius = value;
            invalidate();
        }
    };

    //
    private Property<TView, Float> materialPaintXProperty = new Property<TView, Float>(Float.class, "materialPaintX") {
        @Override
        public Float get(TView tview) {
            return tview.touchDownEventX;
        }

        @Override
        public void set(TView tview, Float value) {
            tview.touchDownEventX = value;
        }
    };

    //
    private Property<TView, Float> materialPaintYProperty = new Property<TView, Float>(Float.class, "materialPaintY") {
        @Override
        public Float get(TView tview) {
            return tview.touchDownEventY;
        }

        @Override
        public void set(TView tview, Float value) {
            tview.touchDownEventY = value;
        }
    };

    // This setting will cause the following message appears reading xml
    // The graphics preview in the layout editor may not be accurate: Paint
    // Flags Draw Filters are not supported. (Ignore for this session)
    private PaintFlagsDrawFilter paintFlagsDrawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);

    public PaintFlagsDrawFilter getPaintFlagsDrawFilter() {
        return paintFlagsDrawFilter;
    }

    public void setPaintFlagsDrawFilter(PaintFlagsDrawFilter paintFlagsDrawFilter) {
        this.paintFlagsDrawFilter = paintFlagsDrawFilter;
    }

    //
    private int touchDownCount;
    private long touchDownTimeStart, touchDownTimeEnd;
    private static final int TOUCH_DOWN_TIMES = 3;
    private static final int SHOW_PROPERTY_MAX_DISTANCE_DP = 10;

    // in response to the dispathtouch event: need touch TOUCH_DOWN_TIMES
    // consecutive times within SHOW_PROPERTY_MAX_TIME_MILLIS ,
    // and the touch location can not exceed SHOW_PROPERTY_MAX_DISTANCE_DIP,
    // touchDownEventX and touchDownEventY position refresh when pressed
    // eachtimes
    private static final int SHOW_PROPERTY_MAX_TIME_MILLIS = 200 * TOUCH_DOWN_TIMES;

    public TView(Context context) {
        this(context, null);
    }

    public TView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        Tag = TView.class.getSimpleName();

        //
        debugable = (0 != (context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE));
        //
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TView, 0, defStyle);

        // touchType default edge
        int touchTypeIndex = typedArray.getInt(R.styleable.TView_touchType, 0);
        touchType = touchTypeArray[touchTypeIndex];

        touchIntercept = typedArray.getBoolean(R.styleable.TView_touchIntercept, false);

        press = typedArray.getBoolean(R.styleable.TView_press, false);
        select = typedArray.getBoolean(R.styleable.TView_select, false);

        // selectType default none
        int selectTypeIndex = typedArray.getInt(R.styleable.TView_selectType, 0);
        selectType = selectTypeArray[selectTypeIndex];

        animationable = typedArray.getBoolean(R.styleable.TView_animationable, false);
        rotate = typedArray.getInt(R.styleable.TView_rotate, 0);

        // xfermodeIndex default PorterDuff.Mode.SRC_IN
        int xfermodeIndex = typedArray.getInt(R.styleable.TView_porterDuffXfermode, 0);

        //
        porterDuffXfermode = new PorterDuffXfermode(porterDuffXfermodeArray[xfermodeIndex]);

        tSuper = TView.class == this.getClass();

        if (tSuper) {

            // note that the use of default values ​​can be defined,backgroundNormal to the default white to achieve clip bitmap results!
            backgroundNormal = typedArray.getColor(R.styleable.TView_backgroundNormal, Color.TRANSPARENT);
            backgroundPress = typedArray.getColor(R.styleable.TView_backgroundPress, backgroundNormal);
            backgroundSelect = typedArray.getColor(R.styleable.TView_backgroundSelect, backgroundNormal);

            foregroundNormal = typedArray.getColor(R.styleable.TView_foregroundNormal, Color.TRANSPARENT);
            foregroundPress = typedArray.getColor(R.styleable.TView_foregroundPress, foregroundNormal);
            foregroundSelect = typedArray.getColor(R.styleable.TView_foregroundSelect, foregroundNormal);

            //
            backgroundNormalAngle = typedArray.getInt(R.styleable.TView_backgroundNormalAngle, Integer.MAX_VALUE);
            if (backgroundNormalAngle != Integer.MAX_VALUE) {
                backgroundNormalGradientStart = typedArray.getColor(R.styleable.TView_backgroundNormalGradientStart, backgroundNormal);
                backgroundNormalGradientEnd = typedArray.getColor(R.styleable.TView_backgroundNormalGradientEnd, backgroundNormal);
            }

            backgroundPressAngle = typedArray.getInt(R.styleable.TView_backgroundPressAngle, Integer.MAX_VALUE);
            if (backgroundPressAngle != Integer.MAX_VALUE) {
                backgroundPressGradientStart = typedArray.getColor(R.styleable.TView_backgroundPressGradientStart, backgroundPress);
                backgroundPressGradientEnd = typedArray.getColor(R.styleable.TView_backgroundPressGradientEnd, backgroundPress);
            }

            backgroundSelectAngle = typedArray.getInt(R.styleable.TView_backgroundSelectAngle, Integer.MAX_VALUE);
            if (backgroundSelectAngle != Integer.MAX_VALUE) {
                backgroundSelectGradientStart = typedArray.getColor(R.styleable.TView_backgroundSelectGradientStart, backgroundSelect);
                backgroundSelectGradientEnd = typedArray.getColor(R.styleable.TView_backgroundSelectGradientEnd, backgroundSelect);
            }

            //Note background Normal ShadowRadius and srcNormal ShadowRadius are two values!
            backgroundNormalShadowRadius = typedArray.getDimension(R.styleable.TView_backgroundNormalShadowRadius, 0);
            if (backgroundNormalShadowRadius > 0) {
                backgroundNormalShadowColor = typedArray.getColor(R.styleable.TView_backgroundNormalShadowColor, Color.TRANSPARENT);
                backgroundNormalShadowDx = typedArray.getDimension(R.styleable.TView_backgroundNormalShadowDx, 0);
                backgroundNormalShadowDy = typedArray.getDimension(R.styleable.TView_backgroundNormalShadowDy, 0);
            }

            //
            backgroundPressShadowRadius = typedArray.getDimension(R.styleable.TView_backgroundPressShadowRadius, backgroundNormalShadowRadius);
            if (backgroundPressShadowRadius > 0) {
                backgroundPressShadowColor = typedArray.getColor(R.styleable.TView_backgroundPressShadowColor, backgroundNormalShadowColor);
                backgroundPressShadowDx = typedArray.getDimension(R.styleable.TView_backgroundPressShadowDx, backgroundNormalShadowDx);
                backgroundPressShadowDy = typedArray.getDimension(R.styleable.TView_backgroundPressShadowDy, backgroundNormalShadowDy);
            }

            //
            backgroundSelectShadowRadius = typedArray.getDimension(R.styleable.TView_backgroundSelectShadowRadius, backgroundNormalShadowRadius);
            if (backgroundSelectShadowRadius > 0) {
                backgroundSelectShadowColor = typedArray.getColor(R.styleable.TView_backgroundSelectShadowColor, backgroundNormalShadowColor);
                backgroundSelectShadowDx = typedArray.getDimension(R.styleable.TView_backgroundSelectShadowDx, backgroundNormalShadowDx);
                backgroundSelectShadowDy = typedArray.getDimension(R.styleable.TView_backgroundSelectShadowDy, backgroundNormalShadowDy);
            }

            //
            int srcNormalId = typedArray.getResourceId(R.styleable.TView_srcNormal, -1);
            if (srcNormalId != -1) {

                srcNormal = BitmapFactory.decodeResource(getResources(), srcNormalId);

                //
                int srcPressId = typedArray.getResourceId(R.styleable.TView_srcPress, -1);
                if (srcPressId != -1) {
                    srcPress = BitmapFactory.decodeResource(getResources(), srcPressId);
                } else {
                    srcPress = srcNormal;
                }
                //
                int srcSelectId = typedArray.getResourceId(R.styleable.TView_srcPress, -1);
                if (srcSelectId != -1) {
                    srcSelect = BitmapFactory.decodeResource(getResources(), srcSelectId);
                } else {
                    srcSelect = srcNormal;
                }

                srcNormalShadowRadius = typedArray.getDimension(R.styleable.TView_srcNormalShadowRadius, 0);
                if (srcNormalShadowRadius > 0) {
                    srcNormalShadowDx = typedArray.getDimension(R.styleable.TView_srcNormalShadowDx, 0);
                    srcNormalShadowDy = typedArray.getDimension(R.styleable.TView_srcNormalShadowDy, 0);
                }
                //
                srcPressShadowRadius = typedArray.getDimension(R.styleable.TView_srcPressShadowRadius, srcNormalShadowRadius);
                if (srcPressShadowRadius > 0) {
                    srcPressShadowDx = typedArray.getDimension(R.styleable.TView_srcPressShadowDx, srcNormalShadowDx);
                    srcPressShadowDy = typedArray.getDimension(R.styleable.TView_srcPressShadowDy, srcNormalShadowDy);
                }
                //
                srcSelectShadowRadius = typedArray.getDimension(R.styleable.TView_srcSelectShadowRadius, srcNormalShadowRadius);
                if (srcSelectShadowRadius > 0) {
                    srcSelectShadowDx = typedArray.getDimension(R.styleable.TView_srcSelectShadowDx, srcNormalShadowDx);
                    srcSelectShadowDy = typedArray.getDimension(R.styleable.TView_srcSelectShadowDy, srcNormalShadowDy);
                }
            }
            //
            int srcAnchorNormalId = typedArray.getResourceId(R.styleable.TView_srcAnchorNormal, -1);
            if (srcAnchorNormalId != -1) {

                //
                srcAnchorNormal = BitmapFactory.decodeResource(getResources(), srcAnchorNormalId);
                srcAnchorNormalWidth = typedArray.getDimension(R.styleable.TView_srcAnchorNormalWidth, 0);
                srcAnchorNormalHeight = typedArray.getDimension(R.styleable.TView_srcAnchorNormalHeight, 0);
                srcAnchorNormalDx = typedArray.getDimension(R.styleable.TView_srcAnchorNormalDx, 0);
                srcAnchorNormalDy = typedArray.getDimension(R.styleable.TView_srcAnchorNormalDy, 0);
                //
                srcAnchorPressWidth = typedArray.getDimension(R.styleable.TView_srcAnchorPressWidth, srcAnchorNormalWidth);
                srcAnchorPressHeight = typedArray.getDimension(R.styleable.TView_srcAnchorPressHeight, srcAnchorNormalHeight);
                srcAnchorPressDx = typedArray.getDimension(R.styleable.TView_srcAnchorPressDx, srcAnchorNormalDx);
                srcAnchorPressDy = typedArray.getDimension(R.styleable.TView_srcAnchorPressDy, srcAnchorNormalDy);
                //
                srcAnchorSelectWidth = typedArray.getDimension(R.styleable.TView_srcAnchorSelectWidth, srcAnchorNormalWidth);
                srcAnchorSelectHeight = typedArray.getDimension(R.styleable.TView_srcAnchorSelectHeight, srcAnchorNormalHeight);
                srcAnchorSelectDx = typedArray.getDimension(R.styleable.TView_srcAnchorSelectDx, srcAnchorNormalDx);
                srcAnchorSelectDy = typedArray.getDimension(R.styleable.TView_srcAnchorSelectDy, srcAnchorNormalDy);

                //
                int srcAnchorPressId = typedArray.getResourceId(R.styleable.TView_srcAnchorPress, -1);
                if (srcAnchorPressId != -1) {
                    srcAnchorPress = BitmapFactory.decodeResource(getResources(), srcAnchorPressId);
                } else {
                    srcAnchorPress = srcAnchorNormal;
                }
                //
                int srcAnchorSelectId = typedArray.getResourceId(R.styleable.TView_srcAnchorSelect, -1);
                if (srcAnchorSelectId != -1) {
                    srcAnchorSelect = BitmapFactory.decodeResource(getResources(), srcAnchorSelectId);
                } else {
                    srcAnchorSelect = srcAnchorNormal;
                }

                //
                srcAnchorGravity = typedArray.getInt(R.styleable.TView_srcAnchorGravity, 0);
            }

            //
            int srcLeftId = typedArray.getResourceId(R.styleable.TView_srcLeft, -1);
            if (srcLeftId != -1) {
                srcLeft = BitmapFactory.decodeResource(getResources(), srcLeftId);
                srcLeftWidth = typedArray.getDimension(R.styleable.TView_srcLeftWidth, 0);
                srcLeftHeight = typedArray.getDimension(R.styleable.TView_srcLeftHeight, 0);
                srcLeftPadding = typedArray.getDimension(R.styleable.TView_srcLeftPadding, 0);
                srcLeftDx = typedArray.getDimension(R.styleable.TView_srcLeftDx, 0);
                srcLeftDy = typedArray.getDimension(R.styleable.TView_srcLeftDy, 0);

                if (srcLeftWidth == 0 || srcLeftHeight == 0) {
                    throw new IllegalArgumentException("The content attribute require property named srcLeftWidth and srcLeftHeight");
                }
            }

            //
            int srcRightId = typedArray.getResourceId(R.styleable.TView_srcRight, -1);
            if (srcRightId != -1) {
                srcRight = BitmapFactory.decodeResource(getResources(), srcRightId);
                srcRightWidth = typedArray.getDimension(R.styleable.TView_srcRightWidth, 0);
                srcRightHeight = typedArray.getDimension(R.styleable.TView_srcRightHeight, 0);
                srcRightPadding = typedArray.getDimension(R.styleable.TView_srcRightPadding, 0);
                srcRightDx = typedArray.getDimension(R.styleable.TView_srcRightDx, 0);
                srcRightDy = typedArray.getDimension(R.styleable.TView_srcRightDy, 0);

                if (srcRightWidth == 0 || srcRightHeight == 0) {
                    throw new IllegalArgumentException("The content attribute require property named srcRightWidth and srcRightHeight");
                }
            }

            //
            textMark = typedArray.getBoolean(R.styleable.TView_textMark, false);
            textMarkTouchable = typedArray.getBoolean(R.styleable.TView_textMarkTouchable, false);
            textMarkRadius = typedArray.getDimension(R.styleable.TView_textMarkRadius, 0);
            textMarkColor = typedArray.getColor(R.styleable.TView_textMarkColor, Color.TRANSPARENT);
            textMarkTextValue = typedArray.getString(R.styleable.TView_textMarkTextValue);
            textMarkTextSize = typedArray.getDimension(R.styleable.TView_textMarkTextSize, 0);
            textMarkTextColor = typedArray.getColor(R.styleable.TView_textMarkTextColor, Color.TRANSPARENT);

            //
            contentMark = typedArray.getBoolean(R.styleable.TView_contentMark, false);
            contentMarkTouchable = typedArray.getBoolean(R.styleable.TView_contentMarkTouchable, false);
            contentMarkRadius = typedArray.getDimension(R.styleable.TView_contentMarkRadius, 0);
            contentMarkColor = typedArray.getColor(R.styleable.TView_contentMarkColor, Color.TRANSPARENT);
            contentMarkTextValue = typedArray.getString(R.styleable.TView_contentMarkTextValue);
            contentMarkTextSize = typedArray.getDimension(R.styleable.TView_contentMarkTextSize, 0);
            contentMarkTextColor = typedArray.getColor(R.styleable.TView_contentMarkTextColor, Color.TRANSPARENT);

            //
            strokeWidthNormal = typedArray.getDimension(R.styleable.TView_strokeWidthNormal, 0);
            strokeColorNormal = typedArray.getColor(R.styleable.TView_strokeColorNormal, Color.TRANSPARENT);
            strokeWidthPress = typedArray.getDimension(R.styleable.TView_strokeWidthPress, strokeWidthNormal);
            strokeColorPress = typedArray.getColor(R.styleable.TView_strokeColorPress, strokeColorNormal);
            strokeWidthSelect = typedArray.getDimension(R.styleable.TView_strokeWidthSelect, strokeWidthNormal);
            strokeColorSelect = typedArray.getColor(R.styleable.TView_strokeColorSelect, strokeColorNormal);

            //
            radius = typedArray.getDimension(R.styleable.TView_radius, 0);
            radiusLeftTop = typedArray.getDimension(R.styleable.TView_radiusLeftTop, radius);
            radiusLeftBottom = typedArray.getDimension(R.styleable.TView_radiusLeftBottom, radius);
            radiusRightTop = typedArray.getDimension(R.styleable.TView_radiusRightTop, radius);
            radiusRightBottom = typedArray.getDimension(R.styleable.TView_radiusRightBottom, radius);

            classic = (radius == radiusLeftTop && radiusLeftTop == radiusLeftBottom && radiusLeftBottom == radiusRightTop && radiusRightTop == radiusRightBottom);

            //
            textValue = typedArray.getString(R.styleable.TView_textValue);
            textSize = typedArray.getDimension(R.styleable.TView_textSize, 0);

            textColorNormal = typedArray.getColor(R.styleable.TView_textColorNormal, Color.TRANSPARENT);
            textColorPress = typedArray.getColor(R.styleable.TView_textColorPress, textColorNormal);
            textColorSelect = typedArray.getColor(R.styleable.TView_textColorSelect, textColorNormal);

            textPaddingLeft = typedArray.getDimension(R.styleable.TView_textPaddingLeft, 0);
            textPaddingRight = typedArray.getDimension(R.styleable.TView_textPaddingRight, 0);
            textRowSpaceRatio = typedArray.getFraction(R.styleable.TView_textRowSpaceRatio, 1, 1, 1);

            //
            int textGravityIndex = typedArray.getInt(R.styleable.TView_textGravity, 0);
            if (textGravityIndex >= 0) {
                textGravity = textGravityArray[textGravityIndex];
            }

            //
            int textTypeFaceIndex = typedArray.getInt(R.styleable.TView_textTypeFace, 0);
            if (textTypeFaceIndex >= 0) {
                textTypeFace = Typeface.create(Typeface.DEFAULT, textTypeFaceArray[textTypeFaceIndex]);
            }

            textDx = typedArray.getDimension(R.styleable.TView_textDx, 0);
            textDy = typedArray.getDimension(R.styleable.TView_textDy, 0);
            textFractionDx = typedArray.getFraction(R.styleable.TView_textFractionDx, 1, 1, 0);
            textFractionDy = typedArray.getFraction(R.styleable.TView_textFractionDy, 1, 1, 0);

            //
            textShadowRadius = typedArray.getDimension(R.styleable.TView_textShadowRadius, 0);
            if (textShadowRadius > 0) {
                textShadowColor = typedArray.getColor(R.styleable.TView_textShadowColor, Color.TRANSPARENT);
                textShadowDx = typedArray.getDimension(R.styleable.TView_textShadowDx, 0);
                textShadowDy = typedArray.getDimension(R.styleable.TView_textShadowDy, 0);
            }

            //
            contentValue = typedArray.getString(R.styleable.TView_contentValue);
            contentSize = typedArray.getDimension(R.styleable.TView_contentSize, 0);

            contentColorNormal = typedArray.getColor(R.styleable.TView_contentColorNormal, Color.TRANSPARENT);
            contentColorPress = typedArray.getColor(R.styleable.TView_contentColorPress, contentColorNormal);
            contentColorSelect = typedArray.getColor(R.styleable.TView_contentColorSelect, contentColorNormal);

            contentPaddingLeft = typedArray.getDimension(R.styleable.TView_contentPaddingLeft, 0);
            contentPaddingRight = typedArray.getDimension(R.styleable.TView_contentPaddingRight, 0);
            contentRowSpaceRatio = typedArray.getFraction(R.styleable.TView_contentRowSpaceRatio, 1, 1, 1);

            //
            int contentGravityIndex = typedArray.getInt(R.styleable.TView_contentGravity, 0);
            if (contentGravityIndex >= 0) {
                contentGravity = contentGravityArray[contentGravityIndex];
            }

            int contentTypeFaceIndex = typedArray.getInt(R.styleable.TView_contentTypeFace, 0);
            if (contentTypeFaceIndex >= 0) {
                contentTypeFace = Typeface.create(Typeface.DEFAULT, contentTypeFaceArray[contentTypeFaceIndex]);
            }

            contentDx = typedArray.getDimension(R.styleable.TView_contentDx, 0);
            contentDy = typedArray.getDimension(R.styleable.TView_contentDy, 0);
            contentFractionDx = typedArray.getFraction(R.styleable.TView_contentFractionDx, 1, 1, 0);
            contentFractionDy = typedArray.getFraction(R.styleable.TView_contentFractionDy, 1, 1, 0);

            //
            contentShadowRadius = typedArray.getDimension(R.styleable.TView_contentShadowRadius, 0);
            if (contentShadowRadius > 0) {
                contentShadowColor = typedArray.getColor(R.styleable.TView_contentShadowColor, Color.TRANSPARENT);
                contentShadowDx = typedArray.getDimension(R.styleable.TView_contentShadowDx, 0);
                contentShadowDy = typedArray.getDimension(R.styleable.TView_contentShadowDy, 0);
            }

            //
            textMarkDx = typedArray.getDimension(R.styleable.TView_textMarkDx, 0);
            textMarkDy = typedArray.getDimension(R.styleable.TView_textMarkDy, 0);
            textMarkFractionDx = typedArray.getFraction(R.styleable.TView_textMarkFractionDx, 1, 1, 0);
            textMarkFractionDy = typedArray.getFraction(R.styleable.TView_textMarkFractionDy, 1, 1, 0);

            // materialIndex default -1
            int materialIndex = typedArray.getInt(R.styleable.TView_material, -1);
            if (materialIndex > -1) {
                material = materialArray[materialIndex];
            }
        }

        typedArray.recycle();

        //
        DeviceTool.initDisplayMetrics(getContext());
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        if (touchType == TouchType.NONE) {
            return super.dispatchTouchEvent(event);
        }

        //This sentence is telling the parent control, my own event handling! when Drag and other views nested inside the ScroView will be used !

        if (touchIntercept) {
            getParent().requestDisallowInterceptTouchEvent(true);
        }

        touchEventX = event.getX();
        touchEventY = event.getY();

        if (touchListener != null && (touchType == TouchType.ALWAYS || !touchOutBounds)) {
            touchListener.touch(this);
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                //
                if (debugable) {

                    if (touchDownCount == 0) {
                        touchDownCount = 1;
                        touchDownTimeStart = System.currentTimeMillis();
                    } else if (touchDownCount == TOUCH_DOWN_TIMES - 1) {
                        touchDownTimeEnd = System.currentTimeMillis();
                        if ((touchDownTimeEnd - touchDownTimeStart) < SHOW_PROPERTY_MAX_TIME_MILLIS) {
                            showProperties();
                        }
                        touchDownCount = 0;
                    } else {
                        float touchDownTimeDistanceX = Math.abs(touchDownEventX - event.getX());
                        float touchDownTimeDistanceY = Math.abs(touchDownEventY - event.getY());
                        if (touchDownTimeDistanceX < SHOW_PROPERTY_MAX_DISTANCE_DP * DeviceTool.displayDensity && touchDownTimeDistanceY < SHOW_PROPERTY_MAX_DISTANCE_DP * DeviceTool.displayDensity) {
                            touchDownCount++;
                        }
                    }
                    touchDownEventX = event.getX();
                    touchDownEventY = event.getY();
                    if ((System.currentTimeMillis() - touchDownTimeStart) >= SHOW_PROPERTY_MAX_TIME_MILLIS) {
                        touchDownCount = 0;
                    }
                }

                press = true;
                select = false;

                if (!textMarkTouchable) {
                    textMark = false;
                }

                if (!contentMarkTouchable) {
                    contentMark = false;
                }

                if (!touchOutBounds && associateListener != null) {
                    associateListener.associate(this);
                }

                if (!touchOutBounds && touchDownListener != null) {
                    touchDownListener.touchDown(this);
                }

                if (material != null) {
                    switch (material) {
                        case SPREAD:
                            float startRadius, endRadius;
                            if (width >= height) {
                                startRadius = touchDownEventY >= height - touchDownEventY ? touchDownEventY : height - touchDownEventY;
                                endRadius = width << 1;
                            } else {
                                startRadius = touchDownEventX >= width - touchDownEventX ? touchDownEventX : width - touchDownEventX;
                                endRadius = height << 1;
                            }
                            materialAnimatorSet = new AnimatorSet();
                            if (materialMove) {
                                materialAnimatorSet.playTogether(ObjectAnimator.ofFloat(this, materialRadiusProperty, startRadius, endRadius),
                                        ObjectAnimator.ofFloat(this, materialPaintXProperty, touchDownEventX, width >> 1),
                                        ObjectAnimator.ofFloat(this, materialPaintYProperty, touchDownEventY, height >> 1));
                            } else {
                                materialAnimatorSet.playTogether(ObjectAnimator.ofFloat(this, materialRadiusProperty, startRadius, endRadius));
                            }
                            materialAnimatorSet.setDuration(materialDuraction);
                            if (materialTimeInterpolator != null) {
                                materialAnimatorSet.setInterpolator(materialTimeInterpolator);
                            }
                            materialAnimatorSet.addListener(new AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animation) {
                                    materialPlay = true;
                                }

                                @Override
                                public void onAnimationRepeat(Animator animation) {
                                }

                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    materialPlay = false;
                                }

                                @Override
                                public void onAnimationCancel(Animator animation) {
                                    materialPlay = false;
                                }
                            });
                            materialAnimatorSet.start();
                            break;
                        case TRANS:
                            break;
                        default:
                            break;
                    }
                }

                break;
            case MotionEvent.ACTION_MOVE:
                //
                if (touchType == TouchType.ALWAYS) {

                    press = true;
                    select = false;
                } else if (!touchOutBounds && (touchEventX < 0 || touchEventX > width || touchEventY < 0 || touchEventY > height)) {

                    press = false;
                    select = false;

                    if (!textMarkTouchable) {
                        textMark = false;
                    }
                    if (!contentMarkTouchable) {
                        contentMark = false;
                    }

                    invalidate();

                    touchOutBounds = true;
                    if (touchOutListener != null) {
                        touchOutListener.touchOut(this);
                    }
                } else if (touchOutBounds && (touchEventX >= 0 && touchEventX <= width && touchEventY >= 0 && touchEventY <= height)) {

                    press = true;
                    select = false;

                    if (!textMarkTouchable) {
                        textMark = false;
                    }

                    if (!contentMarkTouchable) {
                        contentMark = false;
                    }

                    touchOutBounds = false;
                    if (touchInListener != null) {
                        touchInListener.touchIn(this);
                    }
                }

                //
                if (!touchOutBounds && touchMoveListener != null) {
                    touchMoveListener.touchMove(this);
                }

                break;
            case MotionEvent.ACTION_UP:
                //
                press = false;
                switch (selectType) {
                    case NONE:
                        select = false;
                        break;
                    case SAME:
                        select = true;
                        break;
                    case REVERSE:
                        selectRaw = !selectRaw;
                        select = selectRaw;
                        break;
                    default:
                        break;
                }

                if (!textMarkTouchable) {
                    textMark = false;
                }
                if (!contentMarkTouchable) {
                    contentMark = false;
                }

                if (!touchOutBounds && associateListener != null) {
                    associateListener.associate(this);
                }

                if (!touchOutBounds && touchUpListener != null) {
                    touchUpListener.touchUp(this);
                }

                touchOutBounds = false;

                break;
            case MotionEvent.ACTION_CANCEL:
                press = false;
                select = false;

                if (!textMarkTouchable) {
                    textMark = false;
                }
                if (!contentMarkTouchable) {
                    contentMark = false;
                }

                if (!touchOutBounds && touchCancelListener != null) {
                    touchCancelListener.touchCancel(this);
                }
                break;
            default:
                break;
        }

        if (!touchOutBounds) {
            invalidate();
        }
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (textValue != null) {
            textValueMeasureList = measure(widthMeasureSpec, heightMeasureSpec,
                    textValue, PaintTool.initTextPaint(textSize), textPaddingLeft, textPaddingRight,
                    textRowSpaceRatio);
        } else {
            contentValueMeasureList = measure(widthMeasureSpec, heightMeasureSpec,
                    contentValue, PaintTool.initTextPaint(contentSize), textPaddingLeft, textPaddingRight,
                    contentRowSpaceRatio);
        }
    }

    protected List<Integer> measure(int widthMeasureSpec, int heightMeasureSpec,
                                        String textValue, Paint paint, float paddingLeft, float paddingRight, float rowSpaceRatio) {
        List<Integer> ValueMeasureList = null;

        //
        int specModeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int specSizeWidth = MeasureSpec.getSize(widthMeasureSpec);

        //
        int specModeHeight = MeasureSpec.getMode(heightMeasureSpec);
        int specSizeHeight = MeasureSpec.getSize(heightMeasureSpec);

        //
        int measuredWidth = (int) convertToPX(96, this);
        int measuredHeight = (int) convertToPX(48, this);

        //measureWidth
        //expressed hope that the size of the parent view subviews should be determined by the value of specSize
        if (specModeWidth == MeasureSpec.AT_MOST && textValue != null) {//wrap_content
            int requestWidth = (int) (paint.measureText(textValue) + paddingLeft + paddingRight);
            measuredWidth = Math.min(specSizeWidth, requestWidth);
            //showing the child view can only be the size specified specSizer
        } else if (specModeWidth == MeasureSpec.EXACTLY) {// match_parent
            measuredWidth = specSizeWidth;
            //developers can express their wishes in accordance with the view set to any size, without any restrictions, for example, the item in the listview
        } else if (specModeWidth == MeasureSpec.UNSPECIFIED) {// unspecified
            measuredWidth = specSizeWidth;
        }

        //measuredHeight
        if (specModeHeight == MeasureSpec.AT_MOST && textValue != null) {// wrap_content
            ValueMeasureList = generateMeasureList(textValue, paint, measuredWidth, paddingLeft, paddingRight);
            FontMetricsInt fontMetrics = paint.getFontMetricsInt();
            int rowHeight = fontMetrics.descent - fontMetrics.ascent;
            measuredHeight = (int) (rowHeight * rowSpaceRatio * ValueMeasureList.size());
        } else if (specModeHeight == MeasureSpec.EXACTLY) {// match_parent
            measuredHeight = specSizeHeight;
        } else if (specModeHeight == MeasureSpec.UNSPECIFIED && textValue != null) {// unspecified
            ValueMeasureList = generateMeasureList(textValue, paint, measuredWidth, paddingLeft, paddingRight);
            FontMetricsInt fontMetrics = paint.getFontMetricsInt();
            int rowHeight = fontMetrics.descent - fontMetrics.ascent;
            measuredHeight = (int) (rowHeight * rowSpaceRatio * ValueMeasureList.size());
        }
        setMeasuredDimension(measuredWidth, measuredHeight);
        return ValueMeasureList;
    }

    protected List<Integer> generateMeasureList(String textValue, Paint paint, float width, float paddingLeft, float paddingRight) {
        List<Integer> measureList = new ArrayList<Integer>();
        int charatcerLength = textValue.length();
        float characterWidth = paint.measureText(textValue);
        float availableWidth = width - paddingLeft - paddingRight;
        //
        if (characterWidth > availableWidth) {
            for (int measureStart = 0, measureCurrent = 1; measureCurrent <= charatcerLength; measureCurrent++) {
                String measureString = textValue.substring(measureStart, measureCurrent);
                float measureLength = paint.measureText(measureString);
                if (measureLength > availableWidth) {
                    measureStart = measureCurrent - 1;
                    measureList.add(measureStart);
                } else if (measureCurrent == charatcerLength) {
                    measureStart = measureCurrent;
                    measureList.add(measureStart);
                }
            }
        } else {
            measureList.add(charatcerLength);
        }
        return measureList;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        width = getWidth();
        height = getHeight();

        if (!tSuper) {
            return;
        }

        if (foregroundNormal != Color.TRANSPARENT || foregroundPress != Color.TRANSPARENT || foregroundSelect != Color.TRANSPARENT || srcNormal != null
                || srcPress != null || srcSelect != null || srcNormalShadowRadius > 0 || srcPressShadowRadius > 0 || srcSelectShadowRadius > 0
                || backgroundNormalShadowRadius > 0 || backgroundPressShadowRadius > 0 || backgroundSelectShadowRadius > 0 || backgroundNormalAngle != Integer.MAX_VALUE
                || backgroundPressAngle != Integer.MAX_VALUE || backgroundSelectAngle != Integer.MAX_VALUE || srcAnchorNormal != null || srcAnchorPress != null
                || srcAnchorSelect != null

        ) {
            // setShadowLayer() is only supported on text when hardware acceleration is on.
            // Hardware acceleration is on by default when targetSdk=14 or higher.
            // An easy workaround is to put your View in a software layer: myView.setLayerType(View.LAYER_TYPE_SOFTWARE, null).
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        if (srcNormal != null || srcPress != null || srcSelect != null || backgroundNormalShadowRadius > 0 || backgroundPressShadowRadius > 0 || backgroundSelectShadowRadius > 0) {
            if (backgroundNormal == Color.TRANSPARENT) {
                backgroundNormal = Color.WHITE;
            }
            if (backgroundPress == Color.TRANSPARENT) {
                backgroundPress = backgroundNormal;
            }
            if (backgroundSelect == Color.TRANSPARENT) {
                backgroundSelect = backgroundNormal;
            }
        }

        // text
        textDx += width * textFractionDx;
        textDy += height * textFractionDy;

        // textMark
        textMarkDx += width * textMarkFractionDx;
        textMarkDy += height * textMarkFractionDy;

        //
        if (backgroundNormalAngle != Integer.MAX_VALUE) {
            backgroundNormalShader = getLinearGradient(width, height, backgroundNormalAngle, backgroundNormalGradientStart, backgroundNormalGradientEnd);
        }
        if (backgroundPressAngle != Integer.MAX_VALUE) {
            backgroundPressShader = getLinearGradient(width, height, backgroundPressAngle, backgroundPressGradientStart, backgroundPressGradientEnd);
        }
        if (backgroundSelectAngle != Integer.MAX_VALUE) {
            backgroundSelectShader = getLinearGradient(width, height, backgroundSelectAngle, backgroundSelectGradientStart, backgroundSelectGradientEnd);
        }

        //
        int srcNormalWidthRaw = 0, srcNormalHeightRaw = 0, srcPressWidthRaw = 0, srcPressHeightRaw = 0, srcSelectWidthRaw = 0, srcSelectHeightRaw = 0;
        if (srcNormal != null) {
            srcNormalWidthRaw = srcNormal.getWidth();
            srcNormalHeightRaw = srcNormal.getHeight();

            initMatrix((width - srcNormalShadowRadius * 2f - backgroundNormalShadowRadius * 2f - backgroundNormalShadowDx * 2f) / srcNormalWidthRaw,
                    (height - srcNormalShadowRadius * 2f - backgroundNormalShadowRadius * 2f - backgroundNormalShadowDy * 2f) / srcNormalHeightRaw);
        }

        if (srcPress != null) {
            srcPressWidthRaw = srcPress.getWidth();
            srcPressHeightRaw = srcPress.getHeight();
        }

        if (srcSelect != null) {
            srcSelectWidthRaw = srcSelect.getWidth();
            srcSelectHeightRaw = srcSelect.getHeight();
        }

        if (srcNormalWidthRaw != srcPressWidthRaw || srcNormalHeightRaw != srcPressHeightRaw || srcPressWidthRaw != srcSelectWidthRaw
                || srcPressHeightRaw != srcSelectHeightRaw) {
            throw new IndexOutOfBoundsException("Both the width and height of the attribute srcNormal ,srcPress and srcSelect needed equal");
        }

        //
        int srcAnchorNormalWidthRaw = 0, srcAnchorNormalHeightRaw = 0, srcAnchorPressWidthRaw = 0, srcAnchorPressHeightRaw = 0, srcAnchorSelectWidthRaw = 0, srcAnchorSelectHeightRaw = 0;

        if (srcAnchorNormal != null) {
            srcAnchorNormalWidthRaw = srcAnchorNormal.getWidth();
            srcAnchorNormalHeightRaw = srcAnchorNormal.getHeight();

            initanchorMatrix(srcAnchorNormalWidth / srcAnchorNormalWidthRaw, srcAnchorNormalHeight / srcAnchorNormalHeightRaw);
        }

        if (srcAnchorPress != null) {
            srcAnchorPressWidthRaw = srcAnchorPress.getWidth();
            srcAnchorPressHeightRaw = srcAnchorPress.getHeight();
        }

        if (srcAnchorSelect != null) {
            srcAnchorSelectWidthRaw = srcAnchorSelect.getWidth();
            srcAnchorSelectHeightRaw = srcAnchorSelect.getHeight();
        }

        if (srcAnchorNormalWidthRaw != srcAnchorPressWidthRaw || srcAnchorNormalHeightRaw != srcAnchorPressHeightRaw
                || srcAnchorPressWidthRaw != srcAnchorSelectWidthRaw || srcAnchorPressHeightRaw != srcAnchorSelectHeightRaw) {
            throw new IndexOutOfBoundsException("Both the width and height of the attribute srcAnchorNormal ,srcAnchorPress and srcAnchorSelect needed equal");
        }

        //
        if (srcLeft != null) {
            int srcLeftWidthRaw = srcLeft.getWidth();
            int srcLeftHeightRaw = srcLeft.getHeight();
            initLeftMatrix(srcLeftWidth / srcLeftWidthRaw, srcLeftHeight / srcLeftHeightRaw);
        }

        if (srcRight != null) {
            int srcRightWidthRaw = srcRight.getWidth();
            int srcRightHeightRaw = srcRight.getHeight();
            initrightMatrix(srcRightWidth / srcRightWidthRaw, srcRightHeight / srcRightHeightRaw);
        }

        if (layoutListener != null) {
            layoutListener.layout(this);
        }
    }

    @SuppressLint("WrongConstant")
    @Override
    protected void onDraw(Canvas canvas) {

        initcanvas(canvas);

        //
        if (rotate != 0) {
            canvas.rotate(rotate, width >> 1, height >> 1);
        }
        if (!tSuper) {
            return;
        }

        //
        boolean needSaveLayer = (srcNormal != null || srcPress != null || srcSelect != null);
        if (needSaveLayer) {
            // draw the src/dst example into our offscreen bitmap
            layer = canvas.saveLayer(0, 0, width, height, null);
        }

        // dst, note that when srcSelectShadowRadius > 0, the parent background color must take incoming as backgroundNormal!

        if (classic) {

            if (materialPlay) {
                drawRectClassic(canvas, backgroundNormalShadowRadius + backgroundNormalShadowDx, backgroundNormalShadowRadius + backgroundNormalShadowDy,
                        width - backgroundNormalShadowRadius - backgroundNormalShadowDx, height - backgroundNormalShadowRadius
                                - backgroundNormalShadowDy, backgroundNormal, backgroundNormalShader, backgroundNormalShadowRadius,
                        backgroundNormalShadowColor, backgroundNormalShadowDx, backgroundNormalShadowDy, strokeWidthNormal, strokeColorNormal, radius);

                canvas.drawCircle(touchDownEventX, touchDownEventY, materialRadius, PaintTool.initPaint(backgroundPress));
            } else {
                drawRectClassic(canvas, backgroundNormalShadowRadius + backgroundNormalShadowDx, backgroundNormalShadowRadius + backgroundNormalShadowDy,
                        width - backgroundNormalShadowRadius - backgroundNormalShadowDx, height - backgroundNormalShadowRadius
                                - backgroundNormalShadowDy, select ? backgroundSelect : press ? backgroundPress : backgroundNormal,
                        select ? backgroundSelectShader : press ? backgroundPressShader : backgroundNormalShader,
                        select ? backgroundSelectShadowRadius : press ? backgroundPressShadowRadius : backgroundNormalShadowRadius,
                        select ? backgroundSelectShadowColor : press ? backgroundPressShadowColor : backgroundNormalShadowColor,
                        select ? backgroundSelectShadowDx : press ? backgroundPressShadowDx : backgroundNormalShadowDx,
                        select ? backgroundSelectShadowDy : press ? backgroundPressShadowDy : backgroundNormalShadowDy, select ? strokeWidthSelect
                                : press ? strokeWidthPress : strokeWidthNormal, select ? strokeColorSelect : press ? strokeColorPress
                                : strokeColorNormal, radius);
            }
        } else {

            // draw MaterialDesign Effect
            if (materialPlay) {
                drawRectCustom(canvas, backgroundNormalShadowRadius + backgroundNormalShadowDx, backgroundNormalShadowRadius + backgroundNormalShadowDy,
                        width - backgroundNormalShadowRadius - backgroundNormalShadowDx, height - backgroundNormalShadowRadius
                                - backgroundNormalShadowDy, backgroundNormal, backgroundNormalShader, backgroundNormalShadowRadius,
                        backgroundNormalShadowColor, backgroundNormalShadowDx, backgroundNormalShadowDy, strokeWidthNormal, strokeColorNormal,
                        radiusLeftTop, radiusLeftBottom, radiusRightTop, radiusRightBottom);

                canvas.drawCircle(touchDownEventX, touchDownEventY, materialRadius, PaintTool.initPaint(backgroundPress));
            } else {
                drawRectCustom(canvas, backgroundNormalShadowRadius + backgroundNormalShadowDx, backgroundNormalShadowRadius + backgroundNormalShadowDy,
                        width - backgroundNormalShadowRadius - backgroundNormalShadowDx, height - backgroundNormalShadowRadius
                                - backgroundNormalShadowDy, select ? backgroundSelect : press ? backgroundPress : backgroundNormal,
                        select ? backgroundSelectShader : press ? backgroundPressShader : backgroundNormalShader,
                        select ? backgroundSelectShadowRadius : press ? backgroundPressShadowRadius : backgroundNormalShadowRadius,
                        select ? backgroundSelectShadowColor : press ? backgroundPressShadowColor : backgroundNormalShadowColor,
                        select ? backgroundSelectShadowDx : press ? backgroundPressShadowDx : backgroundNormalShadowDx,
                        select ? backgroundSelectShadowDy : press ? backgroundPressShadowDy : backgroundNormalShadowDy, select ? strokeWidthSelect
                                : press ? strokeWidthPress : strokeWidthNormal, select ? strokeColorSelect : press ? strokeColorPress
                                : strokeColorNormal, radiusLeftTop, radiusLeftBottom, radiusRightTop, radiusRightBottom);
            }
        }

        // draw bitmap
        if (needSaveLayer) {
            paint.setXfermode(porterDuffXfermode);

            // If they are offset backgroundShadow, mobile, is to draw on the background shadow,
            // without moving the bigger picture and the need to set the width and height

            canvas.translate(select ? backgroundSelectShadowDx * 2f + srcSelectShadowRadius - srcSelectShadowDx : press ? backgroundPressShadowDx * 2f
                            + srcPressShadowRadius - srcPressShadowDx : backgroundNormalShadowDx * 2f + srcNormalShadowRadius - srcNormalShadowDx,
                    select ? backgroundSelectShadowDy * 2f + srcSelectShadowRadius - srcSelectShadowDy : press ? backgroundPressShadowDy * 2f
                            + srcPressShadowRadius - srcPressShadowDy : backgroundNormalShadowDy * 2f + srcNormalShadowRadius - srcNormalShadowDy);
            canvas.drawBitmap(
                    select ? srcSelect : press ? srcPress : srcNormal,
                    matrix,
                    PaintTool.initPaint(paint, select ? srcSelectShadowRadius : press ? srcPressShadowRadius : srcNormalShadowRadius,
                            select ? srcSelectShadowDx : press ? srcPressShadowDx : srcNormalShadowDx, select ? srcSelectShadowDy
                                    : press ? srcPressShadowDy : srcNormalShadowDy));
            canvas.translate(select ? -backgroundSelectShadowDx * 2f - srcSelectShadowRadius + srcSelectShadowDx : press ? -backgroundPressShadowDx * 2f
                            - srcPressShadowRadius + srcPressShadowDx : -backgroundNormalShadowDx * 2f - srcNormalShadowRadius + srcNormalShadowDx,
                    select ? -backgroundSelectShadowDy * 2f - srcSelectShadowRadius + srcSelectShadowDy : press ? -backgroundPressShadowDy * 2f
                            - srcPressShadowRadius + srcPressShadowDy : -backgroundNormalShadowDy * 2f - srcNormalShadowRadius + srcNormalShadowDy);

            paint.setXfermode(null);

            // Uncomment will cause a null pointer with paint in xml preview canvas.restoreToCount(layer);
        }

        // draw anchor
        if (srcAnchorNormal != null) {

            float anchorDx = 0, anchorDy = 0;
            switch (srcAnchorGravity & GRAVITY_MASK) {
                case LEFT:
                    break;
                case CENTER_HORIZONTAL:
                    anchorDx = (width >> 1) - srcAnchorNormalWidth * 0.5f;
                    break;
                case RIGHT:
                    anchorDx = width - srcAnchorNormalWidth;
                    break;
                case CENTER_VERTICAL:
                    anchorDy = (height >> 1) - srcAnchorNormalHeight * 0.5f;
                    break;
                case CENTER:
                    anchorDx = (width >> 1) - srcAnchorNormalWidth * 0.5f;
                    anchorDy = (height >> 1) - srcAnchorNormalHeight * 0.5f;
                    break;
                case RIGHT | CENTER_VERTICAL:
                    anchorDx = width - srcAnchorNormalWidth;
                    anchorDy = (height >> 1) - srcAnchorNormalHeight * 0.5f;
                    break;
                case BOTTOM:
                    anchorDy = height - srcAnchorNormalHeight;
                    break;
                case BOTTOM | CENTER_HORIZONTAL:
                    anchorDx = (width >> 1) - srcAnchorNormalWidth * 0.5f;
                    anchorDy = height - srcAnchorNormalHeight;
                    break;
                case BOTTOM | RIGHT:
                    anchorDx = width - srcAnchorNormalWidth;
                    anchorDy = height - srcAnchorNormalHeight;
                    break;
                default:
                    break;
            }

            canvas.translate(anchorDx + (select ? srcAnchorSelectDx : press ? srcAnchorPressDx : srcAnchorNormalDx), anchorDy
                    + (select ? srcAnchorSelectDy : press ? srcAnchorPressDy : srcAnchorNormalDy));

            canvas.drawBitmap(select ? srcAnchorSelect : press ? srcAnchorPress : srcAnchorNormal, anchorMatrix, paint);

            canvas.translate(-anchorDx + (select ? -srcAnchorSelectDx : press ? -srcAnchorPressDx : -srcAnchorNormalDx), -anchorDy
                    + (select ? -srcAnchorSelectDy : press ? -srcAnchorPressDy : -srcAnchorNormalDy));
        }

        // draw text
        if (textValue != null) {
            float f[] = drawText(
                    canvas,
                    textValue,
                    width,
                    (width >> 1) + textDx + srcLeftWidth * 0.5f + srcLeftPadding * 0.5f - srcRightWidth * 0.5f - srcRightPadding * 0.5f,
                    (height >> 1) + textDy,
                    textPaddingLeft + srcLeftWidth,
                    textPaddingRight + srcRightWidth,
                    initTextPaint(Paint.Style.FILL,
                            materialPlay ? textColorPress : select ? textColorSelect : press ? textColorPress : textColorNormal, textSize,
                            textShadowRadius, textShadowColor, textShadowDx, textShadowDy, textTypeFace, Paint.Align.CENTER),
                    textGravity,
                    textRowSpaceRatio,
                    textValueMeasureList);

            textDrawWidth = f[0];
            textEndOffsetCenterX = f[1];
            textEndOffsetCenterY = f[2];
        }

        // draw content
        if (contentValue != null) {
            //Conversion of style into textGravity to use drawText method!
            TextGravity textGravityFromContent = textGravityArray[contentGravity.nativeInt];

            float f[] = drawText(
                    canvas,
                    contentValue,
                    width,
                    (width >> 1) + contentDx + srcLeftWidth * 0.5f + srcLeftPadding * 0.5f - srcRightWidth * 0.5f - srcRightPadding * 0.5f,
                    (height >> 1) + contentDy,
                    contentPaddingLeft + srcLeftWidth,
                    contentPaddingRight + srcRightWidth,
                    initTextPaint(Paint.Style.FILL,
                            materialPlay ? contentColorPress : select ? contentColorSelect : press ? contentColorPress : contentColorNormal, contentSize,
                            contentShadowRadius, contentShadowColor, contentShadowDx, contentShadowDy, contentTypeFace, Paint.Align.CENTER),
                    textGravityFromContent,
                    contentRowSpaceRatio,
                    contentValueMeasureList);

            contentDrawWidth = f[0];
            contentEndOffsetCenterX = f[1];
            contentEndOffsetCenterY = f[2];
        }

        // draw bitmapLeft,the draw position is half of the width minus
        // the srcLeftPadding and textActualDrawWidth*0.5f
        if (srcLeft != null) {
            float dx = (width >> 1) - srcLeftWidth * 0.5f - textDrawWidth * 0.5f - srcLeftPadding * 0.5f + srcLeftDx;
            float dy = (height >> 1) - srcLeftHeight * 0.5f + srcLeftDy;

            canvas.translate(dx, dy);
            canvas.drawBitmap(srcLeft, leftMatrix, paint);
            canvas.translate(-dx, -dy);
        }

        if (srcRight != null) {

            float dx = (width >> 1) - srcRightWidth * 0.5f + textDrawWidth * 0.5f + srcRightPadding * 0.5f + srcRightDx;
            float dy = (height >> 1) - srcRightHeight * 0.5f + srcRightDy;

            canvas.translate(dx, dy);
            canvas.drawBitmap(srcRight, rightMatrix, paint);
            canvas.translate(-dx, -dy);
        }

        // draw textMark
        if (textMark) {
            drawTextMark(canvas, textMarkRadius,
                    PaintTool.initPaint(textMarkColor),
                    textMarkRadius,
                    textMarkDx, textMarkDy,
                    textDx + textEndOffsetCenterX + textMarkDx,
                    textDy + textEndOffsetCenterY + textMarkDy,
                    textMarkTextValue,
                    textMarkTextColor,
                    textMarkTextSize,
                    textMarkTextValueMeasureList);
        }

        // draw contentMark
        if (contentMark) {
            drawTextMark(canvas, contentMarkRadius,
                    PaintTool.initPaint(contentMarkColor),
                    contentMarkRadius,
                    contentMarkDx, contentMarkDy,
                    contentDx + contentEndOffsetCenterX + contentMarkDx,
                    contentDy + contentEndOffsetCenterY + contentMarkDy,
                    contentMarkTextValue,
                    contentMarkTextColor,
                    contentMarkTextSize,
                    contentMarkTextValueMeasureList);
        }

        // draw foreground
        if (select && foregroundSelect != Color.TRANSPARENT) {
            if (classic) {
                drawRectClassic(canvas, width, height, foregroundSelect, radius);
            } else {
                drawRectCustom(canvas, width, height, foregroundSelect, 0, Color.TRANSPARENT, radiusLeftTop, radiusLeftBottom, radiusRightTop, radiusRightBottom);
            }
        } else if (press && foregroundPress != Color.TRANSPARENT) {
            if (classic) {
                drawRectClassic(canvas, width, height, foregroundPress, radius);
            } else {
                drawRectCustom(canvas, width, height, foregroundPress, 0, Color.TRANSPARENT, radiusLeftTop, radiusLeftBottom, radiusRightTop, radiusRightBottom);
            }
        } else if (foregroundNormal != Color.TRANSPARENT) {
            if (classic) {
                drawRectClassic(canvas, width, height, foregroundNormal, radius);
            } else {
                drawRectCustom(canvas, width, height, foregroundNormal, 0, Color.TRANSPARENT, radiusLeftTop, radiusLeftBottom, radiusRightTop, radiusRightBottom);
            }
        }
        if (rotate != 0) {
            canvas.rotate(-rotate, width >> 1, height >> 1);
        }
        if (drawListener != null) {
            drawListener.draw(this);
        }
    }
}