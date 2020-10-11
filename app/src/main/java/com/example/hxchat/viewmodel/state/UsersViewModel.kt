package com.example.hxchat.viewmodel.state

import android.app.Application
import android.os.SystemClock
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.hxchat.app.Constants
import com.example.hxchat.data.dao.AppDatabase
import com.example.hxchat.data.dao.MessageDao
import com.example.hxchat.data.dao.RecentChatDao
import com.example.hxchat.data.dao.UserDao
import com.example.hxchat.data.packet.resp.MessageResp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import timber.log.Timber
import com.example.hxchat.app.util.Event
import com.example.hxchat.data.model.bean.*
import com.king.easychat.netty.packet.MessageType
import com.king.frame.mvvmframe.base.BaseApplication
import kotlinx.coroutines.withContext


/**
 *Created by Pbihao
 * on 2020/10/9
 */

open class UsersViewModel(application: Application)  : AndroidViewModel(application){


    /**
     * 在数据库中保存好友列表
     */
    fun saveUsers(users: List<User>?){
        users?.let {
            GlobalScope.launch(Dispatchers.IO){
                with(getUserDao()){
                    deleteAll()
                    insert(it)
                }
            }
        }
    }


    fun getAppDatabase(): AppDatabase{
        return AppDatabase.getInstance(getApplication())
    }

    fun getUserDao(): UserDao {
        return getAppDatabase().userDao()
    }

    fun getMessageDao(): MessageDao {
        return getAppDatabase().messageDao()
    }

    fun getRecentChatDao(): RecentChatDao {
        return getAppDatabase().recentChatDao()
    }

    fun dbGetUsers(): LiveData<List<User>> {
        return getUserDao().getAllUsers()
    }

    fun dbDeleteUsers(){
        getUserDao().deleteAll()
    }

}