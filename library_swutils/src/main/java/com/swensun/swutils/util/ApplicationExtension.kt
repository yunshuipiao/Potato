package com.swensun.swutils.util

import android.app.Activity
import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.os.Bundle
import java.lang.ref.WeakReference
import java.util.*



private var currentActivity: WeakReference<Activity>? = null

private val activities: LinkedList<WeakReference<Activity>> = LinkedList()

fun Application.currentActivity(): Activity? = currentActivity?.get()

fun Application.isForeground(): Boolean = currentActivity?.get()?.isForeground() ?: false

fun getProcessName(context: Context): String? {
    val pid = android.os.Process.myPid()
    val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    val runningProcesses = am.runningAppProcesses
    return runningProcesses.firstOrNull { it.pid == pid }?.processName
}

fun isMainProcess(context: Context): Boolean {
    val name: String? = getProcessName(context)
    name?.let {
        return name == context.packageName
    }
    return false
}

object LifecycleCallbacks : Application.ActivityLifecycleCallbacks {

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
//        Timber.i( "onActivityCreated: $activity")
        activity?.let {
            activity.lifecycleState = LifecycleState.CREATED
            currentActivity = WeakReference(activity)
            activities.offerFirst(WeakReference(activity))
        }
    }

    override fun onActivityStarted(activity: Activity?) {
//        Timber.i("onActivityStarted: $activity")
        activity?.let {
            activity.lifecycleState = LifecycleState.STARTED
        }
    }

    override fun onActivityResumed(activity: Activity?) {
//        Timber.i("onActivityResumed: $activity")
        activity?.let {
            activity.lifecycleState = LifecycleState.RESUMED
            val ref = findActivity(activity)
            ref?.let {
                activities.remove(ref)
                activities.offerFirst(WeakReference(activity))
                if (currentActivity == null || currentActivity?.get() != activity)
                    currentActivity = ref
            }
        }
    }

    override fun onActivityPaused(activity: Activity?) {
//        Timber.i("onActivityPaused: $activity")
        activity?.let {
            activity.lifecycleState = LifecycleState.PAUSED
        }
    }

    override fun onActivityStopped(activity: Activity?) {
//        Timber.i("onActivityStopped: $activity")
        activity?.let {
            activity.lifecycleState = LifecycleState.STOPPED
            if (currentActivity?.get() == activity) {
                currentActivity?.clear()
                currentActivity = null
            }
        }
    }

    override fun onActivityDestroyed(activity: Activity?) {
//        Timber.i("onActivityDestroyed: $activity")
        activity?.let {
            activity.lifecycleState = LifecycleState.DESTROYED
            clearActivityInList(activity)
        }
    }

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
        // do nothing
    }
}

fun getLatestActivity(cls: Class<*>): WeakReference<Activity>? {
    return activities
            .firstOrNull { cls.isInstance(it.get()) }
}

fun finishAllActivities() {
    activities
            .forEach { it.get()?.finish() }
}

private fun clearActivityInList(activity: Activity) {
    activities
            .filter { activity == it.get() }
            .forEach { activities.remove(it) }
}

private fun findActivity(activity: Activity): WeakReference<Activity>? {
    return activities.firstOrNull { activity == it.get() }
}
