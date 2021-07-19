package com.swensun.func.livedata

import androidx.lifecycle.ViewModel
import com.swensun.launch
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow


class LiveDataViewModel : ViewModel() {

    val modelFlow = MutableStateFlow("")
    val intFlow = MutableStateFlow(0)
    fun testFlow(number: Int) {
        modelFlow.value = number.toString()
    }


    fun delayCallback() {
        launch {
            (0..10).forEach {
                delay(1000)
                intFlow.value = it
            }
        }
    }
}