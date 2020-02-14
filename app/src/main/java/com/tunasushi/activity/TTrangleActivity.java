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

    private TView TViewButton, TViewBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_t_trangle);

        TViewButton = findViewById(R.id.tViewButton);
        TViewBox = findViewById(R.id.tViewBox);

        TViewButton.setTouchUpListener(new TView.TouchUpListener() {
            @Override
            public void touchUp(TView t) {
                if ("TunaSashimi".equals(TViewBox.getTextValue())) {
                    TViewBox.setTextValue("金枪鱼刺身");
                } else {
                    TViewBox.setTextValue("TunaSashimi");
                }
            }
        });
    }
}
