package com.swensun

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

sealed class StatusEvent(var msg: String = "") {
    object LOADING : StatusEvent()
    object SUCCESS : StatusEvent()
    object ERROR : StatusEvent()
    object EMPTY : StatusEvent()
}

open class StatusViewModel : ViewModel() {

    val statusLiveData = MutableLiveData<StatusEvent>()

    protected fun postLoading(msg: String = "") {
        statusLiveData.postValue(StatusEvent.LOADING.apply {
            this.msg = msg
        })
    }

    protected fun postSuccess() {
        statusLiveData.postValue(StatusEvent.SUCCESS)
    }

    protected fun postError(msg: String = "") {
        statusLiveData.postValue(StatusEvent.ERROR.apply {
            this.msg = msg
        })
    }
}

fun ViewModel.launchIO(block: suspend CoroutineScope.() -> Unit): Job {
    return viewModelScope.launch(Dispatchers.IO) {
        block()
    }
}

