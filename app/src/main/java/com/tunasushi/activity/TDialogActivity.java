package com.tunasushi.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.tunasushi.R;
import com.tunasushi.tuna.TDialog;
import com.tunasushi.tuna.TView;

/**
 * @author TunaSashimi
 * @date 2015-10-30 16:53
 * @Copyright 2015 TunaSashimi. All rights reserved.
 * @Description
 */
public class TDialogActivity extends Activity {
    private TView tViewConfirm, tViewChoice, tViewSelect;
    private TDialog tDialogChoice, tDialogConfirm, tDialogSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_t_dialog);

        //
        tViewConfirm = findViewById(R.id.tViewConfirm);
        tViewChoice = findViewById(R.id.tViewChoice);
        tViewSelect = findViewById(R.id.tViewSelect);

        //
        tViewConfirm.setTouchUpListener(new TView.TouchUpListener() {
            @Override
            public void touchUp(View t) {
                tDialogConfirm.setVisibility(View.VISIBLE);
            }
        });
        tViewChoice.setTouchUpListener(new TView.TouchUpListener() {
            @Override
            public void touchUp(View t) {
                tDialogChoice.setVisibility(View.VISIBLE);
            }
        });
        //
        tViewSelect.setTouchUpListener(new TView.TouchUpListener() {
            @Override
            public void touchUp(View t) {
                tDialogSelect.setVisibility(View.VISIBLE);
            }
        });


        //
        tDialogConfirm = findViewById(R.id.tDialogConfirm);
        tDialogChoice = findViewById(R.id.tDialogChoice);
        tDialogSelect = findViewById(R.id.tDialogSelect);

        //
        tDialogConfirm.setTouchUpListener(new TView.TouchUpListener() {
            @Override
            public void touchUp(View view) {
                int choiceIndex = tDialogConfirm.getDialogChoiceIndex();
                String valueArray[] = tDialogConfirm.getDialogChoiceTextValueArray();
                if (-1 == choiceIndex) {
                    Toast.makeText(TDialogActivity.this, "Nothing Choice", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TDialogActivity.this, valueArray[choiceIndex], Toast.LENGTH_SHORT).show();
                    tDialogConfirm.setVisibility(View.INVISIBLE);
                }
                tDialogConfirm.setDialogChoiceIndex(-1);
            }
        });
        tDialogChoice.setTouchUpListener(new TView.TouchUpListener() {
            @Override
            public void touchUp(View view) {
                int choiceIndex = tDialogChoice.getDialogChoiceIndex();
                String valueArray[] = tDialogChoice.getDialogChoiceTextValueArray();
                if (-1 == choiceIndex) {
                    Toast.makeText(TDialogActivity.this, "Nothing Choice", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TDialogActivity.this, valueArray[choiceIndex], Toast.LENGTH_SHORT).show();
                    tDialogChoice.setVisibility(View.INVISIBLE);
                }
                tDialogChoice.setDialogChoiceIndex(-1);
            }
        });
        tDialogSelect.setTouchUpListener(new TView.TouchUpListener() {
            @Override
            public void touchUp(View view) {
                int choiceIndex = tDialogSelect.getDialogChoiceIndex();
                String valueArray[] = tDialogSelect.getDialogChoiceTextValueArray();
                if (-1 == choiceIndex) {
                    Toast.makeText(TDialogActivity.this, "Nothing Choice", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TDialogActivity.this, valueArray[choiceIndex], Toast.LENGTH_SHORT).show();
                    tDialogSelect.setVisibility(View.INVISIBLE);
                }
                tDialogSelect.setDialogChoiceIndex(-1);
            }
        });
    }
}
