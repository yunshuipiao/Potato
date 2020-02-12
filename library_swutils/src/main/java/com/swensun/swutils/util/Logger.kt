package com.swensun.swutils.util

import android.util.Log
import com.blankj.utilcode.util.LogUtils
import com.swensun.swutils.SwUtils


object Logger {

    private var tag = "swensun"
    private val debug = Log.isLoggable(tag, Log.DEBUG)

    init {
        val config = LogUtils.getConfig()
        config.isLog2FileSwitch = true
        config.setBorderSwitch(false)
        config.isLogHeadSwitch = false
    }

    fun d(msg: Any) {
        if (SwUtils.isDebug || debug) {
            LogUtils.d(msg)
        }
    }

    fun d(tag: String, msg: String) {
        d("$tag: $msg")
    }

    fun i(msg: String) {
        if (debug) {
            LogUtils.i(msg)
        }
    }
}

