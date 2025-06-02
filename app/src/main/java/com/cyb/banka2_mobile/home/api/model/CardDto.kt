package com.cyb.banka2_mobile.home.api.model

import com.cyb.banka2_mobile.home.models.AccountUiModel
import com.cyb.banka2_mobile.home.models.CardUiModel
import kotlinx.serialization.Serializable

@Serializable
data class CardDto(
    val items: List<CardAccountDto>,
    val pageNumber: Int,
    val pageSize: Int,
    val totalElements: Int,
    val totalPages: Int
)

@Serializable
data class CardAccountDto(
    val id: String,
    val accountNumber: String,
    val client: ClientDto? = null
)

@Serializable
data class ClientDto(
    val id: String,
    val firstName: String? = null,
    val lastName: String? = null,
    val dateOfBirth: String? = null,
    val gender: Int? = null,
    val uniqueIdentificationNumber: String? = null,
    val email: String? = null,
    val phoneNumber: String? = null,
    val address: String? = null,
    val role: Int? = null,
    val permissions: Int? = null,
    val createdAt: String? = null,
    val modifiedAt: String? = null,
    val activated: Boolean? = null
)

fun CardDto.toUiModel(): List<CardUiModel> {
    return items.mapNotNull { account ->
        val client = account.client
        if (client != null) {
            CardUiModel(
                userId = client.id,
                fullName = listOfNotNull(client.firstName, client.lastName).joinToString(" "),
                email = client.email.orEmpty(),
                phone = client.phoneNumber.orEmpty(),
                accounts = listOf(
                    AccountUiModel(
                        accountId = account.id,
                        accountNumber = account.accountNumber
                    )
                )
            )
        } else {
            null
        }
    }
}