package com.cyb.banka2_mobile.totp

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
import com.cyb.banka2_mobile.login.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.turingcomplete.kotlinonetimepassword.HmacAlgorithm
import dev.turingcomplete.kotlinonetimepassword.TimeBasedOneTimePasswordConfig
import dev.turingcomplete.kotlinonetimepassword.TimeBasedOneTimePasswordGenerator
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class TotpViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : ViewModel() {

    private val _state = MutableStateFlow(TotpContract.TotpState())
    val state = _state.asStateFlow()

    private fun setState(reducer: TotpContract.TotpState.() -> TotpContract.TotpState) =
        _state.update(reducer)

    private val events = MutableSharedFlow<TotpContract.TotpEvent>()
    fun setEvent(event: TotpContract.TotpEvent) = viewModelScope.launch { events.emit(event) }


    init {
        fillNavigationItems()
        viewModelScope.launch {
            while (true) {
                generateTotp()
                delay(30_000)
            }
        }
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

    private suspend fun generateTotp() {
        val secret = loginRepository.getUser().id
        val config = TimeBasedOneTimePasswordConfig(
            codeDigits = 6,
            hmacAlgorithm = HmacAlgorithm.SHA256,
            timeStep = 30,
            timeStepUnit = TimeUnit.SECONDS
        )
        val timeBasedOneTimePasswordGenerator = TimeBasedOneTimePasswordGenerator(secret.toByteArray(), config)

        val code: String = timeBasedOneTimePasswordGenerator.generate()
        setState { copy(totp = code) }
    }
}
