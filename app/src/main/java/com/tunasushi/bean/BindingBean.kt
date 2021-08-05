package com.tunasushi.bean;


import androidx.databinding.BaseObservable;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

/**
 * @author TunaSashimi
 * @date 2020-04-11 23:42
 * @Copyright 2020 TunaSashimi. All rights reserved.
 * @Description
 */
public class BindingBean extends BaseObservable {
    public ObservableField<String> name = new ObservableField("hello");
    public ObservableBoolean select = new ObservableBoolean();
}