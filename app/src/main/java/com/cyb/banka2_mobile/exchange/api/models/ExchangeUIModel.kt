package com.cyb.banka2_mobile.exchange.api.models

data class CurrencyExchangeUiModel(
    val id: String,
    val currencyFrom: CurrencyUiModel,
    val currencyTo: CurrencyUiModel,
    val commission: Double,
    val rate: Double,
    val inverseRate: Double,
    val createdAt: String,
    val modifiedAt: String
)

data class CurrencyUiModel(
    val id: String,
    val name: String,
    val code: String,
    val symbol: String,
    val description: String,
    val status: Boolean,
    val createdAt: String,
    val modifiedAt: String
)
