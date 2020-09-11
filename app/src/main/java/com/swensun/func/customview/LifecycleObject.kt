package com.swensun.func.customview

import com.swensun.swutils.ui.showToast
import kotlinx.coroutines.*

class LifecycleObject : LifecycleInterface, CoroutineScope by MainScope() {

    fun print() {
        launch {
            delay(1000)
            showToast("lo, ${Thread.currentThread().name}")
        }
    }

    override fun onDestroy() {
        cancel()
    }
}