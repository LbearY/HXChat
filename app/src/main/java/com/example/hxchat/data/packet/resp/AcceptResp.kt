package com.example.hxchat.data.packet.resp

import com.king.easychat.netty.packet.Packet
import com.king.easychat.netty.packet.PacketType

/**
 *Created by Pbihao
 * on 2020/10/8
 */
class AcceptResp(val inviterId: String, val invitedId: String,val invitedName: String, val success: Boolean) : Packet() {
    override fun packetType(): Int {
        return PacketType.ACCEPT_RESP
    }

    override fun toString(): String {
        return "AcceptResp(inviterId='$inviterId', invitedId='$invitedId', invitedName='$invitedName', success=$success)"
    }


}
