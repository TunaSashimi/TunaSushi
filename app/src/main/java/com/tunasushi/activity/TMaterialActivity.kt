package com.tunasushi.activity;

import android.app.Activity;
import android.os.Bundle;
import com.tunasushi.demo.R;
import com.tunasushi.view.TGroup;
import com.tunasushi.view.TView;

/**
 * @author TunaSashimi
 * @date 2015-10-30 16:53
 * @Copyright 2015 TunaSashimi. All rights reserved.
 * @Description
 */
public class TMaterialActivity extends Activity {
    private TView tViewMaterial03, tViewMaterial04;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_t_materlai);

        //
        tViewMaterial03 = findViewById(R.id.tViewMaterial03);
        tViewMaterial04 = findViewById(R.id.tViewMaterial04);

        TGroup.link(tViewMaterial03, tViewMaterial04);
    }
}
