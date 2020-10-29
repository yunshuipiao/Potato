package com.swensun.http

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.blankj.utilcode.util.AppUtils
import com.swensun.swutils.util.Logger

open class SimpleActivityLifecycleCallbacks : Application.ActivityLifecycleCallbacks {
    override fun onActivityPaused(activity: Activity?) {
        Logger.d("--onActivityPaused: $activity")


    }

    override fun onActivityResumed(activity: Activity?) {
        Logger.d("--onActivityResumed: $activity")
    }

    override fun onActivityStarted(activity: Activity?) {
        Logger.d("--onActivityStarted: $activity")

    }

    override fun onActivityDestroyed(activity: Activity?) {
        Logger.d("--onActivityDestroyed: $activity")

    }

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
    }

    override fun onActivityStopped(activity: Activity?) {
        Logger.d("--onActivityStopped: $activity")

    }

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
        Logger.d("--onActivityCreated: $activity")
    }
}