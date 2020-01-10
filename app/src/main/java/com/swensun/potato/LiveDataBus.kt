package com.swensun.potato

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import java.util.concurrent.ConcurrentHashMap

object LiveDataBus : LifecycleObserver {

    private val map = ConcurrentHashMap<String, MutableLiveData<Any>>()

    fun <T> get(key: String): MutableLiveData<T> {
        val livedata = map[key]
        if (livedata == null) {
            val mutableLiveData = MutableLiveData<T>()
            map[key] = mutableLiveData as MutableLiveData<Any>
            return mutableLiveData
        }
        return livedata as MutableLiveData<T>
    }
}