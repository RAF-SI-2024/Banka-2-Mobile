package com.cyb.banka2_mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.cyb.banka2_mobile.navigation.BankNavigation
import com.cyb.banka2_mobile.ui.theme.Banka2MobileTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Banka2MobileTheme {
                BankNavigation()
            }
        }
    }
}