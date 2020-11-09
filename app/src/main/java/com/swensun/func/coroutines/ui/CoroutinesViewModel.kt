package com.swensun.func.coroutines.ui

import com.swensun.StatusViewModel
import com.swensun.launchIO
import com.swensun.swutils.ui.showToast
import com.swensun.swutils.util.Logger
import kotlinx.coroutines.*

class CoroutinesViewModel : StatusViewModel() {

    var job: Job? = null

    fun delayToast() {
        try {
            job = launchIO {
                delay(2000)
                withContext(Dispatchers.Main) {
                    showToast("delay toast")
                }
            }
        } catch (e: Throwable) {
            log("delayToast ${e.message}")
        }
    }

    fun cancel() {
        launchIO {
            job?.cancelAndJoin()
        }
    }

    private fun log(msg: String) {
        job?.let {
            Logger.d("$msg job ${it.isCompleted}, ${it.isCancelled}, ${it.isActive}")
        }
    }
}
