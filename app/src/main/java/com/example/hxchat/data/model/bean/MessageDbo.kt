package com.example.hxchat.data.model.bean

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.hxchat.data.packet.resp.MessageResp
import kotlinx.android.parcel.Parcelize

/**
 *Created by Pbihao
 * on 2020/10/8
 */
@Entity(indices = [Index(value = ["sender"]), Index(value = ["receiver"])])
@Parcelize
open class MessageDbo(var email : String,
                      val sender : String?,
                      val receiver: String?,
                      val message : String,
                      val send: Boolean = false,
                      val messageType : Int,
                      var dateTime : String,
                      val senderName : String?,
                      var read: Boolean = false) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    override fun toString(): String {
        return "MessageDbo(email='$email', sender=$sender, receiver=$receiver, message='$message', send=$send, messageType=$messageType, dateTime='$dateTime', senderName=$senderName, read=$read, id=$id)"
    }

    fun toMessageResp(): MessageResp{
        var resp = MessageResp(sender, receiver, senderName,message,send,messageType)
        resp.dateTime = dateTime
        return resp
    }


}
