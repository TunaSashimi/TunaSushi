package com.tunasushi.activity;

import android.app.Activity;
import android.os.Bundle;

import com.tunasushi.R;
import com.tunasushi.tuna.TView;

/**
 * @author TunaSashimi
 * @date 2020-04-13 11:26
 * @Copyright 2020 TunaSashimi. All rights reserved.
 * @Description
 */
public class TViewSrcActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tview_src);

        TView t = findViewById(R.id.tView);
        t.setSrcNormal(R.drawable.bitmap_tview_srcnormal04);
    }
}
