package com.example.pickup_pic.di.modules

import android.app.Application
import android.content.Context
import com.example.pickup_pic.data.network.authentication.AuthTokenInterceptor
import com.example.pickup_pic.data.network.status_tracker.NetworkStatus
import com.example.pickup_pic.data.network.status_tracker.NetworkStatusTracker
import com.example.pickup_pic.di.qualifiers.AuthTokenInterceptorQualifier
import com.example.pickup_pic.di.qualifiers.LoggingInterceptorQualifier
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.openid.appauth.AuthorizationService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideContext(app: Application): Context = app

    @Provides
    fun provideNetworkStatusTracker(context: Context): NetworkStatusTracker =
        NetworkStatusTracker(context)

    @Provides
    @AuthTokenInterceptorQualifier
    fun provideAuthTokenInterceptor(): Interceptor = AuthTokenInterceptor()

    @Provides
    fun provideAuthService(context: Context): AuthorizationService = AuthorizationService(context)


    @Provides
    fun provideUnsplashClient(
        @LoggingInterceptorQualifier loggingInterceptor: Interceptor,
        @AuthTokenInterceptorQualifier authTokenInterceptor: Interceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addNetworkInterceptor(loggingInterceptor)
        .addInterceptor(authTokenInterceptor)
        .followRedirects(true)
        .build()

    @Provides
    fun provideConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

//    @Provides
//    fun provideUnsplashApi(
//    okHttpClient: OkHttpClient,
//    gsonConverterFactory: GsonConverterFactory
//    ): UnsplashApi
}