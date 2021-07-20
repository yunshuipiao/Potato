package com.swensun.func.livedata

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.swensun.launch
import kotlinx.coroutines.flow.MutableStateFlow


class LiveDataViewModel : ViewModel() {

    private val intFlow = MutableStateFlow(0)
    val intLiveData
        get() = intFlow.asLiveData()
    
    fun delayCallback() {
        launch {
            (0..20).forEach {
                val result = LiveDataRepository.testFlow(it)
                intFlow.value = result
            }
        }
    }
}