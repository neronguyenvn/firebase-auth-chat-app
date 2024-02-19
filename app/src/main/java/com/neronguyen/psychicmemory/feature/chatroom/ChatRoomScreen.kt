package com.neronguyen.psychicmemory.feature.chatroom

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.screen.Screen
import kotlinx.parcelize.Parcelize

@Parcelize
data object ChatRoomScreen : Screen {

    data class State(
        val messages: List<String>
    ) : CircuitUiState
}

@Composable
fun ChatRoomUi(state: ChatRoomScreen.State, modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), Alignment.Center) {
        Text(text = "Hello, I'm Chat Room")
    }
}