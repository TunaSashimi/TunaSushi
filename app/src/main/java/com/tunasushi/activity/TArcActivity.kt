package com.tunasushi.activity

import android.app.Activity
import android.os.Bundle
import com.tunasushi.view.TArc

/**
 * @author TunaSashimi
 * @date 2020-02-12 19:58
 * @Copyright 2020 TunaSashimi. All rights reserved.
 * @Description
 */
class TArcActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(TArc(this))
    }
}