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
import com.tunasushi.tuna.TGroup;
import com.tunasushi.tuna.TView;
import com.tunasushi.tuna.TView.TouchUpListener;

import java.util.Arrays;

import static com.tunasushi.tool.ConvertTool.dpToPx;
import static com.tunasushi.tuna.TGroup.link;
import static com.tunasushi.tuna.TGroup.create;

/**
 * @author TunaSashimi
 * @date 2015-10-30 16:53
 * @Copyright 2015 TunaSashimi. All rights reserved.
 * @Description
 */
public class TViewActivity extends Activity {
    private TView tViewRect01, tViewClassic02;
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
        tViewRect01.setTouchUpListener(new TouchUpListener() {
            @Override
            public void touchUp(TView t) {
                Toast.makeText(TViewActivity.this, "TView.TouchUp", Toast.LENGTH_SHORT).show();
            }
        });

        /**
         * 注意这里继承的是TView.OnClick事件
         */
        tViewClassic02 = findViewById(R.id.tViewRect02);
        tViewClassic02.setOnClickListener(new TView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TViewActivity.this, "TView.OnClick", Toast.LENGTH_SHORT).show();
            }
        });

        //
        tViewShadow01 = findViewById(R.id.tViewShadow01);
        tViewShadow01.setTouchUpListener(new TouchUpListener() {
            @Override
            public void touchUp(TView t) {
                tViewShadow01.setTextMark(
                        4, TypedValue.COMPLEX_UNIT_DIP,
                        Color.RED,
                        null,
                        0, TypedValue.COMPLEX_UNIT_DIP,
                        0,
                        0, TypedValue.COMPLEX_UNIT_DIP,
                        0, TypedValue.COMPLEX_UNIT_DIP
                );
            }
        });

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
        tViewShadow02.setTouchUpListener(new TouchUpListener() {
            @Override
            public void touchUp(TView t) {
                tViewShadow02.setTextMark(
                        4, TypedValue.COMPLEX_UNIT_DIP,
                        Color.RED,
                        null,
                        0, TypedValue.COMPLEX_UNIT_DIP,
                        0,
                        0, TypedValue.COMPLEX_UNIT_DIP,
                        0, TypedValue.COMPLEX_UNIT_DIP
                );
            }
        });

        //
        tViewGroupStart = findViewById(R.id.tViewGroupStart);
        tViewGroupEnd = findViewById(R.id.tViewGroupEnd);

        //if you want a different TView link, you can put an array of incoming link methods
        //	TView.link(new TView[]{tViewGroupStart, tViewGroupEnd});
        //	or
        //	can be placed on a list of incoming link method
        link(Arrays.asList(tViewGroupStart, tViewGroupEnd));

        //
        String groupTitleArray[] = {"金", "枪", "鱼", "刺", "身"};

        LinearLayout linearGroup = findViewById(R.id.linearGroup);
        TouchUpListener touchUpListener = new TouchUpListener() {
            @Override
            public void touchUp(TView t) {
                Toast.makeText(TViewActivity.this, t.getTextValue(), Toast.LENGTH_SHORT).show();
            }
        };

        //String[] stringArray, int index(下标默认0), TouchUpListener touchUpListener, LinearLayout linearLayout, int widthUnit(默认dp), int width,
        //int leftStyle,int rightStyle, int horizontalStyle, int wholeStyle
        TGroup.create(groupTitleArray,
                "枪",
                touchUpListener,
                linearGroup,
                dpToPx(60), LinearLayout.LayoutParams.MATCH_PARENT,
                R.style.TViewGroupLightGraySrart,
                R.style.TViewGroupLightGrayEnd,
                R.style.TViewGroupLightGrayOther
        );
    }

    //Defined in xml
    //Difference between onClick interface and touchUp interface
    //onClick interface parameter is View, onTouch interface parameter is TView!
    public void touchUp(TView t) {
        Toast.makeText(TViewActivity.this, "app:touchUp=\"touchUp\"", Toast.LENGTH_SHORT).show();
    }

    public void onClick(View v) {
        Toast.makeText(TViewActivity.this, "app:onClick=\"onClick\"", Toast.LENGTH_SHORT).show();
    }
}
