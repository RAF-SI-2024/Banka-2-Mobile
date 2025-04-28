package com.cyb.banka2_mobile.exchange

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Menu
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cyb.banka2_mobile.exchange.repository.ExchangeRepository
import com.cyb.banka2_mobile.home.BottomNavigationItem
import com.cyb.banka2_mobile.home.HomeContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExchangeViewModel @Inject constructor(
    private val exchangeRepository: ExchangeRepository
): ViewModel() {
    private val _state = MutableStateFlow(ExchangeContract.ExchangeState())
    val state = _state.asStateFlow()

    private fun setState(reducer: ExchangeContract.ExchangeState.() -> ExchangeContract.ExchangeState) = _state.update (reducer)

    private val events = MutableSharedFlow<ExchangeContract.ExchangeEvent>()
    fun setEvent(event: ExchangeContract.ExchangeEvent) = viewModelScope.launch { events.emit(event) }

    init {
        fillNavigationItems()
        viewModelScope.launch { fetchExchangeItems() }
    }

    private fun fillNavigationItems() {
        val homeScreen = BottomNavigationItem(
            title = "Home",
            route = "home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home
        )

        val verificationScreen = BottomNavigationItem(
            title = "Verification",
            route = "verification",
            selectedIcon = Icons.Filled.CheckCircle,
            unselectedIcon = Icons.Outlined.CheckCircle
        )

        val exchangeScreen = BottomNavigationItem(
            title = "Exchange",
            route = "exchange",
            selectedIcon = Icons.Filled.Menu,
            unselectedIcon = Icons.Outlined.Menu
        )

        val loansScreen = BottomNavigationItem(
            title = "loans",
            route = "loans",
            selectedIcon = Icons.Filled.Info,
            unselectedIcon = Icons.Outlined.Info
        )

        viewModelScope.launch {
            setState { copy(navigationItems = listOf(homeScreen, verificationScreen, exchangeScreen, loansScreen)) }
        }
    }

    private suspend fun fetchExchangeItems() {
        setState { copy(isLoading = true) }
        exchangeRepository.getExchanges()?.let {
            setState { copy(listOfExchangeItems = it) }
        } ?: {
            setState { copy(errorFetching = true) }
        }
        setState { copy(isLoading = false) }
    }
}