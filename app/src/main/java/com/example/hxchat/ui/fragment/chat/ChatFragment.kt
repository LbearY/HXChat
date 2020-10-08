package com.example.hxchat.ui.fragment.chat

import android.os.Bundle
import android.view.View
import com.example.hxchat.R
import com.example.hxchat.app.base.BaseFragment
import com.example.hxchat.databinding.FragmentChatBinding
import com.example.hxchat.viewmodel.state.ChatViewModel

/**
 *Created by Pbihao
 * on 2020/10/4
 */

class ChatFragment : BaseFragment<ChatViewModel, FragmentChatBinding>(){

    override fun layoutId(): Int = R.layout.fragment_chat

    override fun initView(savedInstanceState: Bundle?) {

    }

    fun onClick(v: View){

    }
}