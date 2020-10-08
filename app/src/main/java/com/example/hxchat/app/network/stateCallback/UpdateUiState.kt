package com.example.hxchat.app.network.stateCallback

/**
 *Created by Pbihao
 * on 2020/10/8
 */
data class UpdateUiState<T>(
    //请求是否成功
    var isSuccess: Boolean = true,
    //操作的对象
    var data: T? = null,
    //请求失败的错误信息
    var errorMsg: String = ""
)