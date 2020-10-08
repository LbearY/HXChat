package com.example.hxchat.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.hxchat.data.model.bean.User

/**
 *Created by Pbihao
 * on 2020/10/8
 */
@Dao
interface UserDao{
    /**
     * 插入用户信息并去重
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(users: List<User>)

    @Delete
    fun delete(user: User)

    /**
     * 删除 所有
     */
    @Query("DELETE FROM User")
    fun deleteAll()

    /**
     * 根据传入的email删除
     */
    @Query("DELETE FROM User WHERE email = :email")
    fun delete(email: String)

    /**
     * 查询所有用户列表
     */
    @Query("SELECT * FROM User")
    fun getAllUsers():LiveData<List<User>>

    @Query("SELECT * FROM User")
    fun getUsers():List<User>

    /**
     * 查询用户列表
     */
    @Query("SELECT * FROM User ORDER BY email DESC LIMIT :count")
    fun getUsers(count: Int): LiveData<List<User>>
}