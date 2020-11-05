package com.swensun.func.status

import com.swensun.StatusEvent
import com.swensun.StatusViewModel

/**
 * author : zp
 * date : 2020/11/5
 * Potato
 */

class StatusPageViewModel : StatusViewModel() {
    fun setStatus(event: StatusEvent) {
        statusLiveData.postValue(event)
    }
}