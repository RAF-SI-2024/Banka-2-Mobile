package com.cyb.banka2_mobile.exchange.repository

import com.cyb.banka2_mobile.exchange.api.ExchangeApi
import com.cyb.banka2_mobile.exchange.api.models.CurrencyExchangeUiModel
import com.cyb.banka2_mobile.exchange.api.models.toUiModel
import javax.inject.Inject

class ExchangeRepository @Inject constructor(
    private val exchangeApi: ExchangeApi
) {
    suspend fun getExchanges(): List<CurrencyExchangeUiModel>? {
        val response = exchangeApi.getAllExchange()

        if (response.isSuccessful) {
            return response.body()?.map { it.toUiModel() }
        }
        return null
    }
}