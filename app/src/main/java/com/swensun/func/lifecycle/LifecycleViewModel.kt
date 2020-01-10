package com.swensun.func.lifecycle

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LifecycleViewModel : ViewModel() {

    val strLiveData = MutableLiveData<String>()

    fun postLifecycler() {
        val str = strLiveData.value
        if (str.isNullOrBlank()) {
            strLiveData.postValue("lifecycle")
        } else {
            strLiveData.postValue(strLiveData.value)
        }
    }
}
