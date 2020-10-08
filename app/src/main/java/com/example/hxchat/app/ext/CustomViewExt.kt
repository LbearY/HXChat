@file:Suppress("DEPRECATION")

package com.example.hxchat.app.ext

import android.app.Activity
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethod
import android.view.inputmethod.InputMethodManager
import android.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.hxchat.R
import com.example.hxchat.app.util.SettingUtil
import com.example.hxchat.ui.fragment.friends.FriendsFragment
import com.example.hxchat.ui.fragment.home.HomeFragment
import com.example.hxchat.ui.fragment.me.MeFragment
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx
import me.hgj.jetpackmvvm.base.appContext
import me.hgj.jetpackmvvm.ext.util.toHtml

/**
 *Created by Pbihao
 * on 2020/10/4
 */

/**
 * 隐藏软键盘
 */
fun hideSoftKeyboard(activity: Activity?){
    activity?.let { act ->
        val view = act.currentFocus
        view?.let {
            val inputMeManager = act.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMeManager.hideSoftInputFromWindow(
                view.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }
}


/**
 * 主界面的的三个界面之间进行 切换
 */
fun ViewPager2.initMain(fragment: Fragment): ViewPager2{
    this.isUserInputEnabled = false
    this.offscreenPageLimit = 5
    adapter = object :  FragmentStateAdapter(fragment) {
        override fun createFragment(position: Int): Fragment {
            when(position){
                0 -> {
                    return HomeFragment()
                }
                1 -> {
                    return FriendsFragment()
                }
                2 -> {
                    return MeFragment()
                }
                else -> {
                    return HomeFragment()
                }
            }
        }

        override fun getItemCount(): Int = 5
    }
    return this
}

/**
 * 初始化底部的导航栏包括导航栏的格式 变化 也在这里
 */
fun BottomNavigationViewEx.init(navigationItemSelectedAction: (Int) -> Unit) : BottomNavigationViewEx {
    enableAnimation(true)
    enableShiftingMode(true)
    enableItemShiftingMode(true)
    setOnNavigationItemSelectedListener{
        navigationItemSelectedAction.invoke(it.itemId)
        true
    }
    return this
}

/**
 * 判断是否为空 并传入相关操作
 */
inline fun <reified T> T?.notNull(notNullAction: (T) -> Unit, nullAction: () -> Unit = {}) {
    if (this != null) {
        notNullAction.invoke(this)
    } else {
        nullAction.invoke()
    }
}

/**
 * 初始化有返回键的toolbar
 */
fun androidx.appcompat.widget.Toolbar.initClose(
    titleStr: String = "",
    backImg: Int = R.drawable.ic_back,
    onBack: (toolbar: androidx.appcompat.widget.Toolbar) -> Unit
): androidx.appcompat.widget.Toolbar {
    setBackgroundColor(SettingUtil.getColor(appContext))
    title = titleStr.toHtml()
    textAlignment = View.TEXT_ALIGNMENT_CENTER
    setNavigationIcon(backImg)
    setNavigationOnClickListener { onBack.invoke(this) }

    return this
}