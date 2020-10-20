package com.example.hxchat.ui.activity.chat

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hxchat.R
import com.example.hxchat.app.Constants
import com.example.hxchat.app.base.BaseActivity
import com.example.hxchat.app.ext.hideSoftKeyboard
import com.example.hxchat.app.util.Event
import com.example.hxchat.app.util.KeyboardUtils
import com.example.hxchat.data.model.bean.Operator
import com.example.hxchat.data.packet.resp.MessageResp
import com.example.hxchat.databinding.FragmentChatBinding
import com.example.hxchat.ui.adapter.ChatAdapter
import com.example.hxchat.ui.adapter.DividerItemDecoration
import com.example.hxchat.viewmodel.request.RequestMessageViewModel
import com.example.hxchat.viewmodel.state.ChatViewModel
import com.example.hxchat.viewmodel.state.MessageViewModel
import com.king.easychat.netty.packet.MessageType
import com.vanniktech.emoji.EmojiPopup
import kotlinx.android.synthetic.main.center_toolbar.*
import kotlinx.android.synthetic.main.fragment_chat.*
import kotlinx.android.synthetic.main.rv_chat_item.*
import kotlinx.android.synthetic.main.rv_chat_item.ivContent
import kotlinx.android.synthetic.main.rv_chat_right_item.*
import me.hgj.jetpackmvvm.ext.parseState
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


/**
 *Created by Pbihao
 * on 2020/10/4
 */

class ChatActivity : BaseActivity<ChatViewModel, FragmentChatBinding>(){
    var friendEmail : String = ""
    var showName : String? = null
    var avatar : String? = null


    val mAdapter by lazy { ChatAdapter(avatar, getUserAvatar()) }

    var message : String? = null

    var curPage = 1

    var isAutoScroll = true

    private val requestMessageViewModel: RequestMessageViewModel by viewModels()
    private val messageViewModel : MessageViewModel by lazy {  ViewModelProvider(this).get(MessageViewModel::class.java) }

    private val emojiPopup:EmojiPopup by lazy { EmojiPopup.Builder.fromRootView(findViewById(R.id.rtContent)).build(findViewById(R.id.etContent)) }

    override fun layoutId(): Int = R.layout.fragment_chat

    override fun initView(savedInstanceState: Bundle?) {

        tvSend.visibility = View.GONE
        srl.setColorSchemeResources(R.color.colorAccent)
        srl.setOnRefreshListener {
            isAutoScroll = false
            messageViewModel.queryMessageByFriendId(getUserEmail(), friendEmail, curPage, Constants.PAGE_SIZE)
        }

        ivEmoji.setOnClickListener{ignore ->
            if (emojiPopup.isShowing){
                ivEmoji.setImageResource(R.drawable.ic_add_emoji)
            }else{
                ivEmoji.setImageResource(R.drawable.ic_keyboard)
            }
            emojiPopup.toggle()
        }

        KeyboardUtils.registerSoftInputChangedListener(this) {
            if(it>0){
                rv.scrollToPosition(mAdapter.itemCount - 1)
            }
        }

        ivLeft.setOnClickListener{
            onBackPressed()
        }

        tvSend.setOnClickListener{
            clickSend()
        }

        etContent.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                updateBtnStatus(s.isEmpty())
            }

            override fun afterTextChanged(s: Editable) {

            }

        })

        avatar = intent.getStringExtra(Constants.KEY_IMAGE_URL)

        rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        rv.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL,R.drawable.line_drawable_xh_none))
        rv.adapter = mAdapter

        rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val layoutManager = recyclerView.layoutManager
                if(layoutManager is LinearLayoutManager){
                    val lastPosition = layoutManager.findLastVisibleItemPosition()

                    if(lastPosition == layoutManager.itemCount - 1){
                        isAutoScroll = true
                    }
                }

            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

            }
        })

        showName = intent.getStringExtra(Constants.KEY_TITLE)
        tvTitle.text = showName
        friendEmail = intent.getStringExtra(Constants.KEY_ID)
        appViewModel.friendEmail.postValue(friendEmail)

        messageViewModel.queryMessageByFriendId(getUserEmail(),friendEmail,curPage,Constants.PAGE_SIZE)
    }

    override fun createObserver() {

        messageViewModel.messageLiveData.observe(this, Observer {
            srl.isRefreshing = false
            if(curPage == 1){
                mAdapter.curTime = System.currentTimeMillis()
                mAdapter.replaceData(it)
            }else if(curPage>1){
                mAdapter.addData(0,it)
            }

            if(mAdapter.itemCount >= (curPage-1) * Constants.PAGE_SIZE){
                curPage++
            }

            etContent.text = null
            if(isAutoScroll){
                rv.scrollToPosition(mAdapter.itemCount - 1)
            }
        })

        requestMessageViewModel.messageReq.observe(this, Observer {resultState ->
            parseState(resultState,{
                handleMessageResp(it)
            })
        })
    }

    private fun updateBtnStatus(isEmpty: Boolean){
        if(isEmpty){
            if(tvSend.visibility == View.VISIBLE){
                tvSend.visibility = View.GONE
                ivAdd.visibility = View.VISIBLE
                ivAdd.startAnimation(AnimationUtils.loadAnimation(this,R.anim.anim_in))
                tvSend.startAnimation(AnimationUtils.loadAnimation(this,R.anim.anim_out))
            }
        }else if(tvSend.visibility == View.GONE){
            tvSend.visibility = View.VISIBLE
            ivAdd.visibility = View.GONE
            ivAdd.startAnimation(AnimationUtils.loadAnimation(this,R.anim.anim_out))
            tvSend.startAnimation(AnimationUtils.loadAnimation(this,R.anim.anim_in))
        }
    }

    override fun onDestroy() {
        KeyboardUtils.unregisterSoftInputChangedListener(this)
        super.onDestroy()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageResp){
        handleMessageResp(event)
    }

    fun handleMessageResp(resp: MessageResp?){
        resp?.let {
            resp.isSelf()
            Log.d("handleMessageResp", resp.isSender.toString())
            if(it.isSender || friendEmail == it.sender){
                mAdapter.addData(it)
                //messageViewModel.saveMessage(getUserEmail(), friendEmail, showName, avatar,true,resp)
                if(isAutoScroll){
                    rv.scrollToPosition(mAdapter.itemCount - 1)
                }
            }
        }

    }

     private fun clickSend(){
        message = etContent.text.toString()
        message?.let {
            requestMessageViewModel.sendMessage(friendEmail, it, MessageType.TEXT)
        }
    }

    override fun onBackPressed() {
        Event.sendEvent(Operator(Constants.EVENT_UPDATE_MESSAGE_READ))
        super.onBackPressed()
    }

}