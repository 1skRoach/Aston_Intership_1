package com.example.pickup_pic.data.network.authentication

import okhttp3.Interceptor
import okhttp3.Response

class AuthTokenInterceptor : Interceptor {

    //запрос отправляется на сервер и перехватывает реквест, меняю header и не нужно пересоздавать каждый
    //раз. перехватчик запроса.
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val modifiedRequest = request.newBuilder()
            //требование unsplash.
            .addHeader("Authorization", "Bearer ${AuthTokenProvider.authToken}")
            .addHeader("Accept-Version", "v1")
            .build()

        return chain.proceed(modifiedRequest)
    }
}