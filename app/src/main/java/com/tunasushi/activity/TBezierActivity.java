package com.tunasushi.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.SeekBar;

import com.tunasushi.R;
import com.tunasushi.tuna.TBezier;

/**
 * @author Tunasashimi
 * @date 2020-02-12 20:01
 * @Copyright 2020 TunaSashimi. All rights reserved.
 * @Description
 */
public class TBezierActivity extends Activity implements SeekBar.OnSeekBarChangeListener {
    private TBezier tBezier;
    private SeekBar seekBar, seekBar2, seekBar3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_t_bezier);

        //Note this configuration!
        //app:touchType="none"
        tBezier = findViewById(R.id.debug_metaball);

        seekBar = findViewById(R.id.seekBar);
        seekBar2 = findViewById(R.id.seekBar2);
        seekBar3 = findViewById(R.id.seekBar3);
        seekBar.setOnSeekBarChangeListener(this);
        seekBar2.setOnSeekBarChangeListener(this);
        seekBar3.setOnSeekBarChangeListener(this);
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.seekBar:
                tBezier.setMaxDistance(progress);
                break;
            case R.id.seekBar2:
                tBezier.setMv(progress / 100f);
                break;
            case R.id.seekBar3:
                tBezier.setHandleLenRate(progress / 100f);
                break;
        }

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
