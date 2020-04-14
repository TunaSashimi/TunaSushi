package com.tunasushi.tuna;


import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.databinding.InverseBindingListener;

/**
 * @author TunaSashimi
 * @date 2020-03-16 23:34
 * @Copyright 2020 TunaSashimi. All rights reserved.
 * @Description
 */
public class TBinding {
    @BindingAdapter(value = "select")
    public static void setSelect(TView t, boolean select) {
        if (isSelect(t) != select) {
            t.setSelect(select);
        }
    }

    @InverseBindingAdapter(attribute = "select", event = "selectChange")
    public static boolean isSelect(TView t) {
        return t.isSelect();
    }

    @BindingAdapter(value = {"selectChange"})
    public static void setSelectChange(TView t, InverseBindingListener inverseBindingListener) {
        if (inverseBindingListener != null) {
            t.setInverseBindingListener(inverseBindingListener);
        }
    }

    @BindingAdapter({"text"})
    public static void setText(TView t, String text) {
        final String oldText = t.getText();
        if (text == null || text == oldText || text.equals(oldText)) {
            return;
        }
        t.setText(text);
    }

    @InverseBindingAdapter(attribute = "text", event = "textChange")
    public static String getText(TView t) {
        return t.getText();
    }

    @BindingAdapter(value = {"textChange"})
    public static void setTextChange(TView t, InverseBindingListener inverseBindingListener) {
        if (inverseBindingListener != null) {
            t.setInverseBindingListener(inverseBindingListener);
        }
    }

    @BindingAdapter({"textColorNormal"})
    public static void setTextColorNormal(TView t, int textColorNormal) {
        t.setTextColorNormal(textColorNormal);
    }

    @BindingAdapter({"textColorPress"})
    public static void setTextColorPress(TView t, int textColorPress) {
        t.setTextColorPress(textColorPress);
    }

    @BindingAdapter({"textColorSelect"})
    public static void setTextColorSelect(TView t, int textColorSelect) {
        t.setTextColorSelect(textColorSelect);
    }

    @BindingAdapter({"textColorNormal"})
    public static void setTextColorNormal(TView t, String textColorNormal) {
        t.setTextColorNormal(textColorNormal);
    }

    @BindingAdapter({"textColorPress"})
    public static void setTextColorPress(TView t, String textColorPress) {
        t.setTextColorPress(textColorPress);
    }

    @BindingAdapter({"textColorSelect"})
    public static void setTextColorSelect(TView t, String textColorSelect) {
        t.setTextColorSelect(textColorSelect);
    }

    @BindingAdapter({"content"})
    public static void setContent(TView t, String content) {
        final String oldContent = t.getContent();
        if (content == null || content == oldContent || content.equals(oldContent)) {
            return;
        }
        t.setContent(content);
    }

    @InverseBindingAdapter(attribute = "content", event = "contentChange")
    public static String getContent(TView t) {
        return t.getContent();
    }

    @BindingAdapter(value = {"contentChange"})
    public static void setContentChange(TView t, InverseBindingListener inverseBindingListener) {
        if (inverseBindingListener != null) {
            t.setInverseBindingListener(inverseBindingListener);
        }
    }

    @BindingAdapter({"contentColorNormal"})
    public static void setContentColorNormal(TView t, int contentColorNormal) {
        t.setTextColorSelect(contentColorNormal);
    }

    @BindingAdapter({"contentColorPress"})
    public static void setContentColorPress(TView t, int contentColorPress) {
        t.setTextColorSelect(contentColorPress);
    }

    @BindingAdapter({"contentColorSelect"})
    public static void setContentColorSelect(TView t, int contentColorSelect) {
        t.setTextColorSelect(contentColorSelect);
    }

    @BindingAdapter({"contentColorNormal"})
    public static void setContentColorNormal(TView t, String contentColorNormal) {
        t.setTextColorSelect(contentColorNormal);
    }

    @BindingAdapter({"contentColorPress"})
    public static void setContentColorPress(TView t, String contentColorPress) {
        t.setTextColorSelect(contentColorPress);
    }

    @BindingAdapter({"contentColorSelect"})
    public static void setContentColorSelect(TView t, String contentColorSelect) {
        t.setTextColorSelect(contentColorSelect);
    }

    @BindingAdapter({"lineMode"})
    public static void setLineMode(TLine tLine, int lineMode) {
        tLine.setLineMode(lineMode);
    }
}
