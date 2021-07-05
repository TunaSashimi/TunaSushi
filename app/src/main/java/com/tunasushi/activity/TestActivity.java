package com.tunasushi.activity;

import android.app.Activity;
import android.os.Bundle;
import com.tunasushi.demo.R;


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
    }
}