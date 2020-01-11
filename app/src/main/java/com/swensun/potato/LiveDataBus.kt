package com.swensun.potato

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import com.swensun.swutils.ui.showToast
import java.util.concurrent.ConcurrentHashMap

object LiveDataBus {

    private val map = ConcurrentHashMap<String, MutableLiveData<Any>>()

    fun <T> get(key: String): MutableLiveData<T> {
        val liveData = map[key]
        if (liveData == null) {
            val mutableLiveData = MutableLiveData<T>()
            map[key] = mutableLiveData as MutableLiveData<Any>
            return mutableLiveData
        }
        return liveData as MutableLiveData<T>
    }

    fun onDestory() {
        map.clear()
        showToast("livedatabus reset")
    }
}