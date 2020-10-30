package com.swensun.func.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.swensun.func.room.database.RDataBase
import com.swensun.func.room.database.RoomEntity
import com.swensun.launchIO

class RoomViewModel : ViewModel() {

    val roomDao by lazy { RDataBase.INSTANCE.roomDao() }

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
