package com.swensun.potato

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.swensun.swutils.util.Logger

object AppStatusUtils {

    private val activityies = arrayListOf<Activity?>()

    fun init(application: Application) {
        application.registerActivityLifecycleCallbacks(object :
            Application.ActivityLifecycleCallbacks {
            override fun onActivityPaused(activity: Activity?) {
            }

            override fun onActivityResumed(activity: Activity?) {
            }

            override fun onActivityStarted(activity: Activity?) {
            }

            override fun onActivityDestroyed(activity: Activity?) {
                Logger.d("activity-- destroy: $activity")

                activityies.remove(activity)
                if (activityies.isEmpty()) {
                    Logger.d("activity-- app exit")
                }
            }

            override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
            }

            override fun onActivityStopped(activity: Activity?) {
            }

            override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
                activityies.add(activity)
                Logger.d("activity-- create: $activity")
            }
        })
    }


}