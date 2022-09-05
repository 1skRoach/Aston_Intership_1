package com.example.pickup_pic.data.use_case

import android.content.Intent
import com.example.pickup_pic.domain.repository.UserAuthService
import com.example.pickup_pic.domain.use_cases.AuthenticateUserUseCase
import net.openid.appauth.TokenRequest
import javax.inject.Inject

class AuthenticateUserUseCaseImpl @Inject constructor(
    private val userAuthService: UserAuthService
) : AuthenticateUserUseCase {
    override fun invoke(): Intent {
        val authRequest = userAuthService.getAuthRequest()
        val customTabsIntent = userAuthService.getCustomTabsIntent()

        return userAuthService.getAuthRequestIntent(
            authRequest,
            customTabsIntent
        )    }

    override fun getToken(
        tokenExchangeRequest: TokenRequest,
        onComplete: () -> Unit,
        onError: (errorMsg: String) -> Unit
    )  = userAuthService.performTokenRequest(tokenExchangeRequest, onComplete, onError)
}