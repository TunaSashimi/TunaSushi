package com.tunasushi.tuna;

import android.content.Context;
import android.support.annotation.IntDef;
import android.widget.LinearLayout;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;


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
    public static void link(final TView[] tViewArray) {
        if (tViewArray == null) {
            return;
        }
        final int arraySize = tViewArray.length;
        for (int i = 0; i < arraySize; i++) {
            final int finalI = i;
            tViewArray[i].setAssociateListener(new TView.associateListener() {
                @Override
                public void associate(TView t) {
                    for (int j = 0; j < arraySize; j++) {
                        if (j != finalI) {
                            tViewArray[j].setStatus(false, false);
                        }
                    }
                }
            });
        }
    }

    //
    public static void link(final List<TView> tViewList) {
        if (tViewList == null) {
            return;
        }
        final int listSize = tViewList.size();
        for (int i = 0; i < listSize; i++) {
            final int finalI = i;
            tViewList.get(i).setAssociateListener(new TView.associateListener() {
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
                              TView.TouchUpListener touchUpListener,
                              LinearLayout linearLayout,
                              int width, int height,
                              int styleStart, int styleEnd, int styleOther,
                              @OrientationMode int mode) {
        int index = 0;
        for (int i = 0; i < stringArray.length; i++) {
            if (stringArray[i].equals(string)) {
                index = i;
                break;
            }
        }
        createRaw(stringArray, index, touchUpListener, null, linearLayout, width, height, styleStart, styleEnd, styleOther, mode);
    }

    //9
    public static void create(String[] stringArray,
                              String string,
                              TView.TouchUpListener touchUpListener,
                              LinearLayout linearLayout,
                              int width, int height,
                              int styleStart, int styleEnd, int styleOther) {
        int index = 0;
        for (int i = 0; i < stringArray.length; i++) {
            if (stringArray[i].equals(string)) {
                index = i;
                break;
            }
        }
        createRaw(stringArray, index, touchUpListener, null, linearLayout, width, height, styleStart, styleEnd, styleOther, HORIZONTAL);
    }

    public static void create(String[] stringArray,
                              String string,
                              TView.TouchUpListener touchUpListener,
                              TView.OnClickListener onClickListener,
                              LinearLayout linearLayout,
                              int width, int height,
                              int styleStart, int styleEnd, int styleOther) {
        int index = 0;
        for (int i = 0; i < stringArray.length; i++) {
            if (stringArray[i].equals(string)) {
                index = i;
                break;
            }
        }
        createRaw(stringArray, index, touchUpListener, onClickListener, linearLayout, width, height, styleStart, styleEnd, styleOther, HORIZONTAL);
    }

    //10
    public static void create(String[] stringArray,
                              String string,
                              TView.OnClickListener onClickListener,
                              LinearLayout linearLayout,
                              int width, int height,
                              int styleStart, int styleEnd, int styleOther,
                              @OrientationMode int mode) {
        int index = 0;
        for (int i = 0; i < stringArray.length; i++) {
            if (stringArray[i].equals(string)) {
                index = i;
                break;
            }
        }
        createRaw(stringArray, index, null, onClickListener, linearLayout, width, height, styleStart, styleEnd, styleOther, mode);
    }

    //10
    public static void create(String[] stringArray,
                              String string,
                              TView.TouchUpListener touchUpListener,
                              TView.OnClickListener onClickListener,
                              LinearLayout linearLayout,
                              int width, int height,
                              int styleStart, int styleEnd, int styleOther,
                              @OrientationMode int mode) {
        int index = 0;
        for (int i = 0; i < stringArray.length; i++) {
            if (stringArray[i].equals(string)) {
                index = i;
                break;
            }
        }
        createRaw(stringArray, index, touchUpListener, onClickListener, linearLayout, width, height, styleStart, styleEnd, styleOther, mode);
    }

    //9
    public static void create(String[] stringArray,
                              String string,
                              TView.OnClickListener onClickListener,
                              LinearLayout linearLayout,
                              int width, int height,
                              int styleStart, int styleEnd, int styleOther) {
        int index = 0;
        for (int i = 0; i < stringArray.length; i++) {
            if (stringArray[i].equals(string)) {
                index = i;
                break;
            }
        }
        createRaw(stringArray, index, null, onClickListener, linearLayout, width, height, styleStart, styleEnd, styleOther, HORIZONTAL);
    }

    //9
    public static void create(String[] stringArray,
                              TView.TouchUpListener touchUpListener,
                              LinearLayout linearLayout,
                              int width, int height,
                              int styleStart, int styleEnd, int styleOther,
                              @OrientationMode int mode) {

        createRaw(stringArray, 0, touchUpListener, null, linearLayout, width, height, styleStart, styleEnd, styleOther, mode);
    }

    //8
    public static void create(String[] stringArray,
                              TView.TouchUpListener touchUpListener,
                              LinearLayout linearLayout,
                              int width, int height,
                              int styleStart, int styleEnd, int styleOther) {

        createRaw(stringArray, 0, touchUpListener, null, linearLayout, width, height, styleStart, styleEnd, styleOther, HORIZONTAL);
    }

    //10
    public static void create(String[] stringArray,
                              TView.TouchUpListener touchUpListener,
                              TView.OnClickListener onClickListener,
                              LinearLayout linearLayout,
                              int width, int height,
                              int styleStart, int styleEnd, int styleOther,
                              @OrientationMode int mode) {

        createRaw(stringArray, 0, touchUpListener, onClickListener, linearLayout, width, height, styleStart, styleEnd, styleOther, mode);
    }

    //9
    public static void create(String[] stringArray,
                              TView.OnClickListener onClickListener,
                              LinearLayout linearLayout,
                              int width, int height,
                              int styleStart, int styleEnd, int styleOther,
                              @OrientationMode int mode) {

        createRaw(stringArray, 0, null, onClickListener, linearLayout, width, height, styleStart, styleEnd, styleOther, mode);
    }

    //9
    public static void create(String[] stringArray,
                              TView.TouchUpListener touchUpListener,
                              TView.OnClickListener onClickListener,
                              LinearLayout linearLayout,
                              int width, int height,
                              int styleStart, int styleEnd, int styleOther) {

        createRaw(stringArray, 0, touchUpListener, onClickListener, linearLayout, width, height, styleStart, styleEnd, styleOther, HORIZONTAL);
    }

    //8
    public static void create(String[] stringArray,
                              TView.OnClickListener onClickListener,
                              LinearLayout linearLayout,
                              int width, int height,
                              int styleStart, int styleEnd, int styleOther) {

        createRaw(stringArray, 0, null, onClickListener, linearLayout, width, height, styleStart, styleEnd, styleOther, HORIZONTAL);
    }

    //10
    public static void create(String[] stringArray,
                              int index,
                              TView.TouchUpListener touchUpListener,
                              LinearLayout linearLayout,
                              int width, int height,
                              int styleStart, int styleEnd, int styleOther,
                              @OrientationMode int mode) {

        createRaw(stringArray, index, touchUpListener, null, linearLayout, width, height, styleStart, styleEnd, styleOther, mode);
    }

    //9
    public static void create(String[] stringArray,
                              int index,
                              TView.TouchUpListener touchUpListener,
                              LinearLayout linearLayout,
                              int width, int height,
                              int styleStart, int styleEnd, int styleOther) {

        createRaw(stringArray, index, touchUpListener, null, linearLayout, width, height, styleStart, styleEnd, styleOther, HORIZONTAL);
    }

    //10
    public static void create(String[] stringArray,
                              int index,
                              TView.OnClickListener onClickListener,
                              LinearLayout linearLayout,
                              int width, int height,
                              int styleStart, int styleEnd, int styleOther,
                              @OrientationMode int mode) {

        createRaw(stringArray, index, null, onClickListener, linearLayout, width, height, styleStart, styleEnd, styleOther, mode);
    }

    //11
    public static void create(String[] stringArray,
                              int index,
                              TView.TouchUpListener touchUpListener,
                              TView.OnClickListener onClickListener,
                              LinearLayout linearLayout,
                              int width, int height,
                              int styleStart, int styleEnd, int styleOther,
                              @OrientationMode int mode) {

        createRaw(stringArray, index, touchUpListener, onClickListener, linearLayout, width, height, styleStart, styleEnd, styleOther, mode);
    }

    //9
    public static void create(String[] stringArray,
                              int index,
                              TView.OnClickListener onClickListener,
                              LinearLayout linearLayout,
                              int width, int height,
                              int styleStart, int styleEnd, int styleOther) {

        createRaw(stringArray, index, null, onClickListener, linearLayout, width, height, styleStart, styleEnd, styleOther, HORIZONTAL);
    }

    //10
    public static void create(String[] stringArray,
                              int index,
                              TView.TouchUpListener touchUpListener,
                              TView.OnClickListener onClickListener,
                              LinearLayout linearLayout,
                              int width, int height,
                              int styleStart, int styleEnd, int styleOther) {

        createRaw(stringArray, index, touchUpListener, onClickListener, linearLayout, width, height, styleStart, styleEnd, styleOther, HORIZONTAL);
    }

    //11
    private static void createRaw(String[] stringArray,
                                  int index,
                                  TView.TouchUpListener touchUpListener,
                                  TView.OnClickListener onClickListener,
                                  LinearLayout linearLayout,
                                  int width, int height,
                                  int styleStart, int styleEnd, int styleOther,
                                  @OrientationMode int mode) {

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


