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
/**
 * @author Tunasashimi
 * @date 10/30/15 16:53
 * @Copyright 2015 Sashimi. All rights reserved.
 * @Description
 */
public class TRepeatActivity extends Activity {
    private TLine tLine;
    private TRepeat tRepeatStar, tRepeatCar, tRepeatTips;

    private Button buttonEvaluation01;
    private Spinner spinnerEvaluation01;

    private String[] indexArray = {"-1", "0", "1", "2", "3"};

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
                    public void touch(TView t) {
                        t.setX(TypedValue.COMPLEX_UNIT_PX, tRepeatStar.getTouchEventX());
                        tRepeatCar.setX(TypedValue.COMPLEX_UNIT_PX, tRepeatStar.getTouchEventX());
                        tLine.setX(TypedValue.COMPLEX_UNIT_PX, tRepeatStar.getTouchEventX());
                    }
                },
                new TView.TouchDownListener() {
                    @Override
                    public void touchDown(TView t) {
                        tRepeatCar.setPress(true);
                    }
                },
                new TView.TouchUpListener() {
                    @Override
                    public void touchUp(TView t) {
                        afterChoice();
                        tRepeatCar.setPress(false);
                    }
                }
        );

        //
        tRepeatCar.setRepeatTotal(indexArray.length);
        tRepeatCar.setRepeatItemTextValueArray(indexArray);

        tRepeatCar.setRepeatListener(
                new TView.TouchListener() {
                    @Override
                    public void touch(TView t) {
                        t.setX(TypedValue.COMPLEX_UNIT_PX, tRepeatCar.getTouchEventX());
                        tRepeatStar.setX(TypedValue.COMPLEX_UNIT_PX, tRepeatCar.getTouchEventX());
                        tLine.setX(TypedValue.COMPLEX_UNIT_PX, tRepeatCar.getTouchEventX());
                    }
                },
                new TView.TouchDownListener() {
                    @Override
                    public void touchDown(TView t) {
                        tRepeatStar.setPress(true);
                    }
                },
                new TView.TouchUpListener() {
                    @Override
                    public void touchUp(TView t) {
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
        spinnerEvaluation01.setAdapter(new ArrayAdapter(this, android.R.layout.simple_spinner_item, indexArray));
        spinnerEvaluation01.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tRepeatStar.setRepeatIndex(position - 1);
                tRepeatCar.setRepeatIndex(position - 1);
                float tunaTouchEventX = tRepeatStar.getTouchEventX();
                if (tunaTouchEventX != 0) {
                    tLine.setX(TypedValue.COMPLEX_UNIT_PX, tRepeatStar.getTouchEventX());
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
                    public void touchUp(TView t) {
                        t.setX(TypedValue.COMPLEX_UNIT_PX, t.getTouchEventX());
                    }
                });
    }

    //
    private void afterChoice() {
        int repeatStarIndex = tRepeatStar.getRepeatIndex();
        tLine.setX(TypedValue.COMPLEX_UNIT_PX, tRepeatStar.getRepeatX());
        if (repeatStarIndex == 0) {
        } else if (repeatStarIndex == 1) {
        } else if (repeatStarIndex == 2) {
        } else if (repeatStarIndex == 3) {
        } else if (repeatStarIndex == 4) {
        }
    }
}
