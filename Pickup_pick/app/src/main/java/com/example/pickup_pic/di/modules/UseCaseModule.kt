package com.example.pickup_pic.di.modules

import com.example.pickup_pic.data.use_case.AuthenticateUserUseCaseImpl
import com.example.pickup_pic.domain.use_cases.AuthenticateUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    fun provideUserAuthUseCase(impl: AuthenticateUserUseCaseImpl): AuthenticateUserUseCase = impl



}