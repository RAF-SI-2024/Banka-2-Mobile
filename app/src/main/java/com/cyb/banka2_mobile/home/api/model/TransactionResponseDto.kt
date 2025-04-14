package com.cyb.banka2_mobile.home.api.model

import com.cyb.banka2_mobile.home.models.TransactionUiModel
import kotlinx.serialization.Serializable

@Serializable
data class TransactionResponseDto(
    val items: List<TransactionDto>,
    val pageNumber: Int,
    val pageSize: Int,
    val totalElements: Int,
    val totalPages: Int
)

@Serializable
data class TransactionDto(
    val id: String,
    val fromAccount: AccountDto,
    val toAccount: AccountDto,
    val fromAmount: Double,
    val toAmount: Double,
    val code: TransactionCodeDto,
    val referenceNumber: String,
    val purpose: String,
    val status: String,
    val createdAt: String,
    val modifiedAt: String
)

@Serializable
data class AccountDto(
    val id: String,
    val accountNumber: String
)

@Serializable
data class TransactionCodeDto(
    val id: String,
    val code: String,
    val name: String,
    val createdAt: String,
    val modifiedAt: String
)

fun TransactionResponseDto.toUiModels(): List<TransactionUiModel> {
    return items.map { it.toUiModel() }
}

fun TransactionDto.toUiModel(): TransactionUiModel {
    return TransactionUiModel(
        id = id,
        fromAccountNumber = fromAccount.accountNumber,
        toAccountNumber = toAccount.accountNumber,
        fromAmount = fromAmount,
        toAmount = toAmount,
        code = code.code,
        codeName = code.name,
        referenceNumber = referenceNumber,
        purpose = purpose,
        status = status,
        createdAt = createdAt
    )
}