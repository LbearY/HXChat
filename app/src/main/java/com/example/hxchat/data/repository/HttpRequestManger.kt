package com.example.hxchat.data.repository

import android.util.Log
import com.example.hxchat.data.model.bean.ApiResponse
import com.example.hxchat.data.model.bean.UserInfo
import com.example.hxchat.app.network.apiService
import com.example.hxchat.data.model.bean.User
import me.hgj.jetpackmvvm.network.AppException
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject

/**
 *Created by Pbihao
 * on 2020/10/7
 */

val HttpRequestCoroutline: HttpRequestManger by lazy (mode = LazyThreadSafetyMode.SYNCHRONIZED){
    HttpRequestManger()
}

class HttpRequestManger {
    suspend fun register(nickname: String, email: String, password: String): ApiResponse<UserInfo> {
        val registerParm = JSONObject()
        registerParm.put("nickname", nickname)
        registerParm.put("email", email)
        registerParm.put("password", password)
        val registerData = apiService.register(
            RequestBody.create(
                MediaType.parse("application/json"),
                registerParm.toString()
            )
        )
        if (registerData.isSucces()) {
            // 直接登录
            val loginParm = JSONObject()
            loginParm.put("email", email)
            loginParm.put("password", password)
            return apiService.login(
                RequestBody.create(
                    MediaType.parse("application/json"),
                    loginParm.toString()
                )
            )
        } else {
            // 抛出错误异常
            throw AppException(registerData.errorCode, registerData.errorMsg)
        }
    }

    /**
     * 获得用户的好友列表
     */
}