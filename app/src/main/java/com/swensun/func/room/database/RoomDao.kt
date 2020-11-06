package com.swensun.func.room.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
abstract class RoomDao : BaseDao<RoomEntity>() {
    @Query("select * from RoomEntity")
    abstract fun queryRooms(): LiveData<List<RoomEntity>>

    @Query("select * from RoomEntity")
    abstract fun queryRooms2(): List<RoomEntity>

    @Query("select * from RoomEntity where title=:title")
    abstract fun queryRoomsByTitle(title: String): RoomEntity?


    override fun upsert(obj: RoomEntity) {
        val roomEntity = queryRoomsByTitle(obj.title)
        if (roomEntity == null) {
            insert(obj)
        } else {
            update(obj)
        }
    }

    @Transaction
    open fun upsertList(entities: List<RoomEntity>) {
        entities.forEach { upsert(it) }
    }
}
