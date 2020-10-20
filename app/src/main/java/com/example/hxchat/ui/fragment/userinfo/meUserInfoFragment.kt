package com.example.hxchat.ui.fragment.userinfo

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.PermissionUtils.permission
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
import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.hxchat.app.util.Event
import com.example.hxchat.data.packet.resp.LogoutResp
import com.tbruyelle.rxpermissions2.RxPermissions
import java.io.File

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
                Log.d("meUserInfo:", "fuck")
                CacheUtil.setUser(null)
                CacheUtil.setIsLogin(false)
                appViewModel.isLogin.postValue(false)
                appViewModel.userInfo.postValue(null)
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


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d("select", "选择了一张图片")
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode ==  RESULT_OK){
            when(requestCode){
                Constants.REQ_SELECT_PHOTO -> cropRawPhoto(obtainSelectPhoto(data))
            }
        }
    }

    private fun cropRawPhoto(uri: Uri?) {
        Log.d("select", "选择了一张图片")
        /*
        uri?.let {
            val options = UCrop.Options()
            //        options.setToolbarTitle("裁剪");
            // 修改标题栏颜色
            options.setToolbarColor(ContextCompat.getColor(context,R.color.colorPrimary))
            options.setToolbarWidgetColor(ContextCompat.getColor(context,R.color.white))
            // 修改状态栏颜色
            options.setStatusBarColor(ContextCompat.getColor(context,R.color.colorPrimaryDark))
            // 隐藏底部工具
            options.setHideBottomControls(true)
            // 图片格式
            options.setCompressionFormat(Bitmap.CompressFormat.JPEG)
            // 设置图片压缩质量
            options.setCompressionQuality(100)
            // 是否让用户调整范围(默认false)，如果开启，可能会造成剪切的图片的长宽比不是设定的
            // 如果不开启，用户不能拖动选框，只能缩放图片
            options.setFreeStyleCropEnabled(false)
            options.setCircleDimmedLayer(true)
            avatarFile = File(getApp().getPath(), System.currentTimeMillis().toString() + ".jpg")

            // 设置源uri及目标uri
            UCrop.of(uri,Uri.fromFile(avatarFile))
                // 长宽比
                .withAspectRatio(1f, 1f)
                // 图片大小
                .withMaxResultSize(300, 300)
                // 配置参数
                .withOptions(options)
                .start(this,Constants.REQ_CROP_PHOTO)
        }

         */
    }

    private fun obtainSelectPhoto(data: Intent?):Uri?{
        val result = Matisse.obtainPathResult(data)
        return Uri.fromFile(File(result[0]))
    }

    inner class ProxyClick{
        fun quitLogin(){
            Log.d("123", "tuichudenglu")
            eventViewModel.quitLogin.postValue(true)
            requestLoginRegisterViewModel.quitLogin(appViewModel.userInfo.value)
        }
        fun toEditNickname(){
            nav().navigateAction(R.id.action_meUserInfoFragment_to_changeUserInfoFragment, Bundle().apply {
                putInt(Constants.KEY_TYPE, Constants.ChangeMeNickName)
            })
        }
        @SuppressLint("CheckResult")
        fun toEditIcon(){
            rxPermission.request(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe{
                    if(it){
                        Matisse.from(activity)
                            .choose(MimeType.ofImage())
                            .maxSelectable(1)
                            .gridExpectedSize(resources.getDimensionPixelSize(R.dimen.size_120dp))
                            .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                            .thumbnailScale(0.85f)
                            .imageEngine(GlideEngine())
                            .forResult(Constants.REQ_SELECT_PHOTO)
                    }
                }
            Log.d("select", "选择了一张图片")
        }
        fun toEditSgnature(){
            nav().navigateAction(R.id.action_meUserInfoFragment_to_changeUserInfoFragment, Bundle().apply {
                putInt(Constants.KEY_TYPE, Constants.ChangeMeSignature)
            })
        }
    }
}