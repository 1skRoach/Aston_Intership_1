package com.example.pickup_pic.presentation.ui.home

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.pickup_pic.data.network.status_tracker.NetworkStatusTracker
import com.example.pickup_pic.domain.use_cases.GetFeedPhotosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    //   private val context: Application,
    networkStatusTracker: NetworkStatusTracker,
    private val getFeedPhotosUseCase: GetFeedPhotosUseCase,
) : ViewModel() {

    private val _feedState = MutableStateFlow<HomeState>(HomeState.Success(PagingData.empty()))
    val feedState = _feedState.asStateFlow()

    var prevOrderBy = DEFAULT_ORDER_BY
        private set

    val networkStatus = networkStatusTracker.networkStatus

    suspend fun getFeedPhotos(order: String) {
            getFeedPhotosUseCase(order, prevOrderBy).cachedIn(viewModelScope)
                .onEach { pagingData ->

                    _feedState.value = HomeState.Success(pagingData)
                }
                .catch { t ->
                    Timber.d(t)
                    _feedState.value = HomeState.Error(t)
                }
                .launchIn(viewModelScope)
            prevOrderBy = order
    }

    companion object {
        private const val DEFAULT_ORDER_BY = "latest"
    }
}