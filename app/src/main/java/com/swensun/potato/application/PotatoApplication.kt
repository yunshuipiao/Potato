package com.swensun.potato.application

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import com.swensun.potato.MainActivity
import com.swensun.potato.SharedViewModelFactory

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
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityPaused(activity: Activity?) {
                
            }

            override fun onActivityResumed(activity: Activity?) {
                
            }

            override fun onActivityStarted(activity: Activity?) {
                
            }

            override fun onActivityDestroyed(activity: Activity?) {
                if (activity is MainActivity) {
                    SharedViewModelFactory.clearViewModel()
                }
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