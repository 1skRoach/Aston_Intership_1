package com.example.pickup_pic.data.repository

import com.example.pickup_pic.data.db.contracts.dao.UrlDao
import com.example.pickup_pic.data.db.contracts.entities.UrlEntity
import com.example.pickup_pic.domain.repository.FeedUrlRepository
import javax.inject.Inject

class FeedUrlRepositoryImpl  @Inject constructor(
    private val dao: UrlDao
) : FeedUrlRepository {
    override suspend fun insertFeedUrl(url: UrlEntity)  = dao.insertFeedUrl(url)


}