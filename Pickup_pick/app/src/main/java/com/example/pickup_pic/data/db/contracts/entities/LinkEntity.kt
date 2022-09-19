package com.example.pickup_pic.data.db.contracts.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pickup_pic.data.db.contracts.LinkContract

@Entity(tableName = LinkContract.TABLE_NAME)
data class LinkEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = LinkContract.Columns.SELF) val self: String,
    @ColumnInfo(name = LinkContract.Columns.HTML) val html: String,
    @ColumnInfo(name = LinkContract.Columns.DOWNLOAD) val download: String,
    @ColumnInfo(name = LinkContract.Columns.DOWNLOAD_LOCATION) val downloadLocation: String
)