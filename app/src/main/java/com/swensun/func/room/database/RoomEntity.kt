package com.swensun.func.room.database

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
class RoomEntity {
    @PrimaryKey
    var id = 0

    var title: String = ""

    var size: Int = 0
}