package com.dueeeke.videoplayer.util

import android.util.Log
import com.dueeeke.videoplayer.player.VideoViewManager.config

/**
 * 日志类
 * Created by dueeeke on 2017/6/5.
 */
object L {
    private const val TAG = "DKPlayer"
    private var isDebug = config.isEnableLog

    @JvmStatic
    fun d(msg: String?) {
        if (isDebug) {
            Log.d(TAG, msg)
        }
    }

    fun e(msg: String?) {
        if (isDebug) {
            Log.e(TAG, msg)
        }
    }

    fun i(msg: String?) {
        if (isDebug) {
            Log.i(TAG, msg)
        }
    }

    fun w(msg: String?) {
        if (isDebug) {
            Log.w(TAG, msg)
        }
    }

    fun setDebug(isDebug: Boolean) {
        L.isDebug = isDebug
    }
}