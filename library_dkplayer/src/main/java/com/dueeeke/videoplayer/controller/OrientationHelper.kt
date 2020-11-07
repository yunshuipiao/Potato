package com.dueeeke.videoplayer.controller

import android.content.Context
import android.view.OrientationEventListener


/**
 * 设备方向监听
 */
class OrientationHelper(context: Context?) : OrientationEventListener(context) {

    private var mLastTime = 0L
    var onOrientationChangeListener: OnOrientationChangeListener? = null

    override fun onOrientationChanged(orientation: Int) {
        val currentTime = System.currentTimeMillis()
        if (currentTime - mLastTime < 300L) {
            //300毫秒检测一次
            return
        }
        onOrientationChangeListener?.onOrientationChanged(orientation)
        mLastTime = currentTime
    }

    interface OnOrientationChangeListener {
        fun onOrientationChanged(orientation: Int)
    }
}
