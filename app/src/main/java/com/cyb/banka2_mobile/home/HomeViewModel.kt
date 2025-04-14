package com.cyb.banka2_mobile.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cyb.banka2_mobile.home.models.AccountUiModel
import com.cyb.banka2_mobile.home.models.TransactionUiModel
import com.cyb.banka2_mobile.home.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.cyb.banka2_mobile.home.models.CardUiModel as CardUiModel1

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
): ViewModel() {
    private val _state = MutableStateFlow(HomeContract.HomeState())
    val state = _state.asStateFlow()

    private fun setState(reducer: HomeContract.HomeState.() -> HomeContract.HomeState) = _state.update (reducer)

    private val events = MutableSharedFlow<HomeContract.HomeEvent>()
    fun setEvent(event: HomeContract.HomeEvent) = viewModelScope.launch { events.emit(event) }


    init {
        populateDummyData()
        populateState()
    }

    private fun populateState() {
        viewModelScope.launch {
            setState { copy(loading = true) }
            val homeUiModel = homeRepository.getUserInfo()
            setState { copy(homeUiModel = homeUiModel) }
            delay(2000L)
            setState { copy(loading = false) }
        }
    }

    private fun populateDummyData() {
        val dummyAccounts = listOf(
            AccountUiModel(accountId = "acc1", accountNumber = "222001112345678901"),
            AccountUiModel(accountId = "acc2", accountNumber = "222001112345678902"),
            AccountUiModel(accountId = "acc3", accountNumber = "222001112345678903")
        )

        val dummyCard = CardUiModel1(
            userId = "user1",
            fullName = "Aleksandar Ivanović",
            email = "aleksandar.ivanovic@gmail.com",
            phone = "+381698812321",
            accounts = dummyAccounts
        )

        val dummyTransactions = listOf(
            TransactionUiModel(
                id = "tx1",
                fromAccountNumber = "222001112345678901",
                toAccountNumber = "222001112345678902",
                fromAmount = 1000.0,
                toAmount = 950.0,
                code = "289",
                codeName = "Platna transakcija",
                referenceNumber = "2345454333",
                purpose = "Plaćanje fakture",
                status = "Failed",
                createdAt = "2025-04-14T15:36:11"
            ),
            TransactionUiModel(
                id = "tx2",
                fromAccountNumber = "222001112345678902",
                toAccountNumber = "222001112345678903",
                fromAmount = 500.0,
                toAmount = 500.0,
                code = "289",
                codeName = "Prenos sredstava",
                referenceNumber = "2345454334",
                purpose = "Interni transfer",
                status = "Success",
                createdAt = "2025-04-13T10:15:00"
            ),
            TransactionUiModel(
                id = "tx2",
                fromAccountNumber = "222001112345678902",
                toAccountNumber = "222001112345678903",
                fromAmount = 500.0,
                toAmount = 500.0,
                code = "289",
                codeName = "Prenos sredstava",
                referenceNumber = "2345454334",
                purpose = "Interni transfer",
                status = "Success",
                createdAt = "2025-04-13T10:15:00"
            ),
            TransactionUiModel(
                id = "tx2",
                fromAccountNumber = "222001112345678902",
                toAccountNumber = "222001112345678903",
                fromAmount = 500.0,
                toAmount = 500.0,
                code = "289",
                codeName = "Prenos sredstava",
                referenceNumber = "2345454334",
                purpose = "Interni transfer",
                status = "Success",
                createdAt = "2025-04-13T10:15:00"
            ),
            TransactionUiModel(
                id = "tx2",
                fromAccountNumber = "222001112345678902",
                toAccountNumber = "222001112345678903",
                fromAmount = 500.0,
                toAmount = 500.0,
                code = "289",
                codeName = "Prenos sredstava",
                referenceNumber = "2345454334",
                purpose = "Interni transfer",
                status = "Success",
                createdAt = "2025-04-13T10:15:00"
            ),
            TransactionUiModel(
                id = "tx2",
                fromAccountNumber = "222001112345678902",
                toAccountNumber = "222001112345678903",
                fromAmount = 500.0,
                toAmount = 500.0,
                code = "289",
                codeName = "Prenos sredstava",
                referenceNumber = "2345454334",
                purpose = "Interni transfer",
                status = "Success",
                createdAt = "2025-04-13T10:15:00"
            )
        )

        setState {
            copy(
                loading = false,
                cards = listOf(dummyCard),
                transactions = dummyTransactions
            )
        }
    }

}