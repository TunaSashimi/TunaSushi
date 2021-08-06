package com.tunasushi.activity

import android.app.Activity
import android.os.Bundle
import com.tunasushi.demo.R
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import com.tunasushi.view.TImage

/**
 * @author TunaSashimi
 * @date 2015-10-30 16:53
 * @Copyright 2015 TunaSashimi. All rights reserved.
 * @Description
 */
class TImageActivity : Activity() {
    private lateinit var tImage: TImage
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_t_image)
        tImage = findViewById(R.id.tImage01)
        val seekBar01 = findViewById<SeekBar>(R.id.seekBar01)
        val seekBar02 = findViewById<SeekBar>(R.id.seekBar02)
        val seekBar03 = findViewById<SeekBar>(R.id.seekBar03)
        seekBar01.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                tImage.setImageBright(progress / 50F) //0-2
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })
        seekBar02.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                tImage.setImageHue((progress - 50) / 50F * 180) //-180-180
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })
        seekBar03.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                tImage.setImageSaturation(progress / 50F) //0-2
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })
    }
}