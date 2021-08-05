package com.tunasushi.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import com.tunasushi.bean.BindingBean;
import com.tunasushi.demo.R;
import com.tunasushi.demo.databinding.ActivityTBindingBinding;
import com.tunasushi.view.TView;

import androidx.databinding.DataBindingUtil;

/**
 * @author TunaSashimi
 * @date 2020-04-12 20:30
 * @Copyright 2020 TunaSashimi. All rights reserved.
 * @Description
 */
public class TBindingActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityTBindingBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_t_binding);

        BindingBean bean = new BindingBean();
        bean.select.set(true);

        //
        binding.setBean(bean);

        final TView tView03 = findViewById(R.id.tView03);
        tView03.setOnClickListener(new TView.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = tView03.getText();
                if ("hello".equals(string)) {
                    tView03.setText("world");
                } else {
                    tView03.setText("hello");
                }
            }
        });
    }
}
