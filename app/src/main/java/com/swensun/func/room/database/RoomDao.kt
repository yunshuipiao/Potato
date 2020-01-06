package com.swensun.func.room.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface RoomDao {
    @Query("select * from RoomEntity")
    fun queryRooms(): LiveData<List<RoomEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRoom(rooms: List<RoomEntity>)

    @Update
    fun updateRoom(roomEntity: RoomEntity)

    @Delete
    fun deleteRoom(roomEntity: RoomEntity)
}