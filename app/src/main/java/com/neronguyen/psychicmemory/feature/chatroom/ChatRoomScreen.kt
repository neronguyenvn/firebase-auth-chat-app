package com.neronguyen.psychicmemory.feature.chatroom

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.neronguyen.psychicmemory.core.model.ChatMessage
import com.neronguyen.psychicmemory.core.model.User
import com.neronguyen.psychicmemory.core.ui.CurrentUserMessageCard
import com.neronguyen.psychicmemory.core.ui.OtherMessageCard
import com.neronguyen.psychicmemory.feature.chatroom.ChatRoomScreen.Event.ConnectSocket
import com.neronguyen.psychicmemory.feature.chatroom.ChatRoomScreen.Event.InputMessage
import com.neronguyen.psychicmemory.feature.chatroom.ChatRoomScreen.Event.SignOut
import com.neronguyen.psychicmemory.feature.chatroom.component.ChattieTopAppBar
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.screen.Screen
import kotlinx.datetime.toJavaInstant
import kotlinx.parcelize.Parcelize
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale

@Parcelize
data object ChatRoomScreen : Screen {

    data class State(
        val currentUser: User,
        val inputMessage: String,
        val chatHistory: List<ChatMessage>,
        val eventSink: (Event) -> Unit
    ) : CircuitUiState

    sealed class Event {
        data object ConnectSocket : Event()
        data class InputMessage(val value: String) : Event()
        data class SendMessage(val message: String) : Event()
        data object SignOut : Event()
    }
}

@Composable
fun ChatRoomUi(state: ChatRoomScreen.State, modifier: Modifier = Modifier) {

    LaunchedEffect(true) {
        state.eventSink(ConnectSocket)
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            ChattieTopAppBar(state.currentUser) {
                state.eventSink(SignOut)
            }
        }
    ) { padding ->
        Column(Modifier.padding(padding)) {
            LazyColumn(
                modifier = Modifier.weight(1f),
                reverseLayout = true,
                contentPadding = PaddingValues(
                    start = 24.dp,
                    end = 16.dp,
                    top = 16.dp,
                    bottom = 16.dp
                ),
            ) {
                items(state.chatHistory) { message ->
                    when (message) {
                        is ChatMessage.CurrentUserMessage -> CurrentUserMessageCard(message)
                        is ChatMessage.OtherMessage -> OtherMessageCard(
                            message,
                            shouldShowInfo = message.shouldShowInfo
                        )
                    }
                    if (message.shouldShowTimestamp) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Box(Modifier.fillMaxWidth()) {
                            Text(
                                text = DateTimeFormatter
                                    .ofLocalizedDateTime(FormatStyle.MEDIUM)
                                    .withLocale(Locale.getDefault())
                                    .withZone(ZoneId.systemDefault())
                                    .format(message.timestamp.toJavaInstant()),
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
            OutlinedTextField(
                value = state.inputMessage,
                onValueChange = { value -> state.eventSink(InputMessage(value)) },
                placeholder = { Text(text = "Type Message") },
                trailingIcon = {
                    TextButton(onClick = {
                        state.eventSink(
                            ChatRoomScreen.Event.SendMessage(
                                state.inputMessage
                            )
                        )
                    }) {
                        Text(text = "Send")
                    }
                },
                shape = CircleShape,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                    unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer
                ),
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
        }
    }
}
