package com.tunasushi.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.tunasushi.R;
import com.tunasushi.tuna.TView;
import com.tunasushi.tuna.TWrap;

/**
 * @author TunaSashimi
 * @date 2015-10-30 16:53
 * @Copyright 2015 TunaSashimi. All rights reserved.
 * @Description
 */

public class TWrapActivity extends Activity {
    private TWrap tWrap01, tWrap02, tWrap03;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_t_wrap);

        tWrap01 = findViewById(R.id.tWrap01);
        tWrap01.setWrapItemTextArray(
                new String[]{
                        "用户要求换车"
//                        , "车辆不整洁"
//                        , "车辆设施不完备", "车辆损坏"
//                        , "用户要求换车", "车辆不整洁",
//                        "车辆设施不完备", "车辆损坏"
                });
        tWrap01.setOnClickListener(new TView.OnClickListener() {
            @Override
            public void onClick(View v) {
                TWrap tWrap = (TWrap) v;
                Toast.makeText(getApplication(), tWrap.getWrapSelectString(), Toast.LENGTH_SHORT).show();
            }
        });

//        tWrap02 = findViewById(R.id.tWrap02);
//        tWrap02.setWrapItemTextArray(
//                new String[]{
//                        "用户要求换车", "车辆不整洁", "车辆设施不完备", "车辆损坏"
//                        , "用户要求换车", "车辆不整洁", "车辆设施不完备", "车辆损坏",
//                });
//        tWrap02.setOnClickListener(new TView.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                TWrap tWrap = (TWrap) v;
//                Toast.makeText(getApplication(), tWrap.getWrapSelectString(), Toast.LENGTH_SHORT).show();
//            }
//        });
//        tWrap03 = findViewById(R.id.tWrap03);
//        tWrap03.setWrapItemTextArray(
//                new String[]{
//                        "用户要求换车", "车辆不整洁", "车辆设施不完备", "车辆损坏"
//                        , "用户要求换车", "车辆不整洁", "车辆设施不完备", "车辆损坏"
//                });
//        tWrap03.setOnClickListener(new TView.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                TWrap tWrap = (TWrap) v;
//                Toast.makeText(getApplication(), tWrap.getWrapSelectString(), Toast.LENGTH_SHORT).show();
//            }
//        });
    }
}