package com.tunasushi.activity;

import android.app.Activity;
import android.os.Bundle;

import com.tunasushi.R;
import com.tunasushi.tuna.TView;

/**
 * @author TunaSashimi
 * @date 2015-10-30 16:53
 * @Copyright 2015 TunaSashimi. All rights reserved.
 * @Description
 */
public class TTrangleActivity extends Activity {
    private TView tView01, tView02;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_t_trangle);

        tView01 = findViewById(R.id.tView01);
        tView02 = findViewById(R.id.tView02);

        tView01.setTouchUpListener(new TView.TouchUpListener() {
            @Override
            public void touchUp(TView t) {
                if ("TunaSashimi".equals(tView02.getTextValue())) {
                    tView02.setTextValue("金枪鱼刺身");
                } else {
                    tView02.setTextValue("TunaSashimi");
                }
            }
        });
    }
}
