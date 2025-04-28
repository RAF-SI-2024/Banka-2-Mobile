package com.cyb.banka2_mobile.loans

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.cyb.banka2_mobile.exchange.ErrorScreen
import com.cyb.banka2_mobile.exchange.ShimmerLoading
import com.cyb.banka2_mobile.loans.api.models.LoanUiModel
import com.cyb.banka2_mobile.ui.theme.EnableEdgeToEdge
fun NavGraphBuilder.loans(
    route: String,
    onNavigate: (String) -> Unit
) = composable(
    route = route
) {
    val loansViewModel = hiltViewModel<LoansViewModel>()
    val state = loansViewModel.state.collectAsState()
    EnableEdgeToEdge()
    LoansScreen(
        state = state.value,
        eventPublisher = {
            loansViewModel.setEvent(it)
        },
        onNavigate = onNavigate
    )
}

@Composable
fun LoansScreen(
    state: LoansContract.LoansState,
    eventPublisher: (uiEvent: LoansContract.LoansEvent) -> Unit,
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
                            eventPublisher(LoansContract.LoansEvent.SelectedNavigationIndex(index))
                            when (index) {
                                0 -> onNavigate("home")
                                1 -> onNavigate("totp")
                                2 -> onNavigate("exchange")
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
                    LoansList(
                        loans = state.listOfLoans
                    )
                }
            }
        }
    }
}

@Composable
fun LoansList(loans: List<LoanUiModel>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Loans Overview",
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
                    LoansTableHeader()
                }
                items(loans) { loan ->
                    LoansTableRow(loan)
                }
            }
        }
    }
}

@Composable
fun LoansTableHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF1A1C2C))
            .padding(horizontal = 20.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        LoansColumnHeader("Client", Modifier.width(120.dp))
        LoansColumnHeader("Account", Modifier.width(150.dp))
        LoansColumnHeader("Loan Type", Modifier.width(120.dp))
        LoansColumnHeader("Amount", Modifier.width(100.dp))
        LoansColumnHeader("Currency", Modifier.width(80.dp))
        LoansColumnHeader("Period", Modifier.width(80.dp))
        LoansColumnHeader("Remaining", Modifier.width(100.dp))
        LoansColumnHeader("Status", Modifier.width(80.dp))
    }
}

@Composable
fun LoansColumnHeader(title: String, modifier: Modifier) {
    Text(
        text = title,
        color = Color.LightGray,
        style = MaterialTheme.typography.titleSmall,
        modifier = modifier
    )
}

@Composable
fun LoansTableRow(loan: LoanUiModel) {
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
            LoansCell(loan.clientName, Modifier.width(120.dp))
            LoansCell(loan.accountNumber, Modifier.width(150.dp))
            LoansCell(loan.loanTypeName, Modifier.width(120.dp))
            LoansCell(String.format("%.2f", loan.amount), Modifier.width(100.dp))
            LoansCell(loan.currencyCode, Modifier.width(80.dp))
            LoansCell("${loan.period} months", Modifier.width(80.dp))
            LoansCell(String.format("%.2f", loan.remainingAmount), Modifier.width(100.dp))
            LoansCell(
                if (loan.status == 1) "Active" else "Inactive",
                Modifier.width(80.dp)
            )
        }
    }
}

@Composable
fun LoansCell(value: String, modifier: Modifier) {
    Text(
        text = value,
        color = Color.White,
        style = MaterialTheme.typography.bodyLarge,
        modifier = modifier
    )
}
