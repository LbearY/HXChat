package com.example.hxchat.data.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.hxchat.data.model.bean.MessageDbo
import com.example.hxchat.data.model.bean.RecentChat
import com.example.hxchat.data.model.bean.User
import com.example.hxchat.viewmodel.state.MessageViewModel


/**
 *Created by Pbihao
 * on 2020/10/8
 */
@Database(entities = [User::class, MessageDbo::class, RecentChat::class], version = 1,exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun messageDao(): MessageDao

    abstract fun recentChatDao(): RecentChatDao

    companion object{
        @Volatile
        private var INSTANCE : AppDatabase? = null

        fun getInstance(context: Context): AppDatabase = INSTANCE ?: synchronized(this){
            INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "AppDatabase.db"
            ).build()
    }
}