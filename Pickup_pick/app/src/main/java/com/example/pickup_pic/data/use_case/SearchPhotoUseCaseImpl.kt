package com.example.pickup_pic.data.use_case

import androidx.paging.PagingData
import com.example.pickup_pic.domain.models.photo.Photo
import com.example.pickup_pic.domain.repository.PhotosRepository
import com.example.pickup_pic.domain.use_cases.SearchPhotoUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchPhotoUseCaseImpl @Inject constructor(
    private val photosRepository: PhotosRepository
) : SearchPhotoUseCase {
    override suspend operator fun invoke(query: String): Flow<PagingData<Photo>> {
        return photosRepository.getSearchResult(query)
    }
}