package com.example.hxchat.ui.fragment.userinfo

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.hxchat.R
import com.example.hxchat.app.base.BaseFragment
import com.example.hxchat.app.ext.nav
import com.example.hxchat.app.ext.showMessage
import com.example.hxchat.app.util.CacheUtil
import com.example.hxchat.databinding.FragmentMeUserInfoBinding
import com.example.hxchat.viewmodel.request.RequestLoginRegisterViewModel
import com.example.hxchat.viewmodel.state.MeUserInfoViewModel
import kotlinx.android.synthetic.main.home_toolbar.*
import me.hgj.jetpackmvvm.ext.parseState

/**
 *Created by Pbihao
 * on 2020/10/11
 */

class meUserInfoFragment: BaseFragment<MeUserInfoViewModel, FragmentMeUserInfoBinding>(){
    private val userInfo by lazy { appViewModel.userInfo.value }
    private val requestLoginRegisterViewModel:RequestLoginRegisterViewModel by  viewModels()
    override fun layoutId(): Int = R.layout.fragment_me_user_info
    override fun initView(savedInstanceState: Bundle?) {
        ivLeft.setOnClickListener{
            nav().navigateUp()
        }
        mDatabind.data = userInfo
        mDatabind.click = ProxyClick()
    }

    override fun createObserver() {
        requestLoginRegisterViewModel.requestSucsess.observe(viewLifecycleOwner, Observer { resultState ->
            parseState(resultState, {
                CacheUtil.setUser(null)
                appViewModel.userInfo.postValue(null)
                appViewModel.isLogin.postValue(null)
                nav().navigateUp()
            }, {
                showMessage(it.errorMsg)
            })
        })
    }

    inner class ProxyClick{
        fun quitLogin(){
            requestLoginRegisterViewModel.quitLogin(appViewModel.userInfo.value)
        }
        fun toEditNickname(){

        }
        fun toEditIcon(){

        }
        fun toEditSgnature(){

        }
    }
}