package com.tunasushi.tool;

import android.content.Context;
import android.util.TypedValue;
import android.widget.LinearLayout;

import com.tunasushi.tuna.TView;

import java.util.ArrayList;
import java.util.List;

import static com.tunasushi.tool.DeviceTool.applyDimension;
import static com.tunasushi.tool.DeviceTool.getViewDisplayMetrics;
import static com.tunasushi.tool.ViewTool.setViewMargins;

/**
 * @author Tunasashimi
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
                            TViewArray[j].setStatus(false, false, false);
                        }
                    }
                }
            });
            TViewArray[i].setTouchCancelListener(new TView.TouchCancelListener() {
                @Override
                public void touchCancel(TView t) {
                    for (int j = 0; j < arraySize; j++) {
                        switch (finalI) {
                            case 0:
                                if (j == finalI + 1) {
                                    TViewArray[j].setStatus(false, true, false);
                                } else if (j > finalI + 1) {
                                    TViewArray[j].setStatus(false, false, false);
                                }
                                break;
                            default:
                                if (j == finalI - 1) {
                                    TViewArray[j].setStatus(false, true, false);
                                } else if (j < finalI - 1) {
                                    TViewArray[j].setStatus(false, false, false);
                                }
                                break;
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
                    for (int j = 0; j < listSize; j++) {
                        if (j != finalI) {
                            TViewList.get(j).setStatus(false, false, false, false);
                        }
                    }
                }
            });
            TViewList.get(i).setTouchCancelListener(new TView.TouchCancelListener() {
                @Override
                public void touchCancel(TView t) {
                    for (int j = 0; j < listSize; j++) {
                        switch (finalI) {
                            case 0:
                                if (j == finalI + 1) {
                                    TViewList.get(j).setStatus(false, true, false, false);
                                } else if (j > finalI + 1) {
                                    TViewList.get(j).setStatus(false, false, false, false);
                                }
                                break;
                            default:
                                if (j == finalI - 1) {
                                    TViewList.get(j).setStatus(false, true, false, false);
                                } else if (j < finalI - 1) {
                                    TViewList.get(j).setStatus(false, false, false, false);
                                }
                                break;
                        }
                    }
                }
            });
        }
    }

    //9
    public static void dynamic(String[] stringArray, String string, TView.TouchUpListener touchUpListener, LinearLayout linearLayout, int width, int leftStyle,
                               int rightStyle, int centerStyle, int itemStyle) {

        dynamic(stringArray, string, touchUpListener, linearLayout, TypedValue.COMPLEX_UNIT_DIP, width, leftStyle, rightStyle, centerStyle, itemStyle);
    }

    //9
    public static void dynamic(String[] stringArray, String string, TView.OnClickListener onClickListener, LinearLayout linearLayout, int width, int leftStyle,
                               int rightStyle, int centerStyle, int itemStyle) {

        dynamic(stringArray, string, onClickListener, linearLayout, TypedValue.COMPLEX_UNIT_DIP, width, leftStyle, rightStyle, centerStyle, itemStyle);
    }

    //10
    public static void dynamic(String[] stringArray, String string, TView.TouchUpListener touchUpListener, LinearLayout linearLayout, int unitWidth, int width, int leftStyle,
                               int rightStyle, int centerStyle, int itemStyle) {
        int index = 0;
        for (int i = 0; i < stringArray.length; i++) {
            if (stringArray[i].equals(string)) {
                index = i;
                break;
            }
        }
        dynamic(stringArray, index, touchUpListener, linearLayout, unitWidth, width, leftStyle, rightStyle, centerStyle, itemStyle);
    }

    //10
    public static void dynamic(String[] stringArray, String string, TView.OnClickListener onClickListener, LinearLayout linearLayout, int unitWidth, int width, int leftStyle,
                               int rightStyle, int centerStyle, int itemStyle) {
        int index = 0;
        for (int i = 0; i < stringArray.length; i++) {
            if (stringArray[i].equals(string)) {
                index = i;
                break;
            }
        }
        dynamic(stringArray, index, onClickListener, linearLayout, unitWidth, width, leftStyle, rightStyle, centerStyle, itemStyle);
    }

    //8
    public static void dynamic(String[] stringArray, TView.TouchUpListener touchUpListener, LinearLayout linearLayout, int width, int leftStyle, int rightStyle,
                               int centerStyle, int itemStyle) {

        dynamic(stringArray, 0, touchUpListener, linearLayout, TypedValue.COMPLEX_UNIT_DIP, width, leftStyle, rightStyle, centerStyle, itemStyle);
    }

    //8
    public static void dynamic(String[] stringArray, TView.OnClickListener onClickListener, LinearLayout linearLayout, int width, int leftStyle, int rightStyle,
                               int centerStyle, int itemStyle) {

        dynamic(stringArray, 0, onClickListener, linearLayout, TypedValue.COMPLEX_UNIT_DIP, width, leftStyle, rightStyle, centerStyle, itemStyle);
    }

    //8
    public static void dynamic(String[] stringArray, TView.TouchUpListener touchUpListener, LinearLayout linearLayout, int unitWidth, int width, int leftStyle,
                               int rightStyle, int centerStyle, int itemStyle) {

        dynamic(stringArray, 0, touchUpListener, linearLayout, unitWidth, width, leftStyle, rightStyle, centerStyle, itemStyle);
    }

    //8
    public static void dynamic(String[] stringArray, TView.OnClickListener onClickListener, LinearLayout linearLayout, int unitWidth, int width, int leftStyle,
                               int rightStyle, int centerStyle, int itemStyle) {

        dynamic(stringArray, 0, onClickListener, linearLayout, unitWidth, width, leftStyle, rightStyle, centerStyle, itemStyle);
    }

    //9
    public static void dynamic(String[] stringArray, int index, TView.TouchUpListener touchUpListener, LinearLayout linearLayout, int width, int leftStyle, int rightStyle,
                               int centerStyle, int itemStyle) {

        dynamic(stringArray, index, touchUpListener, linearLayout, TypedValue.COMPLEX_UNIT_DIP, width, leftStyle, rightStyle, centerStyle, itemStyle);
    }

    //9
    public static void dynamic(String[] stringArray, int index, TView.OnClickListener onClickListener, LinearLayout linearLayout, int width, int leftStyle, int rightStyle,
                               int centerStyle, int itemStyle) {

        dynamic(stringArray, index, onClickListener, linearLayout, TypedValue.COMPLEX_UNIT_DIP, width, leftStyle, rightStyle, centerStyle, itemStyle);
    }

    //10
    public static void dynamic(String[] stringArray, int index,
                               TView.TouchUpListener touchUpListener,
                               LinearLayout linearLayout, int widthUnit, int width, int leftStyle,
                               int rightStyle, int centerStyle, int itemStyle) {

        dynamicRaw(stringArray, index, touchUpListener, null, linearLayout, (int) applyDimension(widthUnit, width, getViewDisplayMetrics(linearLayout)), leftStyle, rightStyle,
                centerStyle, itemStyle);
    }

    //10
    public static void dynamic(String[] stringArray, int index,
                               TView.OnClickListener onClickListener,
                               LinearLayout linearLayout, int widthUnit, int width, int leftStyle,
                               int rightStyle, int centerStyle, int itemStyle) {

        dynamicRaw(stringArray, index, null, onClickListener, linearLayout, (int) applyDimension(widthUnit, width, getViewDisplayMetrics(linearLayout)), leftStyle, rightStyle,
                centerStyle, itemStyle);
    }

    //10
    private static void dynamicRaw(String[] stringArray, int index,
                                   TView.TouchUpListener touchUpListener, TView.OnClickListener onClickListener, LinearLayout linearLayout,
                                   int width, int leftStyle, int rightStyle, int centerStyle, int itemStyle) {

        Context context = linearLayout.getContext();

        int margin = -2;    //-2px

        List<TView> TViewList = new ArrayList();

        if (stringArray.length <= 0) {
            return;
        } else if (stringArray.length == 1) {

            TView TView = new TView(context, null, itemStyle);

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
                TView TView = new TView(context, null, i == 0 ? leftStyle : i == stringArray.length - 1 ? rightStyle : centerStyle);
                TView.setTextValue(stringArray[i]);
                if (i == index) {
                    TView.setSelect(true);
                }
                TView.setTouchUpListener(touchUpListener);

                TViewList.add(TView);
                linearLayout.addView(TView, width, LinearLayout.LayoutParams.MATCH_PARENT);

                if (i == 0 && stringArray.length == 2) {
                    setViewMargins(TView, TypedValue.COMPLEX_UNIT_PX, 0, 0, margin, 0);
                } else if (i == 1 && stringArray.length == 2) {
                    setViewMargins(TView, TypedValue.COMPLEX_UNIT_PX, margin, 0, 0, 0);
                } else if (i != 0 && i != stringArray.length - 1) {
                    setViewMargins(TView, TypedValue.COMPLEX_UNIT_PX, margin, 0, margin, 0);
                }
            }
            associate(TViewList);
        }
    }

}
