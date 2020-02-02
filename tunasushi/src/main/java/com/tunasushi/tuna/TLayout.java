package com.tunasushi.tuna;

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
import android.util.TypedValue;
import android.view.View;
import android.widget.RelativeLayout;

import com.tuna.R;

import static com.tunasushi.tool.DeviceTool.applyDimension;
import static com.tunasushi.tool.DeviceTool.getViewDisplayMetrics;
import static com.tunasushi.tool.ViewTool.getLinearGradient;
import static com.tunasushi.tuna.TView.initMatrix;


/**
 * @author sashimi
 * @date 10/30/15 16:55
 * @Copyright 2015 Sashimi. All rights reserved.
 * @Description
 */
public class TLayout extends RelativeLayout {

    protected String Tag = TLayout.class.getSimpleName();

    protected int layoutWidth, layoutHeight;
    protected int layoutLayer;
    protected Paint layoutPaint;
    //
    //
    protected Path layoutPath;

    protected Matrix layoutMatrix;
    protected RectF layoutRectF;

    // layoutBackgroundNormal in onLayout if backgroundNormal transparent and have drawn the case of default white shadow
    private int layoutBackgroundNormal;

    public int getBackgroundNormal() {
        return layoutBackgroundNormal;
    }

    public void setBackgroundNormal(int layoutBackgroundNormal) {
        this.layoutBackgroundNormal = layoutBackgroundNormal;
    }

    private int layoutBackgroundNormalAngle;

    public int getLayoutBackgroundNormalAngle() {
        return layoutBackgroundNormalAngle;
    }

    public void setLayoutBackgroundNormalAngle(int layuotBackgroundNormalAngle) {
        this.layoutBackgroundNormalAngle = layuotBackgroundNormalAngle;
    }

    // layoutBackgroundNormalGradientStart default layoutBackgroundNormal
    private int layoutBackgroundNormalGradientStart;

    public int getLayoutBackgroundNormalGradientStart() {
        return layoutBackgroundNormalGradientStart;
    }

    public void setBackgroundNormalGradientStart(int layoutBackgroundNormalGradientStart) {
        this.layoutBackgroundNormalGradientStart = layoutBackgroundNormalGradientStart;
    }

    // layoutBackgroundNormalGradientEnd default backgroundNormal
    private int layoutBackgroundNormalGradientEnd;

    public int getBackgroundNormalGradientEnd() {
        return layoutBackgroundNormalGradientEnd;
    }

    public void setBackgroundNormalGradientEnd(int layoutBackgroundNormalGradientEnd) {
        this.layoutBackgroundNormalGradientEnd = layoutBackgroundNormalGradientEnd;
    }

    // layoutBackgroundNormalShader default null
    private Shader layoutBackgroundNormalShader;

    public Shader getLayoutBackgroundNormalShader() {
        return layoutBackgroundNormalShader;
    }

    public void setLayoutBackgroundNormalShader(Shader layoutBackgroundNormalShader) {
        this.layoutBackgroundNormalShader = layoutBackgroundNormalShader;
    }

    //
    private float layoutBackgroundNormalShadowRadius;

    public float getLayoutBackgroundNormalShadowRadius() {
        return layoutBackgroundNormalShadowRadius;
    }

    public void setLayoutBackgroundNormalShadowRadius(float layoutBackgroundNormalShadowRadius) {
        setLayoutBackgroundNormalShadowRadius(TypedValue.COMPLEX_UNIT_DIP, layoutBackgroundNormalShadowRadius);
    }

    public void setLayoutBackgroundNormalShadowRadius(int unit, float layoutBackgroundNormalShadowRadius) {
        setLayoutBackgroundNormalShadowRadiusRaw(applyDimension(unit, layoutBackgroundNormalShadowRadius, getViewDisplayMetrics(this)));
    }

    private void setLayoutBackgroundNormalShadowRadiusRaw(float layoutBackgroundNormalShadowRadius) {
        if (this.layoutBackgroundNormalShadowRadius != layoutBackgroundNormalShadowRadius) {
            this.layoutBackgroundNormalShadowRadius = layoutBackgroundNormalShadowRadius;
            invalidate();
        }
    }

    //
    private int layoutBackgroundNormalShadowColor;

    public int getLayoutBackgroundNormalShadowColor() {
        return layoutBackgroundNormalShadowColor;
    }

    public void setLayoutBackgroundNormalShadowColor(int layoutBackgroundNormalShadowColor) {
        this.layoutBackgroundNormalShadowColor = layoutBackgroundNormalShadowColor;
    }

    //
    private float layoutBackgroundNormalShadowDx;

    public float getLayoutBackgroundNormalShadowDx() {
        return layoutBackgroundNormalShadowDx;
    }

    public void setLayoutBackgroundNormalShadowDx(float layoutBackgroundNormalShadowDx) {
        setLayoutBackgroundNormalShadowDx(TypedValue.COMPLEX_UNIT_DIP, layoutBackgroundNormalShadowDx);
    }

    public void setLayoutBackgroundNormalShadowDx(int unit, float layoutBackgroundNormalShadowDx) {
        setLayoutBackgroundNormalShadowDxRaw(applyDimension(unit, layoutBackgroundNormalShadowDx, getViewDisplayMetrics(this)));
    }

    private void setLayoutBackgroundNormalShadowDxRaw(float layoutBackgroundNormalShadowDx) {
        if (this.layoutBackgroundNormalShadowDx != layoutBackgroundNormalShadowDx) {
            this.layoutBackgroundNormalShadowDx = layoutBackgroundNormalShadowDx;
            invalidate();
        }
    }

    //
    private float layoutBackgroundNormalShadowDy;

    public float getLayoutBackgroundNormalShadowDy() {
        return layoutBackgroundNormalShadowDy;
    }

    public void setLayoutBackgroundNormalShadowDy(float layoutBackgroundNormalShadowDy) {
        setLayoutBackgroundNormalShadowDy(TypedValue.COMPLEX_UNIT_DIP, layoutBackgroundNormalShadowDy);
    }

    public void setLayoutBackgroundNormalShadowDy(int unit, float layoutBackgroundNormalShadowDy) {
        setLayoutBackgroundNormalShadowDyRaw(applyDimension(unit, layoutBackgroundNormalShadowDy, getViewDisplayMetrics(this)));
    }

    private void setLayoutBackgroundNormalShadowDyRaw(float layoutBackgroundNormalShadowDy) {
        if (this.layoutBackgroundNormalShadowDy != layoutBackgroundNormalShadowDy) {
            this.layoutBackgroundNormalShadowDy = layoutBackgroundNormalShadowDy;
            invalidate();
        }
    }

    //
    private Bitmap layoutSrcNormal;

    public Bitmap getLayoutBitmapSrcNormal() {
        return layoutSrcNormal;
    }

    public void setLayoutBitmapSrcNormal(Bitmap layoutSrcNormal) {
        this.layoutSrcNormal = layoutSrcNormal;
    }

    //
    private float layoutSrcNormalShadowRadius;

    public float getLayoutBitmapSrcNormalShadowRadius() {
        return layoutSrcNormalShadowRadius;
    }

    public void setLayoutBitmapSrcNormalShadowRadius(float layoutSrcNormalShadowRadius) {
        setLayoutBitmapSrcNormalShadowRadius(TypedValue.COMPLEX_UNIT_DIP, layoutSrcNormalShadowRadius);
    }

    public void setLayoutBitmapSrcNormalShadowRadius(int unit, float layoutSrcNormalShadowRadius) {
        setLayoutBitmapSrcNormalShadowRadiusRaw(applyDimension(unit, layoutSrcNormalShadowRadius, getViewDisplayMetrics(this)));
    }

    private void setLayoutBitmapSrcNormalShadowRadiusRaw(float layoutSrcNormalShadowRadius) {
        if (this.layoutSrcNormalShadowRadius != layoutSrcNormalShadowRadius) {
            this.layoutSrcNormalShadowRadius = layoutSrcNormalShadowRadius;
            invalidate();
        }
    }

    //
    private float layoutSrcNormalShadowDx;

    public float getLayoutBitmapSrcNormalShadowDx() {
        return layoutSrcNormalShadowDx;
    }

    public void setLayoutBitmapSrcNormalShadowDx(float layoutSrcNormalShadowDx) {
        setLayoutBitmapSrcNormalShadowDx(TypedValue.COMPLEX_UNIT_DIP, layoutSrcNormalShadowDx);
    }

    public void setLayoutBitmapSrcNormalShadowDx(int unit, float layoutSrcNormalShadowDx) {
        setLayoutBitmapSrcNormalShadowDxRaw(applyDimension(unit, layoutSrcNormalShadowDx, getViewDisplayMetrics(this)));
    }

    private void setLayoutBitmapSrcNormalShadowDxRaw(float layoutSrcNormalShadowDx) {
        if (this.layoutSrcNormalShadowDx != layoutSrcNormalShadowDx) {
            this.layoutSrcNormalShadowDx = layoutSrcNormalShadowDx;
            invalidate();
        }
    }

    //
    private float layoutSrcNormalShadowDy;

    public float getLayoutSrcNormalShadowDy() {
        return layoutSrcNormalShadowDy;
    }

    public void setLayoutSrcNormalShadowDy(float layoutSrcNormalShadowDy) {
        setLayoutBitmapSrcNormalShadowDy(TypedValue.COMPLEX_UNIT_DIP, layoutSrcNormalShadowDy);
    }

    public void setLayoutBitmapSrcNormalShadowDy(int unit, float layoutSrcNormalShadowDy) {
        setLayoutBitmapSrcNormalShadowDyRaw(applyDimension(unit, layoutSrcNormalShadowDy, getViewDisplayMetrics(this)));
    }

    private void setLayoutBitmapSrcNormalShadowDyRaw(float layoutSrcNormalShadowDy) {
        if (this.layoutSrcNormalShadowDy != layoutSrcNormalShadowDy) {
            this.layoutSrcNormalShadowDy = layoutSrcNormalShadowDy;
            invalidate();
        }
    }

    // attention layoutPorterDuffXfermode default 0 instead of -1!
    protected PorterDuffXfermode layoutPorterDuffXfermode;

    public enum LayoutPorterDuffXfermode {
        Layout_SRC_IN(0), Layout_SRC_OUT(1),
        ;
        final int nativeInt;

        LayoutPorterDuffXfermode(int ni) {
            nativeInt = ni;
        }
    }

    private static final Mode[] layoutPorterDuffXfermodeArray = {Mode.SRC_IN, Mode.SRC_OUT,};

    public PorterDuffXfermode getLayoutPorterDuffXfermode() {
        return layoutPorterDuffXfermode;
    }

    public void setLayoutPorterDuffXfermode(LayoutPorterDuffXfermode layoutXfermode) {
        this.layoutPorterDuffXfermode = new PorterDuffXfermode(layoutPorterDuffXfermodeArray[layoutXfermode.nativeInt]);
    }

    // layoutStrokeWidthNormal default 0
    private float layoutStrokeWidthNormal;

    public float getLayoutStrokeWidthNormal() {
        return layoutStrokeWidthNormal;
    }

    public void setLayoutStrokeWidthNormal(float layoutStrokeWidthNormal) {
        setLayoutStrokeWidthNormal(TypedValue.COMPLEX_UNIT_DIP, layoutStrokeWidthNormal);
    }

    public void setLayoutStrokeWidthNormal(int unit, float layoutStrokeWidthNormal) {
        setLayoutStrokeWidthNormalRaw(applyDimension(unit, layoutStrokeWidthNormal, getViewDisplayMetrics(this)));
    }

    private void setLayoutStrokeWidthNormalRaw(float layoutStrokeWidthNormal) {
        if (this.layoutStrokeWidthNormal != layoutStrokeWidthNormal) {
            this.layoutStrokeWidthNormal = layoutStrokeWidthNormal;
            invalidate();
        }
    }

    // layoutStrokeColorNormal default transparent
    private int layoutStrokeColorNormal;

    public int getLayoutStrokeColorNormal() {
        return layoutStrokeColorNormal;
    }

    public void setLayoutStrokeColorNormal(int layoutStrokeColorNormal) {
        this.layoutStrokeColorNormal = layoutStrokeColorNormal;
    }

    // layoutRadius default 0
    private float layoutRadius;

    public float getLayoutRadius() {
        return layoutRadius;
    }

    public void setLayoutRadius(float layoutRadius) {
        setLayoutRadius(TypedValue.COMPLEX_UNIT_DIP, layoutRadius);
    }

    public void setLayoutRadius(int unit, float layoutRadius) {
        setLayoutRadiusRaw(applyDimension(unit, layoutRadius, getViewDisplayMetrics(this)));
    }

    private void setLayoutRadiusRaw(float layoutRadius) {
        if (this.layoutRadius != layoutRadius) {
            this.layoutRadius = layoutRadius;
            invalidate();
        }
    }

    // layoutRadiusLeftTop default 0
    private float layoutRadiusLeftTop;

    public float getLayoutRadiusLeftTop() {
        return layoutRadiusLeftTop;
    }

    public void setLayoutRadiusLeftTop(float layoutRadiusLeftTop) {
        setLayoutRadiusLeftTop(TypedValue.COMPLEX_UNIT_DIP, layoutRadiusLeftTop);
    }

    public void setLayoutRadiusLeftTop(int unit, float layoutRadiusLeftTop) {
        setLayoutRadiusLeftTopRaw(applyDimension(unit, layoutRadiusLeftTop, getViewDisplayMetrics(this)));
    }

    private void setLayoutRadiusLeftTopRaw(float layoutRadiusLeftTop) {
        if (this.layoutRadiusLeftTop != layoutRadiusLeftTop) {
            this.layoutRadiusLeftTop = layoutRadiusLeftTop;
            invalidate();
        }
    }

    // layoutRadiusLeftBottom default 0
    private float layoutRadiusLeftBottom;

    public float getLayoutRadiusLeftBottom() {
        return layoutRadiusLeftBottom;
    }

    public void setLayoutRadiusLeftBottom(float layoutRadiusLeftBottom) {
        setLayoutRadiusLeftBottom(TypedValue.COMPLEX_UNIT_DIP, layoutRadiusLeftBottom);
    }

    public void setLayoutRadiusLeftBottom(int unit, float layoutRadiusLeftBottom) {
        setLayoutRadiusLeftBottomRaw(applyDimension(unit, layoutRadiusLeftBottom, getViewDisplayMetrics(this)));
    }

    private void setLayoutRadiusLeftBottomRaw(float layoutRadiusLeftBottom) {
        if (this.layoutRadiusLeftBottom != layoutRadiusLeftBottom) {
            this.layoutRadiusLeftBottom = layoutRadiusLeftBottom;
            invalidate();
        }
    }

    // layoutRadiusRightTop default 0
    private float layoutRadiusRightTop;

    public float getLayoutRadiusRightTop() {
        return layoutRadiusRightTop;
    }

    public void setLayoutRadiusRightTop(float layoutRadiusRightTop) {
        setLayoutRadiusRightTop(TypedValue.COMPLEX_UNIT_DIP, layoutRadiusRightTop);
    }

    public void setLayoutRadiusRightTop(int unit, float layoutRadiusRightTop) {
        setLayoutRadiusRightTopRaw(applyDimension(unit, layoutRadiusRightTop, getViewDisplayMetrics(this)));
    }

    private void setLayoutRadiusRightTopRaw(float layoutRadiusRightTop) {
        if (this.layoutRadiusRightTop != layoutRadiusRightTop) {
            this.layoutRadiusRightTop = layoutRadiusRightTop;
            invalidate();
        }
    }

    // layoutRadiusRightBottom default 0
    private float layoutRadiusRightBottom;

    public float getLayoutRadiusRightBottom() {
        return layoutRadiusRightBottom;
    }

    public void setLayoutRadiusRightBottom(float layoutRadiusRightBottom) {
        setLayoutRadiusRightBottom(TypedValue.COMPLEX_UNIT_DIP, layoutRadiusRightBottom);
    }

    public void setLayoutRadiusRightBottom(int unit, float layoutRadiusRightBottom) {
        setLayoutRadiusRightBottomRaw(applyDimension(unit, layoutRadiusRightBottom, getViewDisplayMetrics(this)));
    }

    private void setLayoutRadiusRightBottomRaw(float layoutRadiusRightBottom) {
        if (this.layoutRadiusRightBottom != layoutRadiusRightBottom) {
            this.layoutRadiusRightBottom = layoutRadiusRightBottom;
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

        //By ViewGroup default, it is set to WILL_NOT_DRAW, which is from a performance consideration, so that, onDraw will not be called.
        //If we want a ViweGroup important onDraw method, there are two methods:
        //1, in the constructor function to set a color, such as # 00000000
        //.2, in the constructor function, call setWillNotDraw (false), remove it WILL_NOT_DRAW flag.

        setWillNotDraw(false);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TLayout, 0, defStyle);

        layoutBackgroundNormal = typedArray.getColor(R.styleable.TLayout_layoutBackgroundNormal, Color.TRANSPARENT);

        //
        layoutBackgroundNormalAngle = typedArray.getInt(R.styleable.TLayout_layoutBackgroundNormalAngle, Integer.MAX_VALUE);
        if (layoutBackgroundNormalAngle != Integer.MAX_VALUE) {
            layoutBackgroundNormalGradientStart = typedArray.getColor(R.styleable.TLayout_layoutBackgroundNormalGradientStart, layoutBackgroundNormal);
            layoutBackgroundNormalGradientEnd = typedArray.getColor(R.styleable.TLayout_layoutBackgroundNormalGradientEnd, layoutBackgroundNormal);

        }

        //
        layoutBackgroundNormalShadowRadius = typedArray.getDimension(R.styleable.TLayout_layoutBackgroundNormalShadowRadius, 0);
        if (layoutBackgroundNormalShadowRadius > 0) {
            layoutBackgroundNormalShadowColor = typedArray.getColor(R.styleable.TLayout_layoutBackgroundNormalShadowColor, Color.TRANSPARENT);
            layoutBackgroundNormalShadowDx = typedArray.getDimension(R.styleable.TLayout_layoutBackgroundNormalShadowDx, 0);
            layoutBackgroundNormalShadowDy = typedArray.getDimension(R.styleable.TLayout_layoutBackgroundNormalShadowDy, 0);
        }

        //
        int layoutSrcNormalId = typedArray.getResourceId(R.styleable.TLayout_layoutSrcNormal, -1);
        if (layoutSrcNormalId != -1) {

            // layoutXfermodeIndex default PorterDuff.Mode.SRC_IN
            int layoutXfermodeIndex = typedArray.getInt(R.styleable.TView_porterDuffXfermode, 0);
            layoutPorterDuffXfermode = new PorterDuffXfermode(layoutPorterDuffXfermodeArray[layoutXfermodeIndex]);

            //
            layoutSrcNormal = BitmapFactory.decodeResource(getResources(), layoutSrcNormalId);

            //
            layoutSrcNormalShadowRadius = typedArray.getDimension(R.styleable.TLayout_layoutSrcNormalShadowRadius, 0);
            if (layoutSrcNormalShadowRadius > 0) {
                layoutSrcNormalShadowDx = typedArray.getDimension(R.styleable.TLayout_layoutSrcNormalShadowDx, 0);
                layoutSrcNormalShadowDy = typedArray.getDimension(R.styleable.TLayout_layoutSrcNormalShadowDy, 0);
            }
        }

        //
        layoutStrokeWidthNormal = typedArray.getDimension(R.styleable.TLayout_layoutStrokeWidthNormal, 0);
        layoutStrokeColorNormal = typedArray.getColor(R.styleable.TLayout_layoutStrokeColorNormal, Color.TRANSPARENT);

        //
        layoutRadius = typedArray.getDimension(R.styleable.TLayout_layoutRadius, 0);
        layoutRadiusLeftTop = typedArray.getDimension(R.styleable.TLayout_layoutRadiusLeftTop, layoutRadius);
        layoutRadiusLeftBottom = typedArray.getDimension(R.styleable.TLayout_layoutRadiusLeftBottom, layoutRadius);
        layoutRadiusRightTop = typedArray.getDimension(R.styleable.TLayout_layoutRadiusRightTop, layoutRadius);
        layoutRadiusRightBottom = typedArray.getDimension(R.styleable.TLayout_layoutRadiusRightBottom, layoutRadius);

        typedArray.recycle();

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        layoutWidth = getWidth();
        layoutHeight = getHeight();

        if (layoutSrcNormal != null || layoutSrcNormalShadowRadius > 0 || layoutBackgroundNormalShadowRadius > 0 || layoutBackgroundNormalAngle != Integer.MAX_VALUE) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        if ((layoutSrcNormal != null || layoutBackgroundNormalShadowRadius > 0) && layoutBackgroundNormal == Color.TRANSPARENT) {
            layoutBackgroundNormal = Color.WHITE;
        }

        //
        if (layoutBackgroundNormalAngle != Integer.MAX_VALUE) {
            layoutBackgroundNormalShader = getLinearGradient(layoutWidth, layoutHeight, layoutBackgroundNormalAngle, layoutBackgroundNormalGradientStart, layoutBackgroundNormalGradientEnd);
        }

        //
        int layoutSrcNormalWidthRaw = 0, layoutSrcNormalHeightRaw = 0;
        if (layoutSrcNormal != null) {
            layoutSrcNormalWidthRaw = layoutSrcNormal.getWidth();
            layoutSrcNormalHeightRaw = layoutSrcNormal.getHeight();

            initMatrix(layoutMatrix, (layoutWidth - layoutSrcNormalShadowRadius * 2f - layoutBackgroundNormalShadowRadius * 2f - layoutBackgroundNormalShadowDx * 2f) / layoutSrcNormalWidthRaw,
                    (layoutHeight - layoutSrcNormalShadowRadius * 2f - layoutBackgroundNormalShadowRadius * 2f - layoutBackgroundNormalShadowDy * 2f) / layoutSrcNormalHeightRaw);
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {

        boolean needSaveLayer = (layoutSrcNormal != null);
        if (needSaveLayer) {
            // draw the src/dst example into our offscreen bitmap
            layoutLayer = canvas.saveLayer(0, 0, layoutWidth, layoutHeight, null);
        }
//		
        drawRectCustom(canvas, layoutBackgroundNormalShadowRadius + layoutBackgroundNormalShadowDx, layoutBackgroundNormalShadowRadius + layoutBackgroundNormalShadowDy,
                layoutWidth - layoutBackgroundNormalShadowRadius - layoutBackgroundNormalShadowDx, layoutHeight - layoutBackgroundNormalShadowRadius
                        - layoutBackgroundNormalShadowDy, layoutBackgroundNormal,
                layoutBackgroundNormalShader,
                layoutBackgroundNormalShadowRadius,
                layoutBackgroundNormalShadowColor,
                layoutBackgroundNormalShadowDx,
                layoutBackgroundNormalShadowDy,
                layoutStrokeWidthNormal,
                layoutStrokeColorNormal, layoutRadiusLeftTop, layoutRadiusLeftBottom, layoutRadiusRightTop, layoutRadiusRightBottom);

        // draw layoutBitmap
        if (needSaveLayer) {
            layoutPaint.setXfermode(layoutPorterDuffXfermode);

            canvas.translate(layoutBackgroundNormalShadowDx * 2f + layoutSrcNormalShadowRadius - layoutSrcNormalShadowDx,
                    layoutBackgroundNormalShadowDy * 2f + layoutSrcNormalShadowRadius - layoutSrcNormalShadowDy);
            canvas.drawBitmap(
                    layoutSrcNormal,
                    layoutMatrix,
                    initPaint(layoutPaint, layoutSrcNormalShadowRadius,
                            layoutSrcNormalShadowDx, layoutSrcNormalShadowDy));
            canvas.translate(-layoutBackgroundNormalShadowDx * 2f - layoutSrcNormalShadowRadius + layoutSrcNormalShadowDx,
                    -layoutBackgroundNormalShadowDy * 2f - layoutSrcNormalShadowRadius + layoutSrcNormalShadowDy);

            layoutPaint.setXfermode(null);

        }
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
    protected Paint initPaint(Style style, int colorValue) {
        return initPaint(style, 0, colorValue, null, 0, Color.TRANSPARENT, 0, 0, -1);

    }

    // 3
    protected Paint initPaint(Style style, int colorValue, int alpha) {
        return initPaint(style, 0, colorValue, null, 0, Color.TRANSPARENT, 0, 0, alpha);
    }

    // 4
    protected Paint initPaint(Style style, int colorValue, Shader shader, int alpha) {
        return initPaint(style, 0, colorValue, shader, 0, Color.TRANSPARENT, 0, 0, alpha);
    }

    // 3
    protected Paint initPaint(Style style, int colorValue, Shader shader) {
        return initPaint(style, 0, colorValue, shader, 0, Color.TRANSPARENT, 0, 0, -1);
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
    protected Paint initPaint(Style style, int colorValue, float strokeWidth) {
        return initPaint(style, strokeWidth, colorValue, null, 0, Color.TRANSPARENT, 0, 0, -1);
    }

    // 4
    protected Paint initPaint(Style style, int colorValue, float strokeWidth, int alpha) {
        return initPaint(style, strokeWidth, colorValue, null, 0, Color.TRANSPARENT, 0, 0, alpha);
    }

    // 6
    protected Paint initPaint(Style style, Shader shader, float shadowRadius, int shadowColor, float shadowDx, float shadowDy) {
        return initPaint(style, 0, Color.TRANSPARENT, shader, shadowRadius, shadowColor, shadowDx, shadowDy, -1);
    }

    // 6
    protected Paint initPaint(Style style, int colorValue, float shadowRadius, int shadowColor, float shadowDx, float shadowDy) {
        return initPaint(style, 0, colorValue, null, shadowRadius, shadowColor, shadowDx, shadowDy, -1);
    }

    // 9
    protected Paint initPaint(Style style, float strokeWidth, int colorValue, Shader shader, float shadowRadius, int shadowColor, float shadowDx, float shadowDy, int alpha) {
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
            layoutPaint.setColor(colorValue);
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
    protected Paint initPaint(Style style, int colorValue, float textSize, Align align) {
        return initPaint(style, colorValue, textSize, 0, Color.TRANSPARENT, 0, 0, align);
    }

    // 8
    protected Paint initPaint(Style style, int colorValue, float textSize, float shadowRadius, int shadowColor, float shadowDx, float shadowDy, Align align) {
        //
        initPaint();
        //
        if (style != null) {
            layoutPaint.setStyle(style);
        }

        layoutPaint.setColor(colorValue);

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
