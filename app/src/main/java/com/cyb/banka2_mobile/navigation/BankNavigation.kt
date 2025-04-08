package com.cyb.banka2_mobile.navigation

import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.cyb.banka2_mobile.home.home
import com.cyb.banka2_mobile.login.login
import com.cyb.banka2_mobile.splash.splash

@Composable
fun BankNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "splash",
        enterTransition = { slideInHorizontally { it } },
        exitTransition = { scaleOut(targetScale = 0.8f)  },
        popExitTransition = { slideOutHorizontally { it } },
        popEnterTransition = { scaleIn(initialScale = 0.8f) }
    ) {
        splash(
            route = "splash",
            navigateTo = {
                if (it == "login")
                    navController.navigate(route = "login")
                else
                    navController.navigate(route = "home")
            }
        )

        login(
            route = "login",
            onUserClick = {
                navController.navigate(route = "home")
            }
        )

        home(
            route = "home"
        )
    }
}