package com.swensun.func.coroutines.ui

import com.swensun.StatusViewModel
import com.swensun.launchIO
import com.swensun.swutils.util.Logger
import kotlinx.coroutines.*

class CoroutinesViewModel : StatusViewModel() {

    var job: Job? = null

    fun delayToast() {
        try {
            job = launchIO {
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
        launchIO {
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
