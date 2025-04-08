package com.cyb.banka2_mobile.home.repository

import com.cyb.banka2_mobile.db.AppDatabase
import com.cyb.banka2_mobile.home.models.HomeUiModel
import com.cyb.banka2_mobile.home.models.mapper.toHomeUiModel
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val database: AppDatabase
) {
    suspend fun getUserInfo(): HomeUiModel? {
        val response = database.userDao().getUser()

        if (response.isNotEmpty())
            return response[0].toHomeUiModel()
        return null
    }
}