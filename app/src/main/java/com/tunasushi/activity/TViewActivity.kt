package com.tunasushi.activity

import com.tunasushi.tool.ConvertTool.dpToPx
import android.app.Activity
import android.os.Bundle
import com.tunasushi.demo.R
import android.graphics.*
import com.tunasushi.view.TView
import com.tunasushi.view.TView.TouchUpListener
import android.widget.Toast
import com.tunasushi.view.TGroup
import android.util.TypedValue
import android.view.View
import com.tunasushi.tool.DeviceTool
import android.widget.TextView
import android.widget.LinearLayout

/**
 * @author TunaSashimi
 * @date 2015-10-30 16:53
 * @Copyright 2015 TunaSashimi. All rights reserved.
 * @Description
 */
class TViewActivity : Activity(), TouchUpListener, TView.OnClickListener {
    private lateinit var tViewRect01: TView
    private lateinit var tViewRect02: TView
    private lateinit var tView02: TView
    private lateinit var tViewShadow01: TView
    private lateinit var tViewShadow02: TView
    private lateinit var tViewGroupStart: TView
    private lateinit var tViewGroupEnd: TView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_t_view)
        //
        val deviceInfo = DeviceTool.getDeviceInfo(this)
        val textPrompt10 = findViewById<TextView>(R.id.textPrompt10)
        textPrompt10.text = deviceInfo.toString()

        //
        tViewRect01 = findViewById(R.id.tViewRect01)
        tViewRect01.setTouchUpListener(this)
        tViewRect02 = findViewById(R.id.tViewRect02)
        tViewRect02.setTouchUpListener(this)
        /**
         * 注意这里继承的是TView.OnClick事件
         */
        tView02 = findViewById(R.id.tViewRect02)
        tView02.setOnClickListener(this)

        //
        tViewShadow01 = findViewById(R.id.tViewShadow01)
        tViewShadow01.setTouchUpListener(this)

        //
        tViewShadow02 = findViewById(R.id.tViewShadow02)
        tViewShadow02.setTextMark(
            10f, TypedValue.COMPLEX_UNIT_DIP,
            Color.RED,
            "10", 10f, TypedValue.COMPLEX_UNIT_DIP,
            Color.WHITE, 0f, TypedValue.COMPLEX_UNIT_DIP, 0f, TypedValue.COMPLEX_UNIT_DIP
        )
        tViewShadow02.setTouchUpListener(this)

        //
        tViewGroupStart = findViewById(R.id.tViewGroupStart)
        tViewGroupEnd = findViewById(R.id.tViewGroupEnd)

        //if you want a different TView link, you can put an array of incoming link methods
        //	TView.link(new TView[]{tViewGroupStart, tViewGroupEnd});
        //	or
        //	can be placed on a list of incoming link method
        TGroup.link(null, this, tViewGroupStart, tViewGroupEnd)

        //
        val groupTitleArray = arrayOf("金", "枪", "鱼", "刺", "身")
        val linearGroup = findViewById<LinearLayout>(R.id.linearGroup)
        TGroup.create(
            groupTitleArray,
            "枪",
            linearGroup,
            dpToPx(60f), LinearLayout.LayoutParams.MATCH_PARENT,
            R.style.TViewGroupLightGraySrart,
            R.style.TViewGroupLightGrayEnd,
            R.style.TViewGroupLightGrayOther,
            this,
            null
        )
    }

    //Difference between onClick interface and touchUp interface
    //onClick interface parameter is View, onTouch interface parameter is TView!
    override fun touchUp(t: TView) {
        when (t.id) {
            R.id.tViewRect01 -> Toast.makeText(
                this@TViewActivity,
                "TView.TouchUp",
                Toast.LENGTH_SHORT
            ).show()
            R.id.tViewRect02 -> tViewRect02!!.setBackground(Color.RED)
            R.id.tViewRect03 -> Toast.makeText(
                this@TViewActivity,
                "app:onClick=\"touchUp\"",
                Toast.LENGTH_SHORT
            ).show()
            R.id.tViewShadow01 -> tViewShadow01!!.setTextMark(
                4f, TypedValue.COMPLEX_UNIT_DIP,
                Color.RED,
                null, 0f, TypedValue.COMPLEX_UNIT_DIP,
                0, 0f, TypedValue.COMPLEX_UNIT_DIP, 0f, TypedValue.COMPLEX_UNIT_DIP
            )
            R.id.tViewShadow02 -> tViewShadow02!!.setTextMark(
                4f, TypedValue.COMPLEX_UNIT_DIP,
                Color.RED,
                null, 0f, TypedValue.COMPLEX_UNIT_DIP,
                0, 0f, TypedValue.COMPLEX_UNIT_DIP, 0f, TypedValue.COMPLEX_UNIT_DIP
            )
            else -> Toast.makeText(this@TViewActivity, t.text, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.tViewRect02 -> Toast.makeText(
                this@TViewActivity,
                "TView.OnClick",
                Toast.LENGTH_SHORT
            ).show()
            R.id.tViewRect04 -> Toast.makeText(
                this@TViewActivity,
                "app:onClick=\"onClick\"",
                Toast.LENGTH_SHORT
            ).show()
            else -> {
            }
        }
    }
}