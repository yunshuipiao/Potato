package com.swensun.func.livedata

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LiveDataViewModel : ViewModel() {
    var oneLiveData = MediatorLiveData<String>()
    var twoLiveData = MutableLiveData<Int>()
}
