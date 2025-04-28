package com.cyb.banka2_mobile.exchange.api.models

import kotlinx.serialization.Serializable

@Serializable
data class CurrencyExchangeDto(
    val id: String,
    val currencyFrom: CurrencyDto,
    val currencyTo: CurrencyDto,
    val commission: Double,
    val rate: Double,
    val inverseRate: Double,
    val createdAt: String,
    val modifiedAt: String
)

@Serializable
data class CurrencyDto(
    val id: String,
    val name: String,
    val code: String,
    val symbol: String,
    val description: String,
    val status: Boolean,
    val createdAt: String,
    val modifiedAt: String
)

fun CurrencyExchangeDto.toUiModel(): CurrencyExchangeUiModel {
    return CurrencyExchangeUiModel(
        id = id,
        currencyFrom = currencyFrom.toUiModel(),
        currencyTo = currencyTo.toUiModel(),
        commission = commission,
        rate = rate,
        inverseRate = inverseRate,
        createdAt = createdAt,
        modifiedAt = modifiedAt
    )
}

fun CurrencyDto.toUiModel(): CurrencyUiModel {
    return CurrencyUiModel(
        id = id,
        name = name,
        code = code,
        symbol = symbol,
        description = description,
        status = status,
        createdAt = createdAt,
        modifiedAt = modifiedAt
    )
}