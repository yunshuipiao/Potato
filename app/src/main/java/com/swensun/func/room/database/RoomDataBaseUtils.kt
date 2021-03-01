package com.swensun.func.room.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.swensun.swutils.SwUtils
import com.swensun.swutils.util.Logger

@TypeConverters(VideoDetailInfoItemConverter::class)
@Database(entities = [RoomEntity::class], version = 1)
abstract class RDataBase : RoomDatabase() {
    abstract fun roomDao(): RoomDao

    companion object {
        fun init() {
            INSTANCE.roomDao().queryRooms().observeForever {
                Logger.d("room data change:${it.size}")
            }
        }

        const val RoomEntity = "RoomEntity"

        val INSTANCE by lazy {
            Room.databaseBuilder(SwUtils.application, RDataBase::class.java, "room.db")
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        Logger.d("room onCreate, ${db.version},  ${db.path}")
                    }

                    override fun onOpen(db: SupportSQLiteDatabase) {
                        super.onOpen(db)
                        Logger.d("room onOpen, ${db.version}, ${db.path}")

//                        db.beginTransaction()
//                        try {
//                            db.execSQL("CREATE TABLE IF NOT EXISTS RoomEntity_new (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `title` TEXT NOT NULL, `count` INTEGER NOT NULL, `startTime` INTEGER NOT NULL)")
//                            db.execSQL("INSERT INTO RoomEntity_new (id, title, count, startTime) SELECT id, title, count, startTime  FROM RoomEntity")
//                            db.execSQL("DROP TABLE RoomEntity")
//                            db.execSQL("ALTER TABLE RoomEntity_new RENAME TO RoomEntity")
//                            db.execSQL("ALTER TABLE RoomEntity ADD COLUMN `video_detail_info_item` TEXT NOT NULL DEFAULT '{}'")
//                            db.setTransactionSuccessful()
//                        } finally {
//                            db.endTransaction()
//                        }
                    }
                })
                .build()
        }
    }
}