package com.tunasushi.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.tunasushi.R;

import static com.tunasushi.tool.ConvertTool.convertToPX;
import static com.tunasushi.tool.ViewTool.getLinearGradient;

/**
 * @author TunaSashimi
 * @date 2015-10-30 16:55
 * @Copyright 2015 TunaSashimi. All rights reserved.
 * @Description
 */
public class TLayout extends RelativeLayout {
    protected String tag;

    protected int layoutWidth, layoutHeight;
    protected int layoutLayer;
    protected Paint layoutPaint;
    //
    protected Path layoutPath;

    protected RectF layoutRectF;
    //
    protected Matrix layoutMatrix;

    protected Matrix initMatrix(Matrix matrix, float sx, float sy) {
        if (matrix == null) {
            matrix = new Matrix();
        }
        matrix.reset();
        matrix.setScale(sx, sy);
        return matrix;
    }

    // layoutBackground in onLayout if background transparent and have drawn the case of default white shadow
    private int layoutBackground;

    private int layoutBackgroundAngle;

    public int getLayoutBackgroundAngle() {
        return layoutBackgroundAngle;
    }

    public void setLayoutBackgroundAngle(int layoutBackgroundAngle) {
        if (this.layoutBackgroundAngle != layoutBackgroundAngle) {
            this.layoutBackgroundAngle = layoutBackgroundAngle;
            invalidate();
        }
    }

    // layoutBackgroundStart default layoutBackground
    private int layoutBackgroundStart;

    public int getLayoutBackgroundStart() {
        return layoutBackgroundStart;
    }

    public void setLayoutBackgroundStart(int layoutBackgroundStart) {
        if (this.layoutBackgroundStart != layoutBackgroundStart) {
            this.layoutBackgroundStart = layoutBackgroundStart;
            invalidate();
        }
    }

    public void setLayoutBackgroundStart(String layoutBackgroundStart) {
        setLayoutBackgroundStart(Color.parseColor(layoutBackgroundStart));
    }

    // layoutBackgroundEnd default background
    private int layoutBackgroundEnd;

    public int getLayoutBackgroundEnd() {
        return layoutBackgroundEnd;
    }

    public void setLayoutBackgroundEnd(int layoutBackgroundEnd) {
        if (this.layoutBackgroundEnd != layoutBackgroundEnd) {
            this.layoutBackgroundEnd = layoutBackgroundEnd;
            invalidate();
        }
    }

    public void setLayoutBackgroundEnd(String layoutBackgroundEnd) {
        setLayoutBackgroundEnd(Color.parseColor(layoutBackgroundEnd));
    }

    // layoutBackgroundShader default null
    private Shader layoutBackgroundShader;

    //
    private float layoutBackgroundShadowRadius;

    public float getLayoutBackgroundShadowRadius() {
        return layoutBackgroundShadowRadius;
    }

    public void setLayoutBackgroundShadowRadius(float layoutBackgroundShadowRadius) {
        setLayoutBackgroundShadowRadiusRaw(layoutBackgroundShadowRadius);
    }

    public void setLayoutBackgroundShadowRadius(float layoutBackgroundShadowRadius, int unit) {
        setLayoutBackgroundShadowRadiusRaw(convertToPX(layoutBackgroundShadowRadius, unit));
    }

    private void setLayoutBackgroundShadowRadiusRaw(float layoutBackgroundShadowRadius) {
        if (this.layoutBackgroundShadowRadius != layoutBackgroundShadowRadius) {
            this.layoutBackgroundShadowRadius = layoutBackgroundShadowRadius;
            invalidate();
        }
    }

    //
    private int layoutBackgroundShadowColor;

    public int getLayoutBackgroundShadowColor() {
        return layoutBackgroundShadowColor;
    }

    public void setLayoutBackgroundShadowColor(int layoutBackgroundShadowColor) {
        this.layoutBackgroundShadowColor = layoutBackgroundShadowColor;
    }

    //
    private float layoutBackgroundShadowDx;

    public float getLayoutBackgroundShadowDx() {
        return layoutBackgroundShadowDx;
    }

    public void setLayoutBackgroundShadowDx(float layoutBackgroundShadowDx) {
        setLayoutBackgroundShadowDxRaw(layoutBackgroundShadowDx);
    }

    public void setLayoutBackgroundShadowDx(float layoutBackgroundShadowDx, int unit) {
        setLayoutBackgroundShadowDxRaw(convertToPX(layoutBackgroundShadowDx, unit));
    }

    private void setLayoutBackgroundShadowDxRaw(float layoutBackgroundShadowDx) {
        if (this.layoutBackgroundShadowDx != layoutBackgroundShadowDx) {
            this.layoutBackgroundShadowDx = layoutBackgroundShadowDx;
            invalidate();
        }
    }

    //
    private float layoutBackgroundShadowDy;

    public float getLayoutBackgroundShadowDy() {
        return layoutBackgroundShadowDy;
    }

    public void setLayoutBackgroundShadowDy(float layoutBackgroundShadowDy) {
        setLayoutBackgroundShadowDyRaw(layoutBackgroundShadowDy);
    }

    public void setLayoutBackgroundShadowDy(float layoutBackgroundShadowDy, int unit) {
        setLayoutBackgroundShadowDyRaw(convertToPX(layoutBackgroundShadowDy, unit));
    }

    private void setLayoutBackgroundShadowDyRaw(float layoutBackgroundShadowDy) {
        if (this.layoutBackgroundShadowDy != layoutBackgroundShadowDy) {
            this.layoutBackgroundShadowDy = layoutBackgroundShadowDy;
            invalidate();
        }
    }

    //
    private Bitmap layoutSrc;

    public Bitmap getLayoutSrc() {
        return layoutSrc;
    }

    public void setLayoutSrc(Bitmap layoutSrc) {
        this.layoutSrc = layoutSrc;
    }

    //
    private float layoutSrcShadowRadius;

    public float getLayoutSrcShadowRadius() {
        return layoutSrcShadowRadius;
    }

    public void setLayoutSrcShadowRadius(float layoutSrcShadowRadius) {
        setLayoutSrcShadowRadiusRaw(layoutSrcShadowRadius);
    }

    public void setLayoutSrcShadowRadius(float layoutSrcShadowRadius, int unit) {
        setLayoutSrcShadowRadiusRaw(convertToPX(layoutSrcShadowRadius, unit));
    }

    private void setLayoutSrcShadowRadiusRaw(float layoutSrcShadowRadius) {
        if (this.layoutSrcShadowRadius != layoutSrcShadowRadius) {
            this.layoutSrcShadowRadius = layoutSrcShadowRadius;
            invalidate();
        }
    }

    //
    private float layoutSrcShadowDx;

    public float getLayoutSrcShadowDx() {
        return layoutSrcShadowDx;
    }

    public void setLayoutSrcShadowDx(float layoutSrcShadowDx) {
        setLayoutSrcShadowDxRaw(layoutSrcShadowDx);
    }

    public void setLayoutSrcShadowDx(float layoutSrcShadowDx, int unit) {
        setLayoutSrcShadowDxRaw(convertToPX(layoutSrcShadowDx, unit));
    }

    private void setLayoutSrcShadowDxRaw(float layoutSrcShadowDx) {
        if (this.layoutSrcShadowDx != layoutSrcShadowDx) {
            this.layoutSrcShadowDx = layoutSrcShadowDx;
            invalidate();
        }
    }

    //
    private float layoutSrcShadowDy;

    public float getLayoutSrcShadowDy() {
        return layoutSrcShadowDy;
    }

    public void setLayoutSrcShadowDy(float layoutSrcShadowDy) {
        setLayoutSrcShadowDyRaw(layoutSrcShadowDy);
    }

    public void setLayoutSrcShadowDy(float layoutSrcShadowDy, int unit) {
        setLayoutSrcShadowDyRaw(convertToPX(layoutSrcShadowDy, unit));
    }

    private void setLayoutSrcShadowDyRaw(float layoutSrcShadowDy) {
        if (this.layoutSrcShadowDy != layoutSrcShadowDy) {
            this.layoutSrcShadowDy = layoutSrcShadowDy;
            invalidate();
        }
    }

    // attention layoutPorterDuffXferStyle default 0 instead of -1!
    protected PorterDuffXfermode layoutPorterDuffXferStyle;
    private static final Mode[] layoutPorterDuffXferStyleArray = {Mode.SRC_IN, Mode.SRC_OUT,};

    // layoutStrokeWidth default 0
    private float layoutStrokeWidth;

    public float getLayoutStrokeWidth() {
        return layoutStrokeWidth;
    }

    public void setLayoutStrokeWidth(float layoutStrokeWidth) {
        setLayoutStrokeWidthRaw(layoutStrokeWidth);
    }

    public void setLayoutStrokeWidth(float layoutStrokeWidth, int unit) {
        setLayoutStrokeWidthRaw(convertToPX(layoutStrokeWidth, unit));
    }

    private void setLayoutStrokeWidthRaw(float layoutStrokeWidth) {
        if (this.layoutStrokeWidth != layoutStrokeWidth) {
            this.layoutStrokeWidth = layoutStrokeWidth;
            invalidate();
        }
    }

    // layoutStrokeColor default transparent
    private int layoutStrokeColor;

    public int getLayoutStrokeColor() {
        return layoutStrokeColor;
    }

    public void setLayoutStrokeColor(int layoutStrokeColor) {
        this.layoutStrokeColor = layoutStrokeColor;
    }

    // layoutRadius default 0
    private float layoutRadius;

    public float getLayoutRadius() {
        return layoutRadius;
    }

    public void setLayoutRadius(float layoutRadius) {
        setLayoutRadiusRaw(layoutRadius);
    }

    public void setLayoutRadius(float layoutRadius, int unit) {
        setLayoutRadiusRaw(convertToPX(layoutRadius, unit));
    }

    private void setLayoutRadiusRaw(float layoutRadius) {
        if (this.layoutRadius != layoutRadius) {
            this.layoutRadius = layoutRadius;
            invalidate();
        }
    }

    // layoutRadiusTopLeft default 0
    private float layoutRadiusTopLeft;

    public float getLayoutRadiusTopLeft() {
        return layoutRadiusTopLeft;
    }

    public void setLayoutRadiusTopLeft(float layoutRadiusTopLeft) {
        setLayoutRadiusTopLeftRaw(layoutRadiusTopLeft);
    }

    public void setLayoutRadiusTopLeft(float layoutRadiusTopLeft, int unit) {
        setLayoutRadiusTopLeftRaw(convertToPX(layoutRadiusTopLeft, unit));
    }

    private void setLayoutRadiusTopLeftRaw(float layoutRadiusTopLeft) {
        if (this.layoutRadiusTopLeft != layoutRadiusTopLeft) {
            this.layoutRadiusTopLeft = layoutRadiusTopLeft;
            invalidate();
        }
    }

    // layoutRadiusBottomLeft default 0
    private float layoutRadiusBottomLeft;

    public float getLayoutRadiusBottomLeft() {
        return layoutRadiusBottomLeft;
    }

    public void setLayoutRadiusBottomLeft(float layoutRadiusBottomLeft) {
        setLayoutRadiusBottomLeftRaw(layoutRadiusBottomLeft);
    }

    public void setLayoutRadiusBottomLeft(float layoutRadiusBottomLeft, int unit) {
        setLayoutRadiusBottomLeftRaw(convertToPX(layoutRadiusBottomLeft, unit));
    }

    private void setLayoutRadiusBottomLeftRaw(float layoutRadiusBottomLeft) {
        if (this.layoutRadiusBottomLeft != layoutRadiusBottomLeft) {
            this.layoutRadiusBottomLeft = layoutRadiusBottomLeft;
            invalidate();
        }
    }

    // layoutRadiusTopRight default 0
    private float layoutRadiusTopRight;

    public float getLayoutRadiusTopRight() {
        return layoutRadiusTopRight;
    }

    public void setLayoutRadiusTopRight(float layoutRadiusTopRight) {
        setLayoutRadiusTopRightRaw(layoutRadiusTopRight);
    }

    public void setLayoutRadiusTopRight(float layoutRadiusTopRight, int unit) {
        setLayoutRadiusTopRightRaw(convertToPX(layoutRadiusTopRight, unit));
    }

    private void setLayoutRadiusTopRightRaw(float layoutRadiusTopRight) {
        if (this.layoutRadiusTopRight != layoutRadiusTopRight) {
            this.layoutRadiusTopRight = layoutRadiusTopRight;
            invalidate();
        }
    }

    // layoutRadiusBottomRight default 0
    private float layoutRadiusBottomRight;

    public float getLayoutRadiusBottomRight() {
        return layoutRadiusBottomRight;
    }

    public void setLayoutRadiusBottomRight(float layoutRadiusBottomRight) {
        setLayoutRadiusBottomRightRaw(layoutRadiusBottomRight);
    }

    public void setLayoutRadiusBottomRight(float layoutRadiusBottomRight, int unit) {
        setLayoutRadiusBottomRightRaw(convertToPX(layoutRadiusBottomRight, unit));
    }

    private void setLayoutRadiusBottomRightRaw(float layoutRadiusBottomRight) {
        if (this.layoutRadiusBottomRight != layoutRadiusBottomRight) {
            this.layoutRadiusBottomRight = layoutRadiusBottomRight;
            invalidate();
        }
    }

    public TLayout(Context context) {
        this(context, null);
    }

    public TLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        tag = TLayout.class.getSimpleName();

        //By ViewGroup default, it is set to WILL_NOT_DRAW, which is from a performance consideration, so that, onDraw will not be called.
        //If we want a ViweGroup important onDraw method, there are two methods:
        //1, in the constructor function to set a color, such as # 00000000
        //.2, in the constructor function, call setWillNotDraw (false), remove it WILL_NOT_DRAW flag.

        setWillNotDraw(false);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TLayout, 0, defStyle);

        layoutBackground = typedArray.getColor(R.styleable.TLayout_layoutBackground, Color.TRANSPARENT);

        //
        layoutBackgroundAngle = typedArray.getInt(R.styleable.TLayout_layoutBackgroundAngle, Integer.MAX_VALUE);
        if (layoutBackgroundAngle != Integer.MAX_VALUE) {
            layoutBackgroundStart = typedArray.getColor(R.styleable.TLayout_layoutBackgroundStart, layoutBackground);
            layoutBackgroundEnd = typedArray.getColor(R.styleable.TLayout_layoutBackgroundEnd, layoutBackground);
        }

        //
        layoutBackgroundShadowRadius = typedArray.getDimension(R.styleable.TLayout_layoutBackgroundShadowRadius, 0);
        if (layoutBackgroundShadowRadius > 0) {
            layoutBackgroundShadowColor = typedArray.getColor(R.styleable.TLayout_layoutBackgroundShadowColor, Color.TRANSPARENT);
            layoutBackgroundShadowDx = typedArray.getDimension(R.styleable.TLayout_layoutBackgroundShadowDx, 0);
            layoutBackgroundShadowDy = typedArray.getDimension(R.styleable.TLayout_layoutBackgroundShadowDy, 0);
        }

        //
        int layoutSrcId = typedArray.getResourceId(R.styleable.TLayout_layoutSrc, -1);
        if (layoutSrcId != -1) {

            // layoutXfermodeIndex default PorterDuff.Mode.SRC_IN
            int layoutXfermodeIndex = typedArray.getInt(R.styleable.TView_porterDuffXferStyle, 0);
            layoutPorterDuffXferStyle = new PorterDuffXfermode(layoutPorterDuffXferStyleArray[layoutXfermodeIndex]);

            //
            layoutSrc = BitmapFactory.decodeResource(getResources(), layoutSrcId);

            //
            layoutSrcShadowRadius = typedArray.getDimension(R.styleable.TLayout_layoutSrcShadowRadius, 0);
            if (layoutSrcShadowRadius > 0) {
                layoutSrcShadowDx = typedArray.getDimension(R.styleable.TLayout_layoutSrcShadowDx, 0);
                layoutSrcShadowDy = typedArray.getDimension(R.styleable.TLayout_layoutSrcShadowDy, 0);
            }
        }

        //
        layoutStrokeWidth = typedArray.getDimension(R.styleable.TLayout_layoutStrokeWidth, 0);
        layoutStrokeColor = typedArray.getColor(R.styleable.TLayout_layoutStrokeColor, Color.TRANSPARENT);

        //
        layoutRadius = typedArray.getDimension(R.styleable.TLayout_layoutRadius, 0);
        layoutRadiusTopLeft = typedArray.getDimension(R.styleable.TLayout_layoutRadiusTopLeft, layoutRadius);
        layoutRadiusBottomLeft = typedArray.getDimension(R.styleable.TLayout_layoutRadiusBottomLeft, layoutRadius);
        layoutRadiusTopRight = typedArray.getDimension(R.styleable.TLayout_layoutRadiusTopRight, layoutRadius);
        layoutRadiusBottomRight = typedArray.getDimension(R.styleable.TLayout_layoutRadiusBottomRight, layoutRadius);

        typedArray.recycle();

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        layoutWidth = getWidth();
        layoutHeight = getHeight();

        if (layoutSrc != null || layoutSrcShadowRadius > 0 || layoutBackgroundShadowRadius > 0 || layoutBackgroundAngle != Integer.MAX_VALUE) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        if ((layoutSrc != null || layoutBackgroundShadowRadius > 0) && layoutBackground == Color.TRANSPARENT) {
            layoutBackground = Color.WHITE;
        }

        //
        if (layoutBackgroundAngle != Integer.MAX_VALUE) {
            layoutBackgroundShader = getLinearGradient(layoutWidth, layoutHeight, layoutBackgroundAngle, layoutBackgroundStart, layoutBackgroundEnd);
        }

        //
        int layoutSrcWidthRaw = 0, layoutSrcHeightRaw = 0;
        if (layoutSrc != null) {
            layoutSrcWidthRaw = layoutSrc.getWidth();
            layoutSrcHeightRaw = layoutSrc.getHeight();

            initMatrix(layoutMatrix, (layoutWidth - layoutSrcShadowRadius * 2f - layoutBackgroundShadowRadius * 2f - layoutBackgroundShadowDx * 2f) / layoutSrcWidthRaw,
                    (layoutHeight - layoutSrcShadowRadius * 2f - layoutBackgroundShadowRadius * 2f - layoutBackgroundShadowDy * 2f) / layoutSrcHeightRaw);
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        boolean needSaveLayer = (layoutSrc != null);
        if (needSaveLayer) {
            // draw the src/dst example into our offscreen bitmap
            layoutLayer = canvas.saveLayer(0, 0, layoutWidth, layoutHeight, null);
        }
//
        drawRectCustom(canvas, layoutBackgroundShadowRadius + layoutBackgroundShadowDx, layoutBackgroundShadowRadius + layoutBackgroundShadowDy,
                layoutWidth - layoutBackgroundShadowRadius - layoutBackgroundShadowDx, layoutHeight - layoutBackgroundShadowRadius
                        - layoutBackgroundShadowDy, layoutBackground,
                layoutBackgroundShader,
                layoutBackgroundShadowRadius,
                layoutBackgroundShadowColor,
                layoutBackgroundShadowDx,
                layoutBackgroundShadowDy,
                layoutStrokeWidth,
                layoutStrokeColor, layoutRadiusTopLeft, layoutRadiusBottomLeft, layoutRadiusTopRight, layoutRadiusBottomRight);

        // draw layoutBitmap
        if (needSaveLayer) {
            layoutPaint.setXfermode(layoutPorterDuffXferStyle);

            canvas.translate(layoutBackgroundShadowDx * 2f + layoutSrcShadowRadius - layoutSrcShadowDx,
                    layoutBackgroundShadowDy * 2f + layoutSrcShadowRadius - layoutSrcShadowDy);
            canvas.drawBitmap(
                    layoutSrc,
                    layoutMatrix,
                    initPaint(layoutPaint, layoutSrcShadowRadius,
                            layoutSrcShadowDx, layoutSrcShadowDy));
            canvas.translate(-layoutBackgroundShadowDx * 2f - layoutSrcShadowRadius + layoutSrcShadowDx,
                    -layoutBackgroundShadowDy * 2f - layoutSrcShadowRadius + layoutSrcShadowDy);

            layoutPaint.setXfermode(null);

        }
    }


    // 10
    protected void drawRectCustom(Canvas canvas, int width, int height, int fillColor, float strokeWidth, int strokeColor, float radiusTopLeft, float radiusBottomLeft,
                                  float radiusTopRight, float radiusBottomRight) {

        drawRectCustom(canvas, 0, 0, width, height, fillColor, null, 0, Color.TRANSPARENT, 0, 0, strokeWidth, strokeColor, radiusTopLeft, radiusBottomLeft, radiusTopRight,
                radiusBottomRight);
    }

    // 10
    protected void drawRectCustom(Canvas canvas, int width, int height, Shader shader, float strokeWidth, int strokeColor, float radiusTopLeft, float radiusBottomLeft,
                                  float radiusTopRight, float radiusBottomRight) {

        drawRectCustom(canvas, 0, 0, width, height, Color.TRANSPARENT, shader, 0, Color.TRANSPARENT, 0, 0, strokeWidth, strokeColor, radiusTopLeft, radiusBottomLeft,
                radiusTopRight, radiusBottomRight);
    }

    // 14
    protected void drawRectCustom(Canvas canvas, int width, int height, int fillColor, float shadowRadius, int shadowColor, float shadowDx, float shadowDy, float strokeWidth,
                                  int strokeColor, float radiusTopLeft, float radiusBottomLeft, float radiusTopRight, float radiusBottomRight) {

        drawRectCustom(canvas, 0, 0, width, height, fillColor, null, shadowRadius, shadowColor, shadowDx, shadowDy, strokeWidth, strokeColor, radiusTopLeft, radiusBottomLeft,
                radiusTopRight, radiusBottomRight);
    }

    // 14
    protected void drawRectCustom(Canvas canvas, int width, int height, Shader shader, float shadowRadius, int shadowColor, float shadowDx, float shadowDy, float strokeWidth,
                                  int strokeColor, float radiusTopLeft, float radiusBottomLeft, float radiusTopRight, float radiusBottomRight) {

        drawRectCustom(canvas, 0, 0, width, height, Color.TRANSPARENT, shader, shadowRadius, shadowColor, shadowDx, shadowDy, strokeWidth, strokeColor, radiusTopLeft,
                radiusBottomLeft, radiusTopRight, radiusBottomRight);
    }

    // 15
    protected void drawRectCustom(Canvas canvas, float left, float top, float right, float bottom, int fillColor, Shader shader, float shadowRadius, int shadowColor,
                                  float shadowDx, float shadowDy, float strokeWidth, int strokeColor, float radiusTopLeft, float radiusBottomLeft, float radiusTopRight, float radiusBottomRight) {

        float[] radii = {radiusTopLeft, radiusTopLeft, radiusTopRight, radiusTopRight, radiusBottomRight, radiusBottomRight, radiusBottomLeft, radiusBottomLeft};
        if (strokeWidth > 0) {
            canvas.drawPath(
                    initPathRoundRect(initRectF(left + strokeWidth * 0.5f, top + strokeWidth * 0.5f, right - strokeWidth * 0.5f, bottom - strokeWidth * 0.5f), radii,
                            Direction.CW), initPaint(Style.STROKE, strokeColor, strokeWidth));
        }

        int radiiLength = radii.length;
        for (int i = 0; i < radiiLength; i++) {
            radii[i] -= strokeWidth;
            radii[i] = radii[i] >= 0 ? radii[i] : 0;
        }

        canvas.drawPath(
                initPathRoundRect(initRectF(left + strokeWidth, top + strokeWidth, right - strokeWidth, bottom - strokeWidth), radii, Direction.CW),
                shader == null ? shadowRadius == 0 ? initPaint(Style.FILL, fillColor) : initPaint(Style.FILL, fillColor, shadowRadius, shadowColor, shadowDx,
                        shadowDy) : shadowRadius == 0 ? initPaint(Style.FILL, shader) : initPaint(Style.FILL, shader, shadowRadius, shadowColor, shadowDx,
                        shadowDy));
    }

    //
    protected RectF initRectF(float left, float top, float right, float bottom) {
        if (layoutRectF == null) {
            layoutRectF = new RectF(left, top, right, bottom);
        }
        layoutRectF.set(left, top, right, bottom);
        return layoutRectF;
    }

    //
    // 0
    protected Paint initPaint() {
        if (layoutPaint == null) {
            return layoutPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        } else {
            layoutPaint.reset();

            layoutPaint.setShader(null);
            layoutPaint.clearShadowLayer();

            layoutPaint.setAntiAlias(true);
        }
        return layoutPaint;
    }

    // 1
    protected Paint initPaint(int alpha) {
        return initPaint(null, 0, Color.TRANSPARENT, null, 0, Color.TRANSPARENT, 0, 0, alpha);
    }

    // 2
    protected Paint initPaint(Style style, int color) {
        return initPaint(style, 0, color, null, 0, Color.TRANSPARENT, 0, 0, -1);

    }

    // 3
    protected Paint initPaint(Style style, int color, int alpha) {
        return initPaint(style, 0, color, null, 0, Color.TRANSPARENT, 0, 0, alpha);
    }

    // 4
    protected Paint initPaint(Style style, int color, Shader shader, int alpha) {
        return initPaint(style, 0, color, shader, 0, Color.TRANSPARENT, 0, 0, alpha);
    }

    // 3
    protected Paint initPaint(Style style, int color, Shader shader) {
        return initPaint(style, 0, color, shader, 0, Color.TRANSPARENT, 0, 0, -1);
    }

    // 2
    protected Paint initPaint(Style style, Shader shader) {
        return initPaint(style, 0, Color.TRANSPARENT, shader, 0, Color.TRANSPARENT, 0, 0, -1);
    }

    // 3
    protected Paint initPaint(Style style, Shader shader, int alpha) {
        return initPaint(style, 0, Color.TRANSPARENT, shader, 0, Color.TRANSPARENT, 0, 0, alpha);
    }


    // 3
    protected Paint initPaint(Style style, int color, float strokeWidth) {
        return initPaint(style, strokeWidth, color, null, 0, Color.TRANSPARENT, 0, 0, -1);
    }

    // 4
    protected Paint initPaint(Style style, int color, float strokeWidth, int alpha) {
        return initPaint(style, strokeWidth, color, null, 0, Color.TRANSPARENT, 0, 0, alpha);
    }

    // 6
    protected Paint initPaint(Style style, Shader shader, float shadowRadius, int shadowColor, float shadowDx, float shadowDy) {
        return initPaint(style, 0, Color.TRANSPARENT, shader, shadowRadius, shadowColor, shadowDx, shadowDy, -1);
    }

    // 6
    protected Paint initPaint(Style style, int color, float shadowRadius, int shadowColor, float shadowDx, float shadowDy) {
        return initPaint(style, 0, color, null, shadowRadius, shadowColor, shadowDx, shadowDy, -1);
    }

    // 9
    protected Paint initPaint(Style style, float strokeWidth, int color, Shader shader, float shadowRadius, int shadowColor, float shadowDx, float shadowDy, int alpha) {
        //
        initPaint();
        //
        if (style != null) {
            layoutPaint.setStyle(style);
        }
        if (strokeWidth != 0) {
            layoutPaint.setStrokeWidth(strokeWidth);
        }

        // When the shadow color can not be set to transparent, but can not set
        if (shader == null) {
            layoutPaint.setColor(color);
        } else {
            layoutPaint.setShader(shader);
        }

        if (shadowRadius != 0) {
            layoutPaint.setShadowLayer(shadowRadius, shadowDx, shadowDy, shadowColor);
        }
        if (alpha != -1) {
            layoutPaint.setAlpha(alpha);
        }
        return layoutPaint;
    }

    // 1
    protected Paint initPaint(float textSize) {
        return initPaint(null, Color.TRANSPARENT, textSize, 0, Color.TRANSPARENT, 0, 0, null);
    }

    // 4
    protected Paint initPaint(Style style, int color, float textSize, Align align) {
        return initPaint(style, color, textSize, 0, Color.TRANSPARENT, 0, 0, align);
    }

    // 8
    protected Paint initPaint(Style style, int color, float textSize, float shadowRadius, int shadowColor, float shadowDx, float shadowDy, Align align) {
        //
        initPaint();
        //
        if (style != null) {
            layoutPaint.setStyle(style);
        }

        layoutPaint.setColor(color);

        if (textSize != 0) {
            layoutPaint.setTextSize(textSize);
        }
        if (shadowRadius != 0) {
            layoutPaint.setShadowLayer(shadowRadius, shadowDx, shadowDy, shadowColor);
        }
        if (align != null) {
            layoutPaint.setTextAlign(align);
        }
        return layoutPaint;
    }

    // 4
    protected Paint initPaint(Paint paint, float shadowRadius, float shadowDx, float shadowDy) {
        paint.clearShadowLayer();
        if (shadowRadius > 0) {
            paint.setShadowLayer(shadowRadius, shadowDx, shadowDy, Color.WHITE);
        }
        return paint;
    }

    protected Path initPathRoundRect(RectF rect, float[] radii, Direction dir) {
        if (layoutPath == null) {
            layoutPath = new Path();
        } else {
            layoutPath.reset();
        }
        // This setting will cause the following message appears reading xml
        // The graphics preview in the layout editor may not be accurate:
        // Different corner sizes are not supported in Path.addRoundRect.
        // (Ignore for this session)
        layoutPath.addRoundRect(rect, radii, dir);
        return layoutPath;
    }
}
