package com.example.pickup_pic.data.db.contracts.mappers

import com.example.pickup_pic.data.db.contracts.entities.UserEntity
import com.example.pickup_pic.domain.models.user.User


class UserToUserEntityMapper : Mapper<User, UserEntity> {
    override fun map(from: User): UserEntity =
        UserEntity(
            id = from.id,
            username = from.username,
            name = from.name,
            firstName = from.firstName,
            lastName = from.lastName ?: "N/A",
            instagramUsername = from.instagramUsername,
            twitterUsername = from.twitterUsername,
            portfolioUrl = from.portfolioUrl,
            bio = from.bio,
            location = from.location,
            totalLikes = from.totalLikes,
            totalPhotos = from.totalPhotos,
            totalCollections = from.totalCollections,
            userProfileImageId = from.id,
            userLinkId = from.id
        )
}