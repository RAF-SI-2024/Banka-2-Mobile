package com.cyb.banka2_mobile.totp

import android.util.Log
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
import com.cyb.banka2_mobile.core.TotpManager
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
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.sql.Timestamp
import java.time.Instant
import java.util.UUID
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class TotpViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val totpManager: TotpManager
) : ViewModel() {

    val totpState = totpManager.totpState

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
        val secret = UUID.fromString(loginRepository.getUser().id)
        val secretBytes = uuidToDotNetBytes(secret)
        Log.e("TotpViewModel", "Secret UUID: $secret")
        Log.e("TotpViewModel", "Secret Bytes: ${secretBytes.joinToString(", ")}")
        val config = TimeBasedOneTimePasswordConfig(
            codeDigits = 6,
            hmacAlgorithm = HmacAlgorithm.SHA256,
            timeStep = 30,
            timeStepUnit = TimeUnit.SECONDS
        )

        val timeBasedOneTimePasswordGenerator = TimeBasedOneTimePasswordGenerator(secretBytes, config)

        val code: String = timeBasedOneTimePasswordGenerator.generate(Instant.now())
        setState { copy(totp = code) }
    }

    private fun uuidToDotNetBytes(uuid: UUID): ByteArray {
        val buffer = ByteArray(16)
        val bb = ByteBuffer.wrap(buffer)

        // Extract components
        val msb = uuid.mostSignificantBits
        val lsb = uuid.leastSignificantBits

        // .NET GUID has little-endian layout for first 3 components
        val data1 = (msb shr 32).toInt()
        val data2 = (msb shr 16).toShort()
        val data3 = msb.toShort()

        bb.order(ByteOrder.LITTLE_ENDIAN)
        bb.putInt(data1)
        bb.putShort(data2)
        bb.putShort(data3)

        // Data4 is big-endian; copy bytes as-is
        bb.order(ByteOrder.BIG_ENDIAN)
        bb.putLong(lsb)

        return buffer
    }
}
