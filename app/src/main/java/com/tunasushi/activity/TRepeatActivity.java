package com.tunasushi.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.tunasushi.R;
import com.tunasushi.tuna.TView;
import com.tunasushi.tuna.TLine;
import com.tunasushi.tuna.TRepeat;

import static com.tunasushi.tool.ViewTool.setLayoutByWidth;

public class TRepeatActivity extends Activity {
    private TLine tunaLine;
    private TRepeat tunaRepeatStar, tunaRepeatCar, tunaRepeatTips;

    private Button buttonEvaluation01;
    private Spinner spinnerEvaluation01;

    private String[]
        indexes = {"-1", "0", "1", "2", "3"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_t_repeat);

        tunaLine = findViewById(R.id.tLine);

        buttonEvaluation01 = findViewById(R.id.buttonEvaluation01);

        tunaRepeatStar = findViewById(R.id.tRepeatStar);
        tunaRepeatCar = findViewById(R.id.tRepeatCar);
        tunaRepeatTips = findViewById(R.id.tRepeatTips);

        tunaRepeatStar.setTunaRepeatListener(
            new TView.TunaTouchListener() {
                @Override
                public void tunaTouch(View v) {
                    tunaRepeatStar.setTunaRepeatCurrentX(TypedValue.COMPLEX_UNIT_PX, tunaRepeatStar.getTunaTouchEventX());
                    tunaRepeatCar.setTunaRepeatCurrentX(TypedValue.COMPLEX_UNIT_PX, tunaRepeatStar.getTunaTouchEventX(), true);
                    tunaLine.setLineCurrentX(TypedValue.COMPLEX_UNIT_PX, tunaRepeatStar.getTunaTouchEventX());
                }
            },
            new TView.TunaTouchDownListener() {
                @Override
                public void tunaTouchDown(View v) {
                    tunaRepeatCar.setTunaPress(true);
                }
            },
            new TView.TunaTouchUpListener() {
                @Override
                public void tunaTouchUp(View v) {
                    afterChoice();
                    tunaRepeatCar.setTunaPress(false);
                }
            }
        );

        //
        tunaRepeatCar.setTunaRepeatTotal(indexes.length);
        tunaRepeatCar.setTunaRepeatItemTextValueArray(indexes);

        tunaRepeatCar.setTunaRepeatListener(
            new TView.TunaTouchListener() {
                @Override
                public void tunaTouch(View v) {
                    tunaRepeatCar.setTunaRepeatCurrentX(TypedValue.COMPLEX_UNIT_PX, tunaRepeatCar.getTunaTouchEventX());
                    tunaRepeatStar.setTunaRepeatCurrentX(TypedValue.COMPLEX_UNIT_PX, tunaRepeatCar.getTunaTouchEventX(), true);
                    tunaLine.setLineCurrentX(TypedValue.COMPLEX_UNIT_PX, tunaRepeatCar.getTunaTouchEventX());
                }
            },
            new TView.TunaTouchDownListener() {
                @Override
                public void tunaTouchDown(View v) {
                    tunaRepeatStar.setTunaPress(true);
                }
            },
            new TView.TunaTouchUpListener() {
                @Override
                public void tunaTouchUp(View v) {
                    afterChoice();
                    tunaRepeatStar.setTunaPress(false);
                }
            }
        );

        buttonEvaluation01.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("Unable".equals(buttonEvaluation01.getText().toString().trim())) {
                    tunaRepeatStar.setTunaTouchType(TView.TunaTouchType.NONE);
                    tunaRepeatCar.setTunaTouchType(TView.TunaTouchType.NONE);
                    buttonEvaluation01.setText("Enable");
                } else {
                    tunaRepeatStar.setTunaTouchType(TView.TunaTouchType.EDGE);
                    tunaRepeatCar.setTunaTouchType(TView.TunaTouchType.EDGE);
                    buttonEvaluation01.setText("Unable");
                }
            }
        });

        spinnerEvaluation01 = findViewById(R.id.spinnerEvaluation01);
        spinnerEvaluation01.setAdapter(new ArrayAdapter(this, android.R.layout.simple_spinner_item, indexes));
        spinnerEvaluation01.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tunaRepeatStar.setTunaRepeatCurrentIndex(position - 1);
                tunaRepeatCar.setTunaRepeatCurrentIndex(position - 1);
                float tunaTouchEventX = tunaRepeatStar.getTunaTouchEventX();
                if (tunaTouchEventX != 0) {
                    tunaLine.setLineCurrentX(TypedValue.COMPLEX_UNIT_PX, tunaRepeatStar.getTunaTouchEventX());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        setLayoutByWidth(tunaRepeatTips, 5 * 40);

        //
        tunaRepeatTips.setTunaRepeatListener(
            null,
            null,
            new TView.TunaTouchUpListener() {
                @Override
                public void tunaTouchUp(View v) {
                    tunaRepeatTips.setTunaRepeatCurrentX(TypedValue.COMPLEX_UNIT_PX, tunaRepeatTips.getTunaTouchEventX());
//						Toast.makeText(TunaRepeatTest.this, "TuochUp", Toast.LENGTH_SHORT).show();
                }
            });
    }

    //
    private void afterChoice() {
        int tunaRepeatStarCurrentIndex = tunaRepeatStar.getTunaRepeatCurrentIndex();
        tunaLine.setLineCurrentX(TypedValue.COMPLEX_UNIT_PX, tunaRepeatStar.getTunaRepeatCurrentX());
        if (tunaRepeatStarCurrentIndex == 0) {
        } else if (tunaRepeatStarCurrentIndex == 1) {
        } else if (tunaRepeatStarCurrentIndex == 2) {
        } else if (tunaRepeatStarCurrentIndex == 3) {
        } else if (tunaRepeatStarCurrentIndex == 4) {
        }
    }
}
