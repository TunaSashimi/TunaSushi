package com.tunasushi.activity

import com.tunasushi.tool.ConvertTool.dpToPx
import android.app.Activity
import android.os.Bundle
import com.tunasushi.demo.R
import com.tunasushi.view.TView
import com.tunasushi.view.TView.TouchListener
import com.tunasushi.view.TLine
import com.tunasushi.view.TRepeat
import com.tunasushi.tool.ViewTool
import android.util.TypedValue

/**
 * @author TunaSashimi
 * @date 2015-10-30 16:53
 * @Copyright 2015 TunaSashimi. All rights reserved.
 * @Description
 */
class TRepeatActivity : Activity(), TouchListener {
    private lateinit var tLine: TLine
    private lateinit var tRepeatStar: TRepeat
    private lateinit var tRepeatCar: TRepeat
    private lateinit var tRepeatTips: TRepeat
    private val indexArray = arrayOf("-1", "0", "1", "2", "3")
    private var dx = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_t_repeat)

        //
        tLine = findViewById(R.id.tLine)
        tRepeatStar = findViewById(R.id.tRepeatStar)
        tRepeatCar = findViewById(R.id.tRepeatCar)
        tRepeatTips = findViewById(R.id.tRepeatTips)

        //
        tRepeatStar.setTouchListener(this)

        //
        tRepeatCar.setRepeatTotal(indexArray.size)
        tRepeatCar.setRepeatItemTextArray(indexArray)
        tRepeatCar.setTouchListener(this)

        //
        ViewTool.setLayoutByWidth(tRepeatTips, (5 * 40).toFloat(), TypedValue.COMPLEX_UNIT_DIP)
    }

    override fun touch(t: TView) {
        val touchEventX = t.touchX
        val touchEventY = t.touchY

        //
        if (dx == 0) {
            dx = tLine.getWidth() shr 1
        }
        tLine.setX((touchEventX.toInt() - dx + dpToPx(20f)).toFloat())
        when (t.id) {
            R.id.tRepeatStar -> tRepeatCar.setTouchXYRaw(touchEventX, touchEventY)
            R.id.tRepeatCar -> tRepeatStar.setTouchXYRaw(touchEventX, touchEventY)
            else -> {
            }
        }
    }
}