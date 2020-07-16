package com.tunasushi.tuna;

import android.content.Context;
import android.widget.LinearLayout;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.IntDef;

/**
 * @author TunaSashimi
 * @date 2020-02-03 01:18
 * @Copyright 2020 TunaSashimi. All rights reserved.
 * @Description
 */
public class TGroup {
    @IntDef({HORIZONTAL, VERTICAL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface OrientationMode {
    }

    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;

    //
    public static void link(final List<TView> tViewList) {
        linkRaw(tViewList, null, null);
    }

    public static void link(final List<TView> tViewList, TView.TouchUpListener touchUpListener) {
        linkRaw(tViewList, touchUpListener, null);
    }

    public static void link(final List<TView> tViewList, TView.OnClickListener onClickListener) {
        linkRaw(tViewList, null, onClickListener);
    }

    public static void link(final List<TView> tViewList,TView.TouchUpListener touchUpListener, TView.OnClickListener onClickListener) {
        linkRaw(tViewList, touchUpListener, onClickListener);
    }

    //3
    public static void linkRaw(final List<TView> tViewList, final TView.TouchUpListener touchUpListener, final TView.OnClickListener onClickListener) {
        if (tViewList == null) {
            return;
        }
        final int listSize = tViewList.size();
        for (int i = 0; i < listSize; i++) {
            final int finalI = i;
            TView t = tViewList.get(i);
            if (touchUpListener != null) {
                t.setTouchUpListener(touchUpListener);
            }
            if (onClickListener != null) {
                t.setOnClickListener(onClickListener);
            }
            t.setAssociateListener(new TView.associateListener() {
                @Override
                public void associate(TView t) {
                    //The link method of any TView is to set the status of other TViews to false!
                    for (int j = 0; j < listSize; j++) {
                        if (j != finalI) {
                            tViewList.get(j).setStatus(false, false);
                        }
                    }
                }
            });
        }
    }

    //10
    public static void create(String[] stringArray,
                              String string,
                              LinearLayout linearLayout,
                              int width, int height,
                              int styleStart, int styleEnd, int styleOther,
                              @OrientationMode int mode,
                              TView.TouchUpListener touchUpListener) {
        int index = 0;
        for (int i = 0; i < stringArray.length; i++) {
            if (stringArray[i].equals(string)) {
                index = i;
                break;
            }
        }
        createRaw(stringArray, index, linearLayout, width, height, styleStart, styleEnd, styleOther, mode, touchUpListener, null);
    }

    //9
    public static void create(String[] stringArray,
                              String string,
                              LinearLayout linearLayout,
                              int width, int height,
                              int styleStart, int styleEnd, int styleOther,
                              TView.TouchUpListener touchUpListener) {
        int index = 0;
        for (int i = 0; i < stringArray.length; i++) {
            if (stringArray[i].equals(string)) {
                index = i;
                break;
            }
        }
        createRaw(stringArray, index, linearLayout, width, height, styleStart, styleEnd, styleOther, HORIZONTAL, touchUpListener, null);
    }

    public static void create(String[] stringArray,
                              String string,
                              LinearLayout linearLayout,
                              int width, int height,
                              int styleStart, int styleEnd, int styleOther,
                              TView.TouchUpListener touchUpListener,
                              TView.OnClickListener onClickListener) {
        int index = 0;
        for (int i = 0; i < stringArray.length; i++) {
            if (stringArray[i].equals(string)) {
                index = i;
                break;
            }
        }
        createRaw(stringArray, index, linearLayout, width, height, styleStart, styleEnd, styleOther, HORIZONTAL, touchUpListener, onClickListener);
    }

    //10
    public static void create(String[] stringArray,
                              String string,
                              LinearLayout linearLayout,
                              int width, int height,
                              int styleStart, int styleEnd, int styleOther,
                              @OrientationMode int mode,
                              TView.OnClickListener onClickListener) {
        int index = 0;
        for (int i = 0; i < stringArray.length; i++) {
            if (stringArray[i].equals(string)) {
                index = i;
                break;
            }
        }
        createRaw(stringArray, index, linearLayout, width, height, styleStart, styleEnd, styleOther, mode, null, onClickListener);
    }

    //10
    public static void create(String[] stringArray,
                              String string,
                              LinearLayout linearLayout,
                              int width, int height,
                              int styleStart, int styleEnd, int styleOther,
                              @OrientationMode int mode,
                              TView.TouchUpListener touchUpListener,
                              TView.OnClickListener onClickListener) {
        int index = 0;
        for (int i = 0; i < stringArray.length; i++) {
            if (stringArray[i].equals(string)) {
                index = i;
                break;
            }
        }
        createRaw(stringArray, index, linearLayout, width, height, styleStart, styleEnd, styleOther, mode, touchUpListener, onClickListener);
    }

    //9
    public static void create(String[] stringArray,
                              String string,
                              LinearLayout linearLayout,
                              int width, int height,
                              int styleStart, int styleEnd, int styleOther,
                              TView.OnClickListener onClickListener) {
        int index = 0;
        for (int i = 0; i < stringArray.length; i++) {
            if (stringArray[i].equals(string)) {
                index = i;
                break;
            }
        }
        createRaw(stringArray, index, linearLayout, width, height, styleStart, styleEnd, styleOther, HORIZONTAL, null, onClickListener);
    }

    //9
    public static void create(String[] stringArray,
                              LinearLayout linearLayout,
                              int width, int height,
                              int styleStart, int styleEnd, int styleOther,
                              @OrientationMode int mode,
                              TView.TouchUpListener touchUpListener) {

        createRaw(stringArray, 0, linearLayout, width, height, styleStart, styleEnd, styleOther, mode, touchUpListener, null);
    }

    //8
    public static void create(String[] stringArray,
                              LinearLayout linearLayout,
                              int width, int height,
                              int styleStart, int styleEnd, int styleOther,
                              TView.TouchUpListener touchUpListener) {

        createRaw(stringArray, 0, linearLayout, width, height, styleStart, styleEnd, styleOther, HORIZONTAL, touchUpListener, null);
    }

    //10
    public static void create(String[] stringArray,
                              LinearLayout linearLayout,
                              int width, int height,
                              int styleStart, int styleEnd, int styleOther,
                              @OrientationMode int mode,
                              TView.TouchUpListener touchUpListener,
                              TView.OnClickListener onClickListener) {

        createRaw(stringArray, 0, linearLayout, width, height, styleStart, styleEnd, styleOther, mode, touchUpListener, onClickListener);
    }

    //9
    public static void create(String[] stringArray,
                              LinearLayout linearLayout,
                              int width, int height,
                              int styleStart, int styleEnd, int styleOther,
                              @OrientationMode int mode,
                              TView.OnClickListener onClickListener) {

        createRaw(stringArray, 0, linearLayout, width, height, styleStart, styleEnd, styleOther, mode, null, onClickListener);
    }

    //9
    public static void create(String[] stringArray,
                              LinearLayout linearLayout,
                              int width, int height,
                              int styleStart, int styleEnd, int styleOther,
                              TView.TouchUpListener touchUpListener,
                              TView.OnClickListener onClickListener) {

        createRaw(stringArray, 0, linearLayout, width, height, styleStart, styleEnd, styleOther, HORIZONTAL, touchUpListener, onClickListener);
    }

    //8
    public static void create(String[] stringArray,
                              LinearLayout linearLayout,
                              int width, int height,
                              int styleStart, int styleEnd, int styleOther,
                              TView.OnClickListener onClickListener) {

        createRaw(stringArray, 0, linearLayout, width, height, styleStart, styleEnd, styleOther, HORIZONTAL, null, onClickListener);
    }

    //10
    public static void create(String[] stringArray,
                              int index,
                              LinearLayout linearLayout,
                              int width, int height,
                              int styleStart, int styleEnd, int styleOther,
                              @OrientationMode int mode,
                              TView.TouchUpListener touchUpListener) {

        createRaw(stringArray, index, linearLayout, width, height, styleStart, styleEnd, styleOther, mode, touchUpListener, null);
    }

    //9
    public static void create(String[] stringArray,
                              int index,
                              LinearLayout linearLayout,
                              int width, int height,
                              int styleStart, int styleEnd, int styleOther,
                              TView.TouchUpListener touchUpListener) {

        createRaw(stringArray, index, linearLayout, width, height, styleStart, styleEnd, styleOther, HORIZONTAL, touchUpListener, null);
    }

    //10
    public static void create(String[] stringArray,
                              int index,
                              LinearLayout linearLayout,
                              int width, int height,
                              int styleStart, int styleEnd, int styleOther,
                              @OrientationMode int mode,
                              TView.OnClickListener onClickListener) {

        createRaw(stringArray, index, linearLayout, width, height, styleStart, styleEnd, styleOther, mode, null, onClickListener);
    }

    //11
    public static void create(String[] stringArray,
                              int index,
                              LinearLayout linearLayout,
                              int width, int height,
                              int styleStart, int styleEnd, int styleOther,
                              @OrientationMode int mode,
                              TView.TouchUpListener touchUpListener,
                              TView.OnClickListener onClickListener) {

        createRaw(stringArray, index, linearLayout, width, height, styleStart, styleEnd, styleOther, mode, touchUpListener, onClickListener);
    }

    //9
    public static void create(String[] stringArray,
                              int index,
                              TView.OnClickListener onClickListener,
                              LinearLayout linearLayout,
                              int width, int height,
                              int styleStart, int styleEnd, int styleOther) {

        createRaw(stringArray, index, linearLayout, width, height, styleStart, styleEnd, styleOther, HORIZONTAL, null, onClickListener);
    }

    //10
    public static void create(String[] stringArray,
                              int index,
                              LinearLayout linearLayout,
                              int width, int height,
                              int styleStart, int styleEnd, int styleOther,
                              TView.TouchUpListener touchUpListener,
                              TView.OnClickListener onClickListener) {

        createRaw(stringArray, index, linearLayout, width, height, styleStart, styleEnd, styleOther, HORIZONTAL, touchUpListener, onClickListener);
    }

    //11
    private static void createRaw(String[] stringArray,
                                  int index,
                                  LinearLayout linearLayout,
                                  int width, int height,
                                  int styleStart, int styleEnd, int styleOther,
                                  @OrientationMode int mode,
                                  TView.TouchUpListener touchUpListener,
                                  TView.OnClickListener onClickListener) {

        Context context = linearLayout.getContext();
        List<TView> tViewList = new ArrayList();
        if (stringArray.length <= 0) {
            return;
        } else if (stringArray.length == 1) {
            TView t = new TView(context, null, styleOther);
            t.setText(stringArray[0]);
            if (touchUpListener != null) {
                t.setTouchUpListener(touchUpListener);
            }
            if (onClickListener != null) {
                t.setOnClickListener(onClickListener);
            }
            linearLayout.addView(t, width, LinearLayout.LayoutParams.MATCH_PARENT);
        } else {
            for (int i = 0; i < stringArray.length; i++) {
                TView t = new TView(context, null, i == 0 ? styleStart : i == stringArray.length - 1 ? styleEnd : styleOther);
                t.setText(stringArray[i]);
                if (i == index) {
                    t.setSelect(true);
                }
                if (touchUpListener != null) {
                    t.setTouchUpListener(touchUpListener);
                }
                if (onClickListener != null) {
                    t.setOnClickListener(onClickListener);
                }
                tViewList.add(t);
                //
                int margin = (int) t.getStrokeWidthNormal();
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, height);
                if (i != 0 && i != stringArray.length - 1) {
                    switch (mode) {
                        case HORIZONTAL:
                            layoutParams.setMargins(-margin, 0, -margin, 0);
                            break;
                        case VERTICAL:
                            layoutParams.setMargins(0, -margin, 0, -margin);
                            break;
                        default:
                            break;
                    }
                }
                linearLayout.addView(t, layoutParams);
            }
            link(tViewList);
        }
    }

    public static void reset(TView[] tViewArray) {
        reset(tViewArray, 0);
    }

    public static void reset(TView[] tViewArray, int index) {
        int arraySize = tViewArray.length;
        for (int i = 0; i < arraySize; i++) {
            if (i == index) {
                tViewArray[i].setStatus(false, true);
            } else {
                tViewArray[i].setStatus(false, false);
            }
        }
    }

    public static void reset(List<TView> tViewList) {
        reset(tViewList, 0);
    }

    public static void reset(List<TView> tViewList, int index) {
        for (int i = 0; i < tViewList.size(); i++) {
            TView tView = tViewList.get(i);
            if (i == index) {
                tView.setStatus(false, true);
            } else {
                tView.setStatus(false, false);
            }
        }
    }

    public static int getIndex(List<TView> tViewList) {
        for (int i = 0; i < tViewList.size(); i++) {
            TView tView = tViewList.get(i);
            if (tView.isSelect()) {
                return i;
            }
        }
        return -1;
    }

    public static String getIndexText(List<TView> tViewList) {
        for (int i = 0; i < tViewList.size(); i++) {
            TView tView = tViewList.get(i);
            if (tView.isSelect()) {
                return tView.getText();
            }
        }
        return null;
    }

    public static String getIndexContent(List<TView> tViewList) {
        for (int i = 0; i < tViewList.size(); i++) {
            TView tView = tViewList.get(i);
            if (tView.isSelect()) {
                return tView.getContent();
            }
        }
        return null;
    }
}


