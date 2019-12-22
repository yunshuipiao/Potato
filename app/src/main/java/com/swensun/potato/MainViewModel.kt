package com.swensun.potato

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ktx.SingleEvent

class MainViewModel : ViewModel() {

    val navigationLiveData = MutableLiveData<SingleEvent<String>>(
        SingleEvent()
    )

    init {
        navigationLiveData.postValue(SingleEvent("2"))
        navigationLiveData.postValue(SingleEvent("4"))
        navigationLiveData.postValue(SingleEvent("5"))
        navigationLiveData.postValue(SingleEvent("6"))
    }

    fun navigation() {
        navigationLiveData.postValue(SingleEvent("3"))
    }
}


