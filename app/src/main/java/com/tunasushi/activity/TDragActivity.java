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
    private TDrag tDragTouchoutable, tDragTouchoutUnable;
    private TView tViewTouchoutableReset, tViewTouchoutableGetIndex,
            tViewTouchoutUnableReset, tViewTouchoutUnableGetIndex;

    private TView.TouchUpListener tunaTouchUpListener = new TView.TouchUpListener() {
        @Override
        public void touchUp(View v) {
            switch (v.getId()) {
                case R.id.tViewTouchoutableReset:
                    tDragTouchoutable.setDragCurrentIndex(0);
                    break;
                case R.id.tViewTouchoutableGetIndex:
                    Toast.makeText(TDragActivity.this, "tunaDragTouchoutable下标为" + tDragTouchoutable.getDragCurrentIndex(), Toast.LENGTH_SHORT).show();
                    break;
                case R.id.tViewTouchoutUnableReset:
                    tDragTouchoutUnable.setDragCurrentIndex(0);
                    break;
                case R.id.tViewTouchoutUnableGetIndex:
                    Toast.makeText(TDragActivity.this, "tunaDragTouchoutUnable下标为" + tDragTouchoutUnable.getDragCurrentIndex(), Toast.LENGTH_SHORT).show();
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

        tDragTouchoutable = findViewById(R.id.tDragTouchoutable);
        tDragTouchoutUnable = findViewById(R.id.tDragTouchoutUnable);

        tViewTouchoutableReset = findViewById(R.id.tViewTouchoutableReset);
        tViewTouchoutableGetIndex = findViewById(R.id.tViewTouchoutableGetIndex);

        tViewTouchoutUnableReset = findViewById(R.id.tViewTouchoutUnableReset);
        tViewTouchoutUnableGetIndex = findViewById(R.id.tViewTouchoutUnableGetIndex);

        tDragTouchoutable.setTouchListener(new TView.TouchListener() {
            @Override
            public void touch(View v) {
                tDragTouchoutable.setDragCurrentX(TypedValue.COMPLEX_UNIT_PX, tDragTouchoutable.getTouchEventX());
            }
        });

        tDragTouchoutUnable.setTouchListener(new TView.TouchListener() {
            @Override
            public void touch(View v) {
                tDragTouchoutUnable.setDragCurrentX(TypedValue.COMPLEX_UNIT_PX, tDragTouchoutUnable.getTouchEventX());
            }
        });

        tViewTouchoutableReset.setTouchUpListener(tunaTouchUpListener);
        tViewTouchoutableGetIndex.setTouchUpListener(tunaTouchUpListener);

        tViewTouchoutUnableReset.setTouchUpListener(tunaTouchUpListener);
        tViewTouchoutUnableGetIndex.setTouchUpListener(tunaTouchUpListener);
    }
}
