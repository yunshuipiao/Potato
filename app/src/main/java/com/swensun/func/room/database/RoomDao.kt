package com.swensun.func.room.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
abstract class RoomDao : BaseDao<RoomEntity>() {
    @Query("select * from RoomEntity")
    abstract fun queryRooms(): LiveData<List<RoomEntity>>

    @Query("select * from RoomEntity")
    abstract fun queryRooms2(): List<RoomEntity>
}