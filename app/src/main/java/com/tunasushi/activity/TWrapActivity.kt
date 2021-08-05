package com.tunasushi.activity

import android.app.Activity
import android.os.Bundle
import com.tunasushi.demo.R
import com.tunasushi.view.TView
import android.widget.Toast
import com.tunasushi.view.TWrap

/**
 * @author TunaSashimi
 * @date 2015-10-30 16:53
 * @Copyright 2015 TunaSashimi. All rights reserved.
 * @Description
 */
class TWrapActivity : Activity() {
    private lateinit var tWrap01: TWrap
    private lateinit var tWrap02: TWrap
    private lateinit var tWrap03: TWrap
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_t_wrap)

        //
        tWrap01 = findViewById(R.id.tWrap01)
        tWrap01.setOnClickListener(TView.OnClickListener { v ->
            val tWrap = v as TWrap
            Toast.makeText(application, tWrap.wrapSelectString, Toast.LENGTH_SHORT).show()
        })

        //
        tWrap02 = findViewById(R.id.tWrap02)
        tWrap02.setWrapTextArray(
            arrayOf(
                "用户要求换车", "车辆不整洁", "车辆设施不完备", "车辆损坏", "用户要求换车", "车辆不整洁", "车辆设施不完备", "车辆损坏"
            )
        )
        tWrap02.setOnClickListener(TView.OnClickListener { v ->
            val tWrap = v as TWrap
            Toast.makeText(application, tWrap.wrapSelectString, Toast.LENGTH_SHORT).show()
        })

        //
        tWrap03 = findViewById(R.id.tWrap03)
        tWrap03.setWrapTextArray(
            arrayOf(
                "用户要求换车", "车辆不整洁", "车辆设施不完备", "车辆损坏", "用户要求换车", "车辆不整洁", "车辆设施不完备", "车辆损坏"
            )
        )
        tWrap03.setOnClickListener(TView.OnClickListener { v ->
            val tWrap = v as TWrap
            Toast.makeText(application, tWrap.wrapSelectString, Toast.LENGTH_SHORT).show()
        })
    }
}