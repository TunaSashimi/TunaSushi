package com.tunasushi.tool

import com.tunasushi.tool.ConvertTool.convertToPX
import com.tunasushi.tool.ViewTool
import android.view.ViewGroup.MarginLayoutParams
import android.app.Activity
import android.content.Context
import android.graphics.LinearGradient
import android.graphics.Rect
import android.util.DisplayMetrics
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.graphics.Shader
import android.view.*
import java.lang.IndexOutOfBoundsException

/**
 * @author TunaSashimi
 * @date 2019-11-19 19:43
 * @Copyright 2019 TunaSashimi. All rights reserved.
 * @Description
 */
object ViewTool {
    //
    fun setViewMargins(view: View, left: Int, top: Int, right: Int, bottom: Int) {
        setViewMarginsRaw(view, left, top, right, bottom)
    }

    //
    fun setViewMargins(view: View, left: Int, top: Int, right: Int, bottom: Int, unit: Int) {
        setViewMarginsRaw(
            view,
            convertToPX(left.toFloat(), unit).toInt(),
            convertToPX(top.toFloat(), unit).toInt(),
            convertToPX(right.toFloat(), unit).toInt(),
            convertToPX(bottom.toFloat(), unit).toInt()
        )
    }

    //
    private fun setViewMarginsRaw(v: View, left: Int, top: Int, right: Int, bottom: Int) {
        if (v.layoutParams is MarginLayoutParams) {
            val p = v.layoutParams as MarginLayoutParams
            p.setMargins(left, top, right, bottom)
            v.requestLayout()
        }
    }

    fun getScreenWidth(activity: Activity): Int {
        val windowManager = activity.windowManager
        val display = windowManager.defaultDisplay
        return display.width
    }

    fun getScreenHeight(activity: Activity): Int {
        val windowManager = activity.windowManager
        val display = windowManager.defaultDisplay
        return display.height
    }

    fun getScreenDensity(context: Context): Float {
        var displayMetrics = DisplayMetrics()
        displayMetrics = context.resources.displayMetrics
        return displayMetrics.density
    }

    // The upper left corner of the view coordinate into an array, in hiding the status bar / title bar case, their height calculated by 0.
    fun getLocationOnScreenX(view: View): Int {
        val location = IntArray(2)
        view.getLocationOnScreen(location)
        return location[0]
    }

    fun getLocationOnScreenY(view: View): Int {
        val location = IntArray(2)
        view.getLocationOnScreen(location)
        return location[1]
    }

    // The common activity, y coordinates as the visible state bar height +
    // visible on the upper left corner of the title bar of view height to the
    // title bar at the bottom of the distance.
    fun getLocationInWindowX(view: View): Int {
        val location = IntArray(2)
        view.getLocationInWindow(location)
        return location[0]
    }

    fun getLocationInWindowY(view: View): Int {
        val location = IntArray(2)
        view.getLocationInWindow(location)
        return location[1]
    }

    fun getStatusBarHeight(activity: Activity): Int {
        val frame = Rect()
        activity.window.decorView.getWindowVisibleDisplayFrame(frame)
        return frame.top
    }

    fun getTitleBarHeight(activity: Activity): Int {
        val contentTop = activity.window.findViewById<View>(Window.ID_ANDROID_CONTENT).top
        return contentTop - getStatusBarHeight(activity)
    }

    //
    fun setLayoutByWidth(view: View, width: Float) {
        setLayoutByWidthRaw(view, width)
    }

    fun setLayoutByWidth(view: View, width: Float, unit: Int) {
        setLayoutByWidthRaw(view, convertToPX(width, unit))
    }

    fun setLayoutByWidthRaw(view: View, width: Float) {
        val params = view.layoutParams
        params.width = width.toInt()
        view.layoutParams = params
    }

    //
    fun setLayoutByHeight(view: View, height: Float) {
        setLayoutByHeightRaw(view, height)
    }

    fun setLayoutByHeight(view: View, height: Float, unit: Int) {
        setLayoutByHeightRaw(view, convertToPX(height, unit))
    }

    fun setLayoutByHeightRaw(view: View, height: Float) {
        val params = view.layoutParams
        params.height = height.toInt()
        view.layoutParams = params
    }

    //
    fun setLayout(view: View, width: Float, height: Float) {
        setLayoutRaw(view, width, height)
    }

    fun setLayout(view: View, width: Float, height: Float, unit: Int) {
        setLayoutRaw(view, convertToPX(width, unit), convertToPX(height, unit))
    }

    private fun setLayoutRaw(view: View, width: Float, height: Float) {
        val params = view.layoutParams
        params.width = width.toInt()
        params.height = height.toInt()
        view.layoutParams = params
    }

    fun adjustViewHeightByWidth(view: View, ratio: Float) {
        val viewTreeObserver = view.viewTreeObserver
        viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                view.viewTreeObserver.removeGlobalOnLayoutListener(this)
                //
                val params = view.layoutParams
                val width = view.width
                params.height = (width / ratio).toInt()
                view.layoutParams = params
            }
        })
    }

    fun adjustViewWidthByHeight(view: View, ratio: Float) {
        val viewTreeObserver = view.viewTreeObserver
        viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                view.viewTreeObserver.removeGlobalOnLayoutListener(this)
                //
                val params = view.layoutParams
                val height = view.height
                params.width = (height * ratio).toInt()
                view.layoutParams = params
            }
        })
    }

    //
    fun adjustViewWidthByView(view01: View, view02: View) {
        val viewTreeObserver = view02.viewTreeObserver
        viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                view02.viewTreeObserver.removeGlobalOnLayoutListener(this)
                //
                val width = view02.width
                val params = view01.layoutParams
                params.width = width
                view01.layoutParams = params
            }
        })
    }

    //
    fun adjustViewHeightByView(view01: View, view02: View) {
        val viewTreeObserver = view02.viewTreeObserver
        viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                view02.viewTreeObserver.removeGlobalOnLayoutListener(this)
                //
                val height = view02.height
                val params = view01.layoutParams
                params.height = height
                view01.layoutParams = params
            }
        })
    }

    //
    fun adjustViewByView(view01: View, view02: View) {
        val viewTreeObserver = view02.viewTreeObserver
        viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                view02.viewTreeObserver.removeGlobalOnLayoutListener(this)
                //
                val width = view02.width
                val height = view02.height
                val params = view01.layoutParams
                params.width = width
                params.height = height
                view01.layoutParams = params
            }
        })
    }

    fun adaptViewAutomatic(view: View, screenWidth: Int, screenHeight: Int, ratio: Float) {
        val params = view.layoutParams
        if (screenWidth * 1F / screenHeight >= ratio) {
            params.height = screenHeight
            params.width = (screenHeight * ratio).toInt()
        } else {
            params.width = screenWidth
            params.height = (screenWidth / ratio).toInt()
        }
        view.layoutParams = params
    }

    fun fillViewAutomatic(view: View, screenWidth: Int, screenHeight: Int, ratio: Float) {
        val params = view.layoutParams
        if (screenWidth * 1F / screenHeight >= ratio) {
            params.width = screenWidth
            params.height = (screenWidth / ratio).toInt()
        } else {
            params.height = screenHeight
            params.width = (screenHeight * ratio).toInt()
        }
        view.layoutParams = params
    }

    @JvmStatic
    fun getLinearGradient(
        width: Int,
        height: Int,
        angle: Int,
        gradientStart: Int,
        gradientEnd: Int
    ): LinearGradient? {
        if (angle % 45 != 0) {
            throw IndexOutOfBoundsException("The content attribute angle value must be a multiple of 45")
        }
        var linearGradient: LinearGradient? = null
        val quotient = angle / 45
        var remainder = quotient % 8
        if (remainder < 0) {
            remainder += 8
        }
        when (remainder) {
            0 -> linearGradient =
                LinearGradient(
                    0F,
                    0F,
                    width * 1F,
                    0F,
                    gradientStart,
                    gradientEnd,
                    Shader.TileMode.CLAMP
                )
            1 -> linearGradient = LinearGradient(
                0F,
                height * 1F,
                width * 1F,
                0F,
                gradientStart,
                gradientEnd,
                Shader.TileMode.CLAMP
            )
            2 -> linearGradient =
                LinearGradient(
                    0F,
                    height * 1F,
                    0F,
                    0F,
                    gradientStart,
                    gradientEnd,
                    Shader.TileMode.CLAMP
                )
            3 -> linearGradient = LinearGradient(
                width * 1F,
                height * 1F,
                0F,
                0F,
                gradientStart,
                gradientEnd,
                Shader.TileMode.CLAMP
            )
            4 -> linearGradient =
                LinearGradient(
                    width * 1F,
                    0F,
                    0F,
                    0F,
                    gradientStart,
                    gradientEnd,
                    Shader.TileMode.CLAMP
                )
            5 -> linearGradient = LinearGradient(
                width * 1F,
                0F,
                0F,
                height * 1F,
                gradientStart,
                gradientEnd,
                Shader.TileMode.CLAMP
            )
            6 -> linearGradient =
                LinearGradient(
                    0F,
                    0F,
                    0F,
                    height * 1F,
                    gradientStart,
                    gradientEnd,
                    Shader.TileMode.CLAMP
                )
            7 -> linearGradient = LinearGradient(
                0F,
                0F,
                width * 1F,
                height * 1F,
                gradientStart,
                gradientEnd,
                Shader.TileMode.CLAMP
            )
            else -> {
            }
        }
        return linearGradient
    }
}