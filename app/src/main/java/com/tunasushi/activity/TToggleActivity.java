package com.tunasushi.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.tunasushi.R;
import com.tunasushi.tuna.TToggle;

/**
 * @author TunaSashimi
 * @date 2015-10-30 16:53
 * @Copyright 2015 TunaSashimi. All rights reserved.
 * @Description
 */
public class TToggleActivity extends Activity {
    private TToggle tToggle01, tToggle02;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_t_toggle);

        tToggle01 = findViewById(R.id.tToggle01);
        tToggle02 = findViewById(R.id.tToggle02);

        //
        tToggle01.setToggleListener(new TToggle.ToggleListener() {
            @Override
            public void onToggle(boolean isToggle) {
                Toast.makeText(getApplication(), "tToggle01==>" + isToggle, Toast.LENGTH_SHORT).show();
            }
        });
        tToggle02.setToggleListener(new TToggle.ToggleListener() {
            @Override
            public void onToggle(boolean toggle) {
                Toast.makeText(getApplication(), "tToggle02==>" + toggle, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
