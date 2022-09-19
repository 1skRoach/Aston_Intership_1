package com.example.pickup_pic.data.repository

import com.example.pickup_pic.data.db.contracts.dao.UserProfileImageDao
import com.example.pickup_pic.data.db.contracts.entities.UserProfileImageEntity
import com.example.pickup_pic.domain.repository.FeedUserProfileImageRepository
import javax.inject.Inject

class FeedUserProfileImageRepositoryImpl  @Inject constructor(
    private val dao: UserProfileImageDao
) : FeedUserProfileImageRepository {
    override suspend fun insertFeedUserProfileImage(image: UserProfileImageEntity) =
        dao.insertUserProfileImage(image)
}