package com.tunasushi.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.tunasushi.R;
import com.tunasushi.tuna.TRipple;
import com.tunasushi.tuna.TView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author TunaSashimi
 * @date 2015-10-30 16:53
 * @Copyright 2015 TunaSashimi. All rights reserved.
 * @Description
 */
public class TRippleActivity extends Activity {
    private static final int PLAY_TUNARIPPLE_ANIMATION = 0;

    private TView tViewPlay, tViewChange;
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

        tViewPlay = findViewById(R.id.tViewPlay);
        tViewPlay.setOnClickListener(new TView.OnClickListener() {
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

        tViewChange = findViewById(R.id.tViewChange);
        tViewChange.setOnClickListener(new TView.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("^".equals(tRipple01.getRippleTextValue())) {
                    tRipple01.setRippleTextValue("&");
                    tRipple02.setRippleTextValue("&");
                    tRipple03.setRippleTextValue("&");
                    tRipple04.setRippleTextValue("&");

                    tRipple01.setRippleCircleColorInner(0xffff9900);
                    tRipple02.setRippleCircleColorOuter(270, 0xfffed136, 0xffff9900);
                    tRipple03.setRippleCircleColorInner(0xfffed136);
                    tRipple03.setRippleCircleColorOuter(0xfffed136);
                    tRipple04.setRippleCircleAngleInner(90);
                    tRipple04.setRippleCircleAngleOuter(90);
                } else {

                    tRipple01.setRippleTextValue("^");
                    tRipple02.setRippleTextValue("^");
                    tRipple03.setRippleTextValue("^");
                    tRipple04.setRippleTextValue("^");

                    tRipple01.setRippleCircleColorInner(0xfffed136);
                    tRipple02.setRippleCircleColorOuter(0xffff9900);
                    tRipple03.setRippleCircleColorInner(Color.WHITE, Color.BLUE);
                    tRipple03.setRippleCircleColorOuter(Color.WHITE, Color.BLUE);
                    tRipple04.setRippleCircleColorInner(270, Color.WHITE, Color.BLUE);
                    tRipple04.setRippleCircleColorOuter(270, Color.RED, Color.BLUE);
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
