package com.example.hxchat.app.base

import android.content.res.Resources
import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.example.hxchat.app.event.AppViewModel
import com.example.hxchat.app.event.EventViewModel
import com.example.hxchat.app.ext.dismissLoadingExt
import com.example.hxchat.app.ext.showLoadingExt
import me.hgj.jetpackmvvm.base.activity.BaseVmActivity
import me.hgj.jetpackmvvm.base.activity.BaseVmDbActivity
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.ext.getAppViewModel
import me.jessyan.autosize.AutoSizeCompat

/**
 * code:Pbihao
 * date:2020.10.4
 */
abstract class BaseActivity<VM: BaseViewModel, DB : ViewDataBinding> :BaseVmDbActivity<VM, DB>(){

    val appViewModel: AppViewModel by lazy { getAppViewModel<AppViewModel>() }
    val eventViewModel: EventViewModel by lazy { getAppViewModel<EventViewModel>() }

    abstract override fun layoutId(): Int

    abstract override fun initView(savedInstanceState: Bundle?)

    override fun createObserver() {

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

    /**
     * 处理适配失败时候的问题
     */
    override fun getResources(): Resources {
        AutoSizeCompat.autoConvertDensityOfGlobal(super.getResources())
        return super.getResources()
    }
}