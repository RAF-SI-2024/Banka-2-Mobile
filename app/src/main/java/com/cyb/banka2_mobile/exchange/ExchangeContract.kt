package com.cyb.banka2_mobile.exchange

import androidx.compose.runtime.Immutable
import com.cyb.banka2_mobile.exchange.api.models.CurrencyExchangeUiModel
import com.cyb.banka2_mobile.home.BottomNavigationItem
import okhttp3.internal.immutableListOf

interface ExchangeContract {
    @Immutable
    data class ExchangeState(
        val listOfExchangeItems: List<CurrencyExchangeUiModel> = immutableListOf(),
        val isLoading: Boolean = false,
        val errorFetching: Boolean = false,
        val navigationItems: List<BottomNavigationItem> = emptyList(),
        val selectedItemNavigationIndex: Int = 2,
    )

    sealed class ExchangeEvent {
        data class SelectedNavigationIndex(val index: Int): ExchangeEvent()
    }
}