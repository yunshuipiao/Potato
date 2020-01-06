package com.swensun.func.room

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.swensun.func.room.database.RoomDataBaseUtils
import com.swensun.func.room.database.RoomEntity

class RoomViewModel : ViewModel() {

    val roomDao by lazy { RoomDataBaseUtils.getRoomDao() }

    val multiRoomLiveData = MediatorLiveData<List<RoomEntity>>()


    init {
        multiRoomLiveData.addSource(roomDao.queryRooms()) {
            multiRoomLiveData.postValue(it)
        }
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

    fun queryRooms() {
        val list = arrayListOf<RoomEntity>().apply {
            (0 until 50).forEach { add(RoomEntity().apply {
                this.id = it
            }) }
        }
        multiRoomLiveData.value?.let {
            list.addAll(it)
        }
        multiRoomLiveData.postValue(list)
    }   
}
