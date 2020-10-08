package com.example.hxchat.data.repository

import com.example.hxchat.data.model.bean.ApiResponse
import com.example.hxchat.data.model.bean.UserInfo
import com.example.hxchat.app.network.apiService
import com.example.hxchat.data.model.bean.User
import me.hgj.jetpackmvvm.network.AppException

/**
 *Created by Pbihao
 * on 2020/10/7
 */

val HttpRequestCoroutline: HttpRequestManger by lazy (mode = LazyThreadSafetyMode.SYNCHRONIZED){
    HttpRequestManger()
}

class HttpRequestManger{
    suspend fun register(email: String, password: String): ApiResponse<UserInfo> {
        val registerData = apiService.register(email, password, password)
        if (registerData.isSucces()) {
            // 直接登录
            return apiService.login(email, password)
        } else {
            // 抛出错误异常
            throw AppException(registerData.errorCode, registerData.errorMsg)
        }
    }

    /**
     * 根据传入的用户的邮箱，获得响应邮箱用户的好友列表
     */
}