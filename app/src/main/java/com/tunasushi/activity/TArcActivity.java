package com.tunasushi.activity;

import android.app.Activity;
import android.os.Bundle;

import com.tunasushi.tuna.TArc;

/**
 * @author TunaSashimi
 * @date 2020-02-12 19:58
 * @Copyright 2020 TunaSashimi. All rights reserved.
 * @Description
 */
public class TArcActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(new TArc(this));

    }
}

