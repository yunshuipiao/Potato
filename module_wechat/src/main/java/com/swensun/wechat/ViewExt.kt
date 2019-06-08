package com.swensun.wechat

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

fun <T> ImageView.setCircularImage(t: T?, roundingRadius: Int = 0) {
    if (t == null) return
    if (roundingRadius == 0) {
        Glide.with(context).load(t)
            .error(R.mipmap.ic_launcher)
            .apply(RequestOptions.circleCropTransform()).into(this)
    } else {
        Glide.with(context).load(t)
            .error(R.mipmap.ic_launcher)
            .apply(
                RequestOptions()
                    .transforms(CenterCrop(), RoundedCorners(roundingRadius))
            )
            .into(this)
    }
}

fun <T> ImageView.setImage(t: T?) {
    if (t == null) return
    Glide.with(context)
        .load(t)
        .error(R.mipmap.ic_launcher)
        .into(this)
}