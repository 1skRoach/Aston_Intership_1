package com.example.pickup_pic.presentation.ui.home.details

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pickup_pic.data.network.status_tracker.NetworkStatusTracker
import com.example.pickup_pic.domain.use_cases.GetPhotoDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PhotoDetailViewModel @Inject constructor(
    val context: Application,
    private val getFeedPhotoDetailsUseCase: GetPhotoDetailsUseCase,
    networkStatusTracker: NetworkStatusTracker
): ViewModel() {

    val networkStatus = networkStatusTracker.networkStatus

    private val _feedDetailsState =
        MutableStateFlow<PhotoDetailsState>(PhotoDetailsState.Loading)
    val feedDetailsState = _feedDetailsState.asStateFlow()

    suspend fun getFeedPhotoDetails(id: String) {
        try {
            viewModelScope.launch {
                val details = getFeedPhotoDetailsUseCase(id)
                _feedDetailsState.value = PhotoDetailsState.Success(details)
            }
        } catch (t: Throwable) {
            _feedDetailsState.value = PhotoDetailsState.Error(t)
        }
    }
}