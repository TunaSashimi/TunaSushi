package com.tunasushi.tuna;


import android.databinding.BindingAdapter;

/**
 * @author TunaSashimi
 * @date 2020-03-16 23:34
 * @Copyright 2020 TunaSashimi. All rights reserved.
 * @Description
 */
public class TBinding {
    @BindingAdapter({"text"})
    public static void setText(TView t, String text) {
        final String oldText = t.getText();
        if (text == null || text == oldText || text.equals(oldText)) {
            return;
        }
        t.setText(text);
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
}
