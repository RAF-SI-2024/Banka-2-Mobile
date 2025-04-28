package com.cyb.banka2_mobile.login.api.model.mappers

import com.cyb.banka2_mobile.login.api.model.LoginResponse
import com.cyb.banka2_mobile.login.model.UserEntity
import com.cyb.banka2_mobile.login.model.UserUiModel

fun LoginResponse.toDbModel(): UserEntity {
    return UserEntity(
        id = user.id,
        token = token,
        address = user.address,
        firstName = user.firstName,
        lastName = user.lastName,
        dateOfBirth = user.dateOfBirth,
        username = user.username,
        email = user.email,
        phoneNumber = user.phoneNumber,
        role = user.role.toString()
    )
}

fun UserEntity.toUiModel(): UserUiModel {
    return UserUiModel(
        id = id,
        firstName = firstName,
        lastName = lastName,
        token = token
    )
}