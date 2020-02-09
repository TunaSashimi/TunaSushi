package com.tunasushi.activity;

import android.app.Activity;
import android.os.Bundle;

import com.tunasushi.R;
import com.tunasushi.tuna.TScale;

/**
 * @author Tunasashimi
 * @date 10/30/15 16:53
 * @Copyright 2015 Sashimi. All rights reserved.
 * @Description
 */
public class TScaleActivity extends Activity {

    private TScale tScaleBitmapSetBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_t_scale);

        tScaleBitmapSetBitmap = findViewById(R.id.tScaleBitmapSetBitmap);
        tScaleBitmapSetBitmap.setScaleBitmapSrc(R.drawable.iv_scale_bg03);
    }
}
