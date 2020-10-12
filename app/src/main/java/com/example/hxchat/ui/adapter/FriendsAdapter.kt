package com.example.hxchat.ui.adapter

import android.util.Log
import androidx.annotation.Nullable
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.hxchat.BR
import com.example.hxchat.R
import com.example.hxchat.data.model.bean.User
import kotlinx.android.synthetic.main.rv_user_item.view.*

/**
 *Created by Pbihao
 * on 2020/10/8
 */
open class FriendsAdapter: BaseQuickAdapter<User, BindingHolder>(R.layout.rv_user_item) {

    override fun convert(helper: BindingHolder, item: User) {
        helper.mBinding?.let {
            it.setVariable(BR.data,item)
            it.executePendingBindings()
        }
        helper.setText(R.id.tvName, item.getShowName())
    }
}