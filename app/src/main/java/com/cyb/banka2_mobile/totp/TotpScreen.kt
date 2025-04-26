package com.cyb.banka2_mobile.totp

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.cyb.banka2_mobile.home.HomeContract
import com.cyb.banka2_mobile.ui.theme.EnableEdgeToEdge
import kotlin.math.roundToInt

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
        eventPublisher = {
            totpViewModel.setEvent(it)
        },
        onNavigate = onNavigate
    )
}

@Composable
fun TotpScreen(
    state: TotpContract.TotpState,
    eventPublisher: (uiEvent: TotpContract.TotpEvent) -> Unit,
    onNavigate: (String) -> Unit
) {
    val animatedProgress = remember { Animatable(0f) }
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = ""
    )

    val gradientColors = listOf(Color(0xFF5B5FEF), Color(0xFF7F83F7))

    val timeLeft = remember(animatedProgress.value) {
        ((1f - animatedProgress.value) * 30).roundToInt()
    }

    LaunchedEffect(Unit) {
        while (true) {
            animatedProgress.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 30_000, easing = LinearEasing)
            )
            animatedProgress.snapTo(0f)
        }
    }

    Scaffold(
        containerColor = Color(0xFF0F1120),
        topBar = {},
        bottomBar = {
            NavigationBar {
                state.navigationItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = state.selectedItemNavigationIndex == index,
                        onClick = {
                            eventPublisher(TotpContract.TotpEvent.SelectedNavigationIndex(index))
                            when (index) {
                                0 -> onNavigate("home")
                                2 -> onNavigate("exchange")
                                3 -> onNavigate("loans")
                            }
                        },
                        icon = {
                            Icon(imageVector = if (index == state.selectedItemNavigationIndex) {
                                item.selectedIcon
                            } else item.unselectedIcon,
                                contentDescription = item.title)
                        }
                    )

                }
            }
        },
        contentWindowInsets = WindowInsets.systemBars
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(
                    brush = Brush.verticalGradient(
                        listOf(
                            Color(0xFF0F1120),
                            Color(0xFF1A1C33),
                            Color(0xFF0F1120)
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Your verification code",
                    color = Color.White,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.SemiBold,
                    style = TextStyle(
                        shadow = Shadow(
                            color = Color(0xFF5B5FEF).copy(alpha = glowAlpha),
                            offset = Offset(0f, 0f),
                            blurRadius = 12f
                        )
                    ),
                    modifier = Modifier.padding(bottom = 20.dp)
                )

                Spacer(modifier = Modifier.height(48.dp))

                Box(
                    modifier = Modifier.size(300.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val strokeWidth = 16.dp.toPx()
                        val radius = size.minDimension / 2 - strokeWidth / 2

                        drawArc(
                            brush = Brush.sweepGradient(gradientColors),
                            startAngle = -90f,
                            sweepAngle = 360 * animatedProgress.value,
                            useCenter = false,
                            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                        )

                        drawCircle(
                            color = Color(0xFF1A1C33),
                            radius = radius - strokeWidth / 2
                        )
                    }

                    Text(
                        text = state.totp.chunked(3).joinToString("-"),
                        color = Color.White,
                        fontSize = 42.sp,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        style = TextStyle(
                            shadow = Shadow(
                                color = Color(0xFF5B5FEF).copy(alpha = glowAlpha),
                                offset = Offset(0f, 0f),
                                blurRadius = 25f
                            )
                        )
                    )
                }

                Spacer(modifier = Modifier.height(48.dp))

                Text(
                    text = "New code in ${timeLeft}s",
                    color = Color.White.copy(alpha = 0.9f),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium,
                    style = TextStyle(
                        shadow = Shadow(
                            color = Color(0xFF5B5FEF).copy(alpha = glowAlpha),
                            offset = Offset(0f, 0f),
                            blurRadius = 10f
                        )
                    )
                )
            }
        }
    }
}