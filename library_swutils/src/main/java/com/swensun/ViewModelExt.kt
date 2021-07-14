package com.swensun

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
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

    protected fun postEmpty(msg: String = "") {
        
    }
}

fun ViewModel.launch(block: suspend CoroutineScope.() -> Unit) = viewModelScope.launch { block() }
fun ViewModel.launchIO(block: suspend CoroutineScope.() -> Unit) = viewModelScope.launch(Dispatchers.IO) { block() }
fun Fragment.launchIO(block: suspend CoroutineScope.() -> Unit) = lifecycleScope.launch { block() }
fun AppCompatActivity.launchIO(block: suspend CoroutineScope.() -> Unit) = lifecycleScope.launch { block() }

