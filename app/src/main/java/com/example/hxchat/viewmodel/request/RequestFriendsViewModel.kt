package com.example.hxchat.viewmodel.request

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.hxchat.app.network.stateCallback.ListDataiState
import com.example.hxchat.data.model.bean.User
import com.example.hxchat.data.repository.HttpRequestCoroutline
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.ext.request


/**
 *Created by Pbihao
 * on 2020/10/8
 */
class RequestFriendsViewModel : BaseViewModel(){
    var friendsDataState : MutableLiveData<ListDataiState<User>> = MutableLiveData()

    /**
     * 获取用户的好友列表
     */
    fun getFriends(){

        request({ HttpRequestCoroutline.getFriendsData()})
    }
}