package com.cyb.banka2_mobile.home.card_preview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cyb.banka2_mobile.home.models.TransactionUiModel
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

@Composable
fun TransactionList(transactions: List<TransactionUiModel>) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        transactions.forEach { tx ->
            TransactionItem(tx)
        }
    }
}

@Composable
fun TransactionItem(tx: TransactionUiModel) {
    val amountColor = if (tx.status == "Success") Color(0xFF81C784) else Color(0xFFE57373)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1C2C)),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = null,
                tint = Color(0xFF7962F9),
                modifier = Modifier
                    .size(40.dp)
                    .padding(4.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = tx.purpose,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = formatDate(tx.createdAt),
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }

            Text(
                text = "-$${String.format("%.2f", tx.fromAmount)}",
                color = amountColor,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

fun formatDate(dateString: String): String {
    return try {
        val inputFormat = DateTimeFormatter.ISO_OFFSET_DATE_TIME
        val outputFormat = DateTimeFormatter.ofPattern("dd MMM, HH:mm")
        val parsed = OffsetDateTime.parse(dateString, inputFormat)
        parsed.format(outputFormat)
    } catch (e: Exception) {
        dateString
    }
}