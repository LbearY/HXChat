package com.example.hxchat.app.network

import com.example.hxchat.data.model.bean.ApiResponse
import com.example.hxchat.data.model.bean.User
import com.example.hxchat.data.model.bean.UserInfo
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

/**
 *Created by Pbihao
 * on 2020/10/6
 */

interface ApiService {
    companion object {
        const val SERVER_URL = "https://37678c1f-29fd-4de7-9cc1-960a64dc4f35.mock.pstmn.io"
    }

    /**
     * 登录
     */
    @FormUrlEncoded
    @POST("session/")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") pwd: String
    ): ApiResponse<UserInfo>

    /**
     * 注册
     */
    @FormUrlEncoded
    @POST("user/")
    suspend fun register(
        @Field("email") email: String,
        @Field("password") pwd: String
    ): ApiResponse<Any>

    /**
     * 获得好友列表
     */
    @GET("friend/")
    suspend fun getFriends() : ApiResponse<ArrayList<User>>

    /**
     * 发送消息
     */
    @POST
    suspend fun sendMessage(

    )
}