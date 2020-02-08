package com.tunasushi.activity;

import android.app.Activity;
import android.os.Bundle;

import com.tunasushi.R;
import com.tunasushi.tuna.TRange;
import com.tunasushi.tuna.TView;

public class TRangeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_t_range);

        TRange rRange = findViewById(R.id.tRange);

        //
        rRange.setTouchDownListener(new TView.TouchDownListener() {
            @Override
            public void touchDown(TView t) {
                System.out.println("TRangeActivity==>" + t.getTouchEventX());
            }
        });


    }
}
