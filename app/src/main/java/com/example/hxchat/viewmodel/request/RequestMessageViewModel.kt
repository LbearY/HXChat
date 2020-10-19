package com.example.hxchat.viewmodel.request

import androidx.lifecycle.MutableLiveData
import com.example.hxchat.app.network.apiService
import com.example.hxchat.app.util.Event
import com.example.hxchat.data.model.bean.UserInfo
import com.example.hxchat.data.packet.req.MessageReq
import com.example.hxchat.data.packet.resp.MessageResp
import com.example.hxchat.data.repository.HttpRequestCoroutline
import com.king.easychat.netty.packet.MessageType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.ext.request
import me.hgj.jetpackmvvm.state.ResultState
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import java.io.File

/**
 *Created by Pbihao
 * on 2020/10/6
 */

class RequestMessageViewModel : BaseViewModel(){
    var messageReq : MutableLiveData<ResultState<MessageResp>> = MutableLiveData()

    /**
     * 发送消息
     */
    fun sendMessage(receiver: String,message: String,messageType: Int) {
        Event.sendEvent(MessageReq(receiver, message, messageType))
        val parm = JSONObject()
        parm.put("receiver", receiver)
        parm.put("message", message)
        parm.put("messageType", messageType)
        val requestBody = RequestBody.create(
            MediaType.parse("application/json"),
            parm.toString()
        )
        request(
            { apiService.sendMessage(requestBody) },
            messageReq
        )

    }
}