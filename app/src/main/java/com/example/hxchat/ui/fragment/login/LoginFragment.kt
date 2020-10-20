package com.example.hxchat.ui.fragment.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.hxchat.R
import com.example.hxchat.app.base.BaseFragment
import com.example.hxchat.app.ext.hideSoftKeyboard
import com.example.hxchat.app.ext.nav
import com.example.hxchat.databinding.FragmentLoginBinding
import com.example.hxchat.viewmodel.state.LoginRegisterViewModel
import kotlinx.android.synthetic.main.include_toolbar.*
import com.example.hxchat.app.ext.initClose
import com.example.hxchat.app.ext.showMessage
import com.example.hxchat.app.util.CacheUtil
import com.example.hxchat.viewmodel.request.RequestLoginRegisterViewModel
import kotlinx.android.synthetic.main.center_toolbar.*
import kotlinx.android.synthetic.main.center_toolbar.view.*
import me.hgj.jetpackmvvm.ext.navigateAction
import me.hgj.jetpackmvvm.ext.parseState

/**
 *Created by Pbihao
 * on 2020/10/4
 */

class LoginFragment : BaseFragment<LoginRegisterViewModel, FragmentLoginBinding>(){

    private val requestLoginRegisterViewModel: RequestLoginRegisterViewModel by viewModels()

    override fun layoutId(): Int = R.layout.fragment_login

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.vm = mViewModel
        mDatabind.click = ProxyClick()


        center_toolbar.tvTitle.text = "登录"
        center_toolbar.ivLeft.visibility = View.GONE
    }


    override fun createObserver() {
        requestLoginRegisterViewModel.loginResult.observe(viewLifecycleOwner, Observer { resultState ->
            parseState(resultState, {
                CacheUtil.setUser(it)
                CacheUtil.setIsLogin(true)
                appViewModel.isLogin.postValue(true)
                appViewModel.userInfo.postValue(it)
                nav().navigateUp()
            }, {
                showMessage(it.errorMsg)
            })
        })
    }

    inner class ProxyClick{

        fun clear(){
            mViewModel.email.value = ""
        }

        fun login(){
            when {
                mViewModel.email.value.isEmpty() -> showMessage("请填写邮箱")
                mViewModel.password.get().isEmpty() -> showMessage("请填写密码")
                else -> requestLoginRegisterViewModel.loginReq(
                    mViewModel.email.value,
                    mViewModel.password.get()
                )
            }
        }

        fun goRegister(){
            hideSoftKeyboard(activity)
            nav().navigateAction(R.id.action_loginFragment_to_registerFrgment)
        }
    }
}