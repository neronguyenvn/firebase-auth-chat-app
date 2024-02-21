package com.neronguyen.psychicmemory.app.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun rememberPsychicMemoryAppState(): PsychicMemoryAppState {
    return remember { PsychicMemoryAppState() }
}

class PsychicMemoryAppState {
    val isAlreadySignIn = Firebase.auth.currentUser != null
}
