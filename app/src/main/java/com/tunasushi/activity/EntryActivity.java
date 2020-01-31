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

public class EntryActivity extends Activity {
    private Class<?>[] c = {TViewActivity.class, TTrangleViewActivity.class, TBubbleViewActivity.class, TAnalysisActivity.class,
            TTrackBallActivity.class, TButtonActivity.class, TDialogActivity.class,

    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        ListView lv = findViewById(R.id.roottt);
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