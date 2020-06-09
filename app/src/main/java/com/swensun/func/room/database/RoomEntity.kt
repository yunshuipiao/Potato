package com.swensun.func.room.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class RoomEntity {
    @PrimaryKey
    var id = 0
    
    var title: String = ""
}