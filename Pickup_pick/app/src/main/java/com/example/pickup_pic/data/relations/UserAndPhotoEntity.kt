package com.example.pickup_pic.data.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.pickup_pic.data.db.contracts.PhotoContract
import com.example.pickup_pic.data.db.contracts.UserContract
import com.example.pickup_pic.data.db.contracts.entities.PhotoEntity
import com.example.pickup_pic.data.db.contracts.entities.UserEntity

//связывание нескольких сущностей с помощью  relations подслоя
data class UserAndPhotoEntity(
    @Embedded
    val user: UserEntity,
    @Relation(
        parentColumn = UserContract.Columns.ID,
        entityColumn = PhotoContract.Columns.USER_ID
    )
    val photo: PhotoEntity?
)