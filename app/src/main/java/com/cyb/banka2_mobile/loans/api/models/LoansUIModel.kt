package com.cyb.banka2_mobile.loans.api.models

data class LoanUiModel(
    val id: String,
    val amount: Double,
    val period: Int,
    val currencyCode: String,
    val accountNumber: String,
    val clientName: String,
    val loanTypeName: String,
    val status: Int,
    val interestType: Int,
    val nominalInstallmentRate: Double,
    val remainingAmount: Double,
    val creationDate: String,
    val maturityDate: String
)
