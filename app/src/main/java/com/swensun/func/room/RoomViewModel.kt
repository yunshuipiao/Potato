package com.swensun.func.room

import androidx.lifecycle.ViewModel
import com.swensun.func.room.database.RoomDataBaseUtils
import com.swensun.func.room.database.RoomEntity
import com.swensun.launchIO
import com.swensun.swutils.util.Logger

class RoomViewModel : ViewModel() {

    val roomDao by lazy { RoomDataBaseUtils.INSTANCE.roomDao() }

    fun queryRoom() {
        launchIO {
            val res = roomDao.queryRooms2()
            Logger.d("room res: ${res.map { it.id }}")
        }
    }

    fun addEntity() {
        launchIO {
            roomDao.insertRoom(arrayListOf(RoomEntity()))
        }
    }

    fun clearAllData() {
        DataCleanManager.cleanDatabases()
    }

    fun migration() {

    }
}
