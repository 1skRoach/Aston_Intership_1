package com.example.pickup_pic.presentation.ui.profile

import com.example.pickup_pic.domain.models.profile.Profile

sealed class ProfileState {
    class Loading(val isLoading: Boolean) : ProfileState()
    object Cancellation : ProfileState()
    class Success(val profile: Profile) : ProfileState()
    class Error(val error: Throwable) : ProfileState()
}