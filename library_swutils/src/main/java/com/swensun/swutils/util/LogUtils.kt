package com.swensun.swutils.util

import com.blankj.utilcode.util.LogUtils
import com.swensun.swutils.SwUtils


object LogUtils {

    init {
        val config = LogUtils.getConfig()
        config.isLog2FileSwitch = true
        config.setBorderSwitch(false)
        config.isLogHeadSwitch = false
    }

    fun d(msg: String) {
        d(null, msg)
    }

    fun d(any: Any) {
        LogUtils.d(any)
        LogUtils.file(any)
    }

    fun d(tag: String? = null, msg: String = "") {
        val tempTag = if(tag.isNullOrBlank()) "" else "$tag:"
        LogUtils.d("$tempTag: $msg")
        LogUtils.file(tempTag, msg)
    }
}

