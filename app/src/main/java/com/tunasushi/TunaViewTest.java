package com.tunasushi;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tuna.TunaView;
import com.tuna.TunaView.DeviceInfo;
import com.tuna.TunaView.TunaTouchUpListener;

import java.util.Arrays;

public class TunaViewTest extends Activity {

    private TunaView

        tunaRectClassic01, tunaMainButton01, tunaMainButton02,
        tunaView_RadioGroup_DrackBrown_Left, tunaView_RadioGroup_DrackBrown_Right;

    private TunaTouchUpListener tunaTouchUpListener = new TunaTouchUpListener() {
        @Override
        public void tunaTouchUp(View v) {
            switch (v.getId()) {
                case R.id.tunaView_RadioGroup_DrackBrown_Left:
                    Toast.makeText(TunaViewTest.this, "金枪鱼刺身", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.tunaView_RadioGroup_DrackBrown_Right:
                    Toast.makeText(TunaViewTest.this, "TunaSashimi", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.tunaviewtest);

        //
        DeviceInfo deviceInfo = TunaView.getDeviceInfo(this);
        TextView textDeviceInfo = findViewById(R.id.textDeviceInfo);
        textDeviceInfo.setText(deviceInfo.toString());

        //
        tunaRectClassic01 = findViewById(R.id.tunaRectClassic01);
        tunaRectClassic01.setTunaTouchUpListener(new TunaTouchUpListener() {
            @Override
            public void tunaTouchUp(View v) {
                Toast.makeText(TunaViewTest.this, "TunaTouchUp", Toast.LENGTH_SHORT).show();
            }
        });

        //
        tunaMainButton01 = findViewById(R.id.tunaShadowButton01);
        tunaMainButton01.setTunaTouchUpListener(new TunaTouchUpListener() {
            @Override
            public void tunaTouchUp(View v) {
                tunaMainButton01.setTunaTextMark(
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
        tunaMainButton02 = findViewById(R.id.tunaShadowButton02);
        tunaMainButton02.setTunaTextMark(
            10,
            Color.RED,
            "10",
            10,
            Color.WHITE,
            0,
            0
        );
        tunaMainButton02.setTunaTouchUpListener(new TunaTouchUpListener() {
            @Override
            public void tunaTouchUp(View v) {
                tunaMainButton02.setTunaTextMark(
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
        tunaView_RadioGroup_DrackBrown_Left = findViewById(R.id.tunaView_RadioGroup_DrackBrown_Left);
        tunaView_RadioGroup_DrackBrown_Right = findViewById(R.id.tunaView_RadioGroup_DrackBrown_Right);

        tunaView_RadioGroup_DrackBrown_Left.setTunaTouchUpListener(tunaTouchUpListener);
        tunaView_RadioGroup_DrackBrown_Right.setTunaTouchUpListener(tunaTouchUpListener);

        //if you want a different nemesisview link, you can put an array of incoming associate methods

        //	TunaView.tunaAssociate(new TunaView[]{tunaView_RadioGroup_DrackBrown_Left, tunaView_RadioGroup_DrackBrown_Right});

        //or  can be placed on a list of incoming associate method
        TunaView.tunaAssociate(Arrays.asList(tunaView_RadioGroup_DrackBrown_Left, tunaView_RadioGroup_DrackBrown_Right));

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
                Toast.makeText(TunaViewTest.this, ((TunaView) v).getTunaTextValue(), Toast.LENGTH_SHORT).show();
            }
        };

        //Activity activity, String[] titleArray, int index(下标默认0), TunaTouchUpListener tunaTouchUpListener, LinearLayout linearLayout, int widthUnit(默认dp), int width,
        //int leftStyle,int rightStyle, int horizontalStyle, int wholeStyle
        TunaView.tunaDynamic(radioGroupTitleArray, "枪", tunaTouchUpListener, linearRadioGroupLightGray, TypedValue.COMPLEX_UNIT_DIP, 60,
            R.style.TunaView_RadioGroup_LightGray_Left,
            R.style.TunaView_RadioGroup_LightGray_Right,
            R.style.TunaView_RadioGroup_LightGray_Horizontal,
            R.style.TunaView_RadioGroup_LightGray_Whole);
    }
}
