package com.example.hxchat.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.hxchat.data.model.bean.RecentChat

/**
 *Created by Pbihao
 * on 2020/10/8
 */
@Dao
interface RecentChatDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: RecentChat)

    @Query("DELETE FROM RecentChat WHERE email = :email AND chatId = :chatId")
    fun delete(email: String, chatId: String)

    @Query("DELETE FROM RecentChat WHERE email = :email")
    fun delete(email: String)

    @Query("SELECT * FROM RecentChat WHERE email = :email")
    fun getRecentChat(email: String): List<RecentChat>
}