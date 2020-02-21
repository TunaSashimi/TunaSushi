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
import com.tunasushi.tuna.TView;
import com.tunasushi.tuna.TView.TouchUpListener;

import java.util.Arrays;

import static com.tunasushi.tool.GroupTool.associate;
import static com.tunasushi.tool.GroupTool.dynamic;

/**
 * @author TunaSashimi
 * @date 2015-10-30 16:53
 * @Copyright 2015 TunaSashimi. All rights reserved.
 * @Description
 */
public class TViewActivity extends Activity {
    private TView tViewRectClassic01, tViewRectClassic02;
    private TView tViewMainButton01, tViewMainButton02;
    private TView tViewRadioGroupDrackBrownLeft, tViewRadioGroupDrackBrownRight;

    private TouchUpListener tunaTouchUpListener = new TouchUpListener() {
        @Override
        public void touchUp(TView t) {
            switch (t.getId()) {
                case R.id.tViewRadioGroupDrackBrownLeft:
                    Toast.makeText(TViewActivity.this, "金枪鱼刺身", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.tViewRadioGroupDrackBrownRight:
                    Toast.makeText(TViewActivity.this, "TunaSashimi", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_t_view);

        //
        DeviceTool.DeviceInfo deviceInfo = DeviceTool.getDeviceInfo(this);
        TextView textDeviceInfo = findViewById(R.id.textDeviceInfo);
        textDeviceInfo.setText(deviceInfo.toString());

        //
        tViewRectClassic01 = findViewById(R.id.tViewRectClassic01);
        tViewRectClassic01.setTouchUpListener(new TouchUpListener() {
            @Override
            public void touchUp(TView t) {
                Toast.makeText(TViewActivity.this, "TView.TouchUp", Toast.LENGTH_SHORT).show();
            }
        });

        /**
         * 注意这里继承的是TView.OnClick事件
         */
        tViewRectClassic02 = findViewById(R.id.tViewRectClassic02);
        tViewRectClassic02.setOnClickListener(new TView.OnClickListener() {
            @Override
            public void onClick(TView t) {
                Toast.makeText(TViewActivity.this, "TView.OnClick", Toast.LENGTH_SHORT).show();
            }
        });

        //
        tViewMainButton01 = findViewById(R.id.tViewShadowButton01);
        tViewMainButton01.setTouchUpListener(new TouchUpListener() {
            @Override
            public void touchUp(TView t) {
                tViewMainButton01.setTextMark(
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
        tViewMainButton02 = findViewById(R.id.tViewShadowButton02);
        tViewMainButton02.setTextMark(
                10, TypedValue.COMPLEX_UNIT_DIP,
                Color.RED,
                "10",
                10, TypedValue.COMPLEX_UNIT_DIP,
                Color.WHITE,
                0, TypedValue.COMPLEX_UNIT_DIP,
                0, TypedValue.COMPLEX_UNIT_DIP
        );
        tViewMainButton02.setTouchUpListener(new TouchUpListener() {
            @Override
            public void touchUp(TView t) {
                tViewMainButton02.setTextMark(
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
        tViewRadioGroupDrackBrownLeft = findViewById(R.id.tViewRadioGroupDrackBrownLeft);
        tViewRadioGroupDrackBrownRight = findViewById(R.id.tViewRadioGroupDrackBrownRight);

        tViewRadioGroupDrackBrownLeft.setTouchUpListener(tunaTouchUpListener);
        tViewRadioGroupDrackBrownRight.setTouchUpListener(tunaTouchUpListener);

        //if you want a different TView link, you can put an array of incoming associate methods

        //	TView.associate(new TView[]{tViewRadioGroupDrackBrownLeft, tViewRadioGroupDrackBrownRight});

        //or  can be placed on a list of incoming associate method
        associate(Arrays.asList(tViewRadioGroupDrackBrownLeft, tViewRadioGroupDrackBrownRight));

        //
        String radioGroupTitleArray[] = {
                "金",
                "枪",
                "鱼",
                "刺",
                "身"
        };
        LinearLayout linearRadioGroupLightGray = findViewById(R.id.linearRadioGroupLightGray);
        TouchUpListener touchUpListener = new TouchUpListener() {
            @Override
            public void touchUp(TView t) {
                Toast.makeText(TViewActivity.this, ((TView) t).getTextValue(), Toast.LENGTH_SHORT).show();
            }
        };

        //String[] stringArray, int index(下标默认0), TouchUpListener touchUpListener, LinearLayout linearLayout, int widthUnit(默认dp), int width,
        //int leftStyle,int rightStyle, int horizontalStyle, int wholeStyle
        dynamic(radioGroupTitleArray,
                "枪",
                touchUpListener,
                linearRadioGroupLightGray,
                60, TypedValue.COMPLEX_UNIT_DIP,
                R.style.TView_RadioGroup_LightGray_Left,
                R.style.TView_RadioGroup_LightGray_Right,
                R.style.TView_RadioGroup_LightGray_Horizontal,
                R.style.TView_RadioGroup_LightGray_Whole);
    }

    //Defined in xml
    public void touchUp(TView t) {
        Toast.makeText(TViewActivity.this, "app:touchUp=\"touchUp\"", Toast.LENGTH_SHORT).show();
    }

    //Defined in xml
    public void onClick(TView t) {
        Toast.makeText(TViewActivity.this, "app:onClick=\"onClick\"", Toast.LENGTH_SHORT).show();
    }
}
