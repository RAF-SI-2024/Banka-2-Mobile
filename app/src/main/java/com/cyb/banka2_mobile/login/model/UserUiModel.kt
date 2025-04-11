package com.cyb.banka2_mobile.login.model

import androidx.compose.runtime.Immutable

@Immutable
data class UserUiModel(
    val firstName: String,
    val lastName: String,
    val token: String
)
