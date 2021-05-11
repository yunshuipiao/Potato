package com.swensun.func.livedata

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.swensun.launchIO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext


class LiveDataViewModel : ViewModel() {

    val modelLiveData = MutableLiveData<Int>()
    val modelFlow = MutableStateFlow("")

    fun testSetOrPost() {
        launchIO {
            withContext(Dispatchers.Main) {
                (0 until 1000).forEach {
                    modelLiveData.value = (it)
                }
            }
        }
    }

    fun testFlow(number: Int) {
        modelFlow.value = number.toString()
    }
}