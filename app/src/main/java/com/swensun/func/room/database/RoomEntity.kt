package com.swensun.func.room.database

import androidx.room.*

@Entity()
class RoomEntity {
    @PrimaryKey(autoGenerate = true)
    var id = 0

    var title: String = ""

    var size: Int = 0
}

@Entity(tableName = "room_master_table")
class RoomMaster {
    @PrimaryKey
    var id = 0

    @ColumnInfo(name = "identity_hash")
    var identityHash: String? = null
}

