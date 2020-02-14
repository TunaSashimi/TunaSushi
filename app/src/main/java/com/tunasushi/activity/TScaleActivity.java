package com.tunasushi.activity;

import android.app.Activity;
import android.os.Bundle;

import com.tunasushi.R;
import com.tunasushi.tuna.TScale;

/**
 * @author TunaSashimi
 * @date 2015-10-30 16:53
 * @Copyright 2015 TunaSashimi. All rights reserved.
 * @Description
 */
public class TScaleActivity extends Activity {

    private TScale tScaleBitmapSetBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_t_scale);

        tScaleBitmapSetBitmap = findViewById(R.id.tScaleBitmapSetBitmap);
        tScaleBitmapSetBitmap.setScaleSrc(R.drawable.iv_scale_bg03);
    }
}
