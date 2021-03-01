package com.swensun.func.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.room.Transaction
import com.swensun.func.room.database.RDataBase
import com.swensun.func.room.database.RoomEntity
import com.swensun.launchIO
import com.swensun.swutils.util.Logger
import kotlinx.coroutines.delay
import java.util.logging.LogManager

class RoomViewModel : ViewModel() {

    val roomDao by lazy { RDataBase.INSTANCE.roomDao() }
    val allRoomLiveData = MediatorLiveData<List<RoomEntity>>()
    var allRoom = listOf<RoomEntity>()
                 
    init {
        allRoomLiveData.addSource(roomDao.queryRooms()) {
            allRoom = it
            allRoomLiveData.postValue(it)
        }
    }

    fun upsertList(entities: List<RoomEntity>) {
        launchIO {
            roomDao.upsertList(entities)
        }
    }

    fun upsertList(entity: RoomEntity) {
        launchIO {
            roomDao.upsert(entity)
        }
    }

    fun delete(it: RoomEntity) {
        launchIO {
            roomDao.delete(it)
        }
    }
}
