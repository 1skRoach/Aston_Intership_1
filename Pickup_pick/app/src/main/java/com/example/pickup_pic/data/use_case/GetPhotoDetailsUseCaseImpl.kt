package com.example.pickup_pic.data.use_case

import com.example.pickup_pic.domain.models.photo_details.PhotoDetails
import com.example.pickup_pic.domain.repository.PhotosRepository
import com.example.pickup_pic.domain.use_cases.GetPhotoDetailsUseCase
import javax.inject.Inject

class GetPhotoDetailsUseCaseImpl @Inject constructor(
    private val photosRepository: PhotosRepository
) : GetPhotoDetailsUseCase {
    override suspend operator fun invoke(id: String): PhotoDetails =
        photosRepository.getFeedPhotoDetailsById(id)
}