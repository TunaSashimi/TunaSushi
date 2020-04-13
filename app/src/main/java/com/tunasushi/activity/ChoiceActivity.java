package com.tunasushi.activity;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.tunasushi.R;
import com.tunasushi.databinding.ActivityChoiceBinding;

/**
 * @author TunaSashimi
 * @date 2020-04-12 20:30
 * @Copyright 2020 TunaSashimi. All rights reserved.
 * @Description
 */
public class ChoiceActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityChoiceBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_choice);
        Choice choice = new Choice();

        binding.setChoice(choice);
        choice.choice.set(true);
    }
}
