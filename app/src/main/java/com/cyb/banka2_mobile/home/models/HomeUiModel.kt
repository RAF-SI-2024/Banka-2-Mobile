package com.cyb.banka2_mobile.home.models

import androidx.compose.runtime.Immutable

@Immutable
data class HomeUiModel(
    val firstName: String,
    val lastName: String,
    val email: String
)
