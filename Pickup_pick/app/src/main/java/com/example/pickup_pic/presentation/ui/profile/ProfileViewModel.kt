package com.example.pickup_pic.presentation.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pickup_pic.domain.use_cases.GetProfileDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileDataUseCase: GetProfileDataUseCase
) : ViewModel() {

    private val scope = viewModelScope

    private val _dataState = MutableLiveData<ProfileState>()
    val dataState: LiveData<ProfileState>
        get() = _dataState

    fun getProfileData() {
        when (dataState.value) {
            is ProfileState.Error, ProfileState.Cancellation, null -> {
                _dataState.postValue(ProfileState.Loading(true))
                // Retrofit launches coroutine on it`s background thread pool
                scope.launch(CoroutineExceptionHandler { _, t ->
                    Timber.d("$t")
                    _dataState.value = ProfileState.Loading(false)
                    _dataState.value = ProfileState.Error(t)
                }) {
                    val profile = getProfileDataUseCase.invoke()
                    Timber.d("Profile data fetched from API = $profile")
                    _dataState.value = ProfileState.Loading(false)
                    _dataState.value = ProfileState.Success(profile)
                }
            }
            else -> {
                try {
                    val profile =
                        getProfileDataUseCase.profileData ?: error("Error retrieving profile data")
                    Timber.d("Profile data fetched from memory = $profile")
                    _dataState.value = ProfileState.Success(profile)
                } catch (t: Throwable) {
                    Timber.d("$t")
                    _dataState.postValue(ProfileState.Error(t))
                }
            }
        }
    }
    fun cancelScopeChildrenJobs() {
        if (!viewModelScope.coroutineContext.job.children.none()) {
            viewModelScope.coroutineContext.cancelChildren()
            _dataState.value = ProfileState.Cancellation
            Timber.i("profile data fetching canceled")
        }
    }
}
