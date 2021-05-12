package com.swensun.swutils.util

import androidx.fragment.app.FragmentActivity
import com.blankj.utilcode.util.ActivityUtils

/**
 * author : zp
 * date : 2021/5/11
 * Potato
 */

val topActivity: FragmentActivity
    get() {
        val act = ActivityUtils.getTopActivity()
        if (act is FragmentActivity) {
            return act
        }
        throw IllegalArgumentException("top activity not FragmentActivity")
    }
