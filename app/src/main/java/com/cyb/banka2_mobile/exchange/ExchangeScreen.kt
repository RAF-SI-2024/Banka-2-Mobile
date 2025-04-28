package com.cyb.banka2_mobile.exchange

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.cyb.banka2_mobile.exchange.api.models.CurrencyExchangeUiModel
import com.cyb.banka2_mobile.ui.theme.EnableEdgeToEdge
import com.google.accompanist.placeholder.material.placeholder

fun NavGraphBuilder.exchange(
    route: String,
    onNavigate: (String) -> Unit
) = composable(
    route = route
) {
    val exchangeViewModel = hiltViewModel<ExchangeViewModel>()
    val state = exchangeViewModel.state.collectAsState()
    EnableEdgeToEdge()
    ExchangeScreen(
        state = state.value,
        eventPublisher = {
            exchangeViewModel.setEvent(it)
        },
        onNavigate = onNavigate
    )
}

@Composable
fun ExchangeScreen(
    state: ExchangeContract.ExchangeState,
    eventPublisher: (uiEvent: ExchangeContract.ExchangeEvent) -> Unit,
    onNavigate: (String) -> Unit
) {
    Scaffold(
        containerColor = Color(0xFF0F1120),
        topBar = {},
        bottomBar = {
            NavigationBar {
                state.navigationItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = state.selectedItemNavigationIndex == index,
                        onClick = {
                            eventPublisher(ExchangeContract.ExchangeEvent.SelectedNavigationIndex(index))
                            when (index) {
                                0 -> onNavigate("home")
                                1 -> onNavigate("totp")
                                3 -> onNavigate("loans")
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = if (index == state.selectedItemNavigationIndex) {
                                    item.selectedIcon
                                } else item.unselectedIcon,
                                contentDescription = item.title
                            )
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
        ) {
            when {
                state.isLoading -> {
                    ShimmerLoading()
                }

                state.errorFetching -> {
                    ErrorScreen(
                        onNavigateHome = { onNavigate("home_route") }
                    )
                }

                else -> {
                    ExchangeList(
                        exchanges = state.listOfExchangeItems
                    )
                }
            }
        }
    }
}

@Composable
fun ExchangeList(exchanges: List<CurrencyExchangeUiModel>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Exchange Rates",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(Alignment.CenterHorizontally)
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .horizontalScroll(rememberScrollState())
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(end = 12.dp)
            ) {
                item {
                    ExchangeTableHeader()
                }
                items(exchanges) { exchange ->
                    ExchangeTableRow(exchange)
                }
            }
        }
    }
}

@Composable
fun ExchangeTableHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF1A1C2C))
            .padding(horizontal = 20.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        ExchangeColumnHeader("From", Modifier.width(90.dp))
        ExchangeColumnHeader("To", Modifier.width(90.dp))
        ExchangeColumnHeader("Rate", Modifier.width(110.dp))
        ExchangeColumnHeader("Inverse", Modifier.width(110.dp))
        ExchangeColumnHeader("Commission", Modifier.width(110.dp))
        ExchangeColumnHeader("Status", Modifier.width(90.dp))
    }
}

@Composable
fun ExchangeColumnHeader(title: String, modifier: Modifier) {
    Text(
        text = title,
        color = Color.LightGray,
        style = MaterialTheme.typography.titleSmall,
        modifier = modifier
    )
}

@Composable
fun ExchangeTableRow(exchange: CurrencyExchangeUiModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF262838))
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            ExchangeCell(exchange.currencyFrom.code, Modifier.width(90.dp))
            ExchangeCell(exchange.currencyTo.code, Modifier.width(90.dp))
            ExchangeCell(String.format("%.4f", exchange.rate), Modifier.width(110.dp))
            ExchangeCell(String.format("%.4f", exchange.inverseRate), Modifier.width(110.dp))
            ExchangeCell("${String.format("%.2f", exchange.commission)}%", Modifier.width(110.dp))
            ExchangeCell(if (exchange.currencyFrom.status) "Active" else "Inactive", Modifier.width(90.dp))
        }
    }
}

@Composable
fun ExchangeCell(value: String, modifier: Modifier) {
    Text(
        text = value,
        color = Color.White,
        style = MaterialTheme.typography.bodyLarge,
        modifier = modifier
    )
}

@Composable
fun ShimmerLoading() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(5) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(24.dp)
                    .placeholder(
                        visible = true,
//                        highlight = shimmer()
                    ),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {}
        }
    }
}

@Composable
fun ErrorScreen(onNavigateHome: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Something went wrong!",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onNavigateHome) {
            Text(text = "Go back Home")
        }
    }
}
