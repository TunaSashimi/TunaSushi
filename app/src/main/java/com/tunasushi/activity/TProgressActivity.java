package com.tunasushi.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.tunasushi.R;
import com.tunasushi.tuna.TProgress;


public class TProgressActivity extends Activity {

    private TProgress tunaProgressCircleClockWise, tunaProgressCircleUpWard, tunaProgressCircleUpDown,
        tunaProgressCustomClockWise, tunaProgressCustomUpWard, tunaProgressCustomUpDown;

    private SeekBar seekbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_t_progress);

        tunaProgressCircleClockWise = findViewById(R.id.tProgressCircleClockWise);
        tunaProgressCircleUpWard = findViewById(R.id.tProgressCircleUpWard);
        tunaProgressCircleUpDown = findViewById(R.id.tProgressCircleUpDown);

        tunaProgressCustomClockWise = findViewById(R.id.tProgressCustomClockWise);
        tunaProgressCustomUpWard = findViewById(R.id.tProgressCustomUpWard);
        tunaProgressCustomUpDown = findViewById(R.id.tProgressCustomUpDown);

        seekbar = findViewById(R.id.seekbar);
        seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tunaProgressCircleClockWise.setTunaProgressPercent(progress * 0.01f);
                tunaProgressCircleUpWard.setTunaProgressPercent(progress * 0.01f);
                tunaProgressCircleUpDown.setTunaProgressPercent(progress * 0.01f);

                tunaProgressCustomClockWise.setTunaProgressPercent(progress * 0.01f);
                tunaProgressCustomUpWard.setTunaProgressPercent(progress * 0.01f);
                tunaProgressCustomUpDown.setTunaProgressPercent(progress * 0.01f);
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
