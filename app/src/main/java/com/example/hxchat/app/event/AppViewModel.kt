package com.example.hxchat.app.event

import com.example.hxchat.app.util.CacheUtil
import com.example.hxchat.data.model.bean.UserInfo
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.callback.livedata.UnPeekLiveData

class AppViewModel:BaseViewModel(){

    var userInfo = UnPeekLiveData<UserInfo>()

    init {
        userInfo.value = CacheUtil.getUser()
    }
}