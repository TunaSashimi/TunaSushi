package com.tunasushi.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.tunasushi.R;
import com.tunasushi.tuna.TView;

public class TTrangleActivity extends Activity {

    private TView TViewButton, TViewDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_t_trangle);

        TViewButton = findViewById(R.id.tViewButton);
        TViewDialog = findViewById(R.id.tViewDialog);

        TViewButton.setTouchUpListener(new TView.TouchUpListener() {
            @Override
            public void touchUp(View v) {
                if ("TunaSashimi".equals(TViewDialog.getTextValue())) {
                    TViewDialog.setTextValue("金枪鱼刺身");
                } else {
                    TViewDialog.setTextValue("TunaSashimi");
                }
            }
        });
    }
}
