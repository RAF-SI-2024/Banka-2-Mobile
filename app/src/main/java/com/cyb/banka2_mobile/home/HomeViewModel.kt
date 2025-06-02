package com.cyb.banka2_mobile.home

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
import com.cyb.banka2_mobile.home.repository.HomeRepository
import com.cyb.banka2_mobile.login.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
    private val loginRepository: LoginRepository
): ViewModel() {
    private val _state = MutableStateFlow(HomeContract.HomeState())
    val state = _state.asStateFlow()

    private fun setState(reducer: HomeContract.HomeState.() -> HomeContract.HomeState) = _state.update (reducer)

    private val events = MutableSharedFlow<HomeContract.HomeEvent>()
    fun setEvent(event: HomeContract.HomeEvent) = viewModelScope.launch { events.emit(event) }

    init {
        viewModelScope.launch { populateDummyData() }
        populateState()
        observeEvents()
        fillNavigationItems()
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

    private fun observeEvents() {
        viewModelScope.launch {
            events.collect { event ->
                when (event) {
                    is HomeContract.HomeEvent.SelectCard -> {
                        setState { copy(selectedAccount = event.account) }
                    }

                    is HomeContract.HomeEvent.SelectedNavigationIndex -> {
                        setState { copy(selectedItemNavigationIndex = event.index) }
                    }

                    HomeContract.HomeEvent.SingOut -> {
                        doLogOut()
                    }
                }
            }
        }
    }

    private fun doLogOut() {
        viewModelScope.launch {
            loginRepository.delete()
            setState { copy(navigateToLogin = true) }
        }
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

    private suspend fun populateDummyData() {
        val userId = loginRepository.getUser().id

        val cards = homeRepository.getCards(userId).orEmpty()

        val allAccounts = cards.flatMap { it.accounts }

        val transactionsMap = allAccounts.associate { account ->
            val transactions = homeRepository.getTransactionsForCard(account.accountId).orEmpty()
            account.accountId to transactions
        }

        val selectedAccount = allAccounts.firstOrNull()

        setState {
            copy(
                loading = false,
                cards = cards,
                transactions = transactionsMap,
                selectedAccount = selectedAccount
            )
        }
    }
}