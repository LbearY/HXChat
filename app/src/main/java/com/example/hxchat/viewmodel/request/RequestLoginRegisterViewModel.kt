package com.example.hxchat.viewmodel.request

import androidx.lifecycle.MutableLiveData
import com.example.hxchat.app.network.apiService
import com.example.hxchat.data.model.bean.UserInfo
import com.example.hxchat.data.repository.HttpRequestCoroutline
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.ext.request
import me.hgj.jetpackmvvm.state.ResultState

/**
 *Created by Pbihao
 * on 2020/10/6
 */

class RequestLoginRegisterViewModel : BaseViewModel(){

    //自动脱壳过滤处理请求结果，判断结果是否成功
    var loginResult = MutableLiveData<ResultState<UserInfo>>()

    fun loginReq(email: String, password: String){
        request(
            { apiService.login(email, password) },
            loginResult,
            true,
            "正在登陆..."
        )
    }


    fun registerAndLogin(email: String, password: String){
        request(
            { HttpRequestCoroutline.register(email, password)},
            loginResult,
            true,
            "正在注册..."
        )
    }
}