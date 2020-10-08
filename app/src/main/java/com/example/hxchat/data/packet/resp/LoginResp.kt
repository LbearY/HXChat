package com.example.hxchat.data.packet.resp

import com.king.easychat.netty.packet.Packet
import com.king.easychat.netty.packet.PacketType

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
open class LoginResp(val email: String,val userName: String,val token: String,val success: Boolean,val reason: String = "") : Packet() {


    override fun packetType(): Int {
        return PacketType.LOGIN_RESP
    }

    override fun toString(): String {
        return "LoginResp(email='$email', userName='$userName', token='$token', success=$success, reason='$reason')"
    }


}