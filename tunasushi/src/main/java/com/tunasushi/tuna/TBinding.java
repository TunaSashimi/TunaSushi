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

    @BindingAdapter({"textSize"})
    public static void setTextSize(TView t, float textSize) {
        t.setTextSize(textSize);
    }

    @BindingAdapter({"textColorNormal"})
    public static void setTextColorNormal(TView t, int textColorNormal) {
        t.setTextColor(textColorNormal);
    }

    @BindingAdapter({"textColorNormal"})
    public static void setTextColorNormal(TView t, String textColorNormal) {
        t.setTextColor(textColorNormal);
    }

    @BindingAdapter({"textColorPress"})
    public static void setTextColorPress(TView t, int textColorPress) {
        t.setTextColorPress(textColorPress);
    }

    @BindingAdapter({"textColorPress"})
    public static void setTextColorPress(TView t, String textColorPress) {
        t.setTextColorPress(textColorPress);
    }

    @BindingAdapter({"textColorSelect"})
    public static void setTextColorSelect(TView t, int textColorSelect) {
        t.setTextColorSelect(textColorSelect);
    }

    @BindingAdapter({"textColorSelect"})
    public static void setTextColorSelect(TView t, String textColorSelect) {
        t.setTextColorSelect(textColorSelect);
    }

    @BindingAdapter({"textPaddingLeft"})
    public static void setTextPaddingLeft(TView t, float textPaddingLeft) {
        t.setTextPaddingLeft(textPaddingLeft);
    }

    @BindingAdapter({"textPaddingRight"})
    public static void setTextPaddingRight(TView t, float textPaddingRight) {
        t.setTextPaddingRight(textPaddingRight);
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

    @BindingAdapter({"contentSize"})
    public static void setContentSize(TView t, float contentSize) {
        t.setContentSize(contentSize);
    }

    @BindingAdapter({"contentColorNormal"})
    public static void setContentColorNormal(TView t, int contentColorNormal) {
        t.setContentColor(contentColorNormal);
    }

    @BindingAdapter({"contentColorNormal"})
    public static void setContentColorNormal(TView t, String contentColorNormal) {
        t.setContentColor(contentColorNormal);
    }

    @BindingAdapter({"contentColorPress"})
    public static void setContentColorPress(TView t, int contentColorPress) {
        t.setContentColorPress(contentColorPress);
    }

    @BindingAdapter({"contentColorPress"})
    public static void setContentColorPress(TView t, String contentColorPress) {
        t.setContentColorPress(contentColorPress);
    }

    @BindingAdapter({"contentColorSelect"})
    public static void setContentColorSelect(TView t, int contentColorSelect) {
        t.setContentColorSelect(contentColorSelect);
    }

    @BindingAdapter({"contentColorSelect"})
    public static void setContentColorSelect(TView t, String contentColorSelect) {
        t.setContentColorSelect(contentColorSelect);
    }

    @BindingAdapter({"contentPaddingLeft"})
    public static void setContentPaddingLeft(TView t, float contentPaddingLeft) {
        t.setContentPaddingLeft(contentPaddingLeft);
    }

    @BindingAdapter({"contentPaddingRight"})
    public static void setContentPaddingRight(TView t, float contentPaddingRight) {
        t.setContentPaddingRight(contentPaddingRight);
    }

    //
    @BindingAdapter({"backgroundNormal"})
    public static void setBackgroundNormal(TView t, int backgroundNormal) {
        t.setBackground(backgroundNormal);
    }

    @BindingAdapter({"backgroundPress"})
    public static void setBackgroundPress(TView t, int backgroundPress) {
        t.setBackgroundPress(backgroundPress);
    }

    @BindingAdapter({"backgroundSelect"})
    public static void setBackgroundSelect(TView t, int backgroundSelect) {
        t.setBackgroundSelect(backgroundSelect);
    }

    @BindingAdapter({"backgroundNormal"})
    public static void setBackgroundNormal(TView t, String backgroundNormal) {
        t.setBackground(backgroundNormal);
    }

    @BindingAdapter({"backgroundPress"})
    public static void setBackgroundPress(TView t, String backgroundPress) {
        t.setBackgroundPress(backgroundPress);
    }

    @BindingAdapter({"backgroundSelect"})
    public static void setBackgroundSelect(TView t, String backgroundSelect) {
        t.setBackgroundSelect(backgroundSelect);
    }

    //
    @BindingAdapter({"strokeColorNormal"})
    public static void setStrokeColorNormal(TView t, int strokeColorNormal) {
        t.setStrokeColor(strokeColorNormal);
    }

    @BindingAdapter({"strokeColorNormal"})
    public static void setStrokeColorNormal(TView t, String strokeColorNormal) {
        t.setStrokeColor(strokeColorNormal);
    }

    @BindingAdapter({"strokeColorPress"})
    public static void setStrokeColorPress(TView t, int strokeColorPress) {
        t.setStrokeColorPress(strokeColorPress);
    }

    @BindingAdapter({"strokeColorPress"})
    public static void setStrokeColorPress(TView t, String strokeColorPress) {
        t.setStrokeColorPress(strokeColorPress);
    }

    @BindingAdapter({"strokeColorSelect"})
    public static void setStrokeColorSelect(TView t, int strokeColorSelect) {
        t.setStrokeColorSelect(strokeColorSelect);
    }

    @BindingAdapter({"strokeColorSelect"})
    public static void setStrokeColorSelect(TView t, String strokeColorSelect) {
        t.setStrokeColorSelect(strokeColorSelect);
    }

    @BindingAdapter({"backgroundStartNormal"})
    public static void setBackgroundStartNormal(TView t, int backgroundStart) {
        t.setBackgroundStart(backgroundStart);
    }

    @BindingAdapter({"backgroundEndNormal"})
    public static void setBackgroundEndNormal(TView t, int backgroundEnd) {
        t.setBackgroundEnd(backgroundEnd);
    }

    @BindingAdapter({"lineMode"})
    public static void setLineMode(TLine tLine, int lineMode) {
        tLine.setLineMode(lineMode);
    }

    @BindingAdapter({"layoutBackgroundAngle"})
    public static void setLayoutBackgroundAngle(TLayout tLayout, int layoutBackgroundAngle) {
        tLayout.setLayoutBackgroundAngle(layoutBackgroundAngle);
    }

    @BindingAdapter({"layoutBackgroundStart"})
    public static void setLayoutBackgroundStart(TLayout tLayout, int layoutBackgroundStart) {
        tLayout.setLayoutBackgroundStart(layoutBackgroundStart);
    }

    @BindingAdapter({"layoutBackgroundStart"})
    public static void setLayoutBackgroundStart(TLayout tLayout, String layoutBackgroundStart) {
        tLayout.setLayoutBackgroundStart(layoutBackgroundStart);
    }

    @BindingAdapter({"layoutBackgroundEnd"})
    public static void setLayoutBackgroundEnd(TLayout tLayout, int layoutBackgroundEnd) {
        tLayout.setLayoutBackgroundEnd(layoutBackgroundEnd);
    }

    @BindingAdapter({"layoutBackgroundEnd"})
    public static void setLayoutBackgroundEnd(TLayout tLayout, String layoutBackgroundEnd) {
        tLayout.setLayoutBackgroundEnd(layoutBackgroundEnd);
    }
}
