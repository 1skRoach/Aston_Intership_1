package com.example.pickup_pic.presentation.ui.home.details

import com.example.pickup_pic.domain.models.photo_details.PhotoDetails

sealed class PhotoDetailsState {
    object Loading : PhotoDetailsState()
    class Success(val data: PhotoDetails) : PhotoDetailsState()
    class Error(val error: Throwable) : PhotoDetailsState()
}