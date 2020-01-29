package com.tuna;

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
import android.graphics.Movie;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.Paint.Style;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.Path.Direction;
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

import com.tool.DeviceTool;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.tool.BitmapTool.bitmapMaxSize;
import static com.tool.BitmapTool.computeSampleSize;
import static com.tool.BitmapTool.tunaGraphicsMap;
import static com.tool.DeviceTool.applyDimension;
import static com.tool.DeviceTool.convertToPX;
import static com.tool.DeviceTool.getViewDisplayMetrics;
import static com.tool.ViewTool.getLinearGradient;
import static com.tool.ViewTool.setViewMargins;

/**
 * @author Tunasashimi
 * @date 11/4/15 17:25
 * @Copyright 2015 TunaSashimi. All rights reserved.
 * @Description
 */
public class TView extends View {

    //
    public Bitmap decodeBitmapResource(int id) {
        return decodeBitmapResource(id, 1);
    }

    //
    public Bitmap decodeBitmapResource(int id, int inSampleSize) {
        String stringId = String.valueOf(id);
        if (tunaGraphicsMap.containsKey(stringId)) {
            Object object = tunaGraphicsMap.get(stringId);
            if (object != null && object instanceof Bitmap) {
                return (Bitmap) object;
            }
        }
        Bitmap bitmap;
        if (inSampleSize > 1) {
            BitmapFactory.Options bitmapFactoryOptions = new BitmapFactory.Options();
            bitmapFactoryOptions.inSampleSize = inSampleSize;
            bitmap = BitmapFactory.decodeResource(getResources(), id, bitmapFactoryOptions);
        } else {
            bitmap = BitmapFactory.decodeResource(getResources(), id);
        }
        tunaGraphicsMap.put(stringId, bitmap);
        return bitmap;
    }


    //
    public Movie decodeGifResource(int id) {
        String stringId = String.valueOf(id);
        if (tunaGraphicsMap.containsKey(stringId)) {
            Object object = tunaGraphicsMap.get(stringId);
            if (object != null && object instanceof Movie) {
                return (Movie) object;
            }
        }
        return Movie.decodeStream(getResources().openRawResource(id));
    }

    //
    public Object decodeGraphicsResource(int id) {
        return decodeGraphicsResource(id, 1);
    }

    //
    public Object decodeGraphicsResource(int id, int inSampleSize) {
        String stringId = String.valueOf(id);
        if (tunaGraphicsMap.containsKey(stringId)) {
            Object object = tunaGraphicsMap.get(stringId);
            if (object != null) {
                return object;
            }
        }
        Movie movie = decodeGifResource(id);
        if (movie != null) {
            return movie;
        } else {
            return decodeBitmapResource(id, inSampleSize);
        }
    }

    //
    public Bitmap decodeBitmapFile(String path) {
        return decodeBitmapFile(path, 0, 0);
    }

    //
    public Bitmap decodeBitmapFile(String path, int reqWidth, int reqHeight) {
        if (tunaGraphicsMap.containsKey(path)) {
            Object object = tunaGraphicsMap.get(path);
            if (object != null && object instanceof Bitmap) {
                return (Bitmap) object;
            }
        }
        Bitmap bitmap;
        if (reqWidth == 0 || reqHeight == 0) {

            //Without image parameters to avoid OOM situation arising
            BitmapFactory.Options bitmapFactoryOptions = new BitmapFactory.Options();
            bitmapFactoryOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, bitmapFactoryOptions);

            int bitmapSize = bitmapFactoryOptions.outWidth * bitmapFactoryOptions.outHeight;
            if (bitmapSize > bitmapMaxSize) {
                bitmapFactoryOptions.inSampleSize = (int) Math.ceil(bitmapSize / bitmapMaxSize);
                bitmapFactoryOptions.inJustDecodeBounds = false;
                bitmap = BitmapFactory.decodeFile(path, bitmapFactoryOptions);
            } else {
                bitmap = BitmapFactory.decodeFile(path);
            }
        } else {
            BitmapFactory.Options bitmapFactoryOptions = new BitmapFactory.Options();

            bitmapFactoryOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, bitmapFactoryOptions);
            bitmapFactoryOptions.inSampleSize = computeSampleSize(bitmapFactoryOptions, -1, reqWidth * reqHeight);
            bitmapFactoryOptions.inJustDecodeBounds = false;
            bitmap = BitmapFactory.decodeFile(path, bitmapFactoryOptions);
        }
        tunaGraphicsMap.put(path, bitmap);
        return bitmap;
    }

    //
    public Movie decodeGifFile(String path) {
        if (tunaGraphicsMap.containsKey(path)) {
            Object object = tunaGraphicsMap.get(path);
            if (object != null && object instanceof Movie) {
                return (Movie) object;
            }
        }
        return Movie.decodeFile(path);
    }

    //
    public Object decodeGraphicsFile(String path) {
        if (tunaGraphicsMap.containsKey(path)) {
            Object object = tunaGraphicsMap.get(path);
            if (object != null) {
                return object;
            }
        }
        Movie movie = Movie.decodeFile(path);
        if (movie != null) {
            return movie;
        } else {
            return decodeBitmapFile(path);
        }
    }

    //
    public Object decodeGraphicsFile(String path, int reqWidth, int reqHeight) {
        if (tunaGraphicsMap.containsKey(path)) {
            Object object = tunaGraphicsMap.get(path);
            if (object != null) {
                return object;
            }
        }

        Movie movie = Movie.decodeFile(path);
        if (movie != null) {
            return movie;
        } else {
            return decodeBitmapFile(path, reqWidth, reqHeight);
        }
    }



    public Bitmap createImageThumbnail(String filePath) {
        Bitmap bitmap = null;
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, opts);

        opts.inSampleSize = computeSampleSize(opts, -1, 128 * 128);
        opts.inJustDecodeBounds = false;

        try {
            bitmap = BitmapFactory.decodeFile(filePath, opts);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * The following fields and methods of the parent class and subclass can always use
     */

    // as the mark of tunaView species
    protected String tunaTag;

    // the width and height of the tunaView(put together to save the number of rows)
    protected int tunaWidth, tunaHeight;

    //
    protected int tunaLayer;
    protected int tunaTotal;

    protected Bitmap tunaSrcBitmap;


    protected float tunaSrcWidthScale, tunaSrcHeightScale;

    protected float tunaCenterX, tunaCenterY;
    protected float tunaDx, tunaDy;
    protected float tunaScale, tunaScaleSx, tunaScaleSy;

    protected float tunaPercent;
    protected float tunaSurplus, tunaShare;


    protected float[] tunaFloatArray;
    protected String[] tunaStringArray;

    protected Paint tunaPaint;

    public Paint getTunaPaint() {
        return tunaPaint;
    }

    public void setTunaPaint(Paint tunaPaint) {
        this.tunaPaint = tunaPaint;
    }

    // 0
    protected Paint initTunaPaint() {
        if (tunaPaint == null) {
            return tunaPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        } else {
            tunaPaint.reset();
            tunaPaint.clearShadowLayer();
            tunaPaint.setAntiAlias(true);
        }
        return tunaPaint;
    }

    // 1
    protected Paint initTunaPaint(int colorValue) {
        return initTunaPaint(Style.FILL, 0, colorValue, null, 0, Color.TRANSPARENT, 0, 0, -1);
    }

    // 2
    protected Paint initTunaPaint(Style style, int colorValue) {
        return initTunaPaint(style, 0, colorValue, null, 0, Color.TRANSPARENT, 0, 0, -1);
    }

    // 2
    protected Paint initTunaPaint(Style style, Shader shader) {
        return initTunaPaint(style, 0, Color.TRANSPARENT, shader, 0, Color.TRANSPARENT, 0, 0, -1);
    }

    // 3
    protected Paint initTunaPaint(Style style, int colorValue, float strokeWidth) {
        return initTunaPaint(style, strokeWidth, colorValue, null, 0, Color.TRANSPARENT, 0, 0, -1);
    }

    // 3
    protected Paint initTunaPaint(Style style, int colorValue, Shader shader) {
        return initTunaPaint(style, 0, colorValue, shader, 0, Color.TRANSPARENT, 0, 0, -1);
    }

    // 3
    protected Paint initTunaPaint(Style style, Shader shader, int alpha) {
        return initTunaPaint(style, 0, Color.TRANSPARENT, shader, 0, Color.TRANSPARENT, 0, 0, alpha);
    }

    // 4
    protected Paint initTunaPaint(Style style, int colorValue, Shader shader, int alpha) {
        return initTunaPaint(style, 0, colorValue, shader, 0, Color.TRANSPARENT, 0, 0, alpha);
    }

    // 4
    protected Paint initTunaPaint(Style style, int colorValue, float strokeWidth, int alpha) {
        return initTunaPaint(style, strokeWidth, colorValue, null, 0, Color.TRANSPARENT, 0, 0, alpha);
    }

    // 6
    protected Paint initTunaPaint(Style style, Shader shader, float shadowRadius, int shadowColor, float shadowDx, float shadowDy) {
        return initTunaPaint(style, 0, Color.TRANSPARENT, shader, shadowRadius, shadowColor, shadowDx, shadowDy, -1);
    }

    // 6
    protected Paint initTunaPaint(Style style, int colorValue, float shadowRadius, int shadowColor, float shadowDx, float shadowDy) {
        return initTunaPaint(style, 0, colorValue, null, shadowRadius, shadowColor, shadowDx, shadowDy, -1);
    }

    // 9
    protected Paint initTunaPaint(Style style, float strokeWidth, int colorValue, Shader shader, float shadowRadius, int shadowColor, float shadowDx, float shadowDy, int alpha) {
        //
        initTunaPaint();
        //
        if (style != null) {
            tunaPaint.setStyle(style);
        }
        if (strokeWidth != 0) {
            tunaPaint.setStrokeWidth(strokeWidth);
        }

        // When the shadow color can not be set to transparent, but can not set
        if (shader == null) {
            tunaPaint.setColor(colorValue);
        } else {
            tunaPaint.setShader(shader);
        }

        if (shadowRadius != 0) {
            tunaPaint.setShadowLayer(shadowRadius, shadowDx, shadowDy, shadowColor);
        }
        if (alpha != -1) {
            tunaPaint.setAlpha(alpha);
        }
        return tunaPaint;
    }

    // 1
    protected Paint initTunaTextPaint(float textSize) {
        return initTunaTextPaint(null, Color.WHITE, textSize, 0, Color.TRANSPARENT, 0, 0, null, null);
    }

    // 2
    protected Paint initTunaTextPaint(int textColor, float textSize) {
        return initTunaTextPaint(null, textColor, textSize, 0, Color.TRANSPARENT, 0, 0, null, null);
    }

    // 3
    protected Paint initTunaTextPaint(int textColor, float textSize, Align align) {
        return initTunaTextPaint(null, textColor, textSize, 0, Color.TRANSPARENT, 0, 0, null, align);
    }

    // 4
    protected Paint initTunaTextPaint(Style style, int textColor, float textSize, Align align) {
        return initTunaTextPaint(style, textColor, textSize, 0, Color.TRANSPARENT, 0, 0, null, align);
    }

    // 5
    protected Paint initTunaTextPaint(Style style, int textColor, float textSize, Typeface typeFace, Align align) {
        return initTunaTextPaint(style, textColor, textSize, 0, Color.TRANSPARENT, 0, 0, typeFace, align);
    }

    // 8
    protected Paint initTunaTextPaint(Style style, int colorValue, float textSize, float shadowRadius, int shadowColor, float shadowDx, float shadowDy, Typeface typeFace, Align align) {
        //
        initTunaPaint();
        //
        if (style != null) {
            tunaPaint.setStyle(style);
        }

        tunaPaint.setColor(colorValue);

        if (textSize != 0) {
            tunaPaint.setTextSize(textSize);
        }
        if (shadowRadius != 0) {
            tunaPaint.setShadowLayer(shadowRadius, shadowDx, shadowDy, shadowColor);
        }
        if (align != null) {
            tunaPaint.setTextAlign(align);
        }

        if (typeFace != null) {
            tunaPaint.setTypeface(typeFace);
        }

        return tunaPaint;
    }

    // 4
    protected Paint initTunaPaint(Paint paint, float shadowRadius, float shadowDx, float shadowDy) {
        paint.clearShadowLayer();
        if (shadowRadius > 0) {
            paint.setShadowLayer(shadowRadius, shadowDx, shadowDy, Color.WHITE);
        }
        return paint;
    }

    //
    protected Path tunaPath;

    public Path getTunaPath() {
        return tunaPath;
    }

    public void setTunaPath(Path tunaPath) {
        this.tunaPath = tunaPath;
    }

    protected Path initTunaPath() {
        if (tunaPath == null) {
            tunaPath = new Path();
        } else {
            tunaPath.reset();
        }
        return tunaPath;
    }

    protected Path initTunaPathMoveTo(float x, float y) {
        if (tunaPath == null) {
            tunaPath = new Path();
        } else {
            tunaPath.reset();
        }
        tunaPath.moveTo(x, y);
        return tunaPath;
    }

    protected Path initTunaPathLineTo(float x, float y) {
        if (tunaPath == null) {
            tunaPath = new Path();
        } else {
            tunaPath.reset();
        }
        tunaPath.lineTo(x, y);
        return tunaPath;
    }

    protected Path initTunaPathArc(RectF oval, float startAngle, float sweepAngle) {
        if (tunaPath == null) {
            tunaPath = new Path();
        } else {
            tunaPath.reset();
        }
        tunaPath.addArc(oval, startAngle, sweepAngle);
        return tunaPath;
    }

    protected Path initTunaPathRoundRect(RectF rect, float[] radii, Direction dir) {
        if (tunaPath == null) {
            tunaPath = new Path();
        } else {
            tunaPath.reset();
        }
        // This setting will cause the following message appears reading xml
        // The graphics preview in the layout editor may not be accurate:
        // Different corner sizes are not supported in Path.addRoundRect.
        // (Ignore for this session)
        tunaPath.addRoundRect(rect, radii, dir);
        return tunaPath;
    }

    protected Rect tunaRect;

    public Rect getTunaRect() {
        return tunaRect;
    }

    public void setTunaRect(Rect tunaRect) {
        this.tunaRect = tunaRect;
    }

    protected Rect initTunaRect() {
        if (tunaRect == null) {
            tunaRect = new Rect();
        }
        return tunaRect;
    }

    protected Rect initTunaRect(int left, int top, int right, int bottom) {
        if (tunaRect == null) {
            tunaRect = new Rect(left, top, right, bottom);
        }
        tunaRect.set(left, top, right, bottom);
        return tunaRect;
    }

    protected RectF tunaRectF;

    public RectF getTunaRectF() {
        return tunaRectF;
    }

    public void setTunaRectF(RectF tunaRectF) {
        this.tunaRectF = tunaRectF;
    }

    //
    protected RectF initTunaRectF(float left, float top, float right, float bottom) {
        if (tunaRectF == null) {
            tunaRectF = new RectF(left, top, right, bottom);
        }
        tunaRectF.set(left, top, right, bottom);
        return tunaRectF;
    }

    protected Canvas tunaCanvas;

    public Canvas getTunaCanvas() {
        return tunaCanvas;
    }

    public void setTunaCanvas(Canvas tunaCanvas) {
        this.tunaCanvas = tunaCanvas;
    }

    //
    protected Matrix tunaMatrix;

    public Matrix getTunaMatrix() {
        return tunaMatrix;
    }

    public void setTunaMatrix(Matrix tunaMatrix) {
        this.tunaMatrix = tunaMatrix;
    }

    protected Matrix initTunaMatrix(float sx, float sy) {
        if (tunaMatrix == null) {
            tunaMatrix = new Matrix();
        }
        tunaMatrix.reset();
        tunaMatrix.setScale(sx, sy);
        return tunaMatrix;
    }

    public static Matrix initTunaMatrix(Matrix matrix, float sx, float sy) {
        if (matrix == null) {
            matrix = new Matrix();
        }
        matrix.reset();
        matrix.setScale(sx, sy);
        return matrix;
    }


    protected LayoutInflater initTunaLayoutInflater() {
        if (tunaLayoutInflater == null) {
            tunaLayoutInflater = LayoutInflater.from(getContext());
        }
        return tunaLayoutInflater;
    }


    protected void setTunaLayout(int width, int height) {
        if (tunaLayoutParams == null) {
            tunaLayoutParams = getLayoutParams();
        }
        tunaLayoutParams.width = width;
        tunaLayoutParams.height = height;
        setLayoutParams(tunaLayoutParams);
        invalidate();
    }


    //
    protected void initTunaCanvas(Canvas canvas) {
        if (canvas.getDrawFilter() == null) {
            canvas.setDrawFilter(tunaPaintFlagsDrawFilter);
            tunaCanvasHardwareAccelerated = canvas.isHardwareAccelerated();
        }
    }

    // 8
    protected void drawTunaRectCustom(Canvas canvas, int width, int height, int fillColor, float radiusLeftTop, float radiusLeftBottom,
                                      float radiusRightTop, float radiusRightBottom) {

        drawTunaRectCustom(canvas, 0, 0, width, height, fillColor, null, 0, Color.TRANSPARENT, 0, 0, 0, Color.TRANSPARENT, radiusLeftTop, radiusLeftBottom, radiusRightTop,
                radiusRightBottom);
    }

    // 10
    protected void drawTunaRectCustom(Canvas canvas, int width, int height, int fillColor, float strokeWidth, int strokeColor, float radiusLeftTop, float radiusLeftBottom,
                                      float radiusRightTop, float radiusRightBottom) {

        drawTunaRectCustom(canvas, 0, 0, width, height, fillColor, null, 0, Color.TRANSPARENT, 0, 0, strokeWidth, strokeColor, radiusLeftTop, radiusLeftBottom, radiusRightTop,
                radiusRightBottom);
    }

    // 10
    protected void drawTunaRectCustom(Canvas canvas, int width, int height, Shader shader, float strokeWidth, int strokeColor, float radiusLeftTop, float radiusLeftBottom,
                                      float radiusRightTop, float radiusRightBottom) {

        drawTunaRectCustom(canvas, 0, 0, width, height, Color.TRANSPARENT, shader, 0, Color.TRANSPARENT, 0, 0, strokeWidth, strokeColor, radiusLeftTop, radiusLeftBottom,
                radiusRightTop, radiusRightBottom);
    }

    // 14
    protected void drawTunaRectCustom(Canvas canvas, int width, int height, int fillColor, float shadowRadius, int shadowColor, float shadowDx, float shadowDy, float strokeWidth,
                                      int strokeColor, float radiusLeftTop, float radiusLeftBottom, float radiusRightTop, float radiusRightBottom) {

        drawTunaRectCustom(canvas, 0, 0, width, height, fillColor, null, shadowRadius, shadowColor, shadowDx, shadowDy, strokeWidth, strokeColor, radiusLeftTop, radiusLeftBottom,
                radiusRightTop, radiusRightBottom);
    }

    // 14
    protected void drawTunaRectCustom(Canvas canvas, int width, int height, Shader shader, float shadowRadius, int shadowColor, float shadowDx, float shadowDy, float strokeWidth,
                                      int strokeColor, float radiusLeftTop, float radiusLeftBottom, float radiusRightTop, float radiusRightBottom) {

        drawTunaRectCustom(canvas, 0, 0, width, height, Color.TRANSPARENT, shader, shadowRadius, shadowColor, shadowDx, shadowDy, strokeWidth, strokeColor, radiusLeftTop,
                radiusLeftBottom, radiusRightTop, radiusRightBottom);
    }

    // 15
    protected void drawTunaRectCustom(Canvas canvas, float left, float top, float right, float bottom, int fillColor, Shader shader, float shadowRadius, int shadowColor,
                                      float shadowDx, float shadowDy, float strokeWidth, int strokeColor, float radiusLeftTop, float radiusLeftBottom, float radiusRightTop, float radiusRightBottom) {

        float[] radii = {radiusLeftTop, radiusLeftTop, radiusRightTop, radiusRightTop, radiusRightBottom, radiusRightBottom, radiusLeftBottom, radiusLeftBottom};
        if (strokeWidth > 0) {
            canvas.drawPath(
                    initTunaPathRoundRect(initTunaRectF(left + strokeWidth * 0.5f, top + strokeWidth * 0.5f, right - strokeWidth * 0.5f, bottom - strokeWidth * 0.5f), radii,
                            Path.Direction.CW), initTunaPaint(Paint.Style.STROKE, strokeColor, strokeWidth));
        }

        int radiiLength = radii.length;
        for (int i = 0; i < radiiLength; i++) {
            radii[i] -= strokeWidth;
            radii[i] = radii[i] >= 0 ? radii[i] : 0;
        }

        canvas.drawPath(
                initTunaPathRoundRect(initTunaRectF(left + strokeWidth, top + strokeWidth, right - strokeWidth, bottom - strokeWidth), radii, Path.Direction.CW),
                shader == null ? shadowRadius == 0 ? initTunaPaint(fillColor) : initTunaPaint(Paint.Style.FILL, fillColor, shadowRadius, shadowColor, shadowDx,
                        shadowDy) : shadowRadius == 0 ? initTunaPaint(Paint.Style.FILL, shader) : initTunaPaint(Paint.Style.FILL, shader, shadowRadius, shadowColor, shadowDx,
                        shadowDy));
    }

    // 5
    protected void drawTunaRectClassic(Canvas canvas, float width, float height, int fillColor, float radius) {

        drawTunaRectClassic(canvas, 0, 0, width, height, fillColor, null, 0, Color.TRANSPARENT, 0, 0, 0, Color.TRANSPARENT, radius);
    }

    // 7
    protected void drawTunaRectClassic(Canvas canvas, float width, float height, int fillColor, float strokeWidth, int strokeColor, float radius) {

        drawTunaRectClassic(canvas, 0, 0, width, height, fillColor, null, 0, Color.TRANSPARENT, 0, 0, strokeWidth, strokeColor, radius);
    }

    // 7
    protected void drawTunaRectClassic(Canvas canvas, float width, float height, Shader shader, float strokeWidth, int strokeColor, float radius) {

        drawTunaRectClassic(canvas, 0, 0, width, height, Color.TRANSPARENT, shader, 0, Color.TRANSPARENT, 0, 0, strokeWidth, strokeColor, radius);
    }

    // 9
    protected void drawTunaRectClassic(Canvas canvas, float left, float top, float right, float bottom, int fillColor, float strokeWidth, int strokeColor, float radius) {

        drawTunaRectClassic(canvas, left, top, right, bottom, fillColor, null, 0, Color.TRANSPARENT, 0, 0, strokeWidth, strokeColor, radius);
    }

    // 9
    protected void drawTunaRectClassic(Canvas canvas, float left, float top, float right, float bottom, Shader shader, float strokeWidth, int strokeColor, float radius) {

        drawTunaRectClassic(canvas, left, top, right, bottom, Color.TRANSPARENT, shader, 0, Color.TRANSPARENT, 0, 0, strokeWidth, strokeColor, radius);
    }

    // 11
    protected void drawTunaRectClassic(Canvas canvas, float width, float height, int fillColor, float shadowRadius, int shadowColor, float shadowDx, float shadowDy, float strokeWidth,
                                       int strokeColor, float radius) {

        drawTunaRectClassic(canvas, 0, 0, width, height, fillColor, null, shadowRadius, shadowColor, shadowDx, shadowDy, strokeWidth, strokeColor, radius);
    }

    // 11
    protected void drawTunaRectClassic(Canvas canvas, float width, float height, Shader shader, float shadowRadius, int shadowColor, float shadowDx, float shadowDy, float strokeWidth,
                                       int strokeColor, float radius) {

        drawTunaRectClassic(canvas, 0, 0, width, height, Color.TRANSPARENT, shader, shadowRadius, shadowColor, shadowDx, shadowDy, strokeWidth, strokeColor, radius);
    }

    // 12
    protected void drawTunaRectClassic(Canvas canvas, float left, float top, float right, float bottom, int fillColor, Shader shader, float shadowRadius, int shadowColor,
                                       float shadowDx, float shadowDy, float strokeWidth, int strokeColor, float radius) {

        if (strokeWidth > 0) {
            canvas.drawRoundRect(initTunaRectF(left + strokeWidth * 0.5f, top + strokeWidth * 0.5f, right - strokeWidth * 0.5f, bottom - strokeWidth * 0.5f), radius, radius,
                    initTunaPaint(Paint.Style.STROKE, strokeColor, strokeWidth));
        }

        canvas.drawRoundRect(
                initTunaRectF(left + strokeWidth, top + strokeWidth, right - strokeWidth, bottom - strokeWidth),
                radius,
                radius,
                shader == null ? shadowRadius == 0 ? initTunaPaint(fillColor) : initTunaPaint(Paint.Style.FILL, fillColor, shadowRadius, shadowColor, shadowDx,
                        shadowDy) : shadowRadius == 0 ? initTunaPaint(Paint.Style.FILL, shader) : initTunaPaint(Paint.Style.FILL, shader, shadowRadius, shadowColor, shadowDx,
                        shadowDy));
    }

    // 6
    protected float[] drawTunaText(Canvas canvas, String string, float width, float centerX, float centerY, Paint paint) {
        return drawTunaText(canvas, string, width, centerX, centerY, 0, 0, paint, tunaTextGravityArray[0], 1.0f, null);
    }

    // 8
    protected float[] drawTunaText(Canvas canvas, String string, float width, float centerX, float centerY, float paddingLeft, float paddingRight, Paint paint) {
        return drawTunaText(canvas, string, width, centerX, centerY, paddingLeft, paddingRight, paint, tunaTextGravityArray[0], 1.0f, null);
    }

    // 9
    protected float[] drawTunaText(Canvas canvas, String string, float width, float centerX, float centerY, float paddingLeft, float paddingRight, Paint paint,
                                   float textRowSpaceRatio, List<Integer> valueMeasureList) {
        return drawTunaText(canvas, string, width, centerX, centerY, paddingLeft, paddingRight, paint, tunaTextGravityArray[0], textRowSpaceRatio, valueMeasureList);
    }

    // 10
    protected float[] drawTunaText(Canvas canvas, String string, float width, float centerX, float centerY, float paddingLeft, float paddingRight, Paint paint,
                                   TunaTextGravity tunaTextGravity, float textRowSpaceRatio, List<Integer> valueMeasureList) {

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
                switch (tunaTextGravity) {
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
                switch (tunaTextGravity) {
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
    protected void drawTunaTextMark(
            Canvas canvas,
            float radius, Paint paint,
            float markRadius,
            float markDx, float markDy,
            float offsetX, float offsetY,
            String markText,
            int markTextColor,
            float markTextSize,
            List<Integer> valueMeasureList) {

        float cx = (tunaWidth >> 1) + offsetX + markRadius + markDx;
        float cy = (tunaHeight >> 1) + offsetY + markDy;

        canvas.drawCircle(cx, cy, radius, paint);
        if (markText != null) {
            // Because, drawText use the same method to clear the cache tunaMeasureRowList
            drawTunaText(canvas, markText, tunaWidth, cx, cy, 0, 0,
                    initTunaTextPaint(Paint.Style.FILL, markTextColor, markTextSize, Paint.Align.CENTER)
                    , 1, valueMeasureList);
        }
    }

    //
    public void setTunaStatius(boolean tunaPress, boolean tunaSelect, boolean tunaTextMark) {
        if (this.tunaPress != tunaPress || this.tunaSelect != tunaSelect || this.tunaTextMark != tunaTextMark) {
            this.tunaPress = tunaPress;
            this.tunaSelect = tunaSelect;
            this.tunaTextMark = tunaTextMark;
            invalidate();
        }
    }

    //
    public void setTunaStatius(boolean tunaPress, boolean tunaSelect, boolean tunaTextMark, boolean tunaMaterial) {
        if (this.tunaPress != tunaPress || this.tunaSelect != tunaSelect || this.tunaTextMark != tunaTextMark) {
            this.tunaPress = tunaPress;
            this.tunaSelect = tunaSelect;
            this.tunaTextMark = tunaTextMark;
            if (!tunaMaterial && tunaMaterialAnimatorSet != null) {
                this.tunaMaterialAnimatorSet.cancel();
            }
            invalidate();
        }
    }

    //
    public static void tunaAssociate(final TView[] tunaViewArray) {
        if (tunaViewArray == null) {
            return;
        }
        final int arraySize = tunaViewArray.length;
        for (int i = 0; i < arraySize; i++) {
            final int finalI = i;
            tunaViewArray[i].setTunaAssociateListener(new TunaAssociateListener() {
                @Override
                public void tunaAssociate(View v) {
                    for (int j = 0; j < arraySize; j++) {
                        if (j != finalI) {
                            tunaViewArray[j].setTunaStatius(false, false, false);
                        }
                    }
                }
            });
            tunaViewArray[i].setTunaTouchCancelListener(new TunaTouchCancelListener() {
                @Override
                public void tunaTouchCancel(View v) {
                    for (int j = 0; j < arraySize; j++) {
                        switch (finalI) {
                            case 0:
                                if (j == finalI + 1) {
                                    tunaViewArray[j].setTunaStatius(false, true, false);
                                } else if (j > finalI + 1) {
                                    tunaViewArray[j].setTunaStatius(false, false, false);
                                }
                                break;
                            default:
                                if (j == finalI - 1) {
                                    tunaViewArray[j].setTunaStatius(false, true, false);
                                } else if (j < finalI - 1) {
                                    tunaViewArray[j].setTunaStatius(false, false, false);
                                }
                                break;
                        }
                    }
                }
            });
        }
    }

    //
    public static void tunaAssociate(final List<TView> tunaViewList) {
        if (tunaViewList == null) {
            return;
        }
        final int listSize = tunaViewList.size();
        for (int i = 0; i < listSize; i++) {
            final int finalI = i;
            tunaViewList.get(i).setTunaAssociateListener(new TunaAssociateListener() {
                @Override
                public void tunaAssociate(View v) {
                    for (int j = 0; j < listSize; j++) {
                        if (j != finalI) {
                            tunaViewList.get(j).setTunaStatius(false, false, false, false);
                        }
                    }
                }
            });
            tunaViewList.get(i).setTunaTouchCancelListener(new TunaTouchCancelListener() {
                @Override
                public void tunaTouchCancel(View v) {
                    for (int j = 0; j < listSize; j++) {
                        switch (finalI) {
                            case 0:
                                if (j == finalI + 1) {
                                    tunaViewList.get(j).setTunaStatius(false, true, false, false);
                                } else if (j > finalI + 1) {
                                    tunaViewList.get(j).setTunaStatius(false, false, false, false);
                                }
                                break;
                            default:
                                if (j == finalI - 1) {
                                    tunaViewList.get(j).setTunaStatius(false, true, false, false);
                                } else if (j < finalI - 1) {
                                    tunaViewList.get(j).setTunaStatius(false, false, false, false);
                                }
                                break;
                        }
                    }
                }
            });
        }
    }

    //
    public static void tunaDynamic(String[] titleArray, String string, TunaTouchUpListener tunaTouchUpListener, LinearLayout linearLayout, int width, int leftStyle,
                                   int rightStyle, int horizontalStyle, int wholeStyle) {

        tunaDynamic(titleArray, string, tunaTouchUpListener, linearLayout, TypedValue.COMPLEX_UNIT_DIP, width, leftStyle, rightStyle, horizontalStyle, wholeStyle);
    }

    //
    public static void tunaDynamic(String[] titleArray, String string, TunaTouchUpListener tunaTouchUpListener, LinearLayout linearLayout, int unitWidth, int width, int leftStyle,
                                   int rightStyle, int horizontalStyle, int wholeStyle) {
        int index = 0;
        for (int i = 0; i < titleArray.length; i++) {
            if (titleArray[i].equals(string)) {
                index = i;
                break;
            }
        }
        tunaDynamic(titleArray, index, tunaTouchUpListener, linearLayout, unitWidth, width, leftStyle, rightStyle, horizontalStyle, wholeStyle);
    }

    //
    public static void tunaDynamic(String[] titleArray, TunaTouchUpListener tunaTouchUpListener, LinearLayout linearLayout, int width, int leftStyle, int rightStyle,
                                   int horizontalStyle, int wholeStyle) {

        tunaDynamic(titleArray, 0, tunaTouchUpListener, linearLayout, TypedValue.COMPLEX_UNIT_DIP, width, leftStyle, rightStyle, horizontalStyle, wholeStyle);
    }

    //
    public static void tunaDynamic(String[] titleArray, TunaTouchUpListener tunaTouchUpListener, LinearLayout linearLayout, int unitWidth, int width, int leftStyle,
                                   int rightStyle, int horizontalStyle, int wholeStyle) {

        tunaDynamic(titleArray, 0, tunaTouchUpListener, linearLayout, unitWidth, width, leftStyle, rightStyle, horizontalStyle, wholeStyle);
    }

    //
    public static void tunaDynamic(String[] titleArray, int index, TunaTouchUpListener tunaTouchUpListener, LinearLayout linearLayout, int width, int leftStyle, int rightStyle,
                                   int horizontalStyle, int wholeStyle) {

        tunaDynamic(titleArray, index, tunaTouchUpListener, linearLayout, TypedValue.COMPLEX_UNIT_DIP, width, leftStyle, rightStyle, horizontalStyle, wholeStyle);
    }

    //
    public static void tunaDynamic(String[] titleArray, int index, TunaTouchUpListener tunaTouchUpListener, LinearLayout linearLayout, int widthUnit, int width, int leftStyle,
                                   int rightStyle, int horizontalStyle, int wholeStyle) {

        tunaDynamicRaw(titleArray, index, tunaTouchUpListener, linearLayout, (int) applyDimension(widthUnit, width, getViewDisplayMetrics(linearLayout)), leftStyle, rightStyle,
                horizontalStyle, wholeStyle);
    }

    //
    private static void tunaDynamicRaw(String[] titleArray, int index, TunaTouchUpListener tunaTouchUpListener, LinearLayout linearLayout, int width, int leftStyle,
                                       int rightStyle, int horizontalStyle, int wholeStyle) {

        Context context = linearLayout.getContext();

//		int margin = context.getResources().getDimensionPixelOffset(R.dimen.tuna_stroke_mask);
        int margin = -2;    //-2px

        List<TView> tunaViewList = new ArrayList<TView>();

        if (titleArray.length <= 0) {
            return;
        } else if (titleArray.length == 1) {

            TView tunaView = new TView(context, null, wholeStyle);

            tunaView.setTunaTextValue(titleArray[0]);
            tunaView.setTunaTouchUpListener(tunaTouchUpListener);
            linearLayout.addView(tunaView, width, LinearLayout.LayoutParams.MATCH_PARENT);
        } else {

            for (int i = 0; i < titleArray.length; i++) {
                TView tunaView = new TView(context, null, i == 0 ? leftStyle : i == titleArray.length - 1 ? rightStyle : horizontalStyle);
                tunaView.setTunaTextValue(titleArray[i]);
                if (i == index) {
                    tunaView.setTunaSelect(true);
                }
                tunaView.setTunaTouchUpListener(tunaTouchUpListener);

                tunaViewList.add(tunaView);
                linearLayout.addView(tunaView, width, LinearLayout.LayoutParams.MATCH_PARENT);

                if (i == 0 && titleArray.length == 2) {
                    setViewMargins(tunaView, TypedValue.COMPLEX_UNIT_PX, 0, 0, margin, 0);
                } else if (i == 1 && titleArray.length == 2) {
                    setViewMargins(tunaView, TypedValue.COMPLEX_UNIT_PX, margin, 0, 0, 0);
                } else if (i != 0 && i != titleArray.length - 1) {
                    setViewMargins(tunaView, TypedValue.COMPLEX_UNIT_PX, margin, 0, margin, 0);
                }
            }
            TView.tunaAssociate(tunaViewList);
        }
    }

    //
    protected View tunaPropertiesView;
    protected LayoutParams tunaLayoutParams;
    protected LayoutInflater tunaLayoutInflater;

    protected void showTunaProperties() {

        tunaPropertiesView = initTunaLayoutInflater().inflate(R.layout.properties, null);

        final TextView text_display = tunaPropertiesView.findViewById(R.id.text_display);

        final EditText edit_width = tunaPropertiesView.findViewById(R.id.edit_width);
        final EditText edit_height = tunaPropertiesView.findViewById(R.id.edit_height);

        final EditText edit_backgroundNormal = tunaPropertiesView.findViewById(R.id.edit_backgroundNormal);
        final EditText edit_backgroundPress = tunaPropertiesView.findViewById(R.id.edit_backgroundPress);
        final EditText edit_backgroundSelect = tunaPropertiesView.findViewById(R.id.edit_backgroundSelect);

        final EditText edit_textSize = tunaPropertiesView.findViewById(R.id.edit_textSize);
        final EditText edit_textColorNormal = tunaPropertiesView.findViewById(R.id.edit_textColorNormal);

        final EditText edit_strokeWidth = tunaPropertiesView.findViewById(R.id.edit_strokeWidth);
        final EditText edit_strokeColor = tunaPropertiesView.findViewById(R.id.edit_strokeColor);

        final Button btn_width_pius = tunaPropertiesView.findViewById(R.id.btn_width_pius);
        final Button btn_width_minus = tunaPropertiesView.findViewById(R.id.btn_width_minus);
        final Button btn_height_pius = tunaPropertiesView.findViewById(R.id.btn_height_pius);
        final Button btn_height_minus = tunaPropertiesView.findViewById(R.id.btn_height_minus);

        final Button btn_textSize_pius = tunaPropertiesView.findViewById(R.id.btn_textSize_pius);
        final Button btn_textSize_minus = tunaPropertiesView.findViewById(R.id.btn_textSize_minus);

        final Button btn_strokeWidth_pius = tunaPropertiesView.findViewById(R.id.btn_strokeWidth_pius);
        final Button btn_strokeWidth_minus = tunaPropertiesView.findViewById(R.id.btn_strokeWidth_minus);

        final Button btn_backgroundNormal = tunaPropertiesView.findViewById(R.id.btn_backgroundNormal);
        final Button btn_backgroundPress = tunaPropertiesView.findViewById(R.id.btn_backgroundPress);
        final Button btn_backgroundSelect = tunaPropertiesView.findViewById(R.id.btn_backgroundSelect);

        final Button btn_textColorNormal = tunaPropertiesView.findViewById(R.id.btn_textColorNormal);

        final Button btn_strokeColor = tunaPropertiesView.findViewById(R.id.btn_strokeColor);

        final ToggleButton toogle_mark = tunaPropertiesView.findViewById(R.id.toogle_mark);
        final TextView text_mark = tunaPropertiesView.findViewById(R.id.text_mark);

        final ToggleButton toogle_thisHardwareAccelerated = tunaPropertiesView.findViewById(R.id.toogle_thisHardwareAccelerated);
        final TextView text_thisHardwareAccelerated = tunaPropertiesView.findViewById(R.id.text_thisHardwareAccelerated);

        final ToggleButton toogle_canvasHardwareAccelerated = tunaPropertiesView.findViewById(R.id.toogle_canvasHardwareAccelerated);
        final TextView text_canvasHardwareAccelerated = tunaPropertiesView.findViewById(R.id.text_canvasHardwareAccelerated);


        DeviceTool.initDisplayMetrics(getContext());
        text_display.setText(DeviceTool.tunaStringBuffer);

        edit_width.setText(String.valueOf(DeviceTool.pxToDp(getContext(), tunaWidth)));
        edit_height.setText(String.valueOf(DeviceTool.pxToDp(getContext(), tunaHeight)));

        edit_backgroundNormal.setText(tunaBackgroundNormal != 0 ? String.valueOf(Integer.toHexString(tunaBackgroundNormal)) : "00000000");
        edit_backgroundPress.setText(tunaBackgroundPress != 0 ? String.valueOf(Integer.toHexString(tunaBackgroundPress)) : "00000000");
        edit_backgroundSelect.setText(tunaBackgroundSelect != 0 ? String.valueOf(Integer.toHexString(tunaBackgroundSelect)) : "00000000");

        edit_textSize.setText(String.valueOf(DeviceTool.pxToDp(getContext(), tunaTextSize)));
        edit_textColorNormal.setText(tunaTextColorNormal != 0 ? String.valueOf(Integer.toHexString(tunaTextColorNormal)) : "00000000");

        edit_strokeWidth.setText(String.valueOf(DeviceTool.pxToDp(getContext(), tunaStrokeWidthNormal)));
        edit_strokeColor.setText(tunaStrokeColorNormal != 0 ? String.valueOf(Integer.toHexString(tunaStrokeColorNormal)) : "00000000");

        //
        edit_backgroundNormal.setTextColor(tunaBackgroundNormal);
        edit_backgroundPress.setTextColor(tunaBackgroundPress);
        edit_backgroundSelect.setTextColor(tunaBackgroundSelect);
        edit_strokeColor.setTextColor(tunaStrokeColorNormal);
        edit_textColorNormal.setTextColor(tunaTextColorNormal);

        btn_backgroundNormal.setBackgroundColor(tunaBackgroundNormal);
        btn_backgroundPress.setBackgroundColor(tunaBackgroundPress);
        btn_backgroundSelect.setBackgroundColor(tunaBackgroundSelect);
        btn_strokeColor.setBackgroundColor(tunaStrokeColorNormal);
        btn_textColorNormal.setBackgroundColor(tunaTextColorNormal);

        toogle_mark.setChecked(tunaTextMark);
        text_mark.setText(String.valueOf(tunaTextMark));

        tunaIsHardwareAccelerated = this.isHardwareAccelerated();
        toogle_thisHardwareAccelerated.setChecked(tunaIsHardwareAccelerated);
        text_thisHardwareAccelerated.setText(String.valueOf(tunaIsHardwareAccelerated));

        toogle_canvasHardwareAccelerated.setChecked(tunaCanvasHardwareAccelerated);
        text_canvasHardwareAccelerated.setText(String.valueOf(tunaCanvasHardwareAccelerated));

        //
        toogle_mark.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                text_mark.setText(String.valueOf(isChecked));
            }
        });

        //
        OnClickListener tunaOnClickListener = new OnClickListener() {
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
                    new TColorPickerDialog(getContext(), tunaBackgroundNormal, new TColorPickerDialog.colorSelectListener() {
                        @Override
                        public void tunaColorSelect(int color) {
                            btn_backgroundNormal.setBackgroundColor(color);
                            edit_backgroundNormal.setTextColor(color);
                            edit_backgroundNormal.setText(String.valueOf(Integer.toHexString(color)));
                        }
                    }).show();
                } else if (viewId == R.id.btn_backgroundPress) {
                    new TColorPickerDialog(getContext(), tunaBackgroundNormal, new TColorPickerDialog.colorSelectListener() {
                        @Override
                        public void tunaColorSelect(int color) {
                            btn_backgroundPress.setBackgroundColor(color);
                            edit_backgroundPress.setTextColor(color);
                            edit_backgroundPress.setText(String.valueOf(Integer.toHexString(color)));
                        }
                    }).show();
                } else if (viewId == R.id.btn_backgroundSelect) {
                    new TColorPickerDialog(getContext(), tunaBackgroundNormal, new TColorPickerDialog.colorSelectListener() {
                        @Override
                        public void tunaColorSelect(int color) {
                            btn_backgroundSelect.setBackgroundColor(color);
                            edit_backgroundSelect.setTextColor(color);
                            edit_backgroundSelect.setText(String.valueOf(Integer.toHexString(color)));
                        }
                    }).show();
                } else if (viewId == R.id.btn_textColorNormal) {
                    new TColorPickerDialog(getContext(), tunaStrokeColorNormal, new TColorPickerDialog.colorSelectListener() {
                        @Override
                        public void tunaColorSelect(int color) {
                            btn_textColorNormal.setBackgroundColor(color);
                            edit_textColorNormal.setTextColor(color);
                            edit_textColorNormal.setText(String.valueOf(Integer.toHexString(color)));
                        }
                    }).show();
                } else if (viewId == R.id.btn_strokeColor) {
                    new TColorPickerDialog(getContext(), tunaStrokeColorNormal, new TColorPickerDialog.colorSelectListener() {
                        @Override
                        public void tunaColorSelect(int color) {
                            btn_strokeColor.setBackgroundColor(color);
                            edit_strokeColor.setTextColor(color);
                            edit_strokeColor.setText(String.valueOf(Integer.toHexString(color)));
                        }
                    }).show();
                }
            }
        };

        btn_width_pius.setOnClickListener(tunaOnClickListener);
        btn_width_minus.setOnClickListener(tunaOnClickListener);
        btn_height_pius.setOnClickListener(tunaOnClickListener);
        btn_height_minus.setOnClickListener(tunaOnClickListener);
        btn_textSize_pius.setOnClickListener(tunaOnClickListener);
        btn_textSize_minus.setOnClickListener(tunaOnClickListener);
        btn_strokeWidth_pius.setOnClickListener(tunaOnClickListener);
        btn_strokeWidth_minus.setOnClickListener(tunaOnClickListener);

        btn_backgroundNormal.setOnClickListener(tunaOnClickListener);
        btn_backgroundPress.setOnClickListener(tunaOnClickListener);
        btn_backgroundSelect.setOnClickListener(tunaOnClickListener);

        btn_textColorNormal.setOnClickListener(tunaOnClickListener);
        btn_strokeColor.setOnClickListener(tunaOnClickListener);

        new AlertDialog.Builder(getContext(), android.R.style.Theme_Holo_Light)
                .setIconAttribute(android.R.attr.alertDialogIcon)
                .setView(tunaPropertiesView)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //
                        tunaBackgroundNormal = Color.parseColor("#" + edit_backgroundNormal.getText().toString().trim());
                        tunaBackgroundPress = Color.parseColor("#" + edit_backgroundPress.getText().toString().trim());
                        tunaBackgroundSelect = Color.parseColor("#" + edit_backgroundSelect.getText().toString().trim());
                        tunaStrokeColorNormal = Color.parseColor("#" + edit_strokeColor.getText().toString().trim());
                        tunaTextColorNormal = Color.parseColor("#" + edit_textColorNormal.getText().toString().trim());

                        tunaTextSize = DeviceTool.dpToPx(getContext(), Float.parseFloat(edit_textSize.getText().toString().trim()));

                        tunaStrokeWidthNormal = DeviceTool.dpToPx(getContext(), Float.parseFloat(edit_strokeWidth.getText().toString().trim()));

                        tunaTextMark = text_mark.getText().toString().trim().equals("true") ? true : false;

                        setTunaLayout(DeviceTool.dpToPx(getContext(), Float.parseFloat(edit_width.getText().toString().trim())), DeviceTool.dpToPx(getContext(), Float.parseFloat(edit_height.getText().toString().trim())));
                    }
                }).setNegativeButton("Cancel", null).create().show();
    }

    // Hardware accelerated this
    protected boolean tunaIsHardwareAccelerated;
    // Hardware accelerated canvas
    protected boolean tunaCanvasHardwareAccelerated;

    // default edge
    private TunaTouchType tunaTouchType;

    public enum TunaTouchType {
        EDGE(0), ALWAYS(1), NONE(2),
        ;
        final int nativeInt;

        TunaTouchType(int ni) {
            nativeInt = ni;
        }
    }

    private static final TunaTouchType[] tunaTouchTypeArray = {TunaTouchType.EDGE, TunaTouchType.ALWAYS, TunaTouchType.NONE,};

    public TunaTouchType getTunaTouchType() {
        return tunaTouchType;
    }

    public void setTunaTouchType(TunaTouchType tunaTouchType) {
        this.tunaTouchType = tunaTouchType;
    }

    private boolean tunaSuper;

    public boolean isTunaSuper() {
        return tunaSuper;
    }

    public void setTunaSuper(boolean tunaSuper) {
        this.tunaSuper = tunaSuper;
    }

    protected boolean tunaClassic;

    public boolean isTunaClassic() {
        return tunaClassic;
    }

    public void setTunaClassic(boolean tunaClassic) {
        this.tunaClassic = tunaClassic;
    }

    // can modify the properties when tunaDebug is true
    protected boolean tunaDebugable;

    public boolean isTunaDebugable() {
        return tunaDebugable;
    }

    public void setTunaDebugable(boolean tunaDebugable) {
        this.tunaDebugable = tunaDebugable;
    }

    // tunaTouchIntercept default false
    protected boolean tunaTouchIntercept;

    public boolean isTunaTouchIntercept() {
        return tunaTouchIntercept;
    }

    public void setTunaTouchIntercept(boolean tunaTouchIntercept) {
        this.tunaTouchIntercept = tunaTouchIntercept;
    }

    // tunaPress default false
    protected boolean tunaPress;

    public boolean isTunaPress() {
        return tunaPress;
    }

    public void setTunaPress(boolean tunaPress) {
        this.tunaPress = tunaPress;
    }

    // tunaSelect default false
    protected boolean tunaSelect;

    public boolean isTunaSelect() {
        return tunaSelect;
    }

    public void setTunaSelect(boolean tunaSelect) {
        this.tunaSelect = tunaSelect;
        invalidate();
    }

    //
    protected boolean tunaSelectRaw;

    // default none
    private TunaSelectType tunaSelectType;

    public enum TunaSelectType {
        NONE(0), SAME(1), REVERSE(2),
        ;
        final int nativeInt;

        TunaSelectType(int ni) {
            nativeInt = ni;
        }
    }

    private static final TunaSelectType[] tunaSelectTypeArray = {TunaSelectType.NONE, TunaSelectType.SAME, TunaSelectType.REVERSE,};

    public TunaSelectType getTunaSelectType() {
        return tunaSelectType;
    }

    public void setTunaSelectType(TunaSelectType tunaSelectType) {
        this.tunaSelectType = tunaSelectType;
    }

    // tunaRotate default 0
    private int tunaRotate;

    public int getTunaRotate() {
        return tunaRotate;
    }

    public void setTunaRotate(int tunaRotate) {
        this.tunaRotate = tunaRotate;
    }

    // default false
    protected boolean tunaAnimationable;

    public boolean isTunaAnimationable() {
        return tunaAnimationable;
    }

    public void setTunaAnimationable(boolean tunaAnimationable) {
        this.tunaAnimationable = tunaAnimationable;
        if (tunaAnimationable) {
            invalidate();
        }
    }

    //
    protected float tunaTouchEventX;

    public float getTunaTouchEventX() {
        return tunaTouchEventX;
    }

    public void setTunaTouchEventX(float tunaTouchEventEventX) {
        this.tunaTouchEventX = tunaTouchEventEventX;
    }

    //
    protected float tunaTouchEventY;

    public float getTunaTouchEventY() {
        return tunaTouchEventY;
    }

    public void setTunaTouchEventY(float tunaTouchEventEventY) {
        this.tunaTouchEventY = tunaTouchEventEventY;
    }

    // default false
    protected boolean tunaTouchOutBounds;

    public boolean isTunaTouchOutBounds() {
        return tunaTouchOutBounds;
    }

    public void setTunaTouchOutBounds(boolean tunaTouchOutBounds) {
        this.tunaTouchOutBounds = tunaTouchOutBounds;
    }

    //
    protected TunaLayoutListener tunaLayoutListener;

    public interface TunaLayoutListener {
        void tunaLayout(View v);
    }

    public TunaLayoutListener getTunaLayoutListener() {
        return tunaLayoutListener;
    }

    public void setTunaLayoutListener(TunaLayoutListener tunaLayoutListener) {
        this.tunaLayoutListener = tunaLayoutListener;
    }

    //
    protected TunaDrawListener tunaDrawListener;

    public interface TunaDrawListener {
        void tunaDraw(View v);
    }

    public TunaDrawListener getTunaDrawListener() {
        return tunaDrawListener;
    }

    public void setTunaDrawListener(TunaDrawListener tunaDrawListener) {
        this.tunaDrawListener = tunaDrawListener;
    }

    //
    protected TunaAnimateEndListener tunaAnimateEndListener;

    public interface TunaAnimateEndListener {
        void tunaAnimateEnd(View v);
    }

    public TunaAnimateEndListener getTunaAnimateEndListener() {
        return tunaAnimateEndListener;
    }

    public void setTunaAnimateEndListener(TunaAnimateEndListener tunaAnimateEndListener) {
        this.tunaAnimateEndListener = tunaAnimateEndListener;
    }

    //
    protected TunaTouchListener tunaTouchListener;

    public interface TunaTouchListener {
        void tunaTouch(View v);
    }

    public TunaTouchListener getTunaTouchListener() {
        return tunaTouchListener;
    }

    public void setTunaTouchListener(TunaTouchListener tunaTouchListener) {
        this.tunaTouchListener = tunaTouchListener;
    }

    //
    protected TunaTouchDownListener tunaTouchDownListener;

    public interface TunaTouchDownListener {
        void tunaTouchDown(View v);
    }

    public TunaTouchDownListener getTunaTouchDownListener() {
        return tunaTouchDownListener;
    }

    public void setTunaTouchDownListener(TunaTouchDownListener tunaTouchDownListener) {
        this.tunaTouchDownListener = tunaTouchDownListener;
    }

    //
    protected TunaTouchMoveListener tunaTouchMoveListener;

    public interface TunaTouchMoveListener {
        void tunaTouchMove(View v);
    }

    public TunaTouchMoveListener getTunaTouchMoveListener() {
        return tunaTouchMoveListener;
    }

    public void setTunaTouchMoveListener(TunaTouchMoveListener tunaTouchMoveListener) {
        this.tunaTouchMoveListener = tunaTouchMoveListener;
    }

    //
    protected TunaTouchUpListener tunaTouchUpListener;

    public interface TunaTouchUpListener {
        void tunaTouchUp(View v);
    }

    public TunaTouchUpListener getTunaTouchUpListener() {
        return tunaTouchUpListener;
    }

    public void setTunaTouchUpListener(TunaTouchUpListener tunaTouchUpListener) {
        this.tunaTouchUpListener = tunaTouchUpListener;
    }

    //
    protected TunaTouchCancelListener tunaTouchCancelListener;

    public interface TunaTouchCancelListener {
        void tunaTouchCancel(View v);
    }

    public TunaTouchCancelListener getTunaTouchCancelListener() {
        return tunaTouchCancelListener;
    }

    public void setTunaTouchCancelListener(TunaTouchCancelListener tunaTouchCancelListener) {
        this.tunaTouchCancelListener = tunaTouchCancelListener;
    }

    //
    protected TunaTouchOutListener tunaTouchOutListener;

    public interface TunaTouchOutListener {
        void tunaTouchOut(View v);
    }

    public TunaTouchOutListener getTunaTouchOutListener() {
        return tunaTouchOutListener;
    }

    public void setTunaTouchOutListener(TunaTouchOutListener tunaTouchOutListener) {
        this.tunaTouchOutListener = tunaTouchOutListener;
    }

    //
    protected TunaTouchInListener tunaTouchInListener;

    public interface TunaTouchInListener {
        void tunaTouchIn(View v);
    }

    public TunaTouchInListener getTunaTouchInListener() {
        return tunaTouchInListener;
    }

    public void setTunaTouchInListener(TunaTouchInListener tunaTouchInListener) {
        this.tunaTouchInListener = tunaTouchInListener;
    }

    // TunaAssociateListener is written in onTouchUp
    protected TunaAssociateListener tunaAssociateListener;

    public interface TunaAssociateListener {
        void tunaAssociate(View v);
    }

    public TunaAssociateListener getTunaAssociateListener() {
        return tunaAssociateListener;
    }

    public void setTunaAssociateListener(TunaAssociateListener tunaAssociateListener) {
        this.tunaAssociateListener = tunaAssociateListener;
    }

    //
    protected TunaSubLayoutListener tunaSubLayoutListener;

    public interface TunaSubLayoutListener {
        void tunaSubLayout(View v);
    }

    public TunaSubLayoutListener getTunaSubLayoutListener() {
        return tunaSubLayoutListener;
    }

    public void setTunaSubLayoutListener(TunaSubLayoutListener tunaSubLayoutListener) {
        this.tunaSubLayoutListener = tunaSubLayoutListener;
    }

    //
    protected TunaSubDrawListener tunaSubDrawListener;

    public interface TunaSubDrawListener {
        void tunaSubDraw(View v);
    }

    public TunaSubDrawListener getTunaSubDrawListener() {
        return tunaSubDrawListener;
    }

    public void setTunaSubDrawListener(TunaSubDrawListener tunaSubDrawListener) {
        this.tunaSubDrawListener = tunaSubDrawListener;
    }

    //
    protected TunaSubAnimateEndListener tunaSubAnimateEndListener;

    public interface TunaSubAnimateEndListener {
        void tunaSubAnimateEnd(View v);
    }

    public TunaSubAnimateEndListener getTunaSubAnimateEndListener() {
        return tunaSubAnimateEndListener;
    }

    public void setTunaSubAnimateEndListener(TunaSubAnimateEndListener tunaSubAnimateEndListener) {
        this.tunaSubAnimateEndListener = tunaSubAnimateEndListener;
    }

    /**
     * The following fields and methods can be used only in the superclass
     */

    // tunaBackgroundNormal in onLayout if tunaBackgroundNormal transparent and have drawn the case of default white shadow
    private int tunaBackgroundNormal;

    public int getTunaBackgroundNormal() {
        return tunaBackgroundNormal;
    }

    public void setTunaBackgroundNormal(int tunaBackgroundNormal) {
        this.tunaBackgroundNormal = tunaBackgroundNormal;
    }

    // tunaBackgroundPress default tunaBackgroundNormal
    private int tunaBackgroundPress;

    public int getTunaBackgroundPress() {
        return tunaBackgroundPress;
    }

    public void setTunaBackgroundPress(int tunaBackgroundPress) {
        this.tunaBackgroundPress = tunaBackgroundPress;
    }

    // tunaBackgroundSelect default tunaBackgroundNormal
    private int tunaBackgroundSelect;

    public int getTunaBackgroundSelect() {
        return tunaBackgroundSelect;
    }

    public void setTunaBackgroundSelect(int tunaBackgroundSelect) {
        this.tunaBackgroundSelect = tunaBackgroundSelect;
    }

    // tunaForegroundNormal default transparent
    private int tunaForegroundNormal;

    public int getTunaForegroundNormal() {
        return tunaForegroundNormal;
    }

    public void setTunaForegroundNormal(int tunaForegroundNormal) {
        this.tunaForegroundNormal = tunaForegroundNormal;
    }

    // tunaForegroundPress default tunaForegroundNormal
    private int tunaForegroundPress;

    public int getTunaForegroundPress() {
        return tunaForegroundPress;
    }

    public void setTunaForegroundPress(int tunaForegroundPress) {
        this.tunaForegroundPress = tunaForegroundPress;
    }

    // tunaForegroundSelect default tunaForegroundNormal
    private int tunaForegroundSelect;

    public int getTunaForegroundSelect() {
        return tunaForegroundSelect;
    }

    public void setTunaForegroundSelect(int tunaForegroundSelect) {
        this.tunaForegroundSelect = tunaForegroundSelect;
    }

    public static final int DIRECTION_LEFT = 0x00000001;
    public static final int DIRECTION_RIGHT = DIRECTION_LEFT << 1;
    public static final int DIRECTION_TOP = DIRECTION_RIGHT << 1;
    public static final int DIRECTION_BOTTOM = DIRECTION_TOP << 1;
    public static final int DIRECTION_MASK = DIRECTION_LEFT | DIRECTION_RIGHT | DIRECTION_TOP | DIRECTION_BOTTOM;

    private int tunaBackgroundNormalAngle;

    public int getTunaBackgroundNormalAngle() {
        return tunaBackgroundNormalAngle;
    }

    public void setTunaBackgroundNormalAngle(int tunaBackgroundNormalAngle) {
        this.tunaBackgroundNormalAngle = tunaBackgroundNormalAngle;
    }

    private int tunaBackgroundPressAngle;

    public int getTunaBackgroundPressAngle() {
        return tunaBackgroundPressAngle;
    }

    public void setTunaBackgroundPressAngle(int tunaBackgroundPressAngle) {
        this.tunaBackgroundPressAngle = tunaBackgroundPressAngle;
    }

    private int tunaBackgroundSelectAngle;

    public int getTunaBackgroundSelectAngle() {
        return tunaBackgroundSelectAngle;
    }

    public void setTunaBackgroundSelectAngle(int tunaBackgroundSelectAngle) {
        this.tunaBackgroundSelectAngle = tunaBackgroundSelectAngle;
    }

    // tunaBackgroundNormalGradientStart default tunaBackgroundNormal
    private int tunaBackgroundNormalGradientStart;

    public int getTunaBackgroundNormalGradientStart() {
        return tunaBackgroundNormalGradientStart;
    }

    public void setTunaBackgroundNormalGradientStart(int tunaBackgroundNormalGradientStart) {
        this.tunaBackgroundNormalGradientStart = tunaBackgroundNormalGradientStart;
    }

    // tunaBackgroundNormalGradientEnd default tunaBackgroundNormal
    private int tunaBackgroundNormalGradientEnd;

    public int getTunaBackgroundNormalGradientEnd() {
        return tunaBackgroundNormalGradientEnd;
    }

    public void setTunaBackgroundNormalGradientEnd(int tunaBackgroundNormalGradientEnd) {
        this.tunaBackgroundNormalGradientEnd = tunaBackgroundNormalGradientEnd;
    }

    // tunaBackgroundPressGradientStart default tunaBackgroundPress
    private int tunaBackgroundPressGradientStart;

    public int getTunaBackgroundPressGradientStart() {
        return tunaBackgroundPressGradientStart;
    }

    public void setTunaBackgroundPressGradientStart(int tunaBackgroundPressGradientStart) {
        this.tunaBackgroundPressGradientStart = tunaBackgroundPressGradientStart;
    }

    // tunaBackgroundPressGradientEnd default tunaBackgroundPress
    private int tunaBackgroundPressGradientEnd;

    public int getTunaBackgroundPressGradientEnd() {
        return tunaBackgroundPressGradientEnd;
    }

    public void setTunaBackgroundPressGradientEnd(int tunaBackgroundPressGradientEnd) {
        this.tunaBackgroundPressGradientEnd = tunaBackgroundPressGradientEnd;
    }

    // tunaBackgroundSelectGradientStart default tunaBackgroundSelect
    private int tunaBackgroundSelectGradientStart;

    public int getTunaBackgroundSelectGradientStart() {
        return tunaBackgroundSelectGradientStart;
    }

    public void setTunaBackgroundSelectGradientStart(int tunaBackgroundSelectGradientStart) {
        this.tunaBackgroundSelectGradientStart = tunaBackgroundSelectGradientStart;
    }

    // tunaBackgroundSelectGradientEnd default tunaBackgroundSelect
    private int tunaBackgroundSelectGradientEnd;

    public int getTunaBackgroundSelectGradientEnd() {
        return tunaBackgroundSelectGradientEnd;
    }

    public void setTunaBackgroundSelectGradientEnd(int tunaBackgroundSelectGradientEnd) {
        this.tunaBackgroundSelectGradientEnd = tunaBackgroundSelectGradientEnd;
    }

    // tunaBackgroundNormalShader default null
    private Shader tunaBackgroundNormalShader;

    public Shader getTunaBackgroundNormalShader() {
        return tunaBackgroundNormalShader;
    }

    public void setTunaBackgroundNormalShader(Shader tunaBackgroundNormalShader) {
        this.tunaBackgroundNormalShader = tunaBackgroundNormalShader;
    }

    // tunaBackgroundPressShader default null
    private Shader tunaBackgroundPressShader;

    public Shader getTunaBackgroundPressShader() {
        return tunaBackgroundPressShader;
    }

    public void setTunaBackgroundPressShader(Shader tunaBackgroundPressShader) {
        this.tunaBackgroundPressShader = tunaBackgroundPressShader;
    }

    // tunaBackgroundSelectShader default null
    private Shader tunaBackgroundSelectShader;

    public Shader getTunaBackgroundSelectShader() {
        return tunaBackgroundSelectShader;
    }

    public void setTunaBackgroundSelectShader(Shader tunaBackgroundSelectShader) {
        this.tunaBackgroundSelectShader = tunaBackgroundSelectShader;
    }

    //
    private float tunaBackgroundNormalShadowRadius;

    public float getTunaBackgroundNormalShadowRadius() {
        return tunaBackgroundNormalShadowRadius;
    }

    public void setTunaBackgroundNormalShadowRadius(float tunaBackgroundNormalShadowRadius) {
        setTunaBackgroundNormalShadowRadius(TypedValue.COMPLEX_UNIT_DIP, tunaBackgroundNormalShadowRadius);
    }

    public void setTunaBackgroundNormalShadowRadius(int unit, float tunaBackgroundNormalShadowRadius) {
        setTunaBackgroundNormalShadowRadiusRaw(applyDimension(unit, tunaBackgroundNormalShadowRadius, getViewDisplayMetrics(this)));
    }

    private void setTunaBackgroundNormalShadowRadiusRaw(float tunaBackgroundNormalShadowRadius) {
        if (this.tunaBackgroundNormalShadowRadius != tunaBackgroundNormalShadowRadius) {
            this.tunaBackgroundNormalShadowRadius = tunaBackgroundNormalShadowRadius;
            invalidate();
        }
    }

    //
    private int tunaBackgroundNormalShadowColor;

    public int getTunaBackgroundNormalShadowColor() {
        return tunaBackgroundNormalShadowColor;
    }

    public void setTunaBackgroundNormalShadowColor(int tunaBackgroundNormalShadowColor) {
        this.tunaBackgroundNormalShadowColor = tunaBackgroundNormalShadowColor;
    }

    //
    private float tunaBackgroundNormalShadowDx;

    public float getTunaBackgroundNormalShadowDx() {
        return tunaBackgroundNormalShadowDx;
    }

    public void setTunaBackgroundNormalShadowDx(float tunaBackgroundNormalShadowDx) {
        setTunaBackgroundNormalShadowDx(TypedValue.COMPLEX_UNIT_DIP, tunaBackgroundNormalShadowDx);
    }

    public void setTunaBackgroundNormalShadowDx(int unit, float tunaBackgroundNormalShadowDx) {
        setTunaBackgroundNormalShadowDxRaw(applyDimension(unit, tunaBackgroundNormalShadowDx, getViewDisplayMetrics(this)));
    }

    private void setTunaBackgroundNormalShadowDxRaw(float tunaBackgroundNormalShadowDx) {
        if (this.tunaBackgroundNormalShadowDx != tunaBackgroundNormalShadowDx) {
            this.tunaBackgroundNormalShadowDx = tunaBackgroundNormalShadowDx;
            invalidate();
        }
    }

    //
    private float tunaBackgroundNormalShadowDy;

    public float getTunaBackgroundNormalShadowDy() {
        return tunaBackgroundNormalShadowDy;
    }

    public void setTunaBackgroundNormalShadowDy(float tunaBackgroundNormalShadowDy) {
        setTunaBackgroundNormalShadowDy(TypedValue.COMPLEX_UNIT_DIP, tunaBackgroundNormalShadowDy);
    }

    public void setTunaBackgroundNormalShadowDy(int unit, float tunaBackgroundNormalShadowDy) {
        setTunaBackgroundNormalShadowDyRaw(applyDimension(unit, tunaBackgroundNormalShadowDy, getViewDisplayMetrics(this)));
    }

    private void setTunaBackgroundNormalShadowDyRaw(float tunaBackgroundNormalShadowDy) {
        if (this.tunaBackgroundNormalShadowDy != tunaBackgroundNormalShadowDy) {
            this.tunaBackgroundNormalShadowDy = tunaBackgroundNormalShadowDy;
            invalidate();
        }
    }

    // default tunaBackgroundNormalShadowRadius
    private float tunaBackgroundPressShadowRadius;

    public float getTunaBackgroundPressShadowRadius() {
        return tunaBackgroundPressShadowRadius;
    }

    public void setTunaBackgroundPressShadowRadius(float tunaBackgroundPressShadowRadius) {
        setTunaBackgroundPressShadowRadius(TypedValue.COMPLEX_UNIT_DIP, tunaBackgroundPressShadowRadius);
    }

    public void setTunaBackgroundPressShadowRadius(int unit, float tunaBackgroundPressShadowRadius) {
        setTunaBackgroundPressShadowRadiusRaw(applyDimension(unit, tunaBackgroundPressShadowRadius, getViewDisplayMetrics(this)));
    }

    private void setTunaBackgroundPressShadowRadiusRaw(float tunaBackgroundPressShadowRadius) {
        if (this.tunaBackgroundPressShadowRadius != tunaBackgroundPressShadowRadius) {
            this.tunaBackgroundPressShadowRadius = tunaBackgroundPressShadowRadius;
            invalidate();
        }
    }

    // default tunaBackgroundNormalShadowColor
    private int tunaBackgroundPressShadowColor;

    public int getTunaBackgroundPressShadowColor() {
        return tunaBackgroundPressShadowColor;
    }

    public void setTunaBackgroundPressShadowColor(int tunaBackgroundPressShadowColor) {
        this.tunaBackgroundPressShadowColor = tunaBackgroundPressShadowColor;
    }

    // default tunaBackgroundNormalShadowDx
    private float tunaBackgroundPressShadowDx;

    public float getTunaBackgroundPressShadowDx() {
        return tunaBackgroundPressShadowDx;
    }

    public void setTunaBackgroundPressShadowDx(float tunaBackgroundPressShadowDx) {
        setTunaBackgroundPressShadowDx(TypedValue.COMPLEX_UNIT_DIP, tunaBackgroundPressShadowDx);
    }

    public void setTunaBackgroundPressShadowDx(int unit, float tunaBackgroundPressShadowDx) {
        setTunaBackgroundPressShadowDxRaw(applyDimension(unit, tunaBackgroundPressShadowDx, getViewDisplayMetrics(this)));
    }

    private void setTunaBackgroundPressShadowDxRaw(float tunaBackgroundPressShadowDx) {
        if (this.tunaBackgroundPressShadowDx != tunaBackgroundPressShadowDx) {
            this.tunaBackgroundPressShadowDx = tunaBackgroundPressShadowDx;
            invalidate();
        }
    }

    // default tunaBackgroundNormalShadowDy
    private float tunaBackgroundPressShadowDy;

    public float getTunaBackgroundPressShadowDy() {
        return tunaBackgroundPressShadowDy;
    }

    public void setTunaBackgroundPressShadowDy(float tunaBackgroundPressShadowDy) {
        setTunaBackgroundPressShadowDy(TypedValue.COMPLEX_UNIT_DIP, tunaBackgroundPressShadowDy);
    }

    public void setTunaBackgroundPressShadowDy(int unit, float tunaBackgroundPressShadowDy) {
        setTunaBackgroundPressShadowDyRaw(applyDimension(unit, tunaBackgroundPressShadowDy, getViewDisplayMetrics(this)));
    }

    private void setTunaBackgroundPressShadowDyRaw(float tunaBackgroundPressShadowDy) {
        if (this.tunaBackgroundPressShadowDy != tunaBackgroundPressShadowDy) {
            this.tunaBackgroundPressShadowDy = tunaBackgroundPressShadowDy;
            invalidate();
        }
    }

    // default tunaBackgroundNormalShadowRadius
    private float tunaBackgroundSelectShadowRadius;

    public float getTunaBackgroundSelectShadowRadius() {
        return tunaBackgroundSelectShadowRadius;
    }

    public void setTunaBackgroundSelectShadowRadius(float tunaBackgroundSelectShadowRadius) {
        setTunaBackgroundSelectShadowRadius(TypedValue.COMPLEX_UNIT_DIP, tunaBackgroundSelectShadowRadius);
    }

    public void setTunaBackgroundSelectShadowRadius(int unit, float tunaBackgroundSelectShadowRadius) {
        setTunaBackgroundSelectShadowRadiusRaw(applyDimension(unit, tunaBackgroundSelectShadowRadius, getViewDisplayMetrics(this)));
    }

    private void setTunaBackgroundSelectShadowRadiusRaw(float tunaBackgroundSelectShadowRadius) {
        if (this.tunaBackgroundSelectShadowRadius != tunaBackgroundSelectShadowRadius) {
            this.tunaBackgroundSelectShadowRadius = tunaBackgroundSelectShadowRadius;
            invalidate();
        }
    }

    // default tunaBackgroundNormalShadowColor
    private int tunaBackgroundSelectShadowColor;

    public int getTunaBackgroundSelectShadowColor() {
        return tunaBackgroundSelectShadowColor;
    }

    public void setTunaBackgroundSelectShadowColor(int tunaBackgroundSelectShadowColor) {
        this.tunaBackgroundSelectShadowColor = tunaBackgroundSelectShadowColor;
    }

    // default tunaBackgroundNormalShadowDx
    private float tunaBackgroundSelectShadowDx;

    public float getTunaBackgroundSelectShadowDx() {
        return tunaBackgroundSelectShadowDx;
    }

    public void setTunaBackgroundSelectShadowDx(float tunaBackgroundSelectShadowDx) {
        setTunaBackgroundSelectShadowDx(TypedValue.COMPLEX_UNIT_DIP, tunaBackgroundSelectShadowDx);
    }

    public void setTunaBackgroundSelectShadowDx(int unit, float tunaBackgroundSelectShadowDx) {
        setTunaBackgroundSelectShadowDxRaw(applyDimension(unit, tunaBackgroundSelectShadowDx, getViewDisplayMetrics(this)));
    }

    private void setTunaBackgroundSelectShadowDxRaw(float tunaBackgroundSelectShadowDx) {
        if (this.tunaBackgroundSelectShadowDx != tunaBackgroundSelectShadowDx) {
            this.tunaBackgroundSelectShadowDx = tunaBackgroundSelectShadowDx;
            invalidate();
        }
    }

    // default tunaBackgroundNormalShadowDy
    private float tunaBackgroundSelectShadowDy;

    public float getTunaBackgroundSelectShadowDy() {
        return tunaBackgroundSelectShadowDy;
    }

    public void setTunaBackgroundSelectShadowDy(float tunaBackgroundSelectShadowDy) {
        setTunaBackgroundSelectShadowDy(TypedValue.COMPLEX_UNIT_DIP, tunaBackgroundSelectShadowDy);
    }

    public void setTunaBackgroundSelectShadowDy(int unit, float tunaBackgroundSelectShadowDy) {
        setTunaBackgroundSelectShadowDyRaw(applyDimension(unit, tunaBackgroundSelectShadowDy, getViewDisplayMetrics(this)));
    }

    private void setTunaBackgroundSelectShadowDyRaw(float tunaBackgroundSelectShadowDy) {
        if (this.tunaBackgroundSelectShadowDy != tunaBackgroundSelectShadowDy) {
            this.tunaBackgroundSelectShadowDy = tunaBackgroundSelectShadowDy;
            invalidate();
        }
    }

    //
    private Bitmap tunaSrcNormal;

    public Bitmap getTunaSrcNormal() {
        return tunaSrcNormal;
    }

    public void setTunaSrcNormal(int id) {
        setTunaSrcNormal(decodeBitmapResource(id));
    }

    public void setTunaSrcNormal(Bitmap tunaSrcNormal) {
        this.tunaSrcNormal = tunaSrcNormal;
        invalidate();
    }

    //
    private Bitmap tunaSrcPress;

    public Bitmap getTunaSrcPress() {
        return tunaSrcPress;
    }

    public void setTunaSrcPress(int id) {
        setTunaSrcPress(decodeBitmapResource(id));
    }

    public void setTunaSrcPress(Bitmap tunaSrcPress) {
        this.tunaSrcPress = tunaSrcPress;
        invalidate();
    }

    //
    private Bitmap tunaSrcSelect;

    public Bitmap getTunaSrcSelect() {
        return tunaSrcSelect;
    }

    public void setTunaSrcSelect(int id) {
        setTunaSrcSelect(decodeBitmapResource(id));
    }

    public void setTunaSrcSelect(Bitmap tunaSrcSelect) {
        this.tunaSrcSelect = tunaSrcSelect;
        invalidate();
    }

    //
    private float tunaSrcNormalShadowRadius;

    public float getTunaSrcNormalShadowRadius() {
        return tunaSrcNormalShadowRadius;
    }

    public void setTunaSrcNormalShadowRadius(float tunaSrcNormalShadowRadius) {
        setTunaSrcNormalShadowRadius(TypedValue.COMPLEX_UNIT_DIP, tunaSrcNormalShadowRadius);
    }

    public void setTunaSrcNormalShadowRadius(int unit, float tunaSrcNormalShadowRadius) {
        setTunaSrcNormalShadowRadiusRaw(applyDimension(unit, tunaSrcNormalShadowRadius, getViewDisplayMetrics(this)));
    }

    private void setTunaSrcNormalShadowRadiusRaw(float tunaSrcNormalShadowRadius) {
        if (this.tunaSrcNormalShadowRadius != tunaSrcNormalShadowRadius) {
            this.tunaSrcNormalShadowRadius = tunaSrcNormalShadowRadius;
            invalidate();
        }
    }

    //
    private float tunaSrcNormalShadowDx;

    public float getTunaSrcNormalShadowDx() {
        return tunaSrcNormalShadowDx;
    }

    public void setTunaSrcNormalShadowDx(float tunaSrcNormalShadowDx) {
        setTunaSrcNormalShadowDx(TypedValue.COMPLEX_UNIT_DIP, tunaSrcNormalShadowDx);
    }

    public void setTunaSrcNormalShadowDx(int unit, float tunaSrcNormalShadowDx) {
        setTunaSrcNormalShadowDxRaw(applyDimension(unit, tunaSrcNormalShadowDx, getViewDisplayMetrics(this)));
    }

    private void setTunaSrcNormalShadowDxRaw(float tunaSrcNormalShadowDx) {
        if (this.tunaSrcNormalShadowDx != tunaSrcNormalShadowDx) {
            this.tunaSrcNormalShadowDx = tunaSrcNormalShadowDx;
            invalidate();
        }
    }

    //
    private float tunaSrcNormalShadowDy;

    public float getTunaSrcNormalShadowDy() {
        return tunaSrcNormalShadowDy;
    }

    public void setTunaSrcNormalShadowDy(float tunaSrcNormalShadowDy) {
        setTunaSrcNormalShadowDy(TypedValue.COMPLEX_UNIT_DIP, tunaSrcNormalShadowDy);
    }

    public void setTunaSrcNormalShadowDy(int unit, float tunaSrcNormalShadowDy) {
        setTunaSrcNormalShadowDyRaw(applyDimension(unit, tunaSrcNormalShadowDy, getViewDisplayMetrics(this)));
    }

    private void setTunaSrcNormalShadowDyRaw(float tunaSrcNormalShadowDy) {
        if (this.tunaSrcNormalShadowDy != tunaSrcNormalShadowDy) {
            this.tunaSrcNormalShadowDy = tunaSrcNormalShadowDy;
            invalidate();
        }
    }

    // default tunaSrcNormalShadowRadius
    private float tunaSrcPressShadowRadius;

    public float getTunaSrcPressShadowRadius() {
        return tunaSrcPressShadowRadius;
    }

    public void setTunaSrcPressShadowRadius(float tunaSrcPressShadowRadius) {
        setTunaSrcPressShadowRadius(TypedValue.COMPLEX_UNIT_DIP, tunaSrcPressShadowRadius);
    }

    public void setTunaSrcPressShadowRadius(int unit, float tunaSrcPressShadowRadius) {
        setTunaSrcPressShadowRadiusRaw(applyDimension(unit, tunaSrcPressShadowRadius, getViewDisplayMetrics(this)));
    }

    private void setTunaSrcPressShadowRadiusRaw(float tunaSrcPressShadowRadius) {
        if (this.tunaSrcPressShadowRadius != tunaSrcPressShadowRadius) {
            this.tunaSrcPressShadowRadius = tunaSrcPressShadowRadius;
            invalidate();
        }
    }

    // default tunaSrcNormalShadowDx
    private float tunaSrcPressShadowDx;

    public float getTunaSrcPressShadowDx() {
        return tunaSrcPressShadowDx;
    }

    public void setTunaSrcPressShadowDx(float tunaSrcPressShadowDx) {
        setTunaSrcPressShadowDx(TypedValue.COMPLEX_UNIT_DIP, tunaSrcPressShadowDx);
    }

    public void setTunaSrcPressShadowDx(int unit, float tunaSrcPressShadowDx) {
        setTunaSrcPressShadowDxRaw(applyDimension(unit, tunaSrcPressShadowDx, getViewDisplayMetrics(this)));
    }

    private void setTunaSrcPressShadowDxRaw(float tunaSrcPressShadowDx) {
        if (this.tunaSrcPressShadowDx != tunaSrcPressShadowDx) {
            this.tunaSrcPressShadowDx = tunaSrcPressShadowDx;
            invalidate();
        }
    }

    // default tunaSrcNormalShadowDy
    private float tunaSrcPressShadowDy;

    public float getTunaSrcPressShadowDy() {
        return tunaSrcPressShadowDy;
    }

    public void setTunaSrcPressShadowDy(float tunaSrcPressShadowDy) {
        setTunaSrcPressShadowDy(TypedValue.COMPLEX_UNIT_DIP, tunaSrcPressShadowDy);
    }

    public void setTunaSrcPressShadowDy(int unit, float tunaSrcPressShadowDy) {
        setTunaSrcPressShadowDyRaw(applyDimension(unit, tunaSrcPressShadowDy, getViewDisplayMetrics(this)));
    }

    private void setTunaSrcPressShadowDyRaw(float tunaSrcPressShadowDy) {
        if (this.tunaSrcPressShadowDy != tunaSrcPressShadowDy) {
            this.tunaSrcPressShadowDy = tunaSrcPressShadowDy;
            invalidate();
        }
    }

    // default tunaSrcNormalShadowRadius
    private float tunaSrcSelectShadowRadius;

    public float getTunaSrcSelectShadowRadius() {
        return tunaSrcSelectShadowRadius;
    }

    public void setTunaSrcSelectShadowRadius(float tunaSrcSelectShadowRadius) {
        setTunaSrcSelectShadowRadius(TypedValue.COMPLEX_UNIT_DIP, tunaSrcSelectShadowRadius);
    }

    public void setTunaSrcSelectShadowRadius(int unit, float tunaSrcSelectShadowRadius) {
        setTunaSrcSelectShadowRadiusRaw(applyDimension(unit, tunaSrcSelectShadowRadius, getViewDisplayMetrics(this)));
    }

    private void setTunaSrcSelectShadowRadiusRaw(float tunaSrcSelectShadowRadius) {
        if (this.tunaSrcSelectShadowRadius != tunaSrcSelectShadowRadius) {
            this.tunaSrcSelectShadowRadius = tunaSrcSelectShadowRadius;
            invalidate();
        }
    }

    // default tunaSrcNormalShadowDx
    private float tunaSrcSelectShadowDx;

    public float getTunaSrcSelectShadowDx() {
        return tunaSrcSelectShadowDx;
    }

    public void setTunaSrcSelectShadowDx(float tunaSrcSelectShadowDx) {
        setTunaSrcSelectShadowDx(TypedValue.COMPLEX_UNIT_DIP, tunaSrcSelectShadowDx);
    }

    public void setTunaSrcSelectShadowDx(int unit, float tunaSrcSelectShadowDx) {
        setTunaSrcSelectShadowDxRaw(applyDimension(unit, tunaSrcSelectShadowDx, getViewDisplayMetrics(this)));
    }

    private void setTunaSrcSelectShadowDxRaw(float tunaSrcSelectShadowDx) {
        if (this.tunaSrcSelectShadowDx != tunaSrcSelectShadowDx) {
            this.tunaSrcSelectShadowDx = tunaSrcSelectShadowDx;
            invalidate();
        }
    }

    // default tunaSrcNormalShadowDy
    private float tunaSrcSelectShadowDy;

    public float getTunaSrcSelectShadowDy() {
        return tunaSrcSelectShadowDy;
    }

    public void setTunaSrcSelectShadowDy(float tunaSrcSelectShadowDy) {
        setTunaSrcSelectShadowDy(TypedValue.COMPLEX_UNIT_DIP, tunaSrcSelectShadowDy);
    }

    public void setTunaSrcSelectShadowDy(int unit, float tunaSrcSelectShadowDy) {
        setTunaSrcSelectShadowDyRaw(applyDimension(unit, tunaSrcSelectShadowDy, getViewDisplayMetrics(this)));
    }

    private void setTunaSrcSelectShadowDyRaw(float tunaSrcSelectShadowDy) {
        if (this.tunaSrcSelectShadowDy != tunaSrcSelectShadowDy) {
            this.tunaSrcSelectShadowDy = tunaSrcSelectShadowDy;
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
    private int tunaSrcAnchorGravity;

    public int getTunaDownloadMarkGravity() {
        return tunaSrcAnchorGravity;
    }

    public void setSrcAnchorGravity(int tunaSrcAnchorGravity) {
        this.tunaSrcAnchorGravity = tunaSrcAnchorGravity;
    }

    // anchor Normal,Press,Select use one Matrix
    protected Matrix tunaAnchorMatrix;

    public Matrix getTunaAnchorMatrix() {
        return tunaAnchorMatrix;
    }

    public void setTunaAnchorMatrix(Matrix tunaAnchorMatrix) {
        this.tunaAnchorMatrix = tunaAnchorMatrix;
    }

    protected Matrix initTunaAnchorMatrix(float sx, float sy) {
        if (tunaAnchorMatrix == null) {
            tunaAnchorMatrix = new Matrix();
        }
        tunaAnchorMatrix.reset();
        tunaAnchorMatrix.setScale(sx, sy);
        return tunaAnchorMatrix;
    }

    //
    private Bitmap tunaSrcAnchorNormal;

    public Bitmap getTunaSrcAnchorNormal() {
        return tunaSrcAnchorNormal;
    }

    public void setTunaSrcAnchorNormal(Bitmap tunaSrcAnchorNormal) {
        this.tunaSrcAnchorNormal = tunaSrcAnchorNormal;
    }

    //
    private float tunaSrcAnchorNormalWidth;

    public float getTunaSrcAnchorNormalWidth() {
        return tunaSrcAnchorNormalWidth;
    }

    public void setTunaSrcAnchorNormalWidth(float tunaSrcAnchorNormalWidth) {
        setTunaSrcAnchorNormalWidth(TypedValue.COMPLEX_UNIT_DIP, tunaSrcAnchorNormalWidth);
    }

    public void setTunaSrcAnchorNormalWidth(int unit, float tunaSrcAnchorNormalWidth) {
        setTunaSrcAnchorNormalWidthRaw(applyDimension(unit, tunaSrcAnchorNormalWidth, getViewDisplayMetrics(this)));
    }

    private void setTunaSrcAnchorNormalWidthRaw(float tunaSrcAnchorNormalWidth) {
        if (this.tunaSrcAnchorNormalWidth != tunaSrcAnchorNormalWidth) {
            this.tunaSrcAnchorNormalWidth = tunaSrcAnchorNormalWidth;
            invalidate();
        }
    }

    //
    private float tunaSrcAnchorNormalHeight;

    public float getTunaSrcAnchorNormalHeight() {
        return tunaSrcAnchorNormalHeight;
    }

    public void setTunaSrcAnchorNormalHeight(float tunaSrcAnchorNormalHeight) {
        setTunaSrcAnchorNormalHeight(TypedValue.COMPLEX_UNIT_DIP, tunaSrcAnchorNormalHeight);
    }

    public void setTunaSrcAnchorNormalHeight(int unit, float tunaSrcAnchorNormalHeight) {
        setTunaSrcAnchorNormalHeightRaw(applyDimension(unit, tunaSrcAnchorNormalHeight, getViewDisplayMetrics(this)));
    }

    private void setTunaSrcAnchorNormalHeightRaw(float tunaSrcAnchorNormalHeight) {
        if (this.tunaSrcAnchorNormalHeight != tunaSrcAnchorNormalHeight) {
            this.tunaSrcAnchorNormalHeight = tunaSrcAnchorNormalHeight;
            invalidate();
        }
    }

    //
    private float tunaSrcAnchorNormalDx;

    public float getTunaSrcAnchorNormalDx() {
        return tunaSrcAnchorNormalDx;
    }

    public void setTunaSrcAnchorNormalDx(float tunaSrcAnchorNormalDx) {
        setTunaSrcAnchorNormalDx(TypedValue.COMPLEX_UNIT_DIP, tunaSrcAnchorNormalDx);
    }

    public void setTunaSrcAnchorNormalDx(int unit, float tunaSrcAnchorNormalDx) {
        setTunaSrcAnchorNormalDxRaw(applyDimension(unit, tunaSrcAnchorNormalDx, getViewDisplayMetrics(this)));
    }

    private void setTunaSrcAnchorNormalDxRaw(float tunaSrcAnchorNormalDx) {
        if (this.tunaSrcAnchorNormalDx != tunaSrcAnchorNormalDx) {
            this.tunaSrcAnchorNormalDx = tunaSrcAnchorNormalDx;
            invalidate();
        }
    }

    //
    private float tunaSrcAnchorNormalDy;

    public float getTunaSrcAnchorNormalDy() {
        return tunaSrcAnchorNormalDy;
    }

    public void setTunaSrcAnchorNormalDy(float tunaSrcAnchorNormalDy) {
        setTunaSrcAnchorNormalDy(TypedValue.COMPLEX_UNIT_DIP, tunaSrcAnchorNormalDy);
    }

    public void setTunaSrcAnchorNormalDy(int unit, float tunaSrcAnchorNormalDy) {
        setTunaSrcAnchorNormalDyRaw(applyDimension(unit, tunaSrcAnchorNormalDy, getViewDisplayMetrics(this)));
    }

    private void setTunaSrcAnchorNormalDyRaw(float tunaSrcAnchorNormalDy) {
        if (this.tunaSrcAnchorNormalDy != tunaSrcAnchorNormalDy) {
            this.tunaSrcAnchorNormalDy = tunaSrcAnchorNormalDy;
            invalidate();
        }
    }

    //
    private Bitmap tunaSrcAnchorPress;

    public Bitmap getTunaSrcAnchorPress() {
        return tunaSrcAnchorPress;
    }

    public void setTunaSrcAnchorPress(Bitmap tunaSrcAnchorPress) {
        this.tunaSrcAnchorPress = tunaSrcAnchorPress;
    }

    //
    private float tunaSrcAnchorPressWidth;

    public float getTunaSrcAnchorPressWidth() {
        return tunaSrcAnchorPressWidth;
    }

    public void setTunaSrcAnchorPressWidth(float tunaSrcAnchorPressWidth) {
        setTunaSrcAnchorPressWidth(TypedValue.COMPLEX_UNIT_DIP, tunaSrcAnchorPressWidth);
    }

    public void setTunaSrcAnchorPressWidth(int unit, float tunaSrcAnchorPressWidth) {
        setTunaSrcAnchorPressWidthRaw(applyDimension(unit, tunaSrcAnchorPressWidth, getViewDisplayMetrics(this)));
    }

    private void setTunaSrcAnchorPressWidthRaw(float tunaSrcAnchorPressWidth) {
        if (this.tunaSrcAnchorPressWidth != tunaSrcAnchorPressWidth) {
            this.tunaSrcAnchorPressWidth = tunaSrcAnchorPressWidth;
            invalidate();
        }
    }

    //
    private float tunaSrcAnchorPressHeight;

    public float getTunaSrcAnchorPressHeight() {
        return tunaSrcAnchorPressHeight;
    }

    public void setTunaSrcAnchorPressHeight(float tunaSrcAnchorPressHeight) {
        setTunaSrcAnchorPressHeight(TypedValue.COMPLEX_UNIT_DIP, tunaSrcAnchorPressHeight);
    }

    public void setTunaSrcAnchorPressHeight(int unit, float tunaSrcAnchorPressHeight) {
        setTunaSrcAnchorPressHeightRaw(applyDimension(unit, tunaSrcAnchorPressHeight, getViewDisplayMetrics(this)));
    }

    private void setTunaSrcAnchorPressHeightRaw(float tunaSrcAnchorPressHeight) {
        if (this.tunaSrcAnchorPressHeight != tunaSrcAnchorPressHeight) {
            this.tunaSrcAnchorPressHeight = tunaSrcAnchorPressHeight;
            invalidate();
        }
    }

    //
    private float tunaSrcAnchorPressDx;

    public float getTunaSrcAnchorPressDx() {
        return tunaSrcAnchorPressDx;
    }

    public void setTunaSrcAnchorPressDx(float tunaSrcAnchorPressDx) {
        setTunaSrcAnchorPressDx(TypedValue.COMPLEX_UNIT_DIP, tunaSrcAnchorPressDx);
    }

    public void setTunaSrcAnchorPressDx(int unit, float tunaSrcAnchorPressDx) {
        setTunaSrcAnchorPressDxRaw(applyDimension(unit, tunaSrcAnchorPressDx, getViewDisplayMetrics(this)));
    }

    private void setTunaSrcAnchorPressDxRaw(float tunaSrcAnchorPressDx) {
        if (this.tunaSrcAnchorPressDx != tunaSrcAnchorPressDx) {
            this.tunaSrcAnchorPressDx = tunaSrcAnchorPressDx;
            invalidate();
        }
    }

    //
    private float tunaSrcAnchorPressDy;

    public float getTunaSrcAnchorPressDy() {
        return tunaSrcAnchorPressDy;
    }

    public void setTunaSrcAnchorPressDy(float tunaSrcAnchorPressDy) {
        setTunaSrcAnchorPressDy(TypedValue.COMPLEX_UNIT_DIP, tunaSrcAnchorPressDy);
    }

    public void setTunaSrcAnchorPressDy(int unit, float tunaSrcAnchorPressDy) {
        setTunaSrcAnchorPressDyRaw(applyDimension(unit, tunaSrcAnchorPressDy, getViewDisplayMetrics(this)));
    }

    private void setTunaSrcAnchorPressDyRaw(float tunaSrcAnchorPressDy) {
        if (this.tunaSrcAnchorPressDy != tunaSrcAnchorPressDy) {
            this.tunaSrcAnchorPressDy = tunaSrcAnchorPressDy;
            invalidate();
        }
    }

    //
    private Bitmap tunaSrcAnchorSelect;

    public Bitmap getTunaSrcAnchorSelect() {
        return tunaSrcAnchorSelect;
    }

    public void setTunaSrcAnchorSelect(Bitmap tunaSrcAnchorSelect) {
        this.tunaSrcAnchorSelect = tunaSrcAnchorSelect;
    }

    //
    private float tunaSrcAnchorSelectWidth;

    public float getTunaSrcAnchorSelectWidth() {
        return tunaSrcAnchorSelectWidth;
    }

    public void setTunaSrcAnchorSelectWidth(float tunaSrcAnchorSelectWidth) {
        setTunaSrcAnchorSelectWidth(TypedValue.COMPLEX_UNIT_DIP, tunaSrcAnchorSelectWidth);
    }

    public void setTunaSrcAnchorSelectWidth(int unit, float tunaSrcAnchorSelectWidth) {
        setTunaSrcAnchorSelectWidthRaw(applyDimension(unit, tunaSrcAnchorSelectWidth, getViewDisplayMetrics(this)));
    }

    private void setTunaSrcAnchorSelectWidthRaw(float tunaSrcAnchorSelectWidth) {
        if (this.tunaSrcAnchorSelectWidth != tunaSrcAnchorSelectWidth) {
            this.tunaSrcAnchorSelectWidth = tunaSrcAnchorSelectWidth;
            invalidate();
        }
    }

    //
    private float tunaSrcAnchorSelectHeight;

    public float getTunaSrcAnchorSelectHeight() {
        return tunaSrcAnchorSelectHeight;
    }

    public void setTunaSrcAnchorSelectHeight(float tunaSrcAnchorSelectHeight) {
        setTunaSrcAnchorSelectHeight(TypedValue.COMPLEX_UNIT_DIP, tunaSrcAnchorSelectHeight);
    }

    public void setTunaSrcAnchorSelectHeight(int unit, float tunaSrcAnchorSelectHeight) {
        setTunaSrcAnchorSelectHeightRaw(applyDimension(unit, tunaSrcAnchorSelectHeight, getViewDisplayMetrics(this)));
    }

    private void setTunaSrcAnchorSelectHeightRaw(float tunaSrcAnchorSelectHeight) {
        if (this.tunaSrcAnchorSelectHeight != tunaSrcAnchorSelectHeight) {
            this.tunaSrcAnchorSelectHeight = tunaSrcAnchorSelectHeight;
            invalidate();
        }
    }

    //
    private float tunaSrcAnchorSelectDx;

    public float getTunaSrcAnchorSelectDx() {
        return tunaSrcAnchorSelectDx;
    }

    public void setTunaSrcAnchorSelectDx(float tunaSrcAnchorSelectDx) {
        setTunaSrcAnchorSelectDx(TypedValue.COMPLEX_UNIT_DIP, tunaSrcAnchorSelectDx);
    }

    public void setTunaSrcAnchorSelectDx(int unit, float tunaSrcAnchorSelectDx) {
        setTunaSrcAnchorSelectDxRaw(applyDimension(unit, tunaSrcAnchorSelectDx, getViewDisplayMetrics(this)));
    }

    private void setTunaSrcAnchorSelectDxRaw(float tunaSrcAnchorSelectDx) {
        if (this.tunaSrcAnchorSelectDx != tunaSrcAnchorSelectDx) {
            this.tunaSrcAnchorSelectDx = tunaSrcAnchorSelectDx;
            invalidate();
        }
    }

    //
    private float tunaSrcAnchorSelectDy;

    public float getTunaSrcAnchorSelectDy() {
        return tunaSrcAnchorSelectDy;
    }

    public void setTunaSrcAnchorSelectDy(float tunaSrcAnchorSelectDy) {
        setTunaSrcAnchorSelectDy(TypedValue.COMPLEX_UNIT_DIP, tunaSrcAnchorSelectDy);
    }

    public void setTunaSrcAnchorSelectDy(int unit, float tunaSrcAnchorSelectDy) {
        setTunaSrcAnchorSelectDyRaw(applyDimension(unit, tunaSrcAnchorSelectDy, getViewDisplayMetrics(this)));
    }

    private void setTunaSrcAnchorSelectDyRaw(float tunaSrcAnchorSelectDy) {
        if (this.tunaSrcAnchorSelectDy != tunaSrcAnchorSelectDy) {
            this.tunaSrcAnchorSelectDy = tunaSrcAnchorSelectDy;
            invalidate();
        }
    }

    // tunaStrokeWidthNormal default 0
    private float tunaStrokeWidthNormal;

    public float getTunaStrokeWidthNormal() {
        return tunaStrokeWidthNormal;
    }

    public void setTunaStrokeWidthNormal(float tunaStrokeWidthNormal) {
        setTunaStrokeWidthNormal(TypedValue.COMPLEX_UNIT_DIP, tunaStrokeWidthNormal);
    }

    public void setTunaStrokeWidthNormal(int unit, float tunaStrokeWidthNormal) {
        setTunaStrokeWidthNormalRaw(applyDimension(unit, tunaStrokeWidthNormal, getViewDisplayMetrics(this)));
    }

    private void setTunaStrokeWidthNormalRaw(float tunaStrokeWidthNormal) {
        if (this.tunaStrokeWidthNormal != tunaStrokeWidthNormal) {
            this.tunaStrokeWidthNormal = tunaStrokeWidthNormal;
            invalidate();
        }
    }

    // tunaStrokeColorNormal default transparent
    private int tunaStrokeColorNormal;

    public int getTunaStrokeColorNormal() {
        return tunaStrokeColorNormal;
    }

    public void setTunaStrokeColorNormal(int tunaStrokeColorNormal) {
        this.tunaStrokeColorNormal = tunaStrokeColorNormal;
    }

    // tunaStrokeWidthPress default tunaStrokeWidthNormal
    private float tunaStrokeWidthPress;

    public float getTunaStrokeWidthPress() {
        return tunaStrokeWidthPress;
    }

    public void setTunaStrokeWidthPress(float tunaStrokeWidthPress) {
        setTunaStrokeWidthPress(TypedValue.COMPLEX_UNIT_DIP, tunaStrokeWidthPress);
    }

    public void setTunaStrokeWidthPress(int unit, float tunaStrokeWidthPress) {
        setTunaStrokeWidthPressRaw(applyDimension(unit, tunaStrokeWidthPress, getViewDisplayMetrics(this)));
    }

    private void setTunaStrokeWidthPressRaw(float tunaStrokeWidthPress) {
        if (this.tunaStrokeWidthPress != tunaStrokeWidthPress) {
            this.tunaStrokeWidthPress = tunaStrokeWidthPress;
            invalidate();
        }
    }

    // tunaStrokeColorPress default tunaStrokeColorNormal
    private int tunaStrokeColorPress;

    public int getTunaStrokeColorPress() {
        return tunaStrokeColorPress;
    }

    public void setTunaStrokeColorPress(int tunaStrokeColorPress) {
        this.tunaStrokeColorPress = tunaStrokeColorPress;
    }

    // tunaStrokeWidthSelect default tunaStrokeWidthNormal
    private float tunaStrokeWidthSelect;

    public float getTunaStrokeWidthSelect() {
        return tunaStrokeWidthSelect;
    }

    public void setTunaStrokeWidthSelect(float tunaStrokeWidthSelect) {
        setTunaStrokeWidthSelect(TypedValue.COMPLEX_UNIT_DIP, tunaStrokeWidthSelect);
    }

    public void setTunaStrokeWidthSelect(int unit, float tunaStrokeWidthSelect) {
        setTunaStrokeWidthSelectRaw(applyDimension(unit, tunaStrokeWidthSelect, getViewDisplayMetrics(this)));
    }

    private void setTunaStrokeWidthSelectRaw(float tunaStrokeWidthSelect) {
        if (this.tunaStrokeWidthSelect != tunaStrokeWidthSelect) {
            this.tunaStrokeWidthSelect = tunaStrokeWidthSelect;
            invalidate();
        }
    }

    // tunaStrokeColorSelect default tunaStrokeColorNormal
    private int tunaStrokeColorSelect;

    public int getTunaStrokeColorSelect() {
        return tunaStrokeColorSelect;
    }

    public void setTunaStrokeColorSelect(int tunaStrokeColorSelect) {
        this.tunaStrokeColorSelect = tunaStrokeColorSelect;
    }

    // tunaRadius default 0
    private float tunaRadius;

    public float getTunaRadius() {
        return tunaRadius;
    }

    public void setTunaRadius(float tunaRadius) {
        setTunaRadius(TypedValue.COMPLEX_UNIT_DIP, tunaRadius);
    }

    public void setTunaRadius(int unit, float tunaRadius) {
        setTunaRadiusRaw(applyDimension(unit, tunaRadius, getViewDisplayMetrics(this)));
    }

    private void setTunaRadiusRaw(float tunaRadius) {
        if (this.tunaRadius != tunaRadius) {
            this.tunaRadius = tunaRadius;
            invalidate();
        }
    }

    // radius of draw custom roundRect
    // tunaRadiusLeftTop,tunaRadiusLeftBottom,tunaRadiusRightTop,tunaRadiusRightBottom default 0
    private float tunaRadiusLeftTop;

    public float getTunaRadiusLeftTop() {
        return tunaRadiusLeftTop;
    }

    public void setTunaRadiusLeftTop(float tunaRadiusLeftTop) {
        setTunaRadiusLeftTop(TypedValue.COMPLEX_UNIT_DIP, tunaRadiusLeftTop);
    }

    public void setTunaRadiusLeftTop(int unit, float tunaRadiusLeftTop) {
        setTunaRadiusLeftTopRaw(applyDimension(unit, tunaRadiusLeftTop, getViewDisplayMetrics(this)));
    }

    private void setTunaRadiusLeftTopRaw(float tunaRadiusLeftTop) {
        if (this.tunaRadiusLeftTop != tunaRadiusLeftTop) {
            this.tunaRadiusLeftTop = tunaRadiusLeftTop;
            invalidate();
        }
    }

    //
    private float tunaRadiusLeftBottom;

    public float getTunaRadiusLeftBottom() {
        return tunaRadiusLeftBottom;
    }

    public void setTunaRadiusLeftBottom(float tunaRadiusLeftBottom) {
        setTunaRadiusLeftBottom(TypedValue.COMPLEX_UNIT_DIP, tunaRadiusLeftBottom);
    }

    public void setTunaRadiusLeftBottom(int unit, float tunaRadiusLeftBottom) {
        setTunaRadiusLeftBottomRaw(applyDimension(unit, tunaRadiusLeftBottom, getViewDisplayMetrics(this)));
    }

    private void setTunaRadiusLeftBottomRaw(float tunaRadiusLeftBottom) {
        if (this.tunaRadiusLeftBottom != tunaRadiusLeftBottom) {
            this.tunaRadiusLeftBottom = tunaRadiusLeftBottom;
            invalidate();
        }
    }

    //
    private float tunaRadiusRightTop;

    public float getTunaRadiusRightTop() {
        return tunaRadiusRightTop;
    }

    public void setTunaRadiusRightTop(float tunaRadiusRightTop) {
        setTunaRadiusRightTop(TypedValue.COMPLEX_UNIT_DIP, tunaRadiusRightTop);
    }

    public void setTunaRadiusRightTop(int unit, float tunaRadiusRightTop) {
        setTunaRadiusRightTopRaw(applyDimension(unit, tunaRadiusRightTop, getViewDisplayMetrics(this)));
    }

    private void setTunaRadiusRightTopRaw(float tunaRadiusRightTop) {
        if (this.tunaRadiusRightTop != tunaRadiusRightTop) {
            this.tunaRadiusRightTop = tunaRadiusRightTop;
            invalidate();
        }
    }

    //
    private float tunaRadiusRightBottom;

    public float getTunaRadiusRightBottom() {
        return tunaRadiusRightBottom;
    }

    public void setTunaRadiusRightBottom(float tunaRadiusRightBottom) {
        setTunaRadiusRightBottom(TypedValue.COMPLEX_UNIT_DIP, tunaRadiusRightBottom);
    }

    public void setTunaRadiusRightBottom(int unit, float tunaRadiusRightBottom) {
        setTunaRadiusRightBottomRaw(applyDimension(unit, tunaRadiusRightBottom, getViewDisplayMetrics(this)));
    }

    private void setTunaRadiusRightBottomRaw(float tunaRadiusRightBottom) {
        if (this.tunaRadiusRightBottom != tunaRadiusRightBottom) {
            this.tunaRadiusRightBottom = tunaRadiusRightBottom;
            invalidate();
        }
    }

    // tunaTextMark default false
    protected boolean tunaTextMark;

    public boolean isTunaTextMark() {
        return tunaTextMark;
    }

    public void setTunaTextMark(boolean tunaTextMark) {
        this.tunaTextMark = tunaTextMark;
        invalidate();
    }

    public void setTunaTextMark(String tunaTextMarkTextValue) {
        this.tunaTextMarkTextValue = tunaTextMarkTextValue;
        requestLayout();
    }

    public void setTunaTextMark(float tunaTextMarkRadius, int tunaTextMarkColor, String tunaTextMarkTextValue, float tunaTextMarkTextSize, int tunaTextMarkTextColor,
                                float tunaTextMarkDx, float tunaTextMarkDy) {
        setTunaTextMark(TypedValue.COMPLEX_UNIT_DIP, tunaTextMarkRadius, tunaTextMarkColor, tunaTextMarkTextValue, TypedValue.COMPLEX_UNIT_DIP, tunaTextMarkTextSize,
                tunaTextMarkTextColor, TypedValue.COMPLEX_UNIT_DIP, tunaTextMarkDx, TypedValue.COMPLEX_UNIT_DIP, tunaTextMarkDy);
    }

    public void setTunaTextMark(int tunaTextMarkRadiusUnit, float tunaTextMarkRadius, int tunaTextMarkColor, String tunaTextMarkTextValue, int tunaTextMarkTextSizeUnit,
                                float tunaTextMarkTextSize, int tunaTextMarkTextColor, int tunaTextMarkDxUnit, float tunaTextMarkDx, int tunaTextMarkDyUnit, float tunaTextMarkDy) {

        DisplayMetrics displayMetrics = getViewDisplayMetrics(this);

        setTunaTextMarkRaw(applyDimension(tunaTextMarkRadiusUnit, tunaTextMarkRadius, displayMetrics), tunaTextMarkColor, tunaTextMarkTextValue,
                applyDimension(tunaTextMarkTextSizeUnit, tunaTextMarkTextSize, displayMetrics), tunaTextMarkTextColor,
                applyDimension(tunaTextMarkDxUnit, tunaTextMarkDx, displayMetrics), applyDimension(tunaTextMarkDyUnit, tunaTextMarkDy, displayMetrics));
    }

    private void setTunaTextMarkRaw(float tunaTextMarkRadius, int tunaTextMarkColor, String tunaTextMarkTextValue, float tunaTextMarkTextSize, int tunaTextMarkTextColor,
                                    float tunaTextMarkDx, float tunaTextMarkDy) {
        if (this.tunaTextMarkRadius != tunaTextMarkRadius || this.tunaTextMarkColor != tunaTextMarkColor || this.tunaTextMarkTextValue != tunaTextMarkTextValue
                || this.tunaTextMarkTextSize != tunaTextMarkTextSize || this.tunaTextMarkTextColor != tunaTextMarkTextColor || this.tunaTextMarkDx != tunaTextMarkDx
                || this.tunaTextMarkDy != tunaTextMarkDy) {
            this.tunaTextMarkRadius = tunaTextMarkRadius;
            this.tunaTextMarkColor = tunaTextMarkColor;
            this.tunaTextMarkTextValue = tunaTextMarkTextValue;
            this.tunaTextMarkTextSize = tunaTextMarkTextSize;
            this.tunaTextMarkTextColor = tunaTextMarkTextColor;
            this.tunaTextMarkDx = tunaTextMarkDx;
            this.tunaTextMarkDy = tunaTextMarkDy;
            this.tunaTextMark = true;
            invalidate();
        }
    }

    // tunaTextMarkTouchable default false
    private boolean tunaTextMarkTouchable;

    public boolean isTunaTextMarkTouchable() {
        return tunaTextMarkTouchable;
    }

    public void setTunaTextMarkTouchable(boolean tunaTextMarkTouchable) {
        this.tunaTextMarkTouchable = tunaTextMarkTouchable;
    }

    // tunaTextMarkRadius default 0
    private float tunaTextMarkRadius;

    public float getTunaTextMarkRadius() {
        return tunaTextMarkRadius;
    }

    public void setTunaTextMarkRadius(float tunaTextMarkRadius) {
        setTunaTextMarkRadius(TypedValue.COMPLEX_UNIT_DIP, tunaTextMarkRadius);
    }

    public void setTunaTextMarkRadius(int unit, float tunaTextMarkRadius) {
        setTunaTextMarkRadiusRaw(applyDimension(unit, tunaTextMarkRadius, getViewDisplayMetrics(this)));
    }

    private void setTunaTextMarkRadiusRaw(float tunaTextMarkRadius) {
        if (this.tunaTextMarkRadius != tunaTextMarkRadius) {
            this.tunaTextMarkRadius = tunaTextMarkRadius;
            invalidate();
        }
    }

    // tunaTextMarkColor default transparent
    private int tunaTextMarkColor;

    public int getTunaTextMarkColor() {
        return tunaTextMarkColor;
    }

    public void setTunaTextMarkColor(int tunaTextMarkColor) {
        this.tunaTextMarkColor = tunaTextMarkColor;
    }

    //
    private String tunaTextMarkTextValue;

    public String getTunaTextMarkTextValue() {
        return tunaTextMarkTextValue;
    }

    public void setTunaTextMarkTextValue(String tunaTextMarkTextValue) {
        this.tunaTextMarkTextValue = tunaTextMarkTextValue;
        requestLayout();
    }

    private List<Integer> tunaTextMarkTextValueMeasureList;

    public List<Integer> getTunaTextMarkTextValueMeasureList() {
        return tunaTextMarkTextValueMeasureList;
    }

    public void setTunaTextMarkTextValueMeasureList(List<Integer> tunaTextMarkTextValueMeasureList) {
        this.tunaTextMarkTextValueMeasureList = tunaTextMarkTextValueMeasureList;
    }

    //
    private float tunaTextMarkTextSize;

    public float getTunaTextMarkTextSize() {
        return tunaTextMarkTextSize;
    }

    public void setTunaTextMarkTextSize(float tunaTextMarkTextSize) {
        setTunaTextMarkTextSize(TypedValue.COMPLEX_UNIT_DIP, tunaTextMarkTextSize);
    }

    public void setTunaTextMarkTextSize(int unit, float tunaTextMarkTextSize) {
        setTunaTextMarkTextSizeRaw(applyDimension(unit, tunaTextMarkTextSize, getViewDisplayMetrics(this)));
    }

    private void setTunaTextMarkTextSizeRaw(float tunaTextMarkTextSize) {
        if (this.tunaTextMarkTextSize != tunaTextMarkTextSize) {
            this.tunaTextMarkTextSize = tunaTextMarkTextSize;
            invalidate();
        }
    }

    //
    private int tunaTextMarkTextColor;

    public int getTunaTextMarkTextColor() {
        return tunaTextMarkTextColor;
    }

    public void setTunaTextMarkTextColor(int tunaTextMarkTextColor) {
        this.tunaTextMarkTextColor = tunaTextMarkTextColor;
    }

    //
    private float tunaTextMarkDx;

    public float getTunaTextMarkDx() {
        return tunaTextMarkDx;
    }

    public void setTunaTextMarkDx(float tunaTextMarkDx) {
        setTunaTextMarkDx(TypedValue.COMPLEX_UNIT_DIP, tunaTextMarkDx);
    }

    public void setTunaTextMarkDx(int unit, float tunaTextMarkDx) {
        setTunaTextMarkDxRaw(applyDimension(unit, tunaTextMarkDx, getViewDisplayMetrics(this)));
    }

    private void setTunaTextMarkDxRaw(float tunaTextMarkDx) {
        if (this.tunaTextMarkDx != tunaTextMarkDx) {
            this.tunaTextMarkDx = tunaTextMarkDx;
            invalidate();
        }
    }

    //
    private float tunaTextMarkDy;

    public float getTunaTextMarkDy() {
        return tunaTextMarkDy;
    }

    public void setTunaTextMarkDy(float tunaTextMarkDy) {
        setTunaTextMarkDy(TypedValue.COMPLEX_UNIT_DIP, tunaTextMarkDy);
    }

    public void setTunaTextMarkDy(int unit, float tunaTextMarkDy) {
        setTunaTextMarkDyRaw(applyDimension(unit, tunaTextMarkDy, getViewDisplayMetrics(this)));
    }

    private void setTunaTextMarkDyRaw(float tunaTextMarkDy) {
        if (this.tunaTextMarkDy != tunaTextMarkDy) {
            this.tunaTextMarkDy = tunaTextMarkDy;
            invalidate();
        }
    }

    //
    private float tunaTextMarkFractionDx;

    public float getTunaTextMarkFractionDx() {
        return tunaTextMarkFractionDx;
    }

    public void setTunaTextMarkFractionDx(float tunaTextMarkFractionDx) {
        this.tunaTextMarkFractionDx = tunaTextMarkFractionDx;
    }

    //
    private float tunaTextMarkFractionDy;

    public float getTunaTextMarkFractionDy() {
        return tunaTextMarkFractionDy;
    }

    public void setTunaTextMarkFractionDy(float tunaTextMarkFractionDy) {
        this.tunaTextMarkFractionDy = tunaTextMarkFractionDy;
    }

    // tunaTextValue default null
    private String tunaTextValue;

    public String getTunaTextValue() {
        return tunaTextValue;
    }

    public void setTunaTextValue(String tunaTextValue) {
        this.tunaTextValue = tunaTextValue;
        requestLayout();
    }

    private List<Integer> tunaTextValueMeasureList;

    public List<Integer> getTunaTextValueMeasureList() {
        return tunaTextValueMeasureList;
    }

    public void setTunaTextValueMeasureList(List<Integer> tunaTextValueMeasureList) {
        this.tunaTextValueMeasureList = tunaTextValueMeasureList;
    }

    // tunaTextSize default 0
    private float tunaTextSize;

    public float getTunaTextSize() {
        return tunaTextSize;
    }

    public void setTunaTextSize(float tunaTextSize) {
        setTunaTextSize(TypedValue.COMPLEX_UNIT_DIP, tunaTextSize);
    }

    public void setTunaTextSize(int unit, float tunaTextSize) {
        setTunaTextSizeRaw(applyDimension(unit, tunaTextSize, getViewDisplayMetrics(this)));
    }

    private void setTunaTextSizeRaw(float tunaTextSize) {
        if (this.tunaTextSize != tunaTextSize) {
            this.tunaTextSize = tunaTextSize;
            invalidate();
        }
    }

    // tunaTextColorNormal default transparent
    private int tunaTextColorNormal;

    public int getTunaTextColorNormal() {
        return tunaTextColorNormal;
    }

    public void setTunaTextColorNormal(int tunaTextColorNormal) {
        this.tunaTextColorNormal = tunaTextColorNormal;
    }

    // tunaTextColorPress default tunaTextColorNormal
    private int tunaTextColorPress;

    public int getTunaTextColorPress() {
        return tunaTextColorPress;
    }

    public void setTunaTextColorPress(int tunaTextColorPress) {
        this.tunaTextColorPress = tunaTextColorPress;
    }

    // tunaTextColorSelect default tunaTextColorNormal
    private int tunaTextColorSelect;

    public int getTunaTextColorSelect() {
        return tunaTextColorSelect;
    }

    public void setTunaTextColorSelect(int tunaTextColorSelect) {
        this.tunaTextColorSelect = tunaTextColorSelect;
    }

    // tunaTextPaddingLeft means distance between tunaSrcLeft and The
    // leftmost,note about the tunaSrcLeftPadding
    private float tunaTextPaddingLeft;

    public float getTunaTextPaddingLeft() {
        return tunaTextPaddingLeft;
    }

    public void setTunaTextPaddingLeft(float tunaTextPaddingLeft) {
        setTunaTextPaddingLeft(TypedValue.COMPLEX_UNIT_DIP, tunaTextPaddingLeft);
    }

    public void setTunaTextPaddingLeft(int unit, float tunaTextPaddingLeft) {
        setTunaTextPaddingLeftRaw(applyDimension(unit, tunaTextPaddingLeft, getViewDisplayMetrics(this)));
    }

    private void setTunaTextPaddingLeftRaw(float tunaTextPaddingLeft) {
        if (this.tunaTextPaddingLeft != tunaTextPaddingLeft) {
            this.tunaTextPaddingLeft = tunaTextPaddingLeft;
            invalidate();
        }
    }

    // tunaTextPaddingRight means distance between tunaSrcRight and The
    // rightmost,note about the tunaSrcRightPadding
    private float tunaTextPaddingRight;

    public float getTunaTextPaddingRight() {
        return tunaTextPaddingRight;
    }

    public void setTunaTextPaddingRight(float tunaTextPaddingRight) {
        setTunaTextPaddingRight(TypedValue.COMPLEX_UNIT_DIP, tunaTextPaddingRight);
    }

    public void setTunaTextPaddingRight(int unit, float tunaTextPaddingRight) {
        setTunaTextPaddingRightRaw(applyDimension(unit, tunaTextPaddingRight, getViewDisplayMetrics(this)));
    }

    private void setTunaTextPaddingRightRaw(float tunaTextPaddingRight) {
        if (this.tunaTextPaddingRight != tunaTextPaddingRight) {
            this.tunaTextPaddingRight = tunaTextPaddingRight;
            invalidate();
        }
    }

    //
    private float tunaTextRowSpaceRatio = 1.0f;

    public float getTunaTextRowSpaceRatio() {
        return tunaTextRowSpaceRatio;
    }

    public void setTunaTextRowSpaceRatio(float tunaTextRowSpaceRatio) {
        this.tunaTextRowSpaceRatio = tunaTextRowSpaceRatio;
    }

    //
    private TunaTextGravity tunaTextGravity;

    public enum TunaTextGravity {
        ALL_CENTER(0), ALL_LEFT(1), CENTER_LEFT(2), LEFT_CENTER(3);
        final int nativeInt;

        TunaTextGravity(int ni) {
            nativeInt = ni;
        }
    }

    private static final TunaTextGravity[] tunaTextGravityArray = {TunaTextGravity.ALL_CENTER, TunaTextGravity.ALL_LEFT, TunaTextGravity.CENTER_LEFT, TunaTextGravity.LEFT_CENTER,};

    public TunaTextGravity getTunaTextGravity() {
        return tunaTextGravity;
    }

    public void setTunaTextGravity(TunaTextGravity tunaTextGravity) {
        this.tunaTextGravity = tunaTextGravity;
    }

    //
    private Typeface tunaTextTypeFace;

    public enum TunaTextTypeFace {
        NORMAL(0), BOLD(1), ITALIC(2), BOLD_ITALIC(3);
        final int nativeInt;

        TunaTextTypeFace(int ni) {
            nativeInt = ni;
        }
    }

    private static final int[] tunaTextTypeFaceArray = {Typeface.NORMAL, Typeface.BOLD, Typeface.ITALIC, Typeface.BOLD_ITALIC};

    public Typeface getTunaTextTypeFace() {
        return tunaTextTypeFace;
    }

    public void setTunaTextTypeFace(Typeface tunaTextTypeFace) {
        this.tunaTextTypeFace = tunaTextTypeFace;
    }

    // attention that tunaTextDx is the width of the base , tunaTextDy is the
    // height of the base
    private float tunaTextDx;

    public float getTunaTextDx() {
        return tunaTextDx;
    }

    public void setTunaTextDx(float tunaTextDx) {
        setTunaTextDx(TypedValue.COMPLEX_UNIT_DIP, tunaTextDx);
    }

    public void setTunaTextDx(int unit, float tunaTextDx) {
        setTunaTextDxRaw(applyDimension(unit, tunaTextDx, getViewDisplayMetrics(this)));
    }

    private void setTunaTextDxRaw(float tunaTextDx) {
        if (this.tunaTextDx != tunaTextDx) {
            this.tunaTextDx = tunaTextDx;
            invalidate();
        }
    }

    //
    private float tunaTextDy;

    public float getTunaTextDy() {
        return tunaTextDy;
    }

    public void setTunaTextDy(float tunaTextDy) {
        setTunaTextDy(TypedValue.COMPLEX_UNIT_DIP, tunaTextDy);
    }

    public void setTunaTextDy(int unit, float tunaTextDy) {
        setTunaTextDyRaw(applyDimension(unit, tunaTextDy, getViewDisplayMetrics(this)));
    }

    private void setTunaTextDyRaw(float tunaTextDy) {
        if (this.tunaTextDy != tunaTextDy) {
            this.tunaTextDy = tunaTextDy;
            invalidate();
        }
    }

    //
    private float tunaTextFractionDx;

    public float getTunaTextFractionDx() {
        return tunaTextFractionDx;
    }

    public void setTunaTextFractionDx(float tunaTextFractionDx) {
        this.tunaTextFractionDx = tunaTextFractionDx;
    }

    //
    private float tunaTextFractionDy;

    public float getTunaTextFractionDy() {
        return tunaTextFractionDy;
    }

    public void setTunaTextFractionDy(float tunaTextFractionDy) {
        this.tunaTextFractionDy = tunaTextFractionDy;
    }

    //
    private float tunaTextDrawWidth;
    private float tunaTextEndOffsetCenterX;
    private float tunaTextEndOffsetCenterY;

    //
    private float tunaTextShadowRadius;

    public float getTunaTextShadowRadius() {
        return tunaTextShadowRadius;
    }

    public void setTunaTextShadowRadius(float tunaTextShadowRadius) {
        setTunaTextShadowRadius(TypedValue.COMPLEX_UNIT_DIP, tunaTextShadowRadius);
    }

    public void setTunaTextShadowRadius(int unit, float tunaTextShadowRadius) {
        setTunaTextShadowRadiusRaw(applyDimension(unit, tunaTextShadowRadius, getViewDisplayMetrics(this)));
    }

    private void setTunaTextShadowRadiusRaw(float tunaTextShadowRadius) {
        if (this.tunaTextShadowRadius != tunaTextShadowRadius) {
            this.tunaTextShadowRadius = tunaTextShadowRadius;
            invalidate();
        }
    }

    //
    private int tunaTextShadowColor;

    public int getTunaTextShadowColor() {
        return tunaTextShadowColor;
    }

    public void setTunaTextShadowColor(int tunaTextShadowColor) {
        this.tunaTextShadowColor = tunaTextShadowColor;
    }

    //
    private float tunaTextShadowDx;

    public float getTunaTextShadowDx() {
        return tunaTextShadowDx;
    }

    public void setTunaTextShadowDx(float tunaTextShadowDx) {
        setTunaTextShadowDx(TypedValue.COMPLEX_UNIT_DIP, tunaTextShadowDx);
    }

    public void setTunaTextShadowDx(int unit, float tunaTextShadowDx) {
        setTunaTextShadowDxRaw(applyDimension(unit, tunaTextShadowDx, getViewDisplayMetrics(this)));
    }

    private void setTunaTextShadowDxRaw(float tunaTextShadowDx) {
        if (this.tunaTextShadowDx != tunaTextShadowDx) {
            this.tunaTextShadowDx = tunaTextShadowDx;
            invalidate();
        }
    }

    //
    private float tunaTextShadowDy;

    public float getTunaTextShadowDy() {
        return tunaTextShadowDy;
    }

    public void setTunaTextShadowDy(float tunaTextShadowDy) {
        setTunaTextShadowDy(TypedValue.COMPLEX_UNIT_DIP, tunaTextShadowDy);
    }

    public void setTunaTextShadowDy(int unit, float tunaTextShadowDy) {
        setTunaTextShadowDyRaw(applyDimension(unit, tunaTextShadowDy, getViewDisplayMetrics(this)));
    }

    private void setTunaTextShadowDyRaw(float tunaTextShadowDy) {
        if (this.tunaTextShadowDy != tunaTextShadowDy) {
            this.tunaTextShadowDy = tunaTextShadowDy;
            invalidate();
        }
    }

    // tunaContentValue default null
    private String tunaContentValue;

    public String getTunaContentValue() {
        return tunaContentValue;
    }

    public void setTunaContentValue(String tunaContentValue) {
        this.tunaContentValue = tunaContentValue;
        requestLayout();
    }

    private List<Integer> tunaContentValueMeasureList;

    public List<Integer> getTunaContentValueMeasureList() {
        return tunaContentValueMeasureList;
    }

    public void setTunaContentValueMeasureList(List<Integer> tunaContentValueMeasureList) {
        this.tunaContentValueMeasureList = tunaContentValueMeasureList;
    }

    // tunaContentSize default 0
    private float tunaContentSize;

    public float getTunaContentSize() {
        return tunaContentSize;
    }

    public void setTunaContentSize(float tunaContentSize) {
        setTunaContentSize(TypedValue.COMPLEX_UNIT_DIP, tunaContentSize);
    }

    public void setTunaContentSize(int unit, float tunaContentSize) {
        setTunaContentSizeRaw(applyDimension(unit, tunaContentSize, getViewDisplayMetrics(this)));
    }

    private void setTunaContentSizeRaw(float tunaContentSize) {
        if (this.tunaContentSize != tunaContentSize) {
            this.tunaContentSize = tunaContentSize;
            invalidate();
        }
    }

    //
    private float tunaContentShadowRadius;

    public float getTunaContentShadowRadius() {
        return tunaContentShadowRadius;
    }

    public void setTunaContentShadowRadius(float tunaContentShadowRadius) {
        setTunaContentShadowRadius(TypedValue.COMPLEX_UNIT_DIP, tunaContentShadowRadius);
    }

    public void setTunaContentShadowRadius(int unit, float tunaContentShadowRadius) {
        setTunaContentShadowRadiusRaw(applyDimension(unit, tunaContentShadowRadius, getViewDisplayMetrics(this)));
    }

    private void setTunaContentShadowRadiusRaw(float tunaContentShadowRadius) {
        if (this.tunaContentShadowRadius != tunaContentShadowRadius) {
            this.tunaContentShadowRadius = tunaContentShadowRadius;
            invalidate();
        }
    }

    //
    private int tunaContentShadowColor;

    public int getTunaContentShadowColor() {
        return tunaContentShadowColor;
    }

    public void setTunaContentShadowColor(int tunaContentShadowColor) {
        this.tunaContentShadowColor = tunaContentShadowColor;
    }

    //
    private float tunaContentShadowDx;

    public float getTunaContentShadowDx() {
        return tunaContentShadowDx;
    }

    public void setTunaContentShadowDx(float tunaContentShadowDx) {
        setTunaContentShadowDx(TypedValue.COMPLEX_UNIT_DIP, tunaContentShadowDx);
    }

    public void setTunaContentShadowDx(int unit, float tunaContentShadowDx) {
        setTunaContentShadowDxRaw(applyDimension(unit, tunaContentShadowDx, getViewDisplayMetrics(this)));
    }

    private void setTunaContentShadowDxRaw(float tunaContentShadowDx) {
        if (this.tunaContentShadowDx != tunaContentShadowDx) {
            this.tunaContentShadowDx = tunaContentShadowDx;
            invalidate();
        }
    }

    //
    private float tunaContentShadowDy;

    public float getTunaContentShadowDy() {
        return tunaContentShadowDy;
    }

    public void setTunaContentShadowDy(float tunaContentShadowDy) {
        setTunaContentShadowDy(TypedValue.COMPLEX_UNIT_DIP, tunaContentShadowDy);
    }

    public void setTunaContentShadowDy(int unit, float tunaContentShadowDy) {
        setTunaContentShadowDyRaw(applyDimension(unit, tunaContentShadowDy, getViewDisplayMetrics(this)));
    }

    private void setTunaContentShadowDyRaw(float tunaContentShadowDy) {
        if (this.tunaContentShadowDy != tunaContentShadowDy) {
            this.tunaContentShadowDy = tunaContentShadowDy;
            invalidate();
        }
    }

    // tunaContentColorNormal default transparent
    private int tunaContentColorNormal;

    public int getTunaContentColorNormal() {
        return tunaContentColorNormal;
    }

    public void setTunaContentColorNormal(int tunaContentColorNormal) {
        this.tunaContentColorNormal = tunaContentColorNormal;
    }

    // tunaContentColorPress default tunaContentColorNormal
    private int tunaContentColorPress;

    public int getTunaContentColorPress() {
        return tunaContentColorPress;
    }

    public void setTunaContentColorPress(int tunaContentColorPress) {
        this.tunaContentColorPress = tunaContentColorPress;
    }

    // tunaContentColorSelect default tunaContentColorNormal
    private int tunaContentColorSelect;

    public int getTunaContentColorSelect() {
        return tunaContentColorSelect;
    }

    public void setTunaContentColorSelect(int tunaContentColorSelect) {
        this.tunaContentColorSelect = tunaContentColorSelect;
    }

    // tunaContentPaddingLeft means distance between tunaSrcLeft and The
    // leftmost,note about the tunaSrcLeftPadding
    private float tunaContentPaddingLeft;

    public float getTunaContentPaddingLeft() {
        return tunaContentPaddingLeft;
    }

    public void setTunaContentPaddingLeft(float tunaContentPaddingLeft) {
        setTunaContentPaddingLeft(TypedValue.COMPLEX_UNIT_DIP, tunaContentPaddingLeft);
    }

    public void setTunaContentPaddingLeft(int unit, float tunaContentPaddingLeft) {
        setTunaContentPaddingLeftRaw(applyDimension(unit, tunaContentPaddingLeft, getViewDisplayMetrics(this)));
    }

    private void setTunaContentPaddingLeftRaw(float tunaContentPaddingLeft) {
        if (this.tunaContentPaddingLeft != tunaContentPaddingLeft) {
            this.tunaContentPaddingLeft = tunaContentPaddingLeft;
            invalidate();
        }
    }

    // tunaContentPaddingRight means distance between tunaSrcRight and The
    // rightmost,note about the tunaSrcRightPadding
    private float tunaContentPaddingRight;

    public float getTunaContentPaddingRight() {
        return tunaContentPaddingRight;
    }

    public void setTunaContentPaddingRight(float tunaContentPaddingRight) {
        setTunaContentPaddingRight(TypedValue.COMPLEX_UNIT_DIP, tunaContentPaddingRight);
    }

    public void setTunaContentPaddingRight(int unit, float tunaContentPaddingRight) {
        setTunaContentPaddingRightRaw(applyDimension(unit, tunaContentPaddingRight, getViewDisplayMetrics(this)));
    }

    private void setTunaContentPaddingRightRaw(float tunaContentPaddingRight) {
        if (this.tunaContentPaddingRight != tunaContentPaddingRight) {
            this.tunaContentPaddingRight = tunaContentPaddingRight;
            invalidate();
        }
    }

    private float tunaContentRowSpaceRatio = 1.0f;

    public float getTunaContentRowSpaceRatio() {
        return tunaContentRowSpaceRatio;
    }

    public void setTunaContentRowSpaceRatio(float tunaContentRowSpaceRatio) {
        this.tunaContentRowSpaceRatio = tunaContentRowSpaceRatio;
    }

    //
    private TunaContentGravity tunaContentGravity;

    public enum TunaContentGravity {
        ALL_CENTER(0), ALL_LEFT(1), CENTER_LEFT(2), LEFT_CENTER(3);
        final int nativeInt;

        TunaContentGravity(int ni) {
            nativeInt = ni;
        }
    }

    private static final TunaContentGravity[] tunaContentGravityArray = {TunaContentGravity.ALL_CENTER, TunaContentGravity.ALL_LEFT, TunaContentGravity.CENTER_LEFT, TunaContentGravity.LEFT_CENTER,};

    public TunaContentGravity getTunaContentGravity() {
        return tunaContentGravity;
    }

    public void setTunaContentGravity(TunaContentGravity tunaContentGravity) {
        this.tunaContentGravity = tunaContentGravity;
    }

    //
    private Typeface tunaContentTypeFace;

    public enum TunaContentTypeFace {
        NORMAL(0), BOLD(1), ITALIC(2), BOLD_ITALIC(3);
        final int nativeInt;

        TunaContentTypeFace(int ni) {
            nativeInt = ni;
        }
    }

    private static final int[] tunaContentTypeFaceArray = {Typeface.NORMAL, Typeface.BOLD, Typeface.ITALIC, Typeface.BOLD_ITALIC,};

    public Typeface getTunaContentTypeFace() {
        return tunaContentTypeFace;
    }

    public void Typeface(Typeface tunaContentTypeFace) {
        this.tunaContentTypeFace = tunaContentTypeFace;
    }

    // attention that tunaContentDx is the width of the base , tunaContentDy is
    // the height of the base
    private float tunaContentDx;

    public float getTunaContenttDx() {
        return tunaContentDx;
    }

    public void setTunaContentDx(float tunaContentDx) {
        setTunaTextDx(TypedValue.COMPLEX_UNIT_DIP, tunaContentDx);
    }

    public void setTunaContentDx(int unit, float tunaContentDx) {
        setTunaContentDxRaw(applyDimension(unit, tunaContentDx, getViewDisplayMetrics(this)));
    }

    private void setTunaContentDxRaw(float tunaContentDx) {
        if (this.tunaContentDx != tunaContentDx) {
            this.tunaContentDx = tunaContentDx;
            invalidate();
        }
    }

    //
    private float tunaContentDy;

    public float getTunaContentDy() {
        return tunaContentDy;
    }

    public void setTunaContentDy(float tunaContentDy) {
        setTunaContentDy(TypedValue.COMPLEX_UNIT_DIP, tunaContentDy);
    }

    public void setTunaContentDy(int unit, float tunaContentDy) {
        setTunaContentDyRaw(applyDimension(unit, tunaContentDy, getViewDisplayMetrics(this)));
    }

    private void setTunaContentDyRaw(float tunaContentDy) {
        if (this.tunaContentDy != tunaContentDy) {
            this.tunaContentDy = tunaContentDy;
            invalidate();
        }
    }

    //
    private float tunaContentFractionDx;

    public float getTunaContentFractionDx() {
        return tunaContentFractionDx;
    }

    public void setTunaContentFractionDx(float tunaContentFractionDx) {
        this.tunaContentFractionDx = tunaContentFractionDx;
    }

    //
    private float tunaContentFractionDy;

    public float getTunaContentFractionDy() {
        return tunaContentFractionDy;
    }

    public void setTunaContentFractionDy(float tunaContentFractionDy) {
        this.tunaContentFractionDy = tunaContentFractionDy;
    }

    // tunaContentMark default false
    protected boolean tunaContentMark;

    public boolean isTunaContentMark() {
        return tunaContentMark;
    }

    public void setTunaContentMark(boolean tunaContentMark) {
        this.tunaContentMark = tunaContentMark;
        invalidate();
    }

    public void setTunaContentMark(String tunaContentMarkTextValue) {
        this.tunaContentMarkTextValue = tunaContentMarkTextValue;
        invalidate();
    }

    public void setTunaContentMark(float tunaContentMarkRadius, int tunaContentMarkColor, String tunaContentMarkTextValue, float tunaContentMarkTextSize,
                                   int tunaContentMarkTextColor, float tunaContentMarkDx, float tunaContentMarkDy) {
        setTunaContentMark(TypedValue.COMPLEX_UNIT_DIP, tunaContentMarkRadius, tunaContentMarkColor, tunaContentMarkTextValue, TypedValue.COMPLEX_UNIT_DIP,
                tunaContentMarkTextSize, tunaContentMarkTextColor, TypedValue.COMPLEX_UNIT_DIP, tunaContentMarkDx, TypedValue.COMPLEX_UNIT_DIP, tunaContentMarkDy);
    }

    public void setTunaContentMark(int tunaContentMarkRadiusUnit, float tunaContentMarkRadius, int tunaContentMarkColor, String tunaContentMarkTextValue,
                                   int tunaContentMarkTextSizeUnit, float tunaContentMarkTextSize, int tunaContentMarkTextColor, int tunaContentMarkDxUnit, float tunaContentMarkDx,
                                   int tunaContentMarkDyUnit, float tunaContentMarkDy) {

        DisplayMetrics displayMetrics = getViewDisplayMetrics(this);

        setTunaContentMarkRaw(applyDimension(tunaContentMarkRadiusUnit, tunaContentMarkRadius, displayMetrics), tunaContentMarkColor, tunaContentMarkTextValue,
                applyDimension(tunaContentMarkTextSizeUnit, tunaContentMarkTextSize, displayMetrics), tunaContentMarkTextColor,
                applyDimension(tunaContentMarkDxUnit, tunaContentMarkDx, displayMetrics), applyDimension(tunaContentMarkDyUnit, tunaContentMarkDy, displayMetrics));
    }

    private void setTunaContentMarkRaw(float tunaContentMarkRadius, int tunaContentMarkColor, String tunaContentMarkTextValue, float tunaContentMarkTextSize,
                                       int tunaContentMarkTextColor, float tunaContentMarkDx, float tunaContentMarkDy) {
        if (this.tunaContentMarkRadius != tunaContentMarkRadius || this.tunaContentMarkColor != tunaContentMarkColor || this.tunaContentMarkTextValue != tunaContentMarkTextValue
                || this.tunaContentMarkTextSize != tunaContentMarkTextSize || this.tunaContentMarkTextColor != tunaContentMarkTextColor
                || this.tunaContentMarkDx != tunaContentMarkDx || this.tunaContentMarkDy != tunaContentMarkDy) {
            this.tunaContentMarkRadius = tunaContentMarkRadius;
            this.tunaContentMarkColor = tunaContentMarkColor;
            this.tunaContentMarkTextValue = tunaContentMarkTextValue;
            this.tunaContentMarkTextSize = tunaContentMarkTextSize;
            this.tunaContentMarkTextColor = tunaContentMarkTextColor;
            this.tunaContentMarkDx = tunaContentMarkDx;
            this.tunaContentMarkDy = tunaContentMarkDy;
            this.tunaContentMark = true;
            invalidate();
        }
    }

    // tunaContentMarkTouchable default false
    private boolean tunaContentMarkTouchable;

    public boolean isTunaContentMarkTouchable() {
        return tunaContentMarkTouchable;
    }

    public void setTunaContentMarkTouchable(boolean tunaContentMarkTouchable) {
        this.tunaContentMarkTouchable = tunaContentMarkTouchable;
    }

    // tunaContentMarkRadius default 0
    private float tunaContentMarkRadius;

    public float getTunaContentMarkRadius() {
        return tunaContentMarkRadius;
    }

    public void setTunaContentMarkRadius(float tunaContentMarkRadius) {
        setTunaContentMarkRadius(TypedValue.COMPLEX_UNIT_DIP, tunaContentMarkRadius);
    }

    public void setTunaContentMarkRadius(int unit, float tunaContentMarkRadius) {
        setTunaContentMarkRadiusRaw(applyDimension(unit, tunaContentMarkRadius, getViewDisplayMetrics(this)));
    }

    private void setTunaContentMarkRadiusRaw(float tunaContentMarkRadius) {
        if (this.tunaContentMarkRadius != tunaContentMarkRadius) {
            this.tunaContentMarkRadius = tunaContentMarkRadius;
            invalidate();
        }
    }

    // tunaContentMarkColor default transparent
    private int tunaContentMarkColor;

    public int getTunaContentMarkColor() {
        return tunaContentMarkColor;
    }

    public void setTunaContentMarkColor(int tunaContentMarkColor) {
        this.tunaContentMarkColor = tunaContentMarkColor;
    }

    //
    private String tunaContentMarkTextValue;

    public String getTunaContentMarkTextValue() {
        return tunaContentMarkTextValue;
    }

    public void setTunaContentMarkTextValue(String tunaContentMarkTextValue) {
        this.tunaContentMarkTextValue = tunaContentMarkTextValue;
        requestLayout();
    }

    private List<Integer> tunaContentMarkTextValueMeasureList;

    public List<Integer> getTunaContentMarkTextValueMeasureList() {
        return tunaContentMarkTextValueMeasureList;
    }

    public void setTunaContentMarkTextValueMeasureList(List<Integer> tunaContentMarkTextValueMeasureList) {
        this.tunaContentMarkTextValueMeasureList = tunaContentMarkTextValueMeasureList;
    }

    //
    private float tunaContentMarkTextSize;

    public float getTunaContentMarkTextSize() {
        return tunaContentMarkTextSize;
    }

    public void setTunaContentMarkTextSize(float tunaContentMarkTextSize) {
        setTunaContentMarkTextSize(TypedValue.COMPLEX_UNIT_DIP, tunaContentMarkTextSize);
    }

    public void setTunaContentMarkTextSize(int unit, float tunaContentMarkTextSize) {
        setTunaContentMarkTextSizeRaw(applyDimension(unit, tunaContentMarkTextSize, getViewDisplayMetrics(this)));
    }

    private void setTunaContentMarkTextSizeRaw(float tunaContentMarkTextSize) {
        if (this.tunaContentMarkTextSize != tunaContentMarkTextSize) {
            this.tunaContentMarkTextSize = tunaContentMarkTextSize;
            invalidate();
        }
    }

    //
    private int tunaContentMarkTextColor;

    public int getTunaContentMarkTextColor() {
        return tunaContentMarkTextColor;
    }

    public void setTunaContentMarkTextColor(int tunaContentMarkTextColor) {
        this.tunaContentMarkTextColor = tunaContentMarkTextColor;
    }

    //
    private float tunaContentMarkDx;

    public float getTunaContentMarkDx() {
        return tunaContentMarkDx;
    }

    public void setTunaContentMarkDx(float tunaContentMarkDx) {
        setTunaContentMarkDx(TypedValue.COMPLEX_UNIT_DIP, tunaContentMarkDx);
    }

    public void setTunaContentMarkDx(int unit, float tunaContentMarkDx) {
        setTunaContentMarkDxRaw(applyDimension(unit, tunaContentMarkDx, getViewDisplayMetrics(this)));
    }

    private void setTunaContentMarkDxRaw(float tunaContentMarkDx) {
        if (this.tunaContentMarkDx != tunaContentMarkDx) {
            this.tunaContentMarkDx = tunaContentMarkDx;
            invalidate();
        }
    }

    //
    private float tunaContentMarkDy;

    public float getTunaContentMarkDy() {
        return tunaContentMarkDy;
    }

    public void setTunaContentMarkDy(float tunaContentMarkDy) {
        setTunaContentMarkDy(TypedValue.COMPLEX_UNIT_DIP, tunaContentMarkDy);
    }

    public void setTunaContentMarkDy(int unit, float tunaContentMarkDy) {
        setTunaContentMarkDyRaw(applyDimension(unit, tunaContentMarkDy, getViewDisplayMetrics(this)));
    }

    private void setTunaContentMarkDyRaw(float tunaContentMarkDy) {
        if (this.tunaContentMarkDy != tunaContentMarkDy) {
            this.tunaContentMarkDy = tunaContentMarkDy;
            invalidate();
        }
    }

    //
    private float tunaContentMarkFractionDx;

    public float getTunaContentMarkFractionDx() {
        return tunaContentMarkFractionDx;
    }

    public void setTunaContentMarkFractionDx(float tunaContentMarkFractionDx) {
        this.tunaContentMarkFractionDx = tunaContentMarkFractionDx;
    }

    //
    private float tunaContentMarkFractionDy;

    public float getTunaContentMarkFractionDy() {
        return tunaContentMarkFractionDy;
    }

    public void setTunaContentMarkFractionDy(float tunaContentMarkFractionDy) {
        this.tunaContentMarkFractionDy = tunaContentMarkFractionDy;
    }

    //
    private float tunaContentDrawWidth;
    private float tunaContentEndOffsetCenterX;
    private float tunaContentEndOffsetCenterY;

    //
    private Bitmap tunaSrcLeft;

    public Bitmap getTunaSrcLeft() {
        return tunaSrcLeft;
    }

    public void setTunaSrcLeft(Bitmap tunaSrcLeft) {
        this.tunaSrcLeft = tunaSrcLeft;
    }

    //
    private float tunaSrcLeftWidth;

    public float getTunaSrcLeftWidth() {
        return tunaSrcLeftWidth;
    }

    public void setTunaSrcLeftWidth(float tunaSrcLeftWidth) {
        setTunaSrcLeftWidth(TypedValue.COMPLEX_UNIT_DIP, tunaSrcLeftWidth);
    }

    public void setTunaSrcLeftWidth(int unit, float tunaSrcLeftWidth) {
        setTunaSrcLeftWidthRaw(applyDimension(unit, tunaSrcLeftWidth, getViewDisplayMetrics(this)));
    }

    private void setTunaSrcLeftWidthRaw(float tunaSrcLeftWidth) {
        if (this.tunaSrcLeftWidth != tunaSrcLeftWidth) {
            this.tunaSrcLeftWidth = tunaSrcLeftWidth;
            invalidate();
        }
    }

    //
    private float tunaSrcLeftHeight;

    public float getTunaSrcLeftHeight() {
        return tunaSrcLeftHeight;
    }

    public void setTunaSrcLeftHeight(float tunaSrcLeftHeight) {
        setTunaSrcLeftHeight(TypedValue.COMPLEX_UNIT_DIP, tunaSrcLeftHeight);
    }

    public void setTunaSrcLeftHeight(int unit, float tunaSrcLeftHeight) {
        setTunaSrcLeftHeightRaw(applyDimension(unit, tunaSrcLeftHeight, getViewDisplayMetrics(this)));
    }

    private void setTunaSrcLeftHeightRaw(float tunaSrcLeftHeight) {
        if (this.tunaSrcLeftHeight != tunaSrcLeftHeight) {
            this.tunaSrcLeftHeight = tunaSrcLeftHeight;
            invalidate();
        }
    }

    //
    protected Matrix tunaLeftMatrix;

    public Matrix getTunaLeftMatrix() {
        return tunaLeftMatrix;
    }

    public void setTunaLeftMatrix(Matrix tunaLeftMatrix) {
        this.tunaLeftMatrix = tunaLeftMatrix;
    }

    protected Matrix initTunaLeftMatrix(float sx, float sy) {
        if (tunaLeftMatrix == null) {
            tunaLeftMatrix = new Matrix();
        }
        tunaLeftMatrix.reset();
        tunaLeftMatrix.setScale(sx, sy);
        return tunaLeftMatrix;
    }

    // tunaSrcLeftPadding means distance between tunaSrcLeft and textview,note
    // about the tunaTextPaddingLeft
    private float tunaSrcLeftPadding;

    public float getTunaSrcLeftPadding() {
        return tunaSrcLeftPadding;
    }

    public void setTunaSrcLeftPadding(float tunaSrcLeftPadding) {
        setTunaSrcLeftPadding(TypedValue.COMPLEX_UNIT_DIP, tunaSrcLeftPadding);
    }

    public void setTunaSrcLeftPadding(int unit, float tunaSrcLeftPadding) {
        setTunaSrcLeftPaddingRaw(applyDimension(unit, tunaSrcLeftPadding, getViewDisplayMetrics(this)));
    }

    private void setTunaSrcLeftPaddingRaw(float tunaSrcLeftPadding) {
        if (this.tunaSrcLeftPadding != tunaSrcLeftPadding) {
            this.tunaSrcLeftPadding = tunaSrcLeftPadding;
            invalidate();
        }
    }

    //
    private float tunaSrcLeftDx;

    public float getTunaSrcLeftDx() {
        return tunaSrcLeftDx;
    }

    public void setTunaSrcLeftDx(float tunaSrcLeftDx) {
        setTunaSrcLeftDx(TypedValue.COMPLEX_UNIT_DIP, tunaSrcLeftDx);
    }

    public void setTunaSrcLeftDx(int unit, float tunaSrcLeftDx) {
        setTunaSrcLeftDxRaw(applyDimension(unit, tunaSrcLeftDx, getViewDisplayMetrics(this)));
    }

    private void setTunaSrcLeftDxRaw(float tunaSrcLeftDx) {
        if (this.tunaSrcLeftDx != tunaSrcLeftDx) {
            this.tunaSrcLeftDx = tunaSrcLeftDx;
            invalidate();
        }
    }

    //
    private float tunaSrcLeftDy;

    public float getTunaSrcLeftDy() {
        return tunaSrcLeftDy;
    }

    public void setTunaSrcLeftDy(float tunaSrcLeftDy) {
        setTunaSrcLeftDy(TypedValue.COMPLEX_UNIT_DIP, tunaSrcLeftDy);
    }

    public void setTunaSrcLeftDy(int unit, float tunaSrcLeftDy) {
        setTunaSrcLeftDyRaw(applyDimension(unit, tunaSrcLeftDy, getViewDisplayMetrics(this)));
    }

    private void setTunaSrcLeftDyRaw(float tunaSrcLeftDy) {
        if (this.tunaSrcLeftDy != tunaSrcLeftDy) {
            this.tunaSrcLeftDy = tunaSrcLeftDy;
            invalidate();
        }
    }

    //
    private Bitmap tunaSrcRight;

    public Bitmap getTunaSrcRight() {
        return tunaSrcRight;
    }

    public void setTunaSrcRight(Bitmap tunaSrcRight) {
        this.tunaSrcRight = tunaSrcRight;
    }

    //
    private float tunaSrcRightWidth;

    public float getTunaSrcRightWidth() {
        return tunaSrcRightWidth;
    }

    public void setTunaSrcRightWidth(float tunaSrcRightWidth) {
        setTunaSrcRightWidth(TypedValue.COMPLEX_UNIT_DIP, tunaSrcRightWidth);
    }

    public void setTunaSrcRightWidth(int unit, float tunaSrcRightWidth) {
        setTunaSrcRightWidthRaw(applyDimension(unit, tunaSrcRightWidth, getViewDisplayMetrics(this)));
    }

    private void setTunaSrcRightWidthRaw(float tunaSrcRightWidth) {
        if (this.tunaSrcRightWidth != tunaSrcRightWidth) {
            this.tunaSrcRightWidth = tunaSrcRightWidth;
            invalidate();
        }
    }

    //
    private float tunaSrcRightHeight;

    public float getTunaSrcRightHeight() {
        return tunaSrcRightHeight;
    }

    public void setTunaSrcRightHeight(float tunaSrcRightHeight) {
        setTunaSrcRightHeight(TypedValue.COMPLEX_UNIT_DIP, tunaSrcRightHeight);
    }

    public void setTunaSrcRightHeight(int unit, float tunaSrcRightHeight) {
        setTunaSrcRightHeightRaw(applyDimension(unit, tunaSrcRightHeight, getViewDisplayMetrics(this)));
    }

    private void setTunaSrcRightHeightRaw(float tunaSrcRightHeight) {
        if (this.tunaSrcRightHeight != tunaSrcRightHeight) {
            this.tunaSrcRightHeight = tunaSrcRightHeight;
            invalidate();
        }
    }

    //
    protected Matrix tunaRightMatrix;

    public Matrix getTunaRightMatrix() {
        return tunaRightMatrix;
    }

    public void setTunaRightMatrix(Matrix tunaRightMatrix) {
        this.tunaRightMatrix = tunaRightMatrix;
    }

    protected Matrix initTunaRightMatrix(float sx, float sy) {
        if (tunaRightMatrix == null) {
            tunaRightMatrix = new Matrix();
        }
        tunaRightMatrix.reset();
        tunaRightMatrix.setScale(sx, sy);
        return tunaRightMatrix;
    }

    // tunaSrcRightPadding means distance between tunaSrcRight and textview,note
    // about the tunaTextPaddingRight
    private float tunaSrcRightPadding;

    public float getTunaSrcRightPadding() {
        return tunaSrcRightPadding;
    }

    public void setTunaSrcRightPadding(float tunaSrcRightPadding) {
        setTunaSrcRightPadding(TypedValue.COMPLEX_UNIT_DIP, tunaSrcRightPadding);
    }

    public void setTunaSrcRightPadding(int unit, float tunaSrcRightPadding) {
        setTunaSrcRightPaddingRaw(applyDimension(unit, tunaSrcRightPadding, getViewDisplayMetrics(this)));
    }

    private void setTunaSrcRightPaddingRaw(float tunaSrcRightPadding) {
        if (this.tunaSrcRightPadding != tunaSrcRightPadding) {
            this.tunaSrcRightPadding = tunaSrcRightPadding;
            invalidate();
        }
    }

    //
    private float tunaSrcRightDx;

    public float getTunaSrcRightDx() {
        return tunaSrcRightDx;
    }

    public void setTunaSrcRightDx(float tunaSrcRightDx) {
        setTunaSrcRightDx(TypedValue.COMPLEX_UNIT_DIP, tunaSrcRightDx);
    }

    public void setTunaSrcRightDx(int unit, float tunaSrcRightDx) {
        setTunaSrcRightDxRaw(applyDimension(unit, tunaSrcRightDx, getViewDisplayMetrics(this)));
    }

    private void setTunaSrcRightDxRaw(float tunaSrcRightDx) {
        if (this.tunaSrcRightDx != tunaSrcRightDx) {
            this.tunaSrcRightDx = tunaSrcRightDx;
            invalidate();
        }
    }

    //
    private float tunaSrcRightDy;

    public float getTunaSrcRightDy() {
        return tunaSrcRightDy;
    }

    public void setTunaSrcRightDy(float tunaSrcRightDy) {
        setTunaSrcRightDy(TypedValue.COMPLEX_UNIT_DIP, tunaSrcRightDy);
    }

    public void setTunaSrcRightDy(int unit, float tunaSrcRightDy) {
        setTunaSrcRightDyRaw(applyDimension(unit, tunaSrcRightDy, getViewDisplayMetrics(this)));
    }

    private void setTunaSrcRightDyRaw(float tunaSrcRightDy) {
        if (this.tunaSrcRightDy != tunaSrcRightDy) {
            this.tunaSrcRightDy = tunaSrcRightDy;
            invalidate();
        }
    }

    // attention tunaPorterDuffXfermode default 0 instead of -1!
    protected PorterDuffXfermode tunaPorterDuffXfermode;

    public enum TunaPorterDuffXfermode {
        SRC_IN(0), SRC_OUT(1),
        ;
        final int nativeInt;

        TunaPorterDuffXfermode(int ni) {
            nativeInt = ni;
        }
    }

    private static final Mode[] tunaPorterDuffXfermodeArray = {PorterDuff.Mode.SRC_IN, PorterDuff.Mode.SRC_OUT,};

    public PorterDuffXfermode getTunaPorterDuffXfermode() {
        return tunaPorterDuffXfermode;
    }

    public void setTunaPorterDuffXfermode(PorterDuffXfermode tunaPorterDuffXfermode) {
        this.tunaPorterDuffXfermode = tunaPorterDuffXfermode;
    }

    //
    private TunaMaterial tunaMaterial;

    public enum TunaMaterial {
        SPREAD(0), TRANS(1),
        ;
        final int nativeInt;

        TunaMaterial(int ni) {
            nativeInt = ni;
        }
    }

    private static final TunaMaterial[] tunaMaterialArray = {TunaMaterial.SPREAD, TunaMaterial.TRANS,};

    public TunaMaterial getTunaMaterial() {
        return tunaMaterial;
    }

    public void setTunaMaterial(TunaMaterial tunaMaterial) {
        this.tunaMaterial = tunaMaterial;
    }

    //
    private boolean tunaMaterialMove;

    public boolean isTunaMaterialMove() {
        return tunaMaterialMove;
    }

    public void setTunaMaterialMove(boolean tunaMaterialMove) {
        this.tunaMaterialMove = tunaMaterialMove;
    }

    //
    private float tunaMaterialRadius;

    public float getTunaMaterialRadius() {
        return tunaMaterialRadius;
    }

    public void setTunaMaterialRadius(float tunaMaterialRadius) {
        this.tunaMaterialRadius = tunaMaterialRadius;
    }

    //
    private float tunaTouchDownEventX;

    public float getTunaTouchDownEventX() {
        return tunaTouchDownEventX;
    }

    public void setTunaTouchDownEventX(float tunaTouchDownEventX) {
        this.tunaTouchDownEventX = tunaTouchDownEventX;
    }

    //
    private float tunaTouchDownEventY;

    public float getTunaTouchDownEventY() {
        return tunaTouchDownEventY;
    }

    public void setTunaTouchDownEventY(float tunaTouchDownEventY) {
        this.tunaTouchDownEventY = tunaTouchDownEventY;
    }

    //
    private int tunaMaterialDuraction = 500;

    public int getTunaMaterialDuraction() {
        return tunaMaterialDuraction;
    }

    public void setTunaMaterialDuraction(int tunaMaterialDuraction) {
        this.tunaMaterialDuraction = tunaMaterialDuraction;
    }

    //
    private boolean tunaMaterialPlay;

    public boolean isTunaMaterialPlay() {
        return tunaMaterialPlay;
    }

    public void setTunaMaterialPlay(boolean tunaMaterialPlay) {
        this.tunaMaterialPlay = tunaMaterialPlay;
    }

    //
    private TimeInterpolator tunaMaterialTimeInterpolator;

    public TimeInterpolator getTunaMaterialTimeInterpolator() {
        return tunaMaterialTimeInterpolator;
    }

    public void setTunaMaterialTimeInterpolator(TimeInterpolator tunaMaterialTimeInterpolator) {
        this.tunaMaterialTimeInterpolator = tunaMaterialTimeInterpolator;
    }

    //
    public enum TunaMaterialTimeInterpolator {
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

        TunaMaterialTimeInterpolator(int ni) {
            nativeInt = ni;
        }
    }

    //
    private static final TimeInterpolator[] tunaMaterialTimeInterpolatorArray = {new AccelerateDecelerateInterpolator(), new AccelerateInterpolator(),
            new AnticipateInterpolator(), new AnticipateOvershootInterpolator(), new BounceInterpolator(), new CycleInterpolator(0), new DecelerateInterpolator(),
            new LinearInterpolator(), new OvershootInterpolator(),};

    //
    private AnimatorSet tunaMaterialAnimatorSet;

    public AnimatorSet getTunaMaterialAnimatorSet() {
        return tunaMaterialAnimatorSet;
    }

    public void setTunaMaterialAnimatorSet(AnimatorSet tunaMaterialAnimatorSet) {
        this.tunaMaterialAnimatorSet = tunaMaterialAnimatorSet;
    }

    //
    private Property<TView, Float> tunaMaterialRadiusProperty = new Property<TView, Float>(Float.class, "tunaMaterialRadius") {
        @Override
        public Float get(TView tunaview) {
            return tunaview.tunaMaterialRadius;
        }

        @Override
        public void set(TView tunaview, Float value) {
            tunaview.tunaMaterialRadius = value;
            invalidate();
        }
    };

    //
    private Property<TView, Float> tunaMaterialPaintXProperty = new Property<TView, Float>(Float.class, "tunaMaterialPaintX") {
        @Override
        public Float get(TView tunaview) {
            return tunaview.tunaTouchDownEventX;
        }

        @Override
        public void set(TView tunaview, Float value) {
            tunaview.tunaTouchDownEventX = value;
        }
    };

    //
    private Property<TView, Float> tunaMaterialPaintYProperty = new Property<TView, Float>(Float.class, "tunaMaterialPaintY") {
        @Override
        public Float get(TView tunaview) {
            return tunaview.tunaTouchDownEventY;
        }

        @Override
        public void set(TView tunaview, Float value) {
            tunaview.tunaTouchDownEventY = value;
        }
    };

    // This setting will cause the following message appears reading xml
    // The graphics preview in the layout editor may not be accurate: Paint
    // Flags Draw Filters are not supported. (Ignore for this session)
    private PaintFlagsDrawFilter tunaPaintFlagsDrawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);

    public PaintFlagsDrawFilter getTunaPaintFlagsDrawFilter() {
        return tunaPaintFlagsDrawFilter;
    }

    public void setTunaPaintFlagsDrawFilter(PaintFlagsDrawFilter tunaPaintFlagsDrawFilter) {
        this.tunaPaintFlagsDrawFilter = tunaPaintFlagsDrawFilter;
    }

    //
    private int touchDownCount;
    private long touchDownTimeStart, touchDownTimeEnd;
    private static final int TOUCH_DOWN_TIMES = 3;
    private static final int SHOW_PROPERTY_MAX_DISTANCE_DP = 10;

    // in response to the dispathtouch event: need touch TOUCH_DOWN_TIMES
    // consecutive times within SHOW_PROPERTY_MAX_TIME_MILLIS ,
    // and the touch location can not exceed SHOW_PROPERTY_MAX_DISTANCE_DIP,
    // tunaTouchDownEventX and tunaTouchDownEventY position refresh when pressed
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

        tunaTag = TView.class.getSimpleName();

        //
        tunaDebugable = (0 != (context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE));
        //
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TView, 0, defStyle);

        // tunaTouchType default edge
        int tunaTouchTypeIndex = typedArray.getInt(R.styleable.TView_touchType, 0);
        tunaTouchType = tunaTouchTypeArray[tunaTouchTypeIndex];

        tunaTouchIntercept = typedArray.getBoolean(R.styleable.TView_touchIntercept, false);

        tunaPress = typedArray.getBoolean(R.styleable.TView_press, false);
        tunaSelect = typedArray.getBoolean(R.styleable.TView_select, false);

        // tunaSelectType default none
        int tunaSelectTypeIndex = typedArray.getInt(R.styleable.TView_selectType, 0);
        tunaSelectType = tunaSelectTypeArray[tunaSelectTypeIndex];

        tunaAnimationable = typedArray.getBoolean(R.styleable.TView_animationable, false);
        tunaRotate = typedArray.getInt(R.styleable.TView_rotate, 0);

        // tunaXfermodeIndex default PorterDuff.Mode.SRC_IN
        int tunaXfermodeIndex = typedArray.getInt(R.styleable.TView_porterDuffXfermode, 0);
        tunaPorterDuffXfermode = new PorterDuffXfermode(tunaPorterDuffXfermodeArray[tunaXfermodeIndex]);

        tunaSuper = TView.class == this.getClass();

        if (tunaSuper) {
            // note that the use of default values ​​can be defined,tunaBackgroundNormal to the default white to achieve clip tunaBitmap results!

            tunaBackgroundNormal = typedArray.getColor(R.styleable.TView_backgroundNormal, Color.TRANSPARENT);
            tunaBackgroundPress = typedArray.getColor(R.styleable.TView_backgroundPress, tunaBackgroundNormal);
            tunaBackgroundSelect = typedArray.getColor(R.styleable.TView_backgroundSelect, tunaBackgroundNormal);

            tunaForegroundNormal = typedArray.getColor(R.styleable.TView_foregroundNormal, Color.TRANSPARENT);
            tunaForegroundPress = typedArray.getColor(R.styleable.TView_foregroundPress, tunaForegroundNormal);
            tunaForegroundSelect = typedArray.getColor(R.styleable.TView_foregroundSelect, tunaForegroundNormal);

            //
            tunaBackgroundNormalAngle = typedArray.getInt(R.styleable.TView_backgroundNormalAngle, Integer.MAX_VALUE);
            if (tunaBackgroundNormalAngle != Integer.MAX_VALUE) {
                tunaBackgroundNormalGradientStart = typedArray.getColor(R.styleable.TView_backgroundNormalGradientStart, tunaBackgroundNormal);
                tunaBackgroundNormalGradientEnd = typedArray.getColor(R.styleable.TView_backgroundNormalGradientEnd, tunaBackgroundNormal);
            }

            tunaBackgroundPressAngle = typedArray.getInt(R.styleable.TView_backgroundPressAngle, Integer.MAX_VALUE);
            if (tunaBackgroundPressAngle != Integer.MAX_VALUE) {
                tunaBackgroundPressGradientStart = typedArray.getColor(R.styleable.TView_backgroundPressGradientStart, tunaBackgroundPress);
                tunaBackgroundPressGradientEnd = typedArray.getColor(R.styleable.TView_backgroundPressGradientEnd, tunaBackgroundPress);
            }

            tunaBackgroundSelectAngle = typedArray.getInt(R.styleable.TView_backgroundSelectAngle, Integer.MAX_VALUE);
            if (tunaBackgroundSelectAngle != Integer.MAX_VALUE) {
                tunaBackgroundSelectGradientStart = typedArray.getColor(R.styleable.TView_backgroundSelectGradientStart, tunaBackgroundSelect);
                tunaBackgroundSelectGradientEnd = typedArray.getColor(R.styleable.TView_backgroundSelectGradientEnd, tunaBackgroundSelect);
            }

            //Note tuna Background Normal ShadowRadius and tunaSrcNormal ShadowRadius are two values!
            tunaBackgroundNormalShadowRadius = typedArray.getDimension(R.styleable.TView_backgroundNormalShadowRadius, 0);
            if (tunaBackgroundNormalShadowRadius > 0) {
                tunaBackgroundNormalShadowColor = typedArray.getColor(R.styleable.TView_backgroundNormalShadowColor, Color.TRANSPARENT);
                tunaBackgroundNormalShadowDx = typedArray.getDimension(R.styleable.TView_backgroundNormalShadowDx, 0);
                tunaBackgroundNormalShadowDy = typedArray.getDimension(R.styleable.TView_backgroundNormalShadowDy, 0);
            }

            //
            tunaBackgroundPressShadowRadius = typedArray.getDimension(R.styleable.TView_backgroundPressShadowRadius, tunaBackgroundNormalShadowRadius);
            if (tunaBackgroundPressShadowRadius > 0) {
                tunaBackgroundPressShadowColor = typedArray.getColor(R.styleable.TView_backgroundPressShadowColor, tunaBackgroundNormalShadowColor);
                tunaBackgroundPressShadowDx = typedArray.getDimension(R.styleable.TView_backgroundPressShadowDx, tunaBackgroundNormalShadowDx);
                tunaBackgroundPressShadowDy = typedArray.getDimension(R.styleable.TView_backgroundPressShadowDy, tunaBackgroundNormalShadowDy);
            }

            //
            tunaBackgroundSelectShadowRadius = typedArray.getDimension(R.styleable.TView_backgroundSelectShadowRadius, tunaBackgroundNormalShadowRadius);
            if (tunaBackgroundSelectShadowRadius > 0) {
                tunaBackgroundSelectShadowColor = typedArray.getColor(R.styleable.TView_backgroundSelectShadowColor, tunaBackgroundNormalShadowColor);
                tunaBackgroundSelectShadowDx = typedArray.getDimension(R.styleable.TView_backgroundSelectShadowDx, tunaBackgroundNormalShadowDx);
                tunaBackgroundSelectShadowDy = typedArray.getDimension(R.styleable.TView_backgroundSelectShadowDy, tunaBackgroundNormalShadowDy);
            }

            //
            int tunaSrcNormalId = typedArray.getResourceId(R.styleable.TView_srcNormal, -1);
            if (tunaSrcNormalId != -1) {

                tunaSrcNormal = BitmapFactory.decodeResource(getResources(), tunaSrcNormalId);

                //
                int tunaSrcPressId = typedArray.getResourceId(R.styleable.TView_srcPress, -1);
                if (tunaSrcPressId != -1) {
                    tunaSrcPress = BitmapFactory.decodeResource(getResources(), tunaSrcPressId);
                } else {
                    tunaSrcPress = tunaSrcNormal;
                }
                //
                int tunaSrcSelectId = typedArray.getResourceId(R.styleable.TView_srcPress, -1);
                if (tunaSrcSelectId != -1) {
                    tunaSrcSelect = BitmapFactory.decodeResource(getResources(), tunaSrcSelectId);
                } else {
                    tunaSrcSelect = tunaSrcNormal;
                }

                tunaSrcNormalShadowRadius = typedArray.getDimension(R.styleable.TView_srcNormalShadowRadius, 0);
                if (tunaSrcNormalShadowRadius > 0) {
                    tunaSrcNormalShadowDx = typedArray.getDimension(R.styleable.TView_srcNormalShadowDx, 0);
                    tunaSrcNormalShadowDy = typedArray.getDimension(R.styleable.TView_srcNormalShadowDy, 0);
                }
                //
                tunaSrcPressShadowRadius = typedArray.getDimension(R.styleable.TView_srcPressShadowRadius, tunaSrcNormalShadowRadius);
                if (tunaSrcPressShadowRadius > 0) {
                    tunaSrcPressShadowDx = typedArray.getDimension(R.styleable.TView_srcPressShadowDx, tunaSrcNormalShadowDx);
                    tunaSrcPressShadowDy = typedArray.getDimension(R.styleable.TView_srcPressShadowDy, tunaSrcNormalShadowDy);
                }
                //
                tunaSrcSelectShadowRadius = typedArray.getDimension(R.styleable.TView_srcSelectShadowRadius, tunaSrcNormalShadowRadius);
                if (tunaSrcSelectShadowRadius > 0) {
                    tunaSrcSelectShadowDx = typedArray.getDimension(R.styleable.TView_srcSelectShadowDx, tunaSrcNormalShadowDx);
                    tunaSrcSelectShadowDy = typedArray.getDimension(R.styleable.TView_srcSelectShadowDy, tunaSrcNormalShadowDy);
                }
            }
            //
            int tunaSrcAnchorNormalId = typedArray.getResourceId(R.styleable.TView_srcAnchorNormal, -1);
            if (tunaSrcAnchorNormalId != -1) {

                //
                tunaSrcAnchorNormal = BitmapFactory.decodeResource(getResources(), tunaSrcAnchorNormalId);
                tunaSrcAnchorNormalWidth = typedArray.getDimension(R.styleable.TView_srcAnchorNormalWidth, 0);
                tunaSrcAnchorNormalHeight = typedArray.getDimension(R.styleable.TView_srcAnchorNormalHeight, 0);
                tunaSrcAnchorNormalDx = typedArray.getDimension(R.styleable.TView_srcAnchorNormalDx, 0);
                tunaSrcAnchorNormalDy = typedArray.getDimension(R.styleable.TView_srcAnchorNormalDy, 0);
                //
                tunaSrcAnchorPressWidth = typedArray.getDimension(R.styleable.TView_srcAnchorPressWidth, tunaSrcAnchorNormalWidth);
                tunaSrcAnchorPressHeight = typedArray.getDimension(R.styleable.TView_srcAnchorPressHeight, tunaSrcAnchorNormalHeight);
                tunaSrcAnchorPressDx = typedArray.getDimension(R.styleable.TView_srcAnchorPressDx, tunaSrcAnchorNormalDx);
                tunaSrcAnchorPressDy = typedArray.getDimension(R.styleable.TView_srcAnchorPressDy, tunaSrcAnchorNormalDy);
                //
                tunaSrcAnchorSelectWidth = typedArray.getDimension(R.styleable.TView_srcAnchorSelectWidth, tunaSrcAnchorNormalWidth);
                tunaSrcAnchorSelectHeight = typedArray.getDimension(R.styleable.TView_srcAnchorSelectHeight, tunaSrcAnchorNormalHeight);
                tunaSrcAnchorSelectDx = typedArray.getDimension(R.styleable.TView_srcAnchorSelectDx, tunaSrcAnchorNormalDx);
                tunaSrcAnchorSelectDy = typedArray.getDimension(R.styleable.TView_srcAnchorSelectDy, tunaSrcAnchorNormalDy);

                //
                int tunaSrcAnchorPressId = typedArray.getResourceId(R.styleable.TView_srcAnchorPress, -1);
                if (tunaSrcAnchorPressId != -1) {
                    tunaSrcAnchorPress = BitmapFactory.decodeResource(getResources(), tunaSrcAnchorPressId);
                } else {
                    tunaSrcAnchorPress = tunaSrcAnchorNormal;
                }
                //
                int tunaSrcAnchorSelectId = typedArray.getResourceId(R.styleable.TView_srcAnchorSelect, -1);
                if (tunaSrcAnchorSelectId != -1) {
                    tunaSrcAnchorSelect = BitmapFactory.decodeResource(getResources(), tunaSrcAnchorSelectId);
                } else {
                    tunaSrcAnchorSelect = tunaSrcAnchorNormal;
                }

                //
                tunaSrcAnchorGravity = typedArray.getInt(R.styleable.TView_srcAnchorGravity, 0);
            }

            //
            int tunaSrcLeftId = typedArray.getResourceId(R.styleable.TView_srcLeft, -1);
            if (tunaSrcLeftId != -1) {
                tunaSrcLeft = BitmapFactory.decodeResource(getResources(), tunaSrcLeftId);
                tunaSrcLeftWidth = typedArray.getDimension(R.styleable.TView_srcLeftWidth, 0);
                tunaSrcLeftHeight = typedArray.getDimension(R.styleable.TView_srcLeftHeight, 0);
                tunaSrcLeftPadding = typedArray.getDimension(R.styleable.TView_srcLeftPadding, 0);
                tunaSrcLeftDx = typedArray.getDimension(R.styleable.TView_srcLeftDx, 0);
                tunaSrcLeftDy = typedArray.getDimension(R.styleable.TView_srcLeftDy, 0);

                if (tunaSrcLeftWidth == 0 || tunaSrcLeftHeight == 0) {
                    throw new IllegalArgumentException("The content attribute require property named tunaSrcLeftWidth and tunaSrcLeftHeight");
                }
            }

            //
            int tunaSrcRightId = typedArray.getResourceId(R.styleable.TView_srcRight, -1);
            if (tunaSrcRightId != -1) {
                tunaSrcRight = BitmapFactory.decodeResource(getResources(), tunaSrcRightId);
                tunaSrcRightWidth = typedArray.getDimension(R.styleable.TView_srcRightWidth, 0);
                tunaSrcRightHeight = typedArray.getDimension(R.styleable.TView_srcRightHeight, 0);
                tunaSrcRightPadding = typedArray.getDimension(R.styleable.TView_srcRightPadding, 0);
                tunaSrcRightDx = typedArray.getDimension(R.styleable.TView_srcRightDx, 0);
                tunaSrcRightDy = typedArray.getDimension(R.styleable.TView_srcRightDy, 0);

                if (tunaSrcRightWidth == 0 || tunaSrcRightHeight == 0) {
                    throw new IllegalArgumentException("The content attribute require property named tunaSrcRightWidth and tunaSrcRightHeight");
                }
            }

            //
            tunaTextMark = typedArray.getBoolean(R.styleable.TView_textMark, false);
            tunaTextMarkTouchable = typedArray.getBoolean(R.styleable.TView_textMarkTouchable, false);
            tunaTextMarkRadius = typedArray.getDimension(R.styleable.TView_textMarkRadius, 0);
            tunaTextMarkColor = typedArray.getColor(R.styleable.TView_textMarkColor, Color.TRANSPARENT);
            tunaTextMarkTextValue = typedArray.getString(R.styleable.TView_textMarkTextValue);
            tunaTextMarkTextSize = typedArray.getDimension(R.styleable.TView_textMarkTextSize, 0);
            tunaTextMarkTextColor = typedArray.getColor(R.styleable.TView_textMarkTextColor, Color.TRANSPARENT);

            //
            tunaContentMark = typedArray.getBoolean(R.styleable.TView_contentMark, false);
            tunaContentMarkTouchable = typedArray.getBoolean(R.styleable.TView_contentMarkTouchable, false);
            tunaContentMarkRadius = typedArray.getDimension(R.styleable.TView_contentMarkRadius, 0);
            tunaContentMarkColor = typedArray.getColor(R.styleable.TView_contentMarkColor, Color.TRANSPARENT);
            tunaContentMarkTextValue = typedArray.getString(R.styleable.TView_contentMarkTextValue);
            tunaContentMarkTextSize = typedArray.getDimension(R.styleable.TView_contentMarkTextSize, 0);
            tunaContentMarkTextColor = typedArray.getColor(R.styleable.TView_contentMarkTextColor, Color.TRANSPARENT);

            //
            tunaStrokeWidthNormal = typedArray.getDimension(R.styleable.TView_strokeWidthNormal, 0);
            tunaStrokeColorNormal = typedArray.getColor(R.styleable.TView_strokeColorNormal, Color.TRANSPARENT);
            tunaStrokeWidthPress = typedArray.getDimension(R.styleable.TView_strokeWidthPress, tunaStrokeWidthNormal);
            tunaStrokeColorPress = typedArray.getColor(R.styleable.TView_strokeColorPress, tunaStrokeColorNormal);
            tunaStrokeWidthSelect = typedArray.getDimension(R.styleable.TView_strokeWidthSelect, tunaStrokeWidthNormal);
            tunaStrokeColorSelect = typedArray.getColor(R.styleable.TView_strokeColorSelect, tunaStrokeColorNormal);

            //
            tunaRadius = typedArray.getDimension(R.styleable.TView_radius, 0);
            tunaRadiusLeftTop = typedArray.getDimension(R.styleable.TView_radiusLeftTop, tunaRadius);
            tunaRadiusLeftBottom = typedArray.getDimension(R.styleable.TView_radiusLeftBottom, tunaRadius);
            tunaRadiusRightTop = typedArray.getDimension(R.styleable.TView_radiusRightTop, tunaRadius);
            tunaRadiusRightBottom = typedArray.getDimension(R.styleable.TView_radiusRightBottom, tunaRadius);

            tunaClassic = (tunaRadius == tunaRadiusLeftTop && tunaRadiusLeftTop == tunaRadiusLeftBottom && tunaRadiusLeftBottom == tunaRadiusRightTop && tunaRadiusRightTop == tunaRadiusRightBottom);

            //
            tunaTextValue = typedArray.getString(R.styleable.TView_textValue);
            tunaTextSize = typedArray.getDimension(R.styleable.TView_textSize, 0);

            tunaTextColorNormal = typedArray.getColor(R.styleable.TView_textColorNormal, Color.TRANSPARENT);
            tunaTextColorPress = typedArray.getColor(R.styleable.TView_textColorPress, tunaTextColorNormal);
            tunaTextColorSelect = typedArray.getColor(R.styleable.TView_textColorSelect, tunaTextColorNormal);

            tunaTextPaddingLeft = typedArray.getDimension(R.styleable.TView_textPaddingLeft, 0);
            tunaTextPaddingRight = typedArray.getDimension(R.styleable.TView_textPaddingRight, 0);
            tunaTextRowSpaceRatio = typedArray.getFraction(R.styleable.TView_textRowSpaceRatio, 1, 1, 1);

            //
            int tunaTextGravityIndex = typedArray.getInt(R.styleable.TView_textGravity, 0);
            if (tunaTextGravityIndex >= 0) {
                tunaTextGravity = tunaTextGravityArray[tunaTextGravityIndex];
            }

            //
            int tunaTextTypeFaceIndex = typedArray.getInt(R.styleable.TView_textTypeFace, 0);
            if (tunaTextTypeFaceIndex >= 0) {
                tunaTextTypeFace = Typeface.create(Typeface.DEFAULT, tunaTextTypeFaceArray[tunaTextTypeFaceIndex]);
            }

            tunaTextDx = typedArray.getDimension(R.styleable.TView_textDx, 0);
            tunaTextDy = typedArray.getDimension(R.styleable.TView_textDy, 0);
            tunaTextFractionDx = typedArray.getFraction(R.styleable.TView_textFractionDx, 1, 1, 0);
            tunaTextFractionDy = typedArray.getFraction(R.styleable.TView_textFractionDy, 1, 1, 0);

            //
            tunaTextShadowRadius = typedArray.getDimension(R.styleable.TView_textShadowRadius, 0);
            if (tunaTextShadowRadius > 0) {
                tunaTextShadowColor = typedArray.getColor(R.styleable.TView_textShadowColor, Color.TRANSPARENT);
                tunaTextShadowDx = typedArray.getDimension(R.styleable.TView_textShadowDx, 0);
                tunaTextShadowDy = typedArray.getDimension(R.styleable.TView_textShadowDy, 0);
            }

            //
            tunaContentValue = typedArray.getString(R.styleable.TView_contentValue);
            tunaContentSize = typedArray.getDimension(R.styleable.TView_contentSize, 0);

            tunaContentColorNormal = typedArray.getColor(R.styleable.TView_contentColorNormal, Color.TRANSPARENT);
            tunaContentColorPress = typedArray.getColor(R.styleable.TView_contentColorPress, tunaContentColorNormal);
            tunaContentColorSelect = typedArray.getColor(R.styleable.TView_contentColorSelect, tunaContentColorNormal);

            tunaContentPaddingLeft = typedArray.getDimension(R.styleable.TView_contentPaddingLeft, 0);
            tunaContentPaddingRight = typedArray.getDimension(R.styleable.TView_contentPaddingRight, 0);
            tunaContentRowSpaceRatio = typedArray.getFraction(R.styleable.TView_contentRowSpaceRatio, 1, 1, 1);

            //
            int tunaContentGravityIndex = typedArray.getInt(R.styleable.TView_contentGravity, 0);
            if (tunaContentGravityIndex >= 0) {
                tunaContentGravity = tunaContentGravityArray[tunaContentGravityIndex];
            }

            int tunaContentTypeFaceIndex = typedArray.getInt(R.styleable.TView_contentTypeFace, 0);
            if (tunaContentTypeFaceIndex >= 0) {
                tunaContentTypeFace = Typeface.create(Typeface.DEFAULT, tunaContentTypeFaceArray[tunaContentTypeFaceIndex]);
            }

            tunaContentDx = typedArray.getDimension(R.styleable.TView_contentDx, 0);
            tunaContentDy = typedArray.getDimension(R.styleable.TView_contentDy, 0);
            tunaContentFractionDx = typedArray.getFraction(R.styleable.TView_contentFractionDx, 1, 1, 0);
            tunaContentFractionDy = typedArray.getFraction(R.styleable.TView_contentFractionDy, 1, 1, 0);

            //
            tunaContentShadowRadius = typedArray.getDimension(R.styleable.TView_contentShadowRadius, 0);
            if (tunaContentShadowRadius > 0) {
                tunaContentShadowColor = typedArray.getColor(R.styleable.TView_contentShadowColor, Color.TRANSPARENT);
                tunaContentShadowDx = typedArray.getDimension(R.styleable.TView_contentShadowDx, 0);
                tunaContentShadowDy = typedArray.getDimension(R.styleable.TView_contentShadowDy, 0);
            }

            //
            tunaTextMarkDx = typedArray.getDimension(R.styleable.TView_textMarkDx, 0);
            tunaTextMarkDy = typedArray.getDimension(R.styleable.TView_textMarkDy, 0);
            tunaTextMarkFractionDx = typedArray.getFraction(R.styleable.TView_textMarkFractionDx, 1, 1, 0);
            tunaTextMarkFractionDy = typedArray.getFraction(R.styleable.TView_textMarkFractionDy, 1, 1, 0);

            // tunaMaterialIndex default -1
            int tunaMaterialIndex = typedArray.getInt(R.styleable.TView_material, -1);
            if (tunaMaterialIndex > -1) {
                tunaMaterial = tunaMaterialArray[tunaMaterialIndex];
            }
        }

        typedArray.recycle();
        DeviceTool.initDisplayMetrics(getContext());
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        if (tunaTouchType == TunaTouchType.NONE) {
            return super.dispatchTouchEvent(event);
        }

        //This sentence is telling the parent control, my own event handling!
        // when TunaDrag and other views nested inside the ScroView will be used !

        if (tunaTouchIntercept) {
            getParent().requestDisallowInterceptTouchEvent(true);
        }

        tunaTouchEventX = event.getX();
        tunaTouchEventY = event.getY();

        if (tunaTouchListener != null && (tunaTouchType == TunaTouchType.ALWAYS || !tunaTouchOutBounds)) {
            tunaTouchListener.tunaTouch(this);
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                //
                if (tunaDebugable) {

                    if (touchDownCount == 0) {
                        touchDownCount = 1;
                        touchDownTimeStart = System.currentTimeMillis();
                    } else if (touchDownCount == TOUCH_DOWN_TIMES - 1) {
                        touchDownTimeEnd = System.currentTimeMillis();
                        if ((touchDownTimeEnd - touchDownTimeStart) < SHOW_PROPERTY_MAX_TIME_MILLIS) {
                            showTunaProperties();
                        }
                        touchDownCount = 0;
                    } else {
                        float touchDownTimeDistanceX = Math.abs(tunaTouchDownEventX - event.getX());
                        float touchDownTimeDistanceY = Math.abs(tunaTouchDownEventY - event.getY());
                        if (touchDownTimeDistanceX < SHOW_PROPERTY_MAX_DISTANCE_DP * DeviceTool.displayDensity && touchDownTimeDistanceY < SHOW_PROPERTY_MAX_DISTANCE_DP * DeviceTool.displayDensity) {
                            touchDownCount++;
                        }
                    }
                    tunaTouchDownEventX = event.getX();
                    tunaTouchDownEventY = event.getY();
                    if ((System.currentTimeMillis() - touchDownTimeStart) >= SHOW_PROPERTY_MAX_TIME_MILLIS) {
                        touchDownCount = 0;
                    }
                }

                tunaPress = true;
                tunaSelect = false;

                if (!tunaTextMarkTouchable) {
                    tunaTextMark = false;
                }

                if (!tunaContentMarkTouchable) {
                    tunaContentMark = false;
                }

                if (!tunaTouchOutBounds && tunaAssociateListener != null) {
                    tunaAssociateListener.tunaAssociate(this);
                }

                if (!tunaTouchOutBounds && tunaTouchDownListener != null) {
                    tunaTouchDownListener.tunaTouchDown(this);
                }

                if (tunaMaterial != null) {
                    switch (tunaMaterial) {
                        case SPREAD:
                            float startRadius, endRadius;
                            if (tunaWidth >= tunaHeight) {
                                startRadius = tunaTouchDownEventY >= tunaHeight - tunaTouchDownEventY ? tunaTouchDownEventY : tunaHeight - tunaTouchDownEventY;
                                endRadius = tunaWidth << 1;
                            } else {
                                startRadius = tunaTouchDownEventX >= tunaWidth - tunaTouchDownEventX ? tunaTouchDownEventX : tunaWidth - tunaTouchDownEventX;
                                endRadius = tunaHeight << 1;
                            }
                            tunaMaterialAnimatorSet = new AnimatorSet();
                            if (tunaMaterialMove) {
                                tunaMaterialAnimatorSet.playTogether(ObjectAnimator.ofFloat(this, tunaMaterialRadiusProperty, startRadius, endRadius),
                                        ObjectAnimator.ofFloat(this, tunaMaterialPaintXProperty, tunaTouchDownEventX, tunaWidth >> 1),
                                        ObjectAnimator.ofFloat(this, tunaMaterialPaintYProperty, tunaTouchDownEventY, tunaHeight >> 1));
                            } else {
                                tunaMaterialAnimatorSet.playTogether(ObjectAnimator.ofFloat(this, tunaMaterialRadiusProperty, startRadius, endRadius));
                            }
                            tunaMaterialAnimatorSet.setDuration(tunaMaterialDuraction);
                            if (tunaMaterialTimeInterpolator != null) {
                                tunaMaterialAnimatorSet.setInterpolator(tunaMaterialTimeInterpolator);
                            }
                            tunaMaterialAnimatorSet.addListener(new AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animation) {
                                    tunaMaterialPlay = true;
                                }

                                @Override
                                public void onAnimationRepeat(Animator animation) {
                                }

                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    tunaMaterialPlay = false;
                                }

                                @Override
                                public void onAnimationCancel(Animator animation) {
                                    tunaMaterialPlay = false;
                                }
                            });
                            tunaMaterialAnimatorSet.start();
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
                if (tunaTouchType == TunaTouchType.ALWAYS) {

                    tunaPress = true;
                    tunaSelect = false;
                } else if (!tunaTouchOutBounds && (tunaTouchEventX < 0 || tunaTouchEventX > tunaWidth || tunaTouchEventY < 0 || tunaTouchEventY > tunaHeight)) {

                    tunaPress = false;
                    tunaSelect = false;

                    if (!tunaTextMarkTouchable) {
                        tunaTextMark = false;
                    }
                    if (!tunaContentMarkTouchable) {
                        tunaContentMark = false;
                    }

                    invalidate();

                    tunaTouchOutBounds = true;
                    if (tunaTouchOutListener != null) {
                        tunaTouchOutListener.tunaTouchOut(this);
                    }
                } else if (tunaTouchOutBounds && (tunaTouchEventX >= 0 && tunaTouchEventX <= tunaWidth && tunaTouchEventY >= 0 && tunaTouchEventY <= tunaHeight)) {

                    tunaPress = true;
                    tunaSelect = false;

                    if (!tunaTextMarkTouchable) {
                        tunaTextMark = false;
                    }

                    if (!tunaContentMarkTouchable) {
                        tunaContentMark = false;
                    }

                    tunaTouchOutBounds = false;
                    if (tunaTouchInListener != null) {
                        tunaTouchInListener.tunaTouchIn(this);
                    }
                }

                //
                if (!tunaTouchOutBounds && tunaTouchMoveListener != null) {
                    tunaTouchMoveListener.tunaTouchMove(this);
                }

                break;
            case MotionEvent.ACTION_UP:
                //
                tunaPress = false;
                switch (tunaSelectType) {
                    case NONE:
                        tunaSelect = false;
                        break;
                    case SAME:
                        tunaSelect = true;
                        break;
                    case REVERSE:
                        tunaSelectRaw = !tunaSelectRaw;
                        tunaSelect = tunaSelectRaw;
                        break;
                    default:
                        break;
                }

                if (!tunaTextMarkTouchable) {
                    tunaTextMark = false;
                }
                if (!tunaContentMarkTouchable) {
                    tunaContentMark = false;
                }

                if (!tunaTouchOutBounds && tunaAssociateListener != null) {
                    tunaAssociateListener.tunaAssociate(this);
                }

                if (!tunaTouchOutBounds && tunaTouchUpListener != null) {
                    tunaTouchUpListener.tunaTouchUp(this);
                }

                tunaTouchOutBounds = false;

                break;
            case MotionEvent.ACTION_CANCEL:
                tunaPress = false;
                tunaSelect = false;

                if (!tunaTextMarkTouchable) {
                    tunaTextMark = false;
                }
                if (!tunaContentMarkTouchable) {
                    tunaContentMark = false;
                }

                if (!tunaTouchOutBounds && tunaTouchCancelListener != null) {
                    tunaTouchCancelListener.tunaTouchCancel(this);
                }
                break;
            default:
                break;
        }

        if (!tunaTouchOutBounds) {
            invalidate();
        }
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (tunaTextValue != null) {
            tunaTextValueMeasureList = tunaMeasure(widthMeasureSpec, heightMeasureSpec,
                    tunaTextValue, initTunaTextPaint(tunaTextSize), tunaTextPaddingLeft, tunaTextPaddingRight,
                    tunaTextRowSpaceRatio);
        } else {
            tunaContentValueMeasureList = tunaMeasure(widthMeasureSpec, heightMeasureSpec,
                    tunaContentValue, initTunaTextPaint(tunaContentSize), tunaTextPaddingLeft, tunaTextPaddingRight,
                    tunaContentRowSpaceRatio);
        }
    }

    protected List<Integer> tunaMeasure(int widthMeasureSpec, int heightMeasureSpec,
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

        tunaWidth = getWidth();
        tunaHeight = getHeight();

        if (!tunaSuper) {
            return;
        }

        if (tunaForegroundNormal != Color.TRANSPARENT || tunaForegroundPress != Color.TRANSPARENT || tunaForegroundSelect != Color.TRANSPARENT || tunaSrcNormal != null
                || tunaSrcPress != null || tunaSrcSelect != null || tunaSrcNormalShadowRadius > 0 || tunaSrcPressShadowRadius > 0 || tunaSrcSelectShadowRadius > 0
                || tunaBackgroundNormalShadowRadius > 0 || tunaBackgroundPressShadowRadius > 0 || tunaBackgroundSelectShadowRadius > 0 || tunaBackgroundNormalAngle != Integer.MAX_VALUE
                || tunaBackgroundPressAngle != Integer.MAX_VALUE || tunaBackgroundSelectAngle != Integer.MAX_VALUE || tunaSrcAnchorNormal != null || tunaSrcAnchorPress != null
                || tunaSrcAnchorSelect != null

        ) {
            // setShadowLayer() is only supported on text when hardware acceleration is on.
            // Hardware acceleration is on by default when targetSdk=14 or higher.
            // An easy workaround is to put your View in a software layer: myView.setLayerType(View.LAYER_TYPE_SOFTWARE, null).
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        if (tunaSrcNormal != null || tunaSrcPress != null || tunaSrcSelect != null || tunaBackgroundNormalShadowRadius > 0 || tunaBackgroundPressShadowRadius > 0 || tunaBackgroundSelectShadowRadius > 0) {
            if (tunaBackgroundNormal == Color.TRANSPARENT) {
                tunaBackgroundNormal = Color.WHITE;
            }
            if (tunaBackgroundPress == Color.TRANSPARENT) {
                tunaBackgroundPress = tunaBackgroundNormal;
            }
            if (tunaBackgroundSelect == Color.TRANSPARENT) {
                tunaBackgroundSelect = tunaBackgroundNormal;
            }
        }

        // tunaText
        tunaTextDx += tunaWidth * tunaTextFractionDx;
        tunaTextDy += tunaHeight * tunaTextFractionDy;

        // tunaTextMark
        tunaTextMarkDx += tunaWidth * tunaTextMarkFractionDx;
        tunaTextMarkDy += tunaHeight * tunaTextMarkFractionDy;

        //
        if (tunaBackgroundNormalAngle != Integer.MAX_VALUE) {
            tunaBackgroundNormalShader = getLinearGradient(tunaWidth, tunaHeight, tunaBackgroundNormalAngle, tunaBackgroundNormalGradientStart, tunaBackgroundNormalGradientEnd);
        }
        if (tunaBackgroundPressAngle != Integer.MAX_VALUE) {
            tunaBackgroundPressShader = getLinearGradient(tunaWidth, tunaHeight, tunaBackgroundPressAngle, tunaBackgroundPressGradientStart, tunaBackgroundPressGradientEnd);
        }
        if (tunaBackgroundSelectAngle != Integer.MAX_VALUE) {
            tunaBackgroundSelectShader = getLinearGradient(tunaWidth, tunaHeight, tunaBackgroundSelectAngle, tunaBackgroundSelectGradientStart, tunaBackgroundSelectGradientEnd);
        }

        //
        int tunaSrcNormalWidthRaw = 0, tunaSrcNormalHeightRaw = 0, tunaSrcPressWidthRaw = 0, tunaSrcPressHeightRaw = 0, tunaSrcSelectWidthRaw = 0, tunaSrcSelectHeightRaw = 0;
        if (tunaSrcNormal != null) {
            tunaSrcNormalWidthRaw = tunaSrcNormal.getWidth();
            tunaSrcNormalHeightRaw = tunaSrcNormal.getHeight();

            initTunaMatrix((tunaWidth - tunaSrcNormalShadowRadius * 2f - tunaBackgroundNormalShadowRadius * 2f - tunaBackgroundNormalShadowDx * 2f) / tunaSrcNormalWidthRaw,
                    (tunaHeight - tunaSrcNormalShadowRadius * 2f - tunaBackgroundNormalShadowRadius * 2f - tunaBackgroundNormalShadowDy * 2f) / tunaSrcNormalHeightRaw);
        }

        if (tunaSrcPress != null) {
            tunaSrcPressWidthRaw = tunaSrcPress.getWidth();
            tunaSrcPressHeightRaw = tunaSrcPress.getHeight();
        }

        if (tunaSrcSelect != null) {
            tunaSrcSelectWidthRaw = tunaSrcSelect.getWidth();
            tunaSrcSelectHeightRaw = tunaSrcSelect.getHeight();
        }

        if (tunaSrcNormalWidthRaw != tunaSrcPressWidthRaw || tunaSrcNormalHeightRaw != tunaSrcPressHeightRaw || tunaSrcPressWidthRaw != tunaSrcSelectWidthRaw
                || tunaSrcPressHeightRaw != tunaSrcSelectHeightRaw) {
            throw new IndexOutOfBoundsException("Both the width and height of the attribute tunaSrcNormal ,tunaSrcPress and tunaSrcSelect needed equal");
        }

        //
        int tunaSrcAnchorNormalWidthRaw = 0, tunaSrcAnchorNormalHeightRaw = 0, tunaSrcAnchorPressWidthRaw = 0, tunaSrcAnchorPressHeightRaw = 0, tunaSrcAnchorSelectWidthRaw = 0, tunaSrcAnchorSelectHeightRaw = 0;

        if (tunaSrcAnchorNormal != null) {
            tunaSrcAnchorNormalWidthRaw = tunaSrcAnchorNormal.getWidth();
            tunaSrcAnchorNormalHeightRaw = tunaSrcAnchorNormal.getHeight();

            initTunaAnchorMatrix(tunaSrcAnchorNormalWidth / tunaSrcAnchorNormalWidthRaw, tunaSrcAnchorNormalHeight / tunaSrcAnchorNormalHeightRaw);
        }

        if (tunaSrcAnchorPress != null) {
            tunaSrcAnchorPressWidthRaw = tunaSrcAnchorPress.getWidth();
            tunaSrcAnchorPressHeightRaw = tunaSrcAnchorPress.getHeight();
        }

        if (tunaSrcAnchorSelect != null) {
            tunaSrcAnchorSelectWidthRaw = tunaSrcAnchorSelect.getWidth();
            tunaSrcAnchorSelectHeightRaw = tunaSrcAnchorSelect.getHeight();
        }

        if (tunaSrcAnchorNormalWidthRaw != tunaSrcAnchorPressWidthRaw || tunaSrcAnchorNormalHeightRaw != tunaSrcAnchorPressHeightRaw
                || tunaSrcAnchorPressWidthRaw != tunaSrcAnchorSelectWidthRaw || tunaSrcAnchorPressHeightRaw != tunaSrcAnchorSelectHeightRaw) {
            throw new IndexOutOfBoundsException("Both the width and height of the attribute tunaSrcAnchorNormal ,tunaSrcAnchorPress and tunaSrcAnchorSelect needed equal");
        }

        //
        if (tunaSrcLeft != null) {
            int tunaSrcLeftWidthRaw = tunaSrcLeft.getWidth();
            int tunaSrcLeftHeightRaw = tunaSrcLeft.getHeight();
            initTunaLeftMatrix(tunaSrcLeftWidth / tunaSrcLeftWidthRaw, tunaSrcLeftHeight / tunaSrcLeftHeightRaw);
        }

        if (tunaSrcRight != null) {
            int tunaSrcRightWidthRaw = tunaSrcRight.getWidth();
            int tunaSrcRightHeightRaw = tunaSrcRight.getHeight();
            initTunaRightMatrix(tunaSrcRightWidth / tunaSrcRightWidthRaw, tunaSrcRightHeight / tunaSrcRightHeightRaw);
        }

        if (tunaLayoutListener != null) {
            tunaLayoutListener.tunaLayout(this);
        }
    }

    @SuppressLint("WrongConstant")
    @Override
    protected void onDraw(Canvas canvas) {

        initTunaCanvas(canvas);

        //
        if (tunaRotate != 0) {
            canvas.rotate(tunaRotate, tunaWidth >> 1, tunaHeight >> 1);
        }
        if (!tunaSuper) {
            return;
        }

        //
        boolean needSaveLayer = (tunaSrcNormal != null || tunaSrcPress != null || tunaSrcSelect != null);
        if (needSaveLayer) {
            // draw the src/dst example into our offscreen bitmap
            tunaLayer = canvas.saveLayer(0, 0, tunaWidth, tunaHeight, null);
        }

        // dst, note that when tunaSrcSelectShadowRadius > 0, the parent background color must take incoming as tunaBackgroundNormal!

        if (tunaClassic) {

            if (tunaMaterialPlay) {
                drawTunaRectClassic(canvas, tunaBackgroundNormalShadowRadius + tunaBackgroundNormalShadowDx, tunaBackgroundNormalShadowRadius + tunaBackgroundNormalShadowDy,
                        tunaWidth - tunaBackgroundNormalShadowRadius - tunaBackgroundNormalShadowDx, tunaHeight - tunaBackgroundNormalShadowRadius
                                - tunaBackgroundNormalShadowDy, tunaBackgroundNormal, tunaBackgroundNormalShader, tunaBackgroundNormalShadowRadius,
                        tunaBackgroundNormalShadowColor, tunaBackgroundNormalShadowDx, tunaBackgroundNormalShadowDy, tunaStrokeWidthNormal, tunaStrokeColorNormal, tunaRadius);

                canvas.drawCircle(tunaTouchDownEventX, tunaTouchDownEventY, tunaMaterialRadius, initTunaPaint(tunaBackgroundPress));
            } else {
                drawTunaRectClassic(canvas, tunaBackgroundNormalShadowRadius + tunaBackgroundNormalShadowDx, tunaBackgroundNormalShadowRadius + tunaBackgroundNormalShadowDy,
                        tunaWidth - tunaBackgroundNormalShadowRadius - tunaBackgroundNormalShadowDx, tunaHeight - tunaBackgroundNormalShadowRadius
                                - tunaBackgroundNormalShadowDy, tunaSelect ? tunaBackgroundSelect : tunaPress ? tunaBackgroundPress : tunaBackgroundNormal,
                        tunaSelect ? tunaBackgroundSelectShader : tunaPress ? tunaBackgroundPressShader : tunaBackgroundNormalShader,
                        tunaSelect ? tunaBackgroundSelectShadowRadius : tunaPress ? tunaBackgroundPressShadowRadius : tunaBackgroundNormalShadowRadius,
                        tunaSelect ? tunaBackgroundSelectShadowColor : tunaPress ? tunaBackgroundPressShadowColor : tunaBackgroundNormalShadowColor,
                        tunaSelect ? tunaBackgroundSelectShadowDx : tunaPress ? tunaBackgroundPressShadowDx : tunaBackgroundNormalShadowDx,
                        tunaSelect ? tunaBackgroundSelectShadowDy : tunaPress ? tunaBackgroundPressShadowDy : tunaBackgroundNormalShadowDy, tunaSelect ? tunaStrokeWidthSelect
                                : tunaPress ? tunaStrokeWidthPress : tunaStrokeWidthNormal, tunaSelect ? tunaStrokeColorSelect : tunaPress ? tunaStrokeColorPress
                                : tunaStrokeColorNormal, tunaRadius);
            }
        } else {

            // draw MaterialDesign Effect
            if (tunaMaterialPlay) {
                drawTunaRectCustom(canvas, tunaBackgroundNormalShadowRadius + tunaBackgroundNormalShadowDx, tunaBackgroundNormalShadowRadius + tunaBackgroundNormalShadowDy,
                        tunaWidth - tunaBackgroundNormalShadowRadius - tunaBackgroundNormalShadowDx, tunaHeight - tunaBackgroundNormalShadowRadius
                                - tunaBackgroundNormalShadowDy, tunaBackgroundNormal, tunaBackgroundNormalShader, tunaBackgroundNormalShadowRadius,
                        tunaBackgroundNormalShadowColor, tunaBackgroundNormalShadowDx, tunaBackgroundNormalShadowDy, tunaStrokeWidthNormal, tunaStrokeColorNormal,
                        tunaRadiusLeftTop, tunaRadiusLeftBottom, tunaRadiusRightTop, tunaRadiusRightBottom);

                canvas.drawCircle(tunaTouchDownEventX, tunaTouchDownEventY, tunaMaterialRadius, initTunaPaint(tunaBackgroundPress));
            } else {
                drawTunaRectCustom(canvas, tunaBackgroundNormalShadowRadius + tunaBackgroundNormalShadowDx, tunaBackgroundNormalShadowRadius + tunaBackgroundNormalShadowDy,
                        tunaWidth - tunaBackgroundNormalShadowRadius - tunaBackgroundNormalShadowDx, tunaHeight - tunaBackgroundNormalShadowRadius
                                - tunaBackgroundNormalShadowDy, tunaSelect ? tunaBackgroundSelect : tunaPress ? tunaBackgroundPress : tunaBackgroundNormal,
                        tunaSelect ? tunaBackgroundSelectShader : tunaPress ? tunaBackgroundPressShader : tunaBackgroundNormalShader,
                        tunaSelect ? tunaBackgroundSelectShadowRadius : tunaPress ? tunaBackgroundPressShadowRadius : tunaBackgroundNormalShadowRadius,
                        tunaSelect ? tunaBackgroundSelectShadowColor : tunaPress ? tunaBackgroundPressShadowColor : tunaBackgroundNormalShadowColor,
                        tunaSelect ? tunaBackgroundSelectShadowDx : tunaPress ? tunaBackgroundPressShadowDx : tunaBackgroundNormalShadowDx,
                        tunaSelect ? tunaBackgroundSelectShadowDy : tunaPress ? tunaBackgroundPressShadowDy : tunaBackgroundNormalShadowDy, tunaSelect ? tunaStrokeWidthSelect
                                : tunaPress ? tunaStrokeWidthPress : tunaStrokeWidthNormal, tunaSelect ? tunaStrokeColorSelect : tunaPress ? tunaStrokeColorPress
                                : tunaStrokeColorNormal, tunaRadiusLeftTop, tunaRadiusLeftBottom, tunaRadiusRightTop, tunaRadiusRightBottom);
            }
        }

        // draw tunaBitmap
        if (needSaveLayer) {
            tunaPaint.setXfermode(tunaPorterDuffXfermode);

            // If they are offset tunaBackgroundShadow, mobile, is to draw on the background shadow,
            // without moving the bigger picture and the need to set the width and height

            canvas.translate(tunaSelect ? tunaBackgroundSelectShadowDx * 2f + tunaSrcSelectShadowRadius - tunaSrcSelectShadowDx : tunaPress ? tunaBackgroundPressShadowDx * 2f
                            + tunaSrcPressShadowRadius - tunaSrcPressShadowDx : tunaBackgroundNormalShadowDx * 2f + tunaSrcNormalShadowRadius - tunaSrcNormalShadowDx,
                    tunaSelect ? tunaBackgroundSelectShadowDy * 2f + tunaSrcSelectShadowRadius - tunaSrcSelectShadowDy : tunaPress ? tunaBackgroundPressShadowDy * 2f
                            + tunaSrcPressShadowRadius - tunaSrcPressShadowDy : tunaBackgroundNormalShadowDy * 2f + tunaSrcNormalShadowRadius - tunaSrcNormalShadowDy);
            canvas.drawBitmap(
                    tunaSelect ? tunaSrcSelect : tunaPress ? tunaSrcPress : tunaSrcNormal,
                    tunaMatrix,
                    initTunaPaint(tunaPaint, tunaSelect ? tunaSrcSelectShadowRadius : tunaPress ? tunaSrcPressShadowRadius : tunaSrcNormalShadowRadius,
                            tunaSelect ? tunaSrcSelectShadowDx : tunaPress ? tunaSrcPressShadowDx : tunaSrcNormalShadowDx, tunaSelect ? tunaSrcSelectShadowDy
                                    : tunaPress ? tunaSrcPressShadowDy : tunaSrcNormalShadowDy));
            canvas.translate(tunaSelect ? -tunaBackgroundSelectShadowDx * 2f - tunaSrcSelectShadowRadius + tunaSrcSelectShadowDx : tunaPress ? -tunaBackgroundPressShadowDx * 2f
                            - tunaSrcPressShadowRadius + tunaSrcPressShadowDx : -tunaBackgroundNormalShadowDx * 2f - tunaSrcNormalShadowRadius + tunaSrcNormalShadowDx,
                    tunaSelect ? -tunaBackgroundSelectShadowDy * 2f - tunaSrcSelectShadowRadius + tunaSrcSelectShadowDy : tunaPress ? -tunaBackgroundPressShadowDy * 2f
                            - tunaSrcPressShadowRadius + tunaSrcPressShadowDy : -tunaBackgroundNormalShadowDy * 2f - tunaSrcNormalShadowRadius + tunaSrcNormalShadowDy);

            tunaPaint.setXfermode(null);

            // Uncomment will cause a null pointer with tunaPaint in xml preview canvas.restoreToCount(tunaLayer);
        }

        // draw tunaAnchor
        if (tunaSrcAnchorNormal != null) {

            float anchorDx = 0, anchorDy = 0;
            switch (tunaSrcAnchorGravity & GRAVITY_MASK) {
                case LEFT:
                    break;
                case CENTER_HORIZONTAL:
                    anchorDx = (tunaWidth >> 1) - tunaSrcAnchorNormalWidth * 0.5f;
                    break;
                case RIGHT:
                    anchorDx = tunaWidth - tunaSrcAnchorNormalWidth;
                    break;
                case CENTER_VERTICAL:
                    anchorDy = (tunaHeight >> 1) - tunaSrcAnchorNormalHeight * 0.5f;
                    break;
                case CENTER:
                    anchorDx = (tunaWidth >> 1) - tunaSrcAnchorNormalWidth * 0.5f;
                    anchorDy = (tunaHeight >> 1) - tunaSrcAnchorNormalHeight * 0.5f;
                    break;
                case RIGHT | CENTER_VERTICAL:
                    anchorDx = tunaWidth - tunaSrcAnchorNormalWidth;
                    anchorDy = (tunaHeight >> 1) - tunaSrcAnchorNormalHeight * 0.5f;
                    break;
                case BOTTOM:
                    anchorDy = tunaHeight - tunaSrcAnchorNormalHeight;
                    break;
                case BOTTOM | CENTER_HORIZONTAL:
                    anchorDx = (tunaWidth >> 1) - tunaSrcAnchorNormalWidth * 0.5f;
                    anchorDy = tunaHeight - tunaSrcAnchorNormalHeight;
                    break;
                case BOTTOM | RIGHT:
                    anchorDx = tunaWidth - tunaSrcAnchorNormalWidth;
                    anchorDy = tunaHeight - tunaSrcAnchorNormalHeight;
                    break;
                default:
                    break;
            }

            canvas.translate(anchorDx + (tunaSelect ? tunaSrcAnchorSelectDx : tunaPress ? tunaSrcAnchorPressDx : tunaSrcAnchorNormalDx), anchorDy
                    + (tunaSelect ? tunaSrcAnchorSelectDy : tunaPress ? tunaSrcAnchorPressDy : tunaSrcAnchorNormalDy));

            canvas.drawBitmap(tunaSelect ? tunaSrcAnchorSelect : tunaPress ? tunaSrcAnchorPress : tunaSrcAnchorNormal, tunaAnchorMatrix, tunaPaint);

            canvas.translate(-anchorDx + (tunaSelect ? -tunaSrcAnchorSelectDx : tunaPress ? -tunaSrcAnchorPressDx : -tunaSrcAnchorNormalDx), -anchorDy
                    + (tunaSelect ? -tunaSrcAnchorSelectDy : tunaPress ? -tunaSrcAnchorPressDy : -tunaSrcAnchorNormalDy));
        }

        // draw tunaText
        if (tunaTextValue != null) {
            float f[] = drawTunaText(
                    canvas,
                    tunaTextValue,
                    tunaWidth,
                    (tunaWidth >> 1) + tunaTextDx + tunaSrcLeftWidth * 0.5f + tunaSrcLeftPadding * 0.5f - tunaSrcRightWidth * 0.5f - tunaSrcRightPadding * 0.5f,
                    (tunaHeight >> 1) + tunaTextDy,
                    tunaTextPaddingLeft + tunaSrcLeftWidth,
                    tunaTextPaddingRight + tunaSrcRightWidth,
                    initTunaTextPaint(Paint.Style.FILL,
                            tunaMaterialPlay ? tunaTextColorPress : tunaSelect ? tunaTextColorSelect : tunaPress ? tunaTextColorPress : tunaTextColorNormal, tunaTextSize,
                            tunaTextShadowRadius, tunaTextShadowColor, tunaTextShadowDx, tunaTextShadowDy, tunaTextTypeFace, Paint.Align.CENTER),
                    tunaTextGravity,
                    tunaTextRowSpaceRatio,
                    tunaTextValueMeasureList);

            tunaTextDrawWidth = f[0];
            tunaTextEndOffsetCenterX = f[1];
            tunaTextEndOffsetCenterY = f[2];
        }

        // draw tunaContent
        if (tunaContentValue != null) {
            //Conversion of style into tunaTextGravity to use drawTunaText method!
            TunaTextGravity tunaTextGravityFromContent = tunaTextGravityArray[tunaContentGravity.nativeInt];

            float f[] = drawTunaText(
                    canvas,
                    tunaContentValue,
                    tunaWidth,
                    (tunaWidth >> 1) + tunaContentDx + tunaSrcLeftWidth * 0.5f + tunaSrcLeftPadding * 0.5f - tunaSrcRightWidth * 0.5f - tunaSrcRightPadding * 0.5f,
                    (tunaHeight >> 1) + tunaContentDy,
                    tunaContentPaddingLeft + tunaSrcLeftWidth,
                    tunaContentPaddingRight + tunaSrcRightWidth,
                    initTunaTextPaint(Paint.Style.FILL,
                            tunaMaterialPlay ? tunaContentColorPress : tunaSelect ? tunaContentColorSelect : tunaPress ? tunaContentColorPress : tunaContentColorNormal, tunaContentSize,
                            tunaContentShadowRadius, tunaContentShadowColor, tunaContentShadowDx, tunaContentShadowDy, tunaContentTypeFace, Paint.Align.CENTER),
                    tunaTextGravityFromContent,
                    tunaContentRowSpaceRatio,
                    tunaContentValueMeasureList);

            tunaContentDrawWidth = f[0];
            tunaContentEndOffsetCenterX = f[1];
            tunaContentEndOffsetCenterY = f[2];
        }

        // draw tunaBitmapLeft,the draw position is half of the tunaWidth minus
        // the tunaSrcLeftPadding and tunaTextActualDrawWidth*0.5f
        if (tunaSrcLeft != null) {
            float dx = (tunaWidth >> 1) - tunaSrcLeftWidth * 0.5f - tunaTextDrawWidth * 0.5f - tunaSrcLeftPadding * 0.5f + tunaSrcLeftDx;
            float dy = (tunaHeight >> 1) - tunaSrcLeftHeight * 0.5f + tunaSrcLeftDy;

            canvas.translate(dx, dy);
            canvas.drawBitmap(tunaSrcLeft, tunaLeftMatrix, tunaPaint);
            canvas.translate(-dx, -dy);
        }

        if (tunaSrcRight != null) {

            float dx = (tunaWidth >> 1) - tunaSrcRightWidth * 0.5f + tunaTextDrawWidth * 0.5f + tunaSrcRightPadding * 0.5f + tunaSrcRightDx;
            float dy = (tunaHeight >> 1) - tunaSrcRightHeight * 0.5f + tunaSrcRightDy;

            canvas.translate(dx, dy);
            canvas.drawBitmap(tunaSrcRight, tunaRightMatrix, tunaPaint);
            canvas.translate(-dx, -dy);
        }

        // draw tunaTextMark
        if (tunaTextMark) {
            drawTunaTextMark(canvas, tunaTextMarkRadius,
                    initTunaPaint(tunaTextMarkColor),
                    tunaTextMarkRadius,
                    tunaTextMarkDx, tunaTextMarkDy,
                    tunaTextDx + tunaTextEndOffsetCenterX + tunaTextMarkDx,
                    tunaTextDy + tunaTextEndOffsetCenterY + tunaTextMarkDy,
                    tunaTextMarkTextValue,
                    tunaTextMarkTextColor,
                    tunaTextMarkTextSize,
                    tunaTextMarkTextValueMeasureList);
        }

        // draw tunaContentMark
        if (tunaContentMark) {
            drawTunaTextMark(canvas, tunaContentMarkRadius,
                    initTunaPaint(tunaContentMarkColor),
                    tunaContentMarkRadius,
                    tunaContentMarkDx, tunaContentMarkDy,
                    tunaContentDx + tunaContentEndOffsetCenterX + tunaContentMarkDx,
                    tunaContentDy + tunaContentEndOffsetCenterY + tunaContentMarkDy,
                    tunaContentMarkTextValue,
                    tunaContentMarkTextColor,
                    tunaContentMarkTextSize,
                    tunaContentMarkTextValueMeasureList);
        }

        // draw tunaForeground
        if (tunaSelect && tunaForegroundSelect != Color.TRANSPARENT) {
            if (tunaClassic) {
                drawTunaRectClassic(canvas, tunaWidth, tunaHeight, tunaForegroundSelect, tunaRadius);
            } else {
                drawTunaRectCustom(canvas, tunaWidth, tunaHeight, tunaForegroundSelect, 0, Color.TRANSPARENT, tunaRadiusLeftTop, tunaRadiusLeftBottom, tunaRadiusRightTop, tunaRadiusRightBottom);
            }
        } else if (tunaPress && tunaForegroundPress != Color.TRANSPARENT) {
            if (tunaClassic) {
                drawTunaRectClassic(canvas, tunaWidth, tunaHeight, tunaForegroundPress, tunaRadius);
            } else {
                drawTunaRectCustom(canvas, tunaWidth, tunaHeight, tunaForegroundPress, 0, Color.TRANSPARENT, tunaRadiusLeftTop, tunaRadiusLeftBottom, tunaRadiusRightTop, tunaRadiusRightBottom);
            }
        } else if (tunaForegroundNormal != Color.TRANSPARENT) {
            if (tunaClassic) {
                drawTunaRectClassic(canvas, tunaWidth, tunaHeight, tunaForegroundNormal, tunaRadius);
            } else {
                drawTunaRectCustom(canvas, tunaWidth, tunaHeight, tunaForegroundNormal, 0, Color.TRANSPARENT, tunaRadiusLeftTop, tunaRadiusLeftBottom, tunaRadiusRightTop, tunaRadiusRightBottom);
            }
        }
        if (tunaRotate != 0) {
            canvas.rotate(-tunaRotate, tunaWidth >> 1, tunaHeight >> 1);
        }
        if (tunaDrawListener != null) {
            tunaDrawListener.tunaDraw(this);
        }
    }
}