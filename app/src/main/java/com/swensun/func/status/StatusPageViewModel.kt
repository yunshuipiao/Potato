package com.swensun.func.status

import com.swensun.StatusEvent
import com.swensun.StatusViewModel
import com.swensun.launch
import kotlinx.coroutines.delay

/**
 * author : zp
 * date : 2020/11/5
 * Potato
 */

class StatusPageViewModel : StatusViewModel() {
    

    fun loadDataWithStatus(event: StatusEvent) {
        launch {
            statusLiveData.postValue(StatusEvent.LOADING)
            delay(2000)
            statusLiveData.postValue(event)
        }
    }
}