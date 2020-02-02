package com.tunasushi.activity;

import android.app.Activity;
import android.os.Bundle;


import com.tunasushi.R;
import com.tunasushi.tuna.TView;

import java.util.Arrays;

import static com.tunasushi.tool.GroupTool.associate;

public class TMaterialActivity extends Activity {

    private TView

            tViewMaterialRadio01, tViewMaterialRadio02;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_t_materlai);

        //
        tViewMaterialRadio01 = findViewById(R.id.tViewMaterialRadio01);
        tViewMaterialRadio02 = findViewById(R.id.tViewMaterialRadio02);

        associate(Arrays.asList(tViewMaterialRadio01, tViewMaterialRadio02));
    }
}
