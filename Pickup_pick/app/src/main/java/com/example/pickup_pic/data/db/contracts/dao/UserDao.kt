package com.example.pickup_pic.data.db.contracts.dao

import androidx.room.*
import com.example.pickup_pic.data.db.contracts.UserContract
import com.example.pickup_pic.data.db.contracts.entities.UserEntity
import com.example.pickup_pic.data.relations.UserAndPhotoEntity

@Dao
interface UserDao {

    @Insert(
        entity = UserEntity::class,
        onConflict = OnConflictStrategy.REPLACE
    )
    suspend fun insertUser(user: UserEntity)

    @Transaction
    @Query("SELECT * FROM ${UserContract.TABLE_NAME} WHERE ${UserContract.Columns.ID} = :id")
    suspend fun getUserAndPhotoWithUserId(id: String): UserAndPhotoEntity?
}