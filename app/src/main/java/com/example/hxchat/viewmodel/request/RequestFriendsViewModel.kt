package com.example.hxchat.viewmodel.request

import android.app.Application
import android.util.Log
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.hxchat.app.network.apiService
import com.example.hxchat.app.network.stateCallback.ListDataiState
import com.example.hxchat.data.model.bean.User
import com.example.hxchat.data.repository.HttpRequestCoroutline
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.ext.request
import me.hgj.jetpackmvvm.state.ResultState
import org.greenrobot.eventbus.EventBus


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
        /**
        request({ apiService.getFriends()},
            friendsData
        )*/
        val user = User("1435343052@qq.com", "pbihao", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1602560792178&di=e495ca3c2ba592c1c52e5668bf7169a7&imgtype=0&src=http%3A%2F%2Fc-ssl.duitang.com%2Fuploads%2Fitem%2F202006%2F22%2F20200622133909_rftue.thumb.400_0.jpeg",
        signature = "好累啊", remark = "彭博濠")

        val list = ArrayList<User>()
        list.add(user)
        list.add(user)
        list.add(user)
        val res = ResultState.Success<ArrayList<User>>(list)
        friendsData.postValue(res)

    }
}