package com.example.pickup_pic.domain.repository

import com.example.pickup_pic.data.db.contracts.entities.UrlEntity

interface FeedUrlRepository {

    suspend fun insertFeedUrl(url: UrlEntity)
}