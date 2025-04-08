package com.cyb.banka2_mobile.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.cyb.banka2_mobile.R
import com.cyb.banka2_mobile.ui.theme.EnableEdgeToEdge

fun NavGraphBuilder.home(
    route: String
) = composable(
    route = route
) {
    val homeViewModel = hiltViewModel<HomeViewModel>()
    val state = homeViewModel.state.collectAsState()
    EnableEdgeToEdge()
    HomeScreen(
        state = state.value,
        eventPublisher = {
            homeViewModel.setEvent(it)
        }
    )
}

@Composable
fun HomeScreen(
    state: HomeContract.HomeState,
    eventPublisher: (uiEvent: HomeContract.HomeEvent) -> Unit
) {
    Scaffold(
        containerColor = Color(0xFF0F1120),
        topBar = {}
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .align(Alignment.TopCenter),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1C2C)),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, Color.White.copy(alpha = 0.1f)),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min)
                        .padding(24.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Welcome back 👋",
                            color = Color.White,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.SemiBold
                        )

                        Text(
                            text = "${state.homeUiModel?.firstName} ${state.homeUiModel?.lastName}",
                            color = Color.White,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )

                        state.homeUiModel?.email?.let {
                            Text(
                                text = it,
                                color = Color.Gray,
                                fontSize = 17.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Image(
                        painter = painterResource(id = R.drawable.homepage_pig),
                        contentDescription = "Piggy bank",
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(130.dp),
                        contentScale = ContentScale.Fit
                    )
                }
            }

        }
    }
}
