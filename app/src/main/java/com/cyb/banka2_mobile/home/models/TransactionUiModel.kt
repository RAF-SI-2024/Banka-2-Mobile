package com.cyb.banka2_mobile.home.models

data class TransactionUiModel(
    val id: String,
    val fromAccountNumber: String,
    val toAccountNumber: String,
    val fromAmount: Double,
    val toAmount: Double,
    val code: String,
    val codeName: String,
    val referenceNumber: String,
    val purpose: String,
    val status: Int,
    val createdAt: String
)
