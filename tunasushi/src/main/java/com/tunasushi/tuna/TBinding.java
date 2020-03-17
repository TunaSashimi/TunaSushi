package com.tunasushi.tuna;

import androidx.databinding.BindingAdapter;

/**
 * @author TunaSashimi
 * @date 2020-03-16 23:34
 * @Copyright 2020 TunaSashimi. All rights reserved.
 * @Description
 */
public class TBinding {
    @BindingAdapter({"content"})
    public static void setContent(TView t, String content) {
        t.setContent(content);
    }

    @BindingAdapter({"text"})
    public static void setText(TView t, String text) {
        t.setText(text);
    }
}
