package com.tunasushi.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Toast;

import com.tunasushi.R;
import com.tunasushi.tuna.TView;
import com.tunasushi.tuna.TDrag;

public class TDragActivity extends Activity {
    private TDrag tunaDragTouchoutable, tunaDragTouchoutUnable;
    private TView tunaViewTouchoutableReset, tunaViewTouchoutableGetIndex,
        tunaViewTouchoutUnableReset, tunaViewTouchoutUnableGetIndex;

    private TView.TunaTouchUpListener tunaTouchUpListener = new TView.TunaTouchUpListener() {
        @Override
        public void tunaTouchUp(View v) {
            switch (v.getId()) {
                case R.id.tViewTouchoutableReset:
                    tunaDragTouchoutable.setTunaCurrentIndex(0);
                    break;
                case R.id.tViewTouchoutableGetIndex:
                    Toast.makeText(TDragActivity.this, "tunaDragTouchoutable下标为" + tunaDragTouchoutable.getTunaCurrentIndex(), Toast.LENGTH_SHORT).show();
                    break;
                case R.id.tViewTouchoutUnableReset:
                    tunaDragTouchoutUnable.setTunaCurrentIndex(0);
                    break;
                case R.id.tViewTouchoutUnableGetIndex:
                    Toast.makeText(TDragActivity.this, "tunaDragTouchoutUnable下标为" + tunaDragTouchoutUnable.getTunaCurrentIndex(), Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_t_drag);

        tunaDragTouchoutable = findViewById(R.id.tDragTouchoutable);
        tunaDragTouchoutUnable = findViewById(R.id.tDragTouchoutUnable);

        tunaViewTouchoutableReset = findViewById(R.id.tViewTouchoutableReset);
        tunaViewTouchoutableGetIndex = findViewById(R.id.tViewTouchoutableGetIndex);

        tunaViewTouchoutUnableReset = findViewById(R.id.tViewTouchoutUnableReset);
        tunaViewTouchoutUnableGetIndex = findViewById(R.id.tViewTouchoutUnableGetIndex);

        tunaDragTouchoutable.setTunaTouchListener(new TView.TunaTouchListener() {
            @Override
            public void tunaTouch(View v) {
                tunaDragTouchoutable.setTunaDragCurrentX(TypedValue.COMPLEX_UNIT_PX, tunaDragTouchoutable.getTunaTouchEventX());
            }
        });

        tunaDragTouchoutUnable.setTunaTouchListener(new TView.TunaTouchListener() {
            @Override
            public void tunaTouch(View v) {
                tunaDragTouchoutUnable.setTunaDragCurrentX(TypedValue.COMPLEX_UNIT_PX, tunaDragTouchoutUnable.getTunaTouchEventX());
            }
        });

        tunaViewTouchoutableReset.setTunaTouchUpListener(tunaTouchUpListener);
        tunaViewTouchoutableGetIndex.setTunaTouchUpListener(tunaTouchUpListener);

        tunaViewTouchoutUnableReset.setTunaTouchUpListener(tunaTouchUpListener);
        tunaViewTouchoutUnableGetIndex.setTunaTouchUpListener(tunaTouchUpListener);
    }
}
