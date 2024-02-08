package com.neronguyen.psychicmemory.app.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.neronguyen.psychicmemory.core.auth.GoogleAuthClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@Composable
fun rememberPsychicMemoryAppState(
    googleAuthClient: GoogleAuthClient,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
): PsychicMemoryAppState {
    return remember(
        coroutineScope,
        googleAuthClient
    ) {
        PsychicMemoryAppState(
            coroutineScope,
            googleAuthClient
        )
    }
}

class PsychicMemoryAppState(
    coroutineScope: CoroutineScope,
    googleAuthClient: GoogleAuthClient
) {
    val isAlreadySignedIn = googleAuthClient.currentUser
        .map { it != null }
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false,
        )
}
