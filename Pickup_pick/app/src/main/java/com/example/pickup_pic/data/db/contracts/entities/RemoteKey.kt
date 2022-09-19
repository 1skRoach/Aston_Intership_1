package com.example.pickup_pic.data.db.contracts.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pickup_pic.data.db.contracts.RemoteKeyContract

@Entity(tableName = RemoteKeyContract.TABLE_NAME)
data class RemoteKey(
    @PrimaryKey
    @ColumnInfo(name = RemoteKeyContract.Columns.PHOTO_ID)
    val feedPhotoId: String,
    @ColumnInfo(name = RemoteKeyContract.Columns.PREV_KEY)
    val prevKey: Int?,
    @ColumnInfo(name = RemoteKeyContract.Columns.NEXT_KEY)
    val nextKey: Int?
)