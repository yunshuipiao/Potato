package com.swensun.swutils.util

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

/**
 * author : zp
 * date : 2021/3/10
 * Potato
 */

class ApplicationLifecycleObserver : LifecycleObserver {

    /**
     * 应用程序的整个生命周期中只会执行一次
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        Logger.d("app onCreate")
    }

    /**
     * 应用出现在前台调用
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        Logger.d("app onStart")
    }

    /**
     * 应用出现在前台调用
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        Logger.d("app onResume")
    }

    /**
     * 应用退到后台调用
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        Logger.d("app onPause")
    }

    /**
     * 应用退到后台调用
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        Logger.d("app onStop")
    }

    /**
     * 永远不会被调用
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        Logger.d("app onDestroy")
    }
}