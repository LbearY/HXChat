package com.example.hxchat.app.base

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.example.hxchat.app.Constants
import com.example.hxchat.app.event.AppViewModel
import com.example.hxchat.app.event.EventViewModel
import com.example.hxchat.app.ext.dismissLoadingExt
import com.example.hxchat.app.ext.hideSoftKeyboard
import com.example.hxchat.app.ext.showLoadingExt
import com.example.hxchat.app.util.Event
import me.hgj.jetpackmvvm.base.fragment.BaseVmDbFragment
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.ext.getAppViewModel
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 *Created by Pbihao
 * on 2020/10/4
 */

abstract class BaseFragment<VM : BaseViewModel, DB: ViewDataBinding>  : BaseVmDbFragment<VM, DB>(){
    val appViewModel: AppViewModel by lazy{getAppViewModel<AppViewModel>()}
    val eventViewModel: EventViewModel by lazy { getAppViewModel<EventViewModel>() }

    abstract override fun layoutId(): Int

    abstract override fun initView(savedInstanceState: Bundle?)

    fun newIntent(title: String, cls: Class<*>): Intent {
        val intent = Intent(context, cls)
        intent.putExtra(Constants.KEY_TITLE, title)
        return intent
    }

    /**
     * 实现懒加载
     */
    override fun lazyLoadData() {    }

    /**
     * 创建LiveData观察者
     */
    override fun createObserver() { }

    override fun initData() {  }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onAllEvent(event: Any){

    }

    /**
     * 等待框
     */
    override fun showLoading(message: String) {
        showLoadingExt(message)
    }

    override fun dismissLoading() {
        dismissLoadingExt()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Event.registerEvent(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        Event.unregisterEvent(this)
    }

    override fun onPause() {
        super.onPause()
        hideSoftKeyboard(activity)
    }

    fun getUserEmail() = appViewModel.userInfo.value?.email ?: ""
    fun getUserAvatar() = appViewModel.userInfo.value?.icon ?: ""
}