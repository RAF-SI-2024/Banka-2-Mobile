package com.cyb.banka2_mobile.loans

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
import com.cyb.banka2_mobile.home.BottomNavigationItem
import com.cyb.banka2_mobile.loans.repository.LoansRepository
import com.cyb.banka2_mobile.login.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoansViewModel @Inject constructor(
    private val loansRepository: LoansRepository,
    private val loginRepository: LoginRepository
): ViewModel() {
    private val _state = MutableStateFlow(LoansContract.LoansState())
    val state = _state.asStateFlow()

    private fun setState(reducer: LoansContract.LoansState.() -> LoansContract.LoansState) = _state.update (reducer)

    private val events = MutableSharedFlow<LoansContract.LoansEvent>()
    fun setEvent(event: LoansContract.LoansEvent) = viewModelScope.launch { events.emit(event) }

    init {
        fillNavigationItems()
        viewModelScope.launch { fetchLoans() }
    }

    private suspend fun fetchLoans() {
        setState { copy(isLoading = true) }
        val clientId = loginRepository.getUser().id
        loansRepository.getLoans(clientId = clientId)?.let {
            setState { copy(listOfLoans = it) }
        } ?: {
            setState { copy(errorFetching = true) }
        }
        setState { copy(isLoading = false) }
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
}