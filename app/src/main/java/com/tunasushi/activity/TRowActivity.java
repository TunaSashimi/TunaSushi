package com.tunasushi.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.tunasushi.R;
import com.tunasushi.tuna.TRow;

/**
 * @author TunaSashimi
 * @date 2015-10-30 16:53
 * @Copyright 2015 TunaSashimi. All rights reserved.
 * @Description
 */
public class TRowActivity extends Activity {
    private TRow tRow;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_t_row);

        tRow = findViewById(R.id.tRow);
        handler.sendEmptyMessageDelayed(TUNAROW_PLAY, 1000);
    }
}
