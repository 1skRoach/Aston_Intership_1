package com.example.pickup_pic.domain.use_cases

import androidx.paging.PagingData
import com.example.pickup_pic.domain.models.photo.Photo
import kotlinx.coroutines.flow.Flow

interface SearchPhotoUseCase {
    suspend operator fun invoke(query: String): Flow<PagingData<Photo>>
}