package com.swensun.potato

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MainViewModel : ViewModel() {

    var countLiveData = MutableLiveData<Int>()

    fun countToast(count: Int) {
        countLiveData.value = (count)

        viewModelScope.launch {

        }
    }

    val first = liveData {
        emit("123")
    }
}