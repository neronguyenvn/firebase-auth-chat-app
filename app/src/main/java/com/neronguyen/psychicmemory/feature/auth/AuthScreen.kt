package com.neronguyen.psychicmemory.feature.auth

import androidx.activity.ComponentActivity
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.neronguyen.psychicmemory.core.auth.GoogleAuthClient
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.screen.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize

@Parcelize
data object AuthScreen : Screen {

    data class State(
        val isAlreadySignIn: Boolean,
        val eventSink: (Event) -> Unit
    ) : CircuitUiState

    sealed class Event : CircuitUiEvent {
        data object SignInClicked : Event()
    }
}

@Composable
fun AuthUi(state: AuthScreen.State, modifier: Modifier = Modifier) {
    Box(modifier.fillMaxSize(), Alignment.Center) {
        if (!state.isAlreadySignIn) {
            SignInButton {
                state.eventSink(AuthScreen.Event.SignInClicked)
            }
        }
    }
}

@Composable
private fun SignInButton(onSignInClick: () -> Unit) {
    Button(onClick = onSignInClick) {
        Text("Sign In")
    }
}

