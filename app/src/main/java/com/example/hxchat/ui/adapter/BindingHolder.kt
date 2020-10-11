package com.example.hxchat.ui.adapter

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.chad.library.adapter.base.BaseViewHolder

/**
 *Created by Pbihao
 * on 2020/10/8
 */
class BindingHolder(view: View) : BaseViewHolder(view) {

    var mBinding: ViewDataBinding? = null
    init {
        try {
            mBinding = DataBindingUtil.bind(view)!!
        } catch (e: Exception) {

        }

    }
}