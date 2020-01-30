package com.tunasushi.tool;

import android.app.Activity;
import android.content.Context;
import android.graphics.LinearGradient;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;

import static com.tunasushi.tool.DeviceTool.applyDimension;
import static com.tunasushi.tool.DeviceTool.getViewDisplayMetrics;

/**
 * @author Tunasashimi
 * @date 2019-11-19 19:43
 * @Copyright 2019 TunaSashimi. All rights reserved.
 * @Description
 */
public class ViewTool {

    //
    public static void setViewMargins(View view, int left, int top, int right, int bottom) {
        setViewMargins(view, TypedValue.COMPLEX_UNIT_DIP, left, top, right, bottom);
    }

    //
    public static void setViewMargins(View view, int unit, int left, int top, int right, int bottom) {
        DisplayMetrics displayMetrics = getViewDisplayMetrics(view);

        setViewMarginsRaw(view, (int) applyDimension(unit, left, displayMetrics), (int) applyDimension(unit, top, displayMetrics),
                (int) applyDimension(unit, right, displayMetrics), (int) applyDimension(unit, bottom, displayMetrics));
    }

    //
    private static void setViewMarginsRaw(View v, int left, int top, int right, int bottom) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(left, top, right, bottom);
            v.requestLayout();
        }
    }

    public static int getScreenWidth(Activity activity) {
        WindowManager windowManager = activity.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        return display.getWidth();
    }

    public static int getScreenHeight(Activity activity) {
        WindowManager windowManager = activity.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        return display.getHeight();
    }

    public static float getScreenDensity(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.density;
    }

    // The upper left corner of the view coordinate into an array, in hiding the status bar / title bar case, their height calculated by 0.
    public static int getLocationOnScreenX(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        return location[0];
    }

    public static int getLocationOnScreenY(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        return location[1];
    }

    // The common activity, y coordinates as the visible state bar height +
    // visible on the upper left corner of the title bar of view height to the
    // title bar at the bottom of the distance.
    public static int getLocationInWindowX(View view) {
        int[] location = new int[2];
        view.getLocationInWindow(location);
        return location[0];
    }

    public static int getLocationInWindowY(View view) {
        int[] location = new int[2];
        view.getLocationInWindow(location);
        return location[1];
    }

    public static int getStatusBarHeight(Activity activity) {
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        return frame.top;
    }

    public static int getTitleBarHeight(Activity activity) {
        int contentTop = activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
        return contentTop - getStatusBarHeight(activity);
    }

    //
    public static void setLayoutByWidth(View view, float width) {
        setLayoutByWidth(view, TypedValue.COMPLEX_UNIT_DIP, width);
    }

    public static void setLayoutByWidth(View view, int unit, float width) {
        setLayoutByWidthRaw(view, applyDimension(unit, width, getViewDisplayMetrics(view)));
    }

    public static void setLayoutByWidthRaw(View view, float width) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.width = (int) (width);
        view.setLayoutParams(params);
    }

    //
    public static void setLayoutByHeight(View view, float height) {
        setLayoutByHeight(view, TypedValue.COMPLEX_UNIT_DIP, height);
    }

    public static void setLayoutByHeight(View view, int unit, float height) {
        setLayoutByHeightRaw(view, applyDimension(unit, height, getViewDisplayMetrics(view)));
    }

    public static void setLayoutByHeightRaw(View view, float height) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = (int) (height);
        view.setLayoutParams(params);
    }

    //
    public static void setLayout(View view, float width, float height) {
        setLayout(view, TypedValue.COMPLEX_UNIT_DIP, width, height);
    }

    public static void setLayout(View view, int unit, float width, float height) {
        DisplayMetrics displayMetrics = getViewDisplayMetrics(view);
        setLayoutRaw(view, applyDimension(unit, width, displayMetrics), applyDimension(unit, height, displayMetrics));
    }

    private static void setLayoutRaw(View view, float width, float height) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.width = (int) (width);
        params.height = (int) (height);
        view.setLayoutParams(params);
    }

    public static void adjustViewHeightByWidth(final View view, final float ratio) {
        ViewTreeObserver viewTreeObserver = view.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                //
                ViewGroup.LayoutParams params = view.getLayoutParams();
                int width = view.getWidth();
                params.height = (int) (width / ratio);
                view.setLayoutParams(params);
            }
        });
    }

    public static void adjustViewWidthByHeight(final View view, final float ratio) {
        ViewTreeObserver viewTreeObserver = view.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                //

                ViewGroup.LayoutParams params = view.getLayoutParams();
                int height = view.getHeight();
                params.width = (int) (height * ratio);
                view.setLayoutParams(params);
            }
        });
    }

    //
    public static void adjustViewWidthByView(final View view01, final View view02) {
        ViewTreeObserver viewTreeObserver = view02.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                view02.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                //
                int width = view02.getWidth();
                ViewGroup.LayoutParams params = view01.getLayoutParams();

                params.width = width;
                view01.setLayoutParams(params);
            }
        });
    }

    //
    public static void adjustViewHeightByView(final View view01, final View view02) {
        ViewTreeObserver viewTreeObserver = view02.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                view02.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                //
                int height = view02.getHeight();
                ViewGroup.LayoutParams params = view01.getLayoutParams();

                params.height = height;
                view01.setLayoutParams(params);
            }
        });
    }

    //
    public static void adjustViewByView(final View view01, final View view02) {
        ViewTreeObserver viewTreeObserver = view02.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                view02.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                //
                int width = view02.getWidth();
                int height = view02.getHeight();
                ViewGroup.LayoutParams params = view01.getLayoutParams();

                params.width = width;
                params.height = height;
                view01.setLayoutParams(params);
            }
        });
    }

    public static void adaptViewAutomatic(View view, int screenWidth, int screenHeight, float ratio) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (screenWidth * 1f / screenHeight >= ratio) {
            params.height = screenHeight;
            params.width = (int) (screenHeight * ratio);
        } else {
            params.width = screenWidth;
            params.height = (int) (screenWidth / ratio);
        }
        view.setLayoutParams(params);
    }

    public static void fillViewAutomatic(View view, int screenWidth, int screenHeight, float ratio) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (screenWidth * 1f / screenHeight >= ratio) {
            params.width = screenWidth;
            params.height = (int) (screenWidth / ratio);
        } else {
            params.height = screenHeight;
            params.width = (int) (screenHeight * ratio);
        }
        view.setLayoutParams(params);
    }

    public static LinearGradient getLinearGradient(int width, int height, int angle, int gradientStart, int gradientEnd) {
        if (angle % 45 != 0) {
            throw new IndexOutOfBoundsException("Angle value must be a multiple of 45");
        }
        LinearGradient linearGradient = null;
        int quotient = angle / 45;
        int remainder = quotient % 8;
        if (remainder < 0) {
            remainder += 8;
        }
        switch (remainder) {
            case 0:
                linearGradient = new LinearGradient(0, 0, width, 0, gradientStart, gradientEnd, Shader.TileMode.CLAMP);
                break;
            case 1:
                linearGradient = new LinearGradient(0, height, width, 0, gradientStart, gradientEnd, Shader.TileMode.CLAMP);
                break;
            case 2:
                linearGradient = new LinearGradient(0, height, 0, 0, gradientStart, gradientEnd, Shader.TileMode.CLAMP);
                break;
            case 3:
                linearGradient = new LinearGradient(width, height, 0, 0, gradientStart, gradientEnd, Shader.TileMode.CLAMP);
                break;
            case 4:
                linearGradient = new LinearGradient(width, 0, 0, 0, gradientStart, gradientEnd, Shader.TileMode.CLAMP);
                break;
            case 5:
                linearGradient = new LinearGradient(width, 0, 0, height, gradientStart, gradientEnd, Shader.TileMode.CLAMP);
                break;
            case 6:
                linearGradient = new LinearGradient(0, 0, 0, height, gradientStart, gradientEnd, Shader.TileMode.CLAMP);
                break;
            case 7:
                linearGradient = new LinearGradient(0, 0, width, height, gradientStart, gradientEnd, Shader.TileMode.CLAMP);
                break;
            default:
                break;
        }
        return linearGradient;
    }
}
