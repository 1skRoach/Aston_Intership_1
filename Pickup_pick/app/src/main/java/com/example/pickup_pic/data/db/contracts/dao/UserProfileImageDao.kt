package com.example.pickup_pic.data.db.contracts.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pickup_pic.data.db.contracts.UserProfileImageContract
import com.example.pickup_pic.data.db.contracts.entities.UserProfileImageEntity

@Dao
interface UserProfileImageDao {

    @Insert(
        entity = UserProfileImageEntity::class,
        onConflict = OnConflictStrategy.REPLACE
    )
    suspend fun insertUserProfileImage(image: UserProfileImageEntity)

    @Query("SELECT * FROM ${UserProfileImageContract.TABLE_NAME} WHERE ${UserProfileImageContract.Columns.ID} = :id")
    suspend fun getUserProfileImageWithId(id: String): UserProfileImageEntity?
}