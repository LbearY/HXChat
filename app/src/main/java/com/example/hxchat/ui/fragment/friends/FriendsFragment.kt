package com.example.hxchat.ui.fragment.friends

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.hxchat.R
import com.example.hxchat.app.base.BaseFragment
import com.example.hxchat.data.model.bean.User
import com.example.hxchat.databinding.FragmentFriendsBinding
import com.example.hxchat.viewmodel.state.FriendsViewModel
import com.king.frame.mvvmframe.bean.Resource

/**
 *Created by Pbihao
 * on 2020/10/4
 */

class FriendsFragment:BaseFragment<FriendsViewModel, FragmentFriendsBinding>(){

    override fun layoutId() : Int = R.layout.fragment_friends

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.vm = mViewModel
    }
}