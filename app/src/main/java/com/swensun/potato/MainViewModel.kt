package com.swensun.potato

import android.os.AsyncTask
import androidx.lifecycle.ViewModel
import com.swensun.func.room.database.RDataBase
import com.swensun.func.room.database.RoomEntity
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

    fun opeDatabase() {
        launchIO {
            RDataBase.INSTANCE.roomDao().upsert(RoomEntity())
        }
    }
}