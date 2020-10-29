package com.swensun.func.livedata

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LiveDataViewModel : ViewModel() {
    fun oneClick() {
        oneClickCount += 1
        oneLiveData.postValue(oneClickCount)
    }

    fun twoClick() {
        twoClickCount += 1
        twoLiveData.postValue(twoClickCount)
    }

    var oneLiveData = MutableLiveData<Int>()
    var twoLiveData = MutableLiveData<Int>()

    var oneAndTwoLiveData = MediatorLiveData<Int>().apply {
        addSource(oneLiveData) {
            val two = twoLiveData.value ?: 0
            this.postValue(it + two)
        }
        addSource(twoLiveData) {
            val one = oneLiveData.value ?: 0
            this.postValue(it + one)
        }
    }

    private var oneClickCount = 0
    private var twoClickCount = 0
}
