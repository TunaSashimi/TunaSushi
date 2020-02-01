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
 * @Copyright 2016 TunaSashimi. All rights reserved.
 * @Description
 */
public class TIrregular extends TView {

    private Bitmap tunaIrregularBitmapSrcNormal;

    public Bitmap getTunaIrregularBitmapSrcNormal() {
        return tunaIrregularBitmapSrcNormal;
    }

    public void setTunaIrregularBitmapSrcNormal(Bitmap tunaIrregularBitmapSrcNormal) {
        this.tunaIrregularBitmapSrcNormal = tunaIrregularBitmapSrcNormal;
    }

    private Bitmap tunaIrregularBitmapSrcSelect;

    public Bitmap getTunaIrregularBitmapSrcSelect() {
        return tunaIrregularBitmapSrcSelect;
    }

    public void setTunaIrregularBitmapSrcSelect(Bitmap tunaIrregularBitmapSrcSelect) {
        this.tunaIrregularBitmapSrcSelect = tunaIrregularBitmapSrcSelect;
    }

    private float tunaIrregularCurrentX;

    //
    protected TunaIrregularChangeListener tunaIrregularChangeListener;

    public interface TunaIrregularChangeListener {
        void tunaIrregularChange(boolean b);
    }

    public TunaIrregularChangeListener getTunaIrregularChangeListener() {
        return tunaIrregularChangeListener;
    }

    public void setTunaIrregularChangeListener(TunaIrregularChangeListener tunaIrregularChangeListener) {
        this.tunaIrregularChangeListener = tunaIrregularChangeListener;
    }

    // tunaIrregularSelect default false
    protected boolean tunaIrregularSelect;
    public boolean isTunaIrregularSelect() {
        return tunaIrregularSelect;
    }

    public void setTunaIrregularSelect(boolean tunaIrregularSelect) {
        this.tunaIrregularSelect = tunaIrregularSelect;
        invalidate();

        if (tunaIrregularChangeListener != null) {
            tunaIrregularChangeListener.tunaIrregularChange(tunaIrregularSelect);
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

        tunaTag = TIrregular.class.getSimpleName();

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TIrregular);

        int tunaIrregularBitmapSrcNormalId = typedArray.getResourceId(R.styleable.TIrregular_irregularBitmapSrcNormal, -1);
        if (tunaIrregularBitmapSrcNormalId != -1) {
            tunaIrregularBitmapSrcNormal = BitmapFactory.decodeResource(getResources(), tunaIrregularBitmapSrcNormalId);
        }

        int tunaIrregularBitmapSrcSelectId = typedArray.getResourceId(R.styleable.TIrregular_irregularBitmapSrcSelect, -1);
        if (tunaIrregularBitmapSrcSelectId != -1) {
            tunaIrregularBitmapSrcSelect = BitmapFactory.decodeResource(getResources(), tunaIrregularBitmapSrcSelectId);
        }

        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        typedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //
        if (tunaIrregularSelect) {
            canvas.drawBitmap(tunaIrregularBitmapSrcSelect, 0, 0, null);
        } else {
            canvas.drawBitmap(tunaIrregularBitmapSrcNormal, 0, 0, null);
        }
    }

    public void setTunaIrregularCurrentX(int unit, float IrregularCurrentX) {
        Context c = getContext();
        Resources r;
        if (c == null)
            r = Resources.getSystem();
        else
            r = c.getResources();
        setTunaIrregularCurrentXRaw(applyDimension(unit, IrregularCurrentX, r.getDisplayMetrics()));
    }


    private void setTunaIrregularCurrentXRaw(float tunaIrregularCurrentX) {
        if (this.tunaIrregularCurrentX != tunaIrregularCurrentX) {
            this.tunaIrregularCurrentX = tunaIrregularCurrentX;
            boolean selected = tunaIrregularCurrentX >= (tunaWidth / 2);
            if (tunaIrregularSelect ^ selected) {
                setTunaIrregularSelect(selected);
            }
        }
    }
}
