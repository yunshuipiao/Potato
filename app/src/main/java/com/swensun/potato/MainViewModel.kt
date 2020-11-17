package com.swensun.potato

import androidx.lifecycle.ViewModel
import com.swensun.launchIO
import com.swensun.swutils.util.Logger

class MainViewModel : ViewModel() {
    fun testCoroutines() {
        (0 until 1000).forEach {
            launchIO {
                formatJson()
                Logger.d("thread:${Thread.currentThread().name}, $it")
            }
        }

    }

    fun formatJson() {
    }
    
}