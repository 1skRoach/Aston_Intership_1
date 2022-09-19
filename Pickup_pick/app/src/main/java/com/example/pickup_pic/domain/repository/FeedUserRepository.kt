package com.example.pickup_pic.domain.repository

import com.example.pickup_pic.data.db.contracts.entities.UserEntity

interface FeedUserRepository {
    suspend fun insertFeedUser(feedUser: UserEntity)

}