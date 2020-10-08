package com.example.hxchat.app.ext

import android.text.TextUtils
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.WhichButton
import com.afollestad.materialdialogs.actions.getActionButton
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.example.hxchat.R
import com.example.hxchat.app.util.CacheUtil
import me.hgj.jetpackmvvm.ext.navigateAction
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException

/**
 *Created by Pbihao
 * on 2020/10/6
 */

/**
 * 获取进程号对应的进程名
 *
 * @param pid 进程号
 * @return 进程名
 */
fun getProcessName(pid: Int): String? {
    var reader: BufferedReader? = null
    try {
        reader = BufferedReader(FileReader("/proc/$pid/cmdline"))
        var processName = reader.readLine()
        if (!TextUtils.isEmpty(processName)) {
            processName = processName.trim { it <= ' ' }
        }
        return processName
    } catch (throwable: Throwable) {
        throwable.printStackTrace()
    } finally {
        try {
            reader?.close()
        } catch (exception: IOException) {
            exception.printStackTrace()
        }

    }
    return null
}

/**
 * 拦截登录操作，如果没有登录就登录，否则执行传入的操作
 */
fun NavController.jumpByLogin(action: (NavController) -> Unit){
    if (CacheUtil.isLogin()){
        action(this)
    }else{
        this.navigateAction(R.id.action_to_loginFragment)
    }
}

/**
 * 在Fragment上面显示弹窗
 */
fun Fragment.showMessage(
    message: String,
    title: String = "温馨提示",
    positiveButtonText: String = "确定",
    positiveAction: () -> Unit = {},
    negativeButtonText: String = "",
    negativeAction: () -> Unit = {}
) {
    activity?.let {
        MaterialDialog(it)
            .cancelable(false)
            .lifecycleOwner(viewLifecycleOwner)
            .show {
                title(text = title)
                message(text = message)
                positiveButton(text = positiveButtonText) {
                    positiveAction.invoke()
                }
                if (negativeButtonText.isNotEmpty()) {
                    negativeButton(text = negativeButtonText) {
                        negativeAction.invoke()
                    }
                }
            }
    }
}