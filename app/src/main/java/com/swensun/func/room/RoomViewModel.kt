package com.swensun.func.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.swensun.func.room.database.RoomDataBaseUtils
import com.swensun.func.room.database.RoomEntity
import com.swensun.launchIO
import com.swensun.swutils.util.Logger

class RoomViewModel : ViewModel() {

    val roomDao by lazy { RoomDataBaseUtils.INSTANCE.roomDao() }

    fun queryRoom(): LiveData<List<RoomEntity>> {
        return roomDao.queryRooms()
    }

    fun addEntity() {
        launchIO {
            roomDao.upsert(RoomEntity().apply { id = 100 })
        }
    }

    fun clearAllData() {
        DataCleanManager.cleanDatabases()
    }

    fun migration() {

    }
}
