package com.tunasushi.view;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.RelativeLayout;

import com.tunasushi.tool.BitmapTool;

import java.util.List;

import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;
import androidx.databinding.InverseBindingListener;

/**
 * @author TunaSashimi
 * @date 2020-03-16 23:34
 * @Copyright 2020 TunaSashimi. All rights reserved.
 * @Description
 */
public class TBinding {
    @BindingAdapter({"layout_above"})
    public static void setLayoutAbove(TView t, int itemId) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) t.getLayoutParams();
        layoutParams.addRule(RelativeLayout.ABOVE, itemId);
    }

    @BindingAdapter({"layout_below"})
    public static void setlayoutBelow(TView t, int itemId) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) t.getLayoutParams();
        layoutParams.addRule(RelativeLayout.BELOW, itemId);
    }

    @BindingAdapter({"layout_toLeftOf"})
    public static void setLayoutToLeftOf(TView t, int itemId) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) t.getLayoutParams();
        layoutParams.addRule(RelativeLayout.LEFT_OF, itemId);
    }

    @BindingAdapter({"layout_toRightOf"})
    public static void setLayoutToRightOf(TView t, int itemId) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) t.getLayoutParams();
        layoutParams.addRule(RelativeLayout.RIGHT_OF, itemId);
    }

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

    @BindingAdapter({"backgroundStartNormal"})
    public static void setBackgroundStartNormal(TView t, String backgroundStart) {
        t.setBackgroundStart(backgroundStart);
    }

    @BindingAdapter({"backgroundEndNormal"})
    public static void setBackgroundEndNormal(TView t, int backgroundEnd) {
        t.setBackgroundEnd(backgroundEnd);
    }

    @BindingAdapter({"backgroundEndNormal"})
    public static void setBackgroundEndNormal(TView t, String backgroundEnd) {
        t.setBackgroundEnd(backgroundEnd);
    }

    @BindingAdapter({"srcLeftDxNormal"})
    public static void setSrcLeftDxNormal(TView t, float srcLeftDxNormal) {
        t.setSrcLeftDx(srcLeftDxNormal);
    }

    @BindingAdapter({"srcLeftDxPress"})
    public static void setSrcLeftDxPress(TView t, float srcLeftDxPress) {
        t.setSrcLeftDxPress(srcLeftDxPress);
    }

    @BindingAdapter({"srcLeftDxSelect"})
    public static void setSrcLeftDxSelect(TView t, float srcLeftDxSelect) {
        t.setSrcLeftDxSelect(srcLeftDxSelect);
    }

    @BindingAdapter({"srcLeftDyNormal"})
    public static void setSrcLeftDyNormal(TView t, float srcLeftDyNormal) {
        t.setSrcLeftDy(srcLeftDyNormal);
    }

    @BindingAdapter({"srcLeftDyPress"})
    public static void setSrcLeftDyPress(TView t, float srcLeftDyPress) {
        t.setSrcLeftDyPress(srcLeftDyPress);
    }

    @BindingAdapter({"srcLeftDySelect"})
    public static void setSrcLeftDySelect(TView t, float srcLeftDySelect) {
        t.setSrcLeftDySelect(srcLeftDySelect);
    }

    @BindingAdapter({"srcRightDxNormal"})
    public static void setSrcRightDxNormal(TView t, float srcRightDxNormal) {
        t.setSrcRightDx(srcRightDxNormal);
    }

    @BindingAdapter({"srcRightDxPress"})
    public static void setSrcRightDxPress(TView t, float srcRightDxPress) {
        t.setSrcRightDxPress(srcRightDxPress);
    }

    @BindingAdapter({"srcRightDxSelect"})
    public static void setSrcRightDxSelect(TView t, float srcRightDxSelect) {
        t.setSrcRightDxSelect(srcRightDxSelect);
    }

    @BindingAdapter({"srcRightDyNormal"})
    public static void setSrcRightDyNormal(TView t, float srcRightDyNormal) {
        t.setSrcRightDy(srcRightDyNormal);
    }

    @BindingAdapter({"srcRightDyPress"})
    public static void setSrcRightDyPress(TView t, float srcRightDyPress) {
        t.setSrcRightDyPress(srcRightDyPress);
    }

    @BindingAdapter({"srcRightDySelect"})
    public static void setSrcRightDySelect(TView t, float srcRightDySelect) {
        t.setSrcRightDySelect(srcRightDySelect);
    }

    @BindingAdapter({"srcLeftWidthNormal"})
    public static void setSrcLeftWidthNormal(TView t, float srcLeftWidthNormal) {
        t.setSrcLeftWidth(srcLeftWidthNormal);
    }

    @BindingAdapter({"srcLeftWidthPress"})
    public static void setSrcLeftWidthPress(TView t, float srcLeftWidthPress) {
        t.setSrcLeftWidthPress(srcLeftWidthPress);
    }

    @BindingAdapter({"srcLeftWidthSelect"})
    public static void setSrcLeftWidthSelect(TView t, float srcLeftWidthSelect) {
        t.setSrcLeftWidthSelect(srcLeftWidthSelect);
    }

    @BindingAdapter({"srcLeftHeightNormal"})
    public static void setSrcLeftHeightNormal(TView t, float srcLeftHeightNormal) {
        t.setSrcLeftHeight(srcLeftHeightNormal);
    }

    @BindingAdapter({"srcLeftHeightPress"})
    public static void setSrcLeftHeightPress(TView t, float srcLeftHeightPress) {
        t.setSrcLeftHeightPress(srcLeftHeightPress);
    }

    @BindingAdapter({"srcLeftHeightSelect"})
    public static void setSrcLeftHeightSelect(TView t, float srcLeftHeightSelect) {
        t.setSrcLeftHeightSelect(srcLeftHeightSelect);
    }

    @BindingAdapter({"srcRightWidthNormal"})
    public static void setSrcRightWidthNormal(TView t, float srcRightWidthNormal) {
        t.setSrcRightWidth(srcRightWidthNormal);
    }

    @BindingAdapter({"srcRightWidthPress"})
    public static void setSrcRightWidthPress(TView t, float srcRightWidthPress) {
        t.setSrcRightWidthPress(srcRightWidthPress);
    }

    @BindingAdapter({"srcRightWidthSelect"})
    public static void setSrcRightWidthSelect(TView t, float srcRightWidthSelect) {
        t.setSrcRightWidthSelect(srcRightWidthSelect);
    }

    @BindingAdapter({"srcRightHeightNormal"})
    public static void setSrcRightHeightNormal(TView t, float srcRightHeightNormal) {
        t.setSrcRightHeight(srcRightHeightNormal);
    }

    @BindingAdapter({"srcRightHeightPress"})
    public static void setSrcRightHeightPress(TView t, float srcRightHeightPress) {
        t.setSrcRightHeightPress(srcRightHeightPress);
    }

    @BindingAdapter({"srcRightHeightSelect"})
    public static void setSrcRightHeightSelect(TView t, float srcRightHeightSelect) {
        t.setSrcRightHeightSelect(srcRightHeightSelect);
    }

    @BindingAdapter({"srcLeftNormal"})
    public static void setSrcLeftNormal(TView t, Bitmap srcLeftNormal) {
        t.setSrcLeft(srcLeftNormal);
    }

    @BindingAdapter({"srcLeftPress"})
    public static void setSrcLeftPress(TView t, Bitmap srcLeftPress) {
        t.setSrcLeftPress(srcLeftPress);
    }

    @BindingAdapter({"srcLeftSelect"})
    public static void setSrcLeftSelect(TView t, Bitmap srcLeftSelect) {
        t.setSrcLeftSelect(srcLeftSelect);
    }

    @BindingAdapter({"srcLeftNormal"})
    public static void setSrcLeftNormal(TView t, Drawable srcLeftNormal) {
        t.setSrcLeft(BitmapTool.drawableToBitmap(srcLeftNormal));
    }

    @BindingAdapter({"srcLeftPress"})
    public static void setSrcLeftPress(TView t, Drawable srcLeftPress) {
        t.setSrcLeftPress(BitmapTool.drawableToBitmap(srcLeftPress));
    }

    @BindingAdapter({"srcLeftSelect"})
    public static void setSrcLeftSelect(TView t, Drawable srcLeftSelect) {
        t.setSrcLeftSelect(BitmapTool.drawableToBitmap(srcLeftSelect));
    }

    @BindingAdapter({"srcRightNormal"})
    public static void setSrcRightNormal(TView t, Bitmap srcRightNormal) {
        t.setSrcRight(srcRightNormal);
    }

    @BindingAdapter({"srcRightPress"})
    public static void setSrcRightPress(TView t, Bitmap srcRightPress) {
        t.setSrcRightPress(srcRightPress);
    }

    @BindingAdapter({"srcRightSelect"})
    public static void setSrcRightSelect(TView t, Bitmap srcRightSelect) {
        t.setSrcRightSelect(srcRightSelect);
    }

    @BindingAdapter({"srcRightNormal"})
    public static void setSrcRightNormal(TView t, Drawable srcRightNormal) {
        t.setSrcRight(BitmapTool.drawableToBitmap(srcRightNormal));
    }

    @BindingAdapter({"srcRightPress"})
    public static void setSrcRightPress(TView t, Drawable srcRightPress) {
        t.setSrcRightPress(BitmapTool.drawableToBitmap(srcRightPress));
    }

    @BindingAdapter({"srcRightSelect"})
    public static void setSrcRightSelect(TView t, Drawable srcRightSelect) {
        t.setSrcRightSelect(BitmapTool.drawableToBitmap(srcRightSelect));
    }

    //
    @BindingAdapter({"lineStyle"})
    public static void setLineStyle(TLine tLine, int lineStyle) {
        tLine.setLineStyle(lineStyle);
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

    @BindingAdapter({"wrapTextArray"})
    public static void setWrapTextArray(TWrap tWrap, String[] wrapTextArray) {
        System.out.println("setWrapTextArray==>String[]==>");
        if (wrapTextArray != null) {
            System.out.println("length==>" + wrapTextArray.length);
        }
        tWrap.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        tWrap.setWrapTextArray(wrapTextArray);
    }

    @BindingAdapter({"wrapTextArray"})
    public static void setWrapTextArray(TWrap tWrap, List<String> wrapTextArray) {

        System.out.println("setWrapTextArray==>List==>");
        if (wrapTextArray != null) {
            System.out.println("size==>" + wrapTextArray.size());
        }
    }
}
