package com.example.pickup_pic.data.db.contracts.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pickup_pic.data.db.contracts.UserProfileImageContract

@Entity(tableName = UserProfileImageContract.TABLE_NAME)
data class UserProfileImageEntity(
    @PrimaryKey
    @ColumnInfo(name = UserProfileImageContract.Columns.ID) val id: String,
    @ColumnInfo(name = UserProfileImageContract.Columns.SMALL) val small: String,
    @ColumnInfo(name = UserProfileImageContract.Columns.MEDIUM) val medium: String,
    @ColumnInfo(name = UserProfileImageContract.Columns.LARGE) val large: String
)