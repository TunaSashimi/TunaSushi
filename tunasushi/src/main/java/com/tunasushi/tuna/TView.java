package com.tunasushi.tuna;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.ContextWrapper;
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
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Property;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

import com.tunasushi.tool.BitmapTool;
import com.tunasushi.tool.DeviceTool;
import com.tuna.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.InverseBindingListener;

import static com.tunasushi.tool.BitmapTool.decodeBitmapResource;
import static com.tunasushi.tool.ConvertTool.convertToPX;
import static com.tunasushi.tool.ConvertTool.dpToPx;
import static com.tunasushi.tool.DeviceTool.showProperties;
import static com.tunasushi.tool.DrawTool.drawAnchor;
import static com.tunasushi.tool.ViewTool.getLinearGradient;

/**
 * @author TunaSashimi
 * @date 2015-11-04 17:25
 * @Copyright 2015 Tunasashimi. All rights reserved.
 * @Description
 */
public class TView extends View {
    private InverseBindingListener inverseBindingListener;

    public InverseBindingListener getInverseBindingListener() {
        return inverseBindingListener;
    }

    public void setInverseBindingListener(InverseBindingListener inverseBindingListener) {
        this.inverseBindingListener = inverseBindingListener;
    }

    /**
     * The following fields and methods of the parent class and subclass can always use!
     */
    protected String tag;

    // the width and height of the TView(put together to save the number of rows)
    public int width, height;

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

    //
    protected float textSizeDefault = dpToPx(16);
    protected int textColorDefault = 0xff999999;

    //paint cannot be used as a static method! Multithreading can go wrong!
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
    protected Paint initPaint(int color) {
        return initPaint(Paint.Style.FILL, 0, color, null, 0, Color.TRANSPARENT, 0, 0, -1);
    }

    // 2
    protected Paint initPaint(Paint.Style style, int color) {
        return initPaint(style, 0, color, null, 0, Color.TRANSPARENT, 0, 0, -1);
    }

    // 2
    protected Paint initPaint(Paint.Style style, Shader shader) {
        return initPaint(style, 0, Color.TRANSPARENT, shader, 0, Color.TRANSPARENT, 0, 0, -1);
    }

    // 3
    protected Paint initPaint(Paint.Style style, int color, float strokeWidth) {
        return initPaint(style, strokeWidth, color, null, 0, Color.TRANSPARENT, 0, 0, -1);
    }

    // 3
    protected Paint initPaint(Paint.Style style, int color, Shader shader) {
        return initPaint(style, 0, color, shader, 0, Color.TRANSPARENT, 0, 0, -1);
    }

    // 3
    protected Paint initPaint(Paint.Style style, Shader shader, int alpha) {
        return initPaint(style, 0, Color.TRANSPARENT, shader, 0, Color.TRANSPARENT, 0, 0, alpha);
    }

    // 4
    protected Paint initPaint(Paint.Style style, int color, Shader shader, int alpha) {
        return initPaint(style, 0, color, shader, 0, Color.TRANSPARENT, 0, 0, alpha);
    }

    // 4
    protected Paint initPaint(Paint.Style style, int color, float strokeWidth, int alpha) {
        return initPaint(style, strokeWidth, color, null, 0, Color.TRANSPARENT, 0, 0, alpha);
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
    protected Paint initPaint(Paint.Style style, int color, float shadowRadius, int shadowColor, float shadowDx, float shadowDy) {
        return initPaint(style, 0, color, null, shadowRadius, shadowColor, shadowDx, shadowDy, -1);
    }

    // 9
    protected Paint initPaint(Paint.Style style, float strokeWidth, int color, Shader shader, float shadowRadius, int shadowColor, float shadowDx, float shadowDy, int alpha) {
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
            paint.setColor(color);
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
    protected Paint initTextPaint(Paint.Style style, int color, float textSize, float shadowRadius, int shadowColor, float shadowDx, float shadowDy, Typeface typeFace, Paint.Align align) {
        //
        initPaint();
        //
        if (style != null) {
            paint.setStyle(style);
        }
        paint.setColor(color);

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

    // 5
    protected void drawRectClassic(Canvas canvas, float width, float height, int fillColor, float radius) {
        drawRectClassic(canvas, 0, 0, width, height, fillColor, null, 0, Color.TRANSPARENT, 0, 0, 0, radius);
    }

    // 7
    protected void drawRectClassic(Canvas canvas, float width, float height, int fillColor, float strokeWidth, float radius) {
        drawRectClassic(canvas, 0, 0, width, height, fillColor, null, 0, Color.TRANSPARENT, 0, 0, strokeWidth, radius);
    }

    // 7
    protected void drawRectClassic(Canvas canvas, float width, float height, Shader shader, float strokeWidth, float radius) {
        drawRectClassic(canvas, 0, 0, width, height, Color.TRANSPARENT, shader, 0, Color.TRANSPARENT, 0, 0, strokeWidth, radius);
    }

    // 9
    protected void drawRectClassic(Canvas canvas, float left, float top, float right, float bottom, int fillColor, float strokeWidth, float radius) {
        drawRectClassic(canvas, left, top, right, bottom, fillColor, null, 0, Color.TRANSPARENT, 0, 0, strokeWidth, radius);
    }

    // 9
    protected void drawRectClassic(Canvas canvas, float left, float top, float right, float bottom, Shader shader, float strokeWidth, float radius) {
        drawRectClassic(canvas, left, top, right, bottom, Color.TRANSPARENT, shader, 0, Color.TRANSPARENT, 0, 0, strokeWidth, radius);
    }

    // 11
    protected void drawRectClassic(Canvas canvas, float width, float height, int fillColor, float shadowRadius, int shadowColor, float shadowDx, float shadowDy, float strokeWidth, float radius) {
        drawRectClassic(canvas, 0, 0, width, height, fillColor, null, shadowRadius, shadowColor, shadowDx, shadowDy, strokeWidth, radius);
    }

    // 11
    protected void drawRectClassic(Canvas canvas, float width, float height, Shader shader, float shadowRadius, int shadowColor, float shadowDx, float shadowDy, float strokeWidth, float radius) {
        drawRectClassic(canvas, 0, 0, width, height, Color.TRANSPARENT, shader, shadowRadius, shadowColor, shadowDx, shadowDy, strokeWidth, radius);
    }

    // 12
    protected void drawRectClassic(Canvas canvas, float left, float top, float right, float bottom, int fillColor, Shader shader, float shadowRadius, int shadowColor,
                                   float shadowDx, float shadowDy, float strokeWidth, float radius) {
        canvas.drawRoundRect(
                initRectF(left + strokeWidth, top + strokeWidth, right - strokeWidth, bottom - strokeWidth),
                radius,
                radius,
                shader == null ? shadowRadius == 0 ? initPaint(fillColor) : initPaint(Paint.Style.FILL, fillColor, shadowRadius, shadowColor, shadowDx,
                        shadowDy) : shadowRadius == 0 ? initPaint(Paint.Style.FILL, shader) : initPaint(Paint.Style.FILL, shader, shadowRadius, shadowColor, shadowDx,
                        shadowDy));
    }

    //
    protected void drawRectClassicStroke(Canvas canvas, float width, float height, float strokeWidth, int strokeColor, float radius) {
        drawRectClassicStroke(canvas, 0, 0, width, height, strokeWidth, strokeColor, radius);
    }

    protected void drawRectClassicStroke(Canvas canvas, float left, float top, float right, float bottom, float strokeWidth, int strokeColor, float radius) {
        canvas.drawRoundRect(initRectF(left + strokeWidth * 0.5f, top + strokeWidth * 0.5f, right - strokeWidth * 0.5f, bottom - strokeWidth * 0.5f), radius, radius,
                initPaint(Paint.Style.STROKE, strokeColor, strokeWidth));
    }

    // 8
    protected void drawRectCustom(Canvas canvas, int width, int height, int fillColor, float radiusTopLeft, float radiusBottomLeft, float radiusTopRight, float radiusBottomRight) {
        drawRectCustom(canvas, 0, 0, width, height, fillColor, null, 0, Color.TRANSPARENT, 0, 0, 0, radiusTopLeft, radiusBottomLeft, radiusTopRight, radiusBottomRight);
    }

    // 10
    protected void drawRectCustom(Canvas canvas, int left, int top, int right, int bottom, int fillColor, float radiusTopLeft, float radiusBottomLeft, float radiusTopRight, float radiusBottomRight) {
        drawRectCustom(canvas, left, top, right, bottom, fillColor, null, 0, Color.TRANSPARENT, 0, 0, 0, radiusTopLeft, radiusBottomLeft, radiusTopRight, radiusBottomRight);
    }

    // 10
    protected void drawRectCustom(Canvas canvas, int width, int height, int fillColor, float strokeWidth, float radiusTopLeft, float radiusBottomLeft, float radiusTopRight, float radiusBottomRight) {
        drawRectCustom(canvas, 0, 0, width, height, fillColor, null, 0, Color.TRANSPARENT, 0, 0, strokeWidth, radiusTopLeft, radiusBottomLeft, radiusTopRight, radiusBottomRight);
    }

    // 10
    protected void drawRectCustom(Canvas canvas, int width, int height, Shader shader, float strokeWidth, float radiusTopLeft, float radiusBottomLeft, float radiusTopRight, float radiusBottomRight) {
        drawRectCustom(canvas, 0, 0, width, height, Color.TRANSPARENT, shader, 0, Color.TRANSPARENT, 0, 0, strokeWidth, radiusTopLeft, radiusBottomLeft, radiusTopRight, radiusBottomRight);
    }

    // 14
    protected void drawRectCustom(Canvas canvas, int width, int height, int fillColor, float shadowRadius, int shadowColor, float shadowDx, float shadowDy, float strokeWidth,
                                  float radiusTopLeft, float radiusBottomLeft, float radiusTopRight, float radiusBottomRight) {
        drawRectCustom(canvas, 0, 0, width, height, fillColor, null, shadowRadius, shadowColor, shadowDx, shadowDy, strokeWidth, radiusTopLeft, radiusBottomLeft,
                radiusTopRight, radiusBottomRight);
    }

    // 14
    protected void drawRectCustom(Canvas canvas, int width, int height, Shader shader, float shadowRadius, int shadowColor, float shadowDx, float shadowDy, float strokeWidth,
                                  float radiusTopLeft, float radiusBottomLeft, float radiusTopRight, float radiusBottomRight) {
        drawRectCustom(canvas, 0, 0, width, height, Color.TRANSPARENT, shader, shadowRadius, shadowColor, shadowDx, shadowDy, strokeWidth, radiusTopLeft,
                radiusBottomLeft, radiusTopRight, radiusBottomRight);
    }

    // 15
    protected void drawRectCustom(Canvas canvas, float left, float top, float right, float bottom, int fillColor, Shader shader, float shadowRadius, int shadowColor,
                                  float shadowDx, float shadowDy, float strokeWidth, float radiusTopLeft, float radiusBottomLeft, float radiusTopRight, float radiusBottomRight) {
        float[] radii = {radiusTopLeft, radiusTopLeft, radiusTopRight, radiusTopRight, radiusBottomRight, radiusBottomRight, radiusBottomLeft, radiusBottomLeft};
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

    protected void drawRectCustomStroke(Canvas canvas, float width, float height, float strokeWidth, int strokeColor, float radiusTopLeft, float radiusBottomLeft, float radiusTopRight, float radiusBottomRight) {
        drawRectCustomStroke(canvas, 0, 0, width, height, strokeWidth, strokeColor, radiusTopLeft, radiusBottomLeft, radiusTopRight, radiusBottomRight);
    }

    protected void drawRectCustomStroke(Canvas canvas, float left, float top, float right, float bottom,
                                        float strokeWidth, int strokeColor, float radiusTopLeft, float radiusBottomLeft, float radiusTopRight, float radiusBottomRight) {
        float[] radii = {radiusTopLeft, radiusTopLeft, radiusTopRight, radiusTopRight, radiusBottomRight, radiusBottomRight, radiusBottomLeft, radiusBottomLeft};
        if (strokeWidth > 0) {
            canvas.drawPath(
                    initPathRoundRect(initRectF(left + strokeWidth * 0.5f, top + strokeWidth * 0.5f, right - strokeWidth * 0.5f, bottom - strokeWidth * 0.5f), radii,
                            Path.Direction.CW), initPaint(Paint.Style.STROKE, strokeColor, strokeWidth));
        }
    }

    // 6
    protected float[] drawText(Canvas canvas, String string, float width, float centerX, float centerY, Paint paint) {
        return drawText(canvas, string, width, centerX, centerY, 0, 0, paint, TEXT_GRAVITY_CENTER, 1.0f, null);
    }

    // 8
    protected float[] drawText(Canvas canvas, String string, float width, float centerX, float centerY, float paddingLeft, float paddingRight, Paint paint) {
        return drawText(canvas, string, width, centerX, centerY, paddingLeft, paddingRight, paint, TEXT_GRAVITY_CENTER, 1.0f, null);
    }

    // 9
    protected float[] drawText(Canvas canvas, String string, float width, float centerX, float centerY, float paddingLeft, float paddingRight, Paint paint,
                               float textRowSpaceRatio, List<Integer> measureList) {
        return drawText(canvas, string, width, centerX, centerY, paddingLeft, paddingRight, paint, TEXT_GRAVITY_CENTER, textRowSpaceRatio, measureList);
    }

    // 10
    protected float[] drawText(Canvas canvas, String string, float width, float centerX, float centerY, float paddingLeft, float paddingRight, Paint paint,
                               int textGravityMode, float textRowSpaceRatio, List<Integer> measureList) {
        if (measureList == null) {
            measureList = createMeasureList(string, paint, width, paddingLeft, paddingRight);
        }

        float textMiddleRow = (measureList.size() + 1) * 0.5f;
        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        // float baseline = (targetRectBottom + targetRectTop - fontMetrics.bottom - fontMetrics.top) * 0.5f;
        float baseline = centerY - fontMetrics.bottom * 0.5f - fontMetrics.top * 0.5f;
        int halfWordHeight = (fontMetrics.descent - fontMetrics.ascent) >> 1;
        //
        String drawString;
        float drawLineY;
        int measureListSize = measureList.size();
        for (int i = 0; i < measureListSize; i++) {
            drawLineY = baseline + (i + 1 - textMiddleRow) * paint.getTextSize() * textRowSpaceRatio;
            if (i == 0) {
                drawString = string.substring(0, measureList.get(i));
            } else {
                drawString = string.substring(measureList.get(i - 1), measureList.get(i));
            }
            float measureLength = paint.measureText(drawString);
            if (i != measureListSize - 1) {
                switch (textGravityMode) {
                    case TEXT_GRAVITY_CENTER:
                    case TEXT_GRAVITY_CENTER_LEFT:
                        canvas.drawText(drawString, centerX + (paddingLeft - paddingRight) * 0.5f, drawLineY, paint);
                        break;
                    case TEXT_GRAVITY_LEFT:
                    case TEXT_GRAVITY_LEFT_CENTER:
                        canvas.drawText(drawString, paddingLeft + measureLength * 0.5f, drawLineY, paint);
                        break;
                    case TEXT_GRAVITY_RIGHT:
                        canvas.drawText(drawString, width - paddingRight - measureLength * 0.5f, drawLineY, paint);
                        break;
                }
            } else {
                float availableWidth = width - paddingLeft - paddingRight;
                switch (textGravityMode) {
                    case TEXT_GRAVITY_CENTER:
                    case TEXT_GRAVITY_LEFT_CENTER:
                        canvas.drawText(drawString, centerX, drawLineY, paint);
                        return new float[]{measureLength, measureLength * 0.5f, drawLineY - centerY - halfWordHeight};
                    case TEXT_GRAVITY_LEFT:
                    case TEXT_GRAVITY_CENTER_LEFT:
                        canvas.drawText(drawString, paddingLeft + measureLength * 0.5f, drawLineY, paint);
                        return new float[]{availableWidth, measureLength + paddingLeft - width * 0.5f, drawLineY - centerY - halfWordHeight};
                    case TEXT_GRAVITY_RIGHT:
                        canvas.drawText(drawString, width - paddingRight - measureLength * 0.5f, drawLineY, paint);
                        return new float[]{availableWidth, width * 0.5f - paddingRight, drawLineY - centerY - halfWordHeight};
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
            List<Integer> measureList) {

        float cx = (width >> 1) + offsetX + markRadius + markDx;
        float cy = (height >> 1) + offsetY + markDy;

        canvas.drawCircle(cx, cy, radius, paint);
        if (markText != null) {
            // Because, drawText use the same method to clear the cache measureRowList
            drawText(canvas, markText, width, cx, cy, 0, 0,
                    initTextPaint(Paint.Style.FILL, markTextColor, markTextSize, Paint.Align.CENTER)
                    , 1, measureList);
        }
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

    protected Path initPathArc(float left, float top, float right, float bottom, float startAngle, float sweepAngle) {
        if (path == null) {
            path = new Path();
        } else {
            path.reset();
        }
        path.addArc(left, top, right, bottom, startAngle, sweepAngle);
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

    //The parent class has the method getTMatrix !
    //Cannot write a method with the same name or the xml cannot be previewed!
    protected Matrix matrixNormal;
    protected Matrix matrixPress;
    protected Matrix matrixSelect;

    protected Matrix initMatrix(Matrix matrix, float sx, float sy) {
        if (matrix == null) {
            matrix = new Matrix();
        }
        matrix.reset();
        matrix.setScale(sx, sy);
        return matrix;
    }

    protected LayoutParams layoutParams;

    public void setLayout(int width, int height) {
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
    public void setStatus(boolean press, boolean select) {
        setStatus(press, select, false);
    }

    //
    public void setStatus(boolean press, boolean select, boolean textMark) {
        setStatus(press, select, textMark, false);
    }

    //
    public void setStatus(boolean press, boolean select, boolean textMark, boolean material) {
        if (this.press != press || this.select != select || this.textMark != textMark) {
            setPress(press);
            setSelect(select);
            setTextMark(textMark);
            if (!material && materialAnimatorSet != null) {
                this.materialAnimatorSet.cancel();
            }
            invalidate();
        }
    }

    // Hardware accelerated canvas
    protected boolean canvasHardwareAccelerated;

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

    // can modify the properties when adjust is true
    protected boolean adjust;

    public boolean isAdjust() {
        return adjust;
    }

    public void setAdjust(boolean adjust) {
        this.adjust = adjust;
    }

    // Whether to return a touch event
    protected boolean dispatch;

    public boolean isDispatch() {
        return dispatch;
    }

    public void setDispatch(boolean dispatch) {
        this.dispatch = dispatch;
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
        invalidate();
    }

    //
    @IntDef({NEED, WITHOUT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface pressMode {
    }

    public static final int NEED = 0;
    public static final int WITHOUT = 1;
    private static final int[] pressModeArray = {NEED, WITHOUT};
    // default REVERSE
    private int pressMode;

    public int getPressType() {
        return pressMode;
    }

    public void setPressType(int pressMode) {
        this.pressMode = pressMode;
    }


    // select default false
    protected boolean select;

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        if (this.select != select) {
            this.select = select;
            if (inverseBindingListener != null) {
                inverseBindingListener.onChange();
            }
            invalidate();
        }
    }

    //
    @IntDef({REVERSE, ALWAYS, NEVER})
    @Retention(RetentionPolicy.SOURCE)
    public @interface selectMode {
    }

    public static final int REVERSE = 0;
    public static final int ALWAYS = 1;
    public static final int NEVER = 2;
    private static final int[] selectModeArray = {REVERSE, ALWAYS, NEVER};
    // default REVERSE
    private int selectMode;

    public int getSelectType() {
        return selectMode;
    }

    public void setSelectType(int selectMode) {
        this.selectMode = selectMode;
    }

    // default false
    protected boolean animation;

    public boolean isAnimation() {
        return animation;
    }

    public void setAnimation(boolean animation) {
        this.animation = animation;
        if (animation) {
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
     * An implementation of TouchUpListener that attempts to lazily load a
     * named click handling method from a parent or ancestor context.
     */
    private static class DeclaredTouchUpListener implements TouchUpListener {
        private final TView mHostView;
        private final String mMethodName;

        private Method mResolvedMethod;
        private Context mResolvedContext;

        public DeclaredTouchUpListener(@NonNull TView hostView, @NonNull String methodName) {
            mHostView = hostView;
            mMethodName = methodName;
        }

        @Override
        public void touchUp(TView t) {
            if (mResolvedMethod == null) {
                resolveMethod(mHostView.getContext(), mMethodName);
            }
            try {
                mResolvedMethod.invoke(mResolvedContext, t);
            } catch (IllegalAccessException e) {
                throw new IllegalStateException("Could not execute non-public method for app:touchUp", e);
            } catch (InvocationTargetException e) {
                throw new IllegalStateException("Could not execute method for app:touchUp", e);
            }
        }

        @NonNull
        private void resolveMethod(@Nullable Context context, @NonNull String name) {
            while (context != null) {
                try {
                    if (!context.isRestricted()) {
                        final Method method = context.getClass().getMethod(name, TView.class);
                        if (method != null) {
                            mResolvedMethod = method;
                            mResolvedContext = context;
                            return;
                        }
                    }
                } catch (NoSuchMethodException e) {
                    // Failed to find method, keep searching up the hierarchy.
                }
                if (context instanceof ContextWrapper) {
                    context = ((ContextWrapper) context).getBaseContext();
                } else {
                    // Can't search up the hierarchy, null out and fail.
                    context = null;
                }
            }

            final int id = mHostView.getId();
            final String idText = id == NO_ID ? "" : " with id '" +
                    mHostView.getContext().getResources().getResourceEntryName(id) + "'";
            throw new IllegalStateException("Could not find method " + mMethodName
                    + "(TView) in a parent or ancestor Context for app:touchUp "
                    + "attribute defined on view " + mHostView.getClass() + idText);
        }
    }

    /**
     * Add a common OnClickListener to give TouchUpListener the same trigger!
     * <p>
     * Only the onClickListener parameter is View, and the other Touch interface parameters are TView!
     */
    protected OnClickListener onClickListener;

    public interface OnClickListener {
        void onClick(View v);
    }

    public OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    /**
     * An implementation of OnClickListener that attempts to lazily load a
     * named click handling method from a parent or ancestor context.
     */
    private static class DeclaredOnClickListener implements OnClickListener {
        private final View mHostView;
        private final String mMethodName;

        private Method mResolvedMethod;
        private Context mResolvedContext;

        public DeclaredOnClickListener(@NonNull View hostView, @NonNull String methodName) {
            mHostView = hostView;
            mMethodName = methodName;
        }

        @Override
        public void onClick(View v) {
            if (mResolvedMethod == null) {
                resolveMethod(mHostView.getContext(), mMethodName);
            }
            try {
                mResolvedMethod.invoke(mResolvedContext, v);
            } catch (IllegalAccessException e) {
                throw new IllegalStateException("Could not execute non-public method for app:onClick", e);
            } catch (InvocationTargetException e) {
                throw new IllegalStateException("Could not execute method for app:onClick", e);
            }
        }

        @NonNull
        private void resolveMethod(@Nullable Context context, @NonNull String name) {
            while (context != null) {
                try {
                    if (!context.isRestricted()) {
                        final Method method = context.getClass().getMethod(name, View.class);
                        if (method != null) {
                            mResolvedMethod = method;
                            mResolvedContext = context;
                            return;
                        }
                    }
                } catch (NoSuchMethodException e) {
                    // Failed to find method, keep searching up the hierarchy.
                }
                if (context instanceof ContextWrapper) {
                    context = ((ContextWrapper) context).getBaseContext();
                } else {
                    // Can't search up the hierarchy, null out and fail.
                    context = null;
                }
            }

            final int id = mHostView.getId();
            final String idText = id == NO_ID ? "" : " with id '" + mHostView.getContext().getResources().getResourceEntryName(id) + "'";
            throw new IllegalStateException("Could not find method " + mMethodName
                    + "(TView) in a parent or ancestor Context for app:onClick "
                    + "attribute defined on view " + mHostView.getClass() + idText);
        }
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
    // radiusTopLeft,radiusBottomLeft,radiusTopRight,radiusBottomRight default 0
    private float radiusTopLeft;

    public float getRadiusTopLeft() {
        return radiusTopLeft;
    }

    public void setRadiusTopLeft(float radiusTopLeft) {
        setRadiusTopLeftRaw(radiusTopLeft);
    }

    public void setRadiusTopLeft(float radiusTopLeft, int unit) {
        setRadiusTopLeftRaw(convertToPX(radiusTopLeft, unit));
    }

    private void setRadiusTopLeftRaw(float radiusTopLeft) {
        if (this.radiusTopLeft != radiusTopLeft) {
            this.radiusTopLeft = radiusTopLeft;
            invalidate();
        }
    }

    //
    private float radiusTopRight;

    public float getRadiusTopRight() {
        return radiusTopRight;
    }

    public void setRadiusTopRight(float radiusTopRight) {
        setRadiusTopRightRaw(radiusTopRight);
    }

    public void setRadiusTopRight(float radiusTopRight, int unit) {
        setRadiusTopRightRaw(convertToPX(radiusTopRight, unit));
    }

    private void setRadiusTopRightRaw(float radiusTopRight) {
        if (this.radiusTopRight != radiusTopRight) {
            this.radiusTopRight = radiusTopRight;
            invalidate();
        }
    }

    //
    private float radiusBottomLeft;

    public float getRadiusBottomLeft() {
        return radiusBottomLeft;
    }

    public void setRadiusBottomLeft(float radiusBottomLeft) {
        setRadiusBottomLeftRaw(radiusBottomLeft);
    }

    public void setRadiusBottomLeft(float radiusBottomLeft, int unit) {
        setRadiusBottomLeftRaw(convertToPX(radiusBottomLeft, unit));
    }

    private void setRadiusBottomLeftRaw(float radiusBottomLeft) {
        if (this.radiusBottomLeft != radiusBottomLeft) {
            this.radiusBottomLeft = radiusBottomLeft;
            invalidate();
        }
    }

    //
    private float radiusBottomRight;

    public float getRadiusBottomRight() {
        return radiusBottomRight;
    }

    public void setRadiusBottomRight(float radiusBottomRight) {
        setRadiusBottomRightRaw(radiusBottomRight);
    }

    public void setRadiusBottomRight(float radiusBottomRight, int unit) {
        setRadiusBottomRightRaw(convertToPX(radiusBottomRight, unit));
    }

    private void setRadiusBottomRightRaw(float radiusBottomRight) {
        if (this.radiusBottomRight != radiusBottomRight) {
            this.radiusBottomRight = radiusBottomRight;
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
        invalidate();
    }

    public void setBackgroundNormal(String backgroundNormal) {
        setBackgroundNormal(Color.parseColor(backgroundNormal));
    }

    // backgroundPress default backgroundNormal
    private int backgroundPress;

    public int getBackgroundPress() {
        return backgroundPress;
    }

    public void setBackgroundPress(int backgroundPress) {
        this.backgroundPress = backgroundPress;
        invalidate();
    }

    public void setBackgroundPress(String backgroundPress) {
        setBackgroundPress(Color.parseColor(backgroundPress));
    }

    // backgroundSelect default backgroundNormal
    private int backgroundSelect;

    public int getBackgroundSelect() {
        return backgroundSelect;
    }

    public void setBackgroundSelect(int backgroundSelect) {
        this.backgroundSelect = backgroundSelect;
        invalidate();
    }

    public void setBackgroundSelect(String backgroundSelect) {
        setBackgroundSelect(Color.parseColor(backgroundSelect));
    }

    public void setBackground(int background) {
        this.backgroundNormal = background;
        this.backgroundPress = background;
        this.backgroundSelect = background;
        invalidate();
    }

    public void setBackground(String background) {
        setBackground(Color.parseColor(background));
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

    private int backgroundAngleNormal;

    public int getBackgroundAngleNormal() {
        return backgroundAngleNormal;
    }

    public void setBackgroundAngleNormal(int backgroundAngleNormal) {
        this.backgroundAngleNormal = backgroundAngleNormal;
    }

    private int backgroundAnglePress;

    public int getBackgroundAnglePress() {
        return backgroundAnglePress;
    }

    public void setBackgroundAnglePress(int backgroundAnglePress) {
        this.backgroundAnglePress = backgroundAnglePress;
    }

    private int backgroundAngleSelect;

    public int getBackgroundAngleSelect() {
        return backgroundAngleSelect;
    }

    public void setBackgroundAngleSelect(int backgroundAngleSelect) {
        this.backgroundAngleSelect = backgroundAngleSelect;
    }

    // backgroundStartNormal default backgroundNormal
    private int backgroundStartNormal;

    public int getBackgroundStartNormal() {
        return backgroundStartNormal;
    }

    public void setBackgroundStartNormal(int backgroundStartNormal) {
        if (this.backgroundStartNormal != backgroundStartNormal) {
            this.backgroundStartNormal = backgroundStartNormal;
            invalidate();
        }
    }

    public void setBackgroundStartNormal(String backgroundStartNormal) {
        setBackgroundStartNormal(Color.parseColor(backgroundStartNormal));
    }

    // backgroundStartPress default backgroundPress
    private int backgroundStartPress;

    public int getBackgroundStartPress() {
        return backgroundStartPress;
    }

    public void setBackgroundStartPress(int backgroundStartPress) {
        if (this.backgroundStartPress != backgroundStartPress) {
            this.backgroundStartPress = backgroundStartPress;
            invalidate();
        }
    }

    public void setBackgroundStartPress(String backgroundStartPress) {
        setBackgroundStartPress(Color.parseColor(backgroundStartPress));
    }

    // backgroundStartSelect default backgroundSelect
    private int backgroundStartSelect;

    public int getBackgroundStartSelect() {
        return backgroundStartSelect;
    }

    public void setBackgroundStartSelect(int backgroundStartSelect) {
        if (this.backgroundStartSelect != backgroundStartSelect) {
            this.backgroundStartSelect = backgroundStartSelect;
            invalidate();
        }
    }

    public void setBackgroundStart(int backgroundStart) {
        this.backgroundStartNormal = backgroundStart;
        this.backgroundStartPress = backgroundStart;
        this.backgroundStartSelect = backgroundStart;
        invalidate();
    }

    public void setBackgroundStart(String backgroundStart) {
        setBackgroundStart(Color.parseColor(backgroundStart));
    }

    public void setBackgroundStartSelect(String backgroundStartSelect) {
        setBackgroundStartSelect(Color.parseColor(backgroundStartSelect));
    }

    // backgroundEndNormal default backgroundNormal
    private int backgroundEndNormal;

    public int getBackgroundEndNormal() {
        return backgroundEndNormal;
    }

    public void setBackgroundEndNormal(int backgroundEndNormal) {
        if (this.backgroundEndNormal != backgroundEndNormal) {
            this.backgroundEndNormal = backgroundEndNormal;
            invalidate();
        }
    }

    public void setBackgroundEndNormal(String backgroundEndNormal) {
        setBackgroundEndNormal(Color.parseColor(backgroundEndNormal));
    }

    // backgroundEndPress default backgroundPress
    private int backgroundEndPress;

    public int getBackgroundEndPress() {
        return backgroundEndPress;
    }

    public void setBackgroundEndPress(int backgroundEndPress) {
        if (this.backgroundEndPress != backgroundEndPress) {
            this.backgroundEndPress = backgroundEndPress;
            invalidate();
        }
    }

    public void setBackgroundEndPress(String backgroundEndPress) {
        setBackgroundEndPress(Color.parseColor(backgroundEndPress));
    }

    // backgroundEndSelect default backgroundSelect
    private int backgroundEndSelect;

    public int getBackgroundEndSelect() {
        return backgroundEndSelect;
    }

    public void setBackgroundEndSelect(int backgroundEndSelect) {
        if (this.backgroundEndSelect != backgroundEndSelect) {
            this.backgroundEndSelect = backgroundEndSelect;
            invalidate();
        }
    }

    public void setBackgroundEndSelect(String backgroundEndSelect) {
        setBackgroundEndSelect(Color.parseColor(backgroundEndSelect));
    }

    public void setBackgroundEnd(int backgroundEnd) {
        this.backgroundEndNormal = backgroundEnd;
        this.backgroundEndPress = backgroundEnd;
        this.backgroundEndSelect = backgroundEnd;
        invalidate();
    }

    public void setBackgroundEnd(String backgroundEnd) {
        setBackgroundEnd(Color.parseColor(backgroundEnd));
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
    private float backgroundShadowRadiusNormal;

    public float getBackgroundShadowRadiusNormal() {
        return backgroundShadowRadiusNormal;
    }

    public void setBackgroundShadowRadiusNormal(float backgroundShadowRadiusNormal) {
        setBackgroundShadowRadiusRawNormal(backgroundShadowRadiusNormal);
    }

    public void setBackgroundShadowRadiusNormal(float backgroundShadowRadiusNormal, int unit) {
        setBackgroundShadowRadiusRawNormal(convertToPX(backgroundShadowRadiusNormal, unit));
    }

    private void setBackgroundShadowRadiusRawNormal(float backgroundShadowRadiusNormal) {
        if (this.backgroundShadowRadiusNormal != backgroundShadowRadiusNormal) {
            this.backgroundShadowRadiusNormal = backgroundShadowRadiusNormal;
            invalidate();
        }
    }

    //
    private int backgroundShadowColorNormal;

    public int getBackgroundNormalShadowColor() {
        return backgroundShadowColorNormal;
    }

    public void setBackgroundNormalShadowColor(int backgroundShadowColorNormal) {
        this.backgroundShadowColorNormal = backgroundShadowColorNormal;
    }

    //
    private float backgroundShadowDxNormal;

    public float getBackgroundShadowDxNormal() {
        return backgroundShadowDxNormal;
    }

    public void setBackgroundShadowDxNormal(float backgroundShadowDxNormal) {
        setBackgroundShadowDxRawNormal(backgroundShadowDxNormal);
    }

    public void setBackgroundShadowDxNormal(float backgroundShadowDxNormal, int unit) {
        setBackgroundShadowDxRawNormal(convertToPX(backgroundShadowDxNormal, unit));
    }

    private void setBackgroundShadowDxRawNormal(float backgroundShadowDxNormal) {
        if (this.backgroundShadowDxNormal != backgroundShadowDxNormal) {
            this.backgroundShadowDxNormal = backgroundShadowDxNormal;
            invalidate();
        }
    }

    //
    private float backgroundShadowDyNormal;

    public float getBackgroundShadowDyNormal() {
        return backgroundShadowDyNormal;
    }

    public void setBackgroundShadowDyNormal(float backgroundShadowDyNormal) {
        setBackgroundShadowDyRawNormal(backgroundShadowDyNormal);
    }

    public void setBackgroundShadowDyNormal(float backgroundShadowDyNormal, int unit) {
        setBackgroundShadowDyRawNormal(convertToPX(backgroundShadowDyNormal, unit));
    }

    private void setBackgroundShadowDyRawNormal(float backgroundShadowDyNormal) {
        if (this.backgroundShadowDyNormal != backgroundShadowDyNormal) {
            this.backgroundShadowDyNormal = backgroundShadowDyNormal;
            invalidate();
        }
    }

    // default backgroundShadowRadiusNormal
    private float backgroundShadowRadiusPress;

    public float getBackgroundShadowRadiusPress() {
        return backgroundShadowRadiusPress;
    }

    public void setBackgroundShadowRadiusPress(float backgroundShadowRadiusPress) {
        setBackgroundShadowRadiusRawPress(backgroundShadowRadiusPress);
    }

    public void setBackgroundShadowRadiusPress(float backgroundShadowRadiusPress, int unit) {
        setBackgroundShadowRadiusRawPress(convertToPX(backgroundShadowRadiusPress, unit));
    }

    private void setBackgroundShadowRadiusRawPress(float backgroundShadowRadiusPress) {
        if (this.backgroundShadowRadiusPress != backgroundShadowRadiusPress) {
            this.backgroundShadowRadiusPress = backgroundShadowRadiusPress;
            invalidate();
        }
    }

    // default backgroundShadowColorNormal
    private int backgroundShadowColorPress;

    public int getBackgroundPressShadowColor() {
        return backgroundShadowColorPress;
    }

    public void setBackgroundPressShadowColor(int backgroundShadowColorPress) {
        this.backgroundShadowColorPress = backgroundShadowColorPress;
    }

    // default backgroundShadowDxNormal
    private float backgroundShadowDxPress;

    public float getBackgroundShadowDxPress() {
        return backgroundShadowDxPress;
    }

    public void setBackgroundShadowDxPress(float backgroundShadowDxPress) {
        setBackgroundShadowDxRawPress(backgroundShadowDxPress);
    }

    public void setBackgroundShadowDxPress(float backgroundShadowDxPress, int unit) {
        setBackgroundShadowDxRawPress(convertToPX(backgroundShadowDxPress, unit));
    }

    private void setBackgroundShadowDxRawPress(float backgroundShadowDxPress) {
        if (this.backgroundShadowDxPress != backgroundShadowDxPress) {
            this.backgroundShadowDxPress = backgroundShadowDxPress;
            invalidate();
        }
    }

    // default backgroundShadowDyNormal
    private float backgroundShadowDyPress;

    public float getBackgroundShadowDyPress() {
        return backgroundShadowDyPress;
    }

    public void setBackgroundShadowDyPress(float backgroundShadowDyPress) {
        setBackgroundShadowDyRawPress(backgroundShadowDyPress);
    }

    public void setBackgroundShadowDyPress(float backgroundShadowDyPress, int unit) {
        setBackgroundShadowDyRawPress(convertToPX(backgroundShadowDyPress, unit));
    }

    private void setBackgroundShadowDyRawPress(float backgroundShadowDyPress) {
        if (this.backgroundShadowDyPress != backgroundShadowDyPress) {
            this.backgroundShadowDyPress = backgroundShadowDyPress;
            invalidate();
        }
    }

    // default backgroundShadowRadiusNormal
    private float backgroundShadowRadiusSelect;

    public float getBackgroundShadowRadiusSelect() {
        return backgroundShadowRadiusSelect;
    }

    public void setBackgroundShadowRadiusSelect(float backgroundShadowRadiusSelect) {
        setBackgroundShadowRadiusRawSelect(backgroundShadowRadiusSelect);
    }

    public void setBackgroundShadowRadiusSelect(float backgroundShadowRadiusSelect, int unit) {
        setBackgroundShadowRadiusRawSelect(convertToPX(backgroundShadowRadiusSelect, unit));
    }

    private void setBackgroundShadowRadiusRawSelect(float backgroundShadowRadiusSelect) {
        if (this.backgroundShadowRadiusSelect != backgroundShadowRadiusSelect) {
            this.backgroundShadowRadiusSelect = backgroundShadowRadiusSelect;
            invalidate();
        }
    }

    // default backgroundShadowColorNormal
    private int backgroundShadowColorSelect;

    public int getBackgroundSelectShadowColor() {
        return backgroundShadowColorSelect;
    }

    public void setBackgroundSelectShadowColor(int backgroundShadowColorSelect) {
        this.backgroundShadowColorSelect = backgroundShadowColorSelect;
    }

    // default backgroundShadowDxNormal
    private float backgroundShadowDxSelect;

    public float getBackgroundShadowDxSelect() {
        return backgroundShadowDxSelect;
    }

    public void setBackgroundShadowDxSelect(float backgroundShadowDxSelect) {
        setBackgroundShadowDxRawSelect(backgroundShadowDxSelect);
    }

    public void setBackgroundShadowDxSelect(float backgroundShadowDxSelect, int unit) {
        setBackgroundShadowDxRawSelect(convertToPX(backgroundShadowDxSelect, unit));
    }

    private void setBackgroundShadowDxRawSelect(float backgroundShadowDxSelect) {
        if (this.backgroundShadowDxSelect != backgroundShadowDxSelect) {
            this.backgroundShadowDxSelect = backgroundShadowDxSelect;
            invalidate();
        }
    }

    // default backgroundShadowDyNormal
    private float backgroundShadowDySelect;

    public float getBackgroundShadowDySelect() {
        return backgroundShadowDySelect;
    }

    public void setBackgroundShadowDySelect(float backgroundShadowDySelect) {
        setBackgroundShadowDyRawSelect(backgroundShadowDySelect);
    }

    public void setBackgroundShadowDySelect(float backgroundShadowDySelect, int unit) {
        setBackgroundShadowDyRawSelect(convertToPX(backgroundShadowDySelect, unit));
    }

    private void setBackgroundShadowDyRawSelect(float backgroundShadowDySelect) {
        if (this.backgroundShadowDySelect != backgroundShadowDySelect) {
            this.backgroundShadowDySelect = backgroundShadowDySelect;
            invalidate();
        }
    }

    //
    int srcWidthRawNormal = 0, srcHeightRawNormal = 0;
    int srcWidthRawPress = 0, srcHeightRawPress = 0;
    int srcWidthRawSelect = 0, srcHeightRawSelect = 0;

    //
    private Bitmap srcNormal;

    public Bitmap getSrcNormal() {
        return srcNormal;
    }

    public void setSrcNormal(int id) {
        setSrcNormal(decodeBitmapResource(getResources(), id));
    }

    public void setSrcNormal(Bitmap srcNormal) {
        this.srcNormal = srcNormal;
//        srcWidthRawNormal = srcNormal.getWidth();
//        srcHeightRawNormal = srcNormal.getHeight();
//        matrixNormal = initMatrix(matrixNormal,
//                (width - srcShadowRadiusNormal * 2f - backgroundShadowRadiusNormal * 2f - backgroundShadowDxNormal * 2f - strokeWidthNormal * 2f) / srcWidthRawNormal,
//                (height - srcShadowRadiusNormal * 2f - backgroundShadowRadiusNormal * 2f - backgroundShadowDyNormal * 2f - strokeWidthNormal * 2f) / srcHeightRawNormal)
//        ;
//        //
//        setSrcPress(srcNormal);
//        setSrcSelect(srcNormal);
//        //
        invalidate();
    }

    public void setSrcNormal(Drawable srcNormal) {
        //It contains synchronous setting of press and select status
        setSrcNormal(BitmapTool.drawableToBitmap(srcNormal));
    }

    //
    private Bitmap srcPress;

    public Bitmap getSrcPress() {
        return srcPress;
    }

    public void setSrcPress(int id) {
        setSrcPress(decodeBitmapResource(getResources(), id));
    }

    public void setSrcPress(Bitmap srcPress) {
        this.srcPress = srcPress;
//        srcWidthRawPress = srcPress.getWidth();
//        srcHeightRawPress = srcPress.getHeight();
//        matrixPress = initMatrix(matrixPress,
//                (width - srcShadowRadiusPress * 2f - backgroundShadowRadiusPress * 2f - backgroundShadowDxPress * 2f - strokeWidthPress * 2f) / srcWidthRawPress,
//                (height - srcShadowRadiusPress * 2f - backgroundShadowRadiusPress * 2f - backgroundShadowDyPress * 2f - strokeWidthPress * 2f) / srcHeightRawPress)
//        ;
        invalidate();
    }

    public void setSrcPress(Drawable srcPress) {
        setSrcPress(BitmapTool.drawableToBitmap(srcPress));
    }

    //
    private Bitmap srcSelect;

    public Bitmap getSrcSelect() {
        return srcSelect;
    }

    public void setSrcSelect(int id) {
        setSrcSelect(decodeBitmapResource(getResources(), id));
    }

    public void setSrcSelect(Bitmap srcSelect) {
        this.srcSelect = srcSelect;
//        srcWidthRawSelect = srcSelect.getWidth();
//        srcHeightRawSelect = srcSelect.getHeight();
//        matrixSelect = initMatrix(matrixSelect,
//                (width - srcShadowRadiusSelect * 2f - backgroundShadowRadiusSelect * 2f - backgroundShadowDxSelect * 2f - strokeWidthSelect * 2f) / srcWidthRawSelect,
//                (height - srcShadowRadiusSelect * 2f - backgroundShadowRadiusSelect * 2f - backgroundShadowDySelect * 2f - strokeWidthSelect * 2f) / srcHeightRawSelect)
//        ;
        invalidate();
    }

    public void setSrcSelect(Drawable srcSelect) {
        setSrcSelect(BitmapTool.drawableToBitmap(srcSelect));
    }

    public void setSrc(int id) {
        setSrcNormal(id);
        setSrcPress(id);
        setSrcSelect(id);
    }

    public void setSrc(Bitmap src) {
        setSrcNormal(src);
        setSrcPress(src);
        setSrcSelect(src);
    }

    public void setSrc(Drawable src) {
        setSrcNormal(src);
        setSrcPress(src);
        setSrcSelect(src);
    }

    @IntDef({FIT_XY, FIT_WIDTH, FIT_HEIGHT, FIT_CENTER})
    @Retention(RetentionPolicy.SOURCE)
    public @interface srcMode {
    }

    public static final int FIT_XY = 0;
    public static final int FIT_WIDTH = 1;
    public static final int FIT_HEIGHT = 2;
    public static final int FIT_CENTER = 3;
    private static final int[] srcModeArray = {FIT_XY, FIT_WIDTH, FIT_HEIGHT, FIT_CENTER,
    };
    private @srcMode
    int srcMode;

    //
    private float srcShadowRadiusNormal;

    public float getSrcShadowRadiusNormal() {
        return srcShadowRadiusNormal;
    }

    public void setSrcShadowRadiusNormal(float srcShadowRadiusNormal) {
        setSrcShadowRadiusRawNormal(srcShadowRadiusNormal);
    }

    public void setSrcShadowRadiusNormal(float srcShadowRadiusNormal, int unit) {
        setSrcShadowRadiusRawNormal(convertToPX(srcShadowRadiusNormal, unit));
    }

    private void setSrcShadowRadiusRawNormal(float srcShadowRadiusNormal) {
        if (this.srcShadowRadiusNormal != srcShadowRadiusNormal) {
            this.srcShadowRadiusNormal = srcShadowRadiusNormal;
            invalidate();
        }
    }

    //
    private float srcShadowDxNormal;

    public float getSrcShadowDxNormal() {
        return srcShadowDxNormal;
    }

    public void setSrcShadowDxNormal(float srcShadowDxNormal) {
        setSrcShadowDxRawNormal(srcShadowDxNormal);
    }

    public void setSrcShadowDxNormal(float srcShadowDxNormal, int unit) {
        setSrcShadowDxRawNormal(convertToPX(srcShadowDxNormal, unit));
    }

    private void setSrcShadowDxRawNormal(float srcShadowDxNormal) {
        if (this.srcShadowDxNormal != srcShadowDxNormal) {
            this.srcShadowDxNormal = srcShadowDxNormal;
            invalidate();
        }
    }

    //
    private float srcShadowDyNormal;

    public float getSrcShadowDyNormal() {
        return srcShadowDyNormal;
    }

    public void setSrcShadowDyNormal(float srcShadowDyNormal) {
        setSrcShadowDyRawNormal(srcShadowDyNormal);
    }

    public void setSrcShadowDyNormal(float srcShadowDyNormal, int unit) {
        setSrcShadowDyRawNormal(convertToPX(srcShadowDyNormal, unit));
    }

    private void setSrcShadowDyRawNormal(float srcShadowDyNormal) {
        if (this.srcShadowDyNormal != srcShadowDyNormal) {
            this.srcShadowDyNormal = srcShadowDyNormal;
            invalidate();
        }
    }

    // default srcShadowRadiusNormal
    private float srcShadowRadiusPress;

    public float getSrcShadowRadiusPress() {
        return srcShadowRadiusPress;
    }

    public void setSrcShadowRadiusPress(float srcShadowRadiusPress) {
        setSrcShadowRadiusRawPress(srcShadowRadiusPress);
    }

    public void setSrcShadowRadiusPress(float srcShadowRadiusPress, int unit) {
        setSrcShadowRadiusRawPress(convertToPX(srcShadowRadiusPress, unit));
    }

    private void setSrcShadowRadiusRawPress(float srcShadowRadiusPress) {
        if (this.srcShadowRadiusPress != srcShadowRadiusPress) {
            this.srcShadowRadiusPress = srcShadowRadiusPress;
            invalidate();
        }
    }

    // default srcShadowDxNormal
    private float srcShadowDxPress;

    public float getSrcShadowDxPress() {
        return srcShadowDxPress;
    }

    public void setSrcShadowDxPress(float srcShadowDxPress) {
        setSrcShadowDxRawPress(srcShadowDxPress);
    }

    public void setSrcShadowDxPress(float srcShadowDxPress, int unit) {
        setSrcShadowDxRawPress(convertToPX(srcShadowDxPress, unit));
    }

    private void setSrcShadowDxRawPress(float srcShadowDxPress) {
        if (this.srcShadowDxPress != srcShadowDxPress) {
            this.srcShadowDxPress = srcShadowDxPress;
            invalidate();
        }
    }

    // default srcShadowDyNormal
    private float srcShadowDyPress;

    public float getSrcShadowDyPress() {
        return srcShadowDyPress;
    }

    public void setSrcShadowDyPress(float srcShadowDyPress) {
        setSrcShadowDyRawPress(srcShadowDyPress);
    }

    public void setSrcShadowDyPress(float srcShadowDyPress, int unit) {
        setSrcShadowDyRawPress(convertToPX(srcShadowDyPress, unit));
    }

    private void setSrcShadowDyRawPress(float srcShadowDyPress) {
        if (this.srcShadowDyPress != srcShadowDyPress) {
            this.srcShadowDyPress = srcShadowDyPress;
            invalidate();
        }
    }

    // default srcShadowRadiusNormal
    private float srcShadowRadiusSelect;

    public float getSrcShadowRadiusSelect() {
        return srcShadowRadiusSelect;
    }

    public void setSrcShadowRadiusSelect(float srcShadowRadiusSelect) {
        setSrcShadowRadiusRawSelect(srcShadowRadiusSelect);
    }

    public void setSrcShadowRadiusSelect(float srcShadowRadiusSelect, int unit) {
        setSrcShadowRadiusRawSelect(convertToPX(srcShadowRadiusSelect, unit));
    }

    private void setSrcShadowRadiusRawSelect(float srcShadowRadiusSelect) {
        if (this.srcShadowRadiusSelect != srcShadowRadiusSelect) {
            this.srcShadowRadiusSelect = srcShadowRadiusSelect;
            invalidate();
        }
    }

    // default srcShadowDxNormal
    private float srcShadowDxSelect;

    public float getSrcShadowDxSelect() {
        return srcShadowDxSelect;
    }

    public void setSrcShadowDxSelect(float srcShadowDxSelect) {
        setSrcShadowDxRawSelect(srcShadowDxSelect);
    }

    public void setSrcShadowDxSelect(float srcShadowDxSelect, int unit) {
        setSrcShadowDxRawSelect(convertToPX(srcShadowDxSelect, unit));
    }

    private void setSrcShadowDxRawSelect(float srcShadowDxSelect) {
        if (this.srcShadowDxSelect != srcShadowDxSelect) {
            this.srcShadowDxSelect = srcShadowDxSelect;
            invalidate();
        }
    }

    // default srcShadowDyNormal
    private float srcShadowDySelect;

    public float getSrcShadowDySelect() {
        return srcShadowDySelect;
    }

    public void setSrcShadowDySelect(float srcShadowDySelect) {
        setSrcShadowDyRawSelect(srcShadowDySelect);
    }

    public void setSrcShadowDySelect(float srcShadowDySelect, int unit) {
        setSrcShadowDyRawSelect(convertToPX(srcShadowDySelect, unit));
    }

    private void setSrcShadowDyRawSelect(float srcShadowDySelect) {
        if (this.srcShadowDySelect != srcShadowDySelect) {
            this.srcShadowDySelect = srcShadowDySelect;
            invalidate();
        }
    }

    public static final int TOP = 0x00000000;
    public static final int LEFT = TOP;
    public static final int CENTER_HORIZONTAL = 0x00000001;
    public static final int RIGHT = CENTER_HORIZONTAL << 1;
    public static final int CENTER_VERTICAL = RIGHT << 1;
    public static final int BOTTOM = CENTER_VERTICAL << 1;
    public static final int CENTER = CENTER_HORIZONTAL | CENTER_VERTICAL;
    public static final int GRAVITY_MASK = CENTER_HORIZONTAL | RIGHT | CENTER_VERTICAL | BOTTOM;

    //
    private int srcAnchorGravityMode;

    public int getDownloadMarkGravity() {
        return srcAnchorGravityMode;
    }

    public void setSrcAnchorGravity(int srcAnchorGravityMode) {
        this.srcAnchorGravityMode = srcAnchorGravityMode;
    }

    // anchor Normal,Press,Select use one Matrix
    protected Matrix matrixAnchorNormal;
    protected Matrix matrixAnchorPress;
    protected Matrix matrixAnchorSelect;

    //
    private Bitmap srcAnchorNormal;

    public Bitmap getSrcAnchorNormal() {
        return srcAnchorNormal;
    }

    public void setSrcAnchorNormal(Bitmap srcAnchorNormal) {
        this.srcAnchorNormal = srcAnchorNormal;
    }

    //
    private float srcAnchorWidthNormal;

    public float getSrcAnchorWidthNormal() {
        return srcAnchorWidthNormal;
    }

    public void setSrcAnchorWidthNormal(float srcAnchorWidthNormal) {
        setSrcAnchorWidthRawNormal(srcAnchorWidthNormal);
    }

    public void setSrcAnchorWidthNormal(float srcAnchorWidthNormal, int unit) {
        setSrcAnchorWidthRawNormal(convertToPX(srcAnchorWidthNormal, unit));
    }

    private void setSrcAnchorWidthRawNormal(float srcAnchorWidthNormal) {
        if (this.srcAnchorWidthNormal != srcAnchorWidthNormal) {
            this.srcAnchorWidthNormal = srcAnchorWidthNormal;
            invalidate();
        }
    }

    //
    private float srcAnchorHeightNormal;

    public float getSrcAnchorHeightNormal() {
        return srcAnchorHeightNormal;
    }

    public void setSrcAnchorHeightNormal(float srcAnchorHeightNormal) {
        setSrcAnchorHeightRawNormal(srcAnchorHeightNormal);
    }

    public void setSrcAnchorNormalHeight(float srcAnchorNormalHeight, int unit) {
        setSrcAnchorHeightRawNormal(convertToPX(srcAnchorNormalHeight, unit));
    }

    private void setSrcAnchorHeightRawNormal(float srcAnchorNormalHeight) {
        if (this.srcAnchorHeightNormal != srcAnchorNormalHeight) {
            this.srcAnchorHeightNormal = srcAnchorNormalHeight;
            invalidate();
        }
    }

    //
    private float srcAnchorDxNormal;

    public float getSrcAnchorDxNormal() {
        return srcAnchorDxNormal;
    }

    public void setSrcAnchorDxNormal(float srcAnchorDxNormal) {
        setSrcAnchorDxRawNormal(srcAnchorDxNormal);
    }

    public void setSrcAnchorDxNormal(float srcAnchorDxNormal, int unit) {
        setSrcAnchorDxRawNormal(convertToPX(srcAnchorDxNormal, unit));
    }

    private void setSrcAnchorDxRawNormal(float srcAnchorDxNormal) {
        if (this.srcAnchorDxNormal != srcAnchorDxNormal) {
            this.srcAnchorDxNormal = srcAnchorDxNormal;
            invalidate();
        }
    }

    //
    private float srcAnchorDyNormal;

    public float getSrcAnchorNormalDy() {
        return srcAnchorDyNormal;
    }

    public void setSrcAnchorNormalDy(float srcAnchorDyNormal) {
        setSrcAnchorNormalDyRaw(srcAnchorDyNormal);
    }

    public void setSrcAnchorNormalDy(float srcAnchorDyNormal, int unit) {
        setSrcAnchorNormalDyRaw(convertToPX(srcAnchorDyNormal, unit));
    }

    private void setSrcAnchorNormalDyRaw(float srcAnchorDyNormal) {
        if (this.srcAnchorDyNormal != srcAnchorDyNormal) {
            this.srcAnchorDyNormal = srcAnchorDyNormal;
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
    private float srcAnchorWidthPress;

    public float getSrcAnchorWidthPress() {
        return srcAnchorWidthPress;
    }

    public void setSrcAnchorWidthPress(float srcAnchorWidthPress) {
        setSrcAnchorWidthRawPress(srcAnchorWidthPress);
    }

    public void setSrcAnchorPressWidth(float srcAnchorPressWidth, int unit) {
        setSrcAnchorWidthRawPress(convertToPX(srcAnchorPressWidth, unit));
    }

    private void setSrcAnchorWidthRawPress(float srcAnchorPressWidth) {
        if (this.srcAnchorWidthPress != srcAnchorPressWidth) {
            this.srcAnchorWidthPress = srcAnchorPressWidth;
            invalidate();
        }
    }

    //
    private float srcAnchorHeightPress;

    public float getSrcAnchorHeightPress() {
        return srcAnchorHeightPress;
    }

    public void setSrcAnchorHeightPress(float srcAnchorHeightPress) {
        setSrcAnchorHeightRawPress(srcAnchorHeightPress);
    }

    public void setSrcAnchorPressHeight(float srcAnchorPressHeight, int unit) {
        setSrcAnchorHeightRawPress(convertToPX(srcAnchorPressHeight, unit));
    }

    private void setSrcAnchorHeightRawPress(float srcAnchorPressHeight) {
        if (this.srcAnchorHeightPress != srcAnchorPressHeight) {
            this.srcAnchorHeightPress = srcAnchorPressHeight;
            invalidate();
        }
    }

    //
    private float srcAnchorDxPress;

    public float getSrcAnchorDxPress() {
        return srcAnchorDxPress;
    }

    public void setSrcAnchorDxPress(float srcAnchorDxPress) {
        setSrcAnchorPressDxRaw(srcAnchorDxPress);
    }

    public void setSrcAnchorPressDx(float srcAnchorPressDx, int unit) {
        setSrcAnchorPressDxRaw(convertToPX(srcAnchorPressDx, unit));
    }

    private void setSrcAnchorPressDxRaw(float srcAnchorPressDx) {
        if (this.srcAnchorDxPress != srcAnchorPressDx) {
            this.srcAnchorDxPress = srcAnchorPressDx;
            invalidate();
        }
    }

    //
    private float srcAnchorDyPress;

    public float getSrcAnchorDyPress() {
        return srcAnchorDyPress;
    }

    public void setSrcAnchorDyPress(float srcAnchorDyPress) {
        setSrcAnchorPressDyRaw(srcAnchorDyPress);
    }

    public void setSrcAnchorPressDy(float srcAnchorPressDy, int unit) {
        setSrcAnchorPressDyRaw(convertToPX(srcAnchorPressDy, unit));
    }

    private void setSrcAnchorPressDyRaw(float srcAnchorPressDy) {
        if (this.srcAnchorDyPress != srcAnchorPressDy) {
            this.srcAnchorDyPress = srcAnchorPressDy;
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
    private float srcAnchorWidthSelect;

    public float getSrcAnchorWidthSelect() {
        return srcAnchorWidthSelect;
    }

    public void setSrcAnchorWidthSelect(float srcAnchorWidthSelect) {
        setSrcAnchorWidthRawSelect(srcAnchorWidthSelect);
    }

    public void setSrcAnchorWidthSelect(float srcAnchorWidthSelect, int unit) {
        setSrcAnchorWidthRawSelect(convertToPX(srcAnchorWidthSelect, unit));
    }

    private void setSrcAnchorWidthRawSelect(float srcAnchorWidthSelect) {
        if (this.srcAnchorWidthSelect != srcAnchorWidthSelect) {
            this.srcAnchorWidthSelect = srcAnchorWidthSelect;
            invalidate();
        }
    }

    //
    private float srcAnchorHeightSelect;

    public float getSrcAnchorHeightSelect() {
        return srcAnchorHeightSelect;
    }

    public void setSrcAnchorHeightSelect(float srcAnchorHeightSelect) {
        setSrcAnchorHeightRawSelect(srcAnchorHeightSelect);
    }

    public void setSrcAnchorHeightSelect(float srcAnchorHeightSelect, int unit) {
        setSrcAnchorHeightRawSelect(convertToPX(srcAnchorHeightSelect, unit));
    }

    private void setSrcAnchorHeightRawSelect(float srcAnchorHeightSelect) {
        if (this.srcAnchorHeightSelect != srcAnchorHeightSelect) {
            this.srcAnchorHeightSelect = srcAnchorHeightSelect;
            invalidate();
        }
    }

    //
    private float srcAnchorDxSelect;

    public float getSrcAnchorDxSelect() {
        return srcAnchorDxSelect;
    }

    public void setSrcAnchorDxSelect(float srcAnchorDxSelect) {
        setSrcAnchorSelectDxRaw(srcAnchorDxSelect);
    }

    public void setSrcAnchorSelectDx(float srcAnchorSelectDx, int unit) {
        setSrcAnchorSelectDxRaw(convertToPX(srcAnchorSelectDx, unit));
    }

    private void setSrcAnchorSelectDxRaw(float srcAnchorSelectDx) {
        if (this.srcAnchorDxSelect != srcAnchorSelectDx) {
            this.srcAnchorDxSelect = srcAnchorSelectDx;
            invalidate();
        }
    }

    //
    private float srcAnchorDySelect;

    public float getSrcAnchorDySelect() {
        return srcAnchorDySelect;
    }

    public void setSrcAnchorDySelect(float srcAnchorDySelect) {
        setSrcAnchorSelectDyRaw(srcAnchorDySelect);
    }

    public void setSrcAnchorSelectDy(float srcAnchorSelectDy, int unit) {
        setSrcAnchorSelectDyRaw(convertToPX(srcAnchorSelectDy, unit));
    }

    private void setSrcAnchorSelectDyRaw(float srcAnchorSelectDy) {
        if (this.srcAnchorDySelect != srcAnchorSelectDy) {
            this.srcAnchorDySelect = srcAnchorSelectDy;
            invalidate();
        }
    }

    // strokeWidthNormal default 0
    private float strokeWidthNormal;

    public float getStrokeWidthNormal() {
        return strokeWidthNormal;
    }

    public void setStrokeWidthNormal(float strokeWidthNormal) {
        setStrokeWidthRawNormal(strokeWidthNormal);
    }

    public void setStrokeWidthNormal(float strokeWidthNormal, int unit) {
        setStrokeWidthRawNormal(convertToPX(strokeWidthNormal, unit));
    }

    private void setStrokeWidthRawNormal(float strokeWidthNormal) {
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

    public void setStrokeColorNormal(String strokeColorNormal) {
        this.strokeColorNormal = Color.parseColor(strokeColorNormal);
    }

    // strokeWidthPress default strokeWidthNormal
    private float strokeWidthPress;

    public float getStrokeWidthPress() {
        return strokeWidthPress;
    }

    public void setStrokeWidthPress(float strokeWidthPress) {
        setStrokeWidthRawPress(strokeWidthPress);
    }

    public void setStrokeWidthPress(float strokeWidthPress, int unit) {
        setStrokeWidthRawPress(convertToPX(strokeWidthPress, unit));
    }

    private void setStrokeWidthRawPress(float strokeWidthPress) {
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
        if (this.strokeColorPress != strokeColorPress) {
            this.strokeColorPress = strokeColorPress;
            invalidate();
        }
    }

    public void setStrokeColorPress(String strokeColorPress) {
        setStrokeColorPress(Color.parseColor(strokeColorPress));
    }

    // strokeWidthSelect default strokeWidthNormal
    private float strokeWidthSelect;

    public float getStrokeWidthSelect() {
        return strokeWidthSelect;
    }

    public void setStrokeWidthSelect(float strokeWidthSelect) {
        setStrokeWidthRawSelect(strokeWidthSelect);
    }

    public void setStrokeWidthSelect(float strokeWidthSelect, int unit) {
        setStrokeWidthRawSelect(convertToPX(strokeWidthSelect, unit));
    }

    private void setStrokeWidthRawSelect(float strokeWidthSelect) {
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
        if (this.strokeColorSelect != strokeColorSelect) {
            this.strokeColorSelect = strokeColorSelect;
            invalidate();
        }
    }

    public void setStrokeColorSelect(String strokeColorSelect) {
        setStrokeColorSelect(Color.parseColor(strokeColorSelect));
    }

    public void setStrokeColor(int strokeColor) {
        this.strokeColorNormal = strokeColor;
        this.strokeColorPress = strokeColor;
        this.strokeColorSelect = strokeColor;
        invalidate();
    }

    public void setStrokeColor(String strokeColor) {
        setStrokeColor(Color.parseColor(strokeColor));
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

    public void setTextMark(String textMarkText) {
        this.textMarkText = textMarkText;
        requestLayout();
    }

    public void setTextMark(float textMarkRadius, int textMarkColor, String textMarkText, float textMarkTextSize, int textMarkTextColor,
                            float textMarkDx, float textMarkDy) {
        setTextMark(textMarkRadius, textMarkColor, textMarkText, textMarkTextSize,
                textMarkTextColor, textMarkDx, textMarkDy);
    }

    public void setTextMark(float textMarkRadius, int textMarkRadiusUnit,
                            int textMarkColor,
                            String textMarkText,
                            float textMarkTextSize, int textMarkTextSizeUnit,
                            int textMarkTextColor,
                            float textMarkDx, int textMarkDxUnit,
                            float textMarkDy, int textMarkDyUnit) {

        setTextMarkRaw(convertToPX(textMarkRadius, textMarkRadiusUnit), textMarkColor, textMarkText,
                convertToPX(textMarkTextSize, textMarkTextSizeUnit), textMarkTextColor,
                convertToPX(textMarkDx, textMarkDxUnit), convertToPX(textMarkDy, textMarkDyUnit));
    }

    private void setTextMarkRaw(float textMarkRadius, int textMarkColor, String textMarkText, float textMarkTextSize, int textMarkTextColor,
                                float textMarkDx, float textMarkDy) {
        if (this.textMarkRadius != textMarkRadius || this.textMarkColor != textMarkColor || this.textMarkText != textMarkText
                || this.textMarkTextSize != textMarkTextSize || this.textMarkTextColor != textMarkTextColor || this.textMarkDx != textMarkDx
                || this.textMarkDy != textMarkDy) {
            this.textMarkRadius = textMarkRadius;
            this.textMarkColor = textMarkColor;
            this.textMarkText = textMarkText;
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
    private String textMarkText;

    public String getTextMarkText() {
        return textMarkText;
    }

    public void setTextMarkText(String textMarkText) {
        this.textMarkText = textMarkText;
        requestLayout();
    }

    private List<Integer> textMarkTextMeasureList;

    public List<Integer> getTextMarkTextMeasureList() {
        return textMarkTextMeasureList;
    }

    public void setTextMarkTextMeasureList(List<Integer> textMarkTextMeasureList) {
        this.textMarkTextMeasureList = textMarkTextMeasureList;
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

    // text default null
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        if (inverseBindingListener != null) {
            inverseBindingListener.onChange();
        }
        requestLayout();
    }

    private List<Integer> textMeasureList;

    public List<Integer> getTextMeasureList() {
        return textMeasureList;
    }

    public void setTextMeasureList(List<Integer> textMeasureList) {
        this.textMeasureList = textMeasureList;
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
        if (this.textColorNormal != textColorNormal) {
            this.textColorNormal = textColorNormal;
            invalidate();
        }
    }

    public void setTextColorNormal(String textColorNormal) {
        setTextColorNormal(Color.parseColor(textColorNormal));
    }

    // textColorPress default textColorNormal
    private int textColorPress;

    public int getTextColorPress() {
        return textColorPress;
    }

    public void setTextColorPress(int textColorPress) {
        if (this.textColorPress != textColorPress) {
            this.textColorPress = textColorPress;
            invalidate();
        }
    }

    public void setTextColorPress(String textColorPress) {
        setTextColorPress(Color.parseColor(textColorPress));
    }

    // textColorSelect default textColorNormal
    private int textColorSelect;

    public int getTextColorSelect() {
        return textColorSelect;
    }

    public void setTextColorSelect(int textColorSelect) {
        if (this.textColorSelect != textColorSelect) {
            this.textColorSelect = textColorSelect;
            invalidate();
        }
    }

    public void setTextColorSelect(String textColorSelect) {
        setTextColorSelect(Color.parseColor(textColorSelect));
    }

    public void setTextColor(int textColor) {
        this.textColorNormal = textColor;
        this.textColorPress = textColor;
        this.textColorSelect = textColor;
        invalidate();
    }

    public void setTextColor(String textColor) {
        setTextColor(Color.parseColor(textColor));
    }

    // textPaddingLeft means distance between srcLeftNormal and The
    // leftmost,note about the srcLeftPaddingNormal
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

    // textPaddingRight means distance between srcRightNormal and The
    // rightmost,note about the srcRightPaddingNormal
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


    @IntDef({TEXT_GRAVITY_CENTER, TEXT_GRAVITY_LEFT, TEXT_GRAVITY_RIGHT, TEXT_GRAVITY_CENTER_LEFT, TEXT_GRAVITY_LEFT_CENTER})
    @Retention(RetentionPolicy.SOURCE)
    public @interface textGravityMode {
    }

    public static final int TEXT_GRAVITY_CENTER = 0;
    public static final int TEXT_GRAVITY_LEFT = 1;
    public static final int TEXT_GRAVITY_RIGHT = 2;
    public static final int TEXT_GRAVITY_CENTER_LEFT = 3;
    public static final int TEXT_GRAVITY_LEFT_CENTER = 4;
    private static final int[] textGravityModeArray = {TEXT_GRAVITY_CENTER, TEXT_GRAVITY_LEFT, TEXT_GRAVITY_RIGHT, TEXT_GRAVITY_CENTER_LEFT, TEXT_GRAVITY_LEFT_CENTER,};
    private @textGravityMode
    int textGravityMode;

    public int getTextGravity() {
        return textGravityMode;
    }

    public void setTextGravity(int textGravityMode) {
        this.textGravityMode = textGravityMode;
    }

    //
    private Typeface textStyle;
    private static final int[] textStyleArray = {Typeface.NORMAL, Typeface.BOLD, Typeface.ITALIC, Typeface.BOLD_ITALIC};

    public Typeface getTextStyle() {
        return textStyle;
    }

    public void setTextStyle(Typeface textStyle) {
        this.textStyle = textStyle;
    }

    private String textAssets;

    public String getTextAssets() {
        return textAssets;
    }

    public void setTextAssets(String textAssets) {
        this.textAssets = textAssets;
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
    private float textDrawThick;
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

    // content default null
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
        if (inverseBindingListener != null) {
            inverseBindingListener.onChange();
        }
        requestLayout();
    }

    private List<Integer> contentMeasureList;

    public List<Integer> getContentMeasureList() {
        return contentMeasureList;
    }

    public void setContentMeasureList(List<Integer> contentMeasureList) {
        this.contentMeasureList = contentMeasureList;
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

    public void setContentColorNormal(String contentColorNormal) {
        this.contentColorNormal = Color.parseColor(contentColorNormal);
    }

    // contentColorPress default contentColorNormal
    private int contentColorPress;

    public int getContentColorPress() {
        return contentColorPress;
    }

    public void setContentColorPress(int contentColorPress) {
        if (this.contentColorPress != contentColorPress) {
            this.contentColorPress = contentColorPress;
            invalidate();
        }
    }

    public void setContentColorPress(String contentColorPress) {
        setContentColorPress(Color.parseColor(contentColorPress));
    }

    // contentColorSelect default contentColorNormal
    private int contentColorSelect;

    public int getContentColorSelect() {
        return contentColorSelect;
    }

    public void setContentColorSelect(int contentColorSelect) {
        if (this.contentColorSelect != contentColorSelect) {
            this.contentColorSelect = contentColorSelect;
            invalidate();
        }
    }

    public void setContentColorSelect(String contentColorSelect) {
        setContentColorSelect(Color.parseColor(contentColorSelect));
    }

    public void setContentColor(int contentColor) {
        this.contentColorNormal = contentColor;
        this.contentColorPress = contentColor;
        this.contentColorSelect = contentColor;
        invalidate();
    }

    public void setContentColor(String contentColor) {
        setContentColor(Color.parseColor(contentColor));
    }

    // contentPaddingLeft means distance between srcLeftNormal and The
    // leftmost,note about the srcLeftPaddingNormal
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

    // contentPaddingRight means distance between srcRightNormal and The
    // rightmost,note about the srcRightPaddingNormal
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
    @IntDef({CONTENT_GRAVITY_CENTER, CONTENT_GRAVITY_LEFT, CONTENT_GRAVITY_CENTER_LEFT, CONTENT_GRAVITY_LEFT_CENTER})
    @Retention(RetentionPolicy.SOURCE)
    public @interface contentGravityMode {
    }

    public static final int CONTENT_GRAVITY_CENTER = 0;
    public static final int CONTENT_GRAVITY_LEFT = 1;
    public static final int CONTENT_GRAVITY_RIGHT = 2;
    public static final int CONTENT_GRAVITY_CENTER_LEFT = 3;
    public static final int CONTENT_GRAVITY_LEFT_CENTER = 4;
    private static final int[] contentGravityModeArray = {CONTENT_GRAVITY_CENTER, CONTENT_GRAVITY_LEFT, CONTENT_GRAVITY_RIGHT, CONTENT_GRAVITY_CENTER_LEFT, CONTENT_GRAVITY_LEFT_CENTER,};
    private @contentGravityMode
    int contentGravityMode;

    //
    private Typeface contentStyle;
    private static final int[] contentStyleArray = {Typeface.NORMAL, Typeface.BOLD, Typeface.ITALIC, Typeface.BOLD_ITALIC,};

    public Typeface getContentStyle() {
        return contentStyle;
    }

    public void setContentStyle(Typeface contentStyle) {
        this.contentStyle = contentStyle;
    }

    private String contentAssets;

    public String getContentAssets() {
        return contentAssets;
    }

    public void setContentAssets(String contentAssets) {
        this.contentAssets = contentAssets;
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

    public void setContentMark(String contentMarkText) {
        this.contentMarkText = contentMarkText;
        invalidate();
    }

    public void setContentMark(float contentMarkRadius, int contentMarkColor, String contentMarkText, float contentMarkTextSize,
                               int contentMarkTextColor, float contentMarkDx, float contentMarkDy) {
        setContentMarkRaw(contentMarkRadius, contentMarkColor, contentMarkText,
                contentMarkTextSize, contentMarkTextColor, contentMarkDx, contentMarkDy);
    }

    public void setContentMark(float contentMarkRadius, int contentMarkRadiusUnit,
                               int contentMarkColor,
                               String contentMarkText,
                               float contentMarkTextSize, int contentMarkTextSizeUnit,
                               int contentMarkTextColor,
                               float contentMarkDx, int contentMarkDxUnit,
                               float contentMarkDy, int contentMarkDyUnit) {

        setContentMarkRaw(convertToPX(contentMarkRadius, contentMarkRadiusUnit), contentMarkColor, contentMarkText,
                convertToPX(contentMarkTextSize, contentMarkTextSizeUnit), contentMarkTextColor,
                convertToPX(contentMarkDx, contentMarkDxUnit), convertToPX(contentMarkDy, contentMarkDyUnit));
    }

    private void setContentMarkRaw(float contentMarkRadius, int contentMarkColor, String contentMarkText, float contentMarkTextSize,
                                   int contentMarkTextColor, float contentMarkDx, float contentMarkDy) {
        if (this.contentMarkRadius != contentMarkRadius || this.contentMarkColor != contentMarkColor || this.contentMarkText != contentMarkText
                || this.contentMarkTextSize != contentMarkTextSize || this.contentMarkTextColor != contentMarkTextColor
                || this.contentMarkDx != contentMarkDx || this.contentMarkDy != contentMarkDy) {
            this.contentMarkRadius = contentMarkRadius;
            this.contentMarkColor = contentMarkColor;
            this.contentMarkText = contentMarkText;
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
    private String contentMarkText;

    public String getContentMarkText() {
        return contentMarkText;
    }

    public void setContentMarkText(String contentMarkText) {
        this.contentMarkText = contentMarkText;
        requestLayout();
    }

    private List<Integer> contentMarkTextMeasureList;

    public List<Integer> getContentMarkTextMeasureList() {
        return contentMarkTextMeasureList;
    }

    public void setContentMarkTextMeasureList(List<Integer> contentMarkTextMeasureList) {
        this.contentMarkTextMeasureList = contentMarkTextMeasureList;
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
    private float contentDrawThick;
    private float contentEndOffsetCenterX;
    private float contentEndOffsetCenterY;

    //
    private Bitmap srcLeftNormal;

    public Bitmap getSrcLeftNormal() {
        return srcLeftNormal;
    }

    public void setSrcLeftNormal(Bitmap srcLeftNormal) {
        this.srcLeftNormal = srcLeftNormal;
        invalidate();
    }

    private Bitmap srcLeftPress;

    public Bitmap getSrcLeftPress() {
        return srcLeftPress;
    }

    public void setSrcLeftPress(Bitmap srcLeftPress) {
        this.srcLeftPress = srcLeftPress;
        invalidate();
    }

    private Bitmap srcLeftSelect;

    public Bitmap getSrcLeftSelect() {
        return srcLeftSelect;
    }

    public void setSrcLeftSelect(Bitmap srcLeftSelect) {
        this.srcLeftSelect = srcLeftSelect;
        invalidate();
    }

    public void setSrcLeft(Bitmap srcLeft) {
        this.srcLeftNormal = srcLeft;
        this.srcLeftPress = srcLeft;
        this.srcLeftSelect = srcLeft;
        invalidate();
    }

    //
    private float srcLeftWidthNormal;

    public float getSrcLeftWidthNormal() {
        return srcLeftWidthNormal;
    }

    public void setSrcLeftWidthNormal(float srcLeftWidthNormal) {
        setSrcLeftWidthRawNormal(srcLeftWidthNormal);
    }

    public void setSrcLeftWidthNormal(float srcLeftWidthNormal, int unit) {
        setSrcLeftWidthRawNormal(convertToPX(srcLeftWidthNormal, unit));
    }

    private void setSrcLeftWidthRawNormal(float srcLeftWidthNormal) {
        if (this.srcLeftWidthNormal != srcLeftWidthNormal) {
            this.srcLeftWidthNormal = srcLeftWidthNormal;
            invalidate();
        }
    }

    private float srcLeftWidthPress;

    public float getSrcLeftWidthPress() {
        return srcLeftWidthPress;
    }

    public void setSrcLeftWidthPress(float srcLeftWidthPress) {
        setSrcLeftWidthRawPress(srcLeftWidthPress);
    }

    public void setSrcLeftWidthPress(float srcLeftWidthPress, int unit) {
        setSrcLeftWidthRawPress(convertToPX(srcLeftWidthPress, unit));
    }

    private void setSrcLeftWidthRawPress(float srcLeftWidthPress) {
        if (this.srcLeftWidthPress != srcLeftWidthPress) {
            this.srcLeftWidthPress = srcLeftWidthPress;
            invalidate();
        }
    }

    private float srcLeftWidthSelect;

    public float getSrcLeftWidthSelect() {
        return srcLeftWidthSelect;
    }

    public void setSrcLeftWidthSelect(float srcLeftWidthSelect) {
        setSrcLeftWidthRawPress(srcLeftWidthSelect);
    }

    public void setSrcLeftWidthSelect(float srcLeftWidthSelect, int unit) {
        setSrcLeftWidthRawSelect(convertToPX(srcLeftWidthSelect, unit));
    }

    private void setSrcLeftWidthRawSelect(float srcLeftWidthSelect) {
        if (this.srcLeftWidthSelect != srcLeftWidthSelect) {
            this.srcLeftWidthSelect = srcLeftWidthSelect;
            invalidate();
        }
    }

    public void setSrcLeftWidth(float srcLeftWidth) {
        setSrcLeftWidthRaw(srcLeftWidth);
    }

    public void setSrcLeftWidth(float srcLeftWidth, int unit) {
        setSrcLeftWidthRaw(convertToPX(srcLeftWidth, unit));
    }

    public void setSrcLeftWidthRaw(float srcLeftWidth) {
        if (this.srcLeftWidthNormal != srcLeftWidth) {
            this.srcLeftWidthNormal = srcLeftWidth;
        }
        if (this.srcLeftWidthPress != srcLeftWidth) {
            this.srcLeftWidthPress = srcLeftWidth;
        }
        if (this.srcLeftWidthSelect != srcLeftWidth) {
            this.srcLeftWidthSelect = srcLeftWidth;
        }
        invalidate();
    }

    //
    private float srcLeftHeightNormal;

    public float getSrcLeftHeightNormal() {
        return srcLeftHeightNormal;
    }

    public void setSrcLeftHeightNormal(float srcLeftHeightNormal) {
        setSrcLeftHeightRawNormal(srcLeftHeightNormal);
    }

    public void setSrcLeftHeightNormal(float srcLeftHeightNormal, int unit) {
        setSrcLeftHeightRawNormal(convertToPX(srcLeftHeightNormal, unit));
    }

    private void setSrcLeftHeightRawNormal(float srcLeftHeightNormal) {
        if (this.srcLeftHeightNormal != srcLeftHeightNormal) {
            this.srcLeftHeightNormal = srcLeftHeightNormal;
            invalidate();
        }
    }

    //
    private float srcLeftHeightPress;

    public float getSrcLeftHeightPress() {
        return srcLeftHeightPress;
    }

    public void setSrcLeftHeightPress(float srcLeftHeightPress) {
        setSrcLeftHeightRawPress(srcLeftHeightPress);
    }

    public void setSrcLeftHeightPress(float srcLeftHeightPress, int unit) {
        setSrcLeftHeightRawPress(convertToPX(srcLeftHeightPress, unit));
    }

    private void setSrcLeftHeightRawPress(float srcLeftHeightPress) {
        if (this.srcLeftHeightPress != srcLeftHeightPress) {
            this.srcLeftHeightPress = srcLeftHeightPress;
            invalidate();
        }
    }

    //
    private float srcLeftHeightSelect;

    public float getSrcLeftHeightSelect() {
        return srcLeftHeightSelect;
    }

    public void setSrcLeftHeightSelect(float srcLeftHeightSelect) {
        setSrcLeftHeightRawSelect(srcLeftHeightSelect);
    }

    public void setSrcLeftHeightSelect(float srcLeftHeightSelect, int unit) {
        setSrcLeftHeightRawSelect(convertToPX(srcLeftHeightSelect, unit));
    }

    private void setSrcLeftHeightRawSelect(float srcLeftHeightSelect) {
        if (this.srcLeftHeightSelect != srcLeftHeightSelect) {
            this.srcLeftHeightSelect = srcLeftHeightSelect;
            invalidate();
        }
    }

    public void setSrcLeftHeight(float srcLeftHeight) {
        setSrcLeftHeightRaw(srcLeftHeight);
    }

    public void setSrcLeftHeight(float srcLeftHeight, int unit) {
        setSrcLeftHeightRaw(convertToPX(srcLeftHeight, unit));
    }

    public void setSrcLeftHeightRaw(float srcLeftHeight) {
        if (this.srcLeftHeightNormal != srcLeftHeight) {
            this.srcLeftHeightNormal = srcLeftHeight;
        }
        if (this.srcLeftHeightPress != srcLeftHeight) {
            this.srcLeftHeightPress = srcLeftHeight;
        }
        if (this.srcLeftHeightSelect != srcLeftHeight) {
            this.srcLeftHeightSelect = srcLeftHeight;
        }
        invalidate();
    }

    //
    protected Matrix matrixSrcLeftNormal;
    protected Matrix matrixSrcLeftPress;
    protected Matrix matrixSrcLeftSelect;

    // srcLeftPaddingNormal means distance between srcLeftNormal and textview,note about the textPaddingLeftNormal
    private float srcLeftPaddingNormal;

    public float getSrcLeftPaddingNormal() {
        return srcLeftPaddingNormal;
    }

    public void setSrcLeftPaddingNormal(float srcLeftPaddingNormal) {
        setSrcLeftPaddingRawNormal(srcLeftPaddingNormal);
    }

    public void setSrcLeftPaddingNormal(float srcLeftPaddingNormal, int unit) {
        setSrcLeftPaddingRawNormal(convertToPX(srcLeftPaddingNormal, unit));
    }

    private void setSrcLeftPaddingRawNormal(float srcLeftPaddingNormal) {
        if (this.srcLeftPaddingNormal != srcLeftPaddingNormal) {
            this.srcLeftPaddingNormal = srcLeftPaddingNormal;
            invalidate();
        }
    }

    private float srcLeftPaddingPress;

    public float getSrcLeftPaddingPress() {
        return srcLeftPaddingPress;
    }

    public void setSrcLeftPaddingPress(float srcLeftPaddingPress) {
        setSrcLeftPaddingRawPress(srcLeftPaddingPress);
    }

    public void setSrcLeftPaddingPress(float srcLeftPaddingPress, int unit) {
        setSrcLeftPaddingRawPress(convertToPX(srcLeftPaddingPress, unit));
    }

    private void setSrcLeftPaddingRawPress(float srcLeftPaddingPress) {
        if (this.srcLeftPaddingPress != srcLeftPaddingPress) {
            this.srcLeftPaddingPress = srcLeftPaddingPress;
            invalidate();
        }
    }

    private float srcLeftPaddingSelect;

    public float getSrcLeftPaddingSelect() {
        return srcLeftPaddingSelect;
    }

    public void setSrcLeftPaddingSelect(float srcLeftPaddingSelect) {
        setSrcLeftPaddingRawSelect(srcLeftPaddingSelect);
    }

    public void setSrcLeftPaddingSelect(float srcLeftPaddingSelect, int unit) {
        setSrcLeftPaddingRawSelect(convertToPX(srcLeftPaddingSelect, unit));
    }

    private void setSrcLeftPaddingRawSelect(float srcLeftPaddingSelect) {
        if (this.srcLeftPaddingSelect != srcLeftPaddingSelect) {
            this.srcLeftPaddingSelect = srcLeftPaddingSelect;
            invalidate();
        }
    }

    //
    private float srcLeftDxNormal;

    public float getSrcLeftDxNormal() {
        return srcLeftDxNormal;
    }

    public void setSrcLeftDxNormal(float srcLeftDxNormal) {
        setSrcLeftDxRawNormal(srcLeftDxNormal);
    }

    public void setSrcLeftDxNormal(float srcLeftDxNormal, int unit) {
        setSrcLeftDxRawNormal(convertToPX(srcLeftDxNormal, unit));
    }

    private void setSrcLeftDxRawNormal(float srcLeftDxNormal) {
        if (this.srcLeftDxNormal != srcLeftDxNormal) {
            this.srcLeftDxNormal = srcLeftDxNormal;
            invalidate();
        }
    }

    private float srcLeftDxPress;

    public float getSrcLeftDxPress() {
        return srcLeftDxPress;
    }

    public void setSrcLeftDxPress(float srcLeftDxPress) {
        setSrcLeftDxRawPress(srcLeftDxPress);
    }

    public void setSrcLeftDxPress(float srcLeftDxPress, int unit) {
        setSrcLeftDxRawPress(convertToPX(srcLeftDxPress, unit));
    }

    private void setSrcLeftDxRawPress(float srcLeftDxPress) {
        if (this.srcLeftDxPress != srcLeftDxPress) {
            this.srcLeftDxPress = srcLeftDxPress;
            invalidate();
        }
    }

    private float srcLeftDxSelect;

    public float getSrcLeftDxSelect() {
        return srcLeftDxSelect;
    }

    public void setSrcLeftDxSelect(float srcLeftDxSelect) {
        setSrcLeftDxRawPress(srcLeftDxSelect);
    }

    public void setSrcLeftDxSelect(float srcLeftDxSelect, int unit) {
        setSrcLeftDxRawSelect(convertToPX(srcLeftDxSelect, unit));
    }

    private void setSrcLeftDxRawSelect(float srcLeftDxSelect) {
        if (this.srcLeftDxSelect != srcLeftDxSelect) {
            this.srcLeftDxSelect = srcLeftDxSelect;
            invalidate();
        }
    }

    public void setSrcLeftDx(float srcLeftDx) {
        setSrcLeftDxRaw(srcLeftDx);
    }

    public void setSrcLeftDx(float srcLeftDx, int unit) {
        setSrcLeftDxRaw(convertToPX(srcLeftDx, unit));
    }

    public void setSrcLeftDxRaw(float srcLeftDx) {
        if (this.srcLeftDxNormal != srcLeftDx) {
            this.srcLeftDxNormal = srcLeftDx;
        }
        if (this.srcLeftDxPress != srcLeftDx) {
            this.srcLeftDxPress = srcLeftDx;
        }
        if (this.srcLeftDxSelect != srcLeftDx) {
            this.srcLeftDxSelect = srcLeftDx;
        }
        invalidate();
    }

    //
    private float srcLeftDyNormal;

    public float getSrcLeftDyNormal() {
        return srcLeftDyNormal;
    }

    public void setSrcLeftDyNormal(float srcLeftDyNormal) {
        setSrcLeftDyRawNormal(srcLeftDyNormal);
    }

    public void setSrcLeftDyNormal(float srcLeftDyNormal, int unit) {
        setSrcLeftDyRawNormal(convertToPX(srcLeftDyNormal, unit));
    }

    private void setSrcLeftDyRawNormal(float srcLeftDyNormal) {
        if (this.srcLeftDyNormal != srcLeftDyNormal) {
            this.srcLeftDyNormal = srcLeftDyNormal;
            invalidate();
        }
    }

    private float srcLeftDyPress;

    public float getSrcLeftDyPress() {
        return srcLeftDyPress;
    }

    public void setSrcLeftDyPress(float srcLeftDyPress) {
        setSrcLeftDyRawPress(srcLeftDyPress);
    }

    public void setSrcLeftDyPress(float srcLeftDyPress, int unit) {
        setSrcLeftDyRawPress(convertToPX(srcLeftDyPress, unit));
    }

    private void setSrcLeftDyRawPress(float srcLeftDyPress) {
        if (this.srcLeftDyPress != srcLeftDyPress) {
            this.srcLeftDyPress = srcLeftDyPress;
            invalidate();
        }
    }

    private float srcLeftDySelect;

    public float getSrcLeftDySelect() {
        return srcLeftDySelect;
    }

    public void setSrcLeftDySelect(float srcLeftDySelect) {
        setSrcLeftDyRawSelect(srcLeftDySelect);
    }

    public void setSrcLeftDySelect(float srcLeftDySelect, int unit) {
        setSrcLeftDyRawSelect(convertToPX(srcLeftDySelect, unit));
    }

    private void setSrcLeftDyRawSelect(float srcLeftDySelect) {
        if (this.srcLeftDySelect != srcLeftDySelect) {
            this.srcLeftDySelect = srcLeftDySelect;
            invalidate();
        }
    }

    public void setSrcLeftDy(float srcLeftDy) {
        setSrcLeftDyRaw(srcLeftDy);
    }

    public void setSrcLeftDy(float srcLeftDy, int unit) {
        setSrcLeftDyRaw(convertToPX(srcLeftDy, unit));
    }

    public void setSrcLeftDyRaw(float srcLeftDy) {
        if (this.srcLeftDyNormal != srcLeftDy) {
            this.srcLeftDyNormal = srcLeftDy;
        }
        if (this.srcLeftDyPress != srcLeftDy) {
            this.srcLeftDyPress = srcLeftDy;
        }
        if (this.srcLeftDySelect != srcLeftDy) {
            this.srcLeftDySelect = srcLeftDy;
        }
        invalidate();
    }

    //
    private Bitmap srcRightNormal;

    public Bitmap getSrcRightNormal() {
        return srcRightNormal;
    }

    public void setSrcRightNormal(Bitmap srcRightNormal) {
        this.srcRightNormal = srcRightNormal;
        invalidate();
    }

    private Bitmap srcRightPress;

    public Bitmap getSrcRightPress() {
        return srcRightPress;
    }

    public void setSrcRightPress(Bitmap srcRightPress) {
        this.srcRightPress = srcRightPress;
        invalidate();
    }

    private Bitmap srcRightSelect;

    public Bitmap getSrcRightSelect() {
        return srcRightNormal;
    }

    public void setSrcRightSelect(Bitmap srcRightSelect) {
        this.srcRightSelect = srcRightSelect;
        invalidate();
    }

    public void setSrcRight(Bitmap srcRight) {
        this.srcRightNormal = srcRight;
        this.srcRightPress = srcRight;
        this.srcRightSelect = srcRight;
        invalidate();
    }

    //
    private float srcRightWidthNormal;

    public float getSrcRightWidthNormal() {
        return srcRightWidthNormal;
    }

    public void setSrcRightWidthNormal(float srcRightWidthNormal) {
        setSrcRightWidthRawNormal(srcRightWidthNormal);
    }

    public void setSrcRightWidthNormal(float srcRightWidthNormal, int unit) {
        setSrcRightWidthRawNormal(convertToPX(srcRightWidthNormal, unit));
    }

    private void setSrcRightWidthRawNormal(float srcRightWidthNormal) {
        if (this.srcRightWidthNormal != srcRightWidthNormal) {
            this.srcRightWidthNormal = srcRightWidthNormal;
            invalidate();
        }
    }

    private float srcRightWidthPress;

    public float getSrcRightWidthPress() {
        return srcRightWidthPress;
    }

    public void setSrcRightWidthPress(float srcRightWidthPress) {
        setSrcRightWidthRawPress(srcRightWidthPress);
    }

    public void setSrcRightWidthPress(float srcRightWidthPress, int unit) {
        setSrcRightWidthRawPress(convertToPX(srcRightWidthPress, unit));
    }

    private void setSrcRightWidthRawPress(float srcRightWidthPress) {
        if (this.srcRightWidthPress != srcRightWidthPress) {
            this.srcRightWidthPress = srcRightWidthPress;
            invalidate();
        }
    }

    private float srcRightWidthSelect;

    public float getSrcRightWidthSelect() {
        return srcRightWidthSelect;
    }

    public void setSrcRightWidthSelect(float srcRightWidthSelect) {
        setSrcRightWidthRawSelect(srcRightWidthSelect);
    }

    public void setSrcRightWidthSelect(float srcRightWidthSelect, int unit) {
        setSrcRightWidthRawSelect(convertToPX(srcRightWidthSelect, unit));
    }

    private void setSrcRightWidthRawSelect(float srcRightWidthSelect) {
        if (this.srcRightWidthSelect != srcRightWidthSelect) {
            this.srcRightWidthSelect = srcRightWidthSelect;
            invalidate();
        }
    }

    public void setSrcRightWidth(float srcRightWidth) {
        setSrcRightWidthRaw(srcRightWidth);
    }

    public void setSrcRightWidth(float srcRightWidth, int unit) {
        setSrcRightWidthRaw(convertToPX(srcRightWidth, unit));
    }

    public void setSrcRightWidthRaw(float srcRightWidth) {
        if (this.srcRightWidthNormal != srcRightWidth) {
            this.srcRightWidthNormal = srcRightWidth;
        }
        if (this.srcRightWidthPress != srcRightWidth) {
            this.srcRightWidthPress = srcRightWidth;
        }
        if (this.srcRightWidthSelect != srcRightWidth) {
            this.srcRightWidthSelect = srcRightWidth;
        }
        invalidate();
    }

    //
    private float srcRightHeightNormal;

    public float getSrcRightHeightNormal() {
        return srcRightHeightNormal;
    }

    public void setSrcRightHeightNormal(float srcRightHeightNormal) {
        setSrcRightHeightRawNormal(srcRightHeightNormal);
    }

    public void setSrcRightHeightNormal(float srcRightHeightNormal, int unit) {
        setSrcRightHeightRawNormal(convertToPX(srcRightHeightNormal, unit));
    }

    private void setSrcRightHeightRawNormal(float srcRightHeightNormal) {
        if (this.srcRightHeightNormal != srcRightHeightNormal) {
            this.srcRightHeightNormal = srcRightHeightNormal;
            invalidate();
        }
    }

    private float srcRightHeightPress;

    public float getSrcRightHeightPress() {
        return srcRightHeightPress;
    }

    public void setSrcRightHeightPress(float srcRightHeightPress) {
        setSrcRightHeightRawPress(srcRightHeightPress);
    }

    public void setSrcRightHeightPress(float srcRightHeightPress, int unit) {
        setSrcRightHeightRawPress(convertToPX(srcRightHeightPress, unit));
    }

    private void setSrcRightHeightRawPress(float srcRightHeightPress) {
        if (this.srcRightHeightPress != srcRightHeightPress) {
            this.srcRightHeightPress = srcRightHeightPress;
            invalidate();
        }
    }

    private float srcRightHeightSelect;

    public float getSrcRightHeightSelect() {
        return srcRightHeightSelect;
    }

    public void setSrcRightHeightSelect(float srcRightHeightSelect) {
        setSrcRightHeightRawSelect(srcRightHeightSelect);
    }

    public void setSrcRightHeightSelect(float srcRightHeightSelect, int unit) {
        setSrcRightHeightRawSelect(convertToPX(srcRightHeightSelect, unit));
    }

    private void setSrcRightHeightRawSelect(float srcRightHeightSelect) {
        if (this.srcRightHeightSelect != srcRightHeightSelect) {
            this.srcRightHeightSelect = srcRightHeightSelect;
            invalidate();
        }
    }

    public void setSrcRightHeight(float srcRightHeight) {
        setSrcRightHeightRaw(srcRightHeight);
    }

    public void setSrcRightHeight(float srcRightHeight, int unit) {
        setSrcRightHeightRaw(convertToPX(srcRightHeight, unit));
    }

    public void setSrcRightHeightRaw(float srcRightHeight) {
        if (this.srcRightHeightNormal != srcRightHeight) {
            this.srcRightHeightNormal = srcRightHeight;
        }
        if (this.srcRightHeightPress != srcRightHeight) {
            this.srcRightHeightPress = srcRightHeight;
        }
        if (this.srcRightHeightSelect != srcRightHeight) {
            this.srcRightHeightSelect = srcRightHeight;
        }
        invalidate();
    }

    //
    protected Matrix matrixSrcRightNormal;
    protected Matrix matrixSrcRightPress;
    protected Matrix matrixSrcRightSelect;
    // srcRightPaddingNormal means distance between srcRightNormal and textview,note about the textPaddingRight

    private float srcRightPaddingNormal;

    public float getSrcRightPaddingNormal() {
        return srcRightPaddingNormal;
    }

    public void setSrcRightPaddingNormal(float srcRightPaddingNormal) {
        setSrcRightPaddingRawNormal(srcRightPaddingNormal);
    }

    public void setSrcRightPaddingNormal(float srcRightPaddingNormal, int unit) {
        setSrcRightPaddingRawNormal(convertToPX(srcRightPaddingNormal, unit));
    }

    private void setSrcRightPaddingRawNormal(float srcRightPaddingNormal) {
        if (this.srcRightPaddingNormal != srcRightPaddingNormal) {
            this.srcRightPaddingNormal = srcRightPaddingNormal;
            invalidate();
        }
    }

    private float srcRightPaddingPress;

    public float getSrcRightPaddingPress() {
        return srcRightPaddingPress;
    }

    public void setSrcRightPaddingPress(float srcRightPaddingPress) {
        setSrcRightPaddingRawPress(srcRightPaddingPress);
    }

    public void setSrcRightPaddingPress(float srcRightPaddingPress, int unit) {
        setSrcRightPaddingRawPress(convertToPX(srcRightPaddingPress, unit));
    }

    private void setSrcRightPaddingRawPress(float srcRightPaddingPress) {
        if (this.srcRightPaddingPress != srcRightPaddingPress) {
            this.srcRightPaddingPress = srcRightPaddingPress;
            invalidate();
        }
    }

    private float srcRightPaddingSelect;

    public float getSrcRightPaddingSelect() {
        return srcRightPaddingSelect;
    }

    public void setSrcRightPaddingSelect(float srcRightPaddingSelect) {
        setSrcRightPaddingRawSelect(srcRightPaddingSelect);
    }

    public void setSrcRightPaddingSelect(float srcRightPaddingSelect, int unit) {
        setSrcRightPaddingRawSelect(convertToPX(srcRightPaddingSelect, unit));
    }

    private void setSrcRightPaddingRawSelect(float srcRightPaddingSelect) {
        if (this.srcRightPaddingSelect != srcRightPaddingSelect) {
            this.srcRightPaddingSelect = srcRightPaddingSelect;
            invalidate();
        }
    }

    //
    private float srcRightDxNormal;

    public float getSrcRightDxNormal() {
        return srcRightDxNormal;
    }

    public void setSrcRightDxNormal(float srcRightDxNormal) {
        setSrcRightDxRawNormal(srcRightDxNormal);
    }

    public void setSrcRightDxNormal(float srcRightDxNormal, int unit) {
        setSrcRightDxRawNormal(convertToPX(srcRightDxNormal, unit));
    }

    private void setSrcRightDxRawNormal(float srcRightDxNormal) {
        if (this.srcRightDxNormal != srcRightDxNormal) {
            this.srcRightDxNormal = srcRightDxNormal;
            invalidate();
        }
    }

    private float srcRightDxPress;

    public float getSrcRightDxPress() {
        return srcRightDxPress;
    }

    public void setSrcRightDxPress(float srcRightDxPress) {
        setSrcRightDxRawPress(srcRightDxPress);
    }

    public void setSrcRightDxPress(float srcRightDxNormal, int unit) {
        setSrcRightDxRawNormal(convertToPX(srcRightDxNormal, unit));
    }

    private void setSrcRightDxRawPress(float srcRightDxNormal) {
        if (this.srcRightDxNormal != srcRightDxNormal) {
            this.srcRightDxNormal = srcRightDxNormal;
            invalidate();
        }
    }

    private float srcRightDxSelect;

    public float getSrcRightDxSelect() {
        return srcRightDxSelect;
    }

    public void setSrcRightDxSelect(float srcRightDxSelect) {
        setSrcRightDxRawSelect(srcRightDxSelect);
    }

    public void setSrcRightDxSelect(float srcRightDxSelect, int unit) {
        setSrcRightDxRawSelect(convertToPX(srcRightDxSelect, unit));
    }

    private void setSrcRightDxRawSelect(float srcRightDxSelect) {
        if (this.srcRightDxSelect != srcRightDxSelect) {
            this.srcRightDxSelect = srcRightDxSelect;
            invalidate();
        }
    }

    public void setSrcRightDx(float srcRightDx) {
        setSrcRightDxRaw(srcRightDx);
    }

    public void setSrcRightDx(float srcRightDx, int unit) {
        setSrcRightDxRaw(convertToPX(srcRightDx, unit));
    }

    public void setSrcRightDxRaw(float srcRightDx) {
        if (this.srcRightDxNormal != srcRightDx) {
            this.srcRightDxNormal = srcRightDx;
        }
        if (this.srcRightDxPress != srcRightDx) {
            this.srcRightDxPress = srcRightDx;
        }
        if (this.srcRightDxSelect != srcRightDx) {
            this.srcRightDxSelect = srcRightDx;
        }
        invalidate();
    }

    //
    private float srcRightDyNormal;

    public float getSrcRightDyNormal() {
        return srcRightDyNormal;
    }

    public void setSrcRightDyNormal(float srcRightDyNormal) {
        setSrcRightDyRawNormal(srcRightDyNormal);
    }

    public void setSrcRightDyNormal(float srcRightDyNormal, int unit) {
        setSrcRightDyRawNormal(convertToPX(srcRightDyNormal, unit));
    }

    private void setSrcRightDyRawNormal(float srcRightDyNormal) {
        if (this.srcRightDyNormal != srcRightDyNormal) {
            this.srcRightDyNormal = srcRightDyNormal;
            invalidate();
        }
    }

    private float srcRightDyPress;

    public float getSrcRightDyPress() {
        return srcRightDyNormal;
    }

    public void setSrcRightDyPress(float srcRightDyPress) {
        setSrcRightDyRawPress(srcRightDyPress);
    }

    public void setSrcRightDyPress(float srcRightDyPress, int unit) {
        setSrcRightDyRawPress(convertToPX(srcRightDyPress, unit));
    }

    private void setSrcRightDyRawPress(float srcRightDyPress) {
        if (this.srcRightDyPress != srcRightDyPress) {
            this.srcRightDyPress = srcRightDyPress;
            invalidate();
        }
    }

    private float srcRightDySelect;

    public float getSrcRightDySelect() {
        return srcRightDySelect;
    }

    public void setSrcRightDySelect(float srcRightDySelect) {
        setSrcRightDyRawSelect(srcRightDySelect);
    }

    public void setSrcRightDySelect(float srcRightDySelect, int unit) {
        setSrcRightDyRawSelect(convertToPX(srcRightDySelect, unit));
    }

    private void setSrcRightDyRawSelect(float srcRightDySelect) {
        if (this.srcRightDySelect != srcRightDySelect) {
            this.srcRightDySelect = srcRightDySelect;
            invalidate();
        }
    }

    public void setSrcRightDy(float srcRightDy) {
        setSrcRightDyRaw(srcRightDy);
    }

    public void setSrcRightDy(float srcRightDy, int unit) {
        setSrcRightDyRaw(convertToPX(srcRightDy, unit));
    }

    public void setSrcRightDyRaw(float srcRightDy) {
        if (this.srcRightDyNormal != srcRightDy) {
            this.srcRightDyNormal = srcRightDy;
        }
        if (this.srcRightDyPress != srcRightDy) {
            this.srcRightDyPress = srcRightDy;
        }
        if (this.srcRightDySelect != srcRightDy) {
            this.srcRightDySelect = srcRightDy;
        }
        invalidate();
    }

    // attention porterDuffXferMode default 0 instead of -1!
    protected PorterDuffXfermode porterDuffXferMode;
    protected final Mode[] porterDuffXferModeArray = {PorterDuff.Mode.SRC_IN, PorterDuff.Mode.SRC_OUT,};

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
    private int materialTime;

    public int getMaterialDuraction() {
        return materialTime;
    }

    public void setMaterialDuraction(int materialTime) {
        this.materialTime = materialTime;
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

    // in response to the dispatchTouchEvent event: need touch TOUCH_DOWN_TIMES
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
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TView, 0, defStyle);

        press = typedArray.getBoolean(R.styleable.TView_press, false);
        // selectMode default need
        int pressModeIndex = typedArray.getInt(R.styleable.TView_pressMode, 0);
        pressMode = pressModeArray[pressModeIndex];

        //
        select = typedArray.getBoolean(R.styleable.TView_select, false);
        // selectMode default reverse
        int selectModeIndex = typedArray.getInt(R.styleable.TView_selectMode, 0);
        selectMode = selectModeArray[selectModeIndex];

        animation = typedArray.getBoolean(R.styleable.TView_animation, false);

        // porterDuffXferModeArrayIndex default PorterDuff.Mode.SRC_IN
        int porterDuffXferModeArrayIndex = typedArray.getInt(R.styleable.TView_porterDuffXferMode, 0);
        porterDuffXferMode = new PorterDuffXfermode(porterDuffXferModeArray[porterDuffXferModeArrayIndex]);

        //
        radius = typedArray.getDimension(R.styleable.TView_radius, 0);
        radiusTopLeft = typedArray.getDimension(R.styleable.TView_radiusTopLeft, radius);
        radiusTopRight = typedArray.getDimension(R.styleable.TView_radiusTopRight, radius);
        radiusBottomLeft = typedArray.getDimension(R.styleable.TView_radiusBottomLeft, radius);
        radiusBottomRight = typedArray.getDimension(R.styleable.TView_radiusBottomRight, radius);

        //
        adjust = typedArray.getBoolean(R.styleable.TView_adjust, false);
        dispatch = typedArray.getBoolean(R.styleable.TView_dispatch, false);

        //
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
            backgroundAngleNormal = typedArray.getInt(R.styleable.TView_backgroundAngleNormal, Integer.MAX_VALUE);
            if (backgroundAngleNormal != Integer.MAX_VALUE) {
                backgroundStartNormal = typedArray.getColor(R.styleable.TView_backgroundStartNormal, backgroundNormal);
                backgroundEndNormal = typedArray.getColor(R.styleable.TView_backgroundEndNormal, backgroundNormal);
            }

            backgroundAnglePress = typedArray.getInt(R.styleable.TView_backgroundAnglePress, backgroundAngleNormal);
            if (backgroundAnglePress != Integer.MAX_VALUE) {
                backgroundStartPress = typedArray.getColor(R.styleable.TView_backgroundStartPress, backgroundStartNormal);
                backgroundEndPress = typedArray.getColor(R.styleable.TView_backgroundEndPress, backgroundEndNormal);
            }

            backgroundAngleSelect = typedArray.getInt(R.styleable.TView_backgroundAngleSelect, backgroundAngleNormal);
            if (backgroundAngleSelect != Integer.MAX_VALUE) {
                backgroundStartSelect = typedArray.getColor(R.styleable.TView_backgroundStartSelect, backgroundStartNormal);
                backgroundEndSelect = typedArray.getColor(R.styleable.TView_backgroundEndSelect, backgroundEndNormal);
            }

            //Note background Normal ShadowRadius and srcNormal ShadowRadius are two values!
            backgroundShadowRadiusNormal = typedArray.getDimension(R.styleable.TView_backgroundShadowRadiusNormal, 0);
            if (backgroundShadowRadiusNormal > 0) {
                backgroundShadowColorNormal = typedArray.getColor(R.styleable.TView_backgroundShadowColorNormal, Color.TRANSPARENT);
                backgroundShadowDxNormal = typedArray.getDimension(R.styleable.TView_backgroundShadowDxNormal, 0);
                backgroundShadowDyNormal = typedArray.getDimension(R.styleable.TView_backgroundShadowDyNormal, 0);
            }

            //
            backgroundShadowRadiusPress = typedArray.getDimension(R.styleable.TView_backgroundShadowRadiusPress, backgroundShadowRadiusNormal);
            if (backgroundShadowRadiusPress > 0) {
                backgroundShadowColorPress = typedArray.getColor(R.styleable.TView_backgroundShadowColorPress, backgroundShadowColorNormal);
                backgroundShadowDxPress = typedArray.getDimension(R.styleable.TView_backgroundShadowDxPress, backgroundShadowDxNormal);
                backgroundShadowDyPress = typedArray.getDimension(R.styleable.TView_backgroundShadowDyPress, backgroundShadowDyNormal);
            }

            //
            backgroundShadowRadiusSelect = typedArray.getDimension(R.styleable.TView_backgroundShadowRadiusSelect, backgroundShadowRadiusNormal);
            if (backgroundShadowRadiusSelect > 0) {
                backgroundShadowColorSelect = typedArray.getColor(R.styleable.TView_backgroundShadowColorSelect, backgroundShadowColorNormal);
                backgroundShadowDxSelect = typedArray.getDimension(R.styleable.TView_backgroundShadowDxSelect, backgroundShadowDxNormal);
                backgroundShadowDySelect = typedArray.getDimension(R.styleable.TView_backgroundShadowDySelect, backgroundShadowDyNormal);
            }

            //
            int srcModeIndex = typedArray.getInt(R.styleable.TView_srcMode, 0);
            if (srcModeIndex >= 0) {
                srcMode = srcModeArray[srcModeIndex];
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
                int srcSelectId = typedArray.getResourceId(R.styleable.TView_srcSelect, -1);
                if (srcSelectId != -1) {
                    srcSelect = BitmapFactory.decodeResource(getResources(), srcSelectId);
                } else {
                    srcSelect = srcNormal;
                }

                srcShadowRadiusNormal = typedArray.getDimension(R.styleable.TView_srcShadowRadiusNormal, 0);
                if (srcShadowRadiusNormal > 0) {
                    srcShadowDxNormal = typedArray.getDimension(R.styleable.TView_srcShadowDxNormal, 0);
                    srcShadowDyNormal = typedArray.getDimension(R.styleable.TView_srcShadowDyNormal, 0);
                }
                //
                srcShadowRadiusPress = typedArray.getDimension(R.styleable.TView_srcShadowRadiusPress, srcShadowRadiusNormal);
                if (srcShadowRadiusPress > 0) {
                    srcShadowDxPress = typedArray.getDimension(R.styleable.TView_srcShadowDxPress, srcShadowDxNormal);
                    srcShadowDyPress = typedArray.getDimension(R.styleable.TView_srcShadowDyPress, srcShadowDyNormal);
                }
                //
                srcShadowRadiusSelect = typedArray.getDimension(R.styleable.TView_srcShadowRadiusSelect, srcShadowRadiusNormal);
                if (srcShadowRadiusSelect > 0) {
                    srcShadowDxSelect = typedArray.getDimension(R.styleable.TView_srcShadowDxSelect, srcShadowDxNormal);
                    srcShadowDySelect = typedArray.getDimension(R.styleable.TView_srcShadowDySelect, srcShadowDyNormal);
                }
            }

            //
            srcAnchorGravityMode = typedArray.getInt(R.styleable.TView_srcAnchorGravityMode, 0);

            //
            int srcAnchorNormalId = typedArray.getResourceId(R.styleable.TView_srcAnchorNormal, -1);
            if (srcAnchorNormalId != -1) {
                srcAnchorNormal = BitmapFactory.decodeResource(getResources(), srcAnchorNormalId);
                srcAnchorWidthNormal = typedArray.getDimension(R.styleable.TView_srcAnchorWidthNormal, 0);
                srcAnchorHeightNormal = typedArray.getDimension(R.styleable.TView_srcAnchorHeightNormal, 0);
                srcAnchorDxNormal = typedArray.getDimension(R.styleable.TView_srcAnchorDxNormal, 0);
                srcAnchorDyNormal = typedArray.getDimension(R.styleable.TView_srcAnchorDyNormal, 0);
            }

            //
            int srcAnchorPressId = typedArray.getResourceId(R.styleable.TView_srcAnchorPress, -1);
            if (srcAnchorPressId != -1) {
                srcAnchorPress = BitmapFactory.decodeResource(getResources(), srcAnchorPressId);
                srcAnchorWidthPress = typedArray.getDimension(R.styleable.TView_srcAnchorWidthPress, srcAnchorWidthNormal);
                srcAnchorHeightPress = typedArray.getDimension(R.styleable.TView_srcAnchorHeightPress, srcAnchorHeightNormal);
                srcAnchorDxPress = typedArray.getDimension(R.styleable.TView_srcAnchorDxPress, srcAnchorDxNormal);
                srcAnchorDyPress = typedArray.getDimension(R.styleable.TView_srcAnchorDyPress, srcAnchorDyNormal);
            } else {
                srcAnchorPress = srcAnchorNormal;
                srcAnchorWidthPress = srcAnchorWidthNormal;
                srcAnchorHeightPress = srcAnchorHeightNormal;
                srcAnchorDxPress = srcAnchorDxNormal;
                srcAnchorDyPress = srcAnchorDyNormal;
            }

            //
            int srcAnchorSelectId = typedArray.getResourceId(R.styleable.TView_srcAnchorSelect, -1);
            if (srcAnchorSelectId != -1) {
                srcAnchorSelect = BitmapFactory.decodeResource(getResources(), srcAnchorSelectId);
                srcAnchorWidthSelect = typedArray.getDimension(R.styleable.TView_srcAnchorWidthSelect, srcAnchorWidthNormal);
                srcAnchorHeightSelect = typedArray.getDimension(R.styleable.TView_srcAnchorHeightSelect, srcAnchorHeightNormal);
                srcAnchorDxSelect = typedArray.getDimension(R.styleable.TView_srcAnchorDxSelect, srcAnchorDxNormal);
                srcAnchorDySelect = typedArray.getDimension(R.styleable.TView_srcAnchorDySelect, srcAnchorDyNormal);
            } else {
                srcAnchorSelect = srcAnchorNormal;
                srcAnchorWidthSelect = srcAnchorWidthNormal;
                srcAnchorHeightSelect = srcAnchorHeightNormal;
                srcAnchorDxSelect = srcAnchorDxNormal;
                srcAnchorDySelect = srcAnchorDyNormal;
            }

            //
            srcLeftWidthNormal = typedArray.getDimension(R.styleable.TView_srcLeftWidthNormal, 0);
            srcLeftHeightNormal = typedArray.getDimension(R.styleable.TView_srcLeftHeightNormal, 0);
            srcLeftPaddingNormal = typedArray.getDimension(R.styleable.TView_srcLeftPaddingNormal, 0);
            srcLeftDxNormal = typedArray.getDimension(R.styleable.TView_srcLeftDxNormal, 0);
            srcLeftDyNormal = typedArray.getDimension(R.styleable.TView_srcLeftDyNormal, 0);

            int srcLeftNormalId = typedArray.getResourceId(R.styleable.TView_srcLeftNormal, -1);
            if (srcLeftNormalId != -1) {
                srcLeftNormal = BitmapFactory.decodeResource(getResources(), srcLeftNormalId);

                if (srcLeftWidthNormal == 0 || srcLeftHeightNormal == 0) {
                    throw new IllegalArgumentException("The content attribute require property named srcLeftWidthNormal and srcLeftHeightNormal");
                }
            }

            //
            srcLeftWidthPress = typedArray.getDimension(R.styleable.TView_srcLeftWidthPress, srcLeftWidthNormal);
            srcLeftHeightPress = typedArray.getDimension(R.styleable.TView_srcLeftHeightPress, srcLeftHeightNormal);
            srcLeftPaddingPress = typedArray.getDimension(R.styleable.TView_srcLeftPaddingPress, srcLeftPaddingNormal);
            srcLeftDxPress = typedArray.getDimension(R.styleable.TView_srcLeftDxPress, srcLeftDxNormal);
            srcLeftDyPress = typedArray.getDimension(R.styleable.TView_srcLeftDyPress, srcLeftDyNormal);

            int srcLeftPressId = typedArray.getResourceId(R.styleable.TView_srcLeftPress, srcLeftNormalId);
            if (srcLeftPressId != -1) {
                srcLeftPress = BitmapFactory.decodeResource(getResources(), srcLeftPressId);

                if (srcLeftWidthPress == 0 || srcLeftHeightPress == 0) {
                    throw new IllegalArgumentException("The content attribute require property named srcLeftWidthPress and srcLeftHeightPress");
                }
            }

            srcLeftWidthSelect = typedArray.getDimension(R.styleable.TView_srcLeftWidthSelect, srcLeftWidthNormal);
            srcLeftHeightSelect = typedArray.getDimension(R.styleable.TView_srcLeftHeightSelect, srcLeftHeightNormal);
            srcLeftPaddingSelect = typedArray.getDimension(R.styleable.TView_srcLeftPaddingSelect, srcLeftPaddingNormal);
            srcLeftDxSelect = typedArray.getDimension(R.styleable.TView_srcLeftDxSelect, srcLeftDxNormal);
            srcLeftDySelect = typedArray.getDimension(R.styleable.TView_srcLeftDySelect, srcLeftDyNormal);

            int srcLeftSelectId = typedArray.getResourceId(R.styleable.TView_srcLeftSelect, srcLeftNormalId);
            if (srcLeftSelectId != -1) {
                srcLeftSelect = BitmapFactory.decodeResource(getResources(), srcLeftSelectId);

                if (srcLeftWidthSelect == 0 || srcLeftHeightSelect == 0) {
                    throw new IllegalArgumentException("The content attribute require property named srcLeftWidthSelect and srcLeftHeightSelect");
                }
            }

            //
            srcRightWidthNormal = typedArray.getDimension(R.styleable.TView_srcRightWidthNormal, 0);
            srcRightHeightNormal = typedArray.getDimension(R.styleable.TView_srcRightHeightNormal, 0);
            srcRightPaddingNormal = typedArray.getDimension(R.styleable.TView_srcRightPaddingNormal, 0);
            srcRightDxNormal = typedArray.getDimension(R.styleable.TView_srcRightDxNormal, 0);
            srcRightDyNormal = typedArray.getDimension(R.styleable.TView_srcRightDyNormal, 0);

            int srcRightNormalId = typedArray.getResourceId(R.styleable.TView_srcRightNormal, -1);
            if (srcRightNormalId != -1) {
                srcRightNormal = BitmapFactory.decodeResource(getResources(), srcRightNormalId);
                if (srcRightWidthNormal == 0 || srcRightHeightNormal == 0) {
                    throw new IllegalArgumentException("The content attribute require property named srcRightWidthNormal and srcRightHeightNormal");
                }
            }

            srcRightWidthPress = typedArray.getDimension(R.styleable.TView_srcRightWidthPress, srcRightWidthNormal);
            srcRightHeightPress = typedArray.getDimension(R.styleable.TView_srcRightHeightPress, srcRightHeightNormal);
            srcRightPaddingPress = typedArray.getDimension(R.styleable.TView_srcRightPaddingPress, srcRightPaddingNormal);
            srcRightDxPress = typedArray.getDimension(R.styleable.TView_srcRightDxPress, srcRightDxNormal);
            srcRightDyPress = typedArray.getDimension(R.styleable.TView_srcRightDyPress, srcRightDyNormal);

            int srcRightPressId = typedArray.getResourceId(R.styleable.TView_srcRightPress, srcRightNormalId);
            if (srcRightPressId != -1) {
                srcRightPress = BitmapFactory.decodeResource(getResources(), srcRightNormalId);


                if (srcRightWidthPress == 0 || srcRightHeightPress == 0) {
                    throw new IllegalArgumentException("The content attribute require property named srcRightWidthPress and srcRightHeightPress");
                }
            }

            srcRightWidthSelect = typedArray.getDimension(R.styleable.TView_srcRightWidthSelect, srcRightWidthNormal);
            srcRightHeightSelect = typedArray.getDimension(R.styleable.TView_srcRightHeightSelect, srcRightHeightNormal);
            srcRightPaddingSelect = typedArray.getDimension(R.styleable.TView_srcRightPaddingSelect, srcRightPaddingNormal);
            srcRightDxSelect = typedArray.getDimension(R.styleable.TView_srcRightDxSelect, srcRightDxNormal);
            srcRightDySelect = typedArray.getDimension(R.styleable.TView_srcRightDySelect, srcRightDyNormal);

            int srcRightSelectId = typedArray.getResourceId(R.styleable.TView_srcRightSelect, srcRightNormalId);
            if (srcRightSelectId != -1) {
                srcRightSelect = BitmapFactory.decodeResource(getResources(), srcRightSelectId);


                if (srcRightWidthSelect == 0 || srcRightHeightSelect == 0) {
                    throw new IllegalArgumentException("The content attribute require property named srcRightWidthSelect and srcRightHeightSelect");
                }
            }

            //
            textMark = typedArray.getBoolean(R.styleable.TView_textMark, false);
            textMarkTouchable = typedArray.getBoolean(R.styleable.TView_textMarkTouchable, false);
            textMarkRadius = typedArray.getDimension(R.styleable.TView_textMarkRadius, 0);
            textMarkColor = typedArray.getColor(R.styleable.TView_textMarkColor, Color.TRANSPARENT);
            textMarkText = typedArray.getString(R.styleable.TView_textMarkText);
            textMarkTextSize = typedArray.getDimension(R.styleable.TView_textMarkTextSize, textSizeDefault);
            textMarkTextColor = typedArray.getColor(R.styleable.TView_textMarkTextColor, textColorDefault);

            //
            contentMark = typedArray.getBoolean(R.styleable.TView_contentMark, false);
            contentMarkTouchable = typedArray.getBoolean(R.styleable.TView_contentMarkTouchable, false);
            contentMarkRadius = typedArray.getDimension(R.styleable.TView_contentMarkRadius, 0);
            contentMarkColor = typedArray.getColor(R.styleable.TView_contentMarkColor, Color.TRANSPARENT);
            contentMarkText = typedArray.getString(R.styleable.TView_contentMarkText);
            contentMarkTextSize = typedArray.getDimension(R.styleable.TView_contentMarkTextSize, textSizeDefault);
            contentMarkTextColor = typedArray.getColor(R.styleable.TView_contentMarkTextColor, textColorDefault);

            //
            strokeWidthNormal = typedArray.getDimension(R.styleable.TView_strokeWidthNormal, 0);
            strokeColorNormal = typedArray.getColor(R.styleable.TView_strokeColorNormal, textColorDefault);
            strokeWidthPress = typedArray.getDimension(R.styleable.TView_strokeWidthPress, strokeWidthNormal);
            strokeColorPress = typedArray.getColor(R.styleable.TView_strokeColorPress, strokeColorNormal);
            strokeWidthSelect = typedArray.getDimension(R.styleable.TView_strokeWidthSelect, strokeWidthNormal);
            strokeColorSelect = typedArray.getColor(R.styleable.TView_strokeColorSelect, strokeColorNormal);

            //
            text = typedArray.getString(R.styleable.TView_text);
            textSize = typedArray.getDimension(R.styleable.TView_textSize, textSizeDefault);

            textColorNormal = typedArray.getColor(R.styleable.TView_textColorNormal, textColorDefault);
            textColorPress = typedArray.getColor(R.styleable.TView_textColorPress, textColorNormal);
            textColorSelect = typedArray.getColor(R.styleable.TView_textColorSelect, textColorNormal);

            textPaddingLeft = typedArray.getDimension(R.styleable.TView_textPaddingLeft, 0);
            textPaddingRight = typedArray.getDimension(R.styleable.TView_textPaddingRight, 0);
            textRowSpaceRatio = typedArray.getFraction(R.styleable.TView_textRowSpaceRatio, 1, 1, 1);

            //
            int textGravityModeIndex = typedArray.getInt(R.styleable.TView_textGravityMode, 0);
            if (textGravityModeIndex >= 0) {
                textGravityMode = textGravityModeArray[textGravityModeIndex];
            }

            // If textAssets is set then textStyle will be replaced!
            textAssets = typedArray.getString(R.styleable.TView_textAssets);
            if (textAssets != null) {
                textStyle = Typeface.createFromAsset(getContext().getAssets(), textAssets);
            } else {
                //
                int textStyleIndex = typedArray.getInt(R.styleable.TView_textStyle, 0);
                if (textStyleIndex >= 0) {
                    textStyle = Typeface.create(Typeface.DEFAULT, textStyleArray[textStyleIndex]);
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
            content = typedArray.getString(R.styleable.TView_content);
            contentSize = typedArray.getDimension(R.styleable.TView_contentSize, textSizeDefault);

            contentColorNormal = typedArray.getColor(R.styleable.TView_contentColorNormal, textColorDefault);
            contentColorPress = typedArray.getColor(R.styleable.TView_contentColorPress, contentColorNormal);
            contentColorSelect = typedArray.getColor(R.styleable.TView_contentColorSelect, contentColorNormal);

            contentPaddingLeft = typedArray.getDimension(R.styleable.TView_contentPaddingLeft, 0);
            contentPaddingRight = typedArray.getDimension(R.styleable.TView_contentPaddingRight, 0);
            contentRowSpaceRatio = typedArray.getFraction(R.styleable.TView_contentRowSpaceRatio, 1, 1, 1);

            //
            int contentGravityModeIndex = typedArray.getInt(R.styleable.TView_contentGravityMode, 0);
            if (contentGravityModeIndex >= 0) {
                contentGravityMode = contentGravityModeArray[contentGravityModeIndex];
            }

            // If contentAssets is set then contentStyle will be replaced!
            contentAssets = typedArray.getString(R.styleable.TView_contentAssets);
            if (contentAssets != null) {
                contentStyle = Typeface.createFromAsset(getContext().getAssets(), contentAssets);
            } else {
                int contentStyleIndex = typedArray.getInt(R.styleable.TView_contentStyle, 0);
                if (contentStyleIndex >= 0) {
                    contentStyle = Typeface.create(Typeface.DEFAULT, contentStyleArray[contentStyleIndex]);
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

            //
            materialTime = typedArray.getInt(R.styleable.TView_materialTime, 0);
            materialMove = typedArray.getBoolean(R.styleable.TView_materialMove, false);

            //
            final String handlerNameTouchUp = typedArray.getString(R.styleable.TView_touchUp);
            if (handlerNameTouchUp != null) {
                setTouchUpListener(new DeclaredTouchUpListener(this, handlerNameTouchUp));
            }
            //The onClick method is the only interface that is passed into View. The other touch methods are TView interfaces.
            final String handlerNameOnClick = typedArray.getString(R.styleable.TView_onClick);
            if (handlerNameOnClick != null) {
                setOnClickListener(new DeclaredOnClickListener(this, handlerNameOnClick));
            }
        }
        typedArray.recycle();
        //
        DeviceTool.initDisplayMetrics();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        touchX = event.getX();
        touchY = event.getY();
        //
        setTouchXY(touchX, touchY);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchDown = true;
                touchMove = false;
                touchUp = false;
                touchCancel = false;
                touchDownEventX = event.getX();
                touchDownEventY = event.getY();

                //
                if (pressMode == NEED) {
                    press = true;
                    if (selectMode == ALWAYS) {
                        setSelect(false);
                    }
                } else if (pressMode == WITHOUT) {
                    press = false;
                    setSelect(true);
                }

                if (!textMarkTouchable) {
                    textMark = false;
                }
                if (!contentMarkTouchable) {
                    contentMark = false;
                }
                if (touchDownListener != null) {
                    touchDownListener.touchDown(this);
                }
                if (associateListener != null) {
                    associateListener.associate(this);
                }
                //
                if (materialTime != 0) {
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
                    materialAnimatorSet.setDuration(materialTime);
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
                }
                //
                if (adjust) {
                    if (touchDownCount >= TOUCH_DOWN_TIMES - 1) {
                        touchDownCount = 0;
                        touchDownTimeEnd = System.currentTimeMillis();
                        if ((touchDownTimeEnd - touchDownTimeStart) <= SHOW_PROPERTY_MAX_TIME_MILLIS) {
                            showProperties(this);
                        }
                    } else {
                        touchDownCount++;
                        if (touchDownCount == 1) {
                            touchDownTimeStart = System.currentTimeMillis();
                        }
                    }
                }
                break;
            //
            case MotionEvent.ACTION_MOVE:
                touchDown = false;
                touchMove = true;
                touchUp = false;
                touchCancel = false;

                //
                if (pressMode == NEED) {
                    press = true;
                } else if (pressMode == WITHOUT) {
                    press = false;
                }

                if (!textMarkTouchable) {
                    textMark = false;
                }
                if (!contentMarkTouchable) {
                    contentMark = false;
                }
                if (touchMoveListener != null) {
                    touchMoveListener.touchMove(this);
                }
                if (associateListener != null) {
                    associateListener.associate(this);
                }
                break;
            case MotionEvent.ACTION_UP:
                touchDown = false;
                touchMove = false;
                touchUp = true;
                touchCancel = false;
                //
                press = false;
                if (selectMode == ALWAYS) {
                    setSelect(true);
                } else if (selectMode == REVERSE) {
                    setSelect(!isSelect());
                }
                if (!textMarkTouchable) {
                    textMark = false;
                }
                if (!contentMarkTouchable) {
                    contentMark = false;
                }
                if (touchUpListener != null) {
                    touchUpListener.touchUp(this);
                }
                if (onClickListener != null) {
                    onClickListener.onClick(this);
                }
                if (associateListener != null) {
                    associateListener.associate(this);
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                touchDown = false;
                touchMove = false;
                touchUp = false;
                touchCancel = true;
                //
                press = false;
                if (selectMode == ALWAYS) {
                    setSelect(true);
                } else if (selectMode == REVERSE) {
                    setSelect(!isSelect());
                }
                if (!textMarkTouchable) {
                    textMark = false;
                }
                if (!contentMarkTouchable) {
                    contentMark = false;
                }
                if (touchCancelListener != null) {
                    touchCancelListener.touchCancel(this);
                }
                if (associateListener != null) {
                    associateListener.associate(this);
                }
                break;
            default:
                break;
        }
        //
        if (touchListener != null) {
            touchListener.touch(this);
        }
        //
        if (isOrigin()) {
            invalidate();
        }
        //
        return dispatch ? super.dispatchTouchEvent(event) : true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (text != null) {
            textMeasureList = measure(widthMeasureSpec, heightMeasureSpec,
                    text, initTextPaint(textSize), textPaddingLeft, textPaddingRight,
                    textRowSpaceRatio);
        } else {
            contentMeasureList = measure(widthMeasureSpec, heightMeasureSpec,
                    content, initTextPaint(contentSize), textPaddingLeft, textPaddingRight,
                    contentRowSpaceRatio);
        }
    }

    protected List<Integer> measure(int widthMeasureSpec, int heightMeasureSpec,
                                    String text, Paint paint, float paddingLeft, float paddingRight, float rowSpaceRatio) {
        List<Integer> measureList = null;
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
        if (specModeWidth == MeasureSpec.AT_MOST && text != null) {//wrap_content
            int requestWidth = (int) (paint.measureText(text) + paddingLeft + paddingRight);
            measuredWidth = Math.min(specSizeWidth, requestWidth);
            //showing the child view can only be the size specified specSizer
        } else if (specModeWidth == MeasureSpec.EXACTLY) {// match_parent
            measuredWidth = specSizeWidth;
            //developers can express their wishes in accordance with the view set to any size, without any restrictions, for example, the item in the listview
        } else if (specModeWidth == MeasureSpec.UNSPECIFIED) {// unspecified
            measuredWidth = specSizeWidth;
        }

        //measuredHeight
        if (specModeHeight == MeasureSpec.AT_MOST && text != null) {// wrap_content
            measureList = createMeasureList(text, paint, measuredWidth, paddingLeft, paddingRight);
            FontMetricsInt fontMetrics = paint.getFontMetricsInt();
            int rowHeight = fontMetrics.descent - fontMetrics.ascent;
            measuredHeight = (int) (rowHeight * rowSpaceRatio * measureList.size());
        } else if (specModeHeight == MeasureSpec.EXACTLY) {// match_parent
            measuredHeight = specSizeHeight;
        } else if (specModeHeight == MeasureSpec.UNSPECIFIED && text != null) {// unspecified
            measureList = createMeasureList(text, paint, measuredWidth, paddingLeft, paddingRight);
            FontMetricsInt fontMetrics = paint.getFontMetricsInt();
            int rowHeight = fontMetrics.descent - fontMetrics.ascent;
            measuredHeight = (int) (rowHeight * rowSpaceRatio * measureList.size());
        }
        setMeasuredDimension(measuredWidth, measuredHeight);
        return measureList;
    }

    protected List<Integer> createMeasureList(String text, Paint paint, float width, float paddingLeft, float paddingRight) {
        List<Integer> measureList = new ArrayList();
        int charatcerLength = text.length();
        float characterWidth = paint.measureText(text);
        float availableWidth = width - paddingLeft - paddingRight;
        //
        if (characterWidth > availableWidth) {
            for (int measureStart = 0, measure = 1; measure <= charatcerLength; measure++) {
                String measureString = text.substring(measureStart, measure);
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
                || srcPress != null || srcSelect != null || srcShadowRadiusNormal > 0 || srcShadowRadiusPress > 0 || srcShadowRadiusSelect > 0
                || backgroundShadowRadiusNormal > 0 || backgroundShadowRadiusPress > 0 || backgroundShadowRadiusSelect > 0 || backgroundAngleNormal != Integer.MAX_VALUE
                || backgroundAnglePress != Integer.MAX_VALUE || backgroundAngleSelect != Integer.MAX_VALUE || srcAnchorNormal != null || srcAnchorPress != null
                || srcAnchorSelect != null
        ) {

            // setShadowLayer() is only supported on text when hardware acceleration is on.
            // Hardware acceleration is on by default when targetSdk=14 or higher.
            // An easy workaround is to put your View in a software layer: myView.setLayerType(View.LAYER_TYPE_SOFTWARE, null).
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        // text
        textDx += width * textFractionDx;
        textDy += height * textFractionDy;

        // textMark
        textMarkDx += width * textMarkFractionDx;
        textMarkDy += height * textMarkFractionDy;

        //
        int srcAnchorWidthRawNormal, srcAnchorHeightRawNormal, srcAnchorWidthRawPress, srcAnchorHeightRawPress, srcAnchorWidthRawSelect, srcAnchorHeightRawSelect;

        if (srcAnchorNormal != null) {
            srcAnchorWidthRawNormal = srcAnchorNormal.getWidth();
            srcAnchorHeightRawNormal = srcAnchorNormal.getHeight();

            matrixAnchorNormal = initMatrix(matrixAnchorNormal, srcAnchorWidthNormal / srcAnchorWidthRawNormal, srcAnchorHeightNormal / srcAnchorHeightRawNormal);
        }

        if (srcAnchorPress != null) {
            srcAnchorWidthRawPress = srcAnchorPress.getWidth();
            srcAnchorHeightRawPress = srcAnchorPress.getHeight();

            matrixAnchorPress = initMatrix(matrixAnchorPress, srcAnchorWidthPress / srcAnchorWidthRawPress, srcAnchorHeightPress / srcAnchorHeightRawPress);
        }

        if (srcAnchorSelect != null) {
            srcAnchorWidthRawSelect = srcAnchorSelect.getWidth();
            srcAnchorHeightRawSelect = srcAnchorSelect.getHeight();

            matrixAnchorSelect = initMatrix(matrixAnchorSelect, srcAnchorWidthSelect / srcAnchorWidthRawSelect, srcAnchorHeightSelect / srcAnchorHeightRawSelect);
        }

        if (layoutListener != null) {
            layoutListener.layout(this);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        initCanvas(canvas);
        if (!origin) {
            return;
        }

        //
        if (backgroundAngleNormal != Integer.MAX_VALUE) {
            backgroundNormalShader = getLinearGradient(width, height, backgroundAngleNormal, backgroundStartNormal, backgroundEndNormal);
        }
        if (backgroundAnglePress != Integer.MAX_VALUE) {
            backgroundPressShader = getLinearGradient(width, height, backgroundAnglePress, backgroundStartPress, backgroundEndPress);
        }
        if (backgroundAngleSelect != Integer.MAX_VALUE) {
            backgroundSelectShader = getLinearGradient(width, height, backgroundAngleSelect, backgroundStartSelect, backgroundEndSelect);
        }

        //
        if (srcNormal != null) {
            srcWidthRawNormal = srcNormal.getWidth();
            srcHeightRawNormal = srcNormal.getHeight();
            switch (srcMode) {
                case FIT_XY:
                    matrixNormal = initMatrix(matrixNormal,
                            (width - srcShadowRadiusNormal * 2f - backgroundShadowRadiusNormal * 2f - backgroundShadowDxNormal * 2f - strokeWidthNormal * 2f) / srcWidthRawNormal,
                            (height - srcShadowRadiusNormal * 2f - backgroundShadowRadiusNormal * 2f - backgroundShadowDyNormal * 2f - strokeWidthNormal * 2f) / srcHeightRawNormal);
                    break;
                case FIT_WIDTH:
                    scale = width * 1f / srcWidthRawNormal;
                    srcWidthScale = srcWidthRawNormal * scale;
                    srcHeightScale = srcHeightRawNormal * scale;
                    dy = srcHeightScale - height;
                    matrixNormal = initMatrix(matrixNormal, scale, scale);
                    matrixNormal.postTranslate(0, dy * -0.5f);
                    break;
                case FIT_HEIGHT:
                    scale = height * 1f / srcHeightRawNormal;
                    srcWidthScale = srcWidthRawNormal * scale;
                    srcHeightScale = srcHeightRawNormal * scale;
                    dx = srcWidthScale - width;
                    matrixNormal = initMatrix(matrixNormal, scale, scale);
                    matrixNormal.postTranslate(dx * -0.5f, 0);
                    break;
                case FIT_CENTER:
                    float AspectRatio = width * 1f / height;
                    float AspectRatioRae = srcWidthRawNormal * 1f / srcHeightRawNormal;
                    boolean useFitWidthMode = (AspectRatio >= AspectRatioRae);
                    //
                    if (useFitWidthMode) {
                        scale = width * 1f / srcWidthRawNormal;
                        srcWidthScale = srcWidthRawNormal * scale;
                        srcHeightScale = srcHeightRawNormal * scale;
                        dy = srcHeightScale - height;
                        matrixNormal = initMatrix(matrixNormal, scale, scale);
                        matrixNormal.postTranslate(0, dy * -0.5f);
                    } else {
                        scale = height * 1f / srcHeightRawNormal;
                        srcWidthScale = srcWidthRawNormal * scale;
                        srcHeightScale = srcHeightRawNormal * scale;
                        dx = srcWidthScale - width;
                        matrixNormal = initMatrix(matrixNormal, scale, scale);
                        matrixNormal.postTranslate(dx * -0.5f, 0);
                    }
            }
        }

        if (srcPress != null) {
            srcWidthRawPress = srcPress.getWidth();
            srcHeightRawPress = srcPress.getHeight();
            switch (srcMode) {
                case FIT_XY:
                    matrixPress = initMatrix(matrixPress,
                            (width - srcShadowRadiusPress * 2f - backgroundShadowRadiusPress * 2f - backgroundShadowDxPress * 2f - strokeWidthPress * 2f) / srcWidthRawPress,
                            (height - srcShadowRadiusPress * 2f - backgroundShadowRadiusPress * 2f - backgroundShadowDyPress * 2f - strokeWidthPress * 2f) / srcHeightRawPress);
                    break;
                case FIT_WIDTH:
                    scale = width * 1f / srcWidthRawPress;
                    srcWidthScale = srcWidthRawPress * scale;
                    srcHeightScale = srcHeightRawPress * scale;
                    dy = srcHeightScale - height;
                    matrixPress = initMatrix(matrixPress, scale, scale);
                    matrixPress.postTranslate(0, dy * -0.5f);
                    break;
                case FIT_HEIGHT:
                    scale = height * 1f / srcHeightRawPress;
                    srcWidthScale = srcWidthRawPress * scale;
                    srcHeightScale = srcHeightRawPress * scale;
                    dx = srcWidthScale - width;
                    matrixPress = initMatrix(matrixPress, scale, scale);
                    matrixPress.postTranslate(dx * -0.5f, 0);
                    break;
                case FIT_CENTER:
                    float AspectRatio = width * 1f / height;
                    float AspectRatioRae = srcWidthRawPress * 1f / srcHeightRawPress;
                    boolean useFitWidthMode = (AspectRatio >= AspectRatioRae);
                    //
                    if (useFitWidthMode) {
                        scale = width * 1f / srcWidthRawPress;
                        srcWidthScale = srcWidthRawPress * scale;
                        srcHeightScale = srcHeightRawPress * scale;
                        dy = srcHeightScale - height;
                        matrixPress = initMatrix(matrixPress, scale, scale);
                        matrixPress.postTranslate(0, dy * -0.5f);
                    } else {
                        scale = height * 1f / srcHeightRawPress;
                        srcWidthScale = srcWidthRawPress * scale;
                        srcHeightScale = srcHeightRawPress * scale;
                        dx = srcWidthScale - width;
                        matrixPress = initMatrix(matrixPress, scale, scale);
                        matrixPress.postTranslate(dx * -0.5f, 0);
                    }
            }
        }

        if (srcSelect != null) {
            srcWidthRawSelect = srcSelect.getWidth();
            srcHeightRawSelect = srcSelect.getHeight();
            switch (srcMode) {
                case FIT_XY:
                    matrixSelect = initMatrix(matrixSelect,
                            (width - srcShadowRadiusSelect * 2f - backgroundShadowRadiusSelect * 2f - backgroundShadowDxSelect * 2f - strokeWidthSelect * 2f) / srcWidthRawSelect,
                            (height - srcShadowRadiusSelect * 2f - backgroundShadowRadiusSelect * 2f - backgroundShadowDySelect * 2f - strokeWidthSelect * 2f) / srcHeightRawSelect);
                    break;
                case FIT_WIDTH:
                    scale = width * 1f / srcWidthRawSelect;
                    srcWidthScale = srcWidthRawSelect * scale;
                    srcHeightScale = srcHeightRawSelect * scale;
                    dy = srcHeightScale - height;
                    matrixSelect = initMatrix(matrixSelect, scale, scale);
                    matrixPress.postTranslate(0, dy * -0.5f);
                    break;
                case FIT_HEIGHT:
                    scale = height * 1f / srcHeightRawSelect;
                    srcWidthScale = srcWidthRawSelect * scale;
                    srcHeightScale = srcHeightRawSelect * scale;
                    dx = srcWidthScale - width;
                    matrixSelect = initMatrix(matrixSelect, scale, scale);
                    matrixSelect.postTranslate(dx * -0.5f, 0);
                    break;
                case FIT_CENTER:
                    float AspectRatio = width * 1f / height;
                    float AspectRatioRae = srcWidthRawSelect * 1f / srcHeightRawSelect;
                    boolean useFitWidthMode = (AspectRatio >= AspectRatioRae);
                    //
                    if (useFitWidthMode) {
                        scale = width * 1f / srcWidthRawSelect;
                        srcWidthScale = srcWidthRawSelect * scale;
                        srcHeightScale = srcHeightRawSelect * scale;
                        dy = srcHeightScale - height;
                        matrixSelect = initMatrix(matrixSelect, scale, scale);
                        matrixPress.postTranslate(0, dy * -0.5f);
                    } else {
                        scale = height * 1f / srcHeightRawSelect;
                        srcWidthScale = srcWidthRawSelect * scale;
                        srcHeightScale = srcHeightRawSelect * scale;
                        dx = srcWidthScale - width;
                        matrixSelect = initMatrix(matrixSelect, scale, scale);
                        matrixSelect.postTranslate(dx * -0.5f, 0);
                    }
            }
        }

        //
        if (srcLeftNormal != null) {
            int srcLeftWidthRawNormal = srcLeftNormal.getWidth();
            int srcLeftHeightRawNormal = srcLeftNormal.getHeight();
            matrixSrcLeftNormal = initMatrix(matrixSrcLeftNormal, srcLeftWidthNormal / srcLeftWidthRawNormal, srcLeftHeightNormal / srcLeftHeightRawNormal);
        }

        if (srcLeftPress != null) {
            int srcLeftWidthRawPress = srcLeftPress.getWidth();
            int srcLeftHeightRawPress = srcLeftPress.getHeight();
            matrixSrcLeftPress = initMatrix(matrixSrcLeftPress, srcLeftWidthPress / srcLeftWidthRawPress, srcLeftHeightPress / srcLeftHeightRawPress);
        }

        if (srcLeftSelect != null) {
            int srcLeftWidthRawSelect = srcLeftSelect.getWidth();
            int srcLeftHeightRawSelect = srcLeftSelect.getHeight();
            matrixSrcLeftSelect = initMatrix(matrixSrcLeftSelect, srcLeftWidthSelect / srcLeftWidthRawSelect, srcLeftHeightSelect / srcLeftHeightRawSelect);
        }

        //
        if (srcRightNormal != null) {
            int srcRightWidthRawNormal = srcRightNormal.getWidth();
            int srcRightHeightRawNormal = srcRightNormal.getHeight();
            matrixSrcRightNormal = initMatrix(matrixSrcRightNormal, srcRightWidthNormal / srcRightWidthRawNormal, srcRightHeightNormal / srcRightHeightRawNormal);
        }

        if (srcRightPress != null) {
            int srcRightWidthRawPress = srcRightPress.getWidth();
            int srcRightHeightRawPress = srcRightPress.getHeight();
            matrixSrcRightPress = initMatrix(matrixSrcRightPress, srcRightWidthPress / srcRightWidthRawPress, srcRightHeightNormal / srcRightHeightRawPress);
        }

        if (srcRightSelect != null) {
            int srcRightWidthRawSelect = srcRightSelect.getWidth();
            int srcRightHeighRawSelect = srcRightSelect.getHeight();
            matrixSrcRightSelect = initMatrix(matrixSrcRightSelect, srcRightWidthSelect / srcRightWidthRawSelect, srcRightHeightSelect / srcRightHeighRawSelect);
        }

        //
        boolean needSaveLayer = (srcNormal != null || srcPress != null || srcSelect != null);
        if (needSaveLayer) {
            // draw the src/dst example into our offscreen bitmap
            layer = canvas.saveLayer(0, 0, width, height, null);
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
        // dst, note that when srcShadowRadiusSelect > 0, the parent background color must take incoming as backgroundNormal!
        classic = (radius == radiusTopLeft && radiusTopLeft == radiusBottomLeft && radiusBottomLeft == radiusTopRight && radiusTopRight == radiusBottomRight);
        if (classic) {
            if (materialPlay) {
                drawRectClassic(
                        canvas,
                        backgroundShadowRadiusNormal + backgroundShadowDxNormal,
                        backgroundShadowRadiusNormal + backgroundShadowDyNormal,
                        width - backgroundShadowRadiusNormal - backgroundShadowDxNormal,
                        height - backgroundShadowRadiusNormal - backgroundShadowDyNormal,
                        backgroundNormal,
                        backgroundNormalShader,
                        backgroundShadowRadiusNormal,
                        backgroundShadowColorNormal,
                        backgroundShadowDxNormal,
                        backgroundShadowDyNormal,
                        strokeWidthNormal,
                        radius);
                canvas.drawCircle(touchDownEventX, touchDownEventY, materialRadius, initPaint(backgroundPress));
            } else {
                drawRectClassic(
                        canvas,
                        backgroundShadowRadiusNormal + backgroundShadowDxNormal,
                        backgroundShadowRadiusNormal + backgroundShadowDyNormal,
                        width - backgroundShadowRadiusNormal - backgroundShadowDxNormal,
                        height - backgroundShadowRadiusNormal - backgroundShadowDyNormal,
                        select ? backgroundSelect : press ? backgroundPress : backgroundNormal,
                        select ? backgroundSelectShader : press ? backgroundPressShader : backgroundNormalShader,
                        select ? backgroundShadowRadiusSelect : press ? backgroundShadowRadiusPress : backgroundShadowRadiusNormal,
                        select ? backgroundShadowColorSelect : press ? backgroundShadowColorPress : backgroundShadowColorNormal,
                        select ? backgroundShadowDxSelect : press ? backgroundShadowDxPress : backgroundShadowDxNormal,
                        select ? backgroundShadowDySelect : press ? backgroundShadowDyPress : backgroundShadowDyNormal,
                        select ? strokeWidthSelect : press ? strokeWidthPress : strokeWidthNormal,
                        radius);
            }
        } else {
            // draw MaterialDesign Effect
            if (materialPlay) {
                drawRectCustom(
                        canvas,
                        backgroundShadowRadiusNormal + backgroundShadowDxNormal,
                        backgroundShadowRadiusNormal + backgroundShadowDyNormal,
                        width - backgroundShadowRadiusNormal - backgroundShadowDxNormal,
                        height - backgroundShadowRadiusNormal - backgroundShadowDyNormal,
                        backgroundNormal,
                        backgroundNormalShader,
                        backgroundShadowRadiusNormal,
                        backgroundShadowColorNormal,
                        backgroundShadowDxNormal,
                        backgroundShadowDyNormal,
                        strokeWidthNormal,
                        radiusTopLeft,
                        radiusBottomLeft,
                        radiusTopRight,
                        radiusBottomRight);
                canvas.drawCircle(touchDownEventX, touchDownEventY, materialRadius, initPaint(backgroundPress));
            } else {
                drawRectCustom(
                        canvas,
                        backgroundShadowRadiusNormal + backgroundShadowDxNormal,
                        backgroundShadowRadiusNormal + backgroundShadowDyNormal,
                        width - backgroundShadowRadiusNormal - backgroundShadowDxNormal,
                        height - backgroundShadowRadiusNormal - backgroundShadowDyNormal,
                        select ? backgroundSelect : press ? backgroundPress : backgroundNormal,
                        select ? backgroundSelectShader : press ? backgroundPressShader : backgroundNormalShader,
                        select ? backgroundShadowRadiusSelect : press ? backgroundShadowRadiusPress : backgroundShadowRadiusNormal,
                        select ? backgroundShadowColorSelect : press ? backgroundShadowColorPress : backgroundShadowColorNormal,
                        select ? backgroundShadowDxSelect : press ? backgroundShadowDxPress : backgroundShadowDxNormal,
                        select ? backgroundShadowDySelect : press ? backgroundShadowDyPress : backgroundShadowDyNormal,
                        select ? strokeWidthSelect : press ? strokeWidthPress : strokeWidthNormal,
                        radiusTopLeft,
                        radiusBottomLeft,
                        radiusTopRight,
                        radiusBottomRight);
            }
        }

        // draw Xfermode SRC
        if (needSaveLayer) {
            paint.setXfermode(porterDuffXferMode);
            // If they are offset backgroundShadow, mobile, is to draw on the background shadow,
            // without moving the bigger picture and the need to set the width and height
            canvas.translate(
                    select ?
                            backgroundShadowDxSelect * 2f + srcShadowRadiusSelect - srcShadowDxSelect + strokeWidthSelect :
                            press ?
                                    backgroundShadowDxPress * 2f + srcShadowRadiusPress - srcShadowDxPress + strokeWidthPress :
                                    backgroundShadowDxNormal * 2f + srcShadowRadiusNormal - srcShadowDxNormal + +strokeWidthNormal,
                    select ?
                            backgroundShadowDySelect * 2f + srcShadowRadiusSelect - srcShadowDySelect + strokeWidthSelect :
                            press ?
                                    backgroundShadowDyPress * 2f + srcShadowRadiusPress - srcShadowDyPress + strokeWidthPress :
                                    backgroundShadowDyNormal * 2f + srcShadowRadiusNormal - srcShadowDyNormal + strokeWidthPress);

            canvas.drawBitmap(
                    select ? srcSelect : press ? srcPress : srcNormal,
                    select ? matrixSelect : press ? matrixPress : matrixNormal,
                    initPaint(
                            paint,
                            select ? srcShadowRadiusSelect : press ? srcShadowRadiusPress : srcShadowRadiusNormal,
                            select ? srcShadowDxSelect : press ? srcShadowDxPress : srcShadowDxNormal,
                            select ? srcShadowDySelect : press ? srcShadowDyPress : srcShadowDyNormal));

            canvas.translate(select ?
                            -backgroundShadowDxSelect * 2f - srcShadowRadiusSelect + srcShadowDxSelect - strokeWidthSelect :
                            press ?
                                    -backgroundShadowDxPress * 2f - srcShadowRadiusPress + srcShadowDxPress - strokeWidthPress :
                                    -backgroundShadowDxNormal * 2f - srcShadowRadiusNormal + srcShadowDxNormal - strokeWidthNormal,
                    select ?
                            -backgroundShadowDySelect * 2f - srcShadowRadiusSelect + srcShadowDySelect - strokeWidthSelect :
                            press ?
                                    -backgroundShadowDyPress * 2f - srcShadowRadiusPress + srcShadowDyPress - strokeWidthPress :
                                    -backgroundShadowDyNormal * 2f - srcShadowRadiusNormal + srcShadowDyNormal - strokeWidthNormal);

            paint.setXfermode(null);
            // Uncomment will cause a null pointer with paint in xml preview canvas.restoreToCount(layer);
        }

        if (strokeWidthNormal > 0) {
            if (classic) {
                drawRectClassicStroke(
                        canvas,
                        backgroundShadowRadiusNormal + backgroundShadowDxNormal,
                        backgroundShadowRadiusNormal + backgroundShadowDyNormal,
                        width - backgroundShadowRadiusNormal - backgroundShadowDxNormal,
                        height - backgroundShadowRadiusNormal - backgroundShadowDyNormal,
                        select ? strokeWidthSelect : press ? strokeWidthPress : strokeWidthNormal,
                        select ? strokeColorSelect : press ? strokeColorPress : strokeColorNormal,
                        radius);
            } else {
                drawRectCustomStroke(
                        canvas,
                        backgroundShadowRadiusNormal + backgroundShadowDxNormal,
                        backgroundShadowRadiusNormal + backgroundShadowDyNormal,
                        width - backgroundShadowRadiusNormal - backgroundShadowDxNormal,
                        height - backgroundShadowRadiusNormal - backgroundShadowDyNormal,
                        select ? strokeWidthSelect : press ? strokeWidthPress : strokeWidthNormal,
                        select ? strokeColorSelect : press ? strokeColorPress : strokeColorNormal,
                        radiusTopLeft,
                        radiusBottomLeft,
                        radiusTopRight,
                        radiusBottomRight);
            }
        }

        // draw anchor
        if (select && srcAnchorSelect != null) {
            drawAnchor(canvas, initPaint(), srcAnchorSelect, matrixAnchorSelect, width, height, srcAnchorGravityMode, srcAnchorWidthSelect, srcAnchorHeightSelect, srcAnchorDxSelect, srcAnchorDySelect);
        } else if (press && srcAnchorPress != null) {
            drawAnchor(canvas, initPaint(), srcAnchorPress, matrixAnchorPress, width, height, srcAnchorGravityMode, srcAnchorWidthPress, srcAnchorHeightPress, srcAnchorDxPress, srcAnchorDyPress);
        } else if (srcAnchorNormal != null) {
            drawAnchor(canvas, initPaint(), srcAnchorNormal, matrixAnchorNormal, width, height, srcAnchorGravityMode, srcAnchorWidthNormal, srcAnchorHeightNormal, srcAnchorDxNormal, srcAnchorDyNormal);
        }

        // draw text
        if (text != null) {
            float f[] = drawText(
                    canvas,
                    text,
                    width,
                    (width >> 1) + textDx + srcLeftWidthNormal * 0.5f + srcLeftPaddingNormal * 0.5f - srcRightWidthNormal * 0.5f - srcRightPaddingNormal * 0.5f,
                    (height >> 1) + textDy,
                    textPaddingLeft + srcLeftWidthNormal,
                    textPaddingRight + srcRightWidthNormal,
                    initTextPaint(Paint.Style.FILL,
                            materialPlay ? textColorPress : select ? textColorSelect : press ? textColorPress : textColorNormal, textSize,
                            textShadowRadius, textShadowColor, textShadowDx, textShadowDy, textStyle, Paint.Align.CENTER),
                    textGravityMode,
                    textRowSpaceRatio,
                    textMeasureList);

            textDrawThick = f[0];
            textEndOffsetCenterX = f[1];
            textEndOffsetCenterY = f[2];
        }

        // draw content
        if (content != null) {
            //Conversion of style into textGravityMode to use drawText method!
            int textGravityModeFromContent = contentGravityModeArray[contentGravityMode];

            float f[] = drawText(
                    canvas,
                    content,
                    width,
                    (width >> 1) + contentDx + srcLeftWidthNormal * 0.5f + srcLeftPaddingNormal * 0.5f - srcRightWidthNormal * 0.5f - srcRightPaddingNormal * 0.5f,
                    (height >> 1) + contentDy,
                    contentPaddingLeft + srcLeftWidthNormal,
                    contentPaddingRight + srcRightWidthNormal,
                    initTextPaint(Paint.Style.FILL,
                            materialPlay ? contentColorPress : select ? contentColorSelect : press ? contentColorPress : contentColorNormal, contentSize,
                            contentShadowRadius, contentShadowColor, contentShadowDx, contentShadowDy, contentStyle, Paint.Align.CENTER),
                    textGravityModeFromContent,
                    contentRowSpaceRatio,
                    contentMeasureList);

            contentDrawThick = f[0];
            contentEndOffsetCenterX = f[1];
            contentEndOffsetCenterY = f[2];
        }

        // draw srcLeft,the draw position is half of the width minus the srcLeftPaddingNormal and textActualDrawThick*0.5f
        if (isSelect() && srcLeftSelect != null) {
            float dx = (width >> 1) - srcLeftWidthSelect * 0.5f - textDrawThick * 0.5f - srcLeftPaddingSelect * 0.5f + srcLeftDxSelect;
            float dy = (height >> 1) - srcLeftHeightSelect * 0.5f + srcLeftDySelect;
            canvas.translate(dx, dy);
            canvas.drawBitmap(srcLeftSelect, matrixSrcLeftSelect, paint);
            canvas.translate(-dx, -dy);
        } else if (isPress() && srcLeftPress != null) {
            float dx = (width >> 1) - srcLeftWidthPress * 0.5f - textDrawThick * 0.5f - srcLeftPaddingPress * 0.5f + srcLeftDxPress;
            float dy = (height >> 1) - srcLeftHeightPress * 0.5f + srcLeftDyPress;
            canvas.translate(dx, dy);
            canvas.drawBitmap(srcLeftNormal, matrixSrcLeftPress, paint);
            canvas.translate(-dx, -dy);
        } else if (srcLeftNormal != null) {
            float dx = (width >> 1) - srcLeftWidthNormal * 0.5f - textDrawThick * 0.5f - srcLeftPaddingNormal * 0.5f + srcLeftDxNormal;
            float dy = (height >> 1) - srcLeftHeightNormal * 0.5f + srcLeftDyNormal;
            canvas.translate(dx, dy);
            canvas.drawBitmap(srcLeftNormal, matrixSrcLeftNormal, paint);
            canvas.translate(-dx, -dy);
        }

        //
        if (isSelect() && srcRightSelect != null) {
            float dx = (width >> 1) - srcRightWidthSelect * 0.5f + textDrawThick * 0.5f + srcRightPaddingSelect * 0.5f + srcRightDxSelect;
            float dy = (height >> 1) - srcRightHeightSelect * 0.5f + srcRightDySelect;
            canvas.translate(dx, dy);
            canvas.drawBitmap(srcRightSelect, matrixSrcRightSelect, paint);
            canvas.translate(-dx, -dy);
        } else if (isPress() && srcRightPress != null) {
            float dx = (width >> 1) - srcRightWidthPress * 0.5f + textDrawThick * 0.5f + srcRightPaddingPress * 0.5f + srcRightDxPress;
            float dy = (height >> 1) - srcRightHeightPress * 0.5f + srcRightDyPress;
            canvas.translate(dx, dy);
            canvas.drawBitmap(srcRightNormal, matrixSrcRightPress, paint);
            canvas.translate(-dx, -dy);
        } else if (srcRightNormal != null) {
            float dx = (width >> 1) - srcRightWidthNormal * 0.5f + textDrawThick * 0.5f + srcRightPaddingNormal * 0.5f + srcRightDxNormal;
            float dy = (height >> 1) - srcRightHeightNormal * 0.5f + srcRightDyNormal;
            canvas.translate(dx, dy);
            canvas.drawBitmap(srcRightNormal, matrixSrcRightNormal, paint);
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
                    textMarkText,
                    textMarkTextColor,
                    textMarkTextSize,
                    textMarkTextMeasureList);
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
                    contentMarkText,
                    contentMarkTextColor,
                    contentMarkTextSize,
                    contentMarkTextMeasureList);
        }

        // draw foreground
        if (select && foregroundSelect != Color.TRANSPARENT) {
            if (classic) {
                drawRectClassic(canvas, width, height, foregroundSelect, radius);
            } else {
                drawRectCustom(canvas, width, height, foregroundSelect, 0, Color.TRANSPARENT, radiusTopLeft, radiusBottomLeft, radiusTopRight, radiusBottomRight);
            }
        } else if (press && foregroundPress != Color.TRANSPARENT) {
            if (classic) {
                drawRectClassic(canvas, width, height, foregroundPress, radius);
            } else {
                drawRectCustom(canvas, width, height, foregroundPress, 0, Color.TRANSPARENT, radiusTopLeft, radiusBottomLeft, radiusTopRight, radiusBottomRight);
            }
        } else if (foregroundNormal != Color.TRANSPARENT) {
            if (classic) {
                drawRectClassic(canvas, width, height, foregroundNormal, radius);
            } else {
                drawRectCustom(canvas, width, height, foregroundNormal, 0, Color.TRANSPARENT, radiusTopLeft, radiusBottomLeft, radiusTopRight, radiusBottomRight);
            }
        }
        if (drawListener != null) {
            drawListener.draw(this);
        }
    }
}