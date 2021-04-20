package androidx.lifecycle

import java.util.concurrent.ConcurrentHashMap

/**
 * author : zp
 * date : 2021/4/20
 * Potato
 */

object LiveDataBus {
    val map = ConcurrentHashMap<String, MutableLiveData<Any>>()
    inline fun <reified T> get(key: String): MutableLiveData<T> {
        val liveData = map[key]
        if (liveData == null) {
            val mutableLiveData = MutableLiveData<T>()
            map[key] = mutableLiveData as MutableLiveData<Any>
            return mutableLiveData
        }
        return liveData as MutableLiveData<T>
    }

    fun onDestroy() {
        map.clear()
    }
}


