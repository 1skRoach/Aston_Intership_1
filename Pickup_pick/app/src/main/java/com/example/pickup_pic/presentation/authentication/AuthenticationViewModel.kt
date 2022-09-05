package com.example.pickup_pic.presentation.authentication

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pickup_pic.domain.use_cases.AuthenticateUserUseCase
import com.example.pickup_pic.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import net.openid.appauth.TokenRequest
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val authenticateUserUseCase: AuthenticateUserUseCase
) : ViewModel() {

//    private val _authenticatingPageIntent = SingleLiveEvent<Intent>()
//    val authenticatingPageIntent = _authenticatingPageIntent

    private val _authenticationState =
        MutableStateFlow<AuthenticationState>(AuthenticationState.NotLoggedIn)
    val authState = _authenticationState.asStateFlow()

    //при повороте чтобы один раз сработал ставим replay = 0
    private val _authenticatingPageIntent = MutableSharedFlow<Intent>(replay = 0)
    val authenticatingPageIntent = _authenticatingPageIntent

    fun authenticateUser() {
        val authUser = authenticateUserUseCase()
        viewModelScope.launch {
            _authenticatingPageIntent.emit(authUser)
        }
    }

    fun getToken(tokenRequest: TokenRequest) {
        _authenticationState.value = AuthenticationState.NotLoggedIn
        authenticateUserUseCase.getToken(
            tokenExchangeRequest = tokenRequest,
            onComplete = { _authenticationState.value = AuthenticationState.LoggedIn },
            onError = { errorMessage ->
                _authenticationState.value = AuthenticationState.Error(errorMessage)
            }
        )
    }
}