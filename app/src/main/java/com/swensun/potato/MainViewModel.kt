package com.swensun.potato

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {

    var count = 0
    var countLiveData = MutableLiveData<Int>()
    fun countToast(count: Int) {
        this.count = count
        countLiveData.postValue(count)
    }
}