package com.tunasushi.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.TypedValue;

import com.tunasushi.R;
import com.tunasushi.tuna.TRange;
import com.tunasushi.tuna.TView;

/**
 * @author Tunasashimi
 * @date 10/30/15 16:53
 * @Copyright 2015 Sashimi. All rights reserved.
 * @Description
 */
public class TRangeActivity extends Activity {
    private TRange rRange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_t_range);

        rRange = findViewById(R.id.tRange);

        //
        rRange.setTouchListener(new TView.TouchListener() {
            @Override
            public void touch(TView t) {
                t.setX(t.getTouchEventX());
            }
        });
    }
}
