package com.example.hxchat.viewmodel.state

import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.callback.databind.StringObservableField

class MeViewModel : BaseViewModel(){

    var imageUrl = StringObservableField("https://img2.woyaogexing.com/2019/08/28/667ebc1b9d7c4783bad801a2a3be199d!600x600.jpeg")

    var name =  StringObservableField("请先登录~")

    var signature = StringObservableField("个性签名:")
}
