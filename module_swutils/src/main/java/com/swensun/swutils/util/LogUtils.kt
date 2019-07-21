package com.swensun.swutils.util

import com.orhanobut.logger.Logger
import java.lang.Exception


object LogUtils {
    fun d(msg: String) {
        d(null, msg)
    }

    fun d(tag: String? = null, msg: String = "") {
        val tempTag = if(tag.isNullOrBlank()) "" else "$tag:"
        Logger.d(msg)
    }
}

