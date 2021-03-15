package com.swensun.func.room.database

import androidx.lifecycle.LiveData
import androidx.room.*

const val ROOM_ENTITY = "room_entity_2"

@Entity(tableName = ROOM_ENTITY)
class RoomEntity {
    @PrimaryKey(autoGenerate = true)
    var id = 0

    var title: String = ""

    var count: Int = 0

    var startTime: Int = 0

}

@Dao
abstract class RoomDao : BaseDao<RoomEntity>() {
    @Query("select * from $ROOM_ENTITY")
    abstract fun queryRooms(): LiveData<List<RoomEntity>>

    @Query("select * from $ROOM_ENTITY")
    abstract fun queryRooms2(): List<RoomEntity>

    @Query("select * from $ROOM_ENTITY where title=:title")
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
