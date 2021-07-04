package com.tunasushi.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.tunasushi.R;
import com.tunasushi.view.TProgress;

/**
 * @author TunaSashimi
 * @date 2015-10-30 16:53
 * @Copyright 2015 TunaSashimi. All rights reserved.
 * @Description
 */
public class TProgressActivity extends Activity {
    private TProgress tProgressCircleClockWise, tProgressCircleUpWard, tProgressCircleUpDown,
            tProgressCustomClockWise, tProgressCustomUpWard, tProgressCustomUpDown;

    private SeekBar seekbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_t_progress);

        tProgressCircleClockWise = findViewById(R.id.tProgressCircleClockWise);
        tProgressCircleUpWard = findViewById(R.id.tProgressCircleUpWard);
        tProgressCircleUpDown = findViewById(R.id.tProgressCircleUpDown);

        tProgressCustomClockWise = findViewById(R.id.tProgressCustomClockWise);
        tProgressCustomUpWard = findViewById(R.id.tProgressCustomUpWard);
        tProgressCustomUpDown = findViewById(R.id.tProgressCustomUpDown);

        seekbar = findViewById(R.id.seekbar);
        seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tProgressCircleClockWise.setProgressPercent(progress * 0.01f);
                tProgressCircleUpWard.setProgressPercent(progress * 0.01f);
                tProgressCircleUpDown.setProgressPercent(progress * 0.01f);

                tProgressCustomClockWise.setProgressPercent(progress * 0.01f);
                tProgressCustomUpWard.setProgressPercent(progress * 0.01f);
                tProgressCustomUpDown.setProgressPercent(progress * 0.01f);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }
}
