package com.tunasushi.activity;

import android.app.Activity;
import android.os.Bundle;

import com.tunasushi.tuna.TPattern;
import com.tunasushi.tuna.TView;

/**
 * @author Tunasashimi
 * @date 2020-02-12 20:05
 * @Copyright 2020 TunaSashimi. All rights reserved.
 * @Description
 */
public class TPatternActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Note this configuration!
        //tPattern.setTouchType(TView.TouchType.NONE);
        TPattern tPattern = new TPattern(this);
        tPattern.setTouchType(TView.TouchType.NONE);

        setContentView(tPattern);
    }
}

