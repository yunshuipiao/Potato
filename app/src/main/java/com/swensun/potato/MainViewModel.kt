package com.swensun.potato

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {

    var countLiveData = MutableLiveData<Int>()

    fun countToast(count: Int) {
        countLiveData.value = (count)
    }
}