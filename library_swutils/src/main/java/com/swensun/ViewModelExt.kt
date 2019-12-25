package com.swensun

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

enum class StateEvent {
    LOADING,
    SUCCESS,
    ERROR
}

open class StateViewModel: ViewModel() {

    val stateLiveData = MutableLiveData<StateEvent>()

    protected fun postLoading(){
        stateLiveData.postValue(StateEvent.LOADING)
    }


    protected fun postSuccess() {
        stateLiveData.postValue(StateEvent.SUCCESS)
    }

    protected fun postError() {
        stateLiveData.postValue(StateEvent.ERROR)
    }
    
}

