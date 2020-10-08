package com.example.hxchat.ui.fragment.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.ObservableField
import androidx.fragment.app.Fragment
import com.example.hxchat.R
import com.example.hxchat.app.base.BaseFragment
import com.example.hxchat.app.ext.showMessage
import com.example.hxchat.databinding.FragmentHomeBinding
import com.example.hxchat.viewmodel.state.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import me.hgj.jetpackmvvm.callback.databind.IntObservableField
import me.hgj.jetpackmvvm.callback.databind.StringObservableField
import java.util.*
import kotlin.math.log

/**
 *Created by Pbihao
 * on 2020/10/4
 */

class HomeFragment: BaseFragment<HomeViewModel, FragmentHomeBinding>(), View.OnClickListener{

    override fun layoutId(): Int = R.layout.fragment_home

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.vm = mViewModel
    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }
}