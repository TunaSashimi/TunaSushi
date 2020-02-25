package com.tunasushi.tool;

import android.content.Context;
import android.widget.LinearLayout;

import com.tunasushi.tuna.TView;

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
public class GroupTool {

    //@IntDef的使用
    @IntDef({HORIZONTAL, VERTICAL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface OrientationMode {
    }

    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;


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
                    //The associate method of any TView is to set the status of other TViews to false!
                    for (int j = 0; j < listSize; j++) {
                        if (j != finalI) {
                            TViewList.get(j).setStatus(false, false);
                        }
                    }
                }
            });
        }
    }

    //10
    public static void dynamic(String[] stringArray,
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
        dynamicRaw(stringArray, index, touchUpListener, null, linearLayout, width, height, styleStart, styleEnd, styleOther, mode);
    }

    //9
    public static void dynamic(String[] stringArray,
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
        dynamicRaw(stringArray, index, touchUpListener, null, linearLayout, width, height, styleStart, styleEnd, styleOther, HORIZONTAL);
    }

    //10
    public static void dynamic(String[] stringArray,
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
        dynamicRaw(stringArray, index, null, onClickListener, linearLayout, width, height, styleStart, styleEnd, styleOther, mode);
    }

    //9
    public static void dynamic(String[] stringArray,
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
        dynamicRaw(stringArray, index, null, onClickListener, linearLayout, width, height, styleStart, styleEnd, styleOther, HORIZONTAL);
    }


    //9
    public static void dynamic(String[] stringArray,
                               TView.TouchUpListener touchUpListener,
                               LinearLayout linearLayout,
                               int width, int height,
                               int styleStart, int styleEnd, int styleOther,
                               @OrientationMode int mode) {

        dynamicRaw(stringArray, 0, touchUpListener, null, linearLayout, width, height, styleStart, styleEnd, styleOther, mode);
    }

    //8
    public static void dynamic(String[] stringArray,
                               TView.TouchUpListener touchUpListener,
                               LinearLayout linearLayout,
                               int width, int height,
                               int styleStart, int styleEnd, int styleOther) {

        dynamicRaw(stringArray, 0, touchUpListener, null, linearLayout, width, height, styleStart, styleEnd, styleOther, HORIZONTAL);
    }

    //9
    public static void dynamic(String[] stringArray,
                               TView.OnClickListener onClickListener,
                               LinearLayout linearLayout,
                               int width, int height,
                               int styleStart, int styleEnd, int styleOther,
                               @OrientationMode int mode) {

        dynamicRaw(stringArray, 0, null, onClickListener, linearLayout, width, height, styleStart, styleEnd, styleOther, mode);
    }

    //8
    public static void dynamic(String[] stringArray,
                               TView.OnClickListener onClickListener,
                               LinearLayout linearLayout,
                               int width, int height,
                               int styleStart, int styleEnd, int styleOther) {

        dynamicRaw(stringArray, 0, null, onClickListener, linearLayout, width, height, styleStart, styleEnd, styleOther, HORIZONTAL);
    }

    //10
    public static void dynamic(String[] stringArray,
                               int index,
                               TView.TouchUpListener touchUpListener,
                               LinearLayout linearLayout,
                               int width, int height,
                               int styleStart, int styleEnd, int styleOther,
                               @OrientationMode int mode) {

        dynamicRaw(stringArray, index, touchUpListener, null, linearLayout, width, height, styleStart, styleEnd, styleOther, mode);
    }

    //9
    public static void dynamic(String[] stringArray,
                               int index,
                               TView.TouchUpListener touchUpListener,
                               LinearLayout linearLayout,
                               int width, int height,
                               int styleStart, int styleEnd, int styleOther) {

        dynamicRaw(stringArray, index, touchUpListener, null, linearLayout, width, height, styleStart, styleEnd, styleOther, HORIZONTAL);
    }

    //10
    public static void dynamic(String[] stringArray,
                               int index,
                               TView.OnClickListener onClickListener,
                               LinearLayout linearLayout,
                               int width, int height,
                               int styleStart, int styleEnd, int styleOther,
                               @OrientationMode int mode) {

        dynamicRaw(stringArray, index, null, onClickListener, linearLayout, width, height, styleStart, styleEnd, styleOther, mode);
    }

    //9
    public static void dynamic(String[] stringArray,
                               int index,
                               TView.OnClickListener onClickListener,
                               LinearLayout linearLayout,
                               int width, int height,
                               int styleStart, int styleEnd, int styleOther) {

        dynamicRaw(stringArray, index, null, onClickListener, linearLayout, width, height, styleStart, styleEnd, styleOther, HORIZONTAL);
    }

    //11
    private static void dynamicRaw(String[] stringArray,
                                   int index,
                                   TView.TouchUpListener touchUpListener,
                                   TView.OnClickListener onClickListener,
                                   LinearLayout linearLayout,
                                   int width, int height,
                                   int styleStart, int styleEnd, int styleOther,
                                   @OrientationMode int mode) {

        Context context = linearLayout.getContext();
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
                TView t = new TView(context, null, i == 0 ? styleStart : i == stringArray.length - 1 ? styleEnd : styleOther);
                t.setTextValue(stringArray[i]);
                if (i == index) {
                    t.setSelect(true);
                }
                t.setTouchUpListener(touchUpListener);
                TViewList.add(t);
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
            associate(TViewList);
        }
    }
}
