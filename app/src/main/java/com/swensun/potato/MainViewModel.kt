package com.swensun.potato

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    var countLiveData = MutableLiveData<Int>()

    fun countToast(count: Int) {
        countLiveData.value = (count)

        viewModelScope.launch {

        }
    }
}