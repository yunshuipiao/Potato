package com.swensun.potato

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ktx.SingleEvent
import com.swensun.func.customview.LifecycleInterface

object GlobalViewModel : ViewModel(), LifecycleInterface {

    val globalLiveData = MutableLiveData<SingleEvent<GlobalEvent>>()

    override fun onDestroy() {
//        numberLiveData.value = null
    }
}

class GlobalEvent {
    var from = ""
}