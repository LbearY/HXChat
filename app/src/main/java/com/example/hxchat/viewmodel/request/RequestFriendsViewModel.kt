package com.example.hxchat.viewmodel.request

import android.app.Application
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.hxchat.app.network.apiService
import com.example.hxchat.app.network.stateCallback.ListDataiState
import com.example.hxchat.data.model.bean.User
import com.example.hxchat.data.repository.HttpRequestCoroutline
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.ext.request
import me.hgj.jetpackmvvm.state.ResultState


/**
 *Created by Pbihao
 * on 2020/10/8
 */
class RequestFriendsViewModel : BaseViewModel(){
    var friendsData : MutableLiveData<ResultState<ArrayList<User>>> = MutableLiveData()

    /**
     * 获取用户的好友列表
     */
    fun getfriends(){
        request({ apiService.getFriends()},{
            friendsData
        })
    }
}