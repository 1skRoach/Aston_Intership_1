package com.example.pickup_pic.data.db.contracts.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pickup_pic.data.db.contracts.UrlContract
import com.example.pickup_pic.data.db.contracts.entities.UrlEntity

@Dao
interface UrlDao {

    @Insert(
        entity = UrlEntity::class,
        onConflict = OnConflictStrategy.REPLACE
    )
    suspend fun insertFeedUrl(url: UrlEntity)

    @Query("SELECT * FROM ${UrlContract.TABLE_NAME} WHERE ${UrlContract.Columns.ID} = :id")
    suspend fun getUrlWithId(id: String): UrlEntity?
}