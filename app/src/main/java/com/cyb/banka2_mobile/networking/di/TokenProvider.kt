package com.cyb.banka2_mobile.networking.di

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenProvider @Inject constructor() {
    private var token: String? = null

    fun setToken(newToken: String) {
        token = newToken
    }

    fun clearToken() {
        token = null
    }

    fun getToken(): String? = token
}
