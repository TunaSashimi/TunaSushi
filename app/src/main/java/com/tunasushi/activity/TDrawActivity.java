package com.tunasushi.activity;

import android.app.Activity;
import android.graphics.BlurMaskFilter;
import android.graphics.EmbossMaskFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.tunasushi.R;
import com.tunasushi.tuna.ColorPickerDialog;
import com.tunasushi.tuna.TDraw;
/**
 * @author TunaSashimi
 * @date 2015-10-30 16:53
 * @Copyright 2015 TunaSashimi. All rights reserved.
 * @Description
 */
public class TDrawActivity extends Activity {

    private static final int COLOR_MENU_ID = Menu.FIRST;
    private static final int EMBOSS_MENU_ID = Menu.FIRST + 1;
    private static final int BLUR_MENU_ID = Menu.FIRST + 2;
    private static final int ERASE_MENU_ID = Menu.FIRST + 3;
    private static final int SRCATOP_MENU_ID = Menu.FIRST + 4;

    private EmbossMaskFilter embossMaskFilter;
    private BlurMaskFilter blurMaskFilter;

    private TDraw tDraw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_t_draw);

        embossMaskFilter = new EmbossMaskFilter(new float[]{1, 1, 1}, 0.4f, 6, 3.5f);
        blurMaskFilter = new BlurMaskFilter(8, BlurMaskFilter.Blur.NORMAL);

        tDraw = findViewById(R.id.tDraw);
        tDraw.setPaintingListener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, COLOR_MENU_ID, 1, "Color");
        menu.add(Menu.NONE, EMBOSS_MENU_ID, 2, "Emboss");
        menu.add(Menu.NONE, BLUR_MENU_ID, 3, "Blur");
        menu.add(Menu.NONE, ERASE_MENU_ID, 4, "Erase");
        menu.add(Menu.NONE, SRCATOP_MENU_ID, 5, "SrcATop");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        tDraw.getPaintingPaint().setXfermode(null);
        tDraw.getPaintingPaint().setAlpha(0xFF);

        switch (item.getItemId()) {
            case COLOR_MENU_ID:
                ColorPickerDialog colorPicker = new ColorPickerDialog(this,
                    tDraw.getPaintingPaint().getColor(),
                    new ColorPickerDialog.colorSelectListener() {
                        @Override
                        public void colorSelect(int color) {
                            tDraw.getPaintingPaint().setColor(color);
                        }
                    });
                colorPicker.show();
                return true;
            case EMBOSS_MENU_ID:
                if (tDraw.getPaintingPaint().getMaskFilter() != embossMaskFilter) {
                    tDraw.getPaintingPaint().setMaskFilter(embossMaskFilter);
                } else {
                    tDraw.getPaintingPaint().setMaskFilter(null);
                }
                return true;
            case BLUR_MENU_ID:
                if (tDraw.getPaintingPaint().getMaskFilter() != blurMaskFilter) {
                    tDraw.getPaintingPaint().setMaskFilter(blurMaskFilter);
                } else {
                    tDraw.getPaintingPaint().setMaskFilter(null);
                }
                return true;
            case ERASE_MENU_ID:
                tDraw.getPaintingPaint().setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
                return true;
            case SRCATOP_MENU_ID:
                tDraw.getPaintingPaint().setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
                tDraw.getPaintingPaint().setAlpha(0x80);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}