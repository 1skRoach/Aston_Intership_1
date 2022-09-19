package com.example.pickup_pic.data.use_case

import com.example.pickup_pic.domain.models.profile.Profile
import com.example.pickup_pic.domain.repository.ProfileRepository
import com.example.pickup_pic.domain.use_cases.GetProfileDataUseCase
import javax.inject.Inject

class GetProfileDataUseCaseImpl @Inject constructor(
    private val repository: ProfileRepository
) : GetProfileDataUseCase {

    override var profileData: Profile? = null

    override suspend fun invoke(): Profile {
        profileData = repository.getCurrentProfileData()
        return profileData ?: error("Error retrieving profile data")
    }
}