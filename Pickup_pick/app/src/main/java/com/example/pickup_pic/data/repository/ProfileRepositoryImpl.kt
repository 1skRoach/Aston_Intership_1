package com.example.pickup_pic.data.repository

import com.example.pickup_pic.data.api.UnsplashApi
import com.example.pickup_pic.domain.models.profile.Profile
import com.example.pickup_pic.domain.repository.ProfileRepository
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val api: UnsplashApi
) : ProfileRepository {
    override suspend fun getCurrentProfileData(): Profile = api.getCurrentProfile()
}