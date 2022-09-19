package com.example.pickup_pic.data.db.contracts.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pickup_pic.data.db.contracts.UserLinkContract
import com.example.pickup_pic.data.db.contracts.entities.UserLinkEntity

@Dao
interface UserLinkDao {

    @Insert(
        entity = UserLinkEntity::class,
        onConflict = OnConflictStrategy.REPLACE
    )
    suspend fun insertUserLink(link: UserLinkEntity)

    @Query("SELECT * FROM ${UserLinkContract.TABLE_NAME} WHERE ${UserLinkContract.Columns.ID} = :id")
    suspend fun getUserLinkWithId(id: String): UserLinkEntity?
}