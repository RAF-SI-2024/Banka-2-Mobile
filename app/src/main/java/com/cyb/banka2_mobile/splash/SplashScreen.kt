package com.cyb.banka2_mobile.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.cyb.banka2_mobile.R
import com.cyb.banka2_mobile.ui.theme.EnableEdgeToEdge

fun NavGraphBuilder.splash(
    route: String,
    navigateTo: (String) -> Unit
) = composable(
    route = route
) {
    val splashViewModel = hiltViewModel<SplashViewModel>()
    val state = splashViewModel.state.collectAsState()
    EnableEdgeToEdge()
    SplashScreen(
        state = state.value,
        navigateTo = navigateTo
    )
}

@Composable
fun SplashScreen(
    state: SplashState,
    navigateTo: (String) -> Unit
) {
    if (state.loading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF0F1120))
        ) {
            Image(
                painter = painterResource(id = R.drawable.background_splash),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 100.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier.size(240.dp)
                )

                Spacer(modifier = Modifier.height(32.dp))

                SingleRunProgressBar()
            }
        }
    } else {
    }
}

@Composable
private fun SingleRunProgressBar() {
    val progress = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        progress.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 4000,
                easing = LinearEasing
            )
        )
    }

    Box(
        modifier = Modifier
            .width(220.dp)
            .height(10.dp)
            .clip(RoundedCornerShape(5.dp))
            .background(Color.Gray.copy(alpha = 0.3f))
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(progress.value)
                .clip(RoundedCornerShape(5.dp))
                .background(Color.White)
        )
    }
}


