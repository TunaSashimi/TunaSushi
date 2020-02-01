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

    private TIrregular tunaIrregular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_t_iregular);

        tunaIrregular = findViewById(R.id.tIrregular);

        tunaIrregular.setTunaTouchListener(new TView.TunaTouchListener() {
            @Override
            public void tunaTouch(View v) {
                tunaIrregular.setTunaIrregularCurrentX(TypedValue.COMPLEX_UNIT_PX, tunaIrregular.getTunaTouchEventX());
            }
        });

        tunaIrregular.setTunaIrregularChangeListener(new TIrregular.TunaIrregularChangeListener() {
            @Override
            public void tunaIrregularChange(boolean b) {
                Toast.makeText(getApplication(), "Change==>" + b, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
