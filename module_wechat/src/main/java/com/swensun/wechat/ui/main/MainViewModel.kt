package com.swensun.wechat.ui.main

import androidx.lifecycle.ViewModel
import com.swensun.wechat.repository.Repository

class MainViewModel : ViewModel() {

    fun requestUserInfo() {
        Repository.requestUserInfo()
    }
}
