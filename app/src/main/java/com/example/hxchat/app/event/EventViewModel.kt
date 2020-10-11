package com.example.hxchat.app.event

import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.callback.livedata.event.EventLiveData

/**
 *Created by Pbihao
 * on 2020/10/4
 */

class EventViewModel: BaseViewModel(){
    //事件通知
    val statusEvent = EventLiveData<Int>()
}