package com.example.pickup_pic.data.db.contracts.mappers

import com.example.pickup_pic.data.db.contracts.entities.UserProfileImageEntity
import com.example.pickup_pic.domain.models.user.UserProfileImage

class UserProfileImageEntityToUserProfileImageMapper:
    Mapper<UserProfileImageEntity, UserProfileImage> {
    override fun map(from: UserProfileImageEntity): UserProfileImage =
        UserProfileImage(
            small = from.small,
            medium = from.medium,
            large = from.large
        )
}