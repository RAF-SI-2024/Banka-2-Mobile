package com.cyb.banka2_mobile.login.api.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val token: String,
    val user: UserDto
)

@Serializable
data class UserDto(
    val id: String,
    val firstName: String,
    val lastName: String,
    val dateOfBirth: String,
    val gender: Int,
    val uniqueIdentificationNumber: String,
    val username: String,
    val email: String,
    val phoneNumber: String,
    val address: String,
    val role: Int,
    val department: String,
    val accounts: List<AccountDto>,
    val createdAt: String,
    val modifiedAt: String,
    val activated: Boolean
)

@Serializable
data class AccountDto(
    val id: String,
    val accountNumber: String
)
