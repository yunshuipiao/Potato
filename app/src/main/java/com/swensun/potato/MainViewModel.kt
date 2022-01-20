package com.swensun.potato

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.swensun.func.room.database.RDataBase
import com.swensun.func.room.database.RoomEntity
import com.swensun.launchIO
import com.swensun.swutils.util.Logger

class MainViewModel : ViewModel() {

    val intLiveData = MutableLiveData<Int>()
    val stringLiveData = MutableLiveData<String>()

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

    fun openDatabase() {
        launchIO {
            RDataBase.INSTANCE.roomDao().upsert(RoomEntity())
        }
    }

    fun fetchdata(i: Int, function: (Int) -> Unit) {
        if (i == 1) {
            return
        }
        function.invoke(1000)
    }

}