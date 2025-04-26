import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cyb.banka2_mobile.home.models.AccountUiModel
import com.cyb.banka2_mobile.home.models.CardUiModel

@Composable
fun AccountCardsSection(
    cards: List<CardUiModel>,
    selectedAccount: AccountUiModel?,
    onAccountSelected: (AccountUiModel) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 4.dp)
    ) {
        cards.forEach { card ->
            items(card.accounts) { account ->
                AccountCard(
                    account = account,
                    isSelected = account.accountId == selectedAccount?.accountId,
                    onClick = { onAccountSelected(account) }
                )
            }
        }
    }
}

@Composable
fun AccountCard(
    account: AccountUiModel,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val gradientBrush = Brush.linearGradient(
        colors = listOf(Color(0xFF5B5FEF), Color(0xFF7F83F7))
    )

    Box(
        modifier = Modifier
            .width(260.dp)
            .height(150.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(gradientBrush)
            .then(
                if (isSelected) Modifier.border(
                    BorderStroke(3.dp, Color.Yellow),
                    RoundedCornerShape(20.dp)
                ) else Modifier
            )
            .clickable { onClick() }
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
                    text = "Account",
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 12.sp
                )
                Text(
                    text = account.accountNumber,
                    color = Color.White,
                    fontSize = 18.sp
                )
            }
        }
    }
}