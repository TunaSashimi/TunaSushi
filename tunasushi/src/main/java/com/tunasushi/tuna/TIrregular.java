package com.tunasushi.tuna;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import com.tuna.R;


/**
 * @author Tunasashimi
 * @date 8/31/16 16:11
 * @Copyright 2016 Sashimi. All rights reserved.
 * @Description
 */
public class TIrregular extends TView {

    private Bitmap irregularSrcNormal;

    public Bitmap getIrregularSrcNormal() {
        return irregularSrcNormal;
    }

    public void setIrregularSrcNormal(Bitmap irregularSrcNormal) {
        this.irregularSrcNormal = irregularSrcNormal;
    }

    private Bitmap irregularSrcSelect;

    public Bitmap getIrregularSrcSelect() {
        return irregularSrcSelect;
    }

    public void setIrregularSrcSelect(Bitmap irregularSrcSelect) {
        this.irregularSrcSelect = irregularSrcSelect;
    }

    protected IrregularSelectListener irregularSelectListener;

    public interface IrregularSelectListener {
        void onIrregularSelect(boolean b);
    }

    public IrregularSelectListener getIrregularSelectListener() {
        return irregularSelectListener;
    }

    public void setIrregularSelectListener(IrregularSelectListener irregularSelectListener) {
        this.irregularSelectListener = irregularSelectListener;
    }

    // irregularSelect default false
    protected boolean irregularSelect;

    public boolean isIrregularSelect() {
        return irregularSelect;
    }

    public void setIrregularSelect(boolean irregularSelect) {
        this.irregularSelect = irregularSelect;
        invalidate();

        if (irregularSelectListener != null) {
            irregularSelectListener.onIrregularSelect(irregularSelect);
        }
    }

    public TIrregular(Context context) {
        this(context, null);
    }

    public TIrregular(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TIrregular(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        tag = TIrregular.class.getSimpleName();

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TIrregular);

        int irregularSrcNormalId = typedArray.getResourceId(R.styleable.TIrregular_irregularSrcNormal, -1);
        if (irregularSrcNormalId != -1) {
            irregularSrcNormal = BitmapFactory.decodeResource(getResources(), irregularSrcNormalId);
        }

        int irregularSrcSelectId = typedArray.getResourceId(R.styleable.TIrregular_irregularSrcSelect, -1);
        if (irregularSrcSelectId != -1) {
            irregularSrcSelect = BitmapFactory.decodeResource(getResources(), irregularSrcSelectId);
        }

        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        typedArray.recycle();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        scaleSx = width * 1f / irregularSrcNormal.getWidth();
        scaleSy = height * 1f / irregularSrcNormal.getHeight();

        initMatrix(scaleSx, scaleSy);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //
        if (irregularSelect) {
            canvas.drawBitmap(irregularSrcSelect, matrix, null);
        } else {
            canvas.drawBitmap(irregularSrcNormal, matrix, null);
        }
    }

    @Override
    public void setXRaw(float x) {
        this.x = x;
        boolean selected = x >= (width >> 1);
        if (irregularSelect ^ selected) {
            setIrregularSelect(selected);
        }
    }
}
