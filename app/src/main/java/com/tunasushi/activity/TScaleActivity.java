package com.tunasushi.activity;

import android.app.Activity;
import android.os.Bundle;

import com.tunasushi.R;
import com.tunasushi.tuna.TScale;


public class TScaleActivity extends Activity {

    private TScale tunaScaleBitmapSetBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_t_scale);

        tunaScaleBitmapSetBitmap = findViewById(R.id.tScaleBitmapSetBitmap);
        tunaScaleBitmapSetBitmap.setTunaScaleBitmapSrc(R.drawable.viewpagertest_imageview_resource02);
    }
}
