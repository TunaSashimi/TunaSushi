package com.tunasushi.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.Toast;

import com.tunasushi.R;
import com.tunasushi.tuna.TView;
import com.tunasushi.tuna.TSeek;

public class TSeekActivity extends Activity {
    private TSeek tSeekTouchoutable, tSeekTouchoutUnable;
    private TView tViewTouchoutableReset, tViewTouchoutableGetIndex,
            tViewTouchoutUnableReset, tViewTouchoutUnableGetIndex;

    private TView.TouchUpListener tunaTouchUpListener = new TView.TouchUpListener() {
        @Override
        public void touchUp(TView t) {
            switch (t.getId()) {
                case R.id.tViewTouchoutableReset:
                    tSeekTouchoutable.setSeekCurrentIndex(0);
                    break;
                case R.id.tViewTouchoutableGetIndex:
                    Toast.makeText(TSeekActivity.this, "TSeekTouchoutable下标为" + tSeekTouchoutable.getSeekCurrentIndex(), Toast.LENGTH_SHORT).show();
                    break;
                case R.id.tViewTouchoutUnableReset:
                    tSeekTouchoutUnable.setSeekCurrentIndex(0);
                    break;
                case R.id.tViewTouchoutUnableGetIndex:
                    Toast.makeText(TSeekActivity.this, "TSeekTouchoutUnable下标为" + tSeekTouchoutUnable.getSeekCurrentIndex(), Toast.LENGTH_SHORT).show();
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

        tSeekTouchoutable = findViewById(R.id.tSeekTouchoutable);
        tSeekTouchoutUnable = findViewById(R.id.tSeekTouchoutUnable);

        tViewTouchoutableReset = findViewById(R.id.tViewTouchoutableReset);
        tViewTouchoutableGetIndex = findViewById(R.id.tViewTouchoutableGetIndex);

        tViewTouchoutUnableReset = findViewById(R.id.tViewTouchoutUnableReset);
        tViewTouchoutUnableGetIndex = findViewById(R.id.tViewTouchoutUnableGetIndex);

        tSeekTouchoutable.setTouchListener(new TView.TouchListener() {
            @Override
            public void touch(TView t) {
                tSeekTouchoutable.setSeekCurrentX(TypedValue.COMPLEX_UNIT_PX, t.getTouchEventX());
            }
        });

        tSeekTouchoutUnable.setTouchListener(new TView.TouchListener() {
            @Override
            public void touch(TView t) {
                tSeekTouchoutUnable.setSeekCurrentX(TypedValue.COMPLEX_UNIT_PX, t.getTouchEventX());
            }
        });

        tViewTouchoutableReset.setTouchUpListener(tunaTouchUpListener);
        tViewTouchoutableGetIndex.setTouchUpListener(tunaTouchUpListener);

        tViewTouchoutUnableReset.setTouchUpListener(tunaTouchUpListener);
        tViewTouchoutUnableGetIndex.setTouchUpListener(tunaTouchUpListener);
    }
}
