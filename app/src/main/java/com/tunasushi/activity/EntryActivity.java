package com.tunasushi.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.tunasushi.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author TunaSashimi
 * @date 2020-02-09 18:45
 * @Copyright 2020 TunaSashimi. All rights reserved.
 * @Description
 */
public class EntryActivity extends Activity {
    private Class<?>[] c = {

            TViewActivity.class,

            //
            TAnalysisActivity.class,
            TArcActivity.class,

            TBezierActivity.class,
            TBubbleActivity.class,
            TButtonActivity.class,

            TDialogActivity.class,
            TDrawActivity.class,

            THollowActivity.class,

            TImageActivity.class,
            TIrregularActivity.class,

            TLayoutActivity.class,
            TLineActivity.class,

            TMaterialActivity.class,
            TMoveActivity.class,

            TPathActivity.class,
            TPatternActivity.class,
            TPentagramActivity.class,
            TProgressActivity.class,

            TRangeActivity.class,
            TRepeatActivity.class,
            TRippleActivity.class,
            TRowActivity.class,

            TScaleActivity.class,
            TScratchActivity.class,
            TSectorActivity.class,
            TSeekActivity.class,
            TSignActivity.class,
            TSVGActivity.class,

            TTrackActivity.class,
            TTrangleActivity.class,

            TWaveActivity.class,
            TWrapActivity.class,
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        ListView lv = findViewById(R.id.listview);
        List<String> list = new ArrayList<String>();

        for (int i = 0; i < c.length; i++) {
            list.add(c[i].getSimpleName());
        }

        lv.setAdapter(new ArrayAdapter(this, R.layout.activity_entryitem, list));

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                startActivity(new Intent(EntryActivity.this, c[arg2]));
            }
        });
    }
}