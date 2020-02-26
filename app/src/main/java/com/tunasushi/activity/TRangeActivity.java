package com.tunasushi.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.tunasushi.R;
import com.tunasushi.tuna.TBubble;
import com.tunasushi.tuna.TRange;
import com.tunasushi.tuna.TView;

import static com.tunasushi.tool.ConvertTool.dpToPx;
import static com.tunasushi.tool.ViewTool.setViewMargins;

/**
 * @author TunaSashimi
 * @date 2020-02-13 13:50
 * @Copyright 2020 TunaSashimi. All rights reserved.
 * @Description
 */
public class TRangeActivity extends Activity {
    private TView tView;
    private TBubble tBubble;
    private TRange tRange;
    private int dx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_t_range);

        tView = findViewById(R.id.tView01);
        tRange = findViewById(R.id.tRange);
        tBubble = findViewById(R.id.tBubble);

        tRange.setTouchListener(new TView.TouchListener() {
            @Override
            public void touch(TView t) {
                //
                if (dx == 0) {
                    dx = tBubble.getWidth() >> 1;
                }
                //
                tBubble.setTBubbleTextValue(tRange.getRangeValue());
                //
                if (t.isPress()) {

                    tBubble.setVisibility(View.VISIBLE);
                    //Need to add marginLift of tRange!
                    tBubble.setX((int) tRange.getRangeCircleCentreX() - dx + dpToPx(20));
                } else {
                    tBubble.setVisibility(View.INVISIBLE);
                }
                //
                tView.setContentValue(tRange.getRangeValueLeft() + " - " + tRange.getRangeValueRight());
            }
        });
    }
}
