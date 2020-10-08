package com.example.hxchat.app.base

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.example.hxchat.app.event.AppViewModel
import com.example.hxchat.app.event.EventViewModel
import com.example.hxchat.app.ext.dismissLoadingExt
import com.example.hxchat.app.ext.hideSoftKeyboard
import com.example.hxchat.app.ext.showLoadingExt
import me.hgj.jetpackmvvm.base.fragment.BaseVmDbFragment
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.ext.getAppViewModel

/**
 *Created by Pbihao
 * on 2020/10/4
 */

abstract class BaseFragment<VM : BaseViewModel, DB: ViewDataBinding>  : BaseVmDbFragment<VM, DB>(){
    val appViewModel: AppViewModel by lazy{getAppViewModel<AppViewModel>()}
    val eventViewModel: EventViewModel by lazy { getAppViewModel<EventViewModel>() }

    abstract override fun layoutId(): Int

    abstract override fun initView(savedInstanceState: Bundle?)

    /**
     * 实现懒加载
     */
    override fun lazyLoadData() {    }

    /**
     * 创建LiveData观察者
     */
    override fun createObserver() { }

    override fun initData() {  }

    /**
     * 等待框
     */
    override fun showLoading(message: String) {
        showLoadingExt(message)
    }

    override fun dismissLoading() {
        dismissLoadingExt()
    }

    override fun onPause() {
        super.onPause()
        hideSoftKeyboard(activity)
    }


}