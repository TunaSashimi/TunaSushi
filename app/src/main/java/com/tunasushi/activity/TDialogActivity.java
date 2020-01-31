package com.tunasushi.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.tunasushi.R;
import com.tunasushi.tuna.TDialog;
import com.tunasushi.tuna.TView;

public class TDialogActivity extends Activity {

    private TDialog tunaDialogChoice, tunaDialogConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tdialog);

        tunaDialogChoice = findViewById(R.id.tunaDialogChoice);
        tunaDialogConfirm = findViewById(R.id.tunaDialogConfirm);

        tunaDialogChoice.setTunaTouchListener(new TView.TunaTouchListener() {
            @Override
            public void tunaTouch(View v) {
                tunaDialogChoice.setTunaDialogCurrentXY(tunaDialogChoice.getTunaTouchEventX(), tunaDialogChoice.getTunaTouchEventY());
            }
        });

        tunaDialogChoice.setTunaTouchUpListener(new TView.TunaTouchUpListener() {
            @Override
            public void tunaTouchUp(View v) {
                String choiceTextValueIndex = tunaDialogChoice.getTunaDialogCurrentChoiceTextValue();
                if (choiceTextValueIndex != null) {
                    Toast.makeText(TDialogActivity.this, choiceTextValueIndex, Toast.LENGTH_SHORT).show();
                }
                tunaDialogChoice.setTunaDialogChoiceCurrentIndex(-1);
            }
        });

        tunaDialogConfirm.setTunaTouchListener(new TView.TunaTouchListener() {
            @Override
            public void tunaTouch(View v) {
                tunaDialogConfirm.setTunaDialogCurrentXY(tunaDialogConfirm.getTunaTouchEventX(), tunaDialogConfirm.getTunaTouchEventY());
            }
        });

        tunaDialogConfirm.setTunaTouchUpListener(new TView.TunaTouchUpListener() {
            @Override
            public void tunaTouchUp(View v) {
                String choiceTextValueIndex = tunaDialogConfirm.getTunaDialogCurrentChoiceTextValue();
                if (choiceTextValueIndex != null) {
                    Toast.makeText(TDialogActivity.this, choiceTextValueIndex, Toast.LENGTH_SHORT).show();
                }
                tunaDialogConfirm.setTunaDialogChoiceCurrentIndex(-1);
            }
        });
    }
}
