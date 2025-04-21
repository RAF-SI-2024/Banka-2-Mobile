package com.cyb.banka2_mobile.login.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserEntity (
    @PrimaryKey
    val token: String,
    val id: String,
    val firstName: String,
    val lastName: String,
    val dateOfBirth: String,
    val username: String,
    val email: String,
    val phoneNumber: String,
    val address: String,
    val role: String
)