package com.example.hxchat.viewmodel.state

import android.app.Application
import android.os.SystemClock
import android.util.Log
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
import timber.log.Timber
import com.example.hxchat.app.util.Event
import com.example.hxchat.data.model.bean.*
import com.example.hxchat.ui.fragment.home.HomeFragment
import com.king.easychat.netty.packet.MessageType
import kotlinx.coroutines.withContext
import me.hgj.jetpackmvvm.callback.livedata.UnPeekLiveData


/**
 *Created by Pbihao
 * on 2020/10/9
 */

open class MessageViewModel(application: Application)  : AndroidViewModel(application){

    var messageLiveData = UnPeekLiveData<List<MessageResp>>() // 和单个 好友的最近聊天记录
    var lastMessageLiveData = UnPeekLiveData<List<Message>>()
    var totalCountLiveData = UnPeekLiveData<Int>()


    fun delay(userEmail:String, sleepTime: Int){
        queryMessageList(userEmail,1,Constants.PAGE_SIZE,sleepTime)
    }

    /**
     * 删除最近的最近的聊天记录并且标记为已读
     */
    fun deleteLatestChat(userEmail: String, data:Message){
        GlobalScope.launch(Dispatchers.Main){
            withContext(Dispatchers.IO){
                dbDeleteRecentChat(userEmail, data.id!!)
                dbUpdateMessageRead(userEmail, data.id!!)
            }
            delay(userEmail,  200)
        }
    }

    /**
     * 查询最近的聊天记录
     */
    private fun queryMessageList(userEmail: String, current: Int, pageSize:Int, sleepTime: Int){
        GlobalScope.launch(Dispatchers.Main) {
            SystemClock.sleep(sleepTime.toLong())
            val list = withContext(Dispatchers.IO){
                queryMessageList(userEmail, pageSize)
            }
            lastMessageLiveData.postValue(list)
        }
    }

    /**
     * 根据好友id获取聊天记录
     */
    fun queryMessageByFriendId(userId : String, friendId : String, currentPage : Int, pageSize: Int) {
        GlobalScope.launch (Dispatchers.IO){
            val list =  getMessageDao().getMessageBySenderId(userId, friendId,friendId, (currentPage-1) * pageSize, pageSize).sortedBy { it.dateTime }
            messageLiveData.postValue(list.map {
                it.toMessageResp()
            })
        }
    }

    /**
     * 保存消息记录
     */
    fun saveMessage(userId: String,friendId: String,showName: String?,avatar: String?,read: Boolean,data: MessageResp){
        if(data.messageType >= MessageType.NORMAL){
            return
        }
        GlobalScope.launch {
            val recentChat = withContext(Dispatchers.IO){
                Timber.d("save:$data")
                dbSaveMessage(userId,friendId,read,data)
                // 保存最近聊天好友
                RecentChat(userId,friendId,showName,avatar,data.dateTime)
            }
            dbSaveRecentChat(recentChat)
        }
    }

    /**
     * 根据好友ID更新消息为已读
     */
    fun updateMessageRead(userId: String,friendId: String){
        GlobalScope.launch(Dispatchers.IO)  {
            Timber.d("updateMessageRead")
            dbUpdateMessageRead(userId, friendId)
            Event.sendEvent(Operator(Constants.EVENT_REFRESH_MESSAGE_COUNT))
        }
    }

    /**
     * 更新所有消息为已读
     */
    fun updateAllMessageRead(userId: String){
        GlobalScope.launch(Dispatchers.IO) {
            dbUpdateAllMessageRead(userId)
            Event.sendEvent(Operator(Constants.EVENT_REFRESH_MESSAGE_COUNT))
        }
    }

    private val db: AppDatabase = AppDatabase.getInstance(application)

    fun getAppDatabase(): AppDatabase{
        return db
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

    /**
     * 保存消息记录
     */
    fun dbSaveMessage(userId : String,friendId: String?, read: Boolean, data: MessageResp){
        getMessageDao().insert(data.toMessageDbo(userId,friendId,read))
    }

    fun dbSaveRecentChat(data: RecentChat){
        getRecentChatDao().insert(data)
    }

    fun dbDeleteRecentChat(email: String,chatId: String){
        getRecentChatDao().delete(email,chatId)
    }

    /**
     * 根据好友ID更新消息为已读
     */
    fun dbUpdateMessageRead(email: String,friendId: String){
        getMessageDao().updateRead(email,friendId,friendId)
    }

    /**
     * 更新普通消息为已读
     */
    fun dbUpdateMessageRead(email: String){
        getMessageDao().updateRead(email)
    }


    /**
     * 更新所有消息为已读
     */
    fun dbUpdateAllMessageRead(email: String){
        dbUpdateMessageRead(email)
    }


    /**
     * 获取最近的消息列表
     */
    fun queryMessageList(userEmail : String, count: Int) : List<Message> {
        val messageDao = getMessageDao()
        val recentChats = getRecentChatDao().getRecentChats(userEmail)
        val users = getUserDao().getUsers()

        val messageLists = ArrayList<Message>()
        var cnt = 0
        //遍历获取最近的聊天消息记录
        for (recentChat in recentChats) {
            val messageResp = messageDao.getLastMessageBySenderId(userEmail, recentChat.chatId,recentChat.chatId)
            val count : Int = messageDao.getUnreadList(userEmail,recentChat.chatId).size
            cnt += count
            val messageList = Message()
            messageList.count = count
            with(messageList){
                id = recentChat.chatId
                name = recentChat.showName
                avatar = recentChat.avatar
                dateTime = recentChat.dateTime
                messageResp?.let {
                    senderId = it.sender
                    message = it.message
                    messageType = it.messageType
                    dateTime = it.dateTime
                }

                users?.let {
                    for(user in users){
                        if(user.email == id){
                            name = user.getShowName()
                            avatar = user.icon
                            break
                        }
                    }
                }
            }

            messageLists.add(messageList)
        }

        messageLists.sortByDescending { it.dateTime }

        if(count < messageLists.size){
            return messageLists.subList(0, count)
        }

        totalCountLiveData.postValue(count)

        return messageLists
    }
}