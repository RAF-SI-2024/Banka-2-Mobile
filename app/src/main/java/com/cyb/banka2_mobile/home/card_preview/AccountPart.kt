package com.cyb.banka2_mobile.home.card_preview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cyb.banka2_mobile.home.models.AccountUiModel
import com.cyb.banka2_mobile.home.models.CardUiModel

@Composable
fun AccountCardsSection(cards: List<CardUiModel>) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 4.dp)
    ) {
        cards.forEach { card ->
            items(card.accounts) { account ->
                AccountCard(account)
            }
        }
    }
}

@Composable
fun AccountCard(account: AccountUiModel) {
    val gradientBrush = Brush.linearGradient(
        colors = listOf(Color(0xFF5B5FEF), Color(0xFF7F83F7))
    )

    Box(
        modifier = Modifier
            .width(260.dp)
            .height(150.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(gradientBrush)
            .padding(20.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "•••• ${account.accountNumber.takeLast(4)}",
                color = Color.White,
                fontSize = 16.sp
            )
            Column {
                Text(
                    text = "Balance",
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 12.sp
                )
                Text(
                    text = "$8,482.00",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Text(
                text = "Valid Thru 08/30",
                color = Color.White.copy(alpha = 0.6f),
                fontSize = 12.sp
            )
        }
    }
}
