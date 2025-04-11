package com.cyb.banka2_mobile.login.api

import com.cyb.banka2_mobile.login.api.model.LoginRequest
import com.cyb.banka2_mobile.login.api.model.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {
    @POST("users/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>
}