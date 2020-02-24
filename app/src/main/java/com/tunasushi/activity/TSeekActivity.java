package com.tunasushi.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.tunasushi.R;
import com.tunasushi.tuna.TView;
import com.tunasushi.tuna.TSeek;

/**
 * @author TunaSashimi
 * @date 2015-10-30 16:53
 * @Copyright 2015 TunaSashimi. All rights reserved.
 * @Description
 */
public class TSeekActivity extends Activity {
    private TSeek tSeek;
    private TView tViewReset, tViewIndex;

    private TView.TouchUpListener touchUpListener = new TView.TouchUpListener() {
        @Override
        public void touchUp(TView t) {
            switch (t.getId()) {
                case R.id.tViewReset:
                    tSeek.setSeekIndex(0);
                    break;
                case R.id.tViewIndex:
                    Toast.makeText(TSeekActivity.this, "TSeek下标==>" + tSeek.getSeekIndex(), Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_t_seek);

        tSeek = findViewById(R.id.tSeek);

        tViewReset = findViewById(R.id.tViewReset);
        tViewIndex = findViewById(R.id.tViewIndex);

        tViewReset.setTouchUpListener(touchUpListener);
        tViewIndex.setTouchUpListener(touchUpListener);
    }
}
