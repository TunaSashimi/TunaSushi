package com.tunasushi.view;

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
    public TPicker.PickerSelectListener colorSelectListener;

    public TPickerDialog(Context context, int initialColor, TPicker.PickerSelectListener colorSelectListener) {
        super(context);

        this.initialColor = initialColor;
        this.colorSelectListener = colorSelectListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //
        TPicker tPicker = new TPicker(getContext(), initialColor);
        tPicker.setPickerSelectListener(new TPicker.PickerSelectListener() {
            @Override
            public void pickerSelect(int color) {
                TPickerDialog.this.colorSelectListener.pickerSelect(color);
                dismiss();
            }
        });

        setContentView(tPicker);
        setTitle("Select Color");
    }
}