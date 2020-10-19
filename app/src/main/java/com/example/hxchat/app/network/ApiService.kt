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
    @POST("session")
    suspend fun login(
        @Body body: RequestBody
    ): ApiResponse<UserInfo>

    /**
     * 注册
     */
    @POST("user")
    suspend fun register(
        @Body body: RequestBody
    ): ApiResponse<Any>

    /**
     * 获得好友列表
     */
    @GET("friend")
    suspend fun getFriends(): ApiResponse<ArrayList<User>>

    /**
     * 搜索用户
     */
    @GET("user")
    suspend fun search(
        @Query("nickname") nickname: String
    ): ApiResponse<ArrayList<User>>

    /**
     * 添加好友
     */
    @POST("friend")
    suspend fun addFriend(
        @Body body: RequestBody
    ): ApiResponse<User>

    /**
     * 发送消息
     */
    @POST
    suspend fun sendMessage(

    )
}