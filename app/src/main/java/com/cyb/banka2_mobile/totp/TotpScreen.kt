package com.cyb.banka2_mobile.totp

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.cyb.banka2_mobile.ui.theme.EnableEdgeToEdge

fun NavGraphBuilder.totp(
    route: String,
    onNavigate: (String) -> Unit
) = composable(
    route = route
) {
    val totpViewModel = hiltViewModel<TotpViewModel>()
    val state = totpViewModel.state.collectAsState()
    EnableEdgeToEdge()
    TotpScreen(
        state = state.value,
        onNavigate = onNavigate
    )
}

@Composable
fun TotpScreen(
    state: TotpContract.TotpState,
    onNavigate: (String) -> Unit
) {
    Scaffold(
        containerColor = Color(0xFF0F1120),
        topBar = {},
        contentWindowInsets = WindowInsets.systemBars
    ) {

    }
}
