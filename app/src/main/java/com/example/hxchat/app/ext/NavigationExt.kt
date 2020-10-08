package com.example.hxchat.app.ext

import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import me.hgj.jetpackmvvm.navigation.NavHostFragment

/**
 *Created by Pbihao
 * on 2020/10/6
 */

fun Fragment.nav() : NavController{
    return NavHostFragment.findNavController(this)
}