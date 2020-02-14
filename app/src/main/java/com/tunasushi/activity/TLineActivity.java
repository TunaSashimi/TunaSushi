package com.tunasushi.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.TypedValue;

import com.tunasushi.R;
import com.tunasushi.tuna.TView;
import com.tunasushi.tuna.TLine;

/**
 * @author TunaSashimi
 * @date 2015-10-30 16:53
 * @Copyright 2015 TunaSashimi. All rights reserved.
 * @Description
 */
public class TLineActivity extends Activity {
    private TLine tLineAC, tLineMove;
    private TView tViewCenter, tViewHidden;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_t_line);

        float tuna_button_width = getResources().getDimension(
                R.dimen.tuna_button_width);

        tLineAC = findViewById(R.id.tLineAC);
        tLineMove = findViewById(R.id.tLineMove);

        tViewCenter = findViewById(R.id.tViewCenter);
        tViewHidden = findViewById(R.id.tViewHidden);

        //
        tLineAC.setX(tuna_button_width);

        //
        tViewCenter.setOnClickListener(new TView.OnClickListener() {
            @Override
            public void onClick(TView v) {
                tLineAC.centerArrow();
            }
        });

        tViewHidden.setOnClickListener(new TView.OnClickListener() {
            @Override
            public void onClick(TView v) {
                tLineAC.hideArrow();
            }
        });

        tLineMove.setX(tuna_button_width);

        tLineMove.setTouchListener(new TView.TouchListener() {
            @Override
            public void touch(TView t) {
                t.setX(t.getTouchEventX());
            }
        });
    }
}
