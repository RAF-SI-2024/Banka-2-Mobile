package com.cyb.banka2_mobile.totp

import androidx.compose.runtime.Immutable

interface TotpContract {
    @Immutable
    data class TotpState(
        val totp: String = ""
    )
}