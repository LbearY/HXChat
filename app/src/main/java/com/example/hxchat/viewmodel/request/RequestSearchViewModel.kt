package com.example.hxchat.viewmodel.request

import androidx.lifecycle.MutableLiveData
import com.example.hxchat.app.network.apiService
import com.example.hxchat.data.model.bean.User
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
import java.io.File

/**
 *Created by Pbihao
 * on 2020/10/6
 */

class RequestSearchViewModel : BaseViewModel(){
    var searchResultData : MutableLiveData<ResultState<ArrayList<User>>> = MutableLiveData()

    /**
     * 用户输入一串字符串在数据库中寻找和这个字符串最匹配的结果（最多6个，然后返回回来
     * 这个请求需要显示等待框，所以如果超过一定的时间没有响应需要提前推出）
     */
    fun search(nickname: String) {
        request(
            { apiService.search(nickname) },
            searchResultData
        )
    }
}