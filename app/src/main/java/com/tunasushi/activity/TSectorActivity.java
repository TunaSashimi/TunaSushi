package com.tunasushi.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.tunasushi.R;
import com.tunasushi.view.TSector;
import com.tunasushi.view.TView;

/**
 * @author TunaSashimi
 * @date 2020-02-12 18:02
 * @Copyright 2020 TunaSashimi. All rights reserved.
 * @Description
 */
public class TSectorActivity extends Activity implements TView.OnClickListener {
    private TSector tSector;
    private TView tViewProgress, tViewAdd, tViewSubtract;
    private int progress = 80;//进度

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_t_sector);

        //
        tSector = findViewById(R.id.tSector);

        //
        tViewProgress = findViewById(R.id.tViewProgress);
        tViewAdd = findViewById(R.id.tViewAdd);
        tViewSubtract = findViewById(R.id.tViewSubtract);

        tSector.setProgress(progress);
        tViewProgress.setText(progress + "%");

        //
        tViewAdd.setOnClickListener(this);
        tViewSubtract.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tViewAdd:
                progress += 1;
                if (progress > 99) {
                    progress = 100;
                }
                tSector.setProgress(progress);
                tViewProgress.setText(progress + "%");
                break;
            case R.id.tViewSubtract:
                progress -= 1;
                if (progress < 0) {
                    progress = 0;
                }
                tSector.setProgress(progress);
                tViewProgress.setText(progress + "%");
                break;
            default:
                break;
        }
    }
}
