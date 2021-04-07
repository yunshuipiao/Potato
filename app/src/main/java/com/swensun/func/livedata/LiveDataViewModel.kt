package com.swensun.func.livedata

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.swensun.launchIO
import io.reactivex.internal.observers.BlockingBaseObserver
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay


data class User(var name: String = "abc")

class LiveDataViewModel : ViewModel() {
    val modelLiveData = MutableLiveData<User>()
    
}