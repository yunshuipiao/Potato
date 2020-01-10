package com.swensun.func.room.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.swensun.swutils.SwUtils

object RoomDataBaseUtils {
    private val INSTANCE by lazy { Room.databaseBuilder(SwUtils.application, RDataBase::class.java, "room.db")
        .fallbackToDestructiveMigration()
        .allowMainThreadQueries()
        .build()}

    fun getRoomDao(): RoomDao {
        return INSTANCE.roomDao()
    }
}

@Database(entities = [RoomEntity::class], version = 1)
abstract class RDataBase: RoomDatabase() {
    abstract fun roomDao(): RoomDao
}