package com.tunasushi.activity

import android.app.Activity
import android.os.Bundle
import com.tunasushi.demo.R
import android.widget.Toast
import com.tunasushi.view.TScratch
import com.tunasushi.view.TScratch.onScratchCompleteListener

/**
 * @author TunaSashimi
 * @date 2015-10-30 16:53
 * @Copyright 2015 TunaSashimi. All rights reserved.
 * @Description
 */
class TScratchActivity : Activity() {
      private lateinit var tScratch: TScratch
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_t_scratch)
        tScratch = findViewById(R.id.tScratch)
        tScratch.setOnScratchCompleteListener(onScratchCompleteListener {
            Toast.makeText(
                applicationContext, "刮除面积达到阈值", Toast.LENGTH_SHORT
            ).show()
        })
    }
}