package com.example.hxchat.app.util

import org.greenrobot.eventbus.EventBus

/**
 *Created by Pbihao
 * on 2020/10/9
 */
object Event {
    private val event = EventBus.getDefault()

    fun sendEvent(msg: Any, isSticky: Boolean = false){
        if (isSticky){
            event.postSticky(msg)
        } else{
            event.post(msg)
        }
    }

    fun registerEvent(obj: Any){
        event.register(obj)
    }

    fun unregisterEvent(obj: Any){
        event.unregister(obj)
    }
}