package com.tunasushi.activity

import android.app.Activity
import android.os.Bundle
import com.tunasushi.demo.R
import com.tunasushi.view.TAnalysis
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener

/**
 * @author TunaSashimi
 * @date 2015-10-30 16:53
 * @Copyright 2015 TunaSashimi. All rights reserved.
 * @Description
 */
class TAnalysisActivity : Activity() {
    private lateinit var tAnalysis: TAnalysis
    private lateinit var seekbar: SeekBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_t_analysis)
        tAnalysis = findViewById(R.id.tAnalysis)
        seekbar = findViewById(R.id.seekbar)
        seekbar?.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                tAnalysis?.setAnalyaiaControlXY(progress, progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })
    }
}