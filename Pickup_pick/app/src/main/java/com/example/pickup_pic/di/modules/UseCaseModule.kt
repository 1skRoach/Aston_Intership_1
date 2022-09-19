package com.example.pickup_pic.di.modules

import com.example.pickup_pic.data.use_case.*
import com.example.pickup_pic.domain.use_cases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    fun provideUserAuthUseCase(impl: AuthenticateUserUseCaseImpl): AuthenticateUserUseCase = impl

    @Provides
    fun provideGetFeedPhotosUseCase(impl: GetFeedPhotosUseCaseImpl): GetFeedPhotosUseCase = impl

    @Provides
    fun provideSearchFeedPhotosUseCase(impl: SearchPhotoUseCaseImpl): SearchPhotoUseCase = impl

    @Provides
    fun provideGetFeedPhotoDetailsUseCase(impl: GetPhotoDetailsUseCaseImpl): GetPhotoDetailsUseCase = impl

    @Provides
    fun provideGetProfileDataUseCase(impl: GetProfileDataUseCaseImpl): GetProfileDataUseCase = impl

}