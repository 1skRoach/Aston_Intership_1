package com.example.pickup_pic.data.use_case

import androidx.paging.PagingData
import com.example.pickup_pic.domain.models.photo.Photo
import com.example.pickup_pic.domain.repository.PhotosRepository
import com.example.pickup_pic.domain.use_cases.GetFeedPhotosUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFeedPhotosUseCaseImpl @Inject constructor(
    private val photosRepository: PhotosRepository
) : GetFeedPhotosUseCase {
    override suspend fun invoke(
        order: String,
        currentOrder: String
    ): Flow<PagingData<Photo>> =
        photosRepository.getPhotos(order, currentOrder)
}
