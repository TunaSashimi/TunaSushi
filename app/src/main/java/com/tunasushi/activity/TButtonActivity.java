package com.tunasushi.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.tunasushi.R;
import com.tunasushi.tuna.TButton;
import com.tunasushi.tuna.TView;

public class TButtonActivity extends Activity {

    private TButton tunaButton01, tunaButton02, tunaButton03;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tbutton);

        tunaButton01 = findViewById(R.id.tunaButton01);
        tunaButton02 = findViewById(R.id.tunaButton02);
        tunaButton03 = findViewById(R.id.tunaButton03);

        tunaButton01.setTunaTouchUpListener(new TView.TunaTouchUpListener() {
            @Override
            public void tunaTouchUp(View v) {
                Toast.makeText(TButtonActivity.this, tunaButton01.getTunaButtonTextValue(), Toast.LENGTH_SHORT).show();
            }
        });
        tunaButton02.setTunaTouchUpListener(new TView.TunaTouchUpListener() {
            @Override
            public void tunaTouchUp(View v) {
                Toast.makeText(TButtonActivity.this, tunaButton02.getTunaButtonTextValue(), Toast.LENGTH_SHORT).show();
            }
        });
        tunaButton03.setTunaTouchUpListener(new TView.TunaTouchUpListener() {
            @Override
            public void tunaTouchUp(View v) {
                Toast.makeText(TButtonActivity.this, tunaButton03.getTunaButtonTextValue(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
