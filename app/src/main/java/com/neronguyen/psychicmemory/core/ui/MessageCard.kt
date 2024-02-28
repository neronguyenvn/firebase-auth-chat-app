package com.neronguyen.psychicmemory.core.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.neronguyen.psychicmemory.core.model.ChatMessage

private const val MESSAGE_CARD_CORNER = 20
private val MessageTextModifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)

@Composable
fun CurrentUserMessageCard(message: ChatMessage) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
        Surface(
            shape = RoundedCornerShape(
                MESSAGE_CARD_CORNER,
                MESSAGE_CARD_CORNER,
                0,
                MESSAGE_CARD_CORNER
            ),
            color = MaterialTheme.colorScheme.primary,
        ) {
            Text(
                text = message.content,
                modifier = MessageTextModifier
            )
        }
    }
}

@Composable
fun OtherMessageCard(message: ChatMessage, shouldShowInfo: Boolean) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
        Column {
            if (shouldShowInfo) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    AsyncImage(
                        model = message.senderInfo.photoUrl,
                        contentDescription = "avatar",
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(32.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = message.senderInfo.name,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
            }
            OtherMessageText(content = message.content)
        }
    }
}

@Composable
private fun OtherMessageText(content: String) {
    Surface(
        shape = RoundedCornerShape(
            0,
            MESSAGE_CARD_CORNER,
            MESSAGE_CARD_CORNER,
            MESSAGE_CARD_CORNER
        ),
        color = MaterialTheme.colorScheme.secondaryContainer,
    ) {
        Text(
            text = content,
            modifier = MessageTextModifier
        )
    }
}