package com.tunasushi.activity

import com.tunasushi.tool.ConvertTool.dpToPx
import android.app.Activity
import android.os.Bundle
import com.tunasushi.demo.R
import com.tunasushi.view.TView
import com.tunasushi.view.TView.TouchUpListener
import com.tunasushi.view.TGroup
import com.tunasushi.view.TBubble
import com.tunasushi.view.TRange
import com.tunasushi.view.TView.TouchListener
import android.view.View

/**
 * @author TunaSashimi
 * @date 2020-02-13 13:50
 * @Copyright 2020 TunaSashimi. All rights reserved.
 * @Description
 */
class TRangeActivity : Activity(), TouchUpListener {
    private lateinit var tViewNoLimit: TView
    private lateinit var tViewStarTwo: TView
    private lateinit var tViewStarThree: TView
    private lateinit var tViewStarFour: TView
    private lateinit var tViewStarFive: TView
    private lateinit var tViewPrice: TView
    private lateinit var tBubble: TBubble
    private lateinit var tRange: TRange
    private var dx = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_t_range)
        tViewNoLimit = findViewById(R.id.tViewNoLimit)
        tViewStarTwo = findViewById(R.id.tViewStarTwo)
        tViewStarThree = findViewById(R.id.tViewStarThree)
        tViewStarFour = findViewById(R.id.tViewStarFour)
        tViewStarFive = findViewById(R.id.tViewStarFive)
        tViewPrice = findViewById(R.id.tViewPrice)
        tRange = findViewById(R.id.tRange)
        tBubble = findViewById(R.id.tBubble)

        //
        TGroup.link(tViewNoLimit, tViewStarTwo, tViewStarThree, tViewStarFour, tViewStarFive)

        //
        tViewPrice.setContent(tRange.getRangeTextLeft() + " - " + tRange.getRangeTextRight())

        //
        tRange.setTouchListener(TouchListener { t -> //
            if (dx == 0) {
                dx = tBubble.getWidth() shr 1
            }
            tBubble.setTBubbleText(tRange.getRangeText())
            if (t.isPress) {
                tBubble.setVisibility(View.VISIBLE)
                //Need to add marginLift of tRange!
                tBubble.setX((tRange.getRangeCircleCentreX().toInt() - dx + dpToPx(20f)).toFloat())
            } else {
                tBubble.setVisibility(View.INVISIBLE)
            }
            //
            tViewPrice.setContent(tRange.getRangeTextLeft() + " - " + tRange.getRangeTextRight())
        })
    }

    override fun touchUp(t: TView) {
        when (t.id) {
            R.id.tViewReset -> tRange!!.reset()
            R.id.tViewComplete -> finish()
            else -> {
            }
        }
    }
}