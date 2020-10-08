package com.example.hxchat.data.model.bean

import com.example.hxchat.app.util.StringUtils

/**
 *Created by Pbihao
 * on 2020/10/8
 */
class Message {

    /** 用户id*/
    var id : String?=null
    /** 消息时间 用于排序*/
    var dateTime : String?=null

    /** 好友id*/
    var senderId : String?=null
    /** 发送的消息内容*/
    var message : String?=null

    /** 0 普通消息 1 图片消息*/
    var messageType : Int?=null

    var name : String? = null

    var avatar: String? = null

    /**
     * 未读消息数
     */
    var count = 0

    fun getMsg() : String? {
        if(messageType ==1){
            return "[图片消息]"
        }
        message?.let {
            return message
        }
        return null
    }

    fun getShowName(): String?{
        return if(StringUtils.isNotBlank(name)) name else id
    }

    override fun toString(): String {
        return "Message(id=$id, dateTime=$dateTime, senderId=$senderId, message=$message, messageType=$messageType, name=$name, avatar=$avatar, count=$count)"
    }


}