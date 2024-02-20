package com.neronguyen.psychicmemory.app.ui

import androidx.compose.runtime.Composable
import com.neronguyen.psychicmemory.feature.auth.AuthScreen
import com.neronguyen.psychicmemory.feature.chatroom.ChatRoomScreen
import com.slack.circuit.backstack.rememberSaveableBackStack
import com.slack.circuit.foundation.Circuit
import com.slack.circuit.foundation.CircuitCompositionLocals
import com.slack.circuit.foundation.NavigableCircuitContent
import com.slack.circuit.foundation.rememberCircuitNavigator
import org.koin.compose.koinInject

@Composable
fun PsychicMemoryApp(
    circuit: Circuit = koinInject(),
    appState: PsychicMemoryAppState = rememberPsychicMemoryAppState()
) {
    val backStack = rememberSaveableBackStack(
        if (appState.isAlreadySignIn) {
            ChatRoomScreen
        } else AuthScreen
    )

    val navigator = rememberCircuitNavigator(backStack)

    CircuitCompositionLocals(circuit) {
        NavigableCircuitContent(navigator = navigator, backStack = backStack)
    }
}
