package com.tunasushi;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.tuna.TView;

public class TTrangleTest extends Activity {

    private TView TViewButton, TViewDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.ttrangletest);

        TViewButton = findViewById(R.id.viewButton);
        TViewDialog = findViewById(R.id.viewDialog);

        TViewButton.setTunaTouchUpListener(new TView.TunaTouchUpListener() {
            @Override
            public void tunaTouchUp(View v) {
                if ("TunaSashimi".equals(TViewDialog.getTunaTextValue())) {
                    TViewDialog.setTunaTextValue("金枪鱼刺身");
                } else {
                    TViewDialog.setTunaTextValue("TunaSashimi");
                }
            }
        });
    }
}
