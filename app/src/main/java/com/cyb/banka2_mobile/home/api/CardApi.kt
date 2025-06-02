package com.cyb.banka2_mobile.home.api

import com.cyb.banka2_mobile.home.api.model.CardDto
import com.cyb.banka2_mobile.home.api.model.TransactionResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CardApi {
    @GET("clients/{id}/accounts")
    suspend fun getCardsForUser(@Path("id") id: String): Response<CardDto>

    @GET("accounts/{accountId}/transactions")
    suspend fun getTransactionsForCard(@Path("accountId") accountId: String): Response<TransactionResponseDto>
}
