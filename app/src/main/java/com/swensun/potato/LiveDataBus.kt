package com.swensun.potato

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ktx.SingleEvent
import com.swensun.swutils.ui.showToast
import java.util.concurrent.ConcurrentHashMap

object LiveDataBus {

    private val map = ConcurrentHashMap<String, MutableLiveData<Any>>()
    fun <T> get(key: String): MutableLiveData<SingleEvent<T>> {
        val liveData = map[key]
        if (liveData == null) {
            val mutableLiveData = MutableLiveData<SingleEvent<T>>()
            map[key] = mutableLiveData as MutableLiveData<Any>
            return mutableLiveData
        }
        return liveData as MutableLiveData<SingleEvent<T>>
    }

    fun onDestory() {
        map.clear()
        showToast("livedatabus reset")
    }
}

val LiveDataBus.Global: String
    get() = "global"