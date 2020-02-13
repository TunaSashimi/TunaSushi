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
 * @author Tunasashimi
 * @date 10/30/15 16:53
 * @Copyright 2015 Sashimi. All rights reserved.
 * @Description
 */
public class TRangeActivity extends Activity {
    private TBubble tBubble;
    private TRange rRange;
    int dx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_t_range);

        rRange = findViewById(R.id.tRange);
        tBubble = findViewById(R.id.tBubble);


        rRange.setTouchListener(new TView.TouchListener() {
            @Override
            public void touch(TView t) {
                //
                t.setX(t.getTouchEventX());
                //
                tBubble.setTBubbleTextValue(rRange.getRangeValue());
                //
                if (dx == 0) {
                    dx = tBubble.getWidth() >> 1;
                }
                //
                if (t.isPress()) {
                    tBubble.setVisibility(View.VISIBLE);
                    setViewMargins(tBubble, (int) rRange.getRangeCircleCentreX() - dx, dpToPx(16), 0, 0);
                } else {
                    tBubble.setVisibility(View.INVISIBLE);
                }
            }
        });
    }
}
