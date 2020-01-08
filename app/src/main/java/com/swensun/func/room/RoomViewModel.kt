package com.swensun.func.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.swensun.func.room.database.RoomDataBaseUtils
import com.swensun.func.room.database.RoomEntity
import com.swensun.swutils.util.Logger

class RoomViewModel : ViewModel() {

    val roomDao by lazy { RoomDataBaseUtils.getRoomDao() }

    val roomQueryLiveData
        get() = queryRooms()

    fun queryRooms(): LiveData<List<RoomEntity>> {
        Logger.d("observer query")
        return roomDao.queryRooms()
    }

    fun add(id: Int) {
        roomDao.insertRoom(listOf(RoomEntity().apply {
            this.id = id
        }))
    }

    fun update(id: Int) {
        roomDao.updateRoom(RoomEntity().apply {
            this.id = id
        })
    }

    fun delete(id: Int) {
        roomDao.deleteRoom(RoomEntity().apply {
            this.id = id
        })
    }

}
