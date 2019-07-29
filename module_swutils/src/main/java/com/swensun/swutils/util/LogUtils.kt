package com.swensun.swutils.util

import com.orhanobut.logger.Logger
import java.lang.Exception
import java.util.logging.LogManager


object LogUtils {
    fun d(msg: String) {
        d(null, msg)
    }

    fun d(any: Any) {
        Logger.d(any)
    }

    fun d(tag: String? = null, msg: String = "") {
        val tempTag = if(tag.isNullOrBlank()) "" else "$tag:"
        Logger.d(msg)
    }
}

