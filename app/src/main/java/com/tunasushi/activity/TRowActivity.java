package com.tunasushi.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.tunasushi.R;
import com.tunasushi.tuna.TRow;

/**
 * @author Tunasashimi
 * @date 10/30/15 16:53
 * @Copyright 2015 Sashimi. All rights reserved.
 * @Description
 */
public class TRowActivity extends Activity {
    private final static int TUNAROW_PLAY = 0;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case TUNAROW_PLAY:
                    tRow.play();
                    break;
                default:
                    break;
            }
        }
    };
    private TRow tRow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_t_row);

        tRow = findViewById(R.id.tRow);
        handler.sendEmptyMessageDelayed(TUNAROW_PLAY, 1000);
    }
}
