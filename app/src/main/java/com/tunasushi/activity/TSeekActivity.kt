package com.tunasushi.activity

import android.app.Activity
import android.os.Bundle
import com.tunasushi.demo.R
import com.tunasushi.view.TView
import com.tunasushi.view.TView.TouchUpListener
import android.widget.Toast
import com.tunasushi.view.TSeek

/**
 * @author TunaSashimi
 * @date 2015-10-30 16:53
 * @Copyright 2015 TunaSashimi. All rights reserved.
 * @Description
 */
class TSeekActivity : Activity(), TouchUpListener {
    private lateinit var tSeek: TSeek
    private lateinit var tViewReset: TView
    private lateinit var tViewIndex: TView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_t_seek)

        tSeek = findViewById(R.id.tSeek)
        tViewReset = findViewById(R.id.tViewReset)
        tViewIndex = findViewById(R.id.tViewIndex)
        tViewReset.setTouchUpListener(this)
        tViewIndex.setTouchUpListener(this)
    }

    override fun touchUp(t: TView) {
        when (t.id) {
            R.id.tViewReset -> tSeek!!.seekIndex = 0
            R.id.tViewIndex -> Toast.makeText(
                this@TSeekActivity,
                "TSeek下标==>" + tSeek!!.seekIndex,
                Toast.LENGTH_SHORT
            ).show()
            else -> {
            }
        }
    }
}