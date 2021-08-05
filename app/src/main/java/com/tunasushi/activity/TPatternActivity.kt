package com.tunasushi.activity

import android.app.Activity
import android.os.Bundle
import com.tunasushi.view.TPattern

/**
 * @author TunaSashimi
 * @date 2020-02-12 20:05
 * @Copyright 2020 TunaSashimi. All rights reserved.
 * @Description
 */
class TPatternActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(TPattern(this))
    }
}