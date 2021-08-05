package com.tunasushi.activity;

import android.app.Activity;
import android.os.Bundle;

import com.tunasushi.demo.R;
import com.tunasushi.view.TGroup;
import com.tunasushi.view.TView;


/**
 * @author TunaSashimi
 * @date 2020-07-06 11:36
 * @Copyright 2020 TunaSashimi. All rights reserved.
 * @Description
 */
public class TestActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_t_test);

        TView tView01 = findViewById(R.id.tView01);
        TView tView02 = findViewById(R.id.tView02);

        TGroup.link(tView01, tView02);
    }
}