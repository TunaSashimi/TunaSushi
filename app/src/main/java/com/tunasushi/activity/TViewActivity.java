package com.tunasushi.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tunasushi.R;
import com.tunasushi.tool.DeviceTool;
import com.tunasushi.view.TGroup;
import com.tunasushi.view.TView;

import static com.tunasushi.tool.ConvertTool.dpToPx;

/**
 * @author TunaSashimi
 * @date 2015-10-30 16:53
 * @Copyright 2015 TunaSashimi. All rights reserved.
 * @Description
 */
public class TViewActivity extends Activity implements TView.TouchUpListener, TView.OnClickListener {
    private TView tViewRect01, tViewRect02, tView02;
    private TView tViewShadow01, tViewShadow02;
    private TView tViewGroupStart, tViewGroupEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_t_view);
        //
        DeviceTool.DeviceInfo deviceInfo = DeviceTool.getDeviceInfo(this);
        TextView textPrompt10 = findViewById(R.id.textPrompt10);
        textPrompt10.setText(deviceInfo.toString());

        //
        tViewRect01 = findViewById(R.id.tViewRect01);
        tViewRect01.setTouchUpListener(this);

        tViewRect02 = findViewById(R.id.tViewRect02);
        tViewRect02.setTouchUpListener(this);

        /**
         * 注意这里继承的是TView.OnClick事件
         */
        tView02 = findViewById(R.id.tViewRect02);
        tView02.setOnClickListener(this);

        //
        tViewShadow01 = findViewById(R.id.tViewShadow01);
        tViewShadow01.setTouchUpListener(this);

        //
        tViewShadow02 = findViewById(R.id.tViewShadow02);
        tViewShadow02.setTextMark(
                10, TypedValue.COMPLEX_UNIT_DIP,
                Color.RED,
                "10",
                10, TypedValue.COMPLEX_UNIT_DIP,
                Color.WHITE,
                0, TypedValue.COMPLEX_UNIT_DIP,
                0, TypedValue.COMPLEX_UNIT_DIP
        );
        tViewShadow02.setTouchUpListener(this);

        //
        tViewGroupStart = findViewById(R.id.tViewGroupStart);
        tViewGroupEnd = findViewById(R.id.tViewGroupEnd);

        //if you want a different TView link, you can put an array of incoming link methods
        //	TView.link(new TView[]{tViewGroupStart, tViewGroupEnd});
        //	or
        //	can be placed on a list of incoming link method
        TGroup.link(null, this, tViewGroupStart, tViewGroupEnd);

        //
        String groupTitleArray[] = {"金", "枪", "鱼", "刺", "身"};
        LinearLayout linearGroup = findViewById(R.id.linearGroup);
        TGroup.create(groupTitleArray,
                "枪",
                linearGroup,
                dpToPx(60), LinearLayout.LayoutParams.MATCH_PARENT,
                R.style.TViewGroupLightGraySrart,
                R.style.TViewGroupLightGrayEnd,
                R.style.TViewGroupLightGrayOther,
                this,
                null
        );
    }

    //Difference between onClick interface and touchUp interface
    //onClick interface parameter is View, onTouch interface parameter is TView!
    @Override
    public void touchUp(TView t) {
        switch (t.getId()) {
            case R.id.tViewRect01:
                Toast.makeText(TViewActivity.this, "TView.TouchUp", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tViewRect02:
                tViewRect02.setBackground(Color.RED);
                break;

            case R.id.tViewRect03:
                Toast.makeText(TViewActivity.this, "app:onClick=\"touchUp\"", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tViewShadow01:
                tViewShadow01.setTextMark(
                        4, TypedValue.COMPLEX_UNIT_DIP,
                        Color.RED,
                        null,
                        0, TypedValue.COMPLEX_UNIT_DIP,
                        0,
                        0, TypedValue.COMPLEX_UNIT_DIP,
                        0, TypedValue.COMPLEX_UNIT_DIP
                );
                break;
            case R.id.tViewShadow02:
                tViewShadow02.setTextMark(
                        4, TypedValue.COMPLEX_UNIT_DIP,
                        Color.RED,
                        null,
                        0, TypedValue.COMPLEX_UNIT_DIP,
                        0,
                        0, TypedValue.COMPLEX_UNIT_DIP,
                        0, TypedValue.COMPLEX_UNIT_DIP
                );
                break;
            default:
                Toast.makeText(TViewActivity.this, t.getText(), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tViewRect02:
                Toast.makeText(TViewActivity.this, "TView.OnClick", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tViewRect04:
                Toast.makeText(TViewActivity.this, "app:onClick=\"onClick\"", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
