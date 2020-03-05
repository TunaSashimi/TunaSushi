package com.tunasushi.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.tunasushi.R;
import com.tunasushi.tuna.TLoad;

/**
 * @author TunaSashimi
 * @date 2015-10-30 16:53
 * @Copyright 2015 TunaSashimi. All rights reserved.
 * @Description
 */

public class TLoadActivity extends Activity implements View.OnClickListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_t_load);
        if (Build.VERSION.SDK_INT >= 23) {
            if (!Settings.canDrawOverlays(TLoadActivity.this)) {
                Toast.makeText(this, "当前无权限，请授权", Toast.LENGTH_SHORT);
                startActivityForResult(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName())), 10);
            }
        }

        findViewById(R.id.btnDecor).setOnClickListener(this);
        findViewById(R.id.btnFloat).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDecor:
                TLoad tLoad = new TLoad(TLoadActivity.this);
                FrameLayout frameLayout = (FrameLayout) getWindow().getDecorView();
                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(200, 200);
                layoutParams.gravity = Gravity.CENTER;
                frameLayout.addView(tLoad, layoutParams);
                break;
            case R.id.btnFloat:
                WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
                TLoad tLoadW = new TLoad(TLoadActivity.this);
                tLoadW.setBackgroundColor(Color.WHITE);
                WindowManager.LayoutParams layoutParamsWindow = new WindowManager.LayoutParams();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    layoutParamsWindow.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
                } else {
                    layoutParamsWindow.type = WindowManager.LayoutParams.TYPE_PHONE;
                }
                layoutParamsWindow.width = 200;
                layoutParamsWindow.height = 200;
                windowManager.addView(tLoadW, layoutParamsWindow);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 10) {
            if (Build.VERSION.SDK_INT >= 23) {
                if (!Settings.canDrawOverlays(this)) {
                    Toast.makeText(TLoadActivity.this, "not granted", Toast.LENGTH_SHORT);
                }
            }
        }
    }
}