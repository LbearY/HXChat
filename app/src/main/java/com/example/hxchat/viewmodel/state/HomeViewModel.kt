package com.example.hxchat.viewmodel.state

import android.app.Application
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.hxchat.data.model.bean.Message
import com.example.hxchat.data.model.bean.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import timber.log.Timber

class HomeViewModel(application: Application) : BaseViewModel(){
    var lastMessageLiveData = MutableLiveData<List<Message>>()

    var userLiveData = MutableLiveData<User>()

    var totalCountLiveData = MutableLiveData<Int>()

    var friendsLiveData = MediatorLiveData<List<User>>()

    fun retry(){

    }

    fun delay(x:Int){

    }


}
