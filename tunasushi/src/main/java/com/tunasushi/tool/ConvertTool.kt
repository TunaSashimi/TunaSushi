package com.tunasushi.tool

import android.content.res.Resources
import com.tunasushi.tool.DeviceTool
import android.util.DisplayMetrics
import android.util.TypedValue

/**
 * @author TunaSashimi
 * @date 2019-11-19 15:25
 * @Copyright 2019 TunaSashimi. All rights reserved.
 * @Description
 */
object ConvertTool {
    // Parameter need to float for example tunaStroke is float convert the value to dip or dp px values​​, to ensure constant size
    @JvmStatic
    fun pxToDp(pxValue: Float): Float {
        if (DeviceTool.displayDensity == 0f) {
            DeviceTool.initDisplayMetrics()
        }
        return pxValue / DeviceTool.displayDensity
    }

    // Parameter need to float for example tunaStroke is float convert the value to dip or dp px values​​, to ensure constant size
    fun pxToSp(pxValue: Float): Float {
        if (DeviceTool.displayDensity == 0f) {
            DeviceTool.initDisplayMetrics()
        }
        return pxValue / DeviceTool.displayScaledDensity
    }

    // convert the value px sp values​​, to ensure constant size
    fun spToPx(spValue: Float): Int {
        if (DeviceTool.displayDensity == 0f) {
            DeviceTool.initDisplayMetrics()
        }
        return (spValue * DeviceTool.displayScaledDensity + 0.5f).toInt()
    }

    // convert the value to px dp values​​, to ensure constant size
    @JvmStatic
    fun dpToPx(dpValue: Float): Int {
        if (DeviceTool.displayDensity == 0f) {
            DeviceTool.initDisplayMetrics()
        }
        return (dpValue * DeviceTool.displayDensity + 0.5f).toInt()
    }

    @JvmStatic
    fun convertToPX(value: Float, unit: Int): Float {
        val metrics = Resources.getSystem().displayMetrics
        when (unit) {
            TypedValue.COMPLEX_UNIT_PX -> return value
            TypedValue.COMPLEX_UNIT_DIP -> return value * metrics.density
            TypedValue.COMPLEX_UNIT_SP -> return value * metrics.scaledDensity
            TypedValue.COMPLEX_UNIT_PT -> return value * metrics.xdpi * (1.0f / 72)
            TypedValue.COMPLEX_UNIT_IN -> return value * metrics.xdpi
            TypedValue.COMPLEX_UNIT_MM -> return value * metrics.xdpi * (1.0f / 25.4f)
        }
        return 0F
    }
}