package com.tunasushi.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.tunasushi.R;
import com.tunasushi.tuna.TView;
/**
 * @author TunaSashimi
 * @date 2015-10-30 16:53
 * @Copyright 2015 TunaSashimi. All rights reserved.
 * @Description
 */
public class TTrangleActivity extends Activity {
    private TView tView, tViewBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_t_trangle);

        tView = findViewById(R.id.tView);
        tViewBox = findViewById(R.id.tViewBox);

        tView.setTouchUpListener(new TView.TouchUpListener() {
            @Override
            public void touchUp(View t) {
                if ("TunaSashimi".equals(tViewBox.getTextValue())) {
                    tViewBox.setTextValue("金枪鱼刺身");
                } else {
                    tViewBox.setTextValue("TunaSashimi");
                }
            }
        });
    }
}
