package com.swensun.func.room.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.swensun.swutils.SwUtils

object RoomDataBaseUtils {
    val INSTANCE by lazy {
        Room.databaseBuilder(SwUtils.application, RDataBase::class.java, "room.db")
            .allowMainThreadQueries()
            .build()
    }
}

@Database(entities = [RoomEntity::class], version = 1, exportSchema = false)
abstract class RDataBase : RoomDatabase() {
    abstract fun roomDao(): RoomDao
}