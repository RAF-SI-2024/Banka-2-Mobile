package com.cyb.banka2_mobile.home

import androidx.compose.runtime.Immutable
import com.cyb.banka2_mobile.home.models.AccountUiModel
import com.cyb.banka2_mobile.home.models.CardUiModel
import com.cyb.banka2_mobile.home.models.HomeUiModel
import com.cyb.banka2_mobile.home.models.TransactionUiModel

interface HomeContract {
    @Immutable
    data class HomeState(
        val loading: Boolean = false,
        val homeUiModel: HomeUiModel? = null,
        val cards: List<CardUiModel> = emptyList(),
        val transactions: Map<String, List<TransactionUiModel>> = emptyMap(),
        val selectedAccount: AccountUiModel? = null,
        val navigationItems: List<BottomNavigationItem> = emptyList(),
        val selectedItemNavigationIndex: Int = 0,
    )

    sealed class HomeEvent{
        data class SelectCard(val account: AccountUiModel): HomeEvent()
        data class SelectedNavigationIndex(val index: Int): HomeEvent()
    }
}