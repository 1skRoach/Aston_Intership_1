package com.example.pickup_pic.domain.use_cases

import androidx.paging.PagingData
import com.example.pickup_pic.domain.models.photo.Photo
import kotlinx.coroutines.flow.Flow

interface GetFeedPhotosUseCase {
    suspend operator fun invoke(
        order: String,
        currentOrder: String
    ): Flow<PagingData<Photo>>
}