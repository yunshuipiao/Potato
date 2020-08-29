package com.swensun.func.room.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.swensun.swutils.SwUtils

object RoomDataBaseUtils {
    private val MIGRATIONS_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL(
                "ALTER TABLE RoomEntity ADD COLUMN size INTEGER DEFAULT 0 NOT NULL"
            )
        }
    }
    val INSTANCE by lazy {
        Room.databaseBuilder(SwUtils.application, RDataBase::class.java, "room.db")
            .addMigrations(MIGRATIONS_1_2)
            .allowMainThreadQueries()
            .build()
    }
}

@Database(entities = [RoomEntity::class], version = 2, exportSchema = false)
abstract class RDataBase : RoomDatabase() {
    abstract fun roomDao(): RoomDao
}