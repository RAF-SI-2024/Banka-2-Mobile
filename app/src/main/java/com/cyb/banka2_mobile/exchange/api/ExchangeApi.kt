package com.cyb.banka2_mobile.exchange.api

import com.cyb.banka2_mobile.exchange.api.models.CurrencyExchangeDto
import retrofit2.Response
import retrofit2.http.GET

interface ExchangeApi {
    @GET("/api/v1/exchanges")
    suspend fun getAllExchange(): Response<List<CurrencyExchangeDto>>
}