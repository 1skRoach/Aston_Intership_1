package com.example.pickup_pic.di.modules

import android.app.Application
import com.example.pickup_pic.data.Db
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun provideDatabase(context: Application): Db = Db.getInstance(context)
}