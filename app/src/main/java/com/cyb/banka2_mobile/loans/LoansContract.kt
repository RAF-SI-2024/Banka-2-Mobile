package com.cyb.banka2_mobile.loans

import androidx.compose.runtime.Immutable
import com.cyb.banka2_mobile.home.BottomNavigationItem
import com.cyb.banka2_mobile.loans.api.models.LoanUiModel
import okhttp3.internal.immutableListOf

interface LoansContract {
    @Immutable
    data class LoansState(
        val listOfLoans: List<LoanUiModel> = immutableListOf(),
        val isLoading: Boolean = false,
        val errorFetching: Boolean = false,
        val navigationItems: List<BottomNavigationItem> = emptyList(),
        val selectedItemNavigationIndex: Int = 3,
    )

    sealed class LoansEvent {
        data class SelectedNavigationIndex(val index: Int): LoansEvent()
    }
}