package com.tunasushi.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.tunasushi.R;
import com.tunasushi.tuna.TButton;
import com.tunasushi.tuna.TView;

public class TButtonActivity extends Activity {

    private TButton tButton01, tButton02, tButton03;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_t_button);

        tButton01 = findViewById(R.id.tButton01);
        tButton02 = findViewById(R.id.tButton02);
        tButton03 = findViewById(R.id.tButton03);

        tButton01.setTouchUpListener(new TView.TouchUpListener() {
            @Override
            public void touchUp(View v) {
                Toast.makeText(TButtonActivity.this, tButton01.getButtonTextValue(), Toast.LENGTH_SHORT).show();
            }
        });
        tButton02.setTouchUpListener(new TView.TouchUpListener() {
            @Override
            public void touchUp(View v) {
                Toast.makeText(TButtonActivity.this, tButton02.getButtonTextValue(), Toast.LENGTH_SHORT).show();
            }
        });
        tButton03.setTouchUpListener(new TView.TouchUpListener() {
            @Override
            public void touchUp(View v) {
                Toast.makeText(TButtonActivity.this, tButton03.getButtonTextValue(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
