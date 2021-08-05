package com.tunasushi.activity

import android.app.Activity
import android.os.Bundle
import com.tunasushi.demo.R
import android.widget.Toast
import com.tunasushi.view.TToggle
import com.tunasushi.view.TToggle.ToggleListener

/**
 * @author TunaSashimi
 * @date 2015-10-30 16:53
 * @Copyright 2015 TunaSashimi. All rights reserved.
 * @Description
 */
class TToggleActivity : Activity() {
    private lateinit var tToggle01: TToggle
    private lateinit var tToggle02: TToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_t_toggle)
        tToggle01 = findViewById(R.id.tToggle01)
        tToggle02 = findViewById(R.id.tToggle02)

        //
        tToggle01.setToggleListener(ToggleListener { isToggle ->
            Toast.makeText(
                application, "tToggle01==>$isToggle", Toast.LENGTH_SHORT
            ).show()
        })
        tToggle02.setToggleListener(ToggleListener { toggle ->
            Toast.makeText(
                application,
                "tToggle02==>$toggle",
                Toast.LENGTH_SHORT
            ).show()
        })
    }
}