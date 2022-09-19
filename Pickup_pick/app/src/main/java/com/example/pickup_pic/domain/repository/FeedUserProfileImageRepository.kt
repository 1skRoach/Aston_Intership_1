package com.example.pickup_pic.domain.repository

import com.example.pickup_pic.data.db.contracts.entities.UserProfileImageEntity

interface FeedUserProfileImageRepository {
    suspend fun insertFeedUserProfileImage(image: UserProfileImageEntity)
}