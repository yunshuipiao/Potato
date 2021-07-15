package com.swensun.func.coroutines.ui

import com.swensun.StatusViewModel
import com.swensun.launch
import com.swensun.launchIO
import com.swensun.swutils.util.Logger
import kotlinx.coroutines.*

class CoroutinesViewModel : StatusViewModel() {

    var job: Job? = null

    fun delayToast() {
        try {
            job = this.launch {
                delay(2000)
                withContext(Dispatchers.Main) {
                    log("success")
                }
            }
        } catch (e: Throwable) {
            log("delayToast ${e.message}")
        }
    }

    fun cancel() {
        this.launch {
            job?.cancelAndJoin()
            log("cancel")
        }
    }

    private fun log(msg: String) {
        job?.let {
            Logger.d("__$msg job ${it.isCompleted}, ${it.isCancelled}, ${it.isActive}, $job")
        }
    }
}
