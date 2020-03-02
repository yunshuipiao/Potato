package com.swensun.potato.application

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.Utils
import com.swensun.potato.AppStatusUtils
import com.swensun.potato.MainActivity
import com.swensun.swutils.util.Logger
import org.jetbrains.anko.toast


class PotatoApplication : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()
//        val callback = object : Choreographer.FrameCallback {
//            override fun doFrame(frameTimeNanos: Long) {
//                val diff = frameTimeNanos / 1000000 - startTime
//                Logger.d("diff: $diff")
//                Choreographer.getInstance().postFrameCallback(this)
//            }
//        }
//        startTime = System.currentTimeMillis()
//        Choreographer.getInstance().postFrameCallback(callback)
        AppStatusUtils.init(this)
        AppUtils.registerAppStatusChangedListener(object: Utils.OnAppStatusChangedListener
        {
            override fun onBackground(activity: Activity?) {
                toast("后台, $activity")

            }

            override fun onForeground(activity: Activity?) {
                toast("前台, $activity")
            }
        })
    }
}