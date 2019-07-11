package com.swensun.swutils.util

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer


// Android Architecture Components Extension


// nonnull livedata
class NonNullMediatorLiveData<T> : MediatorLiveData<T>()

fun <T> LiveData<T>.nonNull(): NonNullMediatorLiveData<T> {
    val mediator: NonNullMediatorLiveData<T> = NonNullMediatorLiveData()
    mediator.addSource(this, Observer {
        it?.let {
            mediator.value = it
        }
    })
    return mediator
}

fun <T> NonNullMediatorLiveData<T>.observe(
        owner: LifecycleOwner, observer: (t: T) -> Unit) {
    this.observe(owner, Observer {
        it?.let(observer)
    })
}

// use livedata as Rxjava2

fun <T> LiveData<T>.distinct(): LiveData<T> {
    val mediatorLiveData: MediatorLiveData<T> = MediatorLiveData()
    mediatorLiveData.addSource(this, Observer {
        if (it != mediatorLiveData.value) {
            mediatorLiveData.value = it
        }
    })
    return mediatorLiveData
}

//fun <T> LiveData<T>.observe(
//        owner: LifecycleOwner, observer: (t: T?) -> Unit) {
//   observe(owner, Observer {
//       observer(it)
//   })
//}


