package com.example.pickup_pic.data.repository

import com.example.pickup_pic.data.db.contracts.dao.UserLinkDao
import com.example.pickup_pic.data.db.contracts.entities.UserLinkEntity
import com.example.pickup_pic.domain.repository.FeedUserLinkRepository
import javax.inject.Inject

class FeedUserLinkRepositoryImpl @Inject constructor(
    private val dao: UserLinkDao
) : FeedUserLinkRepository {
    override suspend fun insertFeedUserLink(link: UserLinkEntity) =
        dao.insertUserLink(link)
}