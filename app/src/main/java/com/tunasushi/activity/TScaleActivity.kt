package com.tunasushi.activity

import android.app.Activity
import android.os.Bundle
import com.tunasushi.demo.R
import com.tunasushi.view.TScale

/**
 * @author TunaSashimi
 * @date 2015-10-30 16:53
 * @Copyright 2015 TunaSashimi. All rights reserved.
 * @Description
 */
class TScaleActivity : Activity() {
    private lateinit var tScale: TScale
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_t_scale)
        tScale = findViewById(R.id.tScale)
        tScale.setScaleSrc(R.drawable.bitmap_tscale_scalebitmap)
    }
}