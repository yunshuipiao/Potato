package com.swensun.potato

import androidx.lifecycle.ViewModel
import com.swensun.swutils.util.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    fun testCoroutines() {
        (0 until 1000).forEach {
            launchIO {
                delay(1000)
                Logger.d("thread:${Thread.currentThread().name}, $it")
            }
        }
    }
}