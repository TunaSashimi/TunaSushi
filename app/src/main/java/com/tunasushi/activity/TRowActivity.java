package com.tunasushi.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.tunasushi.R;
import com.tunasushi.tuna.TRow;


public class TRowActivity extends Activity {
    private final static int TUNAROW_PLAY = 0;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case TUNAROW_PLAY:
                    tunaRow.play();
                    break;
                default:
                    break;
            }
        }
    };
    private TRow tunaRow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_t_row);

        tunaRow = findViewById(R.id.tRow);
        handler.sendEmptyMessageDelayed(TUNAROW_PLAY, 1000);
    }
}
