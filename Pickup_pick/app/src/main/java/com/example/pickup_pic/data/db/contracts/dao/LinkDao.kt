package com.example.pickup_pic.data.db.contracts.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pickup_pic.data.db.contracts.LinkContract
import com.example.pickup_pic.data.db.contracts.entities.LinkEntity

@Dao
interface LinkDao {
    @Insert(
        entity = LinkEntity::class,
        onConflict = OnConflictStrategy.REPLACE
    )
    suspend fun insertFeedLink(link: LinkEntity)

    @Query("SELECT * FROM ${LinkContract.TABLE_NAME} WHERE ${LinkContract.Columns.ID} = :id")
    suspend fun getLinkWithId(id: String): LinkEntity?
}