package com.example.hxchat.ui.fragment.home

import android.app.Application
import android.content.ClipData.newIntent
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.util.Xml
import android.view.View
import androidx.databinding.ObservableField
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.hxchat.R
import com.example.hxchat.app.Constants
import com.example.hxchat.app.base.BaseFragment
import com.example.hxchat.app.ext.showMessage
import com.example.hxchat.data.model.bean.Message
import com.example.hxchat.data.model.bean.Operator
import com.example.hxchat.databinding.FragmentHomeBinding
import com.example.hxchat.ui.activity.chat.ChatActivity
import com.example.hxchat.ui.adapter.MessageAdapter
import com.example.hxchat.viewmodel.state.HomeViewModel
import com.example.hxchat.viewmodel.state.MessageViewModel
import com.king.easychat.netty.packet.Packet
import com.king.easychat.netty.packet.PacketType
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.home_toolbar.*
import me.hgj.jetpackmvvm.callback.databind.IntObservableField
import me.hgj.jetpackmvvm.callback.databind.StringObservableField
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*
import kotlin.math.log

/**
 *Created by Pbihao
 * on 2020/10/4
 */

class HomeFragment: BaseFragment<HomeViewModel, FragmentHomeBinding>(), View.OnClickListener{

    override fun layoutId(): Int = R.layout.fragment_home

    private val messageViewModel =  MessageViewModel(application = Application())

    val mAdapter by lazy {MessageAdapter(getUserEmail(), messageViewModel)}

    var isRefresh = true

    var onTotalCountCallback: OnTotalCountCallback? = null

    interface OnTotalCountCallback{
        fun onTotalCountChanged(count: Int)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnTotalCountCallback){
            onTotalCountCallback = context
        }
    }


    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.homevm = mViewModel

        tvTitle.setText(R.string.menu_message)
        srl.setColorSchemeResources(R.color.colorAccent)
        isRefresh = true

        rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rv.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        rv.adapter = mAdapter

        mAdapter.onItemChildClickListener = BaseQuickAdapter.OnItemChildClickListener{ adapter, view, position ->
            when(view.id){
                R.id.clContent -> clickItem(mAdapter.getItem(position)!!)
                R.id.llDelete -> clickDeleteItem(mAdapter.getItem(position)!!)
            }
        }
    }

    override fun createObserver() {
        mViewModel.run {
            lastMessageLiveData.observe(viewLifecycleOwner, Observer{
                mAdapter.curTime = System.currentTimeMillis()
                mAdapter.replaceData(it)
            })
            totalCountLiveData.observe(viewLifecycleOwner, Observer {
                onTotalCountCallback?.onTotalCountChanged(it)
            })
        }
    }

    private fun setEmpty(){
        if(mAdapter.emptyView == null){
            mAdapter.setEmptyView(R.layout.layout_em, rv)
        }
    }

    private fun requestData(){
        if (isRefresh){
            mViewModel.delay(200)
        }
    }

    override fun onResume() {
        isRefresh = true
        super.onResume()
        requestData()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if(!hidden){
            requestData()
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: Packet){
        when(event.packetType()){
            PacketType.SEND_MESSAGE_RESP -> requestData()
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: Operator){
        when(event.event){
            Constants.EVENT_REFRESH_MESSAGE_COUNT -> requestData()
        }
    }

    fun startChatActivity(data: Message){
        val intent = newIntent(data.getShowName()!!, ChatActivity::class.java)
        intent.putExtra(Constants.KEY_ID, data.id)
        intent.putExtra(Constants.KEY_IMAGE_URL, data.avatar)
        startActivity(intent)
    }

    override fun onPause() {
        super.onPause()

    }

    override fun onDestroy() {
        isRefresh = false
        super.onDestroy()
    }

    fun clickItem(data: Message){
        startChatActivity(data)
    }

    fun clickDeleteItem(data: Message){
        messageViewModel.deleteLatestChat(getUserEmail(), data)
    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }
}