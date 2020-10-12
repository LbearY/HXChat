package com.example.hxchat.ui.fragment.userinfo

import android.os.Bundle
import com.example.hxchat.R
import com.example.hxchat.app.base.BaseFragment
import com.example.hxchat.databinding.FragmentMeUserInfoBinding
import com.example.hxchat.viewmodel.state.MeUserInfoViewModel

/**
 *Created by Pbihao
 * on 2020/10/11
 */

class meUserInfoFragment: BaseFragment<MeUserInfoViewModel, FragmentMeUserInfoBinding>(){
    override fun layoutId(): Int = R.layout.fragment_me_user_info

    override fun initView(savedInstanceState: Bundle?) {

    }
}