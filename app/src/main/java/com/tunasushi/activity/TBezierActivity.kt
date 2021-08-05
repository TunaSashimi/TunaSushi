package com.tunasushi.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.SeekBar;
import com.tunasushi.demo.R;
import com.tunasushi.view.TBezier;

/**
 * @author TunaSashimi
 * @date 2020-02-12 20:01
 * @Copyright 2020 TunaSashimi. All rights reserved.
 * @Description
 */
public class TBezierActivity extends Activity implements SeekBar.OnSeekBarChangeListener {
    private TBezier tBezier;
    private SeekBar seekBar01, seekBar02, seekBar03;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_t_bezier);

        tBezier = findViewById(R.id.tBezier02);

        seekBar01 = findViewById(R.id.seekBar01);
        seekBar02 = findViewById(R.id.seekBar02);
        seekBar03 = findViewById(R.id.seekBar03);
        seekBar01.setOnSeekBarChangeListener(this);
        seekBar02.setOnSeekBarChangeListener(this);
        seekBar03.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.seekBar01:
                tBezier.setMaxDistance(progress);
                break;
            case R.id.seekBar02:
                tBezier.setMv(progress / 100f);
                break;
            case R.id.seekBar03:
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
