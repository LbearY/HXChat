package com.example.hxchat.data.packet.req

import android.annotation.SuppressLint
import android.os.Parcelable
import com.example.hxchat.data.packet.resp.LoginResp
import com.example.hxchat.data.packet.resp.MessageResp
import com.google.gson.annotations.Expose
import com.king.easychat.netty.packet.Packet
import com.king.easychat.netty.packet.PacketType
import kotlinx.android.parcel.Parcelize

/**
 * @author Zed
 * date: 2019/10/09.
 * description:
 */
@SuppressLint("ParcelCreator")
@Parcelize
data class MessageReq(val receiver : String,@Expose val msg : String, val messageType : Int = 0) : Packet(),
    Parcelable {

    val message = msg

    override fun packetType(): Int {
        return PacketType.SEND_MESSAGE_REQ
    }

    fun toMessageResp(userId: String?,userName: String?,isSender: Boolean): MessageResp {
        val data = MessageResp(userId,userName,message,isSender,messageType)
        data.dateTime = dateTime
        return data
    }

    fun toMessageResp(loginResp: LoginResp?, isSender: Boolean): MessageResp{
        val data = MessageResp(loginResp?.email,loginResp?.userName,message,isSender,messageType)
        data.dateTime = dateTime
        return data
    }


    override fun toString(): String {
        return "MessageReq(receiver='$receiver', msg='$msg', message='$message') ${super.toString()}"
    }
}