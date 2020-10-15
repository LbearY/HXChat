package com.example.hxchat.ui.fragment.userinfo

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.hxchat.R
import com.example.hxchat.app.Constants
import com.example.hxchat.app.base.BaseFragment
import com.example.hxchat.app.ext.nav
import com.example.hxchat.app.ext.showMessage
import com.example.hxchat.app.util.CacheUtil
import com.example.hxchat.data.model.bean.UserInfo
import com.example.hxchat.databinding.FragmentMeUserInfoBinding
import com.example.hxchat.viewmodel.request.RequestLoginRegisterViewModel
import com.example.hxchat.viewmodel.state.MeUserInfoViewModel
import com.king.easychat.glide.GlideEngine
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.internal.entity.CaptureStrategy
import kotlinx.android.synthetic.main.home_toolbar.*
import me.hgj.jetpackmvvm.ext.download.ShareDownLoadUtil.putBoolean
import me.hgj.jetpackmvvm.ext.navigateAction
import me.hgj.jetpackmvvm.ext.parseState
import java.util.jar.Manifest

/**
 *Created by Pbihao
 * on 2020/10/11
 */

class meUserInfoFragment: BaseFragment<MeUserInfoViewModel, FragmentMeUserInfoBinding>(){
    private var userInfo:UserInfo? = null
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
        appViewModel.userInfo.observe(viewLifecycleOwner, Observer {
            userInfo = it
            mDatabind.data = it
        })
    }

    inner class ProxyClick{
        fun quitLogin(){
            requestLoginRegisterViewModel.quitLogin(appViewModel.userInfo.value)
        }
        fun toEditNickname(){
            nav().navigateAction(R.id.action_meUserInfoFragment_to_changeUserInfoFragment, Bundle().apply {
                putInt(Constants.KEY_TYPE, Constants.ChangeMeNickName)
            })
        }
        fun toEditIcon(){
           
        }
        fun toEditSgnature(){
            nav().navigateAction(R.id.action_meUserInfoFragment_to_changeUserInfoFragment, Bundle().apply {
                putInt(Constants.KEY_TYPE, Constants.ChangeMeSignature)
            })
        }
    }
}