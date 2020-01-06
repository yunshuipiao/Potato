package com.swensun.func.room.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class RoomEntity {
    @PrimaryKey(autoGenerate = true)
    var id = 0
    
    val title: String
        get() {
            return "title-$id"
        }
}