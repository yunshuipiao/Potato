package com.swensun

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope

object TimeLog : CoroutineScope by MainScope() {
    private var timestamp = 0L
    const val TAG = "time_log"

    @JvmStatic
    fun reset() {
        timestamp = System.currentTimeMillis()
        Log.i(TAG, "time log start")
    }

    @JvmStatic
    fun log(msg: String) {
        Log.i(TAG, "$msg, timeDiff: ${System.currentTimeMillis() - timestamp}")
    }
}