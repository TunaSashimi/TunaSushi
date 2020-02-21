package com.tunasushi.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.tunasushi.R;
import com.tunasushi.tuna.TView;
import com.tunasushi.tuna.TLine;
import com.tunasushi.tuna.TRepeat;

import static com.tunasushi.tool.ViewTool.setLayoutByWidth;

/**
 * @author TunaSashimi
 * @date 2015-10-30 16:53
 * @Copyright 2015 TunaSashimi. All rights reserved.
 * @Description
 */
public class TRepeatActivity extends Activity {
    private TLine tLine;
    private TView tView;
    private TRepeat tRepeatStar, tRepeatCar, tRepeatTips;

    private Spinner spinner;
    private String[] indexArray = {"-1", "0", "1", "2", "3"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_t_repeat);

        tLine = findViewById(R.id.tLine);

        tView = findViewById(R.id.tView);

        tRepeatStar = findViewById(R.id.tRepeatStar);
        tRepeatCar = findViewById(R.id.tRepeatCar);
        tRepeatTips = findViewById(R.id.tRepeatTips);

        //
        tRepeatCar.setRepeatTotal(indexArray.length);
        tRepeatCar.setRepeatItemTextValueArray(indexArray);

        //
        tRepeatStar.setTouchListener(new TView.TouchListener() {
            @Override
            public void touch(TView t) {
                float touchEventX = t.getTouchX();
                float touchEventY = t.getTouchY();
                tRepeatCar.setTouchXYRaw(touchEventX, touchEventY);
                tLine.setTouchXYRaw(touchEventX, touchEventY);
            }
        });

        tRepeatCar.setTouchListener(new TView.TouchListener() {
            @Override
            public void touch(TView t) {
                float touchEventX = t.getTouchX();
                float touchEventY = t.getTouchY();
                tRepeatStar.setTouchXYRaw(touchEventX, touchEventY);
                tLine.setTouchXYRaw(touchEventX, touchEventY);
            }
        });

        //
        setLayoutByWidth(tRepeatTips, 5 * 40, TypedValue.COMPLEX_UNIT_DIP);

        tView.setOnClickListener(new TView.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("Unable".equals(tView.getTextValue().trim())) {
                    tRepeatStar.setTouchType(TView.TouchType.NONE);
                    tRepeatCar.setTouchType(TView.TouchType.NONE);
                    tView.setTextValue("Enable");
                } else {
                    tRepeatStar.setTouchType(TView.TouchType.EDGE);
                    tRepeatCar.setTouchType(TView.TouchType.EDGE);
                    tView.setTextValue("Unable");
                }
            }
        });

        spinner = findViewById(R.id.spinner);
        spinner.setAdapter(new ArrayAdapter(this, android.R.layout.simple_spinner_item, indexArray));
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tRepeatStar.setRepeatIndex(position - 1);
                tRepeatCar.setRepeatIndex(position - 1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}
