package com.tunasushi.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.TypedValue;

import com.tunasushi.R;
import com.tunasushi.tuna.TView;
import com.tunasushi.tuna.TLine;
import com.tunasushi.tuna.TRepeat;

import static com.tunasushi.tool.ConvertTool.dpToPx;
import static com.tunasushi.tool.ViewTool.setLayoutByWidth;

/**
 * @author TunaSashimi
 * @date 2015-10-30 16:53
 * @Copyright 2015 TunaSashimi. All rights reserved.
 * @Description
 */
public class TRepeatActivity extends Activity implements TView.TouchListener {
    private TLine tLine;
    private TRepeat tRepeatStar, tRepeatCar, tRepeatTips;
    private String[] indexArray = {"-1", "0", "1", "2", "3"};
    private int dx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_t_repeat);

        //
        tLine = findViewById(R.id.tLine);

        tRepeatStar = findViewById(R.id.tRepeatStar);
        tRepeatCar = findViewById(R.id.tRepeatCar);
        tRepeatTips = findViewById(R.id.tRepeatTips);

        //
        tRepeatStar.setTouchListener(this);

        //
        tRepeatCar.setRepeatTotal(indexArray.length);
        tRepeatCar.setRepeatItemTextArray(indexArray);
        tRepeatCar.setTouchListener(this);

        //
        setLayoutByWidth(tRepeatTips, 5 * 40, TypedValue.COMPLEX_UNIT_DIP);
    }

    @Override
    public void touch(TView t) {
        float touchEventX = t.getTouchX();
        float touchEventY = t.getTouchY();

        //
        if (dx == 0) {
            dx = tLine.getWidth() >> 1;
        }
        tLine.setX((int) touchEventX - dx + dpToPx(20));

        switch (t.getId()) {
            case R.id.tRepeatStar:
                tRepeatCar.setTouchXYRaw(touchEventX, touchEventY);
                break;
            case R.id.tRepeatCar:
                tRepeatStar.setTouchXYRaw(touchEventX, touchEventY);
                break;
            default:
                break;
        }
    }
}
