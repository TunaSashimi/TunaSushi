package com.tunasushi.tuna;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

/**
 * @author TunaSashimi
 * @date 2015-10-30 16:51
 * @Copyright 2015 TunaSashimi. All rights reserved.
 * @Description
 */

public class TPickerDialog extends Dialog {
    public int initialColor;
    public TView.ColorSelectListener colorSelectListener;

    public TPickerDialog(Context context, int initialColor, TView.ColorSelectListener colorSelectListener) {
        super(context);

        this.initialColor = initialColor;
        this.colorSelectListener = colorSelectListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Add a layer inside the listener and Add to contentview
        TView.ColorSelectListener colorSelectListener = new TView.ColorSelectListener() {
            public void colorSelect(int color) {
                TPickerDialog.this.colorSelectListener.colorSelect(color);
                dismiss();
            }
        };

        setContentView(new TPicker(getContext(), initialColor, colorSelectListener));
        setTitle("Select Color");
    }
}
