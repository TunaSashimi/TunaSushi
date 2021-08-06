package com.tunasushi.activity

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import com.tunasushi.demo.R
import com.tunasushi.view.TView
import android.os.Handler
import android.os.Message
import com.tunasushi.view.TRipple
import java.util.*

/**
 * @author TunaSashimi
 * @date 2015-10-30 16:53
 * @Copyright 2015 TunaSashimi. All rights reserved.
 * @Description
 */
class TRippleActivity : Activity() {
    private lateinit var tViewPlay: TView
    private lateinit var tViewChange: TView
    private lateinit var tRipple01: TRipple
    private lateinit var tRipple02: TRipple
    private lateinit var tRipple03: TRipple
    private lateinit var tRipple04: TRipple
    private var timer: Timer? = null
    private val handler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                PLAY_TUNARIPPLE_ANIMATION -> {
                    tRipple01!!.play()
                    tRipple02!!.play()
                    tRipple03!!.play()
                    tRipple04!!.play()
                }
                else -> {
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_t_ripple)
        tRipple01 = findViewById(R.id.tRipple01)
        tRipple02 = findViewById(R.id.tRipple02)
        tRipple03 = findViewById(R.id.tRipple03)
        tRipple04 = findViewById(R.id.tRipple04)
        tViewPlay = findViewById(R.id.tViewPlay)
        tViewPlay.setOnClickListener(TView.OnClickListener {
            if (timer == null) {
                timer = Timer()
                timer!!.schedule(object : TimerTask() {
                    override fun run() {
                        handler.sendEmptyMessage(PLAY_TUNARIPPLE_ANIMATION)
                    }
                }, 0, 2000)
            }
        })
        tViewChange = findViewById(R.id.tViewChange)
        tViewChange.setOnClickListener(TView.OnClickListener {
            if ("^" == tRipple01.rippleText) {
                tRipple01.rippleText = "&"
                tRipple02.rippleText = "&"
                tRipple03.rippleText = "&"
                tRipple04.rippleText = "&"
                tRipple01.setRippleCircleColorInner(-0x6700)
                tRipple02.setRippleCircleColorOuter(
                    270,
                    -0x12eca,
                    -0x6700
                )
                tRipple03.setRippleCircleColorInner(-0x12eca)
                tRipple03.setRippleCircleColorOuter(-0x12eca)
                tRipple04.setRippleCircleAngleInner(90)
                tRipple04.setRippleCircleAngleOuter(90)
            } else {
                tRipple01.rippleText = "^"
                tRipple02.rippleText = "^"
                tRipple03.rippleText = "^"
                tRipple04.rippleText = "^"
                tRipple01.setRippleCircleColorInner(-0x12eca)
                tRipple02.setRippleCircleColorOuter(-0x6700)
                tRipple03.setRippleCircleColorInner(Color.WHITE, Color.BLUE)
                tRipple03.setRippleCircleColorOuter(Color.WHITE, Color.BLUE)
                tRipple04.setRippleCircleColorInner(270, Color.WHITE, Color.BLUE)
                tRipple04.setRippleCircleColorOuter(270, Color.RED, Color.BLUE)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        if (timer != null) {
            timer!!.cancel()
        }
    }

    companion object {
        private const val PLAY_TUNARIPPLE_ANIMATION = 0
    }
}