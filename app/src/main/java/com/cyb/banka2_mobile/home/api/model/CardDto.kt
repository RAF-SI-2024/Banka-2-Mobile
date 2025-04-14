package com.cyb.banka2_mobile.home.api.model

import com.cyb.banka2_mobile.home.models.AccountUiModel
import com.cyb.banka2_mobile.home.models.CardUiModel
import kotlinx.serialization.Serializable

@Serializable
data class CardDto(
    val items: List<User>,
    val pageNumber: Int,
    val pageSize: Int,
    val totalElements: Int,
    val totalPages: Int
)

@Serializable
data class User(
    val id: String,
    val firstName: String,
    val lastName: String,
    val dateOfBirth: String,
    val gender: Int,
    val uniqueIdentificationNumber: String,
    val email: String,
    val phoneNumber: String,
    val address: String,
    val role: Int,
    val accounts: List<Account>,
    val createdAt: String,
    val modifiedAt: String,
    val activated: Boolean
)

@Serializable
data class Account(
    val id: String,
    val accountNumber: String
)

fun CardDto.toUiModel(): List<CardUiModel> {
    return items.map { user ->
        CardUiModel(
            userId = user.id,
            fullName = "${user.firstName} ${user.lastName}",
            email = user.email,
            phone = user.phoneNumber,
            accounts = user.accounts.map { account ->
                AccountUiModel(
                    accountId = account.id,
                    accountNumber = account.accountNumber
                )
            }
        )
    }
}