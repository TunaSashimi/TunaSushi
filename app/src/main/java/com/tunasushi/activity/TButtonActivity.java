package com.tunasushi.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Toast;

import com.tunasushi.R;
import com.tunasushi.tuna.TButton;
import com.tunasushi.tuna.TView;

/**
 * @author TunaSashimi
 * @date 2015-10-30 16:53
 * @Copyright 2015 TunaSashimi. All rights reserved.
 * @Description
 */
public class TButtonActivity extends Activity implements TView.TouchUpListener {
    private TButton tButton01, tButton02, tButton03;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_t_button);

        tButton01 = findViewById(R.id.tButton01);
        tButton02 = findViewById(R.id.tButton02);
        tButton03 = findViewById(R.id.tButton03);

        tButton01.setTouchUpListener(this);
        tButton02.setTouchUpListener(this);
        tButton03.setTouchUpListener(this);
    }

    @Override
    public void touchUp(TView t) {
        switch (t.getId()) {
            case R.id.tButton01:
            case R.id.tButton02:
            case R.id.tButton03:
                TButton tButton = (TButton) t;
                Toast.makeText(this, tButton.getButtonTextValue(), Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
