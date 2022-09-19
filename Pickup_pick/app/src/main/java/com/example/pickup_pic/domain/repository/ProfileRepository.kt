package com.example.pickup_pic.domain.repository

import com.example.pickup_pic.domain.models.profile.Profile

interface ProfileRepository {
    suspend fun getCurrentProfileData(): Profile
}