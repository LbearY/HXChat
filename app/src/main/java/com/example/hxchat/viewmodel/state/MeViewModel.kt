package com.example.hxchat.viewmodel.state

import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.callback.databind.StringObservableField

class MeViewModel : BaseViewModel(){

    var imageUrl = StringObservableField("")

    var name =  StringObservableField("请先登录~")

    var signature = StringObservableField("个性签名:")
}
