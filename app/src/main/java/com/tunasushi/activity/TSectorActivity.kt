package com.tunasushi.activity

import android.app.Activity
import android.os.Bundle
import com.tunasushi.demo.R
import com.tunasushi.view.TView
import android.view.View
import com.tunasushi.view.TSector

/**
 * @author TunaSashimi
 * @date 2020-02-12 18:02
 * @Copyright 2020 TunaSashimi. All rights reserved.
 * @Description
 */
class TSectorActivity : Activity(), TView.OnClickListener {
    private var tSector: TSector? = null
    private var tViewProgress: TView? = null
    private var tViewAdd: TView? = null
    private var tViewSubtract: TView? = null
    private var tProgress = 80 //进度
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_t_sector)

        //
        tSector = findViewById(R.id.tSector)

        //
        tViewProgress = findViewById(R.id.tViewProgress)
        tViewAdd = findViewById(R.id.tViewAdd)
        tViewSubtract = findViewById(R.id.tViewSubtract)
        tSector?.setProgress(tProgress.toDouble())
        tViewProgress?.setText("$tProgress%")

        //
        tViewAdd?.setOnClickListener(this)
        tViewSubtract?.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.tViewAdd -> {
                tProgress += 1
                if (tProgress > 99) {
                    tProgress = 100
                }
                tSector!!.setProgress(tProgress.toDouble())
                tViewProgress!!.text = "$tProgress%"
            }
            R.id.tViewSubtract -> {
                tProgress -= 1
                if (tProgress < 0) {
                    tProgress = 0
                }
                tSector!!.setProgress(tProgress.toDouble())
                tViewProgress!!.text = "$tProgress%"
            }
            else -> {
            }
        }
    }
}