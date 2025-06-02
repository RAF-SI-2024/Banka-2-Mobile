package com.cyb.banka2_mobile.totp

import androidx.compose.runtime.Immutable
import com.cyb.banka2_mobile.home.BottomNavigationItem

interface TotpContract {
    @Immutable
    data class TotpState(
        val totp: String = "",
        val navigationItems: List<BottomNavigationItem> = emptyList(),
        val selectedItemNavigationIndex: Int = 1,
    )

    sealed class TotpEvent{
        data class SelectedNavigationIndex(val index: Int): TotpEvent()
    }
}