package com.cyb.banka2_mobile.home.models

data class CardUiModel(
    val userId: String,
    val fullName: String,
    val email: String,
    val phone: String,
    val accounts: List<AccountUiModel>
)

data class AccountUiModel(
    val accountId: String,
    val accountNumber: String
)
