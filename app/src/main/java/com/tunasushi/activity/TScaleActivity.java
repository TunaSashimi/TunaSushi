package com.tunasushi.activity;

import android.app.Activity;
import android.os.Bundle;

import com.tunasushi.R;
import com.tunasushi.tuna.TScale;


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
