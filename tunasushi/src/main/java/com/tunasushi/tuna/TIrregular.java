package com.tunasushi.tuna;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import com.tuna.R;

import static com.tunasushi.tool.DeviceTool.applyDimension;


/**
 * @author Tunasashimi
 * @date 8/31/16 16:11
 * @Copyright 2016 Sashimi. All rights reserved.
 * @Description
 */
public class TIrregular extends TView {

    private Bitmap irregularBitmapSrcNormal;

    public Bitmap getIrregularBitmapSrcNormal() {
        return irregularBitmapSrcNormal;
    }

    public void setIrregularBitmapSrcNormal(Bitmap irregularBitmapSrcNormal) {
        this.irregularBitmapSrcNormal = irregularBitmapSrcNormal;
    }

    private Bitmap irregularBitmapSrcSelect;

    public Bitmap getIrregularBitmapSrcSelect() {
        return irregularBitmapSrcSelect;
    }

    public void setIrregularBitmapSrcSelect(Bitmap irregularBitmapSrcSelect) {
        this.irregularBitmapSrcSelect = irregularBitmapSrcSelect;
    }

    protected IrregularChangeListener irregularChangeListener;

    public interface IrregularChangeListener {
        void irregularChange(boolean b);
    }

    public IrregularChangeListener getIrregularChangeListener() {
        return irregularChangeListener;
    }

    public void setIrregularChangeListener(IrregularChangeListener irregularChangeListener) {
        this.irregularChangeListener = irregularChangeListener;
    }

    // irregularSelect default false
    protected boolean irregularSelect;

    public boolean isIrregularSelect() {
        return irregularSelect;
    }

    public void setIrregularSelect(boolean irregularSelect) {
        this.irregularSelect = irregularSelect;
        invalidate();

        if (irregularChangeListener != null) {
            irregularChangeListener.irregularChange(irregularSelect);
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

        Tag = TIrregular.class.getSimpleName();

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TIrregular);

        int irregularBitmapSrcNormalId = typedArray.getResourceId(R.styleable.TIrregular_irregularBitmapSrcNormal, -1);
        if (irregularBitmapSrcNormalId != -1) {
            irregularBitmapSrcNormal = BitmapFactory.decodeResource(getResources(), irregularBitmapSrcNormalId);
        }

        int irregularBitmapSrcSelectId = typedArray.getResourceId(R.styleable.TIrregular_irregularBitmapSrcSelect, -1);
        if (irregularBitmapSrcSelectId != -1) {
            irregularBitmapSrcSelect = BitmapFactory.decodeResource(getResources(), irregularBitmapSrcSelectId);
        }

        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        typedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //
        if (irregularSelect) {
            canvas.drawBitmap(irregularBitmapSrcSelect, 0, 0, null);
        } else {
            canvas.drawBitmap(irregularBitmapSrcNormal, 0, 0, null);
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
