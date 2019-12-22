package com.swensun.potato

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.swensun.swutils.aac.Event

class MainViewModel : ViewModel() {

    val navigationLiveData = MutableLiveData<Event<String>>(Event("1"))

    init {
        navigationLiveData.postValue(Event("2"))
    }

    fun navigation() {
        navigationLiveData.postValue(Event("3"))
    }
}