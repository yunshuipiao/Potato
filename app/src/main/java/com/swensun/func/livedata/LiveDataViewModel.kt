package com.swensun.func.livedata

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.swensun.launchIO
import io.reactivex.internal.observers.BlockingBaseObserver
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay

class LiveDataViewModel : ViewModel() {

    var job: Job? = null

    fun testCount() {
        job?.cancel()
        job = launchIO {
            (0..1000).forEach {
                delay(100)
                oneLiveData.postValue(it)
            }
        }
    }

    var oneLiveData = MutableLiveData<Int>()
}