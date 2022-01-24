package com.swensun.potato

import android.os.AsyncTask
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

    fun fetchdata(i: Int, function: ((Int) -> Unit)? = null): Int {
        var result = i
        AsyncTask.SERIAL_EXECUTOR.execute {
            Thread.sleep(2000)
            result += result + 10

        }
        return result
    }


}