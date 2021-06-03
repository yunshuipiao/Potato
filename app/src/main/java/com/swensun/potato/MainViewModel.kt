package com.swensun.potato

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.swensun.func.room.database.RDataBase
import com.swensun.func.room.database.RoomEntity
import com.swensun.launch
import com.swensun.swutils.util.Logger

class MainViewModel : ViewModel() {

    val intLiveData = MutableLiveData<Int>()

    fun testCoroutines() {
        (0 until 1000).forEach {
            launch {
                formatJson()
                Logger.d("thread:${Thread.currentThread().name}, $it")
            }
        }

    }

    fun formatJson() {
    }

    fun opeDatabase() {
        launch {
            RDataBase.INSTANCE.roomDao().upsert(RoomEntity())
        }
    }
}