package com.example.pickup_pic.presentation.ui.home

import androidx.paging.PagingData
import com.example.pickup_pic.domain.models.photo.Photo

sealed class HomeState {
    class Success(val data: PagingData<Photo>) : HomeState()
    class Error(val error: Throwable) : HomeState()
}