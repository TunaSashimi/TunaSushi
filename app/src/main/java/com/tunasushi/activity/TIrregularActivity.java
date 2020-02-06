package com.tunasushi.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Toast;

import com.tunasushi.R;
import com.tunasushi.tuna.TView;
import com.tunasushi.tuna.TIrregular;

public class TIrregularActivity extends Activity {

    private TIrregular tIrregular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_t_iregular);

        tIrregular = findViewById(R.id.tIrregular);

        tIrregular.setTouchListener(new TView.TouchListener() {
            @Override
            public void touch(View v) {
                tIrregular.setIrregularCurrentX(TypedValue.COMPLEX_UNIT_PX, tIrregular.getTouchEventX());
            }
        });

        tIrregular.setIrregularChangeListener(new TIrregular.IrregularChangeListener() {
            @Override
            public void irregularChange(boolean b) {
                Toast.makeText(getApplication(), "Change==>" + b, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
