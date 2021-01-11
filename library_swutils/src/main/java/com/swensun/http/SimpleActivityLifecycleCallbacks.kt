package com.swensun.http

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.swensun.swutils.util.Logger

open class SimpleActivityLifecycleCallbacks : Application.ActivityLifecycleCallbacks {

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
        Logger.d("__onActivityCreated: $activity")
    }

    override fun onActivityStarted(activity: Activity?) {
        Logger.d("__onActivityStarted: $activity")
    }

    override fun onActivityResumed(activity: Activity?) {
        Logger.d("__onActivityResumed: $activity")
    }

    override fun onActivityPaused(activity: Activity?) {
        Logger.d("__onActivityPaused: $activity, ${activity?.isFinishing}\"")
    }

    override fun onActivityStopped(activity: Activity?) {
        Logger.d("__onActivityStopped: $activity, ${activity?.isFinishing}")
    }

    override fun onActivityDestroyed(activity: Activity?) {
        Logger.d("__onActivityDestroyed: $activity")
    }

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
    }
}