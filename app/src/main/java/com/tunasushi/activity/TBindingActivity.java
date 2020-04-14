package com.tunasushi.activity;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.tunasushi.R;
import com.tunasushi.bean.Choice;
import com.tunasushi.databinding.ActivityTBindingBinding;

/**
 * @author TunaSashimi
 * @date 2020-04-12 20:30
 * @Copyright 2020 TunaSashimi. All rights reserved.
 * @Description
 */
public class TBindingActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityTBindingBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_t_binding);
        Choice choice = new Choice();

        binding.setChoice(choice);
        choice.choice.set(true);
    }
}
