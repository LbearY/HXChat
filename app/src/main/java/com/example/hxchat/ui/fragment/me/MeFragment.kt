package com.example.hxchat.ui.fragment.me

import android.os.Bundle
import androidx.lifecycle.Observer
import com.example.hxchat.R
import com.example.hxchat.app.base.BaseFragment
import com.example.hxchat.app.ext.jumpByLogin
import com.example.hxchat.app.ext.nav
import com.example.hxchat.app.util.StringUtils
import com.example.hxchat.databinding.FragmentMeBinding
import com.example.hxchat.viewmodel.state.MeViewModel
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
                      appViewModel.userInfo.value?.let { mViewModel.signature.set(if ( StringUtils.isNotBlank(it.signature)) it.signature else "") }
                  }, {
                      mViewModel.name.set("请先登录~")
                      mViewModel.signature.set("个性签名:")
                  })
            })
        }
    }

    inner class ProxyClick{

        fun login(){
            nav().jumpByLogin {  }
        }
    }
}