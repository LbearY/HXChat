package com.example.hxchat.viewmodel.request

import android.text.method.MultiTapKeyListener
import androidx.lifecycle.MutableLiveData
import com.example.hxchat.data.model.bean.User
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.state.ResultState


/**
 *Created by Pbihao
 * on 2020/10/12
 */
class RequestUserInfoViewModel : BaseViewModel(){
    var user : MutableLiveData<ResultState<ArrayList<User>>> = MutableLiveData()
    var addFriendsUserInfo : MutableLiveData<ResultState<User>> = MutableLiveData()

    /**
     * 请求添加好友
     */
    fun addFriend(email: String){

    }
}