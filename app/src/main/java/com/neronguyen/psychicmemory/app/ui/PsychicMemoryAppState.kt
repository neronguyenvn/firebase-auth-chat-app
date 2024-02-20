package com.neronguyen.psychicmemory.app.ui

import org.koin.compose.koinInject

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.neronguyen.psychicmemory.core.auth.GoogleAuthClient

@Composable
fun rememberPsychicMemoryAppState(
    googleAuthClient: GoogleAuthClient = koinInject(),
): PsychicMemoryAppState {
    return remember(googleAuthClient) {
        PsychicMemoryAppState(googleAuthClient)
    }
}

class PsychicMemoryAppState(googleAuthClient: GoogleAuthClient) {
    val isAlreadySignIn = googleAuthClient.auth.currentUser != null
}
