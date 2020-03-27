package com.swensun.potato.application

import android.app.Activity
import android.app.Application
import android.content.Context
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.Utils
import com.swensun.library_crash.CrashUtil
import com.swensun.potato.AppStatusUtils
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
        CrashUtil.init(this)
    }
}