package com.swensun.func.feature

import android.net.TrafficStats
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FeatureViewModel : ViewModel() {

    val speedLiveData = MutableLiveData<Double>()

    fun getNetSpeed() {
        viewModelScope.launch {
            (0 until Int.MAX_VALUE).forEach {
                val start = TrafficStats.getTotalRxBytes()
                delay(500)
                val end = TrafficStats.getTotalRxBytes()

                val totalBytes = end - start
                speedLiveData.postValue((totalBytes / 1024) / 0.5)
            }
        }
    }
}
