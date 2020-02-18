package com.tunasushi.tuna;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
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
import android.widget.TextView;
import android.widget.ToggleButton;

import com.tunasushi.tool.DeviceTool;
import com.tuna.R;

import java.util.ArrayList;
import java.util.List;

import static com.tunasushi.tool.BitmapTool.decodeBitmapResource;
import static com.tunasushi.tool.ConvertTool.convertToPX;
import static com.tunasushi.tool.ConvertTool.dpToPx;
import static com.tunasushi.tool.ConvertTool.pxToDp;
import static com.tunasushi.tool.ViewTool.getLinearGradient;

/**
 * @author TunaSashimi
 * @date 2015-11-04 17:25
 * @Copyright 2015 Tunasashimi. All rights reserved.
 * @Description
 */

public class TView extends View {
    /**
     * The following fields and methods of the parent class and subclass can always use
     */
    protected String tag;

    // the width and height of the TView(put together to save the number of rows)
    protected int width, height;

    //
    protected int layer;
    protected int total;

    protected Bitmap srcBitmap;

    protected float srcWidthScale, srcHeightScale;

    protected float dx, dy;
    protected float scale, scaleSx, scaleSy;

    protected float percent;
    protected float surplus, share;

    protected float[] floatArray;
    protected String[] stringArray;

    //paint cannot be used as a static method!
    //Multithreading can go wrong!
    protected Paint paint;

    protected Paint getPaint() {
        return paint;
    }

    protected void setPaint(Paint paint) {
        this.paint = paint;
    }

    // 0
    protected Paint initPaint() {
        if (paint == null) {
            return paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        } else {
            paint.reset();
            paint.clearShadowLayer();
            paint.setAntiAlias(true);
        }
        return paint;
    }

    // 1
    protected Paint initPaint(int colorValue) {
        return initPaint(Paint.Style.FILL, 0, colorValue, null, 0, Color.TRANSPARENT, 0, 0, -1);
    }

    // 2
    protected Paint initPaint(Paint.Style style, int colorValue) {
        return initPaint(style, 0, colorValue, null, 0, Color.TRANSPARENT, 0, 0, -1);
    }

    // 2
    protected Paint initPaint(Paint.Style style, Shader shader) {
        return initPaint(style, 0, Color.TRANSPARENT, shader, 0, Color.TRANSPARENT, 0, 0, -1);
    }

    // 3
    protected Paint initPaint(Paint.Style style, int colorValue, float strokeWidth) {
        return initPaint(style, strokeWidth, colorValue, null, 0, Color.TRANSPARENT, 0, 0, -1);
    }

    // 3
    protected Paint initPaint(Paint.Style style, int colorValue, Shader shader) {
        return initPaint(style, 0, colorValue, shader, 0, Color.TRANSPARENT, 0, 0, -1);
    }

    // 3
    protected Paint initPaint(Paint.Style style, Shader shader, int alpha) {
        return initPaint(style, 0, Color.TRANSPARENT, shader, 0, Color.TRANSPARENT, 0, 0, alpha);
    }

    // 4
    protected Paint initPaint(Paint.Style style, int colorValue, Shader shader, int alpha) {
        return initPaint(style, 0, colorValue, shader, 0, Color.TRANSPARENT, 0, 0, alpha);
    }

    // 4
    protected Paint initPaint(Paint.Style style, int colorValue, float strokeWidth, int alpha) {
        return initPaint(style, strokeWidth, colorValue, null, 0, Color.TRANSPARENT, 0, 0, alpha);
    }

    // 4
    protected Paint initPaint(Paint paint, float shadowRadius, float shadowDx, float shadowDy) {
        paint.clearShadowLayer();
        if (shadowRadius > 0) {
            paint.setShadowLayer(shadowRadius, shadowDx, shadowDy, Color.WHITE);
        }
        return paint;
    }

    // 6
    protected Paint initPaint(Paint.Style style, Shader shader, float shadowRadius, int shadowColor, float shadowDx, float shadowDy) {
        return initPaint(style, 0, Color.TRANSPARENT, shader, shadowRadius, shadowColor, shadowDx, shadowDy, -1);
    }

    // 6
    protected Paint initPaint(Paint.Style style, int colorValue, float shadowRadius, int shadowColor, float shadowDx, float shadowDy) {
        return initPaint(style, 0, colorValue, null, shadowRadius, shadowColor, shadowDx, shadowDy, -1);
    }

    // 9
    protected Paint initPaint(Paint.Style style, float strokeWidth, int colorValue, Shader shader, float shadowRadius, int shadowColor, float shadowDx, float shadowDy, int alpha) {
        //
        initPaint();
        //
        if (style != null) {
            paint.setStyle(style);
        }
        if (strokeWidth != 0) {
            paint.setStrokeWidth(strokeWidth);
        }

        // When the shadow color can not be set to transparent, but can not set
        if (shader == null) {
            paint.setColor(colorValue);
        } else {
            paint.setShader(shader);
        }

        if (shadowRadius != 0) {
            paint.setShadowLayer(shadowRadius, shadowDx, shadowDy, shadowColor);
        }
        if (alpha != -1) {
            paint.setAlpha(alpha);
        }
        return paint;
    }

    // 1
    protected Paint initTextPaint(float textSize) {
        return initTextPaint(null, Color.WHITE, textSize, 0, Color.TRANSPARENT, 0, 0, null, null);
    }

    // 2
    protected Paint initTextPaint(int textColor, float textSize) {
        return initTextPaint(null, textColor, textSize, 0, Color.TRANSPARENT, 0, 0, null, null);
    }

    // 3
    protected Paint initTextPaint(int textColor, float textSize, Paint.Align align) {
        return initTextPaint(null, textColor, textSize, 0, Color.TRANSPARENT, 0, 0, null, align);
    }

    // 4
    protected Paint initTextPaint(Paint.Style style, int textColor, float textSize, Paint.Align align) {
        return initTextPaint(style, textColor, textSize, 0, Color.TRANSPARENT, 0, 0, null, align);
    }

    // 5
    protected Paint initTextPaint(Paint.Style style, int textColor, float textSize, Typeface typeFace, Paint.Align align) {
        return initTextPaint(style, textColor, textSize, 0, Color.TRANSPARENT, 0, 0, typeFace, align);
    }

    // 8
    protected Paint initTextPaint(Paint.Style style, int colorValue, float textSize, float shadowRadius, int shadowColor, float shadowDx, float shadowDy, Typeface typeFace, Paint.Align align) {
        //
        initPaint();
        //
        if (style != null) {
            paint.setStyle(style);
        }
        paint.setColor(colorValue);

        if (textSize != 0) {
            paint.setTextSize(textSize);
        }
        if (shadowRadius != 0) {
            paint.setShadowLayer(shadowRadius, shadowDx, shadowDy, shadowColor);
        }
        if (align != null) {
            paint.setTextAlign(align);
        }

        if (typeFace != null) {
            paint.setTypeface(typeFace);
        }
        return paint;
    }


    // 8
    protected void drawRectCustom(Canvas canvas, int width, int height, int fillColor, float radiusLeftTop, float radiusLeftBottom,
                                  float radiusRightTop, float radiusRightBottom) {
        drawRectCustom(canvas, 0, 0, width, height, fillColor, null, 0, Color.TRANSPARENT, 0, 0, 0, Color.TRANSPARENT, radiusLeftTop, radiusLeftBottom, radiusRightTop,
                radiusRightBottom);
    }

    // 10
    protected void drawRectCustom(Canvas canvas, int left, int top, int right, int bottom, int fillColor, float radiusLeftTop, float radiusLeftBottom,
                                  float radiusRightTop, float radiusRightBottom) {
        drawRectCustom(canvas, left, top, right, bottom, fillColor, null, 0, Color.TRANSPARENT, 0, 0, 0, Color.TRANSPARENT, radiusLeftTop, radiusLeftBottom, radiusRightTop,
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
                            Path.Direction.CW), initPaint(Paint.Style.STROKE, strokeColor, strokeWidth));
        }

        int radiiLength = radii.length;
        for (int i = 0; i < radiiLength; i++) {
            radii[i] -= strokeWidth;
            radii[i] = radii[i] >= 0 ? radii[i] : 0;
        }

        canvas.drawPath(
                initPathRoundRect(initRectF(left + strokeWidth, top + strokeWidth, right - strokeWidth, bottom - strokeWidth), radii, Path.Direction.CW),
                shader == null ? shadowRadius == 0 ? initPaint(fillColor) : initPaint(Paint.Style.FILL, fillColor, shadowRadius, shadowColor, shadowDx,
                        shadowDy) : shadowRadius == 0 ? initPaint(Paint.Style.FILL, shader) : initPaint(Paint.Style.FILL, shader, shadowRadius, shadowColor, shadowDx,
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
                    initPaint(Paint.Style.STROKE, strokeColor, strokeWidth));
        }

        canvas.drawRoundRect(
                initRectF(left + strokeWidth, top + strokeWidth, right - strokeWidth, bottom - strokeWidth),
                radius,
                radius,
                shader == null ? shadowRadius == 0 ? initPaint(fillColor) : initPaint(Paint.Style.FILL, fillColor, shadowRadius, shadowColor, shadowDx,
                        shadowDy) : shadowRadius == 0 ? initPaint(Paint.Style.FILL, shader) : initPaint(Paint.Style.FILL, shader, shadowRadius, shadowColor, shadowDx,
                        shadowDy));
    }


    // 6
    protected float[] drawText(Canvas canvas, String string, float width, float centerX, float centerY, Paint paint) {
        return drawText(canvas, string, width, centerX, centerY, 0, 0, paint, TView.TextGravity.ALL_CENTER, 1.0f, null);
    }

    // 8
    protected float[] drawText(Canvas canvas, String string, float width, float centerX, float centerY, float paddingLeft, float paddingRight, Paint paint) {
        return drawText(canvas, string, width, centerX, centerY, paddingLeft, paddingRight, paint, TView.TextGravity.ALL_CENTER, 1.0f, null);
    }

    // 9
    protected float[] drawText(Canvas canvas, String string, float width, float centerX, float centerY, float paddingLeft, float paddingRight, Paint paint,
                               float textRowSpaceRatio, List<Integer> valueMeasureList) {
        return drawText(canvas, string, width, centerX, centerY, paddingLeft, paddingRight, paint, TView.TextGravity.ALL_CENTER, textRowSpaceRatio, valueMeasureList);
    }

    // 10
    protected float[] drawText(Canvas canvas, String string, float width, float centerX, float centerY, float paddingLeft, float paddingRight, Paint paint,
                               TView.TextGravity textGravity, float textRowSpaceRatio, List<Integer> valueMeasureList) {
        if (valueMeasureList == null) {
            valueMeasureList = generateMeasureList(string, paint, width, paddingLeft, paddingRight);
        }

        float textMiddleRow = (valueMeasureList.size() + 1) * 0.5f;

        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
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
            int width,
            int height,
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
                    initTextPaint(Paint.Style.FILL, markTextColor, markTextSize, Paint.Align.CENTER)
                    , 1, valueMeasureList);
        }
    }

    //
    protected void drawArrow(Canvas canvas,
                             int width, int height,
                             float floatX,
                             float arrowWidth, float arrowHeight,
                             float arrowStrokeWidth, int arrowStrokeColor,
                             boolean upward) {
        if (upward) {
            initPathMoveTo(0, height - arrowStrokeWidth * 0.5f);
            path.lineTo(floatX - arrowWidth * 0.5f, height - arrowStrokeWidth * 0.5f);
            path.lineTo(floatX, height - arrowStrokeWidth * 0.5f - arrowHeight);
            path.lineTo(floatX + arrowWidth * 0.5f, height - arrowStrokeWidth * 0.5f);
            path.lineTo(width, height - arrowStrokeWidth * 0.5f);
        } else {
            initPathMoveTo(0, arrowStrokeWidth * 0.5f);
            path.lineTo(floatX - arrowWidth * 0.5f, arrowStrokeWidth * 0.5f);
            path.lineTo(floatX, arrowHeight + arrowStrokeWidth * 0.5f);
            path.lineTo(floatX + arrowWidth * 0.5f, arrowStrokeWidth * 0.5f);
            path.lineTo(width, arrowStrokeWidth * 0.5f);
        }

        canvas.drawPath(path, initPaint(Paint.Style.STROKE, arrowStrokeColor, arrowStrokeWidth));
    }

    //
    protected Path path;

    protected Path getPath() {
        return path;
    }

    protected Path initPath() {
        if (path == null) {
            path = new Path();
        } else {
            path.reset();
        }
        return path;
    }

    protected Path initPathMoveTo(float x, float y) {
        if (path == null) {
            path = new Path();
        } else {
            path.reset();
        }
        path.moveTo(x, y);
        return path;
    }

    protected Path initPathLineTo(float x, float y) {
        if (path == null) {
            path = new Path();
        } else {
            path.reset();
        }
        path.lineTo(x, y);
        return path;
    }

    protected Path initPathArc(RectF oval, float startAngle, float sweepAngle) {
        if (path == null) {
            path = new Path();
        } else {
            path.reset();
        }
        path.addArc(oval, startAngle, sweepAngle);
        return path;
    }

    protected Path initPathRoundRect(RectF rect, float[] radii, Path.Direction dir) {
        if (path == null) {
            path = new Path();
        } else {
            path.reset();
        }
        // This setting will cause the following message appears reading xml
        // The graphics preview in the layout editor may not be accurate:
        // Different corner sizes are not supported in Path.addRoundRect.
        // (Ignore for this session)
        path.addRoundRect(rect, radii, dir);
        return path;
    }

    //
    public float x;
    //
    public float y;

    public void setTouchXY(float touchX, float touchY) {
        setTouchXYRaw(touchX, touchY);
    }

    public void setTouchXY(float touchX, float touchY, int unit) {
        setTouchXYRaw(convertToPX(touchX, unit), convertToPX(touchY, unit));
    }

    //There are also cases of external calls, so the assignment should be placed in the subclass rather than the parent class!
    public void setTouchXYRaw(float touchX, float touchY) {
    }

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

    protected RectF getRectF() {
        return rectF;
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

    //The parent class has the method getTMatrix !
    //Cannot write a method with the same name or the xml cannot be previewed!
    public Matrix getTMatrix() {
        return matrix;
    }

    public void setTMatrix(Matrix matrix) {
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

    protected Matrix initMatrix(Matrix matrix, float sx, float sy) {
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
    protected void initCanvas(Canvas canvas) {
        if (canvas.getDrawFilter() == null) {
            canvas.setDrawFilter(paintFlagsDrawFilter);
            canvasHardwareAccelerated = canvas.isHardwareAccelerated();
        }
    }

    //
    public void setStatus(boolean press, boolean select, boolean textMark) {
        if (this.press != press || this.select != select || this.textMark != textMark) {
            this.press = press;
            this.select = select;
            this.textMark = textMark;
            invalidate();
        }
    }

    //
    public void setStatus(boolean press, boolean select, boolean textMark, boolean material) {
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


        DeviceTool.initDisplayMetrics();
        text_display.setText(DeviceTool.stringBuffer);

        edit_width.setText(String.valueOf(pxToDp(width)));
        edit_height.setText(String.valueOf(pxToDp(height)));

        edit_backgroundNormal.setText(backgroundNormal != 0 ? Integer.toHexString(backgroundNormal) : "00000000");
        edit_backgroundPress.setText(backgroundPress != 0 ? Integer.toHexString(backgroundPress) : "00000000");
        edit_backgroundSelect.setText(backgroundSelect != 0 ? Integer.toHexString(backgroundSelect) : "00000000");

        edit_textSize.setText(String.valueOf(pxToDp(textSize)));
        edit_textColorNormal.setText(textColorNormal != 0 ? Integer.toHexString(textColorNormal) : "00000000");

        edit_strokeWidth.setText(String.valueOf(pxToDp(strokeWidthNormal)));
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
        View.OnClickListener onClickListener = new View.OnClickListener() {
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

                        textSize = dpToPx(Float.parseFloat(edit_textSize.getText().toString().trim()));

                        strokeWidthNormal = dpToPx(Float.parseFloat(edit_strokeWidth.getText().toString().trim()));

                        textMark = text_mark.getText().toString().trim().equals("true") ? true : false;

                        setLayout(dpToPx(Float.parseFloat(edit_width.getText().toString().trim())), dpToPx(Float.parseFloat(edit_height.getText().toString().trim())));
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

    private boolean origin;

    public boolean isOrigin() {
        return origin;
    }

    public void setOrigin(boolean origin) {
        this.origin = origin;
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

    protected boolean touchDown;
    protected boolean touchMove;
    protected boolean touchUp;
    protected boolean touchCancel;

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

    public boolean isAnimationable() {
        return animationable;
    }

    public void setAnimationable(boolean animationable) {
        this.animationable = animationable;
        if (animationable) {
            invalidate();
        }
    }

    //
    protected float touchX;

    public float getTouchX() {
        return touchX;
    }

    public void setTouchX(float touchEventEventX) {
        this.touchX = touchEventEventX;
    }

    //
    protected float touchY;

    public float getTouchY() {
        return touchY;
    }

    public void setTouchY(float touchEventEventY) {
        this.touchY = touchEventEventY;
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
        void layout(TView t);
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
        void draw(TView t);
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
        void animateEnd(TView t);
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
        void touch(TView t);
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
        void touchDown(TView t);
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
        void touchMove(TView t);
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
        void touchUp(TView t);
    }

    public TouchUpListener getTouchUpListener() {
        return touchUpListener;
    }

    public void setTouchUpListener(TouchUpListener touchUpListener) {
        this.touchUpListener = touchUpListener;
    }

    /**
     * 增加常用的OnClickListener赋予TouchUpListener同样的触发
     */
    protected OnClickListener onClickListener;

    public interface OnClickListener {
        void onClick(TView t);
    }

    public OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    //
    protected TouchCancelListener touchCancelListener;

    public interface TouchCancelListener {
        void touchCancel(TView t);
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
        void touchOut(TView t);
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
        void touchIn(TView t);
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
        void associate(TView t);
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
        void subLayout(TView t);
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
        void subDraw(TView t);
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
        void subAnimateEnd(TView t);
    }

    public SubAnimateEndListener getSubAnimateEndListener() {
        return subAnimateEndListener;
    }

    public void setSubAnimateEndListener(SubAnimateEndListener subAnimateEndListener) {
        this.subAnimateEndListener = subAnimateEndListener;
    }

    // radius default 0
    protected float radius;

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        setRadiusRaw(radius);
    }

    public void setRadius(float radius, int unit) {
        setRadiusRaw(convertToPX(radius, unit));
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
        setRadiusLeftTopRaw(radiusLeftTop);
    }

    public void setRadiusLeftTop(float radiusLeftTop, int unit) {
        setRadiusLeftTopRaw(convertToPX(radiusLeftTop, unit));
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
        setRadiusLeftBottomRaw(radiusLeftBottom);
    }

    public void setRadiusLeftBottom(float radiusLeftBottom, int unit) {
        setRadiusLeftBottomRaw(convertToPX(radiusLeftBottom, unit));
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
        setRadiusRightTopRaw(radiusRightTop);
    }

    public void setRadiusRightTop(float radiusRightTop, int unit) {
        setRadiusRightTopRaw(convertToPX(radiusRightTop, unit));
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
        setRadiusRightBottomRaw(radiusRightBottom);
    }

    public void setRadiusRightBottom(float radiusRightBottom, int unit) {
        setRadiusRightBottomRaw(convertToPX(radiusRightBottom, unit));
    }

    private void setRadiusRightBottomRaw(float radiusRightBottom) {
        if (this.radiusRightBottom != radiusRightBottom) {
            this.radiusRightBottom = radiusRightBottom;
            invalidate();
        }
    }

    /**
     * The following fields and methods can be used only in the origin TView
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

    protected final int DIRECTION_LEFT = 0x00000001;
    protected final int DIRECTION_RIGHT = DIRECTION_LEFT << 1;
    protected final int DIRECTION_TOP = DIRECTION_RIGHT << 1;
    protected final int DIRECTION_BOTTOM = DIRECTION_TOP << 1;
    protected final int DIRECTION_MASK = DIRECTION_LEFT | DIRECTION_RIGHT | DIRECTION_TOP | DIRECTION_BOTTOM;

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
        setBackgroundNormalShadowRadiusRaw(backgroundNormalShadowRadius);
    }

    public void setBackgroundNormalShadowRadius(float backgroundNormalShadowRadius, int unit) {
        setBackgroundNormalShadowRadiusRaw(convertToPX(backgroundNormalShadowRadius, unit));
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
        setBackgroundNormalShadowDxRaw(backgroundNormalShadowDx);
    }

    public void setBackgroundNormalShadowDx(float backgroundNormalShadowDx, int unit) {
        setBackgroundNormalShadowDxRaw(convertToPX(backgroundNormalShadowDx, unit));
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
        setBackgroundNormalShadowDyRaw(backgroundNormalShadowDy);
    }

    public void setBackgroundNormalShadowDy(float backgroundNormalShadowDy, int unit) {
        setBackgroundNormalShadowDyRaw(convertToPX(backgroundNormalShadowDy, unit));
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
        setBackgroundPressShadowRadiusRaw(backgroundPressShadowRadius);
    }

    public void setBackgroundPressShadowRadius(float backgroundPressShadowRadius, int unit) {
        setBackgroundPressShadowRadiusRaw(convertToPX(backgroundPressShadowRadius, unit));
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
        setBackgroundPressShadowDxRaw(backgroundPressShadowDx);
    }

    public void setBackgroundPressShadowDx(float backgroundPressShadowDx, int unit) {
        setBackgroundPressShadowDxRaw(convertToPX(backgroundPressShadowDx, unit));
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
        setBackgroundPressShadowDyRaw(backgroundPressShadowDy);
    }

    public void setBackgroundPressShadowDy(float backgroundPressShadowDy, int unit) {
        setBackgroundPressShadowDyRaw(convertToPX(backgroundPressShadowDy, unit));
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
        setBackgroundSelectShadowRadiusRaw(backgroundSelectShadowRadius);
    }

    public void setBackgroundSelectShadowRadius(float backgroundSelectShadowRadius, int unit) {
        setBackgroundSelectShadowRadiusRaw(convertToPX(backgroundSelectShadowRadius, unit));
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
        setBackgroundSelectShadowDxRaw(backgroundSelectShadowDx);
    }

    public void setBackgroundSelectShadowDx(float backgroundSelectShadowDx, int unit) {
        setBackgroundSelectShadowDxRaw(convertToPX(backgroundSelectShadowDx, unit));
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
        setBackgroundSelectShadowDyRaw(backgroundSelectShadowDy);
    }

    public void setBackgroundSelectShadowDy(float backgroundSelectShadowDy, int unit) {
        setBackgroundSelectShadowDyRaw(convertToPX(backgroundSelectShadowDy, unit));
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
        setSrcNormal(decodeBitmapResource(id));
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
        setSrcPress(decodeBitmapResource(id));
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
        setSrcSelect(decodeBitmapResource(id));
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
        setSrcNormalShadowRadiusRaw(srcNormalShadowRadius);
    }

    public void setSrcNormalShadowRadius(float srcNormalShadowRadius, int unit) {
        setSrcNormalShadowRadiusRaw(convertToPX(srcNormalShadowRadius, unit));
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
        setSrcNormalShadowDxRaw(srcNormalShadowDx);
    }

    public void setSrcNormalShadowDx(float srcNormalShadowDx, int unit) {
        setSrcNormalShadowDxRaw(convertToPX(srcNormalShadowDx, unit));
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
        setSrcNormalShadowDyRaw(srcNormalShadowDy);
    }

    public void setSrcNormalShadowDy(float srcNormalShadowDy, int unit) {
        setSrcNormalShadowDyRaw(convertToPX(srcNormalShadowDy, unit));
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
        setSrcPressShadowRadiusRaw(srcPressShadowRadius);
    }

    public void setSrcPressShadowRadius(float srcPressShadowRadius, int unit) {
        setSrcPressShadowRadiusRaw(convertToPX(srcPressShadowRadius, unit));
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
        setSrcPressShadowDxRaw(srcPressShadowDx);
    }

    public void setSrcPressShadowDx(float srcPressShadowDx, int unit) {
        setSrcPressShadowDxRaw(convertToPX(srcPressShadowDx, unit));
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
        setSrcPressShadowDyRaw(srcPressShadowDy);
    }

    public void setSrcPressShadowDy(float srcPressShadowDy, int unit) {
        setSrcPressShadowDyRaw(convertToPX(srcPressShadowDy, unit));
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
        setSrcSelectShadowRadiusRaw(srcSelectShadowRadius);
    }

    public void setSrcSelectShadowRadius(float srcSelectShadowRadius, int unit) {
        setSrcSelectShadowRadiusRaw(convertToPX(srcSelectShadowRadius, unit));
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
        setSrcSelectShadowDxRaw(srcSelectShadowDx);
    }

    public void setSrcSelectShadowDx(float srcSelectShadowDx, int unit) {
        setSrcSelectShadowDxRaw(convertToPX(srcSelectShadowDx, unit));
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
        setSrcSelectShadowDyRaw(srcSelectShadowDy);
    }

    public void setSrcSelectShadowDy(float srcSelectShadowDy, int unit) {
        setSrcSelectShadowDyRaw(convertToPX(srcSelectShadowDy, unit));
    }

    private void setSrcSelectShadowDyRaw(float srcSelectShadowDy) {
        if (this.srcSelectShadowDy != srcSelectShadowDy) {
            this.srcSelectShadowDy = srcSelectShadowDy;
            invalidate();
        }
    }

    protected final int LEFT = 0x00000000;
    protected final int CENTER_HORIZONTAL = 0x00000001;
    protected final int RIGHT = CENTER_HORIZONTAL << 1;
    protected final int TOP = 0x00000000;
    protected final int CENTER_VERTICAL = RIGHT << 1;
    protected final int BOTTOM = CENTER_VERTICAL << 1;
    protected final int CENTER = CENTER_HORIZONTAL | CENTER_VERTICAL;
    protected final int GRAVITY_MASK = CENTER_HORIZONTAL | RIGHT | CENTER_VERTICAL | BOTTOM;

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
        setSrcAnchorNormalWidthRaw(srcAnchorNormalWidth);
    }

    public void setSrcAnchorNormalWidth(float srcAnchorNormalWidth, int unit) {
        setSrcAnchorNormalWidthRaw(convertToPX(srcAnchorNormalWidth, unit));
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
        setSrcAnchorNormalHeightRaw(srcAnchorNormalHeight);
    }

    public void setSrcAnchorNormalHeight(float srcAnchorNormalHeight, int unit) {
        setSrcAnchorNormalHeightRaw(convertToPX(srcAnchorNormalHeight, unit));
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
        setSrcAnchorNormalDxRaw(srcAnchorNormalDx);
    }

    public void setSrcAnchorNormalDx(float srcAnchorNormalDx, int unit) {
        setSrcAnchorNormalDxRaw(convertToPX(srcAnchorNormalDx, unit));
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
        setSrcAnchorNormalDyRaw(srcAnchorNormalDy);
    }

    public void setSrcAnchorNormalDy(float srcAnchorNormalDy, int unit) {
        setSrcAnchorNormalDyRaw(convertToPX(srcAnchorNormalDy, unit));
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
        setSrcAnchorPressWidthRaw(srcAnchorPressWidth);
    }

    public void setSrcAnchorPressWidth(float srcAnchorPressWidth, int unit) {
        setSrcAnchorPressWidthRaw(convertToPX(srcAnchorPressWidth, unit));
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
        setSrcAnchorPressHeightRaw(srcAnchorPressHeight);
    }

    public void setSrcAnchorPressHeight(float srcAnchorPressHeight, int unit) {
        setSrcAnchorPressHeightRaw(convertToPX(srcAnchorPressHeight, unit));
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
        setSrcAnchorPressDxRaw(srcAnchorPressDx);
    }

    public void setSrcAnchorPressDx(float srcAnchorPressDx, int unit) {
        setSrcAnchorPressDxRaw(convertToPX(srcAnchorPressDx, unit));
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
        setSrcAnchorPressDyRaw(srcAnchorPressDy);
    }

    public void setSrcAnchorPressDy(float srcAnchorPressDy, int unit) {
        setSrcAnchorPressDyRaw(convertToPX(srcAnchorPressDy, unit));
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
        setSrcAnchorSelectWidthRaw(srcAnchorSelectWidth);
    }

    public void setSrcAnchorSelectWidth(float srcAnchorSelectWidth, int unit) {
        setSrcAnchorSelectWidthRaw(convertToPX(srcAnchorSelectWidth, unit));
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
        setSrcAnchorSelectHeightRaw(srcAnchorSelectHeight);
    }

    public void setSrcAnchorSelectHeight(float srcAnchorSelectHeight, int unit) {
        setSrcAnchorSelectHeightRaw(convertToPX(srcAnchorSelectHeight, unit));
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
        setSrcAnchorSelectDxRaw(srcAnchorSelectDx);
    }

    public void setSrcAnchorSelectDx(float srcAnchorSelectDx, int unit) {
        setSrcAnchorSelectDxRaw(convertToPX(srcAnchorSelectDx, unit));
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
        setSrcAnchorSelectDyRaw(srcAnchorSelectDy);
    }

    public void setSrcAnchorSelectDy(float srcAnchorSelectDy, int unit) {
        setSrcAnchorSelectDyRaw(convertToPX(srcAnchorSelectDy, unit));
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
        setStrokeWidthNormalRaw(strokeWidthNormal);
    }

    public void setStrokeWidthNormal(float strokeWidthNormal, int unit) {
        setStrokeWidthNormalRaw(convertToPX(strokeWidthNormal, unit));
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
        setStrokeWidthPressRaw(strokeWidthPress);
    }

    public void setStrokeWidthPress(float strokeWidthPress, int unit) {
        setStrokeWidthPressRaw(convertToPX(strokeWidthPress, unit));
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
        setStrokeWidthSelectRaw(strokeWidthSelect);
    }

    public void setStrokeWidthSelect(float strokeWidthSelect, int unit) {
        setStrokeWidthSelectRaw(convertToPX(strokeWidthSelect, unit));
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

    // textMark default false
    protected boolean textMark;

    public boolean isTextMark() {
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
        setTextMark(textMarkRadius, textMarkColor, textMarkTextValue, textMarkTextSize,
                textMarkTextColor, textMarkDx, textMarkDy);
    }

    public void setTextMark(float textMarkRadius, int textMarkRadiusUnit,
                            int textMarkColor,
                            String textMarkTextValue,
                            float textMarkTextSize, int textMarkTextSizeUnit,
                            int textMarkTextColor,
                            float textMarkDx, int textMarkDxUnit,
                            float textMarkDy, int textMarkDyUnit) {

        setTextMarkRaw(convertToPX(textMarkRadius, textMarkRadiusUnit), textMarkColor, textMarkTextValue,
                convertToPX(textMarkTextSize, textMarkTextSizeUnit), textMarkTextColor,
                convertToPX(textMarkDx, textMarkDxUnit), convertToPX(textMarkDy, textMarkDyUnit));
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
        setTextMarkRadiusRaw(textMarkRadius);
    }

    public void setTextMarkRadius(float textMarkRadius, int unit) {
        setTextMarkRadiusRaw(convertToPX(textMarkRadius, unit));
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
        setTextMarkTextSizeRaw(textMarkTextSize);
    }

    public void setTextMarkTextSize(float textMarkTextSize, int unit) {
        setTextMarkTextSizeRaw(convertToPX(textMarkTextSize, unit));
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
        setTextMarkDxRaw(textMarkDx);
    }

    public void setTextMarkDx(float textMarkDx, int unit) {
        setTextMarkDxRaw(convertToPX(textMarkDx, unit));
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
        setTextMarkDyRaw(textMarkDy);
    }

    public void setTextMarkDy(float textMarkDy, int unit) {
        setTextMarkDyRaw(convertToPX(textMarkDy, unit));
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
        setTextSizeRaw(textSize);
    }

    public void setTextSize(float textSize, int unit) {
        setTextSizeRaw(convertToPX(textSize, unit));
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
        setTextPaddingLeftRaw(textPaddingLeft);
    }

    public void setTextPaddingLeft(float textPaddingLeft, int unit) {
        setTextPaddingLeftRaw(convertToPX(textPaddingLeft, unit));
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
        setTextPaddingRightRaw(textPaddingRight);
    }

    public void setTextPaddingRight(float textPaddingRight, int unit) {
        setTextPaddingRightRaw(convertToPX(textPaddingRight, unit));
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

    private String textTypeFaceFromAsset;

    public String getTextTypeFaceFromAsset() {
        return textTypeFaceFromAsset;
    }

    public void setTextTypeFaceFromAsset(String textTypeFaceFromAsset) {
        this.textTypeFaceFromAsset = textTypeFaceFromAsset;
    }

    // attention that textDx is the width of the base , textDy is the
    // height of the base
    private float textDx;

    public float getTextDx() {
        return textDx;
    }

    public void setTextDx(float textDx) {
        setTextDxRaw(textDx);
    }

    public void setTextDx(float textDx, int unit) {
        setTextDxRaw(convertToPX(textDx, unit));
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
        setTextDyRaw(textDy);
    }

    public void setTextDy(float textDy, int unit) {
        setTextDyRaw(convertToPX(textDy, unit));
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
        setTextShadowRadiusRaw(textShadowRadius);
    }

    public void setTextShadowRadius(float textShadowRadius, int unit) {
        setTextShadowRadiusRaw(convertToPX(textShadowRadius, unit));
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
        setTextShadowDxRaw(textShadowDx);
    }

    public void setTextShadowDx(float textShadowDx, int unit) {
        setTextShadowDxRaw(convertToPX(textShadowDx, unit));
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
        setTextShadowDyRaw(textShadowDy);
    }

    public void setTextShadowDy(float textShadowDy, int unit) {
        setTextShadowDyRaw(convertToPX(textShadowDy, unit));
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
        setContentSizeRaw(contentSize);
    }

    public void setContentSize(float contentSize, int unit) {
        setContentSizeRaw(convertToPX(contentSize, unit));
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
        setContentShadowRadiusRaw(contentShadowRadius);
    }

    public void setContentShadowRadius(float contentShadowRadius, int unit) {
        setContentShadowRadiusRaw(convertToPX(contentShadowRadius, unit));
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
        setContentShadowDxRaw(contentShadowDx);
    }

    public void setContentShadowDx(float contentShadowDx, int unit) {
        setContentShadowDxRaw(convertToPX(contentShadowDx, unit));
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
        setContentShadowDyRaw(contentShadowDy);
    }

    public void setContentShadowDy(float contentShadowDy, int unit) {
        setContentShadowDyRaw(convertToPX(contentShadowDy, unit));
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
        setContentPaddingLeftRaw(contentPaddingLeft);
    }

    public void setContentPaddingLeft(float contentPaddingLeft, int unit) {
        setContentPaddingLeftRaw(convertToPX(contentPaddingLeft, unit));
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
        setContentPaddingRightRaw(contentPaddingRight);
    }

    public void setContentPaddingRight(float contentPaddingRight, int unit) {
        setContentPaddingRightRaw(convertToPX(contentPaddingRight, unit));
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

    private String contentTypeFaceAssets;

    public String getContentTypeFaceAssets() {
        return contentTypeFaceAssets;
    }

    public void setContentTypeFaceAssets(String contentTypeFaceAssets) {
        this.contentTypeFaceAssets = contentTypeFaceAssets;
    }

    // attention that contentDx is the width of the base , contentDy is the height of the base
    private float contentDx;

    public float getContenttDx() {
        return contentDx;
    }

    public void setContentDx(float contentDx) {
        setContentDxRaw(contentDx);
    }

    public void setContentDx(float contentDx, int unit) {
        setContentDxRaw(convertToPX(contentDx, unit));
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
        setContentDyRaw(contentDy);
    }

    public void setContentDy(float contentDy, int unit) {
        setContentDyRaw(convertToPX(contentDy, unit));
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
        setContentMarkRaw(contentMarkRadius, contentMarkColor, contentMarkTextValue,
                contentMarkTextSize, contentMarkTextColor, contentMarkDx, contentMarkDy);
    }

    public void setContentMark(float contentMarkRadius, int contentMarkRadiusUnit,
                               int contentMarkColor,
                               String contentMarkTextValue,
                               float contentMarkTextSize, int contentMarkTextSizeUnit,
                               int contentMarkTextColor,
                               float contentMarkDx, int contentMarkDxUnit,
                               float contentMarkDy, int contentMarkDyUnit) {

        setContentMarkRaw(convertToPX(contentMarkRadius, contentMarkRadiusUnit), contentMarkColor, contentMarkTextValue,
                convertToPX(contentMarkTextSize, contentMarkTextSizeUnit), contentMarkTextColor,
                convertToPX(contentMarkDx, contentMarkDxUnit), convertToPX(contentMarkDy, contentMarkDyUnit));
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

    public boolean isContentMarkTouchable() {
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
        setContentMarkRadiusRaw(contentMarkRadius);
    }

    public void setContentMarkRadius(float contentMarkRadius, int unit) {
        setContentMarkRadiusRaw(convertToPX(contentMarkRadius, unit));
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
        setContentMarkTextSizeRaw(contentMarkTextSize);
    }

    public void setContentMarkTextSize(float contentMarkTextSize, int unit) {
        setContentMarkTextSizeRaw(convertToPX(contentMarkTextSize, unit));
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
        setContentMarkDxRaw(contentMarkDx);
    }

    public void setContentMarkDx(float contentMarkDx, int unit) {
        setContentMarkDxRaw(convertToPX(contentMarkDx, unit));
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
        setContentMarkDyRaw(contentMarkDy);
    }

    public void setContentMarkDy(float contentMarkDy, int unit) {
        setContentMarkDyRaw(convertToPX(contentMarkDy, unit));
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
        setSrcLeftWidthRaw(srcLeftWidth);
    }

    public void setSrcLeftWidth(float srcLeftWidth, int unit) {
        setSrcLeftWidthRaw(convertToPX(srcLeftWidth, unit));
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
        setSrcLeftHeightRaw(srcLeftHeight);
    }

    public void setSrcLeftHeight(float srcLeftHeight, int unit) {
        setSrcLeftHeightRaw(convertToPX(srcLeftHeight, unit));
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
        setSrcLeftPaddingRaw(srcLeftPadding);
    }

    public void setSrcLeftPadding(float srcLeftPadding, int unit) {
        setSrcLeftPaddingRaw(convertToPX(srcLeftPadding, unit));
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
        setSrcLeftDxRaw(srcLeftDx);
    }

    public void setSrcLeftDx(float srcLeftDx, int unit) {
        setSrcLeftDxRaw(convertToPX(srcLeftDx, unit));
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
        setSrcLeftDyRaw(srcLeftDy);
    }

    public void setSrcLeftDy(float srcLeftDy, int unit) {
        setSrcLeftDyRaw(convertToPX(srcLeftDy, unit));
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
        setSrcRightWidthRaw(srcRightWidth);
    }

    public void setSrcRightWidth(float srcRightWidth, int unit) {
        setSrcRightWidthRaw(convertToPX(srcRightWidth, unit));
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
        setSrcRightHeightRaw(srcRightHeight);
    }

    public void setSrcRightHeight(float srcRightHeight, int unit) {
        setSrcRightHeightRaw(convertToPX(srcRightHeight, unit));
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
        setSrcRightPaddingRaw(srcRightPadding);
    }

    public void setSrcRightPadding(float srcRightPadding, int unit) {
        setSrcRightPaddingRaw(convertToPX(srcRightPadding, unit));
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
        setSrcRightDxRaw(srcRightDx);
    }

    public void setSrcRightDx(float srcRightDx, int unit) {
        setSrcRightDxRaw(convertToPX(srcRightDx, unit));
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
        setSrcRightDyRaw(srcRightDy);
    }

    public void setSrcRightDy(float srcRightDy, int unit) {
        setSrcRightDyRaw(convertToPX(srcRightDy, unit));
    }

    private void setSrcRightDyRaw(float srcRightDy) {
        if (this.srcRightDy != srcRightDy) {
            this.srcRightDy = srcRightDy;
            invalidate();
        }
    }

    // attention TPorterDuffXfermode default 0 instead of -1!
    protected PorterDuffXfermode TPorterDuffXfermode;

    public enum TPorterDuffXfermode {
        SRC_IN(0), SRC_OUT(1),
        ;
        final int nativeInt;

        TPorterDuffXfermode(int ni) {
            nativeInt = ni;
        }
    }

    protected final Mode[] porterDuffXfermodeArray = {PorterDuff.Mode.SRC_IN, PorterDuff.Mode.SRC_OUT,};

    public PorterDuffXfermode getTPorterDuffXfermode() {
        return TPorterDuffXfermode;
    }

    public void setTPorterDuffXfermode(PorterDuffXfermode tPorterDuffXfermode) {
        this.TPorterDuffXfermode = tPorterDuffXfermode;
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

        tag = TView.class.getSimpleName();

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

        // porterDuffXfermodeArrayIndex default PorterDuff.Mode.SRC_IN
        int porterDuffXfermodeArrayIndex = typedArray.getInt(R.styleable.TView_porterDuffXfermode, 0);

        //
        TPorterDuffXfermode = new PorterDuffXfermode(porterDuffXfermodeArray[porterDuffXfermodeArrayIndex]);

        //
        radius = typedArray.getDimension(R.styleable.TView_radius, 0);
        radiusLeftTop = typedArray.getDimension(R.styleable.TView_radiusLeftTop, radius);
        radiusLeftBottom = typedArray.getDimension(R.styleable.TView_radiusLeftBottom, radius);
        radiusRightTop = typedArray.getDimension(R.styleable.TView_radiusRightTop, radius);
        radiusRightBottom = typedArray.getDimension(R.styleable.TView_radiusRightBottom, radius);

        classic = (radius == radiusLeftTop && radiusLeftTop == radiusLeftBottom && radiusLeftBottom == radiusRightTop && radiusRightTop == radiusRightBottom);


        origin = TView.class == this.getClass();

        if (origin) {

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

            // If textTypeFaceFromAsset is set then textTypeFace will be replaced!
            textTypeFaceFromAsset = typedArray.getString(R.styleable.TView_textTypeFaceFromAsset);
            if (textTypeFaceFromAsset != null) {
                textTypeFace = Typeface.createFromAsset(getContext().getAssets(), textTypeFaceFromAsset);
            } else {
                //
                int textTypeFaceIndex = typedArray.getInt(R.styleable.TView_textTypeFace, 0);
                if (textTypeFaceIndex >= 0) {
                    textTypeFace = Typeface.create(Typeface.DEFAULT, textTypeFaceArray[textTypeFaceIndex]);
                }
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


            // If contentTypeFaceAssets is set then contentTypeFace will be replaced!
            contentTypeFaceAssets = typedArray.getString(R.styleable.TView_contentTypeFaceAssets);
            if (contentTypeFaceAssets != null) {
                contentTypeFace = Typeface.createFromAsset(getContext().getAssets(), contentTypeFaceAssets);
            } else {
                int contentTypeFaceIndex = typedArray.getInt(R.styleable.TView_contentTypeFace, 0);
                if (contentTypeFaceIndex >= 0) {
                    contentTypeFace = Typeface.create(Typeface.DEFAULT, contentTypeFaceArray[contentTypeFaceIndex]);
                }
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
        DeviceTool.initDisplayMetrics();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (touchType == TouchType.NONE) {
            return super.dispatchTouchEvent(event);
        }
        //This sentence is telling the parent control, my own event handling! when Drag and other views nested inside the ScrollView will be used !
        if (touchIntercept) {
            getParent().requestDisallowInterceptTouchEvent(true);
        }
        touchX = event.getX();
        touchY = event.getY();

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

                //
                touchDown = true;
                touchMove = false;
                touchUp = false;
                touchCancel = false;

                //
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

                touchDown = false;
                touchMove = true;
                touchUp = false;
                touchCancel = false;

                //
                if (touchType == TouchType.ALWAYS) {

                    press = true;
                    select = false;

                } else if (!touchOutBounds && (touchX < 0 || touchX > width || touchY < 0 || touchY > height)) {

                    press = false;
                    select = false;

                    if (!textMarkTouchable) {
                        textMark = false;
                    }
                    if (!contentMarkTouchable) {
                        contentMark = false;
                    }

                    touchOutBounds = true;
                    if (touchOutListener != null) {
                        touchOutListener.touchOut(this);
                    }
                } else if (touchOutBounds && (touchX >= 0 && touchX <= width && touchY >= 0 && touchY <= height)) {

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

                touchDown = false;
                touchMove = false;
                touchUp = true;
                touchCancel = false;

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

                if (!touchOutBounds && onClickListener != null) {
                    onClickListener.onClick(this);
                }

                touchOutBounds = false;

                break;
            case MotionEvent.ACTION_CANCEL:

                //
                touchDown = false;
                touchMove = false;
                touchUp = false;
                touchCancel = true;

                //
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

        //
        setTouchXY(touchX, touchY);

        if (touchListener != null && (touchType == TouchType.ALWAYS || !touchOutBounds)) {
            touchListener.touch(this);
        }

        if (isOrigin() && !touchOutBounds) {
            invalidate();
        }
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (textValue != null) {
            textValueMeasureList = measure(widthMeasureSpec, heightMeasureSpec,
                    textValue, initTextPaint(textSize), textPaddingLeft, textPaddingRight,
                    textRowSpaceRatio);
        } else {
            contentValueMeasureList = measure(widthMeasureSpec, heightMeasureSpec,
                    contentValue, initTextPaint(contentSize), textPaddingLeft, textPaddingRight,
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
        int measuredWidth = (int) convertToPX(96, TypedValue.COMPLEX_UNIT_DIP);
        int measuredHeight = (int) convertToPX(48, TypedValue.COMPLEX_UNIT_DIP);

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
            for (int measureStart = 0, measure = 1; measure <= charatcerLength; measure++) {
                String measureString = textValue.substring(measureStart, measure);
                float measureLength = paint.measureText(measureString);
                if (measureLength > availableWidth) {
                    measureStart = measure - 1;
                    measureList.add(measureStart);
                } else if (measure == charatcerLength) {
                    measureStart = measure;
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

        if (!origin) {
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

    @Override
    protected void onDraw(Canvas canvas) {

        initCanvas(canvas);

        //
        if (rotate != 0) {
            canvas.rotate(rotate, width >> 1, height >> 1);
        }
        if (!origin) {
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

                canvas.drawCircle(touchDownEventX, touchDownEventY, materialRadius, initPaint(backgroundPress));
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

                canvas.drawCircle(touchDownEventX, touchDownEventY, materialRadius, initPaint(backgroundPress));
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
            paint.setXfermode(TPorterDuffXfermode);

            // If they are offset backgroundShadow, mobile, is to draw on the background shadow,
            // without moving the bigger picture and the need to set the width and height

            canvas.translate(select ? backgroundSelectShadowDx * 2f + srcSelectShadowRadius - srcSelectShadowDx : press ? backgroundPressShadowDx * 2f
                            + srcPressShadowRadius - srcPressShadowDx : backgroundNormalShadowDx * 2f + srcNormalShadowRadius - srcNormalShadowDx,
                    select ? backgroundSelectShadowDy * 2f + srcSelectShadowRadius - srcSelectShadowDy : press ? backgroundPressShadowDy * 2f
                            + srcPressShadowRadius - srcPressShadowDy : backgroundNormalShadowDy * 2f + srcNormalShadowRadius - srcNormalShadowDy);
            canvas.drawBitmap(
                    select ? srcSelect : press ? srcPress : srcNormal,
                    matrix,
                    initPaint(paint, select ? srcSelectShadowRadius : press ? srcPressShadowRadius : srcNormalShadowRadius,
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
            drawTextMark(width, height,
                    canvas, textMarkRadius,
                    initPaint(textMarkColor),
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
            drawTextMark(width, height,
                    canvas, contentMarkRadius,
                    initPaint(contentMarkColor),
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