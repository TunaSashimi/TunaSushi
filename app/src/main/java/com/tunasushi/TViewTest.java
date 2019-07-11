package com.tunasushi;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tuna.TView;
import com.tuna.TView.DeviceInfo;
import com.tuna.TView.TunaTouchUpListener;

import java.util.Arrays;

public class TViewTest extends Activity {

    private TView

        TVRectClassic01, TVMainButton01, TVMainButton02,
        TV_RadioGroup_DrackBrown_Left, TV_RadioGroup_DrackBrown_Right;

    private TunaTouchUpListener tunaTouchUpListener = new TunaTouchUpListener() {
        @Override
        public void tunaTouchUp(View v) {
            switch (v.getId()) {
                case R.id.radioGroup_DrackBrown_Left:
                    Toast.makeText(TViewTest.this, "金枪鱼刺身", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.radioGroup_DrackBrown_Right:
                    Toast.makeText(TViewTest.this, "TunaSashimi", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.tviewtest);

        //
        DeviceInfo deviceInfo = TView.getDeviceInfo(this);
        TextView textDeviceInfo = findViewById(R.id.textDeviceInfo);
        textDeviceInfo.setText(deviceInfo.toString());

        //
        TVRectClassic01 = findViewById(R.id.rectClassic01);
        TVRectClassic01.setTunaTouchUpListener(new TunaTouchUpListener() {
            @Override
            public void tunaTouchUp(View v) {
                Toast.makeText(TViewTest.this, "TunaTouchUp", Toast.LENGTH_SHORT).show();
            }
        });

        //
        TVMainButton01 = findViewById(R.id.shadowButton01);
        TVMainButton01.setTunaTouchUpListener(new TunaTouchUpListener() {
            @Override
            public void tunaTouchUp(View v) {
                TVMainButton01.setTunaTextMark(
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
        TVMainButton02 = findViewById(R.id.shadowButton02);
        TVMainButton02.setTunaTextMark(
            10,
            Color.RED,
            "10",
            10,
            Color.WHITE,
            0,
            0
        );
        TVMainButton02.setTunaTouchUpListener(new TunaTouchUpListener() {
            @Override
            public void tunaTouchUp(View v) {
                TVMainButton02.setTunaTextMark(
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
                Toast.makeText(TViewTest.this, ((TView) v).getTunaTextValue(), Toast.LENGTH_SHORT).show();
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
