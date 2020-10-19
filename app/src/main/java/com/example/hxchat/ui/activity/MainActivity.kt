package com.example.hxchat.ui.activity

import android.app.Application
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import com.example.hxchat.R
import com.example.hxchat.app.Constants
import com.example.hxchat.app.base.BaseActivity
import com.example.hxchat.data.model.bean.Operator
import com.example.hxchat.data.packet.resp.MessageResp
import com.example.hxchat.databinding.ActivityMainBinding
import com.example.hxchat.service.JWebSocketClient
import com.example.hxchat.service.JWebSocketClientService
import com.example.hxchat.viewmodel.state.MainViewModel
import com.example.hxchat.viewmodel.state.MessageViewModel
import com.example.hxchat.viewmodel.state.UsersViewModel
import com.king.easychat.netty.packet.Packet
import com.king.easychat.netty.packet.PacketType
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import timber.log.Timber

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {
    override fun layoutId(): Int = R.layout.activity_main

    private val messageViewModel : MessageViewModel by lazy {  ViewModelProvider(this).get(MessageViewModel::class.java) }
    private val usersViewModel: UsersViewModel by lazy { ViewModelProvider(this).get(UsersViewModel::class.java) }

    private var mContext: Context? = null
    private lateinit var client: JWebSocketClient
    private lateinit var binder: JWebSocketClientService.JWebSocketClientBinder
    private lateinit var jWebSClientService: JWebSocketClientService

    private val serviceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(componentName: ComponentName, iBinder: IBinder) {
            Log.e("MainActivity", "服务与活动成功绑定")
            binder = iBinder as JWebSocketClientService.JWebSocketClientBinder
            jWebSClientService = binder.getService()
            client = jWebSClientService.client
        }

        override fun onServiceDisconnected(componentName: ComponentName) {
            Log.e("MainActivity", "服务与活动成功断开")
        }
    }


    /**
     * 绑定服务
     */
    private fun bindService() {
        val bindIntent = Intent(mContext, JWebSocketClientService::class.java)
        bindService(bindIntent, serviceConnection, BIND_AUTO_CREATE)
    }

    /**
     * 启动服务（websocket客户端服务）
     */
    private fun startJWebSClientService() {
        val intent = Intent(mContext, JWebSocketClientService::class.java)
        startService(intent)
    }

    override fun initView(savedInstanceState: Bundle?) {
        mContext = this@MainActivity
        //启动服务
        startJWebSClientService()
        //绑定服务
        bindService()
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