package com.example.pickup_pic.di.modules

import com.example.pickup_pic.di.qualifiers.DispatcherIoQualifier
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
class DispatcherModule {

    @Provides
    @DispatcherIoQualifier
    fun provideDispatcherIo(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    fun provideDispatcher(
        @DispatcherIoQualifier dispatcherIo: CoroutineDispatcher
    ): CoroutineDispatcher = dispatcherIo
}