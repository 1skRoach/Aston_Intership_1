package com.example.pickup_pic.domain.repository

import com.example.pickup_pic.data.db.contracts.entities.UserLinkEntity

interface FeedUserLinkRepository {

    suspend fun insertFeedUserLink(link: UserLinkEntity)
}