package com.example.pickup_pic.domain.use_cases

import com.example.pickup_pic.domain.models.photo_details.PhotoDetails

interface GetPhotoDetailsUseCase {
    suspend operator fun invoke(id: String): PhotoDetails

}