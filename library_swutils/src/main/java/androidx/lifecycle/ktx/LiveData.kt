package androidx.lifecycle.ktx

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<T>.noSticky(): LiveData<T> {
    postValue(null)
    return this
}