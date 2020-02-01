package com.tunasushi.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


import com.tunasushi.R;
import com.tunasushi.tuna.TRipple;

import java.util.Timer;
import java.util.TimerTask;

public class TRippleActivity extends Activity {

    private static final int PLAY_TUNARIPPLE_ANIMATION = 0;

    private Button buttonPlay, buttonChange;
    private TRipple tunaRipple01, tunaRipple02, tunaRipple03, tunaRipple04;

    private Timer timer;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case PLAY_TUNARIPPLE_ANIMATION:
                    tunaRipple01.play();
                    tunaRipple02.play();
                    tunaRipple03.play();
                    tunaRipple04.play();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_t_ripple);

        tunaRipple01 = findViewById(R.id.tRipple01);
        tunaRipple02 = findViewById(R.id.tRipple02);
        tunaRipple03 = findViewById(R.id.tRipple03);
        tunaRipple04 = findViewById(R.id.tRipple04);

        buttonPlay = findViewById(R.id.buttonPlay);
        buttonPlay.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timer == null) {
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            handler.sendEmptyMessage(PLAY_TUNARIPPLE_ANIMATION);
                        }
                    }, 0, 2000);
                }
            }
        });

        buttonChange = findViewById(R.id.buttonChange);
        buttonChange.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if ("^".equals(tunaRipple01.getTunaRippleTextValue())) {

                    tunaRipple01.setTunaRippleTextValue("&");
                    tunaRipple02.setTunaRippleTextValue("&");
                    tunaRipple03.setTunaRippleTextValue("&");
                    tunaRipple04.setTunaRippleTextValue("&");

                    tunaRipple01.setTunaRippleInnerCircleColor(Color.parseColor("#ff9900"));
                    tunaRipple02.setTunaRippleOuterCircleColor(270, Color.parseColor("#fed136"), Color.parseColor("#ff9900"));
                    tunaRipple03.setTunaRippleInnerCircleColor(Color.parseColor("#fed136"));
                    tunaRipple03.setTunaRippleOuterCircleColor(Color.parseColor("#fed136"));
                    tunaRipple04.setTunaRippleInnerCircleAngle(90);
                    tunaRipple04.setTunaRippleOuterCircleAngle(90);
                } else {

                    tunaRipple01.setTunaRippleTextValue("^");
                    tunaRipple02.setTunaRippleTextValue("^");
                    tunaRipple03.setTunaRippleTextValue("^");
                    tunaRipple04.setTunaRippleTextValue("^");

                    tunaRipple01.setTunaRippleInnerCircleColor(Color.parseColor("#fed136"));
                    tunaRipple02.setTunaRippleOuterCircleColor(Color.parseColor("#ff9900"));
                    tunaRipple03.setTunaRippleInnerCircleColor(Color.WHITE, Color.BLUE);
                    tunaRipple03.setTunaRippleOuterCircleColor(Color.WHITE, Color.BLUE);
                    tunaRipple04.setTunaRippleInnerCircleColor(270, Color.WHITE, Color.BLUE);
                    tunaRipple04.setTunaRippleOuterCircleColor(270, Color.RED, Color.BLUE);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }
}
