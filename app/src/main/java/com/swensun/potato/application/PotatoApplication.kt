package com.swensun.potato.application

import android.app.Application
import android.content.Context
import android.view.Choreographer
import com.swensun.swutils.util.LogUtils
import java.util.logging.LogManager

class PotatoApplication : Application() {

    var startTime: Long = 0

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()
//        val callback = object : Choreographer.FrameCallback {
//            override fun doFrame(frameTimeNanos: Long) {
//                val diff = frameTimeNanos / 1000000 - startTime
//                LogUtils.d("diff: $diff")
//                Choreographer.getInstance().postFrameCallback(this)
//            }
//        }
//        startTime = System.currentTimeMillis()
//        Choreographer.getInstance().postFrameCallback(callback)
    }
    
}