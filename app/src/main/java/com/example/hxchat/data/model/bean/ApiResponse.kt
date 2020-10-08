package com.example.hxchat.data.model.bean

import me.hgj.jetpackmvvm.network.BaseResponse

/**
 *Created by Pbihao
 * on 2020/10/6
 */

data class ApiResponse<T>(var errorCode: Int, var errorMsg: String, var data: T) : BaseResponse<T>() {

    override fun isSucces(): Boolean = errorCode == 0

    override fun getResponseCode(): Int = errorCode

    override fun getResponseData(): T = data

    override fun getResponseMsg(): String = errorMsg

}