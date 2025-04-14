package com.cyb.banka2_mobile.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.shimmer
import com.google.accompanist.placeholder.placeholder

@Composable
fun ShimmerContent(paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(paddingValues)
            .padding(horizontal = 24.dp, vertical = 24.dp)
            .background(Color(0xFF0F1120))
    ) {
        ShimmerTopCard()
        Spacer(modifier = Modifier.height(24.dp))
        ShimmerAccountCards()
        Spacer(modifier = Modifier.height(32.dp))
        ShimmerTransactions(count = 6)
    }
}


@Composable
fun ShimmerTopCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .placeholder(
                visible = true,
                highlight = PlaceholderHighlight.shimmer(),
                color = Color.DarkGray),
        shape = RoundedCornerShape(16.dp)
    ) {}
}

@Composable
fun ShimmerAccountCards() {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        items(3) {
            Box(
                modifier = Modifier
                    .width(260.dp)
                    .height(150.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .placeholder(
                        visible = true,
                        highlight = PlaceholderHighlight.shimmer(),
                        color = Color.DarkGray
                    )
            )
        }
    }
}

@Composable
fun ShimmerTransactions(count: Int = 5) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        repeat(count) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .placeholder(
                        visible = true,
                        highlight = PlaceholderHighlight.shimmer(),
                        color = Color.DarkGray
                    )
            )
        }
    }
}

