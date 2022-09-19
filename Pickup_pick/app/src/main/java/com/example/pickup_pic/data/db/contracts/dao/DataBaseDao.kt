package com.example.pickup_pic.data.db.contracts.dao

import androidx.room.Database
import androidx.room.RoomDatabase
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
    version = DatabaseDao.DB_VERSION
)

abstract class DatabaseDao : RoomDatabase() {

    abstract fun photosDao(): PhotoDao
    abstract fun userDao(): UserDao
    abstract fun userProfileImageDao(): UserProfileImageDao
    abstract fun userLinkDao(): UserLinkDao
    abstract fun urlDao(): UrlDao
    abstract fun linkDao(): LinkDao
    abstract fun remoteKeysDao(): RemoteKeysDao

    companion object {
        const val DB_VERSION = 1
    }
}