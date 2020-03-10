package com.tunasushi.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.tunasushi.R;
import com.tunasushi.tuna.TBubble;
import com.tunasushi.tuna.TGroup;
import com.tunasushi.tuna.TRange;
import com.tunasushi.tuna.TView;

import java.util.Arrays;
import java.util.List;

import static com.tunasushi.tool.ConvertTool.dpToPx;

/**
 * @author TunaSashimi
 * @date 2020-02-13 13:50
 * @Copyright 2020 TunaSashimi. All rights reserved.
 * @Description
 */
public class TRangeActivity extends Activity implements TView.TouchUpListener {
    private TView tViewNoLimit, tViewStarTwo, tViewStarThree, tViewStarFour, tViewStarFive;
    private TView tViewPrice;
    private TBubble tBubble;
    private TRange tRange;
    private int dx;

    private List<TView> tViewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_t_range);

        tViewNoLimit = findViewById(R.id.tViewNoLimit);
        tViewStarTwo = findViewById(R.id.tViewStarTwo);
        tViewStarThree = findViewById(R.id.tViewStarThree);
        tViewStarFour = findViewById(R.id.tViewStarFour);
        tViewStarFive = findViewById(R.id.tViewStarFive);

        tViewPrice = findViewById(R.id.tViewPrice);
        tRange = findViewById(R.id.tRange);
        tBubble = findViewById(R.id.tBubble);

        //
        tViewList = Arrays.asList(tViewNoLimit, tViewStarTwo, tViewStarThree, tViewStarFour, tViewStarFive);
        TGroup.link(tViewList);

        //
        tViewPrice.setContent(tRange.getRangeTextLeft() + " - " + tRange.getRangeTextRight());

        //
        tRange.setTouchListener(new TView.TouchListener() {
            @Override
            public void touch(TView t) {
                //
                if (dx == 0) {
                    dx = tBubble.getWidth() >> 1;
                }
                tBubble.setTBubbleText(tRange.getRangeText());
                if (t.isPress()) {
                    tBubble.setVisibility(View.VISIBLE);
                    //Need to add marginLift of tRange!
                    tBubble.setX((int) tRange.getRangeCircleCentreX() - dx + dpToPx(20));
                } else {
                    tBubble.setVisibility(View.INVISIBLE);
                }
                //
                tViewPrice.setContent(tRange.getRangeTextLeft() + " - " + tRange.getRangeTextRight());
            }
        });
    }

    @Override
    public void touchUp(TView t) {
        switch (t.getId()) {
            case R.id.tViewReset:
                TGroup.reset(tViewList);
                break;
            case R.id.tViewComplete:
                Toast.makeText(this, tViewPrice.getContent() + "，" + TGroup.getIndexText(tViewList), Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
