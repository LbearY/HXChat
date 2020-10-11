package com.example.hxchat.ui.adapter

import androidx.annotation.Nullable
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.hxchat.BR

/**
 *Created by Pbihao
 * on 2020/10/8
 */
open class BindingAdapter<T> : BaseQuickAdapter<T, BindingHolder> {

    constructor(layoutResId: Int) : super(layoutResId)

    override fun convert(helper: BindingHolder, item: T) {
        helper.mBinding?.let {
            it.setVariable(BR.data,item)
            it.executePendingBindings()
        }
    }
}