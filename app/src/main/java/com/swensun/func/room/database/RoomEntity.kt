package com.swensun.func.room.database

import androidx.room.*

@Entity
class RoomEntity {
    @PrimaryKey(autoGenerate = true)
    var id = 0

    var title: String = ""

    var count: Int = 0

    var startTime: Int = 0
}

