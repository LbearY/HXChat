package com.example.hxchat.data.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.hxchat.data.model.bean.MessageDbo
import com.example.hxchat.data.model.bean.RecentChat
import com.example.hxchat.data.model.bean.User


/**
 *Created by Pbihao
 * on 2020/10/8
 */
@Database(entities = [User::class, MessageDbo::class, RecentChat::class], version = 1,exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun messageDao(): MessageDao

    abstract fun recentChatDao(): RecentChatDao
}