package com.swensun.func.livedata

import android.os.AsyncTask
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.swensun.launchIO
import io.reactivex.internal.observers.BlockingBaseObserver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext


class LiveDataViewModel : ViewModel() {

    val modelLiveData = MutableLiveData<Int>()

    fun testSetOrPost() {
        launchIO {
            withContext(Dispatchers.Main) {
                (0 until 1000).forEach {
                    modelLiveData.value = (it)
                }
            }
        }
    }
}