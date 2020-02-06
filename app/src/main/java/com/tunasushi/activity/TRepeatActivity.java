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
    private TLine tLine;
    private TRepeat tRepeatStar, tRepeatCar, tRepeatTips;

    private Button buttonEvaluation01;
    private Spinner spinnerEvaluation01;

    private String[]
        indexes = {"-1", "0", "1", "2", "3"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_t_repeat);

        tLine = findViewById(R.id.tLine);

        buttonEvaluation01 = findViewById(R.id.buttonEvaluation01);

        tRepeatStar = findViewById(R.id.tRepeatStar);
        tRepeatCar = findViewById(R.id.tRepeatCar);
        tRepeatTips = findViewById(R.id.tRepeatTips);

        tRepeatStar.setRepeatListener(
            new TView.TouchListener() {
                @Override
                public void touch(View v) {
                    tRepeatStar.setRepeatCurrentX(TypedValue.COMPLEX_UNIT_PX, tRepeatStar.getTouchEventX());
                    tRepeatCar.setRepeatCurrentX(TypedValue.COMPLEX_UNIT_PX, tRepeatStar.getTouchEventX(), true);
                    tLine.setLineCurrentX(TypedValue.COMPLEX_UNIT_PX, tRepeatStar.getTouchEventX());
                }
            },
            new TView.TouchDownListener() {
                @Override
                public void touchDown(View v) {
                    tRepeatCar.setPress(true);
                }
            },
            new TView.TouchUpListener() {
                @Override
                public void touchUp(View v) {
                    afterChoice();
                    tRepeatCar.setPress(false);
                }
            }
        );

        //
        tRepeatCar.setRepeatTotal(indexes.length);
        tRepeatCar.setRepeatItemTextValueArray(indexes);

        tRepeatCar.setRepeatListener(
            new TView.TouchListener() {
                @Override
                public void touch(View v) {
                    tRepeatCar.setRepeatCurrentX(TypedValue.COMPLEX_UNIT_PX, tRepeatCar.getTouchEventX());
                    tRepeatStar.setRepeatCurrentX(TypedValue.COMPLEX_UNIT_PX, tRepeatCar.getTouchEventX(), true);
                    tLine.setLineCurrentX(TypedValue.COMPLEX_UNIT_PX, tRepeatCar.getTouchEventX());
                }
            },
            new TView.TouchDownListener() {
                @Override
                public void touchDown(View v) {
                    tRepeatStar.setPress(true);
                }
            },
            new TView.TouchUpListener() {
                @Override
                public void touchUp(View v) {
                    afterChoice();
                    tRepeatStar.setPress(false);
                }
            }
        );

        buttonEvaluation01.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("Unable".equals(buttonEvaluation01.getText().toString().trim())) {
                    tRepeatStar.setTouchType(TView.TouchType.NONE);
                    tRepeatCar.setTouchType(TView.TouchType.NONE);
                    buttonEvaluation01.setText("Enable");
                } else {
                    tRepeatStar.setTouchType(TView.TouchType.EDGE);
                    tRepeatCar.setTouchType(TView.TouchType.EDGE);
                    buttonEvaluation01.setText("Unable");
                }
            }
        });

        spinnerEvaluation01 = findViewById(R.id.spinnerEvaluation01);
        spinnerEvaluation01.setAdapter(new ArrayAdapter(this, android.R.layout.simple_spinner_item, indexes));
        spinnerEvaluation01.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tRepeatStar.setRepeatCurrentIndex(position - 1);
                tRepeatCar.setRepeatCurrentIndex(position - 1);
                float tunaTouchEventX = tRepeatStar.getTouchEventX();
                if (tunaTouchEventX != 0) {
                    tLine.setLineCurrentX(TypedValue.COMPLEX_UNIT_PX, tRepeatStar.getTouchEventX());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        setLayoutByWidth(tRepeatTips, 5 * 40);

        //
        tRepeatTips.setRepeatListener(
            null,
            null,
            new TView.TouchUpListener() {
                @Override
                public void touchUp(View v) {
                    tRepeatTips.setRepeatCurrentX(TypedValue.COMPLEX_UNIT_PX, tRepeatTips.getTouchEventX());
//						Toast.makeText(repeatTest.this, "TuochUp", Toast.LENGTH_SHORT).show();
                }
            });
    }

    //
    private void afterChoice() {
        int repeatStarCurrentIndex = tRepeatStar.getRepeatCurrentIndex();
        tLine.setLineCurrentX(TypedValue.COMPLEX_UNIT_PX, tRepeatStar.getRepeatCurrentX());
        if (repeatStarCurrentIndex == 0) {
        } else if (repeatStarCurrentIndex == 1) {
        } else if (repeatStarCurrentIndex == 2) {
        } else if (repeatStarCurrentIndex == 3) {
        } else if (repeatStarCurrentIndex == 4) {
        }
    }
}
