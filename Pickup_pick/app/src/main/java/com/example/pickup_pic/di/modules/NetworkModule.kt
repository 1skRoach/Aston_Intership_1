package com.example.pickup_pic.di.modules

import android.app.Application
import android.content.Context
import com.example.pickup_pic.data.api.UnsplashApi
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
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

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
    @LoggingInterceptorQualifier
    fun provideLoginInterceptor(): Interceptor =
        HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)


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
    fun provideConverterFactory(): MoshiConverterFactory = MoshiConverterFactory.create()



    @Provides
    fun provideUnsplashApi(
        okhttpClient: OkHttpClient,
        moshiConverterFactory: MoshiConverterFactory
    ): UnsplashApi {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.unsplash.com/")
            .addConverterFactory(moshiConverterFactory)
            .client(okhttpClient)
            .build()

        return retrofit.create<UnsplashApi>()
    }
}