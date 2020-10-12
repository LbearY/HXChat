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
        val user = User("1435343052@qq.com", "pbihao", "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2902873695,2157267194&fm=11&gp=0.jpg", "我的心已死")
        val list = ArrayList<User>()
        list.add(user)
        list.add(user)
        list.add(user)
        list.add(user)
        list.add(user)
        val req = ResultState.Success<ArrayList<User>>(list)
        friendsData.postValue(req)
    }
}