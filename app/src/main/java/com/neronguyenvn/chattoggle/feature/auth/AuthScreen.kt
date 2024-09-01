package com.neronguyenvn.chattoggle.feature.auth

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.screen.Screen
import kotlinx.parcelize.Parcelize

@Parcelize
data object AuthScreen : Screen {

    data class State(
        val isLoading: Boolean,
        val eventSink: (Event) -> Unit
    ) : CircuitUiState

    sealed class Event : CircuitUiEvent {
        data object SignInClicked : Event()
    }
}

@Composable
fun AuthUi(state: AuthScreen.State, modifier: Modifier = Modifier) {
    Box(modifier.fillMaxSize(), Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Welcome to Nero Chat App")
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                enabled = !state.isLoading,
                onClick = {
                    state.eventSink(AuthScreen.Event.SignInClicked)
                }) {
                Text("Sign In")
            }
        }
    }
}
