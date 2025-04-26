package com.cyb.banka2_mobile.home.repository

import com.cyb.banka2_mobile.db.AppDatabase
import com.cyb.banka2_mobile.home.api.CardApi
import com.cyb.banka2_mobile.home.api.model.toUiModel
import com.cyb.banka2_mobile.home.api.model.toUiModels
import com.cyb.banka2_mobile.home.models.CardUiModel
import com.cyb.banka2_mobile.home.models.HomeUiModel
import com.cyb.banka2_mobile.home.models.TransactionUiModel
import com.cyb.banka2_mobile.home.models.mapper.toHomeUiModel
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val database: AppDatabase,
    private val cardApi: CardApi
) {
    suspend fun getUserInfo(): HomeUiModel? {
        val response = database.userDao().getUser()

        if (response.isNotEmpty())
            return response[0].toHomeUiModel()
        return null
    }

    suspend fun getCards(userId: String): List<CardUiModel>? {
        val response = cardApi.getCardsForUser(id = userId)
        println("Raw JSON: ${response.body().toString()}")

        if (response.isSuccessful)
            return response.body()?.toUiModel()
        return emptyList()
    }

    suspend fun getTransactionsForCard(accountId: String): List<TransactionUiModel>? {
        val response = cardApi.getTransactionsForCard(accountId = accountId)

        if (response.isSuccessful)
            return response.body()?.toUiModels()
        return emptyList()
    }
}