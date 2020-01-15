package com.swensun.potato.application

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import com.swensun.potato.AppStatusUtils
import com.swensun.potato.MainActivity
import com.swensun.swutils.util.Logger


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
        registerActivityLifecycleCallbacks(object: ActivityLifecycleCallbacks {
            private var  mMainOnPaused = false
            private var  mMainOnResumed = false
            override fun onActivityPaused(activity: Activity?) {
                mMainOnPaused = activity is MainActivity
            }

            override fun onActivityResumed(activity: Activity?) {
                mMainOnResumed = activity is MainActivity
                if (mMainOnPaused && mMainOnResumed) {
                    Logger.d("$activity from launcher or other app")
                }
                
            }

            override fun onActivityStarted(activity: Activity?) {
                
            }

            override fun onActivityDestroyed(activity: Activity?) {
                
            }

            override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
                
            }

            override fun onActivityStopped(activity: Activity?) {
                
            }

            override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
                
            }

        })

    }
}