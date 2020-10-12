package com.example.hxchat.ui.fragment.userinfo

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.hxchat.R
import com.example.hxchat.app.base.BaseFragment
import com.example.hxchat.app.ext.nav
import com.example.hxchat.app.ext.showMessage
import com.example.hxchat.data.model.bean.User
import com.example.hxchat.databinding.FragmentFriendUserInfoBinding
import com.example.hxchat.databinding.FragmentFriendsBinding
import com.example.hxchat.viewmodel.request.RequestLoginRegisterViewModel
import com.example.hxchat.viewmodel.request.RequestUserInfoViewModel
import com.example.hxchat.viewmodel.state.FriendUserInfoViewModel
import kotlinx.android.synthetic.main.home_toolbar.*
import me.hgj.jetpackmvvm.ext.parseState

/**
 *Created by Pbihao
 * on 2020/10/11
 */
class friendUserInfoFragment: BaseFragment<FriendUserInfoViewModel, FragmentFriendUserInfoBinding>(){

    private lateinit var email : String
    private var title: String? = null
    private var user: User? = null
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
        fun addFriend(){
            requestUserInfoViewModel.addFriend(user?.email ?: "")
        }
    }
}