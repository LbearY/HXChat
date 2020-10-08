package com.example.hxchat.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import com.example.hxchat.R
import com.example.hxchat.app.base.BaseActivity
import com.example.hxchat.databinding.ActivityMainBinding
import com.example.hxchat.viewmodel.state.MainViewModel

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {
    override fun layoutId(): Int = R.layout.activity_main

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun createObserver() {
        super.createObserver()
    }


}