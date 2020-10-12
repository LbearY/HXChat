package com.example.hxchat.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import timber.log.Timber
import com.example.hxchat.BR

/**
 *Created by Pbihao
 * on 2020/10/8
 */
open class BindingAdapter<T> : BaseQuickAdapter<T, BindingHolder> {

    constructor(layoutResId: Int, @Nullable data: List<T>) : super(layoutResId, data)

    constructor(@Nullable data: List<T>) : super(data)

    constructor(layoutResId: Int) : super(layoutResId)

    override fun convert(helper: BindingHolder, item: T) {
        helper.mBinding?.let {
            Log.d("asdf", "开始输出")
            it.setVariable(BR.data, item)
            it.executePendingBindings()
        }
    }

}