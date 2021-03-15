package com.swensun.func.room.database

import android.os.AsyncTask
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.swensun.swutils.SwUtils
import com.swensun.swutils.util.Logger

@Database(entities = [RoomEntity::class], version = 2)
abstract class RDataBase : RoomDatabase() {
    abstract fun roomDao(): RoomDao

    companion object {
        val M_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                roomlog("room M_1_2, ${Thread.currentThread().name}")
                roomlog("room M_1_2:${db.version}")
                db.execSQL("CREATE TABLE IF NOT EXISTS `${ROOM_ENTITY}` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `title` TEXT NOT NULL, `count` INTEGER NOT NULL, `startTime` INTEGER NOT NULL)")
            }
        }

        val M_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                Thread.sleep(1000)
                roomlog("room M_2_3, ${Thread.currentThread().name}")
                roomlog("room M_2_3:${db.version}")
            }
        }

        val M_3_4 = object : Migration(3, 4) {
            override fun migrate(db: SupportSQLiteDatabase) {
                Thread.sleep(1500)
                roomlog("room M_3_4, ${Thread.currentThread().name}")
                roomlog("room M_3_4:${db.version}")
            }
        }

        fun init() {
            INSTANCE.roomDao().queryRooms().observeForever {
                roomlog("room data change:${it.size}")
            }
        }

        const val RoomEntity = "RoomEntity"

        val INSTANCE by lazy {
            Room.databaseBuilder(SwUtils.application, RDataBase::class.java, "room.db")
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        roomlog("room onCreate, ${db.version},  ${db.path}")
                    }

                    override fun onOpen(db: SupportSQLiteDatabase) {
                        super.onOpen(db)
                        roomlog("room onOpen, ${db.version}, ${db.path}")
                    }
                })
                .addMigrations(M_1_2)
                .build()
        }
    }
}

fun roomlog(msg: String) {
    Log.e("room_msg", "$msg")
}