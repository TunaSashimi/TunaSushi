package com.tunasushi.tool;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
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
                public void associate(View v) {
                    for (int j = 0; j < arraySize; j++) {
                        if (j != finalI) {
                            TViewArray[j].setStatius(false, false, false);
                        }
                    }
                }
            });
            TViewArray[i].setTouchCancelListener(new TView.TouchCancelListener() {
                @Override
                public void touchCancel(View v) {
                    for (int j = 0; j < arraySize; j++) {
                        switch (finalI) {
                            case 0:
                                if (j == finalI + 1) {
                                    TViewArray[j].setStatius(false, true, false);
                                } else if (j > finalI + 1) {
                                    TViewArray[j].setStatius(false, false, false);
                                }
                                break;
                            default:
                                if (j == finalI - 1) {
                                    TViewArray[j].setStatius(false, true, false);
                                } else if (j < finalI - 1) {
                                    TViewArray[j].setStatius(false, false, false);
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
                public void associate(View v) {
                    for (int j = 0; j < listSize; j++) {
                        if (j != finalI) {
                            TViewList.get(j).setStatius(false, false, false, false);
                        }
                    }
                }
            });
            TViewList.get(i).setTouchCancelListener(new TView.TouchCancelListener() {
                @Override
                public void touchCancel(View v) {
                    for (int j = 0; j < listSize; j++) {
                        switch (finalI) {
                            case 0:
                                if (j == finalI + 1) {
                                    TViewList.get(j).setStatius(false, true, false, false);
                                } else if (j > finalI + 1) {
                                    TViewList.get(j).setStatius(false, false, false, false);
                                }
                                break;
                            default:
                                if (j == finalI - 1) {
                                    TViewList.get(j).setStatius(false, true, false, false);
                                } else if (j < finalI - 1) {
                                    TViewList.get(j).setStatius(false, false, false, false);
                                }
                                break;
                        }
                    }
                }
            });
        }
    }

    //9
    public static void dynamic(String[] titleArray, String string, TView.TouchUpListener touchUpListener, LinearLayout linearLayout, int width, int leftStyle,
                               int rightStyle, int horizontalStyle, int wholeStyle) {

        dynamic(titleArray, string, touchUpListener, linearLayout, TypedValue.COMPLEX_UNIT_DIP, width, leftStyle, rightStyle, horizontalStyle, wholeStyle);
    }

    //9
    public static void dynamic(String[] titleArray, String string, TView.OnClickListener onClickListener, LinearLayout linearLayout, int width, int leftStyle,
                               int rightStyle, int horizontalStyle, int wholeStyle) {

        dynamic(titleArray, string, onClickListener, linearLayout, TypedValue.COMPLEX_UNIT_DIP, width, leftStyle, rightStyle, horizontalStyle, wholeStyle);
    }

    //10
    public static void dynamic(String[] titleArray, String string, TView.TouchUpListener touchUpListener, LinearLayout linearLayout, int unitWidth, int width, int leftStyle,
                               int rightStyle, int horizontalStyle, int wholeStyle) {
        int index = 0;
        for (int i = 0; i < titleArray.length; i++) {
            if (titleArray[i].equals(string)) {
                index = i;
                break;
            }
        }
        dynamic(titleArray, index, touchUpListener, linearLayout, unitWidth, width, leftStyle, rightStyle, horizontalStyle, wholeStyle);
    }

    //10
    public static void dynamic(String[] titleArray, String string, TView.OnClickListener onClickListener, LinearLayout linearLayout, int unitWidth, int width, int leftStyle,
                               int rightStyle, int horizontalStyle, int wholeStyle) {
        int index = 0;
        for (int i = 0; i < titleArray.length; i++) {
            if (titleArray[i].equals(string)) {
                index = i;
                break;
            }
        }
        dynamic(titleArray, index, onClickListener, linearLayout, unitWidth, width, leftStyle, rightStyle, horizontalStyle, wholeStyle);
    }

    //8
    public static void dynamic(String[] titleArray, TView.TouchUpListener touchUpListener, LinearLayout linearLayout, int width, int leftStyle, int rightStyle,
                               int horizontalStyle, int wholeStyle) {

        dynamic(titleArray, 0, touchUpListener, linearLayout, TypedValue.COMPLEX_UNIT_DIP, width, leftStyle, rightStyle, horizontalStyle, wholeStyle);
    }

    //8
    public static void dynamic(String[] titleArray, TView.OnClickListener onClickListener, LinearLayout linearLayout, int width, int leftStyle, int rightStyle,
                               int horizontalStyle, int wholeStyle) {

        dynamic(titleArray, 0, onClickListener, linearLayout, TypedValue.COMPLEX_UNIT_DIP, width, leftStyle, rightStyle, horizontalStyle, wholeStyle);
    }

    //8
    public static void dynamic(String[] titleArray, TView.TouchUpListener touchUpListener, LinearLayout linearLayout, int unitWidth, int width, int leftStyle,
                               int rightStyle, int horizontalStyle, int wholeStyle) {

        dynamic(titleArray, 0, touchUpListener, linearLayout, unitWidth, width, leftStyle, rightStyle, horizontalStyle, wholeStyle);
    }

    //8
    public static void dynamic(String[] titleArray, TView.OnClickListener onClickListener, LinearLayout linearLayout, int unitWidth, int width, int leftStyle,
                               int rightStyle, int horizontalStyle, int wholeStyle) {

        dynamic(titleArray, 0, onClickListener, linearLayout, unitWidth, width, leftStyle, rightStyle, horizontalStyle, wholeStyle);
    }

    //9
    public static void dynamic(String[] titleArray, int index, TView.TouchUpListener touchUpListener, LinearLayout linearLayout, int width, int leftStyle, int rightStyle,
                               int horizontalStyle, int wholeStyle) {

        dynamic(titleArray, index, touchUpListener, linearLayout, TypedValue.COMPLEX_UNIT_DIP, width, leftStyle, rightStyle, horizontalStyle, wholeStyle);
    }

    //9
    public static void dynamic(String[] titleArray, int index, TView.OnClickListener onClickListener, LinearLayout linearLayout, int width, int leftStyle, int rightStyle,
                               int horizontalStyle, int wholeStyle) {

        dynamic(titleArray, index, onClickListener, linearLayout, TypedValue.COMPLEX_UNIT_DIP, width, leftStyle, rightStyle, horizontalStyle, wholeStyle);
    }

    //10
    public static void dynamic(String[] titleArray, int index,
                               TView.TouchUpListener touchUpListener,
                               LinearLayout linearLayout, int widthUnit, int width, int leftStyle,
                               int rightStyle, int horizontalStyle, int wholeStyle) {

        dynamicRaw(titleArray, index, touchUpListener, null, linearLayout, (int) applyDimension(widthUnit, width, getViewDisplayMetrics(linearLayout)), leftStyle, rightStyle,
                horizontalStyle, wholeStyle);
    }

    //10
    public static void dynamic(String[] titleArray, int index,
                               TView.OnClickListener onClickListener,
                               LinearLayout linearLayout, int widthUnit, int width, int leftStyle,
                               int rightStyle, int horizontalStyle, int wholeStyle) {

        dynamicRaw(titleArray, index, null, onClickListener, linearLayout, (int) applyDimension(widthUnit, width, getViewDisplayMetrics(linearLayout)), leftStyle, rightStyle,
                horizontalStyle, wholeStyle);
    }

    //10
    private static void dynamicRaw(String[] titleArray, int index,
                                   TView.TouchUpListener touchUpListener, TView.OnClickListener onClickListener, LinearLayout linearLayout,
                                   int width, int leftStyle, int rightStyle, int horizontalStyle, int wholeStyle) {

        Context context = linearLayout.getContext();

        int margin = -2;    //-2px

        List<TView> TViewList = new ArrayList<TView>();

        if (titleArray.length <= 0) {
            return;
        } else if (titleArray.length == 1) {

            TView TView = new TView(context, null, wholeStyle);

            TView.setTextValue(titleArray[0]);

            if (touchUpListener != null) {
                TView.setTouchUpListener(touchUpListener);
            }

            if(onClickListener!=null){
                TView.setOnClickListener(onClickListener);
            }

            linearLayout.addView(TView, width, LinearLayout.LayoutParams.MATCH_PARENT);
        } else {

            for (int i = 0; i < titleArray.length; i++) {
                TView TView = new TView(context, null, i == 0 ? leftStyle : i == titleArray.length - 1 ? rightStyle : horizontalStyle);
                TView.setTextValue(titleArray[i]);
                if (i == index) {
                    TView.setSelect(true);
                }
                TView.setTouchUpListener(touchUpListener);

                TViewList.add(TView);
                linearLayout.addView(TView, width, LinearLayout.LayoutParams.MATCH_PARENT);

                if (i == 0 && titleArray.length == 2) {
                    setViewMargins(TView, TypedValue.COMPLEX_UNIT_PX, 0, 0, margin, 0);
                } else if (i == 1 && titleArray.length == 2) {
                    setViewMargins(TView, TypedValue.COMPLEX_UNIT_PX, margin, 0, 0, 0);
                } else if (i != 0 && i != titleArray.length - 1) {
                    setViewMargins(TView, TypedValue.COMPLEX_UNIT_PX, margin, 0, margin, 0);
                }
            }
            associate(TViewList);
        }
    }

}
