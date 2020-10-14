package com.example.hxchat.ui.fragment.me

import android.os.Bundle
import androidx.lifecycle.Observer
import com.example.hxchat.R
import com.example.hxchat.app.base.BaseFragment
import com.example.hxchat.app.ext.jumpByLogin
import com.example.hxchat.app.ext.nav
import com.example.hxchat.app.util.StringUtils
import com.example.hxchat.data.model.bean.UserInfo
import com.example.hxchat.databinding.FragmentMeBinding
import com.example.hxchat.viewmodel.state.MeViewModel
import me.hgj.jetpackmvvm.ext.navigateAction
import me.hgj.jetpackmvvm.ext.util.notNull


/**
 *Created by Pbihao
 * on 2020/10/4
 */

class MeFragment : BaseFragment<MeViewModel, FragmentMeBinding>(){

    override fun layoutId() : Int = R.layout.fragment_me

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.vm = mViewModel
        mDatabind.click = ProxyClick()

        appViewModel.userInfo.value?.let { mViewModel.name.set(if (it.nickname.isEmpty()) it.email else it.nickname) }
        appViewModel.userInfo.value?.let { mViewModel.signature.set(if ( StringUtils.isNotBlank(it.signature)) it.signature else "") }
    }


    override fun createObserver() {

        appViewModel.run {
            userInfo.observe(viewLifecycleOwner, Observer {
                  it.notNull({
                      mViewModel.name.set(if (it.nickname.isEmpty()) it.email else  it.nickname)
                      appViewModel.userInfo.value?.let { mViewModel.signature.set(if ( StringUtils.isNotBlank(it.signature))"个性签名:"+it.signature else "") }
                      appViewModel.userInfo.value?.let { mViewModel.imageUrl.set(if ( StringUtils.isNotBlank(it.icon)) it.icon else "") }
                  }, {
                      mViewModel.name.set("请先登录~")
                      mViewModel.signature.set("个性签名:")
                      mViewModel.imageUrl.set("")
                  })
            })
        }
    }

    inner class ProxyClick{

        fun login(){
            val  userInfo = UserInfo("1435343052@qq.com", "pbihao", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1602675229475&di=4bafc6967f56dbefaa7e34b9f919ac87&imgtype=0&src=http%3A%2F%2Fc-ssl.duitang.com%2Fuploads%2Fitem%2F201910%2F04%2F20191004114347_coqyw.thumb.400_0.jpeg",
            "123", "123", "好累啊")
            appViewModel.userInfo.postValue(userInfo)
            nav().navigateAction(R.id.action_mainfragment_to_meUserInfoFragment)
            /*
            nav().jumpByLogin {
                nav().navigateAction(R.id.action_mainfragment_to_meUserInfoFragment)
            }*/
        }
    }
}