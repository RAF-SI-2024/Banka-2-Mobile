package com.cyb.banka2_mobile.loans.api

import com.cyb.banka2_mobile.loans.api.models.LoanResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface LoansApi {
    @GET("/api/v1/clients/{clientId}/loans")
    suspend fun getLoansForClient(@Path("clientId") clientId: String): Response<LoanResponseDto>
}