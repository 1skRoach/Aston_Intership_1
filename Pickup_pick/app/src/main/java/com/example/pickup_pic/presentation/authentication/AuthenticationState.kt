package com.example.pickup_pic.presentation.authentication

sealed class AuthenticationState {
    object Loading : AuthenticationState()
    object NotLoggedIn : AuthenticationState()
    object LoggedIn : AuthenticationState()
    class Error(val errorMsg: String) : AuthenticationState()
}