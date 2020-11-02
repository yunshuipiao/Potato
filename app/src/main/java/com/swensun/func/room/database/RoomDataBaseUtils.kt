package com.swensun.func.room.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.swensun.swutils.SwUtils
import com.swensun.swutils.util.Logger

@Database(entities = [RoomEntity::class], version = 1)
abstract class RDataBase : RoomDatabase() {
    abstract fun roomDao(): RoomDao

    companion object {

        const val RoomEntity = "RoomEntity"

        val INSTANCE by lazy {
            Room.databaseBuilder(SwUtils.application, RDataBase::class.java, "room.db")
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        Logger.d("room onCreate, ${db.version},  ${db.path}")
                    }

                    override fun onOpen(db: SupportSQLiteDatabase) {
                        super.onOpen(db)
                        Logger.d("room onOpen, ${db.version}, ${db.path}")
                    }
                })
                .build()
        }
    }
}