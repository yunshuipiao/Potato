package com.swensun

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

sealed class StateEvent(var msg: String = "") {
    object LOADING : StateEvent()
    object SUCCESS : StateEvent()
    object ERROR : StateEvent()
}

open class StateViewModel : ViewModel() {

    val stateLiveData = MutableLiveData<StateEvent>()

    protected fun postLoading(msg: String = "") {
        stateLiveData.postValue(StateEvent.LOADING.apply {
            this.msg = msg
        })
    }

    protected fun postSuccess() {
        stateLiveData.postValue(StateEvent.SUCCESS)
    }

    protected fun postError(msg: String = "") {
        stateLiveData.postValue(StateEvent.ERROR.apply {
            this.msg = msg
        })
    }
}

fun ViewModel.launchIO(block: suspend CoroutineScope.() -> Unit) {
    viewModelScope.launch(Dispatchers.IO) {
        block()
    }
}

