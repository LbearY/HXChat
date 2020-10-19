package com.example.hxchat.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.hxchat.R
import com.example.hxchat.app.base.BaseActivity
import com.example.hxchat.app.util.CacheUtil
import com.example.hxchat.databinding.ActivityWelcomeBinding
import com.example.hxchat.viewmodel.request.RequestLoginRegisterViewModel
import kotlinx.android.synthetic.main.activity_welcome.*
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.ext.parseState

/**
 * code by: Pbihao
 */
@Suppress("DEPRECATED_IDENTITY_EQUALS")
class WelcomeActivity : BaseActivity<BaseViewModel, ActivityWelcomeBinding>() {

    private val requestLoginRegisterViewModel: RequestLoginRegisterViewModel by viewModels()

    override fun layoutId() = R.layout.activity_welcome

    override fun initView(savedInstanceState: Bundle?) {
        if (intent.flags and Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT != 0){
            finish()
            return
        }
        requestLoginRegisterViewModel.login()
    }

    override fun createObserver() {
        requestLoginRegisterViewModel.loginResult.observe(this, Observer {resultState->
            parseState(resultState,{
                CacheUtil.setUser(it)
                CacheUtil.setIsLogin(true)
                appViewModel.userInfo.postValue(it)
                appViewModel.isLogin.postValue(true)
                startActivity(Intent(this, MainActivity::class.java))
                finish()
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            },{
                CacheUtil.setUser(null)
                CacheUtil.setIsLogin(false)
                appViewModel.userInfo.postValue(null)
                appViewModel.isLogin.postValue(false)
                startActivity(Intent(this, MainActivity::class.java))
                finish()
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            })
        })
    }


    inner class ProxyClick{
        fun toMain(){
            startActivity(Intent(this@WelcomeActivity, MainActivity::class.java))
            finish()
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }
}