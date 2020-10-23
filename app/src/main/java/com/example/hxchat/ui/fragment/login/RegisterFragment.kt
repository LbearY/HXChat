package com.example.hxchat.ui.fragment.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.hxchat.R
import com.example.hxchat.app.base.BaseFragment
import com.example.hxchat.app.ext.initClose
import com.example.hxchat.app.ext.nav
import com.example.hxchat.app.ext.showMessage
import com.example.hxchat.app.util.CacheUtil
import com.example.hxchat.databinding.FragmentRegisterBinding
import com.example.hxchat.viewmodel.request.RequestLoginRegisterViewModel
import com.example.hxchat.viewmodel.state.LoginRegisterViewModel
import kotlinx.android.synthetic.main.center_toolbar.*
import kotlinx.android.synthetic.main.center_toolbar.view.*
import kotlinx.android.synthetic.main.include_toolbar.*
import me.hgj.jetpackmvvm.ext.navigateAction
import me.hgj.jetpackmvvm.ext.parseState

/**
 *Created by Pbihao
 * on 2020/10/4
 */

class RegisterFragment : BaseFragment<LoginRegisterViewModel, FragmentRegisterBinding>(){

    private val  requestLoginRegisterViewModel: RequestLoginRegisterViewModel by viewModels()

    override fun layoutId(): Int = R.layout.fragment_register

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.vm = mViewModel
        mDatabind.click = ClickProxy()

        center_toolbar.tvTitle.text = "注册 "
        center_toolbar.ivLeft.setOnClickListener{
            nav().navigateUp()
        }
    }

    override fun createObserver() {
        requestLoginRegisterViewModel.loginResult.observe(viewLifecycleOwner, Observer { resultState ->
            parseState(resultState, {
                CacheUtil.setUser(it)
                CacheUtil.setIsLogin(true)
                appViewModel.isLogin.postValue(true)
                appViewModel.userInfo.postValue(it)
                nav().navigateAction(R.id.action_registerFrgment_to_mainFragment)
            },{
                showMessage(it.errorMsg)
            })
        })
    }

    inner class ClickProxy{
        fun clearEmail(){
            mViewModel.email.value = ""
        }

        fun clearNickname(){
            mViewModel.nickname.value = ""
        }

        fun register(){
            when{
                mViewModel.email.value.isEmpty() -> showMessage("请填写邮箱")
                mViewModel.nickname.value.isEmpty() -> showMessage("请填写昵称")
                mViewModel.password.get().isEmpty() -> showMessage("请填写密码")
                mViewModel.password2.get().isEmpty() -> showMessage("请填写确认密码")
                mViewModel.password.get().length < 6 -> showMessage("密码最少6位")
                mViewModel.password.get() != mViewModel.password2.get() -> showMessage("密码不一致")
                else -> requestLoginRegisterViewModel.registerAndLogin(
                    mViewModel.nickname.value,
                    mViewModel.email.value,
                    mViewModel.password.get()
                )
            }
        }
    }
}