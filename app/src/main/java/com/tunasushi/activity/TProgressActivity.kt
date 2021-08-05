package com.tunasushi.activity

import android.app.Activity
import android.os.Bundle
import com.tunasushi.demo.R
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import com.tunasushi.view.TProgress

/**
 * @author TunaSashimi
 * @date 2015-10-30 16:53
 * @Copyright 2015 TunaSashimi. All rights reserved.
 * @Description
 */
class TProgressActivity : Activity() {
    private lateinit var tProgressCircleClockWise: TProgress
    private lateinit var tProgressCircleUpWard: TProgress
    private lateinit var tProgressCircleUpDown: TProgress
    private lateinit var tProgressCustomClockWise: TProgress
    private lateinit var tProgressCustomUpWard: TProgress
    private lateinit var tProgressCustomUpDown: TProgress
    private lateinit var seekbar: SeekBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_t_progress)
        tProgressCircleClockWise = findViewById(R.id.tProgressCircleClockWise)
        tProgressCircleUpWard = findViewById(R.id.tProgressCircleUpWard)
        tProgressCircleUpDown = findViewById(R.id.tProgressCircleUpDown)
        tProgressCustomClockWise = findViewById(R.id.tProgressCustomClockWise)
        tProgressCustomUpWard = findViewById(R.id.tProgressCustomUpWard)
        tProgressCustomUpDown = findViewById(R.id.tProgressCustomUpDown)
        seekbar = findViewById(R.id.seekbar)
        seekbar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                tProgressCircleClockWise.setProgressPercent(progress * 0.01f)
                tProgressCircleUpWard.setProgressPercent(progress * 0.01f)
                tProgressCircleUpDown.setProgressPercent(progress * 0.01f)
                tProgressCustomClockWise.setProgressPercent(progress * 0.01f)
                tProgressCustomUpWard.setProgressPercent(progress * 0.01f)
                tProgressCustomUpDown.setProgressPercent(progress * 0.01f)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })
    }
}