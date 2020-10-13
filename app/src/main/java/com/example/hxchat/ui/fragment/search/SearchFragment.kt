package com.example.hxchat.ui.fragment.search

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.hxchat.R
import com.example.hxchat.app.base.BaseFragment
import com.example.hxchat.app.ext.hideSoftKeyboard
import com.example.hxchat.app.util.StringUtils
import com.example.hxchat.data.model.bean.User
import com.example.hxchat.databinding.FragmentSearchBinding
import com.example.hxchat.ui.adapter.BindingAdapter
import com.example.hxchat.ui.adapter.DividerItemDecoration
import com.example.hxchat.viewmodel.request.RequestSearchViewModel
import com.example.hxchat.viewmodel.state.SearchViewModel
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.flow.combine
import me.hgj.jetpackmvvm.ext.nav
import me.hgj.jetpackmvvm.ext.parseState


/**
 *Created by Pbihao
 * on 2020/10/12
 */
class SearchFragment : BaseFragment<SearchViewModel, FragmentSearchBinding>(){
    val mAdapter by lazy { BindingAdapter<User>(R.layout.rv_search_user_item) }

    private val requestSearchViewModel : RequestSearchViewModel by viewModels()

    var keyword : String? = null

    override fun layoutId(): Int = R.layout.fragment_search

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.rvm = requestSearchViewModel
        mDatabind.click = ProxyClick()

        srl.setColorSchemeResources(R.color.colorAccent)
        srl.setOnRefreshListener { search(keyword) }

        etSearch.setOnTouchListener{v, event ->
            when (event.action){
                MotionEvent.ACTION_UP -> clickRightClear(etSearch, event)
            }
            false
        }
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                etSearch.isSelected = !TextUtils.isEmpty(s)
            }

        })
        etSearch.setOnKeyListener { v, keyCode, event ->
            if(keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP){
                search(etSearch.text.toString())
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }

        rv.layoutManager = LinearLayoutManager(context)
        rv.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        rv.adapter = mAdapter

        mAdapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener{ adapter, v, position->
            clickItem(mAdapter.getItem(position)!!)
        }

    }

    override fun createObserver() {
        requestSearchViewModel.searchResultData.observe(viewLifecycleOwner, Observer {resultState ->
            parseState(resultState, {
                srl.isRefreshing = false
                mAdapter.replaceData(it)
            })
        })
    }

    fun search(str: String?){
        if(StringUtils.isNotBlank(str) &&  str != ""){
            srl.isRefreshing = true
            requestSearchViewModel.search(str!!)
        }else{
            showToast(R.string.tips_search_key_is_empty)
            srl.isRefreshing = false
        }
    }

    fun clickItem(item: User){

    }

    inner class ProxyClick{
        fun clickBack(){
            Log.d("back","点击了返回按钮")
            hideSoftKeyboard(activity)
            nav().navigateUp()
        }
    }

    private fun clickRightClear(tv: TextView, event: MotionEvent): Boolean {
        val drawableRight = tv.compoundDrawables[2]
        if (drawableRight != null && event.rawX >= tv.right - drawableRight.bounds.width()) {
            tv.text = null
            return true
        }
        return false
    }
}