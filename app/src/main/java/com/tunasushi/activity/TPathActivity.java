package com.tunasushi.activity;

import android.app.Activity;
import android.os.Bundle;

import com.tunasushi.tuna.TPath;

/**
 * @author TunaSashimi
 * @date 2020-02-12 20:02
 * @Copyright 2020 TunaSashimi. All rights reserved.
 * @Description
 */
public class TPathActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new TPath(this));
    }
}

