package com.swensun.swutils.util

import android.app.Activity

/**
 * author : zp
 * date : 2020/11/30
 * Potato
 */


object ActUtils {
    private var activities = arrayListOf<Activity?>()

    fun addActivity(activity: Activity?) {
        activities.add(activity)
        log("add")
    }

    fun removeActivity(activity: Activity?) {
        activities.remove(activity)
        log("remove")
    }

    fun getTopActivity(): Activity {
        return activities.filterNotNull().first()
    }

    fun log(msg: String) {
        Logger.d("ActUtils, $msg, ${activities.map { it?.javaClass?.simpleName }}")
    }
}
