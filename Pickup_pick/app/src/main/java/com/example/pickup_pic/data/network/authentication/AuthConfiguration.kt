package com.example.pickup_pic.data.network.authentication

import net.openid.appauth.ResponseTypeValues

object AuthConfiguration {

    const val AUTH_ENDPOINT = "https://unsplash.com/oauth/authorize"
    const val TOKEN_ENDPOINT = "https://unsplash.com/oauth/token"

    //Redirect URI
    //(optional for applications that are only using the 'Public' permissions)
    const val CALLBACK_ENDPOINT = "unsplash://lt.iskander.unsplash/callback"

    const val ACCESS_KEY = "D5bBL8KbevuPuI33K0sLa-eRJf9995CdqESHuHbGNlY"
    const val SECRET_KEY = "8deHX1zRVrG-HbGeWxx6TZoYGYztyCV2TjCS729cFgE"

    const val RESPONSE_TYPE = ResponseTypeValues.CODE
    const val SCOPE = "public " +
            "read_user " +
            "write_user " +
            "read_photos " +
            "write_photos " +
            "write_likes " +
            "write_followers " +
            "read_collections " +
            "write_collections"
}