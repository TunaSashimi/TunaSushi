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
/**
 * @author Tunasashimi
 * @date 10/30/15 16:53
 * @Copyright 2015 Sashimi. All rights reserved.
 * @Description
 */
public class TRippleActivity extends Activity {

    private static final int PLAY_TUNARIPPLE_ANIMATION = 0;

    private Button buttonPlay, buttonChange;
    private TRipple tRipple01, tRipple02, tRipple03, tRipple04;

    private Timer timer;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case PLAY_TUNARIPPLE_ANIMATION:
                    tRipple01.play();
                    tRipple02.play();
                    tRipple03.play();
                    tRipple04.play();
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

        tRipple01 = findViewById(R.id.tRipple01);
        tRipple02 = findViewById(R.id.tRipple02);
        tRipple03 = findViewById(R.id.tRipple03);
        tRipple04 = findViewById(R.id.tRipple04);

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

                if ("^".equals(tRipple01.getRippleTextValue())) {

                    tRipple01.setRippleTextValue("&");
                    tRipple02.setRippleTextValue("&");
                    tRipple03.setRippleTextValue("&");
                    tRipple04.setRippleTextValue("&");

                    tRipple01.setRippleInnerCircleColor(Color.parseColor("#ff9900"));
                    tRipple02.setRippleOuterCircleColor(270, Color.parseColor("#fed136"), Color.parseColor("#ff9900"));
                    tRipple03.setRippleInnerCircleColor(Color.parseColor("#fed136"));
                    tRipple03.setRippleOuterCircleColor(Color.parseColor("#fed136"));
                    tRipple04.setRippleInnerCircleAngle(90);
                    tRipple04.setRippleOuterCircleAngle(90);
                } else {

                    tRipple01.setRippleTextValue("^");
                    tRipple02.setRippleTextValue("^");
                    tRipple03.setRippleTextValue("^");
                    tRipple04.setRippleTextValue("^");

                    tRipple01.setRippleInnerCircleColor(Color.parseColor("#fed136"));
                    tRipple02.setRippleOuterCircleColor(Color.parseColor("#ff9900"));
                    tRipple03.setRippleInnerCircleColor(Color.WHITE, Color.BLUE);
                    tRipple03.setRippleOuterCircleColor(Color.WHITE, Color.BLUE);
                    tRipple04.setRippleInnerCircleColor(270, Color.WHITE, Color.BLUE);
                    tRipple04.setRippleOuterCircleColor(270, Color.RED, Color.BLUE);
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
