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
    private TView tViewDialogConfirm, tViewDialogChoice, tViewDialogSelect;
    private TDialog tDialogChoice, tDialogConfirm, tDialogSelect;
    private int[] colorArrayChoice = {0xff999999, 0xff1b307c};
    private int[] colorArraySelect = {0xff999999, 0xff999999, 0xff1b307c};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_t_dialog);

        tViewDialogConfirm = findViewById(R.id.tViewDialogConfirm);
        tViewDialogChoice = findViewById(R.id.tViewDialogChoice);
        tViewDialogSelect = findViewById(R.id.tViewDialogSelect);

        //
        tViewDialogConfirm.setTouchUpListener(new TView.TouchUpListener() {
            @Override
            public void touchUp(TView t) {
                tDialogConfirm.setVisibility(View.VISIBLE);
            }
        });
        tViewDialogChoice.setTouchUpListener(new TView.TouchUpListener() {
            @Override
            public void touchUp(TView t) {
                tDialogChoice.setVisibility(View.VISIBLE);
            }
        });
        //
        tViewDialogSelect.setTouchUpListener(new TView.TouchUpListener() {
            @Override
            public void touchUp(TView t) {
                tDialogSelect.setVisibility(View.VISIBLE);
            }
        });


        //
        tDialogConfirm = findViewById(R.id.tDialogConfirm);
        tDialogChoice = findViewById(R.id.tDialogChoice);
        tDialogSelect = findViewById(R.id.tDialogSelect);

        //
        tDialogChoice.setDialogChoiceColorArray(colorArrayChoice);
        tDialogSelect.setDialogChoiceColorArray(colorArraySelect);

        //
        tDialogConfirm.setTouchUpListener(new TView.TouchUpListener() {
            @Override
            public void touchUp(TView t) {
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
            public void touchUp(TView t) {
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
            public void touchUp(TView t) {
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
