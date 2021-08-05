package com.tunasushi.activity

import android.app.Activity
import android.os.Bundle
import com.tunasushi.demo.R
import com.tunasushi.view.TView
import com.tunasushi.view.TView.TouchUpListener
import com.tunasushi.view.TButton
import android.widget.Toast

/**
 * @author TunaSashimi
 * @date 2015-10-30 16:53
 * @Copyright 2015 TunaSashimi. All rights reserved.
 * @Description
 */
class TButtonActivity : Activity(), TouchUpListener {
    private lateinit var tButton01: TButton
    private lateinit var tButton02: TButton
    private lateinit var tButton03: TButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_t_button)
        tButton01 = findViewById(R.id.tButton01)
        tButton02 = findViewById(R.id.tButton02)
        tButton03 = findViewById(R.id.tButton03)
        tButton01.setTouchUpListener(this)
        tButton02.setTouchUpListener(this)
        tButton03.setTouchUpListener(this)
    }

    override fun touchUp(t: TView) {
        when (t.id) {
            R.id.tButton01, R.id.tButton02, R.id.tButton03 -> {
                val tButton = t as TButton
                Toast.makeText(this, tButton.buttonText, Toast.LENGTH_SHORT).show()
            }
            else -> {
            }
        }
    }
}