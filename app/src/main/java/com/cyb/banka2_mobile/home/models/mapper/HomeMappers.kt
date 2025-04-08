package com.cyb.banka2_mobile.home.models.mapper

import com.cyb.banka2_mobile.home.models.HomeUiModel
import com.cyb.banka2_mobile.login.model.UserEntity

fun UserEntity.toHomeUiModel(): HomeUiModel {
    return HomeUiModel(
        firstName = firstName,
        lastName = lastName,
        email = email
    )
}