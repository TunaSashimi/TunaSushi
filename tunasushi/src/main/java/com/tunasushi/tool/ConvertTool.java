package com.tunasushi.tool;

import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import static com.tunasushi.tool.DeviceTool.displayDensity;
import static com.tunasushi.tool.DeviceTool.displayScaledDensity;
import static com.tunasushi.tool.DeviceTool.initDisplayMetrics;

/**
 * @author Tunasashimi
 * @date 2019-11-19 15:25
 * @Copyright 2019 Sashimi. All rights reserved.
 * @Description
 */
public class ConvertTool {
    // Parameter need to float for example tunaStroke is float convert the value to dip or dp px values​​, to ensure constant size
    public static float pxToDp(float pxValue) {
        if (displayDensity == 0f) {
            initDisplayMetrics();
        }
        return pxValue / displayDensity;
    }

    // Parameter need to float for example tunaStroke is float convert the value to dip or dp px values​​, to ensure constant size
    public static float pxToSp(float pxValue) {
        if (displayDensity == 0f) {
            initDisplayMetrics();
        }
        return pxValue / displayScaledDensity;
    }

    // convert the value px sp values​​, to ensure constant size
    public static int spToPx(float spValue) {
        if (displayDensity == 0f) {
            initDisplayMetrics();
        }
        return (int) (spValue * displayScaledDensity + 0.5f);
    }

    // convert the value to px dp values​​, to ensure constant size
    public static int dpToPx(float dpValue) {
        if (displayDensity == 0f) {
            initDisplayMetrics();
        }
        return (int) (dpValue * displayDensity + 0.5f);
    }

    public static float convertToPX(float value, int unit) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        switch (unit) {
            case TypedValue.COMPLEX_UNIT_PX:
                return value;
            case TypedValue.COMPLEX_UNIT_DIP:
                return value * metrics.density;
            case TypedValue.COMPLEX_UNIT_SP:
                return value * metrics.scaledDensity;
            case TypedValue.COMPLEX_UNIT_PT:
                return value * metrics.xdpi * (1.0f / 72);
            case TypedValue.COMPLEX_UNIT_IN:
                return value * metrics.xdpi;
            case TypedValue.COMPLEX_UNIT_MM:
                return value * metrics.xdpi * (1.0f / 25.4f);
        }
        return 0;
    }
}
