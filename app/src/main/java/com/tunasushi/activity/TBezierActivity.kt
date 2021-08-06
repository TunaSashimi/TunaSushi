package com.tunasushi.activity

import android.app.Activity
import android.os.Bundle
import com.tunasushi.demo.R
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import com.tunasushi.view.TBezier

/**
 * @author TunaSashimi
 * @date 2020-02-12 20:01
 * @Copyright 2020 TunaSashimi. All rights reserved.
 * @Description
 */
class TBezierActivity : Activity(), OnSeekBarChangeListener {
    private lateinit var tBezier: TBezier
    private lateinit var seekBar01: SeekBar
    private lateinit var seekBar02: SeekBar
    private lateinit var seekBar03: SeekBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_t_bezier)
        tBezier = findViewById(R.id.tBezier02)
        seekBar01 = findViewById(R.id.seekBar01)
        seekBar02 = findViewById(R.id.seekBar02)
        seekBar03 = findViewById(R.id.seekBar03)
        seekBar01.setOnSeekBarChangeListener(this)
        seekBar02.setOnSeekBarChangeListener(this)
        seekBar03.setOnSeekBarChangeListener(this)
    }

    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
        when (seekBar.id) {
            R.id.seekBar01 -> tBezier.maxDistance = progress.toFloat()
            R.id.seekBar02 -> tBezier.mv = progress / 100F
            R.id.seekBar03 -> tBezier.handleLenRate = progress / 100F
        }
    }

    override fun onStartTrackingTouch(seekBar: SeekBar) {}
    override fun onStopTrackingTouch(seekBar: SeekBar) {}
}