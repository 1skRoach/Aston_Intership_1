package com.example.pickup_pic.domain.use_cases

import com.example.pickup_pic.domain.models.profile.Profile

interface GetProfileDataUseCase {
    var profileData: Profile?
    suspend fun invoke(): Profile
}