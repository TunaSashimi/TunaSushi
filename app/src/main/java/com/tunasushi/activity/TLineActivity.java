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


public class TLineActivity extends Activity {
    private TLine tunaLineAC, tunaLineMove;
    private Button buttonCenterAC, buttonHiddenAC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_t_line);

        int tuna_button_width = 120;

        tunaLineAC = findViewById(R.id.tLineAC);
        tunaLineMove = findViewById(R.id.tLineMove);

        buttonCenterAC = findViewById(R.id.buttonCenterAC);
        buttonHiddenAC = findViewById(R.id.buttonHiddenAC);

        //
        tunaLineAC.setLineCurrentX(TypedValue.COMPLEX_UNIT_PX, tuna_button_width);

        //
        buttonCenterAC.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                tunaLineAC.centerArrow();
            }
        });

        buttonHiddenAC.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                tunaLineAC.hideArrow();
            }
        });

        tunaLineMove.setLineCurrentX(TypedValue.COMPLEX_UNIT_PX, tuna_button_width);

        tunaLineMove.setTunaTouchListener(new TView.TunaTouchListener() {
            @Override
            public void tunaTouch(View v) {
                tunaLineMove.setLineCurrentX(TypedValue.COMPLEX_UNIT_PX, tunaLineMove.getTunaTouchEventX());
            }
        });
    }
}
