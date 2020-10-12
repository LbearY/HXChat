package com.example.hxchat.data.bindadapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.hxchat.R
import com.example.hxchat.app.util.TimeUtils
import com.example.hxchat.ui.view.DragBubbleView
import com.king.easychat.glide.GlideApp
import de.hdodenhof.circleimageview.CircleImageView

/**
 *Created by Pbihao
 * on 2020/10/6
 */

object CustomBingAdapter{

    @BindingAdapter(value = ["circleImageUrl"])
    @JvmStatic
    fun circleImageUrl(view: ImageView, url: String){
        Glide.with(view.context.applicationContext)
            .load(url)
            .apply(RequestOptions.bitmapTransform(CircleCrop()))
            .transition(DrawableTransitionOptions.withCrossFade(500))
            .into(view)
    }

    @JvmStatic
    @BindingAdapter(value = ["time"])
    fun TextView.dateFormat(time: String?){
        time?.run {
            text = TimeUtils.formatDate(time,TimeUtils.FORMAT_Y_TO_M_EN)
        } ?: run {
            text = ""
        }

    }

    @JvmStatic
    @BindingAdapter(value = ["time","curTime"])
    fun TextView.dateFormat(time: String?, curTime: Long){
        dateFormat(time,curTime,false)
    }

    @JvmStatic
    @BindingAdapter(value = ["time","curTime","showDate"])
    fun TextView.dateFormat(time: String?, curTime: Long, showDate: Boolean){
        time?.run {
            val date = TimeUtils.formatDate(time,TimeUtils.FORMAT_Y_TO_D)
            val curDate = TimeUtils.formatDate(curTime,TimeUtils.FORMAT_Y_TO_D)
            if(curDate == date){
                text = TimeUtils.formatDate(time,TimeUtils.FORMAT_H_TO_MIN)
            }else{
                text = TimeUtils.formatDate(time,if(showDate) "MM-dd" else TimeUtils.FORMAT_Y_TO_M_EN)
            }

        } ?: run {
            text = ""
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["count"])
    fun TextView.count(count: Int){
        if(count>0){
            text = count.toString()
        }else{
            text = ""
        }

    }

    @JvmStatic
    @BindingAdapter(value = ["count"])
    fun DragBubbleView.count(count: Int){
        reCreate()
        if(count>0){
            setText(count.coerceAtMost(99).toString())
            visibility = View.VISIBLE
        }else{
            setText("")
            visibility = View.INVISIBLE
        }

    }


    @JvmStatic
    @BindingAdapter(value = ["imageUrl"])
    fun ImageView.imageUrl(imageUrl: String?){
        imageUrl?.run {
            var requestOptions = RequestOptions.bitmapTransform(RoundedCorners(20))
            Glide.with(context).load(imageUrl).apply(requestOptions).into(this@imageUrl)
        }

    }

    @JvmStatic
    @BindingAdapter(value = ["avatar"])
    fun ImageView.avatar(imageUrl: String?){
        imageUrl?.run {
            Glide.with(context).load(imageUrl).error(R.drawable.default_avatar).into(this@avatar)
        }?: run{
            Glide.with(context).load(R.drawable.default_avatar).into(this@avatar)
        }

    }

    @JvmStatic
    @BindingAdapter(value = ["avatar"])
    fun CircleImageView.avatar(avatar: String?){
        avatar?.run {
            Glide.with(context).load(avatar).error(R.drawable.default_avatar).into(this@avatar)
        } ?: run{
            Glide.with(context).load(R.drawable.default_avatar).into(this@avatar)
        }
    }
}