package com.swensun.func.coroutines.ui

import androidx.lifecycle.viewModelScope
import com.swensun.StateViewModel
import com.swensun.func.coroutines.ApiService
import com.swensun.http.BaseResponse
import com.swensun.http.HttpClient
import com.swensun.swutils.util.Logger
import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

class CoroutinesViewModel : StateViewModel() {

    
    private val apiService = HttpClient.retrofit.create(ApiService::class.java)


    fun fetchData() {
        /**
         * 启动一个协程
         */
        postLoading()
        viewModelScope.launch {
            //loading
            val timeDiff = measureTimeMillis {
                withContext(Dispatchers.IO) {
                    val responseOne =  apiFetchOne()
                    val responseTwo =  apiFetchTwo()
                    if (responseOne.success && responseTwo.success) {
                        // success
                        postSuccess()
                    } else {
                        //error
                        postError()
                    }
                }
            }
            Logger.d("timeDiff: $timeDiff")
        }
    }

    private suspend fun apiFetchOne(): BaseResponse<String> {
        /**
         * 模拟网络请求，耗时 1s，打印请求线程
         */
        Logger.d("apiFetchOne current thread: ${Thread.currentThread().name}")
        delay(5000)
        return apiService.fetchData()
    }

    private suspend fun apiFetchTwo(): BaseResponse<String> {
        Logger.d("apiFetchTwo current thread: ${Thread.currentThread().name}")
        delay(3000)
        return apiService.fetchData()
    }

}
