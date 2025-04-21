package com.cyb.banka2_mobile.totp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cyb.banka2_mobile.login.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.turingcomplete.kotlinonetimepassword.HmacAlgorithm
import dev.turingcomplete.kotlinonetimepassword.TimeBasedOneTimePasswordConfig
import dev.turingcomplete.kotlinonetimepassword.TimeBasedOneTimePasswordGenerator
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
): ViewModel() {
    private val _state = MutableStateFlow(TotpContract.TotpState())
    val state = _state.asStateFlow()

    private fun setState(reducer: TotpContract.TotpState.() -> TotpContract.TotpState) = _state.update (reducer)

    init {
        viewModelScope.launch {
            generateTotp()
        }
    }

    private suspend fun generateTotp() {
//        val secret = loginRepository.getUser().id
        val secret = "0be15ed6-db15-4605-898c-6a843fbc604b"
        val config = TimeBasedOneTimePasswordConfig(codeDigits = 6,
            hmacAlgorithm = HmacAlgorithm.SHA256,
            timeStep = 30,
            timeStepUnit = TimeUnit.SECONDS)
        val timeBasedOneTimePasswordGenerator = TimeBasedOneTimePasswordGenerator(secret.toByteArray(), config)

        var code0: String = timeBasedOneTimePasswordGenerator.generate()
    }
}