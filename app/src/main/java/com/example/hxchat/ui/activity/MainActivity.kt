package com.example.hxchat.ui.activity

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import com.example.hxchat.R
import com.example.hxchat.app.Constants
import com.example.hxchat.app.base.BaseActivity
import com.example.hxchat.data.model.bean.Operator
import com.example.hxchat.data.packet.resp.MessageResp
import com.example.hxchat.databinding.ActivityMainBinding
import com.example.hxchat.viewmodel.state.MainViewModel
import com.example.hxchat.viewmodel.state.MessageViewModel
import com.king.easychat.netty.packet.Packet
import com.king.easychat.netty.packet.PacketType
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import timber.log.Timber

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {
    override fun layoutId(): Int = R.layout.activity_main

    private val messageViewModel : MessageViewModel by lazy {  ViewModelProvider(this).get(MessageViewModel::class.java) }

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun createObserver() {
        super.createObserver()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    override fun onMessageEvent(event: Operator){
        when (event.event) {
            Constants.EVENT_UPDATE_MESSAGE_READ -> handleMessageRead()
        }
    }

    private fun handleMessageRead(){
        appViewModel.friendEmail.value?.let {
            messageViewModel.updateMessageRead(getUserEmail(),it)
        }
        appViewModel.friendEmail.postValue(null)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: Packet){
        when(event.packetType()){
            PacketType.SEND_MESSAGE_RESP -> handleMessageResp(event as MessageResp)
        }
    }

    private fun handleMessageResp(data: MessageResp){
        val read = data.sender == appViewModel.friendEmail.value
        messageViewModel.saveMessage(getUserEmail(), data.sender!!, data.senderName,null, read, data)
    }
}