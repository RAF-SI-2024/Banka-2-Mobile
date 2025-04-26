package com.cyb.banka2_mobile.loans.api.models

import kotlinx.serialization.Serializable

@Serializable
data class LoanResponseDto(
    val items: List<LoanDto>,
    val pageNumber: Int,
    val pageSize: Int,
    val totalElements: Int,
    val totalPages: Int
)

@Serializable
data class LoanDto(
    val id: String,
    val type: LoanTypeDto,
    val account: AccountDto,
    val amount: Double,
    val period: Int,
    val creationDate: String,
    val maturityDate: String,
    val currency: CurrencyDto,
    val status: Int,
    val interestType: Int,
    val nominalInstallmentRate: Double,
    val remainingAmount: Double,
    val createdAt: String,
    val modifiedAt: String
)

@Serializable
data class LoanTypeDto(
    val id: String,
    val name: String,
    val margin: Double
)

@Serializable
data class AccountDto(
    val id: String,
    val accountNumber: String,
    val name: String,
    val client: ClientDto,
    val balance: Double,
    val availableBalance: Double,
    val dailyLimit: Double,
    val monthlyLimit: Double,
    val employee: EmployeeDto,
    val currency: CurrencyDto,
    val type: AccountTypeDto,
    val accountCurrencies: List<AccountCurrencyDto>,
    val creationDate: String,
    val expirationDate: String,
    val status: Boolean,
    val createdAt: String,
    val modifiedAt: String
)

@Serializable
data class ClientDto(
    val id: String,
    val firstName: String,
    val lastName: String,
    val dateOfBirth: String,
    val gender: Int,
    val uniqueIdentificationNumber: String,
    val email: String,
    val phoneNumber: String,
    val address: String,
    val role: Int,
    val activated: Boolean,
    val createdAt: String,
    val modifiedAt: String
)

@Serializable
data class EmployeeDto(
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
    val employed: Boolean,
    val activated: Boolean,
    val createdAt: String,
    val modifiedAt: String
)

@Serializable
data class CurrencyDto(
    val id: String,
    val name: String,
    val code: String,
    val symbol: String,
    val countries: List<CountryDto>,
    val description: String,
    val status: Boolean,
    val createdAt: String,
    val modifiedAt: String
)

@Serializable
data class CountryDto(
    val id: String,
    val name: String,
    val createdAt: String,
    val modifiedAt: String
)

@Serializable
data class AccountTypeDto(
    val id: String,
    val name: String,
    val code: String,
    val createdAt: String,
    val modifiedAt: String
)

@Serializable
data class AccountCurrencyDto(
    val id: String,
    val account: SimpleAccountDto,
    val currency: CurrencyDto?
    ,
    val employee: EmployeeDto?,
    val balance: Double,
    val availableBalance: Double,
    val dailyLimit: Double,
    val monthlyLimit: Double,
    val createdAt: String,
    val modifiedAt: String
)

@Serializable
data class SimpleAccountDto(
    val id: String,
    val accountNumber: String
)

fun LoanResponseDto.toUiModels(): List<LoanUiModel> {
    return items.map { loanDto ->
        LoanUiModel(
            id = loanDto.id,
            amount = loanDto.amount,
            period = loanDto.period,
            currencyCode = loanDto.currency.code,
            accountNumber = loanDto.account.accountNumber,
            clientName = "${loanDto.account.client.firstName} ${loanDto.account.client.lastName}",
            loanTypeName = loanDto.type.name,
            status = loanDto.status,
            interestType = loanDto.interestType,
            nominalInstallmentRate = loanDto.nominalInstallmentRate,
            remainingAmount = loanDto.remainingAmount,
            creationDate = loanDto.creationDate,
            maturityDate = loanDto.maturityDate
        )
    }
}
