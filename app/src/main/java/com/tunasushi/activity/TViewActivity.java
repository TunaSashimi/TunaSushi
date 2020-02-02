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

public class TViewActivity extends Activity {
    private TView TViewRectClassic01;
    private TView TViewMainButton01, TViewMainButton02;
    private TView TViewRadioGroup_DrackBrown_Left, TViewRadioGroup_DrackBrown_Right;

    private TouchUpListener tunaTouchUpListener = new TouchUpListener() {
        @Override
        public void touchUp(View v) {
            switch (v.getId()) {
                case R.id.radioGroup_DrackBrown_Left:
                    Toast.makeText(TViewActivity.this, "金枪鱼刺身", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.radioGroup_DrackBrown_Right:
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
        TViewRectClassic01 = findViewById(R.id.rectClassic01);
        TViewRectClassic01.setTouchUpListener(new TouchUpListener() {
            @Override
            public void touchUp(View v) {
                Toast.makeText(TViewActivity.this, "TouchUp", Toast.LENGTH_SHORT).show();
            }
        });

        TViewRectClassic01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TViewActivity.this, "OnClickListener", Toast.LENGTH_SHORT).show();
            }
        });

        //
        TViewMainButton01 = findViewById(R.id.shadowButton01);
        TViewMainButton01.setTouchUpListener(new TouchUpListener() {
            @Override
            public void touchUp(View v) {
                TViewMainButton01.setTextMark(
                        4,
                        Color.RED,
                        null,
                        0,
                        0,
                        0,
                        0
                );
            }
        });

        //
        TViewMainButton02 = findViewById(R.id.shadowButton02);
        TViewMainButton02.setTextMark(
                10,
                Color.RED,
                "10",
                10,
                Color.WHITE,
                0,
                0
        );
        TViewMainButton02.setTouchUpListener(new TouchUpListener() {
            @Override
            public void touchUp(View v) {
                TViewMainButton02.setTextMark(
                        4,
                        Color.RED,
                        null,
                        0,
                        0,
                        0,
                        0
                );
            }
        });

        //
        TViewRadioGroup_DrackBrown_Left = findViewById(R.id.radioGroup_DrackBrown_Left);
        TViewRadioGroup_DrackBrown_Right = findViewById(R.id.radioGroup_DrackBrown_Right);

        TViewRadioGroup_DrackBrown_Left.setTouchUpListener(tunaTouchUpListener);
        TViewRadioGroup_DrackBrown_Right.setTouchUpListener(tunaTouchUpListener);

        //if you want a different TView link, you can put an array of incoming associate methods

        //	TView.associate(new TView[]{TViewRadioGroup_DrackBrown_Left, TViewRadioGroup_DrackBrown_Right});

        //or  can be placed on a list of incoming associate method
        TView.associate(Arrays.asList(TViewRadioGroup_DrackBrown_Left, TViewRadioGroup_DrackBrown_Right));

        //
        String radioGroupTitleArray[] = {
                "金",
                "枪",
                "鱼",
                "刺",
                "身"
        };
        LinearLayout linearRadioGroupLightGray = findViewById(R.id.linearRadioGroupLightGray);
        TouchUpListener tunaTouchUpListener = new TouchUpListener() {
            @Override
            public void touchUp(View v) {
                Toast.makeText(TViewActivity.this, ((TView) v).getTextValue(), Toast.LENGTH_SHORT).show();
            }
        };

        //Activity activity, String[] titleArray, int index(下标默认0), TouchUpListener tunaTouchUpListener, LinearLayout linearLayout, int widthUnit(默认dp), int width,
        //int leftStyle,int rightStyle, int horizontalStyle, int wholeStyle
        TView.dynamic(radioGroupTitleArray, "枪", tunaTouchUpListener, linearRadioGroupLightGray, TypedValue.COMPLEX_UNIT_DIP, 60,
                R.style.TView_RadioGroup_LightGray_Left,
                R.style.TView_RadioGroup_LightGray_Right,
                R.style.TView_RadioGroup_LightGray_Horizontal,
                R.style.TView_RadioGroup_LightGray_Whole);
    }
}
