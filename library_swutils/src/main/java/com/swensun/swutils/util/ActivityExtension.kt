package com.swensun.swutils.util

import android.app.Activity
import java.util.*

enum class LifecycleState {
    CREATED,
    STARTED,
    RESUMED,
    PAUSED,
    STOPPED,
    DESTROYED // be destroyed or not exist
}

private val lifecycleStateMap: WeakHashMap<Activity, LifecycleState> = WeakHashMap()

var Activity.lifecycleState: LifecycleState
    get() = lifecycleStateMap[this] ?: LifecycleState.DESTROYED
    set(value) {
        when(value) {
            LifecycleState.DESTROYED -> lifecycleStateMap.remove(this)
            else -> lifecycleStateMap[this] = value
        }
    }

fun Activity.isForeground(): Boolean {
    return lifecycleState == LifecycleState.RESUMED
}
