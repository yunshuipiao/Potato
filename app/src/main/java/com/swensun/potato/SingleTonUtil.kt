package com.swensun.potato

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swensun.swutils.ui.showToast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

/**
 * author : zp
 * date : 2020/10/9
 * Potato
 */

object SingleTonUtil : ViewModel() {

    fun toast() {
        launchIO {
            withContext(Dispatchers.Main) {
                showToast("lalala")
            }
        }
    }
}

fun ViewModel.launchIO(block: suspend CoroutineScope.() -> Unit) {
    viewModelScope.launch(Dispatchers.IO) {
        block()
    }
}