package com.tunasushi.tool;

import android.content.Context;
import android.widget.LinearLayout;

import com.tunasushi.tuna.TView;

import java.util.ArrayList;
import java.util.List;

import static com.tunasushi.tool.ConvertTool.convertToPX;
import static com.tunasushi.tool.ViewTool.setViewMargins;

/**
 * @author TunaSashimi
 * @date 2020-02-03 01:18
 * @Copyright 2020 TunaSashimi. All rights reserved.
 * @Description
 */
public class GroupTool {
    //
    public static void associate(final TView[] TViewArray) {
        if (TViewArray == null) {
            return;
        }
        final int arraySize = TViewArray.length;
        for (int i = 0; i < arraySize; i++) {
            final int finalI = i;
            TViewArray[i].setAssociateListener(new TView.associateListener() {
                @Override
                public void associate(TView t) {
                    for (int j = 0; j < arraySize; j++) {
                        if (j != finalI) {
                            TViewArray[j].setStatus(false, false);
                        }
                    }
                }
            });
        }
    }

    //
    public static void associate(final List<TView> TViewList) {
        if (TViewList == null) {
            return;
        }
        final int listSize = TViewList.size();
        for (int i = 0; i < listSize; i++) {
            final int finalI = i;
            TViewList.get(i).setAssociateListener(new TView.associateListener() {
                @Override
                public void associate(TView t) {
                    //任何一个TView的associate方法就是把其他的TView的状态设为false
                    for (int j = 0; j < listSize; j++) {
                        if (j != finalI) {
                            TViewList.get(j).setStatus(false, false);
                        }
                    }
                }
            });
        }
    }

    //9
    public static void dynamic(String[] stringArray, String string, TView.TouchUpListener touchUpListener, LinearLayout linearLayout, int width, int styleStart,
                               int styleEnd, int styleOther) {

        dynamic(stringArray, string, touchUpListener, linearLayout, width, styleStart, styleEnd, styleOther);
    }

    //9
    public static void dynamic(String[] stringArray, String string, TView.OnClickListener onClickListener, LinearLayout linearLayout, int width, int styleStart,
                               int styleEnd, int styleOther) {

        dynamic(stringArray, string, onClickListener, linearLayout, width, styleStart, styleEnd, styleOther);
    }

    //
    //10
    public static void dynamic(String[] stringArray, String string, TView.TouchUpListener touchUpListener, LinearLayout linearLayout, int width, int widthUnit, int styleStart,
                               int styleEnd, int styleOther) {
        int index = 0;
        for (int i = 0; i < stringArray.length; i++) {
            if (stringArray[i].equals(string)) {
                index = i;
                break;
            }
        }
        dynamic(stringArray, index, touchUpListener, linearLayout, width, widthUnit, styleStart, styleEnd, styleOther);
    }

    //10
    public static void dynamic(String[] stringArray, String string, TView.OnClickListener onClickListener, LinearLayout linearLayout, int width, int unitWidth, int styleStart,
                               int styleEnd, int styleOther) {
        int index = 0;
        for (int i = 0; i < stringArray.length; i++) {
            if (stringArray[i].equals(string)) {
                index = i;
                break;
            }
        }
        dynamic(stringArray, index, onClickListener, linearLayout, width, unitWidth, styleStart, styleEnd, styleOther);
    }

    //8
    public static void dynamic(String[] stringArray, TView.TouchUpListener touchUpListener, LinearLayout linearLayout, int width, int styleStart, int styleEnd,
                               int styleOther) {

        dynamic(stringArray, 0, touchUpListener, linearLayout, width, styleStart, styleEnd, styleOther);
    }

    //8
    public static void dynamic(String[] stringArray, TView.OnClickListener onClickListener, LinearLayout linearLayout, int width, int styleStart, int styleEnd,
                               int styleOther) {

        dynamic(stringArray, 0, onClickListener, linearLayout, width, styleStart, styleEnd, styleOther);
    }

    //8
    public static void dynamic(String[] stringArray, TView.TouchUpListener touchUpListener, LinearLayout linearLayout, int width, int unitWidth, int styleStart,
                               int styleEnd, int styleOther) {

        dynamic(stringArray, 0, touchUpListener, linearLayout, width, unitWidth, styleStart, styleEnd, styleOther);
    }

    //8
    public static void dynamic(String[] stringArray, TView.OnClickListener onClickListener, LinearLayout linearLayout, int width, int unitWidth, int styleStart,
                               int styleEnd, int styleOther) {

        dynamic(stringArray, 0, onClickListener, linearLayout, width, unitWidth, styleStart, styleEnd, styleOther);
    }

    //9
    public static void dynamic(String[] stringArray, int index, TView.TouchUpListener touchUpListener, LinearLayout linearLayout, int width, int styleStart, int styleEnd,
                               int styleOther) {

        dynamic(stringArray, index, touchUpListener, linearLayout, width, styleStart, styleEnd, styleOther);
    }

    //9
    public static void dynamic(String[] stringArray,
                               int index,
                               TView.OnClickListener onClickListener,
                               LinearLayout linearLayout,
                               int width,
                               int styleStart,
                               int styleEnd,
                               int styleOther) {

        dynamic(stringArray, index, onClickListener, linearLayout, width, styleStart, styleEnd, styleOther);
    }

    //10
    public static void dynamic(String[] stringArray,
                               int index,
                               TView.TouchUpListener touchUpListener,
                               LinearLayout linearLayout,
                               int width, int widthUnit,
                               int styleStart,
                               int styleEnd,
                               int styleOther) {

        dynamicRaw(stringArray, index, touchUpListener, null, linearLayout, (int) convertToPX(width, widthUnit), styleStart, styleEnd,
                styleOther);
    }

    //10
    public static void dynamic(String[] stringArray,
                               int index,
                               TView.OnClickListener onClickListener,
                               LinearLayout linearLayout,
                               int width, int widthUnit,
                               int styleStart,
                               int styleEnd,
                               int styleOther) {
        dynamicRaw(stringArray, index, null, onClickListener, linearLayout, (int) convertToPX(width, widthUnit), styleStart, styleEnd,
                styleOther);
    }

    //10
    private static void dynamicRaw(String[] stringArray, int index,
                                   TView.TouchUpListener touchUpListener, TView.OnClickListener onClickListener, LinearLayout linearLayout,
                                   int width, int styleStart, int styleEnd, int styleOther) {

        Context context = linearLayout.getContext();

        int margin = -2;    //-2px

        List<TView> TViewList = new ArrayList();

        if (stringArray.length <= 0) {
            return;
        } else if (stringArray.length == 1) {

            TView TView = new TView(context, null, styleOther);

            TView.setTextValue(stringArray[0]);

            if (touchUpListener != null) {
                TView.setTouchUpListener(touchUpListener);
            }

            if (onClickListener != null) {
                TView.setOnClickListener(onClickListener);
            }

            linearLayout.addView(TView, width, LinearLayout.LayoutParams.MATCH_PARENT);
        } else {

            for (int i = 0; i < stringArray.length; i++) {
                TView TView = new TView(context, null, i == 0 ? styleStart : i == stringArray.length - 1 ? styleEnd : styleOther);
                TView.setTextValue(stringArray[i]);
                if (i == index) {
                    TView.setSelect(true);
                }
                TView.setTouchUpListener(touchUpListener);

                TViewList.add(TView);
                linearLayout.addView(TView, width, LinearLayout.LayoutParams.MATCH_PARENT);

                if (i == 0 && stringArray.length == 2) {
                    setViewMargins(TView, 0, 0, margin, 0);
                } else if (i == 1 && stringArray.length == 2) {
                    setViewMargins(TView, margin, 0, 0, 0);
                } else if (i != 0 && i != stringArray.length - 1) {
                    setViewMargins(TView, margin, 0, margin, 0);
                }
            }
            associate(TViewList);
        }
    }
}
