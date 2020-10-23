package com.example.hxchat.ui.fragment.userinfo

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.hxchat.R
import com.example.hxchat.app.Constants
import com.example.hxchat.app.base.BaseFragment
import com.example.hxchat.app.ext.nav
import com.example.hxchat.app.ext.showMessage
import com.example.hxchat.data.model.bean.Message
import com.example.hxchat.data.model.bean.User
import com.example.hxchat.databinding.FragmentFriendUserInfoBinding
import com.example.hxchat.databinding.FragmentFriendsBinding
import com.example.hxchat.ui.activity.chat.ChatActivity
import com.example.hxchat.viewmodel.request.RequestLoginRegisterViewModel
import com.example.hxchat.viewmodel.request.RequestUserInfoViewModel
import com.example.hxchat.viewmodel.state.FriendUserInfoViewModel
import kotlinx.android.synthetic.main.fragment_friend_user_info.*
import kotlinx.android.synthetic.main.home_toolbar.*
import me.hgj.jetpackmvvm.ext.parseState
import me.hgj.jetpackmvvm.ext.view.visible
import java.util.*

/**
 *Created by Pbihao
 * on 2020/10/11
 */
class friendUserInfoFragment: BaseFragment<FriendUserInfoViewModel, FragmentFriendUserInfoBinding>(){

    private lateinit var email : String
    private var title: String? = null
    private var user: User? = null
    private var isAlreadyFriend = false
    private val requestUserInfoViewModel: RequestUserInfoViewModel by viewModels()

    override fun layoutId(): Int = R.layout.fragment_friend_user_info
    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.click = ProxyClick()
        arguments?.run {
            getParcelable<User>("user")?.let {
                user = it
                title = it.getShowName()
                title?.let {
                    tvTitle.text = it
                }
                email = it.email
                mDatabind.data = it
            }

            getBoolean("isAlreadyFriend")?.let {
                isAlreadyFriend = it
                if(it){
                    btnAdd.text = "发送消息"
                   // tvLabelRemark.visibility = View.VISIBLE
                }
            }
        }
        ivLeft.setOnClickListener{
            nav().navigateUp()
        }
    }

    override fun createObserver() {
        requestUserInfoViewModel.addFriendsUserInfo.observe(viewLifecycleOwner, Observer {resultState ->
            parseState(resultState, {
                nav().navigateUp()
            },{
                showMessage(it.errorMsg)
            })
        })
    }

    inner class ProxyClick{
        fun clickButton(){
            if(!isAlreadyFriend){
                requestUserInfoViewModel.addFriend(user?.email ?: "")
            }else{
                val intent = newIntent(user!!.getShowName(), ChatActivity::class.java)
                intent.putExtra(Constants.KEY_ID, user!!.email)
                intent.putExtra(Constants.KEY_IMAGE_URL, user!!.icon)
                startActivity(intent)
                Handler().postDelayed({
                    nav().popBackStack()
                },500)
            }
        }
        fun toRemark(){

        }
    }
}