package androidx.lifecycle.ktx

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

/**
 * author : zp
 * date : 2020/11/25
 * Potato
 */

fun <T> LiveData<T>.debounce(
    duration: Long = 1000L,
    coroutineContext: CoroutineContext = Dispatchers.Main
) = MediatorLiveData<T>().also { mld ->

    val source = this
    var job: Job? = null

    mld.addSource(source) {
        job?.cancel()
        job = CoroutineScope(coroutineContext).launch {
            delay(duration)
            mld.value = source.value
        }
    }
}