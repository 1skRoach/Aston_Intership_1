package com.example.pickup_pic.data.db.contracts.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pickup_pic.data.db.contracts.RemoteKeyContract
import com.example.pickup_pic.data.db.contracts.entities.RemoteKey

@Dao
interface RemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllKeys(remoteKeys: List<RemoteKey>)

    @Query("SELECT * FROM ${RemoteKeyContract.TABLE_NAME} WHERE ${RemoteKeyContract.Columns.PHOTO_ID} = :id")
    suspend fun getRemoteKeyByPhotoId(id: String): RemoteKey?

    @Query("DELETE FROM ${RemoteKeyContract.TABLE_NAME}")
    suspend fun deleteAllRemoteKeys()
}