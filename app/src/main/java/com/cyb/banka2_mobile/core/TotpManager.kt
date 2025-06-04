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
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.time.Instant
import java.util.UUID
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
                if (!currentSecret.isNullOrBlank()) {
                    try {
                        val uuid = UUID.fromString(currentSecret)
                        val secretBytes = uuidToDotNetBytes(uuid)
                        val now = Instant.now()
                        val seconds = (System.currentTimeMillis() / 1000).toInt()
                        val secondsPassed = seconds % 30
                        val secondsLeft = 30 - secondsPassed

                        val config = TimeBasedOneTimePasswordConfig(
                            codeDigits = 6,
                            hmacAlgorithm = HmacAlgorithm.SHA256,
                            timeStep = 30,
                            timeStepUnit = TimeUnit.SECONDS
                        )

                        val generator = TimeBasedOneTimePasswordGenerator(secretBytes, config)
                        val code = generator.generate(now)

                        _totpState.value = TotpInfo(code, secondsLeft)
                    } catch (e: Exception) {
                        // Ako je id nevalidan UUID, preskoči generisanje
                        _totpState.value = TotpInfo("", 30)
                    }
                } else {
                    // Ako nema secreta, ne generiši kod
                    _totpState.value = TotpInfo("", 30)
                }

                delay(1000)
            }
        }
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

    data class TotpInfo(
        val totp: String,
        val secondsLeft: Int
    )
}
