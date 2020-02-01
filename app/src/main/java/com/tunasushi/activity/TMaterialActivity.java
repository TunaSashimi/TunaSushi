package com.tunasushi.activity;

import android.app.Activity;
import android.os.Bundle;


import com.tunasushi.R;
import com.tunasushi.tuna.TView;

import java.util.Arrays;

public class TMaterialActivity extends Activity {

    private TView

        tunaMaterialRadio01, tunaMaterialRadio02;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_t_materlai);

        //
        tunaMaterialRadio01 = findViewById(R.id.tViewMaterialRadio01);
        tunaMaterialRadio02 = findViewById(R.id.tViewMaterialRadio02);

        TView.tunaAssociate(Arrays.asList(tunaMaterialRadio01, tunaMaterialRadio02));
    }
}
