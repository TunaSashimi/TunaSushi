package com.tunasushi.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.tunasushi.R;
import com.tunasushi.tuna.TDialog;
import com.tunasushi.tuna.TView;

public class TDialogActivity extends Activity {

    private TDialog tDialogChoice, tDialogConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_t_dialog);

        tDialogChoice = findViewById(R.id.tDialogChoice);
        tDialogConfirm = findViewById(R.id.tDialogConfirm);

        tDialogChoice.setTouchListener(new TView.TouchListener() {
            @Override
            public void touch(TView t) {
                tDialogChoice.setDialogCurrentXY(tDialogChoice.getTouchEventX(), tDialogChoice.getTouchEventY());
            }
        });

        tDialogChoice.setTouchUpListener(new TView.TouchUpListener() {
            @Override
            public void touchUp(TView t) {
                String choiceTextValueIndex = tDialogChoice.getDialogCurrentChoiceTextValue();
                if (choiceTextValueIndex != null) {
                    Toast.makeText(TDialogActivity.this, choiceTextValueIndex, Toast.LENGTH_SHORT).show();
                }
                tDialogChoice.setDialogChoiceCurrentIndex(-1);
            }
        });

        tDialogConfirm.setTouchListener(new TView.TouchListener() {
            @Override
            public void touch(TView t) {
                tDialogConfirm.setDialogCurrentXY(tDialogConfirm.getTouchEventX(), tDialogConfirm.getTouchEventY());
            }
        });

        tDialogConfirm.setTouchUpListener(new TView.TouchUpListener() {
            @Override
            public void touchUp(TView t) {
                String choiceTextValueIndex = tDialogConfirm.getDialogCurrentChoiceTextValue();
                if (choiceTextValueIndex != null) {
                    Toast.makeText(TDialogActivity.this, choiceTextValueIndex, Toast.LENGTH_SHORT).show();
                }
                tDialogConfirm.setDialogChoiceCurrentIndex(-1);
            }
        });
    }
}
