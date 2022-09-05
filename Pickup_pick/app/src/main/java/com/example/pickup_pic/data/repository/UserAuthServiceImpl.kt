package com.example.pickup_pic.data.repository

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import com.example.pickup_pic.R
import com.example.pickup_pic.data.network.authentication.AuthConfiguration
import com.example.pickup_pic.data.network.authentication.AuthTokenProvider
import com.example.pickup_pic.domain.repository.UserAuthService
import net.openid.appauth.*
import timber.log.Timber
import javax.inject.Inject

class UserAuthServiceImpl @Inject constructor(
    val context: Context,
    private val authService: AuthorizationService
) : UserAuthService {

    //создаем реквест для авторизации
    override fun getAuthRequest(): AuthorizationRequest {
        //парсим константы которые создаем в AuthConfiguration
        val serviceConfiguration = AuthorizationServiceConfiguration(
            Uri.parse(AuthConfiguration.AUTH_ENDPOINT),
            Uri.parse(AuthConfiguration.TOKEN_ENDPOINT)
        )
        //парсим redirectURL которые прописываем на сайте unsplash
        val redirectUri = Uri.parse(AuthConfiguration.CALLBACK_ENDPOINT)

        //создание самого реквеста и передача необходимых
        return AuthorizationRequest.Builder(
            serviceConfiguration,
            AuthConfiguration.ACCESS_KEY,
            AuthConfiguration.RESPONSE_TYPE,
            redirectUri
        )
            // unsplash не поддерживает верификацию токена и поэтому надо прописывать
            // вручную
            .setCodeVerifier(null)
            .setScope(AuthConfiguration.SCOPE)
            .build()
    }

    //интент, который нужен для браузера и задаем минимальный дизайн для этого
    override fun getCustomTabsIntent(): CustomTabsIntent {
        val params = CustomTabColorSchemeParams.Builder()
            .setToolbarColor(ContextCompat.getColor(context, R.color.grey))
            .build()

        return CustomTabsIntent.Builder()
            .setDefaultColorSchemeParams(params)
            .build()
    }

    //получаем интент, который нам надо обработать
    override fun getAuthRequestIntent(
        authRequest: AuthorizationRequest,
        customTabsIntent: CustomTabsIntent
    ): Intent = authService.getAuthorizationRequestIntent(authRequest, customTabsIntent)

    //из того интента, который приходит с браузера, достаем токен и не забываем прописать проверку
    //для успешной авторизации и для ошибки в случае отсутствия интернета и пр.
    override fun performTokenRequest(
        tokenExchangeRequest: TokenRequest,
        onComplete: () -> Unit,
        onError: (errorMsg: String) -> Unit
    ) {
        val clientAuthentication = getClientAuthentication()

        authService.performTokenRequest(
            tokenExchangeRequest,
            clientAuthentication
        ) { response, exception ->
            if (response != null) {
                //сохраняем
                AuthTokenProvider.authToken = response.accessToken.orEmpty()
                Timber.d(AuthTokenProvider.authToken)
                onComplete()
            } else {
                exception?.message?.let { errorMessage ->
                    Timber.d(errorMessage)
                    onError(errorMessage)
                }
            }

        }
    }

    private fun getClientAuthentication(): ClientAuthentication =
        ClientSecretPost(AuthConfiguration.SECRET_KEY)

}