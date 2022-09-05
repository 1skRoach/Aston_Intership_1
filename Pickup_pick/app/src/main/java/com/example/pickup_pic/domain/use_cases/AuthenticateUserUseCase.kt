package com.example.pickup_pic.domain.use_cases

import android.content.Intent
import net.openid.appauth.TokenRequest

interface AuthenticateUserUseCase {
    operator fun invoke(): Intent

    fun getToken(
        tokenExchangeRequest: TokenRequest,
        onComplete: () -> Unit,
        onError: (errorMsg: String) -> Unit
    )
}