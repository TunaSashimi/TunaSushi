package com.tunasushi.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Toast;

import com.tunasushi.R;
import com.tunasushi.tuna.TButton;
import com.tunasushi.tuna.TView;
import com.tunasushi.tuna.TLine;

import static com.tunasushi.tool.ConvertTool.dpToPx;

/**
 * @author TunaSashimi
 * @date 2015-10-30 16:53
 * @Copyright 2015 TunaSashimi. All rights reserved.
 * @Description
 */
public class TLineActivity extends Activity implements TView.OnClickListener {
    private TLine tLineAC, tLineMove;
    private TView tViewCenter, tViewHidden;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_t_line);

        tLineAC = findViewById(R.id.tLineAC);
        tLineMove = findViewById(R.id.tLineMove);

        tViewCenter = findViewById(R.id.tViewCenter);
        tViewHidden = findViewById(R.id.tViewHidden);

        //
        tLineAC.setLineX(dpToPx(120));
        tLineMove.setLineX(dpToPx(120));
        //
        tViewCenter.setOnClickListener(this);
        tViewHidden.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tViewCenter:
                tLineAC.centerArrow();
                break;
            case R.id.tViewHidden:
                tLineAC.hideArrow();
                break;
            default:
                break;
        }
    }
}
