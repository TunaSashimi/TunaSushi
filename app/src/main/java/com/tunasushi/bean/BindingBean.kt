package com.tunasushi.bean

import androidx.databinding.BaseObservable
import androidx.databinding.ObservableField
import androidx.databinding.ObservableBoolean

/**
 * @author TunaSashimi
 * @date 2020-04-11 23:42
 * @Copyright 2020 TunaSashimi. All rights reserved.
 * @Description
 */
class BindingBean : BaseObservable() {
    @JvmField
    var name: ObservableField<String?> = ObservableField("hello")
    @JvmField
    var select = ObservableBoolean()
}