package com.tunasushi.activity

import android.app.Activity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.tunasushi.demo.R
import com.tunasushi.bean.BindingBean
import com.tunasushi.view.TView
import com.tunasushi.demo.databinding.ActivityTBindingBinding

/**
 * @author TunaSashimi
 * @date 2020-04-12 20:30
 * @Copyright 2020 TunaSashimi. All rights reserved.
 * @Description
 */
class TBindingActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityTBindingBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_t_binding)
        val bean = BindingBean()
        bean.select.set(true)

        //
        binding.bean = bean
        val tView03: TView = findViewById(R.id.tView03)
        tView03.onClickListener = TView.OnClickListener {
            val string = tView03.text
            if ("hello" == string) {
                tView03.text = "world"
            } else {
                tView03.text = "hello"
            }
        }
    }
}