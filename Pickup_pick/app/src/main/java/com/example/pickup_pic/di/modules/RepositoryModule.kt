package com.example.pickup_pic.di.modules

import com.example.pickup_pic.data.repository.PhotosRepositoryImpl
import com.example.pickup_pic.data.repository.ProfileRepositoryImpl
import com.example.pickup_pic.data.repository.UserAuthServiceImpl
import com.example.pickup_pic.domain.repository.PhotosRepository
import com.example.pickup_pic.domain.repository.ProfileRepository
import com.example.pickup_pic.domain.repository.UserAuthService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    fun provideAuthRepository(impl: UserAuthServiceImpl): UserAuthService = impl

    @Provides
    fun provideFeedPhotoRepository(impl: PhotosRepositoryImpl): PhotosRepository = impl

    @Provides
    fun provideProfileRepository(impl: ProfileRepositoryImpl): ProfileRepository = impl
}