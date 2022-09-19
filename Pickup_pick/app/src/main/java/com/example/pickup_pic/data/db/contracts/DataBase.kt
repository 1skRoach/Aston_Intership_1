package com.example.pickup_pic.data

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pickup_pic.data.db.contracts.dao.*
import com.example.pickup_pic.data.db.contracts.entities.*

@Database(
    entities = [
        PhotoEntity::class,
        UserEntity::class,
        UserProfileImageEntity::class,
        UserLinkEntity::class,
        UrlEntity::class,
        LinkEntity::class,
        RemoteKey::class
    ],
    version = 1
)
abstract class Db : RoomDatabase() {
//абстрактные функции, room сам все инициализирует
    abstract fun photosDao(): PhotoDao
    abstract fun userDao(): UserDao
    abstract fun userProfileImageDao(): UserProfileImageDao
    abstract fun userLinkDao(): UserLinkDao
    abstract fun urlDao(): UrlDao
    abstract fun linkDao(): LinkDao
    abstract fun remoteKeysDao(): RemoteKeysDao

    companion object {

        private var INSTANCE: Db? = null
        private val LOCK = Any()
        private const val DB_NAME = "db"

        fun getInstance(application: Application): Db {
            INSTANCE?.let { db ->
                return db
            }
            //для синхронизирования процессов считывания и записи в бд чтобы все делалось асинхронно
            synchronized(LOCK) {
                INSTANCE?.let { db ->
                    return db
                }
                val db = Room.databaseBuilder(
                    application,
                    Db::class.java,
                    DB_NAME
                ).build()
                INSTANCE = db
                return INSTANCE!!
            }
        }
    }
}