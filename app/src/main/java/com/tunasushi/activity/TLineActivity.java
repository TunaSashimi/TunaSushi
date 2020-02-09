package com.tunasushi.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.tunasushi.R;
import com.tunasushi.tuna.TView;
import com.tunasushi.tuna.TLine;

/**
 * @author Tunasashimi
 * @date 10/30/15 16:53
 * @Copyright 2015 Sashimi. All rights reserved.
 * @Description
 */
public class TLineActivity extends Activity {
    private TLine tLineAC, tLineMove;
    private Button buttonCenterAC, buttonHiddenAC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_t_line);

        float tuna_button_width = getResources().getDimension(
                R.dimen.tuna_button_width);

        tLineAC = findViewById(R.id.tLineAC);
        tLineMove = findViewById(R.id.tLineMove);

        buttonCenterAC = findViewById(R.id.buttonCenterAC);
        buttonHiddenAC = findViewById(R.id.buttonHiddenAC);

        //
        tLineAC.setX(TypedValue.COMPLEX_UNIT_PX, tuna_button_width);

        //
        buttonCenterAC.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                tLineAC.centerArrow();
            }
        });

        buttonHiddenAC.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                tLineAC.hideArrow();
            }
        });

        tLineMove.setX(TypedValue.COMPLEX_UNIT_PX, tuna_button_width);

        tLineMove.setTouchListener(new TView.TouchListener() {
            @Override
            public void touch(TView t) {
                t.setX(TypedValue.COMPLEX_UNIT_PX, t.getTouchEventX());
            }
        });
    }
}
