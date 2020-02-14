package com.tunasushi.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.tunasushi.R;
import com.tunasushi.tuna.TView;
import com.tunasushi.tuna.TWrap;

import java.util.Arrays;

/**
 * @author TunaSashimi
 * @date 2015-10-30 16:53
 * @Copyright 2015 TunaSashimi. All rights reserved.
 * @Description
 */

public class TWrapActivity extends Activity {
    private TWrap tWrap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_t_wrap);

        tWrap = findViewById(R.id.tWrap);
        tWrap.setWrapItemTextValueArray(
                new String[]{
                        " 用户要求换车 ", "车辆不整洁", "车辆设施不完备 ", "车辆损坏 "
                });

        tWrap.setTouchUpListener(new TView.TouchUpListener() {
            @Override
            public void touchUp(TView t) {
                tWrap.setWrapXY(tWrap.getTouchEventX(), tWrap.getTouchEventY());
            }
        });

        TView tView = findViewById(R.id.tView);
        tView.setOnClickListener(new TView.OnClickListener() {
            @Override
            public void onClick(TView v) {
                Toast.makeText(getApplication(), Arrays.toString(tWrap.getWrapSelect()), Toast.LENGTH_LONG).show();
            }
        });
    }
}