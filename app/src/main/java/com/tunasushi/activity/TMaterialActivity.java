package com.tunasushi.activity;

import android.app.Activity;
import android.os.Bundle;


import com.tunasushi.R;
import com.tunasushi.tuna.TView;

import java.util.Arrays;

import static com.tunasushi.tuna.TGroup.link;

/**
 * @author TunaSashimi
 * @date 2015-10-30 16:53
 * @Copyright 2015 TunaSashimi. All rights reserved.
 * @Description
 */
public class TMaterialActivity extends Activity {
    private TView tViewMaterialRadio01, tViewMaterialRadio02;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_t_materlai);

        //
        tViewMaterialRadio01 = findViewById(R.id.tViewGroupMaterial01);
        tViewMaterialRadio02 = findViewById(R.id.tViewGroupMaterial02);

        link(Arrays.asList(tViewMaterialRadio01, tViewMaterialRadio02));
    }
}
