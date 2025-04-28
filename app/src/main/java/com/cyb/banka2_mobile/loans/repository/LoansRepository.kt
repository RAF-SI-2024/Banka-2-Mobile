package com.cyb.banka2_mobile.loans.repository

import com.cyb.banka2_mobile.loans.api.LoansApi
import com.cyb.banka2_mobile.loans.api.models.LoanUiModel
import com.cyb.banka2_mobile.loans.api.models.toUiModels
import javax.inject.Inject

class LoansRepository @Inject constructor(
    private val loansApi: LoansApi
) {
    suspend fun getLoans(clientId: String): List<LoanUiModel>? {
        val response = loansApi.getLoansForClient(clientId = clientId)

        if (response.isSuccessful)
            return response.body()?.toUiModels()
        return null
    }
}