package com.swensun.swutils.util

import java.lang.Exception


object LogUtils {
    fun d(msg: String) {
        d(null, msg)
    }

    fun d(tag: String? = null, msg: String = "") {
        val tempTag = if(tag.isNullOrBlank()) "" else "$tag:"
    }

    fun e(msg: String) {
        e(null, msg)
    }

    fun e(exception: Exception? = null, msg : String? = null) {
        val e = if (exception == null) "" else "$exception:"
    }
}