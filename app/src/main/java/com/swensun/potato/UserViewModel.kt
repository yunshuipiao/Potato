package com.swensun.potato

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserViewModel : ViewModel() {

    
    val name = MutableLiveData<String>("123")

    fun changeName(str: String) {
        name.postValue(str)
    }
}