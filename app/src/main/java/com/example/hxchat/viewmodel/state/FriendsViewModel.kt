package com.example.hxchat.viewmodel.state

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.hxchat.data.model.bean.User
import com.king.frame.mvvmframe.bean.Resource
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel

class FriendsViewModel : BaseViewModel(){
    var friendsLiveData = MediatorLiveData<List<User>>()

    var source: LiveData<Resource<List<User>>>? = null

    fun retry(){

    }
}
