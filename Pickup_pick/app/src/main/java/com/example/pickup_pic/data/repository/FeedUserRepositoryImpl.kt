package com.example.pickup_pic.data.repository

import com.example.pickup_pic.data.db.contracts.dao.UserDao
import com.example.pickup_pic.data.db.contracts.entities.UserEntity
import com.example.pickup_pic.domain.repository.FeedUserRepository
import javax.inject.Inject

class FeedUserRepositoryImpl @Inject constructor(
    private val dao: UserDao
) : FeedUserRepository {
    override suspend fun insertFeedUser(feedUser: UserEntity) = dao.insertUser(feedUser)
}
