package com.cyb.banka2_mobile.core

import com.cyb.banka2_mobile.login.repository.LoginRepository
import dev.turingcomplete.kotlinonetimepassword.HmacAlgorithm
import dev.turingcomplete.kotlinonetimepassword.TimeBasedOneTimePasswordConfig
import dev.turingcomplete.kotlinonetimepassword.TimeBasedOneTimePasswordGenerator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TotpManager @Inject constructor(
    private val loginRepository: LoginRepository
) {
    private val _totpState = MutableStateFlow(TotpInfo("", 30))
    val totpState = _totpState.asStateFlow()

    private var currentSecret: String? = null

    init {
        CoroutineScope(Dispatchers.Default).launch {
            currentSecret = loginRepository.getUser().id

            while (true) {
                val now = System.currentTimeMillis()
                val seconds = (now / 1000).toInt()
                val secondsPassed = seconds % 30
                val secondsLeft = 30 - secondsPassed

                val config = TimeBasedOneTimePasswordConfig(
                    codeDigits = 6,
                    hmacAlgorithm = HmacAlgorithm.SHA256,
                    timeStep = 30,
                    timeStepUnit = TimeUnit.SECONDS
                )
                val secret = currentSecret ?: ""
                val generator = TimeBasedOneTimePasswordGenerator(secret.toByteArray(), config)
                val code = generator.generate()

                _totpState.value = TotpInfo(code, secondsLeft)

                delay(1000)
            }
        }
    }

    data class TotpInfo(
        val totp: String,
        val secondsLeft: Int
    )
}
