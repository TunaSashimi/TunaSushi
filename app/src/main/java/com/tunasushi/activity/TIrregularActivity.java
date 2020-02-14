package com.tunasushi.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.Toast;

import com.tunasushi.R;
import com.tunasushi.tuna.TView;
import com.tunasushi.tuna.TIrregular;

/**
 * @author TunaSashimi
 * @date 2015-10-30 16:53
 * @Copyright 2015 TunaSashimi. All rights reserved.
 * @Description
 */
public class TIrregularActivity extends Activity {

    private TIrregular tIrregular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_t_iregular);

        tIrregular = findViewById(R.id.tIrregular);

        tIrregular.setTouchListener(new TView.TouchListener() {
            @Override
            public void touch(TView t) {
                t.setX(t.getTouchEventX());
            }
        });

        tIrregular.setIrregularSelectListener(new TIrregular.IrregularSelectListener() {
            @Override
            public void onIrregularSelect(boolean irregularSelect) {
                Toast.makeText(getApplication(), "irregularSelect==>" + irregularSelect, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
