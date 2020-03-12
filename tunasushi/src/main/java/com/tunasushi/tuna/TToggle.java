package com.tunasushi.tuna;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.tuna.R;

/**
 * @author TunaSashimi
 * @date 2016-08-31 16:11
 * @Copyright 2016 TunaSashimi. All rights reserved.
 * @Description
 */
public class TToggle extends TView {

    private Bitmap toggleSrcNormal;
    private Bitmap toggleSrcToggle;

    private String toggleTextLeft;
    private String toggleTextRight;

    private int toggleTextColorLeftNormal;
    private int toggleTextColorLeftToggle;

    private float toggleTextSizeLeftNormal;
    private float toggleTextSizeLeftToggle;

    private int toggleTextColorRightNormal;
    private int toggleTextColorRightToggle;

    private float toggleTextSizeRightNormal;
    private float toggleTextSizeRightToggle;

    private float toggleTextFractionLeftDx;
    private float toggleTextFractionLeftDy;
    private float toggleTextFractionRightDx;
    private float toggleTextFractionRightDy;

    private float toggleLeftDx, toggleLeftDy;
    private float toggleRightDx, toggleRightDy;

    protected ToggleListener toggleListener;

    public interface ToggleListener {
        void onToggle(boolean b);
    }

    public ToggleListener getToggleListener() {
        return toggleListener;
    }

    public void setToggleListener(ToggleListener toggleListener) {
        this.toggleListener = toggleListener;
    }

    // toggle default false
    protected boolean toggle;

    public boolean isToggle() {
        return toggle;
    }

    public void setToggle(boolean toggle) {
        this.toggle = toggle;
        invalidate();

        if (toggleListener != null) {
            toggleListener.onToggle(toggle);
        }
    }

    public TToggle(Context context) {
        this(context, null);
    }

    public TToggle(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TToggle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        tag = TToggle.class.getSimpleName();

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TToggle);

        int toggleSrcNormalId = typedArray.getResourceId(R.styleable.TToggle_toggleSrcNormal, -1);
        if (toggleSrcNormalId != -1) {
            toggleSrcNormal = BitmapFactory.decodeResource(getResources(), toggleSrcNormalId);
        }

        int toggleSrcToggleId = typedArray.getResourceId(R.styleable.TToggle_toggleSrcToggle, -1);
        if (toggleSrcToggleId != -1) {
            toggleSrcToggle = BitmapFactory.decodeResource(getResources(), toggleSrcToggleId);
        }

        // 有空做成数组
        toggleTextLeft = typedArray.getString(R.styleable.TToggle_toggleTextLeft);
        toggleTextRight = typedArray.getString(R.styleable.TToggle_toggleTextRight);

        //
        toggleTextColorLeftNormal = typedArray.getColor(R.styleable.TToggle_toggleTextColorLeftNormal, textColorDefault);
        toggleTextColorLeftToggle = typedArray.getColor(R.styleable.TToggle_toggleTextColorLeftToggle, toggleTextColorLeftNormal);
        toggleTextSizeLeftNormal = typedArray.getDimension(R.styleable.TToggle_toggleTextSizeLeftNormal, textSizeDefault);
        toggleTextSizeLeftToggle = typedArray.getDimension(R.styleable.TToggle_toggleTextSizeLeftToggle, toggleTextSizeLeftNormal);

        //
        toggleTextColorRightNormal = typedArray.getColor(R.styleable.TToggle_toggleTextColorRightNormal, textColorDefault);
        toggleTextColorRightToggle = typedArray.getColor(R.styleable.TToggle_toggleTextColorRightToggle, toggleTextColorRightNormal);
        toggleTextSizeRightNormal = typedArray.getDimension(R.styleable.TToggle_toggleTextSizeRightNormal, textSizeDefault);
        toggleTextSizeRightToggle = typedArray.getDimension(R.styleable.TToggle_toggleTextSizeRightToggle, toggleTextSizeRightNormal);

        toggleTextFractionLeftDx = typedArray.getFraction(R.styleable.TToggle_toggleTextFractionLeftDx, 1, 1, 0);
        toggleTextFractionLeftDy = typedArray.getFraction(R.styleable.TToggle_toggleTextFractionLeftDy, 1, 1, 0);
        toggleTextFractionRightDx = typedArray.getFraction(R.styleable.TToggle_toggleTextFractionRightDx, 1, 1, 0);
        toggleTextFractionRightDy = typedArray.getFraction(R.styleable.TToggle_toggleTextFractionRightDy, 1, 1, 0);

        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        typedArray.recycle();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        matrixAnchorNormal = initMatrix(matrixAnchorNormal, width * 1f / toggleSrcNormal.getWidth(), height * 1f / toggleSrcNormal.getHeight());
        matrixAnchorSelect = initMatrix(matrixAnchorSelect, width * 1f / toggleSrcNormal.getWidth(), height * 1f / toggleSrcNormal.getHeight());

        //
        toggleLeftDx = width * toggleTextFractionLeftDx;
        toggleLeftDy = height * toggleTextFractionLeftDy;
        toggleRightDx = width * toggleTextFractionRightDx;
        toggleRightDy = height * toggleTextFractionRightDy;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //
        if (toggle) {
            canvas.drawBitmap(toggleSrcToggle, matrixAnchorSelect, null);
        } else {
            canvas.drawBitmap(toggleSrcNormal, matrixAnchorNormal, null);
        }

        //
        if (toggleTextLeft != null) {
            drawText(
                    canvas, toggleTextLeft,
                    width >> 1, (width >> 2) + toggleLeftDx, (height >> 1) + toggleLeftDy,
                    initTextPaint(Paint.Style.FILL,
                            toggle ? toggleTextColorLeftToggle : toggleTextColorLeftNormal,
                            toggle ? toggleTextSizeLeftToggle : toggleTextSizeLeftNormal,
                            Paint.Align.CENTER));
        }

        //
        if (toggleTextRight != null) {
            drawText(
                    canvas, toggleTextRight,
                    width >> 1, (width >> 1) + (width >> 2) + toggleRightDx, (height >> 1) + toggleRightDy,
                    initTextPaint(Paint.Style.FILL,
                            toggle ? toggleTextColorRightToggle : toggleTextColorRightNormal,
                            toggle ? toggleTextSizeRightToggle : toggleTextSizeRightNormal,
                            Paint.Align.CENTER));
        }
    }

    @Override
    public void setTouchXYRaw(float touchX, float touchY) {
        x = touchX;
        boolean selected = x >= width >> 1;
        if (toggle ^ selected) {
            setToggle(selected);
        }
        invalidate();
    }
}
