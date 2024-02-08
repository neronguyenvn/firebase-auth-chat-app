package com.neronguyen.psychicmemory.app.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.neronguyen.psychicmemory.core.auth.GoogleAuthClient
import com.neronguyen.psychicmemory.feature.auth.AuthRoute
import org.koin.compose.koinInject

@Composable
fun PsychicMemoryApp(
    googleAuthClient: GoogleAuthClient = koinInject(),
    appState: PsychicMemoryAppState = rememberPsychicMemoryAppState(googleAuthClient)
) {
    val isAlreadySignedIn by appState.isAlreadySignedIn.collectAsStateWithLifecycle()
    AuthRoute(
        isAlreadySignedIn = isAlreadySignedIn,
    )
}
