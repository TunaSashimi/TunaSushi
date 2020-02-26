package com.tunasushi.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.tunasushi.R;
import com.tunasushi.tuna.TButton;
import com.tunasushi.tuna.TDialog;
import com.tunasushi.tuna.TView;

/**
 * @author TunaSashimi
 * @date 2015-10-30 16:53
 * @Copyright 2015 TunaSashimi. All rights reserved.
 * @Description
 */
public class TDialogActivity extends Activity implements TView.TouchUpListener {
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

        tDialogConfirm = findViewById(R.id.tDialogConfirm);
        tDialogChoice = findViewById(R.id.tDialogChoice);
        tDialogSelect = findViewById(R.id.tDialogSelect);

        //
        tViewConfirm.setTouchUpListener(this);
        tViewChoice.setTouchUpListener(this);
        tViewSelect.setTouchUpListener(this);

        tDialogConfirm.setTouchUpListener(this);
        tDialogChoice.setTouchUpListener(this);
        tDialogSelect.setTouchUpListener(this);
    }

    @Override
    public void touchUp(TView t) {
        switch (t.getId()) {
            case R.id.tViewConfirm:
                tDialogConfirm.setVisibility(View.VISIBLE);
                break;
            case R.id.tViewChoice:
                tDialogChoice.setVisibility(View.VISIBLE);
                break;
            case R.id.tViewSelect:
                tDialogSelect.setVisibility(View.VISIBLE);
                break;
            case R.id.tDialogConfirm:
            case R.id.tDialogChoice:
            case R.id.tDialogSelect:
                TDialog tDialog = (TDialog) t;
                int choiceIndex = tDialog.getDialogChoiceIndex();
                String valueArray[] = tDialog.getDialogChoiceTextValueArray();
                if (-1 == choiceIndex) {
                    Toast.makeText(this, "Nothing Choice", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, valueArray[choiceIndex], Toast.LENGTH_SHORT).show();
                    tDialog.setVisibility(View.INVISIBLE);
                }
                tDialog.setDialogChoiceIndex(-1);
                break;
            default:
                break;
        }
    }
}
