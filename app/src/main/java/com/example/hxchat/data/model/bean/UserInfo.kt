package com.example.hxchat.data.model.bean

import android.annotation.SuppressLint
import android.os.Parcelable
import android.provider.ContactsContract
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

/**
 *Created by Pbihao
 * on 2020/10/4
 */

@SuppressLint("ParcelCreator")
@Parcelize
data class UserInfo(var email:String="",
                    var nickname: String="",
                    var icon:String="",
                    var password:String="",
                    var signature:String?=null,
                    var remark: String?=null):Parcelable
