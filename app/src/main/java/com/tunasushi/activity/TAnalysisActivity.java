package com.tunasushi.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.tunasushi.R;
import com.tunasushi.tuna.TAnalysis;


public class TAnalysisActivity extends Activity {
    private TAnalysis tAnalysisView;
    private SeekBar seekbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_t_analysis);

        tAnalysisView = findViewById(R.id.tAnalysis);
        seekbar = findViewById(R.id.seekbar);

        seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tAnalysisView.SetControlXandY(progress, progress);
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
