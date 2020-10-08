package com.example.hxchat.data.model.bean

/**
 *Created by Pbihao
 * on 2020/10/8
 */
class Result<T> {

    var code : String? = null

    var desc : String? = null

    var data : T? = null

    fun isSuccess(): Boolean{
        return "0" == code
    }

    override fun toString(): String {
        return "Result(code=$code, desc=$desc, data=$data)"
    }


}