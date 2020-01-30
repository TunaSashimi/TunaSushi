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
import com.tunasushi.tuna.TView.TunaTouchUpListener;

import java.util.Arrays;

public class TViewActivity extends Activity {
    private TView TV_RectClassic01;
    private TView TV_MainButton01, TV_MainButton02;
    private TView TV_RadioGroup_DrackBrown_Left, TV_RadioGroup_DrackBrown_Right;

    private TunaTouchUpListener tunaTouchUpListener = new TunaTouchUpListener() {
        @Override
        public void tunaTouchUp(View v) {
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

        setContentView(R.layout.activity_tview);

        //
        DeviceTool.DeviceInfo deviceInfo = DeviceTool.getDeviceInfo(this);
        TextView textDeviceInfo = findViewById(R.id.textDeviceInfo);
        textDeviceInfo.setText(deviceInfo.toString());

        //
        TV_RectClassic01 = findViewById(R.id.rectClassic01);
        TV_RectClassic01.setTunaTouchUpListener(new TunaTouchUpListener() {
            @Override
            public void tunaTouchUp(View v) {
                Toast.makeText(TViewActivity.this, "TunaTouchUp", Toast.LENGTH_SHORT).show();
            }
        });

        TV_RectClassic01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TViewActivity.this, "OnClickListener", Toast.LENGTH_SHORT).show();
            }
        });

        //
        TV_MainButton01 = findViewById(R.id.shadowButton01);
        TV_MainButton01.setTunaTouchUpListener(new TunaTouchUpListener() {
            @Override
            public void tunaTouchUp(View v) {
                TV_MainButton01.setTunaTextMark(
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
        TV_MainButton02 = findViewById(R.id.shadowButton02);
        TV_MainButton02.setTunaTextMark(
                10,
                Color.RED,
                "10",
                10,
                Color.WHITE,
                0,
                0
        );
        TV_MainButton02.setTunaTouchUpListener(new TunaTouchUpListener() {
            @Override
            public void tunaTouchUp(View v) {
                TV_MainButton02.setTunaTextMark(
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
        TV_RadioGroup_DrackBrown_Left = findViewById(R.id.radioGroup_DrackBrown_Left);
        TV_RadioGroup_DrackBrown_Right = findViewById(R.id.radioGroup_DrackBrown_Right);

        TV_RadioGroup_DrackBrown_Left.setTunaTouchUpListener(tunaTouchUpListener);
        TV_RadioGroup_DrackBrown_Right.setTunaTouchUpListener(tunaTouchUpListener);

        //if you want a different TView link, you can put an array of incoming associate methods

        //	TView.tunaAssociate(new TView[]{TV_RadioGroup_DrackBrown_Left, TV_RadioGroup_DrackBrown_Right});

        //or  can be placed on a list of incoming associate method
        TView.tunaAssociate(Arrays.asList(TV_RadioGroup_DrackBrown_Left, TV_RadioGroup_DrackBrown_Right));

        //
        String radioGroupTitleArray[] = {
                "金",
                "枪",
                "鱼",
                "刺",
                "身"
        };
        LinearLayout linearRadioGroupLightGray = findViewById(R.id.linearRadioGroupLightGray);
        TunaTouchUpListener tunaTouchUpListener = new TunaTouchUpListener() {
            @Override
            public void tunaTouchUp(View v) {
                Toast.makeText(TViewActivity.this, ((TView) v).getTunaTextValue(), Toast.LENGTH_SHORT).show();
            }
        };

        //Activity activity, String[] titleArray, int index(下标默认0), TunaTouchUpListener tunaTouchUpListener, LinearLayout linearLayout, int widthUnit(默认dp), int width,
        //int leftStyle,int rightStyle, int horizontalStyle, int wholeStyle
        TView.tunaDynamic(radioGroupTitleArray, "枪", tunaTouchUpListener, linearRadioGroupLightGray, TypedValue.COMPLEX_UNIT_DIP, 60,
                R.style.TView_RadioGroup_LightGray_Left,
                R.style.TView_RadioGroup_LightGray_Right,
                R.style.TView_RadioGroup_LightGray_Horizontal,
                R.style.TView_RadioGroup_LightGray_Whole);
    }
}
