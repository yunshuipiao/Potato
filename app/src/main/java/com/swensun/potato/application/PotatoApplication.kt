package com.swensun.potato.application

import android.app.Application
import android.content.Context
import com.swensun.TimeLog
import com.swensun.library_crash.CrashUtil
import com.swensun.swutils.SwUtils
import kotlinx.coroutines.coroutineScope
import org.jetbrains.anko.toast


class PotatoApplication : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        TimeLog.reset()
    }

    override fun onCreate() {
        super.onCreate()
        TimeLog.log("1")
//        val callback = object : Choreographer.FrameCallback {
//            override fun doFrame(frameTimeNanos: Long) {
//                val diff = frameTimeNanos / 1000000 - startTime
//                Logger.d("diff: $diff")
//                Choreographer.getInstance().postFrameCallback(this)
//            }
//        }
//        startTime = System.currentTimeMillis()
//        Choreographer.getInstance().postFrameCallback(callback)
//        AppUtils.registerAppStatusChangedListener(object: Utils.OnAppStatusChangedListener
//        {
//            override fun onBackground(activity: Activity?) {
//                toast("后台, $activity")
//
//            }
//
//            override fun onForeground(activity: Activity?) {
//                toast("前台, $activity")
//            }
//        })
        
        if (SwUtils.debug) {
            CrashUtil.init(this)
        }
        toast("application init")
        TimeLog.log("2")
    }
}